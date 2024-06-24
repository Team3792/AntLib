// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.drivers.TalonFX;

/** Add your docs here. */
public class TalonFXControlConfiguration {
    public record VelocityConfiguration(double kS, double kV, double kP, double kI, double kD){}
    public record PositionConfiguration(double kP, double kI, double kD){}
}
