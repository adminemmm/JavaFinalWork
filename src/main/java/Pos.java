package main;
public class Pos {
    private int x;
    private int y;
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Pos(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Pos(Pos p){
        this.x = p.getX();
        this.y = p.getY();
    }
    public void change_x(int x){
        this.x = x;
    }
    public void change_y(int y){
        this.y = y;
    }
}
