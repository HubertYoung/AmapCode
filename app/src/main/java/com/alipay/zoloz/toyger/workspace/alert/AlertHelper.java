package com.alipay.zoloz.toyger.workspace.alert;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import com.alipay.mobile.security.faceauth.InvokeType;
import com.alipay.zoloz.toyger.interfaces.ToygerCallback;
import com.alipay.zoloz.toyger.upload.UploadManager;
import com.alipay.zoloz.toyger.util.DialogTypeIndex;
import com.alipay.zoloz.toyger.workspace.ToygerWorkspace;
import com.alipay.zoloz.toyger.workspace.alert.AlertTypeHelper.AlertContext;

public class AlertHelper implements OnClickListener, View.OnClickListener {
    private boolean isAlertRunning;
    private boolean isAuthInBackground = false;
    private String mAlertNegative;
    private String mAlertPositive;
    private int mAlertReturnCode;
    private AlertTypeHelper mAlertTypeHelper;
    private int mRetryTime;
    private String mScene;
    private ToygerCallback mToygerCallback;
    private ToygerWorkspace mToygerWorkspace;
    private UploadManager mUploadManager;

    public AlertHelper(Context context, ToygerWorkspace toygerWorkspace, ToygerCallback toygerCallback, UploadManager uploadManager) {
        this.mToygerWorkspace = toygerWorkspace;
        this.mToygerCallback = toygerCallback;
        this.mUploadManager = uploadManager;
        this.mAlertTypeHelper = new AlertTypeHelper(toygerCallback, context);
    }

    public int getAlertReturnCode(AlertType alertType) {
        return this.mAlertTypeHelper.getAlertContext(alertType, this.mRetryTime).returnCode;
    }

    public void setAuthInBackground(boolean z) {
        this.isAuthInBackground = z;
    }

    public void alert(AlertType alertType) {
        if (!this.isAuthInBackground && !this.isAlertRunning) {
            this.mToygerWorkspace.stopTimerTask();
            this.mToygerWorkspace.pauseToygerFaceService();
            this.isAlertRunning = true;
            if (this.mRetryTime >= this.mToygerCallback.getRemoteConfig().getColl().getRetry()) {
                alertType = AlertType.ALERT_FACE_FAIL_OVER_MAX_TIME;
            }
            AlertContext alertContext = this.mAlertTypeHelper.getAlertContext(alertType, this.mRetryTime);
            this.mAlertPositive = alertContext.positive;
            this.mAlertNegative = alertContext.negative;
            this.mScene = alertContext.scene;
            this.mAlertReturnCode = alertContext.returnCode;
            this.mToygerWorkspace.alertRecord(this.mScene);
            if (alertContext.tag != DialogTypeIndex.DIALOG_TYPE_INDEX_INVALID) {
                this.mToygerCallback.alert(alertContext.tag, alertContext.title, alertContext.msg1, alertContext.msg2, alertContext.positive, this, alertContext.negative, this, true, alertContext.showIcon);
                if (alertType != AlertType.ALERT_REMOTE_NETWORK_ERROR) {
                    this.mRetryTime++;
                    this.mToygerCallback.setRetryTime(this.mRetryTime);
                }
            }
        }
    }

    public void onClick(View view) {
        this.mToygerWorkspace.clickBackRecord();
        alert(AlertType.ALERT_BACK);
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        DialogTypeIndex alertTag = this.mToygerCallback.getAlertTag();
        switch (i) {
            case -2:
                pressAlertButton(alertTag, false);
                return;
            case -1:
                pressAlertButton(alertTag, true);
                return;
            default:
                return;
        }
    }

    public boolean isAlertRunning() {
        return this.isAlertRunning;
    }

    /* access modifiers changed from: protected */
    public void pressAlertButton(DialogTypeIndex dialogTypeIndex, boolean z) {
        switch (a.a[dialogTypeIndex.ordinal()]) {
            case 1:
                this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                break;
            case 2:
                if (!z) {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertNegative, this.mScene);
                    this.mToygerWorkspace.retry();
                    break;
                } else {
                    if (this.mUploadManager != null) {
                        this.mUploadManager.uploadBehaviourLog(InvokeType.INTERRUPT);
                    }
                    this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                    this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                    break;
                }
            case 3:
                this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                break;
            case 4:
                if (!z) {
                    this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                    if (this.mUploadManager != null) {
                        this.mUploadManager.uploadBehaviourLog(InvokeType.INTERRUPT);
                        break;
                    }
                } else {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                    this.mToygerWorkspace.retry();
                    break;
                }
                break;
            case 5:
                if (z) {
                    this.mToygerCallback.gotoSettings();
                }
                this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                break;
            case 6:
                this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                break;
            case 7:
                if (!z) {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertNegative, this.mScene);
                    this.mToygerWorkspace.responseWithCode(300);
                    break;
                } else {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                    this.mToygerWorkspace.retry();
                    break;
                }
            case 8:
                this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                this.mToygerWorkspace.responseWithCode(300);
                break;
            case 9:
                this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                this.mToygerWorkspace.responseWithCode(209);
                break;
            case 10:
                if (!z) {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertNegative, this.mScene);
                    this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                    break;
                } else {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                    this.mToygerWorkspace.retry();
                    break;
                }
            case 11:
                this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                this.mToygerWorkspace.responseWithCode(300);
                break;
            case 12:
                if (z) {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                    this.mToygerWorkspace.retry();
                    break;
                } else {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertNegative, this.mScene);
                    this.mToygerWorkspace.responseWithCode(300);
                    break;
                }
            case 13:
                this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                break;
            case 14:
                if (!z) {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertNegative, this.mScene);
                    this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                    break;
                } else {
                    this.mToygerWorkspace.retry();
                    this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                    break;
                }
            case 15:
                if (!z) {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertNegative, this.mScene);
                    this.mToygerWorkspace.responseWithCode(300);
                    break;
                } else {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                    this.mToygerWorkspace.retry();
                    break;
                }
            case 16:
                this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                this.mToygerWorkspace.responseWithCode(300);
                break;
            case 17:
                if (!z) {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertNegative, this.mScene);
                    this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                    break;
                } else {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                    this.mToygerWorkspace.retry();
                    break;
                }
            case 18:
                this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                break;
            case 19:
                if (!z) {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertNegative, this.mScene);
                    this.mToygerWorkspace.responseWithCode(300);
                    break;
                } else {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                    this.mToygerWorkspace.retry();
                    break;
                }
            case 20:
                this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                this.mToygerWorkspace.responseWithCode(300);
                break;
            case 21:
                if (!z) {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertNegative, this.mScene);
                    this.mToygerWorkspace.responseWithCode(this.mAlertReturnCode);
                    break;
                } else {
                    this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                    this.mToygerWorkspace.retry();
                    break;
                }
            case 22:
                this.mToygerWorkspace.alertClickRecord(this.mAlertPositive, this.mScene);
                this.mToygerWorkspace.responseWithCode(209);
                break;
            case 23:
                this.mToygerWorkspace.responseWithCode(105);
                break;
        }
        this.isAlertRunning = false;
    }
}
