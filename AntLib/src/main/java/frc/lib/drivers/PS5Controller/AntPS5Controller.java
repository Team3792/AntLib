// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.drivers.PS5Controller;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.lib.debugging.ConnectionManager;

/**See png in folder for image of button mapping*/
public class AntPS5Controller extends CommandPS5Controller{
    public AntPS5Controller(int port, String name){
        super(port);
        ConnectionManager.addConnection(name, this::getConnected); //Seems to only work in simulation
    }

    /**
     * @param level level from 0 to 1 of the strength of the rumble
     */
    public void setRumble(double level){
        getHID().setRumble(RumbleType.kBothRumble, level);
    }

    
    public void rumbleForTime(int level, double timeSeconds){
        setRumble(level);
        new Thread( () -> {
            try{
            Thread.sleep(Double.valueOf(timeSeconds * 1000).longValue());
            setRumble(0);
            } catch (Exception e){}
          }).start();
    }


    public boolean getConnected(){
        return getHID().isConnected();
    }
}
