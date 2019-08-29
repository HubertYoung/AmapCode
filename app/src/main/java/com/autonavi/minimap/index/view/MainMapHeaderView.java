package com.autonavi.minimap.index.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.CircleImageView;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public class MainMapHeaderView extends LinearLayout {
    private ImageView mDefaultPortraitImageView;
    private boolean mIsShowMitEmoji;
    private View mPortraitContainerView;
    /* access modifiers changed from: private */
    public a mPortraitImageCallback;
    /* access modifiers changed from: private */
    public CircleImageView mPortraitImageView;
    private ImageView mPortraitMaskView;
    private ImageView mPortraitTipsView;
    private View mQrScanEntryBtn;
    private TextView mSearchTextView;
    private RelativeLayout mUserLevelLayout;
    private TextView mUserLevelText;
    private VUIEmojiView mVoiceEmojiView;

    static final class a implements bkf {
        private WeakReference<MainMapHeaderView> a;

        public final void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
        }

        public final void onPrepareLoad(Drawable drawable) {
        }

        public a(MainMapHeaderView mainMapHeaderView) {
            this.a = new WeakReference<>(mainMapHeaderView);
        }

        public final void onBitmapFailed(Drawable drawable) {
            MainMapHeaderView mainMapHeaderView = (MainMapHeaderView) this.a.get();
            if (mainMapHeaderView != null) {
                mainMapHeaderView.setPortraitDefaultStyle();
            }
        }
    }

    public void setVoiceSearchClickListener(OnClickListener onClickListener) {
    }

    public void setVoiceSearchTipsVisibility(int i) {
    }

    public MainMapHeaderView(Context context) {
        this(context, null);
    }

    public MainMapHeaderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MainMapHeaderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPortraitImageCallback = new a(this);
        init(context);
    }

    private void init(final Context context) {
        View.inflate(context, R.layout.default_fragment_header_layout, this);
        this.mPortraitContainerView = findViewById(R.id.rl_mine_container);
        if (bno.a) {
            this.mPortraitContainerView.setOnLongClickListener(new OnLongClickListener() {
                public final boolean onLongClick(View view) {
                    awh awh = (awh) defpackage.esb.a.a.a(awh.class);
                    if (awh != null) {
                        awh.a(context);
                    }
                    return false;
                }
            });
        }
        this.mDefaultPortraitImageView = (ImageView) findViewById(R.id.iv_mine_default);
        this.mPortraitImageView = (CircleImageView) findViewById(R.id.iv_mine);
        this.mPortraitImageView.setBorderWidth(1);
        this.mPortraitImageView.setBorderColor(getContext().getResources().getColor(R.color.c_5_d));
        this.mPortraitImageView.setScaleType(ScaleType.CENTER_CROP);
        this.mPortraitMaskView = (ImageView) findViewById(R.id.iv_mine_mask);
        this.mPortraitTipsView = (ImageView) findViewById(R.id.iv_mine_tips);
        this.mVoiceEmojiView = (VUIEmojiView) findViewById(R.id.btn_emoji);
        this.mIsShowMitEmoji = VUIStateManager.f().m();
        if (this.mIsShowMitEmoji) {
            this.mVoiceEmojiView.setShowInit(true);
            this.mVoiceEmojiView.setVisibility(0);
        } else {
            this.mVoiceEmojiView.setVisibility(8);
        }
        this.mSearchTextView = (TextView) findViewById(R.id.btn_search);
        this.mQrScanEntryBtn = findViewById(R.id.qrscan_entry_btn);
        this.mQrScanEntryBtn.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!bnp.a(view.getId())) {
                    awt awt = (awt) defpackage.esb.a.a.a(awt.class);
                    if (awt != null) {
                        awt.a((String) "main");
                    }
                }
            }
        });
        this.mUserLevelLayout = (RelativeLayout) findViewById(R.id.iv_mine_level);
        this.mUserLevelText = (TextView) findViewById(R.id.iv_mine_level_text);
    }

    public void updatePortrait(final String str) {
        if (TextUtils.isEmpty(str)) {
            this.mDefaultPortraitImageView.setVisibility(0);
            this.mPortraitImageView.setVisibility(8);
            this.mPortraitMaskView.setVisibility(8);
            return;
        }
        this.mDefaultPortraitImageView.setVisibility(8);
        this.mPortraitImageView.setVisibility(0);
        this.mPortraitMaskView.setVisibility(0);
        this.mPortraitImageView.post(new Runnable() {
            public final void run() {
                ko.a(MainMapHeaderView.this.mPortraitImageView, str, null, 0, MainMapHeaderView.this.mPortraitImageCallback);
            }
        });
        if (this.mPortraitImageView.getDrawable() == null) {
            this.mPortraitImageView.setImageDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.c_14)));
        }
    }

    public void updateUserLevel(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mUserLevelLayout.setVisibility(8);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "等级挂件");
        } catch (JSONException unused) {
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_GRADE_PENDANT, jSONObject);
        this.mUserLevelLayout.setVisibility(0);
        this.mUserLevelText.setText("Lv.".concat(String.valueOf(str)));
    }

    public void setPortriatTipsVisibility(int i) {
        this.mPortraitTipsView.setVisibility(i);
    }

    public int getPortriatTipsVisibility() {
        return this.mPortraitTipsView.getVisibility();
    }

    public void setPortraitClickListener(OnClickListener onClickListener) {
        this.mPortraitContainerView.setOnClickListener(onClickListener);
    }

    public View getVUIEmojiView() {
        return this.mVoiceEmojiView;
    }

    /* access modifiers changed from: private */
    public void setPortraitDefaultStyle() {
        this.mDefaultPortraitImageView.setVisibility(0);
        this.mPortraitImageView.setVisibility(8);
        this.mPortraitMaskView.setVisibility(8);
    }
}
