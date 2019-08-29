package com.autonavi.minimap.ajx3.widget;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.IHandleBackPressedView;
import com.autonavi.minimap.ajx3.IPageLifeCircleView;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.widget.view.Container;
import com.autonavi.minimap.ajx3.widget.view.Html;
import com.autonavi.minimap.ajx3.widget.view.Image;
import com.autonavi.minimap.ajx3.widget.view.Input;
import com.autonavi.minimap.ajx3.widget.view.Label;
import com.autonavi.minimap.ajx3.widget.view.TextArea;
import com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class AjxViewManager {
    private static final int SCROLLER_TYPE = 1;
    private static final int SCROLLER_TYPE_H = 2;
    private static final int SCROLLER_TYPE_VP = 3;
    private static final Map<String, Constructor<? extends View>> mCacheConstructorMap = new HashMap();
    private static final Map<String, Class<? extends View>> mViewMap = new HashMap();

    public static View createView(IAjxContext iAjxContext, int i) {
        View view = (i == 1056964749 || i == 1056964759 || i == 1056964756) ? new Container(iAjxContext) : i == 1056964757 ? new PullToRefreshList(iAjxContext) : i == 1056964750 ? new Label(iAjxContext) : i == 1056964751 ? new Html(iAjxContext) : i == 1056964752 ? new Input(iAjxContext) : i == 1056964753 ? new TextArea(iAjxContext) : i == 1056964754 ? new Image(iAjxContext) : null;
        if (view instanceof ViewGroup) {
            ((ViewGroup) view).setMotionEventSplittingEnabled(false);
        }
        return view;
    }

    private static int getScrollerType(AjxDomNode ajxDomNode) {
        if (TextUtils.equals("viewpager", (String) ajxDomNode.getAttributeValue("viewType"))) {
            return 3;
        }
        return 1056964743 == ajxDomNode.getStyleIntValue(Property.NODE_PROPERTY_ORIENTATION, -1, 0) ? 2 : 1;
    }

    public static View createView(@NonNull IAjxContext iAjxContext, @NonNull String str) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Constructor<? extends U> constructor = mCacheConstructorMap.get(str);
        if (constructor == null) {
            constructor = iAjxContext.getNativeContext().getClassLoader().loadClass(getFullName(str)).asSubclass(View.class).getConstructor(new Class[]{IAjxContext.class});
            mCacheConstructorMap.put(str, constructor);
        }
        constructor.setAccessible(true);
        View view = (View) constructor.newInstance(new Object[]{iAjxContext});
        if (view instanceof ViewGroup) {
            ((ViewGroup) view).setMotionEventSplittingEnabled(false);
        }
        return view;
    }

    public static void registerInterfaceView(@NonNull IAjxContext iAjxContext, View view) {
        if (view instanceof IPageLifeCircleView) {
            iAjxContext.addPageLifeCircleView((IPageLifeCircleView) view);
        }
        if (view instanceof IHandleBackPressedView) {
            iAjxContext.addHandleBackPressedView((IHandleBackPressedView) view);
        }
    }

    public static void register(@NonNull String str, @NonNull Class<? extends View> cls) {
        mViewMap.put(str, cls);
    }

    private static String getFullName(String str) {
        Class cls = mViewMap.get(str);
        if (cls == null) {
            return str;
        }
        return cls.getName();
    }
}
