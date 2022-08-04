package model.DungeonComponents.DataTypes;

public class Coordinates {

    private int myX;
    private int myY;

    public Coordinates(int theX, int theY){
        myX = theX;
        myY = theY;
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    public String toString(){
        return "(" + myX + ", " + myY + ")";
    }

    public boolean isLocatedAtTheCoordinate(Coordinates theCoordinateToCompare){
        return getX() == theCoordinateToCompare.getX() && getY() == theCoordinateToCompare.getY();
    }
}
