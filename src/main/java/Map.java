package main;
import java.util.ArrayList;

class Body{
    ArrayList<Creature> bodies = new ArrayList<Creature>();
}
public class Map {
    final static int width = 10;
    final static int length = 10;
    final static int north = 0;
    final static int south = 1;
    final static int west = 2;
    final static int east = 3;
    final static int northeast = 4;
    final static int northwest = 5;
    final static int southwest = 6;
    final static int southeast = 7;
    private static boolean [][] empty = new boolean[width][length];//true means empty
    private static Creature[][] hold = new Creature[width][length];
    private static Body[][] body = new Body[width][length];
    public static void init(){
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                empty[i][j] = true;
            }
        }
    }
    public static void print(){
        System.out.println();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++){
                if(!empty[i][j]){
                    System.out.print(hold[i][j].printIndex());
                }
                else{
                    System.out.print(" ");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("------------------------------------------");
    }
    public static void setMapPos(boolean b, Pos p){
        empty[p.getX()][p.getY()] = b;
    }
    public static void setMapCreature(Creature c, Pos p){
        setMapPos(false, p);
        hold[p.getX()][p.getY()] = c;
    }
    public static void creatureMove(Creature c, Pos before, Pos after){
        setMapPos(true, before);
        setMapPos(false, after);
        setMapCreature(c, after);
    }
    public static boolean isEmpty(Pos p){
        return empty[p.getX()][p.getY()];
    }
    public static Creature getCreature(Pos p){
        return hold[p.getX()][p.getY()];
    }
    public static void beKilled(Pos p){
        empty[p.getX()][p.getY()] = true;
    }
}
