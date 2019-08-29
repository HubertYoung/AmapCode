package com.autonavi.bundle.uitemplate.mapwidget.impl;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.MapWidgetContainer;
import com.autonavi.bundle.uitemplate.mapwidget.MapWidgetFactory;
import com.autonavi.bundle.uitemplate.mapwidget.OnFooterChangeListener;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetContainer;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetListener;
import com.autonavi.bundle.uitemplate.mapwidget.widget.combine.CombineMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.combine.CombineWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.IPage;
import com.autonavi.minimap.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import defpackage.bew;
import defpackage.bez;
import defpackage.bid;
import defpackage.euk;

public class MapWidgetManagerImpl implements IMapWidgetManager {
    private IMapWidgetContainer mContainer;
    private Map<String, IMapWidget<? extends IMapWidgetPresenter>> mCustomWidgetMap = new ConcurrentHashMap();
    private LinearLayout mHeaderLayout;
    private boolean mIsNewHomePage;
    private LinearLayout mMsgStateBarContainer;
    private OnFooterChangeListener mOnFooterChangeListener;
    private ViewGroup mRootContainer;
    private ViewGroup mRootView;
    private Map<String, IMapWidget<? extends IMapWidgetPresenter>> mWidgetMap = new ConcurrentHashMap();

    private String getAMapLogTag() {
        return "IMapWidgetManager";
    }

    public void initialize(ViewGroup viewGroup, LinearLayout linearLayout, ViewGroup viewGroup2) {
        this.mRootView = viewGroup;
        if (linearLayout != null && viewGroup2 != null) {
            this.mMsgStateBarContainer = linearLayout;
            this.mRootContainer = viewGroup2;
            View inflate = LayoutInflater.from(this.mRootContainer.getContext()).inflate(R.layout.map_widget_container_layout, null, false);
            if (inflate != null) {
                this.mRootContainer.addView(inflate, new LayoutParams(-1, -1));
                this.mContainer = (IMapWidgetContainer) inflate;
            }
            this.mHeaderLayout = new LinearLayout(getContext());
            this.mHeaderLayout.setOrientation(1);
            this.mRootContainer.addView(this.mHeaderLayout, 0, new LayoutParams(-1, -2));
            setFullScreen(false);
        }
    }

    public void setMsgStateBarView(View view, LinearLayout.LayoutParams layoutParams) {
        bez.a(getAMapLogTag(), "setMsgStateBarView", new bew[0]);
        if (view != null && this.mMsgStateBarContainer != null) {
            removeMsgStateBarView();
            if (layoutParams != null) {
                this.mMsgStateBarContainer.addView(view, layoutParams);
            } else {
                this.mMsgStateBarContainer.addView(view);
            }
            this.mMsgStateBarContainer.setVisibility(0);
        }
    }

    public void removeMsgStateBarView() {
        bez.a(getAMapLogTag(), "removeMsgStateBarView", new bew[0]);
        if (this.mMsgStateBarContainer != null) {
            this.mMsgStateBarContainer.removeAllViews();
            this.mMsgStateBarContainer.setVisibility(8);
        }
    }

    public ViewGroup getRootView() {
        return this.mRootView;
    }

    public void setOnFooterChangeListener(OnFooterChangeListener onFooterChangeListener) {
        this.mOnFooterChangeListener = onFooterChangeListener;
    }

    public void requestContainerLayout() {
        if (this.mContainer != null) {
            this.mContainer.requestContainerLayout();
        }
    }

    public Rect getContainerArea(boolean z) {
        if (this.mContainer != null) {
            return this.mContainer.getContainerArea(z);
        }
        return new Rect();
    }

    public IMapWidget findWidgetByWidgetType(String str) {
        if (this.mContainer != null) {
            return this.mContainer.findWidgetByWidgetType(str);
        }
        return null;
    }

    public void existImmersiveMode() {
        if (this.mContainer != null) {
            this.mContainer.existImmersiveMode();
        }
    }

    public void setHeaderView(View view, LinearLayout.LayoutParams layoutParams) {
        if (!(view == null || this.mHeaderLayout == null)) {
            removeHeaderView();
            if (layoutParams != null) {
                this.mHeaderLayout.addView(view, layoutParams);
                return;
            }
            this.mHeaderLayout.addView(view);
        }
    }

    public void removeHeaderView() {
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.removeAllViews();
        }
    }

    public void setFullScreen(boolean z) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mRootContainer.getLayoutParams();
        if (z) {
            marginLayoutParams.topMargin = 0;
        } else {
            marginLayoutParams.topMargin = euk.a(getContext());
        }
        this.mRootContainer.setLayoutParams(marginLayoutParams);
    }

    public void setHeaderVisibility(int i) {
        if (this.mHeaderLayout != null && this.mHeaderLayout.getVisibility() != i) {
            this.mHeaderLayout.setVisibility(i);
        }
    }

    public void setABHomePage(boolean z) {
        this.mIsNewHomePage = z;
    }

    public boolean isNewHomePage() {
        return this.mIsNewHomePage;
    }

    private void initCache() {
        if (this.mWidgetMap == null) {
            this.mWidgetMap = new ConcurrentHashMap();
        }
    }

    private IMapWidget createCommonWidget(IWidgetProperty iWidgetProperty) {
        if (iWidgetProperty == null) {
            return null;
        }
        initCache();
        IMapWidget<? extends IMapWidgetPresenter> widgetFromCache = getWidgetFromCache(iWidgetProperty.getWidgetType());
        boolean z = false;
        if (widgetFromCache == null) {
            widgetFromCache = createWidgetByFactory(iWidgetProperty);
            z = true;
        }
        if (widgetFromCache != null) {
            if (z) {
                combineWidget(widgetFromCache, iWidgetProperty);
            } else {
                widgetFromCache.setWidgetProperty(iWidgetProperty);
                widgetFromCache.refreshState();
            }
        }
        return widgetFromCache;
    }

    private IMapWidget<? extends IMapWidgetPresenter> getWidgetFromCache(String str) {
        if (this.mWidgetMap == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mWidgetMap.get(str);
    }

    private IMapWidget createWidgetByFactory(IWidgetProperty iWidgetProperty) {
        if (iWidgetProperty != null) {
            iWidgetProperty.setWidgetStyle(this.mIsNewHomePage);
        }
        IMapWidget createInstance = MapWidgetFactory.createInstance(getContext(), iWidgetProperty);
        if (createInstance != null) {
            this.mWidgetMap.put(iWidgetProperty.getWidgetType(), createInstance);
        }
        return createInstance;
    }

    public int addWidget(IMapWidget... iMapWidgetArr) {
        if (this.mCustomWidgetMap == null) {
            this.mCustomWidgetMap = new ConcurrentHashMap();
        }
        if (this.mContainer != null) {
            if (iMapWidgetArr == null || iMapWidgetArr.length <= 0) {
                bez.b(getAMapLogTag(), "addWidget by Object, widget is null", new bew[0]);
            } else {
                int i = -1;
                for (IMapWidget iMapWidget : iMapWidgetArr) {
                    String widgetType = iMapWidget.getWidgetProperty() == null ? null : iMapWidget.getWidgetProperty().getWidgetType();
                    if (!this.mCustomWidgetMap.containsKey(widgetType)) {
                        i = addWidget(iMapWidget, -1);
                        this.mCustomWidgetMap.put(widgetType, iMapWidget);
                        bez.b(getAMapLogTag(), "addWidget by Object,", new bew("widget", widgetType));
                    }
                }
                return i;
            }
        }
        return -1;
    }

    private boolean isMapEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public int addWidget(IWidgetProperty... iWidgetPropertyArr) {
        bez.b(getAMapLogTag(), "addWidget by IWidgetProperty", new bew[0]);
        int i = -1;
        if (iWidgetPropertyArr != null && iWidgetPropertyArr.length > 0) {
            for (IWidgetProperty iWidgetProperty : iWidgetPropertyArr) {
                i = addWidget(createCommonWidget(iWidgetProperty), iWidgetProperty.getIndex());
            }
        }
        return i;
    }

    public int addWidget(IWidgetProperty iWidgetProperty, int i) {
        if (iWidgetProperty != null) {
            return addWidget(createCommonWidget(iWidgetProperty), i);
        }
        return -1;
    }

    public int addWidget(IMapWidget iMapWidget, int i) {
        if (this.mContainer == null || iMapWidget == null) {
            return -1;
        }
        int addWidget = this.mContainer.addWidget(iMapWidget, i);
        if (iMapWidget.getWidgetProperty().getAlignType() == 10) {
            notifyWidgetChangedForFooter(true);
        }
        return addWidget;
    }

    private void notifyWidgetChangedForFooter(boolean z) {
        if (this.mOnFooterChangeListener != null) {
            this.mOnFooterChangeListener.onFooterChange(z);
        }
    }

    public void setContainerMargin(int i, int i2, int i3, int i4) {
        if (this.mContainer != null) {
            bez.a(getAMapLogTag(), "setContainerMargin", new bew("left", Integer.valueOf(i)), new bew("top", Integer.valueOf(i2)), new bew("right", Integer.valueOf(i3)), new bew("bottom", Integer.valueOf(i4)));
            this.mContainer.setContainerMargin(i, i2, i3, i4);
        }
    }

    public MarginLayoutParams getContainerMargin() {
        if (this.mContainer != null) {
            return this.mContainer.getContainerMargin();
        }
        return null;
    }

    public void setContainerBottomMargin(int i, boolean z) {
        if (this.mContainer != null) {
            bez.a(getAMapLogTag(), "setContainerBottomMargin", new bew("bottom", Integer.valueOf(i)), new bew("isAnim", Boolean.valueOf(z)));
            this.mContainer.setContainerBottomMargin(i, z);
        }
    }

    public void setContainerTopMargin(int i, boolean z) {
        if (this.mContainer != null) {
            bez.a(getAMapLogTag(), "setContainerTopMargin", new bew("top", Integer.valueOf(i)), new bew("isAnim", Boolean.valueOf(z)));
            this.mContainer.setContainerTopMargin(i, z);
        }
    }

    public void setContainerPadding(int i, int i2, int i3, int i4) {
        if (this.mContainer != null) {
            bez.a(getAMapLogTag(), "setContainerPadding", new bew("left", Integer.valueOf(i)), new bew("top", Integer.valueOf(i2)), new bew("right", Integer.valueOf(i3)), new bew("bottom", Integer.valueOf(i4)));
            this.mContainer.setContainerPadding(i, i2, i3, i4);
        }
    }

    public void setContainerAlpha(float f) {
        if (this.mContainer != null) {
            this.mContainer.setContainerAlpha(f);
        }
    }

    public float getContainerAlpha() {
        if (this.mContainer != null) {
            return this.mContainer.getContainerAlpha();
        }
        return 1.0f;
    }

    public void setWidgetsVisibility(boolean z) {
        if (this.mContainer != null) {
            this.mContainer.setWidgetsVisibility(z);
        }
    }

    public void removeWidget(IWidgetProperty... iWidgetPropertyArr) {
        if (iWidgetPropertyArr != null && iWidgetPropertyArr.length > 0 && this.mWidgetMap != null) {
            for (IWidgetProperty widgetType : iWidgetPropertyArr) {
                removeWidget(widgetType.getWidgetType());
            }
        }
    }

    public void removeWidget(String str) {
        removeWidget((IMapWidget) getWidgetFromCache(str));
    }

    public void removeAllWidget() {
        if (!isMapEmpty(this.mWidgetMap)) {
            this.mWidgetMap.clear();
            bez.a(getAMapLogTag(), "removeAllWidget common clear cache", new bew[0]);
        }
        if (!isMapEmpty(this.mCustomWidgetMap)) {
            this.mCustomWidgetMap.clear();
            bez.a(getAMapLogTag(), "removeAllWidget custom clear cache", new bew[0]);
        }
        if (this.mContainer != null) {
            this.mContainer.removeAllWidget();
            notifyWidgetChangedForFooter(false);
            bez.a(getAMapLogTag(), "removeAllWidget clear container", new bew[0]);
        }
    }

    public void removeWidget(IMapWidget iMapWidget) {
        String str;
        if (iMapWidget != null) {
            if (this.mContainer != null) {
                String aMapLogTag = getAMapLogTag();
                bew[] bewArr = new bew[1];
                if (iMapWidget.getWidgetProperty() == null) {
                    str = "null";
                } else {
                    str = iMapWidget.getWidgetProperty().getWidgetType();
                }
                bewArr[0] = new bew("widget", str);
                bez.a(aMapLogTag, "removeWidget", bewArr);
                this.mContainer.removeWidget(iMapWidget);
                if (iMapWidget != null && iMapWidget.getWidgetProperty().getAlignType() == 10) {
                    notifyWidgetChangedForFooter(false);
                }
            }
            if (!isMapEmpty(this.mCustomWidgetMap)) {
                String widgetType = iMapWidget.getWidgetProperty().getWidgetType();
                for (String equals : this.mCustomWidgetMap.keySet()) {
                    if (TextUtils.equals(equals, widgetType)) {
                        this.mCustomWidgetMap.remove(widgetType);
                    }
                }
            }
        }
    }

    public void setWidget(IWidgetProperty... iWidgetPropertyArr) {
        setWidget(null, iWidgetPropertyArr);
    }

    public void setWidget( bid bid, IWidgetProperty... iWidgetPropertyArr) {
        IWidgetProperty iWidgetProperty;
        if (iWidgetPropertyArr == null && bid == null) {
            bez.a(getAMapLogTag(), "setWidget widgets: null", new bew[0]);
            return;
        }
        if (this.mContainer != null) {
            StringBuilder sb = new StringBuilder("setWidget widgets:");
            List synchronizedList = Collections.synchronizedList(new ArrayList());
            if (iWidgetPropertyArr != null && iWidgetPropertyArr.length > 0) {
                for (IWidgetProperty iWidgetProperty2 : iWidgetPropertyArr) {
                    IMapWidget createCommonWidget = createCommonWidget(iWidgetProperty2);
                    if (createCommonWidget != null) {
                        sb.append(iWidgetProperty2.getWidgetType());
                        sb.append(Token.SEPARATOR);
                        synchronizedList.add(createCommonWidget);
                    }
                }
            }
            if (bid != null && !isMapEmpty(this.mCustomWidgetMap)) {
                for (String str : this.mCustomWidgetMap.keySet()) {
                    IMapWidget iMapWidget = this.mCustomWidgetMap.get(str);
                    if (iMapWidget == null) {
                        iWidgetProperty = null;
                    } else {
                        iWidgetProperty = iMapWidget.getWidgetProperty();
                    }
                    if (iWidgetProperty != null && iWidgetProperty.isWillBindToPage(bid.getClass().getSimpleName())) {
                        sb.append(iWidgetProperty.getWidgetType());
                        sb.append(Token.SEPARATOR);
                        synchronizedList.add(iMapWidget);
                    }
                }
            }
            if (iWidgetPropertyArr != null || (synchronizedList != null && synchronizedList.size() > 0)) {
                this.mContainer.setWidgets(synchronizedList);
            }
            bez.a(getAMapLogTag(), sb.toString(), new bew[0]);
        }
    }

    private void combineWidget(IMapWidget iMapWidget, IWidgetProperty iWidgetProperty) {
        if (iWidgetProperty != null && iWidgetProperty.isHaveCombineWidgets()) {
            iMapWidget.combineWidgets(getCombineWidgets(iWidgetProperty.getCombineWidgetTypes()));
        }
    }

    private IMapWidget<? extends IMapWidgetPresenter>[] getCombineWidgets(IWidgetProperty[] iWidgetPropertyArr) {
        if (iWidgetPropertyArr == null || iWidgetPropertyArr.length <= 0) {
            return null;
        }
        int length = iWidgetPropertyArr.length;
        IMapWidget<? extends IMapWidgetPresenter>[] iMapWidgetArr = new IMapWidget[length];
        for (int i = 0; i < length; i++) {
            IWidgetProperty iWidgetProperty = iWidgetPropertyArr[i];
            iWidgetProperty.setWidgetStyle(this.mIsNewHomePage);
            iMapWidgetArr[i] = MapWidgetFactory.createInstance(getContext(), iWidgetProperty);
        }
        return iMapWidgetArr;
    }

    public void setWidget(IMapWidget... iMapWidgetArr) {
        if (this.mContainer != null) {
            List synchronizedList = Collections.synchronizedList(new ArrayList());
            if (iMapWidgetArr != null && iMapWidgetArr.length > 0) {
                for (IMapWidget add : iMapWidgetArr) {
                    synchronizedList.add(add);
                }
            }
            this.mContainer.setWidgets(synchronizedList);
            removeHeaderView();
        }
    }

    public void setWidgetVisibleForType(String str, int i) {
        if (this.mWidgetMap != null && this.mWidgetMap.size() > 0 && this.mContainer != null) {
            this.mContainer.setWidgetVisible(this.mWidgetMap.get(str), i);
        }
    }

    public int getWidgetVisibleForType(String str) {
        if (this.mWidgetMap == null || this.mWidgetMap.size() <= 0 || this.mContainer == null) {
            return 8;
        }
        return this.mContainer.getWidgetVisible(this.mWidgetMap.get(str));
    }

    public <T extends IPage> void registerListener(String str, WidgetListener<T> widgetListener) {
        dealWithListener(true, str, null, widgetListener);
    }

    public <T extends IPage> void registerListener(String str, String str2, WidgetListener<T> widgetListener) {
        dealWithListener(true, str, str2, widgetListener);
    }

    public <T extends IPage> void unregisterListener(String str, WidgetListener<T> widgetListener) {
        dealWithListener(false, str, null, widgetListener);
    }

    public <T extends IPage> void unregisterListener(String str, String str2, WidgetListener<T> widgetListener) {
        dealWithListener(false, str, str2, widgetListener);
    }

    public String getCombineWidgetsTag(String... strArr) {
        return Stub.getCombineWidgetsTag(strArr);
    }

    private <T extends IPage> void dealWithListener(boolean z, String str, String str2, WidgetListener<T> widgetListener) {
        IMapWidget<? extends IMapWidgetPresenter> iMapWidget;
        boolean z2;
        if (TextUtils.isEmpty(str2)) {
            iMapWidget = getWidgetFromCache(str);
            z2 = false;
        } else {
            iMapWidget = getWidgetFromCache(str2);
            z2 = true;
        }
        if (iMapWidget != null) {
            IMapWidgetPresenter iMapWidgetPresenter = (!z2 || !(iMapWidget instanceof CombineMapWidget)) ? iMapWidget == null ? null : iMapWidget.getPresenter() : ((CombineWidgetPresenter) ((CombineMapWidget) iMapWidget).getPresenter()).getCombinedPresenter(str);
            if (iMapWidgetPresenter != null) {
                if (z) {
                    iMapWidgetPresenter.addListener(widgetListener);
                    return;
                }
                iMapWidgetPresenter.removeListener(widgetListener);
            }
        }
    }

    public <T extends IMapWidgetPresenter> T getPresenter(String... strArr) {
        String str = strArr != null ? strArr.length == 1 ? strArr[0] : getCombineWidgetsTag(strArr) : null;
        if (!TextUtils.isEmpty(str)) {
            IMapWidget<? extends IMapWidgetPresenter> widgetFromCache = getWidgetFromCache(str);
            if (widgetFromCache != null) {
                return widgetFromCache.getPresenter();
            }
        }
        return null;
    }

    public Context getContext() {
        if (this.mContainer != null) {
            return this.mContainer.getContainerContext();
        }
        return null;
    }

    public void setContainerVisible(int i) {
        if (this.mContainer != null && (this.mContainer instanceof MapWidgetContainer)) {
            ((MapWidgetContainer) this.mContainer).setVisibility(i);
        }
    }

    public int getContainerVisible() {
        if (this.mContainer == null || !(this.mContainer instanceof MapWidgetContainer)) {
            return -1;
        }
        return ((MapWidgetContainer) this.mContainer).getVisibility();
    }

    public void isWidgetsDispatchEvent(boolean z, IWidgetProperty... iWidgetPropertyArr) {
        if (iWidgetPropertyArr == null || iWidgetPropertyArr.length <= 0) {
            if (this.mWidgetMap != null && this.mWidgetMap.size() > 0) {
                for (String widgetEventDispatchFlag : this.mWidgetMap.keySet()) {
                    setWidgetEventDispatchFlag(widgetEventDispatchFlag, z);
                }
            }
            return;
        }
        for (IWidgetProperty widgetType : iWidgetPropertyArr) {
            setWidgetEventDispatchFlag(widgetType.getWidgetType(), z);
        }
    }

    private void setWidgetEventDispatchFlag(String str, boolean z) {
        IMapWidgetPresenter presenter = Stub.getMapWidgetManager().getPresenter(str);
        if (presenter != null) {
            presenter.isNeedDispatchEvent(z);
        }
    }

    public void enterImmersiveMode(String... strArr) {
        enterImmersiveMode(null, strArr);
    }

    public void enterImmersiveMode(Rect rect, String... strArr) {
        String str;
        String aMapLogTag = getAMapLogTag();
        bew[] bewArr = new bew[1];
        if (strArr == null) {
            str = "null";
        } else {
            str = strArr.toString();
        }
        bewArr[0] = new bew("holdTypes", str);
        bez.a(aMapLogTag, "enterImmersiveMode", bewArr);
        if (this.mContainer != null) {
            ArrayList arrayList = new ArrayList();
            List<IMapWidget> doCustomWidgetsImmersiveAction = doCustomWidgetsImmersiveAction(true);
            if (doCustomWidgetsImmersiveAction != null && doCustomWidgetsImmersiveAction.size() > 0) {
                arrayList.addAll(doCustomWidgetsImmersiveAction);
            }
            if (strArr != null && strArr.length != 0) {
                for (String widgetFromCache : strArr) {
                    IMapWidget<? extends IMapWidgetPresenter> widgetFromCache2 = getWidgetFromCache(widgetFromCache);
                    if (widgetFromCache2 != null) {
                        widgetFromCache2.enterImmersiveMode();
                        arrayList.add(widgetFromCache2);
                    }
                }
            } else if (arrayList.size() == 0) {
                this.mContainer.enterImmersiveMode(new IMapWidget[0]);
            }
            IMapWidget[] iMapWidgetArr = new IMapWidget[arrayList.size()];
            arrayList.toArray(iMapWidgetArr);
            if (rect == null) {
                this.mContainer.enterImmersiveMode(iMapWidgetArr);
                return;
            }
            this.mContainer.enterImmersiveMode(rect, iMapWidgetArr);
        }
    }

    public void existImmersiveMode(String... strArr) {
        String str;
        String aMapLogTag = getAMapLogTag();
        bew[] bewArr = new bew[1];
        if (strArr == null) {
            str = "null";
        } else {
            str = strArr.toString();
        }
        bewArr[0] = new bew("widgets", str);
        bez.a(aMapLogTag, "existImmersiveMode", bewArr);
        if (this.mContainer != null) {
            if (strArr != null && strArr.length > 0) {
                for (String widgetFromCache : strArr) {
                    IMapWidget<? extends IMapWidgetPresenter> widgetFromCache2 = getWidgetFromCache(widgetFromCache);
                    if (widgetFromCache2 != null) {
                        widgetFromCache2.exitImmersiveMode();
                    }
                }
            }
            doCustomWidgetsImmersiveAction(false);
            this.mContainer.existImmersiveMode();
        }
    }

    private List<IMapWidget> doCustomWidgetsImmersiveAction(boolean z) {
        List<IMapWidget> list = null;
        if (!isMapEmpty(this.mCustomWidgetMap)) {
            if (z) {
                list = new ArrayList<>();
            }
            for (String str : this.mCustomWidgetMap.keySet()) {
                IMapWidget iMapWidget = this.mCustomWidgetMap.get(str);
                IWidgetProperty widgetProperty = iMapWidget.getWidgetProperty();
                if (widgetProperty != null && widgetProperty.isShowInImmersiveMode()) {
                    if (z) {
                        iMapWidget.enterImmersiveMode();
                        list.add(iMapWidget);
                    } else {
                        iMapWidget.exitImmersiveMode();
                    }
                }
            }
        }
        return list;
    }

    public void onPageStart(bid bid) {
        doHostEvent("onPageStart", bid.class, bid);
    }

    public void onPageCreated(bid bid) {
        bez.a(getAMapLogTag(), "onPageCreated", new bew(DictionaryKeys.V2_PAGENAME, getPageName(bid)));
        doHostEvent("onPageCreated", bid.class, bid);
    }

    public void onPageResume(bid bid) {
        bez.a(getAMapLogTag(), "onPageResume", new bew(DictionaryKeys.V2_PAGENAME, getPageName(bid)));
        doHostEvent("onPageResume", bid.class, bid);
    }

    public void onPagePause(bid bid) {
        doHostEvent("onPagePause", bid.class, bid);
    }

    public void onPageStop(bid bid) {
        doHostEvent("onPageStop", bid.class, bid);
    }

    public void onPageDestroy(bid bid) {
        bez.a(getAMapLogTag(), "onPageDestroy", new bew(DictionaryKeys.V2_PAGENAME, getPageName(bid)));
        doHostEvent("onPageDestroy", bid.class, bid);
    }

    public void onForeground() {
        bez.a(getAMapLogTag(), "onForeground", new bew[0]);
        doHostEvent("onForeground");
    }

    public void onBackground() {
        bez.a(getAMapLogTag(), "onBackground", new bew[0]);
        doHostEvent("onBackground");
    }

    private void doHostEvent(String str) {
        doHostEvent(str, null, new Object[0]);
    }

    private void doHostEvent(String str, Class<?> cls, Object... objArr) {
        Method method;
        try {
            if (this.mWidgetMap != null && !TextUtils.isEmpty(str)) {
                for (String str2 : this.mWidgetMap.keySet()) {
                    IMapWidgetPresenter presenter = getPresenter(str2);
                    if (presenter != null) {
                        if (cls == null) {
                            method = presenter.getClass().getMethod(str, new Class[0]);
                        } else {
                            method = presenter.getClass().getMethod(str, new Class[]{cls});
                        }
                        if (method != null) {
                            method.invoke(presenter, objArr);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPageName(bid bid) {
        return bid == null ? "null" : bid.getClass().getSimpleName();
    }
}
