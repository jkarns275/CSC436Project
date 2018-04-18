package edu.oswego.csc436.data;

import java.io.IOException;

public class UltraSonicSensor {
  private static UltraSonicSensor instance;

    static {
        try {
            instance = new UltraSonicSensor();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
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
