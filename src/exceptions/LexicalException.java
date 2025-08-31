package exceptions;

public class LexicalException extends Exception {
    private String lexeme;
    private int line;
    private int column;
    private String details;

    public LexicalException(String lexeme, int line, int column, String details) {
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
        this.details = details;
    }

    @Override
    public void printStackTrace() {
        System.out.println("[Error in line " + line + ", column " + column + "] - Type error: " + details);
        System.out.print("Detail: ");
        System.out.println(lexeme);
        for (int i = 0; i < 7 + lexeme.length(); i++) {
            System.out.print("-");
        }
        System.out.println("^");
        System.out.println("[Error:" + lexeme + "|" + line + "]");
    }
}
