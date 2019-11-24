package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {

    private Health healthObject;
    //private BufferedImage image;
    private Font font;

    Texture tex = Game.getInstance();

    public HUD(Health h){
        healthObject = h;
        try{
            // load images here
            //image = ImageIO.read(getClass().getResourceAsStream("/health_sheet.png"));
            font = new Font("Arial", Font.PLAIN, 24);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g){
        // draw images and fonts here
        for (int i = 0; i < healthObject.healthNum; i++) {
            g.drawImage(tex.health[0], 30 + i * 32, 50, null);
        }
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("Time: ", 30, 130); // Timer可以用

    }

}


// 创建 HUD head up display
