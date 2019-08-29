package defpackage;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.LongSparseArray;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.jni.alc.ALCManager;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.autonavi.minimap.app.init.Process;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.List;

/* renamed from: ckh reason: default package */
/* compiled from: ALC */
public class ckh extends cky {
    /* access modifiers changed from: private */
    public final String a = ckh.class.getSimpleName();
    /* access modifiers changed from: private */
    public clu b = new clu("");
    /* access modifiers changed from: private */
    public cls c = new cls("");

    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "ALC";
    }

    private static LongSparseArray a(MapSharePreference mapSharePreference) {
        List<Long> a2 = wp.a();
        LongSparseArray longSparseArray = new LongSparseArray();
        int size = a2.size();
        for (int i = 0; i < size; i++) {
            longSparseArray.put(a2.get(i).longValue(), Boolean.valueOf(mapSharePreference.getBooleanValue(String.valueOf(a2.get(i)), true)));
        }
        return longSparseArray;
    }

    public final void a(Application application) {
        String appSDCardFileDir = FileUtil.getAppSDCardFileDir();
        if (!TextUtils.isEmpty(appSDCardFileDir)) {
            a aVar = new a();
            File file = new File(appSDCardFileDir, LocationParams.PARA_AMAP_CLOUD_ALC);
            if (ahs.a(Process.LOTUSPOOL.name, application.getApplicationContext())) {
                StringBuilder sb = new StringBuilder("alc-");
                sb.append(ahs.b(application.getApplicationContext()));
                file = new File(appSDCardFileDir, sb.toString());
                if (bno.a) {
                    aVar.a(524222);
                    aVar.a(ALCLogLevel.DEFAULT_LOG_LEVEL);
                }
                aVar.a.k = new clv();
                LongSparseArray longSparseArray = new LongSparseArray();
                longSparseArray.put(wp.a, Boolean.TRUE);
                aVar.a(longSparseArray);
            } else if (ahs.a(Process.LOCATION.name, application.getApplicationContext())) {
                file = new File(appSDCardFileDir, "alc-loc");
                if (bno.a) {
                    aVar.a(524222);
                    aVar.a(ALCLogLevel.DEFAULT_LOG_LEVEL);
                    LongSparseArray longSparseArray2 = new LongSparseArray();
                    longSparseArray2.put(wp.a, Boolean.TRUE);
                    aVar.a(longSparseArray2);
                }
            } else if (bno.a) {
                MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                aVar.a(mapSharePreference.getLongValue("alc_group", 524222));
                aVar.a(mapSharePreference.getIntValue("alc_level", ALCLogLevel.DEFAULT_LOG_LEVEL));
                aVar.a.l = mapSharePreference.getIntValue("alc_console", 3);
                aVar.a(a(mapSharePreference));
            }
            aVar.a.a = bno.a;
            aVar.a.b = NetworkParam.getDiv();
            aVar.a.c = file.getAbsolutePath();
            aVar.a.i = new clr();
            aVar.a.j = new clt();
            aVar.a.d = 20;
            aVar.a.e = 153600;
            aVar.a.h = 7;
            Context applicationContext = application.getApplicationContext();
            if (TextUtils.isEmpty(aVar.a.b) || TextUtils.isEmpty(aVar.a.c) || aVar.a.i == null) {
                throw new InvalidParameterException("Logs日志参数设置错误!");
            }
            if (aVar.a.d <= 0) {
                aVar.a.d = wq.o;
            }
            if (aVar.a.e <= 0) {
                aVar.a.e = wq.n;
            }
            AMapLog.init(applicationContext, aVar.a);
            if (ahs.a(application)) {
                if (bno.a) {
                    AMapLog.debug("paas.main", this.a, "initCloudStrategy()");
                }
                lo.a().a((String) "alc_cloud_control", (lp) new lp() {
                    public final void onConfigCallBack(int i) {
                    }

                    public final void onConfigResultCallBack(int i, String str) {
                        if (bno.a) {
                            String a2 = ckh.this.a;
                            StringBuilder sb = new StringBuilder("AMapLogCloudStrategy()-state:");
                            sb.append(i);
                            sb.append(",result:");
                            sb.append(str);
                            AMapLog.debug("paas.main", a2, sb.toString());
                        }
                        ckh.this.b.a = str;
                        ALCManager.getInstance().setCloudStategy(ckh.this.b);
                    }
                });
                lo.a().a((String) "alc_record_control", (lp) new lp() {
                    public final void onConfigCallBack(int i) {
                    }

                    public final void onConfigResultCallBack(int i, String str) {
                        if (bno.a) {
                            String a2 = ckh.this.a;
                            StringBuilder sb = new StringBuilder("AMapLogRecordCloudStrategy()-state:");
                            sb.append(i);
                            sb.append(",result:");
                            sb.append(str);
                            AMapLog.debug("paas.main", a2, sb.toString());
                        }
                        ckh.this.c.a = str;
                        ALCManager.getInstance().setRecordCloudStategy(ckh.this.c);
                    }
                });
                String appSDCardFileDir2 = FileUtil.getAppSDCardFileDir();
                if (TextUtils.isEmpty(appSDCardFileDir2)) {
                    AMapLog.error("paas.main", this.a, "initPlayback()-path:".concat(String.valueOf(appSDCardFileDir2)));
                } else if (!AMapLog.initPlayback(new File(appSDCardFileDir2, "alc/playback").getAbsolutePath(), 2097152, 52428800, 10080, "{\n  \"mask\": {\n    \"mainType\": \"0xffff\"\n  },\n  \"ex_config\": {\n    \"upload\": [{\n        \"key\": \"cloudModuleName\",\n        \"value\": \"opt_record\"\n      }, {\n        \"key\": \"maxBlobSize\",\n        \"value\": \"204800\"\n      },\n      {\n        \"key\": \"flowLimitedPerMonth\",\n        \"value\": \"524288000\"\n      },\n      {\n        \"key\": \"flowLimitedPerDay\",\n        \"value\": \"20971520\"\n      }\n    ]\n  }\n}")) {
                    AMapLog.error("paas.main", this.a, "initPlayback() error!!!");
                }
            }
        }
    }
}
