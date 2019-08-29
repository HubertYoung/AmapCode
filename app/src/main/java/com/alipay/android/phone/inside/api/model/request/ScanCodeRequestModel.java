package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.ScanCodeOp;
import com.alipay.android.phone.inside.api.model.scan.CodeTypeEnum;
import com.alipay.android.phone.inside.api.result.code.ScanCode;

public class ScanCodeRequestModel extends BaseModel<ScanCode> {
    private String code;
    private String codeType;
    private int minVersionCode;
    private boolean useInsideMode;

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

    public IBizOperation<ScanCode> getOperaion() {
        return new ScanCodeOp();
    }
}
