// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import frc.lib.subsystem_architectures.GenericIntakeSubsystem;

/** Add your docs here. */
public class ExampleIntakeSubsystem extends GenericIntakeSubsystem{

    public ExampleIntakeSubsystem(){
        addTalonFXMotor(0, false, "Top Intake");
        addTalonFXMotor(1, true, "Bottom Intake");
    }

    @Override
    public boolean hasGamePiece(){
        return getTotalAcceleration() < -10.0;
    }
}
