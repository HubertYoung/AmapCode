package com.alipay.mobile.nebulacore.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class H5PromptDialog extends Dialog {
    private Context a;
    private LayoutInflater b;
    private View c;
    private Button d;
    private Button e;
    /* access modifiers changed from: private */
    public EditText f;
    private LinearLayout g;
    private TextView h;
    private TextView i;
    private String j;
    private String k;
    /* access modifiers changed from: private */
    public OnClickPositiveListener l;
    /* access modifiers changed from: private */
    public OnClickNegativeListener m;
    private RelativeLayout n;
    private String o;
    private String p;
    private boolean q;

    public interface OnClickNegativeListener {
        void onClick();
    }

    public interface OnClickPositiveListener {
        void onClick(String str);
    }

    public H5PromptDialog(Context context, String title, String msg, String positiveString, String negativeString) {
        this(context, title, msg, positiveString, negativeString, false);
    }

    public H5PromptDialog(Context context, String title, String msg, String positiveString, String negativeString, boolean isAutoCancel) {
        super(context, R.style.h5_prompt_noTitleTransBgDialogStyle);
        this.q = false;
        this.a = context;
        this.j = title;
        this.k = msg;
        this.o = positiveString;
        this.p = negativeString;
        this.q = isAutoCancel;
        this.b = LayoutInflater.from(context);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = this.b.inflate(R.layout.h5_prompt_input_dialog, null);
        this.c = view;
        this.e = (Button) view.findViewById(R.id.ensure);
        this.d = (Button) view.findViewById(R.id.cancel);
        this.h = (TextView) view.findViewById(R.id.title);
        this.i = (TextView) view.findViewById(R.id.message);
        this.f = (EditText) view.findViewById(R.id.inputContent);
        this.g = (LinearLayout) view.findViewById(R.id.bottom_ll);
        this.n = (RelativeLayout) view.findViewById(R.id.dialog_bg);
        if (TextUtils.isEmpty(this.k)) {
            this.i.setVisibility(8);
        } else {
            this.i.setText(this.k);
            this.i.setVisibility(0);
        }
        if (TextUtils.isEmpty(this.j)) {
            this.h.setVisibility(8);
        } else {
            this.h.setVisibility(0);
            this.h.setText(this.j);
        }
        setCanceledOnTouchOutside(this.q);
        a();
    }

    public EditText getInputContent() {
        return this.f;
    }

    private void a() {
        if (!TextUtils.isEmpty(this.p) || !TextUtils.isEmpty(this.o)) {
            this.g.setVisibility(0);
            this.d.setText(this.p);
            this.d.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    H5PromptDialog.this.cancel();
                    if (H5PromptDialog.this.m != null) {
                        H5PromptDialog.this.m.onClick();
                    }
                }
            });
            this.e.setText(this.o);
            this.e.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    H5PromptDialog.this.dismiss();
                    if (H5PromptDialog.this.l == null) {
                        return;
                    }
                    if (H5PromptDialog.this.f.getText() != null) {
                        H5PromptDialog.this.l.onClick(H5PromptDialog.this.f.getText().toString());
                    } else {
                        H5PromptDialog.this.l.onClick(null);
                    }
                }
            });
            return;
        }
        this.g.setVisibility(8);
    }

    public void show() {
        super.show();
        setContentView(this.c);
        LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = a(this.a) - (this.a.getResources().getDimensionPixelSize(R.dimen.h5_prompt_height) * 2);
        getWindow().setAttributes(layoutParams);
    }

    private static int a(Context context) {
        return ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth();
    }

    public Button getCancelBtn() {
        return this.d;
    }

    public Button getEnsureBtn() {
        return this.e;
    }

    public TextView getTitle() {
        return this.h;
    }

    public TextView getMsg() {
        return this.i;
    }

    public LinearLayout getBottomLayout() {
        return this.g;
    }

    public RelativeLayout getDialogBg() {
        return this.n;
    }

    public void setPositiveListener(OnClickPositiveListener listener) {
        this.l = listener;
    }

    public void setNegativeListener(OnClickNegativeListener listener) {
        this.m = listener;
    }
}
