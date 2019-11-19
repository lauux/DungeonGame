package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Villain extends GameObject {

    private float width = 32, height = 32;
    private float gravity = 0.5f;
    private final float MAX_SPEED = 10;
//    Handler handler;
    Texture tex = Game.getInstance();


    public Villain(float x, float y, ObjectId id) {
        super(x, y, id);
//        this.handler = handler;

    }


    public void tick(LinkedList<GameObject> object) {

        if (falling || jumping) {
            velY += gravity;

            if (velY > MAX_SPEED)
                velY = MAX_SPEED;
        }
//        Collision(object);
    }

//    private void Collision(LinkedList<GameObject> object) {
//        for (int i = 0; i < handler.object.size(); i++) {
//            GameObject tempObject = handler.object.get(i);
//
//            //Detecting collisions with Blocks
//            if (tempObject.getId() == ObjectId.Block) {
//                // bottom
//                if (getBounds().intersects(tempObject.getBounds())) {
//                    y = tempObject.getY() - height;
//                    velY = 0;
//                    falling = false;
//                    jumping = false;
//                } else {
//                    falling = true;
//                }
//                // right
//                if (getBoundsRight().intersects(tempObject.getBounds())) {
//                    x = tempObject.getX() - width;
//                }
//                // left
//                if (getBoundsLeft().intersects(tempObject.getBounds())) {
//                    x = tempObject.getX() + width;
//                }
//            }
//
//            //Detecting collisions with Moving_Blocks
//            if (tempObject.getId() == ObjectId.Moving_Block) {
//                // bottom
//                if (getBounds().intersects(tempObject.getBounds())) {
//                    y = tempObject.getY() - height;
//                    velY = 0;
//                    falling = false;
//                    jumping = false;
//                } else {
//                    falling = true;
//                }
//                // right
//                if (getBoundsRight().intersects(tempObject.getBounds())) {
//                    x = tempObject.getX() - width;
//                }
//                // left
//                if (getBoundsLeft().intersects(tempObject.getBounds())) {
//                    x = tempObject.getX() + width;
//                }
//            }
//        }
//    }
    public void render(Graphics g) {
        g.drawImage(tex.villain[0],(int)x,(int)y,null);

    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int)((int)x+width-5), (int)y+5, (int) 5, (int)height-10);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
    }
}
