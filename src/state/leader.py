import sys

from constants import Constants
from data import BadSensorValueError
from state import State
import emergency
import follow
import stop
import gopigo.gopigo

class Leader(State):
    _instance = None

    def __init__(self):
        pass

    def update(self, dt, data):
        try:
            gopigo.fwd()
            if data.object_detected():
                uss_value = data.get_uss_value()
                if uss_value < data.dynamic_critical_distance():
                    return emergency.Emergency.get_instance()
                elif uss_value < data.dynamic_stop_distance():
                    return stop.Stop.get_instance()
                elif uss_value < Constants.FOLLOW_DISTANCE:
                    return follow.Follow.get_instance()

            target_speed = data.get_target_speed()
            if target_speed < Constants.SPEED_LIMIT:
                data.set_speed(min(target_speed + (Constants.ACCELERATION_RATE * dt), Constants.SPEED_LIMIT))
            else:
                data.set_speed(max(target_speed - (Constants.ACCELERATION_RATE * dt), 0.0))

            return self

        except (IOError, BadSensorValueError) as e:
            sys.stderr.write(e)
            return self

    @staticmethod
    def get_instance():
        if Leader._instance == None:
            Leader._instance = Leader()
        return Leader._instance
