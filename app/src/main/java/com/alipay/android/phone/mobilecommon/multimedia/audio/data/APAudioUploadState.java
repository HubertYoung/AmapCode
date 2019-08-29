package com.alipay.android.phone.mobilecommon.multimedia.audio.data;

public class APAudioUploadState {
    public static final int STATE_ERROR = 1;
    public static final int STATE_SUCCESS = 0;
    public static final int STATE_UNKNOWN = -1;
    public static final int STATE_UPLOADING = 2;
    public static final int STATE_UPLOAD_CANCEL = 3;
    private int state = -1;

    public APAudioUploadState(int state2) {
        this.state = state2;
    }

    public int getState() {
        return this.state;
    }

    public String toString() {
        return "APAudioUploadState{state=" + this.state + '}';
    }
}
