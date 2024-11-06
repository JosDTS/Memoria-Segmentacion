/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package segmentacion;

import java.util.Scanner;

/**
 *
 * @author ESTUDIANTE
 */
public class Segmentacion {

    /**
     * @param args the command line arguments
     */
     Scanner scanner = new Scanner(System.in);
     static Procesos pr = new Procesos();
     
    
    public static void main(String[] args) {
        
        imprimirMenu();
        pr.añadirProceso();
        pr.mostrarListaProcesos();
        
    }
    
     public static void imprimirMenu(){
         Scanner scanner = new Scanner(System.in);

        System.out.println("-------------------------------");
        System.out.println("          MENU");
        System.out.println("1. Ingresar proceso");
        System.out.println("2. Ejecutar proceso");
        System.out.println("3. Mostrar lista de solicitudes de procesos");
        System.out.println("4. Terminar proceso");
         System.out.println("5. Ver los procesos finalizados");
        System.out.println("00. Cerrar programa");
        System.out.println("-------------------------------");
        
                int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                pr.añadirProceso();
                System.out.println("-------------------------------");

                pr.mostrarListaProcesos();

                imprimirMenu();
                break;

            case 2:

                 pr.validacion();
                imprimirMenu();
                break;

            case 3:
                pr.mostrarListaProcesos();

                imprimirMenu();
                break;

            case 4:

                pr.terminarProceso();
                imprimirMenu();
                break;

            case 00:

                System.exit(opcion);

            default:
                System.out.println("Opcion invalida");
                imprimirMenu();

        }
     }
    
}
