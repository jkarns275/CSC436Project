class Constants:
    # One million micro seconds in a second
    SECONDS_TO_MICROS = 1000000

    # TODO: The following values should be added to the SRS as SOR's
    # Begin
    SPEED_LIMIT = 196.0  # (18 * cm) / s
    TICKS_TO_CM = 1.0 / 18.0
    FOLLOW_DISTANCE = 500.0  # cm
    CRITICAL_DISTANCE = 15.0  # cm
    STOP_DISTANCE = 30.0  # cm
    STEERING_CORRECTION_UPDATE_PERIOD = SECONDS_TO_MICROS / 4.0  # Every quarter of a second

    # How high steering correction can get, before the other value will be reduced instead of this one being increased.
    STEERING_CORRECTION_MAX = 1.5
    STEERING_COMP_VALUE = 0.05
    STEERING_CORRECTION_POS_COMPENSATION = 1.0 + STEERING_COMP_VALUE
    STEERING_CORRECTION_NEG_COMPENSATION = 1.0 - STEERING_COMP_VALUE
    # End

    # D1.17 - TODO: Update this with the value here
    ACCELERATION_RATE = SPEED_LIMIT / 10.0  # (18 * cm) / (s^2)

    # MF4.1.1
    STOP_DECEL_RATE = SPEED_LIMIT / 4.0  # (18 * cm) / (s^2) (see: Acceleration)

    # MF3.1.1
    EMERGENCY_DECEL_RATE = SPEED_LIMIT / 2.05  # (18 * cm) / (s^2) (see: Acceleration)

    FREQUENCY = 10.0  # Hz
    CYCLE_MIN_PERIOD = 1.0 / FREQUENCY

    LEFT_MOTOR = 0
    RIGHT_MOTOR = 1

    ULTRASONIC_PIN = 15