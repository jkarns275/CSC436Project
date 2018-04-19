import sys

from constants import Constants
from data import BadSensorValueError
from state import State
from state.emergency import Emergency
from state.follow import Follow
from state.stop import Stop


class Leader(State):
    _instance = None

    def __init__(self):
        pass

    def update(self, dt, data):
        try:
            if data.object_detected():
                uss_value = data.get_uss_value()
                if uss_value < Constants.CRITICAL_DISTANCE:
                    return Emergency.get_instance()
                elif uss_value < Constants.STOP_DISTANCE:
                    return Stop.get_instance()
                elif uss_value < Constants.FOLLOW_DISTANCE:
                    return Follow.get_instance()

            target_speed = data.get_target_speed()
            if target_speed > Constants.SPEED_LIMIT:
                data.set_speed(min(target_speed + (Constants.ACCELERATION_RATE * dt), Constants.SPEED_LIMIT))
            else:
                data.set_speed(min(target_speed - (Constants.ACCELERATION_RATE * dt), Constants.SPEED_LIMIT))

            return self

        except (IOError, BadSensorValueError) as e:
            sys.stderr.write(e)
            return self

    @staticmethod
    def get_instance():
        if Leader._instance == None:
            Leader._instance = Leader()
        return Leader._instance
