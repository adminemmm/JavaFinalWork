package main;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
public class IO {

    static Document document;
    static Element game;
    static Document documentIn;
    static Element gameIn;
    public static void init(int que1, int que2){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            document = db.newDocument();
            document.setXmlStandalone(true);
            game = document.createElement("game");
            Element queue = document.createElement("queue");
            Element queue1 = document.createElement("queue1");
            Element queue2 = document.createElement("queue2");
            queue1.setTextContent(((Integer)que1).toString());
            queue2.setTextContent(((Integer)que2).toString());
            queue.appendChild(queue1);
            queue.appendChild(queue2);
            game.appendChild(queue);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static void output(int id, int type, Pos pos){
        Element newTurn = document.createElement("newTurn");
        Element newId = document.createElement("newId");
        Element newType = document.createElement("newType");
        Element newPos = document.createElement("newPos");
        newId.setTextContent(((Integer)id).toString());
        newType.setTextContent(((Integer)type).toString());
        Element newX = document.createElement("newX");
        Element newY = document.createElement("newY");
        newX.setTextContent(((Integer)pos.getX()).toString());
        newY.setTextContent(((Integer)pos.getY()).toString());
        newPos.appendChild(newX);
        newPos.appendChild(newY);
        newTurn.appendChild(newId);
        newTurn.appendChild(newType);
        newTurn.appendChild(newPos);
        game.appendChild(newTurn);
    }
    public static void output(int id, int type, int harmedId, int harm){
        Element newTurn = document.createElement("newTurn");
        Element newId = document.createElement("newId");
        Element newType = document.createElement("newType");
        Element newHarmedId = document.createElement("newHarmedId");
        Element newHarm = document.createElement("newHarm");
        newId.setTextContent(((Integer)id).toString());
        newType.setTextContent(((Integer)type).toString());
        newHarmedId.setTextContent(((Integer)harmedId).toString());
        newHarm.setTextContent(((Integer)harm).toString());
        newTurn.appendChild(newId);
        newTurn.appendChild(newType);
        newTurn.appendChild(newHarmedId);
        newTurn.appendChild(newHarm);
        game.appendChild(newTurn);
    }
    public static void outputLastStep(){
        try {
            document.appendChild(game);
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(document), new StreamResult(new File("src/game1.xml")));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Document input(String name){
        try {
            File f = new File(name);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            documentIn = builder.parse(f);
            return documentIn;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
class InputThread extends Thread{
    public static int turn = 0;
    NodeList nodeList;
    ImageView imageView;
    InputThread(NodeList nodeList){
        this.nodeList = nodeList;
    }
    @Override
    public void run(){
        while(turn <= nodeList.getLength()){
            //System.out.println(turn + " " + nodeList.getLength());
            synchronized (UiMoveThread.object){
                try {
                    if (Main.repeatType == 2) {
                        sleep(100);
                        //Main.repeatType = 2;

                        Node node1 = nodeList.item(turn);
                        turn++;
                        if(turn > nodeList.getLength())
                            Main.repeatType = -2;
                            //break;
                        if(node1 != null) {
                            NodeList nodeDetail = node1.getChildNodes();
                            String state = new String();

                            for (int j = 0; j < nodeDetail.getLength(); j++) {
                                Node detail = nodeDetail.item(j);
                                if ("newType".equals(detail.getNodeName()))
                                    state = detail.getTextContent();
                            }
                            if (state.equals("0")) {
                                Main.repeatType = 0;
                                //System.out.println(Thread.currentThread().getName() + "INputThread - UIBattle");
                                int newHarm = 0;
                                int newHarmedId = 0;
                                int newId = 0;
                                for (int j = 0; j < nodeDetail.getLength(); j++) {
                                    if ("newId".equals(nodeDetail.item(j).getNodeName())) {
                                        newId = Integer.parseInt(nodeDetail.item(j).getTextContent());
                                    } else if ("newHarmedId".equals(nodeDetail.item(j).getNodeName())) {
                                        newHarmedId = Integer.parseInt(nodeDetail.item(j).getTextContent());
                                    } else if ("newHarm".equals(nodeDetail.item(j).getNodeName())) {
                                        newHarm = Integer.parseInt(nodeDetail.item(j).getTextContent());
                                    }
                                }
                                imageView = Main.imageView1[newHarmedId];
                                UiBattleThread.imageView = imageView;
                                UiBattleThread.newHarm = newHarm;
                                UiBattleThread.newHarmedId = newHarmedId;
                                UiBattleThread.newId = newId;

                            } else if (state.equals("1")) {
                                Main.repeatType = 1;

                                //System.out.println(Thread.currentThread().getName() + "INputThread - UImove");
                                int newId = 0;
                                Pos newPos = new Pos(-1, -1);
                                for (int j = 0; j < nodeDetail.getLength(); j++) {
                                    if ("newId".equals(nodeDetail.item(j).getNodeName())) {
                                        newId = Integer.parseInt(nodeDetail.item(j).getTextContent());
                                    } else if ("newPos".equals(nodeDetail.item(j).getNodeName())) {
                                        NodeList pos = nodeDetail.item(j).getChildNodes();
                                        int posX = Integer.parseInt(pos.item(1).getTextContent());
                                        int posY = Integer.parseInt(pos.item(3).getTextContent());
                                        newPos.change_x(posX);
                                        newPos.change_y(posY);
                                    }
                                }

                                UiMoveThread.imageView = Main.imageView1[newId];
                                UiMoveThread.newPos = newPos;
                                UiMoveThread.newId = newId;

                            } else {
                                //System.out.println("emmmmmmm");
                            }
                        }

                        //if(turn == nodeList.getLength())
                            //System.out.println("turn equals nodeList.getLength()");
                        //turn++;
                        UiMoveThread.object.notifyAll();
                    }
                    else {
                        UiMoveThread.object.wait();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println("end");
        Main.repeatType = -2;
        /*try {
            UiMoveThread.object.wait();
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
