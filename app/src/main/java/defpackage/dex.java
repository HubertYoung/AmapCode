package defpackage;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.widget.AmapButton;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.errorreport.ErrorReportInputDialog$3;

/* renamed from: dex reason: default package */
/* compiled from: ErrorReportInputDialog */
public final class dex extends sr {
    public a c;
    public PageBundle d;
    /* access modifiers changed from: private */
    public EditText e;
    private AmapButton f;
    private String g;
    private boolean h = true;
    /* access modifiers changed from: private */
    public boolean i = false;
    private Context j;

    /* renamed from: dex$a */
    /* compiled from: ErrorReportInputDialog */
    public interface a {
        void a(String str);
    }

    public dex(Context context, int i2, PageBundle pageBundle) {
        super(context, i2);
        this.j = context;
        this.d = pageBundle;
        this.a.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0 && dex.a(dex.this)) {
                    dex.this.a();
                }
                return true;
            }
        });
        View findViewById = this.a.findViewById(R.id.edit_input_container);
        if (findViewById != null) {
            findViewById.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }
        this.e = (EditText) this.a.findViewById(R.id.edit_input);
        this.e.setOnEditorActionListener(new ErrorReportInputDialog$3(this));
        this.e.addTextChangedListener(new TextWatcher() {
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void afterTextChanged(Editable editable) {
                int i = dex.this.i ? 11 : 300;
                if (editable != null && editable.length() > i) {
                    if (dex.this.i) {
                        ToastHelper.showLongToast("请输入11位有效手机号码");
                    } else {
                        ToastHelper.showLongToast("字数已达到上限");
                    }
                    CharSequence subSequence = editable.subSequence(0, i);
                    editable.clear();
                    editable.append(subSequence);
                    dex.this.e.setText(editable);
                    dex.this.e.setSelection(editable.length());
                }
            }
        });
        this.f = (AmapButton) this.a.findViewById(R.id.car_error_des_submit);
        this.f.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (dex.a(dex.this)) {
                    dex.this.a();
                }
            }
        });
        this.f.post(new Runnable() {
            public final void run() {
                dex.d(dex.this);
            }
        });
        if (this.d != null) {
            if (this.d.containsKey("ErrorReportDescriptionInputFragment.inputstring")) {
                this.e.setText(this.d.getString("ErrorReportDescriptionInputFragment.inputstring"));
            }
            if (this.d.containsKey("ErrorReportDescriptionInputFragment.hintstring")) {
                this.g = this.d.getString("ErrorReportDescriptionInputFragment.hintstring");
                this.e.setHint(this.g);
            }
            if (this.d.containsKey("bundle_contact")) {
                this.i = this.d.getBoolean("bundle_contact");
                if (this.i) {
                    this.e.setInputType(2);
                } else {
                    this.e.setMovementMethod(dew.a());
                }
            }
            if (this.d.containsKey("bundle_limit_input_count")) {
                this.h = this.d.getBoolean("bundle_limit_input_count", true);
            }
        }
    }

    public final void a() {
        String trim = this.e.getText().toString().trim();
        PageBundle pageBundle = new PageBundle();
        if (TextUtils.isEmpty(trim)) {
            trim = "";
        }
        pageBundle.putString("input_string", trim);
        ((InputMethodManager) this.j.getSystemService("input_method")).hideSoftInputFromWindow(this.e.getWindowToken(), 0);
        this.e.setOnEditorActionListener(null);
        this.c.a(trim);
    }

    public static /* synthetic */ boolean a(dex dex) {
        String trim = dex.e.getText().toString().trim();
        if (!TextUtils.isEmpty(trim)) {
            if (dex.i) {
                if (trim.length() != 11) {
                    ToastHelper.showToast("请输入11位有效手机号码 ");
                    return false;
                } else if ('1' != trim.charAt(0)) {
                    ToastHelper.showToast("请输入11位有效手机号码");
                    return false;
                } else {
                    for (int i2 = 0; i2 < trim.length(); i2++) {
                        if (!Character.isDigit(trim.charAt(i2))) {
                            ToastHelper.showToast("请输入11位有效手机号码");
                            return false;
                        }
                    }
                    return true;
                }
            } else if (trim.length() < 5 && dex.h) {
                ToastHelper.showToast("请输入至少5字的描述");
                return false;
            }
        }
        return true;
    }

    static /* synthetic */ boolean d(dex dex) {
        InputMethodManager inputMethodManager = (InputMethodManager) dex.j.getSystemService("input_method");
        if (inputMethodManager == null || dex.e == null) {
            return false;
        }
        dex.e.setFocusableInTouchMode(true);
        dex.e.requestFocus();
        dex.e.setSelection(dex.e.getText().toString().trim().length());
        dex.e.setImeOptions(6);
        return inputMethodManager.showSoftInput(dex.e, 0);
    }
}
