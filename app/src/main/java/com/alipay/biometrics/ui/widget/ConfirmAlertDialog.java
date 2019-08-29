package com.alipay.biometrics.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.biometric.a.a.c;
import com.alipay.android.phone.mobilecommon.biometric.a.a.d;
import com.alipay.android.phone.mobilecommon.biometric.a.a.e;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.StringUtil;

public class ConfirmAlertDialog extends Dialog {
    /* access modifiers changed from: private */
    public ConfirmAlertDialog _self = this;
    private boolean isShowIcons;
    /* access modifiers changed from: private */
    public View mBtnNegative;
    /* access modifiers changed from: private */
    public View mBtnPositive;
    /* access modifiers changed from: private */
    public View mButtonsContainer;
    private Context mContext;
    /* access modifiers changed from: private */
    public View mIconsContainer;
    /* access modifiers changed from: private */
    public String mMessage;
    /* access modifiers changed from: private */
    public String mMessage2;
    /* access modifiers changed from: private */
    public OnClickListener mNegativeListener;
    /* access modifiers changed from: private */
    public String mNegativeText;
    /* access modifiers changed from: private */
    public OnClickListener mPositiveListener;
    /* access modifiers changed from: private */
    public String mPositiveText;
    /* access modifiers changed from: private */
    public String mTitle;
    private Object tag;

    public static class Builder {
        boolean _isShow = false;
        Context b_context;
        String b_message;
        String b_message2;
        String b_negative;
        OnClickListener b_negativeListener;
        String b_positive;
        OnClickListener b_positiveListener;
        String b_title;

        public Builder(Context context) {
            this.b_context = context;
        }

        public Builder setTitle(String str) {
            this.b_title = str;
            return this;
        }

        public Builder setMessage(String str) {
            this.b_message = str;
            return this;
        }

        public Builder setMessage2(String str) {
            this.b_message2 = str;
            return this;
        }

        public Builder setPositiveButton(String str, OnClickListener onClickListener) {
            this.b_positive = str;
            this.b_positiveListener = onClickListener;
            return this;
        }

        public Builder showIcons(boolean z) {
            this._isShow = z;
            return this;
        }

        public Builder setNegativeButton(String str, OnClickListener onClickListener) {
            this.b_negative = str;
            this.b_negativeListener = onClickListener;
            return this;
        }

        public ConfirmAlertDialog show() {
            ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(this.b_context, this.b_message, this.b_message2, this.b_positive, this.b_positiveListener, this.b_negative, this.b_negativeListener, this._isShow, this.b_title);
            try {
                confirmAlertDialog.show();
            } catch (Exception e) {
            }
            return confirmAlertDialog;
        }
    }

    public interface ClickListenerInterface {
        void doCancel();

        void doConfirm();
    }

    private class SetDialogContext {
        private boolean negativeEnable;
        private boolean positiveEnable;
        private TextView tvMessage;
        private View view;

        public SetDialogContext(View view2) {
            this.view = view2;
        }

        public boolean isPositiveEnable() {
            return this.positiveEnable;
        }

        public boolean isNegativeEnable() {
            return this.negativeEnable;
        }

        public TextView getTvMessage() {
            return this.tvMessage;
        }

        public SetDialogContext invoke() {
            this.positiveEnable = true;
            this.negativeEnable = true;
            this.tvMessage = (TextView) this.view.findViewById(c.dialog_msg);
            View findViewById = this.view.findViewById(c.dialog_split);
            ConfirmAlertDialog.this.mBtnPositive = ConfirmAlertDialog.this.findViewById(c.dialog_ok);
            ConfirmAlertDialog.this.mBtnNegative = ConfirmAlertDialog.this.findViewById(c.dialog_cancel);
            ConfirmAlertDialog.this.mIconsContainer = ConfirmAlertDialog.this.findViewById(c.dialog_msg_icons);
            ConfirmAlertDialog.this.mButtonsContainer = ConfirmAlertDialog.this.findViewById(c.dialog_button_container);
            initSubView((TextView) this.view.findViewById(c.dialog_ok_text), (TextView) this.view.findViewById(c.dialog_cancel_text), (TextView) this.view.findViewById(c.dialog_msg_2), (TextView) this.view.findViewById(c.dialog_title), findViewById, true);
            ConfirmAlertDialog.this.mBtnPositive.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ConfirmAlertDialog.this._self.dismiss();
                    if (ConfirmAlertDialog.this.mPositiveListener != null) {
                        ConfirmAlertDialog.this.mPositiveListener.onClick(ConfirmAlertDialog.this._self, -1);
                    }
                }
            });
            ConfirmAlertDialog.this.mBtnNegative.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ConfirmAlertDialog.this._self.dismiss();
                    if (ConfirmAlertDialog.this.mNegativeListener != null) {
                        ConfirmAlertDialog.this.mNegativeListener.onClick(ConfirmAlertDialog.this._self, -2);
                    }
                }
            });
            return this;
        }

        private void initSubView(TextView textView, TextView textView2, TextView textView3, TextView textView4, View view2, boolean z) {
            if (ConfirmAlertDialog.this.mPositiveText == null || "".equals(ConfirmAlertDialog.this.mPositiveText)) {
                textView.setVisibility(8);
                ConfirmAlertDialog.this.mBtnPositive.setVisibility(8);
                view2.setVisibility(8);
                this.positiveEnable = false;
            } else {
                textView.setVisibility(0);
                textView.setText(ConfirmAlertDialog.this.mPositiveText);
                z = false;
            }
            if (ConfirmAlertDialog.this.mNegativeText == null || "".equals(ConfirmAlertDialog.this.mNegativeText)) {
                ConfirmAlertDialog.this.mBtnNegative.setVisibility(8);
                textView2.setVisibility(8);
                view2.setVisibility(8);
                this.negativeEnable = false;
            } else {
                textView2.setVisibility(0);
                textView2.setText(ConfirmAlertDialog.this.mNegativeText);
                z = false;
            }
            if (z) {
                ConfirmAlertDialog.this.mButtonsContainer.setVisibility(8);
            }
            this.tvMessage.setText(ConfirmAlertDialog.this.mMessage);
            if (StringUtil.isNullorEmpty(ConfirmAlertDialog.this.mMessage2)) {
                textView3.setVisibility(8);
            } else {
                textView3.setVisibility(0);
                textView3.setText(ConfirmAlertDialog.this.mMessage2);
            }
            if (StringUtil.isNullorEmpty(ConfirmAlertDialog.this.mTitle)) {
                textView4.setVisibility(8);
                return;
            }
            textView4.setVisibility(0);
            textView4.setText(ConfirmAlertDialog.this.mTitle);
        }
    }

    public Object getTag() {
        return this.tag;
    }

    public void setTag(Object obj) {
        this.tag = obj;
    }

    public ConfirmAlertDialog(Context context, String str, String str2, String str3) {
        super(context, e.ConfirmAlertDialog);
        this.mContext = context;
        this.mMessage = str;
        this.mPositiveText = str2;
        this.mNegativeText = str3;
    }

    public ConfirmAlertDialog(Context context, String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2, boolean z) {
        super(context, e.ConfirmAlertDialog);
        this.mContext = context;
        this.mMessage = str;
        this.mMessage2 = str2;
        this.mPositiveText = str3;
        this.mNegativeText = str4;
        this.mPositiveListener = onClickListener;
        this.mNegativeListener = onClickListener2;
        this.isShowIcons = z;
    }

    public ConfirmAlertDialog(Context context, String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2, boolean z, String str5) {
        super(context, e.ConfirmAlertDialog);
        this.mContext = context;
        this.mMessage = str;
        this.mMessage2 = str2;
        this.mPositiveText = str3;
        this.mNegativeText = str4;
        this.mPositiveListener = onClickListener;
        this.mNegativeListener = onClickListener2;
        this.isShowIcons = z;
        this.mTitle = str5;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
    }

    public void init() {
        View inflate = LayoutInflater.from(this.mContext).inflate(d.bio_alert_dialog, null);
        try {
            setContentView(inflate);
            SetDialogContext invoke = new SetDialogContext(inflate).invoke();
            boolean isNegativeEnable = invoke.isNegativeEnable();
            boolean isPositiveEnable = invoke.isPositiveEnable();
            TextView tvMessage = invoke.getTvMessage();
            if (!isNegativeEnable || !isPositiveEnable) {
                this.mButtonsContainer.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        ConfirmAlertDialog.this._self.dismiss();
                        if (ConfirmAlertDialog.this.mBtnPositive.getVisibility() == 0) {
                            ConfirmAlertDialog.this.mBtnPositive.callOnClick();
                        } else if (ConfirmAlertDialog.this.mBtnNegative.getVisibility() == 0) {
                            ConfirmAlertDialog.this.mBtnNegative.callOnClick();
                        }
                    }
                });
            }
            Window window = getWindow();
            LayoutParams attributes = window.getAttributes();
            BioLog.i("confirmDialog", "message:" + tvMessage.getHeight());
            this.mIconsContainer.setVisibility(0);
            if (!this.isShowIcons) {
                this.mIconsContainer.setVisibility(8);
            }
            window.setAttributes(attributes);
        } catch (RuntimeException e) {
            BioLog.i(e.toString());
        }
    }
}
