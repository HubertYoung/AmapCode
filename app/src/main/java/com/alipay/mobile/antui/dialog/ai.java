package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import java.util.ArrayList;

/* compiled from: AUPopMenu */
final class ai extends BaseAdapter {
    final /* synthetic */ AUPopMenu a;
    private ArrayList<MessagePopItem> b;

    /* synthetic */ ai(AUPopMenu x0, ArrayList x1, byte b2) {
        this(x0, x1);
    }

    private ai(AUPopMenu aUPopMenu, ArrayList<MessagePopItem> popItems) {
        this.a = aUPopMenu;
        this.b = new ArrayList<>();
        this.b.clear();
        this.b.addAll(popItems);
        notifyDataSetChanged();
    }

    public final int getCount() {
        return this.b.size();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public MessagePopItem getItem(int position) {
        return this.b.get(position);
    }

    public final long getItemId(int position) {
        return 0;
    }

    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new aj(this.a, this.a.mContext);
        }
        ((aj) convertView).a(this.b.get(position));
        ((aj) convertView).a(position != 0);
        return convertView;
    }
}
