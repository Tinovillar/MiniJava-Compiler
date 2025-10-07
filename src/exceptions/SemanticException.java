package exceptions;

import lexical.Token;

public class SemanticException extends Exception {
    private Token currentToken;

    public SemanticException(Token currentToken) {
        this.currentToken = currentToken;
    }

    @Override
    public void printStackTrace() {
        System.out.println("Error semantico.");
    }
}
