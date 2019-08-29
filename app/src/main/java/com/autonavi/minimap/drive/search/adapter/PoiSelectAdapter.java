package com.autonavi.minimap.drive.search.adapter;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Locale;

public class PoiSelectAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private ArrayList<POI> mPois;

    public static final class a {
        public TextView a;
        public TextView b;
        public TextView c;
        public View d;
        public View e;
        public TextView f;
        public View g;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public PoiSelectAdapter(Context context, ArrayList<POI> arrayList) {
        this.mPois = arrayList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        if (this.mPois != null) {
            return this.mPois.size();
        }
        return 0;
    }

    public Object getItem(int i) {
        if (this.mPois != null) {
            return this.mPois.get(i);
        }
        return null;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        a aVar;
        boolean z;
        if (view == null) {
            aVar = new a();
            view2 = this.mLayoutInflater.inflate(R.layout.drive_search_listview_select_poi_item, null);
            aVar.a = (TextView) view2.findViewById(R.id.name);
            aVar.b = (TextView) view2.findViewById(R.id.addr);
            aVar.c = (TextView) view2.findViewById(R.id.poiDisNum);
            aVar.d = view2.findViewById(R.id.distance_info_layout);
            aVar.e = view2.findViewById(R.id.poi_info_layout);
            aVar.f = (TextView) view2.findViewById(R.id.status_desc);
            aVar.g = view2.findViewById(R.id.poi_list_bottom_line);
            view2.setTag(aVar);
        } else {
            view2 = view;
            aVar = (a) view.getTag();
        }
        aVar.e.setVisibility(8);
        if (this.mPois == null || i >= this.mPois.size()) {
            return view2;
        }
        POI poi = this.mPois.get(i);
        FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
        String customName = favoritePOI.getCustomName();
        if (TextUtils.isEmpty(customName)) {
            customName = favoritePOI.getName();
        }
        String addr = favoritePOI.getAddr();
        aVar.a.setText(customName);
        if (!TextUtils.isEmpty(addr)) {
            aVar.b.setText(addr);
            aVar.b.setVisibility(0);
            z = true;
        } else {
            aVar.b.setVisibility(8);
            z = false;
        }
        if (poi.getDistance() == -100) {
            aVar.d.setVisibility(8);
        } else {
            aVar.d.setVisibility(0);
            aVar.c.setVisibility(0);
            aVar.c.setText(cfe.a(poi.getDistance()));
            z = true;
        }
        if (z) {
            aVar.e.setVisibility(0);
        }
        String str = (String) poi.getPoiExtra().get("businfo_station_status");
        if (!TextUtils.isEmpty(str)) {
            if (Build.BRAND.toLowerCase(Locale.getDefault()).contains("xiaomi") || VERSION.SDK_INT <= 16) {
                aVar.f.setBackgroundResource(R.drawable.search_result_status_blue);
            } else {
                aVar.f.setBackgroundResource(R.drawable.search_result_status_blue_bkg);
            }
            aVar.f.setTextColor(-16739841);
            String[] split = str.split("\\|");
            ArrayList arrayList = new ArrayList();
            if (split != null && split.length > 0) {
                for (int i2 = 0; i2 < split.length; i2++) {
                    try {
                        arrayList.add(Integer.valueOf(Integer.parseInt(split[i2])));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!arrayList.contains(Integer.valueOf(1))) {
                if (arrayList.contains(Integer.valueOf(0))) {
                    aVar.f.setVisibility(0);
                    aVar.f.setText(R.string.drive_bus_off);
                } else if (arrayList.contains(Integer.valueOf(3))) {
                    aVar.f.setVisibility(0);
                    aVar.f.setText(R.string.drive_bus_building);
                } else if (arrayList.contains(Integer.valueOf(2))) {
                    aVar.f.setVisibility(0);
                    aVar.f.setText(R.string.drive_bus_planning);
                }
            }
            aVar.f.setVisibility(8);
            aVar.f.setText("");
        } else {
            aVar.f.setVisibility(8);
        }
        if (getCount() - 1 == i) {
            aVar.g.setVisibility(0);
        } else {
            aVar.g.setVisibility(8);
        }
        String str2 = (String) poi.getPoiExtra().get("update_flag");
        if (!TextUtils.isEmpty(str2) && "3".equals(str2.trim())) {
            if (Build.BRAND.toLowerCase(Locale.getDefault()).contains("xiaomi") || VERSION.SDK_INT <= 16) {
                aVar.f.setBackgroundResource(R.drawable.search_result_status_red);
            } else {
                aVar.f.setBackgroundResource(R.drawable.search_result_status_red_bkg);
            }
            aVar.f.setVisibility(0);
            aVar.f.setText(R.string.drive_bus_closed);
            aVar.f.setTextColor(-47802);
        }
        return view2;
    }
}
