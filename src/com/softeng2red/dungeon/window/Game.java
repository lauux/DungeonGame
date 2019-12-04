package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.KeyInput;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.objects.*;
import com.softeng2red.dungeon.window.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.util.Random;

//This class Handles the main game logic
public class Game extends Canvas implements Runnable {

    public static boolean isAppear = true;
    private boolean running = false;
    private Thread thread;
    public static int WIDTH, HEIGHT;
    public BufferedImage level0 = null, level = null, city = null;

    public static int init_time = 60;
    public static int time = init_time;

    public static int count;
    public static int delay;

    // Object
    Handler handler;
    Camera cam;
    public static Game_Timer game_timer;
    static Texture tex;
    private HUD hud;

    public static int LEVEL = 1;

    public void init() {
        WIDTH = getWidth();
        HEIGHT = getHeight();
        tex = new Texture();

        BufferedImageLoader loader = new BufferedImageLoader();
        // loading the level
        level = loader.loadImage("/level.png");//Loads the level image
        city = loader.loadImage("/city.png");//Loads the background city image

        cam = new Camera(0,0);//Initializes Camera
        handler = new Handler(cam, game_timer);//Initializes Handler
        handler.LoadImageLevel(level);
        handler.addObject(new Health(650 ,20, handler,ObjectId.Health));//Initializes health
        game_timer = new Game_Timer(0,0, ObjectId.Game_Timer);//Initializes game timer

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ObjectId.Health){
                hud = new HUD((Health) tempObject, game_timer);
            }
        }
        this.addKeyListener(new KeyInput(handler, this, hud));//Adds key Listener
        game_timer.init();

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
//                if (time >= 0)
//                    time--;
                timer += 1000;
                System.out.println("FPS: " + frames + "  TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
    //Function which carries out the functions at each tick
    private void tick() {
        handler.tick();

        // the code block bellow controls the appearance of disappearing blocks
        count ++;
        delay = 80;
        if(count%delay==0) {
            isAppear = !isAppear;
        }

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

        if (game_timer.getTime() <= 0)
            // Checking if the time has run up
            GameOver();
    }
    //Function which is called when player dies
    private void GameOver() {
        for (int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ObjectId.Player){
                handler.clearLevel();//Clears objects
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
        startGame();
    }
    //Creates the new Window
    public static void startGame() {
        new Window(960, 800, "A Dungeon Game",  new Game());
    }
}

