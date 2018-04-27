import sys

from state import State
from data.bad_sensor_value import BadSensorValueError
from constants import Constants
import gopigo.gopigo
import follow
import leader


class Alert(State):
    _instance = None

    def __init__(self):
        self.consecutive_no_leading_object_cycles = 0
        pass

    def update(self, dt, data):
        try:
            gopigo.stop()
            #data.set_speed(0.0)
            if data.get_uss_value() > Constants.STOP_DISTANCE:
                self.consecutive_no_leading_object_cycles = 0
                gopigo.fwd()
                return follow.Follow.get_instance()

            if not data.object_detected():
                self.consecutive_no_leading_object_cycles += 1
                if self.consecutive_no_leading_object_cycles >= 5:
                    gopigo.fwd()
                    return leader.Leader.get_instance()
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
