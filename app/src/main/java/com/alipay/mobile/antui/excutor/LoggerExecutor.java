package com.alipay.mobile.antui.excutor;

import java.util.Map;

public interface LoggerExecutor {
    void debug(String str, String str2);

    void error(String str, String str2);

    void eventBehavor(String str, String str2);

    void eventBehavor(String str, String str2, String str3, String str4, String str5, Map<String, String> map);

    void info(String str, String str2);

    void mtBizReport(String str, String str2);
}
