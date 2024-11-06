/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package segmentacion;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ESTUDIANTE
 */
public class Procesos {

    private String nombre_Proceso;
    private int tamaño_Proceso;
    private int estado;

    public Procesos(String nombre_Proceso, int tamaño_Proceso, int estado) {
        this.nombre_Proceso = nombre_Proceso;
        this.tamaño_Proceso = tamaño_Proceso;
        this.estado = estado;

    }

    public Procesos() {
    }

    public String getNombre_Proceso() {
        return nombre_Proceso;
    }

    public void setNombre_Proceso(String nombre_Proceso) {
        this.nombre_Proceso = nombre_Proceso;
    }

    public int getTamaño_Proceso() {
        return tamaño_Proceso;
    }

    public void setTamaño_Proceso(int tamaño_Proceso) {
        this.tamaño_Proceso = tamaño_Proceso;
    }

    public int getestado() {
        return estado;
    }

    public void setestado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Procesos{" + "nombre_Proceso: " + nombre_Proceso + ", tama\u00f1o_Proceso: " + tamaño_Proceso + '}';
    }

    ArrayList<Procesos> listaProcesos = new ArrayList<>();
    ArrayList <Procesos> procesosEnEjecucion = new ArrayList<>();
    String[] bloqueMemoria = new String[15];
    Scanner scanner = new Scanner(System.in);

    public void añadirProceso() {
        System.out.println("Ingrese el nombre del proceso:");
        nombre_Proceso = scanner.nextLine();
        System.out.println("Ingrese el tamaño del proceso en Mb :");
        tamaño_Proceso = scanner.nextInt();
        scanner.nextLine();
        estado = 0;

        Procesos nuevoProceso = new Procesos(nombre_Proceso, tamaño_Proceso, estado);
        listaProcesos.add(nuevoProceso);
        System.out.println("Proceso insertado correctamente en la lista de solicitudes");
    }

    public void mostrarListaProcesos() {
        System.out.println("Lista de procesos almacenados:\n");
        for (Procesos proceso : listaProcesos) {
            System.out.println(proceso.toString());
        }
    }

    public void asignarBloqueMemoria(Procesos proceso) {
        int bloquesAsignados = 0;
        int bloquesLibres = 0;

        System.out.println("-------------------------------");
        for (String bloque : bloqueMemoria) {
            if (bloque == null) {
                bloquesLibres++;
            }
        }
        if (procesosEnEjecucion.contains(proceso)) {
            System.out.println("El proceso ya se esta ejecutando");
            return; 
        }
        
        if (bloquesLibres < proceso.getTamaño_Proceso()) {
            System.out.println("No hay suficiente espacio en el bloque de memeoria para almacenar este proceso");
            Segmentacion.imprimirMenu();
            return;
        }
        
        int espacioContinuo = verificarEspacioContinuo(proceso.getTamaño_Proceso()); 
        
        if (espacioContinuo == -1) {
            System.out.println("No hay espacios continuos disponibles");
            compactacion(); 
            espacioContinuo = verificarEspacioContinuo(proceso.getTamaño_Proceso()); 
        }
        
        if (espacioContinuo != -1) {
            for (int i = espacioContinuo; i < espacioContinuo+proceso.getTamaño_Proceso(); i++) {
                bloqueMemoria[i] = proceso.getNombre_Proceso();
                bloquesAsignados++; 
            }
         proceso.setestado(1);
         listaProcesos.remove(proceso); 
         procesosEnEjecucion.add(proceso); 
         mostrarBloqueMemoria();
         int memoriaDisponible = bloquesLibres - bloquesAsignados;
            System.out.println("Proceso: " + proceso.getNombre_Proceso()+" de tamaño:"+ proceso.getTamaño_Proceso()+"MB, ejecutado exitosamente");
             System.out.println("Memoria disponible despues de la asignacion:" + memoriaDisponible + "MB") ;

            
        }else{
            System.out.println("No fue posible encontrar  espacio para el proceso");
        }

        }
    
    private int verificarEspacioContinuo(int tamañoProceso){
        int contadorEspacio = 0; 
        int inicioEspacio = -1; 
        
        for (int i = 0; i < bloqueMemoria.length; i++) {
            if (bloqueMemoria[i] == null) {

                if (contadorEspacio == 0) {
                        inicioEspacio = i; 
                    
                }
                contadorEspacio++;
                if (contadorEspacio == tamañoProceso) {
                    return inicioEspacio;
                    
                }
            }else{
                contadorEspacio =0; 
            }
            
        }
        return -1; 
    }
    
    public void compactacion(){
        int indiceCompactacion = 0; 
        
        for (int i = 0; i < bloqueMemoria.length; i++) {
            if (bloqueMemoria[i] != null) {
                bloqueMemoria[indiceCompactacion] = bloqueMemoria[i];
                
                if (i != indiceCompactacion) {
                    bloqueMemoria[i] = null;
                }
                indiceCompactacion++; 
                
            }
            
        }
        System.out.println("Memoria Compactada");
        mostrarBloqueMemoria(); 
    }
    
    public void mostrarBloqueMemoria(){
        System.out.println("-------------------------------");
    System.out.println("Estado del bloque de memoria:");
        for (String bloque : bloqueMemoria) {
            System.out.print("[" + (bloque != null ? bloque : "libre") + "] ");
        }
        System.out.println();
    }

    public void validacion() {
        boolean validacion = false;
        Procesos proceso = null;
        mostrarListaProcesos();
        System.out.println("-------------------------------");
        System.out.println("Cual proceso quiere almacenar en el bloque de memoria? \n");
        String respuesta = scanner.next().trim();
        for (Procesos m : listaProcesos) {
            if (m.getNombre_Proceso().equals(respuesta)) {
                proceso = m;
                validacion = true;
                break;
            }
        }
        if (validacion) {
            asignarBloqueMemoria(proceso);
        } else {
            System.out.println("Proceso inexistente");
        }
    }
    
    
     public void liberarMarcos( String nombreProceso) {
        for (int i = 0; i < bloqueMemoria.length; i++) {
            if (bloqueMemoria[i] != null && bloqueMemoria[i].equals(nombreProceso)) {
                bloqueMemoria[i] = null;
            }
        }
        System.out.println("Los MB del proceso '" + nombreProceso + "' fueron liberados");
    }
     
       public void mostrarProcesosEj() {
        System.out.println("Lista de procesos almacenados en el bloque de memoria:\n");
           if (procesosEnEjecucion.isEmpty()) {
               System.out.println("No hay procesos almacenados en el bloque de memoria");
               return;
           }
           for (Procesos proceso : procesosEnEjecucion) {
               System.out.println(proceso.toString());
           }
       
    }
       
     
     public void terminarProceso(){
         if (procesosEnEjecucion.isEmpty()) {
             System.out.println("No hay procesos en ejecucion");
             return;
         }
         mostrarProcesosEj();
         System.out.println("-------------------------------");
        System.out.println("¿Cuál proceso quiere finalizar?");
        String resp = scanner.next();
        Procesos proceso = null;
        for (Procesos m : procesosEnEjecucion) {
            if (m.getNombre_Proceso().equals(resp)) {
                proceso = m;
                break;
            }
        }
        
        if (proceso != null) {
            liberarMarcos(proceso.getNombre_Proceso());
            proceso.setestado(-1); 
            procesosEnEjecucion.remove(proceso); 
            System.out.println("Proceso " + proceso.getNombre_Proceso() + " finalizado y memoria liberada.");
        } else {
            System.out.println("Proceso no encontrado en ejecución.");
        }
        
        System.out.println("Bloque de Memoria\n");
           mostrarBloqueMemoria();

    
          
}
     
}

