import java.util.List;

public class testBoggleSolver {
    public testBoggleSolver() {

    }
    public void main() {
        String boardFilePath = "smallBoard.txt";
        int k = 7;
        List<String> res = Boggle.solve(k,boardFilePath);
        System.out.println(res);
    }
}
