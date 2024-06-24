// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.subsystem_architectures.swerve;

import frc.lib.drivers.TalonFX.AntTalonFX;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/** Add your docs here. */
public class SwerveModule{
    AntTalonFX driveMotor, turnMotor;

    public SwerveModule(SwerveConfiguration.SharedModuleConfig sharedConfig, SwerveConfiguration.ModuleSpecificConfig moduleConfig){
        driveMotor = new AntTalonFX(moduleConfig.driveMotorID(), "Swerve Drive - " + moduleConfig.driveMotorID());
        turnMotor = new AntTalonFX(moduleConfig.turnMotorID(), "Swerve Drive - " + moduleConfig.turnMotorID());

        //Edit and apply motor configurations
        TalonFXConfiguration driveConfig = new TalonFXConfiguration();

        driveConfig.Slot0.kS = sharedConfig.driveConfig().kS();
        driveConfig.Slot0.kV = sharedConfig.driveConfig().kV();
        driveConfig.Slot0.kP = sharedConfig.driveConfig().kP();
        driveConfig.Slot0.kI = sharedConfig.driveConfig().kI();
        driveConfig.Slot0.kD = sharedConfig.driveConfig().kD();
        
        driveConfig.Feedback.SensorToMechanismRatio = sharedConfig.driveRatio() / sharedConfig.wheelCircumferenceMeters(); //Set gear ratio for drive to be equivalent to distance

        TalonFXConfiguration turnConfig = new TalonFXConfiguration();

        turnConfig.Slot0.kP = sharedConfig.turnConfig().kP();
        turnConfig.Slot0.kI = sharedConfig.turnConfig().kI();
        turnConfig.Slot0.kD = sharedConfig.turnConfig().kD();
        
        turnConfig.Feedback.SensorToMechanismRatio = sharedConfig.turnRatio(); //Set gear ratio for drive
        turnConfig.ClosedLoopGeneral.ContinuousWrap = true; //Continuous mechanism wrap


        driveMotor.applyConfig(driveConfig);
        turnMotor.applyConfig(turnConfig);
    }

    public void enableTelemetry(){
        ShuffleboardLayout moduleLayout = Shuffleboard.getTab("Motors").getLayout("Swerve Module", BuiltInLayouts.kList);
    }

    public void setState(SwerveModuleState desiredState){
        desiredState = SwerveModuleState.optimize(desiredState, getTurnPosition()); //Change direction to turn less, if needed

        driveMotor.setVelocityWithConfiguration(desiredState.speedMetersPerSecond);
        turnMotor.setPositionWithConfiguration(desiredState.angle.getRotations());
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(getSpeedMetersPerSecond(), getTurnPosition());
    }

    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(getDrivePosition(), getTurnPosition());
    }


    private Rotation2d getTurnPosition(){
        return Rotation2d.fromRotations(turnMotor.getPosition().getValueAsDouble());
    }

    private double getDrivePosition(){
        return driveMotor.getPosition().getValueAsDouble();
    }

    private double getSpeedMetersPerSecond(){
        return driveMotor.getVelocity().getValueAsDouble();
    }
}
