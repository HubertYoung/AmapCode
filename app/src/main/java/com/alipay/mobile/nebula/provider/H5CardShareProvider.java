package com.alipay.mobile.nebula.provider;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.CardShareInfo;
import com.alipay.mobile.h5container.api.H5Page;

public interface H5CardShareProvider {

    public interface CardShareCallBack {
        void hideLoading();

        void onCardResult(CardShareInfo cardShareInfo);

        void onNoneCardResult();

        void showLoading();
    }

    void release();

    void requestShareInfo(String str, CardShareCallBack cardShareCallBack, H5Page h5Page, JSONObject jSONObject);

    void syncAutoJsContent(String str, JSONObject jSONObject);
}
