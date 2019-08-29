package com.alipay.mobile.beehive.compositeui.multilevel;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.tablelist.AUSingleTitleListItem;

public class MultilevelSelectAdapter extends BaseAdapter {
    private final Activity activity;
    private JSONArray items;

    public MultilevelSelectAdapter(JSONArray items2, Activity activity2) {
        this.items = items2;
        this.activity = activity2;
    }

    public int getCount() {
        return this.items.size();
    }

    public Object getItem(int position) {
        return this.items.getJSONObject(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new AUSingleTitleListItem(this.activity);
        }
        ((AUSingleTitleListItem) convertView).setItemPositionStyle(19);
        ((AUSingleTitleListItem) convertView).setArrowImageVisibility(8);
        ((AUSingleTitleListItem) convertView).getLeftTextView().setText(((JSONObject) getItem(position)).getString("name"));
        return convertView;
    }
}
