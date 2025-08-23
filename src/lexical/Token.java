package lexical;

public class Token {
    private int id;
    private String lexeme;
    private int lineNumber;

    public Token(int id, String lexeme, int lineNumber) {
        this.id = id;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }

    public int getId() {
        return id;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
