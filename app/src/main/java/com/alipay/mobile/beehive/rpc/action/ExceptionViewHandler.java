package com.alipay.mobile.beehive.rpc.action;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.rpc.RpcSubscriber;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.RpcUtil;
import com.alipay.mobile.beehive.rpc.model.FollowAction;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Map;

public class ExceptionViewHandler {
    public static void run(DefaultActionProcessor p, RpcUiProcessor rr, FollowAction action) {
        String alertBtnText;
        try {
            Map extInfo = action.extInfo;
            String desc = "";
            String subDesc = "";
            String formatSubDesc = "";
            String btnText = "";
            boolean isForceShow = false;
            String exceptionViewType = ActionConstant.EXCEPTION_VIEW_TYPE_EMPTY;
            if (extInfo != null) {
                desc = extInfo.get("desc");
                if (desc == null) {
                    desc = "";
                }
                subDesc = extInfo.get(ActionConstant.SUB_DESC);
                if (subDesc == null) {
                    subDesc = "";
                }
                exceptionViewType = extInfo.get(ActionConstant.EXCEPTION_VIEW_TYPE);
                String forceShowStr = extInfo.get(ActionConstant.FORCE_SHOW);
                if (TextUtils.equals(forceShowStr, "true") || TextUtils.equals(forceShowStr, "1")) {
                    isForceShow = true;
                }
                if (TextUtils.isEmpty(exceptionViewType)) {
                    exceptionViewType = extInfo.get("type");
                }
                formatSubDesc = RpcUtil.formatTextForDebug(subDesc);
                btnText = extInfo.get(ActionConstant.BTN_TEXT);
            }
            final DefaultActionProcessor defaultActionProcessor = p;
            final RpcUiProcessor rpcUiProcessor = rr;
            final FollowAction followAction = action;
            AnonymousClass1 r0 = new Runnable() {
                public final void run() {
                    defaultActionProcessor.runTriggerActions(rpcUiProcessor, followAction, "click");
                }
            };
            RpcSubscriber rs = p.getRpcSubscriber();
            if (rs == null || !rs.isLoadDataSuccess() || isForceShow) {
                DebugUtil.log((String) RpcConstant.TAG, "显示FlowTipView 文案=" + desc + ", " + subDesc);
                if (TextUtils.equals(exceptionViewType, ActionConstant.EXCEPTION_VIEW_TYPE_EMPTY)) {
                    rr.showEmptyView(desc, formatSubDesc, r0, btnText);
                } else if (TextUtils.equals(exceptionViewType, ActionConstant.EXCEPTION_VIEW_TYPE_WARN)) {
                    rr.showWarn(desc, formatSubDesc, r0, btnText);
                } else if (TextUtils.equals(exceptionViewType, ActionConstant.EXCEPTION_VIEW_TYPE_OVERFLOW)) {
                    rr.showOverflow(desc, formatSubDesc, r0, btnText);
                }
            } else {
                DebugUtil.log((String) RpcConstant.TAG, "rpc已加载数据成功(比如有缓存), 显示FlowTipView转化为显示alert, 文案=" + desc + ", " + subDesc);
                if (TextUtils.isEmpty(btnText)) {
                    alertBtnText = rr.getActivity().getResources().getString(R.string.confirm);
                } else {
                    alertBtnText = btnText;
                }
                final AnonymousClass1 r02 = r0;
                rr.alert(desc, subDesc, alertBtnText, new OnClickListener() {
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        r02.run();
                    }
                }, "", null, false);
            }
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
        }
    }
}
