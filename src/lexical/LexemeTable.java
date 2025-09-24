package lexical;

import java.util.HashMap;
import java.util.Map;

public class LexemeTable {
    private static final Map<String, lexID> keywords = new HashMap<>();

    // Constructor to initialize the table
    static {
        keywords.put("class", lexID.kw_class);
        keywords.put("extends", lexID.kw_extends);
        keywords.put("public", lexID.kw_public);
        keywords.put("static", lexID.kw_static);
        keywords.put("void", lexID.kw_void);
        keywords.put("boolean", lexID.kw_boolean);
        keywords.put("char", lexID.kw_char);
        keywords.put("int", lexID.kw_int);
        keywords.put("abstract", lexID.kw_abstract);
        keywords.put("final", lexID.kw_final);
        keywords.put("if", lexID.kw_if);
        keywords.put("else", lexID.kw_else);
        keywords.put("while", lexID.kw_while);
        keywords.put("for", lexID.kw_for);
        keywords.put("return", lexID.kw_return);
        keywords.put("var", lexID.kw_var);
        keywords.put("this", lexID.kw_this);
        keywords.put("new", lexID.kw_new);
        keywords.put("null", lexID.kw_null);
        keywords.put("true", lexID.kw_true);
        keywords.put("false", lexID.kw_false);
    }

    public static lexID getToken(String lexeme) {
        return keywords.getOrDefault(lexeme, lexID.id_met_or_var);
    }
}
