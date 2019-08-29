package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;

/* compiled from: AUCardMenu */
final class l extends BaseAdapter {
    final /* synthetic */ AUCardMenu a;

    /* synthetic */ l(AUCardMenu x0, byte b) {
        this(x0);
    }

    private l(AUCardMenu aUCardMenu) {
        this.a = aUCardMenu;
    }

    public final int getCount() {
        return this.a.mPopItemList.size();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public MessagePopItem getItem(int position) {
        return (MessagePopItem) this.a.mPopItemList.get(position);
    }

    public final long getItemId(int position) {
        return 0;
    }

    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new n(this.a, this.a.mContext);
        }
        ((n) convertView).a((MessagePopItem) this.a.mPopItemList.get(position));
        ((n) convertView).a((j) new m(this, position));
        return convertView;
    }
}
