package org.eclipse.mat.snapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.HashMapIntObject;
import org.eclipse.mat.collect.SetInt;
import org.eclipse.mat.snapshot.model.IClass;

public class MultiplePathsFromGCRootsClassRecord {
    private IClass clazz;
    private SetInt distinctObjects;
    private boolean fromRoots;
    private int level;
    /* access modifiers changed from: private */
    public List<int[]> paths = new ArrayList();
    private long referencedSize = -1;
    private ISnapshot snapshot;

    public MultiplePathsFromGCRootsClassRecord(IClass iClass, int i, boolean z, ISnapshot iSnapshot) {
        this.clazz = iClass;
        this.level = i;
        this.fromRoots = z;
        this.snapshot = iSnapshot;
    }

    public MultiplePathsFromGCRootsClassRecord[] nextLevel() throws SnapshotException {
        int i = this.level + 1;
        if (i < 0) {
            return null;
        }
        HashMapIntObject hashMapIntObject = new HashMapIntObject();
        for (int[] next : this.paths) {
            if (next != null) {
                int length = this.fromRoots ? (next.length - i) - 1 : i;
                if (length >= 0 && length < next.length) {
                    IClass classOf = this.snapshot.getClassOf(next[length]);
                    MultiplePathsFromGCRootsClassRecord multiplePathsFromGCRootsClassRecord = (MultiplePathsFromGCRootsClassRecord) hashMapIntObject.get(classOf.getObjectId());
                    if (multiplePathsFromGCRootsClassRecord == null) {
                        multiplePathsFromGCRootsClassRecord = new MultiplePathsFromGCRootsClassRecord(classOf, i, this.fromRoots, this.snapshot);
                        hashMapIntObject.put(classOf.getObjectId(), multiplePathsFromGCRootsClassRecord);
                    }
                    multiplePathsFromGCRootsClassRecord.addPath(next);
                }
            }
        }
        return (MultiplePathsFromGCRootsClassRecord[]) hashMapIntObject.getAllValues(new MultiplePathsFromGCRootsClassRecord[0]);
    }

    public void addPath(int[] iArr) {
        this.paths.add(iArr);
    }

    public List<int[]> getPaths() {
        return this.paths;
    }

    public int getCount() {
        return this.paths.size();
    }

    public int getDistinctCount() {
        if (this.distinctObjects == null) {
            this.distinctObjects = new SetInt();
            for (int[] next : this.paths) {
                this.distinctObjects.add(next[this.fromRoots ? (next.length - this.level) - 1 : this.level]);
            }
        }
        return this.distinctObjects.size();
    }

    public long getReferencedHeapSize() throws SnapshotException {
        if (this.referencedSize == -1) {
            this.referencedSize = this.snapshot.getHeapSize(getReferencedObjects());
        }
        return this.referencedSize;
    }

    public int[] getReferencedObjects() {
        int[] iArr = new int[this.paths.size()];
        int i = 0;
        for (int[] iArr2 : this.paths) {
            iArr[i] = iArr2[0];
            i++;
        }
        return iArr;
    }

    public static Comparator<MultiplePathsFromGCRootsClassRecord> getComparatorByNumberOfReferencedObjects() {
        return new Comparator<MultiplePathsFromGCRootsClassRecord>() {
            public final int compare(MultiplePathsFromGCRootsClassRecord multiplePathsFromGCRootsClassRecord, MultiplePathsFromGCRootsClassRecord multiplePathsFromGCRootsClassRecord2) {
                if (multiplePathsFromGCRootsClassRecord.paths.size() < multiplePathsFromGCRootsClassRecord2.paths.size()) {
                    return 1;
                }
                return multiplePathsFromGCRootsClassRecord.paths.size() > multiplePathsFromGCRootsClassRecord2.paths.size() ? -1 : 0;
            }
        };
    }

    public static Comparator<MultiplePathsFromGCRootsClassRecord> getComparatorByReferencedHeapSize() {
        return new Comparator<MultiplePathsFromGCRootsClassRecord>() {
            public final int compare(MultiplePathsFromGCRootsClassRecord multiplePathsFromGCRootsClassRecord, MultiplePathsFromGCRootsClassRecord multiplePathsFromGCRootsClassRecord2) {
                try {
                    if (multiplePathsFromGCRootsClassRecord.getReferencedHeapSize() < multiplePathsFromGCRootsClassRecord2.getReferencedHeapSize()) {
                        return 1;
                    }
                    if (multiplePathsFromGCRootsClassRecord.getReferencedHeapSize() > multiplePathsFromGCRootsClassRecord2.getReferencedHeapSize()) {
                        return -1;
                    }
                    return 0;
                } catch (SnapshotException unused) {
                    return 0;
                }
            }
        };
    }

    public IClass getClazz() {
        return this.clazz;
    }

    public boolean isFromRoots() {
        return this.fromRoots;
    }

    public int getLevel() {
        return this.level;
    }
}
