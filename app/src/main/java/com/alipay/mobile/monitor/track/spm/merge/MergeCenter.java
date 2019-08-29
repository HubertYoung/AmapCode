package com.alipay.mobile.monitor.track.spm.merge;

import android.os.AsyncTask;
import android.os.Process;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.alipay.mobile.monitor.track.spm.SpmLogCator;
import com.alipay.mobile.monitor.track.spm.monitor.TrackerExecutor;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.MergeTracker;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.PriorityBlockingQueue;

public enum MergeCenter {
    INSTANCE;
    
    /* access modifiers changed from: private */
    public final String TAG;
    /* access modifiers changed from: private */
    public List<String> mClosedPageId;
    private MergeDispatcher mMergeDispatcher;
    /* access modifiers changed from: private */
    public Map<String, MergeTask> mMergeTasks;
    /* access modifiers changed from: private */
    public List<String> mMergedKeys;
    /* access modifiers changed from: private */
    public TrackerExecutor mTrackerExcutor;
    /* access modifiers changed from: private */
    public final PriorityBlockingQueue<MergeTracker> mWaitingRequests;

    public class MergeDispatcher extends Thread {
        public MergeDispatcher() {
        }

        public void run() {
            Process.setThreadPriority(10);
            while (true) {
                try {
                    MergeTracker mergeTracker = (MergeTracker) MergeCenter.this.mWaitingRequests.take();
                    if (mergeTracker != null) {
                        try {
                            a(mergeTracker);
                        } catch (Exception e) {
                            SpmLogCator.error(MergeCenter.this.TAG, (Throwable) e);
                        }
                    }
                } catch (InterruptedException e2) {
                }
            }
        }

        private void a(MergeTracker tracker) {
            MergeTask mergeTask;
            if (tracker != null && tracker.getBehavorBuilder() != null) {
                Behavor behavor = tracker.getBehavorBuilder().build();
                if (TextUtils.isEmpty(behavor.getSeedID()) || TextUtils.isEmpty(behavor.getPageId())) {
                    MergeCenter.this.mTrackerExcutor.commitTracker(tracker);
                    SpmLogCator.debug(MergeCenter.this.TAG, "execute behavorKey is null. ");
                    return;
                }
                String behavorIdentifier = a(behavor);
                if (MergeCenter.this.mMergedKeys.contains(behavorIdentifier)) {
                    SpmLogCator.debug(MergeCenter.this.TAG, "execute behavorIdentifier has been merged, need not to merge again.behavorIdentifier:" + behavorIdentifier);
                    return;
                }
                String behavorKey = a(tracker.getBehavorId(), behavor);
                MergeTask mergeTask2 = (MergeTask) MergeCenter.this.mMergeTasks.get(behavorKey);
                if (mergeTask2 == null) {
                    mergeTask = new MergeTask(tracker);
                } else {
                    mergeTask = mergeTask2.merge(tracker);
                }
                synchronized (MergeCenter.this) {
                    MergeCenter.this.mMergedKeys.add(behavorIdentifier);
                    if (a(mergeTask)) {
                        MergeCenter.this.mTrackerExcutor.commitTracker(new MergeTracker(mergeTask.getBehavorId(), mergeTask.getBehavorBuider()));
                        MergeCenter.this.mMergeTasks.remove(behavorKey);
                    } else {
                        MergeCenter.this.mMergeTasks.put(behavorKey, mergeTask);
                    }
                }
            }
        }

        private boolean a(MergeTask mergeTask) {
            if (mergeTask.needCommit()) {
                SpmLogCator.debug(MergeCenter.this.TAG, "checkIfCommit mergeTask needCommit");
                return true;
            }
            Builder behavorBuilder = mergeTask.getBehavorBuider();
            if (behavorBuilder != null) {
                String pageId = behavorBuilder.build().getPageId();
                SpmLogCator.debug(MergeCenter.this.TAG, "checkIfCommit pageId: " + pageId);
                boolean isClosedPage = MergeCenter.this.mClosedPageId.contains(pageId);
                if (isClosedPage) {
                    boolean isPageEmpty = a(pageId);
                    SpmLogCator.debug(MergeCenter.this.TAG, "checkIfCommit isClosedPage: " + isClosedPage);
                    SpmLogCator.debug(MergeCenter.this.TAG, "checkIfCommit isPageEmpty: " + isPageEmpty);
                    if (!isPageEmpty) {
                        return isPageEmpty;
                    }
                    MergeCenter.this.mClosedPageId.remove(pageId);
                    return isPageEmpty;
                }
            }
            return false;
        }

        private boolean a(String pageId) {
            Iterator it = MergeCenter.this.mWaitingRequests.iterator();
            while (it.hasNext()) {
                MergeTracker tracker = (MergeTracker) it.next();
                if (tracker != null && tracker.getBehavorBuilder() != null && pageId.equals(tracker.getBehavorBuilder().build().getPageId())) {
                    return false;
                }
            }
            return true;
        }

        private String a(String behavorId, Behavor behavor) {
            if (behavor == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(behavor.getPageId()).append("__");
            sb.append(behavor.getSeedID()).append("__");
            sb.append(behavorId);
            return sb.toString();
        }

        private String a(Behavor behavor) {
            if (behavor == null) {
                return null;
            }
            StringBuilder identifier = new StringBuilder("");
            identifier.append(behavor.getPageId()).append("__");
            identifier.append(behavor.getSeedID());
            Map param4 = a(behavor.getExtParams());
            if (param4 != null) {
                for (String key : param4.keySet()) {
                    if (!"timeThreshold".equals(key) && !"areaThreshold".equals(key)) {
                        identifier.append(key).append(param4.get(key));
                    }
                }
            }
            return identifier.toString();
        }

        private Map<String, String> a(Map<String, String> source) {
            if (source == null) {
                return null;
            }
            Map map = new TreeMap(new Comparator<String>() {
                public int compare(String obj1, String obj2) {
                    return obj1.compareTo(obj2);
                }
            });
            map.putAll(source);
            return map;
        }
    }

    public void merge(MergeTracker tracker) {
        if (this.mMergeDispatcher == null) {
            this.mMergeDispatcher = new MergeDispatcher();
            this.mMergeDispatcher.start();
        }
        synchronized (this.mWaitingRequests) {
            this.mWaitingRequests.add(tracker);
        }
    }

    public void onPageEnd(final String pageId) {
        SpmLogCator.debug(this.TAG, "onPageEnd pageId: " + pageId);
        if (!this.mClosedPageId.contains(pageId)) {
            this.mClosedPageId.add(pageId);
        }
        if (!this.mMergedKeys.isEmpty() || !this.mMergeTasks.isEmpty()) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                public void run() {
                    synchronized (MergeCenter.this) {
                        List removeKeys = new ArrayList();
                        for (String mergeKey : MergeCenter.this.mMergedKeys) {
                            if (mergeKey.startsWith(pageId + "__")) {
                                removeKeys.add(mergeKey);
                            }
                        }
                        MergeCenter.this.mMergedKeys.removeAll(removeKeys);
                        SpmLogCator.debug(MergeCenter.this.TAG, "onPageEnd mMergedKeys.isEmpty: " + MergeCenter.this.mMergedKeys.isEmpty());
                        Iterator it = MergeCenter.this.mMergeTasks.entrySet().iterator();
                        while (it.hasNext()) {
                            Entry entry = (Entry) it.next();
                            if (((String) entry.getKey()).startsWith(pageId + "__") && entry.getValue() != null) {
                                MergeCenter.this.mTrackerExcutor.commitTracker(new MergeTracker(((MergeTask) entry.getValue()).getBehavorId(), ((MergeTask) entry.getValue()).getBehavorBuider()));
                                it.remove();
                            }
                        }
                    }
                    SpmLogCator.debug(MergeCenter.this.TAG, "onPageEnd mMergeTasks.isEmpty: " + MergeCenter.this.mMergeTasks.isEmpty());
                }
            });
        }
    }

    public TrackerExecutor getTrackerExcutor() {
        return this.mTrackerExcutor;
    }
}
