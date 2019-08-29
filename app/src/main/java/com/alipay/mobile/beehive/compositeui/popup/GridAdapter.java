package com.alipay.mobile.beehive.compositeui.popup;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.basic.AUButton;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.api.BeehiveService;
import com.alipay.mobile.beehive.compositeui.popup.model.FilterItem;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    private List<FilterItem> filters = new ArrayList();
    /* access modifiers changed from: private */
    public OnFilterChangedListener listener;
    private Context mContext;
    /* access modifiers changed from: private */
    public FilterPopupWindow popupWindow;
    /* access modifiers changed from: private */
    public String selectedFilter;

    public static class ViewHolder {
        public AUButton filterText;
    }

    public GridAdapter(Context c, FilterPopupWindow popupWindow2, OnFilterChangedListener listener2, String selectedFilter2) {
        this.mContext = c;
        this.popupWindow = popupWindow2;
        this.listener = listener2;
        this.selectedFilter = selectedFilter2;
    }

    public void setFilters(List<FilterItem> filters2) {
        this.filters.clear();
        this.filters.addAll(filters2);
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.filters.size();
    }

    public Object getItem(int index) {
        return this.filters.get(index);
    }

    public long getItemId(int index) {
        return (long) index;
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.popup_grid_item, null);
            holder = new ViewHolder();
            holder.filterText = (AUButton) convertView.findViewById(R.id.bee_item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FilterItem item = this.filters.get(index);
        if (item != null) {
            holder.filterText.setText(item.name);
            if (TextUtils.equals(item.code, this.selectedFilter)) {
                holder.filterText.setSelected(true);
            } else {
                holder.filterText.setSelected(false);
            }
        }
        holder.filterText.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                v.setContentDescription(item.code);
                if (!TextUtils.isEmpty(item.jumpUrl)) {
                    BeehiveService beehiveService = (BeehiveService) MicroServiceUtil.getMicroService(BeehiveService.class);
                    if (beehiveService != null && beehiveService.getSchemaExecutor() != null) {
                        Uri uri = Uri.parse(item.jumpUrl);
                        if (uri != null && beehiveService.getSchemaExecutor().process(uri) == 0) {
                            GridAdapter.this.popupWindow.dismiss();
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (!item.code.equals(GridAdapter.this.selectedFilter)) {
                    GridAdapter.this.listener.onFilterChanged(item);
                }
                GridAdapter.this.popupWindow.dismiss();
                GridAdapter.this.selectedFilter = item.code;
            }
        });
        return convertView;
    }
}
