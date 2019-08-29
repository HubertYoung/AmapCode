package com.alipay.mobile.common.patch.dir;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilesdk.storage.file.ZExternalFile;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.patch.PatchUtils;
import com.dodola.patcher.utils.AppUtils;
import java.io.File;

public class FileDirPatcher {
    private static String a = "FileDirPatcher";

    public FileDirPatcher() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static boolean patchDir(Context context, String newFileFolder, String oldFileFolder, String patchFilePath, String oldFileMD5, String patchFileMD5) {
        if (context == null || TextUtils.isEmpty(newFileFolder) || TextUtils.isEmpty(oldFileFolder) || TextUtils.isEmpty(patchFilePath)) {
            LoggerFactory.getTraceLogger().warn(a, (String) "param is empty");
            return false;
        }
        try {
            if (!PatchUtils.checkFileInMd5(patchFileMD5, new File(patchFilePath))) {
                LoggerFactory.getTraceLogger().error(a, (String) "verifyPatchMD5 fail");
                return false;
            }
            int timeHash = (System.nanoTime() + newFileFolder).hashCode();
            ZExternalFile oldTarFile = new ZExternalFile(context, "tar", "oldTar-" + timeHash + ".tar");
            String oldTarPath = oldTarFile.getAbsolutePath();
            if (TextUtils.isEmpty(oldTarPath)) {
                LoggerFactory.getTraceLogger().warn(a, (String) "storage error");
                return false;
            }
            LoggerFactory.getTraceLogger().warn(a, (String) "begin dir patch");
            if (!Flater.packFolderToFile(oldFileFolder, oldTarPath)) {
                PatchUtils.deleteFileByPath(oldTarPath);
                LoggerFactory.getTraceLogger().warn(a, (String) "tar oldFileFolder fail!");
                return false;
            }
            LoggerFactory.getTraceLogger().warn(a, (String) "tar oldFileFolder success!");
            if (!PatchUtils.checkFileInMd5(oldFileMD5, oldTarFile)) {
                LoggerFactory.getTraceLogger().error(a, (String) "verifyOldTarFileMD5 fail");
                return false;
            }
            String newTarPath = new ZExternalFile(context, "tar", "newTar-" + timeHash + ".tar").getAbsolutePath();
            if (TextUtils.isEmpty(newTarPath)) {
                LoggerFactory.getTraceLogger().warn(a, (String) "storage error");
                return false;
            }
            LoggerFactory.getTraceLogger().warn(a, "begin patch " + newFileFolder);
            if (!patcher(newTarPath, oldTarPath, patchFilePath)) {
                PatchUtils.deleteFileByPath(oldTarPath);
                PatchUtils.deleteFileByPath(newTarPath);
                LoggerFactory.getTraceLogger().warn(a, (String) "patch tar fail!");
                return false;
            }
            LoggerFactory.getTraceLogger().warn(a, "patch tar success! " + newFileFolder);
            if (!Flater.unpackFileToFolder(newTarPath, newFileFolder)) {
                LoggerFactory.getTraceLogger().warn(a, (String) "untar tar fail!");
                return false;
            }
            LoggerFactory.getTraceLogger().warn(a, (String) "untar tar success!");
            PatchUtils.deleteFileByPath(oldTarPath);
            PatchUtils.deleteFileByPath(newTarPath);
            return true;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error(a, e);
            return false;
        }
    }

    public static boolean patcher(String newFilePath, String oldFilePath, String patchFilePath) {
        if (TextUtils.isEmpty(newFilePath) || TextUtils.isEmpty(oldFilePath) || TextUtils.isEmpty(patchFilePath)) {
            LoggerFactory.getTraceLogger().warn(a, (String) "param is empty");
            return false;
        }
        try {
            File patchFile = new File(patchFilePath);
            if (!PatchUtils.isFileExists(patchFilePath)) {
                LoggerFactory.getTraceLogger().error(a, (String) "verifyPatchMD5 fail");
                return false;
            } else if (!PatchUtils.IsCanUseSdCard()) {
                LoggerFactory.getTraceLogger().error(a, (String) "IsCanUseSdCard false");
                return false;
            } else if (!PatchUtils.isFileExists(oldFilePath)) {
                LoggerFactory.getTraceLogger().error(a, (String) "mOldFilePath is not exists");
                return false;
            } else if (!PatchUtils.isEnoughSpaceDoPatch(patchFile, new File(oldFilePath))) {
                LoggerFactory.getTraceLogger().error(a, (String) "space is not enough to patch");
                return false;
            } else if (!PatchUtils.creatFileDir(newFilePath)) {
                LoggerFactory.getTraceLogger().error(a, (String) "mNewFilePath can not creat");
                return false;
            } else if (AppUtils.patcher(oldFilePath, newFilePath, patchFilePath) == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error(a, e);
            return false;
        }
    }
}
