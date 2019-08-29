package com.autonavi.minimap.bundle.msgbox.network;

import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayLogCallback extends FalconAosPrepareResponseCallback<daq> {
    private String a;
    private int b;
    private boolean c;

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* synthetic */ void a(Object obj) {
        if (((daq) obj).result) {
            StringBuilder sb = new StringBuilder("上传曝光成功,id=");
            sb.append(this.a);
            AMapLog.d("----redesign--msgbox-->", sb.toString());
            return;
        }
        a(this.a, this.b);
    }

    public DisplayLogCallback(String str, int i, boolean z) {
        this.a = str;
        this.b = i;
        this.c = z;
    }

    private static daq b(AosByteResponse aosByteResponse) {
        daq daq = new daq();
        try {
            daq.parser((byte[]) aosByteResponse.getResult());
        } catch (Exception e) {
            AMapLog.e("DisplayLogCallback", e.getLocalizedMessage());
        }
        return daq;
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        a(this.a, this.b);
    }

    private void a(String str, int i) {
        if (this.c) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", i == 3 ? H5Param.MENU_ICON : "banner");
                jSONObject.put("from", "ad_display");
                jSONObject.put(TrafficUtil.KEYWORD, str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder("上传曝光失败,jo.toString()=");
            sb.append(jSONObject.toString());
            AMapLog.d("----redesign---msgbox->", sb.toString());
            LogManager.actionLogV2("P00001", LogConstant.MESSAGE_BOX_SHOW_FAIL, jSONObject);
        }
    }
}
