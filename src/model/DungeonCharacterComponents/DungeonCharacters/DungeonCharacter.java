package model.DungeonCharacterComponents.DungeonCharacters;

import controller.DungeonAdventure;
import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import model.DungeonComponents.DataTypes.Coordinates;
import model.DungeonObject;
import model.RoomItemComponents.RoomItem;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static controller.DungeonAdventure.clamp;

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
     * The characters' maximum possible health points.
     */
    private int myMaxHealthPoints;

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
     * Indicates whether they are in combat or not.
     */
    private boolean myCombatFlag;

    /**
     * Indicates whether the character is alive or not.
     */
    private boolean myDeceased;

    private Coordinates myCords;

    /**
     * The items the dungeon character has collected.
     */
    private ArrayList<RoomItem> myInventory;

    protected DungeonCharacter myTarget;

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
        this.myMaxHealthPoints = myHealthPoints;
        this.myDamageRange = theDamageRange;
        this.myAttackSpeed = theAttackSpeed;
        this.myChanceToHit = theChanceToHit;
        this.myDeceased = false;
        this.myInventory = new ArrayList<RoomItem>();
    }

    @Override
    public void objectBehavior() {
        super.objectBehavior();
//        System.out.println(getMyCharacterName() + " checks their behavior");
        if (isInCombat()){
            String combatMessage =  attack(getMyTarget());
            System.out.println(combatMessage);
            DungeonAdventure.updateReportPanel(combatMessage);
            boolean myTargetDied = myTarget.didIDie();
//            System.out.println("Did " + this.getMyTarget().getMyCharacterName() + " die?: " + myTargetDied);
//            if (myTarget.isMarkedForDeath()){
////                myTarget.killMe();
//            }
            if (myTargetDied){
                DungeonAdventure.addToKillCount();
                setCombatFlag(false);
//                System.out.println(getMyCharacterName() + " combat status: " + isInCombat());
                System.out.println(getMyCharacterName() + " defeated " + getMyTarget().getMyCharacterName());
                DungeonAdventure.updateReportPanel(getMyCharacterName() + " defeated " + getMyTarget().getMyCharacterName());
            }
        }
        if (!isInCombat()){
            outOfCombatBehavior();
        }
    }

    protected void outOfCombatBehavior() {

    }

    // TODO write a method called applyDamage
    // TODO override applyDamage in hero incorporating chance to defend
    /**
     * Attacks specifically a hero (called on an object of type <code>Monster</code>).
     * @return The success of the attack.
     */
    public String attack(final DungeonCharacter theTarget) {
        int theDamageToBeDealt = 0;
        int theDamageTaken = 0;
        if (ThreadLocalRandom.current().nextDouble() < this.getMyChanceToHit()) {
            theDamageToBeDealt = this.getTheDamageToBeDealt();
            theDamageTaken = theTarget.takeDamage(theDamageToBeDealt);
        }
        return getMyCharacterName() +
                " attacks " +
                getMyTarget().getMyCharacterName() +
                " and deals " + theDamageTaken;
    }

    public int takeDamage(int theDamageToTake){
        this.setMyHealthPoints(this.getMyHealthPoints() - theDamageToTake);
        return theDamageToTake;
    }

    public boolean didIDie() {
        if (this.myHealthPoints <= 0) {
            setMarkedForDeath(true);
        }
        return isMarkedForDeath();
    }

    /**
     * Gets the characters' name.
     * @return the characters' name.
     */
    public String getMyCharacterName() {
        return myCharacterName;
    }

    /**
     * Gets the characters' health points.
     * @return the characters' health points.
     */
    public int getMyHealthPoints() {
        if (myHealthPoints <= 0) {
            return 0;
        }
        return myHealthPoints;
    }

    /**
     * @return the characters maximum health points
     */
    protected int getMyMaxHealthPoints() {
        return myMaxHealthPoints;
    }

    // TODO check data before assignment
    /**
     * Sets the characters' health points.
     * @param theHealthPoints The new health points
     */
    public void setMyHealthPoints(int theHealthPoints) {
        this.myHealthPoints = clamp(theHealthPoints, 0, myMaxHealthPoints);
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
     * Gets the characters' probability of hitting an opponent
     * @return The chance to hit
     */
    public double getMyChanceToHit() {
        return myChanceToHit;
    }

    /**
     * Gets a number between the damage range randomly.
     * @return The damage to be dealt.
     */
    public int getTheDamageToBeDealt() {
        Random random = new Random();
        return random.nextInt(getMyDamageRange().getMyUpperBound() - getMyDamageRange().getMyLowerBound()) + getMyDamageRange().getMyLowerBound();
    }

    public boolean isInCombat(){
//        System.out.println("Is " + getMyCharacterName() + " in combat?: " + myCombatFlag);
        return myCombatFlag;
    }

    public void setCombatFlag(boolean theCombatFlag){
        this.myCombatFlag = theCombatFlag;
    }

    public DungeonCharacter getMyTarget(){
        if (myTarget == null){
            System.out.println(getMyCharacterName() + "'s target is null");
        }
        return myTarget;
    }

    public void setMyTarget(DungeonCharacter theTarget){
        this.myTarget = theTarget;
    }

    public Coordinates getMyCords(){
        return myCords;
    }

    public void setMyCords(final Coordinates theCords){
        myCords = theCords;
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
