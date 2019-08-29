package com.amap.bundle.planhome.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import com.amap.bundle.planhome.view.RouteEditLineView.LinePosition;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.State;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.a;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.b;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.c;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.d;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RouteEditMultiLineView extends ViewGroup {
    private static final int LINE_START_INDEX = 1;
    private static final int STYLE_DEFAULT = 1;
    private static final int STYLE_WITH_PRE_MID = 2;
    private static int sDividerHeight;
    private static int sFlagMarginLeft;
    private static int sLineHeight;
    private static int sPreLineHeight;
    private ImageView mAddImageView;
    private boolean mEditable;
    private RouteEditLineView mEndLineView;
    private ImageView mFlagImageView;
    private Animator mFlagInAnimator;
    private Animator mFlagOutAnimator;
    /* access modifiers changed from: private */
    public int mFocusWidgetId;
    private int mHeight;
    private a mInternalEditFocusChangeListener;
    private int mMaxMidCount;
    private List<RouteEditLineView> mMidLineViews;
    private CharSequence[] mMidTexts;
    /* access modifiers changed from: private */
    public a mOnEditFocusChangeListener;
    private b mOnEditorActionListener;
    private c mOnRouteEditClickListener;
    private d mOnTextChangedListener;
    private RouteEditLineView mPreMidLineView;
    private Stack<RouteEditLineView> mRecycler;
    private List<RouteEditLineView> mRemovingLineViews;
    private RouteEditLineView mStartLineView;
    private State mState;
    private CharSequence mSummaryMidText;
    private int mViewStyle;

    public RouteEditMultiLineView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMidLineViews = new ArrayList();
        this.mRemovingLineViews = new ArrayList();
        this.mRecycler = new Stack<>();
        this.mEditable = true;
        this.mMaxMidCount = 3;
        this.mFocusWidgetId = -1;
        this.mViewStyle = 1;
        this.mState = State.EDIT;
        init(context);
    }

    public RouteEditMultiLineView(Context context) {
        this(context, null);
    }

    public RouteEditMultiLineView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void init(Context context) {
        ensureDimensValue();
        setClipChildren(false);
        setClipToPadding(false);
        setupEditFocusChangeListener();
        initView(context);
    }

    private void setupEditFocusChangeListener() {
        this.mInternalEditFocusChangeListener = new a() {
            public final void a(int i, View view, boolean z) {
                if (z) {
                    RouteEditMultiLineView.this.mFocusWidgetId = i;
                } else if (RouteEditMultiLineView.this.mFocusWidgetId == i) {
                    RouteEditMultiLineView.this.mFocusWidgetId = -1;
                }
                if (RouteEditMultiLineView.this.mOnEditFocusChangeListener != null) {
                    RouteEditMultiLineView.this.mOnEditFocusChangeListener.a(i, view, z);
                }
            }
        };
    }

    private void initView(Context context) {
        initStartLine(context);
        initPreMidLineLine(context);
        initEndLine(context);
        initFlagView(context);
        addViewInLayout(this.mFlagImageView, 0, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mStartLineView, 1, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mPreMidLineView, 2, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mEndLineView, 3, generateDefaultLayoutParams(), true);
    }

    private void initStartLine(Context context) {
        this.mStartLineView = new RouteEditLineView(context);
        this.mStartLineView.setLinePosition(LinePosition.START);
        this.mStartLineView.setImageDrawable(getResources().getDrawable(R.drawable.icon_route_edit_flag_start));
        this.mStartLineView.setOnEditFocusChangeListener(this.mInternalEditFocusChangeListener);
    }

    private void initEndLine(Context context) {
        this.mEndLineView = new RouteEditLineView(context);
        this.mEndLineView.setLinePosition(LinePosition.END);
        this.mEndLineView.setDividerVisibility(8);
        this.mEndLineView.setImageDrawable(getResources().getDrawable(R.drawable.icon_route_edit_flag_end));
        this.mEndLineView.setOnEditFocusChangeListener(this.mInternalEditFocusChangeListener);
    }

    private void initPreMidLineLine(Context context) {
        this.mPreMidLineView = new RouteEditLineView(context);
        this.mPreMidLineView.setLinePosition(LinePosition.PRE_MID);
        this.mPreMidLineView.setDividerVisibility(8);
        this.mPreMidLineView.setOnEditFocusChangeListener(this.mInternalEditFocusChangeListener);
        this.mPreMidLineView.setTextSize(getResources().getDimensionPixelSize(R.dimen.f_s_13));
        this.mPreMidLineView.setVisibility(8);
        this.mPreMidLineView.setEditable(false);
    }

    private void initFlagView(Context context) {
        this.mFlagImageView = new ImageView(context);
        this.mFlagImageView.setImageResource(R.drawable.icon_route_edit_flag_vertical);
    }

    private void ensureDimensValue() {
        if (sLineHeight <= 0) {
            sDividerHeight = getResources().getDimensionPixelOffset(R.dimen.route_edit_divider_height);
            sLineHeight = getResources().getDimensionPixelOffset(R.dimen.route_edit_line_height);
            sPreLineHeight = getResources().getDimensionPixelOffset(R.dimen.route_edit_pre_line_height);
            sFlagMarginLeft = getResources().getDimensionPixelOffset(R.dimen.route_edit_to_margin_left);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int size = MeasureSpec.getSize(i);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
        if (this.mFlagImageView.getVisibility() != 8) {
            this.mFlagImageView.measure(makeMeasureSpec, makeMeasureSpec);
        }
        int i5 = 0;
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(Math.max(0, size), UCCore.VERIFY_POLICY_QUICK);
        int i6 = 1;
        if (this.mViewStyle == 1) {
            i4 = sLineHeight + sDividerHeight;
            i3 = sLineHeight;
        } else {
            i4 = sPreLineHeight;
            i3 = sPreLineHeight + sDividerHeight;
        }
        int makeMeasureSpec3 = MeasureSpec.makeMeasureSpec(i4, UCCore.VERIFY_POLICY_QUICK);
        int makeMeasureSpec4 = MeasureSpec.makeMeasureSpec(i3, UCCore.VERIFY_POLICY_QUICK);
        int childCount = getChildCount();
        while (i6 < childCount) {
            View childAt = getChildAt(i6);
            if (childAt instanceof RouteEditLineView) {
                RouteEditLineView routeEditLineView = (RouteEditLineView) childAt;
                if (!(routeEditLineView == null || routeEditLineView.getVisibility() == 8)) {
                    int expectHeight = routeEditLineView.getExpectHeight();
                    if (expectHeight >= 0) {
                        i5 += expectHeight;
                    } else {
                        routeEditLineView.measure(makeMeasureSpec2, i6 == childCount + -1 ? makeMeasureSpec4 : makeMeasureSpec3);
                        i5 += routeEditLineView.getMeasuredHeight();
                    }
                }
            }
            i6++;
        }
        this.mHeight = i5;
        setMeasuredDimension(resolveSize(size, i), resolveSize(i5, i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = this.mHeight;
        if (this.mFlagImageView.getVisibility() != 8) {
            int measuredHeight = (i5 - this.mFlagImageView.getMeasuredHeight()) >> 1;
            ImageView imageView = this.mFlagImageView;
            int i6 = sFlagMarginLeft;
            imageView.layout(i6, measuredHeight, this.mFlagImageView.getMeasuredWidth() + i6, this.mFlagImageView.getMeasuredHeight() + measuredHeight);
        }
        int childCount = getChildCount();
        int i7 = 0;
        for (int i8 = 1; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt instanceof RouteEditLineView) {
                RouteEditLineView routeEditLineView = (RouteEditLineView) childAt;
                if (routeEditLineView.getVisibility() != 8) {
                    int expectHeight = routeEditLineView.getExpectHeight();
                    if (expectHeight >= 0) {
                        i7 += expectHeight;
                    } else {
                        routeEditLineView.layout(0, i7, routeEditLineView.getMeasuredWidth() + 0, routeEditLineView.getMeasuredHeight() + i7);
                        i7 += routeEditLineView.getMeasuredHeight();
                    }
                }
            }
        }
    }

    public void setAddImageView(ImageView imageView) {
        this.mAddImageView = imageView;
    }

    public boolean checkMidState() {
        return this.mMidLineViews.size() + this.mRemovingLineViews.size() < this.mMaxMidCount && this.mState == State.EDIT;
    }

    public void setState(State state) {
        if (this.mState != state) {
            this.mState = state;
            this.mViewStyle = (this.mState != State.EDIT && getValidMidContentCount(this.mMidTexts) > 0) ? 2 : 1;
            setupViewStyle();
            requestLayout();
        }
    }

    public void setupSummaryState() {
        this.mStartLineView.setLinePosition(LinePosition.SUMMARY_START);
        this.mEndLineView.setLinePosition(LinePosition.SUMMARY_END);
        this.mPreMidLineView.setLinePosition(LinePosition.SUMMARY_MID);
        setEditable(false);
    }

    private void setupViewStyle() {
        float f = 1.0f;
        boolean z = true;
        if (this.mViewStyle == 1) {
            setMidLineViewVisibility(0);
            this.mPreMidLineView.setVisibility(8);
            boolean z2 = !this.mMidLineViews.isEmpty();
            ImageView imageView = this.mFlagImageView;
            if (z2) {
                f = 0.0f;
            }
            imageView.setAlpha(f);
            this.mStartLineView.setTextSize(getResources().getDimensionPixelSize(R.dimen.f_s_16));
            this.mEndLineView.setTextSize(getResources().getDimensionPixelSize(R.dimen.f_s_16));
            this.mStartLineView.setDividerVisibility(0);
            int midCount = getMidCount();
            ImageView imageView2 = this.mAddImageView;
            if (midCount >= this.mMaxMidCount) {
                z = false;
            }
            imageView2.setEnabled(z);
            return;
        }
        if (this.mViewStyle == 2) {
            setMidLineViewVisibility(8);
            this.mPreMidLineView.setVisibility(0);
            this.mFlagImageView.setAlpha(1.0f);
            this.mPreMidLineView.setText(passTextsToString(this.mSummaryMidText, this.mMidTexts));
            this.mStartLineView.setTextSize(getResources().getDimensionPixelSize(R.dimen.f_s_13));
            this.mEndLineView.setTextSize(getResources().getDimensionPixelSize(R.dimen.f_s_13));
            this.mStartLineView.setDividerVisibility(8);
            int validMidContentCount = getValidMidContentCount(this.mMidTexts);
            ImageView imageView3 = this.mAddImageView;
            if (validMidContentCount >= this.mMaxMidCount) {
                z = false;
            }
            imageView3.setEnabled(z);
        }
    }

    private void setMidLineViewVisibility(int i) {
        int size = this.mMidLineViews.size();
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                RouteEditLineView routeEditLineView = this.mMidLineViews.get(i2);
                if (!(routeEditLineView == null || routeEditLineView.getVisibility() == i)) {
                    routeEditLineView.setVisibility(i);
                }
            }
        }
    }

    public State getState() {
        return this.mState;
    }

    public void setMaxMidCount(int i) {
        if (i >= 0 && this.mMaxMidCount != i) {
            this.mMaxMidCount = i;
            int size = this.mMidLineViews.size();
            boolean z = true;
            if (size > i) {
                for (int i2 = size - 1; i2 >= i; i2--) {
                    removeMidViewByIndex(i2, false, null);
                }
            }
            this.mMidTexts = shrinkMidTexts(this.mMidTexts);
            if (this.mViewStyle == 2 && this.mPreMidLineView.getVisibility() == 0) {
                this.mPreMidLineView.setText(passTextsToString(this.mSummaryMidText, this.mMidTexts));
            }
            if (this.mState == State.EDIT) {
                int midCount = getMidCount();
                ImageView imageView = this.mAddImageView;
                if (midCount >= this.mMaxMidCount) {
                    z = false;
                }
                imageView.setEnabled(z);
                return;
            }
            int length = this.mMidTexts == null ? 0 : this.mMidTexts.length;
            ImageView imageView2 = this.mAddImageView;
            if (length >= this.mMaxMidCount) {
                z = false;
            }
            imageView2.setEnabled(z);
        }
    }

    public void setOnRouteEditClickListener(c cVar) {
        this.mOnRouteEditClickListener = cVar;
        this.mStartLineView.setOnRouteEditClickListener(cVar);
        this.mEndLineView.setOnRouteEditClickListener(cVar);
        this.mPreMidLineView.setOnRouteEditClickListener(cVar);
        for (int i = 0; i < this.mMidLineViews.size(); i++) {
            this.mMidLineViews.get(i).setOnRouteEditClickListener(cVar);
        }
    }

    public void setOnEditorActionListener(b bVar) {
        this.mOnEditorActionListener = bVar;
        this.mStartLineView.setOnEditorActionListener(bVar);
        this.mEndLineView.setOnEditorActionListener(bVar);
        this.mPreMidLineView.setOnEditorActionListener(bVar);
        for (int i = 0; i < this.mMidLineViews.size(); i++) {
            this.mMidLineViews.get(i).setOnEditorActionListener(bVar);
        }
    }

    public void setOnTextChangeListener(d dVar) {
        this.mOnTextChangedListener = dVar;
        this.mStartLineView.setOnTextChangeListener(dVar);
        this.mEndLineView.setOnTextChangeListener(dVar);
        this.mPreMidLineView.setOnTextChangeListener(dVar);
        for (int i = 0; i < this.mMidLineViews.size(); i++) {
            this.mMidLineViews.get(i).setOnTextChangeListener(dVar);
        }
    }

    public void setOnEditFocusChangeListener(a aVar) {
        this.mOnEditFocusChangeListener = aVar;
    }

    public int getFocusWidgetId() {
        return this.mFocusWidgetId;
    }

    public boolean requestFocusWithId(int i) {
        if (this.mEditable && isShown()) {
            EditText d = acv.d(this, i);
            if (d != null) {
                boolean requestFocus = d.requestFocus();
                Editable text = d.getText();
                if (text != null) {
                    d.setSelection(text.length());
                }
                ((InputMethodManager) getContext().getApplicationContext().getSystemService("input_method")).showSoftInput(d, 0);
                return requestFocus;
            }
        }
        return false;
    }

    public void setStartText(CharSequence charSequence) {
        this.mStartLineView.setText(charSequence);
    }

    public void setEndText(CharSequence charSequence) {
        this.mEndLineView.setText(charSequence);
    }

    public void setMidTexts(CharSequence charSequence, CharSequence... charSequenceArr) {
        CharSequence charSequence2;
        this.mSummaryMidText = charSequence;
        this.mMidTexts = shrinkMidTexts(charSequenceArr);
        checkPreMidLineState();
        int size = this.mMidLineViews.size();
        int length = this.mMidTexts == null ? 0 : this.mMidTexts.length;
        this.mAddImageView.setEnabled(length < this.mMaxMidCount);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                RouteEditLineView routeEditLineView = this.mMidLineViews.get(i);
                if (routeEditLineView != null) {
                    if (i >= length) {
                        charSequence2 = "";
                    } else {
                        charSequence2 = this.mMidTexts[i];
                    }
                    routeEditLineView.setText(charSequence2);
                }
            }
        }
    }

    private CharSequence[] shrinkMidTexts(CharSequence... charSequenceArr) {
        if ((charSequenceArr == null ? 0 : charSequenceArr.length) <= this.mMaxMidCount) {
            return charSequenceArr;
        }
        CharSequence[] charSequenceArr2 = new CharSequence[this.mMaxMidCount];
        for (int i = 0; i < this.mMaxMidCount; i++) {
            charSequenceArr2[i] = charSequenceArr[i];
        }
        return charSequenceArr2;
    }

    private void checkPreMidLineState() {
        StringBuilder sb = new StringBuilder("RouteEditMultiLineView: checkPreMidLineState : ");
        sb.append(this.mState == null ? "null" : this.mState.name());
        eao.b("access_point", sb.toString());
        if (this.mState != State.EDIT) {
            int i = this.mViewStyle;
            this.mViewStyle = getValidMidContentCount(this.mMidTexts) > 0 ? 2 : 1;
            if (this.mViewStyle != i) {
                setupViewStyle();
            } else if (this.mPreMidLineView.getVisibility() != 8) {
                this.mPreMidLineView.setText(passTextsToString(this.mSummaryMidText, this.mMidTexts));
            }
        }
    }

    private int getValidMidContentCount(CharSequence... charSequenceArr) {
        if (charSequenceArr == null) {
            return 0;
        }
        int i = 0;
        for (CharSequence isEmpty : charSequenceArr) {
            if (!TextUtils.isEmpty(isEmpty)) {
                i++;
            }
        }
        return i;
    }

    private String passTextsToString(CharSequence charSequence, CharSequence... charSequenceArr) {
        StringBuilder sb = new StringBuilder();
        if (charSequenceArr == null || charSequenceArr.length == 0) {
            return sb.toString();
        }
        int validMidContentCount = getValidMidContentCount(charSequenceArr);
        if (validMidContentCount <= 0) {
            return sb.toString();
        }
        if (validMidContentCount > 1 && !TextUtils.isEmpty(charSequence)) {
            sb.append(charSequence);
            sb.append("：");
        }
        int length = charSequenceArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (!TextUtils.isEmpty(charSequenceArr[i])) {
                if (z) {
                    sb.append("、");
                }
                sb.append(charSequenceArr[i]);
                z = true;
            }
        }
        return sb.toString();
    }

    public void setStartHint(CharSequence charSequence) {
        this.mStartLineView.setHint(charSequence);
    }

    public void setEndHint(CharSequence charSequence) {
        this.mEndLineView.setHint(charSequence);
    }

    public void setMidHints(CharSequence... charSequenceArr) {
        int i;
        String str;
        int size = this.mMidLineViews.size();
        if (charSequenceArr == null) {
            i = 0;
        } else {
            i = charSequenceArr.length;
        }
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                RouteEditLineView routeEditLineView = this.mMidLineViews.get(i2);
                if (routeEditLineView != null) {
                    if (i2 >= i) {
                        str = "";
                    } else {
                        str = charSequenceArr[i2];
                    }
                    routeEditLineView.setHint(str);
                }
            }
        }
    }

    public CharSequence getStartText() {
        return this.mStartLineView.getText();
    }

    public CharSequence getEndText() {
        return this.mEndLineView.getText();
    }

    public CharSequence[] getMidTexts() {
        CharSequence charSequence;
        if (this.mState != State.EDIT) {
            return this.mMidTexts;
        }
        int size = this.mMidLineViews.size();
        if (size <= 0) {
            return new CharSequence[0];
        }
        CharSequence[] charSequenceArr = new CharSequence[size];
        for (int i = 0; i < size; i++) {
            RouteEditLineView routeEditLineView = this.mMidLineViews.get(i);
            if (routeEditLineView == null) {
                charSequence = "";
            } else {
                charSequence = routeEditLineView.getText();
            }
            charSequenceArr[i] = charSequence;
        }
        return charSequenceArr;
    }

    public int getMidCount() {
        if (this.mState == State.EDIT) {
            return this.mMidLineViews.size();
        }
        if (this.mMidTexts == null) {
            return 0;
        }
        return this.mMidTexts.length;
    }

    public void setEditable(boolean z) {
        if (this.mEditable != z) {
            this.mEditable = z;
            this.mStartLineView.setEditable(z);
            this.mEndLineView.setEditable(z);
            for (int i = 0; i < this.mMidLineViews.size(); i++) {
                this.mMidLineViews.get(i).setEditable(z);
            }
        }
    }

    public void prepareEnterAnimator() {
        this.mStartLineView.getFlagImageView().setAlpha(0.0f);
        this.mEndLineView.getFlagImageView().setAlpha(0.0f);
        this.mFlagImageView.setAlpha(0.0f);
        View a = acv.a(this, 33);
        if (a != null) {
            a.setAlpha(0.0f);
        }
    }

    public void enterAnimator() {
        this.mStartLineView.startDiffuseAnimator(0);
        ach.a((View) this.mFlagImageView);
        this.mEndLineView.startDiffuseAnimator(400);
        View a = acv.a(this, 33);
        if (a != null) {
            ach.b(a);
        }
    }

    public void exchangeAnimator(AnimatorListener animatorListener) {
        int abs = Math.abs(this.mEndLineView.getTop() - this.mStartLineView.getTop());
        ValueAnimator a = ach.a(abs);
        a.setTarget(this.mStartLineView.getEditText());
        ValueAnimator b = ach.b(abs);
        b.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                RouteEditMultiLineView.this.invalidate();
            }
        });
        b.setTarget(this.mEndLineView.getEditText());
        AnimatorSet animatorSet = new AnimatorSet();
        Builder play = animatorSet.play(a);
        play.with(b);
        midExchangeAnimator(play);
        if (animatorListener != null) {
            animatorSet.addListener(animatorListener);
        }
        animatorSet.start();
    }

    private void midExchangeAnimator(Builder builder) {
        int size = this.mMidLineViews.size();
        if (size >= 2) {
            RouteEditLineView routeEditLineView = this.mMidLineViews.get(0);
            RouteEditLineView routeEditLineView2 = this.mMidLineViews.get(size - 1);
            int abs = Math.abs(routeEditLineView2.getTop() - routeEditLineView.getTop());
            ValueAnimator a = ach.a(abs);
            a.setTarget(routeEditLineView.getEditText());
            ValueAnimator b = ach.b(abs);
            b.setTarget(routeEditLineView2.getEditText());
            builder.with(a);
            builder.with(b);
        }
    }

    private RouteEditLineView obtainView() {
        RouteEditLineView routeEditLineView;
        if (!this.mRecycler.isEmpty()) {
            routeEditLineView = this.mRecycler.pop();
        } else {
            routeEditLineView = new RouteEditLineView(getContext());
            routeEditLineView.setImageDrawable(getResources().getDrawable(R.drawable.icon_route_edit_remove_selector));
        }
        routeEditLineView.setOnRouteEditClickListener(this.mOnRouteEditClickListener);
        routeEditLineView.setOnEditorActionListener(this.mOnEditorActionListener);
        routeEditLineView.setOnTextChangeListener(this.mOnTextChangedListener);
        routeEditLineView.setOnEditFocusChangeListener(this.mInternalEditFocusChangeListener);
        routeEditLineView.setEditable(this.mEditable);
        return routeEditLineView;
    }

    /* access modifiers changed from: private */
    public void recycleView(RouteEditLineView routeEditLineView) {
        this.mRemovingLineViews.remove(routeEditLineView);
        removeView(routeEditLineView);
        routeEditLineView.reset();
        this.mRecycler.add(routeEditLineView);
    }

    private void resetMidPosition() {
        int size = this.mMidLineViews.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                RouteEditLineView routeEditLineView = this.mMidLineViews.get(i);
                if (routeEditLineView != null) {
                    routeEditLineView.setLinePositionByMidPosition(i);
                }
            }
        }
    }

    private boolean removeMidViewByIndex(int i, boolean z, final AnimatorListener animatorListener) {
        int size = this.mMidLineViews.size();
        boolean z2 = false;
        if (i < 0 || i >= size) {
            return false;
        }
        final RouteEditLineView routeEditLineView = this.mMidLineViews.get(i);
        if (routeEditLineView == null) {
            return false;
        }
        this.mMidLineViews.remove(routeEditLineView);
        routeEditLineView.setLinePosition(null);
        resetMidPosition();
        this.mRemovingLineViews.add(routeEditLineView);
        int measuredHeight = routeEditLineView.getMeasuredHeight();
        if (!z || measuredHeight <= 0 || routeEditLineView.getVisibility() == 8) {
            recycleView(routeEditLineView);
        } else {
            routeEditLineView.startRemoveAnimator(measuredHeight, new AnimatorListenerAdapter() {
                public final void onAnimationStart(Animator animator) {
                    if (animatorListener != null) {
                        animatorListener.onAnimationStart(animator);
                    }
                }

                public final void onAnimationEnd(Animator animator) {
                    RouteEditMultiLineView.this.recycleView(routeEditLineView);
                    if (animatorListener != null) {
                        animatorListener.onAnimationEnd(animator);
                    }
                }
            });
        }
        ImageView imageView = this.mAddImageView;
        if (this.mMidLineViews.size() < this.mMaxMidCount) {
            z2 = true;
        }
        imageView.setEnabled(z2);
        showFlagIfNeed(z);
        return true;
    }

    public boolean removeMidView(int i, boolean z, AnimatorListener animatorListener) {
        return removeMidViewByIndex(acv.a(i), z, animatorListener);
    }

    public boolean addMidView(boolean z, AnimatorListener animatorListener) {
        if (!checkMidState()) {
            return false;
        }
        RouteEditLineView obtainView = obtainView();
        this.mMidLineViews.add(obtainView);
        resetMidPosition();
        if (z && this.mState == State.EDIT) {
            obtainView.startAddAnimator(sLineHeight + sDividerHeight, animatorListener);
        }
        dismissFlagIfNeed(z);
        addView(obtainView, getChildCount() - 1, generateDefaultLayoutParams());
        this.mAddImageView.setEnabled(checkMidState());
        return true;
    }

    private void showFlagIfNeed(boolean z) {
        if (this.mMidLineViews.isEmpty()) {
            cancelFlagAnimator();
            if (z) {
                this.mFlagInAnimator = ach.c(this.mFlagImageView);
                this.mFlagInAnimator.setStartDelay(ach.a);
                this.mFlagInAnimator.start();
                return;
            }
            this.mFlagImageView.setAlpha(1.0f);
        }
    }

    private void dismissFlagIfNeed(boolean z) {
        if (this.mMidLineViews.size() == 1) {
            cancelFlagAnimator();
            if (z) {
                this.mFlagOutAnimator = ach.d(this.mFlagImageView);
                this.mFlagOutAnimator.start();
                return;
            }
            this.mFlagImageView.setAlpha(0.0f);
        }
    }

    private void cancelFlagAnimator() {
        if (this.mFlagInAnimator != null && this.mFlagInAnimator.isRunning() && this.mFlagInAnimator.isStarted()) {
            this.mFlagInAnimator.cancel();
        }
        if (this.mFlagOutAnimator != null && this.mFlagOutAnimator.isRunning() && this.mFlagOutAnimator.isStarted()) {
            this.mFlagOutAnimator.cancel();
        }
        this.mFlagInAnimator = null;
        this.mFlagOutAnimator = null;
    }
}
