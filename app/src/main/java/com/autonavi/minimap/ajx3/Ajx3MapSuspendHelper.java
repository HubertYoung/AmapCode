package com.autonavi.minimap.ajx3;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout.LayoutParams;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.CommonSuspendWidget;
import com.autonavi.map.suspend.refactor.zoom.ZoomView;
import com.autonavi.map.suspend.refactor.zoom.ZoomViewPresenter.a;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.util.AjxFileUtils;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.util.HashMap;
import java.util.List;

public class Ajx3MapSuspendHelper extends ccv implements ccs, a {
    private static final int MIN_MARGIN_BOTTOM = 8;
    private static final String TAG = "Ajx3MapSuspendHelper";
    private IAjxContext mAjxContext;
    protected Context mContext;
    private View mGpsButton;
    private View mScaleView;
    private View mTrafficView;
    private HashMap<String, View> mWidgetHolder = new HashMap<>();
    private ZoomView mZoomView;
    protected ccy manager;

    public void onZoomInClick() {
    }

    public void onZoomOutClick() {
    }

    public void updateControl(String str) {
    }

    public Ajx3MapSuspendHelper(IMapPage iMapPage) {
        super(iMapPage.getContext());
        this.mContext = iMapPage.getContext();
        this.manager = iMapPage.getSuspendWidgetHelper();
    }

    public void init() {
        initTrafficView();
        initZoomView();
        initScale();
    }

    private void initTrafficView() {
        removeFromParent(this.manager.j());
        this.mTrafficView = this.manager.j();
        addWidget(this.manager.j(), this.manager.k(), 4);
        this.mWidgetHolder.put("trafficControl", this.manager.j());
        this.mTrafficView.setVisibility(8);
    }

    public void setAjxContext(IAjxContext iAjxContext) {
        this.mAjxContext = iAjxContext;
    }

    private void initZoomView() {
        removeFromParent(this.manager.l());
        this.mZoomView = this.manager.l();
        addWidget(this.mZoomView, this.manager.m(), 6);
        this.mWidgetHolder.put("zoomControl", this.manager.l());
        this.mZoomView.setVisibility(8);
    }

    private void initScale() {
        removeFromParent(this.manager.f());
        LayoutParams g = this.manager.g();
        g.leftMargin = agn.a(this.mContext, 48.0f);
        this.mScaleView = this.manager.f();
        addWidget(this.mScaleView, g, 3);
        this.mWidgetHolder.put("scaleControl", this.manager.f());
        this.mScaleView.setVisibility(8);
    }

    private void initGpsButton() {
        removeFromParent(this.manager.d());
        this.mGpsButton = this.manager.d();
        addWidget(this.mGpsButton, this.manager.e(), 3);
        this.mWidgetHolder.put("locationControl", this.mGpsButton);
        this.mGpsButton.setVisibility(8);
    }

    public void removeFromParent(View view) {
        if (view != null && view.getParent() != null && (view.getParent() instanceof ViewGroup)) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    public LayoutParams getTrafficParams() {
        LayoutParams k = this.manager.k();
        k.topMargin = agn.a(this.mContext, 4.0f);
        return k;
    }

    public void setMarginBottom(int i, int i2) {
        if (i == 0) {
            i = 8;
        }
        MarginLayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null && layoutParams.bottomMargin != i) {
            layoutParams.bottomMargin = i;
            setLayoutParams(layoutParams);
            AMapLog.d(TAG, "setMarginBottom=".concat(String.valueOf(i)));
        }
    }

    public void setMarginTop(int i, int i2) {
        MarginLayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null && layoutParams.topMargin != i) {
            layoutParams.topMargin = i;
            setLayoutParams(layoutParams);
            AMapLog.d(TAG, "setMarginTop=".concat(String.valueOf(i)));
        }
    }

    public void setViewAlpha(float f, int i) {
        View suspendView = getSuspendView();
        if (suspendView.getAlpha() != f) {
            suspendView.setAlpha(f);
            AMapLog.d(TAG, "setViewAlpha=".concat(String.valueOf(f)));
        }
    }

    public void setVerticalMargin(int i, int i2, int i3, int i4) {
        MarginLayoutParams layoutParams = getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        if (layoutParams.topMargin != i || layoutParams.bottomMargin != i2) {
            layoutParams.topMargin = i;
            layoutParams.bottomMargin = i2;
            setLayoutParams(layoutParams);
            StringBuilder sb = new StringBuilder("setVerticalMargin marginTop/marginBottom=");
            sb.append(i);
            sb.append("/");
            sb.append(i2);
            AMapLog.d(TAG, sb.toString());
        }
    }

    public void addControl(String str, JsFunctionCallback jsFunctionCallback) {
        CommonSuspendWidget commonSuspendWidget;
        List<a> a = ccw.a(str);
        if (a != null && !a.isEmpty()) {
            for (int i = 0; i < a.size(); i++) {
                a aVar = a.get(i);
                if (aVar != null && !isContain(aVar.a, jsFunctionCallback)) {
                    int a2 = ccw.a(aVar);
                    if (a2 >= 0) {
                        Context context = this.mContext;
                        IAjxContext iAjxContext = this.mAjxContext;
                        if (aVar == null) {
                            commonSuspendWidget = null;
                        } else {
                            CommonSuspendWidget commonSuspendWidget2 = new CommonSuspendWidget(context);
                            int a3 = ccw.a(aVar);
                            boolean z = true;
                            if (!(a3 == 1 || a3 == 2 || a3 == 3)) {
                                z = false;
                            }
                            commonSuspendWidget2.setGravityLeft(z);
                            commonSuspendWidget2.setIconSize(DimensionUtils.standardUnitToPixel((float) aVar.c), DimensionUtils.standardUnitToPixel((float) aVar.d));
                            if (iAjxContext != null && !TextUtils.isEmpty(aVar.e)) {
                                commonSuspendWidget2.setImageBitmap(AjxFileUtils.getImage(iAjxContext, aVar.e));
                            }
                            if (!TextUtils.isEmpty(aVar.j) && commonSuspendWidget2.getImageView() != null) {
                                commonSuspendWidget2.getImageView().setContentDescription(aVar.j);
                            }
                            commonSuspendWidget2.showTips(aVar.h);
                            if (jsFunctionCallback != null) {
                                commonSuspendWidget2.setIconClickListener(new OnClickListener(jsFunctionCallback, aVar) {
                                    final /* synthetic */ JsFunctionCallback a;
                                    final /* synthetic */ a b;

                                    {
                                        this.a = r1;
                                        this.b = r2;
                                    }

                                    public final void onClick(View view) {
                                        this.a.callback(this.b.a);
                                    }
                                });
                            }
                            commonSuspendWidget2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                            commonSuspendWidget = commonSuspendWidget2;
                        }
                        this.mWidgetHolder.put(aVar.a, commonSuspendWidget);
                        if (a2 == 3 || a2 == 6) {
                            addWidget(commonSuspendWidget, generateDefaultLayoutParams(), a2, 0);
                        } else {
                            addWidget(commonSuspendWidget, generateDefaultLayoutParams(), a2);
                        }
                        if (aVar.g) {
                            hideControl(aVar.a);
                        } else {
                            showControl(aVar.a, false);
                        }
                    }
                }
            }
        }
    }

    public void setCommonControl(String str, JsFunctionCallback jsFunctionCallback) {
        List<a> b = ccw.b(str);
        if (b != null && !b.isEmpty()) {
            for (int i = 0; i < b.size(); i++) {
                a aVar = b.get(i);
                if (aVar != null) {
                    View view = this.mWidgetHolder.get(aVar.a);
                    if (view != null) {
                        view.setVisibility(aVar.g ? 8 : 0);
                    }
                }
            }
        }
    }

    public void showControl(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            View view = this.mWidgetHolder.get(str);
            if (view != null) {
                view.setVisibility(0);
            }
        }
    }

    public void hideControl(String str) {
        if (!TextUtils.isEmpty(str)) {
            View view = this.mWidgetHolder.get(str);
            if (view != null) {
                view.setVisibility(8);
            }
        }
    }

    private MarginLayoutParams getLayoutParams() {
        return (MarginLayoutParams) getSuspendView().getLayoutParams();
    }

    private void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        getSuspendView().setLayoutParams(layoutParams);
    }

    private boolean isContain(String str, final JsFunctionCallback jsFunctionCallback) {
        for (final String next : this.mWidgetHolder.keySet()) {
            if (str.equals(next)) {
                View view = this.mWidgetHolder.get(next);
                if (!(view == null || !(view instanceof CommonSuspendWidget) || jsFunctionCallback == null)) {
                    ((CommonSuspendWidget) view).setIconClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            jsFunctionCallback.callback(next);
                        }
                    });
                }
                return true;
            }
        }
        return false;
    }

    private ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.LayoutParams(-2, -2);
    }
}
