package com.autonavi.minimap.agroup.network;

import android.content.Context;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.agroup.entity.GroupInfo;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;

public class GroupNetworkUtil$3 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ a a;

    public GroupNetworkUtil$3(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse != null) {
            byte[] responseBodyData = aosByteResponse.getResponseBodyData();
            if (responseBodyData != null) {
                try {
                    String str = new String(responseBodyData, "UTF-8");
                    cin.a;
                    cjo.a();
                    GroupInfo c = cju.c(str);
                    if (c != null) {
                        cjt a2 = cjt.a();
                        if (a2 != null) {
                            a2.a(c);
                            TeamStatus teamStatus = c.getTeamStatus();
                            if (teamStatus != null && (teamStatus == TeamStatus.STATUS_USER_IN_OTHER_TEAM || teamStatus == TeamStatus.STATUS_USER_IN_TEAM || teamStatus == TeamStatus.STATUS_USER_IN_THIS_TEAM || teamStatus == TeamStatus.STATUS_LEADER_IN_OTHER_TEAM)) {
                                cuh cuh = (cuh) a.a.a(cuh.class);
                                if (cuh != null) {
                                    cuh.q();
                                }
                            }
                        }
                    }
                    if (this.a != null) {
                        this.a.a(str);
                    }
                } catch (Exception unused) {
                    cin.a;
                    cjo.c();
                }
            }
        }
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext != null) {
            ToastHelper.showToast(appContext.getString(R.string.network_error));
        }
        if (this.a != null) {
            this.a.a("");
        }
    }
}
