package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

//This class handles all objects in the game
public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;
    private BufferedImage level, level0;
    public Camera cam;
    public Game_Timer timer;

    public Handler(Camera cam, Game_Timer timer) {
        this.cam = cam;
        this.timer = timer;

        BufferedImageLoader loader = new BufferedImageLoader();
        // loading the level
        level = loader.loadImage("/level.png");//Loads the level image
        level0 = loader.loadImage("/GameOverLevel.png");// Loads the Game Over image

    }

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

    //Function that reads the level image and creates objects dependant on the colour of the pixel
    public void LoadImageLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < w; xx++){
            for (int yy = 0; yy < h; yy++){
                int pixel = image.getRGB(xx,yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                //White on paint S, (255,255,255), Standard block
                if(red == 255 && blue == 255 & green == 255) addObject((new Block(xx*32,yy*32, 0, ObjectId.Block)));
                //Blue on paint S (0,0,255), PLayer
                if(red == 0 && blue == 255 & green == 0) addObject((new Player(xx*32,yy*32, this, cam, ObjectId.Player)));
                //Green on paint S (0,255,0), Grass Block
                if(red == 35 && blue == 6 & green == 255) addObject((new Block(xx*32,yy*32, 1, ObjectId.Block)));
                //Pink on Paint S (255,0,255), Moving block
                if (red == 251 && blue == 255 & green == 0) addObject((new Moving_Block(xx*32,yy*32, 1, ObjectId.Moving_Block)));
                //Red on Paint S (251,0,7), Villain
                if (red == 251 && blue == 7 & green == 0) addObject((new Villain(xx*32,yy*32, 2, ObjectId.Villain)));
                // Yellow on Paint S (229,229,92), Beer
                if (red == 229 && blue == 92 & green == 229) addObject((new Beer(xx*32,yy*32, ObjectId.Beer)));
                // Brown on Paint S (102,0,0), Barrel
                if (red == 102 && blue == 0 & green == 0) addObject((new Obstacle(xx*32,yy*32, ObjectId.Obstacle)));
                // Grey on Paint S (141,141,141), Game Over
                if (red == 141 && blue == 141 && green == 141) addObject((new Block(xx*32, yy*32, 2, ObjectId.Block)));
                // Purple on Paint s (160,0,160), Game timer trigger
                if (red == 146 && blue == 104 && green == 104) addObject((new Game_Timer(xx*32, yy*32, ObjectId.Game_Timer)));
                // Paint S (144,144,144), Disappearing Blocks
                if (red ==  144 && blue == 144 & green == 144) addObject((new Disappearing_Block(xx*32,yy*32, ObjectId.Disappearing_Block)));



            }
        }
    }


    public void switchLevel() {
        clearLevel();
        cam.setX(0);
        switch (Game.LEVEL) {
            case 0:
                LoadImageLevel(level);
                break;
        }
        Game.LEVEL++;
    }

    public void clearLevel() {
        object.clear();
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
