package defpackage;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.bundle.logs.AMapLog;
import com.tencent.open.SocialConstants;
import java.io.File;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: eok reason: default package */
/* compiled from: ScreenShotManager */
public final class eok {
    public static Intent a = null;
    private static a b = null;
    /* access modifiers changed from: private */
    public static Context c = null;
    private static eok d = null;
    private static boolean e = true;
    /* access modifiers changed from: private */
    public static boolean f = false;
    private static volatile HashMap<String, Integer> g = null;
    private static int h = 3;
    /* access modifiers changed from: private */
    public static String i = "QAInfo";
    private static long j = 600000;
    private static volatile String k = "default";
    private static boolean l = true;
    private static Runnable m = new Runnable() {
        public final void run() {
            eok.f = true;
            AMapLog.d(eok.i, "upload begin");
            StringBuilder sb = new StringBuilder();
            sb.append(eok.c.getFilesDir().getAbsolutePath());
            sb.append(File.separator);
            sb.append("QAScreenShot");
            File file = new File(sb.toString());
            if (!file.exists() || !file.isDirectory()) {
                AMapLog.d(eok.i, "无图片文件夹");
            } else {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    for (File file2 : listFiles) {
                        if (!eok.b()) {
                            AMapLog.d(eok.i, "upload no wifi ");
                        } else if (eoe.a(file2)) {
                            eok.a(file2.getAbsolutePath());
                            String c = eok.i;
                            StringBuilder sb2 = new StringBuilder("upload success: ");
                            sb2.append(file2.getName());
                            AMapLog.d(c, sb2.toString());
                        } else {
                            String c2 = eok.i;
                            StringBuilder sb3 = new StringBuilder("upload fail: ");
                            sb3.append(file2.getName());
                            AMapLog.d(c2, sb3.toString());
                        }
                    }
                } else {
                    AMapLog.d(eok.i, "文件夹无图片");
                }
            }
            eok.a();
            eok.f = false;
        }
    };

    /* renamed from: eok$a */
    /* compiled from: ScreenShotManager */
    public static final class a extends Handler {
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                if (message.obj instanceof String) {
                    eok.b((String) message.obj);
                } else {
                    AMapLog.d(eok.i, "msg.obj is not a string instance");
                }
            } else if (message.what == 2) {
                eok.d();
            } else {
                if (message.what == 3) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
                    sb.append(File.separator);
                    sb.append("QACaseInfo");
                    sb.append(File.separator);
                    sb.append("record.txt");
                    File file = new File(sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(System.currentTimeMillis());
                    sb2.append(AUScreenAdaptTool.PREFIX_ID);
                    sb2.append(message.obj);
                    sb2.append("\n");
                    eom.a(file, sb2.toString());
                }
            }
        }
    }

    public static void a() {
        if (b != null) {
            b.postDelayed(m, j);
        }
    }

    public static void a(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean b() {
        return ((ConnectivityManager) c.getSystemService("connectivity")).getNetworkInfo(1).isConnected();
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0058  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void b(java.lang.String r4) {
        /*
            java.util.HashMap<java.lang.String, java.lang.Integer> r0 = g
            r1 = 0
            if (r0 != 0) goto L_0x0006
            goto L_0x005e
        L_0x0006:
            boolean r0 = l
            r2 = 1
            if (r0 == 0) goto L_0x0037
            if (r4 == 0) goto L_0x0023
            java.lang.String r0 = "@@"
            boolean r0 = r4.contains(r0)
            if (r0 == 0) goto L_0x0023
            java.lang.String r0 = "@@"
            java.lang.String[] r0 = r4.split(r0)
            if (r0 == 0) goto L_0x0023
            int r3 = r0.length
            if (r3 <= 0) goto L_0x0023
            r0 = r0[r1]
            goto L_0x0024
        L_0x0023:
            r0 = r4
        L_0x0024:
            java.util.HashMap<java.lang.String, java.lang.Integer> r3 = g
            boolean r3 = r3.containsKey(r0)
            if (r3 != 0) goto L_0x005e
            java.util.HashMap<java.lang.String, java.lang.Integer> r1 = g
            java.lang.Integer r3 = java.lang.Integer.valueOf(r2)
            r1.put(r0, r3)
        L_0x0035:
            r1 = 1
            goto L_0x005e
        L_0x0037:
            if (r4 == 0) goto L_0x004f
            java.lang.String r0 = "@@"
            boolean r0 = r4.contains(r0)
            if (r0 == 0) goto L_0x004f
            java.lang.String r0 = "@@"
            java.lang.String[] r0 = r4.split(r0)
            if (r0 == 0) goto L_0x004f
            int r3 = r0.length
            if (r3 <= 0) goto L_0x004f
            r0 = r0[r1]
            goto L_0x0050
        L_0x004f:
            r0 = r4
        L_0x0050:
            java.util.HashMap<java.lang.String, java.lang.Integer> r3 = g
            boolean r3 = r3.containsKey(r0)
            if (r3 == 0) goto L_0x005e
            java.util.HashMap<java.lang.String, java.lang.Integer> r1 = g
            r1.remove(r0)
            goto L_0x0035
        L_0x005e:
            if (r1 == 0) goto L_0x00ce
            java.lang.String r0 = i
            java.lang.String r1 = "isNewPic=true"
            com.amap.bundle.logs.AMapLog.d(r0, r1)
            android.content.Intent r0 = a
            if (r0 != 0) goto L_0x0084
            android.content.Intent r4 = new android.content.Intent
            android.content.Context r0 = c
            java.lang.Class<com.autonavi.profile.QATestInfo.ScreenShotActivity> r1 = com.autonavi.profile.QATestInfo.ScreenShotActivity.class
            r4.<init>(r0, r1)
            java.lang.String r0 = "android.intent.category.DEFAULT"
            r4.addCategory(r0)
            r0 = 268435456(0x10000000, float:2.5243549E-29)
            r4.addFlags(r0)
            android.content.Context r0 = c
            r0.startActivity(r4)
            return
        L_0x0084:
            java.lang.String r0 = k
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00af
            java.lang.String r4 = defpackage.eob.a(r4)
            android.content.Context r0 = c
            r1 = -1
            android.content.Intent r2 = a
            eol r0 = defpackage.eol.a(r0, r1, r2)
            r0.a = r4
            r1 = 0
            r0.a(r1)
            java.lang.String r0 = i
            java.lang.String r1 = "Screenshot Finish: "
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r4 = r1.concat(r4)
            com.amap.bundle.logs.AMapLog.d(r0, r4)
            return
        L_0x00af:
            java.lang.String r0 = i
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "last:"
            r1.<init>(r2)
            java.lang.String r2 = k
            r1.append(r2)
            java.lang.String r2 = "当前："
            r1.append(r2)
            r1.append(r4)
            java.lang.String r4 = r1.toString()
            com.amap.bundle.logs.AMapLog.d(r0, r4)
            return
        L_0x00ce:
            java.lang.String r4 = i
            java.lang.String r0 = "isNewPic=false"
            com.amap.bundle.logs.AMapLog.d(r4, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eok.b(java.lang.String):void");
    }

    static /* synthetic */ void d() {
        AMapLog.e(i, "REQ");
        String a2 = eoe.a();
        if ("".equals(a2)) {
            AMapLog.d(i, "req result equals null");
            e = true;
            h--;
            return;
        }
        AMapLog.d(i, "REQ OK");
        try {
            JSONObject jSONObject = new JSONObject(a2);
            String str = (String) jSONObject.get("type");
            if ("all".equals(str)) {
                g = new HashMap<>();
                AMapLog.d(i, "REQ 可以任意截图");
            } else if (SocialConstants.PARAM_ONLY.equals(str)) {
                l = false;
                JSONArray jSONArray = jSONObject.getJSONArray("piclist");
                if (jSONArray != null) {
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        g.put((String) jSONArray.get(i2), Integer.valueOf(1));
                    }
                    AMapLog.d(i, "REQ 返回需要截图list");
                }
            } else {
                g = new HashMap<>();
                JSONArray jSONArray2 = jSONObject.getJSONArray("piclist");
                if (jSONArray2 != null) {
                    for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                        g.put((String) jSONArray2.get(i3), Integer.valueOf(1));
                    }
                    AMapLog.d(i, "REQ 返回已有截图list");
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e = false;
    }
}
