// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.drivers.TalonFX;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

/** Add your docs here. */
public class TalonFXVelocityControl {
    double kS, kV, kA; //Feedforward terms
    double kP, kI, kD; //PID terms
    double kMaxA, kMaxJ; //Profile constraints (could add jerk)

    /**
     * @param kS voltage to overcome static friction
     * @param kV voltage per rps
     * @param kA voltage per rps/s
     * @param kP voltage per error in rps
     * @param kI voltage per unit of integrated error in rotations
     * @param kD voltage per unit of derivative of error in rps/s
     * @param kMaxA max acceleration of motor in rps/s
     * @param kMaxJ max jerk of motor in rps/s/s
     */
    public TalonFXVelocityControl(double kS, double kV, double kA, double kP, double kI, double kD, double kMaxA, double kMaxJ){
        this.kS = kS;
        this.kV = kV;
        this.kA = kA;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kMaxA = kMaxA;
        this.kMaxJ = kMaxJ;
    }


    /**
     * @return configuration with feedforward, pid, and motion magic parameters
     */
    public TalonFXConfiguration getConfig(){
        TalonFXConfiguration newConfigs = new TalonFXConfiguration();

        Slot1Configs slotConfigs = newConfigs.Slot1;
        slotConfigs.kS = kS;
        slotConfigs.kV = kV;
        slotConfigs.kA = kA;
        slotConfigs.kP = kP;
        slotConfigs.kI = kI;
        slotConfigs.kD = kD;
        
        MotionMagicConfigs motionConfigs = newConfigs.MotionMagic;
        motionConfigs.MotionMagicAcceleration = kMaxA;
        motionConfigs.MotionMagicJerk = kMaxJ;

        return newConfigs;
    }



}
