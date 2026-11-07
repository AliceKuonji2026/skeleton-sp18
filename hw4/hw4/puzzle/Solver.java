package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Solver {
    private SearchNode goalNode ;
    private static class SearchNode implements Comparable<SearchNode> {
        private final WorldState worldState;
        int footStep;
        int distanceToGoal;
        SearchNode preSearchNode;
        public SearchNode(WorldState worldState,SearchNode preSearchNode) {
            this.worldState = worldState;
            this.preSearchNode = preSearchNode;
            this.footStep = (preSearchNode == null) ? 0: preSearchNode.footStep + 1;
            distanceToGoal = this.footStep + this.worldState.estimatedDistanceToGoal();
        }
        @Override
        public int compareTo(SearchNode node) {
            return Integer.compare(this.distanceToGoal,node.distanceToGoal);
        }
    }

    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        SearchNode searchNode = new SearchNode(initial,null);
        minPQ.insert(searchNode);
        while(!minPQ.isEmpty()) {
            SearchNode node = minPQ.delMin();
            if(node.worldState.isGoal()) {
                goalNode = node;
                return;
            } else {
                for (WorldState neighbor : node.worldState.neighbors()) {
                    if(node.preSearchNode != null && neighbor.equals(node.preSearchNode.worldState)) { continue;}
                    SearchNode neighborNode = new SearchNode(neighbor, node);
                    minPQ.insert(neighborNode);
                }
            }
        }
    }

    /**
     * Returns  the minimum number of moves to solve the puzzle starting
     *                  at the initial WorldState.
     */
    public int moves() {
        return goalNode.footStep;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     *                  to the solution.
     */
    public Iterable<WorldState> solution() {
        ArrayList<WorldState> solution = new ArrayList<>();
        SearchNode node = goalNode;
        while(node != null) {
            solution.add(node.worldState);
            node = node.preSearchNode;
        }
        Collections.reverse(solution);
        return solution;
    }
}
