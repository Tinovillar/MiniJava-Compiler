package exceptions;

import lexical.ID;
import lexical.Token;
import sourceManager.SourceManager;

public class SyntacticException extends Exception {
    private Token currentToken;
    private String expected;

    public SyntacticException(Token currentToken, String expected) {
        this.currentToken = currentToken;
        this.expected = expected;
    }

    @Override
    public void printStackTrace() {
        System.out.println("Error sintactico en la linea " + currentToken.getLineNumber());
        System.out.println("Se esperaba " + expected + " y se encontro: '" + currentToken.getLexeme()+"'");
        String lexeme = currentToken.getId().equals(ID.EOF)
                ? "EOF"
                : currentToken.getLexeme();

        System.out.println("[Error:" + lexeme + "|" + currentToken.getLineNumber() + "]");
//        System.out.println("[Error:"+currentToken.getLexeme()+"|"+currentToken.getLineNumber()+"]");
    }
}
