package com.alipay.mobile.tinyappcommon.h5plugin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.plugins.Constant;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.basebridge.H5BaseBridgeContext;
import com.alipay.mobile.nebula.filecache.DiskUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.H5ZipUtil;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.utils.TinyAppEventUtils;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;
import com.alipay.mobile.tinyappcommon.utils.codec.HexUtils;
import com.alipay.mobile.tinyappcommon.utils.io.SafeLibCore;
import com.alipay.mobile.tinyappcommon.utils.io.SafeStructStat;
import com.alipay.mobile.tinyappcommon.utils.io.TinyAppFileUtils;
import com.alipay.mobile.tinyappcommon.utils.pkg.TinyAppPkgUtils;
import com.alipay.mobile.tinyappcommon.utils.zip.ZipFileInfo;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.taobao.accs.common.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.Iterator;

public class H5FSManagePlugin extends H5SimplePlugin {
    private static final String ACTION_ACCESS = "access";
    private static final String ACTION_APPEND_FILE = "appendFile";
    private static final String ACTION_COPY_FILE = "copyFile";
    public static final String ACTION_FS_MANAGE = "fsManage";
    private static final String ACTION_GET_FILE_INFO = "getFileInfo";
    private static final String ACTION_GET_SAVED_FILE_LIST = "getSavedFileList";
    private static final String ACTION_MKDIR = "mkdir";
    private static final String ACTION_READ_DIR = "readdir";
    private static final String ACTION_READ_FILE = "readFile";
    private static final String ACTION_REMOVE_SAVED_FILE = "removeSavedFile";
    private static final String ACTION_RENAME = "rename";
    private static final String ACTION_RMDIR = "rmdir";
    private static final String ACTION_SAVE_FILE = "saveFile";
    private static final String ACTION_STAT = "stat";
    private static final String ACTION_UNLINK = "unlink";
    private static final String ACTION_UNZIP = "unzip";
    private static final String ACTION_WRITE_FILE = "writeFile";
    private static final String DATA_TYPE_AF = "ArrayBuffer";
    private static final String ENCODING_BASE64 = "base64";
    private static final String ENCODING_BINARY = "binary";
    private static final String ENCODING_HEX = "hex";
    private static final int ERROR_DENIED = 10024;
    private static final int ERROR_DIR_NOT_EMPTY = 10027;
    private static final int ERROR_EXISTS = 10025;
    private static final int ERROR_IS_DIR = 10023;
    private static final int ERROR_IS_NOT_DIR = 10026;
    private static final int ERROR_NOT_EXISTS = 10022;
    private static final int ERROR_SIZE_OVERFLOW = 10028;
    private static final int ERROR_USER_INVALID = 10021;
    private static final long MAX_DIR_SIZE = 52428800;
    private static final long MAX_FILE_SIZE = 10485760;
    public static final String PATH_PREFIX = "https://usr/";
    public static final String PATH_ROOT = "https://usr";
    public static final int PATH_ROOT_LENGTH = 11;
    private static final String TAG = "H5FSManagePlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_FS_MANAGE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (ACTION_FS_MANAGE.equals(event.getAction())) {
            return handleAction(event, context, H5Utils.getString(event.getParam(), (String) "action"));
        }
        return false;
    }

    private boolean handleAction(final H5Event event, final H5BridgeContext context, String actionType) {
        H5Log.d(TAG, "actionType: " + actionType);
        if (TextUtils.isEmpty(actionType)) {
            context.sendError(event, Error.INVALID_PARAM);
            return true;
        } else if (TextUtils.isEmpty(TinyappUtils.getUserId())) {
            context.sendError(10021, (String) "用户未登录");
            return true;
        } else if (event.getH5page() == null || event.getParam() == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return true;
        } else if ("access".equals(actionType)) {
            access(event, context);
            return true;
        } else if (ACTION_APPEND_FILE.equals(actionType)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5FSManagePlugin.this.appendFile(event, context);
                }
            });
            return true;
        } else if (ACTION_SAVE_FILE.equals(actionType)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5FSManagePlugin.this.saveFile(event, context);
                }
            });
            return true;
        } else if (ACTION_GET_SAVED_FILE_LIST.equals(actionType)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5FSManagePlugin.this.getSavedFileList(event, context);
                }
            });
            return true;
        } else if (ACTION_REMOVE_SAVED_FILE.equals(actionType)) {
            removeSavedFile(event, context);
            return true;
        } else if (ACTION_COPY_FILE.equals(actionType)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5FSManagePlugin.this.copyFile(event, context);
                }
            });
            return true;
        } else if (ACTION_GET_FILE_INFO.equals(actionType)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5FSManagePlugin.this.getFileInfo(event, context);
                }
            });
            return true;
        } else if (ACTION_MKDIR.equals(actionType)) {
            mkdir(event, context);
            return true;
        } else if (ACTION_READ_FILE.equals(actionType)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5FSManagePlugin.this.readFile(event, context);
                }
            });
            return true;
        } else if (ACTION_READ_DIR.equals(actionType)) {
            readDir(event, context);
            return true;
        } else if (ACTION_RENAME.equals(actionType)) {
            rename(event, context);
            return true;
        } else if (ACTION_RMDIR.equals(actionType)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5FSManagePlugin.this.rmdir(event, context);
                }
            });
            return true;
        } else if (ACTION_STAT.equals(actionType)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5FSManagePlugin.this.stat(event, context);
                }
            });
            return true;
        } else if (ACTION_UNLINK.equals(actionType)) {
            unlink(event, context);
            return true;
        } else if (ACTION_UNZIP.equals(actionType)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5FSManagePlugin.this.unzip(event, context);
                }
            });
            return true;
        } else if (!ACTION_WRITE_FILE.equals(actionType)) {
            return false;
        } else {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5FSManagePlugin.this.writeFile(event, context);
                }
            });
            return true;
        }
    }

    private void access(H5Event event, final H5BridgeContext context) {
        final H5Page h5Page = event.getH5page();
        final String path = H5Utils.getString(event.getParam(), (String) "path");
        if (TextUtils.isEmpty(path)) {
            context.sendError(event, Error.INVALID_PARAM);
        } else if (TinyAppFileUtils.containsRelativeParentPath(path)) {
            context.sendError(event, Error.INVALID_PARAM);
        } else {
            String localPath = usrPathToLocalPath(path, h5Page);
            if (TextUtils.isEmpty(localPath)) {
                if (path.startsWith("https://resource/")) {
                    localPath = TinyappUtils.transferApPathToLocalPath(path);
                } else {
                    H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                        public void run() {
                            if (TinyAppPkgUtils.exists(h5Page, path)) {
                                context.sendSuccess();
                            } else {
                                context.sendError(10022, "文件/目录不存在 " + path);
                            }
                        }
                    });
                    return;
                }
            }
            if (new File(localPath).exists()) {
                context.sendSuccess();
            } else {
                context.sendError(10022, "文件/目录不存在 " + path);
            }
        }
    }

    /* access modifiers changed from: private */
    public void appendFile(H5Event event, H5BridgeContext context) {
        writeFile(event, context, true);
    }

    private void writeFile(H5Event event, H5BridgeContext context, boolean append) {
        byte[] binaryData;
        H5Page h5Page = event.getH5page();
        JSONObject eventParams = event.getParam();
        String path = H5Utils.getString(eventParams, (String) "filePath");
        if (TinyAppFileUtils.containsRelativeParentPath(path)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String localPath = usrPathToLocalPath(path, h5Page);
        if (TextUtils.isEmpty(localPath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        File localFile = new File(localPath);
        if (append && !localFile.exists()) {
            context.sendError(10022, "指定的 filePath 文件不存在 " + path);
        } else if (localFile.isDirectory()) {
            context.sendError(10023, "指定的 filePath 是一个已经存在的目录 " + path);
        } else {
            if (append) {
                if (!localFile.canWrite()) {
                    context.sendError(10024, "指定的 filePath 路径没有写权限 " + path);
                    return;
                }
            } else if (!localFile.getParentFile().canWrite()) {
                context.sendError(10024, "指定的 filePath 路径没有写权限 " + path);
                return;
            }
            if (!TinyAppEventUtils.contains(eventParams, (String) "data")) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
            boolean containsEncoding = TinyAppEventUtils.contains(eventParams, (String) "encoding");
            String encoding = H5Utils.getString(eventParams, (String) "encoding", (String) "utf8");
            if (!containsEncoding || checkEncoding(encoding)) {
                String dataType = H5Utils.getString(eventParams, (String) "dataType");
                if (!containsEncoding || TextUtils.equals(dataType, DATA_TYPE_AF)) {
                    try {
                        binaryData = Base64.decode(H5Utils.getString(eventParams, (String) "data"), 0);
                    } catch (Exception e) {
                        context.sendError(event, Error.INVALID_PARAM);
                        return;
                    }
                } else if ("binary".equals(encoding)) {
                    try {
                        binaryData = H5Utils.getString(eventParams, (String) "data").getBytes();
                    } catch (Exception e2) {
                        context.sendError(event, Error.INVALID_PARAM);
                        return;
                    }
                } else if (ENCODING_HEX.equals(encoding)) {
                    binaryData = HexUtils.hexToByteArray(H5Utils.getString(eventParams, (String) "data"));
                } else if (ENCODING_BASE64.equals(encoding)) {
                    try {
                        binaryData = Base64.decode(H5Utils.getString(eventParams, (String) "data"), 0);
                    } catch (Exception e3) {
                        context.sendError(event, Error.INVALID_PARAM);
                        return;
                    }
                } else {
                    try {
                        binaryData = H5Utils.getString(eventParams, (String) "data").getBytes(convertEncoding(encoding));
                    } catch (Exception e4) {
                        context.sendError(event, Error.INVALID_PARAM);
                        return;
                    }
                }
                if (binaryData == null) {
                    context.sendError(event, Error.INVALID_PARAM);
                    return;
                }
                long fileNewSize = (long) binaryData.length;
                if (append) {
                    fileNewSize += localFile.length();
                }
                if (fileNewSize > 10485760) {
                    context.sendError(10028, (String) "单个文件超限");
                } else if ((getFileSize(new File(usrPathToLocalPath((String) PATH_ROOT, h5Page))) + fileNewSize) - localFile.length() > MAX_DIR_SIZE) {
                    context.sendError(10028, (String) "文件夹超限");
                } else {
                    FileOutputStream fos = null;
                    FileLock fileLock = null;
                    try {
                        FileOutputStream fos2 = new FileOutputStream(localFile, append);
                        try {
                            fileLock = fos2.getChannel().lock();
                            fos2.write(binaryData);
                            context.sendSuccess();
                            TinyAppFileUtils.releaseQuietly(fileLock);
                            H5IOUtils.closeQuietly(fos2);
                        } catch (Exception e5) {
                            e = e5;
                            fos = fos2;
                            try {
                                context.sendError(3, e.getMessage());
                                TinyAppFileUtils.releaseQuietly(fileLock);
                                H5IOUtils.closeQuietly(fos);
                            } catch (Throwable th) {
                                th = th;
                                TinyAppFileUtils.releaseQuietly(fileLock);
                                H5IOUtils.closeQuietly(fos);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            fos = fos2;
                            TinyAppFileUtils.releaseQuietly(fileLock);
                            H5IOUtils.closeQuietly(fos);
                            throw th;
                        }
                    } catch (Exception e6) {
                        e = e6;
                        context.sendError(3, e.getMessage());
                        TinyAppFileUtils.releaseQuietly(fileLock);
                        H5IOUtils.closeQuietly(fos);
                    }
                }
            } else {
                context.sendError(event, Error.INVALID_PARAM);
            }
        }
    }

    /* access modifiers changed from: private */
    public void saveFile(H5Event event, H5BridgeContext context) {
        FileOutputStream fos;
        H5Page h5Page = event.getH5page();
        JSONObject eventParams = event.getParam();
        String tempFilePath = H5Utils.getString(eventParams, (String) "tempFilePath");
        if (TextUtils.isEmpty(tempFilePath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        if (tempFilePath.startsWith("file://")) {
            tempFilePath = tempFilePath.replaceAll("file://", "");
        }
        File tempFile = new File(tempFilePath);
        if (!tempFile.exists()) {
            context.sendError(10022, (String) "指定的 tempFilePath 找不到文件");
            return;
        }
        String filePath = H5Utils.getString(eventParams, (String) "filePath");
        if (TextUtils.isEmpty(filePath)) {
            saveFileToCache(event, context);
        } else if (!hasRightForSaveFile(tempFilePath)) {
            context.sendError(10024, "无权操作 " + tempFilePath);
        } else if (TinyAppFileUtils.containsRelativeParentPath(filePath)) {
            context.sendError(event, Error.INVALID_PARAM);
        } else {
            String localPath = usrPathToLocalPath(filePath, h5Page);
            if (TextUtils.isEmpty(localPath)) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
            File localFile = new File(localPath);
            if (!localFile.exists()) {
                if (localFile.getParentFile().exists()) {
                    context.sendError(10022, "上级目录不存在 " + filePath);
                    return;
                }
            } else if (localFile.isDirectory()) {
                context.sendError(10023, "指定的 filePath 是一个已经存在的目录 " + filePath);
                return;
            } else if (!localFile.canWrite()) {
                context.sendError(10024, "指定的 filePath 路径没有写权限 " + filePath);
                return;
            }
            long fileNewSize = tempFile.length();
            if (fileNewSize > 10485760) {
                context.sendError(10028, (String) "单个文件超限");
            } else if ((getFileSize(new File(usrPathToLocalPath((String) PATH_ROOT, h5Page))) + fileNewSize) - localFile.length() > MAX_DIR_SIZE) {
                context.sendError(10028, (String) "文件夹超限");
            } else {
                FileInputStream fis = null;
                FileOutputStream fos2 = null;
                FileLock outLock = null;
                try {
                    FileInputStream fis2 = new FileInputStream(tempFilePath);
                    try {
                        fos = new FileOutputStream(localPath);
                    } catch (Exception e) {
                        e = e;
                        fis = fis2;
                        try {
                            context.sendError(3, e.getMessage());
                            TinyAppFileUtils.releaseQuietly(outLock);
                            H5IOUtils.closeQuietly(fis);
                            H5IOUtils.closeQuietly(fos2);
                        } catch (Throwable th) {
                            th = th;
                            TinyAppFileUtils.releaseQuietly(outLock);
                            H5IOUtils.closeQuietly(fis);
                            H5IOUtils.closeQuietly(fos2);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fis = fis2;
                        TinyAppFileUtils.releaseQuietly(outLock);
                        H5IOUtils.closeQuietly(fis);
                        H5IOUtils.closeQuietly(fos2);
                        throw th;
                    }
                    try {
                        outLock = fos.getChannel().lock();
                        byte[] buffer = new byte[1024];
                        while (true) {
                            int bufferCount = fis2.read(buffer);
                            if (bufferCount > 0) {
                                fos.write(buffer, 0, bufferCount);
                            } else {
                                context.sendSuccess();
                                TinyAppFileUtils.releaseQuietly(outLock);
                                H5IOUtils.closeQuietly(fis2);
                                H5IOUtils.closeQuietly(fos);
                                return;
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        fos2 = fos;
                        fis = fis2;
                        context.sendError(3, e.getMessage());
                        TinyAppFileUtils.releaseQuietly(outLock);
                        H5IOUtils.closeQuietly(fis);
                        H5IOUtils.closeQuietly(fos2);
                    } catch (Throwable th3) {
                        th = th3;
                        fos2 = fos;
                        fis = fis2;
                        TinyAppFileUtils.releaseQuietly(outLock);
                        H5IOUtils.closeQuietly(fis);
                        H5IOUtils.closeQuietly(fos2);
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                    context.sendError(3, e.getMessage());
                    TinyAppFileUtils.releaseQuietly(outLock);
                    H5IOUtils.closeQuietly(fis);
                    H5IOUtils.closeQuietly(fos2);
                }
            }
        }
    }

    public static boolean hasRightForSaveFile(String tempFilePath) {
        try {
            File file = new File(tempFilePath);
            if (H5Utils.getContext().getFilesDir() == null) {
                return true;
            }
            if (!file.getCanonicalPath().contains(H5Utils.getContext().getFilesDir().getParentFile().getCanonicalPath())) {
                return true;
            }
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider == null) {
                return true;
            }
            JSONArray jsonArray = H5Utils.parseArray(h5ConfigProvider.getConfig("ta_saveFile_whiteList"));
            if (jsonArray == null || jsonArray.isEmpty()) {
                return true;
            }
            Iterator<Object> it = jsonArray.iterator();
            while (it.hasNext()) {
                Object name = it.next();
                if ((name instanceof String) && file.getCanonicalPath().contains((String) name)) {
                    H5Log.d(TAG, "file.getCanonicalPath() = " + file.getCanonicalPath() + " contains  whiteList: " + name);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return true;
        }
    }

    private void saveFileToCache(H5Event event, final H5BridgeContext context) {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        event.setAction(ACTION_SAVE_FILE);
        h5Service.sendEvent(event, new H5BaseBridgeContext() {
            public boolean sendBack(JSONObject jsonObject, boolean b) {
                String apFilePath = H5Utils.getString(jsonObject, (String) Constant.AL_MEDIA_FILE);
                if (TextUtils.isEmpty(apFilePath)) {
                    context.sendBridgeResult(jsonObject);
                } else {
                    JSONObject result = new JSONObject();
                    result.put((String) "success", (Object) Boolean.valueOf(true));
                    result.put((String) "savedFilePath", (Object) apFilePath);
                    context.sendBridgeResult(result);
                }
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void getSavedFileList(H5Event event, final H5BridgeContext context) {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        event.setAction(ACTION_GET_SAVED_FILE_LIST);
        h5Service.sendEvent(event, new H5BaseBridgeContext() {
            public boolean sendBack(JSONObject jsonObject, boolean b) {
                if (H5Utils.getBoolean(jsonObject, (String) "success", false)) {
                    JSONArray fileList = H5Utils.getJSONArray(jsonObject, "fileList", new JSONArray());
                    if (!(fileList == null || fileList.size() == 0)) {
                        int size = fileList.size();
                        for (int i = 0; i < size; i++) {
                            JSONObject file = fileList.getJSONObject(i);
                            if (file != null) {
                                file.put((String) "filePath", file.get(Constant.AL_MEDIA_FILE));
                            }
                        }
                    }
                }
                context.sendBridgeResult(jsonObject);
                return false;
            }
        });
    }

    private void removeSavedFile(H5Event event, final H5BridgeContext context) {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        event.setAction(ACTION_REMOVE_SAVED_FILE);
        h5Service.sendEvent(event, new H5BaseBridgeContext() {
            public boolean sendBack(JSONObject jsonObject, boolean b) {
                context.sendBridgeResult(jsonObject);
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void copyFile(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        JSONObject eventParams = event.getParam();
        String path = H5Utils.getString(eventParams, (String) "srcPath");
        if (TinyAppFileUtils.containsRelativeParentPath(path)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String localPath = usrPathToLocalPath(path, h5Page);
        if (TextUtils.isEmpty(localPath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            context.sendError(10022, "指定的 srcPath 文件不存在 " + path);
        } else if (localFile.isDirectory()) {
            context.sendError(10023, "指定的 srcPath 是一个已经存在的目录 " + path);
        } else if (!localFile.canRead()) {
            context.sendError(10024, "指定的 srcPath 路径没有读权限 " + path);
        } else {
            String destPath = H5Utils.getString(eventParams, (String) "destPath");
            if (TinyAppFileUtils.containsRelativeParentPath(destPath)) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
            String localDestPath = usrPathToLocalPath(destPath, h5Page);
            if (TextUtils.isEmpty(localDestPath)) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
            File localDestFile = new File(localDestPath);
            if (localDestFile.isDirectory()) {
                localDestFile = new File(localDestPath + "/" + localFile.getName());
            }
            if (!localDestFile.getParentFile().canWrite()) {
                context.sendError(10024, "指定的 destPath 路径没有写权限 " + destPath);
                return;
            }
            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileLock outLock = null;
            try {
                FileInputStream fis2 = new FileInputStream(localFile);
                try {
                    FileOutputStream fos2 = new FileOutputStream(localDestFile);
                    try {
                        outLock = fos2.getChannel().lock();
                        byte[] buffer = new byte[1024];
                        while (true) {
                            int bufferCount = fis2.read(buffer);
                            if (bufferCount > 0) {
                                fos2.write(buffer, 0, bufferCount);
                            } else {
                                context.sendSuccess();
                                TinyAppFileUtils.releaseQuietly(outLock);
                                H5IOUtils.closeQuietly(fis2);
                                H5IOUtils.closeQuietly(fos2);
                                return;
                            }
                        }
                    } catch (Exception e) {
                        e = e;
                        fos = fos2;
                        fis = fis2;
                        try {
                            context.sendError(3, e.getMessage());
                            TinyAppFileUtils.releaseQuietly(outLock);
                            H5IOUtils.closeQuietly(fis);
                            H5IOUtils.closeQuietly(fos);
                        } catch (Throwable th) {
                            th = th;
                            TinyAppFileUtils.releaseQuietly(outLock);
                            H5IOUtils.closeQuietly(fis);
                            H5IOUtils.closeQuietly(fos);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fos = fos2;
                        fis = fis2;
                        TinyAppFileUtils.releaseQuietly(outLock);
                        H5IOUtils.closeQuietly(fis);
                        H5IOUtils.closeQuietly(fos);
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                    fis = fis2;
                    context.sendError(3, e.getMessage());
                    TinyAppFileUtils.releaseQuietly(outLock);
                    H5IOUtils.closeQuietly(fis);
                    H5IOUtils.closeQuietly(fos);
                } catch (Throwable th3) {
                    th = th3;
                    fis = fis2;
                    TinyAppFileUtils.releaseQuietly(outLock);
                    H5IOUtils.closeQuietly(fis);
                    H5IOUtils.closeQuietly(fos);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                context.sendError(3, e.getMessage());
                TinyAppFileUtils.releaseQuietly(outLock);
                H5IOUtils.closeQuietly(fis);
                H5IOUtils.closeQuietly(fos);
            }
        }
    }

    /* access modifiers changed from: private */
    public void getFileInfo(H5Event event, final H5BridgeContext context) {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        event.setAction(ACTION_GET_FILE_INFO);
        h5Service.sendEvent(event, new H5BaseBridgeContext() {
            public boolean sendBack(JSONObject jsonObject, boolean b) {
                if (jsonObject != null && TinyAppEventUtils.contains(jsonObject, (String) "error") && H5Utils.getInt(jsonObject, (String) "error") == 12) {
                    jsonObject.put((String) "error", (Object) Integer.valueOf(10022));
                }
                context.sendBridgeResult(jsonObject);
                return false;
            }
        });
    }

    private long getFileSize(File file) {
        if (file == null) {
            return 0;
        }
        if (file.isFile()) {
            return file.length();
        }
        long size = 0;
        if (!file.isDirectory()) {
            return 0;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return 0;
        }
        for (File subFile : files) {
            size += getFileSize(subFile);
        }
        return size;
    }

    private void mkdir(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        JSONObject eventParams = event.getParam();
        String path = H5Utils.getString(eventParams, (String) "dirPath");
        String localPath = usrPathToLocalPath(path, h5Page);
        if (TextUtils.isEmpty(localPath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        File localFile = new File(localPath);
        if (localFile.exists()) {
            context.sendError(10025, "指定的 dirPath 是一个已经存在的文件/目录 " + path);
            return;
        }
        boolean recursive = H5Utils.getBoolean(eventParams, (String) "recursive", false);
        if (!recursive) {
            if (!localFile.getParentFile().exists()) {
                context.sendError(10022, "上级目录不存在 " + path);
                return;
            } else if (!localFile.getParentFile().canWrite()) {
                context.sendError(10024, "指定的 dirPath 路径没有写权限 " + path);
                return;
            }
        }
        if (recursive) {
            if (localFile.mkdirs()) {
                context.sendSuccess();
            } else {
                context.sendError(event, Error.UNKNOWN_ERROR);
            }
        } else if (localFile.mkdir()) {
            context.sendSuccess();
        } else {
            context.sendError(event, Error.UNKNOWN_ERROR);
        }
    }

    /* access modifiers changed from: private */
    public void readFile(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        JSONObject eventParams = event.getParam();
        String path = H5Utils.getString(eventParams, (String) "filePath");
        if (TinyAppFileUtils.containsRelativeParentPath(path)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String localPath = usrPathToLocalPath(path, h5Page);
        if (TextUtils.isEmpty(localPath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            context.sendError(10022, "指定的 filePath 文件不存在  " + path);
        } else if (localFile.isDirectory()) {
            context.sendError(10023, "指定的 filePath 是一个已经存在的目录 " + path);
        } else if (!localFile.canRead()) {
            context.sendError(10024, "指定的 filePath 路径没有读权限 " + path);
        } else {
            FileInputStream fis = null;
            try {
                FileInputStream fis2 = new FileInputStream(localFile);
                try {
                    byte[] binaryData = H5IOUtils.inputToByte(fis2);
                    H5IOUtils.closeQuietly(fis2);
                    if (binaryData == null) {
                        context.sendError(event, Error.UNKNOWN_ERROR);
                        return;
                    }
                    boolean containsEncoding = TinyAppEventUtils.contains(eventParams, (String) "encoding");
                    String encoding = H5Utils.getString(eventParams, (String) "encoding");
                    if (!containsEncoding || checkEncoding(encoding)) {
                        String dataType = H5Utils.getString(eventParams, (String) "dataType");
                        if (TextUtils.isEmpty(encoding) || TextUtils.equals(dataType, DATA_TYPE_AF)) {
                            try {
                                String data = Base64.encodeToString(binaryData, 2);
                                JSONObject result = new JSONObject();
                                result.put((String) "success", (Object) Boolean.valueOf(true));
                                result.put((String) "data", (Object) data);
                                result.put((String) "dataType", (Object) DATA_TYPE_AF);
                                context.sendBridgeResult(result);
                            } catch (Exception e) {
                                context.sendError(3, e.getMessage());
                            }
                        } else if ("binary".equals(encoding)) {
                            try {
                                String data2 = new String(binaryData);
                                JSONObject result2 = new JSONObject();
                                result2.put((String) "success", (Object) Boolean.valueOf(true));
                                result2.put((String) "data", (Object) data2);
                                context.sendBridgeResult(result2);
                            } catch (Exception e2) {
                                context.sendError(3, e2.getMessage());
                            }
                        } else if (ENCODING_HEX.equals(encoding)) {
                            try {
                                String data3 = HexUtils.byteArrayToHex(binaryData);
                                JSONObject result3 = new JSONObject();
                                result3.put((String) "success", (Object) Boolean.valueOf(true));
                                result3.put((String) "data", (Object) data3);
                                context.sendBridgeResult(result3);
                            } catch (Exception e3) {
                                context.sendError(3, e3.getMessage());
                            }
                        } else if (ENCODING_BASE64.equals(encoding)) {
                            try {
                                String data4 = Base64.encodeToString(binaryData, 2);
                                JSONObject result4 = new JSONObject();
                                result4.put((String) "success", (Object) Boolean.valueOf(true));
                                result4.put((String) "data", (Object) data4);
                                context.sendBridgeResult(result4);
                            } catch (Exception e4) {
                                context.sendError(3, e4.getMessage());
                            }
                        } else {
                            try {
                                String data5 = new String(binaryData, convertEncoding(encoding));
                                JSONObject result5 = new JSONObject();
                                result5.put((String) "success", (Object) Boolean.valueOf(true));
                                result5.put((String) "data", (Object) data5);
                                context.sendBridgeResult(result5);
                            } catch (Exception e5) {
                                context.sendError(3, e5.getMessage());
                            }
                        }
                    } else {
                        context.sendError(event, Error.UNKNOWN_ERROR);
                    }
                } catch (Exception e6) {
                    e = e6;
                    fis = fis2;
                    try {
                        context.sendError(3, e.getMessage());
                        H5IOUtils.closeQuietly(fis);
                    } catch (Throwable th) {
                        th = th;
                        H5IOUtils.closeQuietly(fis);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fis = fis2;
                    H5IOUtils.closeQuietly(fis);
                    throw th;
                }
            } catch (Exception e7) {
                e = e7;
                context.sendError(3, e.getMessage());
                H5IOUtils.closeQuietly(fis);
            }
        }
    }

    private void readDir(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        String path = H5Utils.getString(event.getParam(), (String) "dirPath");
        if (TinyAppFileUtils.containsRelativeParentPath(path)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String localPath = usrPathToLocalPath(path, h5Page);
        if (TextUtils.isEmpty(localPath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            context.sendError(10022, "指定的 dirPath 目录不存在 " + path);
        } else if (!localFile.isDirectory()) {
            context.sendError(10026, "dirPath 不是目录 " + path);
        } else if (!localFile.canRead()) {
            context.sendError(10024, "指定的 dirPath 路径没有读权限 " + path);
        } else {
            String prefix = path.endsWith("/") ? path : path + "/";
            JSONArray files = new JSONArray();
            for (File subFile : localFile.listFiles()) {
                files.add(prefix + subFile.getName());
            }
            JSONObject result = new JSONObject();
            result.put((String) "success", (Object) Boolean.valueOf(true));
            result.put((String) AutoJsonUtils.JSON_FILES, (Object) files);
            context.sendBridgeResult(result);
        }
    }

    private void rename(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        JSONObject eventParams = event.getParam();
        String path = H5Utils.getString(eventParams, (String) "oldPath");
        if (TinyAppFileUtils.containsRelativeParentPath(path)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String localPath = usrPathToLocalPath(path, h5Page);
        if (TextUtils.isEmpty(localPath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            context.sendError(10022, "源文件不存在 " + path);
        } else if (!localFile.canWrite()) {
            context.sendError(10024, "指定源文件或目标文件没有写权限 " + path);
        } else {
            String newPath = H5Utils.getString(eventParams, (String) "newPath");
            if (TinyAppFileUtils.containsRelativeParentPath(newPath)) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
            String localNewPath = usrPathToLocalPath(newPath, h5Page);
            if (TextUtils.isEmpty(localNewPath)) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
            File localNewFile = new File(localNewPath);
            if (localNewFile.exists()) {
                context.sendError(10025, "目标文件路径的上层目录不存在 " + newPath);
            } else if (!localNewFile.getParentFile().canWrite()) {
                context.sendError(10024, "目标文件路径没有写权限 " + newPath);
            } else if (localFile.renameTo(localNewFile)) {
                context.sendSuccess();
            } else {
                context.sendError(event, Error.UNKNOWN_ERROR);
            }
        }
    }

    /* access modifiers changed from: private */
    public void rmdir(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        JSONObject eventParams = event.getParam();
        String path = H5Utils.getString(eventParams, (String) "dirPath");
        if (TinyAppFileUtils.containsRelativeParentPath(path)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String localPath = usrPathToLocalPath(path, h5Page);
        if (TextUtils.isEmpty(localPath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            context.sendError(10022, "目录不存在 " + path);
        } else if (!localFile.isDirectory()) {
            context.sendError(10026, "指定的 dirPath 不是目录 " + path);
        } else if (!localFile.canWrite()) {
            context.sendError(10024, "指定的 dirPath 路径没有写权限 " + path);
        } else {
            boolean recursive = H5Utils.getBoolean(eventParams, (String) "recursive", false);
            if (!recursive) {
                File[] subFiles = localFile.listFiles();
                if (!(subFiles == null || subFiles.length == 0)) {
                    context.sendError(10027, (String) "目录不为空");
                    return;
                }
            }
            if (removeFile(localFile, recursive, usrPathToLocalPath((String) PATH_ROOT, h5Page))) {
                context.sendSuccess();
            } else {
                context.sendError(event, Error.UNKNOWN_ERROR);
            }
        }
    }

    private boolean removeFile(File file, boolean recursive, String checkableRoot) {
        if (!file.isDirectory()) {
            return TinyAppFileUtils.deleteFile(file, checkableRoot);
        }
        if (recursive) {
            File[] subFiles = file.listFiles();
            if (!(subFiles == null || subFiles.length == 0)) {
                for (File subFile : subFiles) {
                    if (!removeFile(subFile, recursive, checkableRoot)) {
                        return false;
                    }
                }
            }
        }
        return TinyAppFileUtils.deleteFile(file, checkableRoot);
    }

    /* access modifiers changed from: private */
    public void stat(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        JSONObject eventParams = event.getParam();
        String path = H5Utils.getString(eventParams, (String) "path");
        if (TinyAppFileUtils.containsRelativeParentPath(path)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String localPath = usrPathToLocalPath(path, h5Page);
        if (TextUtils.isEmpty(localPath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            context.sendError(10022, "文件不存在 " + path);
        } else if (!localFile.canRead()) {
            context.sendError(10024, "指定的 path 路径没有读权限 " + path);
        } else {
            boolean recursive = H5Utils.getBoolean(eventParams, (String) "recursive", false);
            JSONArray stats = new JSONArray();
            stat(stats, localFile, localFile, recursive);
            JSONObject result = new JSONObject();
            result.put((String) "success", (Object) Boolean.valueOf(true));
            if (recursive && localFile.isDirectory()) {
                result.put((String) "stats", (Object) stats);
            } else if (stats.size() > 0) {
                JSONObject stat = stats.getJSONObject(0);
                stat.remove("path");
                result.put((String) "stats", (Object) stat);
            }
            context.sendBridgeResult(result);
        }
    }

    private void stat(JSONArray stats, File root, File current, boolean recursive) {
        String path = current.getPath().substring(root.getPath().length());
        boolean isDirectory = current.isDirectory();
        boolean isFile = current.isFile();
        SafeStructStat structStat = SafeLibCore.stat(current.getPath());
        JSONObject stat = new JSONObject();
        if (!TextUtils.isEmpty(path)) {
            stat.put((String) "path", (Object) path);
        } else if (current.isDirectory()) {
            stat.put((String) "path", (Object) "/");
        }
        stat.put((String) Constants.KEY_MODE, (Object) Integer.valueOf(structStat != null ? structStat.st_mode : 0));
        stat.put((String) "size", (Object) Long.valueOf(structStat != null ? structStat.st_size : 0));
        stat.put((String) "lastAccessedTime", (Object) Long.valueOf(structStat != null ? structStat.st_atime : 0));
        stat.put((String) "lastModifiedTime", (Object) Long.valueOf(structStat != null ? structStat.st_mtime : 0));
        stat.put((String) "is_directory", (Object) Boolean.valueOf(isDirectory));
        stat.put((String) "is_file", (Object) Boolean.valueOf(isFile));
        stats.add(stat);
        if (recursive && isDirectory) {
            File[] files = current.listFiles();
            if (files != null && files.length != 0) {
                for (File file : files) {
                    stat(stats, root, file, recursive);
                }
            }
        }
    }

    private void unlink(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        String path = H5Utils.getString(event.getParam(), (String) "filePath");
        if (TinyAppFileUtils.containsRelativeParentPath(path)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String localPath = usrPathToLocalPath(path, h5Page);
        if (TextUtils.isEmpty(localPath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            context.sendError(10022, "文件不存在 " + path);
        } else if (localFile.isDirectory()) {
            context.sendError(10023, "传入的 filePath 是一个目录 " + path);
        } else if (!localFile.canWrite()) {
            context.sendError(10024, "指定的 path 路径没有写权限 " + path);
        } else if (TinyAppFileUtils.deleteFile(localFile, usrPathToLocalPath((String) PATH_ROOT, h5Page))) {
            context.sendSuccess();
        } else {
            context.sendError(event, Error.UNKNOWN_ERROR);
        }
    }

    /* access modifiers changed from: private */
    public void unzip(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        JSONObject eventParams = event.getParam();
        String path = H5Utils.getString(eventParams, (String) "zipFilePath");
        if (TinyAppFileUtils.containsRelativeParentPath(path)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String localPath = usrPathToLocalPath(path, h5Page);
        if (TextUtils.isEmpty(localPath)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            context.sendError(10022, "源文件不存在 " + path);
        } else if (localFile.isDirectory()) {
            context.sendError(10023, "源文件不存在是一个目录 " + path);
        } else if (!localFile.canRead()) {
            context.sendError(10024, "源文件没有读权限 " + path);
        } else {
            String targetPath = H5Utils.getString(eventParams, (String) "targetPath");
            if (TinyAppFileUtils.containsRelativeParentPath(targetPath)) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
            String localTargetPath = usrPathToLocalPath(targetPath, h5Page);
            if (TextUtils.isEmpty(localTargetPath)) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
            File localTargetFile = new File(localTargetPath);
            if (!localTargetFile.getParentFile().exists()) {
                context.sendError(10022, "目标文件路径的上层目录不存在 " + targetPath);
                return;
            }
            if (localTargetFile.exists()) {
                if (!localTargetFile.canWrite()) {
                    context.sendError(10024, "指定目标文件路径没有写权限 " + targetPath);
                    return;
                }
            } else if (!localTargetFile.getParentFile().canWrite()) {
                context.sendError(10024, "指定目标文件路径没有写权限 " + targetPath);
                return;
            }
            if (localFile.length() > 10485760) {
                context.sendError(10028, (String) "单个文件超限");
                return;
            }
            long rootDirSize = getFileSize(new File(usrPathToLocalPath((String) PATH_ROOT, h5Page)));
            if (localFile.length() + rootDirSize > MAX_DIR_SIZE) {
                context.sendError(10028, (String) "文件夹超限");
                return;
            }
            try {
                ZipFileInfo zipFileInfo = ZipFileInfo.parse(localFile.getPath());
                if (!zipFileInfo.isZipFile()) {
                    context.sendError(event, Error.INVALID_PARAM);
                } else if (zipFileInfo.hasRelativeParentPath()) {
                    context.sendError(3, (String) "不允许..路径");
                } else if (zipFileInfo.getCompressedSize() > 10485760 || zipFileInfo.getSize() > 10485760) {
                    context.sendError(10028, (String) "单个文件超限");
                } else if (zipFileInfo.getSize() + rootDirSize > MAX_DIR_SIZE) {
                    context.sendError(10028, (String) "文件夹超限");
                } else if (H5ZipUtil.unZip(localPath, localTargetPath)) {
                    context.sendSuccess();
                } else {
                    context.sendError(event, Error.UNKNOWN_ERROR);
                }
            } catch (Exception e) {
                context.sendError(3, e.getMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    public void writeFile(H5Event event, H5BridgeContext context) {
        writeFile(event, context, false);
    }

    private static String convertEncoding(String encoding) {
        if (TextUtils.isEmpty(encoding)) {
            return encoding;
        }
        if (TextUtils.equals(encoding, "ucs2") || TextUtils.equals(encoding, "ucs-2") || TextUtils.equals(encoding, "utf16le") || TextUtils.equals(encoding, "utf-16le")) {
            return "UTF-16LE";
        }
        return encoding;
    }

    private static boolean checkEncoding(String encoding) {
        if (TextUtils.isEmpty(encoding)) {
            return false;
        }
        return Arrays.asList(new String[]{"ascii", ENCODING_BASE64, "binary", ENCODING_HEX, "ucs2", "ucs-2", "utf16le", "utf-16le", "utf-8", "utf8", "latin1"}).contains(encoding);
    }

    public static String usrPathToLocalPath(String usrPath, H5Event event) {
        if (event == null) {
            return null;
        }
        return usrPathToLocalPath(usrPath, event.getH5page());
    }

    public static String usrPathToLocalPath(String usrPath, H5Page h5Page) {
        if (h5Page == null) {
            return null;
        }
        return usrPathToLocalPath(usrPath, h5Page.getParams());
    }

    public static String usrPathToLocalPath(String usrPath, Bundle startParams) {
        if (TextUtils.isEmpty(usrPath)) {
            return null;
        }
        int usrPathLength = usrPath.length();
        if (usrPathLength < PATH_ROOT_LENGTH) {
            return null;
        }
        if (usrPathLength == PATH_ROOT_LENGTH) {
            if (!usrPath.equals(PATH_ROOT)) {
                return null;
            }
        } else if (!usrPath.startsWith(PATH_PREFIX)) {
            return null;
        }
        String appId = TinyAppParamUtils.getAppId(startParams);
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        String userId = TinyappUtils.getUserId();
        if (TextUtils.isEmpty(userId)) {
            return null;
        }
        String rootPath = DiskUtil.getSubDir(H5Utils.getContext(), "usr/" + H5SecurityUtil.getMD5(appId + userId));
        if (TextUtils.isEmpty(rootPath)) {
            return null;
        }
        File rootDir = new File(rootPath);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
        return usrPathLength != PATH_ROOT_LENGTH ? rootPath + usrPath.substring(PATH_ROOT_LENGTH) : rootPath;
    }

    public static String localPathToUsrPath(String localPath, H5Event event) {
        if (event == null) {
            return null;
        }
        return localPathToUsrPath(localPath, event.getH5page());
    }

    public static String localPathToUsrPath(String localPath, H5Page h5Page) {
        if (h5Page == null) {
            return null;
        }
        return localPathToUsrPath(localPath, h5Page.getParams());
    }

    public static String localPathToUsrPath(String localPath, Bundle startParams) {
        if (startParams == null || TextUtils.isEmpty(localPath)) {
            return null;
        }
        String appId = TinyAppParamUtils.getAppId(startParams);
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        String userId = TinyappUtils.getUserId();
        if (TextUtils.isEmpty(userId)) {
            return null;
        }
        String rootPath = DiskUtil.getSubDir(H5Utils.getContext(), "usr/" + H5SecurityUtil.getMD5(appId + userId));
        if (TextUtils.isEmpty(rootPath)) {
            return null;
        }
        if (localPath.equals(rootPath)) {
            return PATH_ROOT;
        }
        if (localPath.startsWith(rootPath + "/")) {
            return new StringBuilder(PATH_ROOT).append(localPath.substring(rootPath.length())).toString();
        }
        return null;
    }
}
