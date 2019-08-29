package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ali.user.mobile.security.ui.R;
import java.util.ArrayList;
import java.util.List;

public class AUBoxInput extends RelativeLayout implements TextWatcher, OnClickListener {
    private List<TextView> a;
    /* access modifiers changed from: private */
    public EditText b;
    private Drawable c;
    private Drawable d;
    private int e;

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public AUBoxInput(Context context) {
        super(context);
        a(context, (AttributeSet) null);
    }

    public AUBoxInput(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public AUBoxInput(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.a);
        this.c = obtainStyledAttributes.getDrawable(R.styleable.d);
        if (this.c == null) {
            this.c = context.getResources().getDrawable(R.drawable.M);
        }
        this.d = obtainStyledAttributes.getDrawable(R.styleable.e);
        if (this.d == null) {
            this.d = context.getResources().getDrawable(R.drawable.N);
        }
        int i = obtainStyledAttributes.getInt(R.styleable.b, 4);
        this.e = obtainStyledAttributes.getDimensionPixelSize(R.styleable.c, -1);
        a(context, i);
        this.b.addTextChangedListener(this);
        this.b.setFilters(new InputFilter[]{new LengthFilter(i)});
        afterTextChanged(null);
        setOnClickListener(this);
        setWillNotDraw(false);
        obtainStyledAttributes.recycle();
    }

    private void a(Context context, int i) {
        LayoutInflater from = LayoutInflater.from(context);
        from.inflate(R.layout.K, this);
        this.b = (EditText) findViewById(R.id.A);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.B);
        linearLayout.removeAllViews();
        linearLayout.setDescendantFocusability(393216);
        this.a = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            TextView textView = (TextView) from.inflate(R.layout.J, linearLayout, false);
            if (-1 != this.e) {
                LayoutParams layoutParams = textView.getLayoutParams();
                layoutParams.height = this.e;
                layoutParams.width = this.e;
                textView.setLayoutParams(layoutParams);
            }
            this.a.add(textView);
            linearLayout.addView(textView);
        }
    }

    public void setBoxCount(int i) {
        a(getContext(), i);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.a != null) {
            int measuredWidth = this.a.get(0).getMeasuredWidth();
            int size = this.a.size();
            int i5 = ((i3 - i) - (measuredWidth * size)) / (size + 1);
            int i6 = ((i4 - i2) - measuredWidth) / 2;
            for (View next : this.a) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) next.getLayoutParams();
                layoutParams.leftMargin = i5;
                layoutParams.topMargin = i6;
                next.setLayoutParams(layoutParams);
            }
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    public void afterTextChanged(Editable editable) {
        String str = "";
        if (editable != null) {
            str = editable.toString();
        }
        int i = 0;
        int length = str.length();
        while (i < length) {
            TextView textView = this.a.get(i);
            textView.setBackgroundDrawable(this.d);
            textView.setText(String.valueOf(str.charAt(i)));
            textView.invalidate();
            i++;
        }
        int size = this.a.size();
        while (i < size) {
            TextView textView2 = this.a.get(i);
            textView2.setText("");
            textView2.setBackgroundDrawable(this.d);
            textView2.invalidate();
            i++;
        }
        int length2 = str.length();
        if (length2 < size) {
            this.a.get(length2).setBackgroundDrawable(this.c);
        }
    }

    public Editable getText() {
        if (this.b == null) {
            return null;
        }
        return this.b.getText();
    }

    public boolean isFull() {
        Editable text = getText();
        if (text == null || this.a == null || text.length() != this.a.size()) {
            return false;
        }
        return true;
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        if (this.b != null) {
            this.b.addTextChangedListener(textWatcher);
        }
    }

    public void setHighlightBg(Drawable drawable) {
        this.c = drawable;
    }

    public void setNormalBg(Drawable drawable) {
        this.d = drawable;
    }

    public void onClick(View view) {
        if (this.b != null) {
            this.b.requestFocus();
            this.b.postDelayed(new Runnable() {
                public void run() {
                    ((InputMethodManager) AUBoxInput.this.b.getContext().getSystemService("input_method")).showSoftInput(AUBoxInput.this.b, 1);
                }
            }, 200);
        }
    }

    public void clear() {
        this.b.setText("");
    }

    public EditText getRealInput() {
        return this.b;
    }
}
