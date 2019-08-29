package com.amap.location.sdk.e;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.common.d.d.b;
import com.amap.location.common.d.d.c;
import com.amap.location.common.f.e;
import com.amap.location.common.f.h;
import com.autonavi.jni.alc.ALCManager;
import com.autonavi.jni.alc.inter.IALCCloudStrategy;
import com.autonavi.minimap.alc.model.ALCTriggerType;
import java.io.File;

/* compiled from: LogUtil */
public class d {
    private static boolean a = false;
    /* access modifiers changed from: private */
    public static a b = null;
    private static boolean c = true;
    /* access modifiers changed from: private */
    public static String d = "main";

    /* compiled from: LogUtil */
    static class a implements IALCCloudStrategy {
        private Context a;
        private String b = "";

        public a(Context context) {
            if (context != null) {
                this.a = context.getApplicationContext();
            }
        }

        public void a(String str) {
            this.b = str;
            try {
                ALCManager.getInstance().setRecordCloudStategy(d.b);
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }

        public int currentNetworkStatus() {
            int a2 = h.a(this.a);
            if (a2 == 1) {
                return 2;
            }
            return a2 == 0 ? 5 : 1;
        }

        public String cloudStrategy() {
            return this.b;
        }
    }

    public static void a(Context context) {
        if (!a) {
            boolean b2 = f.b(context);
            c = b2;
            d = b2 ? "main" : DictionaryKeys.SECTION_LOC_INFO;
            if (c) {
                b(context);
            }
            com.amap.location.common.d.d.a aVar = new com.amap.location.common.d.d.a();
            aVar.a((b) new b() {
                public final boolean a() {
                    return true;
                }

                public final void a(String str) {
                    com.amap.location.sdk.b.a.d.a(104, str);
                }
            });
            aVar.a(false);
            aVar.b(false);
            aVar.c(true);
            try {
                String a2 = c.a(context);
                if (!TextUtils.isEmpty(a2)) {
                    e.a(new File(a2));
                    com.amap.location.common.d.a.a(context, aVar.a(c.SDK, e.a(context)));
                    com.amap.location.common.d.a.a((com.amap.location.common.d.c) new com.amap.location.common.d.c() {
                        public final void a(String str, String str2) {
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append(d.d);
                                sb.append("_");
                                sb.append(str);
                                AMapLog.info("paas.location", sb.toString(), str2);
                            } catch (Throwable th) {
                                com.amap.location.common.d.a.a(th);
                            }
                        }

                        public final void b(String str, String str2) {
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append(d.d);
                                sb.append("_");
                                sb.append(str);
                                AMapLog.warning("paas.location", sb.toString(), str2);
                            } catch (Throwable th) {
                                com.amap.location.common.d.a.a(th);
                            }
                        }

                        public final void c(String str, String str2) {
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append(d.d);
                                sb.append("_");
                                sb.append(str);
                                AMapLog.error("paas.location", sb.toString(), str2);
                            } catch (Throwable th) {
                                com.amap.location.common.d.a.a(th);
                            }
                        }

                        public final void d(String str, String str2) {
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append(d.d);
                                sb.append("_");
                                sb.append(str);
                                AMapLog.debug("paas.location", sb.toString(), str2);
                            } catch (Throwable th) {
                                com.amap.location.common.d.a.a(th);
                            }
                        }
                    });
                    if (!c) {
                        b = new a(context);
                        ALCManager.getInstance().setRecordCloudStategy(b);
                    }
                    a = true;
                }
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }

    public static void a(Context context, boolean z) {
        try {
            context.getSharedPreferences("SP_TRACE_CLOUD", 0).edit().putBoolean("is_trace_open", z).apply();
            com.amap.location.sdk.b.a.d.a(z);
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
        }
    }

    public static void b(Context context, boolean z) {
        try {
            context.getSharedPreferences("SP_TRACE_CLOUD", 0).edit().putBoolean("is_key_log_switch", z).apply();
            com.amap.location.sdk.b.a.d.b(z);
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
        }
    }

    public static void a() {
        try {
            AMapLog.upload(ALCTriggerType.appEnterForeground);
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    public static void a(String str) {
        if (b != null) {
            b.a(str);
        }
    }

    private static void b(final Context context) {
        new Thread("checkCloudConfig") {
            public final void run() {
                try {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("SP_TRACE_CLOUD", 0);
                    com.amap.location.sdk.b.a.d.a(sharedPreferences.getBoolean("is_trace_open", false));
                    com.amap.location.sdk.b.a.d.b(sharedPreferences.getBoolean("is_key_log_switch", false));
                } catch (Exception e) {
                    com.amap.location.common.d.a.a((Throwable) e);
                }
            }
        }.start();
    }
}
