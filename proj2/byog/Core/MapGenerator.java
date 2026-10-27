package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    public static TETile floor = Tileset.GRASS;
    public static TETile wall = Tileset.WALL;
    private final long seed;
    private int screenWidth;
    private int screenHeight;
    private boolean[][] overlapping;
    private TETile[][] world;
    private static Random RANDOM = null;
    public List<Room> roomList;

    /// initialize the MapGenerator
    public MapGenerator(TETile[][] world, int screenWidth, int screenHeight, String s) {
        this.world = world;
        this.overlapping = new boolean[screenWidth][screenHeight];
        this.seed = Long.parseLong(s);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        RANDOM = new Random(seed);
        this.roomList = new ArrayList<>();
    }


    public void setRooms() {
        int ct = 0;
        int maxsize = 10;
        int maxTempts = 6 * this.screenWidth * this.screenHeight / maxsize / maxsize;
        Position p = new Position(1, 1);
        int h = 5, w = 5;
        while (ct < maxTempts) {
            if (checkSafePlace(p, h, w)) {
                setRectangleRoom(p, h, w);
            }
            // 生成 0 到 screenWidth - 1 之间的随机数
            p.x = RANDOM.nextInt(this.screenWidth);
            p.y = RANDOM.nextInt(this.screenHeight);
            // 生成 5 到 maxsize - 1 之间的随机数（长度 = maxsize - 5）
            w = RANDOM.nextInt(maxsize - 5) + 5;
            // 生成 4 到 maxsize - 1 之间的随机数（长度 = maxsize - 4）
            h = RANDOM.nextInt(maxsize - 4) + 4;
            ct++;
        }

    }

    /// draw s single rectangle room
    public void setRectangleRoom(Position p, int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    world[j + p.x][i + p.y] = wall;
                } else {
                    world[j + p.x][i + p.y] = floor;
                }
            }
        }
        Room m = new Room(p.x, p.y, width, height);
        roomList.add(m);
//        int j1 = p.x, i1 = p.y, j2 = p.x + width - 1, i2 = p.y + height - 1;
//        System.out.println("Room at: i1=" + i1 + ", j1=" + j1 + ", i2=" + i2 + ", j2=" + j2);

        updateOverlapping(p, height, width);
    }

    private void updateOverlapping(Position p, int height, int width) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                overlapping[x + p.x][y + p.y] = true;
            }
        }
    }

    /// check the overlapping room
    private boolean checkSafePlace(Position p, int height, int width) {
        int i1 = p.y, j1 = p.x, i2 = p.y + height - 1, j2 = p.x + width - 1;
        if (!((0 <= i1 && i1 < screenHeight) && (0 <= j1 && j1 < screenWidth) && (0 <= i2 && i2 < screenHeight) && (0 <= j2 && j2 < screenWidth))) {
            return false;
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (overlapping[x + p.x][y + p.y]) return false;
            }
        }
        return true;
    }

    public class Room {
        public int x, y, width, height;

        public Room(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public int getCenterX() {
            return x + width / 2;
        }

        public int getCenterY() {
            return y + height / 2;
        }

        public int distanceTo(Room other) {
            int dx = this.getCenterX() - other.getCenterX();
            int dy = this.getCenterY() - other.getCenterY();
            return Math.abs(dx) + Math.abs(dy);
        }
    }

    public void connectRooms() {
        int n = n = roomList.size();
        if (n < 2) {
            return;
        }
        boolean[] connected = new boolean[n];
        connected[0] = true;
        int ct = 1;
        while (ct < n) {
            int[] best;
            best = findBest(connected);
            if (best[0] != -1) {
                setHallway(roomList.get(best[0]), roomList.get(best[1]));
            }
            connected[best[1]] = true;
            ct++;
        }
    }

    private int[] findBest(boolean[] connected) {
        int n = roomList.size();
        int bestFrom = -1;
        int bestTo = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (!connected[i]) {
                continue;
            }
            for (int j = 0; j < n; j++) {
                if (connected[j]) {
                    continue;
                }
                int dist = roomList.get(i).distanceTo(roomList.get(j));
                if (dist < min) {
                    min = dist;
                    bestFrom = i;
                    bestTo = j;
                }
            }
        }
        int[] res = new int[2];
        res[0] = bestFrom;
        res[1] = bestTo;
        return res;
    }

    public void setHallway(Room r1, Room r2) {
        int x1 = r1.getCenterX(), y1 = r1.getCenterY();
        int x2 = r2.getCenterX(), y2 = r2.getCenterY();
        boolean horizontalFirst = RANDOM.nextBoolean();
        if (horizontalFirst) {
            setHorizontalLine(y1, x1, x2);
            setVerticalLine(x2, y1, y2);
        } else {
            setVerticalLine(x1, y1, y2);
            setHorizontalLine(y2, x1, x2);
        }
    }

    private void setVerticalLine(int x, int y1, int y2) {
        int minY = Math.min(y1, y2), maxY = Math.max(y1, y2);
        for (int i = minY; i <= maxY; i++) {
            if (world[x][i] != Tileset.GRASS) world[x][i] = Tileset.FLOOR;
            if (x + 1 < screenWidth && world[x + 1][i] == Tileset.NOTHING) {
                world[x + 1][i] = Tileset.WALL;
            }
            if (x > 0 && world[x - 1][i] == Tileset.NOTHING) {
                world[x - 1][i] = Tileset.WALL;
            }
        }
    }

    private void setHorizontalLine(int y, int x1, int x2) {
        int minX = Math.min(x1, x2), maxX = Math.max(x1, x2);
        for (int i = minX; i <= maxX; i++) {
            if (world[i][y] != Tileset.GRASS) world[i][y] = Tileset.FLOOR;
            if (y + 1 < screenWidth && world[i][y + 1] == Tileset.NOTHING) {
                world[i][y + 1] = Tileset.WALL;
            }
            if (y > 0 && world[i][y - 1] == Tileset.NOTHING) {
                world[i][y - 1] = Tileset.WALL;
            }
        }
    }


}


