package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RightEncoder extends Encoder {

  private static RightEncoder instance = new RightEncoder();

  RightEncoder() {}

  public static RightEncoder getInstance() {
    return instance;
  }

  @Override
  public int getID() { return 1; }
}
