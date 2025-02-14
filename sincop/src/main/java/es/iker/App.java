package es.iker;

public class App {
    /**
     * Método principal que inicia la aplicación.
     * Comentar los servicios que no se vayan a utilizar.
     * 
     */
    public static void main(String[] args) {
        //Ajustar ruta segun necesidad
        String ruta = "D:\\[df68] One Piece Season 01 [v2][1080p][x264][JPN][SUB]";
        //Ajustar rango de tiempo segun necesidad
        Double rangoTiempo = 14.0;


        ServicioSincronizar servicioSincronizar = new ServicioSincronizar(ruta, rangoTiempo);
        servicioSincronizar.sincronizarNombres(ruta);
        servicioSincronizar.sincronizarTiempos(ruta, rangoTiempo);
    }
}