package model.RoomItemComponents;

import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Heroes;

public class InteractionTest {

    // Testing if a dungeon character can pick up a health potion and use it
    public static void main(String... args) {
        HealthPotion healthPotion = new HealthPotion();
        Pit pit = new Pit();
        Hero hero = HeroFactory.instantiateHero(Heroes.WARRIOR, "Jeff");
        System.out.println(hero);
        System.out.println("-------Picked up Health Potion-------");
        hero.pickUpRoomItem(healthPotion);
        System.out.println(hero.displayInventory());
        System.out.println("-------Found a Pit-------");
        hero.pickUpRoomItem(pit);
        System.out.println(hero.displayInventory());
        System.out.println("-------Took Pit Damage-------");
        hero.doPitDamage();
        System.out.println(hero);
        System.out.println(hero.displayInventory());
        System.out.println("-------Used Health Potion-------");
        hero.useHealthPotion();
        System.out.println(hero);
        System.out.println(hero.displayInventory());

    }
}
