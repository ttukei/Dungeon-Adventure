package model.DungeonCharacterComponents.DungeonCharacters.Heroes;

import controller.DungeonAdventure;
import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonComponents.DataTypes.Coordinates;
import model.DungeonComponents.Dungeon;
import model.DungeonComponents.Room;
import model.DungeonComponents.RoomsOfInterest;
import model.RoomItemComponents.*;

import java.util.ArrayList;
import java.util.LinkedList;

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
    }

    @Override
    public boolean didIDie(){
        boolean died = super.didIDie();
        if (died){
            //
        }
        return died;
    }

    @Override
    protected void outOfCombatBehavior(){
        Room playersCurrentRoom = Dungeon.getDungeon().getPlayersCurrentRoom();
//        System.out.println(this.displayInventory());
        PillarOO pillarOO = null;
        if (playersCurrentRoom.containsRoomItem()) {
            LinkedList<RoomItem> roomItems = playersCurrentRoom.getMyRoomItems();
            if (playersCurrentRoom.containsPillar()) {
                for (RoomItem roomItem : roomItems) {
                    if (roomItem.getClass() == PillarOO.class) {
                        pillarOO = (PillarOO) roomItem;
                        roomItems.remove(roomItem);
                        this.myInventory.add(pillarOO);
                    }
                }
                System.out.println("You have found the pillar of OO, " + pillarOO);
                System.out.println(displayInventory());
            } else if (playersCurrentRoom.containsPotion()) {
                for (RoomItem roomItem: roomItems) {
                    if (roomItem.getClass() == HealthPotion.class) {
                        HealthPotion healthPotion = (HealthPotion) roomItem;
                        roomItems.remove(roomItem);
                        this.setMyHealthPoints(getMyHealthPoints() + healthPotion.getMyHealthToBeRegained());
                        System.out.println("You have found a health potion!");
                        System.out.println("Health before potion = " + (this.getMyHealthPoints() - healthPotion.getMyHealthToBeRegained()));
                        System.out.println("Health after potion = " + this.getMyHealthPoints());
                    }
                }
            } else if (playersCurrentRoom.containsPit()) {
                for (RoomItem roomItem: roomItems) {
                    if (roomItem.getClass() == Pit.class) {
                        Pit pit = (Pit) roomItem;
                        roomItems.remove(roomItem);
                        this.setMyHealthPoints(getMyHealthPoints() + pit.getMyHealthToBeDamaged());
                        didIDie();
                        System.out.println("You have fell into a Pit!!");
                        System.out.println("Health before falling = " + (this.getMyHealthPoints() - pit.getMyHealthToBeDamaged()));
                        System.out.println("Health after falling = " + this.getMyHealthPoints());
                    }
                }
            }
        }


        if (this.hasAllPillarsOfOO() && (playersCurrentRoom.getRoomType() == RoomsOfInterest.EXIT)) {
            endScreenReadOut();
        }
        if (!this.hasAllPillarsOfOO() && (playersCurrentRoom.getRoomType() == RoomsOfInterest.EXIT)) {
            // is in exit but doesnt have all the pillars
            System.out.println("You found the EXIT but don't have all the Pillars");
        }

    }

    private void endScreenReadOut() {
        long totalDuration = System.currentTimeMillis() - DungeonAdventure.getMyTimeStart();
        long minutes = (totalDuration / 1000) / 60;
        long seconds = (totalDuration / 1000) % 60;
        System.out.printf("\nPlayer: %s as Hero %s", getMyCharacterName(), getClass().getSimpleName());
        System.out.printf("\nTime: %d:%d", minutes, seconds);
        System.out.printf("\nHealth remaining: %d", getMyHealthPoints());
        System.out.printf("\nMonsters Killed: %d", DungeonAdventure.getKillCount());
        System.exit(1);
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


    public boolean useHealthPotion() {
        for (int index = 0; index < this.myInventory.size(); index++) {
            if (this.myInventory.get(index).getClass().equals(HealthPotion.class)) {
                HealthPotion healthPotion = (HealthPotion) this.myInventory.remove(index);
                this.setMyHealthPoints(this.getMyHealthPoints() + healthPotion.getMyHealthToBeRegained());
                return true;
            }
        }
        return false;
    }

    public boolean useVisionPotion() {
        for (int index = 0; index < this.myInventory.size(); index++) {
            if (this.myInventory.get(index).getClass().equals(VisionPotion.class)) {
                VisionPotion visionPotion = (VisionPotion) this.myInventory.remove(index);
                // TODO implement code to activate vision
                return true;
            }
        }
        return false;
    }

    public boolean doPitDamage() {
        for (int index = 0; index < this.myInventory.size(); index++) {
            if (this.myInventory.get(index).getClass().equals(Pit.class)) {
                Pit pit = (Pit) this.myInventory.remove(index);
                this.setMyHealthPoints(this.getMyHealthPoints() + pit.getMyHealthToBeDamaged());
                return true;
            }
        }
        return false;
    }

    /**
     * Picks up and places a room item in the heroes inventory.
     * @param theItemToPickUp The room item to pick up.
     * @return True if the item was successfully placed in the inventory and false otherwise.
     */
    public boolean pickUpRoomItem(RoomItem theItemToPickUp) {
        return this.myInventory.add(theItemToPickUp);
    }

    public String displayInventory() {
        StringBuilder result = new StringBuilder("Inventory:[");
        for (int i = 0; i < myInventory.size() - 1; i++) {
            result.append(myInventory.get(i).myName + ", ");
        }
        if (!myInventory.isEmpty()) {
            result.append(myInventory.get(myInventory.size() - 1).myName + "]");
        } else {
            result.append("Empty]");
        }
        return result + "\n";
    }

    public abstract boolean specialSkill(final DungeonCharacter theMonsterToAttack);

    // TODO write set method for chanceToAttack

}
