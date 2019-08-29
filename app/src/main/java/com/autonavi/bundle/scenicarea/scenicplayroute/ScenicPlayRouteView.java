package com.autonavi.bundle.scenicarea.scenicplayroute;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.life.api.api.IScenicPlayRouteCloseCallback;
import com.autonavi.bundle.life.api.api.IScenicPlayRouteItemClickCallback;
import com.autonavi.bundle.life.api.entity.ScenicPlayEntity;
import com.autonavi.bundle.life.api.entity.ScenicPlayRouteItemEntity;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class ScenicPlayRouteView extends RelativeLayout {
    /* access modifiers changed from: private */
    public bbf mAdapter;
    private Context mContext;
    /* access modifiers changed from: private */
    public int mPreSelectedIndex;
    private ViewGroup mRootView;
    private TextView mRouteCount;
    private LinearLayout mRouteCountLayout;
    private RelativeLayout mScenicPlayRouteClose;
    private RecyclerView mScenicPlayRouteView;

    public ScenicPlayRouteView(Context context) {
        super(context);
        init(context);
    }

    public ScenicPlayRouteView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.scenic_play_route_view, this);
        initView();
        setVisible(false);
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mRootView = (ViewGroup) findViewById(R.id.scenic_play_route_widget_root);
        this.mScenicPlayRouteView = (RecyclerView) this.mRootView.findViewById(R.id.scenic_play_route_view);
        this.mScenicPlayRouteClose = (RelativeLayout) this.mRootView.findViewById(R.id.scenic_play_route_close);
        this.mRouteCount = (TextView) this.mRootView.findViewById(R.id.scenic_play_route_count_name);
        this.mRouteCountLayout = (LinearLayout) this.mRootView.findViewById(R.id.scenic_play_route_layout);
    }

    private void setRecyclerViewAdapter(Context context, ArrayList<ScenicPlayRouteItemEntity> arrayList, int i) {
        this.mScenicPlayRouteView.setLayoutManager(new LinearLayoutManager(context, 0, false));
        this.mAdapter = new bbf(context, arrayList);
        this.mScenicPlayRouteView.setAdapter(this.mAdapter);
        this.mAdapter.b = i;
        this.mPreSelectedIndex = i;
    }

    public void initRootView(ScenicPlayEntity scenicPlayEntity, IScenicPlayRouteItemClickCallback iScenicPlayRouteItemClickCallback, final IScenicPlayRouteCloseCallback iScenicPlayRouteCloseCallback, final amv amv, int i) {
        String str = scenicPlayEntity.e;
        final String str2 = scenicPlayEntity.f;
        this.mRouteCount.setText(str);
        this.mRouteCountLayout.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                LogManager.actionLogV25("P00383", "B034", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a), new SimpleEntry("click", DIYMainMapPresenter.DIY_ENTRY_KEY_MORE), new SimpleEntry("itemid", ""));
                if (!TextUtils.isEmpty(str2)) {
                    DoNotUseTool.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(str2)));
                }
            }
        });
        ArrayList<ScenicPlayRouteItemEntity> arrayList = scenicPlayEntity.h;
        if (arrayList != null && arrayList.size() >= 2) {
            setRecyclerViewAdapter(this.mContext, arrayList, i);
            setRecyclerViewOnItemClick(iScenicPlayRouteItemClickCallback);
            this.mScenicPlayRouteClose.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    LogManager.actionLogV25("P00383", "B034", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a), new SimpleEntry("click", "关闭"), new SimpleEntry("itemid", ""));
                    iScenicPlayRouteCloseCallback.a();
                }
            });
        }
    }

    private void setRecyclerViewOnItemClick(final IScenicPlayRouteItemClickCallback iScenicPlayRouteItemClickCallback) {
        this.mAdapter.a = new a() {
            public final void a(int i) {
                ScenicPlayRouteView.this.mAdapter.b = i;
                if (i != ScenicPlayRouteView.this.mPreSelectedIndex) {
                    ScenicPlayRouteView.this.setItemSelectedEvent(i, iScenicPlayRouteItemClickCallback);
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void setItemSelectedEvent(int i, IScenicPlayRouteItemClickCallback iScenicPlayRouteItemClickCallback) {
        this.mScenicPlayRouteView.setHasFixedSize(true);
        this.mAdapter.notifyItemChanged(this.mPreSelectedIndex, Integer.valueOf(1));
        this.mAdapter.notifyItemChanged(i, Integer.valueOf(1));
        this.mPreSelectedIndex = i;
        if (iScenicPlayRouteItemClickCallback != null) {
            iScenicPlayRouteItemClickCallback.a(i);
        }
    }

    public void setVisible(boolean z) {
        int i = z ? 0 : 8;
        if (getVisibility() != i) {
            setVisibility(i);
        }
    }
}
