package ucu.edu.aed.tda.implementaciones;

import java.util.Comparator;
import java.util.function.Predicate;

import ucu.edu.aed.tda.interfaces.TDALista;

public class ListaEnlazada<T> implements TDALista<T> {

    private TDANodo<T> primero;
    private TDANodo<T> ultimo;
    private int cantidad;

    public ListaEnlazada() {
        this.primero = null;
        this.ultimo = null;
        this.cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public void agregar(T elem) {
        TDANodo<T> nuevoNodo = new TDANodo<>(elem);
        if (esVacio()) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            ultimo.setSiguiente(nuevoNodo);
            ultimo = nuevoNodo;
        }
        cantidad++;
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > cantidad) {
            throw new IndexOutOfBoundsException();
        }

        TDANodo<T> nuevoNodo = new TDANodo<>(elem);
        if (index == 0) {
            nuevoNodo.setSiguiente(primero);
            primero = nuevoNodo;
            if (ultimo == null) {
                ultimo = nuevoNodo;
            }
        } else {
            TDANodo<T> anterior = primero;
            for (int i = 0; i < index - 1; i++) {
                anterior = anterior.getSiguiente();
            }
            nuevoNodo.setSiguiente(anterior.getSiguiente());
            anterior.setSiguiente(nuevoNodo);
            if (nuevoNodo.getSiguiente() == null) {
                ultimo = nuevoNodo;
            }
        }
        cantidad++;
    }

    @Override
    public T obtener(int index) {
        if (index < 0 || index >= cantidad) {
            throw new IndexOutOfBoundsException();
        }
        TDANodo<T> nodoActual = primero;
        for (int i = 0; i < index; i++) {
            nodoActual = nodoActual.getSiguiente();
        }
        return nodoActual.getDato();
    }

    @Override
    public T remover(int index) {
        if (index < 0 || index >= cantidad) {
            throw new IndexOutOfBoundsException();
        }

        TDANodo<T> removido;
        if (index == 0) {
            removido = primero;
            primero = primero.getSiguiente();
            if (primero == null) {
                ultimo = null;
            }
        } else {
            TDANodo<T> anterior = primero;
            for (int i = 0; i < index - 1; i++) {
                anterior = anterior.getSiguiente();
            }
            removido = anterior.getSiguiente();
            anterior.setSiguiente(removido.getSiguiente());
            if (removido == ultimo) {
                ultimo = anterior;
            }
        }
        removido.setSiguiente(null);
        cantidad--;
        return removido.getDato();
    }

    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

    @Override
    public boolean eliminar(T elem) {
        if (esVacio()) {
            return false;
        }

        if (primero.getDato() == null ? elem == null : primero.getDato().equals(elem)) {
            primero = primero.getSiguiente();
            if (primero == null) {
                ultimo = null;
            }
            cantidad--;
            return true;
        }

        TDANodo<T> anterior = primero;
        TDANodo<T> actual = primero.getSiguiente();
        while (actual != null) {
            if (actual.getDato() == null ? elem == null : actual.getDato().equals(elem)) {
                anterior.setSiguiente(actual.getSiguiente());
                if (actual == ultimo) {
                    ultimo = anterior;
                }
                actual.setSiguiente(null);
                cantidad--;
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return false;
    }

    @Override
    public int indiceDe(T elem) {
        TDANodo<T> nodoActual = primero;
        int indice = 0;
        while (nodoActual != null) {
            if (nodoActual.getDato() == null ? elem == null : nodoActual.getDato().equals(elem)) {
                return indice;
            }
            nodoActual = nodoActual.getSiguiente();
            indice++;
        }
        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        TDANodo<T> nodoActual = primero;
        while (nodoActual != null) {
            if (criterio.test(nodoActual.getDato())) {
                return nodoActual.getDato();
            }
            nodoActual = nodoActual.getSiguiente();
        }
        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparador) {
        ListaEnlazada<T> nuevaLista = new ListaEnlazada<>();
        TDANodo<T> nodoActual = primero;
        while (nodoActual != null) {
            nuevaLista.agregar(nodoActual.getDato());
            nodoActual = nodoActual.getSiguiente();
        }

        if (nuevaLista.cantidad < 2) {
            return nuevaLista;
        }

        boolean huboIntercambio;
        do {
            huboIntercambio = false;
            TDANodo<T> temp = nuevaLista.primero;
            while (temp.getSiguiente() != null) {
                if (comparador.compare(temp.getDato(), temp.getSiguiente().getDato()) > 0) {
                    T datoTemp = temp.getDato();
                    temp.setDato(temp.getSiguiente().getDato());
                    temp.getSiguiente().setDato(datoTemp);
                    huboIntercambio = true;
                }
                temp = temp.getSiguiente();
            }
        } while (huboIntercambio);

        return nuevaLista;
    }

    @Override
    public int tamaño() {
        return cantidad;
    }

    @Override
    public boolean esVacio() {
        return primero == null;
    }

    @Override
    public void vaciar() {
        primero = null;
        ultimo = null;
        cantidad = 0;
    }
}
