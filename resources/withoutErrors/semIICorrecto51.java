//[SinErrores]
class A {
     int num = 5;
     int metodo(){
        return 3;
    }
     void otroMetodo(){
        var x = num;
        var z = this.num;
        var t = metodo();
        var y = this.metodo();
    }
}
