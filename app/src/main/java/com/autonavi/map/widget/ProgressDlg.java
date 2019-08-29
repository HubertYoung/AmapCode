package com.autonavi.map.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;
import com.amap.bundle.commonui.loading.LoadingView;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.minimap.R;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ProgressDlg extends CompatDialog {
    private static final String TAG = "progressDlg";
    private LoadingView mLoadingView;
    private TextView tvMsg;

    public interface OnSearchKeyEvent {
        void onSearchKeyEvent();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    public ProgressDlg(Activity activity) {
        this(activity, null);
    }

    public ProgressDlg(Activity activity, String str) {
        super(activity, R.style.custom_dlg);
        requestWindowFeature(1);
        setContentView(R.layout.widget_progress_dlg);
        this.mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        this.mLoadingView.setCloseIconVisibility(8);
        setLoadingView(this.mLoadingView);
        this.tvMsg = (TextView) findViewById(R.id.msg);
        if (!TextUtils.isEmpty(str)) {
            this.mLoadingView.setLoadingText(str);
        }
    }

    public ProgressDlg(Activity activity, String str, String str2) {
        super(activity, R.style.custom_dlg);
        requestWindowFeature(1);
        setContentView(R.layout.widget_progress_dlg);
        this.tvMsg = (TextView) findViewById(R.id.msg);
        this.mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        this.mLoadingView.setCloseIconVisibility(8);
        setLoadingView(this.mLoadingView);
        if (!TextUtils.isEmpty(str)) {
            this.mLoadingView.setLoadingText(str);
        }
        if (str2 != null && !str2.equals("")) {
            this.mLoadingView.setLoadingText(str2);
        }
    }

    public ProgressDlg(Activity activity, String str, String str2, boolean z) {
        super(activity, R.style.custom_dlg);
        requestWindowFeature(1);
        setContentView(R.layout.widget_progress_dlg);
        this.mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        this.mLoadingView.setCloseIconVisibility(8);
        setLoadingView(this.mLoadingView);
        this.tvMsg = (TextView) findViewById(R.id.msg);
        if (!TextUtils.isEmpty(str)) {
            this.mLoadingView.setLoadingText(str);
        }
        if (str2 != null && !str2.equals("")) {
            this.mLoadingView.setLoadingText(str2);
        }
    }

    public void setMessage(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.tvMsg.setText(str);
            if (this.mLoadingView != null) {
                this.mLoadingView.setLoadingText(str);
            }
        }
    }

    public void updateMsg(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.tvMsg.setText(str);
            if (this.mLoadingView != null) {
                this.mLoadingView.setLoadingText(str);
            }
        }
    }

    public void setTextMsgGravity(int i) {
        if (this.tvMsg != null) {
            this.tvMsg.setGravity(i);
        }
    }

    public LoadingView getLoadingView() {
        return this.mLoadingView;
    }

    public void setLoadingView(LoadingView loadingView) {
        this.mLoadingView = loadingView;
    }
}
