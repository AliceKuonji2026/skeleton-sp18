package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    int period;
    int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        double res = normalize(state++);
        int weridState = state & (state >>> 3) % period;
        return normalize(weridState);
    }

    protected double normalize(int state) {
        return -1 + (state % period) * 1.0 / (period - 1);
    }
}
