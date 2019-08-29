package com.amap.bundle.planhome.view;

import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.autonavi.bundle.routecommon.inter.IRouteEditView;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.State;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.a;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.b;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.c;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.d;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class RouteEditView extends ViewGroup implements OnClickListener, IRouteEditView {
    private ImageView mBackImageView;
    private int mBackMarginRight;
    private int mBackSize;
    private RouteFrameLayout mEditLayout;
    private int mImmersiveStatusBarHeight;
    private boolean mKeepPlaceHolder;
    private RouteEditMultiLineView mMultiLineView;
    private c mOnRouteEditClickListener;
    private State mState;
    private RouteEditMultiLineView mSummaryView;

    public RouteEditView(Context context) {
        this(context, null);
    }

    public RouteEditView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteEditView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mState = State.EDIT;
        this.mImmersiveStatusBarHeight = 0;
        this.mKeepPlaceHolder = false;
        init(context);
    }

    private void init(Context context) {
        initValues();
        setPadding(getResources().getDimensionPixelOffset(R.dimen.route_edit_padding_left), getResources().getDimensionPixelOffset(R.dimen.route_edit_padding_top), getResources().getDimensionPixelOffset(R.dimen.route_edit_padding_right), getResources().getDimensionPixelOffset(R.dimen.route_edit_padding_bottom));
        initView(context);
        if (euk.a()) {
            this.mImmersiveStatusBarHeight = euk.a(context);
        }
    }

    private void initValues() {
        this.mBackSize = getResources().getDimensionPixelOffset(R.dimen.route_edit_icon_size);
        this.mBackMarginRight = getResources().getDimensionPixelOffset(R.dimen.route_edit_back_margin_right);
    }

    private void initView(Context context) {
        initEditFrame(context);
        initBackView(context);
        addViewInLayout(this.mBackImageView, 0, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mEditLayout, 1, generateDefaultLayoutParams(), true);
        setOnClickListener(this);
    }

    private void initEditFrame(Context context) {
        this.mEditLayout = new RouteFrameLayout(context);
        this.mEditLayout.setId(R.id.route_edit_frame);
        this.mEditLayout.setBackgroundResource(R.drawable.bg_route_edit);
        this.mMultiLineView = this.mEditLayout.getMultiLineView();
        this.mSummaryView = this.mEditLayout.getSummaryView();
        this.mEditLayout.setOnClickListener(this);
    }

    private void initBackView(Context context) {
        this.mBackImageView = new ImageView(context);
        this.mBackImageView.setId(R.id.route_edit_back);
        this.mBackImageView.setScaleType(ScaleType.CENTER);
        this.mBackImageView.setOnClickListener(this);
        this.mBackImageView.setImageResource(R.drawable.icon_route_edit_back_selector);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int size = MeasureSpec.getSize(i);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.mBackSize, UCCore.VERIFY_POLICY_QUICK);
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        if (this.mBackImageView.getVisibility() != 8) {
            this.mBackImageView.measure(makeMeasureSpec, makeMeasureSpec);
            paddingLeft = (paddingLeft - this.mBackImageView.getMeasuredWidth()) - this.mBackMarginRight;
            i3 = Math.max(0, this.mBackImageView.getMeasuredHeight());
        } else {
            i3 = 0;
        }
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(Math.max(0, paddingLeft), UCCore.VERIFY_POLICY_QUICK);
        int makeMeasureSpec3 = MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
        if (this.mEditLayout.getVisibility() != 8) {
            this.mEditLayout.measure(makeMeasureSpec2, makeMeasureSpec3);
            int measuredHeight = this.mEditLayout.getMeasuredHeight();
            if (this.mKeepPlaceHolder) {
                measuredHeight = Math.max(measuredHeight, this.mEditLayout.getMaxHeight());
            }
            i3 = Math.max(i3, measuredHeight);
        }
        setMeasuredDimension(resolveSize(size, i), resolveSize(i3 + getPaddingTop() + getPaddingBottom(), i2) + this.mImmersiveStatusBarHeight);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop() + this.mImmersiveStatusBarHeight;
        if (this.mBackImageView.getVisibility() != 8) {
            this.mBackImageView.layout(paddingLeft, paddingTop, this.mBackImageView.getMeasuredWidth() + paddingLeft, this.mBackImageView.getMeasuredHeight() + paddingTop);
            paddingLeft = paddingLeft + this.mBackImageView.getMeasuredWidth() + this.mBackMarginRight;
        }
        if (this.mEditLayout.getVisibility() != 8) {
            this.mEditLayout.layout(paddingLeft, paddingTop, this.mEditLayout.getMeasuredWidth() + paddingLeft, this.mEditLayout.getMeasuredHeight() + paddingTop);
        }
    }

    public void setStartText(CharSequence charSequence) {
        this.mMultiLineView.setStartText(charSequence);
        this.mSummaryView.setStartText(charSequence);
    }

    public void setEndText(CharSequence charSequence) {
        this.mMultiLineView.setEndText(charSequence);
        this.mSummaryView.setEndText(charSequence);
    }

    public void setMidTexts(CharSequence charSequence, CharSequence... charSequenceArr) {
        this.mMultiLineView.setMidTexts(charSequence, charSequenceArr);
        this.mSummaryView.setMidTexts(charSequence, charSequenceArr);
    }

    public void setStartHint(CharSequence charSequence) {
        this.mMultiLineView.setStartHint(charSequence);
        this.mSummaryView.setStartHint(charSequence);
    }

    public void setEndHint(CharSequence charSequence) {
        this.mMultiLineView.setEndHint(charSequence);
        this.mSummaryView.setEndHint(charSequence);
    }

    public void setMidHint(CharSequence... charSequenceArr) {
        this.mMultiLineView.setMidHints(charSequenceArr);
    }

    public CharSequence getStartText() {
        return this.mMultiLineView.getStartText();
    }

    public CharSequence getEndText() {
        return this.mMultiLineView.getEndText();
    }

    public CharSequence[] getMidTexts() {
        if (isEditState()) {
            return this.mMultiLineView.getMidTexts();
        }
        if (isSummaryState()) {
            return this.mSummaryView.getMidTexts();
        }
        return new CharSequence[0];
    }

    public int getExpectHeight() {
        return this.mEditLayout.getExpectHeight();
    }

    public void setMaxMidCount(int i) {
        this.mMultiLineView.setMaxMidCount(i);
    }

    public boolean addMidView(boolean z) {
        return addMidView(z, null);
    }

    public boolean addMidView(boolean z, AnimatorListener animatorListener) {
        if (isEditState()) {
            return this.mMultiLineView.addMidView(z, animatorListener);
        }
        return false;
    }

    public boolean removeMidView(int i, boolean z) {
        return removeMidView(i, z, null);
    }

    public boolean removeMidView(int i, boolean z, AnimatorListener animatorListener) {
        if (isEditState()) {
            return this.mMultiLineView.removeMidView(i, z, animatorListener);
        }
        return false;
    }

    public int getMidCount() {
        return this.mMultiLineView.getMidCount();
    }

    public void changeState(State state, boolean z, @Nullable AnimatorListenerAdapter animatorListenerAdapter) {
        changeState(state, z, animatorListenerAdapter, null);
    }

    public void changeState(State state, boolean z, AnimatorListenerAdapter animatorListenerAdapter, @Nullable AnimatorUpdateListener animatorUpdateListener) {
        this.mState = state;
        this.mEditLayout.changeState(state, z, animatorListenerAdapter, animatorUpdateListener);
    }

    public void setKeepPlaceHolder(boolean z) {
        if (this.mKeepPlaceHolder != z) {
            this.mKeepPlaceHolder = z;
        }
    }

    public State getState() {
        return this.mState;
    }

    public void prepareEnterAnimator() {
        if (isEditState()) {
            this.mMultiLineView.prepareEnterAnimator();
        }
    }

    public void enterAnimator() {
        if (isEditState()) {
            this.mMultiLineView.enterAnimator();
        }
    }

    public void exchangeAnimator() {
        exchangeAnimator(null);
    }

    public void exchangeAnimator(AnimatorListener animatorListener) {
        this.mEditLayout.exchangeAnimator(animatorListener);
    }

    public void setAddExpectVisibility(int i) {
        this.mEditLayout.setAddExceptVisibility(i);
    }

    public int getAddExpectVisibility() {
        return this.mEditLayout.getAddExceptVisibility();
    }

    public void setVUIExpectVisibility(int i) {
        this.mEditLayout.setVUIExceptVisibility(i);
    }

    public void setEditable(boolean z) {
        this.mEditLayout.setEditable(z);
    }

    public void setOnRouteEditClickListener(c cVar) {
        this.mOnRouteEditClickListener = cVar;
        this.mEditLayout.setOnRouteEditClickListener(cVar);
    }

    public void setOnEditorActionListener(b bVar) {
        this.mMultiLineView.setOnEditorActionListener(bVar);
    }

    public void setOnTextChangeListener(d dVar) {
        this.mMultiLineView.setOnTextChangeListener(dVar);
    }

    public void setOnEditFocusChangeListener(a aVar) {
        this.mMultiLineView.setOnEditFocusChangeListener(aVar);
    }

    public int getFocusWidgetId() {
        return this.mMultiLineView.getFocusWidgetId();
    }

    public boolean requestFocusWithId(int i) {
        if (isEditState()) {
            return this.mMultiLineView.requestFocusWithId(i);
        }
        return false;
    }

    public void setEditSelection(int i, int i2) {
        EditText d = acv.d(this, i);
        if (d != null) {
            try {
                d.setSelection(Math.min(getTextLength(d), i2));
            } catch (IndexOutOfBoundsException unused) {
            }
        }
    }

    public void setEditSelection(int i, int i2, int i3) {
        EditText d = acv.d(this, i);
        if (d != null) {
            int textLength = getTextLength(d);
            try {
                d.setSelection(Math.min(textLength, i2), Math.min(textLength, i3));
            } catch (IndexOutOfBoundsException unused) {
            }
        }
    }

    public void setEditSelectionEnd(int i) {
        EditText d = acv.d(this, i);
        if (d != null) {
            try {
                d.setSelection(getTextLength(d));
            } catch (IndexOutOfBoundsException unused) {
            }
        }
    }

    public void setEditSelectAll(int i) {
        EditText d = acv.d(this, i);
        if (d != null) {
            d.selectAll();
        }
    }

    public int getEditSelectionStart(int i) {
        EditText d = acv.d(this, i);
        if (d != null) {
            return d.getSelectionStart();
        }
        return -1;
    }

    public int getEditSelectionEnd(int i) {
        EditText d = acv.d(this, i);
        if (d != null) {
            return d.getSelectionEnd();
        }
        return -1;
    }

    public void setContentDescription(int i, CharSequence charSequence) {
        View a = acv.a(this, i);
        if (a != null) {
            a.setContentDescription(charSequence);
        }
    }

    public void setEnable(int i, boolean z) {
        View a = acv.a(this, i);
        if (a != null) {
            a.setEnabled(z);
        }
    }

    public void setVisibility(int i, int i2) {
        View a = acv.a(this, i);
        if (a != null) {
            a.setVisibility(i2);
        }
    }

    public CharSequence getText(int i) {
        TextView c = acv.c(this, i);
        if (c == null) {
            return "";
        }
        CharSequence text = c.getText();
        if (text == null) {
            text = "";
        }
        return text;
    }

    public void setText(int i, CharSequence charSequence) {
        TextView c = acv.c(this, i);
        if (c != null) {
            c.setText(charSequence);
        }
    }

    public void setHint(int i, CharSequence charSequence) {
        TextView c = acv.c(this, i);
        if (c != null) {
            c.setHint(charSequence);
        }
    }

    public CharSequence getHint(int i) {
        TextView c = acv.c(this, i);
        if (c != null) {
            return c.getHint();
        }
        return null;
    }

    public void setTextColor(int i, int i2) {
        TextView c = acv.c(this, i);
        if (c != null) {
            c.setTextColor(i2);
        }
    }

    public void setTextHintColor(int i, int i2) {
        TextView c = acv.c(this, i);
        if (c != null) {
            c.setHintTextColor(i2);
        }
    }

    public void setTextSize(int i, int i2) {
        TextView c = acv.c(this, i);
        if (c != null) {
            c.setTextSize(0, (float) i2);
        }
    }

    public void setInputType(int i, int i2) {
        EditText d = acv.d(this, i);
        if (d != null) {
            d.setInputType(i2);
        }
    }

    public void setImeOptions(int i, int i2) {
        EditText d = acv.d(this, i);
        if (d != null) {
            d.setImeOptions(i2);
        }
    }

    public void setBackground(int i, Drawable drawable) {
        View a = acv.a(this, i);
        if (a != null) {
            a.setBackgroundDrawable(drawable);
        }
    }

    public void setImageDrawable(int i, Drawable drawable) {
        ImageView b = acv.b(this, i);
        if (b != null) {
            b.setImageDrawable(drawable);
        }
    }

    public void onClick(View view) {
        if (this.mOnRouteEditClickListener != null) {
            if (view == this.mBackImageView) {
                this.mOnRouteEditClickListener.a(view, 1);
            } else if (view == this.mEditLayout) {
                this.mOnRouteEditClickListener.a(view, 4);
            } else {
                if (view == this) {
                    this.mOnRouteEditClickListener.a(view, 5);
                }
            }
        }
    }

    private boolean isEditState() {
        return this.mState == State.EDIT || this.mState == State.PRE_EDIT;
    }

    private boolean isSummaryState() {
        return this.mState == State.SUMMARY;
    }

    private int getTextLength(@NonNull TextView textView) {
        CharSequence text = textView.getText();
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        return text.length();
    }
}
