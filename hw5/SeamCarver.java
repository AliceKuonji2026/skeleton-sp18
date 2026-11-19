import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public Picture picture() {
        // current picture
        return picture;
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
        int rgbUP = picture.getRGB(x, (y - 1 + height) % height);
        int rgbBELOW = picture.getRGB(x, (y + 1) % height);
        int rgbLEFT = picture.getRGB((x - 1 + width) % width, y);
        int rgbRIGHT = picture.getRGB((x + 1) % width, y);
        return dt_2(rgbUP, rgbBELOW) + dt_2(rgbLEFT, rgbRIGHT);
    }

    private double dt_2(int rgb1, int rgb2) {
        return (Math.pow(((rgb1 >> 16) & 0xFF) - ((rgb2 >> 16) & 0xFF), 2)
                + Math.pow(((rgb1 >> 8) & 0xFF) - ((rgb2 >> 8) & 0xFF), 2)
                + Math.pow((rgb1 & 0xFF) - (rgb2 & 0xFF), 2));
    }

    public int[] findVerticalSeam() {
        // sequence of indices for vertical seam
        double[][] energies = getAllEnergy(picture);
        double[][] bestToXY = new double[width()][height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (y == 0) {
                    bestToXY[x][y] = energies[x][y];
                } else {
                    double min = bestToXY[x][y - 1];
                    if (x > 0) min = Math.min(min, bestToXY[x - 1][y - 1]);
                    if (x < width() - 1) min = Math.min(min, bestToXY[x + 1][y - 1]);
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
        picture = transpose(picture);
        int[] seam = findVerticalSeam();
        picture = transpose(picture);
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
                int rgb = original.getRGB(x, y);
                transposed.setRGB(y, x, rgb);
            }
        }

        return transposed;
    }
}