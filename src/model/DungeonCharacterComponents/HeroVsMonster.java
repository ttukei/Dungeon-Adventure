package model.DungeonCharacterComponents;

import model.DungeonCharacterComponents.DungeonCharacters.Heros.Hero;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heros.Heroes;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monster;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.OldMonsterFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Monsters.Monsters;

public class HeroVsMonster {
    public static void main(String... args) throws InterruptedException {
        Hero hero = HeroFactory.instantiateHero(Heroes.WARRIOR, "The Flash");
        Monster monster = OldMonsterFactory.instantiateMonster(Monsters.OGRE, "Jeff");

        Monsters m = Monsters.values()[1];
        System.out.println(m);

        /*while(!hero.isDeceased() && !monster.isDeceased()) {
            System.out.println("======BATTLING======");
            System.out.println("Hero: Health = " + hero.getMyHealthPoints());
            System.out.println("Monster: Health = " + monster.getMyHealthPoints());
            System.out.println();
            Thread.sleep(5000);
            hero.attack(monster);
            monster.attack(hero);

        }*/
    }
}
