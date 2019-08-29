package com.alipay.mobile.common.logging.impl;

import android.content.Context;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogEvent.Level;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.crash.ThrowableListener;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class TraceLoggerImpl implements TraceLogger {
    private LogContext a;
    private boolean b;
    private StringBuffer c;

    public TraceLoggerImpl(LogContext logContext) {
        Context context = logContext.getApplicationContext();
        this.a = logContext;
        this.b = LoggingUtil.isDebuggable(context);
    }

    public void verbose(String tag, String msg) {
        if (this.b) {
            Log.v(tag, a(msg, null));
        }
        this.a.appendLogEvent(new TraceLogEvent(tag, Level.VERBOSE, msg, null));
    }

    public void debug(String tag, String msg) {
        if (this.b) {
            Log.d(tag, a(msg, null));
        }
        this.a.appendLogEvent(new TraceLogEvent(tag, Level.DEBUG, msg, null));
    }

    public void info(String tag, String msg) {
        if (this.b) {
            Log.i(tag, a(msg, null));
        }
        this.a.appendLogEvent(new TraceLogEvent(tag, Level.INFO, msg, null));
    }

    public void warn(String tag, String msg) {
        if (this.b) {
            Log.w(tag, a(msg, null));
        }
        this.a.appendLogEvent(new TraceLogEvent(tag, Level.WARN, msg, null));
    }

    public void warn(String tag, Throwable tr) {
        String msg = LoggingUtil.throwableToString(tr);
        ThrowableListener.processThrowable(msg);
        if (this.b) {
            Log.w(tag, a(msg, null));
        }
        this.a.appendLogEvent(new TraceLogEvent(tag, Level.WARN, msg, null));
    }

    public void warn(String tag, String msg, Throwable tr) {
        String str = LoggingUtil.throwableToString(tr);
        ThrowableListener.processThrowable(str);
        if (this.b) {
            Log.w(tag, a(msg, str));
        }
        this.a.appendLogEvent(new TraceLogEvent(tag, Level.WARN, msg, str));
    }

    public void error(String tag, String msg) {
        if (this.b) {
            Log.e(tag, a(msg, null));
        }
        this.a.appendLogEvent(new TraceLogEvent(tag, Level.ERROR, msg, null));
    }

    public void error(String tag, Throwable tr) {
        String msg = LoggingUtil.throwableToString(tr);
        ThrowableListener.processThrowable(msg);
        if (this.b) {
            Log.e(tag, a(msg, null));
        }
        this.a.appendLogEvent(new TraceLogEvent(tag, Level.ERROR, msg, null));
    }

    public void error(String tag, String msg, Throwable tr) {
        String str = LoggingUtil.throwableToString(tr);
        ThrowableListener.processThrowable(str);
        if (this.b) {
            Log.e(tag, a(msg, str));
        }
        this.a.appendLogEvent(new TraceLogEvent(tag, Level.ERROR, msg, str));
    }

    public void print(String tag, String msg) {
        if (this.b) {
            Log.v(tag, msg);
        }
    }

    public void print(String tag, Throwable tr) {
        if (this.b) {
            Log.v(tag, "", tr);
        }
    }

    private String a(String msg, String tr) {
        if (this.c == null) {
            this.c = new StringBuffer();
        }
        try {
            this.c.append('[').append(Thread.currentThread().getName()).append("] ");
            this.c.append(msg);
            if (tr != null) {
                this.c.append(Token.SEPARATOR).append(tr);
            }
        } catch (Throwable th) {
        }
        String message = this.c.toString();
        this.c.setLength(0);
        return message;
    }
}
