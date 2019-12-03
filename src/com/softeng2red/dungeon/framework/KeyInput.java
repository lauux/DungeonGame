package com.softeng2red.dungeon.framework;

import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//This class handles key input.
public class KeyInput extends KeyAdapter {
    Handler handler;
    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    //If a useful key is pressed a corresponding action occurs
    //Often a change in the velocity of the player
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Player) {
                if (key == KeyEvent.VK_RIGHT) {
                    tempObject.m_Left = false;
                    tempObject.m_Right = true;
                    tempObject.setVelX(4);
                }
                if (key == KeyEvent.VK_LEFT) {
                    tempObject.m_Right = false;
                    tempObject.m_Left = true;
                    tempObject.setVelX(-4);
                }
                if (key == KeyEvent.VK_UP && !tempObject.isJumping()) {
                    tempObject.setJumping(true);
                    tempObject.setVelY(-10);
                }
            }

            if (tempObject.getId() == ObjectId.Game_Over) {
                if (key == KeyEvent.VK_SPACE)
                    Game.newGame();
                    Game.setTime(Game.init_time);
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
    }
    //Handles functionality for when a key is released.
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Player) {
                if (key == KeyEvent.VK_RIGHT)
                    tempObject.setVelX(0);
                if (key == KeyEvent.VK_LEFT)
                    tempObject.setVelX(0);
            }
        }
    }
}
