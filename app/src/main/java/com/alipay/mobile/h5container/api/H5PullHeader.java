package com.alipay.mobile.h5container.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.view.H5PullHeaderView;

public class H5PullHeader implements H5PullHeaderView {
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public View contentView;
    public Context context;
    public ProgressBar pbLoading = ((ProgressBar) this.contentView.findViewById(R.id.h5_pullrefresh_progress));
    public TextView tvTitle = ((TextView) this.contentView.findViewById(R.id.h5_pullrefresh_title));

    public H5PullHeader(Context context2, ViewGroup viewGroup) {
        this.context = context2;
        this.contentView = LayoutInflater.from(context2).inflate(R.layout.h5_pull_header, viewGroup, false);
        setLastRefresh();
    }

    public void onProgressUpdate(int progress) {
    }

    public void showOpen(int style) {
        this.pbLoading.setVisibility(0);
        this.tvTitle.setText(R.string.h5_pull_can_refresh);
    }

    public void showLoading() {
        this.tvTitle.setText(R.string.h5_refreshing);
    }

    public void showFinish() {
        setLastRefresh();
    }

    public void setLastRefresh() {
    }

    public void showOver() {
        this.tvTitle.setText(R.string.h5_release_to_refresh);
    }

    public View getContentView() {
        return this.contentView;
    }

    public void onRefreshFinish() {
    }
}
