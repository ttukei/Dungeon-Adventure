package model.RoomItemComponents.RoomItems;

import model.DungeonCharacterComponents.DungeonCharacters.Heros.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.Heroes;

public class InteractionTest {

    public static void main(String[] args) {
        Hero hero = HeroFactory.instantiateHero(Heroes.WARRIOR, "The Hero");
        HealthPotion health = new HealthPotion();
        hero.collectItem(health);
        System.out.println(health.getMyHitPoint());
        System.out.println(hero.displayInventory());
        //to test the new repo
    }
}
