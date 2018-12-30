package main;
public class Queue {
    final static int LONGSNAKE = 0;
    final static int CRANEWING = 1;
    final static int FLY = 2;
    public static void longSnake(boolean team){
        if(team){
            Main.thread[0] = new CreatureThread(new Calabash(0, new Pos(0, 0), "R", true, true));
            Main.thread[1] = new CreatureThread(new Calabash(1, new Pos(1, 0), "O", true, true));
            Main.thread[2] = new CreatureThread(new Calabash(2, new Pos(2, 0), "Y", true, true));
            Main.thread[3] = new CreatureThread(new Calabash(3, new Pos(3, 0), "G", true, true));
            Main.thread[4] = new CreatureThread(new Calabash(4, new Pos(4, 0), "B", true, true));
            Main.thread[5] = new CreatureThread(new Calabash(5, new Pos(5, 0), "I", true, true));
            Main.thread[6] = new CreatureThread(new Calabash(6, new Pos(6, 0), "V", true, true));
            Main.thread[7] = new CreatureThread(new Grandfather(7, new Pos(7, 0), "g", true, true));
        }
        else{
            Main.thread[8] = new CreatureThread(new Snake(8, new Pos(0, 9), "S", false, true));
            Main.thread[9]= new CreatureThread(new Scorpion(9, new Pos(1, 9), "s", false, true));
            Main.thread[10] = new CreatureThread(new Underling(10, new Pos(2, 9), "1", false, true));
            Main.thread[11] = new CreatureThread(new Underling(11, new Pos(3, 9), "2", false, true));
            Main.thread[12] = new CreatureThread(new Underling(12, new Pos(4, 9), "3", false, true));
            Main.thread[13] = new CreatureThread(new Underling(13, new Pos(5, 9), "4", false, true));
            Main.thread[14] = new CreatureThread(new Underling(14, new Pos(6, 9), "5", false, true));
            Main.thread[15] = new CreatureThread(new Underling(15, new Pos(7, 9), "6", false, true));
        }
    }
    public static void craneWing(boolean team){
        if(team){
            Main.thread[0] = new CreatureThread(new Calabash(0, new Pos(0, 0), "R", true, true));
            Main.thread[1] = new CreatureThread(new Calabash(1, new Pos(1, 1), "O", true, true));
            Main.thread[2] = new CreatureThread(new Calabash(2, new Pos(2, 2), "Y", true, true));
            Main.thread[3] = new CreatureThread(new Calabash(3, new Pos(3, 3), "G", true, true));
            Main.thread[4] = new CreatureThread(new Calabash(4, new Pos(4, 3), "B", true, true));
            Main.thread[5] = new CreatureThread(new Calabash(5, new Pos(5, 2), "I", true, true));
            Main.thread[6] = new CreatureThread(new Calabash(6, new Pos(6, 1), "V", true, true));
            Main.thread[7] = new CreatureThread(new Grandfather(7, new Pos(7, 0), "g", true, true));
        }
        else{
            Main.thread[8] = new CreatureThread(new Snake(8, new Pos(2, 6), "S", false, true));
            Main.thread[9]= new CreatureThread(new Scorpion(9, new Pos(3, 7), "s", false, true));
            Main.thread[10] = new CreatureThread(new Underling(10, new Pos(4, 8), "1", false, true));
            Main.thread[11] = new CreatureThread(new Underling(11, new Pos(5, 9), "2", false, true));
            Main.thread[12] = new CreatureThread(new Underling(12, new Pos(6, 9), "3", false, true));
            Main.thread[13] = new CreatureThread(new Underling(13, new Pos(7, 8), "4", false, true));
            Main.thread[14] = new CreatureThread(new Underling(14, new Pos(8, 7), "5", false, true));
            Main.thread[15] = new CreatureThread(new Underling(15, new Pos(9, 6), "6", false, true));
        }
    }
    public static void fly(boolean team){
        if(team){
            Main.thread[0] = new CreatureThread(new Calabash(0, new Pos(7, 0), "R", true, true));
            Main.thread[1] = new CreatureThread(new Calabash(1, new Pos(6, 1), "O", true, true));
            Main.thread[2] = new CreatureThread(new Calabash(2, new Pos(5, 2), "Y", true, true));
            Main.thread[3] = new CreatureThread(new Calabash(3, new Pos(4, 3), "G", true, true));
            Main.thread[4] = new CreatureThread(new Calabash(4, new Pos(3, 4), "B", true, true));
            Main.thread[5] = new CreatureThread(new Calabash(5, new Pos(2, 5), "I", true, true));
            Main.thread[6] = new CreatureThread(new Calabash(6, new Pos(1, 6), "V", true, true));
            Main.thread[7] = new CreatureThread(new Grandfather(7, new Pos(0, 7), "g", true, true));
        }
        else{
            Main.thread[8] = new CreatureThread(new Snake(8, new Pos(9, 2), "S", false, true));
            Main.thread[9]= new CreatureThread(new Scorpion(9, new Pos(8, 3), "s", false, true));
            Main.thread[10] = new CreatureThread(new Underling(10, new Pos(7, 4), "1", false, true));
            Main.thread[11] = new CreatureThread(new Underling(11, new Pos(6, 5), "2", false, true));
            Main.thread[12] = new CreatureThread(new Underling(12, new Pos(5, 6), "3", false, true));
            Main.thread[13] = new CreatureThread(new Underling(13, new Pos(4, 7), "4", false, true));
            Main.thread[14] = new CreatureThread(new Underling(14, new Pos(3, 8), "5", false, true));
            Main.thread[15] = new CreatureThread(new Underling(15, new Pos(2, 9), "6", false, true));
        }
    }
    public static void setQueue(int queueType, boolean team){
        if(queueType == LONGSNAKE){
            longSnake(team);
        }
        else if(queueType == CRANEWING){
            craneWing(team);
        }
        else if(queueType == FLY){
            fly(team);
        }
    }
    public static int getQueue(String name){
        switch(name){
            case "LONGSNAKE" : return LONGSNAKE;
            case "CRANEWING" : return CRANEWING;
            case "FLY" : return FLY;
            default: return -1;
        }
    }
}
