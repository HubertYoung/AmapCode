package com.alipay.mobile.beehive.rpc.misc;

import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.antui.basic.AUNetErrorView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.rpc.ActivityUtil;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class FullPageNoticeFactory {
    private static int DEFAULT_TITLEBAR_HEIGHT = 48;
    private static int defaultTitleHeight;

    public static AUNetErrorView build(Activity context, View parentView) {
        if (parentView == null) {
            AUNetErrorView result = build(context);
            updateNetErrorViewOnIsFullPage(result, true);
            return result;
        } else if (!(parentView instanceof ViewGroup)) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, "FullPageNoticeFactory build(context, view(" + parentView.getClass().getName() + ") is null or is not ViewGroup");
            return null;
        } else {
            ViewGroup vg = (ViewGroup) parentView;
            AUNetErrorView result2 = buildWithTopMargin(context, vg, 0);
            updateNetErrorViewOnIsFullPage(result2, checkParentViewIsFull(context, vg));
            return result2;
        }
    }

    private static void updateNetErrorViewOnIsFullPage(AUNetErrorView netErrorView, boolean isFullPage) {
        LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, "isFullPage=" + isFullPage);
        netErrorView.setIsSimpleType(!isFullPage);
        netErrorView.setButtonBottom(isFullPage);
    }

    private static boolean checkParentViewIsFull(Activity context, ViewGroup parentView) {
        int height = context.getWindow().getDecorView().getHeight();
        int ph = parentView.getMeasuredHeight();
        int titleBarBottom = getTitleBottomOffset(context);
        boolean v = ph + titleBarBottom >= height;
        DebugUtil.log((String) RpcConstant.TAG, "checkParentViewIsFull=" + v + ": parentHeight=" + ph + ",activity height=" + height + ", titleBarBottom=" + titleBarBottom);
        return v;
    }

    private static AUNetErrorView build(Activity context) {
        return buildWithTopMargin(context, getActivityRootView(context), getTitleBottomOffset(context));
    }

    private static int getTitleBottomOffset(Activity context) {
        return getTitleHeight(context);
    }

    private static int getTitleHeight(Activity context) {
        int result = 0;
        View titleBar = ActivityUtil.findExtendTitleBarFromView(ActivityUtil.getActivityIdContentView(context));
        if (titleBar != null) {
            result = titleBar.getMeasuredHeight();
        }
        if (result != 0) {
            return result;
        }
        if (defaultTitleHeight <= 0) {
            defaultTitleHeight = (int) TypedValue.applyDimension(1, (float) DEFAULT_TITLEBAR_HEIGHT, context.getResources().getDisplayMetrics());
        }
        return defaultTitleHeight;
    }

    private static ViewGroup getActivityRootView(Activity context) {
        return (ViewGroup) context.findViewById(16908290);
    }

    private static AUNetErrorView buildWithTopMargin(Activity context, ViewGroup parent, int topMargin) {
        MarginLayoutParams params;
        if (parent instanceof FrameLayout) {
            LayoutParams lp = new LayoutParams(-1, -1);
            lp.gravity = 0;
            params = lp;
        } else {
            params = new MarginLayoutParams(-1, -1);
        }
        params.topMargin = topMargin;
        params.bottomMargin = 0;
        params.leftMargin = 0;
        params.rightMargin = 0;
        return buildWithLayoutParams(context, parent, params);
    }

    private static AUNetErrorView buildWithLayoutParams(Activity context, ViewGroup parent, ViewGroup.LayoutParams lp) {
        ViewGroup flowTipGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.rpc_full_page_notice_view, null);
        if (flowTipGroup.getParent() != parent) {
            parent.addView(flowTipGroup, lp);
        }
        return (AUNetErrorView) flowTipGroup.getChildAt(0);
    }
}
