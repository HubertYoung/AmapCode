package com.alipay.mobile.quinox.bundle.bytedata;

import com.alipay.mobile.quinox.bundle.IBundle;
import com.alipay.mobile.quinox.bundle.IBundleOperator;
import com.alipay.mobile.quinox.bundle.IBundleOperator.BundleType;
import com.alipay.mobile.quinox.log.Log;
import com.alipay.mobile.quinox.utils.StreamUtil;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ByteDataBundleOperator implements IBundleOperator {
    public static final int CFG_FORMAT = -2;
    private final String mFileName;
    private final File mRootDir;

    public ByteDataBundleOperator(File file, String str) {
        this.mRootDir = file;
        if (str == null) {
            this.mFileName = BundleType.ByteData.name;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(BundleType.ByteData.name);
        sb.append("_");
        sb.append(str);
        this.mFileName = sb.toString();
    }

    public ByteDataBundleOperator(File file) {
        this(file, null);
    }

    public void readBundlesFromCfg(List<String> list, Map<String, IBundle> map) throws IOException {
        File file = new File(this.mRootDir, this.mFileName);
        if (!file.exists()) {
            StringBuilder sb = new StringBuilder("cfg file not exists:");
            sb.append(file.getAbsolutePath());
            throw new FileNotFoundException(sb.toString());
        }
        BufferedInputStream bufferedInputStream = null;
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
            try {
                readBundlesFromInputStream(bufferedInputStream2, list, map);
                StreamUtil.closeSafely(bufferedInputStream2);
            } catch (IOException e) {
                BufferedInputStream bufferedInputStream3 = bufferedInputStream2;
                e = e;
                bufferedInputStream = bufferedInputStream3;
                Log.w(IBundleOperator.TAG, "Delete the file:".concat(String.valueOf(file)), e);
                file.delete();
                map.clear();
                list.clear();
                throw e;
            } catch (Throwable th) {
                th = th;
                bufferedInputStream = bufferedInputStream2;
                StreamUtil.closeSafely(bufferedInputStream);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            Log.w(IBundleOperator.TAG, "Delete the file:".concat(String.valueOf(file)), e);
            file.delete();
            if (map != null && !map.isEmpty()) {
                map.clear();
            }
            if (list != null && !list.isEmpty()) {
                list.clear();
            }
            throw e;
        } catch (Throwable th2) {
            th = th2;
            Log.w(IBundleOperator.TAG, "Delete the file:".concat(String.valueOf(file)), th);
            file.delete();
            if (map != null && !map.isEmpty()) {
                map.clear();
            }
            if (list != null && !list.isEmpty()) {
                list.clear();
            }
            StreamUtil.closeSafely(bufferedInputStream);
        }
    }

    public BundleType getBundleType() {
        return BundleType.ByteData;
    }

    public void readBundlesFromInputStream(InputStream inputStream, List<String> list, Map<String, IBundle> map) throws IOException {
        List<String> list2;
        if (inputStream != null) {
            if (map != null && !map.isEmpty()) {
                map.clear();
            }
            if (list != null && !list.isEmpty()) {
                list.clear();
            }
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            int readInt = ByteOrderDataUtil.readInt(bufferedInputStream);
            if (-2 == readInt) {
                list2 = ByteOrderDataUtil.readStringList2(bufferedInputStream);
            } else {
                int i = (readInt >> 24) & 255;
                List<String> arrayList = new ArrayList<>(i);
                int i2 = (readInt >> 16) & 255;
                byte[] bArr = new byte[i2];
                bArr[0] = (byte) ((readInt >> 8) & 255);
                bArr[1] = (byte) (readInt & 255);
                bufferedInputStream.read(bArr, 2, i2 - 2);
                arrayList.add(new String(bArr, "utf-8"));
                for (int i3 = 1; i3 < i; i3++) {
                    arrayList.add(ByteOrderDataUtil.readString(bufferedInputStream));
                }
                list2 = arrayList;
            }
            if (!(list == null || list2 == null)) {
                list.addAll(list2);
            }
            if (map != null) {
                int readInt2 = ByteOrderDataUtil.readInt(bufferedInputStream);
                for (int i4 = 0; i4 < readInt2; i4++) {
                    ByteDataBundle read = new ByteDataBundle().read(bufferedInputStream);
                    map.put(read.getName(), read);
                }
            }
        }
    }

    @Deprecated
    public void writeBundlesToCfg(List<String> list, List<IBundle> list2) throws IOException {
        File file = new File(this.mRootDir, this.mFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file));
            try {
                ByteOrderDataUtil.writeStringList(bufferedOutputStream2, list);
                if (list2 != null && !list2.isEmpty()) {
                    ByteOrderDataUtil.writeInt(bufferedOutputStream2, list2.size());
                    for (IBundle byteDataBundle : list2) {
                        new ByteDataBundle(byteDataBundle).write(bufferedOutputStream2);
                    }
                    bufferedOutputStream2.flush();
                }
                StreamUtil.closeSafely(bufferedOutputStream2);
            } catch (IOException e) {
                e = e;
                bufferedOutputStream = bufferedOutputStream2;
                Log.w(IBundleOperator.TAG, "Delete the file:".concat(String.valueOf(file)), e);
                file.delete();
                throw e;
            } catch (Throwable th) {
                th = th;
                bufferedOutputStream = bufferedOutputStream2;
                StreamUtil.closeSafely(bufferedOutputStream);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            Log.w(IBundleOperator.TAG, "Delete the file:".concat(String.valueOf(file)), e);
            file.delete();
            throw e;
        } catch (Throwable th2) {
            th = th2;
            Log.w(IBundleOperator.TAG, "Delete the file:".concat(String.valueOf(file)), th);
            file.delete();
            StreamUtil.closeSafely(bufferedOutputStream);
        }
    }

    @Deprecated
    public void writeBundlesToCfg2(List<String> list, List<IBundle> list2) throws IOException {
        writeBundlesToCfg(list, list2, true);
    }

    public void writeBundlesToCfg(List<String> list, List<IBundle> list2, boolean z) throws IOException {
        File file = new File(this.mRootDir, this.mFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file));
            try {
                ByteOrderDataUtil.writeInt(bufferedOutputStream2, -2);
                ByteOrderDataUtil.writeStringList2(bufferedOutputStream2, list);
                if (list2 != null && !list2.isEmpty()) {
                    ByteOrderDataUtil.writeInt(bufferedOutputStream2, list2.size());
                    for (IBundle byteDataBundle : list2) {
                        new ByteDataBundle(byteDataBundle).write(bufferedOutputStream2);
                    }
                    bufferedOutputStream2.flush();
                }
                StreamUtil.closeSafely(bufferedOutputStream2);
            } catch (IOException e) {
                e = e;
                bufferedOutputStream = bufferedOutputStream2;
                Log.w(IBundleOperator.TAG, "Delete the file:".concat(String.valueOf(file)), e);
                file.delete();
                throw e;
            } catch (Throwable th) {
                th = th;
                bufferedOutputStream = bufferedOutputStream2;
                StreamUtil.closeSafely(bufferedOutputStream);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            Log.w(IBundleOperator.TAG, "Delete the file:".concat(String.valueOf(file)), e);
            file.delete();
            throw e;
        } catch (Throwable th2) {
            th = th2;
            Log.w(IBundleOperator.TAG, "Delete the file:".concat(String.valueOf(file)), th);
            file.delete();
            StreamUtil.closeSafely(bufferedOutputStream);
        }
    }
}
