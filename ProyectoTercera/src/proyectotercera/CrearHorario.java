package proyectotercera;

import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import proyectotercera.utils.FileUtils;

// Pendiente Ampliacion 2.3
public class CrearHorario {

    private static Scanner entrada;
    static Reservas horario;
    public static void main(String[] args) {
        entrada = new Scanner(System.in);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("> Construcción de horarios de tutorias >");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println();
        pedirNombre();
        // Entrada dias
        System.out.println("Escribe en lineas diferentes las fechas de los dias de tu horario. Cuando no quieras introducir más dias, escribe FIN: ");   
        pedirDias();
        // Entrada horas
        System.out.println("Perfecto, indica ahora las horas de inicio de cada sesión de reserva. Cuando no quieras introducir más horas, escribe FIN: ");
        pedirHora();
        // Entrada nombre archivo
        System.out.println("Por último, introduce el nombre de fichero de texto en el que quieres guardar tu horario: ");   
        archivo();
        // FIN
        System.out.println("Fichero creado. Muchas gracias :)");
    }

    public static void pedirNombre() {
        String input;
        boolean fin = false;
        while (!fin) {
            System.out.print("Nombre del profesor: ");
            input = entrada.nextLine();
            if(input.length() > 0) {
                horario = new Reservas(input);
                fin = true;
            } else {
                System.out.println("ERROR: No puede dejar el nombre en blanco.");
            }
        }
    }

    public static void pedirDias() {
        String input;
        boolean fin = false;
        while (!fin) {
            input = entrada.nextLine();
            if(input.length() > 0) {
                if(input.equalsIgnoreCase("FIN")) {
                    if(horario.entradasDia.size() > 0) {
                        fin = true;
                    } else {
                        System.out.println("ERROR: Introduce por lo menos un dia.");
                    }
                }else {
                    try {
                        DateFormat df = new SimpleDateFormat("dd/MM/yy");
                        df.setLenient(false);
                        df.parse(input);
                        horario.addContenido(new Dia(input));
                    } catch(ParseException e) {
                        System.out.println("ERROR: Fecha no válida.");
                        System.out.println("Asegúrese de que la fecha tiene el formato DD/MM/AA y que sea una fecha válida.");
                    }
                }
            } else {
                System.out.println("ERROR: Introduzca una fecha válida.");
                System.out.println("Asegúrese de que la fecha tiene el formato DD/MM/AA y que sea una fecha válida.");
            }
        }
    }

    public static void pedirHora(){
        String input;
        byte horaInicial;
        boolean fin = false;
        boolean introducidoHora = false;

        while(!fin) {
            input = entrada.nextLine();
            if(input.equalsIgnoreCase("FIN")) {
                if(introducidoHora) {
                    fin = true;
                } else {
                    System.out.println("ERROR: Introduce por lo menos una hora.");
                }
            }else {
                if(input.matches("\\d+")) { // Usamos la expresión regular "[0-9]+" para la comprobación del if
                    horaInicial = Byte.parseByte(input); // Lo parseamos a byte
                    if(horaInicial >= 8 && horaInicial <= 20){
                        horario.rellenarHoras(new Hora(horaInicial)); // lo rellenamos
                        introducidoHora = true;
                    } else {
                        System.out.println("ERROR: Introduzca una hora entre las 8 y las 20.");
                    }
                } else {
                    System.out.println("ERROR: Introduzca una hora entre las 8 y las 20 o FIN para terminar.");
                }
            }
        }
    }

    public static void archivo(){
        String nombre = "";
        boolean fin = false;
        while (!fin) {
            nombre = entrada.nextLine();
            if(nombre.length() > 0) {
                if(nombre.matches(".*[\\/:\\*\\?\"<>\\|].*")){
                    System.out.println("ERROR: Nombre de archivo no válido.");
                }else {
                    fin = true;
                }
            } else {
                System.out.println("ERROR: Introduce un nombre de archivo.");
            }
        }
        FileUtils.escribirArchivo(nombre, horario); // llamamos a la funcion escribir archivo para escribir en un fichero
    }
}