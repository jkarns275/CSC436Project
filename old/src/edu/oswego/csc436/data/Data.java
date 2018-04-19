package edu.oswego.csc436.data;

import com.dexterind.gopigo.*;
import com.dexterind.gopigo.behaviours.Motion;

import java.io.IOException;

import static edu.oswego.csc436.Constants.*;

public class Data {


  private static Data instance;

  public final UltraSonicSensor uss = UltraSonicSensor.getInstance();
  public final LeftEncoder leftEncoder = LeftEncoder.getInstance();
  public final RightEncoder rightEncoder = RightEncoder.getInstance();

  private int ussValue = 0, lastUssValue = 0;
  private int leftEncoderValue = 0, lastLeftEncoderValue = 0;
  private int rightEncoderValue = 0, lastRightEncoderValue = 0;

  private float rightRotationalSpeed = 0;
  private float leftRotationalSpeed = 0;
  private float relativeSpeed = 0.0f;


  private float rightSteeringCorrectionAverage = 1.0f;
  private float leftSteeringCorrectionAverage = 1.0f;
  private float rightSteeringCorrection = 1.0f;
  private float leftSteeringCorrection = 1.0f;
  private int steeringCorrectionLeftEncoderValue = 0;
  private int steeringCorrectionRightEncoderValue = 0;
  private long lastSteeringCorrectionUpdateTime = System.nanoTime();

  private float leftSCMD = 0;
  private float rightSCMD = 0;

  public float getTargetSpeed() {
    return targetSpeed;
  }

  private float targetSpeed = 0.0f;

  private Data() throws IOException, BadSensorValueException, InterruptedException {
    initialize();
    this.leftEncoderValue = this.leftEncoder.readEncoderValue();
    this.rightEncoderValue = this.rightEncoder.readEncoderValue();

    this.steeringCorrectionLeftEncoderValue = this.leftEncoderValue;
    this.steeringCorrectionRightEncoderValue = this.rightEncoderValue;
  }

  private void initialize() throws IOException, InterruptedException {
    LeftEncoder.getInstance().writeToEncoder(Motion.getInstance(), 0);
    RightEncoder.getInstance().writeToEncoder(Motion.getInstance(), 0);
  }

  public static Data getInstanace() throws IOException, BadSensorValueException, InterruptedException {
    if (instance == null) {
      instance = new Data();
    }
    return instance;
  }

  public Data update(float dt, float targetSpeed) throws BadSensorValueException, IOException {
    this.targetSpeed = targetSpeed;

    lastUssValue = ussValue;
    ussValue = uss.read();

    lastLeftEncoderValue = leftEncoderValue;
    leftEncoderValue = leftEncoder.readEncoderValue();

    lastRightEncoderValue = rightEncoderValue;
    rightEncoderValue = rightEncoder.readEncoderValue();

    // Calculate new values
    this.leftSCMD = this.calculateLeftSCMD();
    this.rightSCMD = this.calculateRightSCMD();
    this.rightRotationalSpeed = this.calculateRightRotationalSpeed(dt);
    this.leftRotationalSpeed = this.calculateLeftRotationalSpeed(dt);
    this.relativeSpeed = this.calculateRelativeSpeed(dt);

    this.updateSteeringCorrection();

    return this;
  }

  private float calculateRelativeSpeed(float dt) {
    float difference = ussValue - lastUssValue;
    return difference / dt;
  }

  private float calculateLeftSCMD() {
    return (leftEncoderValue - lastLeftEncoderValue) * TICKS_TO_CM;
  }

  private float calculateRightSCMD() {
    return (rightEncoderValue - lastRightEncoderValue) * TICKS_TO_CM;
  }

  private float calculateLeftRotationalSpeed(float dt) {
    return this.getLeftSCMD() / dt;
  }

  private float calculateRightRotationalSpeed(float dt) {
    return this.getRightSCMD() / dt;
  }

  public boolean objectDetected() { return this.ussValue >= 0; }

  private void updateSteeringCorrection() {
    long currentTime = System.nanoTime();
    if (currentTime - this.lastSteeringCorrectionUpdateTime < STEERING_CORRECTION_UPDATE_PERIOD) {
      this.lastSteeringCorrectionUpdateTime = currentTime;
      int deltaLeft = this.leftEncoderValue - this.steeringCorrectionLeftEncoderValue;
      int deltaRight = this.rightEncoderValue - this.steeringCorrectionRightEncoderValue;
      this.steeringCorrectionLeftEncoderValue = leftEncoderValue;
      this.steeringCorrectionRightEncoderValue = rightEncoderValue;

      // Avoid divide by zero errors...
      if (deltaLeft == 0 || deltaRight == 0) return;

      // Depending on which value is greater, compensate for the discrepency in the corresponding correction value.
      // For example, if deltaLeft is more than deltaRight, then: decrease the leftCorrectionValue if it is greater than
      // 1.0, otherwise increase the rightCorrectionValue.
      //
      // Values are modified increasingly dramatically as the difference between values increases.
      if (deltaLeft > deltaRight) {
        if (this.leftSteeringCorrectionAverage > 1.0)
          this.leftSteeringCorrectionAverage = calculateLeftSteeringCorrectionAverage(
            ((float) deltaRight / (float) deltaLeft) * STEERING_CORRECTION_NEG_COMPENSATION);
        else
          this.rightSteeringCorrectionAverage = calculateRightSteeringCorrectionAverage(
            ((float) deltaLeft / (float) deltaRight) * STEERING_CORRECTION_POS_COMPENSATION);
      } else {
        if (this.rightSteeringCorrectionAverage > 1.0)
          this.rightSteeringCorrectionAverage = calculateLeftSteeringCorrectionAverage(
            ((float) deltaLeft / (float) deltaRight) * STEERING_CORRECTION_NEG_COMPENSATION);
        else
          this.leftSteeringCorrectionAverage = calculateRightSteeringCorrectionAverage(
            ((float) deltaRight / (float) deltaLeft) * STEERING_CORRECTION_POS_COMPENSATION);
      }
    }
  }

  private float calculateRightSteeringCorrectionAverage(float newValue) {
    return (1f / 4f) * newValue + (3f / 4f) * rightSteeringCorrectionAverage;
  }

  private float calculateLeftSteeringCorrectionAverage(float newValue) {
    return (1f / 4f) * newValue + (3f / 4f) * leftSteeringCorrectionAverage;
  }

  private float calculateRightSteeringCorrectionAverage() {
    return rightSteeringCorrectionAverage;
  }

  private float calculateLeftSteeringCorrectionAverage() {
    return leftSteeringCorrectionAverage;
  }

  public void writeSpeeds() throws IOException {
    leftEncoder.writeEncoderValue(leftRotationalSpeed);
    rightEncoder.writeEncoderValue(rightRotationalSpeed);
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
}
