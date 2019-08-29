package com.autonavi.bundle.uitemplate.mapwidget.widget.recommend;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.MapWidgetFactory;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.combine.CombineMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.combine.CombineWidgetPresenter;

public class RecommendAndScaleWidget extends CombineMapWidget {
    private RecommendAndScalePresenter mPresenter = new RecommendAndScalePresenter();
    private AbstractMapWidget mRecommend;

    public RecommendAndScaleWidget(Context context) {
        super(context);
        this.mRecommend = new RecommendWidget(context);
        combineWidgets(this.mRecommend, MapWidgetFactory.createInstance(context, new WidgetProperty(2, 0, WidgetType.SCALE)));
        this.mPresenter.initContext(getContext());
        this.mPresenter.initialize(this);
    }

    public View getRecommendView() {
        return this.mRecommend.getContentView();
    }

    public CombineWidgetPresenter getPresenter() {
        return this.mPresenter;
    }

    public void createPresenter(Class<CombineWidgetPresenter> cls) {
        super.createPresenter(cls);
    }
}
