package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.inputfomatter.APFormatter;
import com.ali.user.mobile.ui.widget.keyboard.APSafeEditText;
import com.ali.user.mobile.ui.widget.keyboard.APSafeTextWatcher;
import java.lang.reflect.Field;

public class AUInputBox extends RelativeLayout implements OnFocusChangeListener {
    public static final int DARK = 17;
    public static final int NONE = 18;
    public static final int NORMAL = 16;
    private ImageView a;
    /* access modifiers changed from: private */
    public EditText b;
    /* access modifiers changed from: private */
    public ImageButton c;
    private TextView d;
    private ImageButton e;
    private RelativeLayout f;
    private boolean g;
    /* access modifiers changed from: private */
    public OnClickListener h;
    private Drawable i;
    private String j;
    private float k;
    /* access modifiers changed from: private */
    public float l;
    private int m;
    private int n;
    private int o;
    private String p;
    private int q;
    /* access modifiers changed from: private */
    public float r;
    private boolean s;
    private float t;
    private Drawable u;
    /* access modifiers changed from: private */
    public APFormatter v;
    private OnFocusChangeListener w;

    public void setItemPositionStyle(int i2) {
    }

    public void setVisualStyle(int i2) {
    }

    public AUInputBox(Context context) {
        this(context, null);
    }

    public AUInputBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        boolean z = true;
        this.g = true;
        this.i = null;
        this.j = null;
        this.m = -16777216;
        this.n = 1;
        this.o = -1;
        this.p = null;
        int i2 = 0;
        this.s = false;
        this.v = null;
        LayoutInflater.from(context).inflate(R.layout.r, this, true);
        this.b = (EditText) findViewById(R.id.O);
        this.a = (ImageView) findViewById(R.id.Q);
        this.d = (TextView) findViewById(R.id.R);
        this.c = (ImageButton) findViewById(R.id.I);
        this.e = (ImageButton) findViewById(R.id.bG);
        this.f = (RelativeLayout) findViewById(R.id.P);
        this.t = context.getResources().getDimension(R.dimen.g);
        this.k = this.t;
        this.l = this.t;
        this.q = getResources().getColor(R.color.t);
        if (this.b != null) {
            ViewParent parent = this.b.getParent();
            if (View.class.isInstance(parent)) {
                final View view = (View) parent;
                view.post(new Runnable() {
                    public void run() {
                        Rect rect = new Rect();
                        AUInputBox.this.b.setEnabled(true);
                        AUInputBox.this.b.getHitRect(rect);
                        rect.top -= 100;
                        rect.bottom += 100;
                        rect.left -= 200;
                        rect.right += 100;
                        view.setTouchDelegate(new TouchDelegate(rect, AUInputBox.this.b));
                    }
                });
            }
        }
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.z);
            this.i = obtainStyledAttributes.getDrawable(R.styleable.G);
            this.j = obtainStyledAttributes.getString(R.styleable.F);
            this.k = obtainStyledAttributes.getDimension(R.styleable.H, this.t);
            this.l = obtainStyledAttributes.getDimension(R.styleable.J, this.t);
            this.m = obtainStyledAttributes.getColor(R.styleable.I, -16777216);
            this.n = obtainStyledAttributes.getInt(R.styleable.K, 1);
            this.o = obtainStyledAttributes.getInt(R.styleable.O, -1);
            this.p = obtainStyledAttributes.getString(R.styleable.C);
            this.q = obtainStyledAttributes.getColor(R.styleable.E, getResources().getColor(R.color.t));
            this.r = obtainStyledAttributes.getDimension(R.styleable.D, this.l);
            this.s = obtainStyledAttributes.getBoolean(R.styleable.N, false);
            this.u = obtainStyledAttributes.getDrawable(R.styleable.Q);
            setInputNameImage(this.i);
            setInputName(this.j);
            setInputNameTextSize(this.k);
            setInputTextSize(this.l);
            setInputTextColor(this.m);
            setInputType(this.n);
            setLength(this.o);
            setHint(this.p);
            setHintTextColor(this.q);
            setApprerance(this.s);
            z = this.u == null ? false : z;
            if (z) {
                this.e.setImageDrawable(this.u);
            }
            this.e.setVisibility(!z ? 8 : i2);
            obtainStyledAttributes.recycle();
        }
        addTextChangedListener(new APSafeTextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                AUInputBox.this.a(editable.length() == 0, AUInputBox.this.b.hasFocus());
                if (AUInputBox.this.v != null) {
                    InputFilter[] filters = editable.getFilters();
                    AUInputBox.access$300(AUInputBox.this, filters);
                    editable.setFilters(new InputFilter[0]);
                    AUInputBox.this.v.a(editable);
                    editable.setFilters(filters);
                }
                if (TextUtils.isEmpty(editable)) {
                    AUInputBox.this.setInputTextSize(AUInputBox.this.r);
                } else {
                    AUInputBox.this.setInputTextSize(AUInputBox.this.l);
                }
            }
        });
        this.c.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AUInputBox.this.b.setText("");
                AUInputBox.this.c.setVisibility(8);
                if (AUInputBox.this.h != null) {
                    AUInputBox.this.h.onClick(AUInputBox.this.c);
                }
            }
        });
        this.b.setOnFocusChangeListener(this);
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!AUInputBox.this.b.hasFocus()) {
                    AUInputBox.this.b.requestFocus();
                }
                return false;
            }
        });
        this.b.setText("");
    }

    public void setSepciaFunBtn(int i2) {
        this.e.setImageResource(i2);
    }

    public void setSepciaFunBtn(Drawable drawable) {
        this.e.setImageDrawable(drawable);
    }

    public void setInputErrorState(boolean z) {
        if (z) {
            this.d.setTextColor(getResources().getColor(R.color.r));
        } else {
            this.d.setTextColor(getResources().getColor(R.color.n));
        }
    }

    public void setTextFormatter(APFormatter aPFormatter) {
        this.v = aPFormatter;
    }

    public final void setApprerance(boolean z) {
        Typeface typeface = this.b.getTypeface();
        if (z) {
            this.b.setTypeface(typeface, 1);
        } else {
            this.b.setTypeface(typeface, 0);
        }
    }

    public void setOnEditorActionListener(OnEditorActionListener onEditorActionListener) {
        if (this.b != null) {
            this.b.setOnEditorActionListener(onEditorActionListener);
        }
    }

    public final void addTextChangedListener(TextWatcher textWatcher) {
        if (this.b != null) {
            this.b.addTextChangedListener(textWatcher);
        }
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        if (this.b != null) {
            this.w = onFocusChangeListener;
        }
    }

    public void setCleanButtonListener(OnClickListener onClickListener) {
        this.h = onClickListener;
    }

    public ImageButton getSpecialFuncImg() {
        return this.e;
    }

    public void setText(CharSequence charSequence) {
        Editable editable;
        this.b.setText(charSequence);
        if (this.b instanceof APSafeEditText) {
            editable = ((APSafeEditText) this.b).getSafeText();
        } else {
            editable = this.b.getText();
        }
        if (editable instanceof Spannable) {
            Selection.setSelection(editable, editable.length());
        }
    }

    public String getInputedText() {
        if (this.b instanceof APSafeEditText) {
            return ((APSafeEditText) this.b).getSafeText().toString();
        }
        return this.b.getText().toString();
    }

    public EditText getEtContent() {
        return this.b;
    }

    public final void setInputName(String str, int i2) {
        if (str == null || "".equals(str)) {
            this.d.setText("");
            this.d.setVisibility(8);
            return;
        }
        this.d.setText(str.trim());
        this.d.setVisibility(0);
    }

    public final void setInputNameImage(Drawable drawable) {
        if (drawable != null) {
            this.a.setImageDrawable(drawable);
            this.a.setVisibility(0);
            return;
        }
        this.a.setVisibility(8);
    }

    public ImageView getInputNameImage() {
        return this.a;
    }

    public final void setInputName(String str) {
        setInputName(str, 4);
    }

    public TextView getInputName() {
        return this.d;
    }

    public ViewGroup getInputNameContainer() {
        return this.f;
    }

    public final void setInputNameTextSize(float f2) {
        if (f2 > 0.0f) {
            this.d.setTextSize(0, f2);
        }
    }

    public final void setInputTextSize(float f2) {
        if (f2 > 0.0f) {
            this.b.setTextSize(0, f2);
        }
    }

    public final void setInputTextColor(int i2) {
        this.b.setTextColor(i2);
    }

    public final void setInputType(int i2) {
        this.b.setInputType(i2);
    }

    public final void setHint(String str) {
        if (str != null && !"".equals(str)) {
            this.b.setHint(str);
        }
    }

    public final void setHintTextColor(int i2) {
        this.b.setHintTextColor(i2);
    }

    public final void setLength(int i2) {
        if (i2 >= 0) {
            this.b.setFilters(new InputFilter[]{new LengthFilter(i2)});
            return;
        }
        this.b.setFilters(new InputFilter[0]);
    }

    /* access modifiers changed from: private */
    public void a(boolean z, boolean z2) {
        if (z2) {
            setClearButtonVisiable(!z);
        } else {
            setClearButtonVisiable(false);
        }
    }

    /* access modifiers changed from: protected */
    public void setClearButtonVisiable(boolean z) {
        if (!z || !this.g) {
            this.c.setVisibility(8);
        } else {
            this.c.setVisibility(0);
        }
    }

    public ImageButton getClearButton() {
        return this.c;
    }

    public boolean isNeedShowClearButton() {
        return this.g;
    }

    public void setNeedShowClearButton(boolean z) {
        this.g = z;
    }

    public void onFocusChange(View view, boolean z) {
        boolean z2 = false;
        if (this.b instanceof APSafeEditText) {
            if (((APSafeEditText) this.b).getSafeText().length() == 0) {
                z2 = true;
            }
            a(z2, z);
        } else {
            if (this.b.getText().length() == 0) {
                z2 = true;
            }
            a(z2, z);
        }
        if (this.w != null) {
            this.w.onFocusChange(view, z);
        }
    }

    static /* synthetic */ void access$300(AUInputBox aUInputBox, InputFilter[] inputFilterArr) {
        if (inputFilterArr != null && inputFilterArr.length > 0) {
            for (InputFilter inputFilter : inputFilterArr) {
                if (inputFilter instanceof LengthFilter) {
                    try {
                        Field[] declaredFields = aUInputBox.v.getClass().getDeclaredFields();
                        int length = declaredFields.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                break;
                            }
                            Field field = declaredFields[i2];
                            if (field.getType().getSimpleName().startsWith("InputFilter")) {
                                field.setAccessible(true);
                                field.set(aUInputBox.v, new InputFilter[]{inputFilter});
                                break;
                            }
                            i2++;
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        }
    }
}
