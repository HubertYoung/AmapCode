package com.autonavi.minimap.ajx3.widget.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.MotionEvent;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.htmcompat.AjxImageGetter.AjxImageSpan;
import com.autonavi.minimap.ajx3.htmcompat.AjxSpannableStringBuilder;
import com.autonavi.minimap.ajx3.platform.impl.TextMeasurement;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.HtmlProperty;
import java.util.HashSet;
import java.util.Iterator;

@SuppressLint({"ViewConstructor"})
public class Html extends Label implements ViewExtension {
    private HashSet<AjxImageSpan> ajxImageSpans;

    /* access modifiers changed from: protected */
    public boolean isRich() {
        return true;
    }

    public Html(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    /* access modifiers changed from: protected */
    public BaseProperty createProperty(@NonNull IAjxContext iAjxContext) {
        return new HtmlProperty(this, iAjxContext);
    }

    /* access modifiers changed from: protected */
    public Layout getCacheLayout(long j) {
        CharSequence charSequence;
        Layout fetchLayout = TextMeasurement.fetchLayout(j);
        if (fetchLayout == null) {
            charSequence = null;
        } else {
            charSequence = fetchLayout.getText();
        }
        if (!(charSequence instanceof AjxSpannableStringBuilder) || !((AjxSpannableStringBuilder) charSequence).isWithImgSpan()) {
            return fetchLayout;
        }
        return null;
    }

    public void addAjxImageSpan(AjxImageSpan ajxImageSpan) {
        if (this.ajxImageSpans == null) {
            this.ajxImageSpans = new HashSet<>();
        }
        this.ajxImageSpans.add(ajxImageSpan);
    }

    public AjxImageSpan getTargetImageSpan(MotionEvent motionEvent) {
        if (this.ajxImageSpans == null) {
            return null;
        }
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        Iterator<AjxImageSpan> it = this.ajxImageSpans.iterator();
        while (it.hasNext()) {
            AjxImageSpan next = it.next();
            if (((float) (next.x + iArr[0])) <= rawX && ((float) (next.y + iArr[1])) <= rawY && ((float) (next.x + iArr[0] + next.w)) >= rawX && ((float) (next.y + iArr[1] + next.h)) >= rawY) {
                return next;
            }
        }
        return null;
    }
}
