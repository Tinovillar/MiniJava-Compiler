package exceptions;

import lexical.ID;
import lexical.Token;

public class SyntacticException extends Exception {
    private Token currentToken;
    private String message;

    public SyntacticException(Token currentToken, String message) {
        this.currentToken = currentToken;
        this.message = message;
    }

    @Override
    public void printStackTrace() {
        System.out.println("Error sintactico en la linea " + currentToken.getLineNumber());
        System.out.println(message);
        System.out.println(currentToken.toString());
    }
}
