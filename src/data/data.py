from constants import Constants
from encoders import *
from timer import Timer
from ultra_sonic_sensor import *
import time 
from random import randint

class Data:
    _instance = None

    @staticmethod
    def get_instance():
        if Data._instance == None:
            Data._instance = Data()
        return Data._instance

    def __init__(self):
        self.long_read_time = 0.0
        self.long_renc = 0.0
        self.long_lenc = 0.0
        self.uss = UltraSonicSensor.get_instance()
        self.left_encoder = LeftEncoder.get_instance()
        self.right_encoder = RightEncoder.get_instance()

        self.uss_value = 0
        self.last_uss_value = 0
        self.lenc_value = 0
        self.last_lenc_value = 0
        self.renc_value = 0
        self.last_renc_value = 0

        self.right_rotational_speed = 0.0
        self.left_rotational_speed = 0.0
        self.relative_speed = 0.0
 
        self.right_steering_correction_avg = 1.0
        self.left_steering_correction_avg = 1.0
        self.right_steering_correction = 1.0
        self.left_steering_correction = 1.0
        self.steering_correction_lenc = 0
        self.steering_correction_renc = 0

        self.target_speed = 0.0

        self.timer = Timer()

    def update(self, dt):
        self.long_read_time += dt

        self.last_uss_value = self.uss_value
        self.uss_value = self.uss.read()
        lenc = self.left_encoder.read_encoder_value()
        renc = self.right_encoder.read_encoder_value()
        if self.lenc_value > lenc or self.renc_value > renc:
            return
        
        if self.long_read_time > 3.0:
            self.long_read_time = 0.0
            self.long_lenc = self.steering_correction_lenc
            self.long_renc = self.steering_correction_renc
        
        self.last_lenc_value = self.lenc_value
        self.lenc_value = self.left_encoder.read_encoder_value()
        self.last_renc_value = self.renc_value
        self.renc_value = self.right_encoder.read_encoder_value()
        print("Encoder values: ", self.lenc_value, self.renc_value)
        
        time.sleep(0.01)        

        if self.steering_correction_renc == 0:
            self.steering_correction_renc = self.renc_value
            self.steering_correction_lenc = self.lenc_value

        if self.timer.check_dt() > 0.1250:
             self.update_steering_correction()


        # Calculate new values
        # self.leftSCMD = self.calculateLeftSCMD()
        # self.rightSCMD = self.calculateRightSCMD()
        # self.rightRotationalSpeed = self.calculateRightRotationalSpeed(dt)
        #self.leftRotationalSpeed = self.calculateLeftRotationalSpeed(dt)
        self.relative_speed = self.calculate_relative_speed(dt)

        self.update_steering_correction()

    def dynamic_critical_distance(self):
        return (Constants.CRITICAL_DISTANCE + Constants.CRITICAL_DISTANCE * (self.target_speed / 255.0)) / 2.0
    def dynamic_stop_distance(self):
        return (Constants.STOP_DISTANCE + Constants.STOP_DISTANCE * (self.target_speed / 255.0)) / 2.0
    def update_steering_correction(self):
        if self.timer.check_dt() > .125 and self.target_speed > 20:
            delta_left = float(self.lenc_value - self.steering_correction_lenc) - 1.0
            delta_right = float(self.renc_value - self.steering_correction_renc)
            long_delta_left = float(self.lenc_value - self.long_lenc)
            long_delta_right = float(self.renc_value - self.long_renc)
            if abs(delta_left) > 2 * abs(delta_right) or abs(delta_right) > 2 * abs(delta_left):
                return
            print("Deltas: ", delta_left, delta_right)

            self.timer.last_time = self.timer.get_time()
            #if (abs(delta_left) > 25 or abs(delta_right) > 25):
            #    self.steering_correction_renc = self.renc_value
            #    self.steering_correction_lenc = self.lenc_value

            # Avoid divide by zero
            if delta_left < 0.01 or delta_right < 0.01 or delta_left == delta_right:
                self.left_steering_correction_avg = self.calculate_left_steering_correction_avg(1.0)
                self.left_steering_correction_avg = self.calculate_left_steering_correction_avg(1.0)
                return

            # Depending on which value is greater, compensate for the discrepency in the corresponding correction value.
            # For example, if deltaLeft is more than deltaRight, then: decrease the leftCorrectionValue if it is greater than
            # 1.0, otherwise increase the rightCorrectionValue.
            #
            # Values are modified increasingly dramatically as the difference between values increases.
            ''' if delta_left > delta_right:
                if self.right_steering_correction_avg < 1.0:
                    self.right_steering_correction_avg = self.calculate_right_steering_correction_avg(
                        (float(delta_left) / float(delta_right)) * Constants.STEERING_CORRECTION_NEG_COMPENSATION)
                else:
                    self.left_steering_correction_avg = self.calculate_left_steering_correction_avg(
                        (float(delta_right) / float(delta_left)) * Constants.STEERING_CORRECTION_POS_COMPENSATION)
            else:
                if self.left_steering_correction_avg < 1.0:
                    self.left_steering_correction_avg = self.calculate_left_steering_correction_avg(
                        (float(delta_right) / float(delta_left)) * Constants.STEERING_CORRECTION_NEG_COMPENSATION)
                else:
                    self.right_steering_correction_avg = self.calculate_right_steering_correction_avg(
                        (float(delta_left) / float(delta_right)) * Constants.STEERING_CORRECTION_POS_COMPENSATION)'''
            '''if long_delta_right > long_delta_left:
                long_coef = long_delta_right / long_delta_left
            else:
                long_coef = long_delta_left / long_delta_right
            print("Long coef: ", long_coef, "recip: ", 1.0 / long_coef)
            if delta_left > delta_right:
                self.left_steering_correction_avg = self.calculate_left_steering_correction_avg((delta_right / delta_left) * Constants.STEERING_CORRECTION_NEG_COMPENSATION)
                self.right_steering_correction_avg = self.calculate_right_steering_correction_avg((delta_left / delta_right) * Constants.STEERING_CORRECTION_POS_COMPENSATION)
            else:
                self.right_steering_correction_avg = self.calculate_right_steering_correction_avg((delta_left / delta_right) * Constants.STEERING_CORRECTION_NEG_COMPENSATION)
                self.left_steering_correction_avg = self.calculate_left_steering_correction_avg((delta_right / delta_left) * Constants.STEERING_CORRECTION_POS_COMPENSATION)
            '''
            if delta_left > delta_right:
                self.left_steering_correction_avg = self.calculate_left_steering_correction_avg(1.0 - (delta_left - delta_right) * Constants.STEERING_COMP_VALUE)
                self.right_steering_correction_avg = self.calculate_right_steering_correction_avg(1.0 + (delta_left - delta_right) * Constants.STEERING_COMP_VALUE)
            else:
                self.right_steering_correction_avg = self.calculate_right_steering_correction_avg(1.0 - (delta_right - delta_left) * Constants.STEERING_COMP_VALUE)
                self.left_steering_correction_avg = self.calculate_left_steering_correction_avg(1.0 + (delta_right - delta_left) * Constants.STEERING_COMP_VALUE)
            #self.left_steering_correction_avg = max(min(1.5, self.left_steering_correction_avg), 0.5)
            #self.right_steering_correction_avg = max(min(1.5, self.right_steering_correction_avg), 0.5)
            print("Correction values: ", self.left_steering_correction_avg, self.right_steering_correction_avg)

    def set_speed(self, speed):
        print("target speed", self.target_speed)
        self.target_speed = speed

    def write_speed(self):

        print("left speed: ", self.target_speed * self.left_steering_correction_avg, "right speed: ", self.target_speed * self.right_steering_correction_avg)
        self.left_encoder.write_speed(int(self.target_speed * self.left_steering_correction_avg))
        self.right_encoder.write_speed(int(self.target_speed * self.right_steering_correction_avg))

    def object_detected(self):
        return self.uss_value >= 0

    def get_target_speed(self):
        return self.target_speed

    def set_target_speed(self, new_value):
        self.target_speed = new_value

    def calculate_right_steering_correction_avg(self, new_value):
#        return new_value
        return 0.75 * new_value + (1.0 - 0.75) * self.right_steering_correction_avg

    def calculate_left_steering_correction_avg(self, new_value):        
#        return new_value
        return 0.75 * new_value + (1.0 - 0.75) * self.left_steering_correction_avg

    def get_uss_value(self):
        return self.uss_value

    def get_relative_speed(self):
        return self.relative_speed

    def calculate_relative_speed(self, dt):
        difference = float(self.uss_value) - float(self.last_uss_value)
        return difference / dt
