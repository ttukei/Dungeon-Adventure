package model.DungeonCharacterComponents.DungeonCharacters.Heroes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroFactoryTest {


    private Hero theHeroToTest;

    @Test
    void instantiateHero() {
        theHeroToTest = HeroFactory.instantiateHero(Heroes.THIEF, "Jack");
        assertEquals(theHeroToTest.getClass(), Thief.class);
        theHeroToTest = HeroFactory.instantiateHero(Heroes.WARRIOR, "War");
        assertEquals(theHeroToTest.getClass(), Warrior.class);
        theHeroToTest = HeroFactory.instantiateHero(Heroes.PRIESTESS, "Jane");
        assertEquals(theHeroToTest.getClass(), Priestess.class);
    }
}