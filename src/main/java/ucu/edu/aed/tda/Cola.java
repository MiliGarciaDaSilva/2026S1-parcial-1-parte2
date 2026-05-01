package ucu.edu.aed.tda;

public class Cola<T> extends ListaEnlazada<T> implements TDACola<T> {

    @Override
    public T frente() {
        if (esVacio()) {
            return null;
        }
        return obtener(0);
    }

    @Override
    public boolean poneEnCola(T dato) {
        agregar(dato);
        return true;
    }
    
    @Override
    public T quitaDeCola() {
        if (esVacio()) {
            return null;
        }
        return remover(0);
    }

}
