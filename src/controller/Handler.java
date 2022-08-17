package controller;

import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Handler {

    private static final Handler uniqueInstanceOfHandler = new Handler();

    private final HashMap<Integer, DungeonObject> myDungeonObjects;
    private int myIdCounter;

    private Handler(){
        myDungeonObjects = new HashMap<>();
        myIdCounter = 0;
    }

    /* PUBLIC STATIC METHODS */

    public static Handler getHandler() {
        return uniqueInstanceOfHandler;
    }

    /* PUBLIC INSTANCE METHODS */

    public void tick(){
        LinkedList<DungeonObject> objectsMarkedForDeath = new LinkedList<>();
        for(DungeonObject obj : myDungeonObjects.values()){
            if (obj.isMarkedForDeath()){
                objectsMarkedForDeath.add(obj);
            } else {
                obj.objectBehavior();
            }
        }
        for(DungeonObject objMarkedForDeath : objectsMarkedForDeath){
            objMarkedForDeath.killMe();
        }
    }

    public int addObject(DungeonObject theObject){
        myDungeonObjects.put(myIdCounter, theObject);
        System.out.println(myIdCounter);
        theObject.setId(myIdCounter);
        return myIdCounter++;
    }

    public void removeObject(int theId){
        myDungeonObjects.remove(theId);
    }

    public DungeonObject getObject(int theId){
        return myDungeonObjects.get(theId);
    }

    /* OVERRIDES */

    @Override
    public String toString(){
        StringBuilder objectsInHandler = new StringBuilder();
        for (DungeonObject obj : myDungeonObjects.values()){
            objectsInHandler.append(obj).append("\n");
        }
        return objectsInHandler + "\n";
    }

}
