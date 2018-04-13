package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class LeftEncoder extends Encoder {

  private static LeftEncoder instance = new LeftEncoder();

  LeftEncoder() {}

  public static LeftEncoder getInstance() {
    return instance;
  }

  @Override
  public int getID() {
    throw new NotImplementedException();
  }

}
