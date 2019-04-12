package presentacion;
import aplicacion.KalahGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
//import javax.xml.soap.Text;

/**
 * @version ECI 2019
 */
public class KalahGUI extends JFrame {
    static JFrame frame;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenu opciones;
    private JMenuItem nuevo,abrir,salvar,salvarComo,salir,chooseColorUsuario,chooseColorMaquina,cantidadSemillas,cantidadCeldas,reiniciar;
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
    private JPanel board,board2,board3,board4,board5,board6, board7;
    private JLabel jugador,maquina,jugadas;
    private int[] matrizSemillas;
    private int[] matrizSolucion;
    private Color colorEscogido,colorEscogido2;
    private boolean colorCambiado= false;
    private boolean colorCambiado2= false;
    private int tamano= 6;
    private int Semillas= 3;
    private KalahGame game;
    private int numSemillas=3;
    private int conta=0;
    private ArrayList<JButton> buttonGrid= new ArrayList<JButton>();
    private JButton aceptar;
    private JButton accept;
    private int numeroJugadas=0;
    private JTextField numIngreUsuario,numIngreMauina;
    private int semillasUsuario=0;
    private int semillasMaquina=0;
    private ArrayList<Integer> auxiliar= new ArrayList<>();

    private KalahGUI(){
        super("Kalah");
        colorEscogido= new Color(111, 181, 253);
        game = new KalahGame(tamano,Semillas);
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
        reiniciar= new JMenu("Reiniciar");
        abrir= new JMenuItem("Abrir");
        menu.add(abrir);
        salvar= new JMenuItem("Salvar");
        menu.add(salvar);
        salvarComo= new JMenuItem("Salvar como");
        menu.add(salvarComo);
        salir= new JMenuItem("Salir");
        menu.add(salir);
        opciones= new JMenu("Opciones");
        chooseColorUsuario= new JMenuItem("Escoge color usuario");
        chooseColorMaquina= new JMenuItem("Escoge color Maquina");
        opciones.add(chooseColorUsuario);
        opciones.add(chooseColorMaquina);
        cantidadSemillas= new JMenu("Elija cantidad de Semillas");
        opciones.add(cantidadSemillas);
        cantidadCeldas= new JMenu("Elija tamaño del tablero");
        opciones.add(cantidadCeldas);
        aceptar  = new JButton("Aceptar");
        accept  = new JButton("Aceptar");
        numIngreUsuario = new JTextField();
        numIngreUsuario.setBounds(120,18,90,30);
        numIngreMauina=new JTextField();
        numIngreMauina.setBounds(120,18,90,30);
        menuBar.add(menu);
        menuBar.add(opciones);
        menuBar.add(reiniciar);
        cantidadCeldas.add(numIngreUsuario);
        cantidadCeldas.add(aceptar);
        cantidadSemillas.add(numIngreMauina);
        cantidadSemillas.add(accept);
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
        for(int i= 0; i < 2*tamano; i++) {
//            for (int j=0; j<2;j++)
            if (i%2==0){
                if(!colorCambiado) {colorEscogido=Color.GREEN;}
                prepareSemillas(i,colorEscogido);

            }
            else{
                if(!colorCambiado2) {colorEscogido2=new Color(111, 181, 253);}
                prepareSemillas(i,colorEscogido2);
            }
        }

        for (int i=0; i<buttonGrid.size(); i++){
            final int a=i;
            if (i%2==0){
            buttonGrid.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int numero=0;
                    if (a%2!=0){numero=1;}
                    game.modifiquematriz(a/2,numero, true);

                    int otra=0;
                    otra= game.getOtraVez();
                    if (otra!=0){otraVez(otra,false);}

                    //turnoMaquina
                    int valor= numeroAlAzar();
                    numero=1;
                    game.modifiquematriz(valor,numero,false);
                    auxiliar=game.getMatrizAdyacente();
                    int otra2=0;
                    otra= game.getOtraVez();
                    if (otra!=0){otraVez(otra2,false);}

                    for (int i=0; i<auxiliar.size(); i++){
                        buttonGrid.get(i).setText(Integer.toString(auxiliar.get(i)));
                    }
                    //turno maquina
                    auxiliar.clear();
                    semillasMaquina=game.getSemillasMaquina();
                    semillasUsuario=game.getSemillasJugador();
                    refresqueBoards();
                }
            });}

        }
        board2= new JPanel();
        board2.setLayout(new GridLayout(3,tamano,1,1));
        board2.setBackground(colorEscogido);
        board2.setBorder(BorderFactory.createLineBorder(new Color(111, 181, 253),4));

        board3= new JPanel();
        board3.setBackground(colorEscogido2);
        board3.setLayout(new GridLayout(3,tamano,1,1));
        board3.setBorder(BorderFactory.createLineBorder(new Color(129, 140, 235),4));

        jugador = new JLabel("Cantidad semillas Jugador: "+Integer.toString(semillasUsuario),SwingConstants.CENTER);
        jugador.setBackground(colorEscogido);
        board2.add(jugador);

        maquina = new JLabel("Cantidad semillas PC: "+Integer.toString(semillasMaquina),SwingConstants.CENTER);
        maquina.setBackground(colorEscogido2);
        board3.add(maquina);

        jugadas=new JLabel("Numero jugadas: "+Integer.toString(numeroJugadas));
        jugadas.setBackground(colorEscogido2);
        board3.add(jugadas);

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
        this.revalidate();
        this.repaint();

    }

    private void otraVez(int otra, boolean juegaJugador){

        if (juegaJugador==false){
        for (int i=0; i<buttonGrid.size(); i++){
            final int a=i;
            if (i%2==0){
                buttonGrid.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int numero=0;
                        if (a%2!=0){numero=1;}
                        game.modifiquematriz(a/2,numero, false);
                        auxiliar= game.getMatrizAdyacente();

                        for (int i=0; i<auxiliar.size(); i++){
                            buttonGrid.get(i).setText(Integer.toString(auxiliar.get(i)));
                        }
                        auxiliar.clear();
                        semillasMaquina=game.getSemillasMaquina();
                        semillasUsuario=game.getSemillasJugador();
                        refresqueBoards();
                    }
                });}}
    }
        else{
            for (int i=0; i<buttonGrid.size(); i++){
                final int a=i;
                if (i%2==0){
                    buttonGrid.get(i).addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //juega maquina
                            int numero=0;
                            int valor= numeroAlAzar();
                            numero=1;
                            game.modifiquematriz(valor,numero, true);
                            for (int i=0; i<auxiliar.size(); i++){
                                buttonGrid.get(i).setText(Integer.toString(auxiliar.get(i)));
                            }
                            auxiliar.clear();
                            semillasMaquina=game.getSemillasMaquina();
                            semillasUsuario=game.getSemillasJugador();
                            refresqueBoards();
                        }
                    });}}
        }
    }

    private int numeroAlAzar(){
        int numero = (int) (Math.random() * tamano);
        return numero;
    }

    private void actualizarBoards(){
        board2= new JPanel();
        board2.setLayout(new GridLayout(3,tamano,1,1));
        board2.setBackground(colorEscogido);
        board2.setBorder(BorderFactory.createLineBorder(new Color(111, 181, 253),4));


        board3= new JPanel();
        board3.setBackground(colorEscogido2);
        board3.setLayout(new GridLayout(3,tamano,1,1));
        board3.setBorder(BorderFactory.createLineBorder(new Color(129, 140, 235),4));
    }
    private void prepareSemillas(int i,Color color) {
        JButton myButton = new JButton(Integer.toString(Semillas));
        buttonGrid.add(myButton);
        if (i==0) {
            myButton.setBackground(color);
            myButton.setOpaque(true);
            board.add(myButton);
            conta+=1;
        }
        else{
            myButton.setBackground(color);
            myButton.setOpaque(true);
            board.add(myButton);
            conta=0;
        }
        for (int a=0; a<buttonGrid.size(); a++){

        }

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
        chooseColorUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseColor();
            }
        });
        chooseColorMaquina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseColor2();
            }
        });
        aceptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                nuevo();
            }
        });
        accept.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                nuevo2();
            }
        });
        reiniciar.addMouseListener(new MouseAdapter(){

            public  void mouseClicked(MouseEvent e){
                reStart() ;
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

    private void chooseColor() {
        colorCambiado=true;
        colorEscogido= JColorChooser.showDialog(null, "color usuario", colorEscogido);
        if (colorEscogido == null) {
            colorEscogido= Color.GREEN;
        }
        refresque();
    }

    private void chooseColor2() {
        colorCambiado2=true;
        colorEscogido2= JColorChooser.showDialog(null, "color maquina", colorEscogido2);
        if (colorEscogido == null) {
            colorEscogido2= Color.BLUE;
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
        tamano= 6;
        Semillas= 3;
        semillasUsuario=0;
        semillasMaquina=0;
        numeroJugadas=0;
        colorCambiado= false;
        colorCambiado2= false;
        refresque();
    }
    private void refresque() {
        board.removeAll();
        board2.removeAll();
        board3.removeAll();
        this.remove(board);
        this.remove(board2);
        this.remove(board3);
        prepareGrid(tamano);
        this.revalidate();
        repaint();
    }

    private void refresqueBoards() {
        board2.removeAll();
        board3.removeAll();
        this.remove(board2);
        this.remove(board3);
        prepareGrid(tamano);
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

    private void nuevo(){
        int solicitadoH = Integer.parseInt(numIngreUsuario.getText());
        if(solicitadoH>1){
            tamano = solicitadoH;
            refresque();
        }
        else{
            JOptionPane.showMessageDialog(null,"cambie el numero de canicas");
        }
    }

    private void nuevo2(){
        int solicitadoH = Integer.parseInt(numIngreMauina.getText());
        if(solicitadoH>1){
            Semillas = solicitadoH;
            refresque();

        }
        else{
            JOptionPane.showMessageDialog(null,"cambie el numero de canicas");
        }
    }
    public static void main(String[] args){
        KalahGUI kalah = new KalahGUI();
        kalah.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        kalah.setVisible(true);
    }

}