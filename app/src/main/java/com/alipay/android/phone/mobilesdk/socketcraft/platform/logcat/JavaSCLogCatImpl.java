package com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class JavaSCLogCatImpl implements SCLogCatInterface {
    public void info(String tag, String msg) {
        systemOut(getCommonPrefix(tag, LogHelper.DEFAULT_LEVEL) + ":" + msg);
    }

    public void verbose(String tag, String msg) {
        systemOut(getCommonPrefix(tag, SecureSignatureDefine.SG_KEY_SIGN_VERSION) + ":" + msg);
    }

    public void debug(String tag, String msg) {
        systemOut(getCommonPrefix(tag, "D") + ":" + msg);
    }

    public void warn(String tag, String msg) {
        systemOut(getCommonPrefix(tag, "W") + ":" + msg);
    }

    public void warn(String tag, Throwable throwable) {
        printLog(getCommonPrefix(tag, "W"), throwable);
    }

    public void warn(String tag, String msg, Throwable throwable) {
        printLog(getCommonPrefix(tag, "W") + ":" + msg, throwable);
    }

    private void systemOut(String out) {
        try {
            Class systemClass = Class.forName("java.lang.System");
            Object outObj = systemClass.getField("out").get(systemClass);
            outObj.getClass().getMethod("println", new Class[]{String.class}).invoke(outObj, new Object[]{out});
        } catch (Throwable th) {
        }
    }

    public void error(String tag, String msg) {
        printLog(getCommonPrefix(tag, "E") + ":" + msg, null);
    }

    public void error(String tag, Throwable throwable) {
        printLog(getCommonPrefix(tag, "E"), throwable);
    }

    public void error(String tag, String msg, Throwable throwable) {
        printLog(getCommonPrefix(tag, "E") + ":" + msg, throwable);
    }

    public void printInfo(String tag, String msg) {
        info(tag, msg);
    }

    public void printError(String tag, Throwable e) {
        error(tag, e);
    }

    private String getCommonPrefix(String tag, String level) {
        StringBuilder builder = new StringBuilder();
        builder.append(new Date().toLocaleString()).append(Token.SEPARATOR);
        builder.append(Thread.currentThread().getId()).append(Token.SEPARATOR);
        builder.append(Thread.currentThread().getName()).append(Token.SEPARATOR);
        builder.append(level).append(Token.SEPARATOR);
        builder.append(tag).append(Token.SEPARATOR);
        return builder.toString();
    }

    private void printLog(String msg, Throwable e) {
        if (e != null) {
            msg = msg + Token.SEPARATOR + exception2String(e);
        }
        systemOut(msg);
    }

    /* access modifiers changed from: protected */
    public String exception2String(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        try {
            throwable.getClass().getMethod(String.format("%s%s%s", new Object[]{"print", "Stack", "Trace"}), new Class[]{PrintWriter.class}).invoke(throwable, new Object[]{printWriter});
            printWriter.flush();
        } catch (Throwable th) {
        }
        return sw.toString();
    }
}
