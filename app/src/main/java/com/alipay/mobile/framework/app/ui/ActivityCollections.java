package com.alipay.mobile.framework.app.ui;

import android.app.Activity;
import android.util.Pair;
import com.alipay.mobile.aspect.FrameworkPointcutCall;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityCollections {
    private static volatile ActivityCollections a;
    private Map<String, ActivityRef> b = new HashMap();

    private class ActivityRef extends WeakReference<Activity> {
        public ActivityRef(Activity r) {
            super(r);
        }

        public ActivityRef(Activity r, ReferenceQueue<? super Activity> q) {
            super(r, q);
        }

        public String toString() {
            Activity activity = (Activity) get();
            if (activity != null) {
                return activity.getClass().getName();
            }
            return super.toString();
        }
    }

    private ActivityCollections() {
    }

    public static synchronized ActivityCollections getInstance() {
        ActivityCollections activityCollections;
        synchronized (ActivityCollections.class) {
            try {
                if (a == null) {
                    a = new ActivityCollections();
                }
                activityCollections = a;
            }
        }
        return activityCollections;
    }

    public static synchronized ActivityCollections createInstance() {
        ActivityCollections activityCollections;
        synchronized (ActivityCollections.class) {
            if (a == null) {
                a = new ActivityCollections();
            }
            activityCollections = a;
        }
        return activityCollections;
    }

    public static synchronized void destroy(Activity exclude) {
        synchronized (ActivityCollections.class) {
            if (a != null) {
                Collection<ActivityRef> values = a.b.values();
                if (values != null && !values.isEmpty()) {
                    for (ActivityRef activityRef : values) {
                        Activity activity = (Activity) activityRef.get();
                        if (!(activity == null || activity.isFinishing() || activity == exclude)) {
                            String pointCut = null;
                            if (activity instanceof BaseActivity) {
                                pointCut = PointCutConstants.BASEACTIVITY_FINISH;
                            } else if (activity instanceof BaseFragmentActivity) {
                                pointCut = PointCutConstants.BASEFRAGMENTACTIVITY_FINISH;
                            }
                            Object[] args = new Object[0];
                            FrameworkPointcutCall.onCallBefore(pointCut, activity, args);
                            Pair aroundResult = FrameworkPointcutCall.onCallAround(pointCut, activity, args);
                            if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
                                activity.finish();
                                activity = null;
                            }
                            FrameworkPointcutCall.onCallAfter(pointCut, activity, args);
                        }
                    }
                    a.b.clear();
                }
            }
        }
    }

    public synchronized void recordActivity(Activity activity) {
        if (activity != null) {
            String key = activity.toString();
            TraceLogger.v((String) "ActivityCollections", "recordActivity(key=" + key + ")");
            if (!this.b.containsKey(key)) {
                this.b.put(key, new ActivityRef(activity));
            }
            List<String> deleteKeys = new ArrayList<>();
            for (String k : this.b.keySet()) {
                if (this.b.get(k).get() == null) {
                    deleteKeys.add(k);
                }
            }
            for (String _key : deleteKeys) {
                this.b.remove(_key);
            }
        }
    }
}
