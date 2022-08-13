package controller;

import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class Handler {

    private static final Handler uniqueInstanceOfHandler = new Handler();

    private final ArrayList<DungeonObject> myDungeonObjects;

    private Handler(){
        myDungeonObjects = new ArrayList<>();
    }

    public static Handler getHandler() {
        return uniqueInstanceOfHandler;
    }

    public void tick(){
        LinkedList<DungeonObject> objectsMarkedForDeath = new LinkedList<>();
        for(DungeonObject obj : myDungeonObjects){
            if (obj.isMarkedForDeath()){
                objectsMarkedForDeath.add(obj);
            } else {
                obj.objectBehavior();
            }
        }
        for(DungeonObject objMarkedForDeath : objectsMarkedForDeath){
//            objMarkedForDeath.killMe();
            myDungeonObjects.remove(objMarkedForDeath);
        }
    }

    public int addObject(DungeonObject theObject){
        myDungeonObjects.add(theObject);
        return myDungeonObjects.indexOf(theObject);
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
        return objectsInHandler + "\n";
    }

}
