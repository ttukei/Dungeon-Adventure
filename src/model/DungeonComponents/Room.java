package model.DungeonComponents;

import model.DataTypes.Coordinates;
import model.RoomItemComponents.RoomItems.RoomItem;

import java.util.ArrayList;
import java.util.LinkedList;

public class Room {

    private int myNumberOfDoors;

    private LinkedList<Doors> roomDoors;

    private Coordinates myCoordinates;

    private ArrayList<RoomItem> myRoomItems;

    Room(LinkedList<Doors> theRoomDoors) {
        roomDoors = theRoomDoors;
    }

    Room(RoomsOfInterest TYPE_OF_ROOM, LinkedList<Doors> doorsToBuild) {

    }

    public String getDoors(){
        return roomDoors.toString();
    }

    public boolean hasNorthDoor() {
        return roomDoors.contains(Doors.NORTHDOOR);
    }

    public boolean hasEastDoor() {
        return roomDoors.contains(Doors.EASTDOOR);
    }

    public boolean hasSouthDoor() {
        return roomDoors.contains(Doors.SOUTHDOOR);
    }

    public boolean hasWestDoor() {
        return roomDoors.contains(Doors.WESTDOOR);
    }

}
