// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.subsystem_architectures.swerve;

/** Add your docs here. */
public class SwerveModuleConfiguration {
    public int turnMotorID, driveMotorID, encoderID;
    public double encoderOffset;

    public SwerveModuleConfiguration(int turnMotorID, int driveMotorID, int encoderID, double encoderOffset){
        this.turnMotorID = turnMotorID;
        this.driveMotorID = driveMotorID;
        this.encoderID = encoderID;
        this.encoderOffset = encoderOffset;
    }

}
