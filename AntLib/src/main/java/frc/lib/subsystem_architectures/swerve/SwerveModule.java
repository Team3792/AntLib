// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.subsystem_architectures.swerve;

import frc.lib.drivers.TalonFX.AntTalonFX;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

/** Add your docs here. */
public class SwerveModule {
    AntTalonFX driveMotor, turnMotor;

    public SwerveModule(SwerveModuleConfiguration config){
        driveMotor = new AntTalonFX(config.driveMotorID, "Swerve Drive - " + config.driveMotorID);
        turnMotor = new AntTalonFX(config.turnMotorID, "Swerve Drive - " + config.turnMotorID);

        //Edit and apply motor configurations
        TalonFXConfiguration driveConfig = config.driveVelocityControl.getConfig();
        driveConfig.Feedback.SensorToMechanismRatio = config.driveRatio / config.wheelCircumferenceMeters; //Set gear ratio for drive to be equivalent to distance

        TalonFXConfiguration turnConfig = config.turnPositionControl.getConfig();
        turnConfig.Feedback.SensorToMechanismRatio = config.turnRatio; //Set gear ratio for drive
        turnConfig.ClosedLoopGeneral.ContinuousWrap = true; //Continuous mechanism wrap


        driveMotor.applyConfig(driveConfig);
        turnMotor.applyConfig(turnConfig);
    }

    public void setState(SwerveModuleState desiredState){
        desiredState = SwerveModuleState.optimize(desiredState, getTurnPosition()); //Change direction to turn less, if needed

        driveMotor.setVelocityWithConfiguration(desiredState.speedMetersPerSecond);
        turnMotor.setPositionWithConfiguration(desiredState.angle.getRotations());
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(getSpeedMetersPerSecond(), getTurnPosition());
    }


    private Rotation2d getTurnPosition(){
        return Rotation2d.fromRotations(turnMotor.getPosition().getValueAsDouble());
    }

    private double getSpeedMetersPerSecond(){
        return driveMotor.getVelocity().getValueAsDouble();
    }
}
