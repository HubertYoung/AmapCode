package org.eclipse.mat.hprof;

import com.alibaba.analytics.core.sync.UploadQueueMgr;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.parser.io.BufferedRandomAccessInputStream;
import org.eclipse.mat.parser.io.PositionInputStream;
import org.eclipse.mat.parser.model.ClassImpl;
import org.eclipse.mat.parser.model.ClassLoaderImpl;
import org.eclipse.mat.parser.model.InstanceImpl;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.Field;
import org.eclipse.mat.snapshot.model.FieldDescriptor;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.util.MessageUtil;

public class HprofRandomAccessParser extends AbstractParser {
    public static final int LAZY_LOADING_LIMIT = 256;

    public HprofRandomAccessParser(File file, Version version, int i) throws IOException {
        this.in = new PositionInputStream(new BufferedRandomAccessInputStream(new RandomAccessFile(file, UploadQueueMgr.MSGTYPE_REALTIME), 512));
        this.version = version;
        this.idSize = i;
    }

    public synchronized void close() throws IOException {
        this.in.close();
    }

    public synchronized IObject read(int i, long j, ISnapshot iSnapshot) throws IOException, SnapshotException {
        try {
            this.in.seek(j);
            int readUnsignedByte = this.in.readUnsignedByte();
            switch (readUnsignedByte) {
                case 33:
                    return readInstanceDump(i, iSnapshot);
                case 34:
                    return readObjectArrayDump(i, iSnapshot);
                case 35:
                    return readPrimitiveArrayDump(i, iSnapshot);
                default:
                    throw new IOException(MessageUtil.format(Messages.HprofRandomAccessParser_Error_IllegalDumpSegment, Integer.valueOf(readUnsignedByte)));
            }
        }
    }

    public List<IClass> resolveClassHierarchy(ISnapshot iSnapshot, IClass iClass) throws SnapshotException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(iClass);
        while (iClass.hasSuperClass()) {
            iClass = (IClass) iSnapshot.getObject(iClass.getSuperClassId());
            if (iClass == null) {
                return null;
            }
            arrayList.add(iClass);
        }
        return arrayList;
    }

    private IObject readInstanceDump(int i, ISnapshot iSnapshot) throws IOException, SnapshotException {
        long readID = readID();
        if (this.in.skipBytes(this.idSize + 8) != this.idSize + 8) {
            throw new IOException();
        }
        List<IClass> resolveClassHierarchy = resolveClassHierarchy(iSnapshot, iSnapshot.getClassOf(i));
        if (resolveClassHierarchy == null) {
            throw new IOException(Messages.HprofRandomAccessParser_Error_DumpIncomplete.pattern);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<IClass> it = resolveClassHierarchy.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            List<FieldDescriptor> fieldDescriptors = it.next().getFieldDescriptors();
            for (int i2 = 0; i2 < fieldDescriptors.size(); i2++) {
                FieldDescriptor fieldDescriptor = fieldDescriptors.get(i2);
                arrayList.add(new Field(fieldDescriptor.getName(), fieldDescriptor.getType(), readValue(iSnapshot, fieldDescriptor.getType())));
            }
        }
        ClassImpl classImpl = (ClassImpl) resolveClassHierarchy.get(0);
        if (iSnapshot.isClassLoader(i)) {
            ClassLoaderImpl classLoaderImpl = new ClassLoaderImpl(i, readID, classImpl, arrayList);
            return classLoaderImpl;
        }
        InstanceImpl instanceImpl = new InstanceImpl(i, readID, classImpl, arrayList);
        return instanceImpl;
    }

    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r6v0, types: [java.lang.Object] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.eclipse.mat.snapshot.model.IArray readObjectArrayDump(int r12, org.eclipse.mat.snapshot.ISnapshot r13) throws java.io.IOException, org.eclipse.mat.SnapshotException {
        /*
            r11 = this;
            long r2 = r11.readID()
            org.eclipse.mat.parser.io.PositionInputStream r0 = r11.in
            r1 = 4
            r0.skipBytes(r1)
            org.eclipse.mat.parser.io.PositionInputStream r0 = r11.in
            int r10 = r0.readInt()
            long r0 = r11.readID()
            int r0 = r13.mapAddressToId(r0)
            org.eclipse.mat.snapshot.model.IObject r13 = r13.getObject(r0)
            org.eclipse.mat.snapshot.model.IClass r13 = (org.eclipse.mat.snapshot.model.IClass) r13
            if (r13 != 0) goto L_0x002a
            java.lang.RuntimeException r12 = new java.lang.RuntimeException
            org.eclipse.mat.hprof.Messages r13 = org.eclipse.mat.hprof.Messages.HprofRandomAccessParser_Error_MissingFakeClass
            java.lang.String r13 = r13.pattern
            r12.<init>(r13)
            throw r12
        L_0x002a:
            int r0 = r11.idSize
            int r0 = r0 * r10
            r1 = 256(0x100, float:3.59E-43)
            if (r0 >= r1) goto L_0x0040
            long[] r0 = new long[r10]
            r1 = 0
        L_0x0035:
            if (r1 >= r10) goto L_0x004f
            long r4 = r11.readID()
            r0[r1] = r4
            int r1 = r1 + 1
            goto L_0x0035
        L_0x0040:
            org.eclipse.mat.hprof.ArrayDescription$Offline r0 = new org.eclipse.mat.hprof.ArrayDescription$Offline
            r5 = 0
            org.eclipse.mat.parser.io.PositionInputStream r1 = r11.in
            long r6 = r1.position()
            r8 = 0
            r4 = r0
            r9 = r10
            r4.<init>(r5, r6, r8, r9)
        L_0x004f:
            r6 = r0
            org.eclipse.mat.parser.model.ObjectArrayImpl r7 = new org.eclipse.mat.parser.model.ObjectArrayImpl
            r4 = r13
            org.eclipse.mat.parser.model.ClassImpl r4 = (org.eclipse.mat.parser.model.ClassImpl) r4
            r0 = r7
            r1 = r12
            r5 = r10
            r0.<init>(r1, r2, r4, r5)
            r7.setInfo(r6)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.hprof.HprofRandomAccessParser.readObjectArrayDump(int, org.eclipse.mat.snapshot.ISnapshot):org.eclipse.mat.snapshot.model.IArray");
    }

    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r7v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v11, types: [org.eclipse.mat.hprof.ArrayDescription$Raw] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.eclipse.mat.snapshot.model.IArray readPrimitiveArrayDump(int r13, org.eclipse.mat.snapshot.ISnapshot r14) throws java.io.IOException, org.eclipse.mat.SnapshotException {
        /*
            r12 = this;
            long r2 = r12.readID()
            org.eclipse.mat.parser.io.PositionInputStream r0 = r12.in
            r1 = 4
            r0.skipBytes(r1)
            org.eclipse.mat.parser.io.PositionInputStream r0 = r12.in
            int r10 = r0.readInt()
            org.eclipse.mat.parser.io.PositionInputStream r0 = r12.in
            byte r0 = r0.readByte()
            long r0 = (long) r0
            r4 = 4
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 < 0) goto L_0x00a7
            r4 = 11
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x0025
            goto L_0x00a7
        L_0x0025:
            int[] r4 = org.eclipse.mat.snapshot.model.IPrimitiveArray.ELEMENT_SIZE
            int r11 = (int) r0
            r8 = r4[r11]
            int r4 = r8 * r10
            r5 = 256(0x100, float:3.59E-43)
            if (r4 >= r5) goto L_0x0045
            byte[] r4 = new byte[r4]
            org.eclipse.mat.parser.io.PositionInputStream r5 = r12.in
            r5.readFully(r4)
            r5 = 8
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 != 0) goto L_0x003f
            r0 = r4
            goto L_0x0053
        L_0x003f:
            org.eclipse.mat.hprof.ArrayDescription$Raw r0 = new org.eclipse.mat.hprof.ArrayDescription$Raw
            r0.<init>(r4)
            goto L_0x0053
        L_0x0045:
            org.eclipse.mat.hprof.ArrayDescription$Offline r0 = new org.eclipse.mat.hprof.ArrayDescription$Offline
            r5 = 1
            org.eclipse.mat.parser.io.PositionInputStream r1 = r12.in
            long r6 = r1.position()
            r4 = r0
            r9 = r10
            r4.<init>(r5, r6, r8, r9)
        L_0x0053:
            r7 = r0
            java.lang.String[] r0 = org.eclipse.mat.snapshot.model.IPrimitiveArray.TYPE
            r0 = r0[r11]
            r1 = 0
            java.util.Collection r14 = r14.getClassesByName(r0, r1)
            r4 = 1
            if (r14 == 0) goto L_0x0097
            boolean r5 = r14.isEmpty()
            if (r5 == 0) goto L_0x0067
            goto L_0x0097
        L_0x0067:
            int r5 = r14.size()
            if (r5 <= r4) goto L_0x007d
            java.io.IOException r13 = new java.io.IOException
            org.eclipse.mat.hprof.Messages r14 = org.eclipse.mat.hprof.Messages.HprofRandomAccessParser_Error_DuplicateClass
            java.lang.Object[] r2 = new java.lang.Object[r4]
            r2[r1] = r0
            java.lang.String r14 = org.eclipse.mat.util.MessageUtil.format(r14, r2)
            r13.<init>(r14)
            throw r13
        L_0x007d:
            java.util.Iterator r14 = r14.iterator()
            java.lang.Object r14 = r14.next()
            org.eclipse.mat.snapshot.model.IClass r14 = (org.eclipse.mat.snapshot.model.IClass) r14
            org.eclipse.mat.parser.model.PrimitiveArrayImpl r8 = new org.eclipse.mat.parser.model.PrimitiveArrayImpl
            r4 = r14
            org.eclipse.mat.parser.model.ClassImpl r4 = (org.eclipse.mat.parser.model.ClassImpl) r4
            r0 = r8
            r1 = r13
            r5 = r10
            r6 = r11
            r0.<init>(r1, r2, r4, r5, r6)
            r8.setInfo(r7)
            return r8
        L_0x0097:
            java.io.IOException r13 = new java.io.IOException
            org.eclipse.mat.hprof.Messages r14 = org.eclipse.mat.hprof.Messages.HprofRandomAccessParser_Error_MissingClass
            java.lang.Object[] r2 = new java.lang.Object[r4]
            r2[r1] = r0
            java.lang.String r14 = org.eclipse.mat.util.MessageUtil.format(r14, r2)
            r13.<init>(r14)
            throw r13
        L_0x00a7:
            java.io.IOException r13 = new java.io.IOException
            org.eclipse.mat.hprof.Messages r14 = org.eclipse.mat.hprof.Messages.Pass1Parser_Error_IllegalType
            java.lang.String r14 = r14.pattern
            r13.<init>(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.hprof.HprofRandomAccessParser.readPrimitiveArrayDump(int, org.eclipse.mat.snapshot.ISnapshot):org.eclipse.mat.snapshot.model.IArray");
    }

    public synchronized long[] readObjectArray(Offline offline, int i, int i2) throws IOException {
        long[] jArr;
        try {
            this.in.seek(offline.getPosition() + ((long) (i * this.idSize)));
            jArr = new long[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                jArr[i3] = readID();
            }
        }
        return jArr;
    }

    public synchronized byte[] readPrimitiveArray(Offline offline, int i, int i2) throws IOException {
        byte[] bArr;
        try {
            int elementSize = offline.getElementSize();
            this.in.seek(offline.getPosition() + ((long) (i * elementSize)));
            bArr = new byte[(i2 * elementSize)];
            this.in.readFully(bArr);
        }
        return bArr;
    }
}
