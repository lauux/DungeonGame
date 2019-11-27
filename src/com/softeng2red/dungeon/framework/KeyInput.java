package com.softeng2red.dungeon.framework;

import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Player) {
                if (key == KeyEvent.VK_RIGHT)
                    tempObject.setVelX(4);
                if (key == KeyEvent.VK_LEFT)
                    tempObject.setVelX(-4);
                if (key == KeyEvent.VK_UP && !tempObject.isJumping()) {
                    tempObject.setJumping(true);
                    tempObject.setVelY(-10);
                }

            }

            if (tempObject.getId() == ObjectId.Game_Over) {
                if (key == KeyEvent.VK_SPACE)
                    Game.newGame();
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
    }

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
