package model.RoomItemComponents.RoomItems;

import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.Heroes;

public class InteractionTest {

    // Testing if a dungeon character can pick up a health potion and use it
    public static void main(String... args) {
        HealthPotion healthPotion = new HealthPotion();
        DungeonCharacter dungeonCharacter = HeroFactory.instantiateHero(Heroes.WARRIOR, "Jeff");
        System.out.println(dungeonCharacter);
        dungeonCharacter.pickUpRoomItem(healthPotion);
        System.out.println(dungeonCharacter.displayInventory());
        dungeonCharacter.useHealthPotion();
        System.out.println(dungeonCharacter);
    }
}
