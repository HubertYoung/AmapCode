package com.amap.bundle.statistics;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.server.aos.serverkey;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpUrlCollector {
    public static final String a = "HttpUrlCollector";
    /* access modifiers changed from: private */
    public static Set<String> b = Collections.synchronizedSet(new LinkedHashSet());
    /* access modifiers changed from: private */
    public static int c = 0;
    /* access modifiers changed from: private */
    public static boolean d = false;
    private static HandlerThread e = null;
    private static Handler f = null;
    /* access modifiers changed from: private */
    public static volatile boolean g = false;

    public enum SCENE {
        NORMAL("常规请求", "normal"),
        WEB_VIEW("WebView H5", "webview"),
        SHARE("分享链接", "share");
        
        private String desc;
        /* access modifiers changed from: private */
        public String value;

        private SCENE(String str, String str2) {
            this.desc = str;
            this.value = str2;
        }
    }

    static class a implements Runnable {
        private SCENE a;
        private String b;

        a(SCENE scene, String str) {
            this.a = scene;
            this.b = str;
        }

        public final void run() {
            if (this.a != SCENE.NORMAL) {
                int indexOf = this.b.indexOf("?");
                if (indexOf > 0) {
                    this.b = this.b.substring(0, indexOf);
                }
            }
            if (HttpUrlCollector.b.add(this.b)) {
                HttpUrlCollector.a(this.a.value, this.b);
                return;
            }
            if (HttpUrlCollector.d) {
                StringBuilder sb = new StringBuilder("already exist: ");
                sb.append(this.b);
                AMapLog.e(HttpUrlCollector.a, sb.toString());
            }
        }
    }

    static {
        lo.a().a((String) "collect_http_url", (lp) new lp() {
            public final void onConfigCallBack(int i) {
            }

            public final void onConfigResultCallBack(int i, String str) {
                if (HttpUrlCollector.d) {
                    String str2 = HttpUrlCollector.a;
                    StringBuilder sb = new StringBuilder("onConfigResultCallBack: ");
                    sb.append(i);
                    sb.append(", result: ");
                    sb.append(str);
                    AMapLog.i(str2, sb.toString());
                }
                boolean a = HttpUrlCollector.b(str);
                if (i == 4 || i == 0) {
                    HttpUrlCollector.g = a;
                    HttpUrlCollector.a();
                    return;
                }
                if (i == 1) {
                    if (HttpUrlCollector.g && !a) {
                        HttpUrlCollector.c();
                    } else if (!HttpUrlCollector.g && a) {
                        HttpUrlCollector.g = true;
                        HttpUrlCollector.a();
                    }
                } else if (i == 3) {
                    HttpUrlCollector.g = false;
                    HttpUrlCollector.c();
                }
            }
        });
    }

    public static void a() {
        if (g) {
            if (d) {
                AMapLog.d(a, "init collector");
            }
            HandlerThread handlerThread = new HandlerThread(a);
            e = handlerThread;
            handlerThread.start();
            Handler handler = new Handler(e.getLooper());
            f = handler;
            handler.post(new Runnable() {
                public final void run() {
                    HttpUrlCollector.d();
                }
            });
            in.a().b = new c() {
                public final void a(String str) {
                    HttpUrlCollector.a(SCENE.NORMAL, str);
                }
            };
        }
    }

    public static void a(SCENE scene, String str) {
        if (g && !TextUtils.isEmpty(str) && !str.startsWith("https") && f != null) {
            f.post(new a(scene, str));
        }
    }

    /* access modifiers changed from: private */
    public static boolean b(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            if (new JSONObject(str).optInt("open") == 1) {
                z = true;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return z;
    }

    static void b() {
        if (f != null) {
            f.post(new Runnable() {
                public final void run() {
                    try {
                        int size = HttpUrlCollector.b.size();
                        if (HttpUrlCollector.c < size) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(ahk.a(AMapAppGlobal.getApplication()).getAbsolutePath());
                            sb.append(File.separator);
                            sb.append("httpUrlCache");
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sb.toString(), true));
                            ArrayList arrayList = new ArrayList(HttpUrlCollector.b);
                            int size2 = arrayList.size();
                            if (HttpUrlCollector.d) {
                                StringBuilder sb2 = new StringBuilder("cacheIndex: ");
                                sb2.append(HttpUrlCollector.c);
                                sb2.append(", len: ");
                                sb2.append(size);
                                AMapLog.d(HttpUrlCollector.a, sb2.toString());
                            }
                            for (int h = HttpUrlCollector.c; h < size2; h++) {
                                bufferedWriter.write(serverkey.amapEncodeV2((String) arrayList.get(h)));
                                bufferedWriter.write("\n");
                            }
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            HttpUrlCollector.c = size;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void c() {
        if (d) {
            AMapLog.d(a, "release self");
        }
        in.a().b = null;
        if (e != null) {
            e.quitSafely();
        }
        if (f != null) {
            f.removeCallbacksAndMessages(null);
        }
        f = null;
        if (b != null) {
            b.clear();
        }
    }

    static /* synthetic */ void d() {
        c = b.size();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(ahk.a(AMapAppGlobal.getApplication()).getPath());
            sb.append(File.separator);
            sb.append("httpUrlCache");
            File file = new File(sb.toString());
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    b.add(serverkey.amapDecodeV2(readLine));
                }
                c = b.size();
                bufferedReader.close();
                fileReader.close();
            }
        } catch (Exception e2) {
            if (d) {
                AMapLog.i(a, e2.getMessage());
            }
        }
    }

    static /* synthetic */ void a(String str, String str2) {
        if (d) {
            StringBuilder sb = new StringBuilder("scene: ");
            sb.append(str);
            sb.append(", url: ");
            sb.append(str2);
            AMapLog.d(a, sb.toString());
        }
        HashMap hashMap = new HashMap();
        hashMap.put(H5AppUtil.scene, str);
        hashMap.put("url", str2);
        if (hashMap.size() != 0) {
            AosPostRequest aosPostRequest = new AosPostRequest();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.H5_LOG_URL));
            sb2.append("/ws/h5_log?");
            aosPostRequest.setUrl(sb2.toString());
            aosPostRequest.addSignParam("id");
            aosPostRequest.addSignParam("timestamp");
            aosPostRequest.addReqParams(hashMap);
            yq.a();
            yq.a((AosRequest) aosPostRequest, (AosResponseCallback<T>) null);
        }
    }
}
