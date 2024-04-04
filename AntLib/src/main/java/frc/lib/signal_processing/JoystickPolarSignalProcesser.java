/*
This class is mainly intended for processing joystick inputs in polar coordinates where curves can be applied uniformely
This is intended to get a wider range of control for driving swerve
*/

package frc.lib.signal_processing;

import edu.wpi.first.math.geometry.Translation2d;

/** Add your docs here. */
public class JoystickPolarSignalProcesser {
    public static Translation2d calculate(Translation2d input, JoystickSignalProcessorConfig radialConfig){
        double radius = input.getNorm();
        if(radius == 0)
            return input; //Do nothing for "null" translation to avoid division by 0
        return input.times(JoystickSignalProcessor.calculate(radius, radialConfig)/radius); //Keeping arg the same, replaces radius
    }


    public static Translation2d calculate(double x, double y, JoystickSignalProcessorConfig radialConfig){
        return calculate(new Translation2d(x, y), radialConfig);
    }
}
