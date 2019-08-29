package com.alibaba.sdk.want;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlibcWantEventDispatch {
    private static List<AlibcWantLisener> mLisener = Collections.synchronizedList(new ArrayList());

    public static synchronized void registListener(AlibcWantLisener alibcWantLisener) {
        synchronized (AlibcWantEventDispatch.class) {
            mLisener.add(alibcWantLisener);
        }
    }

    public static synchronized void unregistListener(AlibcWantLisener alibcWantLisener) {
        synchronized (AlibcWantEventDispatch.class) {
            mLisener.remove(alibcWantLisener);
        }
    }

    public static void onEvent(String str, String str2) {
        if (mLisener != null && mLisener.size() > 0) {
            for (AlibcWantLisener next : mLisener) {
                if (next != null) {
                    next.onEvent(str, str2);
                }
            }
        }
    }
}
