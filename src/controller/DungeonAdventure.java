package controller;

import model.DataTypes.Coordinates;
import model.DungeonComponents.Dungeon;

import static controller.Handler.getHandler;
import static model.DungeonComponents.Dungeon.getDungeon;

public class DungeonAdventure implements Runnable {

    private boolean myRunning;

    private Handler handler;

    private Dungeon dungeon;

    private DungeonAdventure(){

        handler = getHandler();
        dungeon = getDungeon(new Coordinates(7, 7));

    }

    private void start(){
            myRunning = true;
    }

    private void stop(){
            myRunning = false;
    }

    public void run(){
        while (myRunning){
            tick();
        }
    }

    private void tick(){
        handler.tick();
    }

    public static void main(String[] args){
        new DungeonAdventure();
    }

    // Public Static Methods

    public static int clamp(int theInteger, int theMin, int theMax){
        if (theInteger >= theMax){
            return theMax;
        } else if (theInteger <= theMin){
            return theMin;
        }
        return theInteger;
    }

    public static double clamp(double theDouble, double theMin, double theMax){
        if (theDouble >= theMax){
            return theMax;
        } else if (theDouble <= theMin){
            return theMin;
        }
        return theDouble;
    }
}
