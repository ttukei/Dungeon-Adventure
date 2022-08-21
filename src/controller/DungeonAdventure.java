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
import view.*;
import view.Window;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static controller.Handler.getHandler;
import static model.DungeonComponents.Dungeon.*;

public class DungeonAdventure extends Canvas implements Runnable, Serializable {

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
    private static Window myGUI;


    private static Hero myHero;

    private static DungeonCharacter myMonsterToBattle;

    // Final Instance Fields
    private final Handler HANDLER;

    private final Dungeon DUNGEON;

    // Global Constants
    private static final int MY_WIDTH = 600;//1024;
    private static final int MY_HEIGHT = 400;//640;
    private static final Dimension MY_DIMENSIONS = new Dimension(MY_WIDTH, MY_HEIGHT);


    private DungeonAdventure() throws InterruptedException {

        music();
        showIntroScreen();
        selectHeroClass();
        HANDLER = getHandler();
        myHero = getMyHero();
        HANDLER.addObject(myHero);
//        DUNGEON = getDungeon();   //Mock Dungeon
        DUNGEON = getDungeon(8,8);
        myTimeStart = System.currentTimeMillis();

        this.addKeyListener(new KeyInputController());
//        mouseMode();
//        godMode();
        Room entrance = getRoomOfInterest(RoomsOfInterest.ENTRANCE);
        System.out.println("Entrance: " + entrance);
        myHeroCoordinates = entrance == null ? new Coordinates(0,0) : entrance.getRoomCords();

        myGUI = new Window("Dungeon Adventure", this);
        //myGUI = new GUI("Dungeon Adventure", this);

//        System.out.println(DUNGEON.toString());
        getPlayersCurrentRoom().reveal();
        revealRoomsOnOtherSideOfDoors(getPlayersCurrentRoom());
        System.out.println(DUNGEON.printDungeonMap());
        myGUI.updateDungeonPanel(DUNGEON.printDungeonMap());
        myGUI.updateReportPanel(getPlayersCurrentRoom().getAnnouncement());
        myGUI.updateRoomPanel(getPlayersCurrentRoom().printRoom());
        System.out.println(getPlayersCurrentRoom().printRoom());
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
        if (myGUI != null){
            updateReportPanel("...");
            myGUI.updateDungeonPanel(DUNGEON.printDungeonMap());
        }
    }

    private static void godMode(){
        getMyHero().setMyHealthPoints(9999);
        getMyHero().setMyDamageRange(new DamageRange(98, 99));
    }

    private static void mouseMode(){
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

    public static void updateReportPanel(String theReport) {
        myGUI.updateReportPanel(theReport);
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


    public static void main(String[] args) throws InterruptedException {
        myTypeOfHero = Heroes.WARRIOR;
        myHeroName = "The Hero";
        new DungeonAdventure();
//        showIntroScreen();
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Please enter your name: ");
//        String name = sc.nextLine();
//        System.out.println("Please enter (W)arrior, (T)hief, or (P)riestess: ");
//        String heroType = sc.nextLine();
    }

    private static void selectHeroClass() throws InterruptedException {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Please enter your name: ");
//        String name = sc.nextLine();
//        System.out.println("Please enter (W)arrior, (T)hief, or (P)riestess: ");
//        String heroType = sc.nextLine();

        JFrame frame = new JFrame ("Begin");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new IntroPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible (true);

        Thread.sleep(1300);
        String name = "1";
        while (!name.matches("[a-zA-Z]+")) {
            name = JOptionPane.showInputDialog(null, "Please enter your name:",
                    "Name", JOptionPane.INFORMATION_MESSAGE);
            if (name == null) {
                System.exit(1);
            }
        }
        switch (name.toLowerCase()) {
            case "mouse" -> {
                mouseMode();
            }
            case "tom" -> {
                godMode();
            }
        }
        String[] heroChoices = {"Warrior", "Thief", "Priestess"};
        int heroType = JOptionPane.showOptionDialog(null,
                "Please choose a Hero", "Hero Selection",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, heroChoices, null);
        frame.setVisible(false);
        switch (heroType) {
            case 0 -> {
                myTypeOfHero = Heroes.WARRIOR;
                myHeroName = name;
            }
            case 1 -> {
                myTypeOfHero = Heroes.THIEF;
                myHeroName = name;
            }
            case 2 -> {
                myTypeOfHero = Heroes.PRIESTESS;
                myHeroName = name;
            }
        }
    }

    private static void showIntroScreen() throws InterruptedException {
        JFrame frame = new JFrame ("Begin");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 550));
        panel.setBackground(new Color(28, 58, 74));

        JTextArea text = new JTextArea(30,110);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setOpaque(false);
        text.setFont(new Font("Monospaced", Font.PLAIN, 12));
        text.setForeground(new Color(130, 140, 164));
        text.append("\n\n\n\n");
        text.setBounds(160, 150,790, 500);
        panel.add(text);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible (true);
        //the title sets printed to intro window
        Thread.sleep(400);
        text.append("             oooooooooo.                                                                      \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("             `888'   `Y8b                                                                    \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("              888      888 oooo  oooo  ooo. .oo.    .oooooooo  .ooooo.   .ooooo.  ooo. .oo.  \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("              888      888 `888  `888  `888P\"Y88b  888' `88b  d88' `88b d88' `88b `888P\"Y88b \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("              888      888  888   888   888   888  888   888  888ooo888 888   888  888   888 \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("              888      888  888   888   888   888  888   888  888ooo888 888   888  888   888 \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("              888     d88'  888   888   888   888  `88bod8P'  888    .o 888   888  888   888 \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("             o888bood8P'    `V88V\"V8P' o888o o888o `8oooooo.  `Y8bod8P' `Y8bod8P' o888o o888o\n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("                                                   d\"     YD                                 \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("                                                   \"Y88888P'                                 \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("\n\n\n");
        text.append("          .o.             .o8                                        .                                  \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("         .888.           \"888                                      .o8                                  \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("        .8\"888.      .oooo888  oooo    ooo  .ooooo.  ooo. .oo.   .o888oo oooo  oooo  oooo d8b  .ooooo.  \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("       .8' `888.    d88' `888   `88.  .8'  d88' `88b `888P\"Y88b    888   `888  `888  `888\"\"8P d88' `88b \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("      .88ooo8888.   888   888    `88..8'   888ooo888  888   888    888    888   888   888     888ooo888 \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("     .8'     `888.  888   888     `888'    888    .o  888   888    888 .  888   888   888     888    .o \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(200);
        text.append("    o88o     o8888o `Y8bod88P\"     `8'     `Y8bod8P' o888o o888o   \"888\"  `V88V\"V8P' d888b    `Y8bod8P' \n");
        text.setCaretPosition(text.getDocument().getLength());
        Thread.sleep(700);
        frame.setVisible(false);
    }

    private void music() {
        try {
            AudioInputStream music = AudioSystem.getAudioInputStream(new File("./Resources/Music.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(music);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    /* INNER CLASSES */

    static class RoomController {

        private static boolean myMoving;
        private static Doors myDirectionToMove;

        static synchronized void moveRooms(int theChangeInX, int theChangeInY, Doors door){

//            System.out.println(getPlayersCurrentRoom());

            if (getPlayersCurrentRoom().hasDoor(door)) {

                int currentX = getPlayerCoordinates().getX();
                int currentY = getPlayerCoordinates().getY();

                Coordinates newPlayerCoordinates = new Coordinates(
                        currentX + theChangeInX,
                        currentY + theChangeInY
                );

                setPlayerCoordinates(newPlayerCoordinates);
                Dungeon.revealRoomsOnOtherSideOfDoors(getPlayersCurrentRoom());
                System.out.println(getDungeon().printDungeonMap());
                System.out.println(getPlayersCurrentRoom().printRoom());
                myGUI.updateDungeonPanel(getDungeon().printDungeonMap());
                myGUI.updateReportPanel(getPlayersCurrentRoom().getAnnouncement());
                myGUI.updateRoomPanel(getPlayersCurrentRoom().printRoom());
                System.out.println(getPlayersCurrentRoom().getAnnouncement());

                if (myMonsterToBattle != null){
                    myMonsterToBattle.setCombatFlag(false);
                }

                if (getPlayersCurrentRoom().getMonsterFromRoom() != null) {
                    myMonsterToBattle = getPlayersCurrentRoom().getMonsterFromRoom();
                    if (myMonsterToBattle != null) {
                        getMyHero().setMyTarget(myMonsterToBattle);
//                        System.out.println("Hero targets " + getMyHero().getMyTarget().getMyCharacterName());
//                        System.out.println("Monster targets " + myMonsterToBattle.getMyTarget().getMyCharacterName());
                        myMonsterToBattle.setCombatFlag(true);
                        getMyHero().setCombatFlag(true);
                    }
                } else {
                    getMyHero().setCombatFlag(false);
                    getMyHero().setMyTarget(null);
                }
//                System.out.println(getPlayerCoordinates());
//                System.out.println(myHero.displayInventory());
            } else {
                System.out.println("You cannot go that way, there is no " + Room.getUserFriendlyDoor(door));
                myGUI.updateReportPanel("You cannot go that way, there is no " + Room.getUserFriendlyDoor(door));
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
