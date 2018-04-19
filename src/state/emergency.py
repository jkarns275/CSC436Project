import sys

from constants import Constants
from data import BadSensorValueError
from state import State
from state.alert import Alert
from state.follow import Follow


class Emergency(State):
    _instance = None

    def __init__(self):
        pass

    def update(self, dt, data):
        try:
            target_speed = data.get_target_speed()
            if target_speed < 0.1:
                return Alert.get_instance()

            data.set_speed(max(target_speed - (dt * Constants.EMERGENCY_DECEL_RATE), 0.0))
            return self

        except (IOError, BadSensorValueError) as e:
            sys.stderr.write(e)
            return self

    @staticmethod
    def get_instance():
        if Emergency._instance == None:
            Emergency._instance = Emergency()
        return Emergency._instance
