package model.RoomItemComponents;

import java.util.Random;

public class HealthPotion extends RoomItem{

    /**
     * The amount of Health to be regained.
     */
    private int myHealthToBeRegained;

    /**
     * Constructor that will also generate a random HitPoint number
     */
    public HealthPotion() {
        super("Health");
        this.myHealthToBeRegained = getRandomHitPoint();
        setType(RoomItems.HEALTH_POTION);
    }

    /**
     * Randomly get a number between 5-15 and sets it to myHitPoint
     */
    private int getRandomHitPoint() {
        Random rand = new Random();
        return rand.nextInt(16 - 5) + 5;
    }

    /**
     * This returns the health points that was randomly generated
     *
     * @return the random number from 5-15
     */
    public int getMyHealthToBeRegained() {
        return myHealthToBeRegained;
    }
}
