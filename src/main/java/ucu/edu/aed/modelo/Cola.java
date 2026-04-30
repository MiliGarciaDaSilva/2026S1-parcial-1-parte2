package ucu.edu.aed.modelo;

import ucu.edu.aed.tda.TDACola;
import ucu.edu.aed.tda.TDANodo;

public class Cola<T> extends ListaEnlazada<T> implements TDACola<T> {

    public T frente(){

        return ultimo.getDato();
    }

    public boolean poneEnCola(T dato){
        if(primero == null)
            return false;
        
        TDANodo<T> nuevoNodo = new TDANodo<T>(dato);
        ultimo.setSiguiente(nuevoNodo);
        ultimo = nuevoNodo;
        return true;
    }

    public T quitaDeCola(){
        if(primero == null)
            return null;
        TDANodo<T> nodoAEliminar = primero;
        primero = primero.getSiguiente();
        nodoAEliminar.setSiguiente(null);
        return nodoAEliminar.getDato();

        
    }

}
