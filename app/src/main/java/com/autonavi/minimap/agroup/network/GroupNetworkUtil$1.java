package com.autonavi.minimap.agroup.network;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.agroup.entity.TeamInfo;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;

public class GroupNetworkUtil$1 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ a a;
    final /* synthetic */ JsFunctionCallback b;

    public GroupNetworkUtil$1(a aVar, JsFunctionCallback jsFunctionCallback) {
        this.a = aVar;
        this.b = jsFunctionCallback;
    }

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        String str = null;
        if (aosByteResponse != null) {
            try {
                str = new String(aosByteResponse.getResponseBodyData(), "UTF-8");
            } catch (Exception unused) {
            }
        }
        cin.a;
        cjo.a();
        if (!TextUtils.isEmpty(str)) {
            TeamInfo e = cju.e(str);
            TeamStatus b2 = cju.b(str);
            cjt a2 = cjt.a();
            if (!(a2 == null || e == null || TextUtils.isEmpty(e.teamId))) {
                a2.a(e.teamId, e.teamNumber, b2);
            }
        }
        if (this.a != null) {
            this.a.a(str, this.b);
        }
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        cin.a;
        cjo.a();
        if (this.a != null) {
            this.a.a("", null);
        }
        if (this.b != null) {
            this.b.callback("");
        }
    }
}
