package org.eclipse.mat.hprof;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.HashMapLongObject;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;
import org.eclipse.mat.parser.io.PositionInputStream;
import org.eclipse.mat.parser.model.ClassImpl;
import org.eclipse.mat.snapshot.model.Field;
import org.eclipse.mat.snapshot.model.FieldDescriptor;
import org.eclipse.mat.snapshot.model.IPrimitiveArray;
import org.eclipse.mat.util.IProgressListener.OperationCanceledException;
import org.eclipse.mat.util.IProgressListener.Severity;
import org.eclipse.mat.util.MessageUtil;
import org.eclipse.mat.util.SimpleMonitor.Listener;

public class Pass1Parser extends AbstractParser {
    private static final Pattern PATTERN_OBJ_ARRAY = Pattern.compile("^(\\[+)L(.*);$");
    private static final Pattern PATTERN_PRIMITIVE_ARRAY = Pattern.compile("^(\\[+)(.)$");
    /* access modifiers changed from: private */
    public HashMapLongObject<String> class2name = new HashMapLongObject<>();
    /* access modifiers changed from: private */
    public HashMapLongObject<Long> classSerNum2id = new HashMapLongObject<>();
    private IHprofParserHandler handler;
    /* access modifiers changed from: private */
    public HashMapLongObject<StackFrame> id2frame = new HashMapLongObject<>();
    private Listener monitor;
    private HashMapLongObject<StackTrace> serNum2stackTrace = new HashMapLongObject<>();
    private HashMapLongObject<Long> thread2id = new HashMapLongObject<>();
    private HashMapLongObject<List<JavaLocal>> thread2locals = new HashMapLongObject<>();

    static class JavaLocal {
        final int lineNumber;
        final long objectId;
        final int type;

        public JavaLocal(long j, int i, int i2) {
            this.objectId = j;
            this.lineNumber = i;
            this.type = i2;
        }

        public int getType() {
            return this.type;
        }
    }

    class StackFrame {
        final long classSerNum;
        final long frameId;
        final int lineNr;
        final String method;
        final String methodSignature;
        final String sourceFile;

        public StackFrame(long j, int i, String str, String str2, String str3, long j2) {
            this.frameId = j;
            this.lineNr = i;
            this.method = str;
            this.methodSignature = str2;
            this.sourceFile = str3;
            this.classSerNum = j2;
        }

        public String toString() {
            String str;
            Long l = (Long) Pass1Parser.this.classSerNum2id.get(this.classSerNum);
            if (l == null) {
                str = "<UNKNOWN CLASS>";
            } else {
                str = (String) Pass1Parser.this.class2name.get(l.longValue());
            }
            String str2 = "";
            if (this.lineNr > 0) {
                StringBuilder sb = new StringBuilder("(");
                sb.append(this.sourceFile);
                sb.append(":");
                sb.append(String.valueOf(this.lineNr));
                sb.append(")");
                str2 = sb.toString();
            } else if (this.lineNr == 0 || this.lineNr == -1) {
                str2 = "(Unknown Source)";
            } else if (this.lineNr == -2) {
                str2 = "(Compiled method)";
            } else if (this.lineNr == -3) {
                str2 = "(Native Method)";
            }
            StringBuilder sb2 = new StringBuilder("  at ");
            sb2.append(str);
            sb2.append(".");
            sb2.append(this.method);
            sb2.append(this.methodSignature);
            sb2.append(Token.SEPARATOR);
            sb2.append(str2);
            return sb2.toString();
        }
    }

    class StackTrace {
        final long[] frameIds;
        final long threadSerialNr;

        public StackTrace(long j, long j2, long[] jArr) {
            this.threadSerialNr = j2;
            this.frameIds = jArr;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (long j : this.frameIds) {
                StackFrame stackFrame = (StackFrame) Pass1Parser.this.id2frame.get(j);
                if (stackFrame != null) {
                    sb.append(stackFrame);
                    sb.append(MultipartUtility.LINE_FEED);
                }
            }
            return sb.toString();
        }
    }

    public Pass1Parser(IHprofParserHandler iHprofParserHandler, Listener listener) {
        this.handler = iHprofParserHandler;
        this.monitor = listener;
    }

    public void read(File file) throws SnapshotException, IOException {
        this.in = new PositionInputStream(new BufferedInputStream(new FileInputStream(file)));
        int determineDumpNumber = determineDumpNumber();
        try {
            this.version = readVersion(this.in);
            this.handler.addProperty(IHprofParserHandler.VERSION, this.version.toString());
            this.idSize = this.in.readInt();
            int i = 4;
            if (this.idSize == 4 || this.idSize == 8) {
                this.handler.addProperty(IHprofParserHandler.IDENTIFIER_SIZE, String.valueOf(this.idSize));
                this.handler.addProperty(IHprofParserHandler.CREATION_DATE, String.valueOf(this.in.readLong()));
                long length = file.length();
                long position = this.in.position();
                char c = 0;
                int i2 = 0;
                while (position < length) {
                    if (this.monitor.isProbablyCanceled()) {
                        throw new OperationCanceledException();
                    }
                    this.monitor.totalWorkDone(position / 1000);
                    int readUnsignedByte = this.in.readUnsignedByte();
                    this.in.skipBytes(i);
                    long readUnsignedInt = readUnsignedInt();
                    if (readUnsignedInt < 0) {
                        Messages messages = Messages.Pass1Parser_Error_IllegalRecordLength;
                        Object[] objArr = new Object[1];
                        objArr[c] = Long.valueOf(this.in.position());
                        throw new SnapshotException(MessageUtil.format(messages, objArr));
                    }
                    if ((position + readUnsignedInt) - 9 > length) {
                        this.monitor.sendUserMessage(Severity.WARNING, MessageUtil.format(Messages.Pass1Parser_Error_invalidHPROFFile, Long.valueOf(readUnsignedInt), Long.valueOf((length - position) - 9)), null);
                    }
                    switch (readUnsignedByte) {
                        case 1:
                            readString(readUnsignedInt);
                            break;
                        case 2:
                            readLoadClass();
                            break;
                        case 4:
                            readStackFrame(readUnsignedInt);
                            break;
                        case 5:
                            readStackTrace(readUnsignedInt);
                            break;
                        case 12:
                        case 28:
                            if (determineDumpNumber == i2) {
                                readDumpSegments(readUnsignedInt);
                            } else {
                                this.in.skipBytes(readUnsignedInt);
                            }
                            if (readUnsignedByte == 12) {
                                i2++;
                                break;
                            }
                            break;
                        case 44:
                            i2++;
                            break;
                    }
                    this.in.skipBytes(readUnsignedInt);
                    position = this.in.position();
                    c = 0;
                    i = 4;
                }
                try {
                    this.in.close();
                } catch (IOException unused) {
                }
                if (i2 <= determineDumpNumber) {
                    throw new SnapshotException(MessageUtil.format(Messages.Pass1Parser_Error_NoHeapDumpIndexFound, Integer.valueOf(i2), file.getName(), Integer.valueOf(determineDumpNumber)));
                }
                if (i2 > 1) {
                    this.monitor.sendUserMessage(Severity.INFO, MessageUtil.format(Messages.Pass1Parser_Info_UsingDumpIndex, Integer.valueOf(i2), file.getName(), Integer.valueOf(determineDumpNumber)), null);
                }
                if (this.serNum2stackTrace.size() > 0) {
                    dumpThreads();
                    return;
                }
                return;
            }
            throw new SnapshotException(Messages.Pass1Parser_Error_SupportedDumps);
        } catch (Throwable th) {
            Throwable th2 = th;
            try {
                this.in.close();
            } catch (IOException unused2) {
            }
            throw th2;
        }
    }

    private void readString(long j) throws IOException {
        long readID = readID();
        byte[] bArr = new byte[((int) (j - ((long) this.idSize)))];
        this.in.readFully(bArr);
        this.handler.getConstantPool().put(readID, new String(bArr));
    }

    private void readLoadClass() throws IOException {
        long readUnsignedInt = readUnsignedInt();
        long readID = readID();
        this.in.skipBytes(4);
        this.class2name.put(readID, getStringConstant(readID()).replace('/', DjangoUtils.EXTENSION_SEPARATOR));
        this.classSerNum2id.put(readUnsignedInt, Long.valueOf(readID));
    }

    private void readStackFrame(long j) throws IOException {
        long readID = readID();
        long readID2 = readID();
        long readID3 = readID();
        long readID4 = readID();
        long readUnsignedInt = readUnsignedInt();
        StackFrame stackFrame = new StackFrame(readID, this.in.readInt(), getStringConstant(readID2), getStringConstant(readID3), getStringConstant(readID4), readUnsignedInt);
        this.id2frame.put(readID, stackFrame);
    }

    private void readStackTrace(long j) throws IOException {
        long readUnsignedInt = readUnsignedInt();
        long readUnsignedInt2 = readUnsignedInt();
        long readUnsignedInt3 = readUnsignedInt();
        long[] jArr = new long[((int) readUnsignedInt3)];
        for (int i = 0; ((long) i) < readUnsignedInt3; i++) {
            jArr[i] = readID();
        }
        StackTrace stackTrace = new StackTrace(readUnsignedInt, readUnsignedInt2, jArr);
        this.serNum2stackTrace.put(readUnsignedInt, stackTrace);
    }

    private void readDumpSegments(long j) throws IOException, SnapshotException {
        long position = this.in.position();
        long j2 = j + position;
        while (position < j2) {
            long j3 = position / 1000;
            if (this.monitor.getWorkDone() < j3) {
                if (this.monitor.isProbablyCanceled()) {
                    throw new OperationCanceledException();
                }
                this.monitor.totalWorkDone(j3);
            }
            int readUnsignedByte = this.in.readUnsignedByte();
            if (readUnsignedByte == 144) {
                readGC(1, 0);
            } else if (readUnsignedByte != 195) {
                switch (readUnsignedByte) {
                    case 1:
                        readGC(128, this.idSize);
                        break;
                    case 2:
                        readGCWithThreadContext(4, true);
                        break;
                    case 3:
                        readGCWithThreadContext(64, true);
                        break;
                    case 4:
                        readGCWithThreadContext(128, false);
                        break;
                    case 5:
                        readGC(2, 0);
                        break;
                    case 6:
                        readGC(16, 4);
                        break;
                    case 7:
                        readGC(32, 0);
                        break;
                    case 8:
                        readGCThreadObject(256);
                        break;
                    default:
                        switch (readUnsignedByte) {
                            case 32:
                                readClassDump(position);
                                break;
                            case 33:
                                readInstanceDump(position);
                                break;
                            case 34:
                                readObjectArrayDump(position);
                                break;
                            case 35:
                                readPrimitiveArrayDump(position);
                                break;
                            default:
                                switch (readUnsignedByte) {
                                    case DumpSegment.ANDROID_ROOT_INTERNED_STRING /*137*/:
                                        readGC(1, 0);
                                        break;
                                    case DumpSegment.ANDROID_ROOT_FINALIZING /*138*/:
                                        readGC(1, 0);
                                        break;
                                    case DumpSegment.ANDROID_ROOT_DEBUGGER /*139*/:
                                        readGC(1, 0);
                                        break;
                                    case 140:
                                        readGC(1, 0);
                                        break;
                                    case DumpSegment.ANDROID_ROOT_VM_INTERNAL /*141*/:
                                        readGC(1, 0);
                                        break;
                                    case DumpSegment.ANDROID_ROOT_JNI_MONITOR /*142*/:
                                        readGC(1, 8);
                                        break;
                                    default:
                                        switch (readUnsignedByte) {
                                            case DumpSegment.ANDROID_HEAP_DUMP_INFO /*254*/:
                                                this.in.skipBytes(this.idSize + 4);
                                                break;
                                            case 255:
                                                readGC(1, 0);
                                                break;
                                            default:
                                                throw new SnapshotException(MessageUtil.format(Messages.Pass1Parser_Error_InvalidHeapDumpFile, Integer.valueOf(readUnsignedByte), Long.valueOf(position)));
                                        }
                                }
                        }
                }
            } else {
                readPrimitiveArrayNoDataDump(position);
            }
            position = this.in.position();
        }
    }

    private void readGCThreadObject(int i) throws IOException {
        long readID = readID();
        this.thread2id.put((long) this.in.readInt(), Long.valueOf(readID));
        this.handler.addGCRoot(readID, 0, i);
        this.in.skipBytes(4);
    }

    private void readGC(int i, int i2) throws IOException {
        this.handler.addGCRoot(readID(), 0, i);
        if (i2 > 0) {
            this.in.skipBytes(i2);
        }
    }

    private void readGCWithThreadContext(int i, boolean z) throws IOException {
        long readID = readID();
        long readInt = (long) this.in.readInt();
        Long l = (Long) this.thread2id.get(readInt);
        if (l != null) {
            this.handler.addGCRoot(readID, l.longValue(), i);
        } else {
            this.handler.addGCRoot(readID, 0, i);
        }
        if (z) {
            int readInt2 = this.in.readInt();
            List list = (List) this.thread2locals.get(readInt);
            if (list == null) {
                list = new ArrayList();
                this.thread2locals.put(readInt, list);
            }
            list.add(new JavaLocal(readID, readInt2, i));
        }
    }

    private void readClassDump(long j) throws IOException {
        String str;
        String str2;
        long readID = readID();
        this.in.skipBytes(4);
        long readID2 = readID();
        long readID3 = readID();
        this.in.skipBytes((this.idSize * 4) + 4);
        int readUnsignedShort = this.in.readUnsignedShort();
        for (int i = 0; i < readUnsignedShort; i++) {
            this.in.skipBytes(2);
            skipValue();
        }
        int readUnsignedShort2 = this.in.readUnsignedShort();
        Field[] fieldArr = new Field[readUnsignedShort2];
        for (int i2 = 0; i2 < readUnsignedShort2; i2++) {
            String stringConstant = getStringConstant(readID());
            byte readByte = this.in.readByte();
            fieldArr[i2] = new Field(stringConstant, readByte, readValue(null, readByte));
        }
        int readUnsignedShort3 = this.in.readUnsignedShort();
        FieldDescriptor[] fieldDescriptorArr = new FieldDescriptor[readUnsignedShort3];
        for (int i3 = 0; i3 < readUnsignedShort3; i3++) {
            fieldDescriptorArr[i3] = new FieldDescriptor(getStringConstant(readID()), this.in.readByte());
        }
        String str3 = (String) this.class2name.get(readID);
        if (str3 == null) {
            StringBuilder sb = new StringBuilder("unknown-name@0x");
            sb.append(Long.toHexString(readID));
            str3 = sb.toString();
        }
        if (str3.charAt(0) == '[') {
            Matcher matcher = PATTERN_OBJ_ARRAY.matcher(str3);
            if (matcher.matches()) {
                int length = matcher.group(1).length();
                str2 = matcher.group(2);
                for (int i4 = 0; i4 < length; i4++) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append("[]");
                    str2 = sb2.toString();
                }
            } else {
                str2 = str3;
            }
            Matcher matcher2 = PATTERN_PRIMITIVE_ARRAY.matcher(str2);
            if (matcher2.matches()) {
                int length2 = matcher2.group(1).length() - 1;
                String str4 = "unknown[]";
                char charAt = matcher2.group(2).charAt(0);
                int i5 = 0;
                while (true) {
                    if (i5 >= IPrimitiveArray.SIGNATURES.length) {
                        break;
                    } else if (IPrimitiveArray.SIGNATURES[i5] == ((byte) charAt)) {
                        str4 = IPrimitiveArray.TYPE[i5];
                        break;
                    } else {
                        i5++;
                    }
                }
                for (int i6 = 0; i6 < length2; i6++) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str2);
                    sb3.append("[]");
                    str4 = sb3.toString();
                }
            }
            str = str2;
        } else {
            str = str3;
        }
        ClassImpl classImpl = new ClassImpl(readID, str, readID2, readID3, fieldArr, fieldDescriptorArr);
        this.handler.addClass(classImpl, j);
    }

    private void readInstanceDump(long j) throws IOException {
        this.handler.reportInstance(readID(), j);
        this.in.skipBytes(this.idSize + 4);
        this.in.skipBytes(this.in.readInt());
    }

    private void readObjectArrayDump(long j) throws IOException {
        this.handler.reportInstance(readID(), j);
        this.in.skipBytes(4);
        int readInt = this.in.readInt();
        long readID = readID();
        if (this.handler.lookupClass(readID) == null) {
            this.handler.reportRequiredObjectArray(readID);
        }
        this.in.skipBytes(readInt * this.idSize);
    }

    private void readPrimitiveArrayDump(long j) throws SnapshotException, IOException {
        this.handler.reportInstance(readID(), j);
        this.in.skipBytes(4);
        int readInt = this.in.readInt();
        byte readByte = this.in.readByte();
        if (readByte < 4 || readByte > 11) {
            throw new SnapshotException(Messages.Pass1Parser_Error_IllegalType);
        }
        if (this.handler.lookupClassByName(IPrimitiveArray.TYPE[readByte], true) == null) {
            this.handler.reportRequiredPrimitiveArray(readByte);
        }
        this.in.skipBytes(IPrimitiveArray.ELEMENT_SIZE[readByte] * readInt);
    }

    private void readPrimitiveArrayNoDataDump(long j) throws SnapshotException, IOException {
        this.handler.reportInstance(readID(), j);
        this.in.skipBytes(4);
        this.in.readInt();
        byte readByte = this.in.readByte();
        if (readByte < 4 || readByte > 11) {
            throw new SnapshotException(Messages.Pass1Parser_Error_IllegalType);
        }
        if (this.handler.lookupClassByName(IPrimitiveArray.TYPE[readByte], true) == null) {
            this.handler.reportRequiredPrimitiveArray(readByte);
        }
    }

    private String getStringConstant(long j) {
        if (j == 0) {
            return "";
        }
        String str = (String) this.handler.getConstantPool().get(j);
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Messages.Pass1Parser_Error_UnresolvedName);
        sb.append(Long.toHexString(j));
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00f9 A[SYNTHETIC, Splitter:B:37:0x00f9] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00fd A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0100 A[SYNTHETIC, Splitter:B:43:0x0100] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void dumpThreads() {
        /*
            r11 = this;
            org.eclipse.mat.collect.HashMapLongObject<org.eclipse.mat.hprof.Pass1Parser$StackTrace> r0 = r11.serNum2stackTrace
            if (r0 == 0) goto L_0x0104
            org.eclipse.mat.collect.HashMapLongObject<org.eclipse.mat.hprof.Pass1Parser$StackTrace> r0 = r11.serNum2stackTrace
            int r0 = r0.size()
            r1 = 1
            if (r0 > r1) goto L_0x000f
            goto L_0x0104
        L_0x000f:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            org.eclipse.mat.hprof.IHprofParserHandler r2 = r11.handler
            org.eclipse.mat.parser.model.XSnapshotInfo r2 = r2.getSnapshotInfo()
            java.lang.String r2 = r2.getPrefix()
            r0.append(r2)
            java.lang.String r2 = "threads"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            r2 = 0
            r3 = 0
            java.io.PrintWriter r4 = new java.io.PrintWriter     // Catch:{ IOException -> 0x00e7 }
            java.io.FileWriter r5 = new java.io.FileWriter     // Catch:{ IOException -> 0x00e7 }
            r5.<init>(r0)     // Catch:{ IOException -> 0x00e7 }
            r4.<init>(r5)     // Catch:{ IOException -> 0x00e7 }
            org.eclipse.mat.collect.HashMapLongObject<org.eclipse.mat.hprof.Pass1Parser$StackTrace> r5 = r11.serNum2stackTrace     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.util.Iterator r5 = r5.values()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
        L_0x003c:
            boolean r6 = r5.hasNext()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            if (r6 == 0) goto L_0x00c7
            java.lang.Object r6 = r5.next()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            org.eclipse.mat.hprof.Pass1Parser$StackTrace r6 = (org.eclipse.mat.hprof.Pass1Parser.StackTrace) r6     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            org.eclipse.mat.collect.HashMapLongObject<java.lang.Long> r7 = r11.thread2id     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            long r8 = r6.threadSerialNr     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.Object r7 = r7.get(r8)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            if (r7 == 0) goto L_0x003c
            if (r7 != 0) goto L_0x0059
            java.lang.String r7 = "<unknown>"
            goto L_0x006f
        L_0x0059:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.String r9 = "0x"
            r8.<init>(r9)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            long r9 = r7.longValue()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.String r7 = java.lang.Long.toHexString(r9)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            r8.append(r7)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.String r7 = r8.toString()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
        L_0x006f:
            java.lang.String r8 = "Thread "
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.String r7 = r8.concat(r7)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            r4.println(r7)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            r4.println(r6)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.String r7 = "  locals:"
            r4.println(r7)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            org.eclipse.mat.collect.HashMapLongObject<java.util.List<org.eclipse.mat.hprof.Pass1Parser$JavaLocal>> r7 = r11.thread2locals     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            long r8 = r6.threadSerialNr     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.Object r6 = r7.get(r8)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.util.List r6 = (java.util.List) r6     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            if (r6 == 0) goto L_0x00c2
            java.util.Iterator r6 = r6.iterator()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
        L_0x0094:
            boolean r7 = r6.hasNext()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            if (r7 == 0) goto L_0x00c2
            java.lang.Object r7 = r6.next()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            org.eclipse.mat.hprof.Pass1Parser$JavaLocal r7 = (org.eclipse.mat.hprof.Pass1Parser.JavaLocal) r7     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.String r9 = "    objecId=0x"
            r8.<init>(r9)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            long r9 = r7.objectId     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.String r9 = java.lang.Long.toHexString(r9)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            r8.append(r9)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.String r9 = ", line="
            r8.append(r9)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            int r7 = r7.lineNumber     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            r8.append(r7)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.String r7 = r8.toString()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            r4.println(r7)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            goto L_0x0094
        L_0x00c2:
            r4.println()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            goto L_0x003c
        L_0x00c7:
            r4.flush()     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            org.eclipse.mat.util.SimpleMonitor$Listener r5 = r11.monitor     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            org.eclipse.mat.util.IProgressListener$Severity r6 = org.eclipse.mat.util.IProgressListener.Severity.INFO     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            org.eclipse.mat.hprof.Messages r7 = org.eclipse.mat.hprof.Messages.Pass1Parser_Info_WroteThreadsTo     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            r1[r2] = r0     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            java.lang.String r0 = org.eclipse.mat.util.MessageUtil.format(r7, r1)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            r5.sendUserMessage(r6, r0, r3)     // Catch:{ IOException -> 0x00e1, all -> 0x00df }
            r4.close()     // Catch:{ Exception -> 0x00de }
        L_0x00de:
            return
        L_0x00df:
            r0 = move-exception
            goto L_0x00fe
        L_0x00e1:
            r0 = move-exception
            r3 = r4
            goto L_0x00e8
        L_0x00e4:
            r0 = move-exception
            r4 = r3
            goto L_0x00fe
        L_0x00e7:
            r0 = move-exception
        L_0x00e8:
            org.eclipse.mat.util.SimpleMonitor$Listener r1 = r11.monitor     // Catch:{ all -> 0x00e4 }
            org.eclipse.mat.util.IProgressListener$Severity r4 = org.eclipse.mat.util.IProgressListener.Severity.WARNING     // Catch:{ all -> 0x00e4 }
            org.eclipse.mat.hprof.Messages r5 = org.eclipse.mat.hprof.Messages.Pass1Parser_Error_WritingThreadsInformation     // Catch:{ all -> 0x00e4 }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x00e4 }
            java.lang.String r2 = org.eclipse.mat.util.MessageUtil.format(r5, r2)     // Catch:{ all -> 0x00e4 }
            r1.sendUserMessage(r4, r2, r0)     // Catch:{ all -> 0x00e4 }
            if (r3 == 0) goto L_0x00fd
            r3.close()     // Catch:{ Exception -> 0x00fc }
        L_0x00fc:
            return
        L_0x00fd:
            return
        L_0x00fe:
            if (r4 == 0) goto L_0x0103
            r4.close()     // Catch:{ Exception -> 0x0103 }
        L_0x0103:
            throw r0
        L_0x0104:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.hprof.Pass1Parser.dumpThreads():void");
    }
}
