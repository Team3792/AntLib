// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.drivers.SRXMagEncoder;
import frc.lib.debugging.Connection;
import frc.lib.debugging.ConnectionManager;

public class RobotContainer {
  public RobotContainer() {
    SRXMagEncoder encoder1 = new SRXMagEncoder(0, "left encoder");
    SRXMagEncoder encoder2 = new SRXMagEncoder(1, "right encoder");
    ConnectionManager.start();

    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
