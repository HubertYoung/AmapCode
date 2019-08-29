package com.autonavi.minimap.search.inter.impl;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchresult.service.callback.AosSearchCallBack;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import org.json.JSONObject;

public class SearchRequestManagerImpl$1 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ CompatDialog a;
    final /* synthetic */ AosSearchCallBack b;
    final /* synthetic */ ela c;

    SearchRequestManagerImpl$1(ela ela, CompatDialog compatDialog, AosSearchCallBack aosSearchCallBack) {
        this.c = ela;
        this.a = compatDialog;
        this.b = aosSearchCallBack;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (this.a != null) {
            this.a.dismiss();
        }
        try {
            String str = new String((byte[]) aosByteResponse.getResult(), "utf-8");
            if (!TextUtils.isEmpty(str)) {
                InfoliteResult a2 = ((bck) a.a.a(bck.class)).a().a(new JSONObject(str));
                a2.responseHeader.f = true;
                List<aup> a3 = bcs.a(a2);
                if (a3 != null && a3.size() > 0) {
                    LogManager.actionLogV25(LogConstant.SEARCH_RESULT_LIST, LogConstant.MAIN_MAP_GUIDE_MAP_SHOW, new SimpleEntry(TrafficUtil.KEYWORD, bcs.b(a2) ? a2.mWrapper.keywords : ""));
                }
                if (this.b != null) {
                    this.b.callback(a2);
                }
                return;
            }
            if (this.b != null) {
                this.b.error(new Throwable(UserTrackerConstants.EM_REQUEST_FAILURE), false);
            }
        } catch (Exception unused) {
            if (this.b != null) {
                this.b.error(new Throwable(UserTrackerConstants.EM_REQUEST_FAILURE), false);
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            this.a.dismiss();
        }
        this.b.error(new Throwable(UserTrackerConstants.EM_REQUEST_FAILURE), false);
    }
}
