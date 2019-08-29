package com.autonavi.bundle.uitemplate.indoor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.minimap.R;

public class MapGuideViewCenter extends RelativeLayout implements bef {
    private boolean mIsTrainStation = false;
    /* access modifiers changed from: private */
    public boolean mLastVisible = false;
    private agl<beg> mListeners = new agl<>();
    private ViewGroup mRootView;

    public MapGuideViewCenter(Context context) {
        super(context);
        init(context);
    }

    public MapGuideViewCenter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.map_guide_view_center, this);
        initView();
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mRootView = (ViewGroup) findViewById(R.id.map_guide_root);
        this.mRootView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                MapGuideViewCenter.this.onBtnClicked();
            }
        });
    }

    public void setIsTrainStation(boolean z) {
        if (z != this.mIsTrainStation) {
            this.mIsTrainStation = z;
        }
    }

    public boolean isTrainStation() {
        return this.mIsTrainStation;
    }

    public void setVisible(final boolean z) {
        if (this.mLastVisible != z) {
            setVisibility(z ? 0 : 8);
            if (z) {
                invalidate();
            }
            Stub.getMapWidgetManager().requestContainerLayout();
            this.mListeners.a((a<T>) new a<beg>() {
                public final /* synthetic */ void onNotify(Object obj) {
                    boolean z = z;
                    MapGuideViewCenter.this.mLastVisible;
                    ((beg) obj).a(z);
                }
            });
            this.mLastVisible = z;
        }
    }

    public void addListener(beg beg) {
        this.mListeners.a(beg);
    }

    public void removeListener(beg beg) {
        this.mListeners.b(beg);
    }

    /* access modifiers changed from: private */
    public void onBtnClicked() {
        this.mListeners.a((a<T>) new a<beg>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((beg) obj).a();
            }
        });
    }
}
