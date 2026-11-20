package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioAnimator;

public class test {
    public void main(){
        Generator g = new StrangeBitwiseGenerator(200);
        GeneratorAudioAnimator ga = new GeneratorAudioAnimator(g);
        ga.drawAndPlay(4096,10000);
    }

}
