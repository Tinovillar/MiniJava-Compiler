package exceptions;

public class LexicalException extends Exception {
    private String lexeme;
    private int line;
    private int column;

    public LexicalException(String lexeme, int line, int column) {
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    @Override
    public void printStackTrace() {
        System.out.println("Lexeme: " + lexeme + " - Line number: " + line);
    }
}
