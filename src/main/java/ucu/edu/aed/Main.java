package ucu.edu.aed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ucu.edu.aed.modelo.Tarea;
import ucu.edu.aed.sistema.GestionTareas;
import ucu.edu.aed.sistema.SistemaGestion;

/**
 * Punto de entrada del proyecto.
 *
 * <p><strong>TODO (alumno):</strong> completar el flujo de demostración:
 * <ol>
 *   <li>Instanciar tu implementación de {@link SistemaGestion}.</li>
 *   <li>Cargar las tareas desde {@code naves.txt} invocando {@code recibirTarea} por
 *       cada línea (la lectura/parseo del archivo ya está hecha en {@link #cargarTareas}).</li>
 *   <li>Procesar todas las tareas hasta vaciar las colas.</li>
 *   <li>Demostrar la búsqueda de una tarea procesada por id (R3).</li>
 *   <li>Demostrar la cancelación de una tarea pendiente por id (R4).</li>
 * </ol>
 */
public class Main {

    public static void main(String[] args) {

        // TODO: instanciar tu implementación de SistemaGestion (la clase que vos crees).
        GestionTareas sistema = new GestionTareas();


        cargarTareas(sistema, "naves.txt");

        System.out.println(sistema.cantidad() + "  " + sistema.espera.tamaño());

        int i = 0;
        while (i < 120) {
            sistema.procesarTarea();
            i++;

        }
        System.out.println(sistema.getHistorial().cantidadNodos() + " " + sistema.procesadas.tamaño());

        
        escribirTxt("historial.txt", sistema);

        // TODO: procesar todas las tareas hasta vaciar las colas.
        //       Recordá que pueden quedar tareas en espera tras la carga inicial.

        // TODO: demostrar buscarTareaProcesada (R3) — debería retornar una tarea ya ejecutada.

        // TODO: demostrar cancelarTarea (R4) — sobre una tarea que aún esté pendiente.
    }

    /**
     * Lee {@code naves.txt} e invoca {@code recibirTarea} en el sistema por cada línea válida.
     *
     * <p>Formato esperado por línea: {@code id;descripcion;criticidad}.
     * Las líneas vacías o que comienzan con {@code #} se ignoran.</p>
     *
     * @param sistema sistema de gestión sobre el que registrar las tareas
     * @param archivo ruta del archivo a leer
     */
    private static void cargarTareas(GestionTareas sistema, String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader("naves.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                String[] partes = linea.split(";");
                if (partes.length < 3) continue;
                try {
                    String descripcion = partes[1].trim();
                    int criticidad = Integer.parseInt(partes[2].trim());
                    // TODO: construir una Tarea con (id, descripcion, criticidad)
                    //       y llamar a sistema.recibirTarea(tarea).
                    Tarea tarea = new Tarea(descripcion, criticidad);
                    sistema.recibirTarea(tarea);
                } catch (IllegalArgumentException ex) {
                    System.err.println("Línea inválida: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer " + archivo + ": " + e.getMessage());
        }
    }

    public static void escribirTxt(String path, GestionTareas gestion){
        try (BufferedWriter salida = new BufferedWriter(new FileWriter(path))){
            gestion.getHistorial().preOrder(tarea -> {
                try {
                    salida.write(tarea.getId());
                    salida.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            salida.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
