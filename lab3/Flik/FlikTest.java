import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void test1(){
        assertTrue(Flik.isSameNumber(1,1));

    }
    @Test
    public void test2(){
        assertTrue("2",Flik.isSameNumber(128,128));

    }
}
