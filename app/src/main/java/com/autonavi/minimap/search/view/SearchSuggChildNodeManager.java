package com.autonavi.minimap.search.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class SearchSuggChildNodeManager implements OnItemClickListener {
    private static final int MAX_DISPLAY_COUNT = 5;
    private ChildNodeAdapter mAdapter;
    private a mChildClickListener;

    static class ChildNodeAdapter extends ArrayAdapter<TipItem> {
        private LayoutInflater mInflater;
        private int mResourceId;

        static class a {
            ImageView a;
            TextView b;

            a() {
            }
        }

        public ChildNodeAdapter(Context context, int i, List<TipItem> list) {
            super(context, i, list);
            this.mInflater = LayoutInflater.from(context);
            this.mResourceId = i;
        }

        public int getCount() {
            int count = super.getCount();
            if (count > 5) {
                return 5;
            }
            return count;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            TipItem tipItem = (TipItem) getItem(i);
            if (view == null) {
                view = this.mInflater.inflate(this.mResourceId, viewGroup, false);
                aVar = new a();
                aVar.a = (ImageView) view.findViewById(R.id.child_icon_view);
                aVar.b = (TextView) view.findViewById(R.id.child_name_view);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            ko.a(aVar.a, tipItem.getChildNodeIconUrl());
            aVar.b.setText(tipItem.getChildNodeName());
            return view;
        }
    }

    public interface a {
        void onChildItemClicked(TipItem tipItem, int i, boolean z);
    }

    public SearchSuggChildNodeManager(@NonNull Context context, @NonNull GridView gridView) {
        this.mAdapter = new ChildNodeAdapter(context, R.layout.search_sugg_child_node_item, new ArrayList());
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(this.mAdapter);
    }

    public void setData(List<TipItem> list) {
        this.mAdapter.clear();
        this.mAdapter.addAll(list);
    }

    public void setChildClickListener(a aVar) {
        this.mChildClickListener = aVar;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        TipItem tipItem = (TipItem) this.mAdapter.getItem(i);
        if (tipItem != null && this.mChildClickListener != null) {
            this.mChildClickListener.onChildItemClicked(tipItem, i, true);
        }
    }
}
