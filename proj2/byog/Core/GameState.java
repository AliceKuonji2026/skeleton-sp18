package byog.Core;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 12323123123L;
    public String seed;
    public int playerX = 0;
    public int playerY = 0;
}
