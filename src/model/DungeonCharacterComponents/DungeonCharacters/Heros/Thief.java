package model.DungeonCharacterComponents.DungeonCharacters.Heros;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;

/**
 * @author Timon Tukei
 */
final public class Thief extends Hero {

    /**
     * The probability that of the thief landing a suprise attack.
     */
    private final double mySurpriseAttackSuccessProb;

    /**
     * Constructs an instance of warrior.
     * @param theCharacterName The name of the character.
     * @param theHealthPoints The characters' health points.
     * @param theDamageRange The characters' damage range.
     * @param theAttackSpeed The characters' attack speed.
     * @param theChanceToHit The characters' probability to hit an opponent.
     * @param theChanceToDefend The character probability to defend against an attack.
     */
    public Thief(final String theCharacterName, final int theHealthPoints, final DamageRange theDamageRange,
                   final int theAttackSpeed, final double theChanceToHit, final double theChanceToDefend,
                 final double theSurpriseAttackSuccessProb) {
        super(theCharacterName, theHealthPoints, theDamageRange, theAttackSpeed,theChanceToHit, theChanceToDefend);
        this.mySurpriseAttackSuccessProb = theSurpriseAttackSuccessProb;
    }

    /**
     * Surprise attacks an opponent.
     * @return The success of the surprise attack.
     */
    public boolean specialSkill(final DungeonCharacter theMonsterToAttack) {
        int damageToBeDealt = this.getTheDamageToBeDealt();
        if (Math.random() < mySurpriseAttackSuccessProb) {
            theMonsterToAttack.setMyHealthPoints(theMonsterToAttack.getMyHealthPoints() - damageToBeDealt);
            return true;
        } else {
            return false;
        }
    }
}
