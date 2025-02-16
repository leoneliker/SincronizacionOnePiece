package es.iker.servicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servicio para sincronizar nombres y tiempos de archivos de subtítulos.
 */
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

    /**
     * Sincroniza los nombres de los archivos MKV y ASS en el directorio
     * especificado.
     * 
     * @param ruta La ruta del directorio.
     */
    public void sincronizarNombres() {
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

    /**
     * Sincroniza los tiempos de los subtítulos en los archivos ASS en el directorio
     * especificado.
     * 
     * @param ruta        La ruta del directorio.
     * @param rangoTiempo El rango de tiempo para la sincronización.
     */
    public void sincronizarTiempos() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File directorio = new File(ruta);
        File[] archivosASS = directorio.listFiles((dir, nombre) -> nombre.endsWith(".ass"));
        SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss.SS");
        for (File archivoASS : archivosASS) {
            try {
                br = new BufferedReader(new FileReader(archivoASS.getAbsolutePath()));
                StringBuilder contenidoModificado = new StringBuilder();
                String texto = br.readLine();
                while (texto != null) {
                    if (texto.startsWith("Dialogue: ")) {
                        String[] partes = texto.split(",");
                        try {
                            Date startTime = sdf.parse(partes[1]);
                            Date endTime = sdf.parse(partes[2]);
                            long startMillis = startTime.getTime() - (long) (rangoTiempo * 1000);
                            long endMillis = endTime.getTime() - (long) (rangoTiempo * 1000);
                            partes[1] = sdf.format(new Date(startMillis));
                            partes[2] = sdf.format(new Date(endMillis));
                            texto = String.join(",", partes);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    contenidoModificado.append(texto).append("\n");
                    texto = br.readLine();
                }
                br.close();
                bw = new BufferedWriter(new FileWriter(archivoASS.getAbsolutePath()));
                bw.write(contenidoModificado.toString());
                bw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
