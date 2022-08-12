package model.DungeonCharacterComponents.DungeonCharacters.Heroes;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonComponents.Dungeon;
import model.DungeonComponents.Room;
import model.RoomItemComponents.*;

import java.util.ArrayList;

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
    }

    @Override
    public void objectBehavior(){
        super.objectBehavior();
    }

    @Override
    protected void outOfCombatBehavior(){
        Room playersCurrentRoom = Dungeon.getDungeon().getPlayersCurrentRoom();
        PillarOO pillarOO = null;
        if (playersCurrentRoom.containsPillar()) {
            for (RoomItem roomItem : playersCurrentRoom.getMyRoomItems()) {
                if (roomItem.getClass() == PillarOO.class) {
                    pillarOO = (PillarOO) roomItem;
                }
            }
            System.out.println("You have found " + pillarOO);
        }
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
        StringBuilder result = new StringBuilder("[");
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
    // TODO change specialAttack to specialSkill


}
