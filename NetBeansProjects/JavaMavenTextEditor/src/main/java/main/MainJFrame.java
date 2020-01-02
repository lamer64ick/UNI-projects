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
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

public class MainJFrame extends JFrame {

    private MainTextArea mainText;
    private JFileChooser fileChooser = new JFileChooser();
    private String fileSeparator = System.getProperty("file.separator"),
            userHome = System.getProperty("user.home"),
            fileName,
            defaultFileName,
            defaultType,
            fileExtension,
            fileDir,
            currentDirectory;

    /**
     * Creates new form MainJFrame
     * @throws java.io.IOException
     */
    public MainJFrame() throws IOException {

        mainText = new MainTextArea();

        this.setBounds(0, 0, 600, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.white);
        mainText.setBounds(0, 0, 600, 500);
        this.add(mainText);
        
        this.add(new CustomPaintComponent());

        userHome = System.getProperty("user.home");
        fileSeparator = System.getProperty("file.separator");
        currentDirectory = userHome + fileSeparator + "Projects" + fileSeparator + "JavaMavenTextEditor";
        fileDir = currentDirectory;
        defaultFileName = "untitled";
        defaultType = "txt";

        fileChooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File file0) {
                if (file0.isDirectory()) {
                    return true;
                }

                String extension = FileUtils.getExtension(file0);
                if (extension != null) {
                    if (extension.equals(FileUtils.txt)
                            || extension.equals(FileUtils.dat)
                            || extension.equals(FileUtils.bin)
                            || extension.equals(FileUtils.doc)
                            || extension.equals(FileUtils.docx)
                            || extension.equals(FileUtils.tiff)
                            || extension.equals(FileUtils.tif)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
//			    return f.getName().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                // TODO Auto-generated method stub
                return "All files";
            }

        });
      
        initComponents();
    }
        
    static class CustomPaintComponent extends Component {
        
        @Override
        public void paint(Graphics g) {
            
            System.out.println("CustomPaintComponent paint method");
            
            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainJFrame().setVisible(true);
                } catch (Exception e) {
                    Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, args);
//                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem = new javax.swing.JMenuItem();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemClose = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemCopy = new javax.swing.JMenuItem();
        jMenuItemPaste = new javax.swing.JMenuItem();
        jMenuItemCut = new javax.swing.JMenuItem();
        JMenuFont = new javax.swing.JMenu();
        jMenuItemSize = new javax.swing.JMenuItem();
        jMenuItemColor = new javax.swing.JMenuItem();
        jMenuImage = new javax.swing.JMenu();
        jMenuItemLoad = new javax.swing.JMenuItem();

        jMenuItem.setText("jMenuItem");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenuFile.setText("File");

        jMenuItemNew.setText("New");
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemNew);

        jMenuItemSave.setText("Save");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSave);

        jMenuItemOpen.setText("Open");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemOpen);

        jMenuItemClose.setText("Close");
        jMenuItemClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCloseActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemClose);

        jMenuBar.add(jMenuFile);

        jMenuEdit.setText("Edit");

        jMenuItemCopy.setText("Copy");
        jMenuItemCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCopyActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemCopy);

        jMenuItemPaste.setText("Paste");
        jMenuEdit.add(jMenuItemPaste);

        jMenuItemCut.setText("Cut");
        jMenuEdit.add(jMenuItemCut);

        jMenuBar.add(jMenuEdit);

        JMenuFont.setText("Font");

        jMenuItemSize.setText("Size");
        jMenuItemSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSizeActionPerformed(evt);
            }
        });
        JMenuFont.add(jMenuItemSize);

        jMenuItemColor.setText("Color");
        JMenuFont.add(jMenuItemColor);

        jMenuBar.add(JMenuFont);

        jMenuImage.setText("Image");

        jMenuItemLoad.setText("Load");
        jMenuImage.add(jMenuItemLoad);

        jMenuBar.add(jMenuImage);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {

        fileChooser.setCurrentDirectory(new File(currentDirectory));
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File file = fileChooser.getSelectedFile();

        String fileAbsolutePath = file.getAbsolutePath();
        currentDirectory = fileAbsolutePath;
        fileName = file.getName();

        System.out.print(fileAbsolutePath);
        System.out.print(fileName);

        try {

            FileReader fileReader = new FileReader(fileAbsolutePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            mainText.doc.deserialize();
            bufferedReader.close();

        } catch (Exception exp) {
            // to handle exception
            exp.printStackTrace();

        }
    }

    private void jMenuItemCloseActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:

    }

    private void jMenuItemCopyActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:

    }

    private void jMenuItemSizeActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu JMenuFont;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuImage;
    private javax.swing.JMenuItem jMenuItem;
    private javax.swing.JMenuItem jMenuItemClose;
    private javax.swing.JMenuItem jMenuItemColor;
    private javax.swing.JMenuItem jMenuItemCopy;
    private javax.swing.JMenuItem jMenuItemCut;
    private javax.swing.JMenuItem jMenuItemLoad;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemPaste;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemSize;
    // End of variables declaration//GEN-END:variables
}
