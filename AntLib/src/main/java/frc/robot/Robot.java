// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.drivers.PS5Controller.AntPS5Controller;
import frc.lib.signal_processing.JoystickPolarSignalProcesser;
import frc.lib.signal_processing.JoystickSignalProcessor;
import frc.lib.signal_processing.JoystickSignalProcessorConfig;
import frc.lib.signal_processing.JoystickSignalProcessorConfig.CurveType;
import frc.lib.signal_processing.SwerveMovementSimulator;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  //Testing signal processing
  private SwerveMovementSimulator swerveMovementSimulator = new SwerveMovementSimulator();
  private AntPS5Controller controller = new AntPS5Controller(5, "Driver Joystick");
  private JoystickSignalProcessorConfig radialSignalProcessor = new JoystickSignalProcessorConfig(-2.5, 2.5, 0, CurveType.kCubic);
  private JoystickSignalProcessorConfig thetaSignalProcessor = new JoystickSignalProcessorConfig(-3, 3, 0, CurveType.kLinear);

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    //controller.setRumble(1);
  }

  @Override
  public void teleopPeriodic() {
    
     swerveMovementSimulator.drive(
       new Transform2d(
         JoystickPolarSignalProcesser.calculate(-controller.getLeftY(), -controller.getLeftX(), radialSignalProcessor), 
         new Rotation2d(JoystickSignalProcessor.calculate(-controller.getRightX(), thetaSignalProcessor)))
     );
  }

  @Override
  public void teleopExit() {
    //controller.setRumble(0);
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
