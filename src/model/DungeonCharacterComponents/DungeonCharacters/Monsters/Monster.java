package model.DungeonCharacterComponents.DungeonCharacters.Monsters;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.HealingRange;
import static controller.DungeonAdventure.*;

/**
 * @author Timon Tukei
 */
public class Monster extends DungeonCharacter {

    // TODO create interface Healable and make priestess and monster implement interface

    private Monsters myMonsterType;
    /**
     * The probability of the monster healing.
     */
    private double myChanceToHeal;

    // TODO decide on a healing range for the Monsters with team

    /**
     * The healing range of the monster.
     */
    private HealingRange myHealingRange;

    /**
     * Instantiates a monster of type <code>Monsters</code> specified in the first parameter.
     * @param theCharacterName
     * @param theHealthPoints
     * @param theDamageRange
     * @param theAttackSpeed
     * @param theChanceToHit
     * @param theChanceToHeal
     */
    protected Monster(final Monsters theTypeOfMonster, final String theCharacterName, final int theHealthPoints,
                      final DamageRange theDamageRange, final int theAttackSpeed, final double theChanceToHit,
                      final double theChanceToHeal, final HealingRange theHealingRange) {
        super(theCharacterName, theHealthPoints, theDamageRange, theAttackSpeed, theChanceToHit);
        setMonsterType(theTypeOfMonster);
        setTheChanceToHeal(theChanceToHeal);
        setTheHealingRange(theHealingRange);
        setMyTarget(getMyHero());
    }

    /**
     * Attempts to heal the monster
     * @return whether the monster has healed.
     */
    protected int heal() {
        if (Math.random() < myChanceToHeal) {
            final int amountOfHealthToBeRegained = (int) (Math.random() * myHealingRange.getMyUpperBound() - myHealingRange.getMyLowerBound()) + myHealingRange.getMyLowerBound();
            setMyHealthPoints(getMyHealthPoints() + amountOfHealthToBeRegained);
            return amountOfHealthToBeRegained;
        }
        return 0;
    }

    public void objectBehavior() {
        super.objectBehavior();
        int healthRegained = heal();
        if (healthRegained > 0) {
            System.out.println(this.getMyName() + " has healed for " + healthRegained);
        }
    }

    private void setTheHealingRange(HealingRange theHealingRange) {
        this.myHealingRange = theHealingRange;
    }

    private void setTheChanceToHeal(double theChanceToHeal) {
        this.myChanceToHeal = theChanceToHeal;
    }

    private void setMonsterType(Monsters theTypeOfMonster) {
        this.myMonsterType = theTypeOfMonster;
    }

    public String getMyAnnouncement(){
        if (isMarkedForDeath()){
            return "";
        }
        String n = "";
        if (myMonsterType == Monsters.OGRE){
            n = "n";
        }
        return "\nThere is a" + n + " " + myMonsterType;
    }

    public Monsters getMyMonsterType() {
        return myMonsterType;
    }

    public double getMyChanceToHeal() {
        return myChanceToHeal;
    }

    public HealingRange getMyHealingRange() {
        return myHealingRange;
    }

    @Override
    public String getMyName() {
        return "" + myMonsterType;
    }
}
