// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.debugging;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/** Add your docs here. */
public class ConnectionManager {
    static ArrayList<BooleanSupplier> connections = new ArrayList<BooleanSupplier>();

    public static void start(){
        Shuffleboard.getTab("Connections").addBoolean("All Connected", () -> allConnected());
    }

    public static void addConnection(String name, BooleanSupplier connectionFuction){
        Shuffleboard.getTab("Connections").addBoolean(name, connectionFuction);
        connections.add(connectionFuction);
    }


    /**
     * 
     * @return whether all connections/checks are true
     */
    public static boolean allConnected(){
        for(BooleanSupplier c : connections){
            if(!c.getAsBoolean()){
                return false;
            }
        }
        return true;
    }
}