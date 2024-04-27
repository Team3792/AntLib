// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.ejml.equation.Sequence;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.lib.drivers.SRXMagEncoder;
import frc.lib.drivers.PS5Controller.AntPS5Controller;
import frc.lib.drivers.TalonFX.AntTalonFX;
import frc.lib.drivers.TalonFX.TalonFXPositionControl;
import frc.robot.Subsystems.ExampleIntakeSubsystem;
import frc.lib.debugging.ConnectionManager;

public class RobotContainer {
  SRXMagEncoder encoder1 = new SRXMagEncoder(0, "left encoder");
  SRXMagEncoder encoder2 = new SRXMagEncoder(1, "right encoder");

  ExampleIntakeSubsystem intakeSubsystem = new ExampleIntakeSubsystem();

  AntPS5Controller controller = new AntPS5Controller(0, "Driver Controller");

  AntTalonFX exampleTalonFX = new AntTalonFX(5, "Example Talon FX");
  TalonFXPositionControl positionControl = new TalonFXPositionControl(0.2, 1.0, 0.0, 1.0, 0.1, 0.0, 10.0, 10.0);

  
  public RobotContainer() {
    ConnectionManager.start();
    configureBindings();

    //Demonstration of features of AntTalonFX
    exampleTalonFX.addInformation(AntTalonFX.MotorInformationType.kKinematics); //Add position, velocity, and acceleration information
    exampleTalonFX.addInformation(AntTalonFX.MotorInformationType.kElectricity); //Add voltage and stator current information
    exampleTalonFX.applyConfig(positionControl.getConfig()); //Apply positionControl configs
    exampleTalonFX.setPositionWithConfiguration(100); //Send position request to motor (only needed one time)
  }

  private void configureBindings() {
    controller.R1().whileTrue(new FunctionalCommand(() -> {exampleTalonFX.resetPulse();}, () -> {exampleTalonFX.setPulse(5, 1, 0.2, 0.2);}, (i) -> {exampleTalonFX.setVoltage(0);}, () -> false));
    controller.R2().whileTrue(intakeSubsystem.getIntakeCommand(new double[] {1, 5}));

    intakeSubsystem.getHasGamePieceTrigger().onTrue(
      Commands.waitSeconds(1).andThen(Commands.print("Hand Off"))
    );
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
