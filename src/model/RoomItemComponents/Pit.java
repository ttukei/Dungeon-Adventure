package model.RoomItemComponents;

import java.util.Random;

public class Pit extends RoomItem{

    /** The amount of HitPoints it will subtract. */
    private int myHealthToBeDamaged;

    /**
     * Constructor that will also generate a random HitPoint number
     */
    public Pit() {
        super("Pit");
        this.myHealthToBeDamaged = getRandomHitPoint();
    }

    /**
     * Randomly get a number between 1-20 and negates it.
     *
     * @return the random number to be subtracted.
     */
    private int getRandomHitPoint() {
        Random rand = new Random();
        return  -1 * (rand.nextInt(21 - 1) + 1);
    }

    /**
     * This returns the random number when the Hero comes across a pit in a room
     * to subtract the health from
     *
     * @return a number of HitPoints
     */
    public int getMyHealthToBeDamaged() {
        return myHealthToBeDamaged;
    }
}
