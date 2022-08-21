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

    /**
     * Constructing a normal room
     * @param theRoomDoors doors that the room will have
     * @param theCords the room's location in the grid
     */
    Room(final LinkedList<Doors> theRoomDoors, final Coordinates theCords) {
        myRoomType = null;
        initializeFields(theRoomDoors, theCords);
    }

    /**
     * Constructing a special room
     * @param TYPE_OF_ROOM a room of interest important to the victory conditions
     * @param theRoomDoors doors that the room will have
     * @param theCords the room's location in the grid
     */
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

    /**
     * Used by constructor to share common initialization routines between overloaded methods.
     * @param theRoomDoors doors that the room will have
     * @param theCords the room's location in the grid
     */
    private void initializeFields(final LinkedList<Doors> theRoomDoors, final Coordinates theCords){
        myRoomDoors = new LinkedList<>();
        myRoomDoors.addAll(theRoomDoors);
        myRoomItems = new LinkedList<>();
        myRoomMonsters = new LinkedList<>();
        myRoomCoordinates = theCords;
        myRevealed = false;
        setMyMonsterFlag(false);
    }

    /**
     *
     * @return whether room has monster
     */
    public boolean getMyMonsterFlag() {
        return myMonsterFlag;
    }

    /**
     *
     * @param myMonsterFlag whether room has monster
     */
    public void setMyMonsterFlag(final boolean myMonsterFlag) {
        this.myMonsterFlag = myMonsterFlag;
    }

    /**
     *
     * @return a string of all the doors in the room.
     */
    public String getRoomDoorsAsString(){
        return myRoomDoors.toString();
    }

    /**
     *
     * @param theDoor The door in question
     * @return whether the room contains the door in question
     */
    public boolean hasDoor(final Doors theDoor){
        return myRoomDoors.contains(theDoor);
    }

    /**
     *
     * @param theItemToAdd Enum representing the desired type of item to add
     */
    public void addItemToRoom(final RoomItems theItemToAdd){
        switch(theItemToAdd){
            case HEALTH_POTION  -> myRoomItems.add(new HealthPotion());
            case VISION_POTION  -> myRoomItems.add(new VisionPotion());
            case PIT            -> myRoomItems.add(new Pit());
        }
    }

    /**
     * Uses Enum to select a particular pillar and add it to the room
     * @param thePillarToAdd Enum representing type of pillar of OO to add to the room
     */
    public void addPillarToRoom(final PillarsOO thePillarToAdd){
        switch(thePillarToAdd){
            case ABSTRACTION    -> myRoomItems.add(new PillarOO(PillarsOO.ABSTRACTION));
            case ENCAPSULATION  -> myRoomItems.add(new PillarOO(PillarsOO.ENCAPSULATION));
            case INHERITANCE    -> myRoomItems.add(new PillarOO(PillarsOO.INHERITANCE));
            case POLYMORPHISM   -> myRoomItems.add(new PillarOO(PillarsOO.POLYMORPHISM));

        }
    }

    /**
     * Uses Enum and MonsterFactory to generate a monster in the room.
     * @param theMonsterToAdd Enum representing the monster desired
     */
    public void addMonsterToRoom(final Monsters theMonsterToAdd){
        MonsterFactory factory = new MonsterFactory();
        Monster newMonster = factory.getMonster(theMonsterToAdd);
        int index = getHandler().addObject(newMonster);
        myRoomMonsters.add(index);

        setMyMonsterFlag(true);
    }

    /**
     * List of id's of Monsters are kept in the room so that the particular room can be
     * associated with a particular monster in the Handler. Kept as a list with a future
     * version of the game in mind where more than one monster might be in a room.
     * @return Reference to Handler's copy of the Monster in the room
     */
    public Monster getMonsterFromRoom() {
        int id = myRoomMonsters.size() > 0 ? myRoomMonsters.getFirst() : -1;
        if (id > -1){
            return (Monster) getHandler().getObject(id);
        }
        return null;
    }

    /**
     * Retrieves a pretty representation of the doors that the player should see.
     * @return String of comma seperated doors in the room
     */
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

    /**
     * Converts to a natural language string showing the direction of the door
     * @param door Enum of particular type of door
     * @return String of direction door represents
     */
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

    /**
     * ASCII character display of the room
     * @return String representing the characteristics of the room, walls, doors, items in center
     */
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

    /**
     * A key detailing what's in the room
     * @return A string made up of a single character determined by what's in the room.
     */
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

    /**
     * A message to send the player when the hero enters a room.
     * @return String built containing details about the room.
     */
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

    /**
     *
     * @return whether any pillars are in the room
     */
    public boolean containsPillar() {
        for (RoomItem roomItem : myRoomItems) {
            if (roomItem.getClass() == PillarOO.class) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return a list of the items in the room
     */
    public LinkedList<RoomItem> getMyRoomItems() {
        return this.myRoomItems;
    }

    /**
     *
     * @return if there are any items in the room at all
     */
    public boolean containsRoomItem() {
        return myRoomItems.size() > 0;
    }

    /**
     * @param theTypeOfItem RoomItems Enum representing the type in question
     * @return if a certain type of item is in the room
     */
    public boolean containsRoomItem(final RoomItems theTypeOfItem) {
        for (RoomItem item : getMyRoomItems()){
            if (item.getType() == theTypeOfItem){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return whether the room has a potion
     */
    public boolean containsPotion() {
        for (RoomItem roomItem: myRoomItems) {
            if (roomItem.getClass() == HealthPotion.class || roomItem.getClass() == VisionPotion.class) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return whether room contains a pit
     */
    public boolean containsPit() {
        for (RoomItem roomItem: myRoomItems) {
            if (roomItem.getClass() == Pit.class) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return whether the room is of type in question
     */
    public RoomsOfInterest getRoomType() {
        if(myRoomType == null){
//            System.out.println("This is a normal room!");
        }
        return myRoomType;
    }

    /**
     *
     * @return the Coordinates representing room's location
     */
    public Coordinates getRoomCords(){
        return myRoomCoordinates;
    }

    /**
     *
     * @return whether the room has been revealed
     */
    public boolean isRevealed(){
        return myRevealed;
    }

    /**
     * flags the room viewable for the player
     */
    public void reveal(){
        myRevealed = true;
    }

}
