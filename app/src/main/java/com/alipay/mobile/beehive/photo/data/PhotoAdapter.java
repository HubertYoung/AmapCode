package com.alipay.mobile.beehive.photo.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> dataList = new ArrayList();

    public PhotoAdapter(Context context2, List<T> models) {
        this.context = context2;
        if (models != null) {
            this.dataList.addAll(models);
        }
    }

    public void addDatas(List<T> datas) {
        this.dataList.addAll(datas);
    }

    public int getCount() {
        if (this.dataList == null) {
            return 0;
        }
        return this.dataList.size();
    }

    public Object getItem(int position) {
        return this.dataList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void setData(List<T> models) {
        if (models != null && !models.isEmpty()) {
            this.dataList.clear();
            this.dataList.addAll(models);
            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return this.dataList;
    }
}
