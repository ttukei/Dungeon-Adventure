package model.DungeonComponents;

import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonComponents.DataTypes.Coordinates;
import model.RoomItemComponents.*;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import org.sqlite.core.CoreDatabaseMetaData;


import java.util.ArrayList;
import java.util.LinkedList;

public class Room {

    private RoomsOfInterest myRoomType;

    private int myNumberOfDoors;

    private LinkedList<Doors> myRoomDoors;

    private LinkedList<RoomItem> myRoomItems;

    private LinkedList<Monster> myRoomMonsters;

    private Coordinates myRoomCoordinates;

    private boolean myMonsterFlag;

    Room(LinkedList<Doors> theRoomDoors, Coordinates theCords) {
        myRoomType = null;
        initializeFields(theRoomDoors, theCords);
    }

    Room(RoomsOfInterest TYPE_OF_ROOM, LinkedList<Doors> theRoomDoors, Coordinates theCords) {
        myRoomType = TYPE_OF_ROOM;
        initializeFields(theRoomDoors, theCords);
    }

    private void initializeFields(LinkedList<Doors> theRoomDoors, Coordinates theCords){
        myRoomDoors = new LinkedList<>();
        myRoomDoors.addAll(theRoomDoors);
        myRoomItems = new LinkedList<>();
        myRoomMonsters = new LinkedList<>();
        myRoomCoordinates = theCords;
        setMyMonsterFlag(false);
    }

    public boolean getMyMonsterFlag() {
        return myMonsterFlag;
    }

    public void setMyMonsterFlag(boolean myMonsterFlag) {
        this.myMonsterFlag = myMonsterFlag;
    }

    public String getRoomDoorsAsString(){
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

    public String getUserFriendlyStringOfDoors(){
        if (myRoomDoors.isEmpty()){
            return "";
        }
        StringBuilder stringOfDoors = new StringBuilder();
        for(Doors door : myRoomDoors) {
            if (!stringOfDoors.isEmpty()) {
                stringOfDoors.append(", ");
            }
            switch(door){
                case NORTHDOOR -> stringOfDoors.append("north");
                case EASTDOOR -> stringOfDoors.append("east");
                case SOUTHDOOR -> stringOfDoors.append("south");
                case WESTDOOR -> stringOfDoors.append("west");
            }
        }
        return stringOfDoors.toString();
    }


    public String getAnnouncement(){
        StringBuilder roomAnnouncement = new StringBuilder();
        roomAnnouncement.append("You are in a room with four stone walls and a dirt floor");

        /* ANNOUNCE MONSTERS */

        for (Monster monster : myRoomMonsters){
            roomAnnouncement.append(monster.getMyAnnouncement());
        }

        /* ANNOUNCE DOORS */

        if (myRoomDoors.isEmpty()){
            roomAnnouncement.append("\nThere are no doors! How did this happen?");
        } else if (myRoomDoors.size() > 1){
            roomAnnouncement.append("\nThere are doors to the ");
        } else {
            roomAnnouncement.append("\nThere is a door to the ");
        }
        roomAnnouncement.append(getUserFriendlyStringOfDoors());

        /* ANNOUNCE ITEMS */

        if (!myRoomItems.isEmpty()){
            roomAnnouncement.append("\nYou see a glint of something valuable!");
        }

        return roomAnnouncement.toString();
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

    public boolean contiansRoomItem() {
        return myRoomItems.size() > 0;
    }

    public boolean containsPotition() {
        for (RoomItem roomItem: myRoomItems) {
            if (roomItem.getClass() == HealthPotion.class || roomItem.getClass() == VisionPotion.class) {
                return true;
            }
        }
        return false;
    }

    public boolean containsPit() {
        for (RoomItem roomItem: myRoomItems) {
            if (roomItem.getClass() == Pit.class) {
                return true;
            }
        }
        return false;
    }

    public RoomsOfInterest getRoomType() {
        if(myRoomType == null){
//            System.out.println("This is a normal room!");
        }
        return myRoomType;
    }

    public Coordinates getRoomCords(){
        return myRoomCoordinates;
    }

}
