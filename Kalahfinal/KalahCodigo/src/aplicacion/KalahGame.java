package aplicacion;
import java.util.*;

public class KalahGame {
    private int[] matrizSemillas;
    private int[] matrizSolucion;
    private int tamanoTablero;
    private int Semillas;
    private ArrayList<int[]> posiciones;
    private ArrayList<Integer> caidos;
    private Hashtable<Integer,int[]> espacios;
    private int [][] matrizAdyacente;
    private int barraJugador=0;
    private int barraMaquina=0;
    private int numeroDeJugadas=0;
    private ArrayList<Integer> lista = new ArrayList<Integer>();
    private ArrayList<Integer> GanoJugador = new ArrayList<>();
    private ArrayList<Integer> GanoMaquina = new ArrayList<>();
    private int otraVez=0;
    private boolean IsJugador;

    public KalahGame(int tamanoTablero, int Semillas) {
        this.tamanoTablero= tamanoTablero;
        this.Semillas= Semillas;
        posiciones= new ArrayList<int[]>();
        caidos= new ArrayList<Integer>();
        espacios= new Hashtable<Integer,int[]>();
        llenarSemillas();
        llenarSolucion();
        llenarMatriz();
    }
    public KalahGame(int[] matrizSemillas, int[] matrizSolucion) {
        this.matrizSemillas= matrizSemillas.clone();
        this.matrizSolucion= matrizSolucion.clone();
        posiciones= new ArrayList<int[]>();
        caidos= new ArrayList<Integer>();
        espacios= new Hashtable<Integer,int[]>();
        for(int i= 0; i < matrizSolucion.length; i++) {
            if(matrizSolucion[i]!= 0) {
                espacios.put(i, new int[] {-1,matrizSolucion[i]});
            }
        }
        int cont= 0;
        for(int i= 0; i < this.matrizSemillas.length;i++) {
            if(this.matrizSemillas[i]!= 0) {
                posiciones.add(new int[] {this.matrizSemillas[i],i,0});
                cont++;
            }
        }
        this.tamanoTablero= (int) Math.pow(matrizSemillas.length, 0.5);
        this.Semillas= cont;
    }

    /*
     * Llena la matrizSemillas de una forma aleatoria.
     */
    private void llenarSemillas() {

        int num= 0; //(int)(Math.random()*(tamanoTablero*tamanoTablero));
        matrizSemillas= new int[tamanoTablero*tamanoTablero];
        limpiar();
        int cont= 0;
        while (cont < Semillas) {
            if(matrizSemillas[num]== 0) {
                posiciones.add(new int[] {cont+1,num,0});
                matrizSemillas[num]= cont+1;
                cont++;
            }
            num+=1; //(int)(Math.random()*(tamanoTablero*tamanoTablero));
        }
    }

    /*
     * Llena la matrizSolucion de una forma aleatoria.
     */
    private void llenarSolucion() {
        int num=0; //(int)(Math.random()*(tamanoTablero*tamanoTablero));
        matrizSolucion= new int[tamanoTablero*tamanoTablero];
        for(int i= 0; i < matrizSolucion.length; i++) {
            matrizSolucion[i]= 0;
        }
        int cont= 0;
        while (cont < Semillas) {
            if(matrizSolucion[num]== 0 && matrizSemillas[num]== 0) {
                matrizSolucion[num]= cont+1;
                cont++;
                espacios.put(num, new int[] {-1,cont});
            }
            num+=1; //(int)(Math.random()*(tamanoTablero*tamanoTablero));
        }
    }

    /*
     * limpia el arreglo de matrizSemillas.
     */
    private void limpiar() {
        for(int i= 0; i < matrizSemillas.length; i++) {
            matrizSemillas[i]= 0;
        }
    }

    /**
     * @return  retorna la matrizSemillas que tiene en el momento.
     */
    public int[] getSemillas() {
        return matrizSemillas;
    }

    /**
     * @return  retorna la matrizSolucion que tiene en el momento.
     */
    public int[] getSolucion() {
        return matrizSolucion;
    }

    /**
     * mueve todas las Semillas una posicion a la derecha si se puede.
     */
    public void moveRight() {
        limpiar();
        inverseSortPos();
        caidos.clear();
        for(int[] pos: posiciones) {
            condicionRight(pos);
        }
        cambiarPosiciones();

    }

    /*
     * tiene las condiciones que le permite a una canica cambiar de posicion.
     */
    private void condicionRight(int[] pos) {
        boolean ver;
        ver= (pos[1]+1)%tamanoTablero== 0 || pos[2]==1 || matrizSemillas[pos[1]+1]!= 0;
        if(!ver) {
            if (matrizSolucion[pos[1]+1]!= 0) {
                int id= espacios.get(pos[1]+1)[0];
                int id1= espacios.get(pos[1]+1)[1];
                if(id== -1) {
                    espacios.replace(pos[1]+1, espacios.get(pos[1]+1),new int[] {pos[0],id1});
                    caidos.add(matrizSemillas[pos[1]+1]);
                }
                else if (id!= pos[0]) {
                    matrizSemillas[pos[1]+1]= pos[0];
                    matrizSolucion[pos[1]+1]= 0;
                }

            }
            else{matrizSemillas[pos[1]+1]= pos[0];}
        }
        else {
            matrizSemillas[pos[1]]= pos[0];
        }
    }

    /**
     * mueve todas las Semillas una posicion a la izquierda si se puede.
     */
    public void moveLeft() {
        limpiar();
        sortPosiciones();
        caidos.clear();
        for(int[] pos: posiciones) {
            condicionLeft(pos);
        }
        cambiarPosiciones();
    }

    /*
     * tiene las condiciones que le permite a una canica cambiar de posicion.
     */
    private void condicionLeft(int[] pos) {
        boolean ver;
        ver= pos[1]%tamanoTablero== 0 || matrizSemillas[pos[1]-1]!= 0 || pos[2]==1 ;
        if(!ver) {
            if (matrizSolucion[pos[1]-1]!= 0) {
                int id= espacios.get(pos[1]-1)[0];
                int id1= espacios.get(pos[1]-1)[1];
                if(id== -1) {
                    espacios.replace(pos[1]-1, espacios.get(pos[1]-1),new int[] {pos[0],id1});
                    caidos.add(matrizSemillas[pos[1]-1]);
                }
                else if (id!= pos[0]) {
                    matrizSemillas[pos[1]-1]= pos[0];
                    matrizSolucion[pos[1]-1]= 0;
                }

            }
            else{matrizSemillas[pos[1]-1]= pos[0];}
        }
        else {
            matrizSemillas[pos[1]]= pos[0];
        }
    }

    /**
     * mueve todas las Semillas una posicion hacia arriba si se puede.
     */
    public void moveUp() {
        limpiar();
        sortPosiciones();
        caidos.clear();
        for(int[] pos: posiciones) {
            condicionUp(pos);
        }
        cambiarPosiciones();
    }

    /*
     * tiene las condiciones que le permite a una canica cambiar de posicion.
     */
    private void condicionUp(int[] pos) {
        boolean ver;
        ver= pos[1] < tamanoTablero ||  matrizSemillas[pos[1]-tamanoTablero]!= 0 || pos[2]==1 ;
        int poss= pos[1]-tamanoTablero;
        if(!ver) {
            if (matrizSolucion[poss]!= 0) {
                int id= espacios.get(poss)[0];
                int id1= espacios.get(poss)[1];
                if(id== -1) {
                    espacios.replace(poss, espacios.get(poss),new int[] {pos[0],id1});
                    caidos.add(matrizSemillas[poss]);
                }
                else if (id!= pos[0]) {
                    matrizSemillas[poss]= pos[0];
                    matrizSolucion[poss]= 0;
                }

            }
            else{matrizSemillas[poss]= pos[0];}
        }
        else {
            matrizSemillas[pos[1]]= pos[0];
        }
    }

    /**
     * mueve todas las Semillas una posicion hacia abajo si se puede.
     */
    public void moveDown() {
        limpiar();
        inverseSortPos();
        caidos.clear();
        boolean ver;
        for(int[] pos: posiciones) {
            condicionDown(pos);
        }
        cambiarPosiciones();
    }

    /*
     * tiene las condiciones que le permite a una canica cambiar de posicion.
     */
    private void condicionDown(int[] pos) {
        boolean ver;
        ver= (tamanoTablero*(tamanoTablero-1)) <= pos[1] || matrizSemillas[pos[1]+tamanoTablero]!= 0 || pos[2]==1 ;
        int poss= pos[1]+tamanoTablero;
        if(!ver) {
            if (matrizSolucion[poss]!= 0) {
                int id= espacios.get(poss)[0];
                int id1= espacios.get(poss)[1];
                if(id== -1) {
                    espacios.replace(poss, espacios.get(poss),new int[] {pos[0],id1});
                    caidos.add(matrizSemillas[poss]);
                }
                else if (id!= pos[0]) {
                    matrizSemillas[poss]= pos[0];
                    matrizSolucion[poss]= 0;
                }

            }
            else{matrizSemillas[poss]= pos[0];}
        }
        else {
            matrizSemillas[pos[1]]= pos[0];
        }
    }

    /*
     * ordena el arreglo de posiciones de menor a mayor.
     */
    private void sortPosiciones() {
        Collections.sort(posiciones,new Comparator <int[]>() {
            public int compare (int[]c1,int[]c2) {
                int x= 1;
                while(c1[x]==c2[x] && x > 0) {
                    x++;
                }
                return Double.compare(c1[x], c2[x]);
            }
        });
    }

    /*
     * ordena el arreglo de posiciones de mayor a menor.
     */
    private void inverseSortPos() {
        sortPosiciones();
        Collections.reverse(posiciones);
    }

    /*
     * genera nuevamente el arreglo posiciones.
     */
    private void cambiarPosiciones() {
        posiciones.clear();
        for(int i= 0; i< matrizSemillas.length; i++) {
            if(matrizSemillas[i]!= 0) {
                if(isInArray(caidos,matrizSemillas[i])) {
                    posiciones.add(new int[] {matrizSemillas[i],i,1});
                }
                else{
                    posiciones.add(new int[] {matrizSemillas[i],i,0});
                }
            }
            else {
                if(espacios.containsKey(i)) {
                    if(espacios.get(i)[0]!= -1) {
                        matrizSemillas[i]= espacios.get(i)[0];
                        matrizSolucion[i]= espacios.get(i)[1];
                    }
                }
            }
        }
    }

    private void llenarMatriz(){
        matrizAdyacente = new int[tamanoTablero][2];
        for (int i=0; i<tamanoTablero;i++){
            for (int j=0;j<2;j++){
                matrizAdyacente[i][j]=Semillas;
            }
        }
    }
    /*
     * GanoJugador si un elemento dado esta en un arreglo dado.
     */
    private boolean isInArray(ArrayList<Integer> array,int pivo) {
        for(Integer i: array) {
            if(i.equals(pivo)) {return true;}
        }
        return false;
    }


    private void HaciaArriba(int semillasRestantes){
        int contador=tamanoTablero-1;
        int semillas1 = semillasRestantes;
        if (semillas1>=1 && IsJugador){barraJugador+=1;}
        semillas1-=1;
        if (semillas1>0){
            int posicion=1;
            while (semillas1>0){
                matrizAdyacente[contador][posicion]+=1;
                contador-=1;
                if (contador==0){
                    HaciaAbajo(semillas1);
                    return;
                }
                semillas1-=1;
            }
            if (contador>=-1 && !IsJugador){
                if (matrizAdyacente[contador+1][1]==1){
                    barraMaquina+=matrizAdyacente[contador+1][0];
                }
            }
        }
        else{
            otraVez+=1;
        }
    }


    public void modifiquematriz(int x, int y, boolean jugador){
        int otra=0;
        int numSemillas = matrizAdyacente[x][y];
        matrizAdyacente[x][y]=0;
        int contador=x+1;
        int posicion=0;
        //Lado Jugador
        if (y%2==0){
            while (numSemillas>0){
                if (contador==tamanoTablero) {
                    HaciaArriba(numSemillas);
                    return;
                }
                matrizAdyacente[contador][posicion] += 1;
                contador+=1;
                numSemillas-=1;
            }
            //Reglas
            if (contador>=0){
                if (matrizAdyacente[contador-1][0]==1){
                    barraJugador+=matrizAdyacente[contador-1][1];
//                    matrizAdyacente[contador-1][1]=0;
                }
            }
        }
        //Lado Maquina
        else{
            contador-=2;
            posicion=1;
            while (numSemillas>0){
                if (contador==-1){
                    HaciaAbajo(numSemillas);
                    return;
                }
                matrizAdyacente[contador][posicion] += 1;
                contador-=1;
                numSemillas-=1;
            }
            //Reglas
            if (contador>=-1 && !IsJugador){
                if (matrizAdyacente[contador+1][1]==1){
                    barraMaquina+=matrizAdyacente[contador+1][0];
                }
            }
        }
    }

    public void HaciaAbajo(int semillasRestantes){
        int contador=0;
        int semillas1 = semillasRestantes;
        if (semillas1>=1 && !IsJugador){barraMaquina+=1;}
        semillas1-=1;
        if (semillas1>0){
            int posicion=0;
            while (semillas1>0){
                if (contador==tamanoTablero){
                    HaciaArriba(semillas1);
                    return;
                }
                matrizAdyacente[contador][posicion]+=1;
                contador+=1;
                semillas1-=1;
            }
           if (contador>=0){
                if (matrizAdyacente[contador-1][0]==1){
                    barraJugador+=matrizAdyacente[contador-1][1];
                }
            }
        }
        else{
            otraVez+=1;
        }
    }

    public ArrayList<Integer> getMatrizAdyacente()
    {
        for (int i=0; i<tamanoTablero;i++){
            for (int j=0;j<2;j++){
                lista.add(matrizAdyacente[i][j]);
                if (i%2==0){
                    GanoJugador.add(matrizAdyacente[i][j]);
                }
                else {
                    GanoMaquina.add(matrizAdyacente[i][j]);
                }
            }}
        return lista;
    }



    public int getSemillasMaquina(){
        return barraMaquina;
    }
    public int getSemillasJugador(){
        return barraJugador;
    }

    public boolean ganoJugador(){
        for (int i =0 ; i<GanoJugador.size();i++){
            if (GanoJugador.get(i)!=0){
                return false;
            }}
        return true;
    }

    public boolean perdioJugador(){
        for (int i =0 ; i<GanoJugador.size();i++){
            if (GanoJugador.get(i)!=0){
                return false;
            }}
        return true;
    }

    public int getOtraVez(){
        return otraVez;
    }

}