import org.apache.commons.lang3.SystemUtils;
import org.junit.*;
import org.apache.commons.lang3.SystemUtils.*;

/**
 * @author: wnavey
 */
public class VideoQueueRunnerTest {

    @Test
    public void testOne() throws Exception{

        if(SystemUtils.IS_OS_WINDOWS){
            System.out.println("Hey! I'm running windows!");
        }
    }
}
