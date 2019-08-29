package com.alipay.zoloz.toyger.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.zoloz.toyger.R;
import com.alipay.zoloz.toyger.util.DialogTypeIndex;

public class GenenalDialog extends Dialog {
    /* access modifiers changed from: private */
    public GenenalDialog _self = this;
    private String cacelButtonText;
    private String confirmButtonText;
    private Context context;
    /* access modifiers changed from: private */
    public OnClickListener negativeListener;
    /* access modifiers changed from: private */
    public OnClickListener positiveListener;
    private boolean showCloseButton;
    private boolean showProtocol;
    private String subTitle;
    private Object tag;
    private String title;

    public static class Builder {
        private String _cacelButtonText;
        private String _confirmButtonText;
        private Context _context;
        OnClickListener _negativeListener;
        OnClickListener _positiveListener;
        private boolean _showCloseButton;
        private boolean _showProtocol;
        private String _subTitle;
        private String _title;

        public Builder(Context context) {
            this._context = context;
        }

        public Builder setTitle(String str) {
            this._title = str;
            return this;
        }

        public Builder setMessage(String str) {
            this._subTitle = str;
            return this;
        }

        public Builder setMessage2(String str) {
            this._subTitle = str;
            return this;
        }

        public Builder setPositiveButton(String str, OnClickListener onClickListener) {
            this._confirmButtonText = str;
            this._positiveListener = onClickListener;
            return this;
        }

        public Builder showProtocol(boolean z) {
            this._showProtocol = z;
            return this;
        }

        public Builder showCloseButton(boolean z) {
            this._showCloseButton = z;
            return this;
        }

        public Builder setNegativeButton(String str, OnClickListener onClickListener) {
            this._cacelButtonText = str;
            this._negativeListener = onClickListener;
            return this;
        }

        public GenenalDialog show() {
            GenenalDialog genenalDialog = new GenenalDialog(this._context, this._title, this._subTitle, this._confirmButtonText, this._positiveListener, this._cacelButtonText, this._negativeListener, this._showCloseButton, this._showProtocol);
            try {
                genenalDialog.show();
            } catch (Exception e) {
            }
            return genenalDialog;
        }
    }

    public GenenalDialog(Context context2, String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2, boolean z, boolean z2) {
        super(context2, R.style.toyger_general_dialog_style);
        this.context = context2;
        this.title = str;
        this.subTitle = str2;
        this.confirmButtonText = str3;
        this.cacelButtonText = str4;
        this.showCloseButton = z;
        this.showProtocol = z2;
        this.negativeListener = onClickListener2;
        this.positiveListener = onClickListener;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initViews();
    }

    private void initViews() {
        setContentView(LayoutInflater.from(this.context).inflate(R.layout.toyger_general_dialog, null));
        ((TextView) findViewById(R.id.toyger_general_dialog_content_title)).setText(this.title);
        ((TextView) findViewById(R.id.toyger_general_dialog_content_sub_title)).setText(this.subTitle);
        Button button = (Button) findViewById(R.id.toyger_general_dialog_btn_confirm);
        button.setText(this.confirmButtonText);
        Button button2 = (Button) findViewById(R.id.toyger_general_dialog_btn_cancel);
        button2.setText(this.cacelButtonText);
        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_x);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.toyger_general_dialog_protocol);
        View findViewById = findViewById(R.id.toyger_general_dialog_btn_cancel_center);
        ((TextView) findViewById(R.id.protocol)).setOnClickListener(new c(this));
        if (TextUtils.isEmpty(this.confirmButtonText)) {
            button.setVisibility(8);
            findViewById.setVisibility(8);
        } else {
            button.setVisibility(0);
        }
        if (TextUtils.isEmpty(this.cacelButtonText)) {
            button2.setVisibility(8);
            findViewById.setVisibility(8);
        } else {
            button2.setVisibility(0);
        }
        if (!this.showProtocol) {
            linearLayout.setVisibility(4);
        }
        if (!this.showCloseButton) {
            imageButton.setVisibility(8);
        }
        Window window = getWindow();
        LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (((double) this.context.getResources().getDisplayMetrics().widthPixels) * 0.9d);
        window.setAttributes(attributes);
        button.setOnClickListener(new d(this));
        button2.setOnClickListener(new e(this));
        imageButton.setOnClickListener(new f(this));
    }

    public void setTag(DialogTypeIndex dialogTypeIndex) {
        this.tag = dialogTypeIndex;
    }
}
