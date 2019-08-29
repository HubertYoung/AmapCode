package com.autonavi.bundle.scenicarea.scenicspeak;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.common.MapWidgetTip;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.AbstractMap.SimpleEntry;

public class ScenicSpeakView extends RelativeLayout {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private int mOperateDuration;
    private ViewGroup mRootView;
    private Runnable mRunnable;
    private ImageView mSpeakIcon;
    private RelativeLayout mSpeakIconLayout;
    /* access modifiers changed from: private */
    public MapWidgetTip mSpeakOperateName;
    /* access modifiers changed from: private */
    public String mSpeakOperateSchema;
    /* access modifiers changed from: private */
    public String mSpeakSchema;

    public ScenicSpeakView(Context context) {
        super(context);
        init(context);
    }

    public ScenicSpeakView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.scenic_speak_view, this);
        initView();
        setVisible(false);
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mRootView = (ViewGroup) findViewById(R.id.scenic_speak_root);
        this.mSpeakIcon = (ImageView) this.mRootView.findViewById(R.id.scenic_speak_icon);
        this.mSpeakIconLayout = (RelativeLayout) this.mRootView.findViewById(R.id.scenic_speak_icon_layout);
        this.mSpeakOperateName = (MapWidgetTip) this.mRootView.findViewById(R.id.scenic_speak_operate_name);
        this.mSpeakOperateName.setVisibility(8);
    }

    public void initRootView(String str, String str2, String str3, int i, boolean z, final amv amv) {
        setSpeakIcon();
        setSpeakOperateName(str);
        this.mSpeakSchema = str2;
        this.mSpeakOperateSchema = str3;
        this.mOperateDuration = i;
        this.mSpeakIconLayout.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                LogManager.actionLogV25("P00383", "B027", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a));
                if (!TextUtils.isEmpty(ScenicSpeakView.this.mSpeakSchema)) {
                    DoNotUseTool.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(ScenicSpeakView.this.mSpeakSchema)));
                }
            }
        });
        this.mSpeakOperateName.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                LogManager.actionLogV25("P00383", "B028", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a));
                if (!TextUtils.isEmpty(ScenicSpeakView.this.mSpeakOperateSchema)) {
                    DoNotUseTool.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(ScenicSpeakView.this.mSpeakOperateSchema)));
                }
            }
        });
        if (z) {
            this.mSpeakOperateName.setVisibility(0);
            this.mRunnable = new Runnable() {
                public final void run() {
                    ScenicSpeakView.this.mSpeakOperateName.setVisibility(8);
                }
            };
            this.mHandler.postDelayed(this.mRunnable, (long) (this.mOperateDuration * 1000));
        }
    }

    private void setSpeakIcon() {
        if (this.mSpeakIcon != null) {
            this.mSpeakIcon.setImageResource(R.drawable.map_widget_guide_speak);
        }
    }

    private void setSpeakOperateName(String str) {
        if (this.mSpeakOperateName != null) {
            this.mSpeakOperateName.setText(str);
        }
    }

    public void setVisible(boolean z) {
        int i = z ? 0 : 8;
        if (getVisibility() != i) {
            setVisibility(i);
        }
    }

    public void closeOperateView() {
        this.mSpeakOperateName.setVisibility(8);
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mRunnable);
        }
    }

    public void removeView() {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mRunnable);
        }
    }
}
