/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ollie
 */
public class gameoflife {

    JFrame jframe;
    JPanel jpanelForm;
    
    Random randomizer;

    MainJPanel mainJPanel;
    JButton generateButton,
            stepButton;
    final JButton launchButton;
    
    final int POSIT_CORDX = 200,
              POSIT_CORDY = 200;
    final int LIVING_AMOUNT = 50;
    final int POINT_RADIUS = 10;
    final int FIELD_SIZE = LIVING_AMOUNT * POINT_RADIUS + 10;

    final int BTN_PANEL_HEIGHT = 60;
    boolean[][] currGeneration = new boolean[LIVING_AMOUNT][LIVING_AMOUNT];
    boolean[][] nextGeneration = new boolean[LIVING_AMOUNT][LIVING_AMOUNT];
    volatile boolean breedNextGeneration = false;
    int showDelay = 200;

    public gameoflife() {

        randomizer = new Random();
        
        mainJPanel = new MainJPanel();
        
        jframe = new JFrame("game of life");
        
        generateButton = new JButton("Generate");
        stepButton = new JButton("1 gen Step");
        launchButton = new JButton("Breed");
        
        jpanelForm = new JPanel();
    }
    
    public static void main(String[] args) {
        
        new gameoflife().launch();
    }

    public void launch() {
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(FIELD_SIZE, FIELD_SIZE);
        jframe.setLocation(POSIT_CORDX, POSIT_CORDY);
        jframe.setResizable(false);
        
        mainJPanel.setBackground(Color.white);
        
        generateButton.addActionListener(new GenerateButtonListener());
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                breeding();
                mainJPanel.repaint();
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                breedNextGeneration = !breedNextGeneration;
                launchButton.setText(breedNextGeneration ? "Stop" : "Breed");
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(launchButton);
        
        jframe.getContentPane().add(BorderLayout.CENTER, mainJPanel);
        jframe.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        jframe.setVisible(true);

        // endless loop of life
        while (true) {
            if (breedNextGeneration) {
                breeding();
                mainJPanel.repaint();
                try {
                    Thread.sleep(showDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void breeding() {
        
//        System.out.println("breeding");
//        int nbr_number;
        
        for (int x_id = 0; x_id < LIVING_AMOUNT; x_id++) {
            for (int y_id = 0; y_id < LIVING_AMOUNT; y_id++) {
                
                nextGeneration[x_id][y_id] = currGeneration[x_id][y_id];
                int nbr_number = numberofneightbours(x_id, y_id);
                
                nextGeneration[x_id][y_id] = (nbr_number == 3) ? true : nextGeneration[x_id][y_id];
                nextGeneration[x_id][y_id] = ((nbr_number < 2 || nbr_number > 3)) ? false : nextGeneration[x_id][y_id];
                
//                if (nbr_number == 3 && !currGeneration[x_id][y_id])
//                    nextGeneration[x_id][y_id] = true;
//                if (nbr_number < 2 || nbr_number > 3)
//                    nextGeneration[x_id][y_id] = false;
            }
        }
        for (int x_id = 0; x_id < LIVING_AMOUNT; x_id++)
            System.arraycopy(nextGeneration[x_id], 0, currGeneration[x_id], 0, LIVING_AMOUNT);
        
    }

    public int numberofneightbours(int x_id, int y_id) {

        int counter = 0;

        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
//                System.out.println("iterate");
                int nbrX = x_id + dx;
                int nbrY = y_id + dy;
                nbrX = nbrX < 0 ? LIVING_AMOUNT - 1 : nbrX;
                nbrY = nbrY < 0 ? LIVING_AMOUNT - 1 : nbrY;
                nbrX = (nbrX > LIVING_AMOUNT - 1) ? 0 : nbrX;
                nbrY = (nbrY > LIVING_AMOUNT - 1) ? 0 : nbrY;
                counter += (currGeneration[nbrX][nbrY]) ? 1 : 0;
            }
        }
        if (currGeneration[x_id][y_id]) {
            counter--;
        }
        return counter;
    }

    public class MainJPanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            for (int x = 0; x < LIVING_AMOUNT; x++) {
                for (int y = 0; y < LIVING_AMOUNT; y++) {
                    
                    if (currGeneration[x][y]) {
                        g.fillOval(x * POINT_RADIUS, y * POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
                    }
                }
            }
        }
    }

    public class GenerateButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {

            for (int x = 0; x < LIVING_AMOUNT; x++) {
                for (int y = 0; y < LIVING_AMOUNT; y++) {
                    currGeneration[x][y] = randomizer.nextBoolean();
                }
            }
            mainJPanel.repaint();
        }
    }

}
