package tests.model.DungeonCharacterComponents.DungeonCharacters.Heros;


import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Heroes;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WarriorTest {

    /**
     * The hero to test.
     */
    Hero myHero;

    /**
     * The test dummy.
     */
    Monster myMonster;

    /**
     * Instantiate a hero with the hero factory. Note that you can test other monsters
     * by simply changing the enum on line 27.
     */
    @BeforeAll
    void setUp() {
        myHero = HeroFactory.instantiateHero(Heroes.WARRIOR, "Jake");
        MonsterFactory monsterFactory = new MonsterFactory();
        myMonster = monsterFactory.getMonster(Monsters.GREMLIN);
    }

    /**
     * Destruct monster.
     */
    @AfterAll
    void tearDown() {
        myHero = null;
        myMonster = null;
    }

    /**
     * Test the special attack.
     */
    @Test
    void specialSkillTest() {
        int monstersStartingHealth = myMonster.getMyHealthPoints();
        int monstersHealthNow = myMonster.getMyHealthPoints();
        for (int count = 0; count < 10; count++) {
            myHero.specialSkill(myMonster);
            monstersHealthNow = myMonster.getMyHealthPoints();
        }
        assert(monstersStartingHealth > monstersHealthNow);
    }
}