package semantic;

import exceptions.SemanticException;
import lexical.Token;

public interface Type {
    String getName();
    void checkType() throws SemanticException;
    boolean equals(Type toCompare);
}
