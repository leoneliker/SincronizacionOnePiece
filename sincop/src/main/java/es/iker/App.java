package es.iker;

import es.iker.servicios.ServicioSincronizar;
import es.iker.servicios.ServicioTraducir;
import java.util.Arrays;
import java.util.List;

public class App {
    /**
     * Método principal que inicia la aplicación.
     * Comentar los servicios que no se vayan a utilizar.
     * 
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // Ajustar rutas según necesidad
        List<String> rutas = Arrays.asList("");
        // Ajustar rango de tiempo según necesidad
        Double rangoTiempo = 14.0;

        ServicioTraducir servicioTraducir = new ServicioTraducir(rutas);
        servicioTraducir.traducir();
        ServicioSincronizar servicioSincronizar = new ServicioSincronizar(rutas, rangoTiempo);
        servicioSincronizar.sincronizarNombres();
        servicioSincronizar.sincronizarTiempos();
    }
}