package byog.lab5;

import org.junit.Test;

import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        int k = s;
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < k; j++) {
                world[p.x + j - i][p.y + i] = t;
            }
            k += 2;
        }
        k = s;
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < k; j++) {
                world[p.x + j - i][p.y + 2 * s - 1 - i] = t;
            }
            k += 2;
        }
    }

    public static void pileHexagon(TETile[][] world, Position p0, int s, int length, TETile t) {
        Position p = new Position(p0.x, p0.y);
        int k = length;
        for (int i = 0; i < 2 * length - 1; i++) {
            for (int j = 0; j < k; j++) {
                HexWorld.addHexagon(world, p, s, t);
                p.y += 2 * s;
            }
            p0.x+=2*s-1;
            if (i < s-1) {
                p0.y-=s;
                k++;
            } else {
                p0.y+=s;
                k--;
            }
            p.x = p0.x;
            p.y = p0.y;
        }
    }
}
