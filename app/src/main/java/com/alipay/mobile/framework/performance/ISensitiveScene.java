package com.alipay.mobile.framework.performance;

public interface ISensitiveScene {
    boolean isSensitiveScene();

    void sensitiveRun(Runnable runnable);

    void sensitiveRun(Runnable runnable, long j);

    void sensitiveRunForHomeBanner(Runnable runnable, long j);
}
