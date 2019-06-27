package zup.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Util {

    // function to convert Iterable into Collection
    public static <T> Collection<T>
    getCollectionFromIteralbe(Iterable<T> itr)
    {
        // Create an empty Collection to hold the result
        Collection<T> cltn = new ArrayList<T>();

        return StreamSupport.stream(itr.spliterator(), false)
                .collect(Collectors.toList());
    }
}
