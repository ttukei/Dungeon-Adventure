package model.DungeonCharacterComponents.DungeonCharacters.Monsters;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.HealingRange;

public class MonsterFactory {

    /**
     * Gremlins Stats
     */
    private static final int GREMLIN_HEALTH_POINTS = 70;
    private static final DamageRange GREMLIN_DAMAGE_RANGE = new DamageRange(15, 30);
    private static final int GREMLIN_ATTACK_SPEED = 5;
    private static final double GREMLIN_CHANCE_TO_HIT = 0.8;
    private static final double GREMLIN_CHANCE_TO_HEAL = 0.4;
    private static final HealingRange GREMLIN_HEALING_RANGE = new HealingRange(20, 40);

    /**
     * Ogres Stats
     */
    private static final int OGRE_HEALTH_POINTS = 200;
    private static final DamageRange OGRE_DAMAGE_RANGE = new DamageRange(30, 60);
    private static final int OGRE_ATTACK_SPEED = 2;
    private static final double OGRE_CHANCE_TO_HIT = 0.6;
    private static final double OGRE_CHANCE_TO_HEAL = 0.1;
    private static final HealingRange OGRE_HEALING_RANGE = new HealingRange(30, 60);

    /**
     * Skeletons Stats
     */
    private static final int SKELETON_HEALTH_POINTS = 100;
    private static final DamageRange SKELETON_DAMAGE_RANGE = new DamageRange(30, 50);
    private static final int SKELETON_ATTACK_SPEED = 3;
    private static final double SKELETON_CHANCE_TO_HIT = 0.8;
    private static final double SKELETON_CHANCE_TO_HEAL = 0.3;
    private static final HealingRange SKELETON_HEALING_RANGE = new HealingRange(30, 50);

    /**
     * Inhibits external instantiation.
     */
    private MonsterFactory() {}

    /**
     * Instantiates an instance of type <code>Monster</code>.
     * @param theMonsterToInstantiate One of the three child classes of <code>Monster</code>.
     * @param theMonstersName The name of the monster.
     * @return an instance of type <code>Monster</code>
     */
    public static Monster instantiateMonster(final Monsters theMonsterToInstantiate, final String theMonstersName) {
        switch (theMonsterToInstantiate) {
            case GREMLIN -> {
                return new Gremlin(theMonstersName, GREMLIN_HEALTH_POINTS, GREMLIN_DAMAGE_RANGE,
                        GREMLIN_ATTACK_SPEED, GREMLIN_CHANCE_TO_HIT, GREMLIN_CHANCE_TO_HEAL, GREMLIN_HEALING_RANGE);
            }
            case OGRE -> {
                return new Ogre(theMonstersName, OGRE_HEALTH_POINTS, OGRE_DAMAGE_RANGE,
                        OGRE_ATTACK_SPEED, OGRE_CHANCE_TO_HIT, OGRE_CHANCE_TO_HEAL, OGRE_HEALING_RANGE);
            }
            case SKELETON -> {
                return new Skeleton(theMonstersName, SKELETON_HEALTH_POINTS, SKELETON_DAMAGE_RANGE,
                        SKELETON_ATTACK_SPEED, SKELETON_CHANCE_TO_HIT, SKELETON_CHANCE_TO_HEAL, SKELETON_HEALING_RANGE);
            }
        }
        return null;
    }
}
