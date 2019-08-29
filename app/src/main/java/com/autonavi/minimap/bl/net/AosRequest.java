package com.autonavi.minimap.bl.net;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AosRequest extends HttpRequest {
    public static final int COMMON_PARAM_POLICY_IN_BODY = 2;
    public static final int COMMON_PARAM_POLICY_IN_URL = 1;
    public static final int COMMON_PARAM_POLICY_NA = 0;
    private static final String FEATURE_COMMON_PARAM_DISABLE = "flag_request_disable_common_param";
    private static final String FEATURE_COMMON_PARAM_POLICY = "flag_request_common_param_wrapper";
    public static final int OUTPUT_FORMAT_BIN = 3;
    public static final int OUTPUT_FORMAT_JSON = 0;
    public static final int OUTPUT_FORMAT_JSONP = 2;
    public static final int OUTPUT_FORMAT_XML = 1;
    private int mCommonParamPolicy = 1;
    private List<String> mDisabledCommonParams = new ArrayList();
    private String mEncryptSignParams;
    private int mOutputFormat = 0;
    private List<String> mSignParams = new ArrayList();

    public AosRequest() {
        setHttpBodyRecvType(0);
    }

    public void addSignParam(String str) {
        this.mSignParams.add(str);
    }

    public void setSignParams(String[] strArr) {
        this.mSignParams.clear();
        this.mSignParams.addAll(Arrays.asList(strArr));
    }

    public void setEncryptSignParams(String str) {
        this.mEncryptSignParams = str;
    }

    public void setOutputFormat(int i) {
        this.mOutputFormat = i;
    }

    public void setCommonParamPolicy(int i) {
        this.mCommonParamPolicy = i;
    }

    public void setDisabledCommonParams(String[] strArr) {
        if (strArr != null) {
            this.mDisabledCommonParams = Arrays.asList(strArr);
        } else {
            this.mDisabledCommonParams = new ArrayList();
        }
    }

    public void setDisabledCommonParams(String str) {
        setDisabledCommonParams(!TextUtils.isEmpty(str) ? str.split(",") : null);
    }

    public List<String> getSignParams() {
        return this.mSignParams;
    }

    public String getEncryptSignParams() {
        return this.mEncryptSignParams;
    }

    public int getOutputFormat() {
        return this.mOutputFormat;
    }

    public int getCommonParamPolicy() {
        return this.mCommonParamPolicy;
    }

    public List<String> getDisabledCommonParams() {
        return this.mDisabledCommonParams;
    }

    /* access modifiers changed from: protected */
    public void setCommand(String str, String str2) {
        super.setCommand(str, str2);
        if (FEATURE_COMMON_PARAM_POLICY.equals(str)) {
            int i = 1;
            if (!TextUtils.isEmpty(str2)) {
                try {
                    i = Integer.parseInt(str2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            setCommonParamPolicy(i);
            return;
        }
        if (FEATURE_COMMON_PARAM_DISABLE.equals(str)) {
            setDisabledCommonParams(str2);
        }
    }
}
