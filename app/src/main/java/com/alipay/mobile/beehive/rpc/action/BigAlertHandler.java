package com.alipay.mobile.beehive.rpc.action;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.dialog.AUImageDialog;
import com.alipay.mobile.antui.dialog.AUImageDialog.OnItemClickListener;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.RpcUtil;
import com.alipay.mobile.beehive.rpc.model.FollowAction;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.common.utils.StringUtils;
import java.util.ArrayList;
import java.util.Map;

public class BigAlertHandler {
    public static void run(DefaultActionProcessor p, RpcUiProcessor rr, FollowAction action) {
        Map extInfo = action.extInfo;
        if (extInfo != null) {
            String title = extInfo.get("title");
            String desc = extInfo.get("desc");
            String subDesc = extInfo.get(ActionConstant.SUB_DESC);
            String thirdDesc = extInfo.get(ActionConstant.THIRD_DESC);
            String imgUrl = extInfo.get(ActionConstant.IMG_URL);
            String mainBtnText = ActionUtil.getAlertBtnText(rr, ActionConstant.MAIN_BTN_TEXT, action);
            String subBtnText = ActionUtil.getAlertBtnText(rr, ActionConstant.SUB_BTN_TEXT, action);
            boolean isModal = StringUtils.equalsIgnoreCase(extInfo.get("isModal"), "true");
            final AUImageDialog dialog = AUImageDialog.getInstance(rr.getActivity());
            if (!TextUtils.isEmpty(title)) {
                dialog.setTitle(title);
            }
            if (!TextUtils.isEmpty(desc)) {
                dialog.setTitle(RpcUtil.formatTextForDebug(desc));
            }
            if (!TextUtils.isEmpty(subDesc)) {
                dialog.setSubTitle(subDesc);
            } else {
                dialog.setSubTitle("");
            }
            if (!TextUtils.isEmpty(thirdDesc)) {
                dialog.setThirdTitleText(thirdDesc);
            } else {
                dialog.setThirdTitleText("");
            }
            dialog.setCanceledOnTouch(!isModal);
            dialog.setCancelable(!isModal);
            final DefaultActionProcessor defaultActionProcessor = p;
            final RpcUiProcessor rpcUiProcessor = rr;
            final FollowAction followAction = action;
            final OnClickListener mainClick = new OnClickListener() {
                public final void onClick(View view) {
                    dialog.dismissWithoutAnim();
                    defaultActionProcessor.runTriggerActions(rpcUiProcessor, followAction, ActionConstant.TRIGGER_TYPE_MAIN_CLICK);
                }
            };
            final DefaultActionProcessor defaultActionProcessor2 = p;
            final RpcUiProcessor rpcUiProcessor2 = rr;
            final FollowAction followAction2 = action;
            final OnClickListener subClick = new OnClickListener() {
                public final void onClick(View view) {
                    dialog.dismissWithoutAnim();
                    defaultActionProcessor2.runTriggerActions(rpcUiProcessor2, followAction2, ActionConstant.TRIGGER_TYPE_SUB_CLICK);
                }
            };
            if (!TextUtils.isEmpty(mainBtnText) && !TextUtils.isEmpty(subBtnText)) {
                ArrayList btnTexts = new ArrayList();
                btnTexts.add(mainBtnText);
                btnTexts.add(subBtnText);
                AnonymousClass3 r0 = new OnItemClickListener() {
                    public final void onItemClick(int i) {
                        if (i == 0) {
                            mainClick.onClick(null);
                        } else if (i == 1) {
                            subClick.onClick(null);
                        }
                    }
                };
                dialog.setButtonListInfo(btnTexts, r0);
                dialog.getConfirmBtn().setVisibility(8);
            } else if (!TextUtils.isEmpty(mainBtnText)) {
                dialog.setButtonListInfo(new ArrayList(), null);
                dialog.getConfirmBtn().setVisibility(0);
                dialog.setConfirmBtnText(mainBtnText);
                dialog.setOnConfirmBtnClick(mainClick);
            } else {
                DebugUtil.log((String) RpcConstant.TAG, (String) "bigAlert无效，mainBtnText为空");
            }
            dialog.setLogoDefaultLoading();
            if (!TextUtils.isEmpty(imgUrl)) {
                ActionUtil.loadImg(imgUrl, dialog);
            }
            DebugUtil.log((String) RpcConstant.TAG, "bigAlert " + desc);
            dialog.showWithoutAnim();
        }
    }
}
