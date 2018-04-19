package edu.oswego.csc436;

public class Constants {
  public static final long SECONDS_TO_NANOS = 1_000_000_000;

  // TODO: The following values should be added to the SRS as SOR's
  // Begin
  public static final int SPEED_LIMIT = 196; // (18 * cm) / s
  public static final float TICKS_TO_CM = 1f/18f;
  public static final int FOLLOW_DISTANCE = 500; //cm
  public static final int CRITICAL_DISTANCE = 15; //cm
  public static final int STOP_DISTANCE = 30; //cm
  public static final long STEERING_CORRECTION_UPDATE_PERIOD = SECONDS_TO_NANOS / 4; // Every quarter of a second

  // How high steering correction can get, before the other value will be reduced instead of this one being increased.
  public static final float STEERING_CORRECTION_MAX = 1.5f;
  private static final float STEERING_COMP_VALUE = 0.05f;
  public static final float STEERING_CORRECTION_POS_COMPENSATION = 1f + STEERING_COMP_VALUE;
  public static final float STEERING_CORRECTION_NEG_COMPENSATION = 1f - STEERING_COMP_VALUE;
  // End

  // D1.17 - TODO: Update this with the value here
  public static final float ACCELERATION_RATE = SPEED_LIMIT / 10; // (18 * cm) / (s^2)

  // MF4.1.1
  public static final float STOP_DECEL_RATE = (float) SPEED_LIMIT / 4; // (18 * cm) / (s^2) (see: Acceleration)

  // MF3.1.1
  public static final float EMERGENCY_DECEL_RATE = (float) SPEED_LIMIT / 2.05f; // (18 * cm) / (s^2) (see: Acceleration)
}
