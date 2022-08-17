package model.DungeonCharacterComponents.DungeonCharacters.Heroes;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.HealingRange;

public class HeroFactory {

    /**
     * Warrior Stats
     */
    private static final int WARRIOR_HEALTH_POINTS = 125;
    private static final DamageRange WARRIOR_DAMAGE_RANGE = new DamageRange(35, 60);
    private static final int WARRIOR_ATTACK_SPEED = 4;
    private static final double WARRIOR_CHANCE_TO_HIT = 0.8;
    private static final double WARRIOR_CHANCE_TO_DEFEND = 0.2;
    private static final double WARRIOR_CRUSHING_BLOW_SUCCESS_PROB = 0.4;

    /**
     * Priestess Stats
     */
    private static final int PRIESTESS_HEALTH_POINTS = 75;
    private static final DamageRange PRIESTESS_DAMAGE_RANGE = new DamageRange(25, 45);
    private static final int PRIESTESS_ATTACK_SPEED = 5;
    private static final double PRIESTESS_CHANCE_TO_HIT = 0.7;
    private static final double PRIESTESS_CHANCE_TO_DEFEND = 0.3;
    private static final HealingRange PRIESTESS_HEALING_RANGE = new HealingRange(50, 100);

    /**
     * Thief Stats
     */
    private static final int THIEF_HEALTH_POINTS = 75;
    private static final DamageRange THIEF_DAMAGE_RANGE = new DamageRange(20, 40);
    private static final int THIEF_ATTACK_SPEED = 6;
    private static final double THIEF_CHANCE_TO_HIT = 0.8;
    private static final double THIEF_CHANCE_TO_DEFEND = 0.4;
    private static final double THIEF_SURPRISE_ATTACK_SUCCESS_PROB = 0.4;

    /**
     * Inhibits external instantiation.
     */
    private HeroFactory() {}

    /**
     * Instantiates an instance of type <code>Hero</code>.
     * @param theHeroToInstantiate One of the three child classes of <code>Hero</code>.
     * @param theHeroesName The name of the hero.
     * @return an instance of type <code>Hero</code>
     */
    public static Hero instantiateHero(final Heroes theHeroToInstantiate, final String theHeroesName) {
        switch (theHeroToInstantiate) {
            case WARRIOR -> {
                return new Warrior(theHeroesName, WARRIOR_HEALTH_POINTS, WARRIOR_DAMAGE_RANGE,
                        WARRIOR_ATTACK_SPEED, WARRIOR_CHANCE_TO_HIT, WARRIOR_CHANCE_TO_DEFEND,
                        WARRIOR_CRUSHING_BLOW_SUCCESS_PROB);
            }
            case PRIESTESS -> {
                return new Priestess(theHeroesName, PRIESTESS_HEALTH_POINTS, PRIESTESS_DAMAGE_RANGE,
                        PRIESTESS_ATTACK_SPEED, PRIESTESS_CHANCE_TO_HIT, PRIESTESS_CHANCE_TO_DEFEND,
                        PRIESTESS_HEALING_RANGE);
            }
            case THIEF -> {
                return new Thief(theHeroesName, THIEF_HEALTH_POINTS, THIEF_DAMAGE_RANGE, THIEF_ATTACK_SPEED,
                        THIEF_CHANCE_TO_HIT, THIEF_CHANCE_TO_DEFEND, THIEF_SURPRISE_ATTACK_SUCCESS_PROB);
            }
        }
        return null;
    }


}
