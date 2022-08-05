package model.DungeonCharacterComponents.DungeonCharacters.Monsters;

import model.DungeonCharacterComponents.DamageRange;
import model.DungeonCharacterComponents.HealingRange;

import java.sql.*;
import java.util.ArrayList;

public class MonsterFactory {

    /**
     * The sqlight database url.
     */
    private static final String myURL = "jdbc:sqlite:Resources/Monsters.db";

    /**
     * The query.
     */
    private static final String myQuery = "SELECT * FROM Monsters";

    /**
     * Monsters
     */
    private ArrayList<Monster> myMonsters;

    public MonsterFactory() {
        myMonsters = getMonsters();
    }

    /**
     * Connects to a sqlight database.
     * @return
     */
    private Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(myURL);
        } catch (SQLException exception) {
            System.out.println(exception);
            exception.printStackTrace();
        }
        return connection;
    }

    /**
     * Retrieves data about monsters from sqlite database,
     * constructs monsters with that data, then returns an
     * array list with all the monsters from the data.
     * @return
     */
    private ArrayList<Monster> getMonsters() {
        ArrayList<Monster> monsters = new ArrayList<>();
        try(Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet monsterData = statement.executeQuery(myQuery);
            while(monsterData.next()) {
                Monsters monster = Monsters.values()[monsterData.getInt("Monster Type") - 1];
                switch(monster) {
                    case GREMLIN -> {
                        monsters.add(new Gremlin(monsterData.getString("Name"),
                                monsterData.getInt("Health"),
                                new DamageRange(monsterData.getInt("Minimum Damage"),
                                        monsterData.getInt("Maximum Damage")),
                                monsterData.getInt("Attack Speed"),
                                monsterData.getDouble("Chance to Hit"),
                                monsterData.getDouble("Chance to Heal"),
                                new HealingRange(monsterData.getInt("Minimum Healing Points"),
                                        monsterData.getInt("Maximum Healing Points"))));
                    }
                    case SKELETON -> {
                        monsters.add(new Skeleton(monsterData.getString("Name"),
                                monsterData.getInt("Health"),
                                new DamageRange(monsterData.getInt("Minimum Damage"),
                                        monsterData.getInt("Maximum Damage")),
                                monsterData.getInt("Attack Speed"),
                                monsterData.getDouble("Chance to Hit"),
                                monsterData.getDouble("Chance to Heal"),
                                new HealingRange(monsterData.getInt("Minimum Healing Points"),
                                        monsterData.getInt("Maximum Healing Points"))));
                    }
                    case OGRE -> {
                        monsters.add(new Ogre(monsterData.getString("Name"),
                                monsterData.getInt("Health"),
                                new DamageRange(monsterData.getInt("Minimum Damage"),
                                        monsterData.getInt("Maximum Damage")),
                                monsterData.getInt("Attack Speed"),
                                monsterData.getDouble("Chance to Hit"),
                                monsterData.getDouble("Chance to Heal"),
                                new HealingRange(monsterData.getInt("Minimum Healing Points"),
                                        monsterData.getInt("Maximum Healing Points"))));
                    }
                 }
            }
        } catch (SQLException exception) {
            System.out.println(exception);
            exception.printStackTrace();
        }
        return monsters;
    }

    public ArrayList<Monster> getMyMonsters() {
        return this.myMonsters;
    }

    /**
     * Randomly picks from a set of monsters of type specified in the parameter.
     * @param theMonsterToGet To get a monster of type Ogre for example: getMonster(Oger.class).
     * @return One of the monster of type specified in the parameter from the set in the database./
     */
    public Monster getMonster(Class theMonsterToGet) {
        ArrayList<Monster> monsters = getMyMonsters();
        for (Monster monster : monsters) {
            if (monster.getClass() == theMonsterToGet) {
                myMonsters.remove(monster);
                return monster;
            }
        }
        return null;
    }

    /**
     * For testing purposes
     * @param args
     */
    public static void main(String... args) {
//        ArrayList<Monster> monsters = getMonsters();
//        for (Monster monster : monsters) {
//            System.out.println(monster);
//        }
        MonsterFactory monsterFactory = new MonsterFactory();
        Monster ogre = monsterFactory.getMonster(Ogre.class);
        System.out.println(ogre);
        Monster ogre1 = monsterFactory.getMonster(Ogre.class);
        System.out.println(ogre1);
    }
}
