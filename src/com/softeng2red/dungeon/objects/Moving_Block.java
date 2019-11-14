package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;

import java.awt.*;
import java.util.LinkedList;

public class Moving_Block extends GameObject {
    Texture tex = Game.getInstance();
    private int count;
    private int delay;

    public Moving_Block(float x, float y,int delay, ObjectId id){
        super(x, y, id);
        this.delay = delay*60;
        setVelY(1);

    }

    public void tick(LinkedList<GameObject> object) {
        this.count +=1;

        if (count%this.delay==0){
            setVelY(velY*-1);
        }
        y += velY;


    }
    public void render(Graphics g) {
            g.drawImage(tex.block[0],(int)x,(int)y,null);

    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
