package com.alipay.zoloz.toyger.upload;

import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.zoloz.toyger.ToygerService;
import com.alipay.zoloz.toyger.bean.ToygerFrame;

/* compiled from: NineShootManager */
final class b implements Runnable {
    final /* synthetic */ ToygerFrame a;
    final /* synthetic */ NineShootManager b;

    b(NineShootManager nineShootManager, ToygerFrame toygerFrame) {
        this.b = nineShootManager;
        this.a = toygerFrame;
    }

    public final void run() {
        try {
            this.b.mToygerFaceService.addMonitorImage(this.a.tgFrame);
            this.b.count = this.b.count + 1;
        } catch (Exception e) {
            BioLog.w((Throwable) e);
        } finally {
            String str = ToygerService.TAG;
            r3 = "NineShootManager.addMonitoryFrame() : ";
            StringBuilder append = new StringBuilder(r3).append(this.a);
            r3 = ", count=";
            BioLog.d(str, append.append(r3).append(this.b.count).toString());
        }
    }
}
