package model.RoomItemComponents.RoomItems;

import java.util.Random;

public class HealthPotion extends RoomItem{

    /** The amount of HitPoints potion will heal. */
    private int myHitPoint;

    /**
     * Constructor that will also generate a random HitPoint number
     */
    public HealthPotion() {
        super();
        getRandomHitPoint();
    }

    /**
     * This will generate a random number of HitPoint each time it's called
     * then returns that number when the Hero uses it to increase health
     *
     * @return a number of HitPoints
     */
    public int usePotion() {
        getRandomHitPoint();
        return myHitPoint;
    }

    /**
     * Randomly get a number between 5-15 and sets it to myHitPoint
     */
    private void getRandomHitPoint() {
        Random rand = new Random();
        myHitPoint = rand.nextInt(16 - 5) + 5;
    }

    public int getMyHitPoint() {
        return myHitPoint;
    }
}
