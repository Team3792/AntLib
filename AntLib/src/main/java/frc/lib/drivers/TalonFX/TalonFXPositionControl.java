// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.drivers.TalonFX;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

/** Add your docs here. */
public class TalonFXPositionControl {
    double kS, kV, kA; //Feedforward terms
    double kP, kI, kD; //PID terms
    double kMaxV, kMaxA; //Profile constraints (could add jerk)

    /**
     * @param kS voltage to overcome static friction
     * @param kV voltage per rps
     * @param kA voltage per rps/s
     * @param kP voltage per error in rotations
     * @param kI voltage per unit of integrated error in rotation*s
     * @param kD voltage per unit of derivative of error in rps
     * @param kMaxV max velocity of motor in rps
     * @param kMaxA max acceleration of motor in rps/s
     */
    public TalonFXPositionControl(double kS, double kV, double kA, double kP, double kI, double kD, double kMaxV, double kMaxA){
        this.kS = kS;
        this.kV = kV;
        this.kA = kA;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kMaxV = kMaxV;
        this.kMaxA = kMaxA;
    }


    /**
     * @return configuration with feedforward, pid, and motion magic parameters
     */
    public TalonFXConfiguration getConfig(){
        TalonFXConfiguration newConfigs = new TalonFXConfiguration();

        Slot0Configs slotConfigs = newConfigs.Slot0;
        slotConfigs.kS = kS;
        slotConfigs.kV = kV;
        slotConfigs.kA = kA;
        slotConfigs.kP = kP;
        slotConfigs.kI = kI;
        slotConfigs.kD = kD;
        
        MotionMagicConfigs motionConfigs = newConfigs.MotionMagic;
        motionConfigs.MotionMagicCruiseVelocity = kMaxV;
        motionConfigs.MotionMagicAcceleration = kMaxA;

        return newConfigs;
    }



}
