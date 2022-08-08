package model.DungeonComponents;

import static controller.DungeonAdventure.*;

import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import model.DungeonComponents.DataTypes.Coordinates;

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
    private final Room[][] myDungeonGrid;
    private final int MIN_X;
    private final int MAX_X;
    private final int MIN_Y; // TODO: 8/8/2022 Adjust parameters for Y values to check MIN_Y instead of MIN_X
    private final int MAX_Y;

    /* CONSTRUCTORS */

    private Dungeon(int theWidth, int theHeight) {
        System.out.println("Building New Dungeon");
        myDungeonGrid = new Room[theHeight][theWidth];
        MIN_X = 0;
        MAX_X = myDungeonGrid.length - 1;
        MIN_Y = 0;
        MAX_Y = myDungeonGrid[0].length - 1;
        createMaze();
    }

    private Dungeon() {
        System.out.println("Building New Dungeon");
        myDungeonGrid = new Room[][]{
            {   new Room(RoomsOfInterest.ENTRANCE, new LinkedList<>(List.of(Doors.EASTDOOR))),
                new Room(new LinkedList<>(List.of(Doors.EASTDOOR, Doors.SOUTHDOOR, Doors.WESTDOOR))),
                new Room(new LinkedList<>(List.of(Doors.WESTDOOR)))
            },
            {   null,
                new Room(new LinkedList<>(List.of(Doors.NORTHDOOR, Doors.SOUTHDOOR))),
                null
            },
            {   null,
                new Room(new LinkedList<>(List.of(Doors.NORTHDOOR))),
                null
            }
        };
        myDungeonGrid[0][1].addMonstersToRoom(Monsters.SKELETON);
        MIN_X = 0;
        MAX_X = myDungeonGrid.length - 1;
        MIN_Y = 0;
        MAX_Y = myDungeonGrid[0].length - 1;
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
                        dungeonToString.append("Room ").append(roomNumber++             // Room Number
                        ).append(", at (").append(i).append(", ").append(j              // Coordinates
                        ).append(") has doors: ").append(currentRoom.getDoors()         // Doors
                        ).append("\n");                                                 // New Line
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

    public void printDungeonMap(){
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

    /* PUBLIC STATIC METHODS */

    private static int getDungeonSize(){
        try {
            return uniqueInstanceOfDungeon.myDungeonGrid.length;
        } catch (NullPointerException e){
            System.out.println("Dungeon must be created before getting Dungeon Size");
            return 0;
        }
    }

    public static int getMAX_X(){
        return getDungeon().MAX_X;
    }

    public static int getMAX_Y(){
        return getDungeon().MAX_Y;
    }

    public static Room getPlayersCurrentRoom(){
        try {
            int x = getPlayerCoordinates().getX();
            int y = getPlayerCoordinates().getY();
            return uniqueInstanceOfDungeon.myDungeonGrid[y][x];
        } catch (NullPointerException e){
            System.out.println("Dungeon must be created before getting Players Current Room");
            return null;
        }
    }

    /* PRIVATE METHODS */

    private String getRoomMarker(Coordinates cord){
        if (cord.isLocatedAtTheCoordinate(getPlayerCoordinates())){
            return "X";
        }
        return " ";
    }

    private void createMaze(){

        // Linked list of the coordinates of rooms to make, randomly generate starting coordinates
        LinkedList<Coordinates> roomsToAdd = new LinkedList();

        int startX = ThreadLocalRandom.current().nextInt(MIN_X, MAX_X);
        int startY = ThreadLocalRandom.current().nextInt(MIN_X, MAX_X);
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
            System.out.println("New Room at: " + newRoomCoordinates);
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
                roomsAvailable.removeIf(new Predicate<Coordinates>() {
                    @Override
                    public boolean test(Coordinates coordinates) {
                        return coordinates.isLocatedAtTheCoordinate(neighbor);
                    }
                });
            }
            System.out.println("Potential rooms at: " + roomsAvailable);

            // Get potential doors per four cardinal direction, add them to a list
            LinkedList<Doors> doorsAvailable = new LinkedList<>();
            for (Doors door : Doors.values()){
                doorsAvailable.add(door);
            }

            // Remove doors that lead to non-existent tiles
//            System.out.println(doorsAvailable);
            if (NEW_ROOM_Y == MIN_X){
                doorsAvailable.remove(Doors.NORTHDOOR);
            } else if (NEW_ROOM_X == MAX_X){
                doorsAvailable.remove(Doors.EASTDOOR);
            } else if (NEW_ROOM_Y == MAX_X){
                doorsAvailable.remove(Doors.SOUTHDOOR);
            } else if (NEW_ROOM_X == MIN_X){
                doorsAvailable.remove(Doors.WESTDOOR);
            }
//            System.out.println(doorsAvailable);
            LinkedList<Doors> newRoomDoors = new LinkedList<>();
            for (Coordinates neighbor : newRoomNeighbors){
                if(NEW_ROOM_X - neighbor.getX() > 0){
                    if (myDungeonGrid[neighbor.getY()][neighbor.getX()].hasDoor(Doors.EASTDOOR)){
                        newRoomDoors.add(Doors.WESTDOOR);
                    }
                    doorsAvailable.remove(Doors.WESTDOOR);
                } else if(NEW_ROOM_X - neighbor.getX() < 0){
                    if (myDungeonGrid[neighbor.getY()][neighbor.getX()].hasDoor(Doors.WESTDOOR)){
                        newRoomDoors.add(Doors.EASTDOOR);
                    }
                    doorsAvailable.remove(Doors.EASTDOOR);
                } else if(NEW_ROOM_Y - neighbor.getY() > 0){
                    if (myDungeonGrid[neighbor.getY()][neighbor.getX()].hasDoor(Doors.SOUTHDOOR)){
                        newRoomDoors.add(Doors.NORTHDOOR);
                    }
                    doorsAvailable.remove(Doors.NORTHDOOR);
                } else if(NEW_ROOM_Y - neighbor.getY() < 0){
                    if (myDungeonGrid[neighbor.getY()][neighbor.getX()].hasDoor(Doors.NORTHDOOR)){
                        newRoomDoors.add(Doors.SOUTHDOOR);
                    }
                    doorsAvailable.remove(Doors.SOUTHDOOR);
                }
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
            System.out.println("number of doors: " + numDoors);
            for (int i = 0; i < numDoors; i++){
                int doorPicked = ThreadLocalRandom.current().nextInt(0, doorsAvailable.size());
                Doors newDoor = doorsAvailable.remove(doorPicked);
                newRoomDoors.add(newDoor);
                switch (newDoor){
                    case NORTHDOOR :
                        if (roomsAvailable.contains(northCord)) {
                            roomsToAdd.add(northCord);
                        }
                    case EASTDOOR :
                        if (roomsAvailable.contains(eastCord)){
                            roomsToAdd.add(eastCord);
                        }
                    case SOUTHDOOR :
                        if (roomsAvailable.contains(southCord)){
                            roomsToAdd.add(southCord);
                        }
                    case WESTDOOR :
                        if (roomsAvailable.contains(westCord)){
                            roomsToAdd.add(westCord);
                        }
                }
            }
            System.out.println("Added Rooms To Build: ");
            myDungeonGrid[NEW_ROOM_Y][NEW_ROOM_X] = new Room(newRoomDoors);

        }

    }

    /**
     * Given a set of Coordinates, returns a list of all adjacent Coordinates already containing rooms.
     * @param theCoordinates Coordinates to check the neighbors of.
     * @return neighbors - a list of Coordinates containing rooms.
     */
    private LinkedList<Coordinates> whoAreMyNeighbors(Coordinates theCoordinates){

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

        System.out.println(theCoordinates + " neighbors are: " + neighbors);

        return neighbors;

    }

    public static void main(String... args) {
        Dungeon dungeon = new Dungeon(6, 6);
        dungeon.printDungeonMap();
    }

}
