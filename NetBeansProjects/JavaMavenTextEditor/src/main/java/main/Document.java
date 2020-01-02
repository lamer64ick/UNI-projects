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
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

public class Document implements Serializable {

    ArrayList<Paragraph> parList = new ArrayList();
    ArrayList<Picture> picList = new ArrayList();
    ArrayList<JSONObject> cord = new ArrayList();
    int i = 0, // индекс изменяемого параграфа (меняется ф-ми mClick, highlightTop, highloghtBottom)
        currPos = 0, // позиция символа в параграфе
        currPosY = 0;
    int cX = 1, cY;
    int mX, mY;
    boolean mouseClick = false;
    boolean cSwitch = false;
    Font font = new Font("Arial", Font.PLAIN, 15);
    String  fontSring = "Arial",
            userHome,
            fileSeparator,
            defaultFileName,
            defaultFileType,
            filePath,
            fileDir,
            saveParStatName,
            savePicStatName,
            saveParPref,
            savePicPref,
            defaultType;
    int fontNum = 0;
    JSONObject textStyle = new JSONObject();
    private static final long serialVersionUID = 1L;

    public Document() throws JSONException {
        
        Paragraph zero = new Paragraph();
        parList.add(zero);
        textStyle.put("type", 0);
        textStyle.put("fontString", "Arial");
        textStyle.put("fontSize", 15);
        textStyle.put("colorR", 0);
        textStyle.put("colorG", 0);
        textStyle.put("colorB", 0);
        
        userHome = System.getProperty("user.home");
        fileSeparator = System.getProperty("file.separator");
        fileDir = userHome + fileSeparator + "Projects" + fileSeparator + "JavaMavenTextEditor";
        defaultFileName = "untitled";
        defaultFileType = ".txt";
//        filePath = fileDir 
        saveParPref = "savePar";
        savePicPref = "savePic";
        saveParStatName = "saveParStat.ser";
        savePicStatName = "savePicStat.ser";
        
    }
    
    public void add() throws JSONException {
        
        System.out.println("main.Document.add()");
        //запросить информацию о положении каретки в i-ом параграфе
        int pparIndex = parList.get(i).getParIndex(currPos),
            posInPpar = parList.get(i).getPosInPar(currPos),
            numOfPpar = parList.get(i).getInfoAmount(),
            numOfSymInPar = parList.get(i).symNumber;
        
        System.out.println(pparIndex + " " + currPos + " " + posInPpar + " " + numOfSymInPar);
        
        Paragraph newParagraph = new Paragraph();
        
        if (currPos > 0 && currPos < numOfSymInPar) {
            
            newParagraph.clearInfo();
            for (int k = pparIndex; k < numOfPpar; k++) {
                
                newParagraph.addInfo(parList.get(i).getStyle(k));
            }
            System.out.println(pparIndex + " " + posInPpar + " " + numOfPpar + " ");
            
            // вычленение части текста из подпараграфа
            String wholeText = (String) parList.get(i).getTextFromPar(pparIndex);
            int textlen = wholeText.length();
            String rightText = wholeText.substring(posInPpar, textlen);
            
            System.out.println(wholeText + " " +  rightText + " " + textlen);
            
            if (rightText.length() != 0)
                // удаление куска rightText из подпараграфа
                parList.get(i).removeTextInPar(pparIndex, posInPpar, textlen-1);
            
            if (currPos > 0)
                // удаление куска leftText из нового
                newParagraph.removeTextInPar(0, 0, textlen - rightText.length()-1);
            
            // удалить всю инф-ю о текстве в данном параграфе с i+1oй позиции:
            for (int k = numOfPpar-1; k > pparIndex; --k) {
                
                parList.get(i).removeInfo(k);
            }
        }
        
        if (i == parList.size() - 1)
        	parList.add(newParagraph);
        else {
        	if (currPos == 0)
        		parList.add(i, newParagraph);
        	else
        		parList.add(i+1, newParagraph);
        }
        	
        i++;
        currPos = 0;
        cX = 1; //?
    }

    public void switchFont(String FontString) throws JSONException {
        textStyle.put("FontString", FontString);
    }

    public void switchColor(int R, int G, int B) throws JSONException {
        
        textStyle.put("colorR", R);
        textStyle.put("colorG", G);
        textStyle.put("colorB", B);
    }

    public void switchSize(int fontSize) throws JSONException {
        
        textStyle.put("fontSize", fontSize);
    }

    public void addSymb(char tecSymb) throws JSONException {
        
        parList.get(i).addSymbol(tecSymb, currPos, textStyle);
        moveCurRight();
    }

    public void paint(Graphics g, int w, int h) throws JSONException, IOException {
        
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);
        int xt = 1, yt = 2;

        for (int ik = 0; ik < parList.size(); ik++) {
            
            parList.get(ik).paint(g, xt, yt);
           
            yt += parList.get(ik).paragraphHeight();
            if (i == ik) { 
                g.drawLine(parList.get(i).posFromStart(currPos), yt, parList.get(i).posFromStart(currPos), yt - 12);
            }
        }
        for(int ik = 0; ik < picList.size(); ik++) {
            
            picList.get(ik).paint(g);
        }
    }

    public void mClick(int x, int y) {
        
        mouseClick = true;
        mX = x;
        mY = y;
        int nowI = mY / 12;
        if (nowI > parList.size() - 1) {
            i = parList.size() - 1;
        } else {
            i = nowI;
        }
//        currPos = 0;
    }

    public void highlightTop() throws JSONException {//maybe input un one function
        
        if (i > 0) {
            
            if (currPos > parList.get(i - 1).symNumber) {
                currPos = parList.get(i - 1).symNumber;
            }
            i--;
        }
    }

    public void highlightBottom() throws JSONException {//maybe input un one function
        
        if (i < (parList.size() - 1)) {
            
            if (currPos > parList.get(i + 1).symNumber) {
                currPos = parList.get(i + 1).symNumber;
            }
            i++;
        }
    }

    public void moveCurRight() throws JSONException {
        
        if (currPos < parList.get(i).symNumber) {
            currPos++;
            System.out.println("Курсор двинулся right");
            System.out.println(currPos);
            System.out.println("---------------");
        }
        System.out.println("main.Document.moveCurRight() currPos symNumber " + currPos + " " + parList.get(i).symNumber);
    }

    public void moveCurLeft() throws JSONException {
        if (currPos > 0) {
            currPos--;
            System.out.println("Курсор двинулся left");
            System.out.println(currPos);
            System.out.println("---------------");
        }
    }

    public void serialize() throws FileNotFoundException, IOException {
        
        String saveFilePath;
        
        for (int i = 0; i < parList.size(); i++) {
            
            saveFilePath = fileDir + fileSeparator + saveParPref + i + ".ser";
            FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(parList.get(i).PrepareForSave());

            objectOutputStream.close();
        }
        for (int i = 0; i < picList.size(); i++){
            
            saveFilePath = fileDir + fileSeparator + savePicPref + i + ".ser";
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(picList.get(i));

            objectOutputStream.close();
        }
        FileWriter fileWriter = new FileWriter(fileDir + fileSeparator + saveParStatName);
        fileWriter.write(parList.size());
        fileWriter.close();
        FileWriter fileWriter1 = new FileWriter(fileDir + fileSeparator + savePicStatName);
        fileWriter1.write(picList.size());
        fileWriter1.close();
        System.out.println("SAVE");
    }

    public void addPic(File file) throws IOException {
        
        Picture f = new Picture(file);
        picList.add(f);
    }

    public void deserialize() throws FileNotFoundException, IOException, ClassNotFoundException, JSONException {

        i = 0;
        currPos = 0;
        
        FileReader  fPar = new FileReader(fileDir + fileSeparator + saveParStatName),
                    fPic = new FileReader(fileDir + fileSeparator + savePicStatName);
        int parN, picN;
        parN = fPar.read();
        picN = fPic.read();

        parList = new ArrayList();
        picList = new ArrayList();
        
        for (int i = 0; i < parN; i++) {
            
            FileInputStream fileInputStream = new FileInputStream(fileDir + fileSeparator + saveParPref + i + ".ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            
            parList.add((Paragraph) objectInputStream.readObject());
            parList.get(i).AfterLoad(parList.get(i));
        }
        for (int i = 0; i < picN; i++) {
            
            FileInputStream fileInputStream = new FileInputStream(fileDir + fileSeparator + savePicPref + i + ".ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            
            picList.add((Picture) objectInputStream.readObject());
        }
    }

    public void changePicPos(int x, int y) {
        
        if (picList.size() > 0) {
            
            picList.get(picList.size()-1).changePos(x, y);
        }
    }

    public void deletElement() throws JSONException {
        
        System.out.println("main.Document.deletElement()");

        if (parList.get(i).symNumber == 0) {
            System.out.println("parList.get(i).symNumber == 0 i " + i);
            if (i == 0) return;
            parList.remove(i);
            highlightTop();
            currPos = parList.get(i).symNumber;
        }
        else {
            System.out.print("parList.get(i).symNumber > 0");
            parList.get(i).deleteElement(currPos);
            moveCurLeft();
        }

    }

}
