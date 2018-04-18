package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
  public void writeToEncoder(Motion motion, int value) {
    motion.setLeftSpeed(value);
  }
}
