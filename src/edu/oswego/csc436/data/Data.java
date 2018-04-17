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

  private float rightRotationalSpeed = 0;
  private float leftRotationalSpeed = 0;
  private float relativeSpeed = 0.0f;
  private float rightSteeringCorrectionAverage = 0.0f;
  private float leftSteeringCorrectionAverage = 0.0f;
  private float rightSteeringCorrection = 0.0f;
  private float leftSteeringCorrection = 0.0f;
  private float leftSCMD = 0;
  private float rightSCMD = 0;

  Data() { }

  public static Data getInstanace() { return Data.instance; }

  public Data update() {
    // Calculate new values
    this.leftSCMD = this.updateLeftSCMD();
    this.rightSCMD = this.updateRightSCMD();
    this.rightRotationalSpeed = this.updateRightRotationalSpeed();
    this.leftRotationalSpeed = this.updateLeftRotationalSpeed();
    this.relativeSpeed = this.updateRelativeSpeed();
    this.rightSteeringCorrectionAverage = this.updateRightSteeringCorrection();
    this.rightSteeringCorrection  = this.updateRightSteeringCorrection();
    this.leftSteeringCorrection = this.updateLeftSteeringCorrection();
    return this;
  }

  private float updateRelativeSpeed() {
    return 0;
  }

  private float updateLeftSCMD() {
    return 0;
  }

  private float updateRightSCMD() {
    return 0;
  }

  private float updateLeftRotationalSpeed() {
    return 0;
  }

  private float updateRightRotationalSpeed() {
    return 0;
  }

  private float updateRightSteeringCorrection() {
    float calculatedValue = throw new NotImplementedException();
    return updateRightSteeringCorrectionAverage(calculatedValue);
  }

  private float updateRightSteeringCorrectionAverage(float newValue) {
    throw new NotImplementedException();
  }

  private float updateLeftSteeringCorrection() {
    float calculatedValue = throw new NotImplementedException();
    return updateLeftSteeringCorrectionAverage(calculatedValue);
  }

  private float updateLeftSteeringCorrectionAverage(float newValue) {
    throw new NotImplementedException();
  }

  private float updateRightSteeringCorrectionAverage() {
    return rightSteeringCorrectionAverage;
  }

  private float updateLeftSteeringCorrectionAverage() {
    return leftSteeringCorrectionAverage;
  }

  public int getRightEncoderValue() {
    return rightEncoderValue;
  }

  public int getLeftEncoderValue() {
    return leftEncoderValue;
  }

  public int getUssValue() {
    return ussValue;
  }

  public float getRightSteeringCorrectionAverage() {
    return rightSteeringCorrectionAverage;
  }

  public float getLeftSteeringCorrectionAverage() {
    return leftSteeringCorrectionAverage;
  }

  public float getRelativeSpeed() {
    return relativeSpeed;
  }

  public float getLeftSCMD() {
    return leftSCMD;
  }

  public float getRightSCMD() {
    return rightSCMD;
  }

  public float getLeftRotationalSpeed() {
    return leftRotationalSpeed;
  }

  public float getRightRotationalSpeed() {
    return rightRotationalSpeed;
  }

  public float getRightSteeringCorrection() {
    return rightSteeringCorrection;
  }

  public float getLeftSteeringCorrection() {
    return leftSteeringCorrection;
  }
}
