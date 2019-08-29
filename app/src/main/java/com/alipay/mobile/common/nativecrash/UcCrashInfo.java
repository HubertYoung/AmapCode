package com.alipay.mobile.common.nativecrash;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class UcCrashInfo {
    private int a = -1;
    private String b = null;
    private int c = -1;
    private String d = null;
    private String e = null;

    public static UcCrashInfo parse(String crashInfo) {
        UcCrashInfo rCrashInfo = new UcCrashInfo();
        try {
            rCrashInfo.a(crashInfo);
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "UcCrashInfo", tr);
        }
        return rCrashInfo;
    }

    private void a(String crashInfo) {
        if (!TextUtils.isEmpty(crashInfo)) {
            String crashSignalLine = a(crashInfo.split("\n"), "signal ", ", code ");
            if (!TextUtils.isEmpty(crashSignalLine)) {
                int start = crashSignalLine.indexOf("signal ");
                int end = crashSignalLine.indexOf(", code ");
                if (start >= 0 && end > start) {
                    String[] signalParts = crashSignalLine.substring(start + 7, end).split(Token.SEPARATOR);
                    try {
                        this.a = Integer.parseInt(signalParts[0]);
                    } catch (Throwable tr) {
                        LoggerFactory.getTraceLogger().warn((String) "UcCrashInfo", tr);
                    }
                    if (signalParts.length >= 2) {
                        this.b = signalParts[1];
                    }
                }
                int start2 = crashSignalLine.indexOf("code ");
                int end2 = crashSignalLine.indexOf(", fault addr ");
                if (start2 >= 0 && end2 > start2) {
                    String[] codeParts = crashSignalLine.substring(start2 + 5, end2).split(Token.SEPARATOR);
                    try {
                        this.c = Integer.parseInt(codeParts[0]);
                    } catch (Throwable tr2) {
                        LoggerFactory.getTraceLogger().warn((String) "UcCrashInfo", tr2);
                    }
                    if (codeParts.length >= 2) {
                        this.d = codeParts[1];
                    }
                }
                int start3 = crashSignalLine.indexOf("fault addr ");
                if (start3 >= 0) {
                    this.e = crashSignalLine.substring(start3 + 11);
                }
            }
        }
    }

    public int getCrashSignal() {
        return this.a;
    }

    private static String a(String[] lines, String start, String contains) {
        boolean checkStart;
        boolean checkEnd;
        boolean checkContains;
        if (lines == null || lines.length - 1 < 0) {
            return null;
        }
        if (!TextUtils.isEmpty(start)) {
            checkStart = true;
        } else {
            checkStart = false;
        }
        if (!TextUtils.isEmpty(null)) {
            checkEnd = true;
        } else {
            checkEnd = false;
        }
        if (!TextUtils.isEmpty(contains)) {
            checkContains = true;
        } else {
            checkContains = false;
        }
        for (int i = 0; i < lines.length; i++) {
            boolean fits = true;
            if (checkStart && !lines[i].startsWith(start)) {
                fits = false;
            }
            if (checkEnd && !lines[i].endsWith(null)) {
                fits = false;
            }
            if (checkContains && !lines[i].contains(contains)) {
                fits = false;
            }
            if (fits) {
                return lines[i];
            }
        }
        return null;
    }
}
