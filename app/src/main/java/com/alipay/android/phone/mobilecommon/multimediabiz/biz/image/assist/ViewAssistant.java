package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ViewAssistant {
    private static volatile ViewAssistant a;
    private Map<Integer, Object> b = new ConcurrentHashMap();

    protected ViewAssistant() {
    }

    public static ViewAssistant getInstance() {
        if (a == null) {
            synchronized (ViewAssistant.class) {
                try {
                    if (a == null) {
                        a = new ViewAssistant();
                    }
                }
            }
        }
        return a;
    }

    public Integer getViewKey(View view) {
        if (view != null) {
            return Integer.valueOf(view.hashCode());
        }
        return Integer.valueOf(-2147483647);
    }

    public Object getViewTag(View view) {
        return this.b.get(getViewKey(view));
    }

    public boolean checkViewReused(ViewWrapper viewWrapper) {
        if (viewWrapper == null || viewWrapper.getTargetView() == null) {
            return false;
        }
        Object tag = getViewTag(viewWrapper.getTargetView());
        if (tag == null || !tag.equals(viewWrapper.getTag())) {
            return true;
        }
        return false;
    }

    public void setViewTag(View v, Object tag) {
        if (tag != null && v != null) {
            this.b.put(getViewKey(v), tag);
        }
    }

    public void removeViewTag(View v) {
        this.b.remove(getViewKey(v));
    }

    public static boolean checkImageViewNeedRender(View view, Drawable drawable) {
        if (!(view instanceof ImageView) || !(drawable instanceof BitmapDrawable)) {
            return true;
        }
        Drawable viewDrawable = ((ImageView) view).getDrawable();
        if (!(viewDrawable instanceof BitmapDrawable) || ((BitmapDrawable) viewDrawable).getBitmap() != ((BitmapDrawable) drawable).getBitmap()) {
            return true;
        }
        return false;
    }

    public static boolean checkImageViewNeedRender(View view, Bitmap bitmap) {
        if (!(view instanceof ImageView) || bitmap == null) {
            return true;
        }
        Drawable viewDrawable = ((ImageView) view).getDrawable();
        if (!(viewDrawable instanceof BitmapDrawable) || ((BitmapDrawable) viewDrawable).getBitmap() != bitmap) {
            return true;
        }
        return false;
    }
}
