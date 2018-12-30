package main;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ui {
    public static void setUi(){
        Main.root.getChildren().clear();
        Main.root.getChildren().add(new ImageView(Main.background));
        for(int i = 0; i < 16; i++){
            //if(Main.thread[i].getCreature().isAlive()){
                Main.root.getChildren().add(Main.thread[i].getImageView());
            //}
        }

    }
    public static void setPic(ImageView imageView, Pos pos){
        System.out.println(Thread.currentThread().getName() + " setPic");
        Main.root.getChildren().remove(imageView);
        imageView.setTranslateX(pos.getY());
        imageView.setTranslateY(pos.getX());
        Main.root.getChildren().add(imageView);
    }
    public static void killPic(ImageView imageView){
        System.out.println(Thread.currentThread().getName() + " killPic");
        Main.root.getChildren().remove(imageView);
        Image image = new Image("image/death.jpg");
        imageView.setImage(image);
        Main.root.getChildren().add(imageView);
    }
}
class UiMoveThread extends Thread{

    public static int newId = 0;
    public static Pos newPos = new Pos(-1, -1);

    public static ImageView imageView = new ImageView();
    public final static Object object = new Object[0];
    @Override
    public void run(){
        while(Main.repeatType != -2){
            synchronized (object){
                try {
                    if (Main.repeatType == 1) {
                        //System.out.println(Thread.currentThread().getName() + "UImove");
                        Platform.runLater(()->Main.root.getChildren().remove(Main.imageView1[newId]));

                        Platform.runLater(()-> Main.root.getChildren().add(Main.imageView1[newId]));
                        Main.imageView1[newId].setTranslateX(newPos.getY() * Main.singleDistance);
                        Main.imageView1[newId].setTranslateY(newPos.getX() * Main.singleDistance);
                        //System.out.println("(" + newPos.getX() + "," + newPos.getY() + ")");
                        Main.repeatType = 2;
                        object.notifyAll();
                    } else {

                        object.wait();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
class UiBattleThread extends Thread{
    public static int newHarm = 0;
    public static int newHarmedId = 0;
    public static int newId = 0;
    public static ImageView imageView = new ImageView();
    @Override
    public void run(){
        while(Main.repeatType != -2){
            synchronized (UiMoveThread.object){
                try {
                    if (Main.repeatType == 0) {
                       // System.out.println(Thread.currentThread().getName() + "UIBattle");
                        Platform.runLater(()->Main.root.getChildren().remove(Main.imageView1[newHarmedId]));
                        Image image = new Image("image/death.jpg");
                        Main.imageView1[newHarmedId].setImage(image);
                        Platform.runLater(() ->Main.root.getChildren().add(Main.imageView1[newHarmedId]));
                        Main.repeatType = 2;
                        UiMoveThread.object.notifyAll();
                    } else {
                        UiMoveThread.object.wait();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
