package com.alipay.mobile.beehive.audio.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.beehive.audio.model.AudioDetail;
import com.alipay.mobile.beehive.audio.utils.APUtils;
import com.alipay.mobile.beehive.audio.utils.BundleLogger;
import com.alipay.mobile.beehive.audio.v2.AudioPlayerStateDetector;
import com.alipay.mobile.beehive.audio.v2.GlobalAudioPlayer;
import com.alipay.mobile.beehive.audio.v2.PlayerState;
import com.alipay.mobile.beehive.audio.v2.views.AutoBlurImageView;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseActivity;
import com.alipay.mobile.beehive.video.views.OriVideoPreviewCon;
import java.util.Map;

public class GeneralAudioPlayActivity extends BeehiveBaseActivity {
    private AutoBlurImageView ivAudioCover;
    private ImageView ivCoverEmpty;
    /* access modifiers changed from: private */
    public ImageView ivPlayControl;
    /* access modifiers changed from: private */
    public AudioDetail mAudioDetail;
    private AudioPlayerStateDetector mDetector = new AudioPlayerStateDetector(AudioPlayerStateDetector.CARE_EVERY_SONG) {
        /* access modifiers changed from: protected */
        public final void onStateChange(AudioDetail sd, PlayerState state, Map<String, Object> extra) {
            GeneralAudioPlayActivity.this.mAudioDetail = sd;
            GeneralAudioPlayActivity.this.renderAudioInfo(sd);
            GeneralAudioPlayActivity.this.renderPlayBtn(state);
        }
    };
    /* access modifiers changed from: private */
    public BundleLogger mLogger = BundleLogger.getLogger(GeneralAudioPlayActivity.class);
    /* access modifiers changed from: private */
    public Drawable mPauseDrawable;
    private Drawable mPlayDrawable;
    private SeekBar sbProgressControl;
    private OnSeekBarChangeListener seekListener = new OnSeekBarChangeListener() {
        public final void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        public final void onStartTrackingTouch(SeekBar seekBar) {
        }

        public final void onStopTrackingTouch(SeekBar seekBar) {
            if (GeneralAudioPlayActivity.this.mAudioDetail == null || GeneralAudioPlayActivity.this.mAudioDetail.duration <= 0) {
                GeneralAudioPlayActivity.this.mLogger.d("Song data src invalid,set to 0.");
                seekBar.setProgress(0);
                return;
            }
            int seekTo = (int) ((((long) seekBar.getProgress()) * GeneralAudioPlayActivity.this.mAudioDetail.duration) / 100);
            GeneralAudioPlayActivity.this.mLogger.d("Seek to " + seekTo + ", duration = " + GeneralAudioPlayActivity.this.mAudioDetail.duration);
            GlobalAudioPlayer.getInstance().seekTo(seekTo);
        }
    };
    private String songWithoutName;
    private AUTitleBar titleBar;
    private TextView tvAudioName;
    private TextView tvAuthor;
    private TextView tvDuration;
    private TextView tvExtraInfo;
    private TextView tvTimePassed;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPlayDrawable = getResources().getDrawable(R.drawable.ic_audio_play);
        this.mPauseDrawable = getResources().getDrawable(R.drawable.ic_audio_pause);
        this.songWithoutName = getResources().getString(R.string.str_no_name_song);
        initViews();
        pendingToPlay();
    }

    private void pendingToPlay() {
        this.mLogger.d("pendingToPlay:###");
        Bundle params = getIntent().getExtras();
        if (params == null) {
            this.mLogger.d("Invalid params.");
            return;
        }
        String audioUrl = params.getString(Constants.KEY_AUDIO_URL);
        if (TextUtils.isEmpty(audioUrl)) {
            this.mLogger.d("AudioUrl is empty,only display playing song..");
            return;
        }
        String audioName = params.getString(Constants.KEY_AUDIO_NAME);
        String singerName = params.getString(Constants.KEY_AUDIO_SINGER_NAME);
        String audioDescribe = params.getString(Constants.KEY_AUDIO_DESCRIBE);
        String audioLogoUrl = params.getString(Constants.KEY_AUDIO_LOGO_URL);
        String coverImgUrl = params.getString(Constants.KEY_AUDIO_COVER_IMAGE_URL);
        String appName = params.getString("appName");
        String appLogoUrl = params.getString(Constants.KEY_AUDIO_SHARE_BY_APP_LOGO_URL);
        String businessId = params.getString(Constants.KEY_AUDIO_BUSINESS_ID);
        AudioDetail detail = new AudioDetail();
        detail.name = audioName;
        detail.author = singerName;
        detail.coverImg = coverImgUrl;
        detail.url = audioUrl;
        detail.extraDesc = audioDescribe;
        detail.audioLogoUrl = audioLogoUrl;
        Bundle extra = new Bundle();
        extra.putString(Constants.KEY_AUDIO_BUSINESS_ID, businessId);
        extra.putString("appName", appName);
        extra.putString(Constants.KEY_AUDIO_SHARE_BY_APP_LOGO_URL, appLogoUrl);
        detail.extraInfo = extra;
        this.mLogger.d("Parsed song detail:" + detail.toString());
        GlobalAudioPlayer.getInstance().playAudio(detail);
    }

    private void initViews() {
        setContentView(R.layout.activity_general_audio_play);
        this.titleBar = (AUTitleBar) findViewById(R.id.tb_title_bar);
        this.ivCoverEmpty = (ImageView) findViewById(R.id.iv_cover_empty);
        this.titleBar.handleScrollChange(0, 1, null);
        this.sbProgressControl = (SeekBar) findViewById(R.id.sb_progress_control);
        this.sbProgressControl.setOnSeekBarChangeListener(this.seekListener);
        this.ivAudioCover = (AutoBlurImageView) findViewById(R.id.iv_audio_cover);
        this.ivPlayControl = (ImageView) findViewById(R.id.iv_play_btn);
        this.tvTimePassed = (TextView) findViewById(R.id.tv_played_time);
        this.tvDuration = (TextView) findViewById(R.id.tv_song_duration);
        this.tvAudioName = (TextView) findViewById(R.id.tv_audio_name);
        this.tvAuthor = (TextView) findViewById(R.id.tv_audio_author);
        this.tvExtraInfo = (TextView) findViewById(R.id.tv_audio_extra_info);
        this.ivPlayControl.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                if (GeneralAudioPlayActivity.this.ivPlayControl.getDrawable() == GeneralAudioPlayActivity.this.mPauseDrawable) {
                    GlobalAudioPlayer.getInstance().pauseAudio();
                } else {
                    GlobalAudioPlayer.getInstance().playAudio(GeneralAudioPlayActivity.this.mAudioDetail);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mDetector.active();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mDetector.disActive();
    }

    /* access modifiers changed from: private */
    public void renderAudioInfo(AudioDetail sd) {
        if (sd == null) {
            resetAudioInfo();
            return;
        }
        this.ivAudioCover.loadImage(sd.coverImg);
        this.tvTimePassed.setText(APUtils.formatDuration(sd.playedTime));
        this.tvDuration.setText(APUtils.formatDuration(sd.duration));
        this.tvAudioName.setText(TextUtils.isEmpty(sd.name) ? this.songWithoutName : sd.name);
        this.tvAuthor.setText(sd.author);
        this.tvExtraInfo.setText(sd.extraDesc);
        renderProgress(sd);
    }

    private void renderProgress(AudioDetail newer) {
        if (newer.duration > 0) {
            this.sbProgressControl.setProgress((int) ((newer.playedTime * 100) / newer.duration));
        } else {
            this.sbProgressControl.setProgress(0);
        }
    }

    /* access modifiers changed from: private */
    public void renderPlayBtn(PlayerState state) {
        switch (state) {
            case PAUSING:
            case STOPPED:
            case COMPLETE:
            case ERROR:
                this.ivPlayControl.setImageDrawable(this.mPlayDrawable);
                return;
            case PLAYING:
            case PREPARING:
                this.ivPlayControl.setImageDrawable(this.mPauseDrawable);
                return;
            default:
                return;
        }
    }

    private void resetAudioInfo() {
        this.ivAudioCover.removeImage();
        this.tvTimePassed.setText(OriVideoPreviewCon.ZERO_DURATION);
        this.tvDuration.setText(OriVideoPreviewCon.ZERO_DURATION);
        this.tvAudioName.setText("");
        this.tvAuthor.setText("");
        this.tvExtraInfo.setText("");
        this.sbProgressControl.setProgress(0);
        this.ivPlayControl.setImageDrawable(this.mPlayDrawable);
    }

    public void onPostBlur() {
        this.ivCoverEmpty.setVisibility(8);
        this.ivAudioCover.setBackgroundColor(0);
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3493";
    }
}
