package controller;

import model.DungeonComponents.Doors;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static controller.DungeonAdventure.*;

public class KeyInputController extends KeyAdapter {

    public void keyPressed(KeyEvent e){

//        System.out.println("Key Pressed!");

        int key = e.getKeyCode();

        // Moving Rooms
        if (key == KeyEvent.VK_W){
            RoomController.setDirectionToMove(Doors.NORTHDOOR);
            RoomController.setMyMoving(true);
            setWaitingForTurn(false);
        }
        if (key == KeyEvent.VK_D) {
            RoomController.setDirectionToMove(Doors.EASTDOOR);
            RoomController.setMyMoving(true);
            setWaitingForTurn(false);
        }
        if (key == KeyEvent.VK_S){
            RoomController.setDirectionToMove(Doors.SOUTHDOOR);
            RoomController.setMyMoving(true);
            setWaitingForTurn(false);
        }
        if (key == KeyEvent.VK_A){
            RoomController.setDirectionToMove(Doors.WESTDOOR);
            RoomController.setMyMoving(true);
            setWaitingForTurn(false);
        }
        if (key == KeyEvent.VK_ESCAPE){
            DungeonAdventure.setRunning(false);
            System.exit(1);
//            System.out.println("Esc Pressed");
        }

        DungeonAdventure.setWaitingForTurn(false);

    }

}
