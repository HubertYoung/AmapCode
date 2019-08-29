package com.autonavi.bundle.uitemplate.mapwidget.widget.third;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import java.lang.reflect.InvocationTargetException;

public class ThirdLocalMapWidget extends AbstractMapWidget<ThirdLocalWidgetPresenter> {
    public ThirdLocalMapWidget(Context context, IWidgetProperty iWidgetProperty) {
        super(context, iWidgetProperty);
    }

    public View createContentView(Context context) {
        View view;
        if (this.mWidgetProperty == null) {
            return null;
        }
        try {
            view = (View) Class.forName("com.amap.bundle.drivecommon.widget.ExpandableIconView").getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException unused) {
            view = null;
        }
        return view;
    }
}
