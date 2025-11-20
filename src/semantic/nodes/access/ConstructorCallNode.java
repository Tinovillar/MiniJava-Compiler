package semantic.nodes.access;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.model.ConcreteClass;
import semantic.model.Constructor;
import semantic.model.Parameter;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.ReferenceType;
import semantic.type.Type;

import java.util.List;

public class ConstructorCallNode extends AccessNode {
    private Token id;
    private List<ExpressionNode> args;
    private ConcreteClass owner;
    private Constructor constructor;

    public ConstructorCallNode(Token id, List<ExpressionNode> args) {
        this.id = id;
        this.args = args;
    }

    public Type check() throws SemanticException {
        owner = Main.ST.getClassOrNull(id.getLexeme());
        if(owner == null) {
            throw new SemanticException(id, "No existe la clase");
        }
        constructor = owner.getConstructor();
//        if(!constructor.hasModifier(lexID.kw_public)) {
//            throw new SemanticException(constructor.getToken(), "No existe el constructor o no es publico");
//        }
        if(constructor != null) {
            if(args.size() != constructor.getParameters().size()) {
                throw new SemanticException(id, "Sobran o faltan parametros");
            }
            List<Parameter> params = constructor.getParameters().values().stream().toList();
            int index = 0;
            for(ExpressionNode arg : args) {
                Type type = arg.check();
                if(!params.get(index).getType().conformsTo(type)) {
                    throw new SemanticException(id, "No coincide ni el orden ni el tipo de los parametros");
                }
                index++;
            }
        }
        Type toReturn = new ReferenceType(id);
        if(chained != null)
            toReturn = chained.check(toReturn);
        return toReturn;
    }
    public boolean hasSideEffect() {
        if(chained == null) return true;
        else return chained.hasSideEffects();
    }
    public void generate() {
        Main.ST.add("RMEM 1 ; Reservo puntero malloc");
        Main.ST.add("PUSH "+ owner.getCirOffset() +" ; Cantidad de atributos + VT Ref");
        Main.ST.add("PUSH simple_malloc ; Push direccion de metodo para reservar heap");
        Main.ST.add("CALL ; Me retorna una referencia del CIR");
        Main.ST.add("DUP ; Duplico la referencia al objeto");
        Main.ST.add("PUSH "+ owner.getVtLabel() +" ; Etiqueta de la VT");
        Main.ST.add("STOREREF 0 ; Guardo VT en CIR, consumiendo una de las referencias");
        Main.ST.add("DUP ; Duplico this, para metodo de constructor");
        // Ejecuta metodo de constructor (ya esta la referencia al CIR en el retorno)
        for (ExpressionNode arg : args) {
            arg.generate();
            Main.ST.add("SWAP ; Muevo this"); // Tiene this
        }
        Main.ST.add("PUSH " + constructor.getLabel() + " ; Direccion del constructor");
        Main.ST.add("CALL ; Llama al metodo");

        if (chained != null) {
            chained.generate();
        }
    }
    public boolean isAssignable() {
        if(chained != null) {
            return chained.isAssignable();
        }
        return false;
    }
}
