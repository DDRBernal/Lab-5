package presentacion;
import aplicacion.KalahGame;

import javax.swing.JButton;
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
import javax.xml.soap.Text;

/**
 * @version ECI 2019
 */
public class Main extends JFrame {
    static JFrame frame;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenu opciones;
    private JMenuItem nuevo,abrir,salvar,salvarComo,salir,chooseColorB,cantidadSemillas,cantidadCeldas,reiniciar;
    //    private JMenuItem abrir;
//    private JMenuItem salvar;
//    private JMenuItem salvarComo;
//    private JMenuItem salir;
//    private JMenuItem chooseColorB;
//    private JMenuItem cantidadSemillas;
//    private JMenuItem cantidadCeldas;
//    private JMenuItem reiniciar;
    private JLabel titulo;
    private ImageIcon tituloP;
    private JPanel panel1;
    private JPanel board,board2,board3,board4,board5,board6;
    private JLabel jugador,maquina;
    private int[] matrizSemillas;
    private int[] matrizSolucion;
    private Color colorEscogido;
    private int tamano= 6;
    private int Semillas= 3;
    private KalahGame game;
    private int numSemillas=3;
    private int conta=0;

    private Main(){
        super("Kalah");
        colorEscogido= Color.blue;
        game = new KalahGame(tamano,6);
        prepareElementos();
        prepareElementosMenu();
        prepararElementosTablero();
        prepareAcciones();
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
        cantidadSemillas= new JMenuItem("Elija cantidad de Semillas");
        opciones.add(cantidadSemillas);
        cantidadCeldas= new JMenuItem("Elija tamaño del tablero");
        opciones.add(cantidadCeldas);

        menuBar.add(menu);
        menuBar.add(opciones);
        this.setJMenuBar(menuBar);
    }

    private void prepararPosicionSemillas() {
        matrizSemillas= game.getSemillas();
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
        prepararPosicionSemillas();
        for(int i= 0; i < tamano+4; i++) {
            if (i%2==0){
                prepareSemillas(i,Color.RED);

            }
            else{
                prepareSemillas(i,Color.BLUE);
            }
        }

        board2= new JPanel();
        board2.setLayout(new GridLayout(3,tamano,1,1));
        board2.setBackground(new Color(200, 221, 0));
        board2.setBorder(BorderFactory.createLineBorder(new Color(200, 221, 0),4));
        prepararPosicionSemillas();
        for(int i= 0; i < 1; i++) {
            prepareSemillas(i,Color.WHITE);
        }

        board3= new JPanel();
        board3.setLayout(new GridLayout(3,tamano,1,1));
        board3.setBackground(new Color(129, 140, 235));
        board3.setBorder(BorderFactory.createLineBorder(new Color(129, 140, 235),4));
        prepararPosicionSemillas();
        for(int i= 0; i < 1; i++) {
            prepareSemillas(i,Color.BLACK);
        }



        jugador = new JLabel("0",SwingConstants.CENTER);
        board2.add(jugador);

        maquina = new JLabel("0",SwingConstants.CENTER);
        board3.add(maquina);

        board5= new JPanel();
        board5.setLayout(new GridLayout(1,tamano,1,1));
        maquina = new JLabel("maquina");
        board5.add(maquina);

        board6= new JPanel();
        board6.setLayout(new GridLayout(1,tamano,1,1));
        jugador = new JLabel("jugador");
        board6.add(jugador);

        this.add(board, BorderLayout.CENTER);
        this.add(board2, BorderLayout.SOUTH);
        this.add(board5, BorderLayout.EAST);
        this.add(board6, BorderLayout.WEST);
        this.add(board3, BorderLayout.NORTH);
//        this.add(board4, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();

    }

    private void prepareSemillas(int i,Color color) {
        if (conta==0) {
            JButton myButton = new JButton("3");
            myButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    myButton.setText("0");
                }
            });
            myButton.setBackground(new Color(200, 221, 0));
            board.add(myButton);
            conta+=1;
        }
        else{
            JButton myButton = new JButton("3");
            myButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    myButton.setText("0");
                }
            });
            myButton.setBackground(new Color(129, 140, 235));
            board.add(myButton);
            conta=0;
        }

//        if (color==Color.WHITE ){
//            Oval semilla= new Oval(Integer.toString(matrizSemillas[i]),new Color(100, 35, 1), true,false,false,10);
//            semilla.setBackground(new Color(100, 35, 1));
//            board2.add(semilla);
//        }
//        if (color==Color.BLACK ){
//            Oval semilla= new Oval(Integer.toString(matrizSemillas[i]),new Color(100, 35, 1), true,false,false,10);
//            semilla.setBackground(new Color(100, 35, 1));
//            board3.add(semilla);
//        }
//        int variable=0;
//        if (conta == 0) {
//            Oval semilla = new Oval(Integer.toString(matrizSemillas[i]), Color.red, true, false, false, 0);
//            semilla.setBackground(new Color(100, 35, 1));
//            board.add(semilla);
//            conta += 1;
//        }
//         else {
//            Oval semilla = new Oval(Integer.toString(matrizSemillas[i]), Color.blue, true, false, false, variable);
//            semilla.setBackground(new Color(100, 35, 1));
//            board.add(semilla);
//            conta = 0;
//        }
    }

    private void abrirArchivo() {
        JFileChooser file = new JFileChooser();
        file.setDialogTitle("archivos a elegir");
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (file.showOpenDialog(abrir) == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "La funcion abrir esta en construccion ");
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
        chooseColorB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseColor();
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
    private void decirSemillas() {

        String a= (String) JOptionPane.showInputDialog(new JFrame(), "Diga la cantidad de Semillas:", "Semillas", JOptionPane.QUESTION_MESSAGE);
        if(a!= null && !a.isEmpty()) {
            int test= Integer.parseInt(a);
            if(test > (tamano*tamano)/2) {
                JOptionPane.showMessageDialog(new JFrame(), "NUMERO DE Semillas INCORRECTO!", "Error Semillas", JOptionPane.ERROR_MESSAGE);
            }
            else {
                Semillas= test;
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
        refresque();
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
        game= new KalahGame(tamano,Semillas);
        prepararElementosTablero();
        refresque();
    }
    private void refresque() {
        panel1.removeAll();
        this.remove(panel1);
        prepareGrid(tamano);
//        this.repaint();
        this.revalidate();
        repaint();
    }

    private void salvarArchivo() {
        JFileChooser file= new JFileChooser();
        file.setSelectedFile(new File("save.txt"));
        file.showSaveDialog(salvar);
        try{
            String link  =  file.getSelectedFile().getName();
            String files = link.replaceAll("[^a-zA-Z0-9.]"," ");
            String[] split = files.split(" ");
            JOptionPane.showMessageDialog(null, "La funcion guardar esta en construccion ");
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
        Main kalah = new Main();
        kalah.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        kalah.setVisible(true);
    }

}
