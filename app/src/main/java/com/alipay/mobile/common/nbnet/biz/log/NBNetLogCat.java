package com.alipay.mobile.common.nbnet.biz.log;

public class NBNetLogCat {
    public static final void a(String tag, String msg) {
        NBNetLogFactory.a().info("nbnet_" + tag, msg);
    }

    public static final void b(String tag, String msg) {
        NBNetLogFactory.a().verbose("nbnet_" + tag, msg);
    }

    public static final void c(String tag, String msg) {
        NBNetLogFactory.a().debug("nbnet_" + tag, msg);
    }

    public static final void d(String tag, String msg) {
        NBNetLogFactory.a().warn("nbnet_" + tag, msg);
    }

    public static final void a(String tag, Throwable throwable) {
        NBNetLogFactory.a().warn("nbnet_" + tag, throwable);
    }

    public static final void a(String tag, String msg, Throwable throwable) {
        NBNetLogFactory.a().warn("nbnet_" + tag, msg, throwable);
    }

    public static final void e(String tag, String msg) {
        NBNetLogFactory.a().error("nbnet_" + tag, msg);
    }

    public static final void b(String tag, Throwable throwable) {
        NBNetLogFactory.a().error("nbnet_" + tag, throwable);
    }

    public static final void b(String tag, String msg, Throwable throwable) {
        NBNetLogFactory.a().error("nbnet_" + tag, msg, throwable);
    }

    public static final void f(String tag, String msg) {
        NBNetLogFactory.a().printInfo("nbnet_" + tag, msg);
    }
}
