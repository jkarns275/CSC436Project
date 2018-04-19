import sys

from constants import Constants
from data import BadSensorValueError
from state import State
from state.alert import Alert
from state.emergency import Emergency
from state.follow import Follow


class Stop(State):
    _instance = None

    def __init__(self):
        pass

    def update(self, dt, data):
        try:
            target_speed = data.get_target_speed()
            if target_speed < 0.1:
                return Alert.get_instance()

            uss_value = data.get_uss_value()
            if uss_value > Constants.STOP_DISTANCE:
                return Follow.get_instance()
            if uss_value < Constants.CRITICAL_DISTANCE:
                return Emergency.get_instance()

            data.set_speed(max(target_speed - (dt * Constants.STOP_DECEL_RATE), 0.0))
            return self
        except (IOError, BadSensorValueError) as e:
            sys.stderr.write(e)
            return self

    @staticmethod
    def get_instance():
        if Stop._instance == None:
            Stop._instance = Stop()
        return Stop._instance
