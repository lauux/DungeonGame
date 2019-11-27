package com.softeng2red.dungeon.framework;
import com.softeng2red.dungeon.window.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Texture {

    SpriteSheet bs, ps, beers, obs, vs, hs, gos;
    private BufferedImage block_sheet = null;
    private BufferedImage player_sheet = null;
    private BufferedImage health_sheet = null;
    private BufferedImage obstacle_sheet = null;
    private BufferedImage villain_sheet = null;
    private BufferedImage gameover_sheet = null;


    public BufferedImage[] block = new BufferedImage[2];
    public BufferedImage[] player = new BufferedImage[1];
    public BufferedImage[] beer = new BufferedImage[1];
    public BufferedImage[] obstacle = new BufferedImage[1];
    public BufferedImage[] villain = new BufferedImage[1];
    public BufferedImage[] health = new BufferedImage[2];
    public BufferedImage[] gameover = new BufferedImage[1];



    public Texture(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            block_sheet = loader.loadImage("/blocks_sheet.png");
            player_sheet = loader.loadImage("/player_sheet1.png");
            health_sheet = loader.loadImage("/health_sheet.png");
            obstacle_sheet = loader.loadImage("/Obstacle_sheet.png");
            villain_sheet = loader.loadImage("/villain_sheet.png");
            gameover_sheet = loader.loadImage("/gameover.png");

        }catch(Exception e){
            e.printStackTrace();
        }
        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(player_sheet);
        beers = new SpriteSheet(health_sheet);
        obs = new SpriteSheet(obstacle_sheet);
        vs = new SpriteSheet(villain_sheet);
        hs = new SpriteSheet(health_sheet);
        gos = new SpriteSheet(gameover_sheet);

        getTextures();

    }
    private void getTextures(){

        block[0] = bs.grabImage(1,1,32,32); //dirt block
        block[1] = bs.grabImage(2,1,32,32); //grass block

        player[0] = ps.grabImage(1,1,25,41);

        beer[0] = beers.grabImage(1,3,32,32);
        obstacle[0] = obs.grabImage(1,1,32,32);
        villain[0] = vs.grabImage(1,1,32,32);
        health[0] = hs.grabImage(1,1,32,32);
        health[1] = hs.grabImage(1,2,32,32);
        gameover[0] = gos.grabImage(1,1,960,800);

    }
}
