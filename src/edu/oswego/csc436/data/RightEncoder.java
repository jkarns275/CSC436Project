package edu.oswego.csc436.data;

import com.dexterind.gopigo.behaviours.Motion;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class RightEncoder extends Encoder {

  private static RightEncoder instance;

  static {
    try {
      instance = new RightEncoder();
    } catch (InterruptedException e) {
      e.printStackTrace(System.err);
    } catch (IOException e) {
      e.printStackTrace(System.err);
    }
  }

  private final int encoderID = 1;

  RightEncoder() throws InterruptedException, IOException {}

  public static RightEncoder getInstance() {
    return instance;
  }

  @Override
  public int getID() {
    return encoderID;
  }

  @Override
  public void writeToEncoder(Motion motion, float value) throws IOException {
    motion.setRightSpeed((int) value);
  }
}
