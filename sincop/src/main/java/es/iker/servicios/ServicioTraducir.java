package es.iker.servicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import com.deepl.api.DeepLClient;
import com.deepl.api.TextResult;

public class ServicioTraducir {
    private String ruta;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public ServicioTraducir(String ruta) {
        this.ruta = ruta;
    }

    /**
     * Traduce los archivos de video en el directorio especificado.
     * En sincronizacion los archivos de subtitulos recogeran el mismo nombre
     * 
     * @param ruta La ruta del directorio.
     */
    public void traducir() throws Exception {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("./sincop/.env")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String authKey = props.getProperty("DEEPL_API_KEY");
        DeepLClient client = new DeepLClient(authKey);
        File directorio = new File(ruta);
        File[] archivosMKV = directorio.listFiles((dir, nombre) -> nombre.endsWith(".mkv"));
        for (File archivoMKV : archivosMKV) {
            String nombreArchivo = archivoMKV.getName();
            String titulo = nombreArchivo.substring(nombreArchivo.indexOf("-") + 2, nombreArchivo.lastIndexOf("-"))
                    .trim();
            titulo = titulo.replaceAll("\\d+ - ", "").replaceAll("\\[.*\\]", "").trim();
            TextResult result = client.translateText(titulo, "en", "es");
            String tituloTraducido = result.getText();
            String nuevoNombreArchivo = nombreArchivo.replace(titulo, tituloTraducido);
            nuevoNombreArchivo = nuevoNombreArchivo.replaceAll("\\[.*\\]-df68", "").trim();
            archivoMKV.renameTo(new File(directorio, nuevoNombreArchivo));
            System.out.println("Traducido!");
        }
    }
}
