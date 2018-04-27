import sys

from constants import Constants
from data import BadSensorValueError
from state import *
import alert
import emergency
import follow
import gopigo.gopigo


class Stop(State):
    _instance = None

    def __init__(self):
        pass

    def update(self, dt, data):
        try:
            target_speed = data.get_target_speed()
            if target_speed < 0.1:
                return alert.Alert.get_instance()

            uss_value = data.get_uss_value()
            if uss_value > data.dynamic_stop_distance():
                return follow.Follow.get_instance()
            if uss_value < data.dynamic_critical_distance():
                gopigo.stop()
                return emergency.Emergency.get_instance()

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
