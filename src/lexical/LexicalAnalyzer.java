package lexical;

import exceptions.LexicalException;
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

    public Token getNextToken() throws LexicalException {
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
    private Token tokenToReturn(ID id) {
        return new Token(id, lexeme, sourceManager.getLineNumber());
    }

    private Token e0() throws LexicalException {
        if(Character.isLetter(currentChar)) {
            updateLexemeAndCurrentChar();
            return e1Letter();
        } else if(Character.isDigit(currentChar)) {
            updateLexemeAndCurrentChar();
            return e1Digit();
        } else if(Character.isWhitespace(currentChar)) {
            updateCurrentChar();
            return e0();
        }

        switch(currentChar) {
            // COMMENTS OR DIVISION
            case '/':
                updateLexemeAndCurrentChar();
                return e1Slash();
            // OPERATORS
            case '=':
                updateLexemeAndCurrentChar();
                return e1Equal();
            case '!':
                updateLexemeAndCurrentChar();
                return e1ExclamationMark();
            case '<':
                updateLexemeAndCurrentChar();
                return e1LessThan();
            case '>':
                updateLexemeAndCurrentChar();
                return e1GreaterThan();
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
            // TEXT
            case '\'':
                updateLexemeAndCurrentChar();
                return e1SingleQuote();
            case '"':
                updateLexemeAndCurrentChar();
                return e1DoubleQuote();
            // EOF
            case SourceManager.END_OF_FILE:
                updateLexemeAndCurrentChar();
                return new Token(ID.EOF, lexeme, sourceManager.getLineNumber());
            default:
                updateLexemeAndCurrentChar();
                throw new LexicalException(sourceManager.getCurrentLine(), sourceManager.getLineNumber(), sourceManager.getColumnNumber());
        }
    }

    private Token e1Digit() {
        if(Character.isDigit(currentChar)) {
            updateLexemeAndCurrentChar();
            return e1Digit();
        } else {
            return tokenToReturn(ID.integer);
        }
    }
    private Token e1Letter() {
        if(Character.isLetterOrDigit(currentChar) || currentChar == '_') {
            updateLexemeAndCurrentChar();
            return e1Letter();
        }
        return tokenToReturn(ID.identifier);
    }
    private Token e1SingleQuote() throws LexicalException {
        if(Character.isWhitespace(currentChar) || Character.isLetter(currentChar) || Character.isDigit(currentChar)) {
            updateLexemeAndCurrentChar();
            return e2SingleQuote();
        }
        throw new LexicalException(sourceManager.getCurrentLine(), sourceManager.getLineNumber(), sourceManager.getColumnNumber());
    }
    private Token e2SingleQuote() throws LexicalException {
        if(currentChar == '\'') {
            return tokenToReturn(ID.t_char);
        }
        throw new LexicalException(sourceManager.getCurrentLine(), sourceManager.getLineNumber(), sourceManager.getColumnNumber());
    }

    private Token e1DoubleQuote() {
        return null;
    }
    private Token e1SimpleComment() throws LexicalException {
        if(currentChar != '\n' && currentChar != SourceManager.END_OF_FILE) {
            updateCurrentChar();
            return e1SimpleComment();
        }
        lexeme = "";
        return e0();
    }
    private Token e1MultipleComment() throws LexicalException {
        if(currentChar == '*') {
            updateCurrentChar();
            if (currentChar == '/') {
                updateCurrentChar();
                lexeme = "";
                return e0();
            }
        }
        updateCurrentChar();
        return e1MultipleComment();
    }
    private Token e1Equal() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
        }
        return tokenToReturn(ID.op_equal);
    }
    private Token e1NotEqual() {
        return tokenToReturn(ID.op_not_equal);
    }
    private Token e1ExclamationMark() throws LexicalException {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return e1NotEqual();
        }
        return tokenToReturn(ID.op_not);
    }
    private Token e1LessThan() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return e2LessThan();
        }
        return tokenToReturn(ID.op_less_than);
    }
    private Token e2LessThan() {
        return tokenToReturn(ID.op_less_than_equal);
    }
    private Token e1GreaterThan() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return e2GreaterThan();
        }
        return tokenToReturn(ID.op_greater_than);
    }
    private Token e2GreaterThan() {
        return tokenToReturn(ID.op_greater_than_equal);
    }
    private Token e1Add() {
        if(currentChar == '+') {
            updateLexemeAndCurrentChar();
            return e2Add();
        }
        return tokenToReturn(ID.op_plus);
    }
    private Token e2Add() {
        return tokenToReturn(ID.op_plus_plus);
    }
    private Token e1Sub() {
        if(currentChar == '-') {
            updateLexemeAndCurrentChar();
            return e2Sub();
        }
        return tokenToReturn(ID.op_minus);
    }
    private Token e2Sub() {
        return tokenToReturn(ID.op_minus_minus);
    }
    private Token e1Mul() {
        return tokenToReturn(ID.op_multiplication);
    }
    private Token e1Slash() throws LexicalException {
        if(currentChar == '/') {
            updateCurrentChar();
            return e1SimpleComment();
        } else if(currentChar == '*') {
            updateCurrentChar();
            return e1MultipleComment();
        } else {
            return tokenToReturn(ID.op_division);
        }
    }
    private Token e1And() throws LexicalException {
        if(currentChar == '&') {
            updateLexemeAndCurrentChar();
            return e2And();
        }
        throw new LexicalException(sourceManager.getCurrentLine(), sourceManager.getLineNumber(), sourceManager.getColumnNumber());
    }
    private Token e2And() {
        return tokenToReturn(ID.op_and);
    }
    private Token e1Or() throws LexicalException {
        if(currentChar == '|') {
            updateLexemeAndCurrentChar();
            return e2Or();
        }
        throw new LexicalException(sourceManager.getCurrentLine(), sourceManager.getLineNumber(), sourceManager.getColumnNumber());
    }
    private Token e2Or() {
        return tokenToReturn(ID.op_or);
    }
    private Token e1OpenParenthesis() {
        return tokenToReturn(ID.p_o_parenthesis);
    }
    private Token e1CloseParenthesis() {
        return tokenToReturn(ID.p_c_parenthesis);
    }
    private Token e1OpenBracket1() {
        return tokenToReturn(ID.p_o_bracket1);
    }
    private Token e1CloseBracket1() {
        return tokenToReturn(ID.p_c_bracket1);
    }
    private Token e1OpenBracket2() {
        return tokenToReturn(ID.p_o_bracket2);
    }
    private Token e1CloseBracket2() {
        return tokenToReturn(ID.p_c_bracket2);
    }
    private Token e1Dot() {
        return tokenToReturn(ID.p_dot);
    }
    private Token e1Comma() {
        return tokenToReturn(ID.p_comma);
    }
    private Token e1Colon() {
        return tokenToReturn(ID.p_colon);
    }
    private Token e1SemiColon() {
        return tokenToReturn(ID.p_semicolon);
    }
}
