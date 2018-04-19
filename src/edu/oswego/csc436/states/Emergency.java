package edu.oswego.csc436.states;

import edu.oswego.csc436.Constants;
import edu.oswego.csc436.data.BadSensorValueException;
import edu.oswego.csc436.data.Data;

import java.io.IOException;

public class Emergency extends State {

  private static Emergency instance = new Emergency();

  @Override
  public State update(float dt, Data data) {
    try {
      if (data.getTargetSpeed() <= Float.MIN_NORMAL) return Alert.getInstance();
      data = data.update(dt, Math.max(0.0f, data.getTargetSpeed() - (dt * Constants.EMERGENCY_DECEL_RATE)));
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
