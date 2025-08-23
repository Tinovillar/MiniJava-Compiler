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

    private Token e1Number() {return null;}
    private Token e1Word() {return null;}
    private Token e1SimpleComment() {return null;}
    private Token e1MultipleComment() {return null;}
    private Token e1Blank() {return null;}
    private Token e1Equal() {return null;}
    private Token e1NonEqual() {return null;}
    private Token e1Lower() {return null;}
    private Token e1Higher() {return null;}
    private Token e1Add() {return null;}
    private Token e1Sub() {return null;}
    private Token e1Mul() {return null;}
    private Token e1Div() {return null;}
    private Token e1And() {return null;}
    private Token e1Or() {return null;}
    private Token e1Parenthesis() {return null;}
    private Token e1Bracket1() {return null;}
    private Token e1Bracket2() {return null;}
    private Token e1Dot() {return null;}
    private Token e1Comma() {return null;}
    private Token e1Colon() {return null;}
    private Token e1SemiColon() {return null;}
}
