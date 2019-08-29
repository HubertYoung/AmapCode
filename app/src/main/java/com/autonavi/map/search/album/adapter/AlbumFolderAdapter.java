package com.autonavi.map.search.album.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;

public class AlbumFolderAdapter extends AbstractViewHolderBaseAdapter<cai, a> {
    private Context mCtx;

    public static class a extends com.autonavi.map.template.AbstractViewHolderAdapter.a {
        public TextView a;
        public TextView b;
        public ImageView c;

        public a(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.album_folder_number);
            this.b = (TextView) view.findViewById(R.id.album_folder_name);
            this.c = (ImageView) view.findViewById(R.id.album_folder_icon);
        }
    }

    public AlbumFolderAdapter(Context context) {
        this.mCtx = context;
    }

    public View onCreateView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(this.mCtx).inflate(R.layout.album_folder_list_item, viewGroup, false);
    }

    public a onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
        return new a(view);
    }

    public void onBindViewHolderWithData(a aVar, cai cai, int i, int i2) {
        if (aVar != null && cai != null) {
            if (!TextUtils.isEmpty(cai.d)) {
                ko.a(aVar.c, cai.d);
            }
            if (!TextUtils.isEmpty(cai.a)) {
                aVar.b.setText(cai.a);
            }
            aVar.a.setText(String.valueOf(cai.b));
        }
    }
}
