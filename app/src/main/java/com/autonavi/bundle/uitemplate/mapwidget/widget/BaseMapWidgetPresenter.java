package com.autonavi.bundle.uitemplate.mapwidget.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.impl.MapWidgetHost;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IEventDelegate;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetListener;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.map.fragmentcontainer.page.IPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseMapWidgetPresenter<Widget extends IMapWidget> extends MapWidgetHost implements IMapWidgetPresenter<Widget> {
    public Widget mBindWidget;
    private IEventDelegate mEventDelegate;
    /* access modifiers changed from: private */
    public boolean mIsNeedDispatchEvent = true;
    protected List<Integer> mListenerTypeList = Collections.synchronizedList(new ArrayList());
    private OnClickListener mWidgetClickListener = new OnClickListener() {
        public void onClick(View view) {
            int i;
            boolean z = false;
            if (!BaseMapWidgetPresenter.this.mIsNeedDispatchEvent) {
                bez.a(BaseMapWidgetPresenter.this.getAMapLogTag(), "widget  onClick (mIsNeedDispatchEvent=false)", new bew[0]);
                return;
            }
            bez.a(BaseMapWidgetPresenter.this.getAMapLogTag(), "widget  onClick (mIsNeedDispatchEvent=true)", new bew("id", Integer.valueOf(view.getId())));
            if (BaseMapWidgetPresenter.this.mWidgetListenerList != null && BaseMapWidgetPresenter.this.mWidgetListenerList.size() > 0) {
                Iterator<WidgetListener<? extends IPage>> it = BaseMapWidgetPresenter.this.mWidgetListenerList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    WidgetListener next = it.next();
                    if (BaseMapWidgetPresenter.this.isDispatchListener(next)) {
                        BaseMapWidgetPresenter.this.commonListener(view);
                        next.onClick(view, BaseMapWidgetPresenter.this.getWidgetType());
                        z = true;
                        break;
                    }
                }
            }
            if (!z) {
                BaseMapWidgetPresenter.this.commonListener(view);
                if (!BaseMapWidgetPresenter.this.isDoEventByAjx()) {
                    BaseMapWidgetPresenter.this.internalClickListener(view);
                }
            }
            if (view != null) {
                Object tag = view.getTag(R.id.map_widget_event_index);
                if (tag == null) {
                    i = -1;
                } else {
                    i = ((Integer) tag).intValue();
                }
                if (-1 != i) {
                    BaseMapWidgetPresenter.this.dispatchAJXEventIfNeeded(i, view);
                }
            }
        }
    };
    protected List<WidgetListener<? extends IPage>> mWidgetListenerList = Collections.synchronizedList(new ArrayList());
    private OnTouchListener mWidgetTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (!BaseMapWidgetPresenter.this.mIsNeedDispatchEvent) {
                bez.a(BaseMapWidgetPresenter.this.getAMapLogTag(), "widget  onTouch (mIsNeedDispatchEvent=false)", new bew[0]);
                return false;
            }
            bez.a(BaseMapWidgetPresenter.this.getAMapLogTag(), "widget  onTouch (mIsNeedDispatchEvent=true)", new bew("id", Integer.valueOf(view.getId())));
            if (BaseMapWidgetPresenter.this.mWidgetListenerList != null && BaseMapWidgetPresenter.this.mWidgetListenerList.size() > 0) {
                for (WidgetListener next : BaseMapWidgetPresenter.this.mWidgetListenerList) {
                    if (BaseMapWidgetPresenter.this.isDispatchListener(next)) {
                        BaseMapWidgetPresenter.this.commonListener(view);
                        return next.onTouch(view, motionEvent, BaseMapWidgetPresenter.this.getWidgetType());
                    }
                }
            }
            BaseMapWidgetPresenter.this.commonListener(view);
            return BaseMapWidgetPresenter.this.internalOnTouchListener(view, motionEvent);
        }
    };

    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WidgetEventIndex {
        public static final int WIDGET_INDEX_MAIN = 0;
        public static final int WIDGET_INDEX_NO_SET = -1;
        public static final int WIDGET_INDEX_SUB_1 = 1;
        public static final int WIDGET_INDEX_SUB_2 = 2;
        public static final int WIDGET_INDEX_SUB_3 = 3;
    }

    public void commonListener(View view) {
    }

    public void initContext(Context context) {
    }

    public void internalClickListener(View view) {
    }

    public boolean internalOnTouchListener(View view, MotionEvent motionEvent) {
        return false;
    }

    public void unInitialize() {
    }

    public void initialize(Widget widget) {
        this.mBindWidget = widget;
        this.mListenerTypeList.add(Integer.valueOf(0));
        bindListener();
    }

    public Widget getWidget() {
        return this.mBindWidget;
    }

    public final boolean isWidgetNotNull() {
        return this.mBindWidget != null;
    }

    /* access modifiers changed from: protected */
    public void bindListener() {
        onBindListener(new View[0]);
    }

    public void addListenerType(int... iArr) {
        if (this.mListenerTypeList != null && iArr != null && iArr.length > 0) {
            for (int i : iArr) {
                if (!this.mListenerTypeList.contains(Integer.valueOf(i))) {
                    this.mListenerTypeList.add(Integer.valueOf(i));
                    bez.a(getAMapLogTag(), "addListenerType", new bew("type", Integer.valueOf(i)));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void onBindListener(View... viewArr) {
        if (viewArr == null || viewArr.length <= 0) {
            View contentView = this.mBindWidget == null ? null : this.mBindWidget.getContentView();
            setWidgetEventIndex(contentView, 0);
            setListener(contentView, false);
            return;
        }
        for (View listener : viewArr) {
            setListener(listener, false);
        }
        bez.a(getAMapLogTag(), "onBindListener child view", new bew(NewHtcHomeBadger.COUNT, Integer.valueOf(viewArr.length)));
    }

    /* access modifiers changed from: protected */
    public final void setWidgetEventIndex(View view, int i) {
        if (view != null) {
            view.setTag(R.id.map_widget_event_index, Integer.valueOf(i));
        }
    }

    /* access modifiers changed from: protected */
    public final void unBindListener(View... viewArr) {
        if (viewArr != null && viewArr.length > 0) {
            for (View listener : viewArr) {
                setListener(listener, true);
            }
        }
    }

    private void setListener(View view, boolean z) {
        if (view != null && this.mListenerTypeList != null && this.mListenerTypeList.size() != 0 && !this.mListenerTypeList.contains(Integer.valueOf(-1))) {
            for (Integer intValue : this.mListenerTypeList) {
                switch (intValue.intValue()) {
                    case 0:
                        if (z) {
                            view.setOnClickListener(null);
                            break;
                        } else {
                            view.setOnClickListener(this.mWidgetClickListener);
                            break;
                        }
                    case 1:
                        if (z) {
                            view.setOnTouchListener(null);
                            break;
                        } else {
                            view.setOnTouchListener(this.mWidgetTouchListener);
                            break;
                        }
                }
            }
        }
    }

    public void isNeedDispatchEvent(boolean z) {
        this.mIsNeedDispatchEvent = z;
    }

    public <T extends IPage> void addListener(WidgetListener<T> widgetListener) {
        if (widgetListener != null && !this.mWidgetListenerList.contains(widgetListener)) {
            this.mWidgetListenerList.add(widgetListener);
        }
    }

    public <T extends IPage> void removeListener(WidgetListener<T> widgetListener) {
        if (widgetListener != null && this.mWidgetListenerList.contains(widgetListener)) {
            this.mWidgetListenerList.remove(widgetListener);
        }
    }

    public void removeAllListener() {
        if (this.mWidgetListenerList != null && this.mWidgetListenerList.size() > 0) {
            this.mWidgetListenerList.clear();
        }
    }

    public final void onPageDestroy(bid bid) {
        super.onPageDestroy(bid);
        WidgetListener searchWidgetListenerByPage = searchWidgetListenerByPage(bid);
        bez.a(getAMapLogTag(), "onPageDestroy", new bew("will be remove listener", searchWidgetListenerByPage));
        if (searchWidgetListenerByPage != null && this.mWidgetListenerList != null) {
            bez.a(getAMapLogTag(), "onPageDestroy remove", new bew("listenername", searchWidgetListenerByPage.getPageClass().getName()));
            this.mWidgetListenerList.remove(searchWidgetListenerByPage);
        }
    }

    private WidgetListener searchWidgetListenerByPage(bid bid) {
        if (!(this.mWidgetListenerList == null || this.mWidgetListenerList.size() <= 0 || bid == null)) {
            for (WidgetListener next : this.mWidgetListenerList) {
                bez.a(getAMapLogTag(), "searchWidgetListenerByPage", new bew(Token.SEPARATOR, next.getClass().getSimpleName()));
                if (next != null && next.getPageClass() != null && TextUtils.equals(bid.getClass().getSimpleName(), next.getPageClass().getSimpleName())) {
                    return next;
                }
            }
        }
        return null;
    }

    public <Delegate extends IEventDelegate> Delegate getEventDelegate() {
        String str;
        String aMapLogTag = getAMapLogTag();
        bew[] bewArr = new bew[1];
        if (this.mEventDelegate == null) {
            str = "null";
        } else {
            str = this.mEventDelegate.getClass().getSimpleName();
        }
        bewArr[0] = new bew("eventDelegate", str);
        bez.a(aMapLogTag, "getEventDelegate", bewArr);
        return this.mEventDelegate;
    }

    public <Delegate extends IEventDelegate> void setEventDelegate(Delegate delegate) {
        String str;
        this.mEventDelegate = delegate;
        String aMapLogTag = getAMapLogTag();
        bew[] bewArr = new bew[1];
        if (delegate == null) {
            str = "null";
        } else {
            str = delegate.getClass().getSimpleName();
        }
        bewArr[0] = new bew("eventDelegate", str);
        bez.a(aMapLogTag, "setEventDelegate", bewArr);
    }

    /* access modifiers changed from: private */
    public boolean isDispatchListener(WidgetListener<? extends IPage> widgetListener) {
        return this.mIsNeedDispatchEvent && widgetListener != null && pageIsForeground(widgetListener.getPageClass());
    }

    private boolean pageIsForeground(Class<? extends IPage> cls) {
        String str;
        String aMapLogTag = getAMapLogTag();
        bew[] bewArr = new bew[2];
        bewArr[0] = new bew("page", this.mCurPageClassName);
        if (cls == null) {
            str = "null";
        } else {
            str = cls.getSimpleName();
        }
        bewArr[1] = new bew("curPage", str);
        bez.a(aMapLogTag, "MapWidgetHost foreground page", bewArr);
        if (cls == null || !cls.getSimpleName().equals(this.mCurPageClassName)) {
            bez.a(getAMapLogTag(), "cur page is not  Foreground", new bew[0]);
            return false;
        }
        bez.a(getAMapLogTag(), "cur page is Foreground", new bew[0]);
        return true;
    }

    /* access modifiers changed from: private */
    public boolean isDoEventByAjx() {
        IWidgetProperty widgetProperty = this.mBindWidget.getWidgetProperty();
        if (widgetProperty == null || !(widgetProperty instanceof WidgetProperty)) {
            return false;
        }
        return ((WidgetProperty) widgetProperty).isInterceptEventByAjx();
    }

    /* access modifiers changed from: protected */
    public void dispatchAJXEventIfNeeded(int i, View view) {
        if (this.mBindWidget != null && this.mBindWidget.getWidgetProperty() != null) {
            JsFunctionCallback jsFunctionCallback = this.mBindWidget.getWidgetProperty().getJsFunctionCallback();
            if (jsFunctionCallback != null && !TextUtils.isEmpty(this.mBindWidget.getWidgetProperty().getWidgetType())) {
                int[] iArr = new int[2];
                view.getLocationInWindow(iArr);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("event", "onClick");
                    jSONObject.put("widgetTag", this.mBindWidget.getWidgetProperty().getWidgetType());
                    jSONObject.put("itemTag", this.mBindWidget.getWidgetProperty().getWidgetType());
                    jSONObject.put("top", (double) DimensionUtils.pixelToStandardUnit((float) iArr[1]));
                    jSONObject.put("bottom", (double) DimensionUtils.pixelToStandardUnit((float) (iArr[1] + view.getMeasuredHeight())));
                    jSONObject.put("left", (double) DimensionUtils.pixelToStandardUnit((float) iArr[0]));
                    jSONObject.put("right", (double) DimensionUtils.pixelToStandardUnit((float) (iArr[0] + view.getMeasuredWidth())));
                } catch (JSONException unused) {
                }
                jsFunctionCallback.callback(jSONObject.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean isAddToWidgetContainer() {
        if (!isWidgetNotNull()) {
            return false;
        }
        IMapWidgetManagerService iMapWidgetManagerService = (IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class);
        String widgetType = this.mBindWidget.getWidgetProperty() == null ? "" : this.mBindWidget.getWidgetProperty().getWidgetType();
        if (iMapWidgetManagerService == null || iMapWidgetManagerService.findWidgetByWidgetType(widgetType) == null) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public String getWidgetType() {
        return this.mBindWidget == null ? "" : this.mBindWidget.getWidgetProperty().getWidgetType();
    }

    /* access modifiers changed from: protected */
    public String getAMapLogTag() {
        String widgetType = getWidgetType();
        return TextUtils.isEmpty(widgetType) ? getClass().getSimpleName() : widgetType;
    }

    /* access modifiers changed from: protected */
    public JSONObject getLogVersionStateParam() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", this.mBindWidget.getLogVersionState());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
