package ucu.edu.aed.sistema;



import ucu.edu.aed.modelo.Tarea;
import ucu.edu.aed.tda.AVLArbol;
import ucu.edu.aed.tda.Cola;

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

    if(this.cantidad() < 25){
      if (!espera.esVacio()) {
        intentarIngresar(espera.quitaDeCola());
        return true;
      }
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
        if (tarea.getCriticidad() == 1) {
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

  public int cantidad(){
    return criticas1.tamaño() + criticas2.tamaño() + comunes.tamaño();
  }

  @Override
  public Tarea procesarTarea() {

    if (!criticas1.esVacio()) {
      Tarea tarea = criticas1.quitaDeCola();
      this.actualizarHistorial(tarea);
      
      if (!espera.esVacio()) {
        intentarIngresar(espera.quitaDeCola());
      }
      
      return tarea;

    }else if (!criticas2.esVacio()) {
      Tarea tarea = criticas2.quitaDeCola();
      this.actualizarHistorial(tarea);
      
      if (!espera.esVacio()) {
        intentarIngresar(espera.quitaDeCola());
      }
      
      return tarea;

    }else if (!comunes.esVacio()) {
      Tarea tarea = comunes.quitaDeCola();
      this.actualizarHistorial(tarea);
      
      if (!espera.esVacio()) {
        intentarIngresar(espera.quitaDeCola());
      }
      
      return tarea;

    }

    return null;
  }

  private void actualizarHistorial(Tarea tarea){
    procesadas.agregar(tarea);
    if (procesadas.tamaño() >= 75) {
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
      i++;
    }
    return null;
  }
  
  public AVLArbol<Tarea> getHistorial(){
    return this.historial;
  }

  public Cola<Tarea> getCriticas1(){
    return this.criticas1;
  }
  
  public Cola<Tarea> getCriticas2(){
    return this.criticas2;
  }
}
