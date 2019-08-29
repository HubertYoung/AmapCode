package com.alipay.mobile.beehive.photo.view.video;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;

public class VideoCutWrapView extends FrameLayout {
    public static final int NUMBER_SPILT_PART = 12;
    public static final int NUMBER_WINDOW_PART = 10;
    private static final String TAG = "VideoCutWrapView";
    private static final int TIME_SHIFT_WHEN_HIT_END = 50;
    /* access modifiers changed from: private */
    public RecyclerView lvThumbList;
    /* access modifiers changed from: private */
    public VideoThumbAdapter mAdapter;
    private c mMeasureListener;
    /* access modifiers changed from: private */
    public float mMsPerPixel;
    private b mOnDragListener;
    /* access modifiers changed from: private */
    public OnVideoEditInfoUpdateListener mOnVideoEditInfoUpdateListener;
    /* access modifiers changed from: private */
    public boolean mScrollIdle;
    private OnScrollListener mScrollListener;
    /* access modifiers changed from: private */
    public int mScrollXRecord;
    /* access modifiers changed from: private */
    public float mStepDuration;
    /* access modifiers changed from: private */
    public int mUnitHeight;
    /* access modifiers changed from: private */
    public int mUnitWidth;
    /* access modifiers changed from: private */
    public int mVideoDuration;
    private VideoWindowView videoWindowView;

    public interface OnVideoEditInfoUpdateListener {
        void onEditStart(boolean z);

        void onVideoEditFinish(int i, int i2);
    }

    public abstract class VideoThumbAdapter extends Adapter<VideoThumbViewHolder> {
        public abstract int getMaxCutDuration();

        public abstract int getVideoDuration();

        public abstract void onBindImage(ImageView imageView, float f, int i, int i2);

        public VideoThumbAdapter() {
        }

        public VideoThumbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(VideoCutWrapView.this.getContext()).inflate(R.layout.view_video_thumb_item, parent, false);
            LayoutParams lp = itemView.getLayoutParams();
            lp.width = VideoCutWrapView.this.mUnitWidth;
            itemView.setLayoutParams(lp);
            return new VideoThumbViewHolder(itemView);
        }

        public void onBindViewHolder(VideoThumbViewHolder holder, int position) {
            if (position == 0 || position == getItemCount() - 1) {
                holder.videoThumb.setImageDrawable(null);
                return;
            }
            float shift = ((float) position) * VideoCutWrapView.this.mStepDuration;
            if (shift > ((float) (VideoCutWrapView.this.mVideoDuration - 50))) {
                shift = (float) (VideoCutWrapView.this.mVideoDuration - 50);
            }
            onBindImage(holder.videoThumb, shift, VideoCutWrapView.this.mUnitWidth, VideoCutWrapView.this.mUnitHeight);
        }

        public int getItemCount() {
            if (VideoCutWrapView.this.mVideoDuration <= getMaxCutDuration()) {
                return 12;
            }
            int thumbCount = ((int) Math.ceil((double) (((float) VideoCutWrapView.this.mVideoDuration) / (((float) VideoCutWrapView.this.mUnitWidth) * VideoCutWrapView.this.mMsPerPixel)))) + 2;
            PhotoLogger.d(VideoCutWrapView.TAG, VideoCutWrapView.this.mVideoDuration + "," + VideoCutWrapView.this.mUnitWidth + "," + VideoCutWrapView.this.mMsPerPixel + "," + thumbCount);
            return thumbCount;
        }
    }

    public class VideoThumbViewHolder extends ViewHolder {
        public ImageView videoThumb;

        public VideoThumbViewHolder(View itemView) {
            super(itemView);
            this.videoThumb = (ImageView) itemView.findViewById(R.id.iv_thumb);
        }
    }

    public VideoCutWrapView(Context context) {
        this(context, null);
    }

    public VideoCutWrapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoCutWrapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mScrollIdle = true;
        this.mMeasureListener = new c() {
            public final void a(int width, int unitWidth, int unitHeight, float msPerPixel) {
                PhotoLogger.d(VideoCutWrapView.TAG, "width = " + width + " unitWidth=" + unitWidth + " msPerPixel = " + msPerPixel);
                VideoCutWrapView.this.mMsPerPixel = msPerPixel;
                VideoCutWrapView.this.mUnitWidth = unitWidth;
                VideoCutWrapView.this.mUnitHeight = unitHeight;
                VideoCutWrapView.this.mStepDuration = ((float) unitWidth) * msPerPixel;
                LinearLayoutManager llManager = new LinearLayoutManager(VideoCutWrapView.this.getContext());
                llManager.setOrientation(0);
                VideoCutWrapView.this.lvThumbList.setLayoutManager(llManager);
                VideoCutWrapView.this.lvThumbList.setAdapter(VideoCutWrapView.this.mAdapter);
                VideoCutWrapView.this.calculateTimeWindowAndNotify();
            }
        };
        this.mOnDragListener = new b() {
            public final void a() {
                VideoCutWrapView.this.notifyStartEdit(true);
            }

            public final void b() {
                VideoCutWrapView.this.calculateTimeWindowAndNotify();
            }
        };
        this.mScrollListener = new OnScrollListener() {
            public final void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    VideoCutWrapView.this.mScrollIdle = true;
                    VideoCutWrapView.this.mScrollXRecord = recyclerView.computeHorizontalScrollOffset();
                    if (VideoCutWrapView.this.mOnVideoEditInfoUpdateListener != null) {
                        VideoCutWrapView.this.calculateTimeWindowAndNotify();
                        return;
                    }
                    return;
                }
                if (VideoCutWrapView.this.mScrollIdle) {
                    VideoCutWrapView.this.notifyStartEdit(false);
                }
                VideoCutWrapView.this.mScrollIdle = false;
            }

            public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        };
        LayoutInflater.from(getContext()).inflate(R.layout.view_video_cut_wrap, this, true);
        this.lvThumbList = (RecyclerView) findViewById(R.id.lv_thumb_list);
        this.videoWindowView = (VideoWindowView) findViewById(R.id.vwv_video_window);
    }

    private void startWork() {
        this.videoWindowView.setOnMeasureFinishListener(this.mMeasureListener);
        this.videoWindowView.setVideoDuration(this.mVideoDuration, this.mAdapter.getMaxCutDuration());
        this.videoWindowView.setOnDragWindowStatusChangeListener(this.mOnDragListener);
        this.lvThumbList.addOnScrollListener(this.mScrollListener);
    }

    /* access modifiers changed from: private */
    public void notifyStartEdit(boolean isWindowResize) {
        if (this.mOnVideoEditInfoUpdateListener != null) {
            this.mOnVideoEditInfoUpdateListener.onEditStart(isWindowResize);
        }
    }

    /* access modifiers changed from: private */
    public void calculateTimeWindowAndNotify() {
        if (this.mOnVideoEditInfoUpdateListener != null) {
            int begin = (int) (((float) (this.mScrollXRecord + this.videoWindowView.getLeftEdgeShift())) * this.mMsPerPixel);
            int end = (int) (((float) begin) + (((float) this.videoWindowView.getWindowWidth()) * this.mMsPerPixel));
            if (end > this.mVideoDuration) {
                PhotoLogger.d(TAG, "Cut to video duration ,from " + end + " to " + this.mVideoDuration);
                end = this.mVideoDuration;
            }
            this.mOnVideoEditInfoUpdateListener.onVideoEditFinish(begin, end);
        }
    }

    public void setOnVideoEditInfoUpdateListener(OnVideoEditInfoUpdateListener listener) {
        this.mOnVideoEditInfoUpdateListener = listener;
    }

    public void setVideoThumbAdapter(VideoThumbAdapter adapter) {
        this.mAdapter = adapter;
        this.mVideoDuration = this.mAdapter.getVideoDuration();
        startWork();
    }

    public void setPlayingProgress(int progress) {
        this.videoWindowView.setProgress(progress);
    }

    public int getPlayingProgress() {
        return this.videoWindowView.getProgress();
    }
}
