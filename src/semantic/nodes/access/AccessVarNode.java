package semantic.nodes.access;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.model.Attribute;
import semantic.model.Parameter;
import semantic.nodes.sentence.LocalVarNode;
import semantic.type.Type;

public class AccessVarNode extends AccessNode {
    private Token id;
    private LocalVarNode localVar;
    private Parameter param;
    private Attribute attr;

    public AccessVarNode(Token id) {
        this.id = id;
    }

    public Type check() throws SemanticException {
        Type type;
        attr = Main.ST.getCurrentClass().getAttributes().get(id.getLexeme());
        param = Main.ST.getCurrentMethod().getParameters().get(id.getLexeme());
        localVar = Main.ST.getCurrentBlock().getLocalVar(id.getLexeme());
        if(param != null) {
            type = param.getType();
        } else if(localVar != null) {
            type = localVar.getType();
        } else if(attr != null) {
            if(Main.ST.getCurrentMethod().hasModifier(lexID.kw_static))
                throw new SemanticException(id, "Se intenta usar un atributo en un metodo estatico");
            type = attr.getType();
        } else {
            throw new SemanticException(id, "Variable no declarada");
        }
        if(chained != null) {
            type = chained.check(type);
        }
        return type;
    }
    public boolean isAssignable() {
        if(chained == null) return true;
        return chained.isAssignable();
    }
    public boolean hasSideEffect() {
        if(chained == null) return false;
        else return chained.hasSideEffects();
    }
    public void generate() {
        if(attr != null && param == null && localVar == null) {
            Main.ST.add("LOAD 3; Apila this");
            if (!isLeftSide || chained != null) {
                Main.ST.add("LOADREF " + attr.getOffset() + " ; Apila el valor de la variable local en el tope de la pila");
            } else {// Si es lado izquierdo o si tiene un encadenado tengo que poner la expresion en el tope de la pila
                Main.ST.add("SWAP ; Pone el valor de la expresion en el tope de la pila");
                Main.ST.add("STOREREF " + attr.getOffset() + " ; Guarda el valor de la expresion en el atributo " + attr.getName());
            }
        } else if(param != null) {
            if(!isLeftSide || chained != null){
                Main.ST.add("LOAD " + param.getOffset() + " ; Apila al valor del parametro ");
            }else{
                Main.ST.add("STORE " + param.getOffset() + " ; Guardo el valor de la expresion en el parametro");
            }
        } else { // Si es una variable local
            if(!isLeftSide || chained != null) {
                Main.ST.add("LOAD " + localVar.getOffset() + " ; Apila al valor de la variable local " + localVar.getName());
            } else {
                Main.ST.add("STORE " + localVar.getOffset() + " ; Guardo el valor de la expresion en la variable local " + localVar.getName());
            }
        }

        if(chained != null) {
            if(isLeftSide)
                chained.setIsLeftSide();
            chained.generate();
        }
    }
    public LocalVarNode getLocalVar() {
        return localVar;
    }
}
