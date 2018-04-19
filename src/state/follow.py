import sys

from state import State
from data.bad_sensor_value import BadSensorValueError
from constants import Constants

# MG2.1, D1.21
from state.emergency import Emergency
from state.leader import Leader
from state.stop import Stop


class Follow(State):
    _instance = None

    def __init__(self):
        # MF2.2.1

        self.consecutive_missing_object_cycles = 0
        pass

    def update(self, dt, data):
        try:

            # MF2.2.1
            if data.objectDetected():
                self.consecutive_missing_object_cycles += 1
                if self.consecutive_missing_object_cycles >= 5:
                    return Leader.get_instance()
            else:
                self.consecutive_missing_object_cycles = 0

            uss_value = data.get_uss_value()

            if uss_value < Constants.CRITICAL_DISTANCE:
                return Emergency.get_instance()
            if uss_value < Constants.STOP_DISTANCE:
                return Stop.get_instance()

            relative_speed = data.get_relative_speed()
            target_speed = data.get_target_speed()

            # TODO: Remove requirement MF2.1.5.1, or uncomment the code. I don't think we should
            # keep the requirement as it will cause the leading object to drift away.

            # if (Math.abs((relativeSpeed - targetSpeed) / targetSpeed) > 0.05):
            # MF2.1.4
            if relative_speed > 0.0:
                # MF2.1.2.1, MF1.1.3
                data.set_speed(
                    min(target_speed + (Constants.ACCELERATION_RATE * dt), Constants.SPEED_LIMIT))  # MF2.1.2.1
            # MF2.1.5
            else:
                data.set_speed(max(target_speed - (Constants.ACCELERATION_RATE * dt), 0.0))  # MF2.1.2.1

            return self

        except (IOError, BadSensorValueError) as e:
            sys.stderr.write(e)
            return self

    @staticmethod
    def get_instance():
        if Follow._instance == None:
            Follow._instance = Follow()
        return Follow._instance
