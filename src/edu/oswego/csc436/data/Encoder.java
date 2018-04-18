package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class Encoder {

  public abstract int getID();
  public abstract void writeToEncoder();
  private Encoders encoders = Encoders.getInstance();
  private Motion motion = Motion.getInstance();

  public int readEncoderValue() throws BadSensorValueException {
    int id = getID();
    return ecoders.read(id);
  }

  public boolean writeEncoderValue(int encoderValue) {
    writeToEncoder(motion,encoderValue);
  }
}
