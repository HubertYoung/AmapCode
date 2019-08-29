package com.autonavi.minimap.route.coach.controller;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.common.view.RouteLoadingView;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;

public final class CoachUIStatusController {
    View a;
    public ResultStatus b;
    private View c;
    private RouteLoadingView d;
    private View e;
    private PullToRefreshListView f;
    private View g;
    private View h;
    private ImageView i;
    private TextView j;
    private TextView k;
    private View l;

    public enum ResultStatus {
        LOADING,
        LOADING_NO_DATE,
        FAILED_NET_ERROR,
        FAILED_NET_ERROR_SIM_LOAD,
        FAILED_NET_ERROR_SIM_LOAD_NO_DATE,
        FAILED_SERVER_ERROR,
        FAILED_NO_RESULT,
        FAILED_NO_RESULT_FOR_SHORT_DISTANCE,
        FAILED_FILTER_NO_RESULT,
        SUCCESS
    }

    public CoachUIStatusController(View view, View view2) {
        this.c = view;
        this.g = view2;
        if (this.c != null) {
            this.d = (RouteLoadingView) this.c.findViewById(R.id.coach_loading_view);
            this.e = this.d.findViewById(R.id.route_loading_view_background_mask);
            this.f = (PullToRefreshListView) this.c.findViewById(R.id.pull_to_coach_plan_info_listview);
            this.a = this.c.findViewById(R.id.coach_plan_non_list_item_tips);
            this.h = this.c.findViewById(R.id.coach_title_time);
            this.i = (ImageView) this.c.findViewById(R.id.listview_status_img);
            this.j = (TextView) this.c.findViewById(R.id.listview_status_text);
            this.k = (TextView) this.c.findViewById(R.id.listview_status_loadingtext);
            this.l = this.c.findViewById(R.id.coach_bottom_view);
        }
    }

    public final boolean a(ResultStatus resultStatus) {
        if (this.d == null) {
            return false;
        }
        this.b = resultStatus;
        if (resultStatus == ResultStatus.LOADING) {
            this.d.setVisibility(0);
            this.h.setVisibility(0);
            this.e.setVisibility(4);
            this.d.bringToFront();
            this.f.setVisibility(8);
            this.g.setVisibility(4);
            this.l.setVisibility(4);
            this.a.setVisibility(8);
            this.i.setVisibility(8);
            this.j.setVisibility(8);
            this.k.setVisibility(8);
        } else if (resultStatus == ResultStatus.LOADING_NO_DATE) {
            this.h.setVisibility(8);
            this.d.setVisibility(0);
            this.e.setVisibility(0);
            this.d.bringToFront();
            this.f.setVisibility(8);
            this.g.setVisibility(8);
            this.l.setVisibility(8);
            this.a.setVisibility(8);
            this.i.setVisibility(8);
            this.j.setVisibility(8);
            this.k.setVisibility(8);
        } else if (resultStatus == ResultStatus.FAILED_NET_ERROR) {
            this.h.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(8);
            this.g.setVisibility(8);
            this.l.setVisibility(8);
            this.a.setVisibility(0);
            this.i.setVisibility(0);
            this.j.setVisibility(0);
            this.k.setVisibility(0);
            this.i.setImageResource(R.drawable.route_net_error);
            this.j.setText(this.j.getResources().getString(R.string.route_network_error).split("/")[0]);
            this.k.setText("");
        } else if (resultStatus == ResultStatus.FAILED_NET_ERROR_SIM_LOAD) {
            a(ResultStatus.LOADING);
            this.a.setClickable(false);
            aho.a(new Runnable() {
                public final void run() {
                    CoachUIStatusController.this.a(ResultStatus.FAILED_NET_ERROR);
                    CoachUIStatusController.this.a.setClickable(true);
                }
            }, 200);
        } else if (resultStatus == ResultStatus.FAILED_NET_ERROR_SIM_LOAD_NO_DATE) {
            a(ResultStatus.LOADING_NO_DATE);
            this.a.setClickable(false);
            aho.a(new Runnable() {
                public final void run() {
                    CoachUIStatusController.this.a(ResultStatus.FAILED_NET_ERROR);
                    CoachUIStatusController.this.a.setClickable(true);
                }
            }, 200);
        } else if (resultStatus == ResultStatus.FAILED_SERVER_ERROR) {
            this.h.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(8);
            this.g.setVisibility(8);
            this.l.setVisibility(8);
            this.a.setVisibility(0);
            this.i.setVisibility(0);
            this.j.setVisibility(0);
            this.k.setVisibility(0);
            this.i.setImageResource(R.drawable.route_net_error);
            this.j.setText("服务器开小差了");
            this.k.setText("稍后点击重试");
        } else if (resultStatus == ResultStatus.FAILED_NO_RESULT) {
            this.h.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(8);
            this.g.setVisibility(8);
            this.l.setVisibility(8);
            this.a.setVisibility(0);
            this.i.setVisibility(0);
            this.j.setVisibility(0);
            this.k.setVisibility(0);
            this.i.setImageResource(R.drawable.rt_list_data_error);
            this.j.setText("没有合适的方案");
            this.k.setText("建议采用其他出行方式");
        } else if (resultStatus == ResultStatus.FAILED_NO_RESULT_FOR_SHORT_DISTANCE) {
            this.h.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(8);
            this.g.setVisibility(8);
            this.l.setVisibility(8);
            this.a.setVisibility(0);
            this.i.setVisibility(0);
            this.j.setVisibility(0);
            this.k.setVisibility(0);
            this.i.setImageResource(R.drawable.rt_list_data_error);
            this.j.setText("距离太近");
            this.k.setText("暂无客车方案");
        } else if (resultStatus == ResultStatus.FAILED_FILTER_NO_RESULT) {
            this.h.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(8);
            this.g.setVisibility(0);
            this.l.setVisibility(0);
            this.a.setVisibility(0);
            this.i.setVisibility(0);
            this.j.setVisibility(0);
            this.k.setVisibility(0);
            this.i.setImageResource(R.drawable.rt_list_data_error);
            this.j.setText("没有合适的方案");
            this.k.setText("请更改筛选条件后再试");
        } else if (resultStatus == ResultStatus.SUCCESS) {
            this.h.setVisibility(0);
            this.d.setVisibility(8);
            this.e.setVisibility(4);
            this.f.setVisibility(0);
            this.g.setVisibility(0);
            this.l.setVisibility(0);
            this.a.setVisibility(8);
            this.i.setVisibility(8);
            this.j.setVisibility(8);
            this.k.setVisibility(8);
        }
        return true;
    }
}
