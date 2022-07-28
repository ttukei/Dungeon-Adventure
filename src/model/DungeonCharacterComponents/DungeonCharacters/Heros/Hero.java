package model.DungeonCharacterComponents.DungeonCharacters.Heros;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.RoomItemComponents.RoomItems.*;

import java.util.ArrayList;

/**
 * @author Timon Tukei
 */
public abstract class Hero extends DungeonCharacter {

    /**
     * Probability to defend against an attack.
     */
    private final double myChanceToDefend;



    /**
     *
     * @param theCharacterName
     * @param theHealthPoints
     * @param theDamageRange
     * @param theAttackSpeed
     * @param theChanceToHit
     * @param theChanceToDefend
     */
    protected Hero(final String theCharacterName, final int theHealthPoints, final DamageRange theDamageRange,
                   final int theAttackSpeed, final double theChanceToHit, final double theChanceToDefend) {
        super(theCharacterName, theHealthPoints, theDamageRange, theAttackSpeed, theChanceToHit);
        this.myChanceToDefend = theChanceToDefend;
    }

    public abstract boolean specialAttack(final DungeonCharacter theMonsterToAttack);



}
