/*
 * This is meant to provide some testing for the controls feeding into swerve, bypassing motor interaction
 */

package frc.lib.signal_processing;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;

import java.util.Calendar;

/** Add your docs here. */
public class SwerveMovementSimulator {
    Pose2d pose = new Pose2d(); //Defualt to origin
    double lastTime = -1;
    Calendar calendar = Calendar.getInstance();
    Field2d field = new Field2d();

    public SwerveMovementSimulator(){
        Shuffleboard.getTab("Match").add(field).withWidget(BuiltInWidgets.kField);
    }

    /**
     * 
     * @param transform in meters and radians per second
     */
    public void drive(Transform2d transform){
        double currentTime = Timer.getFPGATimestamp();
        double dt = 0;
        if(lastTime > 0){
            dt = (currentTime - lastTime);
        }
        lastTime = currentTime;
        pose = new Pose2d(
            new Translation2d(
                clampToField(pose.getX() + transform.getX() * dt, 16.0),
                clampToField(pose.getY() + transform.getY() * dt, 7.5)
            ),
            pose.getRotation().plus(transform.times(dt).getRotation()));
        field.setRobotPose(pose);  
    }


    //Clamps v between 0 and limit
    private double clampToField(double v, double limit){
        if(v < 0){
            return 0;
        }
        if(v > limit){
            return limit;
        }
        return v;
    }
}
