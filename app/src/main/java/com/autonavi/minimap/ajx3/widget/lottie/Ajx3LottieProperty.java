package com.autonavi.minimap.ajx3.widget.lottie;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.airbnb.lottie.LottieAnimationView.CacheStrategy;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import java.io.File;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public class Ajx3LottieProperty extends BaseProperty<Ajx3Lottie> {
    private static final String ADD_ANIMATOR_LISTENER_ON_CANCEL = "lottieCancel";
    private static final String ADD_ANIMATOR_LISTENER_ON_END = "lottieEnd";
    private static final String ADD_ANIMATOR_LISTENER_ON_REPEAT = "lottieRepeat";
    private static final String ADD_ANIMATOR_LISTENER_ON_START = "lottieStart";
    private static final String LOTTIE_ASSETS_PARENT_FOLDER = "lottie/ajx";
    private static final String LOTTIE_DEFAULT_LOADING_PATH = "/feedback_poi/loading-black.json";
    private static final String LOTTIE_PROPERTY_ADD_ANIMATOR_LISTENER = "animation";
    private static final String LOTTIE_PROPERTY_CANCEL = "cancel";
    private static final String LOTTIE_PROPERTY_DEFAULT = "default";
    private static final String LOTTIE_PROPERTY_LISTENER_STATE = "state";
    private static final String LOTTIE_PROPERTY_LOOP = "loop";
    private static final String LOTTIE_PROPERTY_ON_DOWNLOAD_SRC_FINISH = "loadFinish";
    private static final String LOTTIE_PROPERTY_ON_ERROR = "onError";
    private static final String LOTTIE_PROPERTY_PAUSE = "pause";
    private static final String LOTTIE_PROPERTY_PLAY = "play";
    private static final String LOTTIE_PROPERTY_PROGRESS = "progress";
    private static final String LOTTIE_PROPERTY_REMOVE_ANIMATOR_LISTENER = "removeAnimatorListener";
    private static final String LOTTIE_PROPERTY_RESUME = "resume";
    private static final String LOTTIE_PROPERTY_SET_DATA = "data";
    private static final String LOTTIE_PROPERTY_SRC = "src";
    private static final String LOTTIE_PROPERTY_STATUS = "status";
    private static final String PLAY_PROGRESS_END = "endPos";
    private static final String PLAY_PROGRESS_START = "startPos";
    private static final String SET_DATA_FILE_PATH = "filePath";
    private static final String SET_DATA_IMAGE_PATH = "imagePath";
    private static final String TAG = "Ajx3LottieProperty";
    private final float MAX_PROGRESS = 1.0f;
    private final float MIN_PROGRESS = 0.0f;
    private AnimationListener mAjxAnimationListener;
    private boolean mHasSetOnDownloadFinish = false;
    private boolean mHasSetOnError = false;
    private boolean mHaveAddListener = false;
    /* access modifiers changed from: private */
    public boolean mIsLoading = false;
    private Context mNativeContext;
    /* access modifiers changed from: private */
    public volatile boolean mPause = false;

    static class AnimationListener implements AnimatorListener {
        private WeakReference<Ajx3LottieProperty> mAjx3Property;

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public AnimationListener(Ajx3LottieProperty ajx3LottieProperty) {
            this.mAjx3Property = new WeakReference<>(ajx3LottieProperty);
        }

        public void onAnimationStart(Animator animator) {
            Ajx3LottieProperty ajx3LottieProperty = (Ajx3LottieProperty) this.mAjx3Property.get();
            if (ajx3LottieProperty != null) {
                ajx3LottieProperty.native2AjxDataOrEvent(Ajx3LottieProperty.LOTTIE_PROPERTY_ADD_ANIMATOR_LISTENER, Ajx3LottieProperty.LOTTIE_PROPERTY_LISTENER_STATE, Ajx3LottieProperty.ADD_ANIMATOR_LISTENER_ON_START);
            }
        }

        public void onAnimationEnd(Animator animator) {
            Ajx3LottieProperty ajx3LottieProperty = (Ajx3LottieProperty) this.mAjx3Property.get();
            if (ajx3LottieProperty != null && !ajx3LottieProperty.mPause) {
                ajx3LottieProperty.native2AjxDataOrEvent(Ajx3LottieProperty.LOTTIE_PROPERTY_ADD_ANIMATOR_LISTENER, Ajx3LottieProperty.LOTTIE_PROPERTY_LISTENER_STATE, Ajx3LottieProperty.ADD_ANIMATOR_LISTENER_ON_END);
            }
        }
    }

    private void updateDefaultProperty(Object obj) {
    }

    public Ajx3LottieProperty(@NonNull Ajx3Lottie ajx3Lottie, @NonNull IAjxContext iAjxContext) {
        super(ajx3Lottie, iAjxContext);
        this.mNativeContext = iAjxContext.getNativeContext();
        this.mAjxAnimationListener = new AnimationListener(this);
        ((Ajx3Lottie) this.mView).setDrawingCacheEnabled(true);
        ((Ajx3Lottie) this.mView).useHardwareAcceleration(true);
        native2AjxDataOrEvent("", "progress", "0.0");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00c8, code lost:
        if (r8.equals("pause") == false) goto L_0x00df;
     */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00e4  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00ee  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00f8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r7, java.lang.Object r8) {
        /*
            r6 = this;
            int r0 = r7.hashCode()
            r1 = 2
            r2 = -1
            r3 = 1
            r4 = 0
            switch(r0) {
                case -1349867671: goto L_0x006a;
                case -1001078227: goto L_0x0060;
                case -892481550: goto L_0x0055;
                case -598247143: goto L_0x004a;
                case 114148: goto L_0x003f;
                case 3076010: goto L_0x0035;
                case 3327652: goto L_0x002b;
                case 3443508: goto L_0x0021;
                case 1118509956: goto L_0x0017;
                case 1544803905: goto L_0x000d;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0075
        L_0x000d:
            java.lang.String r0 = "default"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 7
            goto L_0x0076
        L_0x0017:
            java.lang.String r0 = "animation"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 5
            goto L_0x0076
        L_0x0021:
            java.lang.String r0 = "play"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 3
            goto L_0x0076
        L_0x002b:
            java.lang.String r0 = "loop"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 1
            goto L_0x0076
        L_0x0035:
            java.lang.String r0 = "data"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 0
            goto L_0x0076
        L_0x003f:
            java.lang.String r0 = "src"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 6
            goto L_0x0076
        L_0x004a:
            java.lang.String r0 = "loadFinish"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 9
            goto L_0x0076
        L_0x0055:
            java.lang.String r0 = "status"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 4
            goto L_0x0076
        L_0x0060:
            java.lang.String r0 = "progress"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 2
            goto L_0x0076
        L_0x006a:
            java.lang.String r0 = "onError"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 8
            goto L_0x0076
        L_0x0075:
            r0 = -1
        L_0x0076:
            r5 = 0
            switch(r0) {
                case 0: goto L_0x01d5;
                case 1: goto L_0x01bb;
                case 2: goto L_0x0188;
                case 3: goto L_0x010b;
                case 4: goto L_0x009e;
                case 5: goto L_0x008d;
                case 6: goto L_0x0089;
                case 7: goto L_0x0085;
                case 8: goto L_0x0081;
                case 9: goto L_0x007c;
                default: goto L_0x007a;
            }
        L_0x007a:
            goto L_0x0219
        L_0x007c:
            r6.updateOnDownloadFinish(r8)
            goto L_0x0219
        L_0x0081:
            r6.updateOnErrorProperty(r8)
            return
        L_0x0085:
            r6.updateDefaultProperty(r8)
            return
        L_0x0089:
            r6.updateSrcProperty(r8)
            return
        L_0x008d:
            boolean r7 = r6.mHaveAddListener
            if (r7 == 0) goto L_0x0092
            return
        L_0x0092:
            r6.mHaveAddListener = r3
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r7 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r7
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3LottieProperty$AnimationListener r8 = r6.mAjxAnimationListener
            r7.addAnimatorListener(r8)
            return
        L_0x009e:
            if (r8 == 0) goto L_0x010a
            boolean r7 = r6.mIsLoading
            if (r7 == 0) goto L_0x00a5
            goto L_0x010a
        L_0x00a5:
            java.lang.String r8 = (java.lang.String) r8
            boolean r7 = android.text.TextUtils.isEmpty(r8)
            if (r7 == 0) goto L_0x00ae
            return
        L_0x00ae:
            int r7 = r8.hashCode()
            r0 = -1367724422(0xffffffffae7a2e7a, float:-5.68847E-11)
            if (r7 == r0) goto L_0x00d5
            r0 = -934426579(0xffffffffc84dc82d, float:-210720.7)
            if (r7 == r0) goto L_0x00cb
            r0 = 106440182(0x65825f6, float:4.0652974E-35)
            if (r7 == r0) goto L_0x00c2
            goto L_0x00df
        L_0x00c2:
            java.lang.String r7 = "pause"
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x00df
            goto L_0x00e0
        L_0x00cb:
            java.lang.String r7 = "resume"
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x00df
            r1 = 1
            goto L_0x00e0
        L_0x00d5:
            java.lang.String r7 = "cancel"
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x00df
            r1 = 0
            goto L_0x00e0
        L_0x00df:
            r1 = -1
        L_0x00e0:
            switch(r1) {
                case 0: goto L_0x00f8;
                case 1: goto L_0x00ee;
                case 2: goto L_0x00e4;
                default: goto L_0x00e3;
            }
        L_0x00e3:
            goto L_0x0109
        L_0x00e4:
            r6.mPause = r3
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r7 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r7
            r7.pauseAnimation()
            goto L_0x0109
        L_0x00ee:
            r6.mPause = r4
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r7 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r7
            r7.resumeAnimation()
            return
        L_0x00f8:
            r6.mPause = r4
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r7 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r7
            r7.cancelAnimation()
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r7 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r7
            r7.setProgress(r5)
            return
        L_0x0109:
            return
        L_0x010a:
            return
        L_0x010b:
            if (r8 == 0) goto L_0x0187
            boolean r7 = r6.mIsLoading
            if (r7 == 0) goto L_0x0113
            goto L_0x0187
        L_0x0113:
            java.lang.String r8 = (java.lang.String) r8
            boolean r7 = android.text.TextUtils.isEmpty(r8)
            if (r7 == 0) goto L_0x011c
            return
        L_0x011c:
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0182 }
            r7.<init>(r8)     // Catch:{ JSONException -> 0x0182 }
            java.lang.String r0 = "startPos"
            r1 = 0
            double r0 = r7.optDouble(r0, r1)     // Catch:{ JSONException -> 0x0182 }
            float r0 = (float) r0     // Catch:{ JSONException -> 0x0182 }
            java.lang.String r1 = "endPos"
            r2 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r1 = r7.optDouble(r1, r2)     // Catch:{ JSONException -> 0x0182 }
            float r7 = (float) r1     // Catch:{ JSONException -> 0x0182 }
            int r1 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r1 < 0) goto L_0x0172
            r1 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r2 > 0) goto L_0x0172
            int r2 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r2 < 0) goto L_0x0172
            int r2 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r2 <= 0) goto L_0x0147
            goto L_0x0172
        L_0x0147:
            r6.mPause = r4     // Catch:{ JSONException -> 0x0182 }
            int r8 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r8 >= 0) goto L_0x015c
            android.view.View r8 = r6.mView     // Catch:{ JSONException -> 0x0182 }
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r8 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r8     // Catch:{ JSONException -> 0x0182 }
            r8.setMinAndMaxProgress(r0, r7)     // Catch:{ JSONException -> 0x0182 }
            android.view.View r7 = r6.mView     // Catch:{ JSONException -> 0x0182 }
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r7 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r7     // Catch:{ JSONException -> 0x0182 }
            r7.setSpeed(r1)     // Catch:{ JSONException -> 0x0182 }
            goto L_0x016a
        L_0x015c:
            android.view.View r8 = r6.mView     // Catch:{ JSONException -> 0x0182 }
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r8 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r8     // Catch:{ JSONException -> 0x0182 }
            r8.setMinAndMaxProgress(r7, r0)     // Catch:{ JSONException -> 0x0182 }
            android.view.View r7 = r6.mView     // Catch:{ JSONException -> 0x0182 }
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r7 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r7     // Catch:{ JSONException -> 0x0182 }
            r7.reverseAnimationSpeed()     // Catch:{ JSONException -> 0x0182 }
        L_0x016a:
            android.view.View r7 = r6.mView     // Catch:{ JSONException -> 0x0182 }
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r7 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r7     // Catch:{ JSONException -> 0x0182 }
            r7.playAnimation()     // Catch:{ JSONException -> 0x0182 }
            return
        L_0x0172:
            java.lang.String r7 = "Ajx3LottieProperty"
            java.lang.String r0 = "play data is invalid:"
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ JSONException -> 0x0182 }
            java.lang.String r8 = r0.concat(r8)     // Catch:{ JSONException -> 0x0182 }
            com.amap.bundle.logs.AMapLog.e(r7, r8)     // Catch:{ JSONException -> 0x0182 }
            return
        L_0x0182:
            r7 = move-exception
            r7.printStackTrace()
            return
        L_0x0187:
            return
        L_0x0188:
            if (r8 == 0) goto L_0x01ba
            boolean r7 = r6.mIsLoading
            if (r7 == 0) goto L_0x018f
            goto L_0x01ba
        L_0x018f:
            java.lang.String r8 = (java.lang.String) r8
            android.view.View r7 = r6.mView     // Catch:{ NumberFormatException -> 0x01a1 }
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r7 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r7     // Catch:{ NumberFormatException -> 0x01a1 }
            java.lang.Float r8 = java.lang.Float.valueOf(r8)     // Catch:{ NumberFormatException -> 0x01a1 }
            float r8 = r8.floatValue()     // Catch:{ NumberFormatException -> 0x01a1 }
            r7.setProgress(r8)     // Catch:{ NumberFormatException -> 0x01a1 }
            return
        L_0x01a1:
            r7 = move-exception
            java.lang.String r8 = "Ajx3LottieProperty"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "progress() error,"
            r0.<init>(r1)
            java.lang.String r7 = r7.getMessage()
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            com.amap.bundle.logs.AMapLog.e(r8, r7)
            return
        L_0x01ba:
            return
        L_0x01bb:
            if (r8 == 0) goto L_0x01d4
            boolean r7 = r6.mIsLoading
            if (r7 == 0) goto L_0x01c2
            goto L_0x01d4
        L_0x01c2:
            java.lang.String r8 = (java.lang.String) r8
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie r7 = (com.autonavi.minimap.ajx3.widget.lottie.Ajx3Lottie) r7
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r8)
            boolean r8 = r8.booleanValue()
            r7.loop(r8)
            return
        L_0x01d4:
            return
        L_0x01d5:
            if (r8 == 0) goto L_0x0218
            boolean r7 = r6.mIsLoading
            if (r7 == 0) goto L_0x01dc
            goto L_0x0218
        L_0x01dc:
            java.lang.String r8 = (java.lang.String) r8
            boolean r7 = android.text.TextUtils.isEmpty(r8)
            if (r7 == 0) goto L_0x01ec
            java.lang.String r7 = "Ajx3LottieProperty"
            java.lang.String r8 = "filePath is invalid"
            com.amap.bundle.logs.AMapLog.e(r7, r8)
            return
        L_0x01ec:
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0213 }
            r7.<init>(r8)     // Catch:{ JSONException -> 0x0213 }
            java.lang.String r8 = "filePath"
            java.lang.String r0 = ""
            java.lang.String r8 = r7.optString(r8, r0)     // Catch:{ JSONException -> 0x0213 }
            java.lang.String r0 = "imagePath"
            java.lang.String r1 = ""
            java.lang.String r7 = r7.optString(r0, r1)     // Catch:{ JSONException -> 0x0213 }
            boolean r0 = android.text.TextUtils.isEmpty(r8)     // Catch:{ JSONException -> 0x0213 }
            if (r0 == 0) goto L_0x020f
            java.lang.String r7 = "Ajx3LottieProperty"
            java.lang.String r8 = "filePath is invalid"
            com.amap.bundle.logs.AMapLog.e(r7, r8)     // Catch:{ JSONException -> 0x0213 }
            return
        L_0x020f:
            r6.setAssetsPath(r8, r7)     // Catch:{ JSONException -> 0x0213 }
            return
        L_0x0213:
            r7 = move-exception
            r7.printStackTrace()
            return
        L_0x0218:
            return
        L_0x0219:
            super.updateAttribute(r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.lottie.Ajx3LottieProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateSrcProperty(@Nullable Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (!TextUtils.isEmpty(str)) {
                setLoadingLottie();
                LottieSrcDownloadManager.getInstance().dealSrc(str, new LottieSrcListener() {
                    public void onDealSrcFinish(String str, String str2) {
                        Ajx3LottieProperty.this.setData(str, str2);
                    }

                    public void onDealSrcFailed() {
                        Ajx3LottieProperty.this.triggerOnErrorCallback();
                    }
                });
            }
        }
    }

    private void setLoadingLottie() {
        this.mIsLoading = true;
        setAssetsPath(LOTTIE_DEFAULT_LOADING_PATH, null);
        float height = ((Ajx3Lottie) this.mView).getProperty().getNode().getHeight();
        float width = ((Ajx3Lottie) this.mView).getProperty().getNode().getWidth();
        float f = height >= width ? 80.0f / width : 80.0f / height;
        if (f >= -3.4028235E38f && f <= Float.MAX_VALUE) {
            ((Ajx3Lottie) this.mView).setScaleX(f);
            ((Ajx3Lottie) this.mView).setScaleY(f);
        }
        ((Ajx3Lottie) this.mView).loop(true);
        ((Ajx3Lottie) this.mView).playAnimation();
    }

    private void updateOnErrorProperty(Object obj) {
        this.mHasSetOnError = obj != null;
    }

    private void updateOnDownloadFinish(Object obj) {
        this.mHasSetOnDownloadFinish = obj != null;
    }

    /* access modifiers changed from: private */
    public void triggerOnErrorCallback() {
        if (this.mHasSetOnError) {
            native2AjxDataOrEvent(LOTTIE_PROPERTY_ON_ERROR, null, null);
        }
    }

    /* access modifiers changed from: private */
    public void triggerOnDownLoadFinishCallback() {
        if (this.mHasSetOnDownloadFinish) {
            native2AjxDataOrEvent(LOTTIE_PROPERTY_ON_DOWNLOAD_SRC_FINISH, null, null);
        }
    }

    private void setAssetsPath(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder("setAssetsPath(),invalid param,filePath:");
            sb.append(str);
            sb.append(",imagePath:");
            sb.append(str2);
            AMapLog.e(TAG, sb.toString());
            return;
        }
        try {
            String concat = LOTTIE_ASSETS_PARENT_FOLDER.concat(String.valueOf(str));
            if (!str.startsWith(File.separator)) {
                StringBuilder sb2 = new StringBuilder(LOTTIE_ASSETS_PARENT_FOLDER);
                sb2.append(File.separator);
                sb2.append(str);
                concat = sb2.toString();
            }
            ((Ajx3Lottie) this.mView).setAnimation(concat, CacheStrategy.Strong);
            if (!TextUtils.isEmpty(str2)) {
                String concat2 = LOTTIE_ASSETS_PARENT_FOLDER.concat(String.valueOf(str2));
                if (!str2.startsWith(File.separator)) {
                    StringBuilder sb3 = new StringBuilder(LOTTIE_ASSETS_PARENT_FOLDER);
                    sb3.append(File.separator);
                    sb3.append(str2);
                    concat2 = sb3.toString();
                }
                if (!concat2.endsWith(File.separator)) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(concat2);
                    sb4.append(File.separator);
                    concat2 = sb4.toString();
                }
                ((Ajx3Lottie) this.mView).setImageAssetsFolder(concat2);
            } else {
                ((Ajx3Lottie) this.mView).setImageAssetsFolder(LOTTIE_ASSETS_PARENT_FOLDER);
            }
            ((Ajx3Lottie) this.mView).setProgress(0.0f);
        } catch (Exception e) {
            AMapLog.e(TAG, e.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public void setData(String str, final String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            StringBuilder sb = new StringBuilder("initLottie(),invalid param,filePath:");
            sb.append(str);
            sb.append(",imagePath:");
            sb.append(str2);
            AMapLog.e(TAG, sb.toString());
            return;
        }
        try {
            a.a(this.mNativeContext.getResources(), new JSONObject(FileUtil.readData(str)), (ey) new ey() {
                public void onCompositionLoaded(@Nullable final ev evVar) {
                    if (evVar != null) {
                        aho.a(new Runnable() {
                            public void run() {
                                if (Ajx3LottieProperty.this.mIsLoading) {
                                    ((Ajx3Lottie) Ajx3LottieProperty.this.mView).setScaleX(1.0f);
                                    ((Ajx3Lottie) Ajx3LottieProperty.this.mView).setScaleY(1.0f);
                                    Ajx3LottieProperty.this.mIsLoading = false;
                                    ((Ajx3Lottie) Ajx3LottieProperty.this.mView).setProgress(0.0f);
                                    Ajx3LottieProperty.this.triggerOnDownLoadFinishCallback();
                                }
                                ((Ajx3Lottie) Ajx3LottieProperty.this.mView).setComposition(evVar);
                            }
                        });
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((Ajx3Lottie) this.mView).setImageAssetDelegate(new et() {
            public Bitmap fetchBitmap(ex exVar) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(exVar.b);
                String sb2 = sb.toString();
                if (!str2.endsWith(File.separator)) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str2);
                    sb3.append(File.separator);
                    sb3.append(exVar.b);
                    sb2 = sb3.toString();
                }
                Options options = new Options();
                options.inScaled = true;
                options.inDensity = 160;
                return BitmapFactory.decodeFile(sb2, options);
            }
        });
    }

    /* access modifiers changed from: private */
    public void native2AjxDataOrEvent(String str, String str2, String str3) {
        Parcel parcel = new Parcel();
        parcel.writeInt(2);
        if (TextUtils.isEmpty(str2)) {
            parcel.writeString("");
        } else {
            parcel.writeString(str2);
        }
        if (TextUtils.isEmpty(str3)) {
            parcel.writeString("");
        } else {
            parcel.writeString(str3);
        }
        IAjxContext iAjxContext = this.mAjxContext;
        if (str == null) {
            str = "";
        }
        iAjxContext.invokeJsEvent(str, this.mAjxContext.getDomTree().getNodeId(this.mView), parcel, null);
    }

    private void setAttribute(String str, String str2) {
        this.mAjxContext.setAttribute(this.mAjxContext.getDomTree().getNodeId(this.mView), "data-".concat(String.valueOf(str)), str2);
    }
}
