// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.subsystem_architectures.swerve;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;

/** Calculates new ChassisSpeed with limited acceleration */
public class SwerveAccelerationLimiter {
    double xAccelerationLimit, yAccelerationLimit, tAccelerationLimit, skidAccelerationLimit;
    double previousTimeStamp = -1; //Initialize with -1 to mark no previous velocity
    double prevXVel, prevYVel, prevTVel;

    /**
     * @param xAccelerationLimit Forward acceleration limit (m/s^2)
     * @param yAccelerationLimit Side-to-side acceleration limit (m/s^2)
     * @param tAccelerationLimit Radial acceleration limit (rad/s^2)
     * @param skidAccelerationLimit Full x and y acceleration limit (m/s^2)
     */
    public SwerveAccelerationLimiter(double xAccelerationLimit, double yAccelerationLimit, double tAccelerationLimit, double skidAccelerationLimit){
        this.xAccelerationLimit = xAccelerationLimit;
        this.yAccelerationLimit = yAccelerationLimit;
        this.tAccelerationLimit = tAccelerationLimit;
        this.skidAccelerationLimit = skidAccelerationLimit;
    }

    ChassisSpeeds calculate(ChassisSpeeds speeds){
        double currentTime = Timer.getFPGATimestamp();
        double deltaT = currentTime - previousTimeStamp;

        //Limit accelerations if already recorded a velocity
        if(previousTimeStamp > -0.5){
            double dVX = speeds.vxMetersPerSecond - prevXVel;
            double dVY = speeds.vyMetersPerSecond - prevYVel;

            //Skid limit
            double deltaVMagnitude = Math.hypot(dVX, dVY);
            if(deltaVMagnitude > skidAccelerationLimit * deltaT){
                //Normalize vector (divide my maginitude) and multiply by skidAccelerationLimit to point in correct location
                dVX *= skidAccelerationLimit/deltaVMagnitude * deltaT;
                dVY *= skidAccelerationLimit/deltaVMagnitude * deltaT;

                //Update speeds from adjusted velocities 
                speeds.vxMetersPerSecond = prevXVel + dVX;
                speeds.vyMetersPerSecond = prevYVel + dVY;
            }

            //x limit
            speeds.vxMetersPerSecond = limitAxis(prevXVel, speeds.vxMetersPerSecond, deltaT, xAccelerationLimit);
            
            //y limit
            speeds.vyMetersPerSecond = limitAxis(prevYVel, speeds.vyMetersPerSecond, deltaT, yAccelerationLimit);

            //t limit
            speeds.omegaRadiansPerSecond = limitAxis(prevTVel, speeds.omegaRadiansPerSecond, deltaT, tAccelerationLimit);
        }

        //Update values
        previousTimeStamp = currentTime;
        prevXVel = speeds.vxMetersPerSecond;
        prevYVel = speeds.vyMetersPerSecond;
        prevTVel = speeds.omegaRadiansPerSecond;
        return speeds;
    }


    //Limit acceleration in a single axis of motion
    //deltaT and limit must be positive
    private double limitAxis(double previousVelocity, double currentVelocity, double deltaT, double limit){
        //Limit in forward (positive) direction
        if(currentVelocity - previousVelocity > limit * deltaT){
            return previousVelocity + limit*deltaT;
        }

        //Limit in backward (negative) direction
        if(currentVelocity - previousVelocity < -1*limit * deltaT){
            return previousVelocity - limit*deltaT;
        }

        return currentVelocity; //If not limited, return requested
    }
}