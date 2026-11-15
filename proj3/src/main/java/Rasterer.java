import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private double ullon;
    private double ullat;
    private double lrlon;
    private double lrlat;
    private double w; // 用户的窗口宽度（像素）
    private double h; // 用户的窗口高度（像素）
    private int depth;
    private double worldLonDPP;
    private double worldLatDPP;
    private int minX, maxX, minY, maxY;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        this.ullon = params.get("ullon");
        this.ullat = params.get("ullat");
        this.lrlon = params.get("lrlon");
        this.lrlat = params.get("lrlat");
        this.w = params.get("w"); // 用户的窗口宽度（像素）
        this.h = params.get("h"); // 用户的窗口高度（像素）
        depthCal();
        results.put("depth", depth);
        this.worldLatDPP = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / (Math.pow(2, depth) * MapServer.TILE_SIZE);
        CalXY();
        putRasterXY(results);

        String[][] s;
        if (checkOverlap()) {
            s = getRasterGrid(results);
            results.put("query_success", true);
        } else {
            s = new String[0][0];
            results.put("query_success", false);
        }

        results.put("render_grid", s);
        return results;
    }

    private String[][] getRasterGrid(Map<String, Object> results) {
        System.out.println("minX:" + minX + " | " + "maxX:" + maxX + "\n" + "minY:" + minY + " | " + "maxY:" + maxY);

        if (maxX < minX) return new String[0][0];

        String[][] raster_grid = new String[maxY - minY + 1][maxX - minX + 1];
        for(int x=0;x<maxX - minX + 1;x++) {
            for(int y=0;y<maxY - minY + 1;y++) {
                raster_grid[y][x] = toString(x + minX,y + minY);
            }
        }
        return raster_grid;
    }

    private void CalXY() {
        int n = (int) Math.pow(2, depth);
        double tileLonSpan = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / n;
        double tileLatSpan = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / n;
        this.minX = (int) Math.floor((ullon - MapServer.ROOT_ULLON) / tileLonSpan);
        this.maxX = (int) Math.floor((lrlon - MapServer.ROOT_ULLON) / tileLonSpan);
        this.minY = (int) Math.floor((MapServer.ROOT_ULLAT - ullat) / tileLatSpan);
        this.maxY = (int) Math.floor((MapServer.ROOT_ULLAT - lrlat) / tileLatSpan);
        minX = Math.max(minX, 0);
        maxX = Math.min(maxX, n - 1);
        minY = Math.max(minY, 0);
        maxY = Math.min(maxY, n - 1);
    }

    private void putRasterXY(Map<String, Object> results) {
        results.put("raster_ul_lon", left(minX, minY));
        results.put("raster_ul_lat", top(minX, minY));
        results.put("raster_lr_lon", right(maxX, maxY));
        results.put("raster_lr_lat", bottom(maxX, maxY));
    }

    private void depthCal() {
        double queryLonDPP = (lrlon - ullon) / w;
        this.depth = 0;
        while (true) {
            this.worldLonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (Math.pow(2, depth) * MapServer.TILE_SIZE);
            if (worldLonDPP <= queryLonDPP || depth == 7) break;
            depth++;
        }
    }

    private double left(int x, int y) {
        return MapServer.ROOT_ULLON + worldLonDPP * x * MapServer.TILE_SIZE;
    }

    private double right(int x, int y) {
        return MapServer.ROOT_ULLON + worldLonDPP * (x + 1) * MapServer.TILE_SIZE;
    }

    private double top(int x, int y) {
        return MapServer.ROOT_ULLAT - worldLatDPP * y * MapServer.TILE_SIZE;
    }

    private double bottom(int x, int y) {
        return MapServer.ROOT_ULLAT - worldLatDPP * (y + 1) * MapServer.TILE_SIZE;
    }

    private boolean checkOverlap() {
        return !(MapServer.ROOT_ULLON > lrlon) && !(MapServer.ROOT_LRLON < ullon) && !(MapServer.ROOT_ULLAT < lrlat) && !(MapServer.ROOT_LRLAT > ullat);
    }

    private String toString(int x, int y) {
        return "d" + depth + "_x" + x + "_y" + y + ".png";
    }


}
