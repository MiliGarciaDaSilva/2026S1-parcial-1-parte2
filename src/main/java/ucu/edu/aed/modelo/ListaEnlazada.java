package ucu.edu.aed.modelo;

import ucu.edu.aed.tda.TDALista;
import ucu.edu.aed.tda.TDANodo;

import java.util.Comparator;
import java.util.function.Predicate;

public class ListaEnlazada<T> implements TDALista<T> {

    TDANodo<T> primero;
    TDANodo<T> ultimo;
    int cantidad;

    public int getCantidad(){
        return cantidad;
    }

    public void agregar(T elem){

        if(esVacio()){
            primero = new TDANodo<T>(elem);
            ultimo = primero;
            cantidad++;
            
        }
        else{
            TDANodo<T> nodoActual = primero;
            while (nodoActual.getSiguiente() != null) {
                nodoActual = nodoActual.getSiguiente();
            }
            TDANodo<T> nuevoNodo = new TDANodo<T>(elem);
            ultimo = nuevoNodo;
            nodoActual.setSiguiente(nuevoNodo);
            cantidad++;
            

        }
        

        
    }

    public void agregar(int index, T elem){
        
        if (esVacio()){
            if(index == 0){
                primero = new TDANodo<T>(elem);
                cantidad++;
                }
            else
            throw new IndexOutOfBoundsException();
        }
        else{
            TDANodo<T> nodoActual = primero;
            int posicion = 0;
            while (nodoActual.getSiguiente() != null && posicion != index) {
                nodoActual = nodoActual.getSiguiente();
                posicion ++;
            }
            if(posicion == index){
                TDANodo<T> nodoDesplazado = nodoActual.getSiguiente();
                TDANodo<T> nuevoNodo = new TDANodo<T>(elem);
                nuevoNodo.setSiguiente(nodoDesplazado);
                nodoActual.setSiguiente(nuevoNodo);
                cantidad++;
            }
            else{
                throw new IndexOutOfBoundsException();
            }

        }
        

    }

    public T obtener(int index){
        
        TDANodo<T> nodoActual = primero;
        int contador = 0;

        
        
        while (nodoActual != null) {
            if(index == contador)
            return nodoActual.getDato();
            nodoActual = nodoActual.getSiguiente();   
            contador++;
        }    

        return null;
        
        
    }
    
    public T remover(int index){
        TDANodo<T> nodoActual = primero;
        TDANodo<T> nodoBorrar;
        int contador = 1;

        while (nodoActual.getSiguiente() != null) {
            if(index == contador) {
                nodoBorrar = nodoActual.getSiguiente();
                nodoActual.setSiguiente(nodoBorrar.getSiguiente());
                nodoBorrar.setSiguiente(null);
                cantidad--;
                return nodoBorrar.getDato();
                
            }
            nodoActual = nodoActual.getSiguiente();
            contador++;
        }
        throw new IndexOutOfBoundsException();

        
    }

    public boolean contiene(T elem){
        TDANodo<T> nodoActual = primero;
        while (nodoActual != null) {
            if(nodoActual.getDato() == elem){
                return true;
            }
            nodoActual = nodoActual.getSiguiente();
        }
        return false;
    }

    public boolean eliminar(T elem){
        TDANodo<T> nodoActual = primero;
        TDANodo<T> nodoSiguiente = nodoActual.getSiguiente();
        while (nodoActual.getSiguiente() != null) {
            if(nodoSiguiente.getDato() == elem){
                nodoActual.setSiguiente(nodoSiguiente.getSiguiente());
                nodoSiguiente.setSiguiente(null);
                cantidad--;
                return true;
            }
            nodoActual = nodoActual.getSiguiente();
            nodoSiguiente = nodoActual.getSiguiente();
        }
        return false;
    }

    public int indiceDe (T elem){

        TDANodo<T> nodoActual = primero;
        
        int indice = 1;
        while (nodoActual.getSiguiente() != null) {
            if(nodoActual.getDato() == elem){
                return indice;
            }
            nodoActual = nodoActual.getSiguiente();
            indice++;
        }
        return 0;
    }


    public T buscar(Predicate<T> criterio){
         
        TDANodo<T> nodoActual = primero; 

        while (nodoActual != null) {
            
            if (criterio.test(nodoActual.getDato())) {
                return nodoActual.getDato(); 
        }
        nodoActual = nodoActual.getSiguiente();
    }

        return null; 

    }

    public TDALista<T> ordenar(Comparator<T> comparador){
        
        ListaEnlazada<T> nuevaLista = new ListaEnlazada<>();
        
        
        TDANodo<T> nodoActual = primero;
        while (nodoActual != null) {
            nuevaLista.agregar(nodoActual.getDato()); 
            nodoActual = nodoActual.getSiguiente();
        }

        
        if (nuevaLista.primero == null || nuevaLista.primero.getSiguiente() == null) {
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

    

    public int tamaño(){
        return cantidad;
    }

    public boolean esVacio(){
        
        return (primero == null);

    }

    public void vaciar(){
        primero = null;
        cantidad = 0;
    }
}
