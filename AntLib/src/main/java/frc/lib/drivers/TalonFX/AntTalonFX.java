/*
 * This class is an extension of TalonFX that includes the connection management for the motor and other convienient features
 */

package frc.lib.drivers.TalonFX;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import edu.wpi.first.wpilibj.Timer;

public class AntTalonFX extends TalonFX{
    private String name;
    ShuffleboardLayout talonFXLayout;
    double pulseStart; //stors the time stamp of the start of pulsing

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


    public void applyConfig(TalonFXConfiguration config){
        getConfigurator().apply(config);
    }


    public void setPositionWithConfiguration(double positionRotation){
        MotionMagicVoltage request = new MotionMagicVoltage(positionRotation).withSlot(0); //0 feedforward 
        setControl(request);
    }


    public void setVelocityWithConfiguration(double velocityRPS){
        MotionMagicVelocityVoltage request = new MotionMagicVelocityVoltage(velocityRPS).withSlot(1); //
        setControl(request);
    }


    /*
     * Reset the pulseState value to the current time so that, if desired, the pulse starts on the a state 
     */
    public void resetPulse(){
        pulseStart = Timer.getFPGATimestamp();
    }


    /**
     * Pulses the motor between a and b voltage at their respective times. Will start a if reset properly
     * Pulsing can be used to achieve sufficient torque at low speeds and was inspired off 1678 and 118's trap pulsing
     * @param aVoltage a state voltage
     * @param bVoltage b state voltage
     * @param aTime time to sustain a voltage in seconds
     * @param bTime time to sustain b voltage in seconds
     */
    public void setPulse(double aVoltage, double bVoltage, double aTime, double bTime){ 
      double dT = Timer.getFPGATimestamp() - pulseStart;
      double voltage = (dT % (aTime + bTime) < aTime) ? aVoltage : bVoltage;
      setVoltage(voltage);
    }


    /**
     * Pulse specificially used for on off (high and 0)
     * @param onVoltage voltage when the motor is considered on
     * @param onTime time, in seconds, the motor is on
     * @param offTime time, in seconds, the motor is off
     */
    public void setPulse(double onVoltage, double onTime, double offTime){
        setPulse(onVoltage, 0, onTime, offTime);
    }
}
