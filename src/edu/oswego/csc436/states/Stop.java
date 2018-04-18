package edu.oswego.csc436.states;

import edu.oswego.csc436.data.BadSensorValueException;
import edu.oswego.csc436.data.Data;

import java.io.IOException;

public class Stop extends State {
  @Override
  public State update(float dt, Data data) throws IOException, BadSensorValueException {
    data = data.update(dt, 1.0f);



  }
}
