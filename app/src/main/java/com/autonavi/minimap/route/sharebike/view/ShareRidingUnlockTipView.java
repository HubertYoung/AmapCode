package com.autonavi.minimap.route.sharebike.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class ShareRidingUnlockTipView extends RelativeLayout {
    private View mErrorReport;
    private TextView mFeeTimeCountDown;
    /* access modifiers changed from: private */
    public a mListener;
    private ShareRidingTorchView mTorchView;
    private TextView[] mUnlockNums;

    public interface a {
        void a();

        void a(boolean z);
    }

    public ShareRidingUnlockTipView(Context context) {
        super(context);
        init();
    }

    public ShareRidingUnlockTipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ShareRidingUnlockTipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void setOnUnlockingTipClickListener(a aVar) {
        this.mListener = aVar;
    }

    private void init() {
        initViews();
        initOnclickListener();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.route_share_riding_page_unlock_tip_layout, this, true);
        this.mUnlockNums = new TextView[4];
        this.mUnlockNums[0] = (TextView) findViewById(R.id.unlock_tip_text_1);
        this.mUnlockNums[1] = (TextView) findViewById(R.id.unlock_tip_text_2);
        this.mUnlockNums[2] = (TextView) findViewById(R.id.unlock_tip_text_3);
        this.mUnlockNums[3] = (TextView) findViewById(R.id.unlock_tip_text_4);
        for (TextView a2 : this.mUnlockNums) {
            efx.a(a2);
        }
        this.mFeeTimeCountDown = (TextView) findViewById(R.id.share_riding_unlock_count_down);
        this.mTorchView = (ShareRidingTorchView) findViewById(R.id.share_riding_unlock_torch);
        this.mErrorReport = findViewById(R.id.share_riding_unlock_error_report);
    }

    public void setTorch(boolean z) {
        if (this.mTorchView != null) {
            if (z) {
                this.mTorchView.openTorch();
                return;
            }
            this.mTorchView.closeTorch();
        }
    }

    private void initOnclickListener() {
        this.mTorchView.setOnTorchClickListener(new com.autonavi.minimap.route.sharebike.view.ShareRidingTorchView.a() {
            public final void a(boolean z) {
                if (ShareRidingUnlockTipView.this.mListener != null) {
                    ShareRidingUnlockTipView.this.mListener.a(z);
                }
            }
        });
        this.mErrorReport.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (ShareRidingUnlockTipView.this.mListener != null) {
                    ShareRidingUnlockTipView.this.mListener.a();
                }
            }
        });
    }

    public void showPWDCode(String str) {
        eao.e(getClass().getName(), "showPWDCode ---- > ".concat(String.valueOf(str)));
        if (!TextUtils.isEmpty(str) && str.length() == this.mUnlockNums.length) {
            for (int i = 0; i < this.mUnlockNums.length; i++) {
                TextView textView = this.mUnlockNums[i];
                StringBuilder sb = new StringBuilder();
                sb.append(str.charAt(i));
                textView.setText(sb.toString());
            }
        }
    }

    public void updateCountdownTime(int i) {
        eao.e(getClass().getName(), "updateCountdownTime ---- > ".concat(String.valueOf(i)));
        String str = "";
        if (i > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append("s后开始计费");
            str = sb.toString();
        }
        this.mFeeTimeCountDown.setText(str);
    }
}
