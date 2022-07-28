package model.DungeonCharacterComponents.DungeonCharacters.Monsters;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.HealingRange;

/**
 * @author Timon Tukei
 */
final public class Skeleton extends Monster {

    /**
     * Constructs an instance of Skeleton.
     * @param theCharacterName The name of the character.
     * @param theHealthPoints The characters' health points.
     * @param theDamageRange The characters' damage range.
     * @param theAttackSpeed The characters' attack speed.
     * @param theChanceToHit The characters' probability to hit an opponent.
     */
    public Skeleton(final String theCharacterName, final int theHealthPoints, final DamageRange theDamageRange,
                    final int theAttackSpeed, final double theChanceToHit, final double theChanceToHeal,
                    final HealingRange theHealingRange) {
        super(theCharacterName, theHealthPoints, theDamageRange,
                theAttackSpeed, theChanceToHit, theChanceToHeal,
                theHealingRange);
    }
}
