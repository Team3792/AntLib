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
import frc.robot.Subsystems.ExampleIntakeSubsystem;
import frc.lib.debugging.ConnectionManager;
import frc.lib.debugging.ControlTuning.DashboardProfiledPIDTuner;
import frc.lib.debugging.ControlTuning.DashboardTuner;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import java.lang.String;

public class RobotContainer {
  SRXMagEncoder encoder1 = new SRXMagEncoder(0, "left encoder");
  SRXMagEncoder encoder2 = new SRXMagEncoder(1, "right encoder");

  ExampleIntakeSubsystem intakeSubsystem = new ExampleIntakeSubsystem();

  AntPS5Controller controller = new AntPS5Controller(0, "Driver Controller");

  AntTalonFX rightShooter = new AntTalonFX(21, "Right Shooter");
  AntTalonFX leftShooter = new AntTalonFX(20, "Left Shooter");


  //DashboardProfiledPIDTuner examplePIDTuner = new DashboardProfiledPIDTuner("Example Tuner");
  
  public RobotContainer() {
    ConnectionManager.start();
    configureBindings();

    //Demonstration of features of AntTalonFX
    // exampleTalonFX.addInformation(AntTalonFX.MotorInformationType.kKinematics); //Add position, velocity, and acceleration information
    // exampleTalonFX.addInformation(AntTalonFX.MotorInformationType.kElectricity); //Add voltage and stator current information
  }

  private void configureBindings() {
    controller.R1().whileTrue(new FunctionalCommand(() -> 
    {rightShooter.resetPulse(); rightShooter.setNeutralMode(NeutralModeValue.Brake); leftShooter.resetPulse(); leftShooter.setNeutralMode(NeutralModeValue.Brake);}, 
    () -> {rightShooter.setPulse(0.7, 0.4, 0.05, 0.1); leftShooter.setPulse(0.4, 0.7, 0.1, 0.05);}, (i) -> {rightShooter.setVoltage(0); leftShooter.setVoltage(0);}, () -> false));
    //controller.R2().whileTrue(intakeSubsystem.getIntakeCommand(new double[] {1, 5}));

    //Wait one second after game piece has been intook before handing off to other mechanism
    intakeSubsystem.getHasGamePieceTrigger().onTrue(
      Commands.waitSeconds(1).andThen(Commands.print("Hand Off"))
    );
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
