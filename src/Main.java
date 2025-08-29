import exceptions.LexicalException;
import lexical.ID;
import lexical.LexicalAnalyzer;
import lexical.Token;
import sourceManager.BetterSourceManagerImpl;
import sourceManager.SourceManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            boolean errors = false;

            SourceManager sourceManager = new BetterSourceManagerImpl();
            sourceManager.open(args[0]);

            Token currentToken = null;
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceManager);

            do {
                try {
                    currentToken = lexicalAnalyzer.getNextToken();
                    System.out.println(currentToken.toString());
                } catch (LexicalException e) {
                    errors = true;
                    e.printStackTrace();
                }
            } while(currentToken == null || currentToken.getId() != ID.EOF);

            if (!errors) System.out.println("[SinErrores]");

            sourceManager.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}