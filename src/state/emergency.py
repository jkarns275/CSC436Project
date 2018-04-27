import sys

from constants import Constants
from data import BadSensorValueError
from state import State
import alert
import follow
import gopigo.gopigo

class Emergency(State):
    _instance = None

    def __init__(self):
        pass

    def update(self, dt, data):
        try:
            data.set_speed(0.0)
            target_speed = data.get_target_speed()
            data.write_speed()
            gopigo.stop()
            if target_speed < 0.1:
                return alert.Alert.get_instance()

            return self

        except (IOError, BadSensorValueError) as e:
            sys.stderr.write(e)
            return self

    @staticmethod
    def get_instance():
        if Emergency._instance == None:
            Emergency._instance = Emergency()
        return Emergency._instance
