package ucu.edu.aed.modelo;

/**
 * Representa una tarea operativa que recibe la nave para ser procesada.
 *
 * <p><strong>TODO (alumno):</strong> completar esta clase según el enunciado del parcial.
 *
 * <p>Recordá que el enunciado define los atributos de una tarea:
 * <ul>
 *   <li>ID único.</li>
 *   <li>Descripción.</li>
 *   <li>Nivel de criticidad (1 a 4, siendo 1 el más crítico).</li>
 * </ul>
 *
 * <p>Sugerencias:
 * <ul>
 *   <li>Para poder usar la tarea como dato del árbol AVL provisto, la clase debería
 *       implementar {@link Comparable Comparable&lt;Tarea&gt;}.</li>
 *   <li>Considerá si la clase debe ser inmutable (campos {@code final}, sin setters).</li>
 *   <li>Considerá qué validaciones aplican al construir una tarea.</li>
 * </ul>
 */
public class Tarea implements Comparable<Tarea>{

    // TODO: definir atributos.

    private int id;
    public String descripcion;
    public int criticidad;
    public static int contador = 0;

    // TODO: definir constructor(es) y validaciones.
    public Tarea(String unaDescripcion, int unaCriticidad){
        this.descripcion = unaDescripcion;
        if (unaCriticidad<1) {
            this.criticidad = 1;
        }else if(unaCriticidad>4){
            this.criticidad = 4;
        }else{
            this.criticidad = unaCriticidad;
        }
        this.id = ++contador;
    }

    // TODO: definir getters relevantes.

    public int getCriticidad(){
        return this.criticidad;
    }

    public int getId(){
        return this.id;
    }
    // TODO: implementar Comparable<Tarea> si corresponde.

    @Override
    public int compareTo(Tarea otraTarea) {
        return Integer.compare(this.id, otraTarea.id);
    }

    // TODO: opcionalmente, sobrescribir equals / hashCode / toString.

}
