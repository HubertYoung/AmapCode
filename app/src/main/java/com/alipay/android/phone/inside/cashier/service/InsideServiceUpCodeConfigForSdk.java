package com.alipay.android.phone.inside.cashier.service;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.app.helper.ChannelHelper;
import com.alipay.android.app.util.LogUtils;
import com.alipay.android.phone.inside.cashier.utils.InsideEnvBuilder;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import org.json.JSONObject;

public class InsideServiceUpCodeConfigForSdk extends AbstractInsideService<Bundle, Bundle> {
    static final String KEY_ASSIGNEDCHANNEL_CHANNEL = "assignedChannel";
    static final String KEY_CHANNEL_FULLNAME = "channelFullName";
    static final String KEY_CHANNEL_INDEX = "channelIndex";
    static final String KEY_CHANNEL_TIPS = "channelTips";
    static final String KEY_LOGO_URL = "logoUrl";
    protected static final String TAG = "inside";

    public Bundle startForResult(Bundle bundle) throws Exception {
        Context context = getContext();
        String externalInfo = getExternalInfo();
        LoggerFactory.d().a("tidContext", BehaviorType.EVENT, "UpConfigTidContext", "context:".concat(String.valueOf(context)));
        return buildResult(ChannelHelper.requestChannel(context, externalInfo));
    }

    private Bundle buildResult(String str) throws Exception {
        if (TextUtils.isEmpty(str)) {
            throw new Exception("decodeResult is empty");
        }
        Bundle bundle = new Bundle();
        JSONObject jSONObject = new JSONObject(str);
        String optString = jSONObject.optString(KEY_CHANNEL_INDEX);
        String optString2 = jSONObject.optString(KEY_CHANNEL_FULLNAME);
        String optString3 = jSONObject.optString(KEY_ASSIGNEDCHANNEL_CHANNEL);
        String optString4 = jSONObject.optString(KEY_CHANNEL_TIPS);
        String optString5 = jSONObject.optString(KEY_LOGO_URL);
        if (!TextUtils.isEmpty(optString) || !TextUtils.isEmpty(optString2) || !TextUtils.isEmpty(optString3) || !TextUtils.isEmpty(optString2)) {
            bundle.putString(KEY_CHANNEL_INDEX, optString);
            bundle.putString(KEY_CHANNEL_FULLNAME, optString2);
            bundle.putString(KEY_ASSIGNEDCHANNEL_CHANNEL, optString3);
            bundle.putString(KEY_CHANNEL_TIPS, optString4);
            bundle.putString(KEY_LOGO_URL, optString5);
        } else {
            LogUtils.record(1, "InsideServiceUpCodeConfig::buildResult", "config null");
        }
        return bundle;
    }

    private String getExternalInfo() throws Exception {
        String str;
        String e = RunningConfig.e();
        if (e == null) {
            e = "";
        }
        if (StaticConfig.k()) {
            str = String.format("biz_type=consult_channel&biz_identity=userassets10001&user_id=%s&trade_from=6003&app_name=tb&extern_token=%s", new Object[]{e, OutsideConfig.m()});
        } else {
            str = String.format("biz_type=consult_channel&biz_identity=userassets10001&user_id=%s&trade_from=6003&app_name=%s", new Object[]{e, StaticConfig.l() ? TAG : BehavorReporter.PROVIDE_BY_ALIPAY});
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("insideEnv", new InsideEnvBuilder().getInsideEnv());
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("&bizcontext=");
        sb.append(jSONObject.toString());
        String sb2 = sb.toString();
        LoggerFactory.f().b((String) TAG, "InsideServiceUpCodeConfig::getExternalInfo > ".concat(String.valueOf(sb2)));
        return sb2;
    }
}
