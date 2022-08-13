package controller;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Heroes;
import model.DungeonComponents.DataTypes.Coordinates;
import model.DungeonComponents.Doors;
import model.DungeonComponents.Dungeon;
import model.DungeonComponents.Room;
import model.DungeonComponents.RoomsOfInterest;
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
    private static Heroes myTypeOfHero;
    private static String myHeroName;
    private static Coordinates myHeroCoordinates;

    private static long myTimeStart;
    private static int myKillCount;

    private static Hero myHero;

    private static DungeonCharacter myMonsterToBattle;
    // Final Instance Fields
    private final Handler HANDLER;

    private final Dungeon DUNGEON;

    // Global Constants
    private static final int MY_WIDTH = 300;//1024;
    private static final int MY_HEIGHT = 10;//640;
    private static final Dimension MY_DIMENSIONS = new Dimension(MY_WIDTH, MY_HEIGHT);


    private DungeonAdventure(){
        myTimeStart = System.currentTimeMillis();

        HANDLER = getHandler();
        myHero = getMyHero();
//        mouseMode();
        HANDLER.addObject(myHero);
        DUNGEON = getDungeon();

        this.addKeyListener(new KeyInputController());
        godMode();
        Room entrance = getRoomOfInterest(RoomsOfInterest.ENTRANCE);
        myHeroCoordinates = entrance == null ? new Coordinates(0,0) : entrance.getRoomCords();


        new Window(MY_DIMENSIONS, "Dungeon Adventure", this);

//        System.out.println(DUNGEON.toString());
        DUNGEON.printDungeonMap();
        System.out.println(getPlayersCurrentRoom().getAnnouncement());

//        System.out.print(myHero.displayInventory());

        setWaitingForTurn(false);

//        HANDLER.addObject(HeroFactory.instantiateHero(Heroes.WARRIOR, "War"));
//        HANDLER.addObject(new MonsterFactory().getMonster(Monsters.SKELETON));

//        System.out.println(HANDLER);

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
        if(RoomController.isMoving()){
            switch (RoomController.getDirectionToMove()){
                case NORTHDOOR -> RoomController.moveNorth();
                case EASTDOOR -> RoomController.moveEast();
                case SOUTHDOOR -> RoomController.moveSouth();
                case WESTDOOR -> RoomController.moveWest();
            }
            RoomController.setMyMoving(false);
        }
        HANDLER.tick();
        System.out.println("...");
    }

    private void godMode(){
        getMyHero().setMyHealthPoints(9999);
        getMyHero().setMyDamageRange(new DamageRange(98, 99));
    }

    private void mouseMode(){
        getMyHero().setMyHealthPoints(1);
        getMyHero().setMyDamageRange(new DamageRange(1, 2));
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

    public static Hero getMyHero(){
        if (myHero == null){
            myHero = HeroFactory.instantiateHero(myTypeOfHero, myHeroName);
        }
        return myHero;
    }

    public static Coordinates getPlayerCoordinates(){
        if (getMyHero().getMyCords() == null) return myHeroCoordinates;
        return getMyHero().getMyCords();
    }

    static void setPlayerCoordinates(final Coordinates theCords){
        getMyHero().setMyCords(theCords);
    }

    static void setWaitingForTurn(final boolean theWaiting){
        myWaitingForTurn = theWaiting;
    }

    static void setRunning(boolean theRunning) {
        myRunning = theRunning;
    }

    public static long getMyTimeStart() {
        return myTimeStart;
    }

    public static void addToKillCount() {
        myKillCount++;
    }

    public static int getKillCount() {
        return myKillCount;
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
        return HANDLER;
    }

    public Dungeon getMyDungeon() {
        return DUNGEON;
    }

    public static void setMyRunning(boolean theRunning) {
        myRunning = theRunning;
    }

    public static void setMyWaitingForTurn(boolean theWaitingForTurn) {
        myWaitingForTurn = theWaitingForTurn;
    }


    public static void main(String[] args){

        myTypeOfHero = Heroes.WARRIOR;
        myHeroName = "The Hero";

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
        new DungeonAdventure();

    }

    /* INNER CLASSES */

    static class RoomController {

        private static boolean myMoving;
        private static Doors myDirectionToMove;

        static synchronized void moveRooms(int theChangeInX, int theChangeInY, Doors door){

//            System.out.println(getPlayersCurrentRoom());

            if (getPlayersCurrentRoom().hasDoor(door)) {

                int currentY = getPlayerCoordinates().getY();
                int currentX = getPlayerCoordinates().getX();

                Coordinates newPlayerCoordinates = new Coordinates(
                        currentX + theChangeInX,
                        currentY + theChangeInY
                );


                setPlayerCoordinates(newPlayerCoordinates);
                getDungeon().printDungeonMap();
                System.out.println(getPlayersCurrentRoom().getAnnouncement());

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
//                System.out.println(getPlayerCoordinates());
//                System.out.println(myHero.displayInventory());
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

        public static Doors getDirectionToMove() {
            return myDirectionToMove;
        }

        public static void setDirectionToMove(Doors theDirectionToMove) {
            myDirectionToMove = theDirectionToMove;
        }

        public static boolean isMoving() {
            return myMoving;
        }

        public static void setMyMoving(boolean theMoving) {
            myMoving = theMoving;
        }
    }

}
