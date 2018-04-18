package edu.oswego.csc436.data;

public class BadSensorValueException extends Exception {

  private String message;

  public BadSensorValueException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() { return message; }
}
