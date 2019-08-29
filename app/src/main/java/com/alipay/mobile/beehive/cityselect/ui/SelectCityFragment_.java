package com.alipay.mobile.beehive.cityselect.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.basic.AUSearchInputBox;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.beehive.cityselect.view.CityBladeView;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import com.googlecode.androidannotations.api.BackgroundExecutor.Task;
import com.googlecode.androidannotations.api.UiThreadExecutor;
import java.util.List;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class SelectCityFragment_ extends SelectCityFragment implements HasViews, OnViewChangedListener {
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
            this.contentView_ = inflater.inflate(R.layout.fragment_city_select, container, false);
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
        this.mCityListView = (AUListView) hasViews.findViewById(R.id.city_list);
        this.mSearchNoResult = (AUTextView) hasViews.findViewById(R.id.search_no_result);
        this.mSearchBar = (AUSearchInputBox) hasViews.findViewById(R.id.search_bar);
        this.mSectionBladeView = (CityBladeView) hasViews.findViewById(R.id.section_list);
        afterViews();
    }

    public final void initView() {
        UiThreadExecutor.runTask("", new Runnable() {
            public final void run() {
                if (SelectCityFragment_.this.getActivity() != null && !SelectCityFragment_.this.getActivity().isFinishing() && !SelectCityFragment_.this.isDetached()) {
                    SelectCityFragment_.super.initView();
                }
            }
        }, 0);
    }

    @SuppressLint({"DefaultLocale"})
    public final void prepareDataAndInitView(final List<CityVO> cityList) {
        BackgroundExecutor.execute((Task) new Task("", "") {
            public final void execute() {
                try {
                    SelectCityFragment_.super.prepareDataAndInitView(cityList);
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        });
    }

    public final void loadCitys() {
        BackgroundExecutor.execute((Task) new Task("", "") {
            public final void execute() {
                try {
                    SelectCityFragment_.super.loadCitys();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        });
    }
}
