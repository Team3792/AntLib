// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.signal_processing;

/** Add your docs here. */
public class JoystickSignalProcessor {
    /**
     * 
     * @param input raw input from -1 to 1
     * @param minOutput minimum output corresponding to -1
     * @param maxOutput maximum output corresponding to 1
     * @param deadband deadband applied to RAW INPUT
     * @param curveType type of curve that will be applied
     * @return processed output from input
     */
    public static double calculate(double input, JoystickSignalProcessorConfig config){
        //Apply Deadband
        input = applyDeadband(input, config.deadband);

        //Apply Curve
        switch(config.curveType){
            case kCubic:
                input = applyCubicCurve(input);
                break;
            case kLinear: //Not needed, but used for clarity
                input = applyLinearCurve(input);
                break;
        }

        //Adjust for range
        input = (input + 1)/2.0 * (config.maxOutput - config.minOutput) + config.minOutput;

        return input;
    }

    

    private static double applyDeadband(double rawInput, double deadband){
        if(Math.abs(rawInput) <= deadband){
            return 0;
        }
        return rawInput;
    }

    //Curves (c(-1) = -1 and c(1) = 1)

    //Most basic, "direct mapping"
    private static double applyLinearCurve(double rawInput){
        return rawInput;
    }

    //Gives greater control at lower inputs but "ramps up fast" to maintain a high range (possibly good for driving)
    private static double applyCubicCurve(double rawInput){
        return Math.pow(rawInput, 7);
    }
}
