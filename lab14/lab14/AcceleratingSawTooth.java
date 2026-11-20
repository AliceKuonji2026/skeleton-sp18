package lab14;

import lab14lib.Generator;

public class AcceleratingSawTooth extends SawToothGenerator {
    double k;
    public AcceleratingSawTooth(int period,double k) {
        super(period);
        this.k = k;
    }
    @Override
    public double next() {
        double res = normalize(state++);
        period = (int) Math.floor(period * k);
        return res;
    }

}
