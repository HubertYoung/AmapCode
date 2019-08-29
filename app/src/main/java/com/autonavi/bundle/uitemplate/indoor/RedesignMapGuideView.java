package com.autonavi.bundle.uitemplate.indoor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.minimap.R;

public class RedesignMapGuideView extends RelativeLayout implements bef {
    private ImageView mBtnMapGuide;
    private boolean mIsTrainStation = false;
    /* access modifiers changed from: private */
    public Boolean mLastVisible = Boolean.FALSE;
    private agl<beg> mListeners = new agl<>();
    private ViewGroup mRootView;

    public RedesignMapGuideView(Context context) {
        super(context);
        init(context);
    }

    public RedesignMapGuideView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.redesign_map_guide_view, this);
        initView();
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mRootView = (ViewGroup) findViewById(R.id.map_guide_root);
        this.mBtnMapGuide = (ImageView) findViewById(R.id.btn_map_guide);
        this.mBtnMapGuide.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RedesignMapGuideView.this.onBtnClicked();
            }
        });
    }

    public void setIsTrainStation(boolean z) {
        if (z != this.mIsTrainStation) {
            this.mIsTrainStation = z;
            if (z) {
                this.mBtnMapGuide.setImageResource(R.drawable.map_button_icon_guide_train_station);
                return;
            }
            this.mBtnMapGuide.setImageResource(R.drawable.btn_mapcontainer_guide);
        }
    }

    public boolean isTrainStation() {
        return this.mIsTrainStation;
    }

    public void setVisible(final boolean z) {
        if (this.mLastVisible.booleanValue() != z) {
            setVisibility(z ? 0 : 8);
            if (z) {
                invalidate();
            }
            Stub.getMapWidgetManager().requestContainerLayout();
            this.mListeners.a((a<T>) new a<beg>() {
                public final /* synthetic */ void onNotify(Object obj) {
                    boolean z = z;
                    RedesignMapGuideView.this.mLastVisible.booleanValue();
                    ((beg) obj).a(z);
                }
            });
            this.mLastVisible = Boolean.valueOf(z);
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
