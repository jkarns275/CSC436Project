import time


class Timer:
    def __init__(self):
        self.last_time = time.time()

    def check_dt(self):
        return time.time() - self.last_time
    def get_time(self):
        return time.time()
    def get_dt(self):
        now_time = time.time()
        print("OOF: ", now_time)
        result = now_time - self.last_time
        self.last_time = now_time
        return result
