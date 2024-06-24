// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.pathplanner.lib.util.PIDConstants;

import edu.wpi.first.wpilibj.Joystick;
import frc.lib.debugging.ControlTuning.ProfiledPIDConfig;
import frc.lib.drivers.TalonFX.TalonFXControlConfiguration;
import frc.lib.signal_processing.JoystickSignalProcessorConfig;
import frc.lib.subsystem_architectures.swerve.SwerveConfiguration;

/** Add your docs here. */
public class ExampleConstants {
    public static class Swerve {
        public static final SwerveConfiguration.GlobalConfig kGlobalConfig = new SwerveConfiguration.GlobalConfig(
            0, 
            0, 
            0, 
            new ProfiledPIDConfig(0, 0, 0, 0, 0),
            new PIDConstants(5, 0, 0),
            new PIDConstants(5, 0, 0)
            );

        public static final SwerveConfiguration.SharedModuleConfig kSharedModuleConfig = new SwerveConfiguration.SharedModuleConfig(
            new TalonFXControlConfiguration.PositionConfiguration(0, 0, 0), 
            new TalonFXControlConfiguration.VelocityConfiguration(0, 0, 0, 0, 0), 
            0, 
            0, 
            0, 
            0
            );

        public static final SwerveConfiguration.ModuleSpecificConfig kModuleConfigs[] = {
            new SwerveConfiguration.ModuleSpecificConfig(0, 0, 0, 0),
            new SwerveConfiguration.ModuleSpecificConfig(0, 0, 0, 0),
            new SwerveConfiguration.ModuleSpecificConfig(0, 0, 0, 0),
            new SwerveConfiguration.ModuleSpecificConfig(0, 0, 0, 0)
        };

        //Joystick control configs
        public static final JoystickSignalProcessorConfig kRadialConfig = new JoystickSignalProcessorConfig(0, 0, 0);
        public static final JoystickSignalProcessorConfig kTurnConfig = new JoystickSignalProcessorConfig(0, 0, 0);
    }
}
