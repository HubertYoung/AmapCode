package org.eclipse.mat.parser.internal;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.mat.collect.BitField;
import org.eclipse.mat.collect.HashMapIntObject;
import org.eclipse.mat.collect.IteratorInt;
import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.parser.index.IIndexReader.IOne2LongIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2ManyIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2OneIndex;
import org.eclipse.mat.parser.index.IndexManager;
import org.eclipse.mat.parser.index.IndexManager.Index;
import org.eclipse.mat.parser.index.IndexWriter.InboundWriter;
import org.eclipse.mat.parser.index.IndexWriter.IntArray1NSortedWriter;
import org.eclipse.mat.parser.index.IndexWriter.IntIndexStreamer;
import org.eclipse.mat.parser.index.IndexWriter.KeyWriter;
import org.eclipse.mat.parser.index.IndexWriter.LongIndexStreamer;
import org.eclipse.mat.parser.internal.snapshot.ObjectMarker;
import org.eclipse.mat.parser.model.ClassImpl;
import org.eclipse.mat.parser.model.XGCRootInfo;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.IProgressListener.OperationCanceledException;
import org.eclipse.mat.util.MessageUtil;
import org.eclipse.mat.util.SilentProgressListener;

class GarbageCleaner {

    static class KeyWriterImpl implements KeyWriter {
        HashMapIntObject<ClassImpl> classesByNewId;

        KeyWriterImpl(HashMapIntObject<ClassImpl> hashMapIntObject) {
            this.classesByNewId = hashMapIntObject;
        }

        public void storeKey(int i, Serializable serializable) {
            ((ClassImpl) this.classesByNewId.get(i)).setCacheEntry(serializable);
        }
    }

    static abstract class NewObjectIntIterator extends NewObjectIterator implements IteratorInt {
        /* access modifiers changed from: 0000 */
        public abstract int doGetNextInt(int i);

        private NewObjectIntIterator() {
        }

        public int next() {
            int doGetNextInt = doGetNextInt(this.nextIndex);
            findNext();
            return doGetNextInt;
        }
    }

    static abstract class NewObjectIterator {
        int[] $map = getMap();
        int nextIndex = -1;

        /* access modifiers changed from: 0000 */
        public abstract int[] getMap();

        public NewObjectIterator() {
            findNext();
        }

        /* access modifiers changed from: protected */
        public void findNext() {
            this.nextIndex++;
            while (this.nextIndex < this.$map.length && this.$map[this.nextIndex] < 0) {
                this.nextIndex++;
            }
        }

        public boolean hasNext() {
            return this.nextIndex < this.$map.length;
        }
    }

    GarbageCleaner() {
    }

    public static int[] clean(PreliminaryIndexImpl preliminaryIndexImpl, SnapshotImplBuilder snapshotImplBuilder, Map<String, String> map, IProgressListener iProgressListener) throws IOException {
        int i;
        int i2;
        IOne2ManyIndex iOne2ManyIndex;
        int[] iArr;
        boolean z;
        int i3;
        IOne2ManyIndex iOne2ManyIndex2;
        boolean[] zArr;
        final PreliminaryIndexImpl preliminaryIndexImpl2 = preliminaryIndexImpl;
        SnapshotImplBuilder snapshotImplBuilder2 = snapshotImplBuilder;
        IProgressListener iProgressListener2 = iProgressListener;
        IndexManager indexManager = new IndexManager();
        try {
            iProgressListener2.beginTask(Messages.GarbageCleaner_RemovingUnreachableObjects, 11);
            iProgressListener2.subTask(Messages.GarbageCleaner_SearchingForUnreachableObjects.pattern);
            int size = preliminaryIndexImpl2.identifiers.size();
            boolean[] zArr2 = new boolean[size];
            int[] allKeys = preliminaryIndexImpl2.gcRoots.getAllKeys();
            IOne2LongIndex iOne2LongIndex = preliminaryIndexImpl2.identifiers;
            IOne2ManyIndex iOne2ManyIndex3 = preliminaryIndexImpl2.outbound;
            IOne2OneIndex iOne2OneIndex = preliminaryIndexImpl2.object2classId;
            HashMapIntObject<ClassImpl> hashMapIntObject = preliminaryIndexImpl2.classesById;
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            ObjectMarker objectMarker = new ObjectMarker(allKeys, zArr2, iOne2ManyIndex3, new SilentProgressListener(iProgressListener2));
            if (availableProcessors > 1) {
                objectMarker.markMultiThreaded(availableProcessors);
                int i4 = 0;
                for (int i5 = 0; i5 < size; i5++) {
                    if (zArr2[i5]) {
                        i4++;
                    }
                }
                i = i4;
            } else {
                try {
                    i = objectMarker.markSingleThreaded();
                } catch (OperationCanceledException unused) {
                    preliminaryIndexImpl.delete();
                    if (iProgressListener.isCanceled()) {
                        indexManager.delete();
                    }
                    return null;
                }
            }
            if (i < size) {
                Serializable property = preliminaryIndexImpl.getSnapshotInfo().getProperty("keep_unreachable_objects");
                if (property instanceof Integer) {
                    markUnreachbleAsGCRoots(preliminaryIndexImpl2, zArr2, i, ((Integer) property).intValue(), iProgressListener2);
                    i = size;
                }
            }
            if (iProgressListener.isCanceled()) {
                throw new OperationCanceledException();
            }
            iProgressListener2.worked(1);
            iProgressListener2.subTask(Messages.GarbageCleaner_ReIndexingObjects.pattern);
            final int[] iArr2 = new int[size];
            long[] jArr = new long[i];
            ArrayList<ClassImpl> arrayList = new ArrayList<>();
            final IOne2OneIndex iOne2OneIndex2 = preliminaryIndexImpl2.array2size;
            int i6 = 0;
            int i7 = 0;
            while (i6 < size) {
                if (zArr2[i6]) {
                    iArr2[i6] = i7;
                    jArr[i7] = iOne2LongIndex.get(i6);
                    i3 = size;
                    zArr = zArr2;
                    iOne2ManyIndex2 = iOne2ManyIndex3;
                    i7++;
                } else {
                    iArr2[i6] = -1;
                    zArr = zArr2;
                    ClassImpl classImpl = (ClassImpl) hashMapIntObject.get(iOne2OneIndex.get(i6));
                    iOne2ManyIndex2 = iOne2ManyIndex3;
                    int i8 = iOne2OneIndex2.get(i6);
                    if (i8 > 0) {
                        classImpl.removeInstance(i8);
                    } else {
                        ClassImpl classImpl2 = (ClassImpl) hashMapIntObject.get(i6);
                        if (classImpl2 == null) {
                            classImpl.removeInstance(classImpl.getHeapSizePerInstance());
                        } else {
                            i3 = size;
                            classImpl.removeInstance(classImpl2.getUsedHeapSize());
                            arrayList.add(classImpl2);
                        }
                    }
                    i3 = size;
                }
                i6++;
                zArr2 = zArr;
                iOne2ManyIndex3 = iOne2ManyIndex2;
                size = i3;
            }
            int i9 = size;
            IOne2ManyIndex iOne2ManyIndex4 = iOne2ManyIndex3;
            for (ClassImpl classImpl3 : arrayList) {
                hashMapIntObject.remove(classImpl3.getObjectId());
                ClassImpl classImpl4 = (ClassImpl) hashMapIntObject.get(classImpl3.getSuperClassId());
                if (classImpl4 != null) {
                    classImpl4.removeSubClass(classImpl3);
                }
            }
            iOne2LongIndex.close();
            iOne2LongIndex.delete();
            if (iProgressListener.isCanceled()) {
                throw new OperationCanceledException();
            }
            iProgressListener2.worked(1);
            iProgressListener2.subTask(Messages.GarbageCleaner_ReIndexingClasses.pattern);
            HashMapIntObject hashMapIntObject2 = new HashMapIntObject(hashMapIntObject.size());
            Iterator values = hashMapIntObject.values();
            while (values.hasNext()) {
                ClassImpl classImpl5 = (ClassImpl) values.next();
                int i10 = iArr2[classImpl5.getObjectId()];
                classImpl5.setObjectId(i10);
                if (classImpl5.getSuperClassId() >= 0) {
                    classImpl5.setSuperClassIndex(iArr2[classImpl5.getSuperClassId()]);
                }
                classImpl5.setClassLoaderIndex(iArr2[classImpl5.getClassLoaderId()]);
                hashMapIntObject2.put(i10, classImpl5);
            }
            preliminaryIndexImpl.getSnapshotInfo().setNumberOfClasses(hashMapIntObject2.size());
            if (iProgressListener.isCanceled()) {
                throw new OperationCanceledException();
            }
            iProgressListener2.worked(1);
            File file = Index.IDENTIFIER.getFile(preliminaryIndexImpl2.snapshotInfo.getPrefix());
            iProgressListener2.subTask(MessageUtil.format(Messages.GarbageCleaner_Writing, file.getAbsolutePath()));
            indexManager.setReader(Index.IDENTIFIER, new LongIndexStreamer().writeTo(file, jArr));
            if (iProgressListener.isCanceled()) {
                throw new OperationCanceledException();
            }
            iProgressListener2.worked(1);
            File file2 = Index.O2CLASS.getFile(preliminaryIndexImpl2.snapshotInfo.getPrefix());
            iProgressListener2.subTask(MessageUtil.format(Messages.GarbageCleaner_Writing, file2.getAbsolutePath()));
            indexManager.setReader(Index.O2CLASS, new IntIndexStreamer().writeTo(file2, (IteratorInt) new NewObjectIntIterator() {
                /* access modifiers changed from: 0000 */
                public final int doGetNextInt(int i) {
                    return iArr2[preliminaryIndexImpl2.object2classId.get(this.nextIndex)];
                }

                /* access modifiers changed from: 0000 */
                public final int[] getMap() {
                    return iArr2;
                }
            }));
            iOne2OneIndex.close();
            iOne2OneIndex.delete();
            if (iProgressListener.isCanceled()) {
                throw new OperationCanceledException();
            }
            iProgressListener2.worked(1);
            File file3 = Index.A2SIZE.getFile(preliminaryIndexImpl2.snapshotInfo.getPrefix());
            iProgressListener2.subTask(MessageUtil.format(Messages.GarbageCleaner_Writing, file3.getAbsolutePath()));
            final BitField bitField = new BitField(i);
            indexManager.setReader(Index.A2SIZE, new IntIndexStreamer().writeTo(file3, (IteratorInt) new NewObjectIntIterator() {
                IOne2OneIndex a2size = iOne2OneIndex2;
                int newIndex = 0;

                /* access modifiers changed from: 0000 */
                public final int doGetNextInt(int i) {
                    int i2 = this.a2size.get(this.nextIndex);
                    if (i2 > 0) {
                        bitField.set(this.newIndex);
                    }
                    this.newIndex++;
                    return i2;
                }

                /* access modifiers changed from: 0000 */
                public final int[] getMap() {
                    return iArr2;
                }
            }));
            iOne2OneIndex2.close();
            iOne2OneIndex2.delete();
            if (iProgressListener.isCanceled()) {
                throw new OperationCanceledException();
            }
            iProgressListener2.worked(1);
            iProgressListener2.subTask(Messages.GarbageCleaner_ReIndexingOutboundIndex.pattern);
            IntArray1NSortedWriter intArray1NSortedWriter = new IntArray1NSortedWriter(i, Index.OUTBOUND.getFile(preliminaryIndexImpl2.snapshotInfo.getPrefix()));
            InboundWriter inboundWriter = new InboundWriter(i, Index.INBOUND.getFile(preliminaryIndexImpl2.snapshotInfo.getPrefix()));
            int i11 = i9;
            int i12 = 0;
            while (i12 < i11) {
                int i13 = iArr2[i12];
                if (i13 >= 0) {
                    iOne2ManyIndex = iOne2ManyIndex4;
                    int[] iArr3 = iOne2ManyIndex.get(i12);
                    int[] iArr4 = new int[iArr3.length];
                    i2 = i11;
                    int i14 = 0;
                    while (i14 < iArr3.length) {
                        int i15 = iArr2[iArr3[i14]];
                        iArr4[i14] = i15;
                        if (i14 == 0) {
                            iArr = iArr3;
                            z = true;
                        } else {
                            iArr = iArr3;
                            z = false;
                        }
                        inboundWriter.log(i15, i13, z);
                        i14++;
                        iArr3 = iArr;
                    }
                    intArray1NSortedWriter.log(i13, iArr4);
                } else {
                    i2 = i11;
                    iOne2ManyIndex = iOne2ManyIndex4;
                }
                i12++;
                iOne2ManyIndex4 = iOne2ManyIndex;
                i11 = i2;
            }
            IOne2ManyIndex iOne2ManyIndex5 = iOne2ManyIndex4;
            iOne2ManyIndex5.close();
            iOne2ManyIndex5.delete();
            if (iProgressListener.isCanceled()) {
                inboundWriter.cancel();
                intArray1NSortedWriter.cancel();
                throw new OperationCanceledException();
            }
            iProgressListener2.worked(1);
            iProgressListener2.subTask(MessageUtil.format(Messages.GarbageCleaner_Writing, inboundWriter.getIndexFile().getAbsolutePath()));
            indexManager.setReader(Index.INBOUND, inboundWriter.flush(iProgressListener2, new KeyWriterImpl(hashMapIntObject2)));
            if (iProgressListener.isCanceled()) {
                intArray1NSortedWriter.cancel();
                throw new OperationCanceledException();
            }
            iProgressListener2.worked(1);
            iProgressListener2.subTask(MessageUtil.format(Messages.GarbageCleaner_Writing, intArray1NSortedWriter.getIndexFile().getAbsolutePath()));
            indexManager.setReader(Index.OUTBOUND, intArray1NSortedWriter.flush());
            if (iProgressListener.isCanceled()) {
                throw new OperationCanceledException();
            }
            iProgressListener2.worked(1);
            HashMapIntObject<XGCRootInfo[]> fix = fix(preliminaryIndexImpl2.gcRoots, iArr2);
            preliminaryIndexImpl.getSnapshotInfo().setNumberOfGCRoots(fix.size());
            HashMapIntObject hashMapIntObject3 = new HashMapIntObject();
            IteratorInt keys = preliminaryIndexImpl2.thread2objects2roots.keys();
            while (keys.hasNext()) {
                int next = keys.next();
                int i16 = iArr2[next];
                if (i16 >= 0) {
                    hashMapIntObject3.put(i16, fix((HashMapIntObject) preliminaryIndexImpl2.thread2objects2roots.get(next), iArr2));
                }
            }
            SnapshotImplBuilder snapshotImplBuilder3 = snapshotImplBuilder;
            snapshotImplBuilder3.setIndexManager(indexManager);
            snapshotImplBuilder3.setClassCache(hashMapIntObject2);
            snapshotImplBuilder3.setArrayObjects(bitField);
            snapshotImplBuilder3.setRoots(fix);
            snapshotImplBuilder3.setRootsPerThread(hashMapIntObject3);
            preliminaryIndexImpl.delete();
            if (iProgressListener.isCanceled()) {
                indexManager.delete();
            }
            return iArr2;
        } catch (InterruptedException e) {
            IOException iOException = new IOException(e.getMessage());
            iOException.initCause(e);
            throw iOException;
        } catch (Throwable th) {
            Throwable th2 = th;
            preliminaryIndexImpl.delete();
            if (iProgressListener.isCanceled()) {
                indexManager.delete();
            }
            throw th2;
        }
    }

    private static HashMapIntObject<XGCRootInfo[]> fix(HashMapIntObject<List<XGCRootInfo>> hashMapIntObject, int[] iArr) {
        HashMapIntObject<XGCRootInfo[]> hashMapIntObject2 = new HashMapIntObject<>(hashMapIntObject.size());
        Iterator values = hashMapIntObject.values();
        while (values.hasNext()) {
            List list = (List) values.next();
            XGCRootInfo[] xGCRootInfoArr = new XGCRootInfo[list.size()];
            for (int i = 0; i < xGCRootInfoArr.length; i++) {
                xGCRootInfoArr[i] = (XGCRootInfo) list.get(i);
                xGCRootInfoArr[i].setObjectId(iArr[xGCRootInfoArr[i].getObjectId()]);
                if (xGCRootInfoArr[i].getContextAddress() != 0) {
                    xGCRootInfoArr[i].setContextId(iArr[xGCRootInfoArr[i].getContextId()]);
                }
            }
            hashMapIntObject2.put(xGCRootInfoArr[0].getObjectId(), xGCRootInfoArr);
        }
        return hashMapIntObject2;
    }

    private static void markUnreachbleAsGCRoots(PreliminaryIndexImpl preliminaryIndexImpl, boolean[] zArr, int i, int i2, IProgressListener iProgressListener) {
        PreliminaryIndexImpl preliminaryIndexImpl2 = preliminaryIndexImpl;
        boolean[] zArr2 = zArr;
        int length = zArr2.length;
        IOne2LongIndex iOne2LongIndex = preliminaryIndexImpl2.identifiers;
        IOne2ManyIndex iOne2ManyIndex = preliminaryIndexImpl2.outbound;
        int[] iArr = new int[1];
        ObjectMarker objectMarker = new ObjectMarker(iArr, zArr2, iOne2ManyIndex, new SilentProgressListener(iProgressListener));
        boolean[] zArr3 = new boolean[length];
        for (int i3 = 0; i3 < length; i3++) {
            if (!zArr2[i3]) {
                for (int i4 : iOne2ManyIndex.get(i3)) {
                    zArr3[i4] = true;
                }
            }
        }
        int i5 = i;
        int i6 = 0;
        while (i6 < 2) {
            int i7 = i5;
            for (int i8 = 0; i8 < length && i7 < length; i8++) {
                if (!zArr2[i8] && (i6 == 1 || !zArr3[i8])) {
                    iArr[0] = i8;
                    XGCRootInfo xGCRootInfo = new XGCRootInfo(iOne2LongIndex.get(i8), 0, i2);
                    xGCRootInfo.setObjectId(i8);
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(xGCRootInfo);
                    preliminaryIndexImpl2.gcRoots.put(i8, arrayList);
                    i7 += objectMarker.markSingleThreaded();
                }
            }
            i6++;
            i5 = i7;
        }
        preliminaryIndexImpl2.setGcRoots(preliminaryIndexImpl2.gcRoots);
        preliminaryIndexImpl.getSnapshotInfo().setNumberOfGCRoots(preliminaryIndexImpl2.gcRoots.size());
    }
}
