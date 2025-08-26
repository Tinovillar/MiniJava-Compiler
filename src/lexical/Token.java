package lexical;

public class Token {
    private ID id;
    private String lexeme;
    private int lineNumber;

    public Token(ID id, String lexeme, int lineNumber) {
        this.id = id;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }

    public ID getId() {
        return id;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return " - ("+ this.id.toString() +", " + this.lexeme + ", " + this.lineNumber + ") - ";
    }
}
