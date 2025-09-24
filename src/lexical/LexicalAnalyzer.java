package lexical;

import exceptions.LexicalException;
import sourceManager.SourceManager;

import java.io.IOException;
import java.lang.Character;

public class LexicalAnalyzer {
    String lexeme;
    char currentChar;
    SourceManager sourceManager;
    boolean hasNext;

    public LexicalAnalyzer(SourceManager sourceManager) {
        this.lexeme = "";
        this.currentChar = ' ';
        this.sourceManager = sourceManager;
        this.hasNext = true;
        updateCurrentChar();
    }

    public Token getNextToken() throws LexicalException {
        lexeme = "";
        return e0();
    }
    public boolean hasNext() {
        return hasNext;
    }
    private void updateLexeme() {
        switch (currentChar) {
            case '\n':
                lexeme += "\\" + "n";
                break;
            case '\r':
                lexeme += "\\" + "r";
            default:
                lexeme = lexeme + currentChar;
        }
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
    private Token tokenToReturn(lexID lexId) {
        return new Token(lexId, lexeme, sourceManager.getLineNumber());
    }

    private Token e0() throws LexicalException {
        if(Character.isLetter(currentChar)) {
            char c = currentChar;
            updateLexemeAndCurrentChar();
            if(Character.isLowerCase(c)) return e1LetterLowerCase();
            return e1LetterUpperCase();
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
            case '%':
                updateLexemeAndCurrentChar();
                return e1Mod();
            case '|':
                updateLexemeAndCurrentChar();
                return e1Or();
            // PUNCTUATION
            case '?':
                updateLexemeAndCurrentChar();
                return e1QuestionMark();
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
                hasNext = false;
                return new Token(lexID.EOF, lexeme, sourceManager.getLineNumber());
            default:
                int column = sourceManager.getColumnNumber();
                updateLexemeAndCurrentChar();
                throw new LexicalException(lexeme, sourceManager.getLineNumber(), column, "The program cannot recognize the current char.");
        }
    }

    private Token e1Digit() throws LexicalException {
        if(Character.isDigit(currentChar)) {
            updateLexemeAndCurrentChar();
            if(lexeme.length() <= 9) return e1Digit();
            throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "The number is too long.");
        } else {
            return tokenToReturn(lexID.literal_integer);
        }
    }
    private Token e1LetterUpperCase() throws LexicalException {
        if(Character.isLetterOrDigit(currentChar) || currentChar == '_') {
            updateLexemeAndCurrentChar();
            return e1LetterUpperCase();
        }
        return tokenToReturn(lexID.id_class);
    }
    private Token e1LetterLowerCase() throws LexicalException {
        if(Character.isLetterOrDigit(currentChar) || currentChar == '_') {
            updateLexemeAndCurrentChar();
            return e1LetterLowerCase();
        }
        return tokenToReturn(LexemeTable.getToken(lexeme));
    }
    private Token e1SingleQuote() throws LexicalException {
        if(currentChar == '\\') {
            updateLexemeAndCurrentChar();
            return e3SingleQuote();
        } else if(currentChar != '\'' && currentChar != SourceManager.END_OF_FILE && currentChar != '\n') {
            updateLexemeAndCurrentChar();
            return e2SingleQuote();
        }
        if(currentChar == '\'') {
            updateLexemeAndCurrentChar();
            throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "Empty character literal.");
        } else if(currentChar == '\n') {
            throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "Literal character must be closed in the same line.");
        }
        throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "Reaching EOF before closing single quote.");
    }
    private Token e2SingleQuote() throws LexicalException {
        if(currentChar == '\'') {
            updateLexemeAndCurrentChar();
            return tokenToReturn(lexID.literal_char);
        }
        while (currentChar != '\'' && currentChar != SourceManager.END_OF_FILE && currentChar != '\n') {
            updateLexemeAndCurrentChar();
        }
        if(currentChar == '\'') {
            updateLexemeAndCurrentChar();
            throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "A literal character must contain ONLY ONE character.");
        } else if(currentChar == '\n') {
            throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "Literal character must be closed in the same line.");
        }
        throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "Reaching EOF before closing single quote.");
    }
    private Token e3SingleQuote() throws LexicalException {
        updateLexemeAndCurrentChar();
        return e2SingleQuote();
    }
    private Token e1DoubleQuote() throws LexicalException {
        if(currentChar == '\\') {
            updateLexemeAndCurrentChar();
            if (currentChar == '"') updateLexemeAndCurrentChar();
            return e1DoubleQuote();
        } else if(currentChar != '"' && currentChar != SourceManager.END_OF_FILE && currentChar != '\n') {
            updateLexemeAndCurrentChar();
            return e1DoubleQuote();
        } else if(currentChar == '"') {
            updateLexemeAndCurrentChar();
            return tokenToReturn(lexID.literal_string);
        }
        if(currentChar == '\n') {
            throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "Literal string must be closed in the same line.");
        } else if(currentChar == SourceManager.END_OF_FILE) {
            throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "Reaching EOF before closing double quote.");
        }
        throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "Double quote were not closed");
    }
    private Token e1Slash() throws LexicalException {
        if(currentChar == '/') {
            updateCurrentChar();
            return e1SimpleComment();
        } else if(currentChar == '*') {
            updateLexemeAndCurrentChar();
            return e1MultipleComment();
        } else {
            return tokenToReturn(lexID.op_division);
        }
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
        if(currentChar == SourceManager.END_OF_FILE) {
            throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "EOF found. Multiple comment must be closed.");
        }
        updateLexemeAndCurrentChar();
        return e1MultipleComment();
    }
    private Token e1Equal() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return e2Equal();
        }
        return tokenToReturn(lexID.op_equal);
    }
    private Token e2Equal() {
        return tokenToReturn(lexID.op_equal_equal);
    }
    private Token e1NotEqual() {
        return tokenToReturn(lexID.op_not_equal);
    }
    private Token e1ExclamationMark() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return e1NotEqual();
        }
        return tokenToReturn(lexID.op_not);
    }
    private Token e1LessThan() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return e2LessThan();
        }
        return tokenToReturn(lexID.op_less_than);
    }
    private Token e2LessThan() {
        return tokenToReturn(lexID.op_less_than_equal);
    }
    private Token e1GreaterThan() {
        if(currentChar == '=') {
            updateLexemeAndCurrentChar();
            return e2GreaterThan();
        }
        return tokenToReturn(lexID.op_greater_than);
    }
    private Token e2GreaterThan() {
        return tokenToReturn(lexID.op_greater_than_equal);
    }
    private Token e1Add() {
        if(currentChar == '+') {
            updateLexemeAndCurrentChar();
            return e2Add();
        }
        return tokenToReturn(lexID.op_plus);
    }
    private Token e2Add() {
        return tokenToReturn(lexID.op_plus_plus);
    }
    private Token e1Sub() {
        if(currentChar == '-') {
            updateLexemeAndCurrentChar();
            return e2Sub();
        }
        return tokenToReturn(lexID.op_minus);
    }
    private Token e2Sub() {
        return tokenToReturn(lexID.op_minus_minus);
    }
    private Token e1Mul() {
        return tokenToReturn(lexID.op_multiplication);
    }
    private Token e1And() throws LexicalException {
        if(currentChar == '&') {
            updateLexemeAndCurrentChar();
            return e2And();
        }
        throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "The & operator is not supported. Must be &&.");
    }
    private Token e2And() {
        return tokenToReturn(lexID.op_and);
    }
    private Token e1Mod() { return tokenToReturn(lexID.op_mod); }
    private Token e1Or() throws LexicalException {
        if(currentChar == '|') {
            updateLexemeAndCurrentChar();
            return e2Or();
        }
        throw new LexicalException(lexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), "The | operator is not supported. Must be ||.");
    }
    private Token e1QuestionMark() {
        return tokenToReturn(lexID.p_question_mark);
    }
    private Token e2Or() {
        return tokenToReturn(lexID.op_or);
    }
    private Token e1OpenParenthesis() {
        return tokenToReturn(lexID.p_o_parenthesis);
    }
    private Token e1CloseParenthesis() {
        return tokenToReturn(lexID.p_c_parenthesis);
    }
    private Token e1OpenBracket1() {
        return tokenToReturn(lexID.p_o_bracket1);
    }
    private Token e1CloseBracket1() {
        return tokenToReturn(lexID.p_c_bracket1);
    }
    private Token e1OpenBracket2() {
        return tokenToReturn(lexID.p_o_bracket2);
    }
    private Token e1CloseBracket2() {
        return tokenToReturn(lexID.p_c_bracket2);
    }
    private Token e1Dot() {
        return tokenToReturn(lexID.p_dot);
    }
    private Token e1Comma() {
        return tokenToReturn(lexID.p_comma);
    }
    private Token e1Colon() {
        return tokenToReturn(lexID.p_colon);
    }
    private Token e1SemiColon() {
        return tokenToReturn(lexID.p_semicolon);
    }
}
