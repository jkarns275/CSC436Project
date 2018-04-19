from constants import Constants
from gopigo.gopigo import *
from data import *
from state import *
from state.alert import Alert
from data.data import Data
from data.timer import Timer

import signal
import sys

def signal_handler(_signal, _frame):
    print("Goodbye!\n")
    data.get_instance()
    data.set_target_speed(0.0)
    data.write_speed()
    sys.exit(0)

if __name__ == '__main__':
    data = Data.get_instance()
    state = Alert.get_instance()

    data.update(.016)
    last_time = time.clock()

    timer = Timer()

    while True:
        try:
            dt = timer.get_dt()
            if dt < Constants.CYCLE_MIN_PERIOD:
                time.sleep(Constants.CYCLE_MIN_PERIOD - dt)

            data.update(dt)
            state = state.update(dt, data)
            data.write_speed()
        except Exception as e:
            sys.stderr.write(e)
