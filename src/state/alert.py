import sys

from state import State
from data.bad_sensor_value import BadSensorValueError
from constants import Constants
from follow import Follow
from leader import Leader


class Alert(State):
    _instance = None

    def __init__(self):
        self.consecutive_no_leading_object_cycles = 0
        pass

    def update(self, dt, data):
        try:
            data.set_speed(0.0)
            if data.getUssValue() > Constants.STOP_DISTANCE:
                self.consecutive_no_leading_object_cycles = 0
                return Follow.get_instance()

            if not data.objectDetected():
                self.consecutive_no_leading_object_cycles += 1
                if self.consecutive_no_leading_object_cycles >= 5:
                    return Leader.get_instance()
            else:
                self.consecutive_no_leading_object_cycles = 0

            return self
        except (IOError, BadSensorValueError) as e:
            sys.stderr.write(e)
            return self

    @staticmethod
    def get_instance():
        if Alert._instance == None:
            Alert._instance = Alert()
        return Alert._instance
