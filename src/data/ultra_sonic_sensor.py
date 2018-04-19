from constants import Constants
from gopigo.gopigo import *


class UltraSonicSensor:
    _instance = None

    @staticmethod
    def get_instance():
        if UltraSonicSensor._instance == None:
            UltraSonicSensor._instance = UltraSonicSensor()
        return UltraSonicSensor._instance

    def __init__(self):
        pass

    def read(self):
        return us_dist(Constants.ULTRASONIC_PIN)
