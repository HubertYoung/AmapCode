package com.amap.bundle.drive.result.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.draggable.DraggableListAdapter;
import com.autonavi.minimap.widget.draggable.DraggableRecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DriveToolboxView extends DraggableRecyclerView {
    private DraggableListAdapter<b, a> mAdapter;
    /* access modifiers changed from: private */
    public int mMaxHeight;
    /* access modifiers changed from: private */
    public int mMaxWidth;
    private List<b> mToolItemList;

    public static class a extends ViewHolder {
        /* access modifiers changed from: private */
        public TextView a;
        /* access modifiers changed from: private */
        public TextView b;
        /* access modifiers changed from: private */
        public ImageView c;
        /* access modifiers changed from: private */
        public RelativeLayout d;

        public a(View view) {
            super(view);
            this.c = (ImageView) view.findViewById(R.id.icon);
            this.a = (TextView) view.findViewById(R.id.icon_txt);
            this.b = (TextView) view.findViewById(R.id.info_tv);
            this.d = (RelativeLayout) view.findViewById(R.id.content);
        }
    }

    public static class b {
        public String a;
        public int b;
        public OnClickListener c;
        boolean d;
        public String e;
    }

    public DriveToolboxView(@NonNull Context context) {
        this(context, null, 0);
    }

    public DriveToolboxView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DriveToolboxView(@NonNull final Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mToolItemList = new ArrayList();
        setBackgroundResource(R.drawable.route_toolbox_bg);
        setOverScrollMode(2);
        int a2 = agn.a(context, 12.0f);
        setPadding(a2, a2, a2, a2);
        this.mMaxHeight = agn.a(context, 314.0f);
        this.mMaxWidth = agn.a(context, 236.0f);
        setLayoutManager(new GridLayoutManager(context) {
            public final void onMeasure(Recycler recycler, State state, int i2, int i3) {
                super.onMeasure(recycler, state, i2, i3);
                int measuredWidth = DriveToolboxView.this.getMeasuredWidth();
                DriveToolboxView.this.getMeasuredHeight();
                int a = agn.a(context, (float) ((((int) (((((float) DriveToolboxView.this.getAdapter().getItemCount()) * 1.0f) / ((float) this.b)) + 0.5f)) * 94) + 32));
                if (measuredWidth > DriveToolboxView.this.mMaxWidth) {
                    measuredWidth = DriveToolboxView.this.mMaxWidth;
                }
                if (a > DriveToolboxView.this.mMaxHeight) {
                    a = DriveToolboxView.this.mMaxHeight;
                }
                setMeasuredDimension(measuredWidth, a);
            }
        });
        this.mAdapter = new DraggableListAdapter<b, a>(this.mToolItemList) {
            public final /* synthetic */ void onBindViewHolderItem(ViewHolder viewHolder, Object obj) {
                a aVar = (a) viewHolder;
                b bVar = (b) obj;
                aVar.d.setOnClickListener(bVar.c);
                aVar.a.setText(bVar.a);
                aVar.c.setImageResource(bVar.b);
                aVar.c.setSelected(bVar.d);
                if (!TextUtils.isEmpty(bVar.e)) {
                    aVar.b.setVisibility(0);
                    aVar.b.setText(bVar.e);
                    return;
                }
                aVar.b.setVisibility(8);
            }

            public final /* synthetic */ ViewHolder onCreateViewHolderItem(ViewGroup viewGroup, int i) {
                return new a(LayoutInflater.from(context).inflate(R.layout.route_widget_drive_toolbox_item_drive, viewGroup, false));
            }
        };
        setAdapter(this.mAdapter);
        setDraggable(false);
    }

    public void setToolItemList(List<b> list) {
        this.mToolItemList.clear();
        if (list != null && !list.isEmpty()) {
            this.mToolItemList.addAll(list);
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
