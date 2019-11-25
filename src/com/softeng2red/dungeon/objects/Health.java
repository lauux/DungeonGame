package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Health extends GameObject {

    Texture tex = Game.getInstance();

    private Handler handler;



    public Health(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
    }

    /////////////////
    public void tick(LinkedList<GameObject> object)
    {
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
        // integrate
            for (int i = 0; i < healthNum; i++) {
                g.drawImage(tex.health[0], (int) x + 300 + i * 32, (int) y - 350, null);
            }
    }
    public void postRender(Graphics g){
        for (int i = 0; i < healthNum; i++) {
            g.drawImage(tex.health[0], (int) x + 300 + i * 32, (int) y - 350, null);
        }
    }


    public Rectangle getBounds() {
        return null;
    }
}
