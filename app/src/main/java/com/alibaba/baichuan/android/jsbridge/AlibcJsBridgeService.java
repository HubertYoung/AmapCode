package com.alibaba.baichuan.android.jsbridge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlibcJsBridgeService {
    private static List a = Collections.synchronizedList(new ArrayList());

    public interface AlibcJSAPIAuthCheck {
        boolean apiAuthCheck(String str, String str2, String str3, String str4);
    }

    public static List getJSBridgePreprocessors() {
        return a;
    }

    public static void registerJsbridgePreprocessor(AlibcJSAPIAuthCheck alibcJSAPIAuthCheck) {
        a.add(alibcJSAPIAuthCheck);
    }

    public static void unregisterPreprocessor(AlibcJSAPIAuthCheck alibcJSAPIAuthCheck) {
        a.remove(alibcJSAPIAuthCheck);
    }
}
