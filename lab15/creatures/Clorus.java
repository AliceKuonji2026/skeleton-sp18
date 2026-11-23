package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class Clorus extends Creature {
    private static final double moveLose = 0.03;
    private static final double stayGain = 0.01;
    private int r;
    private int g;
    private int b;

    /**
     * Creates a creature with the name N. The intention is that this
     * name should be shared between all creatures of the same type.
     *
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    @Override
    public void move() {
        energy -= moveLose;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy += stayGain;
    }

    /**
     * Cloruses should obey exactly the following behavioral rules:
     * If there are no empty squares, the Clorus will STAY (even if there are Plips nearby they could attack).
     * Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * Otherwise, if the Clorus has energy greater than or equal to one, it will REPLICATE to a random empty square.
     * Otherwise, the Clorus will MOVE to a random empty square.
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (!plips.isEmpty()) {
            return new Action(Action.ActionType.ATTACK,
                    plips.get(new Random().nextInt(plips.size())));
        }
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE,
                    empties.get(new Random().nextInt(empties.size())));
        }
        return new Action(Action.ActionType.STAY);
    }

    @Override
    public Color color() {
        return color(r, g, b);
    }
}
