/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

/**
 *
 * @author ollie
 */
public class Picture implements Serializable {
    
    private int posX = 0, posY = 0;
    private int width = 100, height = 100;
    File file;
    
    public Picture(File file) throws IOException{
        this.file = file;
    }
    
    public void paint(Graphics g) throws IOException{
        
        g.drawImage(ImageIO.read(file), posX, posY, width, height, null);
    }
    
    public void changePos(int x, int y){
        posX = x;
        posY = y;
    }
}
