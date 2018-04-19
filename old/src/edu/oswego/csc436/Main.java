package edu.oswego.csc436;

import edu.oswego.csc436.data.*;
import edu.oswego.csc436.states.*;

import java.io.IOException;
import java.util.function.Function;

public class Main {

  private static Main instance;

  static {
    try {
      instance = new Main();
    } catch (IOException | InterruptedException | BadSensorValueException e) {
      e.printStackTrace(System.err);
      shutdown();
    }
  }

  private Data data = Data.getInstanace();
  private State state = new Leader();
  private long lastTime = System.nanoTime();

  private static void shutdown() {
    System.out.println("Running shutdown hook.");
    System.out.println("Stopping vehicle.");
    try {
      LeftEncoder.getInstance().writeEncoderValue(0);
      RightEncoder.getInstance().writeEncoderValue(0);
    } catch (IOException e) {
      e.printStackTrace(System.err);
    }
  }

  private Main() throws IOException, BadSensorValueException, InterruptedException {}

  public static void main(String[] args) throws IOException {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() { shutdown(); }
    });
    try {
      Main.instance.run(args);
    } catch (IOException err) {
      err.printStackTrace(System.err);
      shutdown();
    }
  }

  private float getDT() {
    long time = System.nanoTime();
    float dt = (float) (time - lastTime) / 1000000000f;
    lastTime = time;
    return dt;
  }

  private void run(String[] args) throws IOException { while ((state = state.update(getDT(), data)) != null); }
}
