package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UltraSonicSensor {
  private static UltraSonicSensor instance = new UltraSonicSensor();

  UltraSonicSensor() {}

  public static UltraSonicSensor getInstance() {
    return instance;
  }

  public int read() {
    throw new NotImplementedException();
  }
}
