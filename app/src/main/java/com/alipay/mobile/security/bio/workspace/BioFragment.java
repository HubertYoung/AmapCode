package com.alipay.mobile.security.bio.workspace;

import android.app.Activity;
import android.support.v4.app.Fragment;
import com.alipay.mobile.security.bio.exception.BioObjectNotInitialException;
import com.alipay.mobile.security.bio.service.BioServiceManager;

public class BioFragment extends Fragment {
    protected BioFragmentCallBack mBioFragmentCallBack;
    public BioServiceManager mBioServiceManager;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mBioServiceManager = BioServiceManager.getCurrentInstance();
            this.mBioFragmentCallBack = (BioFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must BioFragmentService");
        }
    }

    /* access modifiers changed from: protected */
    public <T> T getSystemService(Class<T> cls) {
        if (this.mBioServiceManager != null) {
            return this.mBioServiceManager.getBioService(cls);
        }
        throw new BioObjectNotInitialException("");
    }

    /* access modifiers changed from: protected */
    public <T> T getExtService(Class<T> cls) {
        if (this.mBioServiceManager != null) {
            return this.mBioServiceManager.getBioService(cls);
        }
        throw new BioObjectNotInitialException("");
    }

    public void backward() {
        if (this.mBioFragmentCallBack != null) {
            this.mBioFragmentCallBack.backward(null);
        }
    }

    public void forward(BioFragment bioFragment) {
        if (this.mBioFragmentCallBack != null) {
            this.mBioFragmentCallBack.forward(null, bioFragment);
        }
    }

    /* access modifiers changed from: protected */
    public void finish() {
        if (this.mBioFragmentCallBack != null) {
            this.mBioFragmentCallBack.finish(null);
        }
    }
}
