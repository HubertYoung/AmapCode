package com.alipay.streammedia.video.editor;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.MMNativeException.NativeExceptionCode;
import com.alipay.streammedia.utils.SoLoadLock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import tv.danmaku.ijk.media.player.IjkLibLoader;

public class NativeVideoEditor {
    private static final String TAG = "NativeVideoEditor";
    private static volatile boolean mIsLibLoaded = false;
    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() {
        public final void loadLibrary(String libName) {
            System.loadLibrary(libName);
        }
    };

    static native long getCurCompressPtsNative(long j);

    private static native CutResult nativeCut(CutParam cutParam);

    static native PickFrameResult nativePickFrameByIndex(PickFrameParam pickFrameParam, Bitmap bitmap);

    static native VideoGetFrameResult pickerGetFrame(VideoPicker videoPicker, long j, Bitmap bitmap);

    static native int pickerInit(VideoPicker videoPicker, PickerParam pickerParam);

    static native int pickerRelease(VideoPicker videoPicker);

    static native VideoSeekResult pickerSeek(VideoPicker videoPicker, long j);

    public static void loadLibrariesOnce(IjkLibLoader libLoader) {
        synchronized (SoLoadLock.class) {
            if (!mIsLibLoaded) {
                if (libLoader == null) {
                    libLoader = sLocalLibLoader;
                }
                try {
                    libLoader.loadLibrary("ijkeditor");
                    mIsLibLoaded = true;
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
    }

    private static List<String> parseBlacklist(String cloudConfig) {
        if (cloudConfig != null) {
            try {
                if (!cloudConfig.isEmpty()) {
                    Map map = (Map) JSON.parseObject(cloudConfig, (TypeReference<T>) new TypeReference<Map<String, String>>() {
                    }, new Feature[0]);
                    if (map == null) {
                        return null;
                    }
                    String bstr = (String) map.get("black");
                    if (bstr == null || bstr.isEmpty()) {
                        return null;
                    }
                    return (List) JSON.parseObject(bstr, (TypeReference<T>) new TypeReference<List<String>>() {
                    }, new Feature[0]);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @TargetApi(16)
    public static boolean isSupportMeidaCodec(String cloudConfig) {
        List<String> blacklist = parseBlacklist(cloudConfig);
        if (TextUtils.isEmpty("video/avc")) {
            return false;
        }
        ArrayList candidateCodecList = new ArrayList();
        int numCodecs = MediaCodecList.getCodecCount();
        for (int i = 0; i < numCodecs; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfo.isEncoder()) {
                String[] types = codecInfo.getSupportedTypes();
                if (types != null) {
                    for (String type : types) {
                        if (!TextUtils.isEmpty(type) && type.equalsIgnoreCase("video/avc")) {
                            FFmpegMediaCodecInfo candidate = FFmpegMediaCodecInfo.setupCandidate(codecInfo, "video/avc");
                            if (candidate != null) {
                                candidateCodecList.add(candidate);
                                candidate.dumpProfileLevels("video/avc");
                            }
                        }
                    }
                }
            }
        }
        if (candidateCodecList.isEmpty()) {
            return false;
        }
        FFmpegMediaCodecInfo bestCodec = (FFmpegMediaCodecInfo) candidateCodecList.get(0);
        Iterator it = candidateCodecList.iterator();
        while (it.hasNext()) {
            FFmpegMediaCodecInfo codec = (FFmpegMediaCodecInfo) it.next();
            if (codec.mRank > bestCodec.mRank) {
                bestCodec = codec;
            }
        }
        if (bestCodec.mRank < 600) {
            return false;
        }
        if (blacklist != null && !blacklist.isEmpty()) {
            for (String equalsIgnoreCase : blacklist) {
                if (equalsIgnoreCase.equalsIgnoreCase(bestCodec.mCodecInfo.getName())) {
                    return false;
                }
            }
        }
        Log.d(TAG, "codec name:" + bestCodec.mCodecInfo.getName() + ", rank:" + bestCodec.mRank);
        return true;
    }

    public static CutResult cut(CutParam param) {
        try {
            return nativeCut(param);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static PickFrameResult pickFrameByIndex(PickFrameParam param, Bitmap bitmap) {
        try {
            return nativePickFrameByIndex(param, bitmap);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static long getCurCompressPts(long videoId) {
        try {
            return getCurCompressPtsNative(videoId);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }
}
