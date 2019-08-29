package com.alipay.mobile.beehive.compositeui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.beehive.R;

public class CustomDialog extends Dialog {
    private RelativeLayout dialogBg;
    private LayoutInflater inflater;
    private LinearLayout mButtonll;
    private RelativeLayout mContainerView;
    private Context mContext;
    private boolean mIsAutoCancel;
    /* access modifiers changed from: private */
    public OnClickListener mNegativeListener;
    private String mNegativeString;
    private Button mNegativetn;
    private Button mPositiveBtn;
    /* access modifiers changed from: private */
    public OnClickListener mPositiveListener;
    private String mPositiveString;
    private TextView mTitleView;
    private View rootView;
    private String sTitle;

    public CustomDialog(Context context, String title, String positiveString, String negativeString) {
        this(context, title, positiveString, negativeString, true);
    }

    public CustomDialog(Context context, String title, String positiveString, String negativeString, boolean isAutoCancel) {
        super(context, R.style.dialog_with_no_title_style_trans_bg);
        this.mContext = context;
        this.sTitle = title;
        this.mPositiveString = positiveString;
        this.mNegativeString = negativeString;
        this.mIsAutoCancel = isAutoCancel;
        this.inflater = LayoutInflater.from(context);
        this.mIsAutoCancel = isAutoCancel;
        init();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init() {
        View view = this.inflater.inflate(R.layout.custom_dialog, null);
        this.rootView = view;
        this.mPositiveBtn = (Button) view.findViewById(R.id.btn_positive);
        this.mNegativetn = (Button) view.findViewById(R.id.btn_negative);
        this.mTitleView = (TextView) view.findViewById(R.id.title);
        this.mButtonll = (LinearLayout) view.findViewById(R.id.button_ll);
        this.dialogBg = (RelativeLayout) view.findViewById(R.id.dialog_bg);
        this.mContainerView = (RelativeLayout) view.findViewById(R.id.container);
        this.mTitleView.setText(this.sTitle);
        setCanceledOnTouchOutside(this.mIsAutoCancel);
        initBtn();
        initContentView();
    }

    public void initContentView() {
        View contentView = getContentView();
        if (contentView != null) {
            getContainerView().addView(contentView);
        }
    }

    private void initBtn() {
        this.mNegativetn.setText(this.mNegativeString);
        if (TextUtils.isEmpty(this.mNegativeString)) {
            this.mNegativetn.setVisibility(4);
        }
        this.mNegativetn.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                CustomDialog.this.cancel();
                if (CustomDialog.this.mNegativeListener != null) {
                    CustomDialog.this.mNegativeListener.onClick(v);
                }
            }
        });
        this.mPositiveBtn.setText(this.mPositiveString);
        if (TextUtils.isEmpty(this.mPositiveString)) {
            this.mPositiveBtn.setVisibility(4);
        }
        this.mPositiveBtn.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                CustomDialog.this.dismiss();
                if (CustomDialog.this.mPositiveListener != null) {
                    CustomDialog.this.mPositiveListener.onClick(v);
                }
            }
        });
    }

    public void show() {
        super.show();
        setContentView(this.rootView);
        LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = -1;
        getWindow().setBackgroundDrawable(this.mContext.getResources().getDrawable(R.color.transparent));
        getWindow().setAttributes(layoutParams);
        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            int padding = getContext().getResources().getDimensionPixelSize(com.alipay.mobile.antui.R.dimen.AU_SPACE10);
            decorView.setPadding(padding, 0, padding, 0);
        }
    }

    public void setPositiveListener(OnClickListener mPositiveListener2) {
        this.mPositiveListener = mPositiveListener2;
    }

    public void setNegativeListener(OnClickListener mNegativeListener2) {
        this.mNegativeListener = mNegativeListener2;
    }

    public TextView getTitleView() {
        return this.mTitleView;
    }

    public Button getPositiveBtn() {
        return this.mPositiveBtn;
    }

    public Button getNegativetn() {
        return this.mNegativetn;
    }

    public RelativeLayout getDialogBg() {
        return this.dialogBg;
    }

    public LinearLayout getButtonll() {
        return this.mButtonll;
    }

    public RelativeLayout getContainerView() {
        return this.mContainerView;
    }

    public View getContentView() {
        return null;
    }
}
