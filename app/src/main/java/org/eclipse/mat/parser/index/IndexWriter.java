package org.eclipse.mat.parser.index;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.eclipse.mat.collect.ArrayInt;
import org.eclipse.mat.collect.ArrayIntCompressed;
import org.eclipse.mat.collect.ArrayLong;
import org.eclipse.mat.collect.ArrayLongCompressed;
import org.eclipse.mat.collect.ArrayUtils;
import org.eclipse.mat.collect.HashMapIntLong;
import org.eclipse.mat.collect.HashMapIntObject;
import org.eclipse.mat.collect.IteratorInt;
import org.eclipse.mat.collect.IteratorLong;
import org.eclipse.mat.parser.index.IIndexReader.IOne2LongIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2ManyIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2OneIndex;
import org.eclipse.mat.parser.index.IndexReader.IntIndex1NSortedReader;
import org.eclipse.mat.parser.index.IndexReader.IntIndexReader;
import org.eclipse.mat.parser.index.IndexReader.LongIndexReader;
import org.eclipse.mat.parser.io.BitOutputStream;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.IProgressListener.OperationCanceledException;

public abstract class IndexWriter {
    public static final int PAGE_SIZE_INT = 1000000;
    public static final int PAGE_SIZE_LONG = 500000;

    public static class Identifier implements IOne2LongIndex {
        long[] identifiers;
        int size;

        public void close() throws IOException {
        }

        public void add(long j) {
            if (this.identifiers == null) {
                this.identifiers = new long[10000];
                this.size = 0;
            }
            if (this.size + 1 > this.identifiers.length) {
                int length = ((this.identifiers.length * 3) / 2) + 1;
                if (length < this.size + 1) {
                    length = this.size + 1;
                }
                this.identifiers = IndexWriter.copyOf(this.identifiers, length);
            }
            long[] jArr = this.identifiers;
            int i = this.size;
            this.size = i + 1;
            jArr[i] = j;
        }

        public void sort() {
            Arrays.sort(this.identifiers, 0, this.size);
        }

        public int size() {
            return this.size;
        }

        public long get(int i) {
            if (i >= 0 && i < this.size) {
                return this.identifiers[i];
            }
            throw new IndexOutOfBoundsException();
        }

        public int reverse(long j) {
            int i = this.size;
            int i2 = 0;
            while (i2 < i) {
                int i3 = (i2 + i) >>> 1;
                long j2 = get(i3);
                if (j < j2) {
                    i = i3;
                } else if (j2 >= j) {
                    return i3;
                } else {
                    i2 = i3 + 1;
                }
            }
            return -1 - i2;
        }

        public IteratorLong iterator() {
            return new IteratorLong() {
                int index = 0;

                public boolean hasNext() {
                    return this.index < Identifier.this.size;
                }

                public long next() {
                    long[] jArr = Identifier.this.identifiers;
                    int i = this.index;
                    this.index = i + 1;
                    return jArr[i];
                }
            };
        }

        public long[] getNext(int i, int i2) {
            long[] jArr = new long[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                jArr[i3] = this.identifiers[i + i3];
            }
            return jArr;
        }

        public void delete() {
            this.identifiers = null;
        }

        public void unload() throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    public static class InboundWriter {
        int bitLength;
        File indexFile;
        int pageSize;
        int[] segmentSizes;
        BitOutputStream[] segments;
        int size;

        public InboundWriter(int i, File file) throws IOException {
            this.size = i;
            this.indexFile = file;
            int i2 = 1;
            while (i2 < (i / 500000) + 1) {
                i2 <<= 1;
            }
            this.bitLength = IndexWriter.mostSignificantBit(i) + 1;
            this.pageSize = (i / i2) + 1;
            this.segments = new BitOutputStream[i2];
            this.segmentSizes = new int[i2];
        }

        public void log(int i, int i2, boolean z) throws IOException {
            int i3 = i / this.pageSize;
            if (this.segments[i3] == null) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.indexFile.getAbsolutePath());
                sb.append(i3);
                sb.append(".log");
                this.segments[i3] = new BitOutputStream(new FileOutputStream(new File(sb.toString())));
            }
            this.segments[i3].writeBit(z ? 1 : 0);
            this.segments[i3].writeInt(i, this.bitLength);
            this.segments[i3].writeInt(i2, this.bitLength);
            int[] iArr = this.segmentSizes;
            iArr[i3] = iArr[i3] + 1;
        }

        /* JADX WARNING: Removed duplicated region for block: B:45:0x00f1 A[SYNTHETIC, Splitter:B:45:0x00f1] */
        /* JADX WARNING: Removed duplicated region for block: B:49:0x00f6 A[SYNTHETIC, Splitter:B:49:0x00f6] */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x00ff  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public org.eclipse.mat.parser.index.IIndexReader.IOne2ManyObjectsIndex flush(org.eclipse.mat.util.IProgressListener r14, org.eclipse.mat.parser.index.IndexWriter.KeyWriter r15) throws java.io.IOException {
            /*
                r13 = this;
                r13.close()
                int r0 = r13.size
                int[] r0 = new int[r0]
                java.io.DataOutputStream r8 = new java.io.DataOutputStream
                java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream
                java.io.FileOutputStream r2 = new java.io.FileOutputStream
                java.io.File r3 = r13.indexFile
                r2.<init>(r3)
                r3 = 262144(0x40000, float:3.67342E-40)
                r1.<init>(r2, r3)
                r8.<init>(r1)
                r9 = 0
                org.eclipse.mat.parser.index.IndexWriter$IntIndexStreamer r10 = new org.eclipse.mat.parser.index.IndexWriter$IntIndexStreamer     // Catch:{ all -> 0x00ee }
                r10.<init>()     // Catch:{ all -> 0x00ee }
                r1 = 0
                r10.openStream(r8, r1)     // Catch:{ all -> 0x00ee }
                r11 = 0
                r12 = 0
            L_0x0027:
                org.eclipse.mat.parser.io.BitOutputStream[] r1 = r13.segments     // Catch:{ all -> 0x00ee }
                int r1 = r1.length     // Catch:{ all -> 0x00ee }
                if (r12 >= r1) goto L_0x00c0
                boolean r1 = r14.isCanceled()     // Catch:{ all -> 0x00ee }
                if (r1 == 0) goto L_0x0038
                org.eclipse.mat.util.IProgressListener$OperationCanceledException r15 = new org.eclipse.mat.util.IProgressListener$OperationCanceledException     // Catch:{ all -> 0x00ee }
                r15.<init>()     // Catch:{ all -> 0x00ee }
                throw r15     // Catch:{ all -> 0x00ee }
            L_0x0038:
                java.io.File r1 = new java.io.File     // Catch:{ all -> 0x00ee }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ee }
                r2.<init>()     // Catch:{ all -> 0x00ee }
                java.io.File r3 = r13.indexFile     // Catch:{ all -> 0x00ee }
                java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x00ee }
                r2.append(r3)     // Catch:{ all -> 0x00ee }
                r2.append(r12)     // Catch:{ all -> 0x00ee }
                java.lang.String r3 = ".log"
                r2.append(r3)     // Catch:{ all -> 0x00ee }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00ee }
                r1.<init>(r2)     // Catch:{ all -> 0x00ee }
                boolean r2 = r1.exists()     // Catch:{ all -> 0x00ee }
                if (r2 == 0) goto L_0x00bc
                org.eclipse.mat.parser.io.BitInputStream r2 = new org.eclipse.mat.parser.io.BitInputStream     // Catch:{ all -> 0x00ee }
                java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ all -> 0x00ee }
                r3.<init>(r1)     // Catch:{ all -> 0x00ee }
                r2.<init>(r3)     // Catch:{ all -> 0x00ee }
                int[] r3 = r13.segmentSizes     // Catch:{ all -> 0x00b9 }
                r3 = r3[r12]     // Catch:{ all -> 0x00b9 }
                int[] r6 = new int[r3]     // Catch:{ all -> 0x00b9 }
                int[] r3 = r13.segmentSizes     // Catch:{ all -> 0x00b9 }
                r3 = r3[r12]     // Catch:{ all -> 0x00b9 }
                int[] r7 = new int[r3]     // Catch:{ all -> 0x00b9 }
                r3 = 0
            L_0x0074:
                int[] r4 = r13.segmentSizes     // Catch:{ all -> 0x00b9 }
                r4 = r4[r12]     // Catch:{ all -> 0x00b9 }
                if (r3 >= r4) goto L_0x009e
                int r4 = r2.readBit()     // Catch:{ all -> 0x00b9 }
                r5 = 1
                if (r4 != r5) goto L_0x0082
                goto L_0x0083
            L_0x0082:
                r5 = 0
            L_0x0083:
                int r4 = r13.bitLength     // Catch:{ all -> 0x00b9 }
                int r4 = r2.readInt(r4)     // Catch:{ all -> 0x00b9 }
                r6[r3] = r4     // Catch:{ all -> 0x00b9 }
                int r4 = r13.bitLength     // Catch:{ all -> 0x00b9 }
                int r4 = r2.readInt(r4)     // Catch:{ all -> 0x00b9 }
                r7[r3] = r4     // Catch:{ all -> 0x00b9 }
                if (r5 == 0) goto L_0x009b
                r4 = r7[r3]     // Catch:{ all -> 0x00b9 }
                int r4 = -1 - r4
                r7[r3] = r4     // Catch:{ all -> 0x00b9 }
            L_0x009b:
                int r3 = r3 + 1
                goto L_0x0074
            L_0x009e:
                r2.close()     // Catch:{ all -> 0x00b9 }
                boolean r2 = r14.isCanceled()     // Catch:{ all -> 0x00ee }
                if (r2 == 0) goto L_0x00ad
                org.eclipse.mat.util.IProgressListener$OperationCanceledException r15 = new org.eclipse.mat.util.IProgressListener$OperationCanceledException     // Catch:{ all -> 0x00ee }
                r15.<init>()     // Catch:{ all -> 0x00ee }
                throw r15     // Catch:{ all -> 0x00ee }
            L_0x00ad:
                r1.delete()     // Catch:{ all -> 0x00ee }
                r1 = r13
                r2 = r14
                r3 = r15
                r4 = r0
                r5 = r10
                r1.processSegment(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00ee }
                goto L_0x00bc
            L_0x00b9:
                r15 = move-exception
                r9 = r2
                goto L_0x00ef
            L_0x00bc:
                int r12 = r12 + 1
                goto L_0x0027
            L_0x00c0:
                long r1 = r10.closeStream()     // Catch:{ all -> 0x00ee }
                org.eclipse.mat.parser.index.IndexWriter$IntIndexStreamer r15 = new org.eclipse.mat.parser.index.IndexWriter$IntIndexStreamer     // Catch:{ all -> 0x00ee }
                r15.<init>()     // Catch:{ all -> 0x00ee }
                org.eclipse.mat.parser.index.IIndexReader$IOne2OneIndex r15 = r15.writeTo(r8, r1, r0)     // Catch:{ all -> 0x00ee }
                r8.writeLong(r1)     // Catch:{ all -> 0x00ee }
                r8.flush()     // Catch:{ all -> 0x00ee }
                r8.close()     // Catch:{ all -> 0x00ee }
                org.eclipse.mat.parser.index.IndexReader$InboundReader r0 = new org.eclipse.mat.parser.index.IndexReader$InboundReader     // Catch:{ all -> 0x00eb }
                java.io.File r1 = r13.indexFile     // Catch:{ all -> 0x00eb }
                org.eclipse.mat.parser.index.IndexReader$IntIndexReader r2 = r10.getReader(r9)     // Catch:{ all -> 0x00eb }
                r0.<init>(r1, r15, r2)     // Catch:{ all -> 0x00eb }
                boolean r14 = r14.isCanceled()
                if (r14 == 0) goto L_0x00ea
                r13.cancel()
            L_0x00ea:
                return r0
            L_0x00eb:
                r15 = move-exception
                r8 = r9
                goto L_0x00ef
            L_0x00ee:
                r15 = move-exception
            L_0x00ef:
                if (r8 == 0) goto L_0x00f4
                r8.close()     // Catch:{ IOException -> 0x00f4 }
            L_0x00f4:
                if (r9 == 0) goto L_0x00f9
                r9.close()     // Catch:{ IOException -> 0x00f9 }
            L_0x00f9:
                boolean r14 = r14.isCanceled()
                if (r14 == 0) goto L_0x0102
                r13.cancel()
            L_0x0102:
                throw r15
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.index.IndexWriter.InboundWriter.flush(org.eclipse.mat.util.IProgressListener, org.eclipse.mat.parser.index.IndexWriter$KeyWriter):org.eclipse.mat.parser.index.IIndexReader$IOne2ManyObjectsIndex");
        }

        private void processSegment(IProgressListener iProgressListener, KeyWriter keyWriter, int[] iArr, IntIndexStreamer intIndexStreamer, int[] iArr2, int[] iArr3) throws IOException {
            int[] iArr4 = iArr2;
            ArrayUtils.sort(iArr2, iArr3);
            int i = -1;
            int i2 = 0;
            for (int i3 = 0; i3 <= iArr4.length; i3++) {
                if (i3 == 0) {
                    i = iArr4[i3];
                } else if (i3 != iArr4.length && i == iArr4[i3]) {
                } else if (iProgressListener.isCanceled()) {
                    throw new OperationCanceledException();
                } else {
                    iArr[i] = intIndexStreamer.size() + 1;
                    processObject(keyWriter, iArr, intIndexStreamer, i, iArr3, i2, i3);
                    if (i3 < iArr4.length) {
                        i = iArr4[i3];
                    }
                }
                i2 = i3;
            }
        }

        /* JADX WARNING: type inference failed for: r9v2, types: [int[], java.io.Serializable] */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r9v2, types: [int[], java.io.Serializable]
          assigns: [int[]]
          uses: [?[int, float][], java.io.Serializable]
          mth insns count: 74
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void processObject(org.eclipse.mat.parser.index.IndexWriter.KeyWriter r7, int[] r8, org.eclipse.mat.parser.index.IndexWriter.IntIndexStreamer r9, int r10, int[] r11, int r12, int r13) throws java.io.IOException {
            /*
                r6 = this;
                java.util.Arrays.sort(r11, r12, r13)
                int r0 = r13 - r12
                r1 = 1
                r2 = 100000(0x186a0, float:1.4013E-40)
                if (r0 <= r2) goto L_0x0053
                org.eclipse.mat.collect.BitField r0 = new org.eclipse.mat.collect.BitField
                int r2 = r6.size
                r0.<init>(r2)
                r2 = r12
                r3 = r2
            L_0x0014:
                if (r2 >= r13) goto L_0x0037
                r4 = r11[r2]
                if (r4 >= 0) goto L_0x0037
                int r3 = r3 + 1
                r4 = r11[r2]
                int r4 = -r4
                int r4 = r4 - r1
                r11[r2] = r4
                r4 = r11[r2]
                boolean r4 = r0.get(r4)
                if (r4 != 0) goto L_0x0034
                r4 = r11[r2]
                r9.add(r4)
                r4 = r11[r2]
                r0.set(r4)
            L_0x0034:
                int r2 = r2 + 1
                goto L_0x0014
            L_0x0037:
                if (r2 >= r13) goto L_0x0094
                if (r2 == r12) goto L_0x0043
                int r4 = r2 + -1
                r4 = r11[r4]
                r5 = r11[r2]
                if (r4 == r5) goto L_0x0050
            L_0x0043:
                r4 = r11[r2]
                boolean r4 = r0.get(r4)
                if (r4 != 0) goto L_0x0050
                r4 = r11[r2]
                r9.add(r4)
            L_0x0050:
                int r2 = r2 + 1
                goto L_0x0037
            L_0x0053:
                org.eclipse.mat.collect.SetInt r2 = new org.eclipse.mat.collect.SetInt
                r2.<init>(r0)
                r0 = r12
                r3 = r0
            L_0x005a:
                if (r0 >= r13) goto L_0x0078
                r4 = r11[r0]
                if (r4 >= 0) goto L_0x0078
                int r3 = r3 + 1
                r4 = r11[r0]
                int r4 = -r4
                int r4 = r4 - r1
                r11[r0] = r4
                r4 = r11[r0]
                boolean r4 = r2.add(r4)
                if (r4 == 0) goto L_0x0075
                r4 = r11[r0]
                r9.add(r4)
            L_0x0075:
                int r0 = r0 + 1
                goto L_0x005a
            L_0x0078:
                if (r0 >= r13) goto L_0x0094
                if (r0 == r12) goto L_0x0084
                int r4 = r0 + -1
                r4 = r11[r4]
                r5 = r11[r0]
                if (r4 == r5) goto L_0x0091
            L_0x0084:
                r4 = r11[r0]
                boolean r4 = r2.contains(r4)
                if (r4 != 0) goto L_0x0091
                r4 = r11[r0]
                r9.add(r4)
            L_0x0091:
                int r0 = r0 + 1
                goto L_0x0078
            L_0x0094:
                if (r3 <= r12) goto L_0x00a5
                r9 = 2
                int[] r9 = new int[r9]
                r11 = 0
                r8 = r8[r10]
                int r8 = r8 - r1
                r9[r11] = r8
                int r3 = r3 - r12
                r9[r1] = r3
                r7.storeKey(r10, r9)
            L_0x00a5:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.index.IndexWriter.InboundWriter.processObject(org.eclipse.mat.parser.index.IndexWriter$KeyWriter, int[], org.eclipse.mat.parser.index.IndexWriter$IntIndexStreamer, int, int[], int, int):void");
        }

        public synchronized void cancel() {
            try {
                close();
                if (this.segments != null) {
                    for (int i = 0; i < this.segments.length; i++) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.indexFile.getAbsolutePath());
                        sb.append(i);
                        sb.append(".log");
                        new File(sb.toString()).delete();
                    }
                }
                this.indexFile.delete();
            } catch (IOException unused) {
                this.indexFile.delete();
            } catch (Throwable th) {
                this.indexFile.delete();
                throw th;
            }
        }

        public synchronized void close() throws IOException {
            if (this.segments != null) {
                for (int i = 0; i < this.segments.length; i++) {
                    if (this.segments[i] != null) {
                        this.segments[i].flush();
                        this.segments[i].close();
                        this.segments[i] = null;
                    }
                }
            }
        }

        public File getIndexFile() {
            return this.indexFile;
        }
    }

    public static class IntArray1NSortedWriter extends IntArray1NWriter {
        public IntArray1NSortedWriter(int i, File file) throws IOException {
            super(i, file);
        }

        /* access modifiers changed from: protected */
        public void set(int i, int[] iArr, int i2, int i3) throws IOException {
            this.header[i] = this.body.size() + 1;
            this.body.addAll(iArr, i2, i3);
        }

        /* access modifiers changed from: protected */
        public IOne2ManyIndex createReader(IOne2OneIndex iOne2OneIndex, IOne2OneIndex iOne2OneIndex2) throws IOException {
            return new IntIndex1NSortedReader(this.indexFile, iOne2OneIndex, iOne2OneIndex2);
        }
    }

    public static class IntArray1NUncompressedCollector {
        int[][] elements;
        File indexFile;

        public IntArray1NUncompressedCollector(int i, File file) throws IOException {
            this.elements = new int[i][];
            this.indexFile = file;
        }

        public void log(int i, int i2) {
            if (this.elements[i] == null) {
                this.elements[i] = new int[]{i2};
                return;
            }
            int[] iArr = new int[(this.elements[i].length + 1)];
            System.arraycopy(this.elements[i], 0, iArr, 0, this.elements[i].length);
            iArr[this.elements[i].length] = i2;
            this.elements[i] = iArr;
        }

        public File getIndexFile() {
            return this.indexFile;
        }

        public IOne2ManyIndex flush() throws IOException {
            IntArray1NSortedWriter intArray1NSortedWriter = new IntArray1NSortedWriter(this.elements.length, this.indexFile);
            for (int i = 0; i < this.elements.length; i++) {
                if (this.elements[i] != null) {
                    intArray1NSortedWriter.log(i, this.elements[i]);
                }
            }
            return intArray1NSortedWriter.flush();
        }
    }

    public static class IntArray1NWriter {
        IntIndexStreamer body = new IntIndexStreamer();
        int[] header;
        File indexFile;
        DataOutputStream out;

        public IntArray1NWriter(int i, File file) throws IOException {
            this.header = new int[i];
            this.indexFile = file;
            this.out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            this.body.openStream(this.out, 0);
        }

        public void log(Identifier identifier, int i, ArrayLong arrayLong) throws IOException {
            int i2;
            long firstElement = arrayLong.firstElement();
            arrayLong.sort();
            int[] iArr = new int[arrayLong.size()];
            long firstElement2 = arrayLong.firstElement() - 1;
            int i3 = 0;
            int i4 = 1;
            while (i3 < iArr.length) {
                long j = arrayLong.get(i3);
                if (firstElement2 != j) {
                    int reverse = identifier.reverse(j);
                    if (reverse >= 0) {
                        if (j == firstElement) {
                            i2 = i4;
                            i4 = 0;
                        } else {
                            i2 = i4 + 1;
                        }
                        iArr[i4] = reverse;
                        i4 = i2;
                    }
                }
                i3++;
                firstElement2 = j;
            }
            set(i, iArr, 0, i4);
        }

        public void log(int i, ArrayInt arrayInt) throws IOException {
            set(i, arrayInt.toArray(), 0, arrayInt.size());
        }

        public void log(int i, int[] iArr) throws IOException {
            set(i, iArr, 0, iArr.length);
        }

        /* access modifiers changed from: protected */
        public void set(int i, int[] iArr, int i2, int i3) throws IOException {
            this.header[i] = this.body.size();
            this.body.add(i3);
            this.body.addAll(iArr, i2, i3);
        }

        public IOne2ManyIndex flush() throws IOException {
            long closeStream = this.body.closeStream();
            IOne2OneIndex writeTo = new IntIndexStreamer().writeTo(this.out, closeStream, this.header);
            this.out.writeLong(closeStream);
            this.out.close();
            this.out = null;
            return createReader(writeTo, this.body.getReader(null));
        }

        /* access modifiers changed from: protected */
        public IOne2ManyIndex createReader(IOne2OneIndex iOne2OneIndex, IOne2OneIndex iOne2OneIndex2) throws IOException {
            return new IntIndex1NReader(this.indexFile, iOne2OneIndex, iOne2OneIndex2);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0031, code lost:
            if (r2.indexFile.exists() == false) goto L_0x0034;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0034, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0014, code lost:
            if (r2.indexFile.exists() != false) goto L_0x0016;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0016, code lost:
            r2.indexFile.delete();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x001b, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void cancel() {
            /*
                r2 = this;
                java.io.DataOutputStream r0 = r2.out     // Catch:{ IOException -> 0x002b, all -> 0x001c }
                if (r0 == 0) goto L_0x000e
                java.io.DataOutputStream r0 = r2.out     // Catch:{ IOException -> 0x002b, all -> 0x001c }
                r0.close()     // Catch:{ IOException -> 0x002b, all -> 0x001c }
                r0 = 0
                r2.body = r0     // Catch:{ IOException -> 0x002b, all -> 0x001c }
                r2.out = r0     // Catch:{ IOException -> 0x002b, all -> 0x001c }
            L_0x000e:
                java.io.File r0 = r2.indexFile
                boolean r0 = r0.exists()
                if (r0 == 0) goto L_0x0034
            L_0x0016:
                java.io.File r0 = r2.indexFile
                r0.delete()
                return
            L_0x001c:
                r0 = move-exception
                java.io.File r1 = r2.indexFile
                boolean r1 = r1.exists()
                if (r1 == 0) goto L_0x002a
                java.io.File r1 = r2.indexFile
                r1.delete()
            L_0x002a:
                throw r0
            L_0x002b:
                java.io.File r0 = r2.indexFile
                boolean r0 = r0.exists()
                if (r0 == 0) goto L_0x0034
                goto L_0x0016
            L_0x0034:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.index.IndexWriter.IntArray1NWriter.cancel():void");
        }

        public File getIndexFile() {
            return this.indexFile;
        }
    }

    static abstract class IntIndex<V> {
        int pageSize;
        Pages<V> pages;
        int size;

        /* access modifiers changed from: protected */
        public abstract ArrayIntCompressed getPage(int i);

        protected IntIndex() {
        }

        protected IntIndex(int i) {
            init(i, 1000000);
        }

        /* access modifiers changed from: protected */
        public void init(int i, int i2) {
            this.size = i;
            this.pageSize = i2;
            this.pages = new Pages<>((i / i2) + 1);
        }

        public int get(int i) {
            return getPage(i / this.pageSize).get(i % this.pageSize);
        }

        public int[] getNext(int i, int i2) {
            int[] iArr = new int[i2];
            int i3 = i / this.pageSize;
            ArrayIntCompressed page = getPage(i3);
            int i4 = i3;
            int i5 = i % this.pageSize;
            for (int i6 = 0; i6 < i2; i6++) {
                int i7 = i5 + 1;
                iArr[i6] = page.get(i5);
                if (i7 >= this.pageSize) {
                    i4++;
                    page = getPage(i4);
                    i5 = 0;
                } else {
                    i5 = i7;
                }
            }
            return iArr;
        }

        public int[] getAll(int[] iArr) {
            int[] iArr2 = new int[iArr.length];
            int i = -1;
            ArrayIntCompressed arrayIntCompressed = null;
            for (int i2 = 0; i2 < iArr2.length; i2++) {
                int i3 = iArr[i2] / this.pageSize;
                if (i3 != i) {
                    arrayIntCompressed = getPage(i3);
                    i = i3;
                }
                iArr2[i2] = arrayIntCompressed.get(iArr[i2] % this.pageSize);
            }
            return iArr2;
        }

        public void set(int i, int i2) {
            getPage(i / this.pageSize).set(i % this.pageSize, i2);
        }

        public synchronized void unload() {
            this.pages = new Pages<>((this.size / this.pageSize) + 1);
        }

        public int size() {
            return this.size;
        }

        public IteratorInt iterator() {
            return new IntIndexIterator(this);
        }
    }

    public static class IntIndexCollector extends IntIndex<ArrayIntCompressed> implements IOne2OneIndex {
        int mostSignificantBit;

        public void close() throws IOException {
        }

        public /* bridge */ /* synthetic */ int get(int i) {
            return super.get(i);
        }

        public /* bridge */ /* synthetic */ int[] getAll(int[] iArr) {
            return super.getAll(iArr);
        }

        public /* bridge */ /* synthetic */ int[] getNext(int i, int i2) {
            return super.getNext(i, i2);
        }

        public /* bridge */ /* synthetic */ IteratorInt iterator() {
            return super.iterator();
        }

        public /* bridge */ /* synthetic */ void set(int i, int i2) {
            super.set(i, i2);
        }

        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        public /* bridge */ /* synthetic */ void unload() {
            super.unload();
        }

        public IntIndexCollector(int i, int i2) {
            super(i);
            this.mostSignificantBit = i2;
        }

        /* access modifiers changed from: protected */
        public ArrayIntCompressed getPage(int i) {
            ArrayIntCompressed arrayIntCompressed = (ArrayIntCompressed) this.pages.get(i);
            if (arrayIntCompressed != null) {
                return arrayIntCompressed;
            }
            ArrayIntCompressed arrayIntCompressed2 = new ArrayIntCompressed(i < this.size / this.pageSize ? this.pageSize : this.size % this.pageSize, 31 - this.mostSignificantBit, 0);
            this.pages.put(i, arrayIntCompressed2);
            return arrayIntCompressed2;
        }

        public IOne2OneIndex writeTo(File file) throws IOException {
            return new IntIndexStreamer().writeTo(file, iterator());
        }

        public IOne2OneIndex writeTo(DataOutputStream dataOutputStream, long j) throws IOException {
            return new IntIndexStreamer().writeTo(dataOutputStream, j, iterator());
        }

        public void delete() {
            this.pages = null;
        }
    }

    public static class IntIndexCollectorUncompressed {
        int[] dataElements;

        public IntIndexCollectorUncompressed(int i) {
            this.dataElements = new int[i];
        }

        public void set(int i, int i2) {
            this.dataElements[i] = i2;
        }

        public int get(int i) {
            return this.dataElements[i];
        }

        public IOne2OneIndex writeTo(File file) throws IOException {
            return new IntIndexStreamer().writeTo(file, this.dataElements);
        }
    }

    static class IntIndexIterator implements IteratorInt {
        IntIndex<?> intArray;
        int nextIndex = 0;

        public IntIndexIterator(IntIndex<?> intIndex) {
            this.intArray = intIndex;
        }

        public int next() {
            IntIndex<?> intIndex = this.intArray;
            int i = this.nextIndex;
            this.nextIndex = i + 1;
            return intIndex.get(i);
        }

        public boolean hasNext() {
            return this.nextIndex < this.intArray.size();
        }
    }

    public static class IntIndexStreamer extends IntIndex<SoftReference<ArrayIntCompressed>> {
        int left;
        DataOutputStream out;
        int[] page;
        ArrayLong pageStart;

        public /* bridge */ /* synthetic */ int get(int i) {
            return super.get(i);
        }

        public /* bridge */ /* synthetic */ int[] getAll(int[] iArr) {
            return super.getAll(iArr);
        }

        public /* bridge */ /* synthetic */ int[] getNext(int i, int i2) {
            return super.getNext(i, i2);
        }

        public /* bridge */ /* synthetic */ IteratorInt iterator() {
            return super.iterator();
        }

        public /* bridge */ /* synthetic */ void set(int i, int i2) {
            super.set(i, i2);
        }

        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        public /* bridge */ /* synthetic */ void unload() {
            super.unload();
        }

        public IOne2OneIndex writeTo(File file, IteratorInt iteratorInt) throws IOException {
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            openStream(dataOutputStream, 0);
            addAll(iteratorInt);
            closeStream();
            dataOutputStream.close();
            return getReader(file);
        }

        public IOne2OneIndex writeTo(File file, int[] iArr) throws IOException {
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            openStream(dataOutputStream, 0);
            addAll(iArr);
            closeStream();
            dataOutputStream.close();
            return getReader(file);
        }

        public IOne2OneIndex writeTo(DataOutputStream dataOutputStream, long j, IteratorInt iteratorInt) throws IOException {
            openStream(dataOutputStream, j);
            addAll(iteratorInt);
            closeStream();
            return getReader(null);
        }

        public IOne2OneIndex writeTo(DataOutputStream dataOutputStream, long j, int[] iArr) throws IOException {
            openStream(dataOutputStream, j);
            addAll(iArr);
            closeStream();
            return getReader(null);
        }

        /* access modifiers changed from: 0000 */
        public void openStream(DataOutputStream dataOutputStream, long j) {
            this.out = dataOutputStream;
            init(0, 1000000);
            this.page = new int[this.pageSize];
            this.pageStart = new ArrayLong();
            this.pageStart.add(j);
            this.left = this.page.length;
        }

        /* access modifiers changed from: 0000 */
        public long closeStream() throws IOException {
            if (this.left < this.page.length) {
                addPage();
            }
            for (int i = 0; i < this.pageStart.size(); i++) {
                this.out.writeLong(this.pageStart.get(i));
            }
            this.out.writeInt(this.pageSize);
            this.out.writeInt(this.size);
            this.page = null;
            this.out = null;
            return ((this.pageStart.lastElement() + ((long) (this.pageStart.size() * 8))) + 8) - this.pageStart.firstElement();
        }

        /* access modifiers changed from: 0000 */
        public IntIndexReader getReader(File file) {
            IntIndexReader intIndexReader = new IntIndexReader(file, this.pages, this.size, this.pageSize, this.pageStart.toArray());
            return intIndexReader;
        }

        /* access modifiers changed from: 0000 */
        public void addAll(IteratorInt iteratorInt) throws IOException {
            while (iteratorInt.hasNext()) {
                add(iteratorInt.next());
            }
        }

        /* access modifiers changed from: 0000 */
        public void add(int i) throws IOException {
            if (this.left == 0) {
                addPage();
            }
            int[] iArr = this.page;
            int length = this.page.length;
            int i2 = this.left;
            this.left = i2 - 1;
            iArr[length - i2] = i;
            this.size++;
        }

        /* access modifiers changed from: 0000 */
        public void addAll(int[] iArr) throws IOException {
            addAll(iArr, 0, iArr.length);
        }

        /* access modifiers changed from: 0000 */
        public void addAll(int[] iArr, int i, int i2) throws IOException {
            while (i2 > 0) {
                if (this.left == 0) {
                    addPage();
                }
                int min = Math.min(this.left, i2);
                System.arraycopy(iArr, i, this.page, this.page.length - this.left, min);
                this.left -= min;
                this.size += min;
                i2 -= min;
                i += min;
            }
        }

        private void addPage() throws IOException {
            ArrayIntCompressed arrayIntCompressed = new ArrayIntCompressed(this.page, 0, this.page.length - this.left);
            byte[] byteArray = arrayIntCompressed.toByteArray();
            this.out.write(byteArray);
            int length = byteArray.length;
            this.pages.put(this.pages.size(), new SoftReference(arrayIntCompressed));
            this.pageStart.add(this.pageStart.lastElement() + ((long) length));
            this.left = this.page.length;
        }

        /* access modifiers changed from: protected */
        public ArrayIntCompressed getPage(int i) {
            throw new UnsupportedOperationException();
        }
    }

    public interface KeyWriter {
        void storeKey(int i, Serializable serializable);
    }

    public static class LongArray1NWriter {
        LongIndexStreamer body = new LongIndexStreamer();
        int[] header;
        File indexFile;
        DataOutputStream out;

        public LongArray1NWriter(int i, File file) throws IOException {
            this.header = new int[i];
            this.indexFile = file;
            this.out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            this.body.openStream(this.out, 0);
        }

        public void log(int i, long[] jArr) throws IOException {
            set(i, jArr, 0, jArr.length);
        }

        /* access modifiers changed from: protected */
        public void set(int i, long[] jArr, int i2, int i3) throws IOException {
            this.header[i] = this.body.size() + 1;
            this.body.add((long) i3);
            this.body.addAll(jArr, i2, i3);
        }

        public void flush() throws IOException {
            long closeStream = this.body.closeStream();
            new IntIndexStreamer().writeTo(this.out, closeStream, this.header).close();
            this.out.writeLong(closeStream);
            this.out.close();
            this.out = null;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0031, code lost:
            if (r2.indexFile.exists() == false) goto L_0x0034;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0034, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0014, code lost:
            if (r2.indexFile.exists() != false) goto L_0x0016;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0016, code lost:
            r2.indexFile.delete();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x001b, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void cancel() {
            /*
                r2 = this;
                java.io.DataOutputStream r0 = r2.out     // Catch:{ IOException -> 0x002b, all -> 0x001c }
                if (r0 == 0) goto L_0x000e
                java.io.DataOutputStream r0 = r2.out     // Catch:{ IOException -> 0x002b, all -> 0x001c }
                r0.close()     // Catch:{ IOException -> 0x002b, all -> 0x001c }
                r0 = 0
                r2.body = r0     // Catch:{ IOException -> 0x002b, all -> 0x001c }
                r2.out = r0     // Catch:{ IOException -> 0x002b, all -> 0x001c }
            L_0x000e:
                java.io.File r0 = r2.indexFile
                boolean r0 = r0.exists()
                if (r0 == 0) goto L_0x0034
            L_0x0016:
                java.io.File r0 = r2.indexFile
                r0.delete()
                return
            L_0x001c:
                r0 = move-exception
                java.io.File r1 = r2.indexFile
                boolean r1 = r1.exists()
                if (r1 == 0) goto L_0x002a
                java.io.File r1 = r2.indexFile
                r1.delete()
            L_0x002a:
                throw r0
            L_0x002b:
                java.io.File r0 = r2.indexFile
                boolean r0 = r0.exists()
                if (r0 == 0) goto L_0x0034
                goto L_0x0016
            L_0x0034:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.index.IndexWriter.LongArray1NWriter.cancel():void");
        }

        public File getIndexFile() {
            return this.indexFile;
        }
    }

    static abstract class LongIndex {
        private static final int DEPTH = 10;
        HashMapIntLong binarySearchCache = new HashMapIntLong(1024);
        int pageSize;
        HashMapIntObject<Object> pages;
        int size;

        /* access modifiers changed from: protected */
        public abstract ArrayLongCompressed getPage(int i);

        protected LongIndex() {
        }

        protected LongIndex(int i) {
            init(i, 500000);
        }

        /* access modifiers changed from: protected */
        public void init(int i, int i2) {
            this.size = i;
            this.pageSize = i2;
            this.pages = new HashMapIntObject<>((i / i2) + 1);
        }

        public long get(int i) {
            return getPage(i / this.pageSize).get(i % this.pageSize);
        }

        public long[] getNext(int i, int i2) {
            long[] jArr = new long[i2];
            int i3 = i / this.pageSize;
            ArrayLongCompressed page = getPage(i3);
            int i4 = i3;
            int i5 = i % this.pageSize;
            for (int i6 = 0; i6 < i2; i6++) {
                int i7 = i5 + 1;
                jArr[i6] = page.get(i5);
                if (i7 >= this.pageSize) {
                    i4++;
                    page = getPage(i4);
                    i5 = 0;
                } else {
                    i5 = i7;
                }
            }
            return jArr;
        }

        public int reverse(long j) {
            long j2;
            int i;
            int i2 = this.size - 1;
            int i3 = 0;
            ArrayLongCompressed arrayLongCompressed = null;
            int i4 = 0;
            int i5 = -1;
            while (i3 <= i2) {
                int i6 = (i3 + i2) >> 1;
                int i7 = i4 + 1;
                if (i4 < 10) {
                    try {
                        j2 = this.binarySearchCache.get(i6);
                    } catch (NoSuchElementException unused) {
                        i = i6 / this.pageSize;
                        if (i != i5) {
                            arrayLongCompressed = getPage(i);
                        } else {
                            i = i5;
                        }
                        j2 = arrayLongCompressed.get(i6 % this.pageSize);
                        this.binarySearchCache.put(i6, j2);
                    }
                } else {
                    int i8 = i6 / this.pageSize;
                    if (i8 != i5) {
                        arrayLongCompressed = getPage(i8);
                    } else {
                        i8 = i5;
                    }
                    j2 = arrayLongCompressed.get(i6 % this.pageSize);
                    i5 = i;
                }
                int i9 = (j2 > j ? 1 : (j2 == j ? 0 : -1));
                if (i9 < 0) {
                    i3 = i6 + 1;
                } else if (i9 <= 0) {
                    return i6;
                } else {
                    i2 = i6 - 1;
                }
                i4 = i7;
            }
            return -(i3 + 1);
        }

        public void set(int i, long j) {
            getPage(i / this.pageSize).set(i % this.pageSize, j);
        }

        public synchronized void unload() {
            this.pages = new HashMapIntObject<>((this.size / this.pageSize) + 1);
            this.binarySearchCache = new HashMapIntLong(1024);
        }

        public int size() {
            return this.size;
        }

        public IteratorLong iterator() {
            return new LongIndexIterator(this);
        }
    }

    public static class LongIndexCollector extends LongIndex {
        int mostSignificantBit;

        public /* bridge */ /* synthetic */ long get(int i) {
            return super.get(i);
        }

        public /* bridge */ /* synthetic */ long[] getNext(int i, int i2) {
            return super.getNext(i, i2);
        }

        public /* bridge */ /* synthetic */ IteratorLong iterator() {
            return super.iterator();
        }

        public /* bridge */ /* synthetic */ int reverse(long j) {
            return super.reverse(j);
        }

        public /* bridge */ /* synthetic */ void set(int i, long j) {
            super.set(i, j);
        }

        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        public /* bridge */ /* synthetic */ void unload() {
            super.unload();
        }

        public LongIndexCollector(int i, int i2) {
            super(i);
            this.mostSignificantBit = i2;
        }

        /* access modifiers changed from: protected */
        public ArrayLongCompressed getPage(int i) {
            ArrayLongCompressed arrayLongCompressed = (ArrayLongCompressed) this.pages.get(i);
            if (arrayLongCompressed != null) {
                return arrayLongCompressed;
            }
            ArrayLongCompressed arrayLongCompressed2 = new ArrayLongCompressed(i < this.size / this.pageSize ? this.pageSize : this.size % this.pageSize, 63 - this.mostSignificantBit, 0);
            this.pages.put(i, arrayLongCompressed2);
            return arrayLongCompressed2;
        }

        public IOne2LongIndex writeTo(File file) throws IOException {
            return new LongIndexStreamer().writeTo(file, this.size, this.pages, this.pageSize);
        }
    }

    public static class LongIndexCollectorUncompressed {
        long[] dataElements;

        public LongIndexCollectorUncompressed(int i) {
            this.dataElements = new long[i];
        }

        public void set(int i, long j) {
            this.dataElements[i] = j;
        }

        public long get(int i) {
            return this.dataElements[i];
        }

        public IOne2LongIndex writeTo(File file) throws IOException {
            return new LongIndexStreamer().writeTo(file, this.dataElements);
        }
    }

    static class LongIndexIterator implements IteratorLong {
        LongIndex longArray;
        int nextIndex = 0;

        public LongIndexIterator(LongIndex longIndex) {
            this.longArray = longIndex;
        }

        public long next() {
            LongIndex longIndex = this.longArray;
            int i = this.nextIndex;
            this.nextIndex = i + 1;
            return longIndex.get(i);
        }

        public boolean hasNext() {
            return this.nextIndex < this.longArray.size();
        }
    }

    public static class LongIndexStreamer extends LongIndex {
        int left;
        DataOutputStream out;
        long[] page;
        ArrayLong pageStart;

        public /* bridge */ /* synthetic */ long get(int i) {
            return super.get(i);
        }

        public /* bridge */ /* synthetic */ long[] getNext(int i, int i2) {
            return super.getNext(i, i2);
        }

        public /* bridge */ /* synthetic */ IteratorLong iterator() {
            return super.iterator();
        }

        public /* bridge */ /* synthetic */ int reverse(long j) {
            return super.reverse(j);
        }

        public /* bridge */ /* synthetic */ void set(int i, long j) {
            super.set(i, j);
        }

        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        public /* bridge */ /* synthetic */ void unload() {
            super.unload();
        }

        public LongIndexStreamer() {
        }

        public LongIndexStreamer(File file) throws IOException {
            openStream(new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file))), 0);
        }

        public void close() throws IOException {
            DataOutputStream dataOutputStream = this.out;
            closeStream();
            dataOutputStream.close();
        }

        public IOne2LongIndex writeTo(File file, int i, HashMapIntObject<Object> hashMapIntObject, int i2) throws IOException {
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            openStream(dataOutputStream, 0);
            int i3 = i / i2;
            int i4 = i % i2;
            int i5 = i3 + (i4 > 0 ? 1 : 0);
            int i6 = 0;
            while (i6 < i5) {
                ArrayLongCompressed arrayLongCompressed = (ArrayLongCompressed) hashMapIntObject.get(i6);
                i6++;
                int i7 = i6 < i5 ? i2 : i4;
                if (arrayLongCompressed == null) {
                    addAll(new long[i7]);
                } else {
                    for (int i8 = 0; i8 < i7; i8++) {
                        add(arrayLongCompressed.get(i8));
                    }
                }
            }
            closeStream();
            dataOutputStream.close();
            return getReader(file);
        }

        public IOne2LongIndex writeTo(File file, long[] jArr) throws IOException {
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            openStream(dataOutputStream, 0);
            addAll(jArr);
            closeStream();
            dataOutputStream.close();
            return getReader(file);
        }

        public IOne2LongIndex writeTo(File file, IteratorLong iteratorLong) throws IOException {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream));
                openStream(dataOutputStream, 0);
                addAll(iteratorLong);
                closeStream();
                dataOutputStream.flush();
                return getReader(file);
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException unused) {
                }
            }
        }

        public IOne2LongIndex writeTo(File file, ArrayLong arrayLong) throws IOException {
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            openStream(dataOutputStream, 0);
            addAll(arrayLong);
            closeStream();
            dataOutputStream.close();
            return getReader(file);
        }

        /* access modifiers changed from: 0000 */
        public void openStream(DataOutputStream dataOutputStream, long j) {
            this.out = dataOutputStream;
            init(0, 500000);
            this.page = new long[this.pageSize];
            this.pageStart = new ArrayLong();
            this.pageStart.add(j);
            this.left = this.page.length;
        }

        /* access modifiers changed from: 0000 */
        public long closeStream() throws IOException {
            if (this.left < this.page.length) {
                addPage();
            }
            for (int i = 0; i < this.pageStart.size(); i++) {
                this.out.writeLong(this.pageStart.get(i));
            }
            this.out.writeInt(this.pageSize);
            this.out.writeInt(this.size);
            this.page = null;
            this.out = null;
            return ((this.pageStart.lastElement() + ((long) (this.pageStart.size() * 8))) + 8) - this.pageStart.firstElement();
        }

        /* access modifiers changed from: 0000 */
        public LongIndexReader getReader(File file) throws IOException {
            LongIndexReader longIndexReader = new LongIndexReader(file, this.pages, this.size, this.pageSize, this.pageStart.toArray());
            return longIndexReader;
        }

        public void addAll(IteratorLong iteratorLong) throws IOException {
            while (iteratorLong.hasNext()) {
                add(iteratorLong.next());
            }
        }

        public void addAll(ArrayLong arrayLong) throws IOException {
            IteratorLong it = arrayLong.iterator();
            while (it.hasNext()) {
                add(it.next());
            }
        }

        public void add(long j) throws IOException {
            if (this.left == 0) {
                addPage();
            }
            long[] jArr = this.page;
            int length = this.page.length;
            int i = this.left;
            this.left = i - 1;
            jArr[length - i] = j;
            this.size++;
        }

        public void addAll(long[] jArr) throws IOException {
            addAll(jArr, 0, jArr.length);
        }

        public void addAll(long[] jArr, int i, int i2) throws IOException {
            while (i2 > 0) {
                if (this.left == 0) {
                    addPage();
                }
                int min = Math.min(this.left, i2);
                System.arraycopy(jArr, i, this.page, this.page.length - this.left, min);
                this.left -= min;
                this.size += min;
                i2 -= min;
                i += min;
            }
        }

        private void addPage() throws IOException {
            ArrayLongCompressed arrayLongCompressed = new ArrayLongCompressed(this.page, 0, this.page.length - this.left);
            byte[] byteArray = arrayLongCompressed.toByteArray();
            this.out.write(byteArray);
            int length = byteArray.length;
            this.pages.put(this.pages.size(), new SoftReference(arrayLongCompressed));
            this.pageStart.add(this.pageStart.lastElement() + ((long) length));
            this.left = this.page.length;
        }

        /* access modifiers changed from: protected */
        public ArrayLongCompressed getPage(int i) {
            throw new UnsupportedOperationException();
        }
    }

    static class Pages<V> {
        Object[] elements;
        int size = 0;

        public Pages(int i) {
            this.elements = new Object[i];
        }

        private void ensureCapacity(int i) {
            int length = this.elements.length;
            if (i > length) {
                int i2 = ((length * 3) / 2) + 1;
                if (i2 >= i) {
                    i = i2;
                }
                Object[] objArr = new Object[i];
                System.arraycopy(this.elements, 0, objArr, 0, Math.min(this.elements.length, i));
                this.elements = objArr;
            }
        }

        public V get(int i) {
            if (i >= this.elements.length) {
                return null;
            }
            return this.elements[i];
        }

        public void put(int i, V v) {
            int i2 = i + 1;
            ensureCapacity(i2);
            this.elements[i] = v;
            this.size = Math.max(this.size, i2);
        }

        public int size() {
            return this.size;
        }
    }

    public static int mostSignificantBit(int i) {
        int i2;
        if ((-65536 & i) != 0) {
            i >>= 16;
            i2 = 16;
        } else {
            i2 = 0;
        }
        if ((65280 & i) != 0) {
            i2 += 8;
            i >>= 8;
        }
        if ((i & 240) != 0) {
            i2 += 4;
            i >>= 4;
        }
        if ((i & 12) != 0) {
            i2 += 2;
            i >>= 2;
        }
        if ((i & 2) != 0) {
            i2++;
            i >>= 1;
        }
        if ((i & 1) != 0) {
            i2++;
        }
        return i2 - 1;
    }

    public static long[] copyOf(long[] jArr, int i) {
        long[] jArr2 = new long[i];
        System.arraycopy(jArr, 0, jArr2, 0, Math.min(jArr.length, i));
        return jArr2;
    }

    public static int mostSignificantBit(long j) {
        long j2 = j >>> 32;
        return j2 == 0 ? mostSignificantBit((int) j) : mostSignificantBit((int) j2) + 32;
    }
}
