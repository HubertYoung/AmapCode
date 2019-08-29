package org.eclipse.mat.parser.internal;

import java.math.BigInteger;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayInt;
import org.eclipse.mat.collect.HashMapIntObject;
import org.eclipse.mat.snapshot.ISnapshot;

class ThreadStackHelper {
    ThreadStackHelper() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x00e5 A[SYNTHETIC, Splitter:B:51:0x00e5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static org.eclipse.mat.collect.HashMapIntObject<org.eclipse.mat.snapshot.model.IThreadStack> loadThreadsData(org.eclipse.mat.snapshot.ISnapshot r9) throws org.eclipse.mat.SnapshotException {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            org.eclipse.mat.snapshot.SnapshotInfo r1 = r9.getSnapshotInfo()
            java.lang.String r1 = r1.getPrefix()
            r0.append(r1)
            java.lang.String r1 = "threads"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r0 = r1.exists()
            r2 = 0
            if (r0 != 0) goto L_0x0026
            return r2
        L_0x0026:
            org.eclipse.mat.collect.HashMapIntObject r0 = new org.eclipse.mat.collect.HashMapIntObject
            r0.<init>()
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ IOException -> 0x00dc }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ IOException -> 0x00dc }
            r4.<init>(r1)     // Catch:{ IOException -> 0x00dc }
            r3.<init>(r4)     // Catch:{ IOException -> 0x00dc }
            java.lang.String r1 = r3.readLine()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
        L_0x0039:
            if (r1 == 0) goto L_0x00d0
            java.lang.String r1 = r1.trim()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            java.lang.String r2 = "Thread"
            boolean r2 = r1.startsWith(r2)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            if (r2 == 0) goto L_0x00c8
            long r1 = readThreadAddres(r1)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            r4.<init>()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            org.eclipse.mat.collect.HashMapIntObject r5 = new org.eclipse.mat.collect.HashMapIntObject     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            r5.<init>()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            java.lang.String r6 = r3.readLine()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
        L_0x0059:
            if (r6 == 0) goto L_0x006f
            java.lang.String r7 = ""
            boolean r7 = r6.equals(r7)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            if (r7 != 0) goto L_0x006f
            java.lang.String r6 = r6.trim()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            r4.add(r6)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            java.lang.String r6 = r3.readLine()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            goto L_0x0059
        L_0x006f:
            java.lang.String r6 = r3.readLine()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            if (r6 == 0) goto L_0x00b1
            java.lang.String r7 = r6.trim()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            java.lang.String r8 = "locals"
            boolean r7 = r7.startsWith(r8)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            if (r7 == 0) goto L_0x00b1
            java.lang.String r6 = r3.readLine()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
        L_0x0085:
            if (r6 == 0) goto L_0x00b1
            java.lang.String r7 = ""
            boolean r7 = r6.equals(r7)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            if (r7 != 0) goto L_0x00b1
            int r7 = readLineNumber(r6)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            if (r7 < 0) goto L_0x00ac
            int r6 = readLocalId(r6, r9)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            java.lang.Object r8 = r5.get(r7)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            org.eclipse.mat.collect.ArrayInt r8 = (org.eclipse.mat.collect.ArrayInt) r8     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            if (r8 != 0) goto L_0x00a9
            org.eclipse.mat.collect.ArrayInt r8 = new org.eclipse.mat.collect.ArrayInt     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            r8.<init>()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            r5.put(r7, r8)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
        L_0x00a9:
            r8.add(r6)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
        L_0x00ac:
            java.lang.String r6 = r3.readLine()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            goto L_0x0085
        L_0x00b1:
            r7 = -1
            int r7 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x00c7
            int r1 = r9.mapAddressToId(r1)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            org.eclipse.mat.parser.internal.ThreadStackImpl r2 = new org.eclipse.mat.parser.internal.ThreadStackImpl     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            org.eclipse.mat.parser.internal.StackFrameImpl[] r4 = buildFrames(r4, r5)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            r2.<init>(r1, r4)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            r0.put(r1, r2)     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
        L_0x00c7:
            r1 = r6
        L_0x00c8:
            if (r1 == 0) goto L_0x00d0
            java.lang.String r1 = r3.readLine()     // Catch:{ IOException -> 0x00d6, all -> 0x00d4 }
            goto L_0x0039
        L_0x00d0:
            r3.close()     // Catch:{ Exception -> 0x00d3 }
        L_0x00d3:
            return r0
        L_0x00d4:
            r9 = move-exception
            goto L_0x00e3
        L_0x00d6:
            r9 = move-exception
            r2 = r3
            goto L_0x00dd
        L_0x00d9:
            r9 = move-exception
            r3 = r2
            goto L_0x00e3
        L_0x00dc:
            r9 = move-exception
        L_0x00dd:
            org.eclipse.mat.SnapshotException r0 = new org.eclipse.mat.SnapshotException     // Catch:{ all -> 0x00d9 }
            r0.<init>(r9)     // Catch:{ all -> 0x00d9 }
            throw r0     // Catch:{ all -> 0x00d9 }
        L_0x00e3:
            if (r3 == 0) goto L_0x00e8
            r3.close()     // Catch:{ Exception -> 0x00e8 }
        L_0x00e8:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.internal.ThreadStackHelper.loadThreadsData(org.eclipse.mat.snapshot.ISnapshot):org.eclipse.mat.collect.HashMapIntObject");
    }

    private static long readThreadAddres(String str) {
        int indexOf = str.indexOf("0x");
        if (indexOf < 0) {
            return -1;
        }
        return new BigInteger(str.substring(indexOf + 2), 16).longValue();
    }

    private static int readLocalId(String str, ISnapshot iSnapshot) throws SnapshotException {
        int indexOf = str.indexOf("0x");
        return iSnapshot.mapAddressToId(new BigInteger(str.substring(indexOf + 2, str.indexOf(44, indexOf)), 16).longValue());
    }

    private static int readLineNumber(String str) {
        return Integer.valueOf(str.substring(str.indexOf("line=") + 5)).intValue();
    }

    private static StackFrameImpl[] buildFrames(List<String> list, HashMapIntObject<ArrayInt> hashMapIntObject) {
        int size = list.size();
        StackFrameImpl[] stackFrameImplArr = new StackFrameImpl[size];
        for (int i = 0; i < size; i++) {
            int[] iArr = null;
            ArrayInt arrayInt = (ArrayInt) hashMapIntObject.get(i);
            if (arrayInt != null && arrayInt.size() > 0) {
                iArr = arrayInt.toArray();
            }
            stackFrameImplArr[i] = new StackFrameImpl(list.get(i), iArr);
        }
        return stackFrameImplArr;
    }
}
