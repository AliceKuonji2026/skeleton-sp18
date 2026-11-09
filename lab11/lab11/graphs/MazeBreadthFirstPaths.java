package lab11.graphs;
import java.util.ArrayDeque;
import java.util.Queue;
/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int source;
    private int target;
    private boolean targetFound = false;
    private Maze maze;
    private Queue<Integer> q = new ArrayDeque<>();

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        source = maze.xyTo1D(sourceX,sourceY);
        target = maze.xyTo1D(targetX,targetY);
        distTo[source] = 0;
        edgeTo[source] = source;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()

        q.add(source);
        marked[source] = true;
        announce();
        while(!q.isEmpty()) {
            int v = q.remove();
            if(v == target) {
                targetFound = true;
            }
            if(targetFound) {
                return;
            }
            for(int w: maze.adj(v)) {
                if(!marked[w]) {
                    q.add(w);
                    marked[w] = true;
                    announce();
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    announce();
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

