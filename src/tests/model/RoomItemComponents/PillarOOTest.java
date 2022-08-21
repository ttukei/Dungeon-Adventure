package model.RoomItemComponents;

import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Heroes;
import model.DungeonComponents.Dungeon;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PillarOOTest {

    private DungeonCharacter myHero;

    private PillarOO myA;

    private PillarOO myI;

    private PillarOO myE;

    private PillarOO myP;

    @BeforeEach
    void setUp() {
        myHero = HeroFactory.instantiateHero(Heroes.WARRIOR, "War");
        myA = new PillarOO(PillarsOO.ABSTRACTION);
        myI = new PillarOO(PillarsOO.INHERITANCE);
        myE = new PillarOO(PillarsOO.ENCAPSULATION);
        myP = new PillarOO(PillarsOO.POLYMORPHISM);
    }

    @AfterEach
    void tearDown() {
        myA = null;
        myI = null;
        myE = null;
        myP = null;
    }

    @Test
    void placePillar() {
    }
}