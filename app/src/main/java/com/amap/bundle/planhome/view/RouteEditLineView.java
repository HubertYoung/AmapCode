package com.amap.bundle.planhome.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.b;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.c;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.d;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;

public class RouteEditLineView extends RelativeLayout implements OnClickListener {
    private View mDividerView;
    private EditableTextView mEditText;
    private boolean mEditable;
    private int mExpectHeight;
    private ImageView mFlagBackground;
    private ImageView mFlagImageView;
    /* access modifiers changed from: private */
    public boolean mInAnimating;
    private AnimatorSet mInAnimator;
    /* access modifiers changed from: private */
    public LinePosition mLinePosition;
    /* access modifiers changed from: private */
    public com.autonavi.bundle.routecommon.inter.IRouteEditView.a mOnEditFocusChangeListener;
    /* access modifiers changed from: private */
    public b mOnEditorActionListener;
    private c mOnRouteEditClickListener;
    private d mOnTextChangedListener;
    /* access modifiers changed from: private */
    public boolean mOutAnimating;
    private AnimatorSet mOutAnimator;

    public enum LinePosition {
        START(16, 17, 16, R.id.route_edit_start, R.id.route_edit_start_text, R.id.route_edit_start),
        END(32, 33, 32, R.id.route_edit_end, R.id.route_edit_end_text, R.id.route_edit_end),
        PRE_MID(48, 49, 48, R.id.route_edit_pre_mid, R.id.route_edit_pre_mid_text, R.id.route_edit_pre_mid),
        MID(64, 65, 66, R.id.route_edit_mid, R.id.route_edit_mid_text, R.id.route_edit_mid_remove),
        MID2(80, 81, 82, R.id.route_edit_mid2, R.id.route_edit_mid2_text, R.id.route_edit_mid2_remove),
        MID3(96, 97, 98, R.id.route_edit_mid3, R.id.route_edit_mid3_text, R.id.route_edit_mid3_remove),
        SUMMARY_START(256, 256, 256, R.id.route_edit_summary, R.id.route_edit_summary_start, R.id.route_edit_summary),
        SUMMARY_END(512, 512, 512, R.id.route_edit_summary, R.id.route_edit_summary_end, R.id.route_edit_summary),
        SUMMARY_MID(768, 768, 768, R.id.route_edit_summary, R.id.route_edit_summary_mid, R.id.route_edit_summary);
        
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;

        private LinePosition(int i, int i2, int i3, int i4, int i5, int i6) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            this.f = i6;
        }
    }

    static class a implements TextWatcher {
        private WeakReference<RouteEditLineView> a;
        private String b;

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public a(RouteEditLineView routeEditLineView) {
            this.a = new WeakReference<>(routeEditLineView);
        }

        public final void afterTextChanged(Editable editable) {
            String str;
            RouteEditLineView routeEditLineView = (RouteEditLineView) this.a.get();
            if (routeEditLineView != null) {
                boolean isEmpty = TextUtils.isEmpty(this.b);
                boolean isEmpty2 = TextUtils.isEmpty(editable);
                if (isEmpty || isEmpty2) {
                    if (isEmpty != isEmpty2) {
                        routeEditLineView.onTextChanged(editable);
                    }
                } else if (!this.b.equals(editable.toString())) {
                    routeEditLineView.onTextChanged(editable);
                }
                if (editable == null) {
                    str = "";
                } else {
                    str = editable.toString();
                }
                this.b = str;
            }
        }
    }

    public RouteEditLineView(Context context) {
        this(context, null);
    }

    public RouteEditLineView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteEditLineView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mExpectHeight = -1;
        this.mEditable = true;
        init(context);
    }

    private void init(Context context) {
        setClipChildren(false);
        setClipToPadding(false);
        View.inflate(context, R.layout.plan_view_edit_line, this);
        this.mFlagImageView = (ImageView) findViewById(R.id.iv_edit_flag);
        this.mFlagBackground = (ImageView) findViewById(R.id.iv_edit_flag_background);
        this.mEditText = (EditableTextView) findViewById(R.id.et_edit_text);
        this.mDividerView = findViewById(R.id.v_edit_divider);
        setupListener();
    }

    private void setupListener() {
        this.mFlagImageView.setOnClickListener(this);
        setOnClickListener(this);
        this.mEditText.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (RouteEditLineView.this.mOnEditorActionListener == null || RouteEditLineView.this.mLinePosition == null) {
                    return false;
                }
                b access$000 = RouteEditLineView.this.mOnEditorActionListener;
                RouteEditLineView.this.mLinePosition;
                return access$000.a(i);
            }
        });
        this.mEditText.addTextChangedListener(new a(this));
        this.mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            public final void onFocusChange(View view, boolean z) {
                if (!z && (view instanceof EditText)) {
                    EditText editText = (EditText) view;
                    editText.setSelection(editText.getSelectionEnd());
                }
                if (RouteEditLineView.this.mOnEditFocusChangeListener != null && RouteEditLineView.this.mLinePosition != null) {
                    RouteEditLineView.this.mOnEditFocusChangeListener.a(RouteEditLineView.this.mLinePosition.b, view, z);
                }
            }
        });
    }

    public void setLinePosition(LinePosition linePosition) {
        this.mLinePosition = linePosition;
        if (linePosition != null) {
            setId(linePosition.d, linePosition.e, linePosition.f);
        } else {
            setId(-1, -1, -1);
        }
    }

    public void setLinePositionByMidPosition(int i) {
        LinePosition linePosition;
        switch (i) {
            case 0:
                linePosition = LinePosition.MID;
                break;
            case 1:
                linePosition = LinePosition.MID2;
                break;
            case 2:
                linePosition = LinePosition.MID3;
                break;
            default:
                linePosition = null;
                break;
        }
        setLinePosition(linePosition);
    }

    public CharSequence getText() {
        Editable text = this.mEditText.getText();
        return text == null ? "" : text;
    }

    public ImageView getFlagImageView() {
        return this.mFlagImageView;
    }

    public void setText(CharSequence charSequence) {
        this.mEditText.setText(charSequence);
    }

    public void setTextSize(int i) {
        this.mEditText.setTextSize(0, (float) i);
    }

    public void setHint(CharSequence charSequence) {
        this.mEditText.setHint(charSequence);
    }

    public void setImageDrawable(Drawable drawable) {
        this.mFlagImageView.setImageDrawable(drawable);
        this.mFlagBackground.setImageDrawable(drawable);
    }

    public void startDiffuseAnimator(long j) {
        ach.a(this.mFlagImageView, this.mFlagBackground, j);
    }

    public void reset() {
        this.mOnRouteEditClickListener = null;
        this.mOnEditorActionListener = null;
        this.mOnTextChangedListener = null;
        this.mOnEditFocusChangeListener = null;
        this.mLinePosition = null;
        cancelAnimator();
        this.mEditText.setText("");
        this.mExpectHeight = -1;
        setAlpha(0.0f);
        setEditable(true);
    }

    public void setOnRouteEditClickListener(c cVar) {
        this.mOnRouteEditClickListener = cVar;
    }

    public void setOnEditorActionListener(b bVar) {
        this.mOnEditorActionListener = bVar;
    }

    public void setOnTextChangeListener(d dVar) {
        this.mOnTextChangedListener = dVar;
    }

    public void setOnEditFocusChangeListener(com.autonavi.bundle.routecommon.inter.IRouteEditView.a aVar) {
        this.mOnEditFocusChangeListener = aVar;
    }

    public void setEditable(boolean z) {
        if (this.mEditable != z) {
            this.mEditable = z;
            this.mEditText.setEditable(z);
        }
    }

    public int getExpectHeight() {
        return this.mExpectHeight;
    }

    public void setExpectHeight(int i) {
        this.mExpectHeight = i;
        requestLayout();
    }

    public EditText getEditText() {
        return this.mEditText;
    }

    public void setDividerVisibility(int i) {
        this.mDividerView.setVisibility(i);
    }

    public void startAddAnimator(int i, AnimatorListener animatorListener) {
        cancelAnimator();
        setAlpha(0.0f);
        this.mInAnimator = new AnimatorSet();
        this.mInAnimator.play(obtainChangeAnimator(true, i)).before(obtainFadeAnimator(this, true));
        if (animatorListener != null) {
            this.mInAnimator.addListener(animatorListener);
        }
        this.mInAnimator.start();
    }

    public void startRemoveAnimator(int i, AnimatorListener animatorListener) {
        cancelAnimator();
        this.mOutAnimator = new AnimatorSet();
        ValueAnimator obtainChangeAnimator = obtainChangeAnimator(false, i);
        if (animatorListener != null) {
            obtainChangeAnimator.addListener(animatorListener);
        }
        this.mOutAnimator.play(obtainChangeAnimator).after(obtainFadeAnimator(this, false));
        this.mOutAnimator.start();
    }

    private void setId(int i, int i2, int i3) {
        setId(i);
        this.mEditText.setId(i2);
        this.mFlagImageView.setId(i3);
    }

    private ValueAnimator obtainFadeAnimator(final View view, final boolean z) {
        ValueAnimator a2 = z ? ach.a() : ach.b();
        a2.setTarget(view);
        a2.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                view.setAlpha(z ? 1.0f : 0.0f);
                RouteEditLineView.this.mInAnimating = false;
            }
        });
        return a2;
    }

    private ValueAnimator obtainChangeAnimator(boolean z, int i) {
        int[] iArr = new int[2];
        iArr[0] = z ? 0 : i;
        if (!z) {
            i = 0;
        }
        iArr[1] = i;
        ValueAnimator a2 = ach.a(iArr);
        a2.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                RouteEditLineView.this.setExpectHeight(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        a2.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                RouteEditLineView.this.setExpectHeight(-1);
                RouteEditLineView.this.mOutAnimating = false;
            }
        });
        return a2;
    }

    private void cancelAnimator() {
        if (this.mInAnimating && this.mInAnimator != null) {
            this.mInAnimator.end();
        }
        if (this.mOutAnimating && this.mOutAnimator != null) {
            this.mOutAnimator.end();
        }
        this.mInAnimating = false;
        this.mOutAnimating = false;
        this.mInAnimator = null;
        this.mOutAnimator = null;
    }

    /* access modifiers changed from: private */
    public void onTextChanged(Editable editable) {
        if (this.mOnTextChangedListener != null && this.mLinePosition != null) {
            this.mOnTextChangedListener.a(this.mLinePosition.b, editable);
        }
    }

    @SuppressLint({"WrongConstant"})
    public void onClick(View view) {
        if (this.mOnRouteEditClickListener != null && this.mLinePosition != null) {
            if (view == this) {
                this.mOnRouteEditClickListener.a(view, this.mLinePosition.a);
            } else {
                this.mOnRouteEditClickListener.a(view, this.mLinePosition.c);
            }
        }
    }
}
