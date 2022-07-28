package model.DungeonCharacterComponents.DungeonCharacters.Heros;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Gremlin;
import model.RoomItemComponents.RoomItems.HealthPotion;

import java.util.Random;

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

    public void tick(){
        // Put something that the warrior checks every round here

        if (getMyHealthPoints() <= 0){
            setMarkedForDeath(true);
        }
    }


    /**
     * The warrior has a special skill (crushing blow) that is similar to attack but does more damage.
     * @param theMonsterToAttack The opponent.
     * @return The success of the attack.
     */
    public boolean specialAttack(final DungeonCharacter theMonsterToAttack) {
        DamageRange dr = new DamageRange(75, 175);
        Random random = new Random();
        // Randomly gets a number between the damageToBeDealt range.
        final int damageToBeDealt = random.nextInt(dr.getMyUpperBound() - dr.getMyLowerBound()) + dr.getMyLowerBound();
        if (Math.random() < myCrushingBlowSuccessProb) {
            theMonsterToAttack.setMyHealthPoints(theMonsterToAttack.getMyHealthPoints() - damageToBeDealt);
            return true;
        } else {
            return false;
        }
    }
}
