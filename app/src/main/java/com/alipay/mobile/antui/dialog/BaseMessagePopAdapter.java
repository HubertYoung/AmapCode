package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.dialog.BaseMessagePopItemView;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMessagePopAdapter<T extends BaseMessagePopItemView> extends BaseAdapter {
    private Context mContext;
    private List<MessagePopItem> mPopItemList = new ArrayList();

    /* access modifiers changed from: protected */
    public abstract T createView(Context context);

    public BaseMessagePopAdapter(Context context, List<MessagePopItem> itemList) {
        this.mPopItemList = itemList;
        this.mContext = context;
    }

    public int getCount() {
        return this.mPopItemList.size();
    }

    public MessagePopItem getItem(int position) {
        return this.mPopItemList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = createView(this.mContext);
        }
        ((BaseMessagePopItemView) convertView).setPopItem(this.mPopItemList.get(position));
        return convertView;
    }
}
