package com.alipay.zoloz.toyger.workspace;

import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.ZimRecordService;
import java.util.Arrays;
import java.util.HashSet;

/* compiled from: ToygerActivity */
final class a implements Runnable {
    final /* synthetic */ ToygerActivity a;

    a(ToygerActivity toygerActivity) {
        this.a = toygerActivity;
    }

    public final void run() {
        ((ZimRecordService) BioServiceManager.getCurrentInstance().getBioService(ZimRecordService.class)).setLogClassifier(new HashSet(Arrays.asList(this.a.mFaceRemoteConfig.getUpload().getString("log_classifier").split(MetaRecord.LOG_SEPARATOR))));
    }
}
