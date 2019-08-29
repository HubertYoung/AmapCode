package com.oppo.oms.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.oppo.oms.sdk.Util.ThreadExecutor;
import com.oppo.oms.sdk.entity.ErrorEntity;
import com.oppo.oms.sdk.entity.FeatureEntity;
import com.oppo.oms.sdk.entity.FeatureRequest;
import com.oppo.oms.sdk.entity.Result;

public class OmsService {
    private static final String TAG = "OmsService";
    public static OmsService sInstance = new OmsService();
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public OmsServiceHelper mOmsServiceHelper;

    public interface CallBack<T> {
        void onResult(T t);
    }

    private OmsService() {
    }

    private void initOmsServiceHelper(Context context) {
        this.mOmsServiceHelper = new OmsServiceHelper(context);
    }

    public static OmsService getInstance() {
        return sInstance;
    }

    private void initHandler() {
        if (Looper.myLooper() != null) {
            this.mHandler = new Handler();
        }
    }

    public void requestFeature(Context context, final FeatureRequest featureRequest, final CallBack<Result<ErrorEntity, FeatureEntity>> callBack) {
        initOmsServiceHelper(context);
        initHandler();
        ThreadExecutor.getInstance().execute(new Runnable() {
            public void run() {
                final Result<ErrorEntity, FeatureEntity> requestFeature = OmsService.this.mOmsServiceHelper.requestFeature(featureRequest);
                if (OmsService.this.mHandler != null) {
                    OmsService.this.mHandler.post(new Runnable() {
                        public void run() {
                            callBack.onResult(requestFeature);
                        }
                    });
                } else {
                    callBack.onResult(requestFeature);
                }
            }
        });
    }

    public Result<ErrorEntity, FeatureEntity> requestFeature(Context context, FeatureRequest featureRequest) {
        initOmsServiceHelper(context);
        return this.mOmsServiceHelper.requestFeature(featureRequest);
    }
}
