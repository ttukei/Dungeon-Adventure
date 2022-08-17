package model.DungeonComponents;

import static controller.DungeonAdventure.*;

import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import model.DungeonComponents.DataTypes.Coordinates;
import model.RoomItemComponents.*;

import java.util.ArrayList;
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
    private final double CHANCE_FOR_MONSTER = 0.5;
    private final double CHANCE_FOR_HEALTH_POTION = 0.25;
    private final double CHANCE_FOR_VISION_POTION = 0.1;
    private final double CHANCE_FOR_PIT = 0.15;

    /* CONSTRUCTORS */

    private Dungeon(int theWidth, int theHeight) {

//        System.out.println("Building New Dungeon");
        myDungeonGrid = new Room[theHeight][theWidth];
        MAX_X = myDungeonGrid.length - 1;
        MAX_Y = myDungeonGrid[0].length - 1;
        createMaze();
    }

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
        myDungeonGrid[1][1].addItemToRoom(RoomItems.HEALTH_POTION);
        myDungeonGrid[1][1].addMonsterToRoom(Monsters.SKELETON);
        myDungeonGrid[0][2].addItemToRoom(RoomItems.HEALTH_POTION);
        myDungeonGrid[2][1].addItemToRoom(RoomItems.PIT);
//        myDungeonGrid[0][2].addMonsterToRoom(Monsters.SKELETON);
//        myDungeonGrid[1][1].addMonsterToRoom(Monsters.SKELETON);
//        System.out.println(this);
    }

    /* GET SINGLETONS */

    /**
     * Singleton accessor for Dungeon which generates the floor plan using createMaze().
     * @return the existing or new Singleton instance of Dungeon
     */
    public static synchronized Dungeon getDungeon(int theWidth, int theHeight){
        if (uniqueInstanceOfDungeon == null){
            uniqueInstanceOfDungeon = new Dungeon(theWidth, theHeight);
        }
        return uniqueInstanceOfDungeon;
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

    /* OVERRIDDEN PUBLIC METHODS */

    /**
     *
     * @return
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

    public void printDungeonMap2(){
        System.out.println("Dungeon Map:");
        for (int i = 0; i < myDungeonGrid.length; i++){
            for (int j = 0; j < myDungeonGrid[i].length; j++){
                if (myDungeonGrid[i][j] != null){
                    System.out.print("[" + getRoomMarker(new Coordinates(j,i)) + "]");
                } else{
                    System.out.print("###");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public String printDungeonMap(){
        StringBuilder result = new StringBuilder("Dungeon Map:\n");
        for (int i = 0; i < myDungeonGrid.length; i++){
            for (int j = 0; j < myDungeonGrid[i].length; j++){
                Room currentRoom = myDungeonGrid[i][j];
                if (currentRoom != null && currentRoom.isRevealed()){
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

    public static int getMAX_X(){
        return MAX_X;
    }

    public static int getMAX_Y(){
        return MAX_Y;
    }

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

    public static void revealRoomsOnOtherSideOfDoors(final Room theRoom){
        int roomY = theRoom.getRoomCords().getY();
        int roomX = theRoom.getRoomCords().getX();
        for(Doors door : Doors.values()){
            if (theRoom.hasDoor(door)){
                System.out.println("Rooms entered has " + door);
                switch (door){
                    case NORTHDOOR  ->  myDungeonGrid[roomY - 1][roomX].reveal();
                    case EASTDOOR   ->  myDungeonGrid[roomY][roomX + 1].reveal();
                    case SOUTHDOOR  ->  myDungeonGrid[roomY + 1][roomX].reveal();
                    case WESTDOOR   ->  myDungeonGrid[roomY][roomX - 1].reveal();
                }
            }
        }
    }

    /* PRIVATE METHODS */

    private String getRoomMarker(final Coordinates cord){
        if (cord.isLocatedAtTheCoordinate(getPlayerCoordinates())){
            return "X";
        }
        return " ";
    }

    private void createMaze(){

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
//            System.out.println("\nNew Room at: " + newRoomCoordinates);
            final int NEW_ROOM_X = newRoomCoordinates.getX();
            final int NEW_ROOM_Y = newRoomCoordinates.getY();
            final double roomRoll = ThreadLocalRandom.current().nextDouble();
            final Coordinates northCord = new Coordinates(NEW_ROOM_X, NEW_ROOM_Y-1);
            final Coordinates eastCord = new Coordinates(NEW_ROOM_X+1, NEW_ROOM_Y);
            final Coordinates southCord = new Coordinates(NEW_ROOM_X, NEW_ROOM_Y+1);
            final Coordinates westCord = new Coordinates(NEW_ROOM_X-1, NEW_ROOM_Y);

            // Get the neighbors
            LinkedList<Coordinates> newRoomNeighbors = whoAreMyNeighbors(newRoomCoordinates);

            // Make a list to prevent attempting to create rooms where there are already neighbors
            LinkedList<Coordinates> roomsAvailable = new LinkedList<>();
            roomsAvailable.add(northCord);
            roomsAvailable.add(eastCord);
            roomsAvailable.add(southCord);
            roomsAvailable.add(westCord);
            for (Coordinates neighbor : newRoomNeighbors){
                roomsAvailable.removeIf(cord -> cord.isLocatedAtTheCoordinate(neighbor));
            }
            roomsAvailable.removeIf(cord -> cord.isLocatedAtTheCoordinate(newRoomCoordinates));
//            System.out.println("Potential rooms at: " + roomsAvailable);

            // Get potential doors per four cardinal direction, add them to a list
            LinkedList<Doors> doorsAvailable = new LinkedList<>();
            for (Doors door : Doors.values()){
                doorsAvailable.add(door);
            }

            // Remove potential doors that lead to non-existent tiles
//            System.out.println(doorsAvailable);
            if (NEW_ROOM_Y == MIN_Y){
                doorsAvailable.remove(Doors.NORTHDOOR);
            } else if (NEW_ROOM_X == MAX_X){
                doorsAvailable.remove(Doors.EASTDOOR);
            } else if (NEW_ROOM_Y == MAX_Y){
                doorsAvailable.remove(Doors.SOUTHDOOR);
            } else if (NEW_ROOM_X == MIN_X){
                doorsAvailable.remove(Doors.WESTDOOR);
            }
//            System.out.println("Doors available: " + doorsAvailable);

            /* ADD DOORS IF THERE'S ALREADY A DOOR ON THE OTHER SIDE */

            LinkedList<Doors> newRoomDoors = new LinkedList<>();
            for (Coordinates neighborCords : newRoomNeighbors){
                // yDiff determines if new room is south, north or on the
                // same row as neighbor with 1, -1, and 0 respectively.
                int yDiff = NEW_ROOM_Y - neighborCords.getY();
                Room neighbor = myDungeonGrid[neighborCords.getY()][neighborCords.getX()];

                /* NESTED SWITCH STATEMENT */

                switch (yDiff) {
                    // New Room is south of neighbor
                    case 1 -> {
                        if (neighbor.hasDoor(Doors.SOUTHDOOR)) {
                            newRoomDoors.add(Doors.NORTHDOOR);
                        }
                        doorsAvailable.remove(Doors.NORTHDOOR);
                    }
                    // New Room is north of neighbor
                    case -1 -> {
                        if (neighbor.hasDoor(Doors.NORTHDOOR)) {
                            newRoomDoors.add(Doors.SOUTHDOOR);
                        }
                        doorsAvailable.remove(Doors.SOUTHDOOR);
                    }
                    // New Room is on the same Row as neighbor, now check column with nested switch
                    case 0 -> {
                        // xDiff determines if new room is east, west or on the same
                        // column as neighbor with -1, 1, and 0 respectively.
                        int xDiff = NEW_ROOM_X - neighborCords.getX();
                        switch (xDiff) {
                            // New Room is East of neighbor
                            case 1 -> {
                                if (neighbor.hasDoor(Doors.EASTDOOR)) {
                                    newRoomDoors.add(Doors.WESTDOOR);
                                }
                                doorsAvailable.remove(Doors.WESTDOOR);
                            }
                            case -1 -> {
                                if (neighbor.hasDoor(Doors.WESTDOOR)) {
                                    newRoomDoors.add(Doors.EASTDOOR);
                                }
                                doorsAvailable.remove(Doors.EASTDOOR);
                            }
                        }
                    }
                }
//                if(NEW_ROOM_X - neighbor.getX() > 0){
//                    if (myDungeonGrid[neighbor.getY()][neighbor.getX()].hasDoor(Doors.EASTDOOR)){
//                        newRoomDoors.add(Doors.WESTDOOR);
//                    }
//                    doorsAvailable.remove(Doors.WESTDOOR);
//                } else if(NEW_ROOM_X - neighbor.getX() < 0){
//                    if (myDungeonGrid[neighbor.getY()][neighbor.getX()].hasDoor(Doors.WESTDOOR)){
//                        newRoomDoors.add(Doors.EASTDOOR);
//                    }
//                    doorsAvailable.remove(Doors.EASTDOOR);
//                } else if(NEW_ROOM_Y - neighbor.getY() > 0){
//                    if (myDungeonGrid[neighbor.getY()][neighbor.getX()].hasDoor(Doors.SOUTHDOOR)){
//                        newRoomDoors.add(Doors.NORTHDOOR);
//                    }
//                    doorsAvailable.remove(Doors.NORTHDOOR);
//                } else if(NEW_ROOM_Y - neighbor.getY() < 0){
//                    if (myDungeonGrid[neighbor.getY()][neighbor.getX()].hasDoor(Doors.NORTHDOOR)){
//                        newRoomDoors.add(Doors.SOUTHDOOR);
//                    }
//                    doorsAvailable.remove(Doors.SOUTHDOOR);
//                }
            }

            /* Generate 0-4 random doors, but only allow a dead end if there are still rooms to
               add or no more RoomsOfInterest needed. Pick from doorsAvailable at random to keep it
               organic.
             */

            final int MINIMUM_DOORS = !roomsNeeded.isEmpty() && roomsToAdd.isEmpty() ? 1 : 0;
//            final int MINIMUM_DOORS = 0;
            final int MAXIMUM_DOORS = doorsAvailable.size();
            final int numDoors;
            if (MAXIMUM_DOORS > MINIMUM_DOORS) {
                numDoors = ThreadLocalRandom.current().nextInt(MINIMUM_DOORS, MAXIMUM_DOORS);
            } else {
                numDoors = MAXIMUM_DOORS;
            }
//            System.out.println("number of doors: " + numDoors);
            StringBuilder stringOfNewRooms = new StringBuilder();
            for (int i = 0; i < numDoors; i++){
                int doorPicked = doorsAvailable.size() > 1 ?
                        ThreadLocalRandom.current().nextInt(0, doorsAvailable.size() - 1) : 0;
                Doors newDoor = doorsAvailable.remove(doorPicked);
                newRoomDoors.add(newDoor);
//                System.out.println("new door: " + newDoor);
                switch (newDoor){
                    case NORTHDOOR :
                        if (roomsAvailable.contains(northCord)) {
//                            System.out.println("case: " + newDoor);
                            roomsToAdd.add(northCord);
                            stringOfNewRooms.append(northCord + ", ");
                            break;
                        }
                    case EASTDOOR :
                        if (roomsAvailable.contains(eastCord)){
//                            System.out.println("case: " + newDoor);
                            roomsToAdd.add(eastCord);
                            stringOfNewRooms.append(eastCord + ", ");
                            break;
                        }
                    case SOUTHDOOR :
                        if (roomsAvailable.contains(southCord)){
//                            System.out.println("case: " + newDoor);
                            roomsToAdd.add(southCord);
                            stringOfNewRooms.append(southCord + ", ");
                            break;
                        }
                    case WESTDOOR :
                        if (roomsAvailable.contains(westCord)){
//                            System.out.println("case: " + newDoor);
                            roomsToAdd.add(westCord);
                            stringOfNewRooms.append(westCord + ", ");
                            break;
                        }
                }
            }
//            System.out.println("Added Rooms To Build: " + stringOfNewRooms);

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

            if (healthPotRoll < CHANCE_FOR_HEALTH_POTION){
                newRoom.addItemToRoom(RoomItems.HEALTH_POTION);
            }
            if (visionPotRoll < CHANCE_FOR_VISION_POTION){
                newRoom.addItemToRoom(RoomItems.VISION_POTION);
            }
            if (pitRoll < CHANCE_FOR_PIT){
                newRoom.addItemToRoom(RoomItems.PIT);
            }

            myDungeonGrid[NEW_ROOM_Y][NEW_ROOM_X] = newRoom;

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

    private Monsters getRandomMonster (){
        int choice = ThreadLocalRandom.current().nextInt(0, Monsters.values().length);
        return Monsters.values()[choice];
    }

    private RoomItems getRandomItem (){
        int choice = ThreadLocalRandom.current().nextInt(0, RoomItems.values().length);
        return RoomItems.values()[choice];
    }

    private static int getDungeonSize(){
        try {
            return uniqueInstanceOfDungeon.myDungeonGrid.length;
        } catch (NullPointerException e){
            System.out.println("Dungeon must be created before getting Dungeon Size");
            return 0;
        }
    }

    public static void main(String... args) {
        Dungeon dungeon = new Dungeon(6, 6);
        dungeon.printDungeonMap();
    }

}
