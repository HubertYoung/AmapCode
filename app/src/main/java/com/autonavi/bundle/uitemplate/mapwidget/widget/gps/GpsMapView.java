package com.autonavi.bundle.uitemplate.mapwidget.widget.gps;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.airbnb.lottie.LottieAnimationView;
import com.autonavi.minimap.R;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class GpsMapView extends FrameLayout implements ced {
    private final int LOCK_3D_STATE_FRAME = 18;
    private final int LOCK_STATE_FRAME = 9;
    private final int OFF_FOCUS_STATE_FRAME = 0;
    private int mCurGpsBtnState = -1;
    /* access modifiers changed from: private */
    public LottieAnimationView mGpsBtnView;
    private int mGpsLayout = R.layout.map_widget_gps_layout;
    private int mState = -1;
    private String mVersionStateStr;

    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GPSState {
        public static final int GPS_STATE_3D_FOCUS = 13;
        public static final int GPS_STATE_NORMAL = 11;
        public static final int GPS_STATE_NORMAL_FOCUS = 12;
        public static final int GPS_STATE_UN_KNOW = -1;
    }

    public View getView() {
        return this;
    }

    public GpsMapView(@NonNull Context context, int i, String str) {
        super(context);
        if (i <= 0) {
            this.mGpsLayout = R.layout.map_widget_gps_layout;
        } else {
            this.mGpsLayout = i;
        }
        this.mVersionStateStr = str;
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(this.mGpsLayout, null, false);
        this.mGpsBtnView = (LottieAnimationView) inflate.findViewById(R.id.map_widget_common_icon);
        this.mGpsBtnView.setComposition(a.a(context, (String) "ic-location.json"));
        addView(inflate, new LayoutParams(-2, -2));
    }

    public void setState(final int i) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            post(new Runnable() {
                public void run() {
                    GpsMapView.this.updateState(i);
                }
            });
        } else {
            updateState(i);
        }
    }

    /* access modifiers changed from: private */
    public void updateState(int i) {
        if (i != this.mState) {
            updateGpsStateRes(this.mState, i);
            this.mState = i;
        }
    }

    public int getState() {
        return this.mState;
    }

    private void updateGpsStateRes(int i, int i2) {
        switch (i2) {
            case 0:
            case 1:
            case 3:
            case 4:
                if (this.mCurGpsBtnState != 11) {
                    startAnimator(0, 0);
                    this.mCurGpsBtnState = 11;
                    return;
                }
                return;
            case 2:
            case 5:
                if (this.mCurGpsBtnState != 12) {
                    if (i == 6 || i == 7) {
                        startAnimator(18, 9);
                    } else if (i == 0 || i == 3 || i == 1 || i == 4) {
                        startAnimator(0, 9);
                    }
                    this.mCurGpsBtnState = 12;
                    return;
                }
                return;
            case 6:
            case 7:
                if (this.mCurGpsBtnState != 13) {
                    startAnimator(9, 18);
                    this.mCurGpsBtnState = 13;
                    break;
                } else {
                    return;
                }
        }
    }

    public int getCurGPSBtnState() {
        return this.mCurGpsBtnState;
    }

    public String getLogVersionState() {
        return this.mVersionStateStr;
    }

    private void startAnimator(int i, int i2) {
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{i, i2});
        if (ofInt != null) {
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    GpsMapView.this.mGpsBtnView.setFrame(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
            ofInt.start();
        }
    }
}
