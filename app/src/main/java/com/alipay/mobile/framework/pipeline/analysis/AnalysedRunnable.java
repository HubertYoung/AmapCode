package com.alipay.mobile.framework.pipeline.analysis;

import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.reflect.Field;

public class AnalysedRunnable implements Runnable {
    private static boolean a;
    private static Field b;
    private Runnable c;

    public AnalysedRunnable(Runnable runnable) {
        this.c = runnable;
    }

    public void run() {
        if (this.c != null) {
            if (this.c instanceof AnalysedRunnable) {
                this.c.run();
                return;
            }
            String runnableName = a();
            try {
                AnalysedRunnableManager.startRecord(runnableName);
            } catch (Throwable t) {
                if (!a) {
                    a = true;
                    TraceLogger.e((String) "AnalysedRunnable", t);
                }
            }
            this.c.run();
            try {
                AnalysedRunnableManager.endRecord(runnableName);
            } catch (Throwable t2) {
                if (!a) {
                    a = true;
                    TraceLogger.e((String) "AnalysedRunnable", t2);
                }
            }
        }
    }

    private String a() {
        try {
            String runnableName = this.c.getClass().getName();
            if (!"com.alipay.mobile.framework.service.common.OrderedExecutor$Task".equals(runnableName)) {
                return runnableName;
            }
            if (b == null) {
                Field field = getClass().getClassLoader().loadClass("com.alipay.mobile.framework.service.common.OrderedExecutor$Task").getDeclaredField("runningTaskName");
                field.setAccessible(true);
                b = field;
            }
            return (String) b.get(this.c);
        } catch (Throwable t) {
            if (a) {
                return "";
            }
            a = true;
            TraceLogger.e((String) "AnalysedRunnable", t);
            return "";
        }
    }
}
