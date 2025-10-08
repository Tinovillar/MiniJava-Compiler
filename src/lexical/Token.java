package lexical;

public class Token {
    private lexID lexId;
    private String lexeme;
    private int lineNumber;

    public Token(lexID lexId, String lexeme, int lineNumber) {
        this.lexId = lexId;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }

    public lexID getId() {
        return lexId;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        if(lexId == null) return "> ( Blank Token )";
        return " > ("+ this.lexId.toString() +", " + this.lexeme + ", " + this.lineNumber + ")";
    }
    public boolean equals(Token toCompare) {
        return toCompare.getId().equals(lexId) && toCompare.getLexeme().equals(lexeme);
    }

    public static Token blankToken() {
        return new Token(null, "", -1);
    }
}
