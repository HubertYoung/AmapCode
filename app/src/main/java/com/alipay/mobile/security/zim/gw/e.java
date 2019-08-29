package com.alipay.mobile.security.zim.gw;

import com.alipay.bis.common.service.facade.gw.zim.ZimDispatchJsonGwFacade;
import com.alipay.bis.common.service.facade.gw.zim.ZimValidateGwResponse;
import com.alipay.bis.common.service.facade.gw.zim.ZimValidateJsonGwRequest;
import com.alipay.mobile.security.bio.api.BioResponse;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.local.rpc.BioRPCService;
import com.alipay.mobile.security.bio.utils.BioLog;
import java.util.HashMap;
import java.util.Map;

/* compiled from: JsonGwService */
final class e implements Runnable {
    final /* synthetic */ ZimValidateJsonGwRequest a;
    final /* synthetic */ BioResponse b;
    final /* synthetic */ JsonGwService c;

    e(JsonGwService jsonGwService, ZimValidateJsonGwRequest zimValidateJsonGwRequest, BioResponse bioResponse) {
        this.c = jsonGwService;
        this.a = zimValidateJsonGwRequest;
        this.b = bioResponse;
    }

    public final void run() {
        ZimValidateGwResponse zimValidateGwResponse;
        BioLog.d(this.a);
        try {
            zimValidateGwResponse = ((ZimDispatchJsonGwFacade) ((BioRPCService) BioServiceManager.getCurrentInstance().getBioService(BioRPCService.class)).getRpcProxy(ZimDispatchJsonGwFacade.class)).validate(this.a);
        } catch (Throwable th) {
            BioLog.w(th);
            zimValidateGwResponse = null;
        }
        if (zimValidateGwResponse == null) {
            zimValidateGwResponse = new ZimValidateGwResponse();
            zimValidateGwResponse.productRetCode = 1001;
            zimValidateGwResponse.validationRetCode = 1001;
        }
        BioLog.d(String.valueOf(zimValidateGwResponse));
        if (this.b != null) {
            Map<String, String> ext = this.b.getExt();
            if (ext != null && !ext.isEmpty()) {
                if (zimValidateGwResponse.extParams == null) {
                    zimValidateGwResponse.extParams = new HashMap();
                }
                zimValidateGwResponse.extParams.putAll(ext);
            }
        }
        if (this.c.mMainHandler != null) {
            this.c.mMainHandler.post(new f(this, zimValidateGwResponse));
        }
    }
}
