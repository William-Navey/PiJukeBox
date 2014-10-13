package client;

import java.io.FileNotFoundException;

/**
 * @author: wnavey
 */
public class BrowserFacadeFactory {

    public static BrowserFacade getBrowserFacade() throws FileNotFoundException{
        //LOG : only firefox supported
        return new FirefoxFacade();
    }

    public static BrowserFacade getBrowserFacade(String browserPath) throws FileNotFoundException{
        return new FirefoxFacade(browserPath);
    }
}
