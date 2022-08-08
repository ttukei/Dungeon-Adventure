package controller;

import model.DungeonObject;

import java.util.ArrayList;

public class Handler {

    private static Handler uniqueInstanceOfHandler = new Handler();

    private ArrayList<DungeonObject> myDungeonObjects;

    private Handler(){
        myDungeonObjects = new ArrayList<>();
    }

    public static Handler getHandler() {
        return uniqueInstanceOfHandler;
    }

    public void tick(){
        for(DungeonObject obj : myDungeonObjects){
            obj.tick();
            if (obj.isMarkedForDeath()){
                removeObject(obj);
            }
        }
    }

    public void addObject(DungeonObject theObject){
        myDungeonObjects.add(theObject);
    }

    public void removeObject(DungeonObject theObject){
        myDungeonObjects.remove(theObject);
    }

    @Override
    public String toString(){
        StringBuilder objectsInHandler = new StringBuilder();
        for (DungeonObject obj : myDungeonObjects){
            objectsInHandler.append(obj).append("\n");
        }
        return objectsInHandler.toString();
    }

}
