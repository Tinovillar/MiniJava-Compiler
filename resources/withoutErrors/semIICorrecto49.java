//[SinErrores]
class A {
    void metodo(){

    }
}
class B extends A{
    void metodo(){

    }
}
class C {
    void metodo(){
        var b = new B();
        b.metodo();
    }
}