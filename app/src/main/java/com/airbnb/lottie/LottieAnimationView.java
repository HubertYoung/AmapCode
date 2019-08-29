package com.airbnb.lottie;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View.BaseSavedState;
import android.widget.ImageView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class LottieAnimationView extends ImageView {
    /* access modifiers changed from: private */
    public static final Map<String, ev> ASSET_STRONG_REF_CACHE = new HashMap();
    /* access modifiers changed from: private */
    public static final Map<String, WeakReference<ev>> ASSET_WEAK_REF_CACHE = new HashMap();
    /* access modifiers changed from: private */
    public static final SparseArray<ev> RAW_RES_STRONG_REF_CACHE = new SparseArray<>();
    /* access modifiers changed from: private */
    public static final SparseArray<WeakReference<ev>> RAW_RES_WEAK_REF_CACHE = new SparseArray<>();
    private static final String TAG = "LottieAnimationView";
    private String animationName;
    @RawRes
    private int animationResId;
    private boolean autoPlay = false;
    @Nullable
    private ev composition;
    /* access modifiers changed from: private */
    @Nullable
    public er compositionLoader;
    private CacheStrategy defaultCacheStrategy;
    private final ey loadedListener = new ey() {
        public final void onCompositionLoaded(@Nullable ev evVar) {
            if (evVar != null) {
                LottieAnimationView.this.setComposition(evVar);
            }
            LottieAnimationView.this.compositionLoader = null;
        }
    };
    private final ew lottieDrawable = new ew();
    private boolean useHardwareLayer = false;
    private boolean wasAnimatingWhenDetached = false;

    public enum CacheStrategy {
        None,
        Weak,
        Strong
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new SavedState[i];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, 0);
            }
        };
        String a;
        int b;
        float c;
        boolean d;
        boolean e;
        String f;

        /* synthetic */ SavedState(Parcel parcel, byte b2) {
            this(parcel);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.a = parcel.readString();
            this.c = parcel.readFloat();
            boolean z = false;
            this.d = parcel.readInt() == 1;
            this.e = parcel.readInt() == 1 ? true : z;
            this.f = parcel.readString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.a);
            parcel.writeFloat(this.c);
            parcel.writeInt(this.d ? 1 : 0);
            parcel.writeInt(this.e ? 1 : 0);
            parcel.writeString(this.f);
        }
    }

    public LottieAnimationView(Context context) {
        super(context);
        init(null);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(@Nullable AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.LottieAnimationView);
        this.defaultCacheStrategy = CacheStrategy.values()[obtainStyledAttributes.getInt(R.styleable.LottieAnimationView_lottie_cacheStrategy, CacheStrategy.Weak.ordinal())];
        if (!isInEditMode()) {
            boolean hasValue = obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_rawRes);
            boolean hasValue2 = obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_fileName);
            if (hasValue && hasValue2) {
                throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use use only one at once.");
            } else if (hasValue) {
                int resourceId = obtainStyledAttributes.getResourceId(R.styleable.LottieAnimationView_lottie_rawRes, 0);
                if (resourceId != 0) {
                    setAnimation(resourceId);
                }
            } else if (hasValue2) {
                String string = obtainStyledAttributes.getString(R.styleable.LottieAnimationView_lottie_fileName);
                if (string != null) {
                    setAnimation(string);
                }
            }
        }
        if (obtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_autoPlay, false)) {
            this.lottieDrawable.c();
            this.autoPlay = true;
        }
        this.lottieDrawable.a(obtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_loop, false));
        setImageAssetsFolder(obtainStyledAttributes.getString(R.styleable.LottieAnimationView_lottie_imageAssetsFolder));
        setProgress(obtainStyledAttributes.getFloat(R.styleable.LottieAnimationView_lottie_progress, 0.0f));
        enableMergePathsForKitKatAndAbove(obtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_enableMergePathsForKitKatAndAbove, false));
        if (obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_colorFilter)) {
            addColorFilter(new fa(obtainStyledAttributes.getColor(R.styleable.LottieAnimationView_lottie_colorFilter, 0)));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_scale)) {
            this.lottieDrawable.d(obtainStyledAttributes.getFloat(R.styleable.LottieAnimationView_lottie_scale, 1.0f));
        }
        obtainStyledAttributes.recycle();
        if (ij.a(getContext()) == 0.0f) {
            this.lottieDrawable.b.a = true;
        }
        enableOrDisableHardwareLayer();
    }

    public void setImageResource(int i) {
        recycleBitmaps();
        cancelLoaderTask();
        super.setImageResource(i);
    }

    public void setImageDrawable(Drawable drawable) {
        if (drawable != this.lottieDrawable) {
            recycleBitmaps();
        }
        cancelLoaderTask();
        super.setImageDrawable(drawable);
    }

    public void setImageBitmap(Bitmap bitmap) {
        recycleBitmaps();
        cancelLoaderTask();
        super.setImageBitmap(bitmap);
    }

    public void addColorFilterToContent(String str, String str2, @Nullable ColorFilter colorFilter) {
        this.lottieDrawable.a(str, str2, colorFilter);
    }

    public void addColorFilterToLayer(String str, @Nullable ColorFilter colorFilter) {
        this.lottieDrawable.a(str, null, colorFilter);
    }

    public void addColorFilter(@Nullable ColorFilter colorFilter) {
        this.lottieDrawable.a(null, null, colorFilter);
    }

    public void clearColorFilters() {
        ew ewVar = this.lottieDrawable;
        ewVar.d.clear();
        ewVar.a(null, null, null);
    }

    public void invalidateDrawable(@NonNull Drawable drawable) {
        if (getDrawable() == this.lottieDrawable) {
            super.invalidateDrawable(this.lottieDrawable);
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = this.animationName;
        savedState.b = this.animationResId;
        savedState.c = this.lottieDrawable.b.d;
        savedState.d = this.lottieDrawable.b.isRunning();
        savedState.e = this.lottieDrawable.b.getRepeatCount() == -1;
        savedState.f = this.lottieDrawable.g;
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.animationName = savedState.a;
        if (!TextUtils.isEmpty(this.animationName)) {
            setAnimation(this.animationName);
        }
        this.animationResId = savedState.b;
        if (this.animationResId != 0) {
            setAnimation(this.animationResId);
        }
        setProgress(savedState.c);
        loop(savedState.e);
        if (savedState.d) {
            playAnimation();
        }
        this.lottieDrawable.g = savedState.f;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.autoPlay && this.wasAnimatingWhenDetached) {
            playAnimation();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (isAnimating()) {
            cancelAnimation();
            this.wasAnimatingWhenDetached = true;
        }
        recycleBitmaps();
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: 0000 */
    public void recycleBitmaps() {
        if (this.lottieDrawable != null) {
            this.lottieDrawable.a();
        }
    }

    public void enableMergePathsForKitKatAndAbove(boolean z) {
        ew ewVar = this.lottieDrawable;
        if (VERSION.SDK_INT >= 19) {
            ewVar.l = z;
            if (ewVar.a != null) {
                ewVar.b();
            }
        }
    }

    @Deprecated
    public void useExperimentalHardwareAcceleration() {
        useHardwareAcceleration(true);
    }

    @Deprecated
    public void useExperimentalHardwareAcceleration(boolean z) {
        useHardwareAcceleration(z);
    }

    public void useHardwareAcceleration() {
        useHardwareAcceleration(true);
    }

    public void useHardwareAcceleration(boolean z) {
        this.useHardwareLayer = z;
        enableOrDisableHardwareLayer();
    }

    public void setAnimation(@RawRes int i) {
        setAnimation(i, this.defaultCacheStrategy);
    }

    public void setAnimation(@RawRes final int i, final CacheStrategy cacheStrategy) {
        this.animationResId = i;
        this.animationName = null;
        if (RAW_RES_WEAK_REF_CACHE.indexOfKey(i) > 0) {
            ev evVar = (ev) RAW_RES_WEAK_REF_CACHE.get(i).get();
            if (evVar != null) {
                setComposition(evVar);
                return;
            }
        } else if (RAW_RES_STRONG_REF_CACHE.indexOfKey(i) > 0) {
            setComposition(RAW_RES_STRONG_REF_CACHE.get(i));
            return;
        }
        this.lottieDrawable.g();
        cancelLoaderTask();
        Context context = getContext();
        this.compositionLoader = a.a(context, context.getResources().openRawResource(i), (ey) new ey() {
            public final void onCompositionLoaded(ev evVar) {
                if (cacheStrategy == CacheStrategy.Strong) {
                    LottieAnimationView.RAW_RES_STRONG_REF_CACHE.put(i, evVar);
                } else if (cacheStrategy == CacheStrategy.Weak) {
                    LottieAnimationView.RAW_RES_WEAK_REF_CACHE.put(i, new WeakReference(evVar));
                }
                LottieAnimationView.this.setComposition(evVar);
            }
        });
    }

    public void setAnimation(String str) {
        setAnimation(str, this.defaultCacheStrategy);
    }

    public void setAnimation(final String str, final CacheStrategy cacheStrategy) {
        this.animationName = str;
        this.animationResId = 0;
        if (ASSET_WEAK_REF_CACHE.containsKey(str)) {
            ev evVar = (ev) ASSET_WEAK_REF_CACHE.get(str).get();
            if (evVar != null) {
                setComposition(evVar);
                return;
            }
        } else if (ASSET_STRONG_REF_CACHE.containsKey(str)) {
            setComposition(ASSET_STRONG_REF_CACHE.get(str));
            return;
        }
        this.lottieDrawable.g();
        cancelLoaderTask();
        this.compositionLoader = a.a(getContext(), str, (ey) new ey() {
            public final void onCompositionLoaded(ev evVar) {
                if (cacheStrategy == CacheStrategy.Strong) {
                    LottieAnimationView.ASSET_STRONG_REF_CACHE.put(str, evVar);
                } else if (cacheStrategy == CacheStrategy.Weak) {
                    LottieAnimationView.ASSET_WEAK_REF_CACHE.put(str, new WeakReference(evVar));
                }
                LottieAnimationView.this.setComposition(evVar);
            }
        });
    }

    public void setAnimation(JSONObject jSONObject) {
        cancelLoaderTask();
        this.compositionLoader = a.a(getResources(), jSONObject, this.loadedListener);
    }

    private void cancelLoaderTask() {
        if (this.compositionLoader != null) {
            this.compositionLoader.a();
            this.compositionLoader = null;
        }
    }

    public void setComposition(@NonNull ev evVar) {
        boolean z;
        this.lottieDrawable.setCallback(this);
        ew ewVar = this.lottieDrawable;
        if (ewVar.a == evVar) {
            z = false;
        } else {
            ewVar.a();
            if (ewVar.b.isRunning()) {
                ewVar.b.cancel();
            }
            ewVar.a = null;
            ewVar.m = null;
            ewVar.f = null;
            ewVar.invalidateSelf();
            ewVar.a = evVar;
            ewVar.b();
            ig igVar = ewVar.b;
            igVar.b = evVar.a();
            igVar.c();
            ewVar.c(ewVar.b.d);
            ewVar.d(ewVar.c);
            ewVar.f();
            if (ewVar.m != null) {
                for (a next : ewVar.d) {
                    ewVar.m.a(next.a, next.b, next.c);
                }
            }
            Iterator it = new ArrayList(ewVar.e).iterator();
            while (it.hasNext()) {
                ((b) it.next()).a();
                it.remove();
            }
            ewVar.e.clear();
            evVar.a(ewVar.n);
            z = true;
        }
        enableOrDisableHardwareLayer();
        if (z) {
            setImageDrawable(null);
            setImageDrawable(this.lottieDrawable);
            this.composition = evVar;
            requestLayout();
        }
    }

    public boolean hasMasks() {
        ew ewVar = this.lottieDrawable;
        return ewVar.m != null && ewVar.m.e();
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0044 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasMatte() {
        /*
            r4 = this;
            ew r0 = r4.lottieDrawable
            hy r1 = r0.m
            if (r1 == 0) goto L_0x0045
            hy r0 = r0.m
            java.lang.Boolean r1 = r0.h
            r2 = 1
            if (r1 != 0) goto L_0x003c
            boolean r1 = r0.c()
            if (r1 == 0) goto L_0x0019
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r0.h = r1
        L_0x0017:
            r0 = 1
            goto L_0x0042
        L_0x0019:
            java.util.List<hx> r1 = r0.g
            int r1 = r1.size()
            int r1 = r1 - r2
        L_0x0020:
            if (r1 < 0) goto L_0x0038
            java.util.List<hx> r3 = r0.g
            java.lang.Object r3 = r3.get(r1)
            hx r3 = (defpackage.hx) r3
            boolean r3 = r3.c()
            if (r3 == 0) goto L_0x0035
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r0.h = r1
            goto L_0x0017
        L_0x0035:
            int r1 = r1 + -1
            goto L_0x0020
        L_0x0038:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            r0.h = r1
        L_0x003c:
            java.lang.Boolean r0 = r0.h
            boolean r0 = r0.booleanValue()
        L_0x0042:
            if (r0 == 0) goto L_0x0045
            return r2
        L_0x0045:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.LottieAnimationView.hasMatte():boolean");
    }

    public void playAnimation() {
        this.lottieDrawable.c();
        enableOrDisableHardwareLayer();
    }

    public void resumeAnimation() {
        this.lottieDrawable.d();
        enableOrDisableHardwareLayer();
    }

    public void setMinFrame(int i) {
        this.lottieDrawable.a(i);
    }

    public void setMinProgress(float f) {
        this.lottieDrawable.a(f);
    }

    public void setMaxFrame(int i) {
        this.lottieDrawable.b(i);
    }

    public void setMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.lottieDrawable.b(f);
    }

    public void setMinAndMaxFrame(int i, int i2) {
        this.lottieDrawable.a(i, i2);
    }

    public void setMinAndMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.lottieDrawable.b.a(f, f2);
    }

    public void reverseAnimationSpeed() {
        ig igVar = this.lottieDrawable.b;
        igVar.d(-igVar.c);
    }

    public void setSpeed(float f) {
        this.lottieDrawable.b.d(f);
    }

    public float getSpeed() {
        return this.lottieDrawable.b.c;
    }

    public void addAnimatorUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        this.lottieDrawable.b.addUpdateListener(animatorUpdateListener);
    }

    public void removeUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        this.lottieDrawable.b.removeUpdateListener(animatorUpdateListener);
    }

    public void addAnimatorListener(AnimatorListener animatorListener) {
        this.lottieDrawable.b.addListener(animatorListener);
    }

    public void removeAnimatorListener(AnimatorListener animatorListener) {
        this.lottieDrawable.b.removeListener(animatorListener);
    }

    public void loop(boolean z) {
        this.lottieDrawable.a(z);
    }

    public boolean isAnimating() {
        return this.lottieDrawable.b.isRunning();
    }

    public void setImageAssetsFolder(String str) {
        this.lottieDrawable.g = str;
    }

    @Nullable
    public String getImageAssetsFolder() {
        return this.lottieDrawable.g;
    }

    @Nullable
    public Bitmap updateBitmap(String str, @Nullable Bitmap bitmap) {
        ew ewVar = this.lottieDrawable;
        gl h = ewVar.h();
        if (h == null) {
            return null;
        }
        Bitmap put = h.b.put(str, bitmap);
        ewVar.invalidateSelf();
        return put;
    }

    public void setImageAssetDelegate(et etVar) {
        ew ewVar = this.lottieDrawable;
        ewVar.h = etVar;
        if (ewVar.f != null) {
            ewVar.f.a = etVar;
        }
    }

    public void setFontAssetDelegate(es esVar) {
        ew ewVar = this.lottieDrawable;
        ewVar.j = esVar;
        if (ewVar.i != null) {
            ewVar.i.e = esVar;
        }
    }

    public void setTextDelegate(fb fbVar) {
        this.lottieDrawable.k = fbVar;
    }

    public void setScale(float f) {
        this.lottieDrawable.d(f);
        if (getDrawable() == this.lottieDrawable) {
            setImageDrawable(null);
            setImageDrawable(this.lottieDrawable);
        }
    }

    public float getScale() {
        return this.lottieDrawable.c;
    }

    public void cancelAnimation() {
        this.lottieDrawable.g();
        enableOrDisableHardwareLayer();
    }

    public void pauseAnimation() {
        ew ewVar = this.lottieDrawable;
        ewVar.e.clear();
        ig igVar = ewVar.b;
        float f = igVar.d;
        igVar.cancel();
        igVar.a(f);
        enableOrDisableHardwareLayer();
    }

    public void setFrame(int i) {
        this.lottieDrawable.c(i);
    }

    public int getFrame() {
        ew ewVar = this.lottieDrawable;
        if (ewVar.a == null) {
            return 0;
        }
        return (int) (ewVar.b.d * ewVar.a.b());
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.lottieDrawable.c(f);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.lottieDrawable.b.d;
    }

    public long getDuration() {
        if (this.composition != null) {
            return this.composition.a();
        }
        return 0;
    }

    public void setPerformanceTrackingEnabled(boolean z) {
        ew ewVar = this.lottieDrawable;
        ewVar.n = z;
        if (ewVar.a != null) {
            ewVar.a.a(z);
        }
    }

    @Nullable
    public ez getPerformanceTracker() {
        ew ewVar = this.lottieDrawable;
        if (ewVar.a != null) {
            return ewVar.a.g;
        }
        return null;
    }

    private void enableOrDisableHardwareLayer() {
        int i = 1;
        if (this.useHardwareLayer && this.lottieDrawable.b.isRunning()) {
            i = 2;
        }
        setLayerType(i, null);
    }
}
