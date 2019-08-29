package com.alipay.mobile.antui.pop;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.utils.AuiLogger;

public class AUPopTipView {
    private static final String TAG = "AUPopTipView";
    private Context mContext;
    private AUIconView mDownIcon;
    private boolean mIsDown;
    private AUTextView mTipButton;
    private AULinearLayout mTipContainer;
    private AUTextView mTipTextView;
    private AUPopTipContainer mTipView;
    private PopupWindow mTipWindow;
    private AUIconView mUpIcon;
    /* access modifiers changed from: private */
    public OnToolTipClickedListener tipClickedListener;

    public interface OnToolTipClickedListener {
        void onToolTipClicked(View view);
    }

    public AUPopTipView(Context context) {
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        this.mTipView = (AUPopTipContainer) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.au_tip_pop_view, null);
        this.mTipContainer = (AULinearLayout) this.mTipView.findViewById(R.id.tip_container);
        this.mTipTextView = (AUTextView) this.mTipView.findViewById(R.id.tip_text);
        this.mTipButton = (AUTextView) this.mTipView.findViewById(R.id.tip_btn);
        this.mDownIcon = (AUIconView) this.mTipView.findViewById(R.id.tip_up_icon);
        this.mUpIcon = (AUIconView) this.mTipView.findViewById(R.id.tip_down_icon);
        this.mTipView.setOnClickListener(new c(this));
        this.mTipWindow = new PopupWindow(this.mTipView, -2, -2);
        this.mTipWindow.setBackgroundDrawable(context.getResources().getDrawable(17170445));
        this.mTipWindow.setFocusable(true);
        this.mTipWindow.setOutsideTouchable(true);
        this.mTipWindow.setSoftInputMode(16);
    }

    public void disappearByClickView() {
        this.mTipWindow.setFocusable(false);
        this.mTipWindow.setOutsideTouchable(false);
    }

    public void disappearByClickOutside() {
        this.mTipWindow.setFocusable(true);
        this.mTipWindow.setOutsideTouchable(true);
    }

    public void setTipClickedListener(OnToolTipClickedListener tipClickedListener2) {
        this.tipClickedListener = tipClickedListener2;
    }

    public void showTipView(View anchorView, CharSequence text) {
        setDefaultDown(anchorView);
        showTipView(anchorView, text, null, this.mIsDown);
    }

    public void showTipView(View anchorView, CharSequence text, boolean isDown) {
        showTipView(anchorView, text, null, isDown);
    }

    public void showTipView(View anchorView, CharSequence text, String btnText) {
        setDefaultDown(anchorView);
        showTipView(anchorView, text, btnText, this.mIsDown);
    }

    public void showTipView(View anchorView, CharSequence text, String btnText, boolean isDown) {
        if (TextUtils.isEmpty(text)) {
            AuiLogger.debug(TAG, "text is null");
        } else if (anchorView == null) {
            AuiLogger.debug(TAG, "anchorView is null");
        } else {
            if (!TextUtils.isEmpty(btnText)) {
                this.mTipButton.setText(btnText);
                this.mTipButton.setVisibility(0);
                disappearByClickView();
            } else {
                this.mTipButton.setVisibility(8);
                disappearByClickOutside();
            }
            this.mTipTextView.setText(text);
            this.mTipView.setTipLocate(this.mContext, this.mTipWindow, this.mTipContainer, this.mUpIcon, this.mDownIcon, anchorView, isDown, this.mTipTextView, this.mTipButton);
        }
    }

    private void setDefaultDown(View anchorView) {
        if (isUpScreen(anchorView)) {
            this.mIsDown = false;
        } else {
            this.mIsDown = true;
        }
    }

    private boolean isUpScreen(View anchorView) {
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        if (location[1] + (anchorView.getHeight() / 2) < AUPopTipContainer.getScreenHeight(this.mContext) / 2) {
            return true;
        }
        return false;
    }

    public void dismiss() {
        if (this.mContext != null && (this.mContext instanceof Activity) && !((Activity) this.mContext).isFinishing() && this.mTipWindow != null) {
            this.mTipWindow.dismiss();
        }
    }
}
