package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] eTo;

    public MazeCycles(Maze m) {
        super(m);
        eTo = new int[m.N()*m.N()];
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        cycle(0,-1);
    }

    // Helper methods go here
    private boolean cycle(int v,int parent) {
        marked[v] = true;
        eTo[v] = parent;
        announce();
        for(int w:maze.adj(v)) {
            if(!marked[w]) {
                eTo[w] = v;
                if(cycle(w,v)) return true;
            }else{
                if(w != parent) {
                    eTo[w] = v;
                    drawCycle(w);
                    announce();
                    return true;
                }
            }
        }
        return false;
    }
    private void drawCycle(int s) {
        int pre = eTo[s];
        int p=s;
        do{
            edgeTo[p] = pre;
            p=pre;
            pre=eTo[pre];
        }while(p!=s);
    }
}

