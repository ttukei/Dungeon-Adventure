package model.DungeonComponents;

import model.DungeonComponents.DataTypes.Coordinates;
import model.RoomItemComponents.RoomItems.RoomItem;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import model.RoomItemComponents.RoomItems.RoomItems;


import java.util.ArrayList;
import java.util.LinkedList;

public class Room {

    private int myNumberOfDoors;

    private LinkedList<Doors> roomDoors;

    private LinkedList<RoomItems> roomInventory;

    private LinkedList<Monsters> roomMonsters;

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

    public void addItemsToRoom(LinkedList<RoomItems> theItemsToAdd){
        for (RoomItems item : theItemsToAdd){
            roomInventory.add(item);
        }
    }

    public void addMonstersToRoom(LinkedList<Monsters> theMonstersToAdd){
        for (Monsters monster : theMonstersToAdd){
            roomMonsters.add(monster);
        }
    }

}
