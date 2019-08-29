package com.autonavi.minimap.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.minimap.R;

public class CustomDialog extends CompatDialog {
    private Button btnCancel = ((Button) findViewById(R.id.btn_right));
    /* access modifiers changed from: private */
    public OnClickListener btnCancelListener;
    private String btnCancelText;
    private Button btnOk = ((Button) findViewById(R.id.btn_left));
    /* access modifiers changed from: private */
    public OnClickListener btnOkListener;
    private String btnOkText;
    private Context context;
    private String msg;
    private String title;
    private TextView tvMsg = ((TextView) findViewById(R.id.msg));
    private TextView tvTitle = ((TextView) findViewById(R.id.title));

    public CustomDialog setMsg(CharSequence charSequence) {
        this.msg = charSequence.toString();
        return this;
    }

    public CustomDialog setMsg(int i) {
        this.msg = this.context.getString(i);
        return this;
    }

    public CustomDialog setDlgTitle(CharSequence charSequence) {
        this.title = charSequence.toString();
        return this;
    }

    public CustomDialog setMsgGravity(int i) {
        this.tvMsg.setGravity(i);
        return this;
    }

    public CustomDialog setDlgTitle(int i) {
        this.title = this.context.getString(i);
        return this;
    }

    public CustomDialog setNegativeButton(int i, OnClickListener onClickListener) {
        this.btnCancelText = this.context.getString(i);
        this.btnCancelListener = onClickListener;
        this.btnCancel.setVisibility(0);
        return this;
    }

    public CustomDialog setNegativeButton(CharSequence charSequence, OnClickListener onClickListener) {
        this.btnCancelText = charSequence.toString();
        this.btnCancelListener = onClickListener;
        this.btnCancel.setVisibility(0);
        return this;
    }

    public CustomDialog setPositiveButton(int i, OnClickListener onClickListener) {
        this.btnOkText = this.context.getString(i);
        this.btnOkListener = onClickListener;
        this.btnOk.setVisibility(0);
        return this;
    }

    public CustomDialog setPositiveButton(CharSequence charSequence, OnClickListener onClickListener) {
        this.btnOkText = charSequence.toString();
        this.btnOkListener = onClickListener;
        this.btnOk.setVisibility(0);
        return this;
    }

    public void show() {
        Window window = getWindow();
        window.setGravity(48);
        window.setLayout(-1, -2);
        window.setWindowAnimations(R.style.new_dlg_animation);
        this.tvTitle.setText(this.title);
        this.tvMsg.setText(this.msg);
        this.btnOk.setText(this.btnOkText);
        this.btnOk.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CustomDialog.this.dismiss();
                if (CustomDialog.this.btnOkListener != null) {
                    CustomDialog.this.btnOkListener.onClick(view);
                }
            }
        });
        this.btnCancel.setText(this.btnCancelText);
        this.btnCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CustomDialog.this.dismiss();
                if (CustomDialog.this.btnCancelListener != null) {
                    CustomDialog.this.btnCancelListener.onClick(view);
                }
            }
        });
        super.show();
    }

    public CustomDialog(Activity activity) {
        super(activity, R.style.custom_dlg);
        setContentView(R.layout.v3_dlg);
        this.context = activity;
        this.btnOk.setVisibility(8);
        this.btnCancel.setVisibility(8);
    }
}
