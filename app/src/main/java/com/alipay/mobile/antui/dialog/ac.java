package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;

/* compiled from: AUListDialog */
final class ac extends BaseAdapter {
    final /* synthetic */ AUListDialog a;

    private ac(AUListDialog aUListDialog) {
        this.a = aUListDialog;
    }

    /* synthetic */ ac(AUListDialog x0, byte b) {
        this(x0);
    }

    public final int getCount() {
        return this.a.mItemList.size();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public MessagePopItem getItem(int position) {
        return (MessagePopItem) this.a.mItemList.get(position);
    }

    public final long getItemId(int position) {
        return (long) position;
    }

    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ad(this.a, this.a.getContext());
        }
        ((ad) convertView).setPopItem(getItem(position));
        return convertView;
    }
}
