package com.huawei.android.pushselfshow.richpush.html.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.alibaba.baichuan.android.trade.constants.AppLinkConstants;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.utils.a;
import org.json.JSONException;
import org.json.JSONObject;

public class d implements h {
    public boolean a = false;
    public int b;
    public int c;
    private NativeToJsMessageQueue d;
    private String e;
    /* access modifiers changed from: private */
    public Activity f;

    public d(Activity activity) {
        c.e("PushSelfShowLog", "init App");
        this.f = activity;
    }

    private void a(String str, String str2, boolean z) {
        try {
            StringBuilder sb = new StringBuilder("enter launchApp , appPackageName =");
            sb.append(str);
            sb.append(",and msg.intentUri is ");
            sb.append(str2);
            sb.append(" boolean appmarket is ");
            sb.append(z);
            c.a("PushSelfShowLog", sb.toString());
            if (str != null) {
                if (str.trim().length() != 0) {
                    Intent a2 = a.a((Context) this.f, str);
                    if (a2 != null) {
                        if (str2 != null) {
                            try {
                                Intent parseUri = Intent.parseUri(str2, 0);
                                StringBuilder sb2 = new StringBuilder("Intent.parseUri(intentUri, 0)，");
                                sb2.append(a2.toURI());
                                c.e("PushSelfShowLog", sb2.toString());
                                if (a.a((Context) this.f, str, parseUri).booleanValue()) {
                                    a2 = parseUri;
                                }
                            } catch (Exception e2) {
                                c.a((String) "PushSelfShowLog", (String) "intentUri error ", (Throwable) e2);
                            }
                        }
                        if (!a.a((Context) this.f, a2)) {
                            c.c("PushSelfShowLog", "no permission to start Activity");
                            this.d.a(this.e, com.huawei.android.pushselfshow.richpush.html.api.d.a.ILLEGAL_ACCESS_EXCEPTION, "error", null);
                            return;
                        }
                        if (this.a) {
                            StringBuilder sb3 = new StringBuilder(" APP_OPEN startActivityForResult ");
                            sb3.append(a2.toURI());
                            c.e("PushSelfShowLog", sb3.toString());
                            this.f.startActivityForResult(a2, this.b);
                        } else {
                            StringBuilder sb4 = new StringBuilder(" APP_OPEN start ");
                            sb4.append(a2.toURI());
                            c.e("PushSelfShowLog", sb4.toString());
                            this.f.startActivity(a2);
                        }
                        this.d.a(this.e, com.huawei.android.pushselfshow.richpush.html.api.d.a.OK, "success", null);
                        return;
                    } else if (z) {
                        a(str);
                        return;
                    } else {
                        c.e("PushSelfShowLog", "APP_NOT_EXIST and appmaeket is false");
                        this.d.a(this.e, com.huawei.android.pushselfshow.richpush.html.api.d.a.APP_NOT_EXIST, "error", null);
                        return;
                    }
                }
            }
            this.d.a(this.e, com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION, "error", null);
        } catch (Exception e3) {
            c.c("PushSelfShowLog", e3.toString(), e3);
        }
    }

    private void a(JSONObject jSONObject) {
        boolean z;
        String str;
        String str2 = null;
        if (jSONObject == null || !jSONObject.has("package-name")) {
            this.d.a(this.e, com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION, "error", null);
            return;
        }
        try {
            str = jSONObject.getString("package-name");
            try {
                if (jSONObject.has("intent-uri")) {
                    str2 = jSONObject.getString("intent-uri");
                }
                z = jSONObject.has("appmarket") ? jSONObject.getBoolean("appmarket") : false;
            } catch (Exception e2) {
                e = e2;
                z = false;
                c.d("PushSelfShowLog", "openApp param failed ", e);
                c.b((String) "PushSelfShowLog", (String) "packageName is %s , appmarket is %s ,bResult is %s ", str, Boolean.valueOf(z), Boolean.valueOf(this.a));
                a(str, str2, z);
            }
            try {
                if (jSONObject.has(AppLinkConstants.REQUESTCODE) && jSONObject.has("resultCode")) {
                    this.b = jSONObject.getInt(AppLinkConstants.REQUESTCODE);
                    this.c = jSONObject.getInt("resultCode");
                    this.a = true;
                }
            } catch (Exception e3) {
                e = e3;
                c.d("PushSelfShowLog", "openApp param failed ", e);
                c.b((String) "PushSelfShowLog", (String) "packageName is %s , appmarket is %s ,bResult is %s ", str, Boolean.valueOf(z), Boolean.valueOf(this.a));
                a(str, str2, z);
            }
        } catch (Exception e4) {
            e = e4;
            str = null;
            z = false;
            c.d("PushSelfShowLog", "openApp param failed ", e);
            c.b((String) "PushSelfShowLog", (String) "packageName is %s , appmarket is %s ,bResult is %s ", str, Boolean.valueOf(z), Boolean.valueOf(this.a));
            a(str, str2, z);
        }
        c.b((String) "PushSelfShowLog", (String) "packageName is %s , appmarket is %s ,bResult is %s ", str, Boolean.valueOf(z), Boolean.valueOf(this.a));
        a(str, str2, z);
    }

    private void b(JSONObject jSONObject) {
        NativeToJsMessageQueue nativeToJsMessageQueue;
        String str;
        com.huawei.android.pushselfshow.richpush.html.api.d.a aVar;
        if (jSONObject == null || !jSONObject.has("package-name")) {
            nativeToJsMessageQueue = this.d;
            str = this.e;
            aVar = com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION;
        } else {
            try {
                String string = jSONObject.getString("package-name");
                JSONObject jSONObject2 = new JSONObject();
                PackageManager packageManager = this.f.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(string, 0);
                String charSequence = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                String str2 = packageInfo.versionName;
                int i = packageInfo.versionCode;
                jSONObject2.put("appName", charSequence);
                jSONObject2.put("versionCode", i);
                jSONObject2.put("versionName", str2);
                this.d.a(this.e, com.huawei.android.pushselfshow.richpush.html.api.d.a.OK, "success", jSONObject2);
                return;
            } catch (Exception e2) {
                c.d("PushSelfShowLog", "getAppInfo param failed ", e2);
                nativeToJsMessageQueue = this.d;
                str = this.e;
                aVar = com.huawei.android.pushselfshow.richpush.html.api.d.a.APP_NOT_EXIST;
            }
        }
        nativeToJsMessageQueue.a(str, aVar, "error", null);
    }

    private String c(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2;
        com.huawei.android.pushselfshow.richpush.html.api.d.a aVar;
        if (jSONObject == null || !jSONObject.has("package-name")) {
            aVar = com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION;
        } else {
            try {
                String string = jSONObject.getString("package-name");
                PackageManager packageManager = this.f.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(string, 0);
                String charSequence = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                String str = packageInfo.versionName;
                int i = packageInfo.versionCode;
                jSONObject2 = com.huawei.android.pushselfshow.richpush.html.api.d.a(com.huawei.android.pushselfshow.richpush.html.api.d.a.OK);
                jSONObject2.put("appName", charSequence);
                jSONObject2.put("versionCode", i);
                jSONObject2.put("versionName", str);
            } catch (Exception e2) {
                c.d("PushSelfShowLog", "getAppInfo param failed ", e2);
                aVar = com.huawei.android.pushselfshow.richpush.html.api.d.a.APP_NOT_EXIST;
            }
            return jSONObject2.toString();
        }
        jSONObject2 = com.huawei.android.pushselfshow.richpush.html.api.d.a(aVar);
        return jSONObject2.toString();
    }

    public String a(String str, JSONObject jSONObject) {
        try {
            return "getAppInfo".equals(str) ? c(jSONObject) : com.huawei.android.pushselfshow.richpush.html.api.d.a(com.huawei.android.pushselfshow.richpush.html.api.d.a.METHOD_NOT_FOUND_EXCEPTION).toString();
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void a(int i, int i2, Intent intent) {
        StringBuilder sb = new StringBuilder("onActivityResult and requestCode is ");
        sb.append(i);
        sb.append(" resultCode is ");
        sb.append(i2);
        sb.append(" intent data is ");
        sb.append(intent);
        c.e("PushSelfShowLog", sb.toString());
        try {
            if (this.a && i2 == this.c && intent != null) {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    for (String str : extras.keySet()) {
                        jSONObject2.put(str, extras.get(str));
                    }
                    if (jSONObject2.length() > 0) {
                        jSONObject.put("extra", jSONObject2);
                        this.d.a(this.e, com.huawei.android.pushselfshow.richpush.html.api.d.a.OK, "status", jSONObject);
                    }
                }
            }
        } catch (Exception | JSONException unused) {
            c.e("PushSelfShowLog", "onActivityResult error");
        }
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            c.a("PushSelfShowLog", "jsMessageQueue is null while run into App exec");
            return;
        }
        this.d = nativeToJsMessageQueue;
        if ("openApp".equals(str)) {
            d();
            if (str2 != null) {
                this.e = str2;
                a(jSONObject);
                return;
            }
            c.a("PushSelfShowLog", "Audio exec callback is null ");
        } else if (!"getAppInfo".equals(str)) {
            nativeToJsMessageQueue.a(str2, com.huawei.android.pushselfshow.richpush.html.api.d.a.METHOD_NOT_FOUND_EXCEPTION, "error", null);
        } else if (str2 != null) {
            this.e = str2;
            b(jSONObject);
        } else {
            c.a("PushSelfShowLog", "Audio exec callback is null ");
        }
    }

    public void a(String str) {
        Intent intent;
        NativeToJsMessageQueue nativeToJsMessageQueue;
        String str2;
        com.huawei.android.pushselfshow.richpush.html.api.d.a aVar;
        String str3;
        if (a.a((Context) this.f, (String) "com.huawei.appmarket", new Intent("com.huawei.appmarket.intent.action.AppDetail")).booleanValue()) {
            c.a("PushSelfShowLog", "app not exist && appmarkt exist ,so open appmarket");
            intent = new Intent("com.huawei.appmarket.intent.action.AppDetail");
            intent.putExtra("APP_PACKAGENAME", str);
            intent.setPackage("com.huawei.appmarket");
            intent.setFlags(402653184);
            c.a("PushSelfShowLog", "hwAppmarket only support com.huawei.appmarket.intent.action.AppDetail!");
        } else if (a.c(this.f).size() > 0) {
            c.a("PushSelfShowLog", "app not exist && other appmarkt exist ,so open appmarket");
            intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("market://details?id=".concat(String.valueOf(str))));
            intent.setFlags(402653184);
        } else {
            c.a("PushSelfShowLog", "app not exist && other appmarkt not exist ,so do nothing");
            try {
                new e(this).start();
            } catch (Exception unused) {
                c.a("PushSelfShowLog", "show Toast.makeText error");
            }
            intent = null;
        }
        if (intent != null) {
            StringBuilder sb = new StringBuilder("intent is not null ");
            sb.append(intent.toURI());
            c.e("PushSelfShowLog", sb.toString());
            this.f.startActivity(intent);
            c.e("PushSelfShowLog", "APP_OPEN_APPMARKET and open with appmaeket");
            nativeToJsMessageQueue = this.d;
            str2 = this.e;
            aVar = com.huawei.android.pushselfshow.richpush.html.api.d.a.APP_OPEN_APPMARKET;
            str3 = "success";
        } else {
            c.a("PushSelfShowLog", "intent is null ");
            c.e("PushSelfShowLog", "APP_OPEN_APPMARKET and not find any  appmaeket");
            nativeToJsMessageQueue = this.d;
            str2 = this.e;
            aVar = com.huawei.android.pushselfshow.richpush.html.api.d.a.APP_NOT_APPMARKET;
            str3 = "error";
        }
        nativeToJsMessageQueue.a(str2, aVar, str3, null);
    }

    public void b() {
    }

    public void c() {
        d();
    }

    public void d() {
        this.e = null;
        this.a = false;
        this.b = 0;
        this.c = 0;
    }
}
