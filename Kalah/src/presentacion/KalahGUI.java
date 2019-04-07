package presentacion; 
import aplicacion.KalahGame;
import presentacion.Oval;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.Random;

import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 * @version ECI 2019
 */
public class KalahGUI extends JFrame {
		
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenu opciones;
    private JMenuItem nuevo;
    private JMenuItem abrir;
    private JMenuItem salvar;
    private JMenuItem salvarComo;
    private JMenuItem salir;
    private JMenuItem chooseColorB;
    private JMenuItem cantidadCanicas;
    private JMenuItem cantidadCeldas;
    private JMenuItem reiniciar;
    private JLabel titulo;
    private ImageIcon tituloP;
    private JPanel panel1;
    private JPanel board;
    private int[] matrizCanicas;
    private int[] matrizSolucion;
    private Color colorEscogido;
    private int tamano= 6;
    private int canicas= 3;
    private KalahGame game;
    private int numSemillas=3;
    private int conta=0;
	
    private KalahGUI(){
        super("Kalah");
        colorEscogido= Color.blue;
        game = new KalahGame(tamano,6);
        prepareElementos();
        prepareElementosMenu();
        prepararElementosTablero();
        prepareAcciones();
	}	
	
	
	
	private void prepareElementos() {
		this.setTitle("Kalah");
		Dimension screen = tamanoPantalla();
		this.setSize(new Dimension(screen.width/2,screen.height/2));
		centre();
	}
	
    private void key(KeyEvent e) {
        int id= e.getKeyCode();

        if(id== KeyEvent.VK_RIGHT) {
            game.moveRight();
            limpiar();

        }
        else if(id == KeyEvent.VK_LEFT) {
            game.moveLeft();
            limpiar();
        }
        else if(id == KeyEvent.VK_DOWN) {
            game.moveDown();
            limpiar();
        }
        else if(id == KeyEvent.VK_UP) {
            game.moveUp();
            limpiar();
        }
    }
    private void prepareElementosMenu(){
        menuBar= new JMenuBar();
        menu= new JMenu("Menu");
        nuevo= new JMenuItem("Nuevo");
        menu.add(nuevo);
        abrir= new JMenuItem("Abrir");
        menu.add(abrir);
        salvar= new JMenuItem("Salvar");
        menu.add(salvar);
        salvarComo= new JMenuItem("Salvar como");
        menu.add(salvarComo);
        salir= new JMenuItem("Salir");
        menu.add(salir);

        opciones= new JMenu("Opciones");
        chooseColorB= new JMenuItem("Escoge color");
        opciones.add(chooseColorB);
        reiniciar= new JMenuItem ("Reiniciar");
        opciones.add(reiniciar);
        cantidadCanicas= new JMenuItem("Elija cantidad de canicas");
        opciones.add(cantidadCanicas);
        cantidadCeldas= new JMenuItem("Elija tamaño del tablero");
        opciones.add(cantidadCeldas);

        menuBar.add(menu);
        menuBar.add(opciones);
        this.setJMenuBar(menuBar);
    }

    private void prepararPosicionCanicas() {
        matrizCanicas= game.getCanicas();
        matrizSolucion= game.getSolucion();

    }
    private void prepararElementosTablero() {
        prepareTitulo();
        prepareGrid(tamano);
    }

    private void prepareTitulo() {
        setLayout(new BorderLayout());
        panel1= new JPanel();
        tituloP= new ImageIcon("Recursos/tittle.png");
        titulo= new JLabel(tituloP);
        panel1.add(titulo);
        this.add(panel1,BorderLayout.NORTH);
    }
    private void prepareGrid(int tamano) {
        board= new JPanel();
        board.setLayout(new GridLayout(tamano,tamano,5,5));
        board.setBackground(Color.DARK_GRAY);
        board.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,4));
        prepararPosicionCanicas();
        for(int i= 0; i < tamano*2; i++) {
            if (i%2==0){
                prepareCanicas(i,Color.RED);
            }
            else{
                prepareCanicas(i,Color.BLUE);
            }
        }
        this.add(board, BorderLayout.CENTER);
    }


    private void prepareCanicas(int i,Color color) {
//        if (matrizSolucion[i]== 0 && matrizCanicas[i]==0){
//            Oval canica= new Oval(Integer.toString(matrizCanicas[i]-tamano),Color.red,false,false,false);
//            canica.setBackground(new Color(100, 35, 1));
//            board.add(canica);
//        }
//        else if (matrizCanicas[i]==matrizSolucion[i]) {
//            Oval canica= new Oval(Integer.toString(matrizCanicas[i]),Color.red, true,true,true);
//            canica.setBackground(new Color(100, 35, 1));
//            board.add(canica);
//        }
//        else if(matrizCanicas[i]!= 0) {
//            if (matrizSolucion[i]== 0) {
                if (conta==0){
                    Oval canica= new Oval(Integer.toString(matrizCanicas[i]),Color.red, true,false,false);
                    canica.setBackground(new Color(100, 35, 1));
                    board.add(canica);
                    conta+=1;
                }
                else{
                    Oval canica= new Oval(Integer.toString(matrizCanicas[i]),Color.blue, true,false,false);
                    canica.setBackground(new Color(100, 35, 1));
                    board.add(canica);
                    conta=0;
                }
//            }
//            else {
//                Oval canica= new Oval(Integer.toString(matrizCanicas[i]),Color.red, true,false,true);
//                canica.setBackground(new Color(100, 35, 1));
//                board.add(canica);
//            }
//        }
//        else if(matrizSolucion[i]!= 0) {
//            Oval canica= new Oval(Integer.toString(matrizSolucion[i]),Color.red,false,false,true);
//            canica.setBackground(new Color(100, 35, 1));
//            board.add(canica);
//        }

    }
    private void abrirArchivo() {
        JFileChooser file = new JFileChooser();
        file.setDialogTitle("archivos a elegir");
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (file.showOpenDialog(abrir) == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Elegiste este archivo para abrir: " +
                    file.getSelectedFile().getName());
        }

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
        abrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirArchivo();
            }
        });
        salvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarArchivo();
            }
        });
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                key(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyPressed(KeyEvent e) {
                key(e);
            }
        });
    }
    private void decirCanicas() {

        String a= (String) JOptionPane.showInputDialog(new JFrame(), "Diga la cantidad de canicas:", "Canicas", JOptionPane.QUESTION_MESSAGE);
        if(a!= null && !a.isEmpty()) {
            int test= Integer.parseInt(a);
            if(test > (tamano*tamano)/2) {
                JOptionPane.showMessageDialog(new JFrame(), "NUMERO DE CANICAS INCORRECTO!", "Error Canicas", JOptionPane.ERROR_MESSAGE);
            }
            else {
                canicas= test;
                reStart();
            }
        }
    }
    private void decirTamano() {
        String a= (String) JOptionPane.showInputDialog(new JFrame(), "Diga la cantidad de filas:", "Filas", JOptionPane.QUESTION_MESSAGE);
        if(a!= null && !a.isEmpty()) {
            tamano= Integer.parseInt(a);
            reStart();
        }
    }

    private void chooseColor() {
        colorEscogido= JColorChooser.showDialog(null, "Escoge tu color", colorEscogido);
        if (colorEscogido == null) {
            colorEscogido= Color.BLUE;
        }
        limpiar();
    }

    private void limpiar() {
        board.removeAll();
        panel1.removeAll();
        this.remove(panel1);
        this.remove(board);
        prepararElementosTablero();
        refresque();
    }

    private void reStart() {
        board.removeAll();
        panel1.removeAll();
        this.remove(panel1);
        this.remove(board);
        game= new KalahGame(tamano,canicas);
        prepararElementosTablero();
        refresque();
    }
    private void refresque() {
        this.revalidate();
    }
    private void salvarArchivo() {
        JFileChooser file= new JFileChooser();
        file.setSelectedFile(new File("save.txt"));
        file.showSaveDialog(salvar);
        try{
            String link  =  file.getSelectedFile().getName();
            String files = link.replaceAll("[^a-zA-Z0-9.]"," ");
            String[] split = files.split(" ");
            JOptionPane.showMessageDialog(null,"Funcion Salvar "+split[split.length-1],"En proceso",JOptionPane.ERROR_MESSAGE);
        }catch(Exception e){}
    }


    private void salir(){
        int dialogButton = JOptionPane.showConfirmDialog (null, "Are you sure??","WARNING",JOptionPane.YES_NO_OPTION);
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
 