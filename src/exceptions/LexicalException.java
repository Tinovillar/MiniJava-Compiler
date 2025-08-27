package exceptions;

public class LexicalException extends Exception {
    private String currentLine;
    private int line;
    private int column;

    public LexicalException(String currentLine, int line, int column) {
        this.currentLine = currentLine;
        this.line = line;
        this.column = column;
    }

    @Override
    public void printStackTrace() {
        System.out.println("Error ==> Lexeme: \"" + currentLine + "\" - Line number: " + line);
        for (int i = 0; i < 18 + column; i++) {
            System.out.print(" ");
        }
        System.out.print("^");
        System.out.println();
    }
}
