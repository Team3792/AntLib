// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.lib.drivers.SRXMagEncoder;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.lib.debugging.ConnectionManager;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;

public class RobotContainer {
  SRXMagEncoder encoder1 = new SRXMagEncoder(0, "left encoder");
  SRXMagEncoder encoder2 = new SRXMagEncoder(1, "right encoder");
  IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  CommandPS5Controller controller = new CommandPS5Controller(0);
  Command intakeCommand = new FunctionalCommand(intakeSubsystem::intake, () -> {}, intakeSubsystem::stop, intakeSubsystem::hasGamePiece, intakeSubsystem);

  public RobotContainer() {
    ConnectionManager.start();
    configureBindings();
  }

  private void configureBindings() {
    controller.R1().whileTrue(intakeCommand);
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
