package com.alipay.mobile.beehive.rpc;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.alipay.mobile.antui.basic.AUNetErrorView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.antui.dialog.AUImageDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickNegativeListener;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.rpc.action.ActionUtil;
import com.alipay.mobile.beehive.rpc.misc.FullPageNoticeFactory;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.widget.SimpleToast;
import com.alipay.mobile.commonui.widget.APFlowTipView;
import com.alipay.mobile.commonui.widget.APTitleBar;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.ui.ActivityResponsable;
import java.lang.ref.WeakReference;

public class RpcUiProcessor {
    public static final String DEFAULT_STRING = null;
    private WeakReference<Activity> activityRef;
    private String emptySubText;
    private String emptyText;
    private int flowTipHolderId;
    private AUNetErrorView flowTipView;
    private int flowTipViewBgColor;
    private WeakReference<Fragment> fragmentRef;
    private String loadingText;
    private String netErrorSubText;
    private String netErrorText;
    /* access modifiers changed from: private */
    public Runnable retryRunnable;
    private String retryText;
    private String warnSubText;
    private String warnText;

    public RpcUiProcessor(Activity act) {
        init(act);
    }

    public RpcUiProcessor(Fragment f) {
        this.fragmentRef = new WeakReference<>(f);
        init(f.getActivity());
    }

    private void init(Activity act) {
        this.emptyText = "";
        this.netErrorText = "";
        this.warnText = "";
        this.emptySubText = null;
        if (act != null) {
            this.activityRef = new WeakReference<>(act);
            try {
                Resources res = getActivity().getResources();
                this.loadingText = res.getString(R.string.loading);
                this.retryText = res.getString(com.alipay.mobile.antui.R.string.refresh_net);
                this.netErrorText = res.getString(com.alipay.mobile.antui.R.string.net_connection_error);
                this.netErrorSubText = res.getString(com.alipay.mobile.antui.R.string.net_connection_error_sub);
                this.warnText = res.getString(com.alipay.mobile.antui.R.string.net_system_busy);
                this.warnSubText = res.getString(com.alipay.mobile.antui.R.string.net_system_sub);
                this.emptyText = res.getString(com.alipay.mobile.antui.R.string.net_empty);
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            }
        }
    }

    public void showProgressDialog(boolean cancelable, OnCancelListener cancelListener) {
        if (getActivityResponsible() == null || !isUiValid()) {
            MicroApplicationContext ctx = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            if (ctx != null) {
                ctx.showProgressDialog(this.loadingText, cancelable, cancelListener);
                return;
            }
            return;
        }
        getActivityResponsible().showProgressDialog(this.loadingText, cancelable, cancelListener);
    }

    public void dismissProgressDialog() {
        if (getActivityResponsible() == null || !isUiValid()) {
            MicroApplicationContext ctx = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            if (ctx != null) {
                ctx.dismissProgressDialog();
                return;
            }
            return;
        }
        getActivityResponsible().dismissProgressDialog();
    }

    public void showTitleBarLoading() {
        View v = getTitleBar();
        if (v != null && isUiValid()) {
            if (v instanceof APTitleBar) {
                ((APTitleBar) v).startProgressBar();
            } else if (v instanceof AUTitleBar) {
                ((AUTitleBar) v).startProgressBar();
            }
        }
    }

    public void dismissTitleBarLoading() {
        View v = getTitleBar();
        if (v != null && isUiValid()) {
            if (v instanceof APTitleBar) {
                ((APTitleBar) v).stopProgressBar();
            } else if (v instanceof AUTitleBar) {
                ((AUTitleBar) v).stopProgressBar();
            }
        }
    }

    private View getTitleBar() {
        IRpcUiResponsible res = getRpcUiResponsible();
        if (res != null && res.getTitleBar() != null) {
            return getRpcUiResponsible().getTitleBar();
        }
        Activity act = getActivity();
        if (act == null || act.isFinishing()) {
            return null;
        }
        return ActivityUtil.findStandardTitleBarFromView(ActivityUtil.getActivityRootView(getActivity()));
    }

    public void setFlowTipHolderId(int id) {
        boolean changed = this.flowTipHolderId != id;
        this.flowTipHolderId = id;
        if (changed) {
            this.flowTipView = null;
        }
    }

    public void setFlowTipViewBgColor(int v) {
        boolean changed = this.flowTipHolderId != v;
        this.flowTipViewBgColor = v;
        if (changed) {
            this.flowTipView = null;
        }
    }

    public void showNetworkError(String mainText) {
        showNetworkError(mainText, null);
    }

    public void showNetworkError(String text, String subText) {
        showNetworkError(text, subText, this.retryRunnable);
    }

    public void showNetworkError(String text, String subText, Runnable runnable) {
        setFlowTipViewParams(16, getNotNullTextWithOther(text, this.netErrorText), getNotNullTextWithOther(subText, this.netErrorSubText), runnable);
    }

    public void showEmptyView(String tip, String subTip) {
        showEmptyView(tip, subTip, this.retryRunnable);
    }

    public void showEmptyView(String text, String subText, Runnable runnable) {
        showEmptyView(text, subText, runnable, null);
    }

    public void showEmptyView(String text, String subText, Runnable runnable, String btnText) {
        setFlowTipViewParams(17, getNotNullTextWithOther(text, this.emptyText), getNotNullTextWithOther(subText, this.emptySubText), runnable, btnText);
    }

    public void showWarn(String text, String subText) {
        showWarn(text, subText, this.retryRunnable);
    }

    public void showWarn(String text, String subText, Runnable runnable) {
        showWarn(text, subText, runnable, null);
    }

    public void showWarn(String text, String subText, Runnable runnable, String btnText) {
        setFlowTipViewParams(18, getNotNullTextWithOther(text, this.warnText), getNotNullTextWithOther(subText, this.warnSubText), runnable, btnText);
    }

    public void showOverflow(String text, String subText) {
        showOverflow(text, subText, this.retryRunnable);
    }

    public void showOverflow(String text, String subText, Runnable runnable) {
        showOverflow(text, subText, runnable, null);
    }

    public void showOverflow(String text, String subText, Runnable runnable, String btnText) {
        showOverflow(text, subText, runnable, btnText, 0, "");
    }

    public void showOverflow(String text, String subText, Runnable runnable, String btnText, int waitTime, String imgUrl) {
        setFlowTipViewParams(19, text, subText, runnable, btnText, waitTime, imgUrl);
    }

    public void showOverflowDialog(String mainText, String subText, final Runnable runnable, int waitTime, String imgUrl) {
        final AUImageDialog dialog = AUImageDialog.getInstance(getActivity());
        if (!TextUtils.isEmpty(mainText)) {
            dialog.setTitle(RpcUtil.formatTextForDebug(mainText));
        }
        if (subText != null) {
            dialog.setSubTitle(subText);
        } else {
            dialog.getSubTitleTextView().setVisibility(0);
        }
        OnClickListener mainClick = new OnClickListener() {
            public final void onClick(View view) {
                Runnable r = runnable == null ? RpcUiProcessor.this.retryRunnable : runnable;
                if (r != null) {
                    r.run();
                }
                dialog.dismiss();
            }
        };
        dialog.setCanceledOnTouch(true);
        DebugUtil.log((String) RpcConstant.TAG, "showOverflowDialog " + mainText);
        dialog.showWithTimer(waitTime, mainClick, null);
        if (!TextUtils.isEmpty(imgUrl)) {
            ActionUtil.loadImg(imgUrl, dialog);
        }
    }

    private String getNotNullTextWithOther(String text, String other) {
        return text == null ? other : text;
    }

    private void setFlowTipViewParams(int type, String tip, String subTip, Runnable r) {
        setFlowTipViewParams(type, tip, subTip, r, null);
    }

    private void setFlowTipViewParams(int type, String tip, String subTip, Runnable r, String actionText) {
        setFlowTipViewParams(type, tip, subTip, r, actionText, 0, "");
    }

    private void setFlowTipViewParams(int type, String tip, String subTip, final Runnable r, String actionText, int waitTime, String iconUrl) {
        if (isUiValid()) {
            createFlowTipViewIfNot();
            if (this.flowTipView != null) {
                this.flowTipView.resetNetErrorType(type);
                if (tip != null) {
                    this.flowTipView.setTips(tip);
                }
                if (subTip != null) {
                    this.flowTipView.setSubTips(subTip);
                }
                if (!TextUtils.isEmpty(iconUrl)) {
                    DebugUtil.log((String) RpcConstant.TAG, "iconUrl " + iconUrl);
                }
                OnClickListener l = null;
                if (r != null) {
                    l = new OnClickListener() {
                        public final void onClick(View view) {
                            r.run();
                        }
                    };
                }
                if (type == 19) {
                    this.flowTipView.setTimer(waitTime, l, null);
                } else {
                    if (actionText == DEFAULT_STRING) {
                        actionText = this.retryText;
                    }
                    if (r != null) {
                        this.flowTipView.setAction(actionText, l);
                    } else {
                        this.flowTipView.setNoAction();
                    }
                }
                ((View) this.flowTipView.getParent()).setVisibility(0);
                setHolderVisibilityIfHas(0);
            }
        }
    }

    public String getNetErrorText() {
        return this.netErrorText;
    }

    public void setNetErrorText(String netErrorText2) {
        this.netErrorText = netErrorText2;
    }

    public String getLoadingText() {
        return this.loadingText;
    }

    public void setLoadingText(String loadingText2) {
        this.loadingText = loadingText2;
    }

    public String getEmptyText() {
        return this.emptyText;
    }

    public void setEmptyText(String emptyText2) {
        this.emptyText = emptyText2;
    }

    public void setWarnText(String warnText2) {
        this.warnText = warnText2;
    }

    public String getWarnText() {
        return this.warnText;
    }

    public void setRetryRunnable(Runnable retryRunnable2) {
        this.retryRunnable = retryRunnable2;
    }

    public Runnable getRetryRunnable() {
        return this.retryRunnable;
    }

    public View createFlowTipViewIfNot() {
        if (this.flowTipView == null && getActivity() != null) {
            this.flowTipView = createAuFlowTipView();
            if (this.flowTipViewBgColor != 0) {
                this.flowTipView.setBackgroundColor(this.flowTipViewBgColor);
            } else {
                View v = ActivityUtil.getActivityXmlRootView(getActivity());
                if (v != null && v.getBackground() == null) {
                    LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "界面未设置任何背景, 使用mpaas基本背景backgroundColor");
                    ((View) this.flowTipView.getParent()).setBackgroundColor(getActivity().getResources().getColor(com.alipay.mobile.ui.R.color.backgroudColor));
                }
            }
        }
        return this.flowTipView;
    }

    /* access modifiers changed from: protected */
    public AUNetErrorView createAuFlowTipView() {
        return FullPageNoticeFactory.build(getActivity(), findFlowTipParentViewById(this.flowTipHolderId));
    }

    private View findFlowTipParentViewById(int id) {
        if (id > 0 && getActivity() != null) {
            ViewGroup root = (ViewGroup) getActivity().findViewById(16908290);
            if (root == null) {
                return null;
            }
            View v = root.findViewById(id);
            if (v != null) {
                return v;
            }
            return null;
        } else if (this.fragmentRef == null || this.fragmentRef.get() == null) {
            return null;
        } else {
            return ((Fragment) this.fragmentRef.get()).getView();
        }
    }

    public APFlowTipView getFlowTipView() {
        return null;
    }

    /* access modifiers changed from: protected */
    public APFlowTipView createFlowTipView() {
        return null;
    }

    public void hideFlowTipViewIfShow() {
        if (isUiValid() && this.flowTipView != null) {
            ((View) this.flowTipView.getParent()).setVisibility(8);
            setHolderVisibilityIfHas(8);
        }
    }

    public Object getUiHost() {
        Fragment f = getFragment();
        return f != null ? f : getActivity();
    }

    public IRpcUiResponsible getRpcUiResponsible() {
        if (getActivity() instanceof IRpcUiResponsible) {
            return (IRpcUiResponsible) getActivity();
        }
        return null;
    }

    private void setHolderVisibilityIfHas(int v) {
        View p = (View) this.flowTipView.getParent();
        if (this.flowTipHolderId > 0 && p.getParent() != null) {
            ((View) p.getParent()).setVisibility(v);
        }
    }

    private boolean isUiValid() {
        if (!(getActivity() != null && !getActivity().isFinishing())) {
            return false;
        }
        if (this.fragmentRef == null || this.fragmentRef.get() == null) {
            return true;
        }
        return !((Fragment) this.fragmentRef.get()).isDetached();
    }

    public String getNetErrorSlowText() {
        return this.netErrorText;
    }

    public Activity getActivity() {
        if (this.activityRef != null) {
            return (Activity) this.activityRef.get();
        }
        return null;
    }

    public Fragment getFragment() {
        if (this.fragmentRef != null) {
            return (Fragment) this.fragmentRef.get();
        }
        return null;
    }

    public ActivityResponsable getActivityResponsible() {
        Activity act = getActivity();
        if (act == null || !(act instanceof ActivityResponsable)) {
            return null;
        }
        return (ActivityResponsable) act;
    }

    public void setContentVisibility(int v) {
        if (getActivity() != null) {
            ViewGroup vg = ActivityUtil.findActivityContentRoot(getActivity());
            if (vg != null) {
                vg.setVisibility(v);
            } else {
                LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "查找activity content root失败, 可能是没有TitleBar?");
            }
        }
    }

    public boolean isActivityContentVisible() {
        return getActivity() != null && ActivityUtil.isActivityContentVisible(getActivity());
    }

    public void finishActivity() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public void toast(String v, int len) {
        if (!TextUtils.isEmpty(v)) {
            v = RpcUtil.formatTextForDebug(v);
        }
        if (getActivity() != null) {
            SimpleToast.showToastWithSuper(getActivity(), v, len);
            return;
        }
        MicroApplicationContext ctx = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        if (ctx != null && ctx.getTopActivity() != null) {
            Activity act = (Activity) ctx.getTopActivity().get();
            if (act != null) {
                SimpleToast.showToastWithSuper(act, v, len);
            }
        }
    }

    public void alert(String title, String content, String mainText, final DialogInterface.OnClickListener mainClick, String subText, final DialogInterface.OnClickListener subClick, boolean isModal) {
        if (!TextUtils.isEmpty(content)) {
            content = RpcUtil.formatTextForDebug(content);
        }
        Context ctx = null;
        if (getActivity() != null) {
            ctx = getActivity();
        } else {
            WeakReference w = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity();
            if (w != null) {
                ctx = (Context) w.get();
            }
        }
        if (ctx != null) {
            final AUNoticeDialog dialog = new AUNoticeDialog(ctx, title, content, mainText, subText, !isModal);
            dialog.setCancelable(!isModal);
            if (mainClick != null) {
                dialog.setPositiveListener(new OnClickPositiveListener() {
                    public final void onClick() {
                        mainClick.onClick(dialog, 0);
                    }
                });
            }
            if (subClick != null) {
                dialog.setNegativeListener(new OnClickNegativeListener() {
                    public final void onClick() {
                        subClick.onClick(dialog, 0);
                    }
                });
            }
            dialog.show();
            return;
        }
        DebugUtil.log((String) RpcConstant.TAG, (String) "RpcUiProcessor alert失败, context为空, 未传context或则topActivity为空");
    }
}
