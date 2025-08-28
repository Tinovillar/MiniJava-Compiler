package sourceManager;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class BetterSourceManagerImpl implements SourceManager {
    private BufferedReader reader;
    String currentLine;
    private int lineNumber;
    private int lineIndexNumber;

    public BetterSourceManagerImpl() {
        lineNumber = 1;
        lineIndexNumber = 0;
        currentLine = "";
    }

    @Override
    public void open(String filePath) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);

        reader = new BufferedReader(inputStreamReader);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public char getNextChar() throws IOException {
        int readCode = reader.read();

        if(readCode == -1) {
            return END_OF_FILE;
        }

        char currentChar = (char) readCode;

        if (currentChar == '\n') {
            lineNumber++;
            lineIndexNumber = 0;
            currentLine = "";
        } else {
            currentLine += currentChar;
            lineIndexNumber++;
        }

        return currentChar;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public int getColumnNumber() {
        return lineIndexNumber;
    }

    @Override
    public String getCurrentLine() {
        String toReturn = "";
        try {
            reader.mark(0);
            toReturn = currentLine + reader.readLine();
            reader.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}
