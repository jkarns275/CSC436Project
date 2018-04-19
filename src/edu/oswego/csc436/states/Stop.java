package edu.oswego.csc436.states;

import edu.oswego.csc436.Constants;
import edu.oswego.csc436.data.BadSensorValueException;
import edu.oswego.csc436.data.Data;

import java.io.IOException;

public class Stop extends State {

  private static Stop instance = new Stop();

  @Override
  public State update(float dt, Data data) throws IOException {
    try {
      if (data.getTargetSpeed() <= Float.MIN_NORMAL) return Alert.getInstance();
      data = data.update(dt, Math.max(0.0f, data.getTargetSpeed() - (dt * Constants.STOP_DECEL_RATE)));
      data.writeSpeeds();

      if (data.getUssValue() > Constants.STOP_DISTANCE)
        return Follow.getInstance();
      if (data.getUssValue() < Constants.CRITICAL_DISTANCE)
        return Emergency.getInstance();
      return this;

    } catch (BadSensorValueException e) {
      e.printStackTrace(System.err);
      return this;
    }
  }

  public static State getInstance() { return instance; }
}
