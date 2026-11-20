package lab14;

public class AcceleratingSawToothGenerator extends SawToothGenerator {
    double k;
    public AcceleratingSawToothGenerator(int period,double k) {
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
