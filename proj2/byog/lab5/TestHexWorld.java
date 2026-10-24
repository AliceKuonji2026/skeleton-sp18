package byog.lab5;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class TestHexWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
//        Position p= new Position(6,6);
//        HexWorld.addHexagon(world,p, 6,Tileset.MOUNTAIN);
//        Position p1=new Position(5,30);
//        HexWorld.addHexagon(world,p1,5,Tileset.FLOWER);
        Position p=new Position(30,12);
        HexWorld.pileHexagon(world,p,3,3,Tileset.TREE);


        // draws the world to the screen
        ter.renderFrame(world);
    }
}
