package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.window.Animation;
import com.softeng2red.dungeon.window.BufferedImageLoader;
import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;
import com.softeng2red.dungeon.framework.Texture;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.util.LinkedList;


//This class handles the PLayers functions and data
public class Player extends GameObject {

    private float width = 32, height = 64;
    private float gravity = 0.5f;
    private final float MAX_SPEED = 10;
    private Handler handler;
    Texture tex = Game.getInstance();
    private Animation playerWalkRight;
    private Animation playerWalkLeft;

//    Game game;

    public Player(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;

        playerWalkRight = new Animation(5,tex.player[0],tex.player[1],tex.player[2]);
        playerWalkLeft = new Animation(5,tex.player[3],tex.player[4],tex.player[5]);
    }
    //Updates the players position each 'tick'
    public void tick(LinkedList<GameObject> object) {
        x += velX;
        y += velY;

        if (falling || jumping) {
            velY += gravity;

            if (velY > MAX_SPEED)
                velY = MAX_SPEED;
        }
        Collision(object);
        //Calls the animation functions
        playerWalkRight.runAnimation();
        playerWalkLeft.runAnimation();
    }
    //This functions detects if the player has collided with any objects and responds acordingly
    private void Collision(LinkedList<GameObject> object) {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            // *need to refactor
            GameObject healthObject = handler.object.get(0);

            //Detecting collisions with Blocks
            if (tempObject.getId() == ObjectId.Block) {
                // top
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    if (on_m_block && m_Left){
                        x = x + 3*width;
                        on_m_block = false;
                        if (healthObject.healthNum>0) {
                            healthObject.healthNum--;
                        }
                    }else if(on_m_block && m_Right){
                        x = x - 3*width;
                        on_m_block = false;
                        if (healthObject.healthNum>0) {
                            healthObject.healthNum--;
                        }
                    }
                    else {
                        y = tempObject.getY() + (height / 2);
                        velY = 0;
                    }
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
                    if (tempObject.is_m_Up() == true){
                        on_m_block = true;
                    }
                } else {
                    falling = true;
                    on_m_block = false;
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
                        healthObject.beerNum++;
                    }
                }
                // bottom
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (healthObject.healthNum<healthObject.maxHealth) {
                        healthObject.healthNum++;
                        healthObject.beerNum++;
                    }
                }
                // right
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (healthObject.healthNum<healthObject.maxHealth) {
                        healthObject.healthNum++;
                        healthObject.beerNum++;
                    }
                }
                // left
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (healthObject.healthNum<healthObject.maxHealth) {
                        healthObject.healthNum++;
                        healthObject.beerNum++;
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
    //Draws the player with the animation
    public void render(Graphics g) {
        g.setColor(Color.red);
        if (velX>0){
            playerWalkRight.drawAnimation(g, (int) x, (int) y, 32, 64);
        }else if (velX<0){
            playerWalkLeft.drawAnimation(g, (int) x, (int) y, 32, 64);
        }
        else{
            g.drawImage(tex.player[0],(int)x,(int)y,32,64, null);
        }
    }

    //Functions to get the boundaries of the player
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
