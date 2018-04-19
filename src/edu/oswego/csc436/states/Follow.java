package edu.oswego.csc436.states;

import edu.oswego.csc436.Constants;
import edu.oswego.csc436.data.BadSensorValueException;
import edu.oswego.csc436.data.Data;

import java.io.IOException;

// MG2.1, D1.21
public class Follow extends State {
  private static Follow instance = new Follow();

  // MF2.2.1
  private int consecutiveMissingObjectCycles = 0;

  @Override
  public State update(float dt, Data data) {
    try {
      // MF2.2.1
      if (!data.objectDetected()) consecutiveMissingObjectCycles += 1;
      else                        consecutiveMissingObjectCycles = 0;
      if (consecutiveMissingObjectCycles >= 5) return Leader.getInstance();

      int ussValue = data.getUssValue();

      // MF2.1.5
      if (ussValue < Constants.CRITICAL_DISTANCE)
        return Emergency.getInstance();
      // MF2.1.4
      else if (ussValue < Constants.STOP_DISTANCE)
        return Stop.getInstance();

      float relativeSpeed = data.getRelativeSpeed();
      float targetSpeed = data.getTargetSpeed();

      // TODO: Remove requirement MF2.1.5.1, or uncomment the code. I don't think we should
      // keep the requirement as it will cause the leading object to drift away.

      //if (Math.abs((relativeSpeed - targetSpeed) / targetSpeed) > 0.05) {
      // MF2.1.4
      if (relativeSpeed > 0.0f)
        data.update(dt,
          // MF2.1.2.1, MF1.1.3
          Math.min(data.getTargetSpeed() + (Constants.ACCELERATION_RATE * dt), Constants.SPEED_LIMIT) // MF2.1.2.1
        );
      // MF2.1.5
      else
        data.update(dt,
          data.getTargetSpeed() - (Constants.ACCELERATION_RATE * dt) // MF2.1.2.1
        );
      //}

      // MG1.1.2
      data.writeSpeeds();
      return this;

    } catch (BadSensorValueException | IOException e) {
      e.printStackTrace(System.err);
      return this;
    }
  }

  public static State getInstance() {
    return instance;
  }
}
