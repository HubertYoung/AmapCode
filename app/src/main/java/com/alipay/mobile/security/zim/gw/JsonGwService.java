package com.alipay.mobile.security.zim.gw;

import android.os.Handler;
import com.alibaba.fastjson.JSON;
import com.alipay.bis.common.service.facade.gw.zim.ZimInitGwRequest;
import com.alipay.bis.common.service.facade.gw.zim.ZimInitGwResponse;
import com.alipay.bis.common.service.facade.gw.zim.ZimValidateJsonGwRequest;
import com.alipay.mobile.security.bio.api.BioResponse;

public class JsonGwService extends a {
    public JsonGwService(b bVar) {
        super(bVar, "zim-json-upload");
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
    }

    public void init(ZimInitGwRequest zimInitGwRequest) {
        this.mHandler.post(new c(this, zimInitGwRequest));
    }

    public void validate(BioResponse bioResponse, ZimValidateJsonGwRequest zimValidateJsonGwRequest) {
        if (this.mHandler != null) {
            this.mHandler.post(new e(this, zimValidateJsonGwRequest, bioResponse));
        }
    }

    public ZimInitGwResponse convert(String str) {
        return (ZimInitGwResponse) JSON.parseObject(str, ZimInitGwResponse.class);
    }
}
