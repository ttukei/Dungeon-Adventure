package model.RoomItemComponents;

import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Heroes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthPotionTest {

    private DungeonCharacter myHero;

    private HealthPotion myHP;

    @BeforeEach
    void setUp() {
        myHero = HeroFactory.instantiateHero(Heroes.WARRIOR, "War");
        myHP = new HealthPotion();
    }

    @AfterEach
    void tearDown() {
        myHero = null;
        myHP = null;
    }

    @Test
    void getMyHealthToBeRegained() {
        int beforeHP = myHero.getMyHealthPoints();
        myHero.setMyHealthPoints(myHero.getMyHealthPoints() + myHP.getMyHealthToBeRegained());
        int afterHP = myHero.getMyHealthPoints();
        System.out.println(beforeHP + ":" + afterHP);
        assertTrue(afterHP > beforeHP);
    }
}