package com.alipay.mobile.nebula.log.linkmonitor;

import android.os.Handler;
import android.os.Looper;
import com.alipay.mobile.nebula.log.linkmonitor.H5LinkMonitor.ExpectationListener;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5LinkMonitorImpl implements H5LinkMonitor {
    private static final String TAG = "H5LinkMonitorImpl";
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean linkCompleted = false;
    private Map<String, H5LinkMonitorTree> linkMonitorMap = new ConcurrentHashMap();

    public List<H5LinkNode> getLinkData(String linkId) {
        H5LinkMonitorTree linkMonitorTree = this.linkMonitorMap.get(linkId);
        if (linkMonitorTree != null) {
            return linkMonitorTree.getLinkMonitorList();
        }
        return null;
    }

    public H5LinkMonitorTree getLinkById(String linkId) {
        return this.linkMonitorMap.get(linkId);
    }

    public void addLinkMonitorTree(String linkId) {
        if (this.linkMonitorMap.containsKey(linkId)) {
            H5Log.d(TAG, " linkMonitorMap already contains : " + linkId);
        } else {
            this.linkMonitorMap.put(linkId, new H5LinkMonitorTree());
        }
    }

    public boolean linkCompleted() {
        if (this.linkCompleted) {
            H5Log.d(TAG, "linkCompleted : " + this.linkCompleted);
        }
        return this.linkCompleted;
    }

    public void setLinkCompleted(boolean completed) {
        this.linkCompleted = completed;
    }

    public void setLinkData(String linkId, String nodeName, long triggerTime, String parentName) {
        H5Log.d(TAG, "setLinkData linkId : " + linkId + " nodeName : " + nodeName + " triggerTime : " + triggerTime + " parentName : " + parentName);
        H5LinkMonitorTree linkMonitorTree = this.linkMonitorMap.get(linkId);
        if (linkMonitorTree == null) {
            H5Log.w(TAG, "linkMonitorTree == null");
            return;
        }
        H5LinkNode linkNode = new H5LinkNode();
        linkNode.setNodeName(nodeName);
        linkNode.setTriggerTime(triggerTime);
        linkMonitorTree.addNode(linkNode, parentName);
    }

    public boolean createExpectation(String linkId, String nodeName, String depend, int timeout) {
        return createExpectation(linkId, nodeName, depend, timeout, null);
    }

    public boolean createExpectation(String linkId, String nodeName, String depend, int timeout, ExpectationListener listener) {
        H5Log.d(TAG, "createExpectation linkId : " + linkId + " nodeName : " + nodeName + " depend : " + depend + " timeout : " + timeout);
        H5LinkMonitorTree linkMonitorTree = this.linkMonitorMap.get(linkId);
        if (linkMonitorTree == null) {
            H5Log.w(TAG, "linkMonitorTree == null");
            return false;
        } else if (linkMonitorTree.containsNode(nodeName)) {
            H5Log.w(TAG, "linkMonitorTree already contain : " + nodeName);
            return false;
        } else {
            H5LinkNode linkNode = new H5LinkNode();
            linkNode.setNodeName(nodeName);
            linkMonitorTree.addNode(linkNode, depend);
            H5LinkNode dependNode = linkMonitorTree.getLinkNodeByName(depend);
            if (dependNode != null) {
                final String str = linkId;
                final String str2 = nodeName;
                final int i = timeout;
                final ExpectationListener expectationListener = listener;
                Runnable runnable = new Runnable() {
                    public void run() {
                        H5LinkMonitorImpl.this.handleCheckExpectation(str, str2, i, expectationListener);
                    }
                };
                linkNode.setCheckRunnable(runnable);
                this.handler.postDelayed(runnable, (long) (timeout - ((int) (System.currentTimeMillis() - dependNode.getTriggerTime()))));
            }
            return true;
        }
    }

    public void checkExpectation(String linkId, String nodeName, int timeout, ExpectationListener listener) {
        final String str = linkId;
        final String str2 = nodeName;
        final int i = timeout;
        final ExpectationListener expectationListener = listener;
        this.handler.postDelayed(new Runnable() {
            public void run() {
                H5LinkMonitorImpl.this.handleCheckExpectation(str, str2, i, expectationListener);
            }
        }, (long) timeout);
    }

    /* access modifiers changed from: private */
    public void handleCheckExpectation(String linkId, String nodeName, int timeout, ExpectationListener listener) {
        H5LinkMonitorTree linkMonitorTree = this.linkMonitorMap.get(linkId);
        if (linkMonitorTree == null) {
            H5Log.w(TAG, "linkMonitorTree == null");
        } else if (listener != null) {
            listener.checkExpectation(linkMonitorTree);
        } else {
            H5LinkNode linkNode = linkMonitorTree.getLinkNodeByName(nodeName);
            if (linkNode != null) {
                linkNode.setCheckRunnable(null);
                long triggerTime = linkNode.getTriggerTime();
                long parentTriggerTime = 0;
                H5LinkNode parentNode = linkNode.getParentNode();
                if (parentNode != null) {
                    parentTriggerTime = parentNode.getTriggerTime();
                }
                if (triggerTime <= 0 || triggerTime - parentTriggerTime >= ((long) timeout)) {
                    linkMonitorTree.logExpectationFail(linkId, nodeName, timeout);
                } else {
                    H5Log.d(TAG, "checkExpectation nodeName : " + nodeName + " triggerTime : " + triggerTime + " parentNodeName : " + parentNode.getNodeName() + " parentNodeTrigger : " + parentTriggerTime);
                }
            }
        }
    }

    public void fulfillExpectation(String linkId, String nodeName, long triggerTime) {
        H5Log.d(TAG, "fulfillExpectation linkId : " + linkId + " nodeName : " + nodeName + " triggerTime : " + triggerTime);
        H5LinkMonitorTree linkMonitorTree = this.linkMonitorMap.get(linkId);
        if (linkMonitorTree == null) {
            H5Log.w(TAG, "linkMonitorTree == null");
        } else if (linkMonitorTree.getLinkNodeByName(nodeName) == null || linkMonitorTree.getLinkNodeByName(nodeName).getTriggerTime() <= 0) {
            H5LinkNode linkNode = linkMonitorTree.getLinkNodeByName(nodeName);
            if (linkNode != null) {
                linkNode.setTriggerTime(triggerTime);
            }
        } else {
            H5Log.w(TAG, nodeName + " already trigger");
        }
    }

    public void cancelExpectation(String linkId, String nodeName) {
        H5LinkMonitorTree linkMonitorTree = this.linkMonitorMap.get(linkId);
        if (linkMonitorTree == null) {
            H5Log.w(TAG, "linkMonitorTree == null");
            return;
        }
        H5LinkNode linkNode = linkMonitorTree.getLinkNodeByName(nodeName);
        if (linkNode != null && linkNode.getCheckRunnable() != null) {
            this.handler.removeCallbacks(linkNode.getCheckRunnable());
            linkNode.setCheckRunnable(null);
        }
    }

    public void cancelLinkMonitor(String linkId) {
        H5LinkMonitorTree linkMonitorTree = getLinkById(linkId);
        if (linkMonitorTree != null && linkMonitorTree.getLinkMonitorList() != null && linkMonitorTree.getLinkMonitorList().size() > 0) {
            for (H5LinkNode linkNode : linkMonitorTree.getLinkMonitorList()) {
                Runnable runnable = linkNode.getCheckRunnable();
                if (runnable != null) {
                    this.handler.removeCallbacks(runnable);
                    linkNode.setCheckRunnable(null);
                }
            }
        }
    }
}
