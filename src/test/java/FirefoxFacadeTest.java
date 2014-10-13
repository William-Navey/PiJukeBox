import client.BrowserFacade;
import client.FirefoxFacade;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * @author: wnavey
 */
public class FirefoxFacadeTest {

    @Test(expected = FileNotFoundException.class)
    public void testConstructorGivenInvalidFirefoxPathThrowsFileNotFoundException() throws Exception{
        BrowserFacade firefoxFacade = new FirefoxFacade("C:\\this\\is\\not\\a\\real\\path");
    }
}
