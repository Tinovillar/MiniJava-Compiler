package syntactic;

import lexical.lexID;

import java.util.*;

public class Primeros {
    private static final Map<synID, Set<lexID>> primeros = new HashMap<>();
    static {
        primeros.put(synID.listaExpsResto, Set.of(lexID.p_comma)); // "€"
        primeros.put(synID.argsActuales, Set.of(lexID.p_o_parenthesis));
        primeros.put(synID.argsFormales, Set.of(lexID.p_o_parenthesis));
        primeros.put(synID.varMetEncadenada, primeros.get(synID.argsActuales));
        primeros.put(synID.expresionParentizada, Set.of(lexID.p_o_parenthesis));
        primeros.put(synID.llamadaConstructor, Set.of(lexID.kw_new));
        primeros.put(synID.llamadaMetodoEstatico, Set.of(lexID.id_class));
        primeros.put(synID.llamadaMetOrVar, Set.of(lexID.id_met_or_var));
        primeros.put(synID.varLocal, Set.of(lexID.kw_var));
        primeros.put(synID.return_, Set.of(lexID.kw_return));
        primeros.put(synID.if_, Set.of(lexID.kw_if));
        primeros.put(synID.else_, Set.of(lexID.kw_else));
        primeros.put(synID.while_, Set.of(lexID.kw_while));
        primeros.put(synID.bloque, Set.of(lexID.p_o_bracket1));
        primeros.put(synID.bloqueOpcional, Set.of(lexID.p_o_bracket1)); // "€"
        primeros.put(synID.operadorUnario, Set.of(lexID.op_plus, lexID.op_minus, lexID.op_plus_plus, lexID.op_minus_minus, lexID.op_not));
        primeros.put(synID.operadorBinario, Set.of(lexID.op_or, lexID.op_and, lexID.op_equal_equal, lexID.op_not_equal, lexID.op_less_than, lexID.op_greater_than, lexID.op_less_than_equal, lexID.op_greater_than_equal, lexID.op_plus, lexID.op_minus, lexID.op_multiplication, lexID.op_division, lexID.op_mod));
        primeros.put(synID.primitivo, Set.of(lexID.kw_true, lexID.kw_false, lexID.literal_integer, lexID.literal_char, lexID.kw_null));
        primeros.put(synID.modificadorMiembro, Set.of(lexID.kw_abstract, lexID.kw_static, lexID.kw_final));
        primeros.put(synID.modificadorOpcional, union(primeros.get(synID.modificadorMiembro))); // "€"
        primeros.put(synID.herenciaOpcional, Set.of(lexID.kw_extends)); // "€"
        primeros.put(synID.declaracionVariable, Set.of(lexID.op_equal, lexID.p_semicolon)); // "€"
        primeros.put(synID.tipoPrimitivo, Set.of(lexID.kw_boolean, lexID.kw_char, lexID.kw_int));
        primeros.put(synID.tipo, union(
                Set.of(lexID.id_class),
                primeros.get(synID.tipoPrimitivo)
        ));
        primeros.put(synID.tipoMetodo, union(
                Set.of(lexID.kw_void),
                primeros.get(synID.tipo)
        ));
        primeros.put(synID.argFormal, primeros.get(synID.tipo));
        primeros.put(synID.listaArgsFormales, primeros.get(synID.argFormal));
        primeros.put(synID.listaArgsFormalesOpcional, union(primeros.get(synID.listaArgsFormales))); // "€"
        primeros.put(synID.listaArgsFormalesResto, Set.of(lexID.p_comma)); // "€"
        primeros.put(synID.constructor, Set.of(lexID.kw_public));
        primeros.put(synID.clase, union(
                Set.of(lexID.kw_class),
                primeros.get(synID.modificadorOpcional)
        ));
        primeros.put(synID.listaClases, union(primeros.get(synID.clase))); // "€"
        primeros.put(synID.inicial, union(
                Set.of(lexID.EOF),
                primeros.get(synID.clase)
        ));
        primeros.put(synID.primario, union(
                Set.of(lexID.kw_this, lexID.literal_string),
                primeros.get(synID.llamadaMetOrVar),
                primeros.get(synID.llamadaConstructor),
                primeros.get(synID.llamadaMetodoEstatico),
                primeros.get(synID.expresionParentizada)
        ));
        primeros.put(synID.referencia, primeros.get(synID.primario));
        primeros.put(synID.referenciaResto, union( // "€"
                Set.of(lexID.p_dot)
        ));
        primeros.put(synID.operando, union(
                primeros.get(synID.primitivo),
                primeros.get(synID.referencia)
        ));
        primeros.put(synID.expresionBasica, union(
                primeros.get(synID.operadorUnario),
                primeros.get(synID.operando)
        ));
        primeros.put(synID.expresionCompuestaResto, union(
                Set.of(lexID.p_question_mark),
                primeros.get(synID.operadorBinario)
        )); // "€"
        primeros.put(synID.expresionCompuesta, union(
                primeros.get(synID.expresionBasica)
        ));
        primeros.put(synID.expresionResto, Set.of(lexID.op_equal));
        primeros.put(synID.expresion, union(primeros.get(synID.expresionCompuesta)));
        primeros.put(synID.expresionOpcional, union(primeros.get(synID.expresion))); // "€"
        primeros.put(synID.forIterador, Set.of(lexID.p_colon));
        primeros.put(synID.forExpresion, Set.of(lexID.p_semicolon));
        primeros.put(synID.forInstancia, union(
                primeros.get(synID.forIterador),
                primeros.get(synID.forExpresion)
        ));
        primeros.put(synID.forArgs, union(
                Set.of(lexID.kw_var),
                primeros.get(synID.expresion)
        ));
        primeros.put(synID.for_, Set.of(lexID.kw_for));
        primeros.put(synID.sentencia, union(
                Set.of(lexID.p_semicolon),
                primeros.get(synID.varLocal),
                primeros.get(synID.return_),
                primeros.get(synID.if_),
                primeros.get(synID.while_),
                primeros.get(synID.for_),
                primeros.get(synID.bloque),
                primeros.get(synID.expresion)
        ));
        primeros.put(synID.listaSentencias, union(primeros.get(synID.sentencia))); // "€"
        primeros.put(synID.declaracionMetodo, primeros.get(synID.argsFormales));
        primeros.put(synID.metodoVariable, union(
                primeros.get(synID.declaracionMetodo),
                primeros.get(synID.declaracionVariable)
        ));
        primeros.put(synID.miembro, union(
                primeros.get(synID.tipo),
                primeros.get(synID.modificadorMiembro),
                primeros.get(synID.constructor),
                Set.of(lexID.kw_void)
        ));
        primeros.put(synID.listaMiembros, primeros.get(synID.miembro));
        primeros.put(synID.listaExps, primeros.get(synID.expresion));
        primeros.put(synID.listaExpsOpcional, union(primeros.get(synID.listaExps))); // "€"
    }

    @SafeVarargs
    private static <T> Set<T> union(Set<T>... sets) {
        Set<T> result = new HashSet<>();
        for (Set<T> s : sets) {
            if (s != null) {
                result.addAll(s);
            }
        }
        return result;
    }
    public static boolean isFirstOf(synID currentState, lexID tokenLexID) {
        return primeros.get(currentState).contains(tokenLexID);
    }
    public static Set<lexID> getFirsts(synID currentState) {
        return primeros.get(currentState);
    }
}

