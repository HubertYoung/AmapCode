package com.alipay.mobile.beehive.audio.v2.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.audio.model.AudioDetail;
import com.alipay.mobile.beehive.audio.utils.BundleLogger;
import com.alipay.mobile.beehive.audio.v2.AudioPlayerStateDetector;
import com.alipay.mobile.beehive.audio.v2.GlobalAudioPlayer;
import com.alipay.mobile.beehive.audio.v2.PlayerState;
import java.util.Map;

public class AudioPlayButton extends ImageView implements OnClickListener {
    private AudioDetail mAudioDetail;
    private AudioPlayerStateDetector mDetector;
    private BundleLogger mLogger;
    private Drawable mPlayDrawable;
    private Drawable mStopDrawable;

    public AudioPlayButton(Context context) {
        this(context, null);
    }

    public AudioPlayButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioPlayButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mLogger = BundleLogger.getLogger(AudioPlayButton.class);
        setOnClickListener(this);
        this.mPlayDrawable = getResources().getDrawable(R.drawable.ic_start_play);
        this.mStopDrawable = getResources().getDrawable(R.drawable.ic_stop_play);
    }

    public void onClick(View v) {
        if (this.mAudioDetail == null || TextUtils.isEmpty(this.mAudioDetail.url)) {
            this.mLogger.d("No valid song bind.");
        } else if (!TextUtils.equals(GlobalAudioPlayer.getInstance().getDataSource(), this.mAudioDetail.url)) {
            GlobalAudioPlayer.getInstance().playAudio(this.mAudioDetail);
        } else if (getDrawable() == this.mStopDrawable) {
            GlobalAudioPlayer.getInstance().pauseAudio();
        } else {
            GlobalAudioPlayer.getInstance().playAudio(this.mAudioDetail);
        }
    }

    public boolean bindSong(AudioDetail audioDetail) {
        this.mAudioDetail = audioDetail;
        if (audioDetail == null || TextUtils.isEmpty(audioDetail.url)) {
            this.mLogger.d("AudioDetail is not valid.");
            if (this.mDetector != null) {
                this.mDetector.disActive();
            }
            return false;
        }
        if (this.mDetector != null) {
            if (!TextUtils.equals(audioDetail.url, this.mDetector.getCareWhichSong())) {
                this.mLogger.d("Care a new song,disActive the old one.");
                this.mDetector.disActive();
            } else {
                this.mLogger.d("Care the same song,do nothing.");
                return true;
            }
        }
        this.mDetector = new AudioPlayerStateDetector(audioDetail.url) {
            /* access modifiers changed from: protected */
            public final void onStateChange(AudioDetail audioDetail, PlayerState state, Map<String, Object> extra) {
                AudioPlayButton.this.onPlayerStateChange(audioDetail, state);
            }
        };
        if (this.mDetector.isActive()) {
            return true;
        }
        this.mDetector.active();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onPlayerStateChange(AudioDetail audioDetail, PlayerState state) {
        if (state == PlayerState.PLAYING || state == PlayerState.PREPARING) {
            setImageDrawable(this.mStopDrawable);
        } else {
            setImageDrawable(this.mPlayDrawable);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mDetector != null && this.mDetector.isActive()) {
            this.mDetector.disActive();
        }
    }
}
