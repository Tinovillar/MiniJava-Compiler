package semantic;

import lexical.Token;

import java.util.HashSet;

public class ConcreteClass {
    Token token;
    Token inheritance;
    HashSet<Constructor> constructors;
    HashSet<Attribute> attributes;
    HashSet<Method> methods;

    public ConcreteClass(Token token) {
        this.token = token;
        this.constructors = new HashSet<>();
        this.attributes = new HashSet<>();
        this.methods = new HashSet<>();
    }

    public boolean isWellDeclared() {
        return true;
    }
    public void consolidate() {}

    public String getName() {
        return token.getLexeme();
    }
    public void addInheritance(Token inheritance) {
        this.inheritance = inheritance;
    }
    public void addConstructor(Constructor constructor) {
        constructors.add(constructor);
    }
    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }
    public void addMethod(Method method) {
        methods.add(method);
    }
}
