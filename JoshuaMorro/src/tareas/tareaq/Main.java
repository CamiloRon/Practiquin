package tareas.tareaq;
import java.util.Scanner;
public class Main {
    static Scanner leer= new Scanner(System.in);
    static String tablero[][];
    static int tamtablero = 5;
    static int posicionesJ[][]=new int[2][3];
    static String signos[]={"#","@","%","&"};
    static int noJugadores = 2, bajadas=1,subidas=1;
    static int noTurno=0;
    static int posubidas[][]=new int[2][2];
    static int posbajadas[][]=new int[2][2];


    public static void main(String[] args) {
        iniciar();
        menu();
    }

    public static void menu(){
        System.out.println("-------Menu Principal---------");
        System.out.println("1.Iniciar Juego");
        System.out.println("2.Regresar al Juego");
        System.out.println("3.Cofiguarcion");
        System.out.println("4.Salir");
        System.out.print("Elija Opcion:");
        int op = leer.nextInt();
        System.out.println("-----------------------------");
        switch (op){
            case 1:
                iniciar();
                imprimir();
                break;
            case 2:
                imprimir();
                break;
            case 3:
                configuracion();
                break;


        }

    }

    public static void iniciar(){
        tablero = new String[tamtablero][tamtablero];
        for(int f=0; f<tamtablero ;f++){
            for (int c=0;c<tamtablero;c++){
                tablero[f][c] = " ";
            }
        }
        posicionesJ = new int[noJugadores][3];
        for (int f=0;f<noJugadores;f++){
            posicionesJ[f][0]=tablero.length-1;
            posicionesJ[f][1]=0;
        }
        posubidas=new int[subidas*2][3];
        posbajadas=new int[bajadas*2][3];
        crearsubidasybajadas();

        posicionarJugadores();
        asignarsignos();




    }
    public static void imprimir(){
        tablero[tablero.length-1][0]="$";
        tablero[0][tablero.length-1]="$";
        dibujarsubidasybajadas();
        posicionarJugadores();
        for(int f=0; f<tamtablero ;f++){
            for (int c=0;c<tamtablero;c++){
                System.out.print("|"+tablero[f][c]+"|");
            }
            System.out.println();
        }
        System.out.println("Quiere tirar el dado o regresar al menu:");
        System.out.println("1.Si");
        System.out.println("2.Regresar al menu");
        int op = leer.nextInt();
        switch (op){
            case 1:
                mover();
                break;
            case 2:
                menu();
                break;
            default:
                System.out.println("Opcion no valida-------");
                imprimir();

        }
    }
    public static void mover(){
        int nodad = (int) (Math.random() * 6) + 1;
        System.out.println("El dado determino que avanzara "+nodad+" posiciones");
        System.out.println("Si Desea continuar presione cualquier caracter seguido de un enter");
        leer.next();
        tablero[posicionesJ[noTurno][0]][posicionesJ[noTurno][1]]= " ";
        for (int x=0;x<nodad;x++) {
            if ((posicionesJ[noTurno][0] % 2 == 0 && posicionesJ[noTurno][1] == tamtablero - 1) || (posicionesJ[noTurno][0] % 2 != 0 && posicionesJ[noTurno][1] == 0)) {
                posicionesJ[noTurno][0]--;
            } else {
                if (posicionesJ[noTurno][0] % 2 == 0) {
                    posicionesJ[noTurno][1]++;
                } else {
                    posicionesJ[noTurno][1]--;
                }

            }
            if ( tablero[posicionesJ[noTurno][0]][posicionesJ[noTurno][1]]=="$"){
                System.out.println("El jugador "+(posicionesJ[noTurno][2]+1)+" ha ganado");
                System.exit(0);
            }

        }
        if (!tablero[posicionesJ[noTurno][0]][posicionesJ[noTurno][1]].equals(" ")){
            comprobar(posicionesJ[noTurno][0],posicionesJ[noTurno][1]);
        }
        noTurno++;
        if (noTurno==noJugadores){
            noTurno=0;
        }
        imprimir();

    }
    public static void posicionarJugadores(){
        for (int f=0;f<noJugadores;f++){
            tablero[posicionesJ[f][0]][posicionesJ[f][1]]=signos[posicionesJ[f][2]];
        }
    }

    public static void configuracion(){
        System.out.println("---------Menu Configuracion---------");
        System.out.println("1.Dimensiones del tablero");
        System.out.println("2.Cantidad de subidas y bajadas");
        System.out.println("3.Ingresar Jugadores");
        System.out.println("4.Regresar");
        System.out.print("Elija Opcion:");
        int op = leer.nextInt();
        System.out.println("-----------------------------");
        switch (op){
            case 1:
                dimensionestablero();
                break;
            case 2:
                cantidadsubidasybajadas();
                break;
            case 3:
                modJugadores();
                break;
            case 4:
                menu();
                break;



        }
    }

    public static void dimensionestablero(){
        System.out.println("---------Dimensiones del tablero---------");
        System.out.println("1.Pequeño(5x5)");
        System.out.println("2.Grande(11x11)");
        System.out.println("3.Regresar");
        System.out.print("Elija Opcion:");
        int op= leer.nextInt();
        switch (op){
            case 1:
                tamtablero=5;
                iniciar();
                System.out.println("Se ha modificado el tamaño el tablero");
                configuracion();
                break;
            case 2:
                tamtablero=11;
                iniciar();
                System.out.println("Se ha modificado el tamaño el tablero");
                configuracion();
                break;
            case 3:
                configuracion();
                break;
            default:
                System.out.println("Opcion no valida");
                dimensionestablero();
                break;

        }
    }
    public static void cantidadsubidasybajadas(){
        System.out.println("---------Cantidad de Subidas y Bajadas---------");
        setsubidas();
        setbajadas();
        System.out.println("Ha modificado sus subidas y bajadas");
        iniciar();
        configuracion();
    }
    public static void setsubidas(){
        System.out.println("Ingrese cantidad de Subidas");
        int sub = leer.nextInt();
        if ((sub > (Math.pow(tamtablero,2)/4)-2 )||sub>25){
            System.out.println("El numero de subidas no puede ser mayor a la mitad de casillas menos dos para que subidas y bajadas sean parejos o mayor al numero de letras del abecedario");
            setsubidas();
        }
        else {
            subidas=sub;
        }



    }
    public static void setbajadas(){
        System.out.println("Ingrese cantidad de Bajadas");
        int baja = leer.nextInt();
        if (baja > (Math.pow(tamtablero,2)/4)-2 ){
            System.out.println("El numero de bajadas no puede ser mayor a la mitad de casillas menos dos para que subidas y bajadas sean parejos");
            setbajadas();
        }
        else {
            bajadas=baja;
        }

    }
    public static void crearsubidasybajadas(){
        tablero[tablero.length-1][0]="$";
        tablero[0][tablero.length-1]="$";
        for (int i=0;i<subidas*2;i++){
            posubidas[i][0] = (int) (Math.random() * tamtablero);
            posubidas[i][1] = (int) (Math.random() * tamtablero);
            while ((!tablero[posubidas[i][0]][posubidas[i][1]].equals(" "))||((i%2==0 && posubidas[i][0]==posubidas[i+1][0])||(i%2!=0 && posubidas[i][0]==posubidas[i-1][0]))){
                posubidas[i][0] = (int) (Math.random() * tamtablero);
                posubidas[i][1] = (int) (Math.random() * tamtablero);
            }

        }
        for (int i=0;i<bajadas*2;i++){
            posbajadas[i][0] = (int) (Math.random() * tamtablero);
            posbajadas[i][1] = (int) (Math.random() * tamtablero);
            while (!tablero[posbajadas[i][0]][posbajadas[i][1]].equals(" ")||((i%2==0 && posbajadas[i][0]==posbajadas[i+1][0])||(i%2!=0 && posbajadas[i][0]==posbajadas[i-1][0]))){
                posbajadas[i][0] = (int) (Math.random() * tamtablero);
                posbajadas[i][1] = (int) (Math.random() * tamtablero);
            }

        }

        dibujarsubidasybajadas();
    }
    public static void  dibujarsubidasybajadas(){
        for (int i=0;i<subidas*2;i++){
            tablero[posubidas[i][0]][posubidas[i][1]]=String.valueOf((char)(97+(i/2)));

        }
        for (int i=0;i<bajadas*2;i++){
            tablero[posbajadas[i][0]][posbajadas[i][1]]=String.valueOf((i/2)+1);
        }
    }
    public static void modJugadores(){
        System.out.println("--------Menu Jugadores--------");
        System.out.println("1.Ingresar cantidad de Jugadores");
        System.out.println("2.Regresar");
        int op = leer.nextInt();
        switch (op){
            case 1:
                IngresarJugadores();
                break;
            case 2:
                configuracion();
                break;

        }


    }
    public static void IngresarJugadores(){
        System.out.println("Ingrese cantidad de jugadores(2min y 4max): ");
        int noJ = leer.nextInt();
        if (noJ>1&&noJ<5){
            noJugadores=noJ;
            System.out.println("Se ha modificado el numero de jugadores a "+noJ);
            iniciar();
            configuracion();
        }
        else {
            System.out.println("Opcion no valida");
            IngresarJugadores();
        }
    }

    public static void comprobar(int x,int y){
        for (int i=0;i<subidas*2;i++){
            if ((posubidas[i][0]==x)&&(posubidas[i][1]==y)){
                if (i%2==0){
                    if (posubidas[i][0]>posubidas[i+1][0]){
                        posicionesJ[noTurno][0]=posubidas[i+1][0];
                        posicionesJ[noTurno][1]=posubidas[i+1][1];
                    }
                }
                else {
                    if (posubidas[i][0]>posubidas[i-1][0]){
                        posicionesJ[noTurno][0]=posubidas[i-1][0];
                        posicionesJ[noTurno][1]=posubidas[i-1][1];
                    }
                }
            }

        }
        for (int i=0;i<bajadas*2;i++){
            if ((posbajadas[i][0]==x)&&(posbajadas[i][1]==y)){
                if (i%2==0){
                    if (posbajadas[i][0]<posbajadas[i+1][0]){
                        posicionesJ[noTurno][0]=posbajadas[i+1][0];
                        posicionesJ[noTurno][1]=posbajadas[i+1][1];
                    }
                }
                else {
                    if (posbajadas[i][0]<posbajadas[i-1][0]){
                        posicionesJ[noTurno][0]=posbajadas[i-1][0];
                        posicionesJ[noTurno][1]=posbajadas[i-1][1];
                    }
                }

            }
        }
    }

    public static void asignarsignos(){
        for (int f=0;f<noJugadores;f++){
            posicionesJ[f][2]=-1;
        }
        for (int f=0;f<noJugadores;f++){
            int a = (int) (Math.random() * noJugadores);
            posicionesJ[f][2] = a;
            for (int k=0;k<noJugadores;k++) {
                while (posicionesJ[f][2] == posicionesJ[k][2] && k!=f) {
                    int b = (int) (Math.random() * noJugadores);
                    posicionesJ[f][2] = b;
                }
            }

        }
    }






}
