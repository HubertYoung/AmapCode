package com.amap.bundle.planhome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.BalloonText;

public class RoutePopupView extends RelativeLayout {
    public static final int TYPE_NATIVE_CAR = 2;
    public static final int TYPE_NATIVE_FOOT = 1;
    private int mAlternativeType = -1;
    /* access modifiers changed from: private */
    public axd mPageContainer;
    private Runnable mPopRunnable = new Runnable() {
        public final void run() {
            RoutePopupView.this.hideAlternativePopup();
        }
    };
    private RelativeLayout routePopupLine;
    private BalloonText routePopupTextView;
    /* access modifiers changed from: private */
    public RouteType routeType;
    private String tips;

    public RoutePopupView(Context context, axd axd) {
        super(context);
        this.mPageContainer = axd;
        initView();
        addRoutePopupView();
    }

    public RoutePopupView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.route_bus_popup_view, this, true);
        this.routePopupLine = (RelativeLayout) findViewById(R.id.route_fragment_popup_line);
        this.routePopupTextView = (BalloonText) findViewById(R.id.route_fragment_popup_text_view);
        this.routePopupTextView.setVisibility(0);
        setVisibility(8);
        setListeners();
    }

    private void addRoutePopupView() {
        setRelatLayoutParams();
        this.mPageContainer.getRouteInputUI().a((View) this);
    }

    private void setListeners() {
        NoDBClickUtil.a((View) this.routePopupTextView, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RoutePopupView.this.hideAlternativePopup();
                if (RoutePopupView.this.mPageContainer != null) {
                    RoutePopupView.this.mPageContainer.getRouteInputUI().a(RoutePopupView.this.routeType);
                }
            }
        });
    }

    public void hidePopupLineView() {
        if (this.routePopupLine != null) {
            this.routePopupLine.setVisibility(8);
        }
    }

    public void setRelatLayoutParams() {
        if (this.mPageContainer != null) {
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            layoutParams.topMargin = this.mPageContainer.getRouteInputUI().m();
            setLayoutParams(layoutParams);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        hidePopupLineView();
        return super.onTouchEvent(motionEvent);
    }

    public void alternativePopGone() {
        this.routePopupLine.setVisibility(8);
        setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void hideAlternativePopup() {
        this.mAlternativeType = 0;
        alternativePopGone();
        removePopRunnable();
    }

    public void alternativePopVisible() {
        this.routePopupLine.setVisibility(0);
        setVisibility(0);
    }

    private void setOnFootData() {
        this.tips = getContext().getResources().getString(R.string.bus_result_popup_for_foot);
        this.routeType = RouteType.ONFOOT;
    }

    private void setCarData() {
        this.tips = getContext().getResources().getString(R.string.bus_result_popup_for_drive);
        this.routeType = RouteType.CAR;
    }

    public void showAlternativePopup(int i) {
        if (this.mAlternativeType != 0) {
            this.mAlternativeType = i;
        }
        if (i == 1) {
            setOnFootData();
        } else if (i == 2) {
            setCarData();
        } else {
            return;
        }
        alternativePopVisible();
        setPopupTextView();
        removePopRunnable();
        ebr.a(true).postDelayed(this.mPopRunnable, 5000);
    }

    private void setPopupTextView() {
        this.routePopupTextView.setText(this.tips);
    }

    public void onDestroy() {
        removePopRunnable();
    }

    private void removePopRunnable() {
        if (this.mPopRunnable != null) {
            ebr.a(true).removeCallbacks(this.mPopRunnable);
        }
    }

    private void setPopupViewLParams() {
        if (this.mPageContainer != null) {
            LayoutParams layoutParams = (LayoutParams) this.routePopupTextView.getLayoutParams();
            if (this.routeType == RouteType.ONFOOT) {
                this.routePopupTextView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
                layoutParams.addRule(14);
            } else if (this.routeType == RouteType.CAR) {
                this.routePopupTextView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
                this.routePopupTextView.getMeasuredWidth();
                layoutParams.addRule(14);
            }
            if (layoutParams.leftMargin < 0) {
                layoutParams.leftMargin = 0;
            }
            this.routePopupTextView.setLayoutParams(layoutParams);
            this.routePopupTextView.requestLayout();
        }
    }
}
