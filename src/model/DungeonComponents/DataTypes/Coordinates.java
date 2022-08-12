package model.DungeonComponents.DataTypes;

import model.DungeonComponents.Dungeon;

import static controller.DungeonAdventure.*;

public class Coordinates {

    private final int myX;
    private final int myY;

    public Coordinates(int theX, int theY){
        myX = clamp(theX, 0, Dungeon.getMAX_X());
        myY = clamp(theY, 0, Dungeon.getMAX_Y());
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    public String toString(){
        return "(" + getY() + ", " + getX() + ")";
    }

    public boolean isLocatedAtTheCoordinate(Coordinates theCoordinateToCompare){
        return getX() == theCoordinateToCompare.getX() && getY() == theCoordinateToCompare.getY();
    }
}
