package com.alipay.zoloz.toyger.upload;

import android.os.Handler;
import android.os.Looper;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.zoloz.toyger.bean.ToygerFrame;
import com.alipay.zoloz.toyger.face.ToygerFaceService;
import com.alipay.zoloz.toyger.workspace.FaceRemoteConfig;

public class NineShootManager {
    /* access modifiers changed from: private */
    public int count = 0;
    private boolean isFullRequirement = false;
    private FaceRemoteConfig mFaceRemoteProtocol;
    private Handler mMainHandler;
    ToygerFaceService mToygerFaceService;
    /* access modifiers changed from: private */
    public boolean recordFrame;

    public NineShootManager(ToygerFaceService toygerFaceService, FaceRemoteConfig faceRemoteConfig) {
        this.mToygerFaceService = toygerFaceService;
        this.mFaceRemoteProtocol = faceRemoteConfig;
        this.mMainHandler = new Handler(Looper.myLooper());
    }

    public void shootToygerFrame(ToygerFrame toygerFrame) {
        if (this.mFaceRemoteProtocol.getColl().getUploadMonitorPic() == 1 && toygerFrame != null && !this.isFullRequirement && !this.recordFrame) {
            this.recordFrame = true;
            this.mMainHandler.postDelayed(new a(this), 500);
            BioLog.i("shootFaceFrame");
            if (this.count >= 10) {
                this.isFullRequirement = true;
            } else {
                new Thread(new b(this, toygerFrame), "shootToygerFrame").start();
            }
        }
    }

    public boolean isNeedUpload() {
        return this.mFaceRemoteProtocol.getColl().getUploadMonitorPic() == 1;
    }

    public void destroy() {
        this.mFaceRemoteProtocol = null;
        this.isFullRequirement = false;
        this.count = 0;
    }
}
