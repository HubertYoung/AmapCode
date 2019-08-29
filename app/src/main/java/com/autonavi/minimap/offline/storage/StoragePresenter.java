package com.autonavi.minimap.offline.storage;

import android.app.Activity;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.OfflineSDK;
import com.autonavi.minimap.offline.storage.OfflineStorageProgressDlg.MyOnCancelListener;
import com.autonavi.minimap.offline.storage.StorageService.IStorageCallback;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.minimap.offline.utils.UserReport;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class StoragePresenter implements IStorageCallback {
    private static final int TOAST_TYPE_NO_STORAGE = 2;
    private static final int TOAST_TYPE_STORAGE_CANCEL = 4;
    private static final int TOAST_TYPE_STORAGE_FAIL = 3;
    private static final int TOAST_TYPE_STORAGE_FINISH = 1;
    private boolean isCancel = false;
    private OfflineStorageProgressDlg mCopyDataDialog;
    private JsFunctionCallback mJsCallback;
    private StorageService mStorageService = new StorageService(this);

    public void onDestroy() {
    }

    public void setJsCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsCallback = jsFunctionCallback;
    }

    public void cancelCopyDataDialog() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager != null) {
            downloadManager.cancelSwitchDir();
        }
    }

    public void confirmChangeWarningDialog(String str, String str2) {
        this.isCancel = false;
        doStorageChange(str, str2);
        UserReport.actionLogV2(UserReport.PAGE_OFFLINEDATA_STORAGEINFO, "B003");
    }

    public void retryAccessErrorDialog(String str, String str2) {
        doStorageChange(str, str2);
    }

    public void clickStorageItemView(String str, String str2) {
        int i;
        switch (OfflineSDK.getInstance().getDownloadingDataType()) {
            case 1:
                i = R.string.offline_storage_warn_downing_offline;
                break;
            case 2:
                i = R.string.offline_storage_warn_downing_navitts;
                break;
            case 3:
                i = R.string.offline_storage_warn_downing;
                break;
            default:
                i = 0;
                break;
        }
        if (i != 0) {
            ToastHelper.showToast(OfflineUtil.getString(i));
        } else {
            confirmChangeWarningDialog(str, str2);
        }
    }

    private void doStorageChange(String str, String str2) {
        this.mStorageService.doStorageChange(str, str2);
        showCopyDataDialog(0);
    }

    private void showToast(int i) {
        switch (i) {
            case 1:
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.offline_storage_finish));
                return;
            case 2:
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.no_Storage));
                return;
            case 3:
                showStorageErrorDialog(AMapAppGlobal.getTopActivity());
                return;
            case 4:
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.offline_storage_cancle));
                break;
        }
    }

    public static void showStorageErrorDialog(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            public final void run() {
                if (activity != null && !activity.isFinishing()) {
                    Builder builder = new Builder(activity);
                    builder.setMessage((CharSequence) "存储路径被占用，建议关闭其他应用或重启设备");
                    builder.setPositiveButton((CharSequence) "我知道了", (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                        public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                        }
                    });
                    AMapPageUtil.startAlertDialogPage(builder);
                }
            }
        });
    }

    @SuppressFBWarnings({"RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT"})
    public void onStatusChanged(int i) {
        switch (i) {
            case 5:
                dismissAllDialog();
                if (this.mJsCallback != null) {
                    this.mJsCallback.callback(new Object[0]);
                    return;
                }
                break;
            case 6:
                showFailToast();
                dismissAllDialog();
                break;
        }
    }

    public void onProgress(int i) {
        showCopyDataDialog(i);
    }

    public void showCopyDataDialog(int i) {
        String str;
        if (i >= 0 && i <= 100 && !this.isCancel) {
            Activity topActivity = AMapAppGlobal.getTopActivity();
            if (topActivity != null && !topActivity.isFinishing()) {
                if (this.mCopyDataDialog == null) {
                    this.mCopyDataDialog = new OfflineStorageProgressDlg(topActivity);
                    this.mCopyDataDialog.setCancelable(false);
                    this.mCopyDataDialog.setCanceledOnTouchOutside(false);
                    this.mCopyDataDialog.setMyCancelListener(new MyOnCancelListener() {
                        public final void onCancel() {
                            StoragePresenter.this.cancelCopyDataDialog();
                        }
                    });
                }
                if (i < 10) {
                    StringBuilder sb = new StringBuilder(",  ");
                    sb.append(i);
                    sb.append("%");
                    str = sb.toString();
                } else if (i < 100) {
                    StringBuilder sb2 = new StringBuilder(", ");
                    sb2.append(i);
                    sb2.append("%");
                    str = sb2.toString();
                } else {
                    StringBuilder sb3 = new StringBuilder(",");
                    sb3.append(i);
                    sb3.append("%");
                    str = sb3.toString();
                }
                OfflineStorageProgressDlg offlineStorageProgressDlg = this.mCopyDataDialog;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(AMapAppGlobal.getApplication().getString(R.string.offline_storage_progress_msg));
                sb4.append(str);
                offlineStorageProgressDlg.setMessage(sb4.toString());
                if (!this.mCopyDataDialog.isShowing()) {
                    this.mCopyDataDialog.show();
                }
            }
        }
    }

    public void showFailToast() {
        ToastHelper.showLongToast(OfflineUtil.getString(R.string.offline_storage_fail_msg));
    }

    public void onError(int i, String str, String str2) {
        dismissAllDialog();
        if (i != 8) {
            switch (i) {
                case 1:
                    showNoSpaceToast(str);
                    return;
                case 2:
                case 3:
                    showAccessErrorDialog(str, str2);
                    return;
                case 4:
                    showToast(3);
                    return;
            }
        } else {
            if (!this.isCancel) {
                showToast(4);
            }
            this.isCancel = true;
        }
    }

    public void showAccessErrorDialog(final String str, final String str2) {
        Builder builder = new Builder(AMapAppGlobal.getTopActivity());
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(OfflineUtil.getString(R.string.offline_storage_access_msg));
        builder.setTitle((CharSequence) sb.toString());
        builder.setPositiveButton(R.string.retry, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                StoragePresenter.this.retryAccessErrorDialog(str, str2);
            }
        });
        builder.setNegativeButton(R.string.cancle, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
            }
        });
        AMapPageUtil.startAlertDialogPage(builder);
    }

    public void dismissAllDialog() {
        if (this.mCopyDataDialog != null && this.mCopyDataDialog.isShowing()) {
            this.mCopyDataDialog.dismiss();
        }
    }

    public void showNoSpaceToast(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(OfflineUtil.getString(R.string.offline_storage_size_msg));
        ToastHelper.showLongToast(sb.toString());
    }
}
