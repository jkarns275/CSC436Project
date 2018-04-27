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
        self.avg = 0.0

    def read(self):
        dist = min(251, us_dist(Constants.ULTRASONIC_PIN))
        if dist == 0:
            return self.avg
        self.avg = self.avg * .125 + (1.0 - .125) * dist
        return dist
        print("uss: ", dist, self.avg)
        if dist > 250:
            return self.avg
        if dist < 0 and self.avg < 10.0:
            return self.avg
        if dist < 60:
            return dist
        print("uss: ", self.avg)
        return self.avg
#        self.hist[0] = self.hist[1]; self.hist[1] = self.hist[2]; self.hist[2] = self.hist[3]
#        self.hist[3] = dist
#        ret = (0.125 * self.hist[0] + 0.125 * self.hist[1] + 0.25 * self.hist[2] + 0.5 * self.hist[3])
        return dist
