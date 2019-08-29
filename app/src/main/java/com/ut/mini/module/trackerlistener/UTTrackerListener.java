package com.ut.mini.module.trackerlistener;

import com.ut.mini.UTTracker;
import java.util.Map;

public abstract class UTTrackerListener {
    public void pageAppear(UTTracker uTTracker, Object obj, String str, boolean z) {
    }

    public void pageDisAppear(UTTracker uTTracker, Object obj) {
    }

    public void send(UTTracker uTTracker, Map<String, String> map) {
    }

    public abstract String trackerListenerName();

    public void updateNextPageProperties(UTTracker uTTracker, Map<String, String> map) {
    }

    public void updatePageName(UTTracker uTTracker, Object obj, String str) {
    }

    public void updatePageProperties(UTTracker uTTracker, Object obj, Map<String, String> map) {
    }
}
