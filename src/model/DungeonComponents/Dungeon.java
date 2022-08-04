package model.DungeonComponents;

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
    final int MIN_LOCATION;
    final int MAX_LOCATION;

    /* CONSTRUCTORS */

    private Dungeon(Coordinates size) {
        System.out.println("Building New Dungeon");
        myDungeonGrid = new Room[size.getX()][size.getY()];
        MIN_LOCATION = 0;
        MAX_LOCATION = myDungeonGrid.length - 1;
        createMaze();
    }

    private Dungeon() {
        System.out.println("Building New Mock Dungeon");
        myDungeonGrid = new Room[][]{
            {   new Room(new LinkedList<>(List.of(Doors.EASTDOOR))),
                new Room(new LinkedList<>(List.of(Doors.EASTDOOR, Doors.SOUTHDOOR, Doors.WESTDOOR))),
                new Room(new LinkedList<>(List.of(Doors.WESTDOOR)))},
            {   null,
                new Room(new LinkedList<>(List.of(Doors.NORTHDOOR, Doors.SOUTHDOOR))),
                null},
            {   null,
                new Room(new LinkedList<>(List.of(Doors.NORTHDOOR))),
                null}
        };
        MIN_LOCATION = 0;
        MAX_LOCATION = myDungeonGrid.length - 1;
        System.out.println(this);
    }

    /* GET SINGLETONS */

    /**
     * Singleton accessor for Dungeon which generates the floor plan using creatMaze().
     * @return the existing or new Singleton instance of Dungeon
     */
    public static synchronized Dungeon getDungeon(Coordinates size){
        if (uniqueInstanceOfDungeon == null){
            uniqueInstanceOfDungeon = new Dungeon(size);
        }
        return uniqueInstanceOfDungeon;
    }

    /**
     * Singleton accessor for MockDungeon which forgoes generating the
     * floor plan randomly for early iterations of game.
     * @return the existing or new Singleton instance of Dungeon
     */
    public static synchronized Dungeon getMockDungeon(){
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

    /* PRIVATE METHODS */

    private void createMaze(){

        // Linked list of the coordinates of rooms to make, randomly generate starting coordinates
        LinkedList<Coordinates> roomsToAdd = new LinkedList();

        int startX = ThreadLocalRandom.current().nextInt(MIN_LOCATION, MAX_LOCATION);
        int startY = ThreadLocalRandom.current().nextInt(MIN_LOCATION, MAX_LOCATION);
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
            final int X = newRoomCoordinates.getX();
            final int Y = newRoomCoordinates.getY();
            final double roomRoll = ThreadLocalRandom.current().nextDouble();
            final Coordinates northCord = new Coordinates(X, Y-1);
            final Coordinates eastCord = new Coordinates(X+1, Y);
            final Coordinates southCord = new Coordinates(X, Y+1);
            final Coordinates westCord = new Coordinates(X-1, Y);

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
            if (Y == MIN_LOCATION){
                doorsAvailable.remove(Doors.NORTHDOOR);
            } else if (X == MAX_LOCATION){
                doorsAvailable.remove(Doors.EASTDOOR);
            } else if (Y == MAX_LOCATION){
                doorsAvailable.remove(Doors.SOUTHDOOR);
            } else if (X == MIN_LOCATION){
                doorsAvailable.remove(Doors.WESTDOOR);
            }
//            System.out.println(doorsAvailable);
            LinkedList<Doors> newRoomDoors = new LinkedList<>();
            for (Coordinates neighbor : newRoomNeighbors){
                if(X - neighbor.getX() > 0){
                    if (myDungeonGrid[neighbor.getX()][neighbor.getY()].hasEastDoor()){
                        newRoomDoors.add(Doors.WESTDOOR);
                    }
                    doorsAvailable.remove(Doors.WESTDOOR);
                } else if(X - neighbor.getX() < 0){
                    if (myDungeonGrid[neighbor.getX()][neighbor.getY()].hasWestDoor()){
                        newRoomDoors.add(Doors.EASTDOOR);
                    }
                    doorsAvailable.remove(Doors.EASTDOOR);
                } else if(Y - neighbor.getY() > 0){
                    if (myDungeonGrid[neighbor.getX()][neighbor.getY()].hasSouthDoor()){
                        newRoomDoors.add(Doors.NORTHDOOR);
                    }
                    doorsAvailable.remove(Doors.NORTHDOOR);
                } else if(Y - neighbor.getY() < 0){
                    if (myDungeonGrid[neighbor.getX()][neighbor.getY()].hasNorthDoor()){
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
            myDungeonGrid[X][Y] = new Room(newRoomDoors);

        }

    }

    /**
     * Given a set of Coordinates, returns a list of all adjacent Coordinates already containing rooms.
     * @param theCoordinates Coordinates to check the neighbors of.
     * @return neighbors - a list of Coordinates containing rooms.
     */
    private LinkedList<Coordinates> whoAreMyNeighbors(Coordinates theCoordinates){

        LinkedList<Coordinates> neighbors = new LinkedList<>();

        final int X = theCoordinates.getX();
        final int Y = theCoordinates.getY();

        // North
        if(Y > 0 && myDungeonGrid[X][Y-1] != null){
            neighbors.add(new Coordinates(X, Y-1));
        }
        // East
        if(X < MAX_LOCATION && myDungeonGrid[X+1][Y] != null){
            neighbors.add(new Coordinates(X+1, Y));
        }
        // South
        if(Y < MAX_LOCATION && myDungeonGrid[X][Y+1] != null){
            neighbors.add(new Coordinates(X, Y+1));
        }
        // West
        if(X > 0 && myDungeonGrid[X-1][Y] != null){
            neighbors.add(new Coordinates(X-1, Y));
        }

        System.out.println(theCoordinates + " neighbors are: " + neighbors);

        return neighbors;

    }

    public static void main(String... args) {
        Dungeon dungeon = new Dungeon(new Coordinates(6,6));
    }

}
