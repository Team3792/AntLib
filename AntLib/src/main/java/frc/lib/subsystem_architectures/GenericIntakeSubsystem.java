/**
 * Creates a template for an intake subsystem that runs purely off voltage. 
 * It also monitors the velocity for any drops that can be used to detect game piece
 */

package frc.lib.subsystem_architectures;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.ArrayList;
import com.ctre.phoenix6.hardware.TalonFX;

public class GenericIntakeSubsystem extends SubsystemBase {
  /** Creates a new GenericIntakeSubsystem. */
  ArrayList<TalonFX> motors = new ArrayList<TalonFX>();
  public GenericIntakeSubsystem() {}

  public void addTalonFXMotor(int canID, boolean inverted){
    //By standards, positive should be intaking. This becomes important in acceleration testing for game piece detection
    TalonFX newMotor = new TalonFX(canID);
    newMotor.setInverted(inverted);
    motors.add(newMotor);
  }

  /**
   * @param universalVoltage voltage that will be applied to all motors (no need to make negative for reverse if set inverted)
   */
  public void applyVoltageToIntake(double universalVoltage){
    for(TalonFX m : motors){
      m.setVoltage(universalVoltage);
    }
  }

  /**
   * Applies different voltages to different motors
   * @param voltages array of voltages with the same length as motors corresponding to 
   */
  public void applyVoltageToIntake(double[] voltages){
    for(int i = 0; i < motors.size(); i++){
      motors.get(i).setVoltage(voltages[i]);
    }
  }

  /**
   * 
   * @return total speed of all the wheels to detect game piece 
   */
  public double getTotalSpeed(){
    double totalSpeed = 0;
    for(TalonFX m: motors){
      totalSpeed += Math.abs(m.getVelocity().getValueAsDouble());
    }
    return totalSpeed;
  }

  public double getTotalAcceleration(){
    double totalAcceleration = 0;
    for(TalonFX m: motors){
      totalAcceleration += m.getAcceleration().getValueAsDouble();
    }
    //This will be negative when game piece is in
    return totalAcceleration;
  }



  /**
   * @return total stator current of all wheels
   */
  public double getTotalStatorCurrent(){
    double totalCurrent = 0;
    for(TalonFX m: motors){
      totalCurrent += Math.abs(m.getStatorCurrent().getValueAsDouble());
    }
    return totalCurrent;
  }
  

  @Override
  public void periodic() {
  }
}
