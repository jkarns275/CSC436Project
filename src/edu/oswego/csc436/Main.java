package edu.oswego.csc436;

import edu.oswego.csc436.data.*;
import edu.oswego.csc436.states.*;

public class Main {

  private static Main instance = new Main();

  private Data data = Data.getInstanace();
  private State state = new Leader();
  private long lastTime = System.nanoTime();

  public static void main(String[] args) { Main.instance.run(args); }

  private float getDT() {
    long time = System.nanoTime();
    float dt = (float) (time - lastTime) / 1000000000f;
    lastTime = time;
    return dt;
  }

  public void run(String[] args) {
    while ((state = state.update(getDT(), data)) != null);
  }
}
