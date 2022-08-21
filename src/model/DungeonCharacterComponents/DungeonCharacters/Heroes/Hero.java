package model.DungeonCharacterComponents.DungeonCharacters.Heroes;

import controller.DungeonAdventure;
import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonComponents.Dungeon;
import model.DungeonComponents.Room;
import model.DungeonComponents.RoomsOfInterest;
import model.RoomItemComponents.*;
import view.EndPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Timon Tukei
 */
public abstract class Hero extends DungeonCharacter {

    /**
     * Probability to defend against an attack.
     */
    private final double myChanceToDefend;

    private final ArrayList<RoomItem> myInventory;


    /**
     *
     * @param theCharacterName
     * @param theHealthPoints
     * @param theDamageRange
     * @param theAttackSpeed
     * @param theChanceToHit
     * @param theChanceToDefend
     */
    protected Hero(final String theCharacterName, final int theHealthPoints, final DamageRange theDamageRange,
                   final int theAttackSpeed, final double theChanceToHit, final double theChanceToDefend) {
        super(theCharacterName, theHealthPoints, theDamageRange, theAttackSpeed, theChanceToHit);
        this.myChanceToDefend = theChanceToDefend;
        myInventory = new ArrayList<>();
//        System.out.println("Hero is created");
    }

    @Override
    public void objectBehavior(){
        super.objectBehavior();
        if(!isInCombat()){
            outOfCombatBehavior();
        }
    }

    @Override
    public boolean didIDie(){
        boolean died = super.didIDie();
        if (died){
            System.out.println(endScreenReadOut());
            JFrame frame = new JFrame ("End");
            frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new EndPanel(0));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible (true);
        }
        return died;
    }

    private void outOfCombatBehavior(){
        Room playersCurrentRoom = Dungeon.getDungeon().getPlayersCurrentRoom();
//        System.out.println(this.displayInventory());
        PillarOO pillarOO = null;
        if (playersCurrentRoom.containsRoomItem()) {
            LinkedList<RoomItem> roomItems = playersCurrentRoom.getMyRoomItems();
            if (playersCurrentRoom.containsPillar()) {
                for (RoomItem roomItem : roomItems) {
                    if (roomItem.getType() == RoomItems.PILLAR) {
                        pillarOO = (PillarOO) roomItem;
                        this.myInventory.add(pillarOO);
                    }
                }
                System.out.println("You have found the pillar of OO, " + pillarOO);
//                System.out.println(displayInventory());
                DungeonAdventure.updateReportPanel("You have found the pillar of OO, " + pillarOO);
            }
            if (playersCurrentRoom.containsPotion()) {
                for (RoomItem roomItem: roomItems) {
                    switch (roomItem.getType()){
                        case HEALTH_POTION -> {
                            HealthPotion healthPotion = (HealthPotion) roomItem;
                            setMyHealthPoints(getMyHealthPoints() + healthPotion.getMyHealthToBeRegained());
                            System.out.println("You have found a health potion!");
                            System.out.println("Health before potion = " + (this.getMyHealthPoints() - healthPotion.getMyHealthToBeRegained()));
                            System.out.println("Health after potion = " + this.getMyHealthPoints());
                            DungeonAdventure.updateReportPanel("You have found a health potion!");
                            DungeonAdventure.updateReportPanel("Health before potion = " + (this.getMyHealthPoints() - healthPotion.getMyHealthToBeRegained()));
                            DungeonAdventure.updateReportPanel("Health after potion = " + this.getMyHealthPoints());
                        }
                        case VISION_POTION -> {
                            for(RoomsOfInterest typeOfSpecialRoom : RoomsOfInterest.values()){
                                Room currentRoom = Dungeon.getRoomOfInterest(typeOfSpecialRoom);
                                if (currentRoom != null && !currentRoom.isRevealed()){
                                    currentRoom.reveal();
                                    System.out.println("You have found a vision potion!");
                                    System.out.println("It reveals the room with the " + typeOfSpecialRoom);
                                    DungeonAdventure.updateReportPanel("You have found a vision potion!");
                                    DungeonAdventure.updateReportPanel("It reveals the room with the " + typeOfSpecialRoom);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (playersCurrentRoom.containsPit()) {
                for (RoomItem roomItem: roomItems) {
                    if (roomItem.getType() == RoomItems.PIT) {
                        Pit pit = (Pit) roomItem;
                        this.setMyHealthPoints(getMyHealthPoints() + pit.getMyHealthToBeDamaged());
                        didIDie();
                        System.out.println("You have fell into a Pit!!");
                        System.out.println("Health before falling = " + (this.getMyHealthPoints() - pit.getMyHealthToBeDamaged()));
                        System.out.println("Health after falling = " + this.getMyHealthPoints());
                        DungeonAdventure.updateReportPanel("You have fell into a Pit!!");
                        DungeonAdventure.updateReportPanel("Health before falling = " + (this.getMyHealthPoints() - pit.getMyHealthToBeDamaged()));
                        DungeonAdventure.updateReportPanel("Health after falling = " + this.getMyHealthPoints());
                    }
                }
            }
            roomItems.clear();
        }
        if (this.hasAllPillarsOfOO() && (playersCurrentRoom.getRoomType() == RoomsOfInterest.EXIT)) {
            System.out.println(endScreenReadOut());
            JFrame frame = new JFrame ("End");
            frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new EndPanel(1));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible (true);
            //System.exit(1);
        }
        if (!this.hasAllPillarsOfOO() && (playersCurrentRoom.getRoomType() == RoomsOfInterest.EXIT)) {
            // is in exit but doesnt have all the pillars
            System.out.println("You found the EXIT but don't have all the Pillars");
            DungeonAdventure.updateReportPanel("You found the EXIT but don't have all the Pillars");
        }

    }

    public String endScreenReadOut() {
        long totalDuration = System.currentTimeMillis() - DungeonAdventure.getMyTimeStart();
        long minutes = (totalDuration / 1000) / 60;
        long seconds = (totalDuration / 1000) % 60;
        StringBuilder result = new StringBuilder();
//        System.out.printf("\nPlayer: %s as Hero %s", getMyCharacterName(), getClass().getSimpleName());
//        System.out.printf("\nTime: %d:%d", minutes, seconds);
//        System.out.printf("\nHealth remaining: %d", getMyHealthPoints());
//        System.out.printf("\nMonsters Killed: %d", DungeonAdventure.getKillCount());
//
        result.append("\nPlayer: " + getMyCharacterName() + " as " + getClass().getSimpleName());
        result.append("\nTime: " + minutes + ":" + seconds);
        result.append("\nHealth remaining: " + getMyHealthPoints());
        result.append("\nMonsters Killed: " + DungeonAdventure.getKillCount());
        return result.toString();
    }


    private boolean hasAllPillarsOfOO() {
        int pillarsCounter = 0;
        for (RoomItem roomItem : this.myInventory) {
            if (roomItem.getClass() == PillarOO.class) {
                pillarsCounter++;
            }
        }
        return pillarsCounter == 4;
    }

    public double getMyChanceToDefend() {
        return myChanceToDefend;
    }

    public String displayInventory() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < myInventory.size() - 1; i++) {
            result.append(myInventory.get(i).myName + ",\n");
        }
        if (!myInventory.isEmpty()) {
            result.append(myInventory.get(myInventory.size() - 1).myName + "]");
        } else {
            result.append("Empty]");
        }
        return result + "\n";
    }

    public abstract int specialSkill(final DungeonCharacter theMonsterToAttack);

    public int takeDamage(final int theDamageToTake) {
        if (!(ThreadLocalRandom.current().nextDouble() < this.getMyChanceToDefend())) {
            super.takeDamage(theDamageToTake);
            return theDamageToTake;
        }
        return 0;
    }

}
