package org.eclipse.mat.snapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.HashMapIntObject;

public class MultiplePathsFromGCRootsRecord {
    final int level;
    final int objectId;
    List<int[]> paths;
    long referencedRetainedSize;
    long referencedSize = -1;
    final ISnapshot snapshot;

    public long getReferencedRetainedSize() {
        return this.referencedRetainedSize;
    }

    public void setReferencedRetainedSize(long j) {
        this.referencedRetainedSize = j;
    }

    public MultiplePathsFromGCRootsRecord(int i, int i2, ISnapshot iSnapshot) {
        this.level = i2;
        this.objectId = i;
        this.paths = new ArrayList();
        this.snapshot = iSnapshot;
    }

    public MultiplePathsFromGCRootsRecord[] nextLevel() {
        int i = this.level + 1;
        HashMapIntObject hashMapIntObject = new HashMapIntObject();
        for (int[] next : this.paths) {
            if (next != null && (next.length - i) - 1 >= 0) {
                MultiplePathsFromGCRootsRecord multiplePathsFromGCRootsRecord = (MultiplePathsFromGCRootsRecord) hashMapIntObject.get(next[(next.length - i) - 1]);
                if (multiplePathsFromGCRootsRecord == null) {
                    multiplePathsFromGCRootsRecord = new MultiplePathsFromGCRootsRecord(next[(next.length - i) - 1], i, this.snapshot);
                    hashMapIntObject.put(next[(next.length - i) - 1], multiplePathsFromGCRootsRecord);
                }
                multiplePathsFromGCRootsRecord.addPath(next);
            }
        }
        return (MultiplePathsFromGCRootsRecord[]) hashMapIntObject.getAllValues(new MultiplePathsFromGCRootsRecord[0]);
    }

    public void addPath(int[] iArr) {
        this.paths.add(iArr);
    }

    public List<int[]> getPaths() {
        return this.paths;
    }

    public int getObjectId() {
        return this.objectId;
    }

    public int getCount() {
        return this.paths.size();
    }

    public int getLevel() {
        return this.level;
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

    public long getReferencedHeapSize() throws SnapshotException {
        if (this.referencedSize == -1) {
            this.referencedSize = this.snapshot.getHeapSize(getReferencedObjects());
        }
        return this.referencedSize;
    }

    public static Comparator<MultiplePathsFromGCRootsRecord> getComparatorByNumberOfReferencedObjects() {
        return new Comparator<MultiplePathsFromGCRootsRecord>() {
            public final int compare(MultiplePathsFromGCRootsRecord multiplePathsFromGCRootsRecord, MultiplePathsFromGCRootsRecord multiplePathsFromGCRootsRecord2) {
                if (multiplePathsFromGCRootsRecord.paths.size() < multiplePathsFromGCRootsRecord2.paths.size()) {
                    return 1;
                }
                return multiplePathsFromGCRootsRecord.paths.size() > multiplePathsFromGCRootsRecord2.paths.size() ? -1 : 0;
            }
        };
    }

    public static Comparator<MultiplePathsFromGCRootsRecord> getComparatorByReferencedHeapSize() {
        return new Comparator<MultiplePathsFromGCRootsRecord>() {
            public final int compare(MultiplePathsFromGCRootsRecord multiplePathsFromGCRootsRecord, MultiplePathsFromGCRootsRecord multiplePathsFromGCRootsRecord2) {
                try {
                    if (multiplePathsFromGCRootsRecord.getReferencedHeapSize() < multiplePathsFromGCRootsRecord2.getReferencedHeapSize()) {
                        return 1;
                    }
                    if (multiplePathsFromGCRootsRecord.getReferencedHeapSize() > multiplePathsFromGCRootsRecord2.getReferencedHeapSize()) {
                        return -1;
                    }
                    return 0;
                } catch (SnapshotException unused) {
                    return 0;
                }
            }
        };
    }

    public static Comparator<MultiplePathsFromGCRootsRecord> getComparatorByReferencedRetainedSize() {
        return new Comparator<MultiplePathsFromGCRootsRecord>() {
            public final int compare(MultiplePathsFromGCRootsRecord multiplePathsFromGCRootsRecord, MultiplePathsFromGCRootsRecord multiplePathsFromGCRootsRecord2) {
                if (multiplePathsFromGCRootsRecord.getReferencedRetainedSize() < multiplePathsFromGCRootsRecord2.getReferencedRetainedSize()) {
                    return 1;
                }
                return multiplePathsFromGCRootsRecord.getReferencedRetainedSize() > multiplePathsFromGCRootsRecord2.getReferencedRetainedSize() ? -1 : 0;
            }
        };
    }
}
