package com.alipay.mobile.beehive.cityselect.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.beehive.R;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import com.googlecode.androidannotations.api.BackgroundExecutor.Task;
import com.googlecode.androidannotations.api.UiThreadExecutor;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class ProvinceCityListActivity_ extends ProvinceCityListActivity implements HasViews, OnViewChangedListener {
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public final void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(this.onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_province_city_select);
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
        this.provinceList = (AUListView) hasViews.findViewById(R.id.provincecitylist_listview);
        this.titleBar = (AUTitleBar) hasViews.findViewById(R.id.provincecitylist_titleBar);
        afterBindViews();
    }

    public final void refreshList(int type, int index, String country, String province, String city) {
        final int i = type;
        final int i2 = index;
        final String str = country;
        final String str2 = province;
        final String str3 = city;
        UiThreadExecutor.runTask("", new Runnable() {
            public final void run() {
                if (!ProvinceCityListActivity_.this.isFinishing()) {
                    ProvinceCityListActivity_.super.refreshList(i, i2, str, str2, str3);
                }
            }
        }, 0);
    }

    public final void showLoactionInfo(final String locationStrShow) {
        UiThreadExecutor.runTask("", new Runnable() {
            public final void run() {
                if (!ProvinceCityListActivity_.this.isFinishing()) {
                    ProvinceCityListActivity_.super.showLoactionInfo(locationStrShow);
                }
            }
        }, 0);
    }

    public final void refreshList(int type, int index, int listIndex, int listTop) {
        final int i = type;
        final int i2 = index;
        final int i3 = listIndex;
        final int i4 = listTop;
        UiThreadExecutor.runTask("", new Runnable() {
            public final void run() {
                if (!ProvinceCityListActivity_.this.isFinishing()) {
                    ProvinceCityListActivity_.super.refreshList(i, i2, i3, i4);
                }
            }
        }, 0);
    }

    public final void initDistrictList() {
        BackgroundExecutor.execute((Task) new Task("", "") {
            public final void execute() {
                try {
                    ProvinceCityListActivity_.super.initDistrictList();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        });
    }

    public final void loadDataFromLocal() {
        BackgroundExecutor.execute((Task) new Task("", "") {
            public final void execute() {
                try {
                    ProvinceCityListActivity_.super.loadDataFromLocal();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        });
    }
}
