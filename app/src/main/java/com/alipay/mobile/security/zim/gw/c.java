package com.alipay.mobile.security.zim.gw;

import com.alipay.bis.common.service.facade.gw.zim.ZimDispatchJsonGwFacade;
import com.alipay.bis.common.service.facade.gw.zim.ZimInitGwRequest;
import com.alipay.bis.common.service.facade.gw.zim.ZimInitGwResponse;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.local.rpc.BioRPCService;
import com.alipay.mobile.security.bio.utils.BioLog;

/* compiled from: JsonGwService */
final class c implements Runnable {
    final /* synthetic */ ZimInitGwRequest a;
    final /* synthetic */ JsonGwService b;

    c(JsonGwService jsonGwService, ZimInitGwRequest zimInitGwRequest) {
        this.b = jsonGwService;
        this.a = zimInitGwRequest;
    }

    public final void run() {
        ZimInitGwResponse zimInitGwResponse;
        BioLog.d(this.a);
        ZimDispatchJsonGwFacade zimDispatchJsonGwFacade = (ZimDispatchJsonGwFacade) ((BioRPCService) BioServiceManager.getCurrentInstance().getBioService(BioRPCService.class)).getRpcProxy(ZimDispatchJsonGwFacade.class);
        try {
            if (this.a.zimId.contains("_bis")) {
                zimInitGwResponse = zimDispatchJsonGwFacade.init(this.a);
            } else {
                zimInitGwResponse = zimDispatchJsonGwFacade.initStandard(this.a);
            }
        } catch (Throwable th) {
            BioLog.w(th);
            zimInitGwResponse = null;
        }
        if (zimInitGwResponse == null) {
            zimInitGwResponse = new ZimInitGwResponse();
            zimInitGwResponse.retCode = 200;
        }
        BioLog.d(String.valueOf(zimInitGwResponse));
        if (this.b.mMainHandler != null) {
            this.b.mMainHandler.post(new d(this, zimInitGwResponse));
        }
    }
}
