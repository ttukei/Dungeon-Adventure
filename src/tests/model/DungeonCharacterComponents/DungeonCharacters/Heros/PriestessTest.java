package tests.model.DungeonCharacterComponents.DungeonCharacters.Heros;

import model.DungeonCharacterComponents.DungeonCharacters.Heros.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.Heroes;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PriestessTest {

    /**
     * The priestess to test
     */
    Hero myHero;

    @BeforeAll
    void setUp() {
        myHero = HeroFactory.instantiateHero(Heroes.PRIESTESS, "Jane");
    }

    @AfterAll
    void tearDown() {
        myHero = null;
    }

    @Test
    void specialSkillTest() {
        MonsterFactory monsterFactory = new MonsterFactory();
        Monster monster = monsterFactory.getMonster(Monsters.GREMLIN);
        int heroesStartingHealth = myHero.getMyHealthPoints();
        int heroesHealthNow = myHero.getMyHealthPoints();
        for (int count = 0; count < 10; count++) {
            myHero.specialSkill(monster);
            heroesHealthNow = myHero.getMyHealthPoints();
        }
        assertTrue(heroesHealthNow > heroesStartingHealth);
    }
}