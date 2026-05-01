package ucu.edu.aed.tda.implementaciones;

public class NodoPrioridad<T> {
    private T dato;
    private int prioridad;
    private NodoPrioridad<T> siguiente;

    public NodoPrioridad(T dato, int prioridad) {
        this.dato = dato;
        this.prioridad = prioridad;
        this.siguiente = null;
    }

    public T getDato() { return dato; }
    public int getPrioridad() { return prioridad; }
    public NodoPrioridad<T> getSiguiente() { return siguiente; }
    public void setSiguiente(NodoPrioridad<T> sig) { this.siguiente = sig; }
}