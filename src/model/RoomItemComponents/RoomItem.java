package model.RoomItemComponents;

import model.DungeonObject;

import java.util.Random;

public abstract class RoomItem extends DungeonObject {

    /** The 10% possibility of an item being in a room. */
    public static final int PLACE_ITEM_CHANCE = 1;

    /** The name of the RoomItem. */
    public String myName;

    public RoomItem(String theName) {
        super();
        myName = theName;
    }

    /**
     * Places an item in the room
     */
    public void placeItem() {
        if (!checkDoor() && getChanceToPlace()) {
            //TODO figure out how to place item in dungeon
        }
    }

    /**
     * Checks if the given room has a door or not
     *
     * Will be passed a room[][] not decided on it
     * @return a boolean if a room has a door(true) or not(false)
     */
    private boolean checkDoor() {
        //TODO need door code to check if room has a door
        return false;
    }

    /**
     * Randomly gets a number from 1-10 and if it's a 1 which is a
     * 10% chance it will return true
     *
     * @return a boolean on whether an item is randomly chosen or not
     */
    private boolean getChanceToPlace() {
        Random rand = new Random();
        int chance = rand.nextInt(11 - 1) + 1;
        boolean result = false;
        if (chance == PLACE_ITEM_CHANCE) {
            result = true;
        }
        return result;
    }
}
