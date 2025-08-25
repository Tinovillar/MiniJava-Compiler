package lexical;

import sourceManager.SourceManager;

import java.io.IOException;
import java.lang.Character;

public class LexicalAnalyzer {
    String lexeme;
    char currentChar;
    SourceManager sourceManager;

    public LexicalAnalyzer(SourceManager sourceManager) {
        this.lexeme = "";
        this.currentChar = ' ';
        this.sourceManager = sourceManager;
        updateCurrentChar();
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
    private void updateLexemeAndCurrentChar() {
        updateLexeme();
        updateCurrentChar();
    }

    private Token e0() {
        switch(currentChar) {
            // BLANK
            case ' ':
            case '\n':
            case '\t':
                e0();
                break;
            // COMMENTS OR DIVISION
            case '/':
            // OPERATORS
            case '=':
                updateLexemeAndCurrentChar();
                return e1Equal();
            case '!':
                updateLexemeAndCurrentChar();
                return e1ExclamationMark();
            case '<':
            case '>':
            default:
                if(Character.isLetter(currentChar)) {
                    updateLexemeAndCurrentChar();
                    return e1Letter();
                } else if(Character.isDigit(currentChar)) {
                    updateLexemeAndCurrentChar();
                    return e1Digit();
                }
        }
        return null;
    }

    private Token e1Digit() {
        if(Character.isDigit(currentChar)) {
            updateLexemeAndCurrentChar();
            return e1Digit();
        } else {
            return new Token(0, lexeme, sourceManager.getLineNumber());
        }
    }
    private Token e1Letter() {return null;}
    private Token e1SimpleComment() {
        while(currentChar != '\n') {
            updateCurrentChar();
        }
        lexeme = "";
        return e0();
    }
    private Token e1MultipleComment() {
        while(true) {
            if(currentChar == '*') {
                updateCurrentChar();
                if(currentChar == '/')
                    break;
            } else {
                updateCurrentChar();
            }
        }
        lexeme = "";
        return e0();
    }
    private Token e1Equal() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
        }
        return new Token(0, lexeme, sourceManager.getLineNumber());
    }
    private Token e1NonEqual() {return null;}
    private Token e1ExclamationMark() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return new Token(0, lexeme, sourceManager.getLineNumber());
        }
        return null; // Exception
    }
    private Token e1Lower() {return null;}
    private Token e1Higher() {return null;}
    private Token e1Add() {return null;}
    private Token e1Sub() {return null;}
    private Token e1Mul() {return null;}
    private Token e1Slash() {
        if(currentChar == '/') {
            updateCurrentChar();
            return e1SimpleComment();
        } else if(currentChar == '*') {
            updateCurrentChar();
            return e1MultipleComment();
        } else {
            return new Token(0, lexeme, sourceManager.getLineNumber());
        }
    }
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
