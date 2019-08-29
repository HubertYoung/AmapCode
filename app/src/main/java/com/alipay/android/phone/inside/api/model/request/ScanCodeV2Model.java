package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.scan.CodeTypeEnum;
import com.alipay.android.phone.inside.api.result.code.ScanCodeV2;

public class ScanCodeV2Model extends BaseModel<ScanCodeV2> {
    private String code;
    private String codeType;
    private int minVersionCode;
    private boolean routeDirectly = false;
    private int timeout;
    private boolean useInsideMode;

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int i) {
        this.timeout = i;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getCodeType() {
        return this.codeType;
    }

    public void setCodeType(CodeTypeEnum codeTypeEnum) {
        if (codeTypeEnum == null) {
            codeTypeEnum = CodeTypeEnum.BARCODE;
        }
        this.codeType = codeTypeEnum.getCodeName();
    }

    public boolean isUseInsideMode() {
        return this.useInsideMode;
    }

    public void setUseInsideMode(boolean z) {
        this.useInsideMode = z;
    }

    public int getMinVersionCode() {
        return this.minVersionCode;
    }

    public void setMinVersionCode(int i) {
        this.minVersionCode = i;
    }

    public boolean isRouteDirectly() {
        return this.routeDirectly;
    }

    public void setRouteDirectly(boolean z) {
        this.routeDirectly = z;
    }

    public IBizOperation<ScanCodeV2> getOperaion() {
        return new IBizOperation<ScanCodeV2>() {
            public ScanCodeV2 parseResultCode(String str, String str2) {
                return ScanCodeV2.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.SCAN_CODE_V2;
            }
        };
    }
}
