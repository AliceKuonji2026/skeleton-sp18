package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;

import byog.lab5.Position;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    public static boolean gameStart = false;
    public static boolean gameOver = false;
    public static Position p = new Position(0, 0);
    public boolean maybeQuit = false;
    public static GameState gameState = new GameState();
    public String seed;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        startMenu();
        String input = startMenuInput();

        p.x = gameState.playerX;
        p.y = gameState.playerY;
        seed = input;


        while (!gameOver) {
            drawWorld(world, input);
            drawUI(world);

            drawPlayer();
            checkKeyboard(world);
            drawPlayer();
            updateState();
        }
    }

    /**
     * Method used for auto grading and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww"). The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        seed = input;
        gameStart = true;
        while (!gameOver) {
            drawWorld(world, input);
            drawUI(world);

            drawPlayer();
            checkKeyboard(world);
            drawPlayer();
            updateState();
        }
        return world;
    }

    private String startMenuInput() {
        boolean waitForSeed = false;
        StringBuilder s = new StringBuilder();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);
                if (c == 'n') {
                    waitForSeed = true;
                    continue;
                } else if (c == 'q') {
                    end();
                } else if (c == 'l') {
                    gameState = load();
                    return gameState.seed;
                }
                if (waitForSeed) {
                    if (c >= '0' && c <= '9') {
                        s.append(c);
                    } else if (c == 's' && s.length()>0) {
                        break;
                    } else {
                        s = new StringBuilder();
                        waitForSeed = false;
                    }
                }
            }
        }
        return s.toString();
    }


    private void startMenu() {
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2., HEIGHT * 2. / 3., "CS61B: THE GAME");
        font = new Font("Monaco", Font.BOLD, 16);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2., HEIGHT / 2., "New Game (N)");
        StdDraw.text(WIDTH / 2., HEIGHT / 2. - 1, "Load Game (L)");
        StdDraw.text(WIDTH / 2., HEIGHT / 2. - 2, "Quit (Q)");
        StdDraw.show();
    }

    private void drawUI(TETile[][] world) {
        double x = StdDraw.mouseX(), y = StdDraw.mouseY();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(3, HEIGHT - 2, world[(int) x][(int) y].description());
        StdDraw.show();
    }

    private void drawWorld(TETile[][] world, String input) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        MapGenerator m = new MapGenerator(world, WIDTH, HEIGHT, input);
        m.drawRooms();
        m.connectRooms();
        ter.renderFrame(world);
    }

    private void move(TETile[][] world, char c) {
        switch (c) {
            case 'w':
                if (!(p.y == HEIGHT - 1 || world[p.x][p.y + 1] == Tileset.WALL)) {
                    p.y++;
                }
                break;
            case 'a':
                if (!(p.x == 0 || world[p.x - 1][p.y] == Tileset.WALL)) {
                    p.x--;
                }
                break;
            case 's':
                if (!(p.y == 0 || world[p.x][p.y - 1] == Tileset.WALL)) {
                    p.y--;
                }
                break;
            case 'd':
                if (!(p.x == WIDTH - 1 || world[p.x + 1][p.y] == Tileset.WALL)) {
                    p.x++;
                }
                break;
        }

    }

    private void drawPlayer() {
        Tileset.PLAYER.draw(p.x, p.y);
        StdDraw.show();
    }

    private void checkKeyboard(TETile[][] world) {
        if (StdDraw.hasNextKeyTyped()) {
            char c = StdDraw.nextKeyTyped();
            c = Character.toLowerCase(c);
            if (c == 'w' || c == 'a' || c == 's' || c == 'd') {
                move(world, c);
                maybeQuit = false;
            } else {
                if (maybeQuit && c == 'q') {
                    save(gameState);
                    end();
                }
                maybeQuit = c == ':';
            }
        }
    }

    public void save(GameState state) {
        File f = new File("./GameState.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(state);
            os.close();
            fs.close();
            System.out.println("The game has been saved!");
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private GameState load() {
        System.out.println("load");
        try {
            FileInputStream fi = new FileInputStream("./GameState.ser");
            ObjectInputStream oi = new ObjectInputStream(fi);
            GameState state = (GameState) oi.readObject();
            oi.close();
            fi.close();
            return state;
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
            return null;
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("class not found");
            System.exit(0);
            return null;
        }
    }

    public void updateState() {
        gameState.playerX = p.x;
        gameState.playerY = p.y;
        gameState.seed = seed;
    }

    public void end() {
        System.out.println("end");
        System.exit(0);
    }

}
