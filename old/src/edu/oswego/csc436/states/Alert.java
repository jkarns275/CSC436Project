package edu.oswego.csc436.states;

import edu.oswego.csc436.Constants;
import edu.oswego.csc436.data.BadSensorValueException;
import edu.oswego.csc436.data.Data;

import java.io.IOException;

public class Alert extends State {
  private static Alert instance = new Alert();

  private int consecutiveNoLeadingObjectCyles = 0;
  @Override
  public State update(float dt, Data data) {
    try {
      data.update(dt, 0.0f);

      if (data.getUssValue() > Constants.STOP_DISTANCE) return Follow.getInstance();
      if (!data.objectDetected()) {
        consecutiveNoLeadingObjectCyles += 1;
        if (consecutiveNoLeadingObjectCyles >= 5) {
          return Leader.getInstance();
        }
      } else
        consecutiveNoLeadingObjectCyles = 0;

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
