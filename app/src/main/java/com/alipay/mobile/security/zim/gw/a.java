package com.alipay.mobile.security.zim.gw;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.alipay.bis.common.service.facade.gw.zim.ZimInitGwRequest;
import com.alipay.bis.common.service.facade.gw.zim.ZimInitGwResponse;
import com.alipay.bis.common.service.facade.gw.zim.ZimValidateJsonGwRequest;
import com.alipay.mobile.security.bio.api.BioResponse;

/* compiled from: BaseGwService */
public abstract class a {
    b mGwListener;
    Handler mHandler;
    HandlerThread mHandlerThread;
    Handler mMainHandler = new Handler(Looper.getMainLooper());

    public abstract ZimInitGwResponse convert(String str);

    public abstract void init(ZimInitGwRequest zimInitGwRequest);

    public abstract void validate(BioResponse bioResponse, ZimValidateJsonGwRequest zimValidateJsonGwRequest);

    a(b bVar, String str) {
        this.mHandlerThread = new HandlerThread(str);
        this.mGwListener = bVar;
    }

    public void destroy() {
        if (this.mHandlerThread != null && this.mHandlerThread.isAlive()) {
            this.mHandlerThread.quit();
        }
        this.mHandlerThread = null;
        this.mHandler = null;
        this.mGwListener = null;
        this.mMainHandler = null;
    }
}
