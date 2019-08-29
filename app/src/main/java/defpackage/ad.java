package defpackage;

import com.taobao.orange.OrangeConfig;
import com.taobao.orange.OrangeConfigListenerV1;

/* renamed from: ad reason: default package */
/* compiled from: OrangeConfigImpl */
public final class ad implements dr {
    private static boolean a = false;

    static {
        try {
            Class.forName("com.taobao.orange.OrangeConfig");
            a = true;
        } catch (Exception unused) {
            a = false;
        }
    }

    public final void a() {
        if (!a) {
            cl.c("awcn.OrangeConfigImpl", "no orange sdk", null, new Object[0]);
            return;
        }
        try {
            OrangeConfig.getInstance().registerListener(new String[]{"networkSdk"}, new OrangeConfigListenerV1() {
            });
            a("networkSdk", "network_empty_scheme_https_switch", "true");
        } catch (Exception unused) {
            cl.e("awcn.OrangeConfigImpl", "register fail", null, new Object[0]);
        }
    }

    public final void b() {
        if (!a) {
            cl.c("awcn.OrangeConfigImpl", "no orange sdk", null, new Object[0]);
        } else {
            OrangeConfig.getInstance().unregisterListener(new String[]{"networkSdk"});
        }
    }

    private static String a(String... strArr) {
        if (!a) {
            cl.c("awcn.OrangeConfigImpl", "no orange sdk", null, new Object[0]);
            return null;
        }
        try {
            return OrangeConfig.getInstance().getConfig(strArr[0], strArr[1], strArr[2]);
        } catch (Exception unused) {
            cl.e("awcn.OrangeConfigImpl", "get config failed!", null, new Object[0]);
            return null;
        }
    }
}
