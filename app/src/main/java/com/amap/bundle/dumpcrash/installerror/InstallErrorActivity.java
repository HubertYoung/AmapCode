package com.amap.bundle.dumpcrash.installerror;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.amap.bundle.blutils.log.DebugLog;
import com.autonavi.amap.app.AMapAppGlobal;
import org.json.JSONException;
import org.json.JSONObject;

public class InstallErrorActivity extends Activity {
    private int a;
    private final String b = "&aid=";

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = getIntent().getIntExtra("typevalue", 5);
        AMapAppGlobal.setActivity(this);
        setFinishOnTouchOutside(false);
        Builder builder = new Builder(this);
        builder.setMessage("我们检测到当前版本未正确安装");
        builder.setNegativeButton("忽略", new OnClickListener() {
            public final void onClick(final DialogInterface dialogInterface, int i) {
                InstallErrorActivity.a(InstallErrorActivity.this, 2);
                InstallErrorActivity.b(new a() {
                    public final void a() {
                        dialogInterface.dismiss();
                        System.exit(0);
                    }
                });
            }
        }).setPositiveButton("立即安装", new OnClickListener() {
            public final void onClick(final DialogInterface dialogInterface, int i) {
                InstallErrorActivity.a(InstallErrorActivity.this, 1);
                Intent intent = new Intent("android.intent.action.VIEW");
                StringBuilder sb = new StringBuilder("&aid=");
                sb.append(InstallErrorActivity.a(kn.a((Context) AMapAppGlobal.getApplication())));
                intent.setData(Uri.parse("http://wap.amap.com?type=aie".concat(String.valueOf(sb.toString()))));
                intent.setFlags(268435456);
                AMapAppGlobal.getApplication().startActivity(intent);
                InstallErrorActivity.b(new a() {
                    public final void a() {
                        dialogInterface.dismiss();
                        System.exit(0);
                    }
                });
            }
        });
        builder.setCancelable(false);
        try {
            builder.create().show();
        } catch (Exception e) {
            DebugLog.error(e);
        }
    }

    public static String a(String str) {
        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ 48);
        }
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bytes) {
            byte b3 = b2 & 255;
            if (b3 < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(b3));
        }
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public static void b(a aVar) {
        bmx.a();
        try {
            Thread.sleep(2000);
        } catch (Throwable unused) {
        }
        new um().a(aVar);
    }

    public void finish() {
        b(new a() {
            public final void a() {
                System.exit(0);
            }
        });
        super.finish();
    }

    static /* synthetic */ void a(InstallErrorActivity installErrorActivity, int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", installErrorActivity.a);
            jSONObject.put("action", i);
        } catch (JSONException unused) {
        }
        ul.a.a("P00165", "B001", jSONObject);
        StringBuilder sb = new StringBuilder("pageid:P00165/actionid:B001/type:");
        sb.append(installErrorActivity.a);
        sb.append("/action:");
        sb.append(i);
    }
}
