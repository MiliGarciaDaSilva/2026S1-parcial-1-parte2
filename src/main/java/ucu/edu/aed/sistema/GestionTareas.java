package ucu.edu.aed.sistema;

import java.util.ArrayList;

import ucu.edu.aed.modelo.Cola;
import ucu.edu.aed.modelo.Tarea;
import ucu.edu.aed.tda.AVLArbol;

public class GestionTareas implements SistemaGestion{
//  public ArrayList<Cola<Tarea>> pendientes;
  public Cola<Tarea> criticas1;
  public Cola<Tarea> criticas2;
  public Cola<Tarea> comunes;
  public Cola<Tarea> espera;
  public Cola<Tarea> procesadas;
  public AVLArbol<Tarea> historial;

  public GestionTareas(){
    criticas1 = new Cola<Tarea>();
    criticas2 = new Cola<Tarea>();
    comunes = new Cola<Tarea>();
  /* pendientes = new ArrayList<Cola<Tarea>>();
    pendientes.add(criticas1);
    pendientes.add(criticas2);
    pendientes.add(comunes);
    */
    espera = new Cola<Tarea>();
    procesadas = new Cola<Tarea>();
    historial = new AVLArbol<>();
  }

  @Override
  public boolean recibirTarea(Tarea tarea) {
    
    if (tarea == null) {
      return false;
    }
    
    boolean agarroUltimo = true;
    while (agarroUltimo && !espera.esVacio()) {
      recibirTarea(espera.quitaDeCola());
      agarroUltimo = false;
    }
    
    if(this.cantidad() < 25){
      intentarIngresar(tarea);
    }
    else{
      espera.agregar(tarea);
    }

    return true;
  }



  private void intentarIngresar(Tarea tarea){
  /* 
    if (tarea.getCriticidad() < 3) {
      if (criticas1.tamaño() + criticas2.tamaño() < 10) {
        pendientes.get(tarea.getCriticidad() - 1).agregar(tarea);
      }else{
        espera.agregar(tarea);
      }
    }else{
      pendientes.get(tarea.getCriticidad() - 1).agregar(tarea);
    }
    */

    if (tarea.getCriticidad() < 3 ){
      if ((criticas1.tamaño() + criticas2.tamaño())< 10) {
        if (tarea.getCriticidad()==1) {
          criticas1.agregar(tarea);
        }else{
          criticas2.agregar(tarea);
        }
      }
      else{
        espera.agregar(tarea);
      }
    }else{
      comunes.agregar(tarea);
    }
  }

  private int cantidad(){
    return criticas1.tamaño() + criticas2.tamaño() + comunes.tamaño();
  }

  @Override
  public Tarea procesarTarea() {
    if (!criticas1.esVacio()) {
      Tarea tarea = criticas1.quitaDeCola();
      this.actualizarHistorial(tarea);
      return tarea;
    }
    if (!criticas2.esVacio()) {
      Tarea tarea = criticas2.quitaDeCola();
      this.actualizarHistorial(tarea);
      return tarea;
    }
    if (!comunes.esVacio()) {
      Tarea tarea = comunes.quitaDeCola();
      this.actualizarHistorial(tarea);
      return tarea;
    }
    return null;
  }

  private void actualizarHistorial(Tarea tarea){
    if (procesadas.tamaño() < 74) {
      procesadas.agregar(tarea);
    }else{
      procesadas.agregar(tarea);
      while (!procesadas.esVacio()) {
        historial.insertar(procesadas.quitaDeCola());
      }
    }
  }

  @Override
  public Tarea buscarTareaProcesada(int id) {
    return historial.buscar(tarea -> Integer.compare(id, tarea.getId()));
  }

  @Override
  public Tarea cancelarTarea(int id) {
    
    if (buscarPendiente(comunes, id) != null) {
      return buscarPendiente(comunes, id);
    }
    if (buscarPendiente(criticas1, id) != null) {
      return buscarPendiente(criticas1, id);
    }
    if (buscarPendiente(criticas2, id) != null) {
      return buscarPendiente(criticas2, id);
    }
    return null; 
  }

  private Tarea buscarPendiente(Cola<Tarea> cola, int id){
    int i = 0;
    while (i < cola.tamaño()) {
      if (cola.obtener(i).getId() == id) {
        return (Tarea) cola.obtener(i);
      }
    }
    return null;
  }
  
}
