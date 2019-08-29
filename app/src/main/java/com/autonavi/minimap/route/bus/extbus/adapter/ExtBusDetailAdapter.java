package com.autonavi.minimap.route.bus.extbus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.ExTrainPath;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter;
import com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"InflateParams", "ClickableViewAccessibility"})
public class ExtBusDetailAdapter extends RouteBusDetailAdapter {
    private OnClickListener mClickListener;
    private ArrayList<dvs> mStationList = new ArrayList<>();

    public interface a extends com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter.a {
        void a(View view);
    }

    static class b implements OnClickListener {
        private a a;

        b(a aVar) {
            this.a = aVar;
        }

        public final void onClick(View view) {
            if (this.a != null && view.getId() == R.id.railway_more) {
                this.a.a(view);
                LogManager.actionLogV2("P00262", "B003");
            }
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getViewTypeCount() {
        return 18;
    }

    public ExtBusDetailAdapter(Context context) {
        super(context, false);
        this.mInflater = LayoutInflater.from(context);
    }

    public void setListData(List<dvs> list) {
        this.mStationList.clear();
        this.mStationList.addAll(list);
        notifyDataSetChanged();
    }

    public void setExtBusDetailClickListener(a aVar) {
        super.setBusDetailClickListener(aVar);
        this.mClickListener = new b(aVar);
    }

    public int getCount() {
        return this.mStationList.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public int getItemViewType(int i) {
        return this.mStationList.get(i).A;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        dvs dvs = this.mStationList.get(i);
        int i2 = dvs.A;
        if (i2 == 0 || i2 == 12) {
            view = setFootOrTransferDesView(view, dvs);
            getView(view, R.id.foot_icon_container).setVisibility(8);
        } else if (i2 != 17) {
            switch (i2) {
                case 2:
                    view = setStartEndPointDesView(view, dvs, true);
                    break;
                case 3:
                    view = setStartEndPointDesView(view, dvs, false);
                    break;
                default:
                    switch (i2) {
                        case 5:
                            view = setRailwayPointDesView(view, dvs);
                            break;
                        case 6:
                            view = setBusStationDesView(view, dvs, i);
                            break;
                    }
            }
        } else {
            view = setTrainTransferDesView(view, dvs);
        }
        if (view != null) {
            view.setOnTouchListener(this);
        }
        return view;
    }

    private View setRailwayPointDesView(View view, dvs dvs) {
        if (view == null) {
            view = this.mInflater.inflate(R.layout.route_ext_bus_result_detail_railway_item, null);
        }
        ExTrainPath exTrainPath = dvs.B;
        int i = R.drawable.route_extbus_logo;
        int color = this.mContext.getResources().getColor(R.color.c_11);
        int a2 = ebn.a(62.0d, color);
        View view2 = getView(view, R.id.section_item_title_layout);
        view2.setTag(KEY_INDEX, dvs);
        dwn.a(view2, ebn.a(75.0d, color));
        ((ImageView) getView(view, R.id.section_item_title_icon)).setImageResource(i);
        ((TextView) getView(view, R.id.section_item_title_name)).setText(exTrainPath.trip);
        ((TextView) getView(view, R.id.section_item_bus_direction)).setText(exTrainPath.getTrainSectionDesc());
        dwn.a(getView(view, R.id.section_item_content_layout), ebn.a(10.0d, color));
        dwn.a(getView(view, R.id.content_line_view), a2);
        RouteBusStationUpDownNameView routeBusStationUpDownNameView = (RouteBusStationUpDownNameView) getView(view, R.id.up_station_name_layout);
        routeBusStationUpDownNameView.setStationData(exTrainPath.getTrainStartStation(), null, a2, true);
        dvs dvs2 = new dvs();
        dvs2.o = dvs.o - 1;
        routeBusStationUpDownNameView.setTag(KEY_INDEX, dvs2);
        RouteBusStationUpDownNameView routeBusStationUpDownNameView2 = (RouteBusStationUpDownNameView) getView(view, R.id.down_station_name_layout);
        routeBusStationUpDownNameView2.setStationData(exTrainPath.getTrainEndStation(), null, a2, false);
        routeBusStationUpDownNameView2.setTag(KEY_INDEX, dvs);
        StringBuilder sb = new StringBuilder();
        sb.append(this.mContext.getString(R.string.route_about));
        sb.append(exTrainPath.getTrainCostTime());
        ((TextView) getView(view, R.id.railway_cost_time)).setText(sb.toString());
        View view3 = getView(view, R.id.railway_more);
        view3.setTag(dvs);
        NoDBClickUtil.a(view3, this.mClickListener);
        return view;
    }

    private View setTrainTransferDesView(View view, dvs dvs) {
        if (view == null) {
            view = this.mInflater.inflate(R.layout.route_bus_result_detail_foot_transfer_item, null);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.route_bus_detail_line_point));
            bitmapDrawable.setTileModeXY(TileMode.CLAMP, TileMode.REPEAT);
            bitmapDrawable.setDither(true);
            getView(view, R.id.transfer_line).setBackgroundDrawable(bitmapDrawable);
        }
        TextView textView = (TextView) getView(view, R.id.section_name);
        String str = "";
        if (dvs.H == 1) {
            str = AMapAppGlobal.getApplication().getString(R.string.route_transfer);
        } else if (dvs.H == 2) {
            if (dvs.x) {
                str = dvs.c();
            } else {
                str = AMapAppGlobal.getApplication().getString(R.string.route_incoming);
            }
        } else if (dvs.H == 3) {
            if (dvs.x) {
                str = dvs.c();
            } else {
                str = AMapAppGlobal.getApplication().getString(R.string.route_departure);
            }
        }
        textView.setText(str);
        getView(view, R.id.transfer_icon).setVisibility(8);
        getView(view, R.id.transfer_line).setVisibility(0);
        getView(view, R.id.foot_icon_container).setVisibility(8);
        return view;
    }
}
