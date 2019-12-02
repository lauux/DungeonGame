package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.window.BufferedImageLoader;
import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;
import com.softeng2red.dungeon.framework.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends GameObject {

    private float width = 32, height = 64;
    
    private float gravity = 0.5f;
    private final float MAX_SPEED = 10;

    private Handler handler;
    Texture tex = Game.getInstance();

//    Game game;

    public Player(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
    }

    public void tick(LinkedList<GameObject> object) {
        x += velX;
        y += velY;

        if (falling || jumping) {
            velY += gravity;

            if (velY > MAX_SPEED)
                velY = MAX_SPEED;
        }
        Collision(object);
    }

    private void Collision(LinkedList<GameObject> object) {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            // *need to refactor
            GameObject healthObject = handler.object.get(0);

            //Detecting collisions with Blocks
            if (tempObject.getId() == ObjectId.Block) {
                // top
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + (height/2);
                    velY = 0;
                }
                // bottom
                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height;
                    velY = 0;
                    falling = false;
                    jumping = false;
                } else {
                    falling = true;
                }
                // right
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - width;
                }
                // left
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + width;
                }
            }
            //Detecting collisions with Moving_Blocks
            if (tempObject.getId() == ObjectId.Moving_Block) {
                // top
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + (height/2);
                    velY = 0;
                }
                // bottom
                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height;
                    velY = 0;
                    falling = false;
                    jumping = false;
                } else {
                    falling = true;
                }
                // right
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - width;
                }
                // left
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + width;
                }
            }
            if (tempObject.getId() == ObjectId.Villain) {

                // bottom
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.object.remove(tempObject);

                }
                // right
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    handler.object.remove(tempObject);
                    if (healthObject.healthNum>0) {
                        healthObject.healthNum--;
                    }
                }
                // left
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    handler.object.remove(tempObject);
                    if (healthObject.healthNum>0) {
                        healthObject.healthNum--;
                    }
                }

            }

            if (tempObject.getId() == ObjectId.Player) {

                //System.out.print(i);

            }
           // Detecting collisions with Beers
            if (tempObject.getId() == ObjectId.Beer) {
                // top
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (healthObject.healthNum<healthObject.maxHealth) {
                        healthObject.healthNum++;
                    }
                }
                // bottom
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (healthObject.healthNum<healthObject.maxHealth) {
                        healthObject.healthNum++;
                    }
                }
                // right
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (healthObject.healthNum<healthObject.maxHealth) {
                        healthObject.healthNum++;
                    }
                }
                // left
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (healthObject.healthNum<healthObject.maxHealth) {
                        healthObject.healthNum++;
                    }
                }
            }

            // Detecting collisions with Obstacles
            if (tempObject.getId() == ObjectId.Obstacle) {
                // top
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + (height/2);
                    velY = 0;
                }
                // bottom
                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height;
                    velY = 0;
                    falling = false;
                    jumping = false;
                } else {
                    falling = true;
                }
                // right
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - width;
                }
                // left
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + width;
                }
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.drawImage(tex.player[0],(int)x,(int)y,32,64, null);


    }

    public Rectangle getBounds() {
        return new Rectangle((int)((int)x+(width/2)-(width/2)/2), (int)((int)y+(height/2)), (int)width/2, (int)height/2);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle((int)((int)x+(width/2)-(width/2)/2), (int)y, (int)width/2, (int)height/2);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int)((int)x+width-5), (int)y+5, (int) 5, (int)height-10);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
    }

}
