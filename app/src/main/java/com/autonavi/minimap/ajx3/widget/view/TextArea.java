package com.autonavi.minimap.ajx3.widget.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils.TruncateAt;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import com.autonavi.minimap.ajx3.IPageLifeCircleView;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.util.KeyBoardUtil;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.TextAreaProperty;

@SuppressLint({"ViewConstructor"})
public class TextArea extends AbsoluteLayout implements IPageLifeCircleView, ViewExtension {
    private final AjxEditText mEditView;
    private final View mFocusPreemptor;
    private final EditText mHintView;
    private IPageLifeCircleListener mListener = null;
    private final BaseProperty mProperty;

    public interface IPageLifeCircleListener {
        void onPageDestroy(View view);

        void onPageResume(View view);

        void onPageStop(View view);
    }

    public int getLines() {
        return Integer.MAX_VALUE;
    }

    public void onNewIntent() {
    }

    public TextArea(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mFocusPreemptor = new View(iAjxContext.getNativeContext());
        this.mEditView = (AjxEditText) LayoutInflater.from(iAjxContext.getNativeContext()).inflate(R.layout.ajx3_input_editview, null);
        this.mEditView.initView(iAjxContext, this);
        this.mHintView = new BaseEditText(iAjxContext.getNativeContext(), null, 16842884);
        this.mHintView.setEnabled(false);
        addView(this.mFocusPreemptor, new LayoutParams(1, 1));
        addView(this.mHintView, new LayoutParams(-1, -1));
        addView(this.mEditView, new LayoutParams(-1, -1));
        this.mProperty = createProperty(iAjxContext);
        setClickable(true);
        cancelAutoFocus();
    }

    /* access modifiers changed from: protected */
    public BaseProperty createProperty(@NonNull IAjxContext iAjxContext) {
        return new TextAreaProperty(this, iAjxContext);
    }

    public AjxEditText getEditView() {
        return this.mEditView;
    }

    public EditText getHintView() {
        return this.mHintView;
    }

    public void setFilters(InputFilter[] inputFilterArr) {
        this.mHintView.setFilters(inputFilterArr);
        this.mEditView.setFilters(inputFilterArr);
    }

    public InputFilter[] getFilters() {
        return this.mEditView.getFilters();
    }

    public void setLines(int i) {
        this.mHintView.setLines(i);
        this.mEditView.setLines(i);
    }

    public void setSingleLine(boolean z) {
        this.mHintView.setSingleLine(z);
        this.mEditView.setSingleLine(z);
    }

    public void setGravity(int i) {
        this.mHintView.setGravity(i);
        this.mEditView.setGravity(i);
    }

    public void setTransformationMethod(TransformationMethod transformationMethod) {
        this.mHintView.setTransformationMethod(transformationMethod);
        this.mEditView.setTransformationMethod(transformationMethod);
    }

    public void setTypeface(int i) {
        this.mHintView.setTypeface(null, i);
        this.mEditView.setTypeface(null, i);
    }

    public void setEllipsize(TruncateAt truncateAt) {
        this.mHintView.setEllipsize(truncateAt);
        this.mEditView.setEllipsize(truncateAt);
    }

    public void setPaintFlags(int i) {
        this.mHintView.setPaintFlags(i);
        this.mEditView.setPaintFlags(i);
    }

    public void setTextSize(int i, float f) {
        this.mHintView.setTextSize(i, f);
        this.mEditView.setTextSize(i, f);
    }

    public void afterTextChanged(String str) {
        ((TextAreaProperty) this.mProperty).afterTextChanged(str);
    }

    public void updateValue() {
        ((TextAreaProperty) this.mProperty).updateValue();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    public void cancelAutoFocus() {
        this.mFocusPreemptor.setFocusable(true);
        this.mFocusPreemptor.setFocusableInTouchMode(true);
    }

    public void onPageResume() {
        if (getEditView().isFocused()) {
            if (getEditView().getNoKeyboard()) {
                if (getContext() instanceof Activity) {
                    KeyBoardUtil.setInputStateShow((Activity) getContext(), false);
                }
                return;
            }
            ((TextAreaProperty) this.mProperty).updateSelfSoftInputMode();
        }
        if (this.mListener != null) {
            this.mListener.onPageResume(getEditView());
        }
    }

    public void onPageStop() {
        if (this.mListener != null) {
            this.mListener.onPageStop(getEditView());
        }
    }

    public void onPageDestroy() {
        if (this.mListener != null) {
            this.mListener.onPageDestroy(getEditView());
        }
        setPageLifeCircleListener(null);
    }

    public void setPageLifeCircleListener(IPageLifeCircleListener iPageLifeCircleListener) {
        this.mListener = iPageLifeCircleListener;
    }
}
