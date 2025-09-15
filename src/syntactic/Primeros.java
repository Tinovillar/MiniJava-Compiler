package syntactic;

import lexical.ID;

import java.util.*;

public class Primeros {
    private static final Map<String, Set<ID>> primeros = new HashMap<>();
    static {
        primeros.put("MetodoEncadenado", Set.of(ID.p_dot));
        primeros.put("VarEncadenada", Set.of(ID.p_dot));
        primeros.put("ListaExpsResto", Set.of(ID.p_comma)); // "€"
        primeros.put("ArgsActuales", Set.of(ID.p_o_parenthesis));
        primeros.put("ArgsFormales", Set.of(ID.p_o_parenthesis));
        primeros.put("ExpresionParentizada", Set.of(ID.p_o_parenthesis));
        primeros.put("LlamadaConstructor", Set.of(ID.kw_new));
        primeros.put("LlamadaMetodoEstatico", Set.of(ID.id_class));
        primeros.put("LlamadaMetodo", Set.of(ID.id_met_or_var));
        primeros.put("AccesoVar", Set.of(ID.id_met_or_var));
        primeros.put("VarLocal", Set.of(ID.kw_var));
        primeros.put("Return", Set.of(ID.kw_return));
        primeros.put("If", Set.of(ID.kw_if));
        primeros.put("Else", Set.of(ID.kw_else));
        primeros.put("While", Set.of(ID.kw_while));
        primeros.put("Bloque", Set.of(ID.p_o_bracket1));
        primeros.put("BloqueOpcional", Set.of(ID.p_o_bracket1)); // "€"
        primeros.put("OperadorAsignacion", Set.of(ID.op_equal));
        primeros.put("OperadorUnario", Set.of(ID.op_plus, ID.op_minus, ID.op_plus_plus, ID.op_minus_minus, ID.op_not));
        primeros.put("OperadorBinario", Set.of(ID.op_or, ID.op_and, ID.op_equal_equal, ID.op_not_equal, ID.op_less_than, ID.op_greater_than, ID.op_less_than_equal, ID.op_greater_than_equal, ID.op_plus, ID.op_minus, ID.op_multiplication, ID.op_division, ID.op_mod));
        primeros.put("Primitivo", Set.of(ID.kw_true, ID.kw_false, ID.literal_integer, ID.literal_char, ID.kw_null));
        primeros.put("ModificadorMiembro", Set.of(ID.kw_abstract, ID.kw_static, ID.kw_final));
        primeros.put("ModificadorOpcional", union(primeros.get("ModificadorMiembro"))); // "€"
        primeros.put("HerenciaOpcional", Set.of(ID.kw_extends)); // "€"
        primeros.put("DeclaracionVariable", Set.of(ID.op_equal)); // "€"
        primeros.put("TipoPrimitivo", Set.of(ID.kw_boolean, ID.kw_char, ID.kw_int));
        primeros.put("Tipo", union(
                Set.of(ID.id_class),
                primeros.get("TipoPrimitivo")
        ));
        primeros.put("TipoMetodo", union(
                Set.of(ID.kw_void),
                primeros.get("Tipo")
        ));
        primeros.put("ArgFormal", primeros.get("Tipo"));
        primeros.put("ListaArgsFormales", primeros.get("ArgFormal"));
        primeros.put("ListaArgsFormalesOpcional", union(primeros.get("ListaArgsFormales"))); // "€"
        primeros.put("ListaArgsFormalesResto", Set.of(ID.p_comma)); // "€"
        primeros.put("Constructor", Set.of(ID.kw_public));
        primeros.put("Clase", union(
                Set.of(ID.kw_class),
                primeros.get("ModificadorOpcional")
        ));
        primeros.put("ListaClases", union(primeros.get("Clase"))); // "€"
        primeros.put("Inicial", union(
                Set.of(ID.EOF),
                primeros.get("Clase")
        ));
        primeros.put("Primario", union(
                Set.of(ID.kw_this, ID.literal_string),
                primeros.get("AccesoVar"),
                primeros.get("LlamadaConstructor"),
                primeros.get("LlamadaMetodo"),
                primeros.get("LlamadaMetodoEstatico"),
                primeros.get("ExpresionParentizada")
        ));
        primeros.put("Referencia", primeros.get("Primario"));
        primeros.put("ReferenciaResto", union( // "€"
                primeros.get("VarEncadenada"),
                primeros.get("MetodoEncadenado")
        ));
        primeros.put("Operando", union(
                primeros.get("Primitivo"),
                primeros.get("Referencia")
        ));
        primeros.put("ExpresionBasica", union(
                primeros.get("OperadorUnario"),
                primeros.get("Operando")
        ));
        primeros.put("ExpresionCompuestaResto", union(primeros.get("ExpresionBasica")));
        primeros.put("ExpresionCompuesta", union(primeros.get("ExpresionBasica")));
        primeros.put("ExpresionResto", union(primeros.get("OperadorAsignacion")));
        primeros.put("Expresion", union(primeros.get("ExpresionCompuesta")));
        primeros.put("ExpresionOpcional", union(primeros.get("Expresion"))); // "€"
        primeros.put("Sentencia", union(
                Set.of(ID.p_semicolon),
                primeros.get("VarLocal"),
                primeros.get("Return"),
                primeros.get("If"),
                primeros.get("While"),
                primeros.get("Bloque"),
                primeros.get("Expresion")
        ));
        primeros.put("ListaSentencias", union(primeros.get("Sentencia"))); // "€"
        primeros.put("DeclaracionMetodo", primeros.get("ArgsFormales"));
        primeros.put("MetodoVariable", union(
                primeros.get("DeclaracionMetodo"),
                primeros.get("DeclaracionVariable")
        ));
        primeros.put("Miembro", union(
                primeros.get("Tipo"),
                primeros.get("ModificadorMiembro"),
                primeros.get("Constructor"),
                Set.of(ID.kw_void)
        ));
        primeros.put("ListaMiembros", primeros.get("Miembro"));
        primeros.put("ListaExps", primeros.get("Expresion"));
        primeros.put("ListaExpsOpcional", union(primeros.get("ListaExps"))); // "€"
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
    public static boolean isFirstOf(String currentState, ID tokenID) {
        return primeros.get(currentState).contains(tokenID);
    }
}

