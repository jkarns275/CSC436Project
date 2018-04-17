package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Data {
  public static final int SPEED_LIMIT = 196;

  private final static Data instance = new Data();

  public final UltraSonicSensor uss = UltraSonicSensor.getInstance();
  public final LeftEncoder leftEncoder = LeftEncoder.getInstance();
  public final RightEncoder rightEncoder = RightEncoder.getInstance();

  private int ussValue = 0, lastUssValue = 0;
  private int leftEncoderValue = 0, lastLeftEncoderValue = 0;
  private int rightEncoderValue = 0, lastRightEncoderValue = 0;

  private float rightSteeringCorrectionAverage = 0.0f;
  private float leftSteeringCorrectionAverage = 0.0f;

  Data() { }

  public static Data getInstanace() { return Data.instance; }

  public Data update() {
    // Calculate new values
    return this;
  }

  public float getLeftSCMD() {
    return 0;
  }

  public float getRightSCMD() {
    return 0;
  }

  public float getLeftRotationalSpeed() {
    return 0;
  }

  public float getRightRotationalSpeed() {
    return 0;
  }

  public float rightSteeringCorrection() {
    float calculatedValue = throw new NotImplementedException();
    return updateRightSteeringCorrectionAverage(calculatedValue);
  }

  /**
   * Update the rolling exponential average
   * @param newValue
   * @return the new rolling exponential average
   */
  private float updateRightSteeringCorrectionAverage(float newValue) {
    throw new NotImplementedException();
  }

  public float getLeftSteeringCorrection() {
    float calculatedValue = throw new NotImplementedException();
    return updateLeftSteeringCorrectionAverage(calculatedValue);
  }

  /**
   * Update the rolling exponential average
   * @param newValue
   * @return the new rolling exponential average
   */
  private float updateLeftSteeringCorrectionAverage(float newValue) {
    throw new NotImplementedException();
  }
}
