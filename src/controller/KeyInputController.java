package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static controller.DungeonAdventure.*;

public class KeyInputController extends KeyAdapter {

    public void keyPressed(KeyEvent e){

//        System.out.println("Key Pressed!");

        int key = e.getKeyCode();

        // Moving Rooms
        if (key == KeyEvent.VK_W){
            RoomController.moveNorth();
            setWaitingForTurn(false);
        }
        if (key == KeyEvent.VK_D) {
            RoomController.moveEast();
            setWaitingForTurn(false);
        }
        if (key == KeyEvent.VK_S){
            RoomController.moveSouth();
            setWaitingForTurn(false);
        }
        if (key == KeyEvent.VK_A){
            RoomController.moveWest();
            setWaitingForTurn(false);
        }

    }

}
