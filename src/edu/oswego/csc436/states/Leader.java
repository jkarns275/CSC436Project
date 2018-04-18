package edu.oswego.csc436.states;

import edu.oswego.csc436.data.BadSensorValueException;
import edu.oswego.csc436.data.Data;

import java.io.IOException;

public class Leader extends State {
    @Override
    public State update(float dt, Data data) {
        try {
            data = data.update(dt, data.SPEED_LIMIT);
            if (data.getUssValue() < data.CRITICAL_DISTANCE) {
                return new Emergency();
            } else if (data.getUssValue() < data.STOP_DISTANCE) {
                return new Stop();
            } else if (data.getUssValue() < data.FOLLOW_DISTANCE) {
                return new Follow();
            } else {
                data.writeSpeeds();
                return this;
            }
        } catch (BadSensorValueException exception) {
            return this;
        } catch (IOException e) {
            e.printStackTrace();
            return this;
        }
    }
}
