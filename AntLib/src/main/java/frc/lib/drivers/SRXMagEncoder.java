package frc.lib.drivers;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.lib.debugging.ConnectionManager;

/**
 * Extended features for DutyCycleEncoder focused on the SRXMagEncoder. Before setting offsets and reversed, set those to 0 and false respectively, and the mechanism ratio to 1
*/
public class SRXMagEncoder{
    private DutyCycleEncoder encoder;

    /**
     * 
     * @param dio port on the rio that the encoder is plugged into
     * @param name device will be displayed as for debugging
     * @param multiplier from one encoder rotation to mechanism
     * @param reversed (true if positive movement on mechanism makes the encoder rotate cw)
     * @param offset of encoder (calculated/converted reading from encoder when mechanism is at its zero point)
     */
    public SRXMagEncoder(int rioDioPort, String name, double mechanismToEncoderRatio, boolean reversed, double offset){
        encoder = new DutyCycleEncoder(rioDioPort);
        encoder.setDistancePerRotation(mechanismToEncoderRatio * (reversed? -1 : 1));
        encoder.setPositionOffset(offset);
        ConnectionManager.addConnection(name, () -> isConnected());
    }

    public SRXMagEncoder(int rioDioPort, String name){
        this(rioDioPort, name, 1, false, 0);
    }

    public double getMeasurement(){
        return encoder.getAbsolutePosition();
    }

    /**
     * @return whether the encoder is connected
     */
    public boolean isConnected(){
        return encoder.isConnected();
    }
}
