package model.DungeonCharacterComponents.DungeonCharacters.Monsters;

import java.util.HashMap;
import java.util.Map;

public enum Monsters {
    GREMLIN(1), OGRE(2), SKELETON(3);

    private final int myMonsterType;

    private static final Map monsters = new HashMap();

    private Monsters(final int theMonsterType) {
        this.myMonsterType = theMonsterType;
    }

}
