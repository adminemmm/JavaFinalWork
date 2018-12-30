package main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import java.io.File;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.w3c.dom.Node;
public class Main extends Application {
    public static CreatureThread []thread = new CreatureThread[16];
    public static int turn = 0;
    public static int repeatType = -1;
    public static ImageView []imageView1 = new ImageView[16];
    final static int width = 1000;
    final static int height = 600;
    final static int singleDistance = 50;
    int pressType = -1;
    public static BorderPane root = new BorderPane();
    public static Image background = new Image("image/background.jpg");
    ObservableList<String> options = FXCollections.observableArrayList("LONGSNAKE", "CRANEWING", "FLY");
    ComboBox queue1Box = new ComboBox(options);
    ComboBox queue2Box = new ComboBox(options);
    public static boolean checkIfIsEnd(String name){//this function is used to find the class's enemy
        boolean team = false;
        switch(name){
            case "Calabash": team = true;break;
            case "Grandfather": team = true;break;
            case "Scorpion": team = false;break;
            case "Snake": team = false;break;
            case "Underling": team = false;break;
            default: team = true;break;
        }
        int n = 0;
        for(int i = 0; i < Map.width; i++) {
            for (int j = 0; j < Map.length; j++) {
                if (!Map.isEmpty(new Pos(i, j))) {
                    try {
                        if (Map.getCreature(new Pos(i, j)).team() != team) {
                            n++;
                        }
                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        if(n == 0)
            return false;
        else
            return true;
    }
    public static boolean checkIfIsMyAllies(String name){
        boolean team = false;
        switch(name){
            case "Calabash": team = true;break;
            case "Grandfather": team = true;break;
            case "Scorpion": team = false;break;
            case "Underling": team = false;break;
            case "Snake": team = false;break;
            default: team = true;break;
        }
        int n = 0;
        for(int i = 0; i < Map.width; i++) {
            for (int j = 0; j < Map.length; j++) {
                if (!Map.isEmpty(new Pos(i, j))) {
                    if(Map.getCreature(new Pos(i, j)).team() == team) {
                        n++;
                    }
                }
            }
        }
        if(n == 0)
            return false;
        else
            return true;
    }
    public void init(){
        Map.init();
        ImageView imageView = new ImageView();
        imageView.setImage(background);
        imageView.setFitWidth(Main.width);
        imageView.setFitHeight(Main.height);
        root.getChildren().add(imageView);
        Label queue1BoxLabel = new Label("queue1: ");
        queue1BoxLabel.setTranslateY(1 * singleDistance);
        queue1BoxLabel.setTranslateX(15 * singleDistance);

        //ObservableList<String> options = FXCollections.observableArrayList("LONGSNAKE", "CRANEWING", "FLY");
        //queue1Box.getItems().addAll(Queue.LONGSNAKE, Queue.CRANEWING, Queue.FLY);
        //ComboBox queue1Box = new ComboBox(options);
        queue1Box.setTranslateY(1 * singleDistance);
        queue1Box.setTranslateX(16 * singleDistance);
        queue1Box.setValue("LONGSNAKE");
        Label queue2BoxLabel = new Label("queue2: ");
        queue1BoxLabel.setMaxWidth(singleDistance);
        queue1BoxLabel.setMaxHeight(singleDistance);
        queue2BoxLabel.setTranslateY(2 * singleDistance);
        queue2BoxLabel.setTranslateX(15 * singleDistance);
        queue1BoxLabel.setTextFill(Color.web("#0076a3"));
        queue2BoxLabel.setTextFill(Color.web("#0076a3"));
        //ComboBox queue2Box = new ComboBox(options);
        //queue2Box.getItems().addAll(Queue.LONGSNAKE, Queue.CRANEWING, Queue.FLY);
        queue2Box.setTranslateY(2 * singleDistance);
        queue2Box.setTranslateX(16 * singleDistance);
        queue2Box.setValue("LONGSNAKE");

        root.getChildren().add(queue1Box);
        root.getChildren().add(queue2Box);
        root.getChildren().add(queue1BoxLabel);
        root.getChildren().add(queue2BoxLabel);
        //Queue.longsnake(false);
        //Queue.longsnake(true);
        //Queue.setQueue(Queue.LONGSNAKE, false);
        //Queue.setQueue(Queue.LONGSNAKE, true);
        //System.out.println("before queue");
        int queue1 = Queue.CRANEWING;
        int queue2 = Queue.FLY;
        Queue.setQueue(queue1, true);
        Queue.setQueue(queue2, false);

        Map.print();
        IO.init(queue1, queue2);
    }
    public void start(Stage stage){

        Scene scene = new Scene(root, width, height);
        stage.setTitle("a");
        stage.setScene(scene);



        stage.show();
        //System.out.println("before press");
        //KeyCode code;
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            if (code.equals(KeyCode.M)) {//press M
                //System.out.println(queue1Box.getValue());
                //System.out.println("press space");
                Map.init();
                int queue1 = Queue.getQueue(queue1Box.getValue().toString());
                int queue2 = Queue.getQueue(queue2Box.getValue().toString());
                Queue.setQueue(queue1, true);
                Queue.setQueue(queue2, false);
                System.out.println(queue1 + " " + queue2);
                Map.print();
                IO.init(queue1, queue2);
                pressType = 0;
                for (int i = 0; i < Map.width; i++) {
                    for (int j = 0; j < Map.length; j++) {
                        if (!Map.isEmpty(new Pos(i, j))) {
                            root.getChildren().add(Main.thread[Map.getCreature(new Pos(i, j)).id()].getImageView());
                        }
                    }
                }
                for (int i = 0; i < 16; i++) {
                    thread[i].start();
                }
            }
            else if(code.equals(KeyCode.L)){//press L
                try {
                    //repeatType = -1;
                    ComboBox queue1Box = new ComboBox();
                    queue1Box.getItems().addAll(Queue.LONGSNAKE, Queue.CRANEWING, Queue.FLY);
                    queue1Box.setTranslateX(6 * singleDistance);
                    queue1Box.setTranslateY(16 * singleDistance);
                    ComboBox queue2Box = new ComboBox();
                    queue2Box.getItems().addAll(Queue.LONGSNAKE, Queue.CRANEWING, Queue.FLY);
                    queue2Box.setTranslateX(8 * singleDistance);
                    queue2Box.setTranslateY(16 * singleDistance);
                    root.getChildren().add(queue1Box);
                    root.getChildren().add(queue2Box);
                    pressType = 1;
                    root.getChildren().clear();
                    ImageView imageView2 = new ImageView();
                    imageView2.setImage(background);
                    imageView2.setFitWidth(Main.width);
                    imageView2.setFitHeight(Main.height);
                    root.getChildren().add(imageView2);
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Resource File");
                    fileChooser.setInitialDirectory(new File("src"));
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
                    String name = fileChooser.showOpenDialog(stage).toString();
                    //System.out.println("repeat");
                    //Document document = IO.input("src/game1.xml");
                    Document document = IO.input(name);
                    NodeList queue = document.getElementsByTagName("queue");
                    NodeList newTurn = document.getElementsByTagName("newTurn");
                    NodeList queue1 = document.getElementsByTagName("queue1");
                    NodeList queue2 = document.getElementsByTagName("queue2");
                    //System.out.println("queue: " + queue1.getLength());
                    //System.out.println(queue2.getLength());
                    //System.out.println(Integer.parseInt(queue1.item(0).getTextContent()));
                    switch (Integer.parseInt(queue1.item(0).getTextContent())){
                        case 0: Queue.setQueue(Queue.LONGSNAKE, true);break;
                        case 1: Queue.setQueue(Queue.CRANEWING, true);break;//System.out.println("queue1");break;
                        case 2: Queue.setQueue(Queue.FLY, true);break;
                    }
                    switch(Integer.parseInt(queue2.item(0).getTextContent())){
                        case 0: Queue.setQueue(Queue.LONGSNAKE, false);break;
                        case 1: Queue.setQueue(Queue.CRANEWING, false);break;
                        case 2: Queue.setQueue(Queue.FLY, false);break;//System.out.println("ueue2");break;
                    }
                    for(int i = 0; i < 16; i++){
                        imageView1[i] = thread[i].getImageView();
                        root.getChildren().add(imageView1[i]);
                    }

                    UiMoveThread uiMoveThread = new UiMoveThread();
                    UiBattleThread uiBattleThread = new UiBattleThread();
                    InputThread inputThread = new InputThread(newTurn);//System.out.println("X start");
                    Main.repeatType = 2;
                    InputThread.turn = 0;
                    //System.out.println(newTurn.getLength());
                    uiBattleThread.start();
                    uiMoveThread.start();
                    inputThread.start();

                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
    });

    }
    public static void main(String[] args){
        launch(args);
    }
    public static void UiThreadRemove(BorderPane root, int id){
        Platform.runLater(()-> root.getChildren().remove(Main.thread[id].getImageView()));
    }
    public static void UiThreadAdd(BorderPane root, int id){
       // System.out.println(Thread.currentThread().getName() + " UiThreadAdd");
        Platform.runLater(()->root.getChildren().add(Main.thread[id].getImageView()));
    }
    public void stop(){
        if(pressType == 0)
            IO.outputLastStep();
    }
}