package com.jiuyan.inimage.b;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.jiuyan.inimage.InSDKEntrance;

/* compiled from: AlipayDownloader */
public class l {
    private final String a = "SingleFileDownloader";
    private Context b;
    /* access modifiers changed from: private */
    public s c;
    /* access modifiers changed from: private */
    public boolean d;
    private Handler e;
    private MultimediaFileService f;
    private String g;

    public l(Context context) {
        this.b = context;
        this.e = new Handler();
        this.f = (MultimediaFileService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaFileService.class.getName());
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        this.e.post(new m(this, str));
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        this.e.post(new n(this, str));
    }

    public void a(String str, String str2, String str3, s sVar) {
        this.c = sVar;
        this.g = str2;
        a(str, str2, str3);
    }

    private void a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2)) {
            a(str);
        } else {
            this.f.downLoad(str2, (APFileDownCallback) new o(this, str), InSDKEntrance.getDownloadBiz());
        }
    }
}
