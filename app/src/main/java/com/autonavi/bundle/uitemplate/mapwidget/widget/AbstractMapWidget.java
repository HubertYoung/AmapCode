package com.autonavi.bundle.uitemplate.mapwidget.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.LogVersionType;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractMapWidget<Presenter extends BaseMapWidgetPresenter> implements IMapWidget<Presenter> {
    protected View mContentView;
    public Presenter mPresenter;
    private int mWidgetLastVisible;
    public IWidgetProperty mWidgetProperty;

    public void combineWidgets(IMapWidget<? extends IMapWidgetPresenter>... iMapWidgetArr) {
    }

    public abstract View createContentView(Context context);

    public void enterImmersiveMode() {
    }

    public void exitImmersiveMode() {
    }

    /* access modifiers changed from: protected */
    public <T extends Presenter> T getCustomPresenter() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onInit(Context context) {
    }

    public void refreshState() {
    }

    public AbstractMapWidget(Context context, IWidgetProperty iWidgetProperty) {
        this.mWidgetLastVisible = -1;
        this.mWidgetProperty = iWidgetProperty;
        onCreateContentView(context);
        onCreatePresenter();
        onInit(context);
        initPresenter();
    }

    public AbstractMapWidget(Context context) {
        this(context, new WidgetProperty());
    }

    /* access modifiers changed from: protected */
    public void onCreateContentView(Context context) {
        this.mContentView = createContentView(context);
    }

    /* access modifiers changed from: protected */
    public final void onCreatePresenter() {
        createPresenter(getPresenterClass());
    }

    public void setVisibility(int i) {
        IWidgetProperty widgetProperty = getWidgetProperty();
        if (widgetProperty != null && this.mWidgetLastVisible != i) {
            this.mWidgetLastVisible = i;
            ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).setWidgetVisibleForType(widgetProperty.getWidgetType(), i);
        }
    }

    public int getVisibility() {
        IWidgetProperty widgetProperty = getWidgetProperty();
        if (widgetProperty != null) {
            return ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getWidgetVisibleForType(widgetProperty.getWidgetType());
        }
        return 8;
    }

    public IMapWidget<Presenter> setWidgetProperty(IWidgetProperty iWidgetProperty) {
        this.mWidgetProperty = iWidgetProperty;
        return this;
    }

    public IWidgetProperty getWidgetProperty() {
        return this.mWidgetProperty;
    }

    public Context getContext() {
        if (this.mContentView != null) {
            return this.mContentView.getContext();
        }
        return null;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public Presenter getPresenter() {
        return this.mPresenter;
    }

    public void createPresenter(Class<Presenter> cls) {
        if (cls != null) {
            try {
                this.mPresenter = (BaseMapWidgetPresenter) cls.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        } else {
            this.mPresenter = getCustomPresenter();
        }
    }

    private void initPresenter() {
        if (this.mPresenter != null) {
            this.mPresenter.initialize(this);
            this.mPresenter.initContext(getContext());
        }
    }

    public Class<Presenter> getPresenterClass() {
        try {
            return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public View loadLayoutRes(Context context, int i) {
        if (i > 0) {
            return LayoutInflater.from(context).inflate(i, null, false);
        }
        return null;
    }

    public String getLogVersionState() {
        IWidgetProperty widgetProperty = getWidgetProperty();
        return (widgetProperty == null || !widgetProperty.isLoadNewWidgetStyle()) ? LogVersionType.UN_REDESIGN : LogVersionType.REDESIGN;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IMapWidget)) {
            return false;
        }
        IWidgetProperty widgetProperty = ((IMapWidget) obj).getWidgetProperty();
        IWidgetProperty widgetProperty2 = getWidgetProperty();
        if (widgetProperty == null || widgetProperty2 == null) {
            return super.equals(obj);
        }
        return TextUtils.equals(widgetProperty.getWidgetType(), widgetProperty2.getWidgetType());
    }
}
