package com.autonavi.minimap.drive.sticker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.minimap.R;

public class StickerTipView extends LinearLayout {
    private Context mContext;
    private TextView mDetailView;
    private TextView mLastTimeView;
    private TextView mNameView;
    private LinearLayout mNormalFrame;
    private TextView mRecentView;
    private TextView mStatusView;
    /* access modifiers changed from: private */
    public div mSticker;
    /* access modifiers changed from: private */
    public a mStickerTipsClickListener;
    private TextView mStrictControlDetailTv;
    private FrameLayout mStrictControlFrame;
    private TextView mTimeView;
    private OnClickListener mViewOnClickListener;

    public interface a {
        void a(div div);

        void b(div div);

        void c(div div);
    }

    public StickerTipView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public StickerTipView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        this.mContext = context;
    }

    public StickerTipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mViewOnClickListener = new OnClickListener() {
            public final void onClick(View view) {
                int id = view.getId();
                if (id == R.id.btn_detail || id == R.id.tv_status || id == R.id.tv_details_strict_control) {
                    if (StickerTipView.this.mStickerTipsClickListener != null) {
                        StickerTipView.this.mStickerTipsClickListener.a(StickerTipView.this.mSticker);
                    }
                } else if (id == R.id.btn_parking) {
                    if (StickerTipView.this.mStickerTipsClickListener != null) {
                        StickerTipView.this.mStickerTipsClickListener.b(StickerTipView.this.mSticker);
                    }
                } else if (id == R.id.btn_navi && StickerTipView.this.mStickerTipsClickListener != null) {
                    StickerTipView.this.mStickerTipsClickListener.c(StickerTipView.this.mSticker);
                }
            }
        };
        this.mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.route_car_sticker_tipview, this, true);
        NoDBClickUtil.a(findViewById(R.id.btn_navi), this.mViewOnClickListener);
        NoDBClickUtil.a(findViewById(R.id.btn_parking), this.mViewOnClickListener);
        NoDBClickUtil.a(findViewById(R.id.btn_detail), this.mViewOnClickListener);
        setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (StickerTipView.this.mStickerTipsClickListener != null) {
                    StickerTipView.this.mStickerTipsClickListener.a(StickerTipView.this.mSticker);
                }
            }
        });
        this.mStatusView = (TextView) findViewById(R.id.tv_status);
        NoDBClickUtil.a((View) this.mStatusView, this.mViewOnClickListener);
        this.mNameView = (TextView) findViewById(R.id.tv_name);
        this.mTimeView = (TextView) findViewById(R.id.tv_times);
        this.mRecentView = (TextView) findViewById(R.id.tv_recentNum);
        this.mLastTimeView = (TextView) findViewById(R.id.tv_lastTime);
        this.mDetailView = (TextView) findViewById(R.id.tv_details_strict_control);
        this.mNormalFrame = (LinearLayout) findViewById(R.id.normal_mode_frame);
        this.mStrictControlFrame = (FrameLayout) findViewById(R.id.strict_control_frame);
        this.mStrictControlDetailTv = (TextView) findViewById(R.id.strict_control_detail);
    }

    public void refreshView(int i, div div) {
        this.mSticker = div;
        switch (div.g) {
            case 0:
                this.mStatusView.setText(this.mContext.getString(R.string.stickers_safe_string));
                this.mStatusView.setBackgroundResource(R.drawable.sticker_tips_safe);
                break;
            case 1:
                this.mStatusView.setText(this.mContext.getString(R.string.stickers_danger_string));
                this.mStatusView.setBackgroundResource(R.drawable.sticker_tips_normal);
                break;
            case 2:
                this.mStatusView.setText(this.mContext.getString(R.string.stickers_high_danger_string));
                this.mStatusView.setBackgroundResource(R.drawable.sticker_tips_danger);
                break;
            case 3:
                this.mStatusView.setText(this.mContext.getString(R.string.stickers_strict_control_string));
                this.mStatusView.setBackgroundResource(R.drawable.sticker_tips_strict_control);
                break;
        }
        updateTextViews(div);
    }

    private void updateTextViews(div div) {
        this.mNameView.setText(div.b);
        if (div.g < 3) {
            this.mTimeView.setVisibility(0);
            this.mRecentView.setVisibility(0);
            this.mLastTimeView.setVisibility(0);
            this.mDetailView.setVisibility(8);
            TextView textView = this.mTimeView;
            String string = this.mContext.getString(R.string.stickers_high_frequency_time);
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(div.o);
            textView.setText(sb.toString());
            TextView textView2 = this.mRecentView;
            String string2 = this.mContext.getString(R.string.stickers_total_count);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(div.k);
            textView2.setText(div.a(string2.replace("%s", sb2.toString())));
            TextView textView3 = this.mLastTimeView;
            String string3 = this.mContext.getString(R.string.stickers_last_time);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(string3);
            sb3.append(div.j);
            textView3.setText(sb3.toString());
            this.mNormalFrame.setVisibility(0);
            this.mStrictControlFrame.setVisibility(8);
            return;
        }
        this.mTimeView.setVisibility(8);
        this.mRecentView.setVisibility(8);
        this.mLastTimeView.setVisibility(8);
        this.mDetailView.setVisibility(0);
        this.mDetailView.setText(div.m);
        this.mNormalFrame.setVisibility(8);
        this.mStrictControlFrame.setVisibility(0);
        this.mStrictControlDetailTv.setText(div.m);
    }

    public void setTipsClickListener(a aVar) {
        this.mStickerTipsClickListener = aVar;
    }
}
