package es.iker;

import java.io.File;

public class ServicioSincronizar {
    private String ruta;
    private Double rangoTiempo;
    
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Double getRangoTiempo() {
        return rangoTiempo;
    }

    public void setRangoTiempo(Double rangoTiempo) {
        this.rangoTiempo = rangoTiempo;
    }

    public ServicioSincronizar(String ruta, Double rangoTiempo) {
        this.ruta = ruta;
        this.rangoTiempo = rangoTiempo;
    }

    public void sincronizarNombres(String ruta) {
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
            for (int i = 0; i < lenght; i++) {
                File archivoMKV = archivosMKV[i];
                File archivoASS = archivosASS[i];
                File aux = new File(ruta + "\\" + archivoMKV.getName().replace(".mkv", ".ass"));
                archivoASS.renameTo(aux);
            }
        } else {
            System.out.println("Tamaño diferente, no posible transcripccion");
        }
    }

    public void sincronizarTiempos(String ruta, Double rangoTiempo) {
        // BufferedReader br = null;
        // File directorio = new File(ruta);
        // File[] archivosASS = directorio.listFiles((dir, nombre) -> nombre.endsWith(".ass"));
        // for (File archivoASS : archivosASS) {
        //     try {
        //         br = new BufferedReader(new FileReader(archivoASS.getAbsolutePath()));
        //         String texto = br.readLine();
        //         while (texto != null) {
        //             System.out.println(texto);
        //             texto = br.readLine();
        //         }
        //     } catch (FileNotFoundException e) {
        //         e.printStackTrace();
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        // }
    }
}
