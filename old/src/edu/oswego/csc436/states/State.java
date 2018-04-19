package edu.oswego.csc436.states;

import edu.oswego.csc436.data.BadSensorValueException;
import edu.oswego.csc436.data.Data;

import java.io.IOException;

public abstract class State {
  public abstract State update(float dt, Data data) throws IOException;
}
