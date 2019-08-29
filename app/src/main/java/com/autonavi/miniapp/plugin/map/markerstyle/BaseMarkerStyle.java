package com.autonavi.miniapp.plugin.map.markerstyle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5Page;
import java.lang.ref.WeakReference;

public abstract class BaseMarkerStyle {
    protected WeakReference<Context> mContext;
    protected int mMeasuredHeight = 0;
    protected int mMeasuredWidth = 0;
    protected WeakReference<H5Page> mPage;

    public interface Callback {
        public static final int ERRCODE_MEASURE = -1;
        public static final int ERRCODE_SUCCESS = 0;

        void call(Bitmap bitmap, int i);
    }

    /* access modifiers changed from: 0000 */
    public abstract BaseMarkerStyle bindData(JSONObject jSONObject);

    /* access modifiers changed from: 0000 */
    public abstract void getBitmapImpl(Callback callback);

    /* access modifiers changed from: 0000 */
    public abstract boolean measure();

    BaseMarkerStyle(H5Page h5Page, Context context) {
        this.mContext = new WeakReference<>(context);
        this.mPage = new WeakReference<>(h5Page);
    }

    public final void getBitmap(Callback callback) {
        if (callback != null) {
            try {
                if (measure()) {
                    getBitmapImpl(callback);
                } else {
                    callback.call(null, -1);
                }
            } catch (Throwable th) {
                LoggerFactory.getTraceLogger().error((String) "BaseMarkerStyle", th);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final Matrix getMatrix(Rect rect, Rect rect2) {
        float f;
        float f2;
        Matrix matrix = new Matrix();
        int width = rect.width();
        int height = rect.height();
        int width2 = rect2.width();
        int height2 = rect2.height();
        if ((width < 0 || width2 == width) && (height < 0 || height2 == height)) {
            return matrix;
        }
        float f3 = 0.0f;
        if (width * height2 > width2 * height) {
            f = ((float) height2) / ((float) height);
            f2 = (((float) width2) - (((float) width) * f)) * 0.5f;
        } else {
            float f4 = ((float) width2) / ((float) width);
            f3 = (((float) height2) - (((float) height) * f4)) * 0.5f;
            f = f4;
            f2 = 0.0f;
        }
        matrix.setScale(f, f);
        matrix.postTranslate((float) Math.round(f2), (float) Math.round(f3));
        return matrix;
    }

    public static BaseMarkerStyle fromJSONObject(JSONObject jSONObject, H5Page h5Page, Context context) {
        switch (jSONObject.getIntValue("type")) {
            case 1:
                return new MarkerStyle1(h5Page, context).bindData(jSONObject);
            case 2:
                return new MarkerStyle2(h5Page, context).bindData(jSONObject);
            case 3:
                return new MarkerStyle3(h5Page, context).bindData(jSONObject);
            default:
                return null;
        }
    }
}
