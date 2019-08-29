package com.ali.user.mobile.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import com.ali.user.mobile.security.ui.R;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class AUNetworkPopDialog extends Dialog {
    private Context a;
    private LayoutInflater b;
    private View c;

    public AUNetworkPopDialog(Context context) {
        super(context, R.style.g);
        this.b = LayoutInflater.from(context);
        this.a = context;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c = this.b.inflate(R.layout.t, null);
        ((ImageView) this.c.findViewById(R.id.J)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AUNetworkPopDialog.this.cancel();
            }
        });
        ((Button) this.c.findViewById(R.id.K)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AUNetworkPopDialog.this.cancel();
            }
        });
        setCanceledOnTouchOutside(false);
    }

    public void show() {
        super.show();
        setContentView(this.c);
        LayoutParams attributes = getWindow().getAttributes();
        attributes.width = ((WindowManager) this.a.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth() - this.a.getResources().getDimensionPixelSize(R.dimen.l);
        getWindow().setAttributes(attributes);
    }
}
