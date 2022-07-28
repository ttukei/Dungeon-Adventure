package model.DungeonCharacterComponents.DungeonCharacters.Heros;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.HealingRange;

import java.util.Random;

/**
 * @author Timon Tukei
 */
final public class Priestess extends Hero {

    // TODO decide on a healing range for the priestess with team
    /**
     * The healing range of the priestess.
     */
    private final HealingRange myHealingRange;

    /**
     * Constructs an instance of warrior.
     * @param theCharacterName The name of the character.
     * @param theHealthPoints The characters' health points.
     * @param theDamageRange The characters' damage range.
     * @param theAttackSpeed The characters' attack speed.
     * @param theChanceToHit The characters' probability to hit an opponent.
     * @param theChanceToDefend The character probability to defend against an attack.
     */
    public Priestess(final String theCharacterName, final int theHealthPoints, final DamageRange theDamageRange,
                   final int theAttackSpeed, final double theChanceToHit, final double theChanceToDefend,
                     final HealingRange theHealingRange) {
        super(theCharacterName, theHealthPoints, theDamageRange, theAttackSpeed,theChanceToHit, theChanceToDefend);
        this.myHealingRange = theHealingRange;
    }

    /**
     * Heals this priestess.
     */
    public boolean specialAttack(final DungeonCharacter theMonsterToAttack) {
        setMyHealthPoints(getMyHealthPoints() + getAmountOfHealthToBeRegained());
        return true;
    }

    /**
     * Randomly generates an amount of health to be regained within <code>myHealingRange</code>
     * @return
     */
    private int getAmountOfHealthToBeRegained() {
        Random random = new Random();
        return random.nextInt(this.myHealingRange.getMyUpperBound() - this.myHealingRange.getMyLowerBound()) + this.myHealingRange.getMyLowerBound();
    }

    @Override
    public String toString() {
        return "Priestess{" +
                "myHealingRange=" + myHealingRange +
                "} " + super.toString();
    }
}
