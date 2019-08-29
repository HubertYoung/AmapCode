package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.view.View;
import java.lang.ref.WeakReference;

public class ViewWrapper<T extends View> {
    private WeakReference<T> a;
    private Object b;

    public ViewWrapper(T v) {
        this(v, null);
    }

    public ViewWrapper(T v, Object tag) {
        this.a = new WeakReference<>(v);
        this.b = tag;
    }

    public T getTargetView() {
        return (View) this.a.get();
    }

    public Object getTag() {
        return this.b;
    }

    public void setTag(Object tag) {
        this.b = tag;
    }

    public String toString() {
        return "ViewWrapper{" + (this.a.get() == null ? null : Integer.valueOf(((View) this.a.get()).hashCode())) + ", tag=" + this.b + '}';
    }
}
