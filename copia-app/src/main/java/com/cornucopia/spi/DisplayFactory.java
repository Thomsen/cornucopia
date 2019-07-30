package com.cornucopia.spi;

import com.cornucopia.jetpack.spi.Display;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DisplayFactory {

    private static DisplayFactory mDisplayFactory;

    private Iterator<Display> mIterator;

    private DisplayFactory() {
        // 运行时通过反射加载类实例 缺陷： 影响性能
        ServiceLoader<Display> loader = ServiceLoader.load(Display.class);
        mIterator = loader.iterator();
    }

    static DisplayFactory getSingleton() {
        if (null == mDisplayFactory) {
            synchronized (DisplayFactory.class) {
                if (null == mDisplayFactory) {
                    mDisplayFactory = new DisplayFactory();
                }
            }
        }
        return mDisplayFactory;
    }

    Display getDisplay() {
        return mIterator.next();
    }

    boolean hasNextDisplay() {
        return mIterator.hasNext();
    }


}
