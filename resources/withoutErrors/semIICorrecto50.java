//[SinErrores]
class A {
    int att;
}
class B extends A{

}
class C {
    void metodo(){
        var b = new B();
        var x = b.att;
    }
}