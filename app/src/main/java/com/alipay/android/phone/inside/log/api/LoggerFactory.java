package com.alipay.android.phone.inside.log.api;

import com.alipay.android.phone.inside.log.LogCollect;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger;
import com.alipay.android.phone.inside.log.api.ex.ExceptionLogger;
import com.alipay.android.phone.inside.log.api.perf.PerfLogger;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.log.behavior.BehaviorLoggerImpl;
import com.alipay.android.phone.inside.log.biz.ContextManager;
import com.alipay.android.phone.inside.log.ex.ExceptionLoggerImpl;
import com.alipay.android.phone.inside.log.perf.PerfLoggerImpl;
import com.alipay.android.phone.inside.log.trace.TraceLoggerImpl;

public class LoggerFactory {
    private static TraceLogger a;
    private static BehaviorLogger b;
    private static ExceptionLogger c;
    private static PerfLogger d;

    public static synchronized void a(LogContext logContext) {
        synchronized (LoggerFactory.class) {
            ContextManager.a(logContext);
        }
    }

    public static void a() {
        LogCollect.a().b();
    }

    public static void b() {
        LogCollect.a().c();
    }

    public static synchronized PerfLogger c() {
        PerfLogger perfLogger;
        synchronized (LoggerFactory.class) {
            if (d == null) {
                d = new PerfLoggerImpl();
            }
            perfLogger = d;
        }
        return perfLogger;
    }

    public static synchronized BehaviorLogger d() {
        BehaviorLogger behaviorLogger;
        synchronized (LoggerFactory.class) {
            try {
                if (b == null) {
                    b = new BehaviorLoggerImpl();
                }
                behaviorLogger = b;
            }
        }
        return behaviorLogger;
    }

    public static synchronized ExceptionLogger e() {
        ExceptionLogger exceptionLogger;
        synchronized (LoggerFactory.class) {
            try {
                if (c == null) {
                    c = new ExceptionLoggerImpl();
                }
                exceptionLogger = c;
            }
        }
        return exceptionLogger;
    }

    public static synchronized TraceLogger f() {
        TraceLogger traceLogger;
        synchronized (LoggerFactory.class) {
            try {
                if (a == null) {
                    a = new TraceLoggerImpl();
                }
                traceLogger = a;
            }
        }
        return traceLogger;
    }
}
