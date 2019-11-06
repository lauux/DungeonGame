package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.objects.Block;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();

    private GameObject tempObject;

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            tempObject.tick(object);
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void createLevel() {
        // add block at bottom
        for (int xx = 0; xx < Game.WIDTH+32; xx += 32)
            addObject(new Block(xx, Game.HEIGHT-32, ObjectId.Block));
        // add block at right
        for (int yy = 0; yy < Game.HEIGHT+32; yy += 32)
            addObject(new Block(Game.WIDTH-32, yy, ObjectId.Block));
        // add block at left
        for (int yy = 0; yy < Game.HEIGHT+32; yy += 32)
            addObject(new Block(0, yy, ObjectId.Block));
        // add block at middle space
        for (int mm = 32*5; mm < Game.WIDTH-32*6; mm += 32)
            addObject(new Block(mm, 32*19, ObjectId.Block));
        for (int mm = 32*10; mm < Game.WIDTH-32; mm += 32)
            addObject(new Block(mm, 32*14, ObjectId.Block));
    }
}
