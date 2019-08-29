package com.ali.user.mobile.base;

import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.base.helper.ActivityUIHelper;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.login.LoginActivityCollections;
import com.ali.user.mobile.login.rds.RDSWraper;
import com.ali.user.mobile.ui.widget.APListPopDialog;
import com.ali.user.mobile.ui.widget.APListPopDialog.OnItemClickListener;
import com.ali.user.mobile.ui.widget.APNormalPopDialog.OnClickNegativeListener;
import com.ali.user.mobile.ui.widget.APNormalPopDialog.OnClickPositiveListener;
import com.ali.user.mobile.ui.widget.AUNetworkPopDialog;
import com.ali.user.mobile.ui.widget.PopMenuItem;
import com.ali.user.mobile.ui.widget.keyboard.APSafeEditText;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import java.util.ArrayList;

public abstract class BaseActivity extends AdaptorActivity {
    private static final String TAG = "BaseActivity";
    private boolean hasFocus;
    private ActivityUIHelper mActivityHelper;
    private boolean mExclude = false;
    protected boolean mIsDestroy = false;
    protected int mRightCornerDefaultVisible = 8;
    /* access modifiers changed from: private */
    public boolean waitForFocus;
    private View waitForFocusView;

    /* access modifiers changed from: protected */
    public void performDialogAction(PopMenuItem popMenuItem) {
    }

    /* access modifiers changed from: protected */
    public void performDialogAction(String str) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            this.mActivityHelper = new ActivityUIHelper(this);
            AliUserInit.a(getApplicationContext());
            RDSWraper.init(getApplicationContext());
        } catch (Throwable th) {
            AliUserLog.b(TAG, "oncreate error", th);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        LoginActivityCollections.a().a(this);
    }

    public void alert(String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2) {
        this.mActivityHelper.a(str, str2, str3, onClickListener, str4, onClickListener2);
    }

    public void alertPop(String str, String str2, String str3, String str4, OnClickPositiveListener onClickPositiveListener, String str5, OnClickNegativeListener onClickNegativeListener) {
        this.mActivityHelper.a(str, str2, str3, str4, onClickPositiveListener, str5, onClickNegativeListener);
    }

    public void toast(String str, int i) {
        this.mActivityHelper.a(str, i);
    }

    public void toast(String str) {
        this.mActivityHelper.a(str, 0);
    }

    public void showProgress(String str) {
        this.mActivityHelper.a(str);
    }

    public void dismissProgress() {
        this.mActivityHelper.b();
    }

    /* access modifiers changed from: protected */
    public void setButtonEnable(final Button button, final boolean z) {
        if (button != null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    button.setEnabled(z);
                }
            });
        }
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.hasFocus = z;
        if (z && this.waitForFocus) {
            invokeInputMethod(this.waitForFocusView);
        }
    }

    /* access modifiers changed from: protected */
    public void configRightCornerView(ViewGroup viewGroup) {
        if (DataflowMonitorModel.METHOD_NAME_CLOSE.equals(DataflowMonitorModel.METHOD_NAME_CLOSE)) {
            viewGroup.setVisibility(8);
            this.mRightCornerDefaultVisible = 8;
            return;
        }
        Adapter a = UIConfigManager.a();
        if (a == null) {
            viewGroup.setVisibility(8);
            this.mRightCornerDefaultVisible = 8;
            return;
        }
        View view = a.getView(1, null, viewGroup);
        if (view == null) {
            viewGroup.setVisibility(8);
            this.mRightCornerDefaultVisible = 8;
            return;
        }
        this.mRightCornerDefaultVisible = 0;
        viewGroup.setVisibility(0);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
    }

    public void showListDialog(final ArrayList<String> arrayList) {
        final APListPopDialog aPListPopDialog = new APListPopDialog(this, arrayList);
        aPListPopDialog.a((OnItemClickListener) new OnItemClickListener() {
            public final void a(int i) {
                aPListPopDialog.dismiss();
                BaseActivity.this.performDialogAction((String) arrayList.get(i));
            }
        });
        aPListPopDialog.show();
    }

    /* access modifiers changed from: protected */
    public void showListDialogWithTitle(String str, final ArrayList<PopMenuItem> arrayList) {
        final APListPopDialog aPListPopDialog = new APListPopDialog(str, arrayList, this);
        aPListPopDialog.setCanceledOnTouchOutside(false);
        aPListPopDialog.a((OnItemClickListener) new OnItemClickListener() {
            public final void a(int i) {
                aPListPopDialog.dismiss();
                BaseActivity.this.performDialogAction((PopMenuItem) arrayList.get(i));
            }
        });
        aPListPopDialog.show();
    }

    /* access modifiers changed from: protected */
    public void showInputMethodPannel(View view) {
        if (this.hasFocus) {
            invokeInputMethod(view);
            return;
        }
        this.waitForFocus = true;
        this.waitForFocusView = view;
    }

    private void invokeInputMethod(final View view) {
        getWindow().getDecorView().post(new Runnable() {
            public void run() {
                view.requestFocus();
                if (view instanceof APSafeEditText) {
                    APSafeEditText aPSafeEditText = (APSafeEditText) view;
                    if (aPSafeEditText.isPasswordType()) {
                        aPSafeEditText.showSafeKeyboard();
                    } else {
                        ((InputMethodManager) BaseActivity.this.getSystemService("input_method")).showSoftInput(view, 0);
                    }
                } else {
                    ((InputMethodManager) BaseActivity.this.getSystemService("input_method")).showSoftInput(view, 0);
                }
                BaseActivity.this.waitForFocus = false;
            }
        });
    }

    public void closeInputMethod(View view) {
        try {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            String simpleName = getClass().getSimpleName();
            StringBuilder sb = new StringBuilder("closeInputMethod exception");
            sb.append(e.getMessage());
            AliUserLog.c(simpleName, sb.toString());
        }
    }

    public void handleRpcException(RpcException rpcException) {
        dismissProgress();
        if (rpcException == null || !rpcException.isClientError()) {
            AliUserLog.c(TAG, "not client error，throw rpcException");
            throw rpcException;
        }
        AliUserLog.c(getClass().getSimpleName(), "rpcException.isClientError, show network guide");
        runOnUiThread(new Runnable() {
            public void run() {
                if (!BaseActivity.this.isFinishing() && !BaseActivity.this.mIsDestroy) {
                    try {
                        new AUNetworkPopDialog(BaseActivity.this).show();
                    } catch (Exception e) {
                        AliUserLog.a(getClass().getSimpleName(), (Throwable) e);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setExclude(boolean z) {
        this.mExclude = z;
    }

    public boolean isExclude() {
        return this.mExclude;
    }

    public void finish() {
        AliUserLog.c(TAG, "finish()");
        if (this.mActivityHelper != null) {
            this.mActivityHelper.a();
        }
        super.finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        AliUserLog.c(TAG, "onDestroy()");
        this.mIsDestroy = true;
        if (this.mActivityHelper != null) {
            this.mActivityHelper.a();
        }
    }

    public boolean isActivityDestroy() {
        return this.mIsDestroy;
    }
}
