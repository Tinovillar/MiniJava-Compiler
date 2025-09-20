package lexical;

import java.util.HashMap;
import java.util.Map;

public class LexemeTable {
    private static final Map<String, ID> keywords = new HashMap<>();

    // Constructor to initialize the table
    static {
        keywords.put("class", ID.kw_class);
        keywords.put("extends", ID.kw_extends);
        keywords.put("public", ID.kw_public);
        keywords.put("static", ID.kw_static);
        keywords.put("void", ID.kw_void);
        keywords.put("boolean", ID.kw_boolean);
        keywords.put("char", ID.kw_char);
        keywords.put("int", ID.kw_int);
        keywords.put("abstract", ID.kw_abstract);
        keywords.put("final", ID.kw_final);
        keywords.put("if", ID.kw_if);
        keywords.put("else", ID.kw_else);
        keywords.put("while", ID.kw_while);
        keywords.put("for", ID.kw_for);
        keywords.put("return", ID.kw_return);
        keywords.put("var", ID.kw_var);
        keywords.put("this", ID.kw_this);
        keywords.put("new", ID.kw_new);
        keywords.put("null", ID.kw_null);
        keywords.put("true", ID.kw_true);
        keywords.put("false", ID.kw_false);
    }

    public static ID getToken(String lexeme) {
        return keywords.getOrDefault(lexeme, ID.id_met_or_var);
    }
}
