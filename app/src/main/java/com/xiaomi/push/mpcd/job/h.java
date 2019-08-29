package com.xiaomi.push.mpcd.job;

import android.content.Context;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.xiaomi.xmpush.thrift.d;

public class h extends f {
    private boolean a;
    private boolean b;
    private boolean e;
    private boolean f;
    private boolean g;

    public h(Context context, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        super(context, i);
        this.a = z;
        this.b = z2;
        this.e = z3;
        this.f = z4;
        this.g = z5;
    }

    private String f() {
        if (!this.a) {
            return CameraParams.FLASH_MODE_OFF;
        }
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) this.d.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
            StringBuilder sb = new StringBuilder();
            sb.append(displayMetrics.heightPixels);
            sb.append(",");
            sb.append(displayMetrics.widthPixels);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    private String g() {
        if (!this.b) {
            return CameraParams.FLASH_MODE_OFF;
        }
        try {
            return VERSION.RELEASE;
        } catch (Throwable unused) {
            return "";
        }
    }

    private String h() {
        if (!this.e) {
            return CameraParams.FLASH_MODE_OFF;
        }
        try {
            return String.valueOf(VERSION.SDK_INT);
        } catch (Throwable unused) {
            return "";
        }
    }

    private String i() {
        if (!this.f) {
            return CameraParams.FLASH_MODE_OFF;
        }
        try {
            return Secure.getString(this.d.getContentResolver(), "android_id");
        } catch (Throwable unused) {
            return "";
        }
    }

    private String j() {
        if (!this.g) {
            return CameraParams.FLASH_MODE_OFF;
        }
        try {
            return ((TelephonyManager) this.d.getSystemService("phone")).getSimOperator();
        } catch (Throwable unused) {
            return "";
        }
    }

    public int a() {
        return 3;
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        sb.append(f());
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(g());
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(h());
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(i());
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(j());
        return sb.toString();
    }

    public d d() {
        return d.DeviceInfoV2;
    }
}
