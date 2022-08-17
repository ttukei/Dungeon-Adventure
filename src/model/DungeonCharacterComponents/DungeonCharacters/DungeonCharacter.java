package model.DungeonCharacterComponents.DungeonCharacters;

import controller.DungeonAdventure;
import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Hero;
import model.DungeonComponents.DataTypes.Coordinates;
import model.DungeonObject;
import model.RoomItemComponents.RoomItem;

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
            String combatMessage =  getMyCharacterName() +
                                    " attacks " +
                                    getMyTarget().getMyCharacterName() +
                                    " and deals " +
                                    attack(getMyTarget()) +
                                    " damage";
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
        // getTarget() uses some methodology determined at a lower inheritance level to
                    // select targets by testing that methodology against the objects of the
                    // type determined that are in the handler
        // inCombat() checks if in combat
            // combatActions()
                // attackTarget() overloads a target or checks for null to not attack

    }

    protected void outOfCombatBehavior() {

    }

    // TODO write a method called applyDamage
    // TODO override applyDamage in hero incorporating chance to defend
    /**
     * Attacks an opponent.
     * @return The success of the attack.
     */
    public String attack(final DungeonCharacter theCharacterToAttack) {
        // Randomly gets a number between the damage range.
        if ((myChanceToHit == theCharacterToAttack.getMyChanceToHit() || Math.random() < this.getMyChanceToHit()) && !theCharacterToAttack.isMarkedForDeath()) {
            final int damage = getTheDamageToBeDealt();
            theCharacterToAttack.setMyHealthPoints(theCharacterToAttack.getMyHealthPoints() - damage);
            return "" + damage;
        } else {
            return "0";
        }
    }

    public boolean didIDie() {
        if (this.myHealthPoints <= 0) {
            setMarkedForDeath(true);
        }
        return isMarkedForDeath();
    }

    public boolean isDeceased() {
        return this.myHealthPoints <= 0;
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
        if (myHealthPoints <= 0) {
            return 0;
        }
        return myHealthPoints;
    }
    // TODO check data before assignment
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
        if (myAttackSpeed < 0) {
            throw new IllegalArgumentException("Attack speed cannot be negative");
        }
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
