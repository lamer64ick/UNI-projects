/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemy_mode;

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
public class Epidemy {

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
    final int q = 2;
    final int startAmount = 100;
    final int POINT_RADIUS = 10;
    final int FIELD_SIZE = LIVING_AMOUNT * POINT_RADIUS + 10;
    final int BTN_PANEL_HEIGHT = 60;
    int[][] currGeneration = new int[LIVING_AMOUNT][LIVING_AMOUNT];
    int[][] nextGeneration = new int[LIVING_AMOUNT][LIVING_AMOUNT];
    volatile boolean breedNextGeneration = false;
    int showDelay = 200;

   
    public Epidemy() {

        randomizer = new Random();
        
        mainJPanel = new MainJPanel();
        
        jframe = new JFrame("game of life");
        
        generateButton = new JButton("Generate");
        stepButton = new JButton("Step");
        launchButton = new JButton("Launch");
        
        jpanelForm = new JPanel();
    }
    
    public static void main(String[] args) {
        
        new Epidemy().launch();
    }

    public void launch() {
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(FIELD_SIZE, FIELD_SIZE+60);
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
          
        System.out.println("epidemy_mode.Epidemy.breeding()");
        for (int x_id = 0; x_id < LIVING_AMOUNT; x_id++) {
            for (int y_id = 0; y_id < LIVING_AMOUNT; y_id++) {
                
                nextGeneration[x_id][y_id] = currGeneration[x_id][y_id];

                if (nextGeneration[x_id][y_id] == 2){
                    nextGeneration[x_id][y_id] = 3;
                }
                else 
                if (nextGeneration[x_id][y_id] == 3) {
                    nextGeneration[x_id][y_id] = 4;
                }
                else
                for (int i = 0; i < q; i++) { //nextGeneration[x_id][y_id] = 1
                    int nbr_state = rand_neighbor_state(x_id, y_id);
                    nextGeneration[x_id][y_id] = (nbr_state == 2 || nbr_state == 3) ? 2 : nextGeneration[x_id][y_id];
                }
            }
            
        }
        for (int x_id = 0; x_id < LIVING_AMOUNT; x_id++)
            System.arraycopy(nextGeneration[x_id], 0, currGeneration[x_id], 0, LIVING_AMOUNT);
        
    }

    public int rand_neighbor_state(int x_id, int y_id) {

        boolean i1;
        i1 = randomizer.nextBoolean();
        int nbrX = i1 ? x_id + 1 : x_id - 1;
        i1 = randomizer.nextBoolean();
        int nbrY = i1 ? y_id + 1 : y_id - 1;
        nbrX = nbrX < 0 ? LIVING_AMOUNT - 1 : nbrX;
        nbrY = nbrY < 0 ? LIVING_AMOUNT - 1 : nbrY;
        nbrX = (nbrX > LIVING_AMOUNT - 1) ? 0 : nbrX;
        nbrY = (nbrY > LIVING_AMOUNT - 1) ? 0 : nbrY;

        return currGeneration[nbrX][nbrY];
    }

    public class MainJPanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            
            super.paint(g);
            
            for (int x = 0; x < LIVING_AMOUNT; x++) {
                for (int y = 0; y < LIVING_AMOUNT; y++) {
                    
                    switch (currGeneration[x][y]) {
                        case 0:
                            currGeneration[x][y] = 1;
                        case 1:
                            g.setColor(Color.GREEN);
                            break;
                        case 2:
                            g.setColor(Color.YELLOW);
                            break;
                        case 3:
                            g.setColor(Color.RED);
                            break;
                        case 4:
                            g.setColor(Color.BLUE);
                            break;
                    }       
                    g.fillOval(x * POINT_RADIUS, y * POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
                    
                }
            }
        }
    }

    public class GenerateButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            System.out.println("epidemy_mode.Epidemy.GenerateButtonListener.actionPerformed()");
                
            int xi, yi;
            for (int i = 0; i < startAmount; i++) {
                xi = randomizer.nextInt(LIVING_AMOUNT + i) % LIVING_AMOUNT;
                yi = randomizer.nextInt(LIVING_AMOUNT + i) % LIVING_AMOUNT;
                
                System.out.println(i + " " + xi + " " + yi);
                currGeneration[xi][yi] = 2; 
            }
            mainJPanel.repaint();
        }
    }

}
