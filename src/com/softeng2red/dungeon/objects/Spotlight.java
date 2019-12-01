package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Spotlight extends GameObject{

    Texture tex = Game.getInstance();
    private Handler handler;



    public Spotlight(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
    }

    /////////////////
    public void tick(LinkedList<GameObject> object) {
        for(int i = 0; i < handler.object.size(); i ++)
        {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ObjectId.Player) {
                x = tempObject.getX();
                y = tempObject.getY();
            }
        }
    }


    public void render(Graphics g) {
        x = x - Game.WIDTH/2;
        y = y - Game.HEIGHT/2;
        // integrate
        g.drawImage(tex.spotlight[0], (int) x, (int) y, Game.WIDTH + 20,Game.HEIGHT + 20, null);

    }

    public Rectangle getBounds() {
        return null;
    }

}
