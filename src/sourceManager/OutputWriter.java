package sourceManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputWriter {

    public static void createFile(String nombreArchivoSalida, List<String> listaInstrucciones) throws IOException{
        FileWriter writer = new FileWriter(nombreArchivoSalida);
        for(String instruccion : listaInstrucciones){
            writer.write(instruccion+"\n");
        }
        writer.close();
    }
}
