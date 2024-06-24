// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.debugging.ControlTuning;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import java.lang.String;

/** Add your docs here. */
public class DashboardTuner implements Sendable{
    private double value = 0;
    private String name;

    public DashboardTuner(String name){
        this.name = name;
    }


    @Override
    public void initSendable(SendableBuilder builder){
        builder.setSmartDashboardType(name);
        builder.addDoubleProperty(name, this::getValue, this::setValue);
    }

    public double getValue(){
        return value;
    }

    public void setValue(double newValue){
        value = newValue;
    }
}
