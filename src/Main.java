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
            SourceManager sourceManager = new BetterSourceManagerImpl();
            sourceManager.open("C:\\Users\\valen\\Desktop\\compilador\\compilador\\resource\\test2.java");

            Token currentToken = null;
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceManager);

            do {
                try {
                    currentToken = lexicalAnalyzer.getNextToken();
                    System.out.println(currentToken.toString());
                } catch (LexicalException e) {
                    e.printStackTrace();
                }
            } while(currentToken == null || currentToken.getId() != ID.EOF);

            sourceManager.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}