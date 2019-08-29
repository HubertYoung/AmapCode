package com.autonavi.minimap.account.modify;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.modify.model.ModifyPWResponse;
import com.autonavi.minimap.account.modify.param.ModifyPWParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ModifyPWRequestHolder {
    private static volatile ModifyPWRequestHolder instance;
    private AosRequest mModifyPWRequest;

    private ModifyPWRequestHolder() {
    }

    public static ModifyPWRequestHolder getInstance() {
        if (instance == null) {
            synchronized (ModifyPWRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new ModifyPWRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendModifyPW(ModifyPWParam modifyPWParam, dko<ModifyPWResponse> dko) {
        this.mModifyPWRequest = new AosPostRequest();
        AosRequest aosRequest = this.mModifyPWRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/password/update/");
        aosRequest.setUrl(sb.toString());
        this.mModifyPWRequest.addSignParam("channel");
        this.mModifyPWRequest.addSignParam("oldpassword");
        this.mModifyPWRequest.addSignParam("newpassword");
        this.mModifyPWRequest.addReqParam("oldpassword", modifyPWParam.oldpassword);
        this.mModifyPWRequest.addReqParam("newpassword", modifyPWParam.newpassword);
        in.a().a(this.mModifyPWRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<ModifyPWResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new ModifyPWResponse();
            }
        });
    }

    public void cancelModifyPW() {
        if (this.mModifyPWRequest != null) {
            in.a().a(this.mModifyPWRequest);
            this.mModifyPWRequest = null;
        }
    }
}
