package semantic.type;

import exceptions.SemanticException;

public interface Type {
    String getName();
    void checkType() throws SemanticException;
    boolean equals(Type toCompare);
}
