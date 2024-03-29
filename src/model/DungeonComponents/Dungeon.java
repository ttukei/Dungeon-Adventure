package model.DungeonComponents;

import static controller.DungeonAdventure.*;

import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import model.DungeonComponents.DataTypes.Coordinates;
import model.RoomItemComponents.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class Dungeon {

    private static Dungeon uniqueInstanceOfDungeon;
    /**
     * Holds a 2D Array. Each element is one of the Rooms of the dungeon. Array location
     * corresponds to the coordinate of the room.
     */
    private static Room[][] myDungeonGrid;
    private static final int MIN_X = 0;
    private static int MAX_X;
    private static final int MIN_Y = 0;
    private static int MAX_Y;

    /* CONSTRUCTORS */

    /**
     * Builds dungeon using createMaze, represented as myDungeonGrid, a 2D
     * array with all the rooms in the dungeon.
     * @param theWidth specified grid width
     * @param theHeight specified grid height
     */
    private Dungeon(final int theWidth, final int theHeight) {

//        System.out.println("Building New Dungeon");
        myDungeonGrid = new Room[theHeight][theWidth];
        MAX_X = myDungeonGrid.length - 1;
        MAX_Y = myDungeonGrid[0].length - 1;
        createMaze();
    }

    /* GET SINGLETON */

    /**
     * Singleton accessor for Dungeon which generates the floor plan using createMaze().
     * @return the existing or new Singleton instance of Dungeon
     */
    public static synchronized Dungeon getDungeon(final int theWidth, final int theHeight){
        if (uniqueInstanceOfDungeon == null){
            uniqueInstanceOfDungeon = new Dungeon(theWidth, theHeight);
        }
        return uniqueInstanceOfDungeon;
    }

    /* OVERRIDDEN PUBLIC METHODS */

    /**
     * Used during development for algorithm visibility.
     * @return A list of features of the dungeon and each of the rooms within
     */
    @Override
    public String toString(){
        StringBuilder dungeonToString = new StringBuilder("");
        int roomNumber = 0;
        for (int i = 0; i < myDungeonGrid.length; i++){
            if (myDungeonGrid[i] != null){
                Room[] roomRow = myDungeonGrid[i];
                dungeonToString.append("Row ").append((char)('A' + i)).append("\n");            // Row Letter
                for (int j = 0; j < roomRow.length; j++){
                    Room currentRoom = myDungeonGrid[i][j];
                    if (currentRoom != null) {
                        dungeonToString.append("Room ").append(roomNumber++                 // Room Number
                        ).append(", at (").append(i).append(", ").append(j                  // Coordinates
                        ).append(") has doors: ").append(currentRoom.getRoomDoorsAsString() // Doors
                        ).append("\n");                                                     // New Line
                    } else{
                        dungeonToString.append("Empty Room at (").append(i).append(", ").append(j).append(")\n");
                    }
                }
            } else {
                dungeonToString.append("ERROR: No values in row").append(i).append("\n");
            }
        }
        return dungeonToString.toString();
    }

    /* PUBLIC METHODS */

    /**
     * Builds a map of the dungeon represented with ASCII characters
     * @return the String representing the dungeon map.
     */
    public String printDungeonMap(){
        StringBuilder result = new StringBuilder("Dungeon Map:\n");
        for (int i = 0; i < myDungeonGrid.length; i++){
            for (int j = 0; j < myDungeonGrid[i].length; j++){
                Room currentRoom = myDungeonGrid[i][j];
                if (currentRoom != null && currentRoom.isRevealed()){
//                if (currentRoom != null){
                    result.append("[" + getRoomMarker(new Coordinates(j,i)) + "]");
                } else{
                    result.append("###");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    /* PUBLIC STATIC METHODS */

    /**
     * Can be checked anywhere in project.
     * @return Maximum X value for a tile coordinate (also represents 2D array index)
     */
    public static int getMAX_X(){
        return MAX_X;
    }

    /**
     * Can be checked anywhere in project.
     * @return Maximum Y value for a tile coordinate (also represents 2D array index)
     */
    public static int getMAX_Y(){
        return MAX_Y;
    }

    /**
     * Gets the room that the player is in based on the current coordinates. Depends on static
     * DungeonAdventure function getPlayerCoordinates().
     * @return the room the player is in.
     */
    public static Room getPlayersCurrentRoom(){
        try {
            int y = getPlayerCoordinates().getY();
            int x = getPlayerCoordinates().getX();
            return myDungeonGrid[y][x];
        } catch (NullPointerException e){
            System.out.println("Player's coordinates are not in a valid room");
            return null;
        }
    }

    /**
     * Used to request a particular special room.
     * @param theRoomOfInterest the Enum that the room to retrieved is marked.
     * @return the room that is marked with the Enum passed.
     */
    public static Room getRoomOfInterest(final RoomsOfInterest theRoomOfInterest){
        for (int i = 0; i < myDungeonGrid.length; i++) {
            for (int j = 0; j < myDungeonGrid[i].length; j++) {
                if (myDungeonGrid[i][j] != null && myDungeonGrid[i][j].getRoomType() == theRoomOfInterest) {
                    return myDungeonGrid[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Takes a room as an argument and calls the reveal function on every
     * room that has a door leading to it.
     * @param theRoom room to reveal the neighboring rooms of.
     */
    public static void revealRoomsOnOtherSideOfDoors(final Room theRoom){
        int roomY = theRoom.getRoomCords().getY();
        int roomX = theRoom.getRoomCords().getX();
        for(Doors door : Doors.values()){
            if (theRoom.hasDoor(door)){
                System.out.println("Rooms entered has " + door);
                switch (door){
                    case NORTHDOOR  ->  {
                        Room roomToCheck = myDungeonGrid[roomY - 1][roomX];
                        if (roomToCheck != null){
                            roomToCheck.reveal();
                        }
                    }
                    case EASTDOOR   ->  {
                        Room roomToCheck = myDungeonGrid[roomY][roomX + 1];
                        if (roomToCheck != null){
                            roomToCheck.reveal();
                        }
                    }
                    case SOUTHDOOR  ->  {
                        Room roomToCheck = myDungeonGrid[roomY + 1][roomX];
                        if (roomToCheck != null){
                            roomToCheck.reveal();
                        }
                    }
                    case WESTDOOR   ->  {
                        Room roomToCheck = myDungeonGrid[roomY][roomX - 1];
                        if (roomToCheck != null){
                            roomToCheck.reveal();
                        }
                    }
                }
            }
        }
    }

    /* PRIVATE METHODS */

    /**
     * Returns a character representation of the room, first if the players in it, then what items are in it.
     * @param theCords the Coordinates of the room to check
     * @return The icon representing the room's contents
     */
    private String getRoomMarker(final Coordinates theCords){
        if (theCords.isLocatedAtTheCoordinate(getPlayerCoordinates())){
            return "@";
        }
        return myDungeonGrid[theCords.getY()][theCords.getX()].getCenterIcon();
    }

    /**
     * Builds the dungeon as a random maze by starting in one tile, then randomly choosing a number of doors
     * and adding a queue of rooms to be generated for every door leading to an empty space.
     */
    private void createMaze(){

        final double CHANCE_FOR_MONSTER = 0.5;
        final double CHANCE_FOR_HEALTH_POTION = 0.25;
        final double CHANCE_FOR_VISION_POTION = 0.2;
        final double CHANCE_FOR_PIT = 0.15;

        // Linked list of the coordinates of rooms to make, randomly generate starting coordinates
        LinkedList<Coordinates> roomsToAdd = new LinkedList();

        int startX = ThreadLocalRandom.current().nextInt(MIN_X, MAX_X);
        int startY = ThreadLocalRandom.current().nextInt(MIN_Y, MAX_Y);
        double chanceForSpecialRoom = 0.4;

        // Linked List holding the RoomsOfInterest to add and then remove (checking it off) from list
        LinkedList<RoomsOfInterest> roomsNeeded = new LinkedList<>();
        for (RoomsOfInterest room : RoomsOfInterest.values()){
            roomsNeeded.add(room);
        }
        roomsToAdd.add(new Coordinates(startX, startY));

        // Starts with one room but adds more in as rooms are generated with doors
        while (!roomsToAdd.isEmpty()){

            Coordinates newRoomCoordinates = roomsToAdd.poll();
            System.out.println("\nNew Room at: " + newRoomCoordinates);
            final int NEW_ROOM_X = newRoomCoordinates.getX();
            final int NEW_ROOM_Y = newRoomCoordinates.getY();
            final double roomRoll = ThreadLocalRandom.current().nextDouble();

            // Get the neighbors
            LinkedList<Coordinates> newRoomNeighbors = whoAreMyNeighbors(newRoomCoordinates);

            // Make a list to prevent attempting to create rooms where there are already neighbors
            LinkedList<Coordinates> roomsAvailable = new LinkedList<>();

            // Record the location of the adjacent coordinates
            final Coordinates northCord = new Coordinates(NEW_ROOM_X, NEW_ROOM_Y-1);
            final Coordinates eastCord = new Coordinates(NEW_ROOM_X+1, NEW_ROOM_Y);
            final Coordinates southCord = new Coordinates(NEW_ROOM_X, NEW_ROOM_Y+1);
            final Coordinates westCord = new Coordinates(NEW_ROOM_X-1, NEW_ROOM_Y);


            roomsAvailable.add(northCord);
            roomsAvailable.add(eastCord);
            roomsAvailable.add(southCord);
            roomsAvailable.add(westCord);
            for (Coordinates neighbor : newRoomNeighbors){
                roomsAvailable.removeIf(cord -> cord.isLocatedAtTheCoordinate(neighbor));
            }
            for (Coordinates roomToAdd : roomsToAdd){
                roomsAvailable.removeIf(cord -> cord.isLocatedAtTheCoordinate(roomToAdd));
            }
            roomsAvailable.removeIf(cord -> cord.isLocatedAtTheCoordinate(newRoomCoordinates));
//            System.out.println("Potential rooms at: " + roomsAvailable);

            // Get potential doors per four cardinal direction, add them to a list
            LinkedList<Doors> doorsAvailable = new LinkedList<>(Arrays.asList(Doors.values()));

            // Remove potential doors that lead to non-existent tiles
            removeDoorsToNowhere(NEW_ROOM_X, NEW_ROOM_Y, doorsAvailable);
            System.out.println("Doors available: " + doorsAvailable);

            // Add doors if there's already a door on the other side
            LinkedList<Doors> newRoomDoors = addDoorsCorrespondingToNeighbor(NEW_ROOM_X, NEW_ROOM_Y, newRoomNeighbors, doorsAvailable);

            // Generate doors to new rooms, adding those new rooms to the queue
            String stringOfNewRooms = generateDoorsToEmptyTiles(
                    roomsToAdd,
                    roomsNeeded,
                    roomsAvailable,
                    newRoomCoordinates,
                    doorsAvailable,
                    newRoomDoors    );
            System.out.println("New room doors added: " + newRoomDoors);
            System.out.println("Added Rooms To Build: " + stringOfNewRooms);

            /* ADD NEW ROOM, CHANCE TO BE SPECIAL ROOM */

            Room newRoom;
            if (roomRoll < chanceForSpecialRoom && !roomsNeeded.isEmpty()) {
                RoomsOfInterest specialRoom = roomsNeeded.remove();
                newRoom = new Room(specialRoom, newRoomDoors, newRoomCoordinates);
                chanceForSpecialRoom -= 0.05;
                if (specialRoom != RoomsOfInterest.ENTRANCE) {
                    newRoom.addMonsterToRoom(getRandomMonster());
                }
            } else {
                newRoom = new Room(newRoomDoors, newRoomCoordinates);
                chanceForSpecialRoom += 0.1;
                if (roomRoll < CHANCE_FOR_MONSTER){
                    newRoom.addMonsterToRoom(getRandomMonster());
                }
            }

            double healthPotRoll = ThreadLocalRandom.current().nextDouble();
            double visionPotRoll = ThreadLocalRandom.current().nextDouble();
            double pitRoll = ThreadLocalRandom.current().nextDouble();

            if (newRoom.getRoomType() != RoomsOfInterest.ENTRANCE){
                if (healthPotRoll < CHANCE_FOR_HEALTH_POTION){
                    newRoom.addItemToRoom(RoomItems.HEALTH_POTION);
                }
                if (visionPotRoll < CHANCE_FOR_VISION_POTION){
                    newRoom.addItemToRoom(RoomItems.VISION_POTION);
                }
                if (!newRoom.getMyMonsterFlag() && pitRoll < CHANCE_FOR_PIT){
                    newRoom.addItemToRoom(RoomItems.PIT);
                }
            }

            myDungeonGrid[NEW_ROOM_Y][NEW_ROOM_X] = newRoom;

        }

    }

    /** Generate 0-4 random doors, but only allow a dead end if there are still rooms to
     *  add or no more RoomsOfInterest needed. Pick from doorsAvailable at random to keep it
     *  organic.
     * @param theRoomsToAdd rooms will be added to the queue here.
     * @param theRoomsNeeded the special rooms still left to generate
     * @param theRoomsAvailable possible neighboring room tiles to create
     * @param doorsAvailable possible doors to generate for the new room
     * @param newRoomDoors list of doors to ultimately be added to the room later
     * @return list of the new rooms being added by this function
     */
    private String generateDoorsToEmptyTiles(
            final LinkedList<Coordinates> theRoomsToAdd,
            final LinkedList<RoomsOfInterest> theRoomsNeeded,
            final LinkedList<Coordinates> theRoomsAvailable,
            final Coordinates theNewRoomCords,
            final LinkedList<Doors> doorsAvailable,
            final LinkedList<Doors> newRoomDoors)
    {
        final int MINIMUM_DOORS = !theRoomsNeeded.isEmpty() ? 1 : 0;
//            final int MINIMUM_DOORS = 0;
        final int MAXIMUM_DOORS = doorsAvailable.size();
        final int numDoors;
        final int X = theNewRoomCords.getX();
        final int Y = theNewRoomCords.getY();
        if (MAXIMUM_DOORS > MINIMUM_DOORS) {
            numDoors = ThreadLocalRandom.current().nextInt(MINIMUM_DOORS, MAXIMUM_DOORS);
        } else {
            numDoors = MAXIMUM_DOORS;
        }
//            System.out.println("number of doors: " + numDoors);
        StringBuilder stringOfNewRooms = new StringBuilder();
        for (int i = 0; i < numDoors; i++){
            int indexOfDoorPicked = doorsAvailable.size() > 1 ?
                    ThreadLocalRandom.current().nextInt(0, doorsAvailable.size() - 1) : 0;
            Doors newDoor = doorsAvailable.remove(indexOfDoorPicked);
            newRoomDoors.add(newDoor);
//            System.out.println("new door: " + newDoor);
            switch (newDoor){
                case NORTHDOOR -> {
                    for (Coordinates theCord : theRoomsAvailable){
                        if (theCord.isLocatedAtTheCoordinate(new Coordinates(X, Y-1))){
                            System.out.println("case: " + newDoor);
                            System.out.println("cord: " + theCord);
                            theRoomsToAdd.add(theCord);
                        }
                    }
                }
                case EASTDOOR -> {
                    for (Coordinates theCord : theRoomsAvailable){
                        if (theCord.isLocatedAtTheCoordinate(new Coordinates(X+1, Y))){
                            System.out.println("case: " + newDoor);
                            System.out.println("cord: " + theCord);
                            theRoomsToAdd.add(theCord);
                        }
                    }
                }
                case SOUTHDOOR -> {
                    for (Coordinates theCord : theRoomsAvailable){
                        if (theCord.isLocatedAtTheCoordinate(new Coordinates(X, Y+1))){
                            System.out.println("case: " + newDoor);
                            System.out.println("cord: " + theCord);
                            theRoomsToAdd.add(theCord);
                        }
                    }
                }
                case WESTDOOR -> {
                    for (Coordinates theCord : theRoomsAvailable){
                        if (theCord.isLocatedAtTheCoordinate(new Coordinates(X-1, Y))){
                            System.out.println("case: " + newDoor);
                            System.out.println("cord: " + theCord);
                            theRoomsToAdd.add(theCord);
                        }
                    }
                }
            }
        }
        return stringOfNewRooms.toString();
    }

    /**
     * Creates a list of doors checking each of the neighboring tiles of a new room passed and adding in the
     * corresponding door for that the neighbor has. For example, if the neighbor to the East has a WestDoor, then it
     * adds in an EastDoor which represents the other side of that door.
     * @param theNewRoomX X coordinate of the room passed
     * @param theNewRoomY y coordinate of the room passed
     * @param theNewRoomNeighbors List of neighboring room coordinates
     * @param theDoorsAvailable List of possible doors in the new room
     * @return newRoomDoors - the list of doors to be added first to the new room
     */
    private LinkedList<Doors> addDoorsCorrespondingToNeighbor(
            final int theNewRoomX,
            final int theNewRoomY,
            final LinkedList<Coordinates> theNewRoomNeighbors,
            final LinkedList<Doors> theDoorsAvailable)
    {
        LinkedList<Doors> newRoomDoors = new LinkedList<>();
        for (Coordinates neighborCords : theNewRoomNeighbors){
            // yDiff determines if new room is south, north or on the
            // same row as neighbor with 1, -1, and 0 respectively.
            int yDiff = theNewRoomY - neighborCords.getY();
            Room neighbor = myDungeonGrid[neighborCords.getY()][neighborCords.getX()];

            /* NESTED SWITCH STATEMENT */

            switch (yDiff) {
                // New Room is south of neighbor
                case 1 -> {
                    if (neighbor.hasDoor(Doors.SOUTHDOOR)) {
                        newRoomDoors.add(Doors.NORTHDOOR);
                    }
                    theDoorsAvailable.remove(Doors.NORTHDOOR);
                }
                // New Room is north of neighbor
                case -1 -> {
                    if (neighbor.hasDoor(Doors.NORTHDOOR)) {
                        newRoomDoors.add(Doors.SOUTHDOOR);
                    }
                    theDoorsAvailable.remove(Doors.SOUTHDOOR);
                }
                // New Room is on the same Row as neighbor, now check column with nested switch
                case 0 -> {
                    // xDiff determines if new room is east, west or on the same
                    // column as neighbor with -1, 1, and 0 respectively.
                    int xDiff = theNewRoomX - neighborCords.getX();
                    switch (xDiff) {
                        // New Room is East of neighbor
                        case 1 -> {
                            if (neighbor.hasDoor(Doors.EASTDOOR)) {
                                newRoomDoors.add(Doors.WESTDOOR);
                            }
                            theDoorsAvailable.remove(Doors.WESTDOOR);
                        }
                        case -1 -> {
                            if (neighbor.hasDoor(Doors.WESTDOOR)) {
                                newRoomDoors.add(Doors.EASTDOOR);
                            }
                            theDoorsAvailable.remove(Doors.EASTDOOR);
                        }
                    }
                }
            }
        }
        return newRoomDoors;
    }

    /**
     * If a door would be out of bounds, remove it from the list passed in
     * @param theNewRoomX X coordinate of the room passed
     * @param theNewRoomY Y coordinate of the room passed
     * @param theDoorsAvailable list to remove impossible doors from.
     */
    private void removeDoorsToNowhere(final int theNewRoomX, final int theNewRoomY, final LinkedList<Doors> theDoorsAvailable) {
        if (theNewRoomY <= MIN_Y){
            theDoorsAvailable.remove(Doors.NORTHDOOR);
        }
        if (theNewRoomX >= MAX_X){
            theDoorsAvailable.remove(Doors.EASTDOOR);
        }
        if (theNewRoomY >= MAX_Y){
            theDoorsAvailable.remove(Doors.SOUTHDOOR);
        }
        if (theNewRoomX <= MIN_X){
            theDoorsAvailable.remove(Doors.WESTDOOR);
        }
    }

    /**
     * Given a set of Coordinates, returns a list of all adjacent Coordinates already containing rooms.
     * @param theCoordinates Coordinates to check the neighbors of.
     * @return neighbors - a list of Coordinates containing rooms.
     */
    private LinkedList<Coordinates> whoAreMyNeighbors(final Coordinates theCoordinates){

        LinkedList<Coordinates> neighbors = new LinkedList<>();

        final int Y = theCoordinates.getY();
        final int X = theCoordinates.getX();

        // North
        if(Y > 0 && myDungeonGrid[Y-1][X] != null){
            neighbors.add(new Coordinates(X, Y-1));
        }
        // East
        if(X < MAX_X && myDungeonGrid[Y][X+1] != null){
            neighbors.add(new Coordinates(X+1, Y));
        }
        // South
        if(Y < MAX_X && myDungeonGrid[Y+1][X] != null){
            neighbors.add(new Coordinates(X, Y+1));
        }
        // West
        if(X > 0 && myDungeonGrid[Y][X-1] != null){
            neighbors.add(new Coordinates(X-1, Y));
        }

//        System.out.println(theCoordinates + " neighbors are: " + neighbors);

        return neighbors;

    }

    /**
     *
     * @return A monster selected at random from the Monsters Enum
     */
    private Monsters getRandomMonster (){
        int choice = ThreadLocalRandom.current().nextInt(0, Monsters.values().length);
        return Monsters.values()[choice];
    }

    /* MOCK DUNGEON */

    /**
     * Used for development to build the dungeon with all of its rooms manually instead of
     * using createMaze().
     */
    public Dungeon() {

//        System.out.println("Building New Dungeon");

        /* MIN AND MAX X, Y */

        MAX_X = 4;
        MAX_Y = 4;

        myDungeonGrid = new Room[][]{
                {   new Room(RoomsOfInterest.ENTRANCE, new LinkedList<>(List.of(Doors.EASTDOOR)), new Coordinates(0, 0)),
                        new Room(new LinkedList<>(List.of(Doors.EASTDOOR, Doors.SOUTHDOOR, Doors.WESTDOOR)), new Coordinates(1, 0)),
                        new Room(new LinkedList<>(List.of(Doors.WESTDOOR)), new Coordinates(2, 0)),
                        null,
                        null
                },
                {   null,
                        new Room(RoomsOfInterest.ABSTRACTION, new LinkedList<>(List.of(Doors.NORTHDOOR, Doors.SOUTHDOOR)), new Coordinates(1, 1)),
                        null,
                        new Room(RoomsOfInterest.POLYMORPHISM, new LinkedList<>(List.of(Doors.EASTDOOR, Doors.SOUTHDOOR)), new Coordinates(3,1)),
                        new Room(new LinkedList<>(List.of(Doors.WESTDOOR)), new Coordinates(4,1))
                },
                {   new Room(RoomsOfInterest.EXIT, new LinkedList<>(List.of(Doors.EASTDOOR)), new Coordinates(0,2)),
                        new Room(RoomsOfInterest.ENCAPSULATION, new LinkedList<>(List.of(Doors.NORTHDOOR, Doors.WESTDOOR, Doors.EASTDOOR)), new Coordinates(1,2)),
                        new Room(new LinkedList<>(List.of(Doors.WESTDOOR, Doors.EASTDOOR)), new Coordinates(2,2)),
                        new Room(new LinkedList<>(List.of(Doors.NORTHDOOR, Doors.WESTDOOR, Doors.SOUTHDOOR)), new Coordinates(3,2)),
                        null
                },
                {   null,
                        null,
                        new Room(new LinkedList<>(List.of(Doors.EASTDOOR, Doors.SOUTHDOOR)), new Coordinates(2,3)),
                        new Room(RoomsOfInterest.INHERITANCE, new LinkedList<>(List.of(Doors.NORTHDOOR, Doors.EASTDOOR, Doors.WESTDOOR)), new Coordinates(3,3)),
                        new Room(new LinkedList<>(List.of(Doors.WESTDOOR)), new Coordinates(4,3))
                },
                {   new Room(new LinkedList<>(List.of(Doors.EASTDOOR)), new Coordinates(0,4)),
                        new Room(new LinkedList<>(List.of(Doors.EASTDOOR, Doors.WESTDOOR)), new Coordinates(1,4)),
                        new Room(new LinkedList<>(List.of(Doors.NORTHDOOR, Doors.WESTDOOR)), new Coordinates(2,4)),
                        null,
                        null
                }
        };

//        myDungeonGrid[0][1].addMonsterToRoom(Monsters.SKELETON);
        myDungeonGrid[1][1].addItemToRoom(RoomItems.VISION_POTION);
        myDungeonGrid[1][1].addMonsterToRoom(Monsters.SKELETON);
        myDungeonGrid[0][2].addItemToRoom(RoomItems.HEALTH_POTION);
        myDungeonGrid[2][1].addItemToRoom(RoomItems.PIT);
//        myDungeonGrid[0][2].addMonsterToRoom(Monsters.SKELETON);
//        myDungeonGrid[1][1].addMonsterToRoom(Monsters.SKELETON);
//        System.out.println(this);
    }

    /**
     * Singleton accessor for Dungeon which forgoes generating the
     * floor plan randomly for early iterations of game.
     * @return the existing or new Singleton instance of Dungeon
     */
    public static synchronized Dungeon getDungeon(){
        if (uniqueInstanceOfDungeon == null){
            uniqueInstanceOfDungeon = new Dungeon();
        }
        return uniqueInstanceOfDungeon;
    }

}
