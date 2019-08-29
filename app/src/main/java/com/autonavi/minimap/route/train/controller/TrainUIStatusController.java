package com.autonavi.minimap.route.train.controller;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.common.view.RouteLoadingView;
import com.autonavi.minimap.route.train.page.TrainPlanListPage;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;

public final class TrainUIStatusController {
    View a;
    public RequestStatus b;
    private View c;
    private RouteLoadingView d;
    private View e;
    private PullToRefreshListView f;
    private TrainPlanListPage g;
    private View h;
    private View i;
    private ImageView j;
    private TextView k;
    private TextView l;

    public enum RequestStatus {
        LOADING,
        LOADING_NO_DATE,
        FAILED_NET_ERROR,
        FAILED_NET_ERROR_SIM_LOAD,
        FAILED_NET_ERROR_SIM_LOAD_NO_DATE,
        FAILED_SERVER_ERROR,
        FAILED_NO_RESULT,
        FAILED_FILTER_NO_RESULT,
        SUCCESS
    }

    public TrainUIStatusController(TrainPlanListPage trainPlanListPage, View view) {
        this.c = view;
        this.g = trainPlanListPage;
        if (view != null) {
            this.d = (RouteLoadingView) view.findViewById(R.id.loading_view);
            this.e = this.d.findViewById(R.id.route_loading_view_background_mask);
            this.f = (PullToRefreshListView) view.findViewById(R.id.pull_to_train_plan_info_listview);
            this.h = view.findViewById(R.id.train_plan_bottom_condition_view);
            this.a = view.findViewById(R.id.train_plan_non_list_item_tips);
            this.i = view.findViewById(R.id.title_time);
            this.j = (ImageView) view.findViewById(R.id.listview_status_img);
            this.k = (TextView) view.findViewById(R.id.listview_status_text);
            this.l = (TextView) view.findViewById(R.id.listview_status_loadingtext);
        }
    }

    public final boolean a(RequestStatus requestStatus) {
        if (this.d == null) {
            return false;
        }
        this.b = requestStatus;
        if (requestStatus == RequestStatus.LOADING) {
            this.d.setVisibility(0);
            this.i.setVisibility(0);
            this.e.setVisibility(4);
            this.d.bringToFront();
            this.f.setVisibility(8);
            this.h.setVisibility(4);
            this.a.setVisibility(8);
            this.j.setVisibility(8);
            this.k.setVisibility(8);
            this.l.setVisibility(8);
        } else if (requestStatus == RequestStatus.LOADING_NO_DATE) {
            this.i.setVisibility(8);
            this.d.setVisibility(0);
            this.e.setVisibility(0);
            this.d.bringToFront();
            this.f.setVisibility(8);
            this.h.setVisibility(8);
            this.a.setVisibility(8);
            this.j.setVisibility(8);
            this.k.setVisibility(8);
            this.l.setVisibility(8);
        } else if (requestStatus == RequestStatus.FAILED_NET_ERROR) {
            this.i.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(8);
            this.h.setVisibility(8);
            this.a.setVisibility(0);
            this.j.setVisibility(0);
            this.k.setVisibility(0);
            this.l.setVisibility(0);
            this.j.setImageResource(R.drawable.route_net_error);
            this.k.setText(this.k.getResources().getString(R.string.route_network_error).split("/")[0]);
            this.l.setText("");
            this.g.e();
        } else if (requestStatus == RequestStatus.FAILED_NET_ERROR_SIM_LOAD) {
            a(RequestStatus.LOADING);
            this.a.setClickable(false);
            aho.a(new Runnable() {
                public final void run() {
                    TrainUIStatusController.this.a(RequestStatus.FAILED_NET_ERROR);
                    TrainUIStatusController.this.a.setClickable(true);
                }
            }, 200);
        } else if (requestStatus == RequestStatus.FAILED_NET_ERROR_SIM_LOAD_NO_DATE) {
            a(RequestStatus.LOADING_NO_DATE);
            this.a.setClickable(false);
            aho.a(new Runnable() {
                public final void run() {
                    TrainUIStatusController.this.a(RequestStatus.FAILED_NET_ERROR);
                    TrainUIStatusController.this.a.setClickable(true);
                }
            }, 200);
        } else if (requestStatus == RequestStatus.FAILED_SERVER_ERROR) {
            this.i.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(8);
            this.h.setVisibility(8);
            this.a.setVisibility(0);
            this.j.setVisibility(0);
            this.k.setVisibility(0);
            this.l.setVisibility(0);
            this.j.setImageResource(R.drawable.route_net_error);
            this.k.setText("服务器开小差了");
            this.l.setText("稍后点击重试");
            this.g.e();
        } else if (requestStatus == RequestStatus.FAILED_NO_RESULT) {
            this.i.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(8);
            this.h.setVisibility(8);
            this.a.setVisibility(0);
            this.j.setVisibility(0);
            this.k.setVisibility(0);
            this.l.setVisibility(0);
            this.j.setImageResource(R.drawable.rt_list_data_error);
            this.k.setText("没有合适的方案");
            this.l.setText("建议采用其他出行方式");
            this.g.e();
        } else if (requestStatus == RequestStatus.FAILED_FILTER_NO_RESULT) {
            this.i.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(8);
            this.h.setVisibility(0);
            this.a.setVisibility(0);
            this.j.setVisibility(0);
            this.k.setVisibility(0);
            this.l.setVisibility(0);
            this.j.setImageResource(R.drawable.rt_list_data_error);
            this.k.setText("没有合适的方案");
            this.l.setText("请更改筛选条件后再试");
        } else if (requestStatus == RequestStatus.SUCCESS) {
            this.i.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(0);
            this.h.setVisibility(0);
            this.a.setVisibility(8);
            this.j.setVisibility(8);
            this.k.setVisibility(8);
            this.l.setVisibility(8);
        }
        return true;
    }
}
