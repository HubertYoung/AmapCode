package com.alipay.android.phone.mobilecommon.multimedia.audio.data;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.BaseInfo;

public class APAudioInfo extends BaseInfo {
    public static final int INT_UPLOAD_TYPE_FILE = 0;
    public static final int INT_UPLOAD_TYPE_SYNC = 1;
    public static final String KEY_UPLOAD_TYPE = "uploadType";
    private long fileSize;
    private String mCloudId;
    private int mDuration;
    private Bundle mExtra;
    private String mFileMD5;
    private String mLocalId;
    private AudioOptions mOptions;
    private String mPath;
    private int mProgressUpdateInterval;
    private int mRecordMaxTime;
    private int mRecordMinTime;
    private String mSavePath;
    private boolean mSyncUpload;
    private APAudioUploadState mUploadState;
    public boolean pauseThirdAudio;
    private boolean skipRecordPermissionTimeout;

    public static class AudioOptions {
        public static final int MAX_DURATION = 180000;
        AudioSource audioSource;
        int duration;
        int encodeBitRate;
        AudioFormat format;
        int frameSize;
        int numberOfChannels;
        int sampleRate;

        public static class Builder {
            AudioSource audioSource = AudioSource.SOURCE_AUTO;
            int duration = 60000;
            int encodeBitRate = 32000;
            AudioFormat format = AudioFormat.AAC;
            int frameSize = 1024;
            int numberOfChannels = 1;
            int sampleRate = 16000;

            public Builder duration(int duration2) {
                this.duration = duration2;
                if (this.duration > 180000) {
                    this.duration = AudioOptions.MAX_DURATION;
                }
                return this;
            }

            public Builder sampleRate(int sampleRate2) {
                this.sampleRate = sampleRate2;
                return this;
            }

            public Builder channels(int numberOfChannels2) {
                this.numberOfChannels = numberOfChannels2;
                return this;
            }

            public Builder encodeBitRate(int encodeBitRate2) {
                this.encodeBitRate = encodeBitRate2;
                return this;
            }

            public Builder format(AudioFormat format2) {
                this.format = format2;
                return this;
            }

            public Builder frameSize(int frameSize2) {
                this.frameSize = frameSize2;
                return this;
            }

            public Builder audioSource(AudioSource source) {
                this.audioSource = source;
                return this;
            }

            public AudioOptions build() {
                return new AudioOptions(this);
            }
        }

        public int getDuration() {
            return this.duration;
        }

        public int getSampleRate() {
            return this.sampleRate;
        }

        public int getNumberOfChannels() {
            return this.numberOfChannels;
        }

        public int getEncodeBitRate() {
            return this.encodeBitRate;
        }

        public AudioFormat getFormat() {
            return this.format;
        }

        public int getFrameSize() {
            return this.frameSize;
        }

        public AudioSource getAudioSource() {
            return this.audioSource;
        }

        private AudioOptions(Builder builder) {
            this.duration = builder.duration;
            this.sampleRate = builder.sampleRate;
            this.numberOfChannels = builder.numberOfChannels;
            this.encodeBitRate = builder.encodeBitRate;
            this.format = builder.format;
            this.frameSize = builder.frameSize;
            this.audioSource = builder.audioSource;
        }
    }

    public enum AudioSource {
        SOURCE_AUTO(0),
        SOURCE_MIC(1),
        SOURCE_CAMCORDER(5),
        SOURCE_VOICE_RECOGNITION(6),
        SOURCE_VOICE_COMMUNICATION(7);
        
        int val;

        private AudioSource(int val2) {
            this.val = val2;
        }

        public final int value() {
            return this.val;
        }
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long fileSize2) {
        this.fileSize = fileSize2;
    }

    public static APAudioInfo fromPath(String path) {
        return new APAudioInfo(path);
    }

    public static APAudioInfo fromCloudId(String cloudId) {
        return new APAudioInfo(null, cloudId, null);
    }

    public static APAudioInfo fromLocalId(String localId) {
        return new APAudioInfo(localId, null, null);
    }

    public APAudioInfo() {
        this(null, null, null);
    }

    public APAudioInfo(String savePath) {
        this(null, null, savePath);
    }

    public APAudioInfo(String localId, String savePath) {
        this(localId, null, savePath);
    }

    public APAudioInfo(String localId, String cloudId, String path) {
        this.mRecordMinTime = 1000;
        this.mRecordMaxTime = 60000;
        this.mProgressUpdateInterval = 1000;
        this.mSyncUpload = false;
        this.mExtra = new Bundle();
        this.pauseThirdAudio = true;
        this.skipRecordPermissionTimeout = false;
        this.mLocalId = localId;
        this.mCloudId = cloudId;
        setSavePath(path);
    }

    public String getLocalId() {
        return this.mLocalId;
    }

    public void setLocalId(String localId) {
        this.mLocalId = localId;
    }

    public String getCloudId() {
        return this.mCloudId;
    }

    public void setCloudId(String cloudId) {
        this.mCloudId = cloudId;
    }

    public String getSavePath() {
        return this.mSavePath;
    }

    public void setSavePath(String savePath) {
        this.mSavePath = savePath;
        setPath(savePath);
    }

    public String getPath() {
        return this.mPath;
    }

    public void setPath(String path) {
        this.mPath = path;
    }

    public String getFileMD5() {
        return this.mFileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.mFileMD5 = fileMD5;
    }

    public int getRecordMinTime() {
        return this.mRecordMinTime;
    }

    public void setRecordMinTime(int recordMinTime) {
        this.mRecordMinTime = recordMinTime;
    }

    public int getRecordMaxTime() {
        return this.mRecordMaxTime;
    }

    public void setRecordMaxTime(int recordMaxTime) {
        this.mRecordMaxTime = recordMaxTime;
    }

    public int getProgressUpdateInterval() {
        return this.mProgressUpdateInterval;
    }

    public void setProgressUpdateInterval(int progressUpdateInterval) {
        this.mProgressUpdateInterval = progressUpdateInterval;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public boolean isSyncUpload() {
        return this.mSyncUpload;
    }

    public void setSyncUpload(boolean syncUpload) {
        this.mSyncUpload = syncUpload;
    }

    public Bundle getExtra() {
        return this.mExtra;
    }

    public APAudioUploadState getUploadState() {
        return this.mUploadState;
    }

    public void setUploadState(APAudioUploadState uploadState) {
        this.mUploadState = uploadState;
    }

    public void setSkipRecordPermissionTimeout(boolean bSkip) {
        this.skipRecordPermissionTimeout = bSkip;
    }

    public boolean getSkipRecordPermissionTimeout() {
        return this.skipRecordPermissionTimeout;
    }

    public AudioOptions getAudioOptions() {
        return this.mOptions;
    }

    public void setAudioOptions(AudioOptions mOptions2) {
        this.mOptions = mOptions2;
    }

    public String toString() {
        return "APAudioInfo{mLocalId='" + this.mLocalId + '\'' + ", mCloudId='" + this.mCloudId + '\'' + ", mSavePath='" + this.mSavePath + '\'' + ", mPath='" + this.mPath + '\'' + ", mFileMD5='" + this.mFileMD5 + '\'' + ", mRecordMinTime=" + this.mRecordMinTime + ", mRecordMaxTime=" + this.mRecordMaxTime + ", mProgressUpdateInterval=" + this.mProgressUpdateInterval + ", mDuration=" + this.mDuration + ", mSyncUpload=" + this.mSyncUpload + ", mExtra=" + this.mExtra + ", mUploadState=" + this.mUploadState + ", skipRecordPermissionTimeout=" + this.skipRecordPermissionTimeout + '}' + super.toString();
    }
}
