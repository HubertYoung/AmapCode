package com.alipay.mobile.framework.util;

import android.content.Context;
import com.alipay.mobile.framework.MicroDescription;
import com.alipay.mobile.framework.PackageDescription;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.msg.BroadcastReceiverDescription;
import com.alipay.mobile.framework.pipeline.ValveDescription;
import com.alipay.mobile.framework.service.ServiceDescription;
import com.alipay.mobile.quinox.log.Log;
import com.alipay.mobile.quinox.utils.ProcessLock;
import com.alipay.mobile.quinox.utils.StreamUtil;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MetaInfoOperator {
    public static final String METAINFO_MF = "METAINFO.MF";
    public static final String META_INFO_CFG = "metainfos.cfg";
    public static final String META_INFO_EXT_CFG = "metainfos-ext.cfg";
    public static final String META_INF_METAINFO_MF = "META-INF/METAINFO.MF";
    public static final String TAG = "MetaInfoOperator";
    private Context a;

    public MetaInfoOperator(Context context) {
        this.a = context;
    }

    public MetaInfoOperator() {
        this(null);
    }

    private static List<MicroDescription<?>> a(BufferedInputStream bis) {
        MicroDescription desc;
        try {
            int N = ByteOrderDataUtil.readInt(bis);
            List list = new ArrayList(N);
            for (int i = 0; i < N; i++) {
                byte type = ByteOrderDataUtil.readByte(bis);
                switch (type) {
                    case 1:
                        desc = new ApplicationDescription();
                        break;
                    case 2:
                        desc = new ServiceDescription();
                        break;
                    case 3:
                        desc = new BroadcastReceiverDescription();
                        break;
                    case 4:
                        desc = new ValveDescription();
                        break;
                    case 5:
                        desc = new PackageDescription();
                        break;
                    default:
                        throw new RuntimeException("Failed to create MicroDescription: type=" + type);
                }
                desc.deserialize(bis);
                list.add(desc);
            }
            return list;
        } catch (Throwable e) {
            Log.w((String) TAG, e);
            return null;
        }
    }

    public List<MicroDescription<?>> readMetaInfo(InputStream is) {
        if (is == null) {
            return null;
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        try {
            return a(bis);
        } finally {
            StreamUtil.closeSafely(bis);
        }
    }

    public List<MicroDescription<?>> readMetaInfo(File file) {
        List list = null;
        FileInputStream fis = null;
        try {
            FileInputStream fis2 = new FileInputStream(file);
            try {
                list = readMetaInfo((InputStream) fis2);
                StreamUtil.closeSafely(fis2);
                FileInputStream fileInputStream = fis2;
            } catch (Throwable th) {
                th = th;
                fis = fis2;
                StreamUtil.closeSafely(fis);
                throw th;
            }
        } catch (Throwable th2) {
            e = th2;
            Log.w((String) TAG, e);
            StreamUtil.closeSafely(fis);
            return list;
        }
        return list;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0053, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0054, code lost:
        r4 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0081, code lost:
        r6 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0082, code lost:
        r4 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0091, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0092, code lost:
        com.alipay.mobile.quinox.log.Log.w((java.lang.String) TAG, r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0081 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:4:0x0009] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0085 A[SYNTHETIC, Splitter:B:38:0x0085] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.alipay.mobile.framework.MicroDescription<?>> readMetaInfoFromZipFile(java.io.File r10) {
        /*
            r9 = this;
            r2 = 0
            r4 = 0
            java.util.zip.ZipFile r5 = new java.util.zip.ZipFile     // Catch:{ Throwable -> 0x009a }
            r5.<init>(r10)     // Catch:{ Throwable -> 0x009a }
            java.lang.String r6 = "META-INF/METAINFO.MF"
            java.util.zip.ZipEntry r3 = r5.getEntry(r6)     // Catch:{ Throwable -> 0x0039, all -> 0x0081 }
            if (r3 != 0) goto L_0x002c
            java.lang.String r6 = "MetaInfoOperator"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0039, all -> 0x0081 }
            r7.<init>()     // Catch:{ Throwable -> 0x0039, all -> 0x0081 }
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ Throwable -> 0x0039, all -> 0x0081 }
            java.lang.String r8 = " can't find META-INF/METAINFO.MF == null."
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x0039, all -> 0x0081 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0039, all -> 0x0081 }
            com.alipay.mobile.quinox.log.Log.w(r6, r7)     // Catch:{ Throwable -> 0x0039, all -> 0x0081 }
        L_0x0027:
            r5.close()     // Catch:{ Throwable -> 0x0089 }
            r4 = r5
        L_0x002b:
            return r2
        L_0x002c:
            r1 = 0
            java.io.InputStream r1 = r5.getInputStream(r3)     // Catch:{ all -> 0x007c }
            java.util.List r2 = r9.readMetaInfo(r1)     // Catch:{ all -> 0x007c }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r1)     // Catch:{ Throwable -> 0x0039, all -> 0x0081 }
            goto L_0x0027
        L_0x0039:
            r0 = move-exception
            java.lang.String r6 = "MetaInfoOperator"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0053, all -> 0x0081 }
            r7.<init>()     // Catch:{ Throwable -> 0x0053, all -> 0x0081 }
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ Throwable -> 0x0053, all -> 0x0081 }
            java.lang.String r8 = " can't find META-INF/METAINFO.MF. \n"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x0053, all -> 0x0081 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0053, all -> 0x0081 }
            com.alipay.mobile.quinox.log.Log.w(r6, r7, r0)     // Catch:{ Throwable -> 0x0053, all -> 0x0081 }
            goto L_0x0027
        L_0x0053:
            r0 = move-exception
            r4 = r5
        L_0x0055:
            java.lang.String r6 = "MetaInfoOperator"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0098 }
            java.lang.String r8 = "Failed to create ZipFile: "
            r7.<init>(r8)     // Catch:{ all -> 0x0098 }
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ all -> 0x0098 }
            java.lang.String r8 = ". \n"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0098 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0098 }
            com.alipay.mobile.quinox.log.Log.w(r6, r7, r0)     // Catch:{ all -> 0x0098 }
            if (r4 == 0) goto L_0x002b
            r4.close()     // Catch:{ Throwable -> 0x0075 }
            goto L_0x002b
        L_0x0075:
            r0 = move-exception
            java.lang.String r6 = "MetaInfoOperator"
            com.alipay.mobile.quinox.log.Log.w(r6, r0)
            goto L_0x002b
        L_0x007c:
            r6 = move-exception
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r1)     // Catch:{ Throwable -> 0x0039, all -> 0x0081 }
            throw r6     // Catch:{ Throwable -> 0x0039, all -> 0x0081 }
        L_0x0081:
            r6 = move-exception
            r4 = r5
        L_0x0083:
            if (r4 == 0) goto L_0x0088
            r4.close()     // Catch:{ Throwable -> 0x0091 }
        L_0x0088:
            throw r6
        L_0x0089:
            r0 = move-exception
            java.lang.String r6 = "MetaInfoOperator"
            com.alipay.mobile.quinox.log.Log.w(r6, r0)
            r4 = r5
            goto L_0x002b
        L_0x0091:
            r0 = move-exception
            java.lang.String r7 = "MetaInfoOperator"
            com.alipay.mobile.quinox.log.Log.w(r7, r0)
            goto L_0x0088
        L_0x0098:
            r6 = move-exception
            goto L_0x0083
        L_0x009a:
            r0 = move-exception
            goto L_0x0055
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.framework.util.MetaInfoOperator.readMetaInfoFromZipFile(java.io.File):java.util.List");
    }

    public Map<String, List<MicroDescription<?>>> readMetaInfoCfg(InputStream is) {
        BufferedInputStream bis = null;
        try {
            BufferedInputStream bis2 = new BufferedInputStream(is);
            try {
                int N = ByteOrderDataUtil.readInt(bis2);
                Map map = new HashMap(N);
                for (int i = 0; i < N; i++) {
                    String bundleName = ByteOrderDataUtil.readString(bis2);
                    List list = a(bis2);
                    if (list == null) {
                        throw new RuntimeException(bundleName + ": empty MicroDescription List.");
                    }
                    map.put(bundleName, list);
                }
                StreamUtil.closeSafely(bis2);
                BufferedInputStream bufferedInputStream = bis2;
                return map;
            } catch (Throwable th) {
                th = th;
                bis = bis2;
                StreamUtil.closeSafely(bis);
                throw th;
            }
        } catch (Throwable th2) {
            e = th2;
            Log.w((String) TAG, e);
            StreamUtil.closeSafely(bis);
            return null;
        }
    }

    public Map<String, List<MicroDescription<?>>> readMetaInfoCfg(File file) {
        Map map = null;
        FileInputStream is = null;
        try {
            FileInputStream is2 = new FileInputStream(file);
            try {
                map = readMetaInfoCfg((InputStream) is2);
                StreamUtil.closeSafely(is2);
                FileInputStream fileInputStream = is2;
            } catch (Throwable th) {
                th = th;
                is = is2;
                StreamUtil.closeSafely(is);
                throw th;
            }
        } catch (Throwable th2) {
            e = th2;
            Log.w((String) TAG, e);
            StreamUtil.closeSafely(is);
            return map;
        }
        return map;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        com.alipay.mobile.quinox.log.Log.w(TAG, r10 + "can't read assets/metainfos.cfg", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0086, code lost:
        r6 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0087, code lost:
        r4 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x009b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x009c, code lost:
        com.alipay.mobile.quinox.log.Log.w((java.lang.String) TAG, r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0086 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:4:0x0009] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x008a A[SYNTHETIC, Splitter:B:40:0x008a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Map<java.lang.String, java.util.List<com.alipay.mobile.framework.MicroDescription<?>>> readMetaInfoCfgFromApk(java.io.File r10) {
        /*
            r9 = this;
            r2 = 0
            r4 = 0
            java.util.zip.ZipFile r5 = new java.util.zip.ZipFile     // Catch:{ Throwable -> 0x00a4 }
            r5.<init>(r10)     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r6 = "assets/metainfos.cfg"
            java.util.zip.ZipEntry r3 = r5.getEntry(r6)     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
            if (r3 != 0) goto L_0x002c
            java.lang.String r6 = "MetaInfoOperator"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
            r7.<init>()     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
            java.lang.String r8 = " can't read assets/metainfos.cfg"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
            com.alipay.mobile.quinox.log.Log.w(r6, r7)     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
        L_0x0027:
            r5.close()     // Catch:{ Throwable -> 0x0093 }
            r4 = r5
        L_0x002b:
            return r2
        L_0x002c:
            r1 = 0
            java.io.InputStream r1 = r5.getInputStream(r3)     // Catch:{ Throwable -> 0x007c }
            java.util.Map r2 = r9.readMetaInfoCfg(r1)     // Catch:{ Throwable -> 0x007c }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r1)     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
            goto L_0x0027
        L_0x0039:
            r0 = move-exception
            java.lang.String r6 = "MetaInfoOperator"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0053, all -> 0x0086 }
            r7.<init>()     // Catch:{ Throwable -> 0x0053, all -> 0x0086 }
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ Throwable -> 0x0053, all -> 0x0086 }
            java.lang.String r8 = "can't read assets/metainfos.cfg"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x0053, all -> 0x0086 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0053, all -> 0x0086 }
            com.alipay.mobile.quinox.log.Log.w(r6, r7, r0)     // Catch:{ Throwable -> 0x0053, all -> 0x0086 }
            goto L_0x0027
        L_0x0053:
            r0 = move-exception
            r4 = r5
        L_0x0055:
            java.lang.String r6 = "MetaInfoOperator"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a2 }
            java.lang.String r8 = "Failed to create ZipFile: "
            r7.<init>(r8)     // Catch:{ all -> 0x00a2 }
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ all -> 0x00a2 }
            java.lang.String r8 = ". \n"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x00a2 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00a2 }
            com.alipay.mobile.quinox.log.Log.w(r6, r7, r0)     // Catch:{ all -> 0x00a2 }
            if (r4 == 0) goto L_0x002b
            r4.close()     // Catch:{ Throwable -> 0x0075 }
            goto L_0x002b
        L_0x0075:
            r0 = move-exception
            java.lang.String r6 = "MetaInfoOperator"
            com.alipay.mobile.quinox.log.Log.w(r6, r0)
            goto L_0x002b
        L_0x007c:
            r0 = move-exception
            java.lang.String r6 = "MetaInfoOperator"
            com.alipay.mobile.quinox.log.Log.w(r6, r0)     // Catch:{ all -> 0x008e }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r1)     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
            goto L_0x0027
        L_0x0086:
            r6 = move-exception
            r4 = r5
        L_0x0088:
            if (r4 == 0) goto L_0x008d
            r4.close()     // Catch:{ Throwable -> 0x009b }
        L_0x008d:
            throw r6
        L_0x008e:
            r6 = move-exception
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r1)     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
            throw r6     // Catch:{ Throwable -> 0x0039, all -> 0x0086 }
        L_0x0093:
            r0 = move-exception
            java.lang.String r6 = "MetaInfoOperator"
            com.alipay.mobile.quinox.log.Log.w(r6, r0)
            r4 = r5
            goto L_0x002b
        L_0x009b:
            r0 = move-exception
            java.lang.String r7 = "MetaInfoOperator"
            com.alipay.mobile.quinox.log.Log.w(r7, r0)
            goto L_0x008d
        L_0x00a2:
            r6 = move-exception
            goto L_0x0088
        L_0x00a4:
            r0 = move-exception
            goto L_0x0055
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.framework.util.MetaInfoOperator.readMetaInfoCfgFromApk(java.io.File):java.util.Map");
    }

    private void a(List<MicroDescription<?>> descs, BufferedOutputStream bos) {
        if (this.a != null) {
            ProcessLock processLock = new ProcessLock(this.a.getCacheDir() + "/.doWriteMetaInfo.lock");
            try {
                processLock.lock();
                b(descs, bos);
            } finally {
                processLock.unlock();
            }
        } else {
            b(descs, bos);
        }
    }

    private static void b(List<MicroDescription<?>> descs, BufferedOutputStream bos) {
        if (descs != null && !descs.isEmpty()) {
            ByteOrderDataUtil.writeInt(bos, descs.size());
            for (MicroDescription desc : descs) {
                if (desc instanceof ApplicationDescription) {
                    ByteOrderDataUtil.writeByte(bos, 1);
                } else if (desc instanceof ServiceDescription) {
                    ByteOrderDataUtil.writeByte(bos, 2);
                } else if (desc instanceof BroadcastReceiverDescription) {
                    ByteOrderDataUtil.writeByte(bos, 3);
                } else if (desc instanceof ValveDescription) {
                    ByteOrderDataUtil.writeByte(bos, 4);
                } else if (desc instanceof PackageDescription) {
                    ByteOrderDataUtil.writeByte(bos, 5);
                } else {
                    throw new RuntimeException("UnKnown MicroDescription : " + desc);
                }
                desc.serialize(bos);
            }
        }
    }

    public void writeMetaInfo(List<MicroDescription<?>> descriptions, OutputStream os) {
        if (descriptions != null && !descriptions.isEmpty() && os != null) {
            BufferedOutputStream bos = null;
            try {
                BufferedOutputStream bos2 = new BufferedOutputStream(os);
                try {
                    a(descriptions, bos2);
                    bos2.flush();
                    StreamUtil.closeSafely(bos2);
                } catch (Throwable th) {
                    th = th;
                    bos = bos2;
                    StreamUtil.closeSafely(bos);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                StreamUtil.closeSafely(bos);
                throw th;
            }
        }
    }

    public void writeMetaInfo(List<MicroDescription<?>> descriptions, File file) {
        if (descriptions != null && !descriptions.isEmpty() && file != null) {
            writeMetaInfo(descriptions, (OutputStream) new FileOutputStream(file));
        }
    }

    public void writeMetaInfoCfg(Map<String, List<MicroDescription<?>>> descriptions, OutputStream os) {
        if (this.a != null) {
            ProcessLock processLock = new ProcessLock(this.a.getCacheDir() + "/.writeMetaInfoCfg.lock");
            try {
                processLock.lock();
                a(descriptions, os);
            } finally {
                processLock.unlock();
            }
        } else {
            a(descriptions, os);
        }
    }

    private void a(Map<String, List<MicroDescription<?>>> descriptions, OutputStream os) {
        if (descriptions != null && !descriptions.isEmpty() && os != null) {
            Set entrySet = descriptions.entrySet();
            BufferedOutputStream bos = null;
            try {
                BufferedOutputStream bos2 = new BufferedOutputStream(os);
                try {
                    ByteOrderDataUtil.writeInt(bos2, entrySet.size());
                    for (Entry entry : entrySet) {
                        ByteOrderDataUtil.writeString(bos2, (String) entry.getKey());
                        a((List) entry.getValue(), bos2);
                    }
                    bos2.flush();
                    StreamUtil.closeSafely(bos2);
                } catch (Throwable th) {
                    th = th;
                    bos = bos2;
                    StreamUtil.closeSafely(bos);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                StreamUtil.closeSafely(bos);
                throw th;
            }
        }
    }

    public void writeMetaInfoCfg(Map<String, List<MicroDescription<?>>> descriptions, File file) {
        if (descriptions != null && !descriptions.isEmpty() && file != null) {
            FileOutputStream fos = null;
            try {
                FileOutputStream fos2 = new FileOutputStream(file);
                try {
                    writeMetaInfoCfg(descriptions, (OutputStream) fos2);
                    StreamUtil.closeSafely(fos2);
                } catch (Throwable th) {
                    th = th;
                    fos = fos2;
                    StreamUtil.closeSafely(fos);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                StreamUtil.closeSafely(fos);
                throw th;
            }
        }
    }
}
