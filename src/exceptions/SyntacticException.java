package exceptions;

import lexical.lexID;
import lexical.Token;

import java.util.Set;
import java.util.stream.Collectors;

public class SyntacticException extends Exception {
    private Token currentToken;
    private Set<lexID> expected;

    public SyntacticException(Token currentToken, Set<lexID> expected) {
        this.currentToken = currentToken;
        this.expected = expected;
    }

    @Override
    public void printStackTrace() {
        System.out.println("Error sintactico en la linea " + currentToken.getLineNumber());
        System.out.println("Se esperaba " + expected.stream().map(Enum::name).collect(Collectors.joining(", ")) + " y se encontro: '" + currentToken.getLexeme()+"'");
        String lexeme = currentToken.getId().equals(lexID.EOF)
                ? "EOF"
                : currentToken.getLexeme();

        System.out.println("[Error:" + lexeme + "|" + currentToken.getLineNumber() + "]");
    }
}
