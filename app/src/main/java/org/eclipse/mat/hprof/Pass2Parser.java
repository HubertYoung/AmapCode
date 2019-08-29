package org.eclipse.mat.hprof;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.hprof.IHprofParserHandler.HeapObject;
import org.eclipse.mat.parser.io.PositionInputStream;
import org.eclipse.mat.parser.model.ClassImpl;
import org.eclipse.mat.parser.model.ObjectArrayImpl;
import org.eclipse.mat.parser.model.PrimitiveArrayImpl;
import org.eclipse.mat.snapshot.model.FieldDescriptor;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IPrimitiveArray;
import org.eclipse.mat.util.IProgressListener.OperationCanceledException;
import org.eclipse.mat.util.MessageUtil;
import org.eclipse.mat.util.SimpleMonitor.Listener;

public class Pass2Parser extends AbstractParser {
    static final Set<String> ignorableClasses;
    private IHprofParserHandler handler;
    private Listener monitor;

    public Pass2Parser(IHprofParserHandler iHprofParserHandler, Listener listener) {
        this.handler = iHprofParserHandler;
        this.monitor = listener;
    }

    public void read(File file) throws SnapshotException, IOException {
        this.in = new PositionInputStream(new BufferedInputStream(new FileInputStream(file)));
        int determineDumpNumber = determineDumpNumber();
        try {
            this.version = readVersion(this.in);
            this.idSize = this.in.readInt();
            if (this.idSize == 4 || this.idSize == 8) {
                this.in.skipBytes(8);
                long length = file.length();
                long position = this.in.position();
                int i = 0;
                while (position < length) {
                    if (this.monitor.isProbablyCanceled()) {
                        throw new OperationCanceledException();
                    }
                    this.monitor.totalWorkDone(position / 1000);
                    int readUnsignedByte = this.in.readUnsignedByte();
                    this.in.skipBytes(4);
                    long readUnsignedInt = readUnsignedInt();
                    if (readUnsignedInt < 0) {
                        throw new SnapshotException(MessageUtil.format(Messages.Pass1Parser_Error_IllegalRecordLength, Long.valueOf(this.in.position())));
                    }
                    if (readUnsignedByte == 12 || readUnsignedByte == 28) {
                        if (determineDumpNumber == i) {
                            readDumpSegments(readUnsignedInt);
                        } else {
                            this.in.skipBytes(readUnsignedInt);
                        }
                        if (readUnsignedByte == 12) {
                            i++;
                        }
                    } else {
                        if (readUnsignedByte == 44) {
                            i++;
                        }
                        this.in.skipBytes(readUnsignedInt);
                    }
                    position = this.in.position();
                }
            } else {
                throw new SnapshotException(Messages.Pass1Parser_Error_SupportedDumps);
            }
        } finally {
            try {
                this.in.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0087, code lost:
        r6.in.skipBytes(r6.idSize);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readDumpSegments(long r7) throws org.eclipse.mat.SnapshotException, java.io.IOException {
        /*
            r6 = this;
            org.eclipse.mat.parser.io.PositionInputStream r0 = r6.in
            long r0 = r0.position()
            long r7 = r7 + r0
        L_0x0007:
            int r2 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r2 >= 0) goto L_0x00c0
            r2 = 1000(0x3e8, double:4.94E-321)
            long r2 = r0 / r2
            org.eclipse.mat.util.SimpleMonitor$Listener r4 = r6.monitor
            long r4 = r4.getWorkDone()
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 >= 0) goto L_0x002c
            org.eclipse.mat.util.SimpleMonitor$Listener r4 = r6.monitor
            boolean r4 = r4.isProbablyCanceled()
            if (r4 == 0) goto L_0x0027
            org.eclipse.mat.util.IProgressListener$OperationCanceledException r7 = new org.eclipse.mat.util.IProgressListener$OperationCanceledException
            r7.<init>()
            throw r7
        L_0x0027:
            org.eclipse.mat.util.SimpleMonitor$Listener r4 = r6.monitor
            r4.totalWorkDone(r2)
        L_0x002c:
            org.eclipse.mat.parser.io.PositionInputStream r2 = r6.in
            int r2 = r2.readUnsignedByte()
            r3 = 144(0x90, float:2.02E-43)
            if (r2 == r3) goto L_0x00b1
            r3 = 195(0xc3, float:2.73E-43)
            if (r2 == r3) goto L_0x00ad
            r3 = 2
            switch(r2) {
                case 1: goto L_0x00a3;
                case 2: goto L_0x0099;
                case 3: goto L_0x0099;
                case 4: goto L_0x008f;
                case 5: goto L_0x0087;
                case 6: goto L_0x008f;
                case 7: goto L_0x0087;
                case 8: goto L_0x0099;
                default: goto L_0x003e;
            }
        L_0x003e:
            switch(r2) {
                case 32: goto L_0x0083;
                case 33: goto L_0x007f;
                case 34: goto L_0x007b;
                case 35: goto L_0x0077;
                default: goto L_0x0041;
            }
        L_0x0041:
            switch(r2) {
                case 137: goto L_0x00b1;
                case 138: goto L_0x00b1;
                case 139: goto L_0x00b1;
                case 140: goto L_0x00b1;
                case 141: goto L_0x00b1;
                case 142: goto L_0x006d;
                default: goto L_0x0044;
            }
        L_0x0044:
            switch(r2) {
                case 254: goto L_0x0063;
                case 255: goto L_0x0087;
                default: goto L_0x0047;
            }
        L_0x0047:
            org.eclipse.mat.SnapshotException r7 = new org.eclipse.mat.SnapshotException
            org.eclipse.mat.hprof.Messages r8 = org.eclipse.mat.hprof.Messages.Pass1Parser_Error_InvalidHeapDumpFile
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3[r4] = r2
            r2 = 1
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            r3[r2] = r0
            java.lang.String r8 = org.eclipse.mat.util.MessageUtil.format(r8, r3)
            r7.<init>(r8)
            throw r7
        L_0x0063:
            org.eclipse.mat.parser.io.PositionInputStream r0 = r6.in
            int r1 = r6.idSize
            int r1 = r1 + 4
            r0.skipBytes(r1)
            goto L_0x00b8
        L_0x006d:
            org.eclipse.mat.parser.io.PositionInputStream r0 = r6.in
            int r1 = r6.idSize
            int r1 = r1 + 8
            r0.skipBytes(r1)
            goto L_0x00b8
        L_0x0077:
            r6.readPrimitiveArrayDump(r0)
            goto L_0x00b8
        L_0x007b:
            r6.readObjectArrayDump(r0)
            goto L_0x00b8
        L_0x007f:
            r6.readInstanceDump(r0)
            goto L_0x00b8
        L_0x0083:
            r6.skipClassDump()
            goto L_0x00b8
        L_0x0087:
            org.eclipse.mat.parser.io.PositionInputStream r0 = r6.in
            int r1 = r6.idSize
            r0.skipBytes(r1)
            goto L_0x00b8
        L_0x008f:
            org.eclipse.mat.parser.io.PositionInputStream r0 = r6.in
            int r1 = r6.idSize
            int r1 = r1 + 4
            r0.skipBytes(r1)
            goto L_0x00b8
        L_0x0099:
            org.eclipse.mat.parser.io.PositionInputStream r0 = r6.in
            int r1 = r6.idSize
            int r1 = r1 + 8
            r0.skipBytes(r1)
            goto L_0x00b8
        L_0x00a3:
            org.eclipse.mat.parser.io.PositionInputStream r0 = r6.in
            int r1 = r6.idSize
            int r1 = r1 * 2
            r0.skipBytes(r1)
            goto L_0x00b8
        L_0x00ad:
            r6.readPrimitiveArrayNoDataDump(r0)
            goto L_0x00b8
        L_0x00b1:
            org.eclipse.mat.parser.io.PositionInputStream r0 = r6.in
            int r1 = r6.idSize
            r0.skipBytes(r1)
        L_0x00b8:
            org.eclipse.mat.parser.io.PositionInputStream r0 = r6.in
            long r0 = r0.position()
            goto L_0x0007
        L_0x00c0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.hprof.Pass2Parser.readDumpSegments(long):void");
    }

    private void skipClassDump() throws IOException {
        this.in.skipBytes((this.idSize * 7) + 8);
        int readUnsignedShort = this.in.readUnsignedShort();
        for (int i = 0; i < readUnsignedShort; i++) {
            this.in.skipBytes(2);
            skipValue();
        }
        int readUnsignedShort2 = this.in.readUnsignedShort();
        for (int i2 = 0; i2 < readUnsignedShort2; i2++) {
            this.in.skipBytes(this.idSize);
            skipValue();
        }
        this.in.skipBytes((this.idSize + 1) * this.in.readUnsignedShort());
    }

    static {
        HashSet hashSet = new HashSet();
        ignorableClasses = hashSet;
        hashSet.add(WeakReference.class.getName());
        ignorableClasses.add(SoftReference.class.getName());
        ignorableClasses.add(PhantomReference.class.getName());
        ignorableClasses.add("java.lang.ref.Finalizer");
        ignorableClasses.add("java.lang.ref.FinalizerReference");
    }

    private void readInstanceDump(long j) throws IOException {
        boolean z;
        long readID = readID();
        this.in.skipBytes(4);
        long readID2 = readID();
        long position = this.in.position() + ((long) this.in.readInt());
        List<IClass> resolveClassHierarchy = this.handler.resolveClassHierarchy(readID2);
        ClassImpl classImpl = (ClassImpl) resolveClassHierarchy.get(0);
        HeapObject heapObject = new HeapObject(this.handler.mapAddressToId(readID), readID, classImpl, classImpl.getHeapSizePerInstance());
        heapObject.references.add(classImpl.getObjectAddress());
        Iterator<IClass> it = resolveClassHierarchy.iterator();
        while (true) {
            if (it.hasNext()) {
                if (ignorableClasses.contains(it.next().getName())) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        for (IClass fieldDescriptors : resolveClassHierarchy) {
            for (FieldDescriptor next : fieldDescriptors.getFieldDescriptors()) {
                int type = next.getType();
                if (type == 2) {
                    long readID3 = readID();
                    if (readID3 != 0 && (!z || !next.getName().equals("referent"))) {
                        heapObject.references.add(readID3);
                    }
                } else {
                    skipValue(type);
                }
            }
        }
        if (position != this.in.position()) {
            throw new IOException(MessageUtil.format(Messages.Pass2Parser_Error_InsufficientBytesRead, Long.valueOf(j)));
        }
        this.handler.addObject(heapObject, j);
    }

    private void readObjectArrayDump(long j) throws IOException {
        long readID = readID();
        this.in.skipBytes(4);
        int readInt = this.in.readInt();
        long readID2 = readID();
        ClassImpl classImpl = (ClassImpl) this.handler.lookupClass(readID2);
        if (classImpl == null) {
            throw new RuntimeException(MessageUtil.format(Messages.Pass2Parser_Error_HandlerMustCreateFakeClassForAddress, Long.toHexString(readID2)));
        }
        HeapObject heapObject = new HeapObject(this.handler.mapAddressToId(readID), readID, classImpl, ObjectArrayImpl.doGetUsedHeapSize(classImpl, readInt));
        heapObject.references.add(classImpl.getObjectAddress());
        heapObject.isArray = true;
        for (int i = 0; i < readInt; i++) {
            long readID3 = readID();
            if (readID3 != 0) {
                heapObject.references.add(readID3);
            }
        }
        this.handler.addObject(heapObject, j);
    }

    private void readPrimitiveArrayDump(long j) throws SnapshotException, IOException {
        long readID = readID();
        this.in.skipBytes(4);
        int readInt = this.in.readInt();
        byte readByte = this.in.readByte();
        if (readByte < 4 || readByte > 11) {
            throw new SnapshotException(Messages.Pass1Parser_Error_IllegalType);
        }
        String str = IPrimitiveArray.TYPE[readByte];
        ClassImpl classImpl = (ClassImpl) this.handler.lookupClassByName(str, true);
        if (classImpl == null) {
            throw new RuntimeException(MessageUtil.format(Messages.Pass2Parser_Error_HandleMustCreateFakeClassForName, str));
        }
        HeapObject heapObject = new HeapObject(this.handler.mapAddressToId(readID), readID, classImpl, PrimitiveArrayImpl.doGetUsedHeapSize(classImpl, readInt, readByte));
        heapObject.references.add(classImpl.getObjectAddress());
        heapObject.isArray = true;
        this.handler.addObject(heapObject, j);
        this.in.skipBytes(IPrimitiveArray.ELEMENT_SIZE[readByte] * readInt);
    }

    private void readPrimitiveArrayNoDataDump(long j) throws SnapshotException, IOException {
        long readID = readID();
        this.in.skipBytes(4);
        int readInt = this.in.readInt();
        byte readByte = this.in.readByte();
        if (readByte < 4 || readByte > 11) {
            throw new SnapshotException(Messages.Pass1Parser_Error_IllegalType);
        }
        String str = IPrimitiveArray.TYPE[readByte];
        ClassImpl classImpl = (ClassImpl) this.handler.lookupClassByName(str, true);
        if (classImpl == null) {
            throw new RuntimeException(MessageUtil.format(Messages.Pass2Parser_Error_HandleMustCreateFakeClassForName, str));
        }
        HeapObject heapObject = new HeapObject(this.handler.mapAddressToId(readID), readID, classImpl, PrimitiveArrayImpl.doGetUsedHeapSize(classImpl, readInt, readByte));
        heapObject.references.add(classImpl.getObjectAddress());
        heapObject.isArray = true;
        this.handler.addObject(heapObject, j);
    }
}
