package com.alipay.mobile.nebulacore.dev.bugme;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class H5BugMeListView extends ListView {
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public H5BugmeConsole b;
    private H5BugMeListViewAdapter c = new H5BugMeListViewAdapter();
    /* access modifiers changed from: private */
    public int d;

    class H5BugMeListViewAdapter extends BaseAdapter {
        H5BugMeListViewAdapter() {
        }

        public int getCount() {
            return H5BugMeListView.this.b.getLogDataList(H5BugMeListView.this.d).size();
        }

        public Object getItem(int position) {
            return H5BugMeListView.this.b.getLogDataList(H5BugMeListView.this.d).get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                textView = new TextView(H5BugMeListView.this.a);
            } else {
                textView = (TextView) convertView;
            }
            textView.setTextIsSelectable(true);
            textView.setText(H5BugMeListView.this.b.getLogDataList(H5BugMeListView.this.d).get(position).getContent());
            textView.setLayoutParams(new LayoutParams(-1, -2));
            textView.setPadding(50, 50, 50, 50);
            return textView;
        }
    }

    public H5BugMeListView(Context context, H5BugmeConsole console, int index) {
        super(context);
        this.a = context;
        this.b = console;
        this.d = index;
        setAdapter(this.c);
    }

    public void refresh() {
        this.c.notifyDataSetChanged();
    }
}
