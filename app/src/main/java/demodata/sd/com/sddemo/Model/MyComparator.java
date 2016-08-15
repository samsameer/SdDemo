package demodata.sd.com.sddemo.Model;

import java.util.Comparator;

/**
 * Created by jabbir on 2/8/16.
 */
public class MyComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o2.compareTo(o1); // magic line
    }
}
