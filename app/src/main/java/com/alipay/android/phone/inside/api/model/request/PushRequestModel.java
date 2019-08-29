package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.PushOp;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.List;

public class PushRequestModel extends BaseModel<ResultCode> {
    private String appName;
    private String dynamicIds;
    private boolean payCodePageVisible = true;
    private String pushChannel = PushChannel.DEFAULT.getValue();
    private String pushContext;

    public enum PushChannel {
        DEFAULT("default"),
        ALIPAY_SYNC("alipay_sync");
        
        String value;

        private PushChannel(String str) {
            this.value = str;
        }

        public final String getValue() {
            return this.value;
        }
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getPushChannel() {
        return this.pushChannel;
    }

    public void setPushChannel(String str) {
        this.pushChannel = str;
    }

    public String getPushContext() {
        return this.pushContext;
    }

    public void setPushContext(String str) {
        this.pushContext = str;
    }

    public String getDynamicIds() {
        return this.dynamicIds;
    }

    public void setDynamicIds(List<String> list) {
        if (list != null && !list.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String append : list) {
                stringBuffer.append(append);
                stringBuffer.append(MetaRecord.LOG_SEPARATOR);
            }
            this.dynamicIds = stringBuffer.toString();
        }
    }

    public boolean isPayCodePageVisible() {
        return this.payCodePageVisible;
    }

    public void setPayCodePageVisible(boolean z) {
        this.payCodePageVisible = z;
    }

    public IBizOperation<ResultCode> getOperaion() {
        return new PushOp();
    }
}
