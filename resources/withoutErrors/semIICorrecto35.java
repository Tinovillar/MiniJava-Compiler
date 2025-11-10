//[SinErrores]

class A {
    B prueba(){
         return new B();
     }
     void otroMetodo(){
         B.metodoB();
     }
}
class B{
    static void metodoB(){}
}



class Init{
    static void main()
    { }
}


