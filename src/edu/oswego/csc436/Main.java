package edu.oswego.csc436;

import edu.oswego.csc436.data.*;
import edu.oswego.csc436.states.*;

public class Main {

  private static Main instance = new Main();

  private Data data = Data.getInstanace();
  private State state = new Leader();
  private long lastTime = System.nanoTime();

  public static void main(String[] args) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.out.println("Running shutdown hook.");
        System.out.println("Stopping vehicle.");
        LeftEncoder.getInstance().writeEncoderValue(0);
        RightEncoder.getInstance().writeEncoderValue(0);
      }
    });
    Main.instance.run(args);
  }

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