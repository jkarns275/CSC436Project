package edu.oswego.csc436.data;

import com.dexterind.gopigo.behaviours.Motion;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class LeftEncoder extends Encoder {

  private static LeftEncoder instance = new LeftEncoder();
  private final int encoderID = 0;

  LeftEncoder() {}

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
