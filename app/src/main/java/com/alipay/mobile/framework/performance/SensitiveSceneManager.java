package com.alipay.mobile.framework.performance;

public class SensitiveSceneManager implements ISensitiveScene {
    private static SensitiveSceneManager a = new SensitiveSceneManager();
    private ISensitiveScene b;

    public static SensitiveSceneManager getInstance() {
        return a;
    }

    public void attach(ISensitiveScene instance) {
        this.b = instance;
    }

    public void sensitiveRun(Runnable runnable, long timeout) {
        if (this.b != null) {
            this.b.sensitiveRun(runnable, timeout);
        }
    }

    public void sensitiveRun(Runnable runnable) {
        if (this.b != null) {
            this.b.sensitiveRun(runnable);
        }
    }

    public void sensitiveRunForHomeBanner(Runnable runnable, long timeout) {
        if (this.b != null) {
            this.b.sensitiveRunForHomeBanner(runnable, timeout);
        }
    }

    public boolean isSensitiveScene() {
        return this.b != null && this.b.isSensitiveScene();
    }
}
