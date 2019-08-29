package com.alipay.mobile.beehive.compositeui.multilevel;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.antui.segement.AUSegment;
import com.alipay.mobile.beehive.R;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class MultilevelSelectActivity_ extends MultilevelSelectActivity implements HasViews, OnViewChangedListener {
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public final void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(this.onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_multilevel_select);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    public final void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        this.onViewChangedNotifier_.notifyViewChanged(this);
    }

    public final void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        this.onViewChangedNotifier_.notifyViewChanged(this);
    }

    public final void setContentView(View view) {
        super.setContentView(view);
        this.onViewChangedNotifier_.notifyViewChanged(this);
    }

    public final void onViewChanged(HasViews hasViews) {
        this.mTitleBar = (AUTitleBar) hasViews.findViewById(R.id.title_bar);
        this.mSegemnt = (AUSegment) hasViews.findViewById(R.id.segment);
        this.mContainer = (AULinearLayout) hasViews.findViewById(R.id.container);
        this.mListView = (AUListView) hasViews.findViewById(R.id.listview);
        init();
    }
}
