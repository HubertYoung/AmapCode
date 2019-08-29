package defpackage;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.minimap.R;
import java.io.File;
import org.json.JSONObject;

/* renamed from: eoa reason: default package */
/* compiled from: DetailDialogWindow */
public final class eoa extends eny implements OnClickListener {
    LinearLayout a = null;
    WindowManager b = null;
    boolean c = false;
    LayoutParams d = null;
    /* access modifiers changed from: private */
    public Button e = null;
    private Button f = null;
    /* access modifiers changed from: private */
    public EditText g = null;
    private JSONObject h = null;
    private JSONObject i = null;
    private Context j = null;
    private Activity k = null;
    /* access modifiers changed from: private */
    public String l = "";

    public eoa(Context context) {
        this.j = context;
        e();
    }

    /* access modifiers changed from: protected */
    public final void a() {
        this.a = (LinearLayout) ((LayoutInflater) this.j.getSystemService("layout_inflater")).inflate(R.layout.profile_pop_dialog, null);
        this.e = (Button) this.a.findViewById(R.id.evaluate_button_user_insight_send);
        this.f = (Button) this.a.findViewById(R.id.evaluate_button_user_insight_cancel);
        this.g = (EditText) this.a.findViewById(R.id.evaluate_editText_user_insight_detail);
    }

    /* access modifiers changed from: protected */
    public final void b() {
        this.b = (WindowManager) this.j.getSystemService(TemplateTinyApp.WINDOW_KEY);
    }

    /* access modifiers changed from: protected */
    public final void c() {
        LayoutParams layoutParams = new LayoutParams(-1, -1, 1003, 512, -3);
        this.d = layoutParams;
    }

    /* access modifiers changed from: protected */
    public final void d() {
        this.e.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.g.addTextChangedListener(new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (TextUtils.isEmpty(eoa.this.g.getText())) {
                    eoa.this.e.setEnabled(false);
                    eoa.this.e.setTextColor(-4144960);
                    return;
                }
                eoa.this.e.setEnabled(true);
                eoa.this.e.setTextColor(-12417025);
                eoa.this.l = eoa.this.g.getText().toString();
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
            eof.a(this.j);
            eof.b(this.l);
            eof.a(this.j);
            eof.a(true);
            eof.a(this.j);
            File file = new File(eof.d());
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            eoc.a(file, sb.toString());
            Toast.makeText(this.j, "已保存请退出", 0);
            this.a.clearFocus();
            this.g.clearFocus();
            f();
            eoj.a(this.j).a(eod.a());
            return;
        }
        if (id == R.id.evaluate_button_user_insight_cancel) {
            this.a.clearFocus();
            this.g.clearFocus();
            f();
            eoj.a(this.j).a(eod.a());
        }
    }
}
