package es.iker.servicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import com.deepl.api.DeepLClient;
import com.deepl.api.TextResult;

public class ServicioTraducir {
    private List<String> rutas;

    public List<String> getRutas() {
        return rutas;
    }

    public void setRutas(List<String> rutas) {
        this.rutas = rutas;
    }

    public ServicioTraducir(List<String> rutas) {
        this.rutas = rutas;
    }

    public void traducir() throws Exception {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("./sincop/.env")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String authKey = props.getProperty("DEEPL_API_KEY");
        DeepLClient client = new DeepLClient(authKey);
        for (String ruta : rutas) {
            File directorio = new File(ruta);
            File[] archivosMKV = directorio.listFiles((dir, nombre) -> nombre.endsWith(".mkv"));
            for (File archivoMKV : archivosMKV) {
                System.out.println("Traduciendo...");
                String nombreArchivo = archivoMKV.getName();
                String titulo = nombreArchivo.substring(nombreArchivo.indexOf("-") + 2, nombreArchivo.lastIndexOf("-"))
                        .trim();
                titulo = titulo.replaceAll("\\d+ - ", "").replaceAll("\\[.*\\]", "").trim();
                TextResult result = client.translateText(titulo, "es", "es");
                String tituloTraducido = result.getText();
                String nuevoNombreArchivo = nombreArchivo.replace(titulo, tituloTraducido);
                nuevoNombreArchivo = nuevoNombreArchivo.replaceAll("\\[.*\\]-df68", "").trim();
                archivoMKV.renameTo(new File(directorio, nuevoNombreArchivo));
            }
            System.out.println("Traducida una carpeta!");
        }
    }
}