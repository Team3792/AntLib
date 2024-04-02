// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.debugging;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/** Add your docs here. */
public class Connection implements Sendable{
    String name;
    BooleanSupplier connectionFunction;

    public Connection(String name, BooleanSupplier connectionFunction){
        this.name = name;
        this.connectionFunction = connectionFunction;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Connection");
        builder.addBooleanProperty(name, connectionFunction, (b) -> {System.out.println("Cannot set value");});
    }
}
