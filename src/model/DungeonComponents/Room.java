package model.DungeonComponents;

import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonComponents.DataTypes.Coordinates;
import model.RoomItemComponents.*;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import static controller.Handler.*;


import java.io.Serializable;
import java.util.LinkedList;

public class Room implements Serializable {

    private RoomsOfInterest myRoomType;

    private int myNumberOfDoors;

    private LinkedList<Doors> myRoomDoors;

    private LinkedList<RoomItem> myRoomItems;

    private LinkedList<Integer> myRoomMonsters;

    private Coordinates myRoomCoordinates;

    private boolean myMonsterFlag;

    private boolean myRevealed;

    Room(final LinkedList<Doors> theRoomDoors, final Coordinates theCords) {
        myRoomType = null;
        initializeFields(theRoomDoors, theCords);
    }

    Room(final RoomsOfInterest TYPE_OF_ROOM, final LinkedList<Doors> theRoomDoors, final Coordinates theCords) {
        myRoomType = TYPE_OF_ROOM;
        initializeFields(theRoomDoors, theCords);
        switch (myRoomType){
            case ABSTRACTION    ->  addPillarToRoom(PillarsOO.ABSTRACTION);
            case ENCAPSULATION  ->  addPillarToRoom(PillarsOO.ENCAPSULATION);
            case INHERITANCE    ->  addPillarToRoom(PillarsOO.INHERITANCE);
            case POLYMORPHISM   ->  addPillarToRoom(PillarsOO.POLYMORPHISM);
        }
    }

    private void initializeFields(final LinkedList<Doors> theRoomDoors, final Coordinates theCords){
        myRoomDoors = new LinkedList<>();
        myRoomDoors.addAll(theRoomDoors);
        myRoomItems = new LinkedList<>();
        myRoomMonsters = new LinkedList<>();
        myRoomCoordinates = theCords;
        myRevealed = false;
        setMyMonsterFlag(false);
    }

    public boolean getMyMonsterFlag() {
        return myMonsterFlag;
    }

    public void setMyMonsterFlag(final boolean myMonsterFlag) {
        this.myMonsterFlag = myMonsterFlag;
    }

    public String getRoomDoorsAsString(){
        return myRoomDoors.toString();
    }

    public boolean hasDoor(final Doors door){
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

    public void addItemToRoom(final RoomItems theItemToAdd){
        switch(theItemToAdd){
            case HEALTH_POTION  -> myRoomItems.add(new HealthPotion());
            case VISION_POTION  -> myRoomItems.add(new VisionPotion());
            case PIT            -> myRoomItems.add(new Pit());
        }
    }

    public void addPillarToRoom(final PillarsOO thePillarToAdd){
        switch(thePillarToAdd){
            case ABSTRACTION    -> myRoomItems.add(new PillarOO(PillarsOO.ABSTRACTION));
            case ENCAPSULATION  -> myRoomItems.add(new PillarOO(PillarsOO.ENCAPSULATION));
            case INHERITANCE    -> myRoomItems.add(new PillarOO(PillarsOO.INHERITANCE));
            case POLYMORPHISM   -> myRoomItems.add(new PillarOO(PillarsOO.POLYMORPHISM));

        }
    }

    public void addMonsterToRoom(final Monsters theMonsterToAdd){
        MonsterFactory factory = new MonsterFactory();
        Monster newMonster = factory.getMonster(theMonsterToAdd);
        int index = getHandler().addObject(newMonster);
        myRoomMonsters.add(index);

        setMyMonsterFlag(true);
    }

    public Monster getMonsterFromRoom() {
        int id = myRoomMonsters.size() > 0 ? myRoomMonsters.getFirst() : -1;
        if (id > -1){
            return (Monster) getHandler().getObject(id);
        }
        return null;
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
            stringOfDoors.append(getUserFriendlyDoor(door));
        }
        return stringOfDoors.toString();
    }

    public static String getUserFriendlyDoor(final Doors door) {
        String doorString = "not a door";
        switch(door){
            case NORTHDOOR  ->  doorString = "north";
            case EASTDOOR   ->  doorString = "east";
            case SOUTHDOOR  ->  doorString = "south";
            case WESTDOOR   ->  doorString = "west";
        }
        return doorString;
    }

    public String printRoom(){
        StringBuilder result = new StringBuilder("\nCurrent Room:\n");

        /* DOOR ICONS */

        String northDoorIcon = hasDoor(Doors.NORTHDOOR) ? "[-]" : "###";
        String eastDoorIcon = hasDoor(Doors.EASTDOOR) ? "[|]" : "###";
        String southDoorIcon = hasDoor(Doors.SOUTHDOOR) ? "[-]" : "###";
        String westDoorIcon = hasDoor(Doors.WESTDOOR) ? "[|]" : "###";

        /* CENTER ICON */

        String centerIcon = getCenterIcon();

        /* BUILD STRING */

        result.append("###" + northDoorIcon + "###\n");
        result.append(westDoorIcon + " " + centerIcon + " " + eastDoorIcon + "\n");
        result.append("###" + southDoorIcon + "###\n");

        return result.toString();

    }

    String getCenterIcon() {
        String centerIcon = " ";
        if (getRoomType() != null){
            switch (getRoomType()){
                case ENTRANCE       -> centerIcon = "e";
                case ABSTRACTION    -> centerIcon = "A";
                case ENCAPSULATION  -> centerIcon = "E";
                case INHERITANCE    -> centerIcon = "I";
                case POLYMORPHISM   -> centerIcon = "P";
                case EXIT           -> centerIcon = "^";
            }
        } else if (myRoomItems.size() > 1){
            centerIcon = "M";
        } else if (containsRoomItem(RoomItems.PIT)){
            centerIcon = "X";
        } else if (containsRoomItem(RoomItems.HEALTH_POTION)){
            centerIcon = "h";
        } else if (containsRoomItem(RoomItems.VISION_POTION)){
            centerIcon = "v";
        }
        return centerIcon;
    }

    public String getAnnouncement(){
        StringBuilder roomAnnouncement = new StringBuilder();
        roomAnnouncement.append("You are in a room with four stone walls and a dirt floor");

        /* ANNOUNCE MONSTERS */

        for (int monsterID : myRoomMonsters){
            Monster monster = (Monster) getHandler().getObject(monsterID);
            if (monster != null){
                roomAnnouncement.append(monster.getMyAnnouncement());
            }
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

    public boolean containsRoomItem() {
        return myRoomItems.size() > 0;
    }

    public boolean containsRoomItem(RoomItems theTypeOfItem) {
        for (RoomItem item : getMyRoomItems()){
            if (item.getType() == theTypeOfItem){
                return true;
            }
        }
        return false;
    }

    public boolean containsPotion() {
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

    public boolean isRevealed(){
        return myRevealed;
    }

    public void reveal(){
        myRevealed = true;
    }

}
