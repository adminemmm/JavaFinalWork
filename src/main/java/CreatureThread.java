package main;
import main.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class CreatureThread extends Thread {
    private Creature creature;
    private ImageView imageView = new ImageView();
    static Object key = new Object[0];
    CreatureThread(Creature creature){
        this.creature = creature;
        Map.setMapCreature(creature, creature.p());
        int num = creature.id();
        if(creature.id() > 10)
            num = 10;
        Image image = new Image("image/" + num + ".jpg");
        imageView.setImage(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setTranslateY(creature.p().getX() * Main.singleDistance);//scene'x 与 map的 y对应
        imageView.setTranslateX(creature.p().getY() * Main.singleDistance);
        System.out.println(creature.printIndex + " ready");
    }
    public ImageView getImageView(){return imageView;}
    public void changePos(Pos pos){
        this.creature.setP(pos);
        Main.UiThreadRemove(Main.root, this.creature.id());
        //System.out.println(this.creature.id() + " changePos" + "(" + pos.getY() + "," + pos.getX() + ")");
        this.imageView.setTranslateX(pos.getY() * Main.singleDistance);
        this.imageView.setTranslateY(pos.getX() * Main.singleDistance);
        Main.UiThreadAdd(Main.root, this.creature.id());
    }
    public void beKilled(){
        this.creature.setIsAlive(false);
        Main.UiThreadRemove(Main.root, this.creature.id());
        System.out.println(this.creature.id() + " beKilled");
        Image image = new Image("image/death.jpg");
        this.imageView.setImage(image);
        Main.UiThreadAdd(Main.root, this.creature.id());
    }
    /*public void changePosNotInThread(Pos pos){
        System.out.println(Thread.currentThread().getName() + " changePosNotInThread");
        this.creature.setP(pos);
        System.out.println(this.creature.id() + " changePos" + "(" + pos.getY() + "," + pos.getX() + ")");
        this.imageView.setTranslateX(pos.getY() * Main.singleDistance);
        this.imageView.setTranslateY(pos.getX() * Main.singleDistance);
    }
    public void beKilledNotInThread(){
        System.out.println(Thread.currentThread().getName() + " beKilledNotInThread");
        this.creature.setIsAlive(false);
        System.out.println(this.creature.id() + " beKilled");
        Image image = new Image("file:src/death.jpg");
        this.imageView.setImage(image);
    }*/
    /*public void changePosNotInRun(Pos pos){
        this.creature.setP(pos);
        Main.UiRemove(Main.root, this.creature.id());
        this.imageView.setTranslateX(pos.getY() * Main.singleDistance);
        this.imageView.setTranslateY(pos.getX() * Main.singleDistance);
        Main.UiAdd(Main.root, this.creature.id());
    }
    public void beKilledNotInRun(){
        this.creature.setIsAlive(false);
        Main.UiRemove(Main.root, this.creature.id());
        Image image = new Image("file:src/death.jpg");
        this.imageView.setImage(image);
        Main.UiAdd(Main.root, this.creature.id());
    }*/
    public Creature getCreature(){
        return creature;
    }
    public void setCreature(Creature c){
        this.creature = c;
    }
    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }
    @Override
    public void run(){
        while(Main.checkIfIsEnd(this.creature.getClass().getName()) && Main.checkIfIsMyAllies(this.creature.getClass().getName())) {
            if (this.creature.isAlive()) {
                synchronized (key) {
                    if (Main.turn == creature.id()) {
                        try {
                            System.out.println(creature.printIndex() + "'s turn");
                            if (!creature.battle())
                                if (!creature.move())
                                    creature.stop();
                            Main.turn++;
                            Main.turn = Main.turn % 16;
                            try {
                                sleep(100);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }
                            //Map.print();
                            //System.out.println(Thread.currentThread().getName() + " " + Main.turn);
                            key.notifyAll();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            key.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                synchronized (key) {
                    if (Main.turn == creature.id()) {
                        Main.turn++;
                        Main.turn = Main.turn % 16;
                        key.notifyAll();
                    }
                }
            }
        }
        /*while(Main.checkIfIsEnd(this.creature.getClass().getName()) && !this.creature.isAlive()){
            synchronized (key){
                if(Main.turn == creature.id()){
                    Main.turn++;
                    Main.turn = Main.turn % 16;
                    key.notifyAll();
                }
            }
        }*/
    }
}

