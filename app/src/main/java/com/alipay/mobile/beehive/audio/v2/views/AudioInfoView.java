package com.alipay.mobile.beehive.audio.v2.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.audio.activity.GeneralAudioPlayActivity;
import com.alipay.mobile.beehive.audio.model.AudioDetail;
import com.alipay.mobile.beehive.audio.utils.BundleLogger;
import com.alipay.mobile.beehive.audio.v2.AudioPlayerStateDetector;
import com.alipay.mobile.beehive.audio.v2.PlayerState;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.Map;

public class AudioInfoView extends FrameLayout {
    /* access modifiers changed from: private */
    public ImageView ivAudioPlaying;
    private AudioPlayerStateDetector mDetector;
    private BundleLogger mLogger;
    /* access modifiers changed from: private */
    public AnimationDrawable mPlayingDrawable;
    private String mSongNameUnKnow;
    private TextView tvAudioName;
    public View vSeparate;

    public AudioInfoView(Context context) {
        this(context, null);
    }

    public AudioInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mLogger = BundleLogger.getLogger(AudioInfoView.class);
        this.mDetector = new AudioPlayerStateDetector(AudioPlayerStateDetector.CARE_EVERY_SONG) {
            /* access modifiers changed from: protected */
            public final void onStateChange(AudioDetail audioDetail, PlayerState state, Map<String, Object> extra) {
                if (audioDetail == null || !(state == PlayerState.PLAYING || state == PlayerState.PREPARING)) {
                    AudioInfoView.this.setVisibility(8);
                    if (AudioInfoView.this.mPlayingDrawable != null) {
                        AudioInfoView.this.mPlayingDrawable.stop();
                        return;
                    }
                    return;
                }
                AudioInfoView.this.setVisibility(0);
                AudioInfoView.this.renderSongName(audioDetail);
                if (AudioInfoView.this.mPlayingDrawable == null) {
                    AudioInfoView.this.ivAudioPlaying.setImageDrawable(AudioInfoView.this.getResources().getDrawable(R.drawable.anim_audio_playing));
                    AudioInfoView.this.mPlayingDrawable = (AnimationDrawable) AudioInfoView.this.ivAudioPlaying.getDrawable();
                }
                AudioInfoView.this.mPlayingDrawable.start();
            }
        };
        init(context);
    }

    private void init(Context context) {
        this.mSongNameUnKnow = context.getString(R.string.str_no_name_song);
        LayoutInflater.from(context).inflate(R.layout.view_audio_info, this, true);
        this.tvAudioName = (TextView) findViewById(R.id.tv_audio_name);
        this.vSeparate = findViewById(R.id.v_separate);
        this.ivAudioPlaying = (ImageView) findViewById(R.id.iv_audio_playing);
        setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                AudioInfoView.this.jumpToDetail();
            }
        });
    }

    public void setSeparateColor(int color) {
        this.vSeparate.setBackgroundColor(color);
    }

    /* access modifiers changed from: private */
    public void renderSongName(AudioDetail audioDetail) {
        if (TextUtils.isEmpty(audioDetail.name)) {
            this.tvAudioName.setText(this.mSongNameUnKnow);
        } else if (audioDetail.name != this.tvAudioName.getTag()) {
            this.tvAudioName.setText(audioDetail.name);
            this.tvAudioName.setTag(audioDetail.name);
        }
    }

    /* access modifiers changed from: private */
    public void jumpToDetail() {
        this.mLogger.d("jumpToDetail:###");
        getContext().startActivity(new Intent(LauncherApplicationAgent.getInstance().getApplicationContext(), GeneralAudioPlayActivity.class));
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mDetector.active();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDetector.disActive();
    }
}
