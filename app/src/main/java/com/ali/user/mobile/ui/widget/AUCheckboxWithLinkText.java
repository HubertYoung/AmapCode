package com.ali.user.mobile.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import com.ali.user.mobile.security.ui.R;

public class AUCheckboxWithLinkText extends APLinearLayout {
    String checkBoxText;
    boolean checked;
    boolean enabled;
    APImageView imageView;
    String linkText;
    TextView linkTextView;
    CheckBox mCheckBox;

    public AUCheckboxWithLinkText(Context context) {
        super(context);
    }

    @SuppressLint({"Recycle"})
    public AUCheckboxWithLinkText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.s, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.o);
        this.checkBoxText = obtainStyledAttributes.getString(R.styleable.p);
        this.linkText = obtainStyledAttributes.getString(R.styleable.s);
        this.checked = obtainStyledAttributes.getBoolean(R.styleable.q, false);
        this.enabled = obtainStyledAttributes.getBoolean(R.styleable.r, true);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mCheckBox = (CheckBox) findViewById(R.id.S);
        this.linkTextView = (TextView) findViewById(R.id.ar);
        this.imageView = (APImageView) findViewById(R.id.aq);
        setCheckBoxText(this.checkBoxText);
        this.mCheckBox.setChecked(this.checked);
        this.mCheckBox.setEnabled(this.enabled);
    }

    public void setTextViewUri(String str) {
        this.linkTextView.setText(Html.fromHtml(this.linkText));
        this.linkTextView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        setMovementMethod();
    }

    @Deprecated
    public void setTextViewText(String str, String str2) {
        this.linkTextView.setText(Html.fromHtml(this.linkText));
        setMovementMethod();
    }

    public void setMovementMethod() {
        this.linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setCheckBoxText(String str) {
        this.mCheckBox.setText(str);
    }

    public void setImageView(Drawable drawable) {
        this.imageView.setVisibility(0);
        this.imageView.setBackground(drawable);
    }

    public CheckBox getCheckBox() {
        return this.mCheckBox;
    }

    public void setTextViewText(String str) {
        this.linkTextView.setText(Html.fromHtml(str));
    }

    public void setNormalTextViewText(String str) {
        this.linkTextView.setTextColor(-16777216);
        this.linkTextView.setText(str);
    }

    public TextView getLinkTextView() {
        return this.linkTextView;
    }

    public String getLinkText() {
        return this.linkText;
    }

    public void setLinkText(String str) {
        this.linkText = str;
    }
}
