package com.alipay.zoloz.toyger.workspace;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import com.alipay.mobile.security.bio.utils.BioLog;

/* compiled from: ToygerWorkspace */
final class l implements Runnable {
    final /* synthetic */ Bitmap a;
    final /* synthetic */ ToygerWorkspace b;

    l(ToygerWorkspace toygerWorkspace, Bitmap bitmap) {
        this.b = toygerWorkspace;
        this.a = bitmap;
    }

    public final void run() {
        try {
            if (this.b.mToygerCirclePattern != null && this.a != null) {
                this.b.mToygerCirclePattern.getGuassianBackground().setVisibility(0);
                this.b.mToygerCirclePattern.getGuassianBackground().setBackgroundDrawable(new BitmapDrawable(this.b.mBioServiceManager.getBioApplicationContext().getResources(), this.a));
            }
        } catch (Throwable th) {
            BioLog.e(th);
        }
    }
}
