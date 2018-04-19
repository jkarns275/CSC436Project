package edu.oswego.csc436.data;

import com.dexterind.gopigo.behaviours.Motion;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class LeftEncoder extends Encoder {

  private static LeftEncoder instance;

  static {
    try {
      instance = new LeftEncoder();
    } catch (InterruptedException e) {
      e.printStackTrace(System.err);
    } catch (IOException e) {
      e.printStackTrace(System.err);
    }
  }

  private final int encoderID = 0;

  LeftEncoder() throws InterruptedException, IOException {}

  public static LeftEncoder getInstance() {
    return instance;
  }

  @Override
  public int getID() {
    return encoderID;
  }

  @Override
  public void writeToEncoder(Motion motion, float value) throws IOException {
    motion.setLeftSpeed((int) value);
  }
}
