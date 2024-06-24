// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.subsystem_architectures.swerve;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.signal_processing.JoystickPolarSignalProcesser;
import frc.lib.signal_processing.JoystickSignalProcessor;
import frc.lib.signal_processing.JoystickSignalProcessorConfig;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import java.util.function.Supplier;

public class GenericSwerveSubsystem extends SubsystemBase {
  SwerveModule[] modules = new SwerveModule[4];
  SwerveDriveKinematics kinematics;
  SwerveDrivePoseEstimator poseEstimator;
  Pigeon2 pigeon;
  double maxModuleSpeed;
  ProfiledPIDController headingController;
  SwerveAccelerationLimiter accelerationLimiter;
  boolean limitAcceleration = false;
  boolean headingAlign = false;

  public GenericSwerveSubsystem(SwerveConfiguration.GlobalConfig globalConfig, SwerveConfiguration.SharedModuleConfig sharedModuleConfig, SwerveConfiguration.ModuleSpecificConfig[] moduleConfigs) {
    //Apply configurations to each module
    //Standard order, front left, front right, back left, back right
    for(int i = 0; i < 4; i++){
      modules[i] = new SwerveModule(sharedModuleConfig, moduleConfigs[i]);
    }


    //Create kinematics object
    double dX = globalConfig.frontBackTrackWidthMeters() / 2;
    double dY = globalConfig.leftRightTrackWidthMeters() / 2;
    kinematics = new SwerveDriveKinematics(
      new Translation2d(dX, dY),
      new Translation2d(dX, -dY),
      new Translation2d(-dX, dY),
      new Translation2d(-dX, -dY)
    );

    //Create pigeon
    pigeon = new Pigeon2(globalConfig.pigeonID());

    maxModuleSpeed = sharedModuleConfig.maxModuleSpeed();

    //Setup headingController
    headingController = globalConfig.headingControllerConfig().getController();
    headingController.enableContinuousInput(-Math.PI, Math.PI);

    //Setup poseEstimator
    poseEstimator = new SwerveDrivePoseEstimator(kinematics, getPigeonRotation2d(), getModulePositions(), new Pose2d());
  }

  
  public void addAccelerationLimit(double x, double y, double t, double skid){
    accelerationLimiter = new SwerveAccelerationLimiter(x, y, t, skid);
    limitAcceleration = true;
  }


  public void drive(ChassisSpeeds speeds, boolean fieldCentric){
    //Convert field centric to robot centric if fieldCentric is true
    if(fieldCentric){
      speeds = ChassisSpeeds.fromFieldRelativeSpeeds(speeds, getHeadingRotation2d());
    }

    //Limit accelerations in robot-centric coordinates with SwerveAccelerationLimiter object
    if(limitAcceleration){
      speeds = accelerationLimiter.calculate(speeds);
    }

    //Using kinematics, determine individual module states
    SwerveModuleState[] desiredStates = kinematics.toSwerveModuleStates(speeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, maxModuleSpeed);

    //Apply states to each module
    for(int i = 0; i < 4; i++){
      modules[i].setState(desiredStates[i]);
    }
  }


  //Adds heading lock functionality to driving method
  private void driveWithHeadingAlign(boolean fieldCentric, Translation2d translation, double turn){
    //Exit heading align if turn signal to large enough
    if(turn > 0.1){
      headingAlign = false;
    }

    if(headingAlign){
      turn = headingController.calculate(getHeadingDegrees());
    } 

    if(fieldCentric){
      drive(ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), turn, getHeadingRotation2d()), true);
    } else{
      drive(new ChassisSpeeds(translation.getX(), translation.getY(), turn), false);
    }
  }


  public Command driveCommand(Supplier<Double> rawX, Supplier<Double> rawY, Supplier<Double> rawT, JoystickSignalProcessorConfig radialConfig, JoystickSignalProcessorConfig turnConfig, boolean fieldCentric){
    return this.run(
      () -> {
        driveWithHeadingAlign(
          fieldCentric, 
          JoystickPolarSignalProcesser.calculate(new Translation2d(rawX.get(), rawY.get()), radialConfig), 
          JoystickSignalProcessor.calculate(rawT.get(), turnConfig)
          );
      }
    );
  }


  public Command headingLockCommand(double angleDegrees){
    return this.runOnce(() -> setHeadingLock(angleDegrees));
  }

  /**
   * @param angleDegrees 0 is facing the opposite alliance
   */
  private void setHeadingLock(double angleDegrees){
    headingController.setGoal(angleDegrees);
    headingAlign = true;
  }


  //Returns rotation2d with ccw+
  private Rotation2d getPigeonRotation2d(){
    return pigeon.getRotation2d();
  }

  private SwerveModulePosition[] getModulePositions(){
    return new SwerveModulePosition[]{
      modules[0].getPosition(),
      modules[1].getPosition(),
      modules[2].getPosition(),
      modules[3].getPosition()
    };
  }


  //Returns the field centric heading of the robot with apropriate gryo offsets
  private Rotation2d getHeadingRotation2d(){
    return getPigeonRotation2d();
  }

  private double getHeadingDegrees(){
    return getHeadingRotation2d().getDegrees();
  }


  public Pose2d getPose(){
    return poseEstimator.getEstimatedPosition();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //Update heading controller velocity information
    headingController.calculate(getHeadingDegrees());

    //Update odemetry
    poseEstimator.update(getPigeonRotation2d(), getModulePositions());
  }

}
