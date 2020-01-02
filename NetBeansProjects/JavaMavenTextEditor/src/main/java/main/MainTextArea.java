/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author ollie
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import static main.FileUtils.doc;

import org.json.JSONException;

public class MainTextArea extends JComponent implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    Document doc;
    BufferedImage bI;
    int width, height;
    String filePath;

    public MainTextArea() throws IOException {
        doc = new Document();
        width = this.getWidth();
        height = this.getHeight();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);
//        this.bI = ImageIO.read(new File("src/main/java/main/background.jpeg"));
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {

//        g.drawImage(bI, 0, 0, this);
        System.out.println("MainTextArea paint method");
        
        Graphics2D g2d = (Graphics2D) g;

        try {
            doc.paint(g2d, width, height);
        } catch (JSONException ex) {
            Logger.getLogger(MainTextArea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainTextArea.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void bPaint() throws JSONException, IOException {

//        doc.paint(bI.getGraphics(), this.getWidth(), this.getHeight());
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        doc.mClick(arg0.getX(), arg0.getY());
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        try {
            int key = arg0.getKeyCode();
            switch (key) {
                case KeyEvent.VK_UP:
                    doc.highlightTop();
                    break;
                case KeyEvent.VK_DOWN:
                    doc.highlightBottom();
                    break;
                case KeyEvent.VK_LEFT:
                    doc.moveCurLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    doc.moveCurRight();
                    break;
                case KeyEvent.VK_ENTER:
                    doc.add();
                    break;
                case KeyEvent.VK_F1:

                    break;
                case KeyEvent.VK_F5:
                    save();
                    break;
                case KeyEvent.VK_F6:
                    load();
                    break;
                case KeyEvent.VK_F4:
                    Point posCursor = MouseInfo.getPointerInfo().getLocation();
                    doc.changePicPos(posCursor.x, posCursor.y);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    doc.deletElement();
                    break;
                default:
                    doc.addSymb(arg0.getKeyChar());
                    break;
            }
            bPaint();
            repaint();

        } catch (JSONException e) {
            Logger.getLogger(MainTextArea.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(MainTextArea.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(MainTextArea.class.getName()).log(Level.SEVERE, null, e);
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void save() throws IOException {
        doc.serialize();
        //сохранение документа в требуемом формате.
    }

    private void load() throws IOException, FileNotFoundException, ClassNotFoundException, JSONException {
        doc.deserialize();
        bPaint();
    }

    public void switchFont(String FontString) throws JSONException {
        doc.switchFont(FontString);
    }

    public void switchColor(int R, int G, int B) throws JSONException {
        doc.switchColor(R, G, B);
    }

    public void switchSize(int fontSize) throws JSONException {
        doc.switchSize(fontSize);
    }

    public void addPic(File picture) throws IOException {
        doc.addPic(picture);
    }

}
