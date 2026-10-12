public class GuitarHero {
    private synthesizer.GuitarString[] chords;
    public static void main(String[] args){
        GuitarHero hero =new GuitarHero();
        hero.run();
    }
    public void run(){
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        chords=new synthesizer.GuitarString[37];
        for(int i=0;i<37;i++){
            double frequency=440.0*Math.pow(2,(i-24)/12.0);
            chords[i]=new synthesizer.GuitarString(frequency);
        }
        while(true){
            if(StdDraw.hasNextKeyTyped()){
                char key=StdDraw.nextKeyTyped();
                int index=keyboard.indexOf(key);
                if(index!=-1){
                    chords[index].pluck();
                }
                for (int i = 0; i < 50000; i++) {
                    StdAudio.play(chords[index].sample());
                    chords[index].tic();
                }
            }
        }
    }
}
