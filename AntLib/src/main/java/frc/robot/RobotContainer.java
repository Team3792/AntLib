// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.ejml.equation.Sequence;

import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.lib.drivers.SRXMagEncoder;
import frc.lib.drivers.PS5Controller.AntPS5Controller;
import frc.lib.drivers.TalonFX.AntTalonFX;
import frc.lib.subsystem_architectures.swerve.GenericSwerveSubsystem;
import frc.robot.Subsystems.ExampleIntakeSubsystem;
import frc.lib.debugging.ConnectionManager;
import frc.lib.debugging.ControlTuning.DashboardProfiledPIDTuner;
import frc.lib.debugging.ControlTuning.DashboardTuner;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import java.lang.String;

public class RobotContainer {
  ExampleIntakeSubsystem intakeSubsystem = new ExampleIntakeSubsystem();
  GenericSwerveSubsystem swerveSubsystem = new GenericSwerveSubsystem(
    ExampleConstants.Swerve.kGlobalConfig,
    ExampleConstants.Swerve.kSharedModuleConfig,
    ExampleConstants.Swerve.kModuleConfigs
    );

  AntPS5Controller controller = new AntPS5Controller(0, "Driver Controller");
  
  public RobotContainer() {
    ConnectionManager.start();
    configureBindings();
  }

  private void configureBindings() {
    swerveSubsystem.setDefaultCommand(
      swerveSubsystem.driveCommand(
        () -> -controller.getLeftY(), 
        () -> -controller.getLeftX(), 
        () -> -controller.getRightX(), 
        ExampleConstants.Swerve.kRadialConfig, 
        ExampleConstants.Swerve.kTurnConfig, 
        true)
    );
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
