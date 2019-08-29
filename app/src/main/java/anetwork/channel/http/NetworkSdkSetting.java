package anetwork.channel.http;

import android.app.Application;
import android.content.Context;
import anet.channel.entity.ENV;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkSdkSetting implements Serializable {
    public static ENV CURRENT_ENV = ENV.ONLINE;
    private static final String TAG = "anet.NetworkSdkSetting";
    private static Context context;
    private static HashMap<String, Object> initParams;
    private static AtomicBoolean isInit = new AtomicBoolean(false);

    public static void init(Context context2) {
        if (context2 != null) {
            try {
                if (isInit.compareAndSet(false, true)) {
                    cl.d(TAG, "NetworkSdkSetting init", null, new Object[0]);
                    context = context2;
                    m.a(context2);
                    initTaobaoAdapter();
                    ee.a();
                    ds.a();
                    dt.a(context2);
                    r.a(context2);
                }
            } catch (Throwable unused) {
                cl.e(TAG, "Network SDK initial failed!", null, new Object[0]);
            }
        }
    }

    public static void init(Application application, HashMap<String, Object> hashMap) {
        try {
            m.a((String) hashMap.get("ttid"));
            m.c((String) hashMap.get("deviceId"));
            initParams = new HashMap<>(hashMap);
            init(application.getApplicationContext());
            initParams = null;
        } catch (Exception unused) {
            cl.e(TAG, "Network SDK initial failed!", null, new Object[0]);
        }
    }

    public static void setTtid(String str) {
        m.a(str);
    }

    public static Context getContext() {
        return context;
    }

    private static void initTaobaoAdapter() {
        try {
            db.a("anet.channel.TaobaoNetworkAdapter", "init", new Class[]{Context.class, HashMap.class}, context, initParams);
            cl.b(TAG, "init taobao adapter success", null, new Object[0]);
        } catch (Exception e) {
            cl.b(TAG, "initTaobaoAdapter failed. maybe not taobao app", null, e);
        }
    }
}
