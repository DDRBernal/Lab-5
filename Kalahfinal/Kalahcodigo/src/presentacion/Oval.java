package presentacion;

import java.io.*;
import java.text.AttributedCharacterIterator;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import javafx.scene.layout.Border;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

class Oval extends JPanel {
    private String numero ;
    private Color color;
    private boolean isCanica;
    private boolean isCorrect;
    private boolean hueco;
    private int sum;
    public Oval(String numero, Color color, boolean isCanica, boolean isCorrect, boolean hueco, int suma) {
        super();
        this.sum=suma;
        this.numero = numero;
        this.color= color;
        this.isCanica= isCanica;
        this.isCorrect= isCorrect;
        this.hueco= hueco;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String text = "3";
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        int dx = x / 2;
        int dy = y / 2;
        if(isCorrect || (isCanica && hueco)) {
            g.setColor(Color.BLACK);
            g.fillOval(dx + dx / 2, dy, y, y);
            g.setColor(color);
            g.fillOval( dx+dx / 2+5, dy+5, y-10, y-10);
        }
        else {
            g.setColor(color);
            g.fillOval(dx+dx / 2, dy, y, y);

        }
        FontMetrics fm = g.getFontMetrics();
        double textWidth = fm.getStringBounds(text, g).getWidth();
        g.setColor(Color.black);
        if(color.equals(Color.gray)) g.setColor(Color.gray);
        if (sum==0){
            g.drawString(text, (int) (x-38- textWidth/2),
                    (int) (y  + fm.getMaxAscent() / 2));
        }
    }

}