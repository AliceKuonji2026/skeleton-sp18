package lab11.graphs;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int targetX = maze.toX(t), targetY = maze.toY(t);
        int sourceX = maze.toX(v), sourceY = maze.toY(v);
        return Math.abs(targetX - sourceX) + Math.abs(targetY  - sourceY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        PriorityQueue<Integer> q = new PriorityQueue<>((a,b) -> (distTo[a] + h(a)) - (distTo[b] + h(b)));
        q.add(s);
        distTo[s] = 0;
        edgeTo[s] = s;
        while(!q.isEmpty()) {
            int v = q.remove();
            if(marked[v]) continue;
            marked[v] = true;
            announce();
            if(v == t) { return;}
            for(int w:maze.adj(v)) {
                if(distTo[w] > distTo[v] + 1) {
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    q.add(w);

                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

