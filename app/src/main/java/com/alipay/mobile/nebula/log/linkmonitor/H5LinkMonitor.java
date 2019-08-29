package com.alipay.mobile.nebula.log.linkmonitor;

import java.util.List;

public interface H5LinkMonitor {

    public interface ExpectationListener {
        void checkExpectation(H5LinkMonitorTree h5LinkMonitorTree);
    }

    void addLinkMonitorTree(String str);

    void cancelExpectation(String str, String str2);

    void cancelLinkMonitor(String str);

    void checkExpectation(String str, String str2, int i, ExpectationListener expectationListener);

    boolean createExpectation(String str, String str2, String str3, int i);

    boolean createExpectation(String str, String str2, String str3, int i, ExpectationListener expectationListener);

    void fulfillExpectation(String str, String str2, long j);

    H5LinkMonitorTree getLinkById(String str);

    List<H5LinkNode> getLinkData(String str);

    boolean linkCompleted();

    void setLinkCompleted(boolean z);

    void setLinkData(String str, String str2, long j, String str3);
}
