package edu.oswego.csc436.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Data {
  public static final int SPEED_LIMIT = 196;
  public static final float TICKS_TO_CM = 1f/18f;
  public static final int FOLLOW_DISTANCE = 500; //cm
  public static final int CRITICAL_DISTANCE = 15; //cm
  public static final int STOP_DISTANCE = 30; //cm

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

  public Data update(float dt, float targetSpeed) throws BadSensorValueException {
    lastUssValue = ussValue;
    ussValue = uss.read();

    lastLeftEncoderValue = leftEncoderValue;
    leftEncoderValue = leftEncoder.readEncoderValue();

    lastRightEncoderValue = rightEncoderValue;
    rightEncoderValue = rightEncoder.readEncoderValue();

    // Calculate new values
    this.leftSCMD = this.updateLeftSCMD();
    this.rightSCMD = this.updateRightSCMD();
    this.rightRotationalSpeed = this.updateRightRotationalSpeed(dt);
    this.leftRotationalSpeed = this.updateLeftRotationalSpeed(dt);
    this.relativeSpeed = this.updateRelativeSpeed(dt);
    this.rightSteeringCorrection  = this.updateRightSteeringCorrection(targetSpeed);
    this.leftSteeringCorrection = this.updateLeftSteeringCorrection(targetSpeed);
    return this;
  }

  private float updateRelativeSpeed(float dt) {
    float difference = ussValue - lastUssValue;
    return difference / dt;
  }

  private float updateLeftSCMD() {
    return (leftEncoderValue - lastLeftEncoderValue) * TICKS_TO_CM;
  }

  private float updateRightSCMD() {
    return (rightEncoderValue - lastRightEncoderValue) * TICKS_TO_CM;
  }

  private float updateLeftRotationalSpeed(float dt) {
    return this.getLeftSCMD() / dt;
  }

  private float updateRightRotationalSpeed(float dt) {
    return this.getRightSCMD() / dt;
  }

  private float updateRightSteeringCorrection(float targetSpeed) {
    float calculatedValue = 1.0f + (targetSpeed - rightRotationalSpeed) / targetSpeed;
    return updateRightSteeringCorrectionAverage(calculatedValue);
  }

  private float updateRightSteeringCorrectionAverage(float newValue) {
    return (1f/16f) * newValue + (15f/16f) * rightSteeringCorrectionAverage;
  }

  private float updateLeftSteeringCorrection(float  targetSpeed) {
    float calculatedValue = 1.0f + (targetSpeed - leftRotationalSpeed) / targetSpeed;
    return updateLeftSteeringCorrectionAverage(calculatedValue);
  }

  private float updateLeftSteeringCorrectionAverage(float newValue) {
    return (1f/16f) * newValue + (15f/16f) * leftSteeringCorrectionAverage;
  }

  private float updateRightSteeringCorrectionAverage() {
    return rightSteeringCorrectionAverage;
  }

  private float updateLeftSteeringCorrectionAverage() {
    return leftSteeringCorrectionAverage;
  }

  public void writeVariables() {
      leftEncoder.writeEncoderValue(leftSteeringCorrection);
      rightEncoder.writeEncoderValue(rightSteeringCorrection);
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
