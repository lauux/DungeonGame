package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.objects.Block;
import com.softeng2red.dungeon.objects.Health;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();

    private GameObject tempObject;
    private GameObject health;

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
            if (tempObject.getId() == ObjectId.Health){
                this.health = tempObject;
            }
        }
        health.render(g);

    }

    public void addObject(GameObject object) {
        this.object.addFirst(object);
    }


    public void removeObject(GameObject object) {
        this.object.remove(object);
    }


}
