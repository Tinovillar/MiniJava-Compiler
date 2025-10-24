package semantic.type;

import exceptions.SemanticException;
import lexical.lexID;

public interface Type {
    String getName();
    void checkType() throws SemanticException;
    boolean equals(Type toCompare);
    boolean isBoolean();
}
