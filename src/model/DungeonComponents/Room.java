package model.DungeonComponents;

import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonComponents.DataTypes.Coordinates;
import model.RoomItemComponents.PillarOO;
import model.RoomItemComponents.RoomItem;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;


import java.util.ArrayList;
import java.util.LinkedList;

public class Room {

    private RoomsOfInterest myRoomType;

    private int myNumberOfDoors;

    private LinkedList<Doors> myRoomDoors;

    private LinkedList<RoomItem> myRoomItems;

    private LinkedList<Monster> myRoomMonsters;

    private Coordinates myCoordinates;

    private boolean myMonsterFlag;

    Room(LinkedList<Doors> theRoomDoors) {
        myRoomType = null;
        initializeFields(theRoomDoors);
    }

    Room(RoomsOfInterest TYPE_OF_ROOM, LinkedList<Doors> theRoomDoors) {
        myRoomType = TYPE_OF_ROOM;
        initializeFields(theRoomDoors);
    }

    private void initializeFields(LinkedList<Doors> theRoomDoors){
        myRoomDoors = new LinkedList<>();
        myRoomDoors.addAll(theRoomDoors);
        myRoomItems = new LinkedList<>();
        myRoomMonsters = new LinkedList<>();
        setMyMonsterFlag(false);
    }

    public boolean getMyMonsterFlag() {
        return myMonsterFlag;
    }

    public void setMyMonsterFlag(boolean myMonsterFlag) {
        this.myMonsterFlag = myMonsterFlag;
    }

    public String getDoors(){
        return myRoomDoors.toString();
    }

    public boolean hasDoor(Doors door){
        return myRoomDoors.contains(door);
    }

//    public void addItemsToRoom(LinkedList<RoomItem> theItemsToAdd){
//        myRoomItems.addAll(theItemsToAdd);
//    }
//
//    public void addMonstersToRoom(LinkedList<Monsters> theMonstersToAdd){
//        MonsterFactory factory = new MonsterFactory();
//        for (Monsters monster : theMonstersToAdd){
//            roomMonsters.add(factory.getMonster(monster));
//        }
//        setMyMonsterFlag(true);
//    }

    public void addItemToRoom(RoomItem theItemToAdd){
        myRoomItems.add(theItemToAdd);
    }

    public void addMonsterToRoom(Monsters theMonsterToAdd){
        MonsterFactory factory = new MonsterFactory();
        Monster newMonster = factory.getMonster(theMonsterToAdd);
        myRoomMonsters.add(newMonster);
        controller.Handler.getHandler().addObject(newMonster);

        setMyMonsterFlag(true);
    }

    public Monster getMonsterFromRoom() {
        return myRoomMonsters.getFirst();
    }


    public String toString(){
        StringBuilder roomStringBuilder = new StringBuilder();
        roomStringBuilder.append("You are in a room with four stone walls and a dirt floor\n");
        for (Monster monster : myRoomMonsters){
            roomStringBuilder.append(monster.getMyAnnouncement());
        }
        for (RoomItem item : myRoomItems) {
        }
        return roomStringBuilder.toString();
    }

    public boolean containsPillar() {
        for (RoomItem roomItem : myRoomItems) {
            if (roomItem.getClass() == PillarOO.class) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<RoomItem> getMyRoomItems() {
        return this.myRoomItems;
    }
}
