package model;

public class DungeonObject {

    private boolean myMarkedForDeath;

    protected DungeonObject(){
        initializeFields();
    }

    /** Should be overridden, children's fields should be initialized in this method, and initializeFields()
     *  should be called in the constructor. Intended to prevent code repetition when overloading constructors.
     */
    protected void initializeFields(){
        setMarkedForDeath(false);
    }

    public void tick(){}

    public boolean isMarkedForDeath() {
        return myMarkedForDeath;
    }

    protected void setMarkedForDeath(boolean mark) {
        this.myMarkedForDeath = mark;
    }

    /**
     * Should be overridden in child classes to provide a method to report just the object's
     * simple name. For example, when used on the hero it would return the hero's name. When used on
     * a monster of type, "Ogre" it would return, "Ogre".
     * @return A String representing the user-friendly name of the object.
     */
    protected String getMyName(){
        return "";
    }
}
