/*
 * This is meant to provide some testing for the controls feeding into swerve, bypassing motor interaction
 */

package frc.lib.signal_processing;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.Timer;
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
        Shuffleboard.getTab("Match").add(field);
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
            pose.getTranslation().plus(transform.times(dt).getTranslation()),
            pose.getRotation().plus(transform.times(dt).getRotation()));
        field.setRobotPose(pose);  
    }
}
