package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini;

import java.util.Arrays;

public class AudioRecordRule {
    private static final int[] a = {8000, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000};
    private static final int[] b = {16000, 16000, 24000, 24000, 32000, 32000, 48000, 64000, 64000};
    private static final int[] c = {48000, 48000, 64000, 96000, 128000, 128000, 192000, 320000, 320000};

    public static boolean checkAudioSampleRule(int sampleRate, int encodeBitRate) {
        boolean checkValidEncode = false;
        boolean checkValidSample = false;
        int i = 0;
        while (true) {
            if (i >= a.length) {
                break;
            } else if (sampleRate == a[i]) {
                checkValidSample = true;
                checkValidEncode = a(i, encodeBitRate);
                break;
            } else {
                i++;
            }
        }
        if (checkValidSample) {
            return checkValidEncode;
        }
        throw new IllegalArgumentException("invalid sampleRate " + sampleRate + ",sampleRate should be one of " + Arrays.toString(a));
    }

    private static boolean a(int i, int encodeBitRate) {
        int minValue = b[i];
        int maxValue = c[i];
        if (encodeBitRate >= minValue && encodeBitRate <= maxValue) {
            return true;
        }
        throw new IllegalArgumentException("invalid encodeBitRate " + encodeBitRate + ",encodeBitRate should be greater than " + minValue + " and less than " + maxValue);
    }

    public static boolean checkDuration(int minDuration, int maxDuration) {
        if (minDuration <= maxDuration) {
            return minDuration <= maxDuration;
        }
        throw new IllegalArgumentException("invalid duration " + maxDuration + ",duration should be in [1000ms,3*60*1000ms]");
    }

    public static void checkFrameSize(int frameSize) {
        if (frameSize <= 0 || frameSize > 2097152) {
            throw new IllegalArgumentException("invalid frameSize " + frameSize + ", frameSize should be greater than 0 and less than 2097152B(2MB)");
        }
    }
}
