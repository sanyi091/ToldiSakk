package util;

import java.util.Comparator;

public class LogComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Logger l1 = (Logger) o1;
        Logger l2 = (Logger) o2;
        return l1.path.compareTo(l2.path);

    }
}
