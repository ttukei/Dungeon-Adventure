package controller;


import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.HeroFactory;
import model.DungeonCharacterComponents.DungeonCharacters.Heroes.Heroes;
import model.DungeonComponents.DataTypes.Coordinates;
import model.DungeonComponents.Dungeon;

import static model.DungeonComponents.Dungeon.getDungeon;

public class Controller {

    private static DungeonCharacter myHero;

    private static Dungeon myDungeon;

    private static Coordinates myHeroCoord;

    public static void main(String[] args) {
        // Start screen

        myHero = HeroFactory.instantiateHero(Heroes.WARRIOR, "War");
        myDungeon = new Dungeon();
        myHeroCoord = new Coordinates(0, 0);
        myDungeon.printDungeonMap();
        while(!myHero.isDeceased()) {


        }



    }


}