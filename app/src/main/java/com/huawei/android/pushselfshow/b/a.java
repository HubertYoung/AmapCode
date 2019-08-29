package com.huawei.android.pushselfshow.b;

import android.support.v4.app.NotificationCompat;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.huawei.android.pushagent.a.a.c;
import com.taobao.agoo.control.data.BaseDO;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements Serializable {
    public String A;
    public String B;
    public String C;
    public String D;
    public String E;
    public String F = "";
    public int G = 1;
    public int H = 0;
    public String I;
    public String J;
    public String K;
    public int L = com.huawei.android.pushselfshow.c.a.STYLE_1.ordinal();
    public int M = 0;
    public String[] N = null;
    public String[] O = null;
    public String[] P = null;
    public int Q = 0;
    public String[] R = null;
    public String S = "";
    public String T = "";
    public String a = "";
    public String b;
    public String c;
    public String d;
    public String e;
    public int f;
    public String g;
    public int h;
    public String i;
    public int j;
    public int k;
    public String l;
    public String m = "";
    public String n;
    public String o;
    public String p;
    public String q;
    public String r = "";
    public String s;
    public String t;
    public String u;
    public String v;
    public String w;
    public String x;
    public String y;
    public String z = "";

    public a() {
    }

    public a(byte[] bArr, byte[] bArr2) {
        try {
            this.J = new String(bArr, "UTF-8");
            this.K = new String(bArr2, "UTF-8");
        } catch (Exception unused) {
            c.d("PushSelfShowLog", "get msg byte arr error");
        }
    }

    private boolean a(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("param");
            if (jSONObject2.has("autoClear")) {
                this.f = jSONObject2.getInt("autoClear");
            } else {
                this.f = 0;
            }
            if (!"app".equals(this.p)) {
                if (!"cosa".equals(this.p)) {
                    if (NotificationCompat.CATEGORY_EMAIL.equals(this.p)) {
                        if (!jSONObject2.has("emailAddr") || !jSONObject2.has("emailSubject")) {
                            c.a("PushSelfShowLog", "emailAddr or emailSubject is null");
                            return false;
                        }
                        this.x = jSONObject2.getString("emailAddr");
                        this.y = jSONObject2.getString("emailSubject");
                        if (jSONObject2.has("emailContent")) {
                            this.z = jSONObject2.getString("emailContent");
                        }
                    } else if ("phone".equals(this.p)) {
                        if (jSONObject2.has("phoneNum")) {
                            this.w = jSONObject2.getString("phoneNum");
                        } else {
                            c.a("PushSelfShowLog", "phoneNum is null");
                            return false;
                        }
                    } else if ("url".equals(this.p)) {
                        if (jSONObject2.has("url")) {
                            this.C = jSONObject2.getString("url");
                            if (jSONObject2.has("inBrowser")) {
                                this.G = jSONObject2.getInt("inBrowser");
                            }
                            if (jSONObject2.has("needUserId")) {
                                this.H = jSONObject2.getInt("needUserId");
                            }
                            if (jSONObject2.has("sign")) {
                                this.I = jSONObject2.getString("sign");
                            }
                            if (jSONObject2.has("rpt") && jSONObject2.has("rpl")) {
                                this.D = jSONObject2.getString("rpl");
                                this.E = jSONObject2.getString("rpt");
                                if (jSONObject2.has("rpct")) {
                                    this.F = jSONObject2.getString("rpct");
                                }
                            }
                        } else {
                            c.a("PushSelfShowLog", "url is null");
                            return false;
                        }
                    } else if ("rp".equals(this.p)) {
                        if (!jSONObject2.has("rpt") || !jSONObject2.has("rpl")) {
                            c.a("PushSelfShowLog", "rpl or rpt is null");
                            return false;
                        }
                        this.D = jSONObject2.getString("rpl");
                        this.E = jSONObject2.getString("rpt");
                        if (jSONObject2.has("rpct")) {
                            this.F = jSONObject2.getString("rpct");
                        }
                        if (jSONObject2.has("needUserId")) {
                            this.H = jSONObject2.getInt("needUserId");
                        }
                    }
                    return true;
                }
            }
            if (jSONObject2.has("acn")) {
                this.B = jSONObject2.getString("acn");
                this.g = this.B;
            }
            if (jSONObject2.has("intentUri")) {
                this.g = jSONObject2.getString("intentUri");
            }
            if (jSONObject2.has(DictionaryKeys.V2_PACKAGENAME)) {
                this.A = jSONObject2.getString(DictionaryKeys.V2_PACKAGENAME);
                return true;
            }
            c.a("PushSelfShowLog", "appPackageName is null");
            return false;
        } catch (Exception e2) {
            c.c("PushSelfShowLog", "ParseParam error ", e2);
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0127, code lost:
        com.huawei.android.pushagent.a.a.c.a(r8, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean b(org.json.JSONObject r8) {
        /*
            r7 = this;
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r1 = "enter parseNotifyParam"
            com.huawei.android.pushagent.a.a.c.a(r0, r1)
            r0 = 0
            java.lang.String r1 = "notifyParam"
            org.json.JSONObject r8 = r8.getJSONObject(r1)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r1 = "style"
            boolean r1 = r8.has(r1)     // Catch:{ JSONException -> 0x01ad }
            if (r1 == 0) goto L_0x01ac
            java.lang.String r1 = "style"
            int r1 = r8.getInt(r1)     // Catch:{ JSONException -> 0x01ad }
            r7.L = r1     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r3 = "style:"
            r2.<init>(r3)     // Catch:{ JSONException -> 0x01ad }
            int r3 = r7.L     // Catch:{ JSONException -> 0x01ad }
            r2.append(r3)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x01ad }
            com.huawei.android.pushagent.a.a.c.a(r1, r2)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r1 = "btnCount"
            boolean r1 = r8.has(r1)     // Catch:{ JSONException -> 0x01ad }
            if (r1 == 0) goto L_0x0043
            java.lang.String r1 = "btnCount"
            int r1 = r8.getInt(r1)     // Catch:{ JSONException -> 0x01ad }
            r7.M = r1     // Catch:{ JSONException -> 0x01ad }
        L_0x0043:
            int r1 = r7.M     // Catch:{ JSONException -> 0x01ad }
            if (r1 <= 0) goto L_0x00e1
            int r1 = r7.M     // Catch:{ JSONException -> 0x01ad }
            r2 = 3
            if (r1 <= r2) goto L_0x004e
            r7.M = r2     // Catch:{ JSONException -> 0x01ad }
        L_0x004e:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r3 = "btnCount:"
            r2.<init>(r3)     // Catch:{ JSONException -> 0x01ad }
            int r3 = r7.M     // Catch:{ JSONException -> 0x01ad }
            r2.append(r3)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x01ad }
            com.huawei.android.pushagent.a.a.c.a(r1, r2)     // Catch:{ JSONException -> 0x01ad }
            int r1 = r7.M     // Catch:{ JSONException -> 0x01ad }
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ JSONException -> 0x01ad }
            r7.N = r1     // Catch:{ JSONException -> 0x01ad }
            int r1 = r7.M     // Catch:{ JSONException -> 0x01ad }
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ JSONException -> 0x01ad }
            r7.O = r1     // Catch:{ JSONException -> 0x01ad }
            int r1 = r7.M     // Catch:{ JSONException -> 0x01ad }
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ JSONException -> 0x01ad }
            r7.P = r1     // Catch:{ JSONException -> 0x01ad }
            r1 = 0
        L_0x0076:
            int r2 = r7.M     // Catch:{ JSONException -> 0x01ad }
            if (r1 >= r2) goto L_0x00e1
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r3 = "btn"
            r2.<init>(r3)     // Catch:{ JSONException -> 0x01ad }
            int r3 = r1 + 1
            r2.append(r3)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r4 = "Text"
            r2.append(r4)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x01ad }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r5 = "btn"
            r4.<init>(r5)     // Catch:{ JSONException -> 0x01ad }
            r4.append(r3)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r5 = "Image"
            r4.append(r5)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x01ad }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r6 = "btn"
            r5.<init>(r6)     // Catch:{ JSONException -> 0x01ad }
            r5.append(r3)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r6 = "Event"
            r5.append(r6)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x01ad }
            boolean r6 = r8.has(r2)     // Catch:{ JSONException -> 0x01ad }
            if (r6 == 0) goto L_0x00c3
            java.lang.String[] r6 = r7.N     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = r8.getString(r2)     // Catch:{ JSONException -> 0x01ad }
            r6[r1] = r2     // Catch:{ JSONException -> 0x01ad }
        L_0x00c3:
            boolean r2 = r8.has(r4)     // Catch:{ JSONException -> 0x01ad }
            if (r2 == 0) goto L_0x00d1
            java.lang.String[] r2 = r7.O     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r4 = r8.getString(r4)     // Catch:{ JSONException -> 0x01ad }
            r2[r1] = r4     // Catch:{ JSONException -> 0x01ad }
        L_0x00d1:
            boolean r2 = r8.has(r5)     // Catch:{ JSONException -> 0x01ad }
            if (r2 == 0) goto L_0x00df
            java.lang.String[] r2 = r7.P     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r4 = r8.getString(r5)     // Catch:{ JSONException -> 0x01ad }
            r2[r1] = r4     // Catch:{ JSONException -> 0x01ad }
        L_0x00df:
            r1 = r3
            goto L_0x0076
        L_0x00e1:
            com.huawei.android.pushselfshow.c.a r1 = com.huawei.android.pushselfshow.c.a.STYLE_1     // Catch:{ JSONException -> 0x01ad }
            int r2 = r7.L     // Catch:{ JSONException -> 0x01ad }
            if (r2 < 0) goto L_0x00f8
            int r2 = r7.L     // Catch:{ JSONException -> 0x01ad }
            com.huawei.android.pushselfshow.c.a[] r3 = com.huawei.android.pushselfshow.c.a.values()     // Catch:{ JSONException -> 0x01ad }
            int r3 = r3.length     // Catch:{ JSONException -> 0x01ad }
            if (r2 >= r3) goto L_0x00f8
            com.huawei.android.pushselfshow.c.a[] r1 = com.huawei.android.pushselfshow.c.a.values()     // Catch:{ JSONException -> 0x01ad }
            int r2 = r7.L     // Catch:{ JSONException -> 0x01ad }
            r1 = r1[r2]     // Catch:{ JSONException -> 0x01ad }
        L_0x00f8:
            int[] r2 = com.huawei.android.pushselfshow.b.a.AnonymousClass1.a     // Catch:{ JSONException -> 0x01ad }
            int r1 = r1.ordinal()     // Catch:{ JSONException -> 0x01ad }
            r1 = r2[r1]     // Catch:{ JSONException -> 0x01ad }
            switch(r1) {
                case 1: goto L_0x014f;
                case 2: goto L_0x012c;
                case 3: goto L_0x0105;
                case 4: goto L_0x0105;
                default: goto L_0x0103;
            }     // Catch:{ JSONException -> 0x01ad }
        L_0x0103:
            goto L_0x01aa
        L_0x0105:
            java.lang.String r1 = "bigPic"
            boolean r1 = r8.has(r1)     // Catch:{ JSONException -> 0x01ad }
            if (r1 == 0) goto L_0x01aa
            java.lang.String r1 = "bigPic"
            java.lang.String r8 = r8.getString(r1)     // Catch:{ JSONException -> 0x01ad }
            r7.T = r8     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = "bigPicUrl:"
            r1.<init>(r2)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = r7.T     // Catch:{ JSONException -> 0x01ad }
            r1.append(r2)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x01ad }
        L_0x0127:
            com.huawei.android.pushagent.a.a.c.a(r8, r1)     // Catch:{ JSONException -> 0x01ad }
            goto L_0x01aa
        L_0x012c:
            java.lang.String r1 = "subTitle"
            boolean r1 = r8.has(r1)     // Catch:{ JSONException -> 0x01ad }
            if (r1 == 0) goto L_0x01aa
            java.lang.String r1 = "subTitle"
            java.lang.String r8 = r8.getString(r1)     // Catch:{ JSONException -> 0x01ad }
            r7.S = r8     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = "subTitle:"
            r1.<init>(r2)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = r7.S     // Catch:{ JSONException -> 0x01ad }
            r1.append(r2)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x01ad }
            goto L_0x0127
        L_0x014f:
            java.lang.String r1 = "iconCount"
            boolean r1 = r8.has(r1)     // Catch:{ JSONException -> 0x01ad }
            if (r1 == 0) goto L_0x015f
            java.lang.String r1 = "iconCount"
            int r1 = r8.getInt(r1)     // Catch:{ JSONException -> 0x01ad }
            r7.Q = r1     // Catch:{ JSONException -> 0x01ad }
        L_0x015f:
            int r1 = r7.Q     // Catch:{ JSONException -> 0x01ad }
            if (r1 <= 0) goto L_0x01aa
            int r1 = r7.Q     // Catch:{ JSONException -> 0x01ad }
            r2 = 6
            if (r1 <= r2) goto L_0x016a
            r7.Q = r2     // Catch:{ JSONException -> 0x01ad }
        L_0x016a:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r3 = "iconCount:"
            r2.<init>(r3)     // Catch:{ JSONException -> 0x01ad }
            int r3 = r7.Q     // Catch:{ JSONException -> 0x01ad }
            r2.append(r3)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x01ad }
            com.huawei.android.pushagent.a.a.c.a(r1, r2)     // Catch:{ JSONException -> 0x01ad }
            int r1 = r7.Q     // Catch:{ JSONException -> 0x01ad }
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ JSONException -> 0x01ad }
            r7.R = r1     // Catch:{ JSONException -> 0x01ad }
            r1 = 0
        L_0x0186:
            int r2 = r7.Q     // Catch:{ JSONException -> 0x01ad }
            if (r1 >= r2) goto L_0x01aa
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r3 = "icon"
            r2.<init>(r3)     // Catch:{ JSONException -> 0x01ad }
            int r3 = r1 + 1
            r2.append(r3)     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x01ad }
            boolean r4 = r8.has(r2)     // Catch:{ JSONException -> 0x01ad }
            if (r4 == 0) goto L_0x01a8
            java.lang.String[] r4 = r7.R     // Catch:{ JSONException -> 0x01ad }
            java.lang.String r2 = r8.getString(r2)     // Catch:{ JSONException -> 0x01ad }
            r4[r1] = r2     // Catch:{ JSONException -> 0x01ad }
        L_0x01a8:
            r1 = r3
            goto L_0x0186
        L_0x01aa:
            r8 = 1
            return r8
        L_0x01ac:
            return r0
        L_0x01ad:
            r8 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r8 = r8.toString()
            com.huawei.android.pushagent.a.a.c.b(r1, r8)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.b.a.b(org.json.JSONObject):boolean");
    }

    public String a() {
        StringBuilder sb = new StringBuilder("msgId =");
        sb.append(this.m);
        c.a("PushSelfShowLog", sb.toString());
        return this.m;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a0 A[Catch:{ Exception -> 0x01c1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b0 A[Catch:{ Exception -> 0x01c1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b9 A[Catch:{ Exception -> 0x01c1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c3 A[Catch:{ Exception -> 0x01c1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cc A[Catch:{ Exception -> 0x01c1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00d6 A[Catch:{ Exception -> 0x01c1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0117 A[Catch:{ Exception -> 0x01c1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x012b A[Catch:{ Exception -> 0x01c1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean b() {
        /*
            r7 = this;
            r0 = 0
            java.lang.String r1 = r7.K     // Catch:{ Exception -> 0x01c1 }
            if (r1 == 0) goto L_0x01b9
            java.lang.String r1 = r7.K     // Catch:{ Exception -> 0x01c1 }
            int r1 = r1.length()     // Catch:{ Exception -> 0x01c1 }
            if (r1 != 0) goto L_0x000f
            goto L_0x01b9
        L_0x000f:
            java.lang.String r1 = r7.K     // Catch:{ Exception -> 0x01c1 }
            r7.i = r1     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r1 = r7.J     // Catch:{ Exception -> 0x01c1 }
            if (r1 == 0) goto L_0x01b1
            java.lang.String r1 = r7.J     // Catch:{ Exception -> 0x01c1 }
            int r1 = r1.length()     // Catch:{ Exception -> 0x01c1 }
            if (r1 != 0) goto L_0x0021
            goto L_0x01b1
        L_0x0021:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r2 = r7.J     // Catch:{ Exception -> 0x01c1 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r2 = "msgType"
            int r2 = r1.getInt(r2)     // Catch:{ Exception -> 0x01c1 }
            r7.h = r2     // Catch:{ Exception -> 0x01c1 }
            int r2 = r7.h     // Catch:{ Exception -> 0x01c1 }
            r3 = 1
            if (r2 == r3) goto L_0x003d
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "not a selefShowMsg"
            com.huawei.android.pushagent.a.a.c.a(r1, r2)     // Catch:{ Exception -> 0x01c1 }
            return r0
        L_0x003d:
            java.lang.String r2 = "group"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x0062
            java.lang.String r2 = "group"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x01c1 }
            r7.a = r2     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r5 = "NOTIFY_GROUP:"
            r4.<init>(r5)     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r5 = r7.a     // Catch:{ Exception -> 0x01c1 }
            r4.append(r5)     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01c1 }
            com.huawei.android.pushagent.a.a.c.a(r2, r4)     // Catch:{ Exception -> 0x01c1 }
        L_0x0062:
            java.lang.String r2 = "msgContent"
            org.json.JSONObject r1 = r1.getJSONObject(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r1 != 0) goto L_0x0072
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "msgObj == null"
            com.huawei.android.pushagent.a.a.c.b(r1, r2)     // Catch:{ Exception -> 0x01c1 }
            return r0
        L_0x0072:
            java.lang.String r2 = "msgId"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x01a9
            java.lang.String r2 = "msgId"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ Exception -> 0x01c1 }
            boolean r4 = r2 instanceof java.lang.String     // Catch:{ Exception -> 0x01c1 }
            if (r4 == 0) goto L_0x0089
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x01c1 }
        L_0x0086:
            r7.m = r2     // Catch:{ Exception -> 0x01c1 }
            goto L_0x0098
        L_0x0089:
            boolean r4 = r2 instanceof java.lang.Integer     // Catch:{ Exception -> 0x01c1 }
            if (r4 == 0) goto L_0x0098
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ Exception -> 0x01c1 }
            int r2 = r2.intValue()     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x01c1 }
            goto L_0x0086
        L_0x0098:
            java.lang.String r2 = "dispPkgName"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x00a8
            java.lang.String r2 = "dispPkgName"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x01c1 }
            r7.n = r2     // Catch:{ Exception -> 0x01c1 }
        L_0x00a8:
            java.lang.String r2 = "rtn"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x00b9
            java.lang.String r2 = "rtn"
            int r2 = r1.getInt(r2)     // Catch:{ Exception -> 0x01c1 }
            r7.k = r2     // Catch:{ Exception -> 0x01c1 }
            goto L_0x00bb
        L_0x00b9:
            r7.k = r3     // Catch:{ Exception -> 0x01c1 }
        L_0x00bb:
            java.lang.String r2 = "fm"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x00cc
            java.lang.String r2 = "fm"
            int r2 = r1.getInt(r2)     // Catch:{ Exception -> 0x01c1 }
            r7.j = r2     // Catch:{ Exception -> 0x01c1 }
            goto L_0x00ce
        L_0x00cc:
            r7.j = r3     // Catch:{ Exception -> 0x01c1 }
        L_0x00ce:
            java.lang.String r2 = "ap"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x010f
            java.lang.String r2 = "ap"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x01c1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c1 }
            r3.<init>()     // Catch:{ Exception -> 0x01c1 }
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01c1 }
            r5 = 48
            if (r4 != 0) goto L_0x010a
            int r4 = r2.length()     // Catch:{ Exception -> 0x01c1 }
            if (r4 >= r5) goto L_0x010a
            r4 = 0
        L_0x00f0:
            int r6 = r2.length()     // Catch:{ Exception -> 0x01c1 }
            int r6 = 48 - r6
            if (r4 >= r6) goto L_0x0100
            java.lang.String r6 = "0"
            r3.append(r6)     // Catch:{ Exception -> 0x01c1 }
            int r4 = r4 + 1
            goto L_0x00f0
        L_0x0100:
            r3.append(r2)     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r2 = r3.toString()     // Catch:{ Exception -> 0x01c1 }
        L_0x0107:
            r7.l = r2     // Catch:{ Exception -> 0x01c1 }
            goto L_0x010f
        L_0x010a:
            java.lang.String r2 = r2.substring(r0, r5)     // Catch:{ Exception -> 0x01c1 }
            goto L_0x0107
        L_0x010f:
            java.lang.String r2 = "extras"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x0123
            java.lang.String r2 = "extras"
            org.json.JSONArray r2 = r1.getJSONArray(r2)     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x01c1 }
            r7.o = r2     // Catch:{ Exception -> 0x01c1 }
        L_0x0123:
            java.lang.String r2 = "psContent"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x01a8
            java.lang.String r2 = "psContent"
            org.json.JSONObject r1 = r1.getJSONObject(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r1 != 0) goto L_0x0134
            return r0
        L_0x0134:
            java.lang.String r2 = "cmd"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x01c1 }
            r7.p = r2     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r2 = "content"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x014d
            java.lang.String r2 = "content"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x01c1 }
        L_0x014a:
            r7.q = r2     // Catch:{ Exception -> 0x01c1 }
            goto L_0x0150
        L_0x014d:
            java.lang.String r2 = ""
            goto L_0x014a
        L_0x0150:
            java.lang.String r2 = "notifyIcon"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x0161
            java.lang.String r2 = "notifyIcon"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x01c1 }
        L_0x015e:
            r7.r = r2     // Catch:{ Exception -> 0x01c1 }
            goto L_0x0170
        L_0x0161:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c1 }
            r2.<init>()     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r3 = r7.m     // Catch:{ Exception -> 0x01c1 }
            r2.append(r3)     // Catch:{ Exception -> 0x01c1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x01c1 }
            goto L_0x015e
        L_0x0170:
            java.lang.String r2 = "statusIcon"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x0180
            java.lang.String r2 = "statusIcon"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x01c1 }
            r7.t = r2     // Catch:{ Exception -> 0x01c1 }
        L_0x0180:
            java.lang.String r2 = "notifyTitle"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x0190
            java.lang.String r2 = "notifyTitle"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x01c1 }
            r7.s = r2     // Catch:{ Exception -> 0x01c1 }
        L_0x0190:
            java.lang.String r2 = "notifyParam"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x019b
            r7.b(r1)     // Catch:{ Exception -> 0x01c1 }
        L_0x019b:
            java.lang.String r2 = "param"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x01c1 }
            if (r2 == 0) goto L_0x01a8
            boolean r1 = r7.a(r1)     // Catch:{ Exception -> 0x01c1 }
            return r1
        L_0x01a8:
            return r0
        L_0x01a9:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "msgId == null"
            com.huawei.android.pushagent.a.a.c.b(r1, r2)     // Catch:{ Exception -> 0x01c1 }
            return r0
        L_0x01b1:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "msg is null"
            com.huawei.android.pushagent.a.a.c.a(r1, r2)     // Catch:{ Exception -> 0x01c1 }
            return r0
        L_0x01b9:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "token is null"
            com.huawei.android.pushagent.a.a.c.a(r1, r2)     // Catch:{ Exception -> 0x01c1 }
            return r0
        L_0x01c1:
            r1 = move-exception
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = r1.toString()
            com.huawei.android.pushagent.a.a.c.a(r2, r3, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.b.a.b():boolean");
    }

    public byte[] c() {
        String str;
        String str2;
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("autoClear", this.f);
            jSONObject4.put("s", this.b);
            jSONObject4.put(UploadQueueMgr.MSGTYPE_REALTIME, this.c);
            jSONObject4.put("smsC", this.d);
            jSONObject4.put("mmsUrl", this.e);
            jSONObject4.put("url", this.C);
            jSONObject4.put("inBrowser", this.G);
            jSONObject4.put("needUserId", this.H);
            jSONObject4.put("sign", this.I);
            jSONObject4.put("rpl", this.D);
            jSONObject4.put("rpt", this.E);
            jSONObject4.put("rpct", this.F);
            jSONObject4.put(DictionaryKeys.V2_PACKAGENAME, this.A);
            jSONObject4.put("acn", this.B);
            jSONObject4.put("intentUri", this.g);
            jSONObject4.put("emailAddr", this.x);
            jSONObject4.put("emailSubject", this.y);
            jSONObject4.put("emailContent", this.z);
            jSONObject4.put("phoneNum", this.w);
            jSONObject4.put("replyToSms", this.v);
            jSONObject4.put("smsNum", this.u);
            jSONObject3.put(BaseDO.JSON_CMD, this.p);
            jSONObject3.put("content", this.q);
            jSONObject3.put("notifyIcon", this.r);
            jSONObject3.put("notifyTitle", this.s);
            jSONObject3.put("statusIcon", this.t);
            jSONObject3.put("param", jSONObject4);
            jSONObject2.put("dispPkgName", this.n);
            jSONObject2.put("msgId", this.m);
            jSONObject2.put("fm", this.j);
            jSONObject2.put(H5Param.ANTI_PHISHING, this.l);
            jSONObject2.put("rtn", this.k);
            jSONObject2.put("psContent", jSONObject3);
            if (this.o != null && this.o.length() > 0) {
                jSONObject2.put("extras", new JSONArray(this.o));
            }
            jSONObject.put("msgType", this.h);
            jSONObject.put("msgContent", jSONObject2);
            jSONObject.put("group", this.a);
            return jSONObject.toString().getBytes("UTF-8");
        } catch (JSONException e2) {
            e = e2;
            str2 = "PushSelfShowLog";
            str = "getMsgData failed JSONException:";
            c.a(str2, str, e);
            return new byte[0];
        } catch (UnsupportedEncodingException e3) {
            e = e3;
            str2 = "PushSelfShowLog";
            str = "getMsgData failed UnsupportedEncodingException:";
            c.a(str2, str, e);
            return new byte[0];
        }
    }

    public byte[] d() {
        try {
            if (this.i != null && this.i.length() > 0) {
                return this.i.getBytes("UTF-8");
            }
        } catch (Exception e2) {
            c.a((String) "PushSelfShowLog", (String) "getToken getByte failed ", (Throwable) e2);
        }
        return new byte[0];
    }
}
