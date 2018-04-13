package edu.oswego.csc436.states;

import edu.oswego.csc436.data.Data;

public abstract class State {
  public abstract State update(float dt, Data data);
}
