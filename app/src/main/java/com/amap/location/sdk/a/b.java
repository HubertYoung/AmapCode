package com.amap.location.sdk.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.amap.location.sdk.setting.a;
import com.amap.location.sdk.setting.a.C0038a;
import com.taobao.accs.utl.AdapterUtilityImpl;

/* compiled from: AgooServiceHelper */
public class b {
    private Context a;
    private boolean b = false;

    public b(Context context) {
        this.a = context;
    }

    public void a() {
        if (!this.b) {
            try {
                C0038a a2 = a.a(this.a);
                long j = a2.a;
                long j2 = a2.b;
                if (j != -1) {
                    if (j2 != -1) {
                        long currentTimeMillis = System.currentTimeMillis();
                        if (j >= currentTimeMillis || currentTimeMillis >= j2) {
                            this.b = false;
                        } else {
                            c();
                        }
                    }
                }
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
                this.b = false;
            }
        }
    }

    private void c() {
        this.b = true;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.autonavi.minimap", AdapterUtilityImpl.channelService));
        try {
            this.a.startService(intent);
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
        }
    }

    public void b() {
        this.b = false;
    }
}
