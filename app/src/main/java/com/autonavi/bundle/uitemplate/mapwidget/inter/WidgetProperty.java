package com.autonavi.bundle.uitemplate.mapwidget.inter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.ViewGroup.MarginLayoutParams;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;

public class WidgetProperty implements IWidgetProperty {
    private int alignType;
    private boolean enable;
    private int index;
    private boolean isLoadNewWidgetStyle;
    private MarginLayoutParams layoutParams;
    private String mCombinedTag;
    private IWidgetProperty[] mCombinedWidgets;
    private boolean mInterceptEventByAjx;
    private boolean mIsShowInImmersiveMode;
    private JsFunctionCallback mJsFunctionCallback;
    private String[] mWillBindPages;
    private MarginLayoutParams offsetParams;
    private int priority;
    private String widgetType;

    public WidgetProperty() {
        this.alignType = 1;
        this.priority = 0;
        this.layoutParams = new MarginLayoutParams(-2, -2);
        this.offsetParams = new MarginLayoutParams(-2, -2);
        this.enable = true;
        this.mInterceptEventByAjx = false;
        this.index = -1;
        this.mIsShowInImmersiveMode = false;
    }

    public WidgetProperty(int i, int i2, String str) {
        this.alignType = 1;
        this.priority = 0;
        this.layoutParams = new MarginLayoutParams(-2, -2);
        this.offsetParams = new MarginLayoutParams(-2, -2);
        this.enable = true;
        this.mInterceptEventByAjx = false;
        this.index = -1;
        this.mIsShowInImmersiveMode = false;
        this.alignType = i;
        this.priority = i2;
        this.widgetType = str;
    }

    public WidgetProperty(int i, int i2, String str, int i3) {
        this(i, i2, str);
        this.index = i3;
    }

    public WidgetProperty(int i, int i2, String str, MarginLayoutParams marginLayoutParams) {
        this(i, i2, str);
        this.layoutParams = marginLayoutParams;
    }

    public WidgetProperty(int i, int i2, String str, int i3, MarginLayoutParams marginLayoutParams) {
        this(i, i2, str, marginLayoutParams);
        this.index = i3;
    }

    public WidgetProperty(int i, int i2, String str, int i3, MarginLayoutParams marginLayoutParams, MarginLayoutParams marginLayoutParams2) {
        this(i, i2, str, i3, marginLayoutParams);
        this.offsetParams = marginLayoutParams2;
    }

    public WidgetProperty setAlignType(int i) {
        this.alignType = i;
        return this;
    }

    public WidgetProperty setPriority(int i) {
        this.priority = i;
        return this;
    }

    public WidgetProperty setLayoutParams(@NonNull MarginLayoutParams marginLayoutParams) {
        this.layoutParams = marginLayoutParams;
        return this;
    }

    public void setOffsetParams(@NonNull MarginLayoutParams marginLayoutParams) {
        this.offsetParams = marginLayoutParams;
    }

    public int getAlignType() {
        return this.alignType;
    }

    public int getPriority() {
        return this.priority;
    }

    public MarginLayoutParams getLayoutParams() {
        return this.layoutParams;
    }

    public MarginLayoutParams getOffsetParams() {
        return this.offsetParams;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public WidgetProperty setEnable(boolean z) {
        this.enable = z;
        return this;
    }

    public String getWidgetType() {
        if (isHaveCombineWidgets()) {
            return getCombineTag();
        }
        return this.widgetType;
    }

    public IWidgetProperty setWidgetType(String str) {
        this.widgetType = str;
        return this;
    }

    public int getIndex() {
        return this.index;
    }

    public IWidgetProperty setIndex(int i) {
        this.index = i;
        return this;
    }

    public boolean isLoadNewWidgetStyle() {
        return this.isLoadNewWidgetStyle;
    }

    public IWidgetProperty setWidgetStyle(boolean z) {
        this.isLoadNewWidgetStyle = z;
        return this;
    }

    public <T extends IWidgetProperty> IWidgetProperty setCombineWidgets(T[] tArr) {
        if (tArr != null && tArr.length > 1) {
            int length = tArr.length;
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = tArr[i].getWidgetType();
            }
            this.mCombinedTag = ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getCombineWidgetsTag(strArr);
            this.mCombinedWidgets = tArr;
        }
        return this;
    }

    public String getCombineTag() {
        return this.mCombinedTag;
    }

    public IWidgetProperty[] getCombineWidgetTypes() {
        return this.mCombinedWidgets;
    }

    public boolean isHaveCombineWidgets() {
        return this.mCombinedWidgets != null;
    }

    public JsFunctionCallback getJsFunctionCallback() {
        return this.mJsFunctionCallback;
    }

    public void setJsFunctionCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsFunctionCallback = jsFunctionCallback;
    }

    public boolean isInterceptEventByAjx() {
        return this.mInterceptEventByAjx;
    }

    public void setInterceptEventByAjx(boolean z) {
        this.mInterceptEventByAjx = z;
    }

    public boolean isWillBindToPage(String str) {
        if (this.mWillBindPages != null && !TextUtils.isEmpty(str)) {
            for (String equals : this.mWillBindPages) {
                if (TextUtils.equals(str, equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    public WidgetProperty setWillBindPages(String[] strArr) {
        this.mWillBindPages = strArr;
        return this;
    }

    public boolean isShowInImmersiveMode() {
        return this.mIsShowInImmersiveMode;
    }

    public WidgetProperty setInImmersiveModeVisible(boolean z) {
        this.mIsShowInImmersiveMode = z;
        return this;
    }
}
