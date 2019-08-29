package com.alipay.mobile.beehive.rpc.action;

import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.RpcUtil;
import com.alipay.mobile.beehive.rpc.model.FollowAction;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.common.utils.StringUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.Map;

public class AlertHandler {
    public static void run(DefaultActionProcessor p, RpcUiProcessor uiProcessor, FollowAction action) {
        Map extInfo = action.extInfo;
        if (extInfo != null) {
            String title = extInfo.get("title");
            String desc = extInfo.get("desc");
            String mainText = ActionUtil.getAlertBtnText(uiProcessor, ActionConstant.MAIN_BTN_TEXT, action);
            String subText = ActionUtil.getAlertBtnText(uiProcessor, ActionConstant.SUB_BTN_TEXT, action);
            boolean isModal = StringUtils.equalsIgnoreCase(extInfo.get("isModal"), "true");
            OnClickListener mainClickListener = (OnClickListener) ActionUtil.getAlertClickListener(p, true, mainText, uiProcessor, action);
            OnClickListener subClickListener = (OnClickListener) ActionUtil.getAlertClickListener(p, false, subText, uiProcessor, action);
            if (TextUtils.isEmpty(desc)) {
                desc = RpcUtil.getString(uiProcessor, R.string.default_follow_action_desc);
            }
            if (!TextUtils.isEmpty(desc)) {
                DebugUtil.log((String) RpcConstant.TAG, action.type + Token.SEPARATOR + desc);
                uiProcessor.alert(title, desc, mainText, mainClickListener, subText, subClickListener, isModal);
            }
        }
    }
}
