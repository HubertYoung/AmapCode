package com.autonavi.bundle.uitemplate.mapwidget.inter;

import android.view.MotionEvent;
import android.view.View;
import com.autonavi.map.fragmentcontainer.page.IPage;
import java.lang.reflect.ParameterizedType;

public abstract class WidgetListener<T extends IPage> {
    public void onClick(View view, String str) {
    }

    public boolean onTouch(View view, MotionEvent motionEvent, String str) {
        return false;
    }

    public Class<T> getPageClass() {
        try {
            return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception unused) {
            return null;
        }
    }
}
