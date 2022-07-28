package model.RoomItemComponents.RoomItems;

import java.util.Random;

public class Pit extends RoomItem{

    /** The amount of HitPoints it will subtract. */
    private int myHitPoint;

    /**
     * Constructor that will also generate a random HitPoint number
     */
    public Pit() {
        super();
        getRandomHitPoint();
    }

    /**
     * This will generate a random number of HitPoint each time it's called
     * then returns that number when the Hero comes across a pit in a room
     *
     * @return a number of HitPoints
     */
    public int doDamage() {
        getRandomHitPoint();
        return myHitPoint;
    }

    /**
     * Randomly get a number between 1-20 and negates it and sets it to myHitPoint.
     */
    private void getRandomHitPoint() {
        Random rand = new Random();
        myHitPoint = -1 * (rand.nextInt(21 - 1) + 1);
    }
}
