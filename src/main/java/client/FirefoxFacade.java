package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.lang3.SystemUtils;

/**
 * Firefox impl of BrowserFacade interface. Currently only supported on windows
 * @author: William Navey
 */
public class FirefoxFacade implements BrowserFacade {

    private final String pathToBrowser;

    public FirefoxFacade() throws FileNotFoundException{
        this("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe ");
    }

    public FirefoxFacade(String installedBrowserPath) throws FileNotFoundException {
        if(SystemUtils.IS_OS_WINDOWS){
            //LOG initializing path to browser to default windows path
            File firefoxExecutable = new File(installedBrowserPath);
            if(!firefoxExecutable.exists()){
                throw new FileNotFoundException("Firefox executable not found using default path: " + firefoxExecutable.getAbsolutePath());
            }
            pathToBrowser = firefoxExecutable.getAbsolutePath().endsWith(" ") ?
                    firefoxExecutable.getAbsolutePath() : firefoxExecutable.getAbsolutePath() + " ";
        }
        else{
            //LOG os not supported yet
            throw new UnsupportedOperationException("OS: " + System.getProperty("os.name") + " not yet supported by Firefox.");
        }
    }

    @Override
    public Process launchBrowserProcess(String url) throws IOException{
        // method inspired from
        //http://www.programcreek.com/2009/05/using-java-open-ie-and-close-ie/
        try {
            return Runtime.getRuntime().exec(pathToBrowser + url);
        } catch (IOException e) {
            throw new IOException("Error launching Firefox browser: " + e.getMessage(), e);
        }
    }
}
