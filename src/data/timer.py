import time


class Timer:
    def __init__(self):
        self.last_time = time.clock()

    def check_dt(self):
        return time.clock() - self.last_time
    def get_time(self):
        return time.clock()
    def get_dt(self):
        now_time = time.clock()
        result = now_time - self.last_time
        self.last_time = now_time
        return result
