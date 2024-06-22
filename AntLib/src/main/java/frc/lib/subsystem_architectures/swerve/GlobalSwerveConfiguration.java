// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.subsystem_architectures.swerve;

import frc.lib.drivers.TalonFX.TalonFXVelocityControl;
import frc.lib.debugging.ControlTuning.ProfiledPIDConfig;
import frc.lib.drivers.TalonFX.TalonFXPositionControl;

/** Add your docs here. */
public class GlobalSwerveConfiguration {
    public TalonFXPositionControl turnPositionControl;
    public TalonFXVelocityControl driveVelocityControl;
    public double turnRatio, driveRatio; //Gear ratios from motor to wheel, should be above 1
    public double wheelCircumferenceMeters;
    public double frontBackTrackWidthMeters, leftRightTrackWidthMeters;
    public int pigeonID;
    public double maxModuleSpeed;
    public ProfiledPIDConfig headingControllerConfig;

    public GlobalSwerveConfiguration(TalonFXPositionControl turnPositionControl, TalonFXVelocityControl driveVelocityControl,
        double turnRatio, double driveRatio, double wheelCircumferenceMeters,
        double frontBackTrackWidthMeters, double leftRightTrackWidthMeters, int pigeonID, double maxModuleSpeed, ProfiledPIDConfig headingControllerConfig){
            this.turnPositionControl = turnPositionControl;
            this.driveVelocityControl = driveVelocityControl;
            this.turnRatio = turnRatio;
            this.driveRatio = driveRatio;
            this.wheelCircumferenceMeters = wheelCircumferenceMeters;
            this.frontBackTrackWidthMeters = frontBackTrackWidthMeters;
            this.leftRightTrackWidthMeters = leftRightTrackWidthMeters;
            this.pigeonID = pigeonID;
            this.maxModuleSpeed = maxModuleSpeed;
            this.headingControllerConfig = headingControllerConfig;
    }
}
