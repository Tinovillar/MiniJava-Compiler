package sourceManager;
// Author: Juan Dingevan
// Con modificaciones de Valentino Villar

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SourceManager {
    void open(String filePath) throws FileNotFoundException;

    void close() throws IOException;

    char getNextChar() throws IOException;

    int getLineNumber();

    String getCurrentLine();

    int getColumnNumber();

    public static final char END_OF_FILE = (char) 26;
}
