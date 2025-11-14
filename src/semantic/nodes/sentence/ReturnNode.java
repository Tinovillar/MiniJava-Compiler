package semantic.nodes.sentence;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.model.Method;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

public class ReturnNode extends SentenceNode {
    private ExpressionNode return_;
    private Token returnToken;

    private Method method;

    public ReturnNode(Type type, ExpressionNode expression, Token returnToken) {
        this.return_ = expression;
        this.returnToken = returnToken;
    }

    public void check() throws SemanticException {
        method = Main.ST.getCurrentMethod();

        if(method.getReturnType() != null) {
            if(return_ != null && methodIsVoid()) { // return something when method has void return type
                if(return_.check() != null)
                    throw new SemanticException(returnToken, "El metodo es void y se esta retornando algo");
            }
            if(return_.check() == null && !methodIsVoid()) { // return is empty and method has return type
                throw new SemanticException(returnToken, "Tiene tipo de retorno pero no retorna nada");
            }
            if(return_ != null && return_.check() != null && !method.getReturnType().conformsTo(return_.check())) { // non compatible types
                throw new SemanticException(returnToken, "Los tipos de retorno no coinciden");
            }
        } else if(return_ != null) {
            throw new SemanticException(returnToken, "En un constructor no puede haber una sentencia return");
        }
    }
    private boolean methodIsVoid() {
        return method.getReturnType().getName().equals("void");
    }
    public void generate() {
        // TODO
    }
}
