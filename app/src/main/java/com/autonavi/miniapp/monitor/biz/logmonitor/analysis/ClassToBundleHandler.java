package com.autonavi.miniapp.monitor.biz.logmonitor.analysis;

import android.content.Context;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClassToBundleHandler {
    private static final String DATA_FILE_NAME = "classes_tree";
    private static final String DATA_STREAM_CHARSET = "UTF-8";
    private static final int LEAF_CLASS_MODBIT = 16384;
    private static final char LEAF_CLASS_PREFIX = '_';
    private static final int NULL_BUNDLE_INDEX = -1;
    private static final String TAG = "ClassToBundleHandler";
    private static final int _LEAF_CLASS_MODBIT = -16385;
    private Context mContext;
    private Node mRootNode;

    static class Node {
        public String bundleName;
        public Map<String, Node> childNodes;
        public Node fatherNode;
        public String nodeName;

        private Node() {
        }
    }

    public ClassToBundleHandler(Context context) {
        this.mContext = context;
    }

    public void onDestory() {
        this.mRootNode = null;
    }

    public String getBundleByClass(String str) {
        boolean z;
        if (str == null || str.length() == 0) {
            return null;
        }
        prepareMappingTree();
        if (this.mRootNode == null || this.mRootNode.childNodes == null) {
            return null;
        }
        Node node = this.mRootNode;
        int i = 0;
        while (true) {
            int indexOf = str.indexOf(46, i);
            if (indexOf < 0) {
                indexOf = str.length();
                z = true;
            } else {
                z = false;
            }
            String substring = str.substring(i, indexOf);
            int i2 = indexOf + 1;
            if (z) {
                substring = "_".concat(String.valueOf(substring));
            }
            Node node2 = node.childNodes.get(substring);
            if (node2 == null) {
                return node.bundleName;
            }
            if (node2.childNodes == null) {
                return node2.bundleName;
            }
            if (z) {
                return null;
            }
            node = node2;
            i = i2;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:23|(0)|37|38) */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:12|13|14|15|16|17|18) */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:21|22|25|26|(0)|30|31|32|33) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0031 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x004e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x0056 */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004b A[SYNTHETIC, Splitter:B:28:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0053 A[SYNTHETIC, Splitter:B:35:0x0053] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:16:0x0031=Splitter:B:16:0x0031, B:37:0x0056=Splitter:B:37:0x0056, B:30:0x004e=Splitter:B:30:0x004e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean prepareMappingTree() {
        /*
            r5 = this;
            com.autonavi.miniapp.monitor.biz.logmonitor.analysis.ClassToBundleHandler$Node r0 = r5.mRootNode
            r1 = 1
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            monitor-enter(r5)
            com.autonavi.miniapp.monitor.biz.logmonitor.analysis.ClassToBundleHandler$Node r0 = r5.mRootNode     // Catch:{ all -> 0x0057 }
            if (r0 == 0) goto L_0x000d
            monitor-exit(r5)     // Catch:{ all -> 0x0057 }
            return r1
        L_0x000d:
            r0 = 0
            android.content.Context r2 = r5.mContext     // Catch:{ Throwable -> 0x003d }
            android.content.res.Resources r2 = r2.getResources()     // Catch:{ Throwable -> 0x003d }
            android.content.res.AssetManager r2 = r2.getAssets()     // Catch:{ Throwable -> 0x003d }
            java.lang.String r3 = "classes_tree"
            java.io.InputStream r2 = r2.open(r3)     // Catch:{ Throwable -> 0x003d }
            java.io.DataInputStream r3 = new java.io.DataInputStream     // Catch:{ Throwable -> 0x003d }
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x003d }
            r4.<init>(r2)     // Catch:{ Throwable -> 0x003d }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x003d }
            com.autonavi.miniapp.monitor.biz.logmonitor.analysis.ClassToBundleHandler$Node r0 = r5.parseMappingTree(r3)     // Catch:{ Throwable -> 0x0037, all -> 0x0033 }
            r5.mRootNode = r0     // Catch:{ Throwable -> 0x0037, all -> 0x0033 }
            r3.close()     // Catch:{ Throwable -> 0x0031 }
        L_0x0031:
            monitor-exit(r5)     // Catch:{ all -> 0x0057 }
            return r1
        L_0x0033:
            r0 = move-exception
            r1 = r0
            r0 = r3
            goto L_0x0051
        L_0x0037:
            r0 = move-exception
            r1 = r0
            r0 = r3
            goto L_0x003e
        L_0x003b:
            r1 = move-exception
            goto L_0x0051
        L_0x003d:
            r1 = move-exception
        L_0x003e:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x003b }
            java.lang.String r3 = "ClassToBundleHandler"
            java.lang.String r4 = "prepareMappingTree"
            r2.error(r3, r4, r1)     // Catch:{ all -> 0x003b }
            if (r0 == 0) goto L_0x004e
            r0.close()     // Catch:{ Throwable -> 0x004e }
        L_0x004e:
            monitor-exit(r5)     // Catch:{ all -> 0x0057 }
            r0 = 0
            return r0
        L_0x0051:
            if (r0 == 0) goto L_0x0056
            r0.close()     // Catch:{ Throwable -> 0x0056 }
        L_0x0056:
            throw r1     // Catch:{ all -> 0x0057 }
        L_0x0057:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0057 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.analysis.ClassToBundleHandler.prepareMappingTree():boolean");
    }

    private Node parseMappingTree(DataInputStream dataInputStream) throws IOException {
        short readShort = dataInputStream.readShort();
        Node[] nodeArr = new Node[readShort];
        short readShort2 = dataInputStream.readShort();
        String[] strArr = new String[readShort2];
        for (int i = 0; i < readShort2; i++) {
            short readShort3 = dataInputStream.readShort();
            if (readShort3 != 0) {
                byte[] bArr = new byte[readShort3];
                dataInputStream.read(bArr);
                strArr[i] = new String(bArr, "UTF-8");
            }
        }
        Node node = null;
        for (int i2 = 0; i2 < readShort; i2++) {
            Node parseClassToBundleNode = parseClassToBundleNode(i2, dataInputStream, strArr, nodeArr);
            if (i2 == 0) {
                node = parseClassToBundleNode;
            }
        }
        if (dataInputStream.read() < 0) {
            return node;
        }
        throw new IllegalStateException("has not read the end");
    }

    private Node parseClassToBundleNode(int i, DataInputStream dataInputStream, String[] strArr, Node[] nodeArr) throws IOException {
        String str;
        String str2;
        short readShort = dataInputStream.readShort();
        short readShort2 = dataInputStream.readShort();
        byte[] bArr = new byte[dataInputStream.readShort()];
        dataInputStream.read(bArr);
        String str3 = new String(bArr, "UTF-8");
        Node node = null;
        Node node2 = new Node();
        if (readShort2 != -1) {
            if ((readShort2 & 16384) == 16384) {
                readShort2 = (short) (readShort2 & -16385);
                str2 = "_".concat(String.valueOf(str3));
            } else {
                str2 = str3;
            }
            str = strArr[readShort2];
        } else {
            str2 = str3;
            str = null;
        }
        if (i != 0) {
            node = nodeArr[readShort];
            if (node.childNodes == null) {
                node.childNodes = new HashMap();
            }
            node.childNodes.put(str2, node2);
        }
        node2.nodeName = str3;
        node2.bundleName = str;
        node2.fatherNode = node;
        nodeArr[i] = node2;
        return node2;
    }
}
