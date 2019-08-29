package com.alipay.android.phone.inside.api.model.iotads;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotads.IotAdsPreMemberCode;

public class IotAdsPreMemberModel extends IotAdsBaseModel<IotAdsPreMemberCode> {
    private String barToken;
    private boolean doubleDisplays;
    private String pid;
    private String storeId;
    private String timeout;
    private String userId;

    public String getBarToken() {
        return this.barToken;
    }

    public void setBarToken(String str) {
        this.barToken = str;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String str) {
        this.pid = str;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getStoreId() {
        return this.storeId;
    }

    public void setStoreId(String str) {
        this.storeId = str;
    }

    public String getTimeout() {
        return this.timeout;
    }

    public void setTimeout(String str) {
        this.timeout = str;
    }

    public boolean isDoubleDisplays() {
        return this.doubleDisplays;
    }

    public void setDoubleDisplays(boolean z) {
        this.doubleDisplays = z;
    }

    public IBizOperation<IotAdsPreMemberCode> getOperaion() {
        return new IBizOperation<IotAdsPreMemberCode>() {
            public IotAdsPreMemberCode parseResultCode(String str, String str2) {
                return IotAdsPreMemberCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_ADS_PREMEMBER;
            }
        };
    }
}
