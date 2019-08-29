package com.alipay.mobile.nebula.appcenter.download;

import android.content.Context;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.util.H5ZExternalFile;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import java.io.File;

public class H5DownloadRequest implements Parcelable {
    private static final String ALIPAY = "alipay";
    public static final String AUTO_LOGIN = "auto_login";
    public static final Creator<H5DownloadRequest> CREATOR = new Creator<H5DownloadRequest>() {
        public final H5DownloadRequest createFromParcel(Parcel source) {
            return new H5DownloadRequest(source);
        }

        public final H5DownloadRequest[] newArray(int size) {
            return new H5DownloadRequest[size];
        }
    };
    private static final String DOWNLOAD = "downloads";
    public static final String FULL_RPC_SCENE = "full_rpc_scene";
    public static final String NET_CHANGE = "net_change";
    public static final String SYNC_SCENE = "sync_scene";
    private static final String TAG = "H5DownloadRequest";
    public static final String USER_LEAVE_HINT_SCENE = "user_leave_hint_scene";
    public static final String nebulaDownload = "nebulaDownload";
    public static final String nebulaH5App = "nebulaH5App";
    private static String sLastDestPath = null;
    private String appId;
    private boolean autoInstall;
    private boolean autoLaunch;
    private String description;
    private String downloadUrl;
    private String fileName;
    private boolean needProgress;
    private String scene;
    private boolean showRunningNotification;
    private String targetFileName;
    private String title;
    private String version;

    public H5DownloadRequest() {
        this.targetFileName = "";
        this.needProgress = true;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName2) {
        this.fileName = fileName2;
    }

    public String getTargetFileName() {
        return this.targetFileName;
    }

    public void setTargetFileName(String targetFileName2) {
        this.targetFileName = targetFileName2;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl2) {
        this.downloadUrl = downloadUrl2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public boolean isShowRunningNotification() {
        return this.showRunningNotification;
    }

    public void setShowRunningNotification(boolean showRunningNotification2) {
        this.showRunningNotification = showRunningNotification2;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId2) {
        this.appId = appId2;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version2) {
        this.version = version2;
    }

    public boolean isAutoInstall() {
        return this.autoInstall;
    }

    public void setAutoInstall(boolean autoInstall2) {
        this.autoInstall = autoInstall2;
    }

    public boolean isNeedProgress() {
        return this.needProgress;
    }

    public void setNeedProgress(boolean needProgress2) {
        this.needProgress = needProgress2;
    }

    public boolean isAutoLaunch() {
        return this.autoLaunch;
    }

    public void setAutoLaunch(boolean autoLaunch2) {
        this.autoLaunch = autoLaunch2;
    }

    public String getScene() {
        return this.scene;
    }

    public void setScene(String scene2) {
        this.scene = scene2;
    }

    public static String getDefaultDownloadDir(Context context) {
        return getAlipayAmrDownloadPath(context);
    }

    private static String getAlipayAmrDownloadPath(Context context) {
        String destPath;
        try {
            if (sLastDestPath != null && sLastDestPath.startsWith(Environment.getExternalStorageDirectory().toString())) {
                return sLastDestPath;
            }
        } catch (Throwable th) {
            sLastDestPath = null;
        }
        try {
            destPath = Environment.getExternalStorageDirectory() + File.separator + "alipay" + File.separator + context.getPackageName() + File.separator + nebulaDownload + File.separator + DOWNLOAD;
            if (!TextUtils.isEmpty(destPath)) {
                sLastDestPath = destPath;
            }
        } catch (Throwable th2) {
            destPath = sLastDestPath;
        }
        if (TextUtils.isEmpty(destPath) || H5FileUtil.exists(destPath)) {
            return destPath;
        }
        H5Log.d(TAG, " H5FileUtil mkdirs : " + destPath);
        H5FileUtil.mkdirs(destPath, true);
        return destPath;
    }

    public boolean isFromPreDownload() {
        return FULL_RPC_SCENE.equals(this.scene) || SYNC_SCENE.equals(this.scene) || USER_LEAVE_HINT_SCENE.equals(this.scene) || AUTO_LOGIN.equals(this.scene) || NET_CHANGE.equals(this.scene);
    }

    public static String getOldDownloadDir(Context context) {
        H5ZExternalFile h5ZExternalFile = new H5ZExternalFile(context, "nebulaH5App", DOWNLOAD);
        String downloadDir = h5ZExternalFile.getAbsolutePath();
        H5Log.d(TAG, "downloadDir:" + downloadDir);
        if (!h5ZExternalFile.exists() && !h5ZExternalFile.mkdirs()) {
            H5Log.e((String) TAG, "path not exist but fail to create: " + downloadDir);
            return downloadDir;
        } else if (h5ZExternalFile.isDirectory()) {
            return downloadDir;
        } else {
            H5Log.e((String) TAG, downloadDir + " dir exist,but not directory.");
            return null;
        }
    }

    public int describeContents() {
        return 0;
    }

    private H5DownloadRequest(Parcel source) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        this.targetFileName = "";
        this.needProgress = true;
        this.downloadUrl = source.readString();
        this.title = source.readString();
        this.description = source.readString();
        this.showRunningNotification = source.readInt() != 0;
        this.appId = source.readString();
        this.fileName = source.readString();
        this.targetFileName = source.readString();
        if (source.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.autoInstall = z;
        if (source.readInt() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.needProgress = z2;
        this.autoLaunch = source.readInt() == 0 ? false : z3;
        this.version = source.readString();
        this.scene = source.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2;
        int i3 = 1;
        dest.writeString(this.downloadUrl);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.showRunningNotification ? 1 : 0);
        dest.writeString(this.appId);
        dest.writeString(this.fileName);
        dest.writeString(this.targetFileName);
        if (this.autoInstall) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        if (this.needProgress) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        dest.writeInt(i2);
        if (!this.autoLaunch) {
            i3 = 0;
        }
        dest.writeInt(i3);
        dest.writeString(this.version);
        dest.writeString(this.scene);
    }
}
