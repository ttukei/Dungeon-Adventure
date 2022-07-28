package model;

public class DungeonObject {

    private boolean myMarkedForDeath;

    protected DungeonObject(){
        myMarkedForDeath = false;
    }

    public void tick(){}

    public boolean isMarkedForDeath() {
        return myMarkedForDeath;
    }

    protected void setMarkedForDeath(boolean marked) {
        this.myMarkedForDeath = marked;
    }
}
