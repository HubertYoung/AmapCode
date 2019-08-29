package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic;

import com.alipay.android.phone.mobilecommon.multimedia.video.data.LiveData;
import tv.danmaku.ijk.media.encode.LiveCounter;

public class LiveStatistic {
    public static LiveData convertToLiveStatsItem(LiveCounter curCounter, LiveCounter lastCounter, long timeInterval) {
        LiveData item = new LiveData();
        if (curCounter.isRecord()) {
            item.mTotalFrames = (long) curCounter.videoFrameCount;
            item.mTotalSize = curCounter.streamSendSize;
            item.mTotalTime = (long) curCounter.videoFrameProcessTime;
            item.mTimeInterval = timeInterval;
            if (curCounter != null && lastCounter != null) {
                item.mSizeInterval = curCounter.streamSendSize - lastCounter.streamSendSize;
                item.mFramesInterval = (long) (curCounter.videoFrameCount - lastCounter.videoFrameCount);
            } else if (lastCounter == null) {
                item.mSizeInterval = curCounter.streamSendSize;
                item.mFramesInterval = (long) curCounter.videoFrameCount;
            }
        } else {
            item.mTotalSize = curCounter.videoCachedBytes + curCounter.audioCachedBytes;
            item.mTotalTime = curCounter.videoCachedDuration;
            item.mTimeInterval = timeInterval;
            if (curCounter != null && lastCounter != null) {
                item.mSizeInterval = item.mTotalSize - (lastCounter.videoCachedBytes + lastCounter.audioCachedBytes);
            } else if (lastCounter == null) {
                item.mSizeInterval = item.mTotalSize;
            }
        }
        return item;
    }
}
