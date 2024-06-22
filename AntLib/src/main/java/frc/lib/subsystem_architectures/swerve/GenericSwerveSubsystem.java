// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.subsystem_architectures.swerve;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class GenericSwerveSubsystem extends SubsystemBase {
  SwerveModule[] modules = new SwerveModule[4];
  SwerveDriveKinematics kinematics;
  Pigeon2 pigeon;
  double maxModuleSpeed;
  ProfiledPIDController headingController;
  SwerveAccelerationLimiter accelerationLimiter;
  boolean limitAcceleration = false;

  public GenericSwerveSubsystem(GlobalSwerveConfiguration globalConfig, SwerveModuleConfiguration[] moduleConfigs) {
    //Apply configurations to each module
    //Standard order, front left, front right, back left, back right
    for(int i = 0; i < 4; i++){
      modules[i] = new SwerveModule(globalConfig, moduleConfigs[i]);
    }


    //Create kinematics object
    double dX = globalConfig.frontBackTrackWidthMeters / 2;
    double dY = globalConfig.leftRightTrackWidthMeters / 2;
    kinematics = new SwerveDriveKinematics(
      new Translation2d(dX, dY),
      new Translation2d(dX, -dY),
      new Translation2d(-dX, dY),
      new Translation2d(-dX, -dY)
    );

    //Create pigeon
    pigeon = new Pigeon2(globalConfig.pigeonID);

    maxModuleSpeed = globalConfig.maxModuleSpeed;

    //Setup headingController
    headingController = globalConfig.headingControllerConfig.getController();
    headingController.enableContinuousInput(-Math.PI, Math.PI);
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


  //Returns rotation2d with ccw+
  private Rotation2d getPigeonRotation2d(){
    return pigeon.getRotation2d();
  }

  //Returns the field centric heading of the robot with apropriate gryo offsets
  private Rotation2d getHeadingRotation2d(){
    return getPigeonRotation2d();
  }

  private double getHeadingDegrees(){
    return getHeadingRotation2d().getDegrees();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    headingController.calculate(getHeadingDegrees());
    
  }
}
