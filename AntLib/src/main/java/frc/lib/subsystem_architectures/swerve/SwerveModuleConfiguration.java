// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.subsystem_architectures.swerve;

import frc.lib.drivers.TalonFX.TalonFXVelocityControl;
import frc.lib.drivers.TalonFX.TalonFXPositionControl;

/** Add your docs here. */
public class SwerveModuleConfiguration {
    public TalonFXPositionControl turnPositionControl;
    public TalonFXVelocityControl driveVelocityControl;
    public double turnRatio, driveRatio; //Gear ratios from motor to wheel, should be above 1
    public double wheelCircumferenceMeters;
    public int turnMotorID, driveMotorID, encoderID;

    public SwerveModuleConfiguration(int turnMotorID, int driveMotorID, int encoderID, 
        TalonFXPositionControl turnPositionControl, TalonFXVelocityControl driveVelocityControl,
        double turnRatio, double driveRatio, double wheelCircumferenceMeters){
            this.turnPositionControl = turnPositionControl;
            this.driveVelocityControl = driveVelocityControl;
            this.turnRatio = turnRatio;
            this.driveRatio = driveRatio;
            this.wheelCircumferenceMeters = wheelCircumferenceMeters;
            this.turnMotorID = turnMotorID;
            this.driveMotorID = driveMotorID;
            this.encoderID = encoderID;
    }
}
