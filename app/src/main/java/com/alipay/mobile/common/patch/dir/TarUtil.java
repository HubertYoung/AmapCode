package com.alipay.mobile.common.patch.dir;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.patch.dir.tar.TarEntry;
import com.alipay.mobile.common.patch.dir.tar.TarInputStream;
import com.alipay.mobile.common.patch.dir.tar.TarOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Stack;

public class TarUtil {
    public static final String TAG = "TarUtil";
    public static String failMessage = null;

    public TarUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static boolean untar(String tarPath, String untarFolder) {
        if (!FileUtil.exists(tarPath)) {
            failMessage = "tar path not exists!";
            Log.e(TAG, failMessage);
            return false;
        } else if (!FileUtil.mkdirs(untarFolder, true)) {
            failMessage = "failed to create untar folder.";
            Log.e(TAG, failMessage);
            return false;
        } else {
            try {
                TarInputStream tis = new TarInputStream(new BufferedInputStream(new FileInputStream(tarPath)));
                while (true) {
                    TarEntry te = tis.getNextEntry();
                    if (te != null) {
                        String entryName = te.getName();
                        Log.d(TAG, "untar entry " + entryName);
                        String entryPath = untarFolder + "/" + entryName;
                        if (te.isDirectory()) {
                            FileUtil.mkdirs(entryPath);
                        } else if (!FileUtil.create(entryPath, true)) {
                            Log.e(TAG, "failed to create file " + entryPath);
                        } else {
                            byte[] buffer = new byte[2048];
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(entryPath));
                            while (true) {
                                int count = tis.read(buffer);
                                if (count == -1) {
                                    break;
                                }
                                bos.write(buffer, 0, count);
                            }
                            bos.flush();
                            bos.close();
                        }
                    } else {
                        tis.close();
                        return true;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
    }

    public static boolean tar(String tarPath, String tarFolder) {
        if (!FileUtil.create(tarPath)) {
            Log.d(TAG, "create tar file failed");
            return false;
        } else if (!FileUtil.isFolder(tarFolder)) {
            Log.d(TAG, "tar folder not exists!");
            return false;
        } else if (FileUtil.childOf(tarPath, tarFolder)) {
            Log.d(TAG, "can't create tar file under folder!");
            return false;
        } else {
            try {
                TarOutputStream tarOutputStream = new TarOutputStream(new File(tarPath));
                Stack fileStack = new Stack();
                fileStack.push(tarFolder);
                StringBuilder sb = new StringBuilder();
                File file = new File(tarFolder);
                String rootFolder = sb.append(file.getParent()).append("/").toString();
                while (!fileStack.isEmpty()) {
                    String current = (String) fileStack.pop();
                    File currentFile = new File(current);
                    if (FileUtil.isFolder(current)) {
                        File[] children = currentFile.listFiles();
                        for (File child : children) {
                            fileStack.push(child.getAbsolutePath());
                        }
                    } else {
                        String entryName = current.replace(rootFolder, "");
                        Log.d(TAG, "tar entryName " + entryName);
                        TarEntry tarEntry = new TarEntry(currentFile, entryName);
                        tarOutputStream.putNextEntry(tarEntry);
                        BufferedInputStream origin = new BufferedInputStream(new FileInputStream(currentFile));
                        byte[] data = new byte[2048];
                        while (true) {
                            int count = origin.read(data);
                            if (count == -1) {
                                break;
                            }
                            tarOutputStream.write(data, 0, count);
                        }
                        tarOutputStream.flush();
                        origin.close();
                    }
                }
                tarOutputStream.close();
            } catch (Exception e) {
                Log.w(TAG, "error", e);
            }
            return true;
        }
    }
}
