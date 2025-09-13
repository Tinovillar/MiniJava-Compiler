package syntactic;

import java.util.*;

public class Primeros {
    private static final Map<String, Set<String>> primeros = new HashMap<>();
    static {
        primeros.put("MetodoEncadenado", Set.of("."));
        primeros.put("VarEncadenada", Set.of("."));
        primeros.put("ListaExpsResto", Set.of(",", "€"));
        primeros.put("ArgsActuales", Set.of("("));
        primeros.put("ArgsFormales", Set.of("("));
        primeros.put("ExpresionParentizada", Set.of("("));
        primeros.put("LlamadaConstructor", Set.of("new"));
        primeros.put("LlamadaMetodoEstatico", Set.of("idClase"));
        primeros.put("LlamadaMetodo", Set.of("idMetVar"));
        primeros.put("AccesoVar", Set.of("idMetVar"));
        primeros.put("VarLocal", Set.of("var"));
        primeros.put("Return", Set.of("return"));
        primeros.put("If", Set.of("if"));
        primeros.put("Else", Set.of("else"));
        primeros.put("While", Set.of("while"));
        primeros.put("Bloque", Set.of("{"));
        primeros.put("BloqueOpcional", Set.of("{", "€"));
        primeros.put("OperadorAsignacion", Set.of("="));
        primeros.put("OperadorUnario", Set.of("+", "-", "++", "--", "!"));
        primeros.put("OperadorBinario", Set.of("||", "&&", "==", "!=", "<", ">", "<=", ">=", "+", "-", "*", "/", "%"));
        primeros.put("Primitivo", Set.of("true", "false", "intLiteral", "charLiteral", "null"));
        primeros.put("ModificadorMiembro", Set.of("abstract", "static", "final"));
        primeros.put("ModificadorOpcional", union(
                Set.of("€"),
                primeros.get("ModificadorMiembro")
        ));
        primeros.put("HerenciaOpcional", Set.of("extends", "€"));
        primeros.put("DeclaracionVariable", Set.of("=", "€"));
        primeros.put("TipoPrimitivo", Set.of("boolean", "char", "int"));
        primeros.put("Tipo", union(
                Set.of("idClase"),
                primeros.get("TipoPrimitivo")
        ));
        primeros.put("TipoMetodo", union(
                Set.of("void"),
                primeros.get("Tipo")
        ));
        primeros.put("ArgFormal", primeros.get("Tipo"));
        primeros.put("ListaArgsFormales", primeros.get("ArgFormal"));
        primeros.put("ListaArgsFormalesOpcional", union(
                Set.of("€"),
                primeros.get("ListaArgsFormales")
        ));
        primeros.put("ListaArgsFormalesResto", Set.of(",", "€"));
        primeros.put("Constructor", Set.of("public"));
        primeros.put("Clase", union(
                Set.of("class"),
                primeros.get("ModificadorOpcional")
        ));
        primeros.put("ListaClases", union(
                Set.of("€"),
                primeros.get("Clase")
        ));
        primeros.put("Inicial", union(
                Set.of("EOF"),
                primeros.get("Clase")
        ));
        primeros.put("Primario", union(
                Set.of("this", "stringLiteral"),
                primeros.get("AccesoVar"),
                primeros.get("LlamadaConstructor"),
                primeros.get("LlamadaMetodo"),
                primeros.get("LlamadaMetodoEstatico"),
                primeros.get("ExpresionParentizada")
        ));
        primeros.put("Referencia", primeros.get("Primario"));
        primeros.put("ReferenciaResto", union(
                primeros.get("VarEncadenada"),
                primeros.get("MetodoEncadenado"),
                Set.of("€")
        ));
        primeros.put("Operando", union(
                primeros.get("Primitivo"),
                primeros.get("Referencia")
        ));
        primeros.put("ExpresionBasica", union(
                primeros.get("OperadorUnario"),
                primeros.get("Operando")
        ));
        primeros.put("ExpresionCompuestaResto", union(
                primeros.get("ExpresionBasica"),
                Set.of("€")
        ));
        primeros.put("ExpresionCompuesta", union(
                primeros.get("ExpresionBasica")
        ));
        primeros.put("ExpresionResto", union(
                primeros.get("OperadorAsignacion")
        ));
        primeros.put("Expresion", union(
                primeros.get("ExpresionCompuesta")
        ));
        primeros.put("ExpresionOpcional", union(
                primeros.get("Expresion"),
                Set.of("€")
        ));
        primeros.put("Sentencia", union(
                Set.of(";"),
                primeros.get("VarLocal"),
                primeros.get("Return"),
                primeros.get("If"),
                primeros.get("While"),
                primeros.get("Bloque"),
                primeros.get("Expresion")
        ));
        primeros.put("ListaSentencias", union(
                primeros.get("Sentencia"),
                Set.of("€")
        ));
        primeros.put("DeclaracionMetodo", primeros.get("ArgsFormales"));
        primeros.put("MetodoVariable", union(
                primeros.get("DeclaracionMetodo"),
                primeros.get("DeclaracionVariable")
        ));
        primeros.put("Miembro", union(
                primeros.get("Tipo"),
                primeros.get("ModificadorMiembro"),
                primeros.get("Constructor"),
                Set.of("void")
        ));
        primeros.put("ListaMiembros", primeros.get("Miembro"));
        primeros.put("ListaExps", primeros.get("Expresion"));
        primeros.put("ListaExpsOpcional", union(
                primeros.get("ListaExps"),
                Set.of("€")
        ));
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
}

