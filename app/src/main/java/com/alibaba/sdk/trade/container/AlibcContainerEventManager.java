package com.alibaba.sdk.trade.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlibcContainerEventManager {
    private static List<AlibcContainerEventListener> mWantEventListener = Collections.synchronizedList(new ArrayList());

    public static synchronized void registListener(AlibcContainerEventListener alibcContainerEventListener) {
        synchronized (AlibcContainerEventManager.class) {
            mWantEventListener.add(alibcContainerEventListener);
        }
    }

    public static synchronized void unregistListener(AlibcContainerEventListener alibcContainerEventListener) {
        synchronized (AlibcContainerEventManager.class) {
            mWantEventListener.remove(alibcContainerEventListener);
        }
    }

    static void sendEvent(int i, Object obj) {
        if (mWantEventListener != null && mWantEventListener.size() > 0) {
            for (AlibcContainerEventListener next : mWantEventListener) {
                if (next != null) {
                    next.onEvent(i, obj);
                }
            }
        }
    }
}
