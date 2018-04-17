package edu.oswego.csc436.data;

public class Data {
  private final static Data instance = new Data();

  public final UltraSonicSensor uss = UltraSonicSensor.getInstance();
  public final LeftEncoder leftEncoder = LeftEncoder.getInstance();
  public final RightEncoder rightEncoder = RightEncoder.getInstance();

  private int ussValue = 0, lastUssValue = 0;
  private int leftEncoderValue = 0, lastLeftEncoderValue = 0;
  private int rightEncoderValue = 0, lastRightEncoderValue = 0;

  Data() { }

  public static Data getInstanace() { return Data.instance; }

  public Data update() {
    // Calculate new values
    return this;
  }
}
