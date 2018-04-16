package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class Encoder {

  public abstract int getID();

  public int readEncoderValue() throws BadSensorValueException {
    int id = getID();

    throw new NotImplementedException();

    //return id;
  }

  public boolean writeEncoderValue(int encoderValue) {
    throw new NotImplementedException();
  }
}
