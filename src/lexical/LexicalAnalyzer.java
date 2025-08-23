package lexical;

import sourceManager.SourceManager;

import java.io.IOException;

public class LexicalAnalyzer {
    String lexeme;
    char currentChar;
    SourceManager sourceManager;

    public LexicalAnalyzer(SourceManager sourceManager) {
        this.lexeme = "";
        this.currentChar = ' ';
        this.sourceManager = sourceManager;
    }

    public Token getNextToken() {
        lexeme = "";
        return e0();
    }
    private void updateLexeme() {
        lexeme = lexeme + currentChar;
    }
    private void updateCurrentChar() {
        try {
            currentChar = sourceManager.getNextChar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Token e0() {return null;}
}
