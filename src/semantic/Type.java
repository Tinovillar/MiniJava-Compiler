package semantic;

import exceptions.SemanticException;

public interface Type {
    String getName();
    void checkType() throws SemanticException;
}
