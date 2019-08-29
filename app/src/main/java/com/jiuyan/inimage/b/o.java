package com.jiuyan.inimage.b;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import java.io.File;

/* compiled from: AlipayDownloader */
class o implements APFileDownCallback {
    final /* synthetic */ String a;
    final /* synthetic */ l b;

    o(l lVar, String str) {
        this.b = lVar;
        this.a = str;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onDownloadStart(APMultimediaTaskModel aPMultimediaTaskModel) {
    }

    public void onDownloadProgress(APMultimediaTaskModel aPMultimediaTaskModel, int i, long j, long j2) {
    }

    public void onDownloadBatchProgress(APMultimediaTaskModel aPMultimediaTaskModel, int i, int i2, long j, long j2) {
    }

    public void onDownloadFinished(APMultimediaTaskModel aPMultimediaTaskModel, APFileDownloadRsp aPFileDownloadRsp) {
        if (!this.b.d) {
            File file = new File(aPFileDownloadRsp.getFileReq().getSavePath());
            if (file.exists()) {
                if (file.length() > 0) {
                    this.b.b(this.a);
                } else {
                    this.b.a(this.a);
                }
            } else {
                this.b.a(this.a);
            }
        }
    }

    public void onDownloadError(APMultimediaTaskModel aPMultimediaTaskModel, APFileDownloadRsp aPFileDownloadRsp) {
        this.b.a(this.a);
    }
}
