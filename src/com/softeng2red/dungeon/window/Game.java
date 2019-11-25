package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.KeyInput;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.objects.*;

import java.awt.image.BufferedImage;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    private boolean running = false;
    private Thread thread;

    public static int WIDTH, HEIGHT;

    private BufferedImage level = null;

    // Object
    Handler handler;
    Camera cam;
    static Texture tex;

    Random rand = new Random();

    public void init() {
        WIDTH = getWidth();
        HEIGHT = getHeight();
        tex = new Texture();

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/level.png");//loading the level

        cam = new Camera(0,0);

        handler = new Handler();
        LoadImageLevel(level);

        // temporarily initialize health, need to improve later
        handler.addObject(new Health(650 ,20, handler,ObjectId.Health));

        this.addKeyListener(new KeyInput(handler));
   }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {


        init();
        this.requestFocus();
        long lastTime = System.nanoTime();
        // decrease from 60 to 40 due to health object, need to improve later
        double amountOfTicks =40.0;
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
                timer += 1000;
                System.out.println("FPS: " + frames + "  TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void tick() {

        handler.tick();
        for (int i = 0; i<handler.object.size(); i++){
            if(handler.object.get(i).getId() == ObjectId.Player){
                cam.tick(handler.object.get(i));
            }
        }


    }

    private void render() {
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
        handler.render(g);
        g2d.translate(-cam.getX(),-cam.getY());
        /******************/
        g.dispose();
        bs.show();
    }

    private void LoadImageLevel(BufferedImage image){
         int w = image.getWidth();
         int h = image.getHeight();

         for (int xx = 0; xx<h; xx++){
             for (int yy = 0; yy < w; yy++){
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
                 if (red == 251 && blue == 7 & green == 0) handler.addObject((new Villain(xx*32,yy*32, ObjectId.Villain)));
                 // Yellow on Paint S (229,229,92), Beer
                 if (red == 229 && blue == 92 & green == 229) handler.addObject((new Beer(xx*32,yy*32, ObjectId.Beer)));
                 // Brown on Paint S (102,0,0), Barrel
                 if (red == 102 && blue == 0 & green == 0) handler.addObject((new Obstacle(xx*32,yy*32, ObjectId.Obstacle)));




             }
         }
    }

    public static Texture getInstance(){
        return tex;
    }

    public static void main(String args[]) {

        new Window(960, 800, "A Dungeon Game",  new Game());


    }
}

