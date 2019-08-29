package tv.danmaku.ijk.media.encode;

public interface VideoRecordListener {
    boolean isAudioStart();

    void onAudioStart();

    void onAudioTimeUpdate(long j);

    void onGetCount(LiveCounter liveCounter, long j, int i, boolean z);

    void onPutError(int i);

    void onVideoTimeUpdate(long j);
}
