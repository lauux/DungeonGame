package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.KeyInput;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.objects.*;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

//This class Handles the main game logic
public class Game extends Canvas implements Runnable {

    private boolean running = false;
    private Thread thread;
    public static int WIDTH, HEIGHT;
    private BufferedImage level = null, city = null;


    public static int init_time = 60;
    public static int time = init_time;

    // Object
    Handler handler;
    Camera cam;
    static Texture tex;
    private HUD hud;


    public void init() {
        WIDTH = getWidth();
        HEIGHT = getHeight();
        tex = new Texture();

        BufferedImageLoader loader = new BufferedImageLoader();
        // loading the level
        level = loader.loadImage("/level.png");//Loads the level image
        city = loader.loadImage("/city.png");//Loads the background city image

        cam = new Camera(0,0);//Initializes Camera
        handler = new Handler();//Initializes Handler
        LoadImageLevel(level);
        handler.addObject(new Health(650 ,20, handler,ObjectId.Health));//Initializes health
        this.addKeyListener(new KeyInput(handler));//Adds key Listener

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ObjectId.Health){
                hud = new HUD((Health) tempObject);
            }
        }
   }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();

    }
// Function which runs the FPS
    public void run() {

        init();
        this.requestFocus();
        long lastTime = System.nanoTime();
        // decrease from 60 to 40 due to health object, need to improve later
        double amountOfTicks = 40.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if ((System.currentTimeMillis() - timer) > 1000) {
                if (time >= 0)
                    time--;
                timer += 1000;
                System.out.println("FPS: " + frames + "  TICKS: " + updates + " TIME: " + time);
                frames = 0;
                updates = 0;
            }
        }
    }
//Function which carries out the functions at each tick
    private void tick() {
        handler.tick();
        for (int i = 0; i<handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ObjectId.Player){
                cam.tick(tempObject);
                GameObject healthObject = handler.object.get(0);
                if (healthObject.healthNum == healthObject.minHealth) {
                //Checking if the player has died
                    GameOver();
                }
            }
        }

        if (time < 0)
            // Checking if the time has run up
            GameOver();
    }
//Function which is called when player dies
    private void GameOver() {
        for (int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ObjectId.Player){
                    handler.object.clear();//Clears objects
                    hud.clear();//Clears HUD
                    //Displays GameOver
                    handler.addObject(new Game_Over(tempObject.getX()-((WIDTH)/2), tempObject.getY()-HEIGHT/2, ObjectId.Game_Over));

            }
        }
    }

    private void render() {
        //This is the Buffer strategy
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        /******************/
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g2d.translate(cam.getX(),cam.getY());

        g.drawImage(city,0,0, 960, 2000, this);//Draws the background scene
        handler.render(g);//Draws all the objects

        g2d.translate(-cam.getX(),-cam.getY());//Adjusts camera so is aligned with player
        hud.draw((Graphics2D) g);//Draws the heads up display


        /******************/
        g.dispose();
        bs.show();

    }
//Function that reads the level image and creates objects dependant on the colour of the pixel
    private void LoadImageLevel(BufferedImage image){
         int w = image.getWidth();
         int h = image.getHeight();

         for (int xx = 0; xx < w; xx++){
             for (int yy = 0; yy < h; yy++){
                 int pixel = image.getRGB(xx,yy);
                 int red = (pixel >> 16) & 0xff;
                 int green = (pixel >> 8) & 0xff;
                 int blue = (pixel) & 0xff;


                 //White on paint S, (255,255,255), Standard block
                 if(red == 255 && blue == 255 & green == 255) handler.addObject((new Block(xx*32,yy*32, 0, ObjectId.Block)));
                 //Blue on paint S (0,0,255), PLayer
                 if(red == 0 && blue == 255 & green == 0) handler.addObject((new Player(xx*32,yy*32, handler, ObjectId.Player)));
                 //Green on paint S (0,255,0), Grass Block
                 if(red == 35 && blue == 6 & green == 255) handler.addObject((new Block(xx*32,yy*32, 1, ObjectId.Block)));
                 //Pink on Paint S (255,0,255), Moving block
                 if (red == 251 && blue == 255 & green == 0) handler.addObject((new Moving_Block(xx*32,yy*32, 1, ObjectId.Moving_Block)));
                 //Red on Paint S (255,0,0), Villain
                 if (red == 251 && blue == 7 & green == 0) handler.addObject((new Villain(xx*32,yy*32, 2, ObjectId.Villain)));
                 // Yellow on Paint S (229,229,92), Beer
                 if (red == 229 && blue == 92 & green == 229) handler.addObject((new Beer(xx*32,yy*32, ObjectId.Beer)));
                 // Brown on Paint S (102,0,0), Barrel
                 if (red == 102 && blue == 0 & green == 0) handler.addObject((new Obstacle(xx*32,yy*32, ObjectId.Obstacle)));

             }
         }
    }

    public static int getTime() {
        return time;
    }

    public static void setTime(int new_time) {
        time = new_time;
    }

    public static Texture getInstance(){
        return tex;
    }

    public static void main(String args[]) {
        newGame();
    }
    //Creates the new Window
    public static void newGame() {
        new Window(960, 800, "A Dungeon Game",  new Game());
    }
}

