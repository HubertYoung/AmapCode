package com.alipay.zoloz.toyger.workspace;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import com.alipay.mobile.security.bio.utils.BioLog;

/* compiled from: ToygerWorkspace */
final class j implements Runnable {
    final /* synthetic */ Bitmap a;
    final /* synthetic */ ToygerWorkspace b;

    j(ToygerWorkspace toygerWorkspace, Bitmap bitmap) {
        this.b = toygerWorkspace;
        this.a = bitmap;
    }

    public final void run() {
        try {
            if (this.b.mToygerCirclePattern != null) {
                this.b.mToygerCirclePattern.getGuassianBackground().setVisibility(0);
                this.b.mToygerCirclePattern.getGuassianBackground().setBackgroundDrawable(new BitmapDrawable(this.b.mBioServiceManager.getBioApplicationContext().getResources(), this.a));
            }
            BioLog.i("onHighQualityFrame 显示成功");
        } catch (Throwable th) {
            BioLog.e((String) "显示HighQualityFrame出现异常！", th);
        }
    }
}
