package edu.oswego.csc436.states;

import static edu.oswego.csc436.Constants.*;
import edu.oswego.csc436.data.BadSensorValueException;
import edu.oswego.csc436.data.Data;

import java.io.IOException;

public class Leader extends State {
  private static Leader instance = new Leader();

  @Override
  public State update(float dt, Data data) {
    try {
      data = data.update(dt, Math.min(SPEED_LIMIT, data.getTargetSpeed() + dt * ACCELERATION_RATE));
      data.writeSpeeds();

      if (data.objectDetected()) {
        if (data.getUssValue() < CRITICAL_DISTANCE) {
          return Emergency.getInstance();
        } else if (data.getUssValue() < STOP_DISTANCE) {
          return Stop.getInstance();
        } else if (data.getUssValue() < FOLLOW_DISTANCE) {
          return Follow.getInstance();
        }
      }
      return this;
    } catch (BadSensorValueException | IOException exception) {
      exception.printStackTrace(System.err);
      return this;
    }
  }

  public static State getInstance() {
    return instance;
  }
}
