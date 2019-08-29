package com.alipay.mobile.framework.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import com.alipay.mobile.aspect.FrameworkPointcutExecution;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.AppLoadException;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;

public abstract class BaseFragment extends Fragment {
    public static final String KEY_APP_ID = "app_id";
    public static final String KEY_EXTRAS = "mExtras";
    public static final String TAG = "FragmentApplication";
    private static InterruptOnTouchListener a = new InterruptOnTouchListener();
    protected FragmentApplication mFragmentApplication;
    protected MicroApplicationContext mMicroApplicationContext;

    public abstract String getBundleName();

    public FragmentApplication getFragmentApplication() {
        return this.mFragmentApplication;
    }

    public void onAttach(Context context) {
        LauncherApplicationAgent application = LauncherApplicationAgent.getInstance();
        application.getBundleContext().appendResourcesByBundleName(context, getBundleName());
        super.onAttach(context);
        this.mMicroApplicationContext = application.getMicroApplicationContext();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String appId = bundle.getString("app_id", "");
            Bundle _bundle = bundle.getBundle("mExtras");
            if (_bundle != null) {
                _bundle.setClassLoader(getClass().getClassLoader());
                try {
                    ReflectUtil.setFieldValue(Fragment.class, this, "mArguments", _bundle);
                } catch (Throwable e) {
                    throw new AppLoadException(e);
                }
            }
            this.mFragmentApplication = (FragmentApplication) this.mMicroApplicationContext.findAppById(appId);
            TraceLogger.v((String) TAG, "BaseFragment.onAttach(" + this + ") appId: " + appId);
            if (this.mFragmentApplication == null && !TextUtils.isEmpty(appId)) {
                this.mFragmentApplication = (FragmentApplication) this.mMicroApplicationContext.createAppById(appId);
                if (this.mFragmentApplication == null) {
                    return;
                }
            }
            this.mFragmentApplication.setIsPrevent(false);
            return;
        }
        throw new AppLoadException("appId=" + "");
    }

    public void onCreate(Bundle savedInstanceState) {
        Object[] args = {savedInstanceState};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENT_ONCREATE, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENT_ONCREATE, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(savedInstanceState);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENT_ONCREATE, this, args);
    }

    private void a(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onStart() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENT_ONSTART, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENT_ONSTART, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENT_ONSTART, this, args);
    }

    private void a() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        if (getView() != null) {
            getView().setOnTouchListener(a);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mFragmentApplication != null) {
            this.mFragmentApplication.remove(this);
        }
    }

    public void onReady(Bundle bundle) {
        if (this.mFragmentApplication != null && !TextUtils.isEmpty(this.mFragmentApplication.getAppId())) {
            if (bundle == null) {
                bundle = new Bundle();
                bundle.putString("appId", this.mFragmentApplication.getAppId());
            } else if (!bundle.containsKey("appId")) {
                bundle.putString("appId", this.mFragmentApplication.getAppId());
            }
        }
        Object[] args = {bundle};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENT_ONREADY, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENT_ONREADY, this, args);
        if (aroundResult != null) {
            ((Boolean) aroundResult.first).booleanValue();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENT_ONREADY, this, args);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Object[] args = {view, savedInstanceState};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.BASEFRAGMENT_ONVIEWCREATED, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.BASEFRAGMENT_ONVIEWCREATED, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(view, savedInstanceState);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.BASEFRAGMENT_ONVIEWCREATED, this, args);
    }

    private void a(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
