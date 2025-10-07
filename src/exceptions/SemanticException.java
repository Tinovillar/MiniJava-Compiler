package exceptions;

import lexical.Token;

public class SemanticException extends Exception {
    private Token currentToken;
    private String msg;

    public SemanticException(Token currentToken, String msg) {
        this.currentToken = currentToken;
        this.msg = msg;
    }

    @Override
    public void printStackTrace() {
        System.out.println("Error semantico en la linea " + currentToken.getLineNumber() + ": " + msg);
        System.out.println("[Error:"+ currentToken.getLexeme() + "|" + currentToken.getLineNumber() + "]");
    }
}
