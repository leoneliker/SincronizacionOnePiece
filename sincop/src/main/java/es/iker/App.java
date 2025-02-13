package es.iker;

public class App {
    public static void main(String[] args) {
        String ruta = "D:\\[df68] One Piece Season 01 [v2][1080p][x264][JPN][SUB]";
        Double rangoTiempo = 14.0;
        ServicioSincronizar servicioSincronizar = new ServicioSincronizar(ruta, rangoTiempo);
        //traducirNombres(ruta);
        servicioSincronizar.sincronizarNombres(ruta);
        servicioSincronizar.sincronizarTiempos(ruta, rangoTiempo);
    }
}