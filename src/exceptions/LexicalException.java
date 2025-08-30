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
        System.out.println("[Error in line " + line + ", column " + column + "]");
        System.out.print("Lexeme: ");
        System.out.println(currentLine);
        for (int i = 0; i < 7 + currentLine.length(); i++) {
            System.out.print("-");
        }
        System.out.println("^");
        System.out.println("[Error:"+currentLine+"|"+line+"]");
    }
}
