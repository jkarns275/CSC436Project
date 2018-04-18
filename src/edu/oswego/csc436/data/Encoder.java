package edu.oswego.csc436.data;

import com.dexterind.gopigo.behaviours.Motion;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public abstract class Encoder {

  protected Encoder() throws IOException, InterruptedException {
  }

  public abstract int getID();
  public abstract void writeToEncoder(Motion motion, float value) throws IOException;
  private com.dexterind.gopigo.components.Encoders encoders = com.dexterind.gopigo.components.Encoders.getInstance();
  private Motion motion = Motion.getInstance();

  public int readEncoderValue() throws BadSensorValueException, IOException {
    int id = getID();
    return encoders.read(id);
  }

  public void writeEncoderValue(float encoderValue) throws IOException {
    writeToEncoder(motion,encoderValue);
  }
}
