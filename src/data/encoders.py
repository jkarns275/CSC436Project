from constants import Constants
from gopigo import *

class Encoder:
    def __init__(self):
        pass

    def read_encoder_value(self):
        id = self.get_id()
        return enc_read(id)

    def write_encoder_value(self, value):
        #(m1, m2, _id) = self.get_id()
        # Disable encoder targeting
        enc_tgt(0, 0, 0)

    def write_speed(self, value):
        id = self.get_id()
        if id == Constants.LEFT_MOTOR:
            set_left_speed(value)
        else:
            set_right_speed(value)

    def get_id(self):
        raise Exception("This method should be overridden!")

class LeftEncoder(Encoder):
    _instance = None
    @staticmethod
    def get_instance():
        if LeftEncoder._instance == None:
            LeftEncoder._instance = LeftEncoder()
        return LeftEncoder._instance

    def __init__(self):
        pass

    def get_id(self):
        return Constants.LEFT_MOTOR

class RightEncoder(Encoder):
    _instance = None
    @staticmethod
    def get_instance():
        if RightEncoder._instance == None:
            RightEncoder._instance = LeftEncoder()
        return RightEncoder._instance

    def __init__(self):
        pass

    def get_id(self):
        return Constants.RIGHT_MOTOR
