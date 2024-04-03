// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.signal_processing;

import com.fasterxml.jackson.databind.cfg.ConstructorDetector.SingleArgConstructor;

/** Add your docs here. */
public class JoystickSignalProcessorConfig {
    public double minOutput, maxOutput, deadband;
    CurveType curveType;
    
    public JoystickSignalProcessorConfig(double minOutput, double maxOutput, double deadband, CurveType curveType){
        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
        this.deadband = deadband;
        this.curveType = curveType;
    }

    public JoystickSignalProcessorConfig(double minOutput, double maxOutput, double deadband){
        this(minOutput, maxOutput, deadband, CurveType.kLinear);
    }
    
    public enum CurveType{
        kLinear,
        kCubic
    }
}
