package model.DungeonCharacterComponents.DungeonCharacters;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonObject;
import model.RoomItemComponents.RoomItems.RoomItem;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Timon Tukei
 */
public abstract class DungeonCharacter extends DungeonObject {

    /**
     * The name of the character.
     */
    private String myCharacterName;

    /**
     * The characters' health points.
     */
    private int myHealthPoints;

    /**
     * The characters' damage range.
     */
    private DamageRange myDamageRange;

    /**
     * The characters' attack speed.
     */
    private int myAttackSpeed;

    /**
     * The characters' chance to hit.
     */
    private double myChanceToHit;

    /**
     * Indicates whether the character is alive or not.
     */
    private boolean myDeceased;

    /**
     * The items the dungeon character has collected.
     */
    private ArrayList<RoomItem> myInventory;

    /**
     *
     * @param theCharacterName
     * @param theHealthPoints
     * @param theDamageRange
     * @param theAttackSpeed
     * @param theChanceToHit
     */
    protected DungeonCharacter(final String theCharacterName, int theHealthPoints, final DamageRange theDamageRange,
                               final int theAttackSpeed, final double theChanceToHit) {
        super();
        this.myCharacterName = theCharacterName;
        this.myHealthPoints = theHealthPoints;
        this.myDamageRange = theDamageRange;
        this.myAttackSpeed = theAttackSpeed;
        this.myChanceToHit = theChanceToHit;
        this.myDeceased = false;
        this.myInventory = new ArrayList<RoomItem>();
    }

    @Override
    public void tick() {
        super.tick();
        isDeceased();
    }

    // TODO Create a method that can either return whats in the inventory and or uses whats in the inventory

    /**
     * Collects an item an places it in the dungeon characters backpack.
     * @param theItemToCollect
     * @return
     */
    public boolean collectItem(final RoomItem theItemToCollect) {
        try {
            //maybe have health pass "H:5-15" in constructor so when added it can add String so
            //myInventory can be a list of strings?? ask tim for his thoughts
            this.myInventory.add(theItemToCollect);
            return true;
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Attacks an opponent.
     * @return The success of the attack.
     */
    public boolean attack(final DungeonCharacter theCharacterToAttack) {
        // Randomly gets a number between the damage range.
        final int damage = getTheDamageToBeDealt();
        if (myChanceToHit == theCharacterToAttack.getMyChanceToHit() || Math.random() < this.getMyChanceToHit()) {
            theCharacterToAttack.setMyHealthPoints(theCharacterToAttack.getMyHealthPoints() - damage);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the characters' name.
     * @return the characters' name.
     */
    public String getMyCharacterName() {
        return myCharacterName;
    }

    /**
     * Sets my characters' name.
     * @param myCharacterName
     */
    public void setMyCharacterName(String myCharacterName) {
        this.myCharacterName = myCharacterName;
    }

    /**
     * Gets the characters' health points.
     * @return the characters' health points.
     */
    public int getMyHealthPoints() {
        return myHealthPoints;
    }

    /**
     * Sets the characters' health points.
     * @param myHealthPoints The new health points
     */
    public void setMyHealthPoints(int myHealthPoints) {
        this.myHealthPoints = myHealthPoints;
    }

    /**
     * Gets the characters' damage range.
     * @return the characters' damage range.
     */
    public DamageRange getMyDamageRange() {
        return myDamageRange;
    }

    /**
     * Sets the characters' damage range.
     * @param myDamageRange The new damage range
     */
    public void setMyDamageRange(DamageRange myDamageRange) {
        this.myDamageRange = myDamageRange;
    }

    /**
     * Gets the characters' attack speed.
     * @return The characters' attack speed.
     */
    public int getMyAttackSpeed() {
        return myAttackSpeed;
    }

    /**
     * Sets the characters' attack speed.
     * @param myAttackSpeed The new attack speed.
     */
    public void setMyAttackSpeed(int myAttackSpeed) {
        this.myAttackSpeed = myAttackSpeed;
    }

    /**
     * Gets the characters' probability of hitting an opponent
     * @return The chance to hit
     */
    public double getMyChanceToHit() {
        return myChanceToHit;
    }

    /**
     * Sets the characters' probability to hit an opponent.
     * @param myChanceToHit The new probability to hit.
     */
    public void setMyChanceToHit(double myChanceToHit) {
        this.myChanceToHit = myChanceToHit;
    }

    public boolean isDeceased() {
        if (this.myHealthPoints <= 0) {
            setMarkedForDeath(true);
        }
        return isMarkedForDeath();
    }

    /**
     * Gets a number between the damage range randomly.
     * @return The damage to be dealt.
     */
    public int getTheDamageToBeDealt() {
        Random random = new Random();
        return random.nextInt(getMyDamageRange().getMyUpperBound() - getMyDamageRange().getMyLowerBound()) + getMyDamageRange().getMyLowerBound();
    }

    public String displayInventory() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < myInventory.size() - 1; i++) {
            result.append(myInventory.get(i).getClass() + ", ");
        }
        result.append(myInventory.get(myInventory.size() - 1) + "]");
        return result.toString();
    }

    @Override
    public String toString() {
        return this.getClass() + " DungeonCharacter{" +
                "myCharacterName='" + myCharacterName + '\'' +
                ", myHealthPoints=" + myHealthPoints +
                ", myDamageRange=" + myDamageRange +
                ", myAttackSpeed=" + myAttackSpeed +
                ", myChanceToHit=" + myChanceToHit +
                '}';
    }
}
