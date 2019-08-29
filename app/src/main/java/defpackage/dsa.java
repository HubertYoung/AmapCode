package defpackage;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.blutils.device.DeviceInfo;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.alimama.AlimamaRequestHolder;
import com.autonavi.minimap.alimama.param.SplashScreenRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback.WorkThread;
import com.autonavi.minimap.net.Callback;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.HashMap;

/* renamed from: dsa reason: default package */
/* compiled from: Requester */
public final class dsa {
    /* access modifiers changed from: private */
    public static SplashScreenRequest a;
    /* access modifiers changed from: private */
    public static dkn b;

    public static void a() {
        ahm.c(new Runnable() {
            public final void run() {
                if (NetworkReachability.b()) {
                    dsa.b();
                    AlimamaRequestHolder.getInstance().sendSplashScreen(dsa.a, dsa.b, new Callback());
                    emd.a("", H5PageData.KEY_UC_START);
                }
            }
        });
    }

    static /* synthetic */ void b() {
        String str;
        SplashScreenRequest splashScreenRequest = new SplashScreenRequest();
        a = splashScreenRequest;
        splashScreenRequest.c = 1.0d;
        a.h = 2;
        SplashScreenRequest splashScreenRequest2 = a;
        StringBuilder sb = new StringBuilder();
        sb.append(ags.a((Context) AMapAppGlobal.getApplication()).width());
        sb.append(DictionaryKeys.CTRLXY_X);
        sb.append(ags.a((Context) AMapAppGlobal.getApplication()).height());
        splashScreenRequest2.b = sb.toString();
        a.c = (double) ags.c(AMapAppGlobal.getApplication());
        SplashScreenRequest splashScreenRequest3 = a;
        String str2 = "unknown";
        NetworkInfo a2 = NetworkReachability.a((Context) AMapAppGlobal.getApplication());
        if (a2 != null) {
            if (a2.isConnected()) {
                int type = a2.getType();
                if (type == 1) {
                    str2 = "wifi";
                } else if (type == 0) {
                    str2 = "cell";
                }
            } else {
                str2 = "offline";
            }
        }
        splashScreenRequest3.d = str2;
        SplashScreenRequest splashScreenRequest4 = a;
        NetworkInfo a3 = NetworkReachability.a((Context) AMapAppGlobal.getApplication());
        if (a3 == null || !a3.isConnected()) {
            str = "";
        } else {
            str = a3.getSubtypeName();
        }
        splashScreenRequest4.e = str;
        SplashScreenRequest splashScreenRequest5 = a;
        AMapAppGlobal.getApplication();
        String str3 = "";
        switch (kx.a()) {
            case 1:
                str3 = "01";
                break;
            case 2:
                str3 = "03";
                break;
            case 3:
                str3 = "00";
                break;
        }
        splashScreenRequest5.f = str3;
        SplashScreenRequest splashScreenRequest6 = a;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(String.valueOf(LocationInstrument.getInstance().getLatestPosition().getLatitude()));
        sb2.append(",");
        sb2.append(String.valueOf(LocationInstrument.getInstance().getLatestPosition().getLongitude()));
        splashScreenRequest6.g = sb2.toString();
        a.h = 2;
        a.i = Build.BRAND;
        a.j = Build.MODEL;
        a.k = VERSION.RELEASE;
        a.l = String.valueOf(DeviceInfo.getInstance(AMapAppGlobal.getApplication()).getMcc());
        a.m = agq.d(AMapAppGlobal.getApplication());
        b = new dkn(WorkThread.WORK, new HashMap(), HttpConstants.CONNECTION_TIME_OUT, 0);
    }
}
