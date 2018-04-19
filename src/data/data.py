from constants import Constants
from data.encoders import LeftEncoder, RightEncoder
from data.timer import Timer
from data.ultra_sonic_sensor import UltraSonicSensor


class Data:
    _instance = None

    @staticmethod
    def get_instance():
        if Data._instance == None:
            Data._instance = Data()
        return Data._instance

    def __init__(self):
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

        self.right_steering_correction_avg = 0.0
        self.left_steering_correction_avg = 0.0
        self.right_steering_correction = 0.0
        self.left_steering_correction = 0.0
        self.steering_correction_lenc = 0
        self.steering_correction_renc = 0

        self.target_speed = 0.0

        self.timer = Timer()

    def update(self, dt):
        self.last_uss_value = self.uss_value
        self.uss_value = self.uss.read()

        self.last_lenc_value = self.lenc_value
        self.lenc_value = self.left_encoder.read_encoder_value()

        self.last_renc_value = self.renc_value
        self.renc_value = self.right_encoder.read_encoder_value()

        # Calculate new values
        # self.leftSCMD = self.calculateLeftSCMD()
        # self.rightSCMD = self.calculateRightSCMD()
        # self.rightRotationalSpeed = self.calculateRightRotationalSpeed(dt)
        #self.leftRotationalSpeed = self.calculateLeftRotationalSpeed(dt)
        self.relativeSpeed = self.calculate_relative_speed(dt)

        self.update_steering_correction()

    def update_steering_correction(self):
        if self.timer.check_dt() > Constants.STEERING_CORRECTION_UPDATE_PERIOD:
            self.timer.last_time = self.timer.get_time()
            delta_left = float(self.lenc_value - self.steering_correction_lenc)
            delta_right = float(self.renc_value - self.steering_correction_renc)
            self.steering_correction_renc = self.renc_value
            self.steering_correction_lenc = self.lenc_value

            # Avoid divide by zero
            if delta_left == 0 or delta_right == 0:
                return

            # Depending on which value is greater, compensate for the discrepency in the corresponding correction value.
            # For example, if deltaLeft is more than deltaRight, then: decrease the leftCorrectionValue if it is greater than
            # 1.0, otherwise increase the rightCorrectionValue.
            #
            # Values are modified increasingly dramatically as the difference between values increases.
            if (delta_left > delta_right):
                if self.left_steering_correction_avg > 1.0:
                    self.leftSteeringCorrectionAverage = self.calculate_left_steering_correction_avg(
                        (float(delta_right) / float(delta_left)) * Constants.STEERING_CORRECTION_NEG_COMPENSATION)
                else:
                    self.rightSteeringCorrectionAverage = self.calculate_right_steering_correction_avg(
                        (float(delta_left) / float(delta_right)) * Constants.STEERING_CORRECTION_POS_COMPENSATION)
            else:
                if (self.rightSteeringCorrectionAverage > 1.0):
                    self.rightSteeringCorrectionAverage = self.calculate_right_steering_correction_avg(
                        (float(delta_left) / float(delta_right)) * Constants.STEERING_CORRECTION_NEG_COMPENSATION)
                else:
                    self.leftSteeringCorrectionAverage = self.calculate_left_steering_correction_avg(
                        (float(delta_right) / float(delta_left)) * Constants.STEERING_CORRECTION_POS_COMPENSATION)


    def write_speed(self):
        self.left_encoder.write_speed(self.target_speed * self.left_steering_correction_avg)
        self.right_encoder.write_speed(self.target_speed * self.right_steering_correction_avg)

    def object_detected(self):
        return self.uss_value >= 0

    def get_target_speed(self):
        return self.target_speed

    def set_target_speed(self, new_value):
        self.target_speed = new_value

    def calculate_right_steering_correction_avg(self, new_value):
        return (1.0 / 4.0) * new_value + (3.0 / 4.0) * self.right_steering_correction_avg

    def calculate_left_steering_correction_avg(self, new_value):
        return (1.0 / 4.0) * new_value + (3.0 / 4.0) * self.left_steering_correction_avg

    def get_uss_value(self):
        pass

    def get_relative_speed(self):
        pass

    def calculate_relative_speed(self, dt):
        difference = float(self.uss_value) - float(self.last_uss_value)
        return difference / dt
