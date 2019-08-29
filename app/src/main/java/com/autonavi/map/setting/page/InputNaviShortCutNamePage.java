package com.autonavi.map.setting.page;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;

@PageAction("amap.basemap.action.input_navi_shortcut_name_page")
public class InputNaviShortCutNamePage extends AbstractBasePage<ccl> implements OnClickListener {
    private EditText a;
    private View b;
    private ImageButton c;
    private ImageButton d;
    private TextView e;
    /* access modifiers changed from: private */
    public View f;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.shortcut_navi_input_name_layout);
        this.a = (EditText) findViewById(R.id.edit);
        this.b = findViewById(R.id.btn_ok);
        this.c = (ImageButton) findViewById(R.id.title_btn_left);
        this.d = (ImageButton) findViewById(R.id.title_btn_right);
        this.e = (TextView) findViewById(R.id.title_text_name);
        this.f = findViewById(R.id.btn_clear);
        this.e.setText(R.string.shortcut_navi);
        this.b.setOnClickListener(this);
        this.c.setOnClickListener(this);
        this.d.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.d.setVisibility(4);
        this.a.addTextChangedListener(new TextWatcher() {
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    InputNaviShortCutNamePage.this.f.setVisibility(8);
                } else {
                    InputNaviShortCutNamePage.this.f.setVisibility(0);
                }
            }
        });
        this.a.setText("");
        this.a.requestFocus();
    }

    public void onClick(View view) {
        if (view == this.b) {
            a();
            String obj = this.a.getText().toString();
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("name", obj);
            setResult(ResultType.OK, pageBundle);
            finish();
        } else if (view == this.c) {
            a();
            finish();
        } else if (view == this.d) {
            a();
            finish();
        } else {
            if (view == this.f) {
                this.a.setText("");
            }
        }
    }

    private void a() {
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.a.getApplicationWindowToken(), 0);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new ccl(this);
    }
}
