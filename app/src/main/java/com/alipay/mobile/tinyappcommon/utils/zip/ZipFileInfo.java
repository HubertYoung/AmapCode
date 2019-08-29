package com.alipay.mobile.tinyappcommon.utils.zip;

import com.alipay.mobile.nebula.util.H5Log;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileInfo {
    private static final String TAG = "ZipFileInfo";
    private long compressedSize;
    private boolean hasRelativeParentPath;
    private boolean isZipFile;
    private String path;
    private long size;

    private ZipFileInfo() {
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path2) {
        this.path = path2;
    }

    public boolean isZipFile() {
        return this.isZipFile;
    }

    public void setZipFile(boolean zipFile) {
        this.isZipFile = zipFile;
    }

    public long getCompressedSize() {
        return this.compressedSize;
    }

    public void setCompressedSize(long compressedSize2) {
        this.compressedSize = compressedSize2;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size2) {
        this.size = size2;
    }

    public boolean hasRelativeParentPath() {
        return this.hasRelativeParentPath;
    }

    public void setHasRelativeParentPath(boolean hasRelativeParentPath2) {
        this.hasRelativeParentPath = hasRelativeParentPath2;
    }

    public static ZipFileInfo parse(String path2) {
        ZipFile zipFile = null;
        try {
            ZipFile zipFile2 = new ZipFile(path2);
            try {
                ZipFileInfo zipFileInfo = new ZipFileInfo();
                zipFileInfo.path = path2;
                zipFileInfo.isZipFile = true;
                Enumeration entries = zipFile2.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                    String name = zipEntry.getName();
                    if (!zipFileInfo.hasRelativeParentPath && (name.startsWith("../") || name.endsWith("/..") || name.contains("/../"))) {
                        zipFileInfo.hasRelativeParentPath = true;
                    }
                    zipFileInfo.size += zipEntry.getSize();
                    zipFileInfo.compressedSize += zipEntry.getCompressedSize();
                }
                closeQuietly(zipFile2);
                return zipFileInfo;
            } catch (Exception e) {
                e = e;
                zipFile = zipFile2;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                zipFile = zipFile2;
                closeQuietly(zipFile);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            throw e;
        }
    }

    public static void closeQuietly(ZipFile zipFile) {
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
    }
}
