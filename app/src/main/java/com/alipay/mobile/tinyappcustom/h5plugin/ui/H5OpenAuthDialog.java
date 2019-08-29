package com.alipay.mobile.tinyappcustom.h5plugin.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.a.a.a.C0098a;
import com.alipay.mobile.a.a.a.b;
import com.alipay.mobile.a.a.a.d;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.nebula.providermanager.H5BaseProviderInfo;
import com.alipay.mobile.nebula.util.H5Log;

public class H5OpenAuthDialog extends Dialog {
    private TextView a;
    private TextView b;
    private TextView c;
    private LinearLayout d;
    private TextView e;
    private TextView f;
    private OnClickListener g = new OnClickListener() {
        public void onClick(View v) {
            H5OpenAuthDialog.this.cancel();
        }
    };
    private OnClickListener h;
    private OnClickListener i;

    public H5OpenAuthDialog(Context context) {
        super(context, d.h5noTitleTransBgDialogStyle);
        View rootView = LayoutInflater.from(context).inflate(LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle(H5BaseProviderInfo.nebulabiz).getLayout(b.h5_auth_dialog), null);
        this.a = (TextView) rootView.findViewById(C0098a.h5_audialog_footer_confirm);
        this.b = (TextView) rootView.findViewById(C0098a.h5_audialog_footer_reject);
        this.c = (TextView) rootView.findViewById(C0098a.h5_audialog_content_auth_title);
        this.d = (LinearLayout) rootView.findViewById(C0098a.h5_audialog_content_auth_realcontent);
        this.e = (TextView) rootView.findViewById(C0098a.h5_audialog_content_auth_isv_tip);
        this.f = (TextView) rootView.findViewById(C0098a.h5_audialog_content_auth_protocol);
        setContentView(rootView);
        setCanceledOnTouchOutside(false);
        this.b.setOnClickListener(this.g);
    }

    public void setOnCloseClickListener(OnClickListener onClickListener) {
        this.h = onClickListener;
        this.b.setOnClickListener(this.h);
    }

    public void setOnConfirmClickListener(OnClickListener onClickListener) {
        this.i = onClickListener;
        this.a.setOnClickListener(this.i);
    }

    public TextView getAuthContentTitle() {
        return this.c;
    }

    public LinearLayout getAuthContentContainer() {
        return this.d;
    }

    public TextView getAuthContentIsvTip() {
        return this.e;
    }

    public TextView getAuthContentProtocol() {
        return this.f;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (4 != event.getKeyCode()) {
            return super.onKeyDown(keyCode, event);
        }
        H5Log.d("H5OpenAuthDialog", "do nothing");
        return true;
    }
}
