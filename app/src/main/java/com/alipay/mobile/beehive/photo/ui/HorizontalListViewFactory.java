package com.alipay.mobile.beehive.photo.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import com.alipay.mobile.antui.basic.AUHorizontalListView;
import com.alipay.mobile.beehive.photo.data.HorizontalListAdapter;
import com.alipay.mobile.beehive.photo.data.PhotoRpcRunnable;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.service.PhotoParam;

public class HorizontalListViewFactory {
    private static int DEFAULT_LIST_ITEM_MARGIN = 6;

    public static AUHorizontalListView getHorizontalListView(Context context, PhotoRpcRunnable refreshRpcRunnable, Bundle param) {
        int i = 134;
        AUHorizontalListView listView = new AUHorizontalListView(context);
        int width = PhotoUtil.dp2px(context, (param == null || param.getInt(PhotoParam.REMOTEPHOTO_WIDTH) == 0) ? 134 : param.getInt(PhotoParam.REMOTEPHOTO_WIDTH));
        if (!(param == null || param.getInt(PhotoParam.REMOTEPHOTO_HEIGHT) == 0)) {
            i = param.getInt(PhotoParam.REMOTEPHOTO_HEIGHT);
        }
        int height = PhotoUtil.dp2px(context, i);
        int marginDp = DEFAULT_LIST_ITEM_MARGIN;
        if (!(param == null || param.getInt("itemMargin") == 0)) {
            marginDp = param.getInt("itemMargin", DEFAULT_LIST_ITEM_MARGIN);
        }
        listView.setItemMargin(PhotoUtil.dp2px(context, marginDp));
        final HorizontalListAdapter adapter = new HorizontalListAdapter(context, refreshRpcRunnable, width, height, param);
        listView.setAdapter((ListAdapter) adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoLogger.verbose(HorizontalListAdapter.TAG, "HorizontalListView click " + position);
                adapter.preview(position);
            }
        });
        return listView;
    }
}
