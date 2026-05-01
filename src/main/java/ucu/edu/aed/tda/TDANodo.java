package ucu.edu.aed.tda.implementaciones;

public class TDANodo<T> {

    private T dato;
    private TDANodo<T> siguiente;

    public TDANodo(T unDato){
        setDato(unDato);
        this.siguiente = null;
    }
    
    public T getDato(){
        return this.dato;
    }

    public void setDato(T elem){
        this.dato = elem;
    }

    public void setSiguiente(TDANodo<T> nodo){
        this.siguiente = nodo;
    }
    

    public TDANodo<T> getSiguiente(){
        return this.siguiente;
    }

}
