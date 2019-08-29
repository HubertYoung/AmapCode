package com.autonavi.bundle.scenicarea.scenicguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.nebula.search.H5SearchType;
import com.autonavi.bundle.life.api.api.IScenicGuideItemClickCallback;
import com.autonavi.bundle.life.api.entity.ScenicGuideListItemEntity;
import com.autonavi.bundle.scenicarea.scenicguide.ScenicGuideWidgetViewLayout.a;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class ScenicGuideView extends RelativeLayout {
    /* access modifiers changed from: private */
    public bbe mAdapter;
    private Context mContext;
    /* access modifiers changed from: private */
    public int mMaxCellCount = 2;
    private int mPreSelectedIndex = -1;
    private ViewGroup mRootView;
    /* access modifiers changed from: private */
    public ViewGroup mRootViewGuide;
    private ScenicGuideWidgetViewLayout mRootViewLayout;
    /* access modifiers changed from: private */
    public RecyclerView mScenicGuideView;
    private ImageView mScenicGuideViewArrow;

    public ScenicGuideView(Context context) {
        super(context);
        init(context);
    }

    public ScenicGuideView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.scenic_guide_view, this);
        initView();
        setVisible(false);
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mRootView = (ViewGroup) findViewById(R.id.scenic_guide_root);
        this.mRootViewLayout = (ScenicGuideWidgetViewLayout) findViewById(R.id.scenic_guide_root_layout);
        this.mRootViewGuide = (ViewGroup) findViewById(R.id.scenic_guide_widget_root);
        this.mScenicGuideView = (RecyclerView) this.mRootView.findViewById(R.id.scenic_guide_view);
        this.mScenicGuideViewArrow = (ImageView) this.mRootView.findViewById(R.id.scenic_guide_view_arrow);
        this.mScenicGuideViewArrow.setVisibility(8);
    }

    private void setRecyclerViewAdapter(Context context, ArrayList<ScenicGuideListItemEntity> arrayList) {
        this.mScenicGuideView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.mAdapter = new bbe(context, arrayList);
        this.mScenicGuideView.setAdapter(this.mAdapter);
    }

    public void initRootView(ArrayList<ScenicGuideListItemEntity> arrayList, IScenicGuideItemClickCallback iScenicGuideItemClickCallback) {
        if (arrayList != null && arrayList.size() >= 2) {
            if (arrayList.size() > 2) {
                this.mScenicGuideViewArrow.setVisibility(0);
            } else {
                this.mScenicGuideViewArrow.setVisibility(8);
            }
            setRecyclerViewAdapter(this.mContext, arrayList);
            setRecyclerViewOnItemClick(arrayList, iScenicGuideItemClickCallback);
            this.mRootViewLayout.setOwner(new a() {
                public final int a() {
                    return ScenicGuideView.this.mMaxCellCount;
                }

                public final int b() {
                    return ScenicGuideView.this.mAdapter.getItemCount();
                }

                public final View c() {
                    return ScenicGuideView.this.mRootViewGuide;
                }
            });
        }
    }

    private void setRecyclerViewOnItemClick(final ArrayList<ScenicGuideListItemEntity> arrayList, final IScenicGuideItemClickCallback iScenicGuideItemClickCallback) {
        this.mAdapter.a = new a() {
            public final void a(int i) {
                ScenicGuideView.this.mAdapter.b = i;
                if (((ScenicGuideListItemEntity) arrayList.get(i)).f) {
                    ((ScenicGuideListItemEntity) arrayList.get(i)).f = false;
                    boolean z = true;
                    ScenicGuideView.this.mScenicGuideView.setHasFixedSize(true);
                    ScenicGuideView.this.mAdapter.notifyItemChanged(i, Integer.valueOf(1));
                    String str = ((ScenicGuideListItemEntity) arrayList.get(i)).c;
                    if (H5SearchType.SEARCH.equals(str) || !ActionConstant.SCHEMA.equals(str)) {
                        z = false;
                    }
                    if (iScenicGuideItemClickCallback != null) {
                        iScenicGuideItemClickCallback.a(false, "", "", z);
                    }
                    return;
                }
                ScenicGuideView.this.setItemSelectedEvent(i, arrayList, iScenicGuideItemClickCallback);
            }
        };
    }

    /* access modifiers changed from: private */
    public void setItemSelectedEvent(int i, ArrayList<ScenicGuideListItemEntity> arrayList, IScenicGuideItemClickCallback iScenicGuideItemClickCallback) {
        this.mScenicGuideView.setHasFixedSize(true);
        if (this.mPreSelectedIndex != -1) {
            arrayList.get(this.mPreSelectedIndex).f = false;
            this.mAdapter.notifyItemChanged(this.mPreSelectedIndex, Integer.valueOf(1));
        }
        arrayList.get(i).f = true;
        this.mAdapter.notifyItemChanged(i, Integer.valueOf(1));
        this.mPreSelectedIndex = i;
        ScenicGuideListItemEntity scenicGuideListItemEntity = arrayList.get(i);
        String str = scenicGuideListItemEntity.c;
        if (H5SearchType.SEARCH.equals(str)) {
            String str2 = scenicGuideListItemEntity.d;
            String str3 = scenicGuideListItemEntity.b;
            if (iScenicGuideItemClickCallback != null) {
                iScenicGuideItemClickCallback.a(true, str2, str3, false);
            }
            return;
        }
        if (ActionConstant.SCHEMA.equals(str)) {
            if (iScenicGuideItemClickCallback != null) {
                iScenicGuideItemClickCallback.a(true, "", "", true);
            }
            String str4 = scenicGuideListItemEntity.e;
            if (!TextUtils.isEmpty(str4)) {
                DoNotUseTool.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(str4)));
            }
        }
    }

    public void setVisible(boolean z) {
        int i = z ? 0 : 8;
        if (getVisibility() != i) {
            setVisibility(i);
        }
    }

    public void setScenicGuideUnselected(ArrayList<ScenicGuideListItemEntity> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).f) {
                arrayList.get(i).f = false;
                this.mScenicGuideView.setHasFixedSize(true);
                this.mAdapter.notifyItemChanged(i, Integer.valueOf(1));
            }
        }
    }
}
