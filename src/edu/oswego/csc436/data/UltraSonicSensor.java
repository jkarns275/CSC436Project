package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UltraSonicSensor {
  private static UltraSonicSensor instance = new UltraSonicSensor();
  private final com.dexterind.gopigo.components.UltraSonicSensor uss = new com.dexterind.gopigo.components.UltraSonicSensor();

  UltraSonicSensor() {}

  public static UltraSonicSensor getInstance() {
    return instance;
  }

  public int read() throws BadSensorValueException {
      int distance = uss.getDistance();
      if (distance > 0) {
          return distance;
      } else {
          throw new BadSensorValueException("Bad sensor value");
      }
  }
}
