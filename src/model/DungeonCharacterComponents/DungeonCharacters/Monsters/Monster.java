package model.DungeonCharacterComponents.DungeonCharacters.Monsters;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.HealingRange;

/**
 * @author Timon Tukei
 */
public abstract class Monster extends DungeonCharacter {

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
     *
     * @param theCharacterName
     * @param theHealthPoints
     * @param theDamageRange
     * @param theAttackSpeed
     * @param theChanceToHit
     * @param theChanceToHeal
     */
    protected Monster(final String theCharacterName, final int theHealthPoints, final DamageRange theDamageRange,
                      final int theAttackSpeed, final double theChanceToHit, final double theChanceToHeal,
                      final HealingRange theHealingRange) {
        super(theCharacterName, theHealthPoints, theDamageRange, theAttackSpeed, theChanceToHit);
        this.myChanceToHeal = theChanceToHeal;
        this.myHealingRange = theHealingRange;
    }

    /**
     * Attempts to heal the monster
     * @return whether the monster has healed.
     */
    protected boolean heal() {
        if (Math.random() < myChanceToHeal) {
            final int amountOfHealthToBeRegained = (int) (Math.random() * myHealingRange.getMyUpperBound() - myHealingRange.getMyLowerBound()) + myHealingRange.getMyLowerBound();
            setMyHealthPoints(getMyHealthPoints() + amountOfHealthToBeRegained);
            return true;
        }
        return false;
    }
}
