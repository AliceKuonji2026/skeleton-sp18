import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        // current picture
        return new Picture(picture);
    }

    public int width() {
        // width of current picture
        return picture.width();
    }

    public int height() {
        // height of current picture
        return picture.height();
    }

    public double energy(int x, int y) {
        // energy of pixel at column x and row y
        int height = picture.height();
        int width = picture.width();
        Color up = picture.get(x, (y - 1 + height) % height);
        Color below = picture.get(x, (y + 1) % height);
        Color left = picture.get((x - 1 + width) % width, y);
        Color right = picture.get((x + 1) % width, y);
        return dt2(up, below) + dt2(left, right);
    }

    private double dt2(Color a, Color b) {
        int dr = a.getRed() - b.getRed();
        int dg = a.getGreen() - b.getGreen();
        int db = a.getBlue() - b.getBlue();
        return dr * dr + dg * dg + db * db;
    }

    public int[] findVerticalSeam() {
        // sequence of indices for vertical seam
        double[][] energies = getAllEnergy(picture);
        double[][] bestToXY = new double[width()][height()];
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                if (y == 0) {
                    bestToXY[x][y] = energies[x][y];
                } else {
                    double min = bestToXY[x][y - 1];
                    if (x > 0) {
                        min = Math.min(min, bestToXY[x - 1][y - 1]);
                    }
                    if (x < width() - 1) {
                        min = Math.min(min, bestToXY[x + 1][y - 1]);
                    }
                    bestToXY[x][y] = min + energies[x][y];
                }
            }
        }
        double min = bestToXY[0][height() - 1];
        int xPos = 0;
        for (int x = 1; x < width(); x++) {
            double tmp = bestToXY[x][height() - 1];
            if (min > tmp) {
                xPos = x;
                min = tmp;
            }
        }
        int[] seam = new int[height()];
        seam[height() - 1] = xPos;
        for (int y = height() - 1; y > 0; y--) {
            int bestX = xPos;
            double best = bestToXY[xPos][y - 1];
            if (xPos > 0 && bestToXY[xPos - 1][y - 1] < best) {
                best = bestToXY[xPos - 1][y - 1];
                bestX = xPos - 1;
            }
            if (xPos + 1 < width() && bestToXY[xPos + 1][y - 1] < best) {
                best = bestToXY[xPos + 1][y - 1];
                bestX = xPos + 1;
            }
            seam[y - 1] = bestX;
            xPos = bestX;
        }
        return seam;
    }

    private double[][] getAllEnergy(Picture p) {
        double[][] energies = new double[width()][height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                energies[x][y] = energy(x, y);
            }
        }
        return energies;
    }

    public int[] findHorizontalSeam() {
        // sequence of indices for horizontal seam
        Picture original = picture;
        picture = transpose(picture);
        int[] seam = findVerticalSeam();
        picture = original;
        return seam;

    }

    public void removeHorizontalSeam(int[] seam) {
        // remove horizontal seam from picture
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        // remove vertical seam from picture
        picture = SeamRemover.removeVerticalSeam(picture, seam);
    }

    private static Picture transpose(Picture original) {
        int width = original.width();
        int height = original.height();
        Picture transposed = new Picture(height, width);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = original.get(x, y);
                transposed.set(y, x, c);
            }
        }

        return transposed;
    }
}