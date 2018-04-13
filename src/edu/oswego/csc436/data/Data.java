package edu.oswego.csc436.data;

public class Data {
  private final static Data instance = new Data();

  public final UltraSonicSensor uss = UltraSonicSensor.getInstance();
  public final LeftEncoder leftEncoder = LeftEncoder.getInstance();
  public final RightEncoder rightEncoder = RightEncoder.getInstance();

  Data() {}

  public static Data getInstanace() { return Data.instance; }
}
