package com.alipay.mobile.nebula.dev;

import android.os.Process;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.webview.APWebView;

public class H5BugmeIdGenerator {
    private static int sId = 0;
    private static int sPid = Process.myPid();
    private static int sRequestId = 0;

    public static synchronized int nextRequestId() {
        int i;
        synchronized (H5BugmeIdGenerator.class) {
            i = sRequestId;
            sRequestId = i + 1;
        }
        return i;
    }

    public static synchronized int nextId() {
        int i;
        synchronized (H5BugmeIdGenerator.class) {
            i = sId;
            sId = i + 1;
        }
        return i;
    }

    public static String getBugmeViewId(H5Page page) {
        if (page != null) {
            APWebView webView = page.getWebView();
            if (webView != null) {
                return "view_" + webView.hashCode() + "_" + sPid;
            }
        }
        return "";
    }

    public static String getSessionId(H5Page page) {
        if (page != null) {
            H5Session session = page.getSession();
            if (session != null) {
                return "session_" + session.hashCode() + "_" + sPid;
            }
        }
        return "";
    }
}
