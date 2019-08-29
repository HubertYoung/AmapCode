package com.alipay.android.phone.mobilecommon.multimedia.material;

import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnCancelListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnCompleteListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnDownloadTaskAddListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnErrorListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnProgressListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APPackageQueryCallback;

public class APMaterialDownloadRequest {
    private String id;
    private APOnCancelListener mCancelListener;
    private APOnCompleteListener mCompleteListener;
    private APOnErrorListener mErrorListener;
    private APPackageQueryCallback mMaterialPackageQueryCallback;
    private APOnProgressListener mProgressListener;
    private APOnDownloadTaskAddListener mTaskAddListener;

    public APMaterialDownloadRequest(String id2) {
        setId(id2);
    }

    public String getId() {
        return this.id;
    }

    public APMaterialDownloadRequest setId(String id2) {
        this.id = id2;
        if (id2 != null) {
            return this;
        }
        throw new IllegalArgumentException("id must be not null!!");
    }

    public APOnProgressListener getProgressListener() {
        return this.mProgressListener;
    }

    public APMaterialDownloadRequest setProgressListener(APOnProgressListener progressListener) {
        this.mProgressListener = progressListener;
        return this;
    }

    public APOnCompleteListener getCompleteListener() {
        return this.mCompleteListener;
    }

    public APMaterialDownloadRequest setCompleteListener(APOnCompleteListener completeListener) {
        this.mCompleteListener = completeListener;
        return this;
    }

    public APOnErrorListener getErrorListener() {
        return this.mErrorListener;
    }

    public APMaterialDownloadRequest setErrorListener(APOnErrorListener errorListener) {
        this.mErrorListener = errorListener;
        return this;
    }

    public APOnCancelListener getCancelListener() {
        return this.mCancelListener;
    }

    public APMaterialDownloadRequest setCancelListener(APOnCancelListener cancelListener) {
        this.mCancelListener = cancelListener;
        return this;
    }

    public APPackageQueryCallback getMaterialPackageQueryCallback() {
        return this.mMaterialPackageQueryCallback;
    }

    public APMaterialDownloadRequest setMaterialPackageQueryCallback(APPackageQueryCallback materialPackageQueryCallback) {
        this.mMaterialPackageQueryCallback = materialPackageQueryCallback;
        return this;
    }

    public APOnDownloadTaskAddListener getTaskAddListener() {
        return this.mTaskAddListener;
    }

    public APMaterialDownloadRequest setTaskAddListener(APOnDownloadTaskAddListener taskAddListener) {
        this.mTaskAddListener = taskAddListener;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.id.equals(((APMaterialDownloadRequest) o).id);
    }

    public int hashCode() {
        return this.id.hashCode();
    }
}
