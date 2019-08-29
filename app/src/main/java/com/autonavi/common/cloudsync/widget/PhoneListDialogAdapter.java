package com.autonavi.common.cloudsync.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.List;

public class PhoneListDialogAdapter extends BaseAdapter {
    public static final String PHONE_LIST_SPLIT = "$";
    private Context context;
    private List<String> list;

    static class a {
        TextView a;

        a() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public PhoneListDialogAdapter(List<String> list2, Context context2) {
        this.list = list2;
        this.context = context2;
    }

    public int getCount() {
        if (this.list != null) {
            return this.list.size();
        }
        return 0;
    }

    public Object getItem(int i) {
        if (this.list != null) {
            return this.list.get(i);
        }
        return null;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        a aVar;
        if (view == null) {
            aVar = new a();
            view2 = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.phone_list_item, null);
            aVar.a = (TextView) view2.findViewById(R.id.phone);
            view2.setTag(aVar);
        } else {
            view2 = view;
            aVar = (a) view.getTag();
        }
        String str = this.list.get(i);
        if (str.contains("$")) {
            str = str.substring(0, str.lastIndexOf("$"));
        }
        aVar.a.setText(str);
        return view2;
    }
}
