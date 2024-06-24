// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.subsystem_architectures.swerve;

import frc.lib.drivers.TalonFX.TalonFXVelocityControl;

import com.ctre.phoenix6.configs.TalonFXConfiguration;

import frc.lib.debugging.ControlTuning.ProfiledPIDConfig;
import frc.lib.drivers.TalonFX.TalonFXPositionControl;

/** Add your docs here. */
public class SwerveConfiguration {
    public record GlobalConfig(double frontBackTrackWidthMeters, double leftRightTrackWidthMeters, int pigeonID, ProfiledPIDConfig headingControllerConfig){}
    public record ModuleSpecificConfig(int turnMotorID, int driveMotorID, int encoderID, double encoderOffset){}
    public record SharedModuleConfig(TalonFXConfiguration turnConfig, TalonFXConfiguration driveConfig, double turnRatio, double driveRatio, double wheelCircumferenceMeters, double maxModuleSpeed){}
}
