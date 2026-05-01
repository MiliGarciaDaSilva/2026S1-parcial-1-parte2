package ucu.edu.aed.tda.implementaciones;

public class ColaPrioridad<T> {

    private NodoPrioridad<T> primero;

    public boolean esVacia() {
        return primero == null;
    }

    public void insertar(T dato, int prioridad) {
        NodoPrioridad<T> nuevo = new NodoPrioridad<>(dato, prioridad);

        if (primero == null || prioridad > primero.getPrioridad()) {
            nuevo.setSiguiente(primero);
            primero = nuevo;
            return;
        }

        NodoPrioridad<T> actual = primero;
        while (actual.getSiguiente() != null &&
               actual.getSiguiente().getPrioridad() >= prioridad) {
            actual = actual.getSiguiente();
        }

        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
    }

    public T quitar() {
        if (esVacia()) return null;

        NodoPrioridad<T> aux = primero;
        primero = primero.getSiguiente();
        return aux.getDato();
    }

    public T frente() {
        if (esVacia()) return null;
        return primero.getDato();
    }
}