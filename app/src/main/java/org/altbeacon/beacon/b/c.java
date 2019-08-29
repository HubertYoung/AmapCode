package org.altbeacon.beacon.b;

import com.alipay.mobile.common.logging.api.LoggerFactory;

/* compiled from: InfoAndroidLogger */
final class c extends a {
    c() {
    }

    public final void a(String tag, String message, Object... args) {
        LoggerFactory.getTraceLogger().debug(tag, a(message, args));
    }

    public final void b(String tag, String message, Object... args) {
        LoggerFactory.getTraceLogger().info(tag, a(message, args));
    }

    public final void c(String tag, String message, Object... args) {
        LoggerFactory.getTraceLogger().warn(tag, a(message, args));
    }

    public final void a(Throwable t, String tag, String message, Object... args) {
        LoggerFactory.getTraceLogger().warn(tag, a(message, args), t);
    }

    public final void d(String tag, String message, Object... args) {
        LoggerFactory.getTraceLogger().error(tag, a(message, args));
    }

    public final void b(Throwable t, String tag, String message, Object... args) {
        LoggerFactory.getTraceLogger().error(tag, a(message, args), t);
    }
}
