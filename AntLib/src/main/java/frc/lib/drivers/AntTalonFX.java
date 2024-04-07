/*
 * This class is an extension of TalonFX that includes the connection management for the motor and other convienient features
 */

package frc.lib.drivers;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import java.util.ArrayList;

public class AntTalonFX extends TalonFX{
    private String name;
    ShuffleboardLayout talonFXLayout;

    public AntTalonFX(int id, String baseName){
        super(id, "rio");
        name = baseName + " - " + id; //Add canID to name
        talonFXLayout = Shuffleboard.getTab("Motors").getLayout(name, BuiltInLayouts.kList);
    }


    /**
     * Adds requested information type to Shuffleboard
     * @param type type of information to add, kElectricity, kKinematics, or kLimitSwitches
     */
    public void addInformation(MotorInformationType type){
        switch(type){
            case kElectricity:
                talonFXLayout.addDouble("Voltage", () -> getMotorVoltage().getValueAsDouble());
                talonFXLayout.addDouble("Stator Current", () -> getStatorCurrent().getValueAsDouble());
                break;
            case kKinematics:
                talonFXLayout.addDouble("Position", () -> getPosition().getValueAsDouble());
                talonFXLayout.addDouble("Velocity", () -> getVelocity().getValueAsDouble());
                break;
            case kLimitSwitches:
                talonFXLayout.addBoolean("Forward Limit Triggered", () -> getForwardLimit().getValue().value == 0);
                talonFXLayout.addBoolean("Reverse Limit Triggered", () -> getReverseLimit().getValue().value == 0);
                break;
        }
    }


    /**
     * Add multiple motor information types to Shuffleboard
     * @param types array of all the motor types to add
     */
    public void addInformation(ArrayList<MotorInformationType> types){
        for(MotorInformationType t : types){
            addInformation(t);
        }
    }


    public enum MotorInformationType{
        kElectricity, //Voltage and stator current
        kKinematics, //Position, velocity, and acceleration
        kLimitSwitches //Forward and reverse limit switches
    }
}
