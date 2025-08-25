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
    private Token tokenToReturn(int id) {
        return new Token(id, lexeme, sourceManager.getLineNumber());
    }

    private Token e0() {
        switch(currentChar) {
            // BLANK
            case ' ':
            case '\t':
            case '\r':
            case '\n':
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
                updateLexemeAndCurrentChar();
                return e1Lower();
            case '>':
                updateLexemeAndCurrentChar();
                return e1Higher();
            case '+':
                updateLexemeAndCurrentChar();
                return e1Add();
            case '-':
                updateLexemeAndCurrentChar();
                return e1Sub();
            case '*':
                updateLexemeAndCurrentChar();
                return e1Mul();
            case '&':
                updateLexemeAndCurrentChar();
                return e1And();
            case '|':
                updateLexemeAndCurrentChar();
                return e1Or();
            // PUNCTUATION
            case '(':
                updateLexemeAndCurrentChar();
                return e1OpenParenthesis();
            case ')':
                updateLexemeAndCurrentChar();
                return e1CloseParenthesis();
            case '{':
                updateLexemeAndCurrentChar();
                return e1OpenBracket1();
            case '}':
                updateLexemeAndCurrentChar();
                return e1CloseBracket1();
            case '[':
                updateLexemeAndCurrentChar();
                return e1OpenBracket2();
            case ']':
                updateLexemeAndCurrentChar();
                return e1CloseBracket2();
            case '.':
                updateLexemeAndCurrentChar();
                return e1Dot();
            case ',':
                updateLexemeAndCurrentChar();
                return e1Comma();
            case ':':
                updateLexemeAndCurrentChar();
                return e1Colon();
            case ';':
                updateLexemeAndCurrentChar();
                return e1SemiColon();
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
            return tokenToReturn(0);
        }
    }
    private Token e1Letter() {
        if(Character.isLetterOrDigit(currentChar)) {
            updateLexemeAndCurrentChar();
            return tokenToReturn(0);
        }
        return tokenToReturn(0);
    }
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
        return tokenToReturn(0);
    }
    private Token e1NonEqual() {
        return tokenToReturn(0);
    }
    private Token e1ExclamationMark() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return e1NonEqual();
        }
        return null; // Exception
    }
    private Token e1Lower() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return e2Lower();
        }
        return tokenToReturn(0);
    }
    private Token e2Lower() {
        return tokenToReturn(0);
    }
    private Token e1Higher() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return e2Higher();
        }
        return tokenToReturn(0);
    }
    private Token e2Higher() {
        return tokenToReturn(0);
    }
    private Token e1Add() {
        if(currentChar == '+') {
            updateLexemeAndCurrentChar();
            return e2Add();
        }
        return tokenToReturn(0);
    }
    private Token e2Add() {
        return tokenToReturn(0);
    }
    private Token e1Sub() {
        if(currentChar == '-') {
            updateLexemeAndCurrentChar();
            return e2Sub();
        }
        return tokenToReturn(0);
    }
    private Token e2Sub() {
        return tokenToReturn(0);
    }
    private Token e1Mul() {
        return tokenToReturn(0);
    }
    private Token e1Slash() {
        if(currentChar == '/') {
            updateCurrentChar();
            return e1SimpleComment();
        } else if(currentChar == '*') {
            updateCurrentChar();
            return e1MultipleComment();
        } else {
            return tokenToReturn(0);
        }
    }
    private Token e1And() {
        if(currentChar == '&') {
            updateLexemeAndCurrentChar();
            return e2And();
        }
        return null; // ERROR
    }
    private Token e2And() {
        return tokenToReturn(0);
    }
    private Token e1Or() {
        if(currentChar == '|') {
            updateLexemeAndCurrentChar();
            return e2Or();
        }
        return null; // ERROR
    }
    private Token e2Or() {
        return tokenToReturn(0);
    }
    private Token e1OpenParenthesis() {
        return tokenToReturn(0);
    }
    private Token e1CloseParenthesis() {
        return tokenToReturn(0);
    }
    private Token e1OpenBracket1() {
        return tokenToReturn(0);
    }
    private Token e1CloseBracket1() {
        return tokenToReturn(0);
    }
    private Token e1OpenBracket2() {
        return tokenToReturn(0);
    }
    private Token e1CloseBracket2() {
        return tokenToReturn(0);
    }
    private Token e1Dot() {
        return tokenToReturn(0);
    }
    private Token e1Comma() {
        return tokenToReturn(0);
    }
    private Token e1Colon() {
        return tokenToReturn(0);
    }
    private Token e1SemiColon() {
        return tokenToReturn(0);
    }
}
