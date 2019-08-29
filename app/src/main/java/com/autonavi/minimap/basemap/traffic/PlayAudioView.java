package com.autonavi.minimap.basemap.traffic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class PlayAudioView extends RelativeLayout {
    private static final int DURATION = 300;
    private AnimationDrawable animationDrawable = null;
    private OnClickListener clickListener = new OnClickListener() {
        public final void onClick(View view) {
            if (PlayAudioView.this.mPlayLayout == view && PlayAudioView.this.clickPlayListener != null && PlayAudioView.this.mLoad.getVisibility() != 0) {
                PlayAudioView.this.clickPlayListener;
            }
        }
    };
    /* access modifiers changed from: private */
    public a clickPlayListener = null;
    private Handler handler = new Handler();
    private boolean isSmall = false;
    private Context mContext;
    private ImageView mImgStates;
    /* access modifiers changed from: private */
    public ProgressBar mLoad;
    private RelativeLayout mParentLayout;
    /* access modifiers changed from: private */
    public RelativeLayout mPlayLayout = null;
    private TextView mTxtTime;
    private ImageView playAnim;

    public interface a {
    }

    public PlayAudioView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public PlayAudioView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView(context);
    }

    public PlayAudioView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PlayAudioView);
        this.isSmall = obtainStyledAttributes.getBoolean(R.styleable.PlayAudioView_is_small, false);
        obtainStyledAttributes.recycle();
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        if (!this.isSmall) {
            this.mParentLayout = (RelativeLayout) layoutInflater.inflate(R.layout.play_layout, null);
        }
        addView(this.mParentLayout, new LayoutParams(-2, -2));
        this.mPlayLayout = (RelativeLayout) this.mParentLayout.findViewById(R.id.layout_audio_play);
        this.mPlayLayout.setOnClickListener(this.clickListener);
        this.playAnim = (ImageView) this.mParentLayout.findViewById(R.id.img_audio_play);
        this.playAnim.setVisibility(4);
        this.mImgStates = (ImageView) this.mParentLayout.findViewById(R.id.img_audio_states);
        this.mImgStates.setVisibility(0);
        this.animationDrawable = (AnimationDrawable) this.playAnim.getBackground();
        this.mLoad = (ProgressBar) this.mParentLayout.findViewById(R.id.progress_load_item);
        this.mLoad.setVisibility(4);
        this.mTxtTime = (TextView) this.mParentLayout.findViewById(R.id.txt_audio_length);
    }

    public void setTime(String str) {
        this.mTxtTime.setText(str);
    }

    public void play() {
        this.playAnim.setVisibility(0);
        this.mImgStates.setVisibility(4);
        if (this.animationDrawable != null) {
            this.animationDrawable.start();
        }
    }

    public void stop() {
        if (this.animationDrawable != null && this.animationDrawable.isRunning()) {
            this.animationDrawable.stop();
        }
        this.playAnim.setVisibility(4);
        this.mImgStates.setVisibility(0);
    }

    public void loading() {
        this.playAnim.setVisibility(4);
        this.mImgStates.setVisibility(4);
        this.mLoad.setVisibility(0);
    }

    public void init() {
        this.playAnim.setVisibility(4);
        this.mImgStates.setVisibility(0);
        this.mLoad.setVisibility(4);
    }

    public void setClickPlayListener(a aVar) {
        this.clickPlayListener = aVar;
    }

    public boolean isAnimRunning() {
        return this.animationDrawable.isRunning();
    }

    private AnimationDrawable initAnim() {
        AnimationDrawable animationDrawable2 = new AnimationDrawable();
        animationDrawable2.addFrame(this.mContext.getResources().getDrawable(R.drawable.tmc_voice_playing), 300);
        animationDrawable2.addFrame(this.mContext.getResources().getDrawable(R.drawable.tmc_voice_normal), 300);
        animationDrawable2.addFrame(this.mContext.getResources().getDrawable(R.drawable.tmc_voice_playing_1), 300);
        animationDrawable2.addFrame(this.mContext.getResources().getDrawable(R.drawable.tmc_voice_playing_2), 300);
        animationDrawable2.setOneShot(false);
        return animationDrawable2;
    }
}
