package com.autonavi.minimap.statusbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.autonavi.widget.ui.StatusBar1;
import com.j256.ormlite.stmt.query.SimpleComparison;

public abstract class StatusBarManager {

    public enum FeatureType {
        TYPE_TAXI,
        TYPE_BICYCLE,
        TYPE_GROUP,
        TYPE_FREERIDE
    }

    public static class ShortCompatStatusBar extends StatusBar1 {
        protected CharSequence mStatusBarText;

        public void disableTwinkle() {
        }

        public void enableTwinkle() {
        }

        public TextView getTextView() {
            return null;
        }

        public boolean isInWindow() {
            return false;
        }

        public void setBizIcon(int i) {
        }

        public void setDuration(long j) {
        }

        public void setHorizonAlign(int i) {
        }

        public void setInitDelay(long j) {
        }

        public void setIntervalTime(long j) {
        }

        public void setTextColor(int i) {
        }

        public void setTextColor(ColorStateList colorStateList) {
        }

        public void setTextSize(float f) {
        }

        public void setTextSize(int i, float f) {
        }

        public ShortCompatStatusBar(Context context) {
            this(context, null, 0);
        }

        public ShortCompatStatusBar(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public ShortCompatStatusBar(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        public void setTextWithArrow(CharSequence charSequence) {
            StringBuilder sb = new StringBuilder();
            sb.append(charSequence);
            sb.append(SimpleComparison.GREATER_THAN_OPERATION);
            this.mStatusBarText = sb.toString();
        }

        public void setText(CharSequence charSequence) {
            this.mStatusBarText = charSequence;
        }

        public void setText(int i) {
            this.mStatusBarText = getContext().getResources().getText(i);
        }

        public void setTextCompatShortCut(CharSequence charSequence, CharSequence charSequence2) {
            this.mStatusBarText = charSequence;
        }

        public boolean isTextEmpty() {
            return TextUtils.isEmpty(this.mStatusBarText);
        }
    }

    public static class a {
        public static int a = 10086;
        public static int b = -12345;
        public static int c;
        public CharSequence d;
        public boolean e;
        public b f;
        public FeatureType g;
        public Long h = Long.valueOf(-1);
        public int i = c;
        public View j;

        public final void a(View view) {
            if (view instanceof ShortCompatStatusBar) {
                this.d = ((ShortCompatStatusBar) view).mStatusBarText;
            }
        }

        public a(FeatureType featureType) {
            this.g = featureType;
            this.e = true;
        }
    }

    public interface b {
    }

    public abstract void a(bid bid);

    public abstract void a(@NonNull FeatureType featureType);

    public abstract void a(@NonNull FeatureType featureType, @NonNull View view);

    public abstract void a(FeatureType featureType, CharSequence charSequence, OnClickListener onClickListener);

    public abstract void a(boolean z);

    public abstract void b();

    public abstract void b(bid bid);

    public abstract boolean b(@NonNull FeatureType featureType);

    public abstract void c();

    public abstract void c(@NonNull FeatureType featureType);

    public abstract void d(@NonNull FeatureType featureType);

    public static StatusBarManager d() {
        return ((apr) defpackage.esb.a.a.a(apr.class)).a();
    }
}
