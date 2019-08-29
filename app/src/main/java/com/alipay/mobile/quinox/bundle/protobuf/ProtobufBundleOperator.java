package com.alipay.mobile.quinox.bundle.protobuf;

import com.alipay.mobile.quinox.bundle.IBundle;
import com.alipay.mobile.quinox.bundle.IBundleOperator;
import com.alipay.mobile.quinox.bundle.IBundleOperator.BundleType;
import com.alipay.mobile.quinox.log.Log;
import com.alipay.mobile.quinox.utils.StreamUtil;
import com.squareup.wire.Wire;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProtobufBundleOperator implements IBundleOperator {
    private final String mFileName;
    private final File mRootDir;
    private boolean mVersionMismatch;
    private final String mVersionName;

    public class VersionMismatchException extends IOException {
        public VersionMismatchException(String str) {
            super(str);
        }
    }

    public ProtobufBundleOperator(File file) {
        this(file, null);
    }

    public ProtobufBundleOperator(File file, String str) {
        this.mVersionMismatch = false;
        this.mRootDir = file;
        this.mVersionName = str;
        this.mFileName = BundleType.ProtoBuf.name;
    }

    public BundleType getBundleType() {
        return BundleType.ProtoBuf;
    }

    public void readBundlesFromInputStream(InputStream inputStream, List<String> list, Map<String, IBundle> map) throws IOException {
        if (inputStream != null) {
            if (map != null && !map.isEmpty()) {
                map.clear();
            }
            if (list != null && !list.isEmpty()) {
                list.clear();
            }
            ProtobufBundleCfg protobufBundleCfg = (ProtobufBundleCfg) new Wire((Class<?>[]) new Class[0]).parseFrom(inputStream, ProtobufBundleCfg.class);
            if (protobufBundleCfg.mVersionName == null || this.mVersionName == null || protobufBundleCfg.mVersionName.equals(this.mVersionName)) {
                if (list != null) {
                    List<String> list2 = protobufBundleCfg.mStaticLinkBundleNames;
                    if (list2 != null && !list2.isEmpty()) {
                        list.addAll(list2);
                    }
                }
                if (map != null) {
                    List<ProtobufBundle> list3 = protobufBundleCfg.mAllBundles;
                    if (list3 != null && !list3.isEmpty()) {
                        for (ProtobufBundle next : list3) {
                            map.put(next.mName, new ProtobufBundleProxy(next));
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            this.mVersionMismatch = true;
            StringBuilder sb = new StringBuilder("version name not mach, expect");
            sb.append(this.mVersionName);
            sb.append(",but got ");
            sb.append(protobufBundleCfg.mVersionName);
            throw new VersionMismatchException(sb.toString());
        }
    }

    public void readBundlesFromCfg(List<String> list, Map<String, IBundle> map) throws IOException {
        File file = new File(this.mRootDir, this.mFileName);
        if (!file.exists()) {
            StringBuilder sb = new StringBuilder("cfg file not exists:");
            sb.append(file.getAbsolutePath());
            throw new FileNotFoundException(sb.toString());
        }
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                readBundlesFromInputStream(fileInputStream2, list, map);
                StreamUtil.closeSafely(fileInputStream2);
            } catch (IOException e) {
                FileInputStream fileInputStream3 = fileInputStream2;
                e = e;
                fileInputStream = fileInputStream3;
                Log.w((String) IBundleOperator.TAG, (Throwable) e);
                file.delete();
                if (map != null && !map.isEmpty()) {
                    map.clear();
                }
                if (list != null && !list.isEmpty()) {
                    list.clear();
                }
                throw e;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                StreamUtil.closeSafely(fileInputStream);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            Log.w((String) IBundleOperator.TAG, (Throwable) e);
            file.delete();
            map.clear();
            list.clear();
            throw e;
        } catch (Throwable th2) {
            th = th2;
            Log.w((String) IBundleOperator.TAG, th);
            file.delete();
            map.clear();
            list.clear();
            StreamUtil.closeSafely(fileInputStream);
        }
    }

    public void writeBundlesToCfg(List<String> list, List<IBundle> list2, boolean z) throws IOException {
        if (this.mVersionMismatch) {
            if (z) {
                Log.w((String) IBundleOperator.TAG, (String) "config say we can't prevent write cfg when version mismatch.");
            } else {
                throw new IOException("we can't write cfg when version mismatch!");
            }
        }
        File file = new File(this.mRootDir, this.mFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = null;
        try {
            ProtobufBundleCfg protobufBundleCfg = new ProtobufBundleCfg();
            protobufBundleCfg.mVersionName = this.mVersionName;
            if (list != null && !list.isEmpty()) {
                ArrayList arrayList = new ArrayList(list.size());
                for (String add : list) {
                    arrayList.add(add);
                }
                protobufBundleCfg.mStaticLinkBundleNames = arrayList;
            }
            if (list2 != null && !list2.isEmpty()) {
                ArrayList arrayList2 = new ArrayList(list2.size());
                for (IBundle protobufBundleProxy : list2) {
                    arrayList2.add(new ProtobufBundleProxy(protobufBundleProxy).get());
                }
                protobufBundleCfg.mAllBundles = arrayList2;
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(protobufBundleCfg.toByteArray());
                fileOutputStream2.flush();
                StreamUtil.closeSafely(fileOutputStream2);
            } catch (IOException e) {
                fileOutputStream = fileOutputStream2;
                e = e;
                Log.w((String) IBundleOperator.TAG, (Throwable) e);
                file.delete();
                throw e;
            } catch (Throwable th) {
                fileOutputStream = fileOutputStream2;
                th = th;
                StreamUtil.closeSafely(fileOutputStream);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            Log.w((String) IBundleOperator.TAG, (Throwable) e);
            file.delete();
            throw e;
        } catch (Throwable th2) {
            th = th2;
            Log.w((String) IBundleOperator.TAG, th);
            file.delete();
            StreamUtil.closeSafely(fileOutputStream);
        }
    }

    public void writeBundlesToCfg(List<String> list, List<IBundle> list2) throws IOException {
        writeBundlesToCfg(list, list2, true);
    }

    public void writeBundlesToCfg2(List<String> list, List<IBundle> list2) throws IOException {
        writeBundlesToCfg(list, list2);
    }
}
