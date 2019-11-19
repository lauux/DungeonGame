package com.softeng2red.dungeon.framework;
import com.softeng2red.dungeon.window.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class Texture {

    SpriteSheet bs, ps, beers, obs;
    private BufferedImage block_sheet = null;
    private BufferedImage player_sheet = null;
    private BufferedImage health_sheet = null;
    private BufferedImage obstacle_sheet = null;


    public BufferedImage[] block = new BufferedImage[2];
    public BufferedImage[] player = new BufferedImage[1];
    public BufferedImage[] beer = new BufferedImage[1];
    public BufferedImage[] obstacle = new BufferedImage[1];


    public Texture(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            block_sheet = loader.loadImage("/block_sheet.png");
            player_sheet = loader.loadImage("/player_sheet1.png");
            health_sheet = loader.loadImage("/health_sheet.png");
            obstacle_sheet = loader.loadImage("/Obstacle_sheet.png");

        }catch(Exception e){
            e.printStackTrace();
        }
        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(player_sheet);
        beers = new SpriteSheet(health_sheet);
        obs = new SpriteSheet(obstacle_sheet);

        getTextures();

    }
    private void getTextures(){

        block[0] = bs.grabImage(1,1,32,32); //dirt block
        block[1] = bs.grabImage(2,1,32,32); //grass block

        player[0] = ps.grabImage(1,1,25,40);

        beer[0] = beers.grabImage(1,3,32,32);
        obstacle[0] = obs.grabImage(1,1,32,32);

    }
}
