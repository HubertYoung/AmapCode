package com.ali.user.mobile.register.region;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APPinnedHeaderListView.PinnedHeaderAdapter;
import java.util.List;

public class RegionAdapter extends BaseAdapter implements PinnedHeaderAdapter {
    private final List<RegionInfo> a;
    private final Context b;

    class ViewHolder {
        protected LinearLayout a;
        protected TextView b;
        protected View c;
        protected TextView d;
        protected TextView e;

        ViewHolder() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public RegionAdapter(Context context, List<RegionInfo> list) {
        this.b = context;
        this.a = list;
    }

    public int getCount() {
        return this.a.size();
    }

    public Object getItem(int i) {
        return this.a.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(this.b).inflate(R.layout.F, null);
            viewHolder = new ViewHolder();
            viewHolder.a = (LinearLayout) view.findViewById(R.id.L);
            viewHolder.b = (TextView) view.findViewById(R.id.M);
            viewHolder.c = view.findViewById(R.id.bs);
            viewHolder.d = (TextView) view.findViewById(R.id.bt);
            viewHolder.e = (TextView) view.findViewById(R.id.bu);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        RegionInfo regionInfo = this.a.get(i);
        if (regionInfo.isDisplayLetter) {
            viewHolder.a.setVisibility(0);
            viewHolder.c.setVisibility(8);
            viewHolder.b.setText(regionInfo.mCharacter);
            view.setBackgroundColor(-1);
        } else {
            viewHolder.a.setVisibility(8);
            viewHolder.c.setVisibility(0);
            view.setBackgroundColor(-1);
        }
        viewHolder.d.setText(regionInfo.mRegionName);
        viewHolder.e.setText(regionInfo.mRegionNumber);
        return view;
    }
}
