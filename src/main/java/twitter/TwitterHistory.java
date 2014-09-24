package twitter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: wnavey
 */
public class TwitterHistory {

    public static Set<String> usersLogged = Collections.synchronizedSet(new HashSet<String>());
    public static Set<String> songsLogged = Collections.synchronizedSet(new HashSet<String>());

}
