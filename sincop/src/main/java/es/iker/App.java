package es.iker;

import es.iker.servicios.ServicioSincronizar;
import es.iker.servicios.ServicioTraducir;

public class App {
    /**
     * Método principal que inicia la aplicación.
     * Comentar los servicios que no se vayan a utilizar.
          * @throws Exception 
          * 
          */
         public static void main(String[] args) throws Exception {
        // Ajustar ruta segun necesidad
        String ruta = "D:\\One Piece 63-77";
        // Ajustar rango de tiempo segun necesidad
        Double rangoTiempo = 14.0;

        ServicioTraducir servicioTraducir = new ServicioTraducir(ruta);
        servicioTraducir.traducir();
        ServicioSincronizar servicioSincronizar = new ServicioSincronizar(ruta,
        rangoTiempo);
        servicioSincronizar.sincronizarNombres();
        // servicioSincronizar.sincronizarTiempos();
    }
}