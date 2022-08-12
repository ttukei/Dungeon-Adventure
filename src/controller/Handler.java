package controller;

import model.DungeonCharacterComponents.DungeonCharacters.DungeonCharacter;
import model.DungeonObject;

import java.util.ArrayList;
import java.util.LinkedList;

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
        LinkedList<DungeonObject> objectsMarkedForDeath = new LinkedList<>();
        for(DungeonObject obj : myDungeonObjects){
            obj.objectBehavior();
            if (obj.isMarkedForDeath()){
                objectsMarkedForDeath.add(obj);
            }
        }
        for(DungeonObject objMarkedForDeath : objectsMarkedForDeath){
            myDungeonObjects.remove(objMarkedForDeath);
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
        return objectsInHandler + "\n";
    }

}
