package main;
public abstract class Creature {
    protected int id;
    protected String printIndex;
    protected Pos p;
    protected boolean team;//to decide which team it belongs to
    protected boolean isAlive = true;// true means it's alive
    //get info
    public int id(){return id;}
    public boolean team(){return team;}
    public String printIndex(){return printIndex;}
    public Pos p(){return p;}
    public boolean isAlive(){return isAlive;}
    //set info
    public void setId(int id){
        this.id = id;
    }
    public void setP(Pos p){
        this.p = p;
    }
    public void setPrintIndex(String s){this.printIndex = s;}
    public void setTeam(boolean b){this.team = b;}
    public void setIsAlive(boolean b){this.isAlive = b;}
    public void setAllInfo(int id, Pos p, String s, boolean team, boolean isAlive){
        this.id = id;
        this.p = p;
        this.printIndex = s;
        this.team = team;
        this.isAlive = isAlive;
    }
    Creature(int id, Pos p, String s, boolean team, boolean isAlive){
        this.id = id;
        this.p = p;
        this.printIndex = s;
        this.team = team;
        this.isAlive = isAlive;
    }
    Creature(){
    }
    public int findNearestEnemy(){
        //int range = 1;
        int wid = Map.width - 1;
        int len = Map.length - 1;
        for(int i = 0; i < Map.width; i++){
            for(int j = 0; j < Map.length; j++){
                if(!Map.isEmpty(new Pos(i, j))){
                    if(Map.getCreature(new Pos(i, j)).team() != team) {
                        int deltaWid = i - p.getX();
                        int deltaLen = j - p.getY();
                        //System.out.println(Thread.currentThread().getName() + " " + wid + " " + len + " " +  deltaWid + " " + deltaLen);
                        //if (SpecialMath.bigger(SpecialMath.abs(deltaLen), SpecialMath.abs(deltaWid)) <= SpecialMath.bigger(SpecialMath.abs(wid), SpecialMath.abs(len))){
                        if(SpecialMath.abs(deltaLen) + SpecialMath.abs(deltaWid) < SpecialMath.abs(wid) + SpecialMath.abs(len)){
                            wid = deltaWid;
                            len = deltaLen;
                        }
                    }
                }
            }
        }
        //System.out.println(Thread.currentThread().getName() + " " + wid + " " + len);
        if(SpecialMath.abs(wid) > SpecialMath.abs(len)){
            if(wid < 0)return 0;
            else return 1;
        }
        else{
            if(len < 0)return 2;
            else return 3;
        }
    }
    //activity
    public synchronized boolean move(){
        int dest = findNearestEnemy();
        //System.out.println(dest);
        int myPosX = p.getX();
        int myPosY = p.getY();
        switch(dest){
            case 0:if(myPosX >= 1)if(Map.isEmpty(new Pos(myPosX - 1, myPosY)))p = new Pos(myPosX - 1, myPosY);break;
            case 1:if(myPosX < Map.width - 1)if(Map.isEmpty(new Pos(myPosX + 1, myPosY)))p = new Pos(myPosX + 1, myPosY);break;
            case 2:if(myPosY >= 1)if(Map.isEmpty(new Pos(myPosX, myPosY - 1)))p = new Pos(myPosX, myPosY - 1);break;
            case 3:if(myPosY < Map.length - 1)if(Map.isEmpty(new Pos(myPosX, myPosY + 1)))p = new Pos(myPosX, myPosY + 1);break;
            default:break;
        }
        if(p.getX() == myPosX && p.getY() == myPosY)
            return false;
        else {
            for(int i = 0; i < 16; i++){
                if(Main.thread[i].getCreature().id() == id){
                    Main.thread[i].changePos(p);
                }
            }
            //System.out.println(Thread.currentThread().getName() + "(" + myPosX+","+myPosY+")(" + p.getX() + "," + p.getY() + ")");
            Map.creatureMove(this, new Pos(myPosX, myPosY), p);
            IO.output(Main.turn, 1, p);
            //System.out.println("(" + myPosX+","+myPosY+")(" + p.getX() + "," + p.getY() + ")");
            Map.print();
            return true;
        }
    }
    public synchronized void stop(){
        //System.out.println(Thread.currentThread().getName() + " stop");
    }
    public synchronized boolean battle() {
        //System.out.println(Thread.currentThread().getName()+ " " + "In battle" + " " + Main.turn);
        boolean result = false;
        int leftNearY = this.p.getY() - 1;
        int rightNearY = this.p.getY() + 1;
        int upNearX = this.p.getX() - 1;
        int downNearX = this.p.getX() + 1;
        if (leftNearY < 0)
            leftNearY = 0;
        if (rightNearY >= Map.length)
            rightNearY = Map.length - 1;
        if (upNearX < 0)
            upNearX = 0;
        if (downNearX >= Map.width)
            downNearX = Map.width - 1;
        //System.out.println(p.getX() + " " + p.getY() + " " + leftNearY + " " + rightNearY + " " + upNearX + " " + downNearX);
        for (int i = upNearX; i <= downNearX; i++) {
            for (int j = leftNearY; j <= rightNearY; j++) {
                if (i != this.p.getX() || j != this.p.getY()) {
                    if (!Map.isEmpty(new Pos(i, j))) {
                        if(Map.getCreature(new Pos(i, j)).team() != team) {
                            result = true;
                            int ran = (int)(Math.random() * 2);
                            if(ran == 0) {//kill others
                                Map.getCreature(new Pos(i, j)).setIsAlive(false);
                                Map.beKilled(new Pos(i, j));
                                IO.output(Main.turn, 0,  Map.getCreature(new Pos(i, j)).id(),1);
                                for (int e = 0; e < 16; e++) {
                                    if (Main.thread[e].getCreature().id() == Map.getCreature(new Pos(i, j)).id()) {
                                        Main.thread[e].beKilled();
                                    }
                                }

                                System.out.println(printIndex + " killed " + Map.getCreature(new Pos(i, j)).printIndex());
                                Map.print();
                            }
                            else {//be killed
                                Map.getCreature(p).setIsAlive(false);
                                Map.beKilled(p);
                                for (int e = 0; e < 16; e++) {
                                    if (Main.thread[e].getCreature().id() == id()) {
                                        Main.thread[e].beKilled();
                                    }
                                }
                                IO.output(Main.turn, 0,  Map.getCreature(p).id(),1);
                                System.out.println(Map.getCreature(new Pos(i, j)).printIndex() + " killed " + printIndex);
                                Map.print();
                            }
                            //break;
                            return result;
                        }

                    }
                }
            }
            //if(result)
                //break;
        }
        return result;
    }
}
class Calabash extends Creature{
    Calabash(int id, Pos p, String s, boolean team, boolean isAlive){
        this.id = id;
        this.p = p;
        this.printIndex = s;
        this.team = team;
        this.isAlive = isAlive;
    }
}
class Grandfather extends Creature{
    Grandfather(int id, Pos p, String s, boolean team, boolean isAlive){
        this.id = id;
        this.p = p;
        this.printIndex = s;
        this.team = team;
        this.isAlive = isAlive;
    }
}
class Scorpion extends Creature{
    Scorpion(int id, Pos p, String s, boolean team, boolean isAlive){
        this.id = id;
        this.p = p;
        this.printIndex = s;
        this.team = team;
        this.isAlive = isAlive;
    }
}
class Snake extends  Creature{
    Snake(int id, Pos p, String s, boolean team, boolean isAlive){
        this.id = id;
        this.p = p;
        this.printIndex = s;
        this.team = team;
        this.isAlive = isAlive;
    }

}
class Underling extends Creature{
    Underling(int id, Pos p, String s, boolean team, boolean isAlive){
        this.id = id;
        this.p = p;
        this.printIndex = s;
        this.team = team;
        this.isAlive = isAlive;
    }
}