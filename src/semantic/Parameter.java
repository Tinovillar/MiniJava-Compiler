package semantic;

public class Parameter {
    private String name;
    private Type type;

    public Parameter(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public void isWellDeclared() {}
    public void consolidate() {}
    public String getName() {
        return name;
    }
    public Type getType() {
        return type;
    }
}
