package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUDialog;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.iconfont.AUIconView;

public class AUCustomDialog extends AUDialog {
    /* access modifiers changed from: private */
    public OnClickListener closeClickListener;
    private int closeColor;
    private View customView;
    private boolean hasCloseBtn;
    private int horizonMaskSpace;
    private LayoutParams layoutParams;
    private AUIconView mCloseBtn;

    public AUCustomDialog(Context context, View customView2) {
        this(context, customView2, null);
    }

    public AUCustomDialog(Context context, View customView2, LayoutParams layoutParams2) {
        super(context, R.style.noTitleTransBgDialogStyle);
        this.hasCloseBtn = true;
        this.customView = customView2;
        this.layoutParams = layoutParams2;
    }

    public void setCloseColor(int color) {
        this.closeColor = color;
    }

    public void setHasCloseBtn(boolean hasCloseBtn2) {
        this.hasCloseBtn = hasCloseBtn2;
    }

    public void setCloseClickListener(OnClickListener closeClickListener2) {
        this.closeClickListener = closeClickListener2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AURelativeLayout rootView = (AURelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.au_custom_dialog, null);
        this.mCloseBtn = (AUIconView) rootView.findViewById(R.id.dialog_close);
        rootView.addView(this.customView, 0);
        setContentView(rootView);
        this.horizonMaskSpace = getContext().getResources().getDimensionPixelSize(R.dimen.custom_horizon_padding);
        initCloseBtn();
    }

    private void initCloseBtn() {
        if (this.hasCloseBtn) {
            this.mCloseBtn.setVisibility(0);
            this.mCloseBtn.setOnClickListener(new p(this));
            if (this.closeColor != 0) {
                this.mCloseBtn.setIconfontColor(this.closeColor);
            }
        }
    }

    public void show() {
        super.show();
        WindowManager.LayoutParams windowLayoutParams = getWindow().getAttributes();
        if (this.layoutParams == null) {
            setWindowMaxWidth(this.horizonMaskSpace);
            return;
        }
        windowLayoutParams.width = this.layoutParams.width;
        windowLayoutParams.height = this.layoutParams.height;
        getWindow().setAttributes(windowLayoutParams);
    }
}
