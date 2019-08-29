package com.alipay.mobile.beehive.cityselect.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import java.util.List;

public class LinkGridAdapter extends BaseAdapter {
    private int bgResID;
    private Context context;
    private List<String> datas;

    private static class a {
        public AUTextView a;

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }

    public LinkGridAdapter(Context context2, List<String> datas2) {
        this.context = context2;
        this.datas = datas2;
    }

    public int getCount() {
        return this.datas.size();
    }

    public Object getItem(int position) {
        return this.datas.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.grid_item_city_list, parent, false);
        }
        a holder = (a) convertView.getTag();
        if (holder == null) {
            holder = new a(0);
            holder.a = (AUTextView) convertView.findViewById(R.id.city_item);
        }
        holder.a.setText((CharSequence) getItem(position));
        holder.a.setTextColor(this.context.getResources().getColor(com.alipay.mobile.antui.R.color.AU_COLOR_LINK));
        if (this.bgResID != 0) {
            convertView.setBackgroundResource(this.bgResID);
        }
        return convertView;
    }

    public void setBgResID(int bgResID2) {
        this.bgResID = bgResID2;
    }
}
