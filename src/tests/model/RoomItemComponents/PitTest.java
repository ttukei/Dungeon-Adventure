package model.RoomItemComponents;

import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Heroes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitTest {
    private DungeonCharacter myHero;

    private Pit myPit;

    @BeforeEach
    void setUp() {
        myHero = HeroFactory.instantiateHero(Heroes.WARRIOR, "War");
        myPit = new Pit();
    }

    @AfterEach
    void tearDown() {
        myHero = null;
        myPit = null;
    }

    @Test
    void getMyHealthToBeDamaged() {
        int beforePit = myHero.getMyHealthPoints();
        myHero.setMyHealthPoints(myHero.getMyHealthPoints() + myPit.getMyHealthToBeDamaged());
        int afterPit = myHero.getMyHealthPoints();
        System.out.println(beforePit + ":" + afterPit);
        assertTrue(beforePit > afterPit);
    }
}