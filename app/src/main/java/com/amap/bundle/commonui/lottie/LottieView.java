package com.amap.bundle.commonui.lottie;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.autonavi.minimap.R;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LottieView extends LinearLayout {
    private static final String FALL_BACK_VALUE = "-1~-1";
    private static final String JSON_SPLIT = "~";
    private static float TOTAL_TIME = 1.0f;
    public final String TAG = "LottieView";
    AnimatorListener mAnimatorListener = new AnimatorListener() {
        public final void onAnimationRepeat(Animator animator) {
        }

        public final void onAnimationStart(Animator animator) {
            LottieView.this.LogI("onAnimationEnd");
        }

        public final void onAnimationEnd(Animator animator) {
            synchronized (LottieView.this.mPlayEndList) {
                if (LottieView.this.mPlayEndList.size() > 0) {
                    final a aVar = (a) LottieView.this.mPlayEndList.get(0);
                    LottieView.this.LogI("onAnimationEnd");
                    LottieView.this.mPlayEndList.remove(0);
                    LottieView.this.mLottieView.cancelAnimation();
                    LottieView.this.mLottieView.clearAnimation();
                    LottieView.this.mLottieView.post(new Runnable() {
                        public final void run() {
                            LottieView.this.playAnimation(aVar);
                        }
                    });
                }
            }
        }

        public final void onAnimationCancel(Animator animator) {
            LottieView.this.mPlayEndList.clear();
        }
    };
    private boolean mAutoStart = false;
    /* access modifiers changed from: private */
    public List<LottieConfig> mConfigList = new ArrayList();
    private b mCurrentPos = new b(this, 0);
    float mCurrentProgress = -1.0f;
    /* access modifiers changed from: private */
    public LottieAnimationView mLottieView;
    /* access modifiers changed from: private */
    public final List<a> mPlayEndList = new ArrayList(10);
    private c mPositionCallBack;

    static class LottieConfig {
        public float a;
        public float b;
        public float c;
        public float d;
        public float e;
        public float f;

        enum LottieState {
            PREPARE,
            RUNNING
        }

        private LottieConfig() {
        }

        /* synthetic */ LottieConfig(byte b2) {
            this();
        }

        public final boolean a() {
            return this.a >= 0.0f && this.b >= 0.0f && this.b >= this.a;
        }

        public final boolean b() {
            return this.c >= 0.0f && this.d >= 0.0f && this.d >= this.c;
        }
    }

    public static class a {
        boolean a = false;
        boolean b = false;
        float c = 0.0f;
        float d = 0.0f;

        a(boolean z, float f, float f2) {
            this.a = z;
            this.b = false;
            this.c = f;
            this.d = f2;
        }
    }

    class b {
        public int a;
        public LottieState b;

        private b() {
            this.a = 0;
            this.b = LottieState.PREPARE;
        }

        /* synthetic */ b(LottieView lottieView, byte b2) {
            this();
        }
    }

    public interface c {
    }

    private void LogE(String str) {
    }

    /* access modifiers changed from: private */
    public void LogI(String str) {
    }

    public LottieView(Context context) {
        super(context);
        initView(context, null);
    }

    public LottieView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context, attributeSet);
    }

    public void setAssertData(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str3)) {
            StringBuilder sb = new StringBuilder("LottieView,setAssertData() invalid param! jsonFilePath:");
            sb.append(str);
            sb.append(",lottieConfig:");
            sb.append(str3);
            throw new RuntimeException(sb.toString());
        }
        if (!TextUtils.isEmpty(str2)) {
            this.mLottieView.setImageAssetsFolder(str2);
        }
        this.mLottieView.setAnimation(str);
        parseLottieConfig(str3);
        if (this.mAutoStart) {
            startPlay(true);
        }
    }

    public void setAssertData(String str, String str2, double d, double d2, double d3, double d4, boolean z) {
        String str3 = str;
        final String str4 = str2;
        this.mLottieView.setImageAssetsFolder(str4);
        try {
            this.mLottieView.setAnimation(new JSONObject(str3));
            this.mLottieView.setImageAssetDelegate(new et() {
                public final Bitmap fetchBitmap(ex exVar) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str4);
                    sb.append(exVar.b);
                    String sb2 = sb.toString();
                    Options options = new Options();
                    options.inScaled = true;
                    options.inDensity = 160;
                    return BitmapFactory.decodeFile(sb2, options);
                }
            });
        } catch (Throwable unused) {
            this.mLottieView.setAnimation(str3);
        }
        parseLottieConfig(d, d2, d3, d4, z);
        if (this.mAutoStart) {
            startPlay(true);
        }
    }

    public void setLocalData(@NonNull JSONObject jSONObject, @NonNull String str, @NonNull String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder("Lottie Data Error:jsonObject");
            sb.append(jSONObject);
            sb.append(",imageFolderPath:");
            sb.append(str);
            throw new RuntimeException(sb.toString());
        }
        parseLottieConfig(str2);
        loadData(jSONObject, str);
    }

    public void setPositionCallBack(c cVar) {
        this.mPositionCallBack = cVar;
    }

    public void startPlay(boolean z) {
        LottieConfig currentPageConfig = getCurrentPageConfig();
        if (currentPageConfig != null) {
            ArrayList arrayList = new ArrayList();
            if (z) {
                arrayList.add(new a(false, currentPageConfig.a, currentPageConfig.b));
            }
            arrayList.add(new a(true, currentPageConfig.c, currentPageConfig.d));
            playListAnimation(arrayList);
            this.mCurrentProgress = -1.0f;
        }
    }

    public void cancelPlay() {
        this.mLottieView.cancelAnimation();
    }

    public void pausePlay() {
        this.mLottieView.pauseAnimation();
    }

    public void resumePlay() {
        this.mLottieView.resumeAnimation();
    }

    public void setInitialProgress() {
        LottieConfig currentPageConfig = getCurrentPageConfig();
        if (currentPageConfig != null) {
            if (currentPageConfig.a()) {
                if (this.mCurrentProgress != currentPageConfig.a) {
                    this.mCurrentProgress = currentPageConfig.a;
                    this.mLottieView.setProgress(currentPageConfig.a);
                    StringBuilder sb = new StringBuilder("setInitialProgress():");
                    sb.append(currentPageConfig.a);
                    LogI(sb.toString());
                }
            } else if (currentPageConfig.b() && this.mCurrentProgress != currentPageConfig.c) {
                this.mCurrentProgress = currentPageConfig.c;
                this.mLottieView.setProgress(currentPageConfig.c);
                StringBuilder sb2 = new StringBuilder("setInitialProgress():");
                sb2.append(currentPageConfig.c);
                LogI(sb2.toString());
            }
        }
    }

    public boolean isAnimating() {
        return this.mLottieView.isAnimating();
    }

    private void initView(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.lottie_view_layout, this);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.LottieView);
        this.mLottieView = (LottieAnimationView) findViewById(R.id.animation_view);
        this.mLottieView.addAnimatorListener(this.mAnimatorListener);
        String string = obtainStyledAttributes.getString(R.styleable.LottieView_lottie_fileName);
        if (!TextUtils.isEmpty(string)) {
            this.mLottieView.setAnimation(string);
        }
        String string2 = obtainStyledAttributes.getString(R.styleable.LottieView_lottie_imageAssetsFolder);
        if (!TextUtils.isEmpty(string2)) {
            this.mLottieView.setImageAssetsFolder(string2);
        }
        String string3 = obtainStyledAttributes.getString(R.styleable.LottieView_lottie_fileConfigName);
        if (!TextUtils.isEmpty(string3)) {
            parseLottieConfig(string3);
        }
        this.mLottieView.loop(obtainStyledAttributes.getBoolean(R.styleable.LottieView_lottie_loop, false));
        if (obtainStyledAttributes.getBoolean(R.styleable.LottieView_lottie_autoPlay, false)) {
            startPlay(true);
        } else {
            LottieConfig currentPageConfig = getCurrentPageConfig();
            if (currentPageConfig != null) {
                if (currentPageConfig.a()) {
                    this.mLottieView.setProgress(currentPageConfig.a);
                } else if (currentPageConfig.b()) {
                    this.mLottieView.setProgress(currentPageConfig.c);
                }
            } else {
                return;
            }
        }
        this.mLottieView.enableMergePathsForKitKatAndAbove(true);
        this.mLottieView.useHardwareAcceleration(true);
    }

    private void setLottieProgress(float f) {
        if (f < 0.0f || f > 1.0f) {
            LogE("invalid progerss:".concat(String.valueOf(f)));
            return;
        }
        this.mLottieView.setProgress(f);
        LogI("setLottieProgress:".concat(String.valueOf(f)));
    }

    /* access modifiers changed from: private */
    public void playAnimation(a aVar) {
        StringBuilder sb = new StringBuilder("playAnimation:bLooper:");
        sb.append(aVar.a);
        sb.append(",bRevert:");
        sb.append(aVar.b);
        sb.append(",mMinProgress:");
        sb.append(aVar.c);
        sb.append(",mMaxProgress:");
        sb.append(aVar.d);
        LogI(sb.toString());
        this.mLottieView.loop(aVar.a);
        if (aVar.b) {
            this.mLottieView.reverseAnimationSpeed();
        } else {
            this.mLottieView.setSpeed(1.0f);
        }
        this.mLottieView.setMinAndMaxProgress(aVar.c, aVar.d);
        this.mLottieView.playAnimation();
    }

    private void addPlayEndAnimation(a aVar) {
        LogI("addPlayEndAnimation");
        synchronized (this.mPlayEndList) {
            this.mPlayEndList.add(aVar);
        }
    }

    private void loadData(JSONObject jSONObject, final String str) {
        this.mLottieView.setImageAssetsFolder(str);
        this.mLottieView.setAnimation(jSONObject);
        defpackage.ev.a.a(getResources(), jSONObject, (ey) new ey() {
            public final void onCompositionLoaded(@Nullable ev evVar) {
                if (evVar != null) {
                    LottieView.this.mLottieView.setComposition(evVar);
                }
            }
        });
        this.mLottieView.setImageAssetDelegate(new et() {
            public final Bitmap fetchBitmap(ex exVar) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(exVar.b);
                String sb2 = sb.toString();
                Options options = new Options();
                options.inScaled = true;
                options.inDensity = 160;
                return BitmapFactory.decodeFile(sb2, options);
            }
        });
    }

    private void parseLottieConfig(String str) {
        try {
            JSONObject jSONObject = new JSONObject(getFromAssets(str));
            JSONArray optJSONArray = jSONObject.optJSONArray("lottie");
            if (optJSONArray != null) {
                if (optJSONArray.length() != 0) {
                    TOTAL_TIME = (float) jSONObject.optDouble("total", (double) TOTAL_TIME);
                    this.mAutoStart = jSONObject.optBoolean("autoPlay", false);
                    this.mConfigList.clear();
                    int length = optJSONArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                        LottieConfig lottieConfig = new LottieConfig(0);
                        String[] split = optJSONObject.optString("fa", FALL_BACK_VALUE).split("~");
                        if (split.length == 2) {
                            lottieConfig.a = Float.valueOf(split[0]).floatValue() / TOTAL_TIME;
                            lottieConfig.b = Float.valueOf(split[1]).floatValue() / TOTAL_TIME;
                        }
                        String[] split2 = optJSONObject.optString("la", FALL_BACK_VALUE).split("~");
                        if (split2.length == 2) {
                            lottieConfig.c = Float.valueOf(split2[0]).floatValue() / TOTAL_TIME;
                            lottieConfig.d = Float.valueOf(split2[1]).floatValue() / TOTAL_TIME;
                        }
                        String[] split3 = optJSONObject.optString("ta", FALL_BACK_VALUE).split("~");
                        if (split3.length == 2) {
                            lottieConfig.e = Float.valueOf(split3[0]).floatValue() / TOTAL_TIME;
                            lottieConfig.f = Float.valueOf(split3[1]).floatValue() / TOTAL_TIME;
                        }
                        this.mConfigList.add(lottieConfig);
                    }
                    return;
                }
            }
            throw new RuntimeException("LottieView,parseLottieConfig() invalid jsonObject:json is null or length is 0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseLottieConfig(double d, double d2, double d3, double d4, boolean z) {
        TOTAL_TIME = (float) d4;
        this.mAutoStart = z;
        this.mConfigList.clear();
        LottieConfig lottieConfig = new LottieConfig(0);
        float f = 0.0f;
        if (0.0d != d) {
            lottieConfig.a = 0.0f / TOTAL_TIME;
            lottieConfig.b = (float) (d / ((double) TOTAL_TIME));
            f = (float) (d + 0.0d);
        }
        if (0.0d != d2) {
            lottieConfig.c = f / TOTAL_TIME;
            double d5 = ((double) f) + d2;
            lottieConfig.d = (float) (d5 / ((double) TOTAL_TIME));
            f = (float) d5;
        }
        if (0.0d != d3) {
            lottieConfig.e = f / TOTAL_TIME;
            lottieConfig.f = (float) ((((double) f) + d3) / ((double) TOTAL_TIME));
        }
        this.mConfigList.add(lottieConfig);
    }

    private void playListAnimation(List<a> list) {
        if (list != null && !list.isEmpty()) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                a aVar = list.get(i);
                if (i == 0) {
                    playAnimation(aVar);
                } else {
                    addPlayEndAnimation(aVar);
                }
            }
        }
    }

    @Nullable
    private LottieConfig getCurrentPageConfig() {
        if (this.mCurrentPos.a >= this.mConfigList.size()) {
            return null;
        }
        return this.mConfigList.get(this.mCurrentPos.a);
    }

    private LottieConfig getNextPageConfig(boolean z) {
        if (z) {
            b bVar = this.mCurrentPos;
            if (bVar.a == LottieView.this.mConfigList.size() - 1) {
                return this.mConfigList.get(0);
            }
            return this.mConfigList.get(this.mCurrentPos.a + 1);
        } else if (this.mCurrentPos.a + 1 < this.mConfigList.size()) {
            return this.mConfigList.get(this.mCurrentPos.a + 1);
        } else {
            throw new RuntimeException("LottieView,getNextPageConfig(): pos >= size");
        }
    }

    private LottieConfig getPreviousPageConfig() {
        int i = this.mCurrentPos.a - 1;
        if (i >= 0) {
            return this.mConfigList.get(i);
        }
        return this.mConfigList.get(this.mConfigList.size() - 1);
    }

    private void onPagePosCallBack() {
        if (this.mPositionCallBack != null) {
            this.mConfigList.size();
        }
    }

    private String getFromAssets(String str) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources().getAssets().open(str)));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return sb.toString();
                }
                sb.append(readLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
