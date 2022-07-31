package model.RoomItemComponents.RoomItems;

import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.Heroes;

public class InteractionTest {

    // Testing if a dungeon character can pick up a health potion and use it
    public static void main(String... args) {
        HealthPotion healthPotion = new HealthPotion();
        Pit pit = new Pit();
        DungeonCharacter dungeonCharacter = HeroFactory.instantiateHero(Heroes.WARRIOR, "Jeff");
        System.out.println(dungeonCharacter);
        System.out.println("-------Picked up Health Potion-------");
        dungeonCharacter.pickUpRoomItem(healthPotion);
        System.out.println(dungeonCharacter.displayInventory());
        System.out.println("-------Found a Pit-------");
        dungeonCharacter.pickUpRoomItem(pit);
        System.out.println(dungeonCharacter.displayInventory());
        System.out.println("-------Took Pit Damage-------");
        dungeonCharacter.doPitDamage();
        System.out.println(dungeonCharacter);
        System.out.println(dungeonCharacter.displayInventory());
        System.out.println("-------Used Health Potion-------");
        dungeonCharacter.useHealthPotion();
        System.out.println(dungeonCharacter);
        System.out.println(dungeonCharacter.displayInventory());

    }
}
