package es.iker;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import es.iker.servicios.ServicioSincronizar;
import es.iker.servicios.ServicioTraducir;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> rutas = Arrays.asList("");
        Double rangoTiempo = 14.0;
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Thread traducirThread = new Thread(() -> {
            try {
                for (String ruta : rutas) {
                    ServicioTraducir servicioTraducir = new ServicioTraducir(Arrays.asList(ruta));
                    servicioTraducir.traducir();
                    System.out.println("Traducción completada para la carpeta: " + ruta);
                    queue.put(ruta); 
                }
                queue.put("FIN"); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread sincronizarThread = new Thread(() -> {
            try {
                while (true) {
                    String ruta = queue.take();
                    if (ruta.equals("FIN")) {
                        break;
                    }
                    System.out.println("Iniciando sincronización para la carpeta: " + ruta);
                    ServicioSincronizar servicioSincronizar = new ServicioSincronizar(Arrays.asList(ruta), rangoTiempo);
                    servicioSincronizar.sincronizarNombres();
                    servicioSincronizar.sincronizarTiempos();
                    System.out.println("Sincronización completada para la carpeta: " + ruta);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        traducirThread.start();
        sincronizarThread.start();

        traducirThread.join();
        sincronizarThread.join();
    }
}