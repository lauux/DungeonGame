package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.objects.Block;
import com.softeng2red.dungeon.objects.Health;
import java.awt.*;
import java.util.LinkedList;

//This class handles all objects in the game
public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;
    private GameObject health;
    private GameObject spotlight;

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            tempObject.tick(object);//Calls the tick function on all objects
        }
    }
//Renders all objects
    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
                tempObject.render(g);
        }
    }
    //Function to add objects
    public void addObject(GameObject object) {
        this.object.addFirst(object);
    }
    //Function to remove objects
    public void removeObject(GameObject object) {
        this.object.remove(object);
    }


}
