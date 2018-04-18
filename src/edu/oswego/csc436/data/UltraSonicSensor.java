package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class UltraSonicSensor {
  private static UltraSonicSensor instance;

    static {
        try {
            instance = new UltraSonicSensor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final com.dexterind.gopigo.components.UltraSonicSensor uss = new com.dexterind.gopigo.components.UltraSonicSensor();

  UltraSonicSensor() throws IOException, InterruptedException {}

  public static UltraSonicSensor getInstance() {
    return instance;
  }

  public int read() throws BadSensorValueException, IOException {
      int distance = uss.getDistance();
      if (distance > 0) {
          return distance;
      } else {
          throw new BadSensorValueException("Bad sensor value");
      }
  }
}
