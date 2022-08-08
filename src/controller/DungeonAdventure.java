package controller;

import model.DungeonCharacterComponents.DungeonCharacters.Heroes.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Heroes;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import model.DungeonComponents.DataTypes.Coordinates;
import model.DungeonComponents.Doors;
import model.DungeonComponents.Dungeon;
import view.Window;

import java.awt.*;

import static controller.Handler.getHandler;
import static model.DungeonComponents.Dungeon.*;

public class DungeonAdventure extends Canvas implements Runnable {

    // Instance fields
    private Thread myThread;

    // Static fields
    private static boolean myRunning;
    private static boolean myWaitingForTurn;
    private static Coordinates playerCoordinates;

    // Final Instance Fields
    private final Handler myHandler;
    private final Dungeon myDungeon;

    // Global Constants
    private static final int MY_WIDTH = 1024;
    private static final int MY_HEIGHT = 640;
    private static final Dimension MY_DIMENSIONS = new Dimension(MY_WIDTH, MY_HEIGHT);

    private DungeonAdventure(){

        myHandler = getHandler();
        myDungeon = getDungeon();
        this.addKeyListener(new KeyInputController());

        playerCoordinates = new Coordinates(0,0);

        new Window(MY_DIMENSIONS, "Dungeon Adventure", this);

        myDungeon.printDungeonMap();
        System.out.println(getPlayersCurrentRoom());

        myHandler.addObject(HeroFactory.instantiateHero(Heroes.WARRIOR, "War"));
        myHandler.addObject(new MonsterFactory().getMonster(Monsters.SKELETON));

        System.out.println(myHandler);

    }

    public synchronized void start(){
        myThread = new Thread(this);
        myThread.start();
        myRunning = true;
    }

    synchronized void stop(){
        try {
            myThread.join(); // kills myThread
            myRunning = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        this.requestFocus();
        long lastTime = System.nanoTime();          // starting Time
        final double amountOfTicks = 2.0;           // How many ticks do we want per second?
        double ns = 1000000000 / amountOfTicks;     // Nanoseconds per tick
        double delta = 0;
        while (myRunning){
            long now = System.nanoTime();           // current Loop Time
            delta += (now - lastTime) / ns;         // changeBetween reported times in nanoseconds
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
        }
        stop();
        System.out.println("Thread dead");
    }

    private void tick(){
        myHandler.tick();
        System.out.println("tick");
    }

    // Public Static Methods

    public static int clamp(int theInteger, int theMin, int theMax){
        if (theInteger >= theMax){
            return theMax;
        } else if (theInteger <= theMin){
            return theMin;
        }
        return theInteger;
    }

    public static double clamp(final double theDouble, final double theMin, final double theMax){
        if (theDouble >= theMax){
            return theMax;
        } else if (theDouble <= theMin){
            return theMin;
        }
        return theDouble;
    }

    public static Coordinates getPlayerCoordinates(){
        return playerCoordinates;
    }

    static void setPlayerCoordinates(final Coordinates theCords){
        playerCoordinates = theCords;
    }

    static void setWaitingForTurn(final boolean theWaiting){
        myWaitingForTurn = theWaiting;
    }

    static void setRunning(boolean theRunning) {
        myRunning = theRunning;
    }

    public static void main(String[] args){

        new DungeonAdventure();

    }

    /* INNER CLASSES */

    static class RoomController {

        static void moveRooms(int theChangeInX, int theChangeInY, Doors door){

//            System.out.println(getPlayersCurrentRoom());

            if (getPlayersCurrentRoom().hasDoor(door)) {

                int currentX = getPlayerCoordinates().getX();
                int currentY = getPlayerCoordinates().getY();

                Coordinates newPlayerCoordinates = new Coordinates(
                        currentX + theChangeInX,
                        currentY + theChangeInY
                );

                setPlayerCoordinates(newPlayerCoordinates);
                //            System.out.println(playerCoordinates.toString());

                getDungeon().printDungeonMap();
            } else {
                System.out.println("You cannot go that way, there is no " + door);
            }

        }

        static void moveNorth(){

            moveRooms(0, -1, Doors.NORTHDOOR);

        }

        static void moveEast() {

            moveRooms(+1, 0, Doors.EASTDOOR);

        }

        static void moveSouth() {

            moveRooms(0, +1, Doors.SOUTHDOOR);

        }

        static void moveWest() {

            moveRooms(-1, 0, Doors.WESTDOOR);

        }

    }
}
