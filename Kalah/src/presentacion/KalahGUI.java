package presentacion; 
//import aplicacion.Kalah; 
 
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 * @version ECI 2019
 */
public class KalahGUI extends JFrame {
	
	
    private KalahGUI(){
		super("Kalah"); 
		prepareElementos();
		prepareAcciones();
	}	

	private void prepareElementos() {
		this.setTitle("Kalah");
		Dimension screen = tamanoPantalla();
		this.setSize(new Dimension(screen.width/2,screen.height/2));
		centre();
	}
	private void prepareAcciones(){
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE );
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) { 
				salir();
			}
	    });
	}
	
	private void salir(){
		int dialogButton = JOptionPane.showConfirmDialog (null, "Are you sure?","WARNING",JOptionPane.YES_NO_OPTION);
		if(dialogButton == JOptionPane.YES_OPTION) {
		System.exit(0);}else {remove(dialogButton);}
	}	
	
	private void centre(){
		Dimension screen= tamanoPantalla();
		int xEsquina = (screen.width - getSize().width )/2;
		int yEsquina = (screen.height - getSize().height )/2;
		this.setLocation(xEsquina,yEsquina);
	}
	
	private Dimension tamanoPantalla(){
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public static void main(String[] args){
		KalahGUI kalah = new KalahGUI();
		kalah.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		kalah.setVisible(true);
	}
	
}
 