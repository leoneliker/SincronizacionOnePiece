package es.iker;

import java.io.File;

public class App {
    public static void main(String[] args) {
        String ruta = "D:\\[df68] One Piece Season 01 [v2][1080p][x264][JPN][SUB]";
        sincronizarNombres(ruta);
    }

    public static void sincronizarNombres(String ruta) {
        File directorio = new File(ruta);
        File[] archivosMKV = directorio.listFiles((dir, nombre) -> nombre.endsWith(".mkv"));
        File[] archivosASS = directorio.listFiles((dir, nombre) -> nombre.endsWith(".ass"));

        if (archivosMKV == null) {
            System.out.println("No se encontraron archivos mkv en la ruta especificada.");
            return;
        } else if (archivosASS == null) {
            System.out.println("No se encontraron archivos ass en la ruta especificada.");
            return;
        }
        if (archivosMKV.length == archivosASS.length) {
            int lenght = archivosASS.length;
            for (int i = 0; i<lenght; i++) {
                File archivoMKV = archivosMKV[i];
                File archivoASS = archivosASS[i];
                File aux = new File(ruta+"\\"+archivoMKV.getName().replace(".mkv", ".ass"));
                archivoASS.renameTo(aux);

            }
        } else {
            System.out.println("Tamaño diferente, no posible transcripccion");
        }
    }
}
