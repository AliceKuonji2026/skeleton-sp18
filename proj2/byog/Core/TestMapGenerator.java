package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class TestMapGenerator {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        int width=100,height=50;
        ter.initialize(width,height);
        TETile[][] world =new TETile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        MapGenerator m=new MapGenerator(world,width,height,"000000000");
        m.setRooms();
        m.connectRooms();

        ter.renderFrame(world);
    }
}
