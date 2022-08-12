package controller;

import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Heroes;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import model.DungeonComponents.DataTypes.Coordinates;
import model.DungeonComponents.Doors;
import model.DungeonComponents.Dungeon;
import view.Window;

import java.awt.*;
import java.io.Serializable;
import java.util.Scanner;

import static controller.Handler.getHandler;
import static model.DungeonComponents.Dungeon.*;

public class DungeonAdventure extends Canvas implements Runnable {

    // Instance fields
    private Thread myThread;

    // Static fields
    private static boolean myRunning;
    private static boolean myWaitingForTurn;
    private static Coordinates playerCoordinates;

    private static DungeonCharacter myHero;

    private static DungeonCharacter myMonsterToBattle;
    // Final Instance Fields
    private Handler myHandler;

    private Dungeon myDungeon;

    // Global Constants
    private static final int MY_WIDTH = 300;//1024;
    private static final int MY_HEIGHT = 10;//640;
    private static final Dimension MY_DIMENSIONS = new Dimension(MY_WIDTH, MY_HEIGHT);


    private DungeonAdventure(Heroes myTypeOfHero, String theName){

        myHandler = getHandler();
        myHero = getMyHero(myTypeOfHero, theName);
        myHandler.addObject(myHero);
        myDungeon = getDungeon();
        this.addKeyListener(new KeyInputController());

        playerCoordinates = new Coordinates(0,0);

        new Window(MY_DIMENSIONS, "Dungeon Adventure", this);

//        System.out.println(myDungeon.toString());
        myDungeon.printDungeonMap();
        System.out.println(getPlayersCurrentRoom());

        setWaitingForTurn(true);

//        myHandler.addObject(HeroFactory.instantiateHero(Heroes.WARRIOR, "War"));
//        myHandler.addObject(new MonsterFactory().getMonster(Monsters.SKELETON));

//        System.out.println(myHandler);

        ///


        ///

        // run() method does the game loop
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
        this.requestFocus();                // set OS to focus on game window
        while (myRunning){                  // check if game is running
            if (!myWaitingForTurn) {        // don't tick if waiting for turn (trigger on key input)
                tick();                     // run behaviors
                setWaitingForTurn(true);    // wait for turn again
            }
        }
        stop();
    }

    private void tick(){
        myHandler.tick();
//        System.out.println("tick");
//        System.out.println(myHandler.toString());
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

    public static DungeonCharacter getMyHero(){
        try{
            return myHero;
        } catch (NullPointerException e){
            System.out.println("Hero must be created first!");
            return null;
        }
    }

    public static DungeonCharacter getMyHero(final Heroes theTypeOfHero, final String theName) {
        if (myHero == null){
            myHero = HeroFactory.instantiateHero(theTypeOfHero, theName);
        }
        return myHero;
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

    public Thread getMyThread() {
        return myThread;
    }

    public void setMyThread(Thread myThread) {
        this.myThread = myThread;
    }

    public static boolean getMyRunning() {
        return myRunning;
    }

    public static boolean getMyWaitingForTurn() {
        return myWaitingForTurn;
    }

    public Handler getMyHandler() {
        return myHandler;
    }

    public Dungeon getMyDungeon() {
        return myDungeon;
    }

    public static void setMyRunning(boolean myRunning) {
        DungeonAdventure.myRunning = myRunning;
    }

    public static void setMyWaitingForTurn(boolean myWaitingForTurn) {
        DungeonAdventure.myWaitingForTurn = myWaitingForTurn;
    }

    public static void setMyHero(DungeonCharacter myHero) {
        DungeonAdventure.myHero = myHero;
    }

    public void setMyHandler(Handler myHandler) {
        this.myHandler = myHandler;
    }

    public void setMyDungeon(Dungeon myDungeon) {
        this.myDungeon = myDungeon;
    }


    public static void main(String[] args){

//        Scanner sc = new Scanner(System.in);
//        System.out.println("Please enter your name: ");
//        String name = sc.nextLine();
//        System.out.println("Please enter (W)arrior, (T)hief, or (P)riestess: ");
//        String heroType = sc.nextLine();
//        switch (heroType.toLowerCase()) {
//            case "w" -> {
//
//            }
//        }
        new DungeonAdventure(Heroes.WARRIOR, "The Player");

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

                // Other methods that happen when rooms are checked
                // Dungeon adds monsters to handler
                    // Updates Room.hasMonster
                    // calls Room's addMonster Method to add the reference
                    //      to the monster added to the handler into the room
                // if the next room to move to has a monster
                    // Room.hasMonster
                        // returns monster if true
                //

                if (myMonsterToBattle != null){
                    myMonsterToBattle.setCombatFlag(false);
                }

                if (getPlayersCurrentRoom().getMyMonsterFlag()) {
                    myMonsterToBattle = getPlayersCurrentRoom().getMonsterFromRoom();
                    if (myMonsterToBattle != null) {
                        getMyHero().setMyTarget(myMonsterToBattle);
//                        System.out.println("Hero targets " + getMyHero().getMyTarget().getMyCharacterName());
//                        System.out.println("Monster targets " + myMonsterToBattle.getMyTarget().getMyCharacterName());
                        myMonsterToBattle.setCombatFlag(true);
                        getMyHero().setCombatFlag(true);
                    }
                        // Add reporting if monsterFlag was true but getMonsterFromRoom() returns null
                } else {
                     getMyHero().setCombatFlag(false);
                     getMyHero().setMyTarget(null);
                }

                getDungeon().printDungeonMap();
                System.out.println(getPlayerCoordinates());
                System.out.println(getPlayersCurrentRoom());
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
