package model.DungeonComponents;

import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonComponents.DataTypes.Coordinates;
import model.RoomItemComponents.RoomItem;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import model.RoomItemComponents.RoomItems;


import java.util.ArrayList;
import java.util.LinkedList;

public class Room {

    private int myNumberOfDoors;

    private LinkedList<Doors> roomDoors;

    private LinkedList<RoomItem> roomInventory;

    private LinkedList<Monster> roomMonsters;

    private Coordinates myCoordinates;

    private ArrayList<RoomItem> myRoomItems;

    Room(LinkedList<Doors> theRoomDoors) {
        initializeFields(theRoomDoors);
    }

    Room(RoomsOfInterest TYPE_OF_ROOM, LinkedList<Doors> theRoomDoors) {

        initializeFields(theRoomDoors);
    }

    private void initializeFields(LinkedList<Doors> theRoomDoors){
        roomDoors = new LinkedList<>();
        roomDoors.addAll(theRoomDoors);
        roomInventory = new LinkedList<>();
        roomMonsters = new LinkedList<>();
    }

    public String getDoors(){
        return roomDoors.toString();
    }

    public boolean hasDoor(Doors door){
        return roomDoors.contains(door);
    }

    public void addItemsToRoom(LinkedList<RoomItem> theItemsToAdd){
        roomInventory.addAll(theItemsToAdd);
    }

    public void addMonstersToRoom(LinkedList<Monsters> theMonstersToAdd){
        MonsterFactory factory = new MonsterFactory();
        for (Monsters monster : theMonstersToAdd){
            roomMonsters.add(factory.getMonster(monster));
        }
    }

    public void addItemsToRoom(RoomItem theItemToAdd){
        roomInventory.add(theItemToAdd);
    }

    public void addMonstersToRoom(Monsters theMonstersToAdd){
        MonsterFactory factory = new MonsterFactory();
        roomMonsters.add(factory.getMonster(theMonstersToAdd));
    }

    public String toString(){
        StringBuilder roomStringBuilder = new StringBuilder();
        roomStringBuilder.append("You are in a room with four stone walls and a dirt floor\n");
        for (Monster monster : roomMonsters){
            roomStringBuilder.append(monster.getMyAnnouncement());
        }
        return roomStringBuilder.toString();
    }

}
