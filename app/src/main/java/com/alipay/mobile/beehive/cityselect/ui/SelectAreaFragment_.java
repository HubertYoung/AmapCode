package com.alipay.mobile.beehive.cityselect.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.antui.bar.AUVerticalTabView;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.basic.AUSearchInputBox;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class SelectAreaFragment_ extends SelectAreaFragment implements HasViews, OnViewChangedListener {
    private View contentView_;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public final void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(this.onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    public final View findViewById(int id) {
        if (this.contentView_ == null) {
            return null;
        }
        return this.contentView_.findViewById(id);
    }

    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (this.contentView_ == null) {
            this.contentView_ = inflater.inflate(R.layout.fragment_area_select, container, false);
        }
        return this.contentView_;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    public final void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.onViewChangedNotifier_.notifyViewChanged(this);
    }

    public final void onViewChanged(HasViews hasViews) {
        this.mVerticalTabView = (AUVerticalTabView) hasViews.findViewById(R.id.left_tabs);
        this.mTVNoResult = (AUTextView) hasViews.findViewById(R.id.search_no_result);
        this.mRightContainer = (AUFrameLayout) hasViews.findViewById(R.id.right_container);
        this.mSearchInputBox = (AUSearchInputBox) hasViews.findViewById(R.id.search_bar);
        this.mSearchCityList = (AUListView) hasViews.findViewById(R.id.search_city_list);
        afterViews();
    }
}
