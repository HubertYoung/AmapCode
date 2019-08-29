package org.eclipse.mat.parser.internal;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayUtils;
import org.eclipse.mat.collect.BitField;
import org.eclipse.mat.collect.IteratorInt;
import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.parser.index.IIndexReader.IOne2ManyIndex;
import org.eclipse.mat.parser.index.IndexManager;
import org.eclipse.mat.parser.index.IndexManager.Index;
import org.eclipse.mat.parser.index.IndexWriter;
import org.eclipse.mat.parser.index.IndexWriter.IntArray1NWriter;
import org.eclipse.mat.parser.index.IndexWriter.IntIndexStreamer;
import org.eclipse.mat.parser.index.IndexWriter.LongIndexCollector;
import org.eclipse.mat.parser.internal.util.IntStack;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.IProgressListener.OperationCanceledException;
import org.eclipse.mat.util.SimpleMonitor;

public class DominatorTree {

    static class Calculator {
        private static int ROOT_VALUE = -1;
        private static int[] ROOT_VALUE_ARR = {ROOT_VALUE};
        private int[] anchestor;
        int[] bucket;
        /* access modifiers changed from: private */
        public int[] dom;
        int[] gcRootsArray;
        private BitField gcRootsSet;
        IOne2ManyIndex inboundIndex;
        private int[] label;
        SimpleMonitor monitor;
        private int n;
        IOne2ManyIndex outboundIndex;
        private int[] parent;
        private int r;
        private int[] semi;
        SnapshotImpl snapshot;
        private int[] vertex;

        public class FlatDominatorTree {
            private static final int TEMP_ARR_LENGTH = 1000000;
            int[] dom;
            SnapshotImpl dump;
            int[] elements;
            int[] tempIntArray = new int[1000000];
            long[] tempLongArray = new long[1000000];
            long[] ts;

            class SuccessorsEnum {
                int nextIndex;
                int parent;

                SuccessorsEnum(int i) {
                    this.parent = i;
                    this.nextIndex = findFirstChildIndex(i + 2);
                }

                public boolean hasMoreElements() {
                    return this.nextIndex > 0;
                }

                public int nextElement() {
                    if (this.nextIndex < 0) {
                        throw new NoSuchElementException();
                    }
                    int[] iArr = FlatDominatorTree.this.elements;
                    int i = this.nextIndex;
                    this.nextIndex = i + 1;
                    int i2 = iArr[i];
                    if (this.nextIndex >= FlatDominatorTree.this.dom.length || FlatDominatorTree.this.dom[this.nextIndex] != this.parent + 2) {
                        this.nextIndex = -1;
                    }
                    return i2;
                }

                /* access modifiers changed from: 0000 */
                public int findFirstChildIndex(int i) {
                    int binarySearch = Arrays.binarySearch(FlatDominatorTree.this.dom, i);
                    while (binarySearch > 1 && FlatDominatorTree.this.dom[binarySearch - 1] == i) {
                        binarySearch--;
                    }
                    return binarySearch;
                }
            }

            FlatDominatorTree(SnapshotImpl snapshotImpl, int[] iArr, int[] iArr2, int i) throws SnapshotException, IOException {
                this.dump = snapshotImpl;
                this.dom = iArr;
                this.elements = iArr2;
                this.ts = new long[iArr.length];
                calculateTotalSizesIterative(i);
            }

            public SuccessorsEnum getSuccessorsEnum(int i) {
                return new SuccessorsEnum(i);
            }

            public int[] getSuccessorsArr(int i) {
                int i2 = i + 2;
                int binarySearch = Arrays.binarySearch(this.dom, i2);
                if (binarySearch < 0) {
                    return new int[0];
                }
                int i3 = binarySearch;
                while (i3 > 1 && this.dom[i3 - 1] == i2) {
                    i3--;
                }
                while (binarySearch < this.dom.length && this.dom[binarySearch] == i2) {
                    binarySearch++;
                }
                int i4 = binarySearch - i3;
                int[] iArr = new int[i4];
                System.arraycopy(this.elements, i3, iArr, 0, i4);
                return iArr;
            }

            public void sortByTotalSize(int[] iArr) {
                int length = iArr.length;
                long[] jArr = new long[length];
                for (int i = 0; i < length; i++) {
                    jArr[i] = this.ts[iArr[i] + 2];
                }
                if (length > 1) {
                    if (length > 1000000) {
                        ArrayUtils.sortDesc(jArr, iArr);
                        return;
                    }
                    ArrayUtils.sortDesc(jArr, iArr, this.tempLongArray, this.tempIntArray);
                }
            }

            public void calculateTotalSizesIterative(int i) throws SnapshotException, IOException {
                LongIndexCollector longIndexCollector = new LongIndexCollector(this.dump.getSnapshotInfo().getNumberOfObjects(), IndexWriter.mostSignificantBit(this.dump.getSnapshotInfo().getUsedHeapSize()));
                int[] iArr = new int[2048];
                SuccessorsEnum[] successorsEnumArr = new SuccessorsEnum[2048];
                SuccessorsEnum successorsEnum = getSuccessorsEnum(i);
                iArr[0] = i;
                successorsEnumArr[0] = successorsEnum;
                IProgressListener nextMonitor = Calculator.this.monitor.nextMonitor();
                nextMonitor.beginTask(Messages.DominatorTree_CalculateRetainedSizes, this.dump.getSnapshotInfo().getNumberOfObjects() / 1000);
                int i2 = 1;
                int i3 = 2048;
                int i4 = 0;
                while (i2 > 0) {
                    int i5 = i2 - 1;
                    int i6 = iArr[i5];
                    SuccessorsEnum successorsEnum2 = successorsEnumArr[i5];
                    if (successorsEnum2.hasMoreElements()) {
                        int nextElement = successorsEnum2.nextElement();
                        SuccessorsEnum successorsEnum3 = getSuccessorsEnum(nextElement);
                        this.ts[nextElement + 2] = nextElement < 0 ? 0 : (long) Calculator.this.snapshot.getHeapSize(nextElement);
                        if (i2 == i3) {
                            int i7 = i3 << 1;
                            int[] iArr2 = new int[i7];
                            System.arraycopy(iArr, 0, iArr2, 0, i3);
                            SuccessorsEnum[] successorsEnumArr2 = new SuccessorsEnum[i7];
                            System.arraycopy(successorsEnumArr, 0, successorsEnumArr2, 0, i3);
                            successorsEnumArr = successorsEnumArr2;
                            i3 = i7;
                            iArr = iArr2;
                        }
                        iArr[i2] = nextElement;
                        successorsEnumArr[i2] = successorsEnum3;
                        i2++;
                    } else {
                        i2--;
                        if (i2 > 0) {
                            long[] jArr = this.ts;
                            int i8 = iArr[i2 - 1] + 2;
                            jArr[i8] = jArr[i8] + this.ts[i6 + 2];
                        }
                        if (i6 >= 0) {
                            longIndexCollector.set(i6, this.ts[i6 + 2]);
                            i4++;
                            if (i4 % 1000 != 0) {
                                continue;
                            } else if (nextMonitor.isCanceled()) {
                                throw new OperationCanceledException();
                            } else {
                                nextMonitor.worked(1);
                            }
                        } else {
                            continue;
                        }
                    }
                }
                this.dump.getIndexManager().setReader(Index.O2RETAINED, longIndexCollector.writeTo(Index.O2RETAINED.getFile(this.dump.getSnapshotInfo().getPrefix())));
                nextMonitor.done();
            }
        }

        public Calculator(SnapshotImpl snapshotImpl, IProgressListener iProgressListener) throws SnapshotException {
            this.snapshot = snapshotImpl;
            this.inboundIndex = snapshotImpl.getIndexManager().inbound();
            this.outboundIndex = snapshotImpl.getIndexManager().outbound();
            this.monitor = new SimpleMonitor(Messages.DominatorTree_CalculatingDominatorTree.pattern, iProgressListener, new int[]{300, 300, 200, 200, 200});
            this.gcRootsArray = snapshotImpl.getGCRoots();
            this.gcRootsSet = new BitField(snapshotImpl.getSnapshotInfo().getNumberOfObjects());
            for (int i : this.gcRootsArray) {
                this.gcRootsSet.set(i);
            }
            IndexManager indexManager = this.snapshot.getIndexManager();
            try {
                indexManager.a2size().unload();
                indexManager.o2address().unload();
                indexManager.o2class().unload();
                this.n = snapshotImpl.getSnapshotInfo().getNumberOfObjects() + 1;
                this.r = 1;
                this.dom = new int[(this.n + 1)];
                this.parent = new int[(this.n + 1)];
                this.anchestor = new int[(this.n + 1)];
                this.vertex = new int[(this.n + 1)];
                this.label = new int[(this.n + 1)];
                this.semi = new int[(this.n + 1)];
                this.bucket = new int[(this.n + 1)];
                Arrays.fill(this.bucket, -1);
            } catch (IOException e) {
                throw new SnapshotException((Throwable) e);
            }
        }

        public void compute() throws IOException, SnapshotException, OperationCanceledException {
            IProgressListener nextMonitor = this.monitor.nextMonitor();
            nextMonitor.beginTask(Messages.DominatorTree_DominatorTreeCalculation, 3);
            this.n = 0;
            dfs(this.r);
            this.snapshot.getIndexManager().outbound().unload();
            IProgressListener nextMonitor2 = this.monitor.nextMonitor();
            nextMonitor2.beginTask(Messages.DominatorTree_ComputingDominators.pattern, this.n / 1000);
            for (int i = this.n; i >= 2; i--) {
                int i2 = this.vertex[i];
                for (int i3 : getPredecessors(i2)) {
                    int i4 = i3 + 2;
                    if (i4 >= 0) {
                        int eval = eval(i4);
                        if (this.semi[eval] < this.semi[i2]) {
                            int[] iArr = this.semi;
                            iArr[i2] = iArr[eval];
                        }
                    }
                }
                int[] iArr2 = this.bucket;
                iArr2[i2] = iArr2[this.vertex[this.semi[i2]]];
                this.bucket[this.vertex[this.semi[i2]]] = i2;
                link(this.parent[i2], i2);
                int i5 = this.bucket[this.parent[i2]];
                while (i5 != -1) {
                    int eval2 = eval(i5);
                    if (this.semi[eval2] < this.semi[i5]) {
                        this.dom[i5] = eval2;
                    } else {
                        this.dom[i5] = this.parent[i2];
                    }
                    i5 = this.bucket[i5];
                }
                this.bucket[this.parent[i2]] = -1;
                if (i % 1000 == 0) {
                    if (nextMonitor2.isCanceled()) {
                        throw new OperationCanceledException();
                    }
                    nextMonitor2.worked(1);
                }
            }
            for (int i6 = 2; i6 <= this.n; i6++) {
                int i7 = this.vertex[i6];
                if (this.dom[i7] != this.vertex[this.semi[i7]]) {
                    int[] iArr3 = this.dom;
                    iArr3[i7] = iArr3[this.dom[i7]];
                }
            }
            this.dom[this.r] = 0;
            nextMonitor2.done();
            this.bucket = null;
            this.semi = null;
            this.label = null;
            this.vertex = null;
            this.anchestor = null;
            this.parent = null;
            this.snapshot.getIndexManager().inbound().unload();
            if (nextMonitor.isCanceled()) {
                throw new OperationCanceledException();
            }
            this.snapshot.getIndexManager().setReader(Index.DOMINATOR, new IntIndexStreamer().writeTo(Index.DOMINATOR.getFile(this.snapshot.getSnapshotInfo().getPrefix()), (IteratorInt) new IteratorInt() {
                int nextIndex = 2;

                public boolean hasNext() {
                    return this.nextIndex < Calculator.this.dom.length;
                }

                public int next() {
                    int[] access$000 = Calculator.this.dom;
                    int i = this.nextIndex;
                    this.nextIndex = i + 1;
                    return access$000[i];
                }
            }));
            int[] iArr4 = new int[(this.snapshot.getSnapshotInfo().getNumberOfObjects() + 2)];
            for (int i8 = 0; i8 < iArr4.length; i8++) {
                iArr4[i8] = i8 - 2;
            }
            iArr4[0] = -2;
            iArr4[1] = ROOT_VALUE;
            nextMonitor.worked(1);
            ArrayUtils.sort(this.dom, iArr4, 2, this.dom.length - 2);
            nextMonitor.worked(1);
            FlatDominatorTree flatDominatorTree = new FlatDominatorTree(this.snapshot, this.dom, iArr4, ROOT_VALUE);
            if (nextMonitor.isCanceled()) {
                throw new OperationCanceledException();
            }
            writeIndexFiles(flatDominatorTree);
            nextMonitor.done();
        }

        private void dfs(int i) throws UnsupportedOperationException {
            int[] iArr;
            Object[] objArr;
            IProgressListener nextMonitor = this.monitor.nextMonitor();
            nextMonitor.beginTask(Messages.DominatorTree_DepthFirstSearch, this.snapshot.getSnapshotInfo().getNumberOfObjects() >> 16);
            int[] iArr2 = new int[2048];
            int[] iArr3 = new int[2048];
            Object[] objArr2 = new Object[2048];
            int[] iArr4 = this.gcRootsArray;
            iArr2[0] = i;
            objArr2[0] = iArr4;
            iArr3[0] = 0;
            int i2 = 1;
            int i3 = 2048;
            while (i2 > 0) {
                int i4 = i2 - 1;
                int i5 = iArr2[i4];
                int[] iArr5 = (int[]) objArr2[i4];
                int i6 = iArr3[i4];
                if (this.semi[i5] == 0) {
                    this.n++;
                    this.semi[i5] = this.n;
                    this.vertex[this.n] = i5;
                    this.label[i5] = i5;
                    this.anchestor[i5] = 0;
                }
                if (i6 < iArr5.length) {
                    int i7 = iArr5[i6] + 2;
                    iArr3[i4] = i6 + 1;
                    if (this.semi[i7] == 0) {
                        this.parent[i7] = i5;
                        int[] iArr6 = this.outboundIndex.get(i7 - 2);
                        if (i2 == i3) {
                            int i8 = i3 << 1;
                            int[] iArr7 = new int[i8];
                            System.arraycopy(iArr2, 0, iArr7, 0, i3);
                            int[] iArr8 = new int[i8];
                            System.arraycopy(iArr3, 0, iArr8, 0, i3);
                            objArr = new Object[i8];
                            System.arraycopy(objArr2, 0, objArr, 0, i3);
                            iArr = iArr8;
                            i3 = i8;
                            iArr2 = iArr7;
                        } else {
                            Object[] objArr3 = objArr2;
                            iArr = iArr3;
                            objArr = objArr3;
                        }
                        iArr2[i2] = i7;
                        objArr[i2] = iArr6;
                        iArr[i2] = 0;
                        i2++;
                        if ((this.n & 65535) == 0) {
                            if (nextMonitor.isCanceled()) {
                                throw new OperationCanceledException();
                            }
                            nextMonitor.worked(1);
                        }
                        int[] iArr9 = iArr;
                        objArr2 = objArr;
                        iArr3 = iArr9;
                    } else {
                        continue;
                    }
                } else {
                    i2--;
                }
            }
            nextMonitor.done();
        }

        private int[] getPredecessors(int i) {
            int i2 = i - 2;
            if (this.gcRootsSet.get(i2)) {
                return ROOT_VALUE_ARR;
            }
            return this.inboundIndex.get(i2);
        }

        private void compress(int i) {
            IntStack intStack = new IntStack();
            while (this.anchestor[this.anchestor[i]] != 0) {
                intStack.push(i);
                i = this.anchestor[i];
            }
            while (intStack.size() > 0) {
                int pop = intStack.pop();
                if (this.semi[this.label[this.anchestor[pop]]] < this.semi[this.label[pop]]) {
                    int[] iArr = this.label;
                    iArr[pop] = iArr[this.anchestor[pop]];
                }
                int[] iArr2 = this.anchestor;
                iArr2[pop] = iArr2[this.anchestor[pop]];
            }
        }

        private int eval(int i) {
            if (this.anchestor[i] == 0) {
                return i;
            }
            compress(i);
            return this.label[i];
        }

        private void link(int i, int i2) {
            this.anchestor[i2] = i;
        }

        private void writeIndexFiles(FlatDominatorTree flatDominatorTree) throws IOException {
            IntArray1NWriter intArray1NWriter = new IntArray1NWriter(this.dom.length - 1, Index.DOMINATED.getFile(this.snapshot.getSnapshotInfo().getPrefix()));
            int numberOfObjects = this.snapshot.getSnapshotInfo().getNumberOfObjects();
            IProgressListener nextMonitor = this.monitor.nextMonitor();
            nextMonitor.beginTask(Messages.DominatorTree_CreateDominatorsIndexFile, numberOfObjects / 1000);
            int i = -1;
            while (i < numberOfObjects) {
                int[] successorsArr = flatDominatorTree.getSuccessorsArr(i);
                flatDominatorTree.sortByTotalSize(successorsArr);
                int i2 = i + 1;
                intArray1NWriter.log(i2, successorsArr);
                if (i % 1000 == 0) {
                    if (nextMonitor.isCanceled()) {
                        throw new OperationCanceledException();
                    }
                    nextMonitor.worked(1);
                }
                i = i2;
            }
            this.snapshot.getIndexManager().setReader(Index.DOMINATED, intArray1NWriter.flush());
            nextMonitor.done();
        }
    }

    public static void calculate(SnapshotImpl snapshotImpl, IProgressListener iProgressListener) throws SnapshotException, IOException {
        new Calculator(snapshotImpl, iProgressListener).compute();
    }
}
