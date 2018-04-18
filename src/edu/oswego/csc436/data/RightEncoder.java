package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RightEncoder extends Encoder {

  private static RightEncoder instance = new RightEncoder();
  private final int encoderID = 1;

  RightEncoder() {}

  public static RightEncoder getInstance() {
    return instance;
  }

  @Override
  public int getID() {
    return encoderID;
  }

  @Override
  public void writeToEncoder(Motion motion, int value) {
    motion.setRightSpeed(value);
  }
}
