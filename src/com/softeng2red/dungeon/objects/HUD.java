package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

//This class handles the heads up display
//This shows the health and the time
public class HUD {

    private Health healthObject;
    private Font font;
    private boolean running = true;
    Texture tex = Game.getInstance();

    public HUD (Health h) {
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

        if(running) {
            // Displays a timer
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString(String.format("Time: %03d", Game.getTime()), 37, 130); // Timer可以用

            //Varies the size of the spotlight dependant on how much beer has been collected
            if (healthObject.beerNum == 1) g.drawImage(tex.spotlight[3], 1,1, Game.WIDTH + 20,Game.HEIGHT + 20, null);
            else if (healthObject.beerNum == 2) g.drawImage(tex.spotlight[2], 1,1, Game.WIDTH + 20,Game.HEIGHT + 20, null);
            else if (healthObject.beerNum == 3) g.drawImage(tex.spotlight[1], 1,1, Game.WIDTH + 20,Game.HEIGHT + 20, null);
            else if (healthObject.beerNum >= 4) g.drawImage(tex.spotlight[0], 1,1, Game.WIDTH + 20,Game.HEIGHT + 20, null);


            // Different number of hearts shown
            for (int i = 0; i < healthObject.healthNum; i++) {
                g.drawImage(tex.health[0], 30 + i * 32, 50, null);
            }

            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString(String.format("Time: %03d", Game.getTime()), 37, 130); // Timer可以用
        }
    }


    public void clear(){
        running = false;
    }

}


