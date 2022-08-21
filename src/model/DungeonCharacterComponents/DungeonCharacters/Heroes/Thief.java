package model.DungeonCharacterComponents.DungeonCharacters.Heroes;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
     * Thief rolls for surprise attack in addition to attacking as normal
     * @param theTarget
     * @return
     */
    @Override
    public String attack(DungeonCharacter theTarget) {
        if (ThreadLocalRandom.current().nextDouble() < mySurpriseAttackSuccessProb){
            int surpriseAttackDamage = specialSkill(theTarget);
            return getMyCharacterName() + " makes a surprise attack for " + surpriseAttackDamage + " damage!";
        }
        return super.attack(theTarget);
    }

    /**
     * Surprise attacks an opponent.
     * @return The success of the surprise attack.
     */
    public int specialSkill(final DungeonCharacter theMonsterToAttack) {
        DamageRange dr = new DamageRange(100, 200);
        Random random = new Random();
        // Randomly gets a number between the damageToBeDealt range.
        final int damageToBeDealt = random.nextInt(dr.getMyUpperBound() - dr.getMyLowerBound()) + dr.getMyLowerBound();
        theMonsterToAttack.setMyHealthPoints(theMonsterToAttack.getMyHealthPoints() - damageToBeDealt);
        return damageToBeDealt;
    }

    @Override
    public String toString() {
        return "Thief{" +
                "mySurpriseAttackSuccessProb=" + mySurpriseAttackSuccessProb +
                super.toString() + '}';
    }
}
