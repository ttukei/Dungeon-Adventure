package model.DungeonCharacterComponents.DungeonCharacters;

import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Heroes;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DungeonCharacterTest {

    private DungeonCharacter myHero;

    private DungeonCharacter myMonster;


    @BeforeAll
    void setUp() {
        myHero = HeroFactory.instantiateHero(Heroes.WARRIOR, "War");
        myMonster = new MonsterFactory().getMonster(Monsters.GREMLIN);
    }

    @AfterAll
    void tearDown() {
        myMonster = null;
        myHero = null;
    }

    @Test
    void tick() {
    }

    @Test
    void attack() {
        int theMonstersStartingHealth = myMonster.getMyHealthPoints();
        int theMonstersHealthNow = myMonster.getMyHealthPoints();
        for (int count = 0; count < 10; count++) {
            myHero.attack((Monster) myMonster);
            theMonstersHealthNow = myMonster.getMyHealthPoints();
        }
        assertTrue(theMonstersHealthNow < theMonstersStartingHealth);

        // Resurrect monster if deceased.
        myMonster.setMyHealthPoints(100);

        int theHeroesStartingHealth = myHero.getMyHealthPoints();
        int theHeroesHealthNow = myHero.getMyHealthPoints();
        for (int count = 0; count < 10; count ++) {
            myMonster.attack((Hero) myHero);
            theHeroesHealthNow = myHero.getMyHealthPoints();
        }
        assertTrue(theMonstersStartingHealth > theHeroesHealthNow);

    }

    @Test
    void isDeceased() {
        myHero.setMyHealthPoints(0);
        assertTrue(myHero.didIDie());
        myMonster.setMyHealthPoints(0);
        assertTrue(myMonster.didIDie());

        myMonster.setMyHealthPoints(100);
        myHero.setMyHealthPoints(100);
    }

    @Test
    void getTheDamageToBeDealt() {
    }

    @Test
    void testToString() {
    }
}