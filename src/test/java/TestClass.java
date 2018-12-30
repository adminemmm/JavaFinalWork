package test;
import org.junit.Assert;
import org.junit.Test;
import main.*;
import main.Creature.*;
public class TestClass {
    @Test
    public void testBegin(){
        //System.out.println("emmm");
        System.out.println("test begin");
    }
    /*@Test
    public void testCreatureThread(){
        //Pos p = new Pos(0, 0);
        CreatureThread creatureThread = new CreatureThread(new Calabash(0, new Pos(0, 0), "R", true, true));
    }*/
    @Test
    public void testMapInit(){
        Map.init();
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                Assert.assertEquals(true, Map.isEmpty(new Pos(i, j)));
            }
            System.out.println("map init successfully");
    }

}
