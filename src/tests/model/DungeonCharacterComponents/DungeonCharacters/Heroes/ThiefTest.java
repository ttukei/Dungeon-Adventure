package model.DungeonCharacterComponents.DungeonCharacters.Heroes;

import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.MonsterFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ThiefTest {

    private Hero myHero;

    private Monster myTestDummy;

    @BeforeAll
    void setUp() {
        myHero = HeroFactory.instantiateHero(Heroes.THIEF, "Jack");
        myTestDummy = new MonsterFactory().getMonster(Monsters.GREMLIN);
    }

    @AfterAll
    void tearDown() {
        myHero = null;
        myTestDummy = null;
    }

    @Test
    void specialAttack() {
        int theTestDummyHealthBefore = myTestDummy.getMyHealthPoints();
        int theTestDummyHealthNow = myTestDummy.getMyHealthPoints();
        for (int count = 0; count < 10; count++) {
            myHero.specialSkill(myTestDummy);
            theTestDummyHealthNow = myTestDummy.getMyHealthPoints();
        }
        assertTrue(theTestDummyHealthBefore > theTestDummyHealthNow);
    }
}