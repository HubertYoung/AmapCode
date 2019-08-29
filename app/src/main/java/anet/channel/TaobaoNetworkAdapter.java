package anet.channel;

import android.content.Context;
import anet.channel.entity.ENV;
import anet.channel.strategy.ConnProtocol;
import com.taobao.applink.util.TBAppLinkUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaobaoNetworkAdapter implements Serializable {
    public static AtomicBoolean isInited = new AtomicBoolean();

    public static void init(Context context, HashMap<String, Object> hashMap) {
        if (isInited.compareAndSet(false, true)) {
            cl.a((a) new ap());
            ds.a((dr) new ad());
            x.a(new y());
            al.a(new w());
            ck.a(new Runnable() {
                public final void run() {
                    try {
                        ac acVar = new ac();
                        ac.a();
                        dp.a(acVar, new dq() {
                            public final boolean a(Map<String, String> map) {
                                return "weex".equals(map.get("f-refer"));
                            }
                        });
                    } catch (Exception unused) {
                    }
                }
            }, c.b);
            if (hashMap != null) {
                try {
                    if (TBAppLinkUtil.TAOPACKAGENAME.equals((String) hashMap.get("process"))) {
                        String str = (String) hashMap.get("onlineAppKey");
                        registerPresetSession("guide-acs.m.taobao.com", str, ConnProtocol.valueOf("http2", "0rtt", "acs"), true);
                        ConnProtocol valueOf = ConnProtocol.valueOf("http2", "0rtt", "cdn");
                        registerPresetSession("gw.alicdn.com", str, valueOf, false);
                        registerPresetSession("dorangesource.alicdn.com", str, valueOf, false);
                        registerPresetSession("ossgw.alicdn.com", str, valueOf, false);
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    private static void registerPresetSession(String str, String str2, ConnProtocol connProtocol, boolean z) {
        a.a.a(str, connProtocol);
        if (z) {
            t a = t.a(str, z, false, null, null);
            a aVar = new a();
            aVar.b = str2;
            aVar.c = ENV.ONLINE;
            r.a(aVar.a()).a(a);
        }
    }
}
