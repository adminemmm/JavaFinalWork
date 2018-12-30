package main;
public class SpecialMath{
    public static int bigger(int a, int b){
        if(a > b)
            return a;
        else
            return b;
    }
    public static int abs(int a){
        if(a < 0){
            return -a;
        }
        return a;
    }
}
