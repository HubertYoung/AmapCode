package com.jiuyan.inimage;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.q;

/* compiled from: MockLauncherActivityAgent */
class w implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ z b;
    final /* synthetic */ MockLauncherActivityAgent c;

    w(MockLauncherActivityAgent mockLauncherActivityAgent, Activity activity, z zVar) {
        this.c = mockLauncherActivityAgent;
        this.a = activity;
        this.b = zVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        InSDKEntrance.initialize(this.a);
        x xVar = new x(this);
        y yVar = new y(this);
        MockLauncherActivityAgent$1$3 mockLauncherActivityAgent$1$3 = new MockLauncherActivityAgent$1$3(this);
        q.a("mingtian ", "localConfig = " + this.b);
        q.a("mingtian ", "localConfig = " + this.b.d);
        q.a("mingtian ", "localConfig = " + this.b.c);
        q.a("mingtian ", "localConfig = " + this.b.b);
        q.a("mingtian ", "localConfig = " + this.b.e);
        q.a("mingtian ", "localConfig = " + this.b.a);
        q.a("mingtian ", "localConfig = " + this.b.f);
        q.a("mingtian ", "localConfig = " + this.b.g);
        InSDKConfig photoSaveDelegate = InSDKConfig.build().setFlag(0).bitmap(null).editCallback(yVar).faceDelegate(xVar).photoSaveDelegate(mockLauncherActivityAgent$1$3);
        if (this.b != null) {
            if (this.b.b) {
                photoSaveDelegate.addFlag(1);
            }
            if (this.b.c) {
                photoSaveDelegate.addFlag(2);
            }
            if (this.b.a) {
                photoSaveDelegate.addFlag(4);
            }
            if (this.b.d) {
                photoSaveDelegate.addFlag(16);
            }
            if (this.b.e) {
                photoSaveDelegate.addFlag(8);
            }
            if (!TextUtils.isEmpty(this.b.f)) {
                photoSaveDelegate.customText(InSDKConfig.KEY_CONFIG_TEXT_LEFT, this.b.f);
            }
            if (!TextUtils.isEmpty(this.b.g)) {
                photoSaveDelegate.customText(InSDKConfig.KEY_CONFIG_TEXT_FUNC_TEXT, this.b.g);
            }
        }
        InSDKEntrance.startEdit(this.a, photoSaveDelegate);
        this.a.finish();
    }
}
