package com.amap.bundle.cloudres;

import com.alibaba.wireless.security.securitybodysdk.BuildConfig;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.network.util.NetworkReachability.NetworkType;
import com.amap.bundle.network.util.NetworkReachability.a;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.VirtualApp;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

@VirtualApp(priority = 1000)
public class CloudResApp extends esh {
    private lp a = new lp() {
        public final void onConfigCallBack(int i) {
        }

        public final void onConfigResultCallBack(int i, String str) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("onConfigResultCallBack: status ");
                sb.append(i);
                sb.append(",result");
                sb.append(str);
                sb.append(Thread.currentThread().getId());
            }
            if (i != 4) {
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        break;
                    default:
                        return;
                }
            }
            mf.a().a(str, CloudResApp.this.isColdBoot());
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        InputStream inputStream = null;
        try {
            bqd bqd = a.a;
            if (bqd.a == null) {
                bqd.a = AMapAppGlobal.getApplication().getSharedPreferences("network_env_cfg", 0);
            }
            InputStream open = AMapAppGlobal.getApplication().getAssets().open(bqd.a.getBoolean("isInternal", BuildConfig.FLAVOR.equals(bqc.a().b())) ? "cloudres_internal" : "cloudres_master");
            try {
                mf.a().a(new String(ahe.a(open)), false);
                ahe.a((Closeable) open);
            } catch (IOException unused) {
                inputStream = open;
                ahe.a((Closeable) inputStream);
            } catch (Throwable th) {
                InputStream inputStream2 = open;
                th = th;
                inputStream = inputStream2;
                ahe.a((Closeable) inputStream);
                throw th;
            }
        } catch (IOException unused2) {
            ahe.a((Closeable) inputStream);
        } catch (Throwable th2) {
            th = th2;
            ahe.a((Closeable) inputStream);
            throw th;
        }
    }

    public void vAppCreate() {
        super.vAppCreate();
        lo.a().a((String) "cloud_resouce", this.a);
        NetworkReachability.a((a) new a() {
            public final void a(NetworkType networkType) {
                if (bno.a) {
                    new StringBuilder("onNetConnectivityChange: ").append(networkType);
                }
                if (NetworkReachability.b()) {
                    mf.a().a(false);
                }
            }
        });
    }

    public void vAppEnterBackground() {
        super.vAppEnterBackground();
        mf.a().a(isColdBoot());
    }
}
