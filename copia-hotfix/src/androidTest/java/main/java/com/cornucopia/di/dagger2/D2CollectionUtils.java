package com.cornucopia.di.dagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class D2CollectionUtils {
    
    public <T> List<T> toSortedList(Comparator<T> comparator, T... stringsToSort) {
        List<T> list = Arrays.asList(stringsToSort);
        Collections.sort(list, comparator);
        return list;
    }

}
