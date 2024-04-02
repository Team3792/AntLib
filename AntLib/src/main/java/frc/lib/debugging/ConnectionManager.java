// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.debugging;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.debugging.Connection;

/** Add your docs here. */
public class ConnectionManager {
    static ArrayList<Connection> connections = new ArrayList<Connection>();
    static Connection allConnectedConnection = new Connection("All Connected", () -> allConnected());

    public static void addConnection(Connection connection){
        connections.add(connection);
        Shuffleboard.getTab("Connections").add(connection.name, connection);
    }

    public static void start(){
        Shuffleboard.getTab("Connections").add("Name", allConnectedConnection);
    }

    public static void addConnection(String name, BooleanSupplier connectionFuction){
        addConnection(new Connection(name, connectionFuction));
    }

    public static boolean allConnected(){
        for(Connection c : connections){
            if(!c.connectionFunction.getAsBoolean()){
                return false;
            }
        }
        return true;
    }
}
