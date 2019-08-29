package defpackage;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cxe reason: default package */
/* compiled from: DetailDialogWindow */
public final class cxe extends cxf implements OnClickListener {
    LinearLayout a = null;
    WindowManager b = null;
    boolean c = false;
    LayoutParams d = null;
    JSONObject e = null;
    JSONObject f = null;
    /* access modifiers changed from: private */
    public Button g = null;
    private Button h = null;
    /* access modifiers changed from: private */
    public EditText i = null;

    public cxe() {
        e();
    }

    /* access modifiers changed from: protected */
    public final void a() {
        this.a = (LinearLayout) ((LayoutInflater) cxk.a().a.getSystemService("layout_inflater")).inflate(R.layout.evaluate_pop_dialog, null);
        this.g = (Button) this.a.findViewById(R.id.evaluate_button_user_insight_send);
        this.h = (Button) this.a.findViewById(R.id.evaluate_button_user_insight_cancel);
        this.i = (EditText) this.a.findViewById(R.id.evaluate_editText_user_insight_detail);
    }

    /* access modifiers changed from: protected */
    public final void b() {
        this.b = (WindowManager) cxk.a().a.getSystemService(TemplateTinyApp.WINDOW_KEY);
    }

    /* access modifiers changed from: protected */
    public final void c() {
        LayoutParams layoutParams = new LayoutParams(-1, -1, 1003, 512, -3);
        this.d = layoutParams;
    }

    /* access modifiers changed from: protected */
    public final void d() {
        this.g.setOnClickListener(this);
        this.h.setOnClickListener(this);
        this.i.addTextChangedListener(new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (TextUtils.isEmpty(cxe.this.i.getText())) {
                    cxe.this.g.setEnabled(false);
                    cxe.this.g.setTextColor(-4144960);
                    return;
                }
                cxe.this.g.setEnabled(true);
                cxe.this.g.setTextColor(-12417025);
            }
        });
    }

    private void f() {
        try {
            if (this.c) {
                this.b.removeViewImmediate(this.a);
                this.c = false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.evaluate_button_user_insight_send) {
            f();
            if (this.f == null) {
                this.f = new JSONObject();
            }
            try {
                this.f.put("detail", this.i.getText().toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            } catch (NullPointerException e3) {
                e3.printStackTrace();
            }
            cxl.b(2, this.e, this.f);
            ToastHelper.showToast("发送成功，感谢您的反馈！", 1);
            return;
        }
        if (id == R.id.evaluate_button_user_insight_cancel) {
            f();
            cxl.b(3, this.e, this.f);
        }
    }
}
