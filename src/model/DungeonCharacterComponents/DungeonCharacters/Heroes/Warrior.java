package model.DungeonCharacterComponents.DungeonCharacters.Heroes;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Timon Tukei
 */
final public class Warrior extends Hero {

    /**
     * The probability of the warrior landing a crushing blow to an opponent.
     */
    private double myCrushingBlowSuccessProb;

    /**
     * Constructs an instance of warrior.
     * @param theCharacterName The name of the character.
     * @param theHealthPoints The characters' health points.
     * @param theDamageRange The characters' damage range.
     * @param theAttackSpeed The characters' attack speed.
     * @param theChanceToHit The characters' probability to hit an opponent.
     * @param theChanceToDefend The character probability to defend against an attack.
     */
    public Warrior(final String theCharacterName, final int theHealthPoints, final DamageRange theDamageRange,
                   final int theAttackSpeed, final double theChanceToHit, final double theChanceToDefend,
                   final double theCrushingBlowSuccessProb) {
        super(theCharacterName, theHealthPoints, theDamageRange, theAttackSpeed,theChanceToHit, theChanceToDefend);
        this.myCrushingBlowSuccessProb = theCrushingBlowSuccessProb;
    }

    @Override
    public String attack(final DungeonCharacter theTarget) {
        if (ThreadLocalRandom.current().nextDouble() < myCrushingBlowSuccessProb){
            int crushingBlowDamage = specialSkill(theTarget);
            return getMyCharacterName() + " makes a crushing blow for " + crushingBlowDamage + " damage!";
        }
        return super.attack(theTarget);
    }


    /**
     * The warrior has a special skill (crushing blow) that is similar to attack but does more damage.
     * @param theMonsterToAttack The opponent.
     * @return The success of the attack.
     */
    public int specialSkill(final DungeonCharacter theMonsterToAttack) {
        DamageRange dr = new DamageRange(75, 175);
        Random random = new Random();
        // Randomly gets a number between the damageToBeDealt range.
        final int damageToBeDealt = random.nextInt(dr.getMyUpperBound() - dr.getMyLowerBound()) + dr.getMyLowerBound();
        theMonsterToAttack.setMyHealthPoints(theMonsterToAttack.getMyHealthPoints() - damageToBeDealt);
        return damageToBeDealt;
    }

    @Override
    public String toString() {
        return "Warrior{" +
                "myCrushingBlowSuccessProb=" + myCrushingBlowSuccessProb +
                super.toString() + '}';
    }
}
