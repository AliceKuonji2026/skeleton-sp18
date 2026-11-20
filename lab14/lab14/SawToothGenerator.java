package lab14;

import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    int period;
    int state;

    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        return normalize(state++);
    }

    protected double normalize(int state) {
        return -1 + (state % period) * 1.0 / (period - 1);
    }
}
