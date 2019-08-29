package com.alipay.mobile.antui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUEditText;
import com.alipay.mobile.antui.utils.AuiLogger;

public class AUInputDialog extends Dialog {
    private LinearLayout bottomLayout;
    private RelativeLayout dialogBg;
    private LayoutInflater inflater;
    /* access modifiers changed from: private */
    public AUEditText inputContent;
    private Button mCancelBtn;
    private Context mContext;
    private Button mEnsureBtn;
    private boolean mIsAutoCancel;
    private TextView mMsg;
    /* access modifiers changed from: private */
    public OnClickNegativeListener mNegativeListener;
    private String mNegativeString;
    /* access modifiers changed from: private */
    public OnClickPositiveListener mPositiveListener;
    private String mPositiveString;
    private TextView mTitle;
    private String negativeTextColor;
    private String positiveTextColor;
    private View rootView;
    private String sMsg;
    private String sTitle;

    public interface OnClickNegativeListener {
        void onClick();
    }

    public interface OnClickPositiveListener {
        void onClick(String str);
    }

    public AUInputDialog(Context context, String title, String msg, String positiveString, String negativeString) {
        this(context, title, msg, positiveString, negativeString, false);
    }

    public AUInputDialog(Context context, String title, String msg, String positiveString, String negativeString, boolean isAutoCancel) {
        super(context, R.style.noTitleTransBgDialogStyle);
        this.mIsAutoCancel = false;
        this.mContext = context;
        this.sTitle = title;
        this.sMsg = msg;
        this.mPositiveString = positiveString;
        this.mNegativeString = negativeString;
        this.mIsAutoCancel = isAutoCancel;
        this.inflater = LayoutInflater.from(context);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = this.inflater.inflate(R.layout.au_input_dialog, null);
        this.rootView = view;
        this.mEnsureBtn = (Button) view.findViewById(R.id.ensure);
        this.mCancelBtn = (Button) view.findViewById(R.id.cancel);
        this.mTitle = (TextView) view.findViewById(R.id.title);
        this.mMsg = (TextView) view.findViewById(R.id.message);
        this.inputContent = (AUEditText) view.findViewById(R.id.inputContent);
        this.bottomLayout = (LinearLayout) view.findViewById(R.id.bottom_ll);
        this.dialogBg = (RelativeLayout) view.findViewById(R.id.dialog_bg);
        if (!TextUtils.isEmpty(this.positiveTextColor)) {
            try {
                this.mEnsureBtn.setTextColor(Color.parseColor(this.positiveTextColor));
            } catch (Exception ex) {
                AuiLogger.error("AUInputDialog", "positiveTextColor设置失败：" + this.positiveTextColor + "，" + ex.getLocalizedMessage());
            }
        }
        if (!TextUtils.isEmpty(this.negativeTextColor)) {
            try {
                this.mCancelBtn.setTextColor(Color.parseColor(this.negativeTextColor));
            } catch (Exception ex2) {
                AuiLogger.error("AUInputDialog", "negativeTextColor设置失败：" + this.negativeTextColor + "，" + ex2.getLocalizedMessage());
            }
        }
        if (TextUtils.isEmpty(this.sMsg)) {
            this.mMsg.setVisibility(8);
        } else {
            this.mMsg.setText(this.sMsg);
            this.mMsg.setVisibility(0);
        }
        if (TextUtils.isEmpty(this.sTitle)) {
            this.mTitle.setVisibility(8);
        } else {
            this.mTitle.setVisibility(0);
            this.mTitle.setText(this.sTitle);
        }
        setCanceledOnTouchOutside(this.mIsAutoCancel);
        initBtn();
    }

    public AUEditText getInputContent() {
        return this.inputContent;
    }

    private void initBtn() {
        if (!TextUtils.isEmpty(this.mNegativeString) || !TextUtils.isEmpty(this.mPositiveString)) {
            this.bottomLayout.setVisibility(0);
            this.mCancelBtn.setText(this.mNegativeString);
            this.mCancelBtn.setOnClickListener(new y(this));
            this.mEnsureBtn.setText(this.mPositiveString);
            this.mEnsureBtn.setOnClickListener(new z(this));
            return;
        }
        this.bottomLayout.setVisibility(8);
    }

    public void show() {
        super.show();
        setContentView(this.rootView);
        setWindowMaxWidth(this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE10));
    }

    /* access modifiers changed from: protected */
    public void setWindowMaxWidth(int padding) {
        LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = -1;
        getWindow().setAttributes(layoutParams);
        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            decorView.setPadding(padding, 0, padding, 0);
        }
    }

    public Button getCancelBtn() {
        return this.mCancelBtn;
    }

    public Button getEnsureBtn() {
        return this.mEnsureBtn;
    }

    public TextView getTitle() {
        return this.mTitle;
    }

    public TextView getMsg() {
        return this.mMsg;
    }

    public LinearLayout getBottomLayout() {
        return this.bottomLayout;
    }

    public RelativeLayout getDialogBg() {
        return this.dialogBg;
    }

    public void setPositiveListener(OnClickPositiveListener listener) {
        this.mPositiveListener = listener;
    }

    public void setNegativeListener(OnClickNegativeListener listener) {
        this.mNegativeListener = listener;
    }

    public void setPositiveTextColor(String positiveTextColor2) {
        this.positiveTextColor = positiveTextColor2;
    }

    public void setNegativeTextColor(String negativeTextColor2) {
        this.negativeTextColor = negativeTextColor2;
    }
}
