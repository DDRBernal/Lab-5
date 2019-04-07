package aplicacion;
import java.util.*;

public class KalahGame {
    private int[] matrizCanicas;
    private int[] matrizSolucion;
    private int tamanoTablero;
    private int cantidadBolas;
    private ArrayList<int[]> posiciones;
    private ArrayList<Integer> caidos;
    private Hashtable<Integer,int[]> espacios;

    public KalahGame(int tamanoTablero, int cantidadBolas) {
        this.tamanoTablero= tamanoTablero;
        this.cantidadBolas= cantidadBolas;
        posiciones= new ArrayList<int[]>();
        caidos= new ArrayList<Integer>();
        espacios= new Hashtable<Integer,int[]>();
        llenarCanicas();
        llenarSolucion();
    }
    public KalahGame(int[] matrizCanicas, int[] matrizSolucion) {
        this.matrizCanicas= matrizCanicas.clone();
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
        for(int i= 0; i < this.matrizCanicas.length;i++) {
            if(this.matrizCanicas[i]!= 0) {
                posiciones.add(new int[] {this.matrizCanicas[i],i,0});
                cont++;
            }
        }
        this.tamanoTablero= (int) Math.pow(matrizCanicas.length, 0.5);
        this.cantidadBolas= cont;
    }

    /*
     * Llena la matrizCanicas de una forma aleatoria.
     */
    private void llenarCanicas() {

        int num= 0; //(int)(Math.random()*(tamanoTablero*tamanoTablero));
        matrizCanicas= new int[tamanoTablero*tamanoTablero];
        limpiar();
        int cont= 0;
        while (cont < cantidadBolas) {
            if(matrizCanicas[num]== 0) {
                posiciones.add(new int[] {cont+1,num,0});
                matrizCanicas[num]= cont+1;
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
        while (cont < cantidadBolas) {
            if(matrizSolucion[num]== 0 && matrizCanicas[num]== 0) {
                matrizSolucion[num]= cont+1;
                cont++;
                espacios.put(num, new int[] {-1,cont});
            }
            num+=1; //(int)(Math.random()*(tamanoTablero*tamanoTablero));
        }
    }

    /*
     * limpia el arreglo de matrizCanicas.
     */
    private void limpiar() {
        for(int i= 0; i < matrizCanicas.length; i++) {
            matrizCanicas[i]= 0;
        }
    }

    /**
     * @return  retorna la matrizCanicas que tiene en el momento.
     */
    public int[] getCanicas() {
        return matrizCanicas;
    }

    /**
     * @return  retorna la matrizSolucion que tiene en el momento.
     */
    public int[] getSolucion() {
        return matrizSolucion;
    }

    /**
     * mueve todas las canicas una posicion a la derecha si se puede.
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
        ver= (pos[1]+1)%tamanoTablero== 0 || pos[2]==1 || matrizCanicas[pos[1]+1]!= 0;
        if(!ver) {
            if (matrizSolucion[pos[1]+1]!= 0) {
                int id= espacios.get(pos[1]+1)[0];
                int id1= espacios.get(pos[1]+1)[1];
                if(id== -1) {
                    espacios.replace(pos[1]+1, espacios.get(pos[1]+1),new int[] {pos[0],id1});
                    caidos.add(matrizCanicas[pos[1]+1]);
                }
                else if (id!= pos[0]) {
                    matrizCanicas[pos[1]+1]= pos[0];
                    matrizSolucion[pos[1]+1]= 0;
                }

            }
            else{matrizCanicas[pos[1]+1]= pos[0];}
        }
        else {
            matrizCanicas[pos[1]]= pos[0];
        }
    }

    /**
     * mueve todas las canicas una posicion a la izquierda si se puede.
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
        ver= pos[1]%tamanoTablero== 0 || matrizCanicas[pos[1]-1]!= 0 || pos[2]==1 ;
        if(!ver) {
            if (matrizSolucion[pos[1]-1]!= 0) {
                int id= espacios.get(pos[1]-1)[0];
                int id1= espacios.get(pos[1]-1)[1];
                if(id== -1) {
                    espacios.replace(pos[1]-1, espacios.get(pos[1]-1),new int[] {pos[0],id1});
                    caidos.add(matrizCanicas[pos[1]-1]);
                }
                else if (id!= pos[0]) {
                    matrizCanicas[pos[1]-1]= pos[0];
                    matrizSolucion[pos[1]-1]= 0;
                }

            }
            else{matrizCanicas[pos[1]-1]= pos[0];}
        }
        else {
            matrizCanicas[pos[1]]= pos[0];
        }
    }

    /**
     * mueve todas las canicas una posicion hacia arriba si se puede.
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
        ver= pos[1] < tamanoTablero ||  matrizCanicas[pos[1]-tamanoTablero]!= 0 || pos[2]==1 ;
        int poss= pos[1]-tamanoTablero;
        if(!ver) {
            if (matrizSolucion[poss]!= 0) {
                int id= espacios.get(poss)[0];
                int id1= espacios.get(poss)[1];
                if(id== -1) {
                    espacios.replace(poss, espacios.get(poss),new int[] {pos[0],id1});
                    caidos.add(matrizCanicas[poss]);
                }
                else if (id!= pos[0]) {
                    matrizCanicas[poss]= pos[0];
                    matrizSolucion[poss]= 0;
                }

            }
            else{matrizCanicas[poss]= pos[0];}
        }
        else {
            matrizCanicas[pos[1]]= pos[0];
        }
    }

    /**
     * mueve todas las canicas una posicion hacia abajo si se puede.
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
        ver= (tamanoTablero*(tamanoTablero-1)) <= pos[1] || matrizCanicas[pos[1]+tamanoTablero]!= 0 || pos[2]==1 ;
        int poss= pos[1]+tamanoTablero;
        if(!ver) {
            if (matrizSolucion[poss]!= 0) {
                int id= espacios.get(poss)[0];
                int id1= espacios.get(poss)[1];
                if(id== -1) {
                    espacios.replace(poss, espacios.get(poss),new int[] {pos[0],id1});
                    caidos.add(matrizCanicas[poss]);
                }
                else if (id!= pos[0]) {
                    matrizCanicas[poss]= pos[0];
                    matrizSolucion[poss]= 0;
                }

            }
            else{matrizCanicas[poss]= pos[0];}
        }
        else {
            matrizCanicas[pos[1]]= pos[0];
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
        for(int i= 0; i< matrizCanicas.length; i++) {
            if(matrizCanicas[i]!= 0) {
                if(isInArray(caidos,matrizCanicas[i])) {
                    posiciones.add(new int[] {matrizCanicas[i],i,1});
                }
                else{
                    posiciones.add(new int[] {matrizCanicas[i],i,0});
                }
            }
            else {
                if(espacios.containsKey(i)) {
                    if(espacios.get(i)[0]!= -1) {
                        matrizCanicas[i]= espacios.get(i)[0];
                        matrizSolucion[i]= espacios.get(i)[1];
                    }
                }
            }
        }
    }

    /*
     * verifica si un elemento dado esta en un arreglo dado.
     */
    private boolean isInArray(ArrayList<Integer> array,int pivo) {
        for(Integer i: array) {
            if(i.equals(pivo)) {return true;}
        }
        return false;
    }
}
