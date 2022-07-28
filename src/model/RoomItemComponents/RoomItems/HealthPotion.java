package model.RoomItemComponents.RoomItems;

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
        super();
        this.myHealthToBeRegained = getRandomHitPoint();
    }

    /**
     * Randomly get a number between 5-15 and sets it to myHitPoint
     */
    private int getRandomHitPoint() {
        Random rand = new Random();
        return rand.nextInt(16 - 5) + 5;
    }

    public int getMyHealthToBeRegained() {
        return myHealthToBeRegained;
    }
}
