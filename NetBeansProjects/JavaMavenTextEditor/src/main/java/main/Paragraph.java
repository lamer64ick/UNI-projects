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
import org.json.JSONObject;
import org.json.JSONException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.io.Serializable;
import javax.swing.JComponent;

public class Paragraph implements Serializable {
    
    private static final long serialVersionUID = 1L;
    //ArrayList lineHeight = new ArrayList(); // Переделать, доделать когда будет перенос строк
    int lineHeight;
    transient ArrayList<JSONObject> txtInfoList = new ArrayList(); // Массив TextInfo Подпараграфов
    Font font = new Font("Arial", Font.PLAIN, 15);
    public int symNumber = 0; // Общее число символов в параграфе
    public int parIndex = 0; //  Индекс текущего изменяемого подпараграфа
    public int posInPar; // Позиция курсора в текущем изменяемом подпараграфе
    ArrayList<String> parList = new ArrayList(); // Сохраняемые подпараграфы
    
    
    public Paragraph() throws JSONException {
        
        JSONObject f = new JSONObject();
        f.put("type", 0);// 0-text, 1-picture
        f.put("text", "");
        f.put("font", font);
        f.put("fontString", "Arial");
        f.put("fontSize", 15);
        f.put("colorR", 0);
        f.put("colorG", 0);
        f.put("colorB", 0);
        f.put("width", 0);
        f.put("length", 0);
        txtInfoList.add(f);
        lineHeight = (int) f.get("fontSize");
        
    }
    
    public JSONObject getStyle(int parIndex) throws JSONException {
        
        System.out.println("main.Paragraph.getStyle()");
        JSONObject copyTo = new JSONObject(),
                   copyFrom = txtInfoList.get(parIndex);
        System.out.println(parIndex);
        System.out.println(copyFrom);
        copyTo.put("type", copyFrom.get("type"));// 0-text, 1-picture
        copyTo.put("text", (String)copyFrom.get("text"));
        copyTo.put("font", copyFrom.get("font"));
        copyTo.put("fontString", copyFrom.get("fontString"));
        copyTo.put("fontSize", copyFrom.get("fontSize"));
        copyTo.put("colorR", copyFrom.get("colorR"));
        copyTo.put("colorG", copyFrom.get("colorG"));
        copyTo.put("colorB", copyFrom.get("colorB"));
        copyTo.put("width", copyFrom.get("width"));
        copyTo.put("length", copyFrom.get("length"));
        System.out.println(copyTo);
        return copyTo;
    }
    
    public void paint(Graphics g, int x, int y) throws JSONException{     
        
        int localX = x, localY = y + lineHeight;
        
        for (int i = 0; i < txtInfoList.size(); i++) {
            
            g.setColor(new Color((int)txtInfoList.get(i).get("colorR"),(int)txtInfoList.get(i).get("colorG"),(int)txtInfoList.get(i).get("colorB")));
            System.out.println("color "+(int)txtInfoList.get(i).get("colorR")+" "+(int)txtInfoList.get(i).get("colorG")+" "+(int)txtInfoList.get(i).get("colorB")+" "+i);
            g.setFont((Font) txtInfoList.get(i).get("font"));
            g.drawString((String) txtInfoList.get(i).get("text"), localX, localY);
            localX += (int) txtInfoList.get(i).get("width");
        }
        
    }
    
    public void addSymbol(char tecSymb, int currPos, JSONObject inJson) throws JSONException{ // Добавление символа в массив по индексу
        
        setParIndexAndIndexInPar(currPos);
        
        if(!checkChangeFont(inJson, txtInfoList.get(parIndex))) {
            addChar(tecSymb);
        }
        else { // если изменено форматирование
            
            JSONObject f = new JSONObject();
            f.put("type", 0);// 0-text, 1-picture
            f.put("text", "");
            f.put("fontString", (String) inJson.get("fontString"));
            f.put("fontSize", (int) inJson.get("fontSize"));
            f.put("colorR", (int) inJson.get("colorR"));
            f.put("colorG", (int) inJson.get("colorG"));
            f.put("colorB", (int) inJson.get("colorB"));
            f.put("width", 0);
            f.put("length", 0);
            f.put("font", new Font((String) f.get("fontString"), Font.PLAIN, (int) f.get("fontSize")));
            lineHeight = (int) f.get("fontSize");
            
            if (currPos == 0) {
                
                txtInfoList.add(0, f);
                addChar(tecSymb);
            }
            else
                if (parIndex == txtInfoList.size()-1 && (int)txtInfoList.get(parIndex).get("length") == posInPar) { //конец последнего подпараграфа
                    txtInfoList.add(f);
                    parIndex++;
                    posInPar = 0;
                    addChar(tecSymb);                
                }
                else
                    if ((int)txtInfoList.get(parIndex).get("length") == posInPar) { //конец подпараграфа
                        txtInfoList.add(parIndex+1, f);
                        parIndex++;
                        posInPar = 0;
                        addChar(tecSymb); 
                    }
                    else { //где-то в подпараграфе параграфа
                        
                        JSONObject txtInfo = txtInfoList.get(parIndex);
                        String str = (String) txtInfoList.get(parIndex).get("text");
                        int length = (int) txtInfoList.get(parIndex).get("length");                       
                        String str1 = str.substring(0, posInPar);
                        String str2 = str.substring(posInPar, length);

                        JSONObject a = new JSONObject();
                        a.put("type", 0);// 0-text, 1-picture
                        a.put("text", str1);
                        a.put("fontString", (String) txtInfo.get("fontString"));
                        a.put("fontSize", (int) txtInfo.get("fontSize"));
                        a.put("colorR", (int) txtInfo.get("colorR"));
                        a.put("colorG", (int) txtInfo.get("colorG"));
                        a.put("colorB", (int) txtInfo.get("colorB"));
                        a.put("font", new Font((String) a.get("fontString"), Font.PLAIN, (int) a.get("fontSize")));

                        a.put("length", str1.length());
                        a.put("width", widthTec((String) a.get("text"), (Font) a.get("font")));
                        txtInfoList.remove(parIndex);
                        txtInfoList.add(parIndex, a); 
                        
                        txtInfoList.add(parIndex+1, f);
                        int kooooo = parIndex+2;
                        JSONObject b = txtInfo;
                        b.put("text", str2);
                        b.put("length", str2.length());
                        b.put("width", widthTec((String) b.get("text"), (Font) b.get("font")));
                        txtInfoList.add(kooooo, b);
                        
                        parIndex++;
                        posInPar = 0;
                        addChar(tecSymb); 
                    }
        }
        symNumber++; 
    }
    
    private void addChar(char tecSymb) throws JSONException {
        
        System.out.println("main.Paragraph.addChar()");
        String str = (String) txtInfoList.get(parIndex).get("text");
        String str1 = str.substring(0, posInPar);
        int length = (int) txtInfoList.get(parIndex).get("length");
        String str2 = str.substring(posInPar, length);
        str = str1 + tecSymb + str2;    
        txtInfoList.get(parIndex).put("text", str);
        txtInfoList.get(parIndex).put("length", str.length());
        txtInfoList.get(parIndex).put("width", widthTec((String) txtInfoList.get(parIndex).get("text"), (Font) txtInfoList.get(parIndex).get("font")));        
    }
    
    public void deleteElement(int currPos) throws JSONException {
        
        setParIndexAndIndexInPar(currPos);

        if ((int) txtInfoList.get(parIndex).get("length") >= 1) { 
            
            String str = (String) txtInfoList.get(parIndex).get("text");
            String str1, str2;
            if (posInPar > 0)
                str1 = str.substring(0, posInPar-1);
            else
                str1 = str.substring(0, 0);
            int length = (int) txtInfoList.get(parIndex).get("length");
            str2 = str.substring(posInPar, length);
            str = str1 + str2;    
            txtInfoList.get(parIndex).put("text", str);
            txtInfoList.get(parIndex).put("length", str.length());
            txtInfoList.get(parIndex).put("width", widthTec((String) txtInfoList.get(parIndex).get("text"), (Font) txtInfoList.get(parIndex).get("font")));
            symNumber--;
        }
        else { //txtInfoList.get(parIndex).get("length") = 0
            System.out.print("public void Paragraph.deleteElement(int currPos) parIndex " + parIndex);
            if (parIndex != 0)
                txtInfoList.remove(parIndex);
            else {
//                txtInfoList.get(parIndex).put("text", "");
                txtInfoList.get(parIndex).put("length", 0);
                txtInfoList.get(parIndex).put("width", widthTec((String) txtInfoList.get(parIndex).get("text"), (Font) txtInfoList.get(parIndex).get("font")));
            } 
        }
        
    } 
    
    public int posFromStart(int k) throws JSONException {
        
        int nextParIndex = 0;
        int i = 0, length =0;
        while (i < k) {
            int tecLength = (int) txtInfoList.get(nextParIndex).get("length");
            Font tecFont = (Font) txtInfoList.get(nextParIndex).get("font");
            String tecString = (String) txtInfoList.get(nextParIndex).get("text");
            int l = 0;
            while(l < tecLength && i < k){
                length += widthTec(tecString.substring(l, l+1), tecFont);
                l++;
                i++;
            }
            if(i < k){
                tecLength = (int) txtInfoList.get(nextParIndex+1).get("length");
                tecFont = (Font) txtInfoList.get(nextParIndex+1).get("font");
                tecString = (String) txtInfoList.get(nextParIndex+1).get("text");
                length += widthTec(tecString.substring(0, 1), tecFont);
            }
            i++;
            nextParIndex++;
        }
        return length;
    }
    
    private int widthTec(String str, Font font) { // Количество пикселей занимаемых стрингом с заданым шрифтом
        
        FontRenderContext frc = new FontRenderContext(null, VALUE_TEXT_ANTIALIAS_DEFAULT,VALUE_FRACTIONALMETRICS_DEFAULT);     
        int textWidth = (int)(font.getStringBounds(str, frc).getWidth());
        return textWidth;
    } 
 
    public int paragraphHeight(){
//        int heigt = 0;
//        for(int i = 0; i < lineHeight.size(); i++)
//            heigt += (int) lineHeight.get(i);
        return lineHeight;
    }
    
    private void setParIndexAndIndexInPar(int currPos) throws JSONException { // Индекс позиции курсора в массиве
        
        int nextPParIndex = 0;
        int i = 0, //счетчик символов в параграфе
            l = 0; //счетчик символов в подпараграфе
        while (i < currPos) {
            
            int pparLength = (int) txtInfoList.get(nextPParIndex).get("length");
            
            while (l < pparLength && i < currPos){
                i++;
                l++;
            }
            if(i < currPos){
                nextPParIndex++;                
                l = 0;
            }
            //i++;
        }
        parIndex = nextPParIndex;
        posInPar = l;
    }
    
    public Paragraph PrepareForSave(){
        
        parList = new ArrayList();
        for(int i = 0; i < txtInfoList.size(); i++){
            parList.add(txtInfoList.get(i).toString());
        }
        return this;
    }
    
    public void AfterLoad(Paragraph dl) throws JSONException{
        
        txtInfoList = new ArrayList();
        for (int i = 0; i < parList.size(); i++) {
            System.out.println(parList.get(i));
            txtInfoList.add(new JSONObject(parList.get(i)));
            txtInfoList.get(i).put("font", new Font((String) txtInfoList.get(i).get("fontString"), Font.PLAIN, (int) txtInfoList.get(i).get("fontSize")));
        }
    }
    
    private boolean checkChangeFont(JSONObject one, JSONObject second) throws JSONException{
        
        if((String)one.get("fontString") != (String)second.get("fontString"))
            return true;
        if((int)one.get("fontSize") != (int)second.get("fontSize"))
            return true;
        if((int)one.get("colorR") != (int)second.get("colorR"))
            return true;
        if((int)one.get("colorG") != (int)second.get("colorG"))
            return true;
        if((int)one.get("colorB") != (int)second.get("colorB"))
            return true;
        return false; // изменений нет
    }
    
    public void removeTextInPar(int parIndex, int begin, int end) {
        
        System.out.println("main.Paragraph.removeTextInPar()");
        System.out.println(parIndex + " " + begin + " " + end);
        String lefttmp = "", righttmp = "";
        String tmp = (String) txtInfoList.get(parIndex).get("text");
        System.out.println(tmp);
        if (end < tmp.length() - 1)
        	righttmp = tmp.substring(end+1, tmp.length());
        
        if (begin > 0)
        	lefttmp = tmp.substring(0, begin);
        
        String text = lefttmp + righttmp;
        System.out.println(text);
        txtInfoList.get(parIndex).put("text", text);
        txtInfoList.get(parIndex).put("length", text.length());
        txtInfoList.get(parIndex).put("width", widthTec((String) txtInfoList.get(parIndex).get("text"), (Font) txtInfoList.get(parIndex).get("font")));
        symNumber -= tmp.length() - text.length();
    }   
    
    public void prependText(int parIndex, String text0) {
        
        String tmp = (String) txtInfoList.get(parIndex).get("text"),
               text = text0 + tmp;
        System.out.println("main.Paragraph.addText()");
        txtInfoList.get(parIndex).put("text", text);
        txtInfoList.get(parIndex).put("length", text.length());
        txtInfoList.get(parIndex).put("width", widthTec((String) txtInfoList.get(parIndex).get("text"), (Font) txtInfoList.get(parIndex).get("font")));
        symNumber += text0.length();
        
    }
    
    public void addInfo(JSONObject newInfo) {
        
        txtInfoList.add(newInfo);
        symNumber += newInfo.get("text").toString().length();
    }
    
    public int getPosInPar(int currPos) {
        setParIndexAndIndexInPar(currPos);
        return posInPar;
    }
    
    public int getParIndex(int currPos) {
        setParIndexAndIndexInPar(currPos);
        return parIndex;
    }
    
    public JSONObject getParInfo(int parIndex) {
        
        return txtInfoList.get(parIndex);
    }
    
    public String getTextFromPar(int parIndex) {
        
        return txtInfoList.get(parIndex).get("text").toString();
    }

    public int getInfoAmount() {
        
        return txtInfoList.size();
    }
    
    public void removeInfo(int parIndex) {
        
        txtInfoList.remove(parIndex);
    }
    
    public void clearInfo() {
        
        txtInfoList.clear();
    }
    
}
