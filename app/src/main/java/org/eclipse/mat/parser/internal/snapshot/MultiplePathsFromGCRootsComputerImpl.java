package org.eclipse.mat.parser.internal.snapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayInt;
import org.eclipse.mat.collect.BitField;
import org.eclipse.mat.collect.QueueInt;
import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.parser.index.IIndexReader.IOne2ManyIndex;
import org.eclipse.mat.parser.internal.SnapshotImpl;
import org.eclipse.mat.snapshot.IMultiplePathsFromGCRootsComputer;
import org.eclipse.mat.snapshot.MultiplePathsFromGCRootsClassRecord;
import org.eclipse.mat.snapshot.MultiplePathsFromGCRootsRecord;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.NamedReference;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.IProgressListener.OperationCanceledException;

public class MultiplePathsFromGCRootsComputerImpl implements IMultiplePathsFromGCRootsComputer {
    private static final int NOT_VISITED = -2;
    private static final int NO_PARENT = -1;
    private BitField excludeInstances;
    private Map<IClass, Set<String>> excludeMap;
    int[] objectIds;
    IOne2ManyIndex outboundIndex;
    Object[] paths;
    private boolean pathsCalculated;
    SnapshotImpl snapshot;

    public MultiplePathsFromGCRootsComputerImpl(int[] iArr, Map<IClass, Set<String>> map, SnapshotImpl snapshotImpl) throws SnapshotException {
        this.snapshot = snapshotImpl;
        this.objectIds = iArr;
        this.excludeMap = map;
        this.outboundIndex = snapshotImpl.getIndexManager().outbound;
        if (map != null) {
            initExcludeInstances();
        }
    }

    private void initExcludeInstances() throws SnapshotException {
        this.excludeInstances = new BitField(this.snapshot.getIndexManager().o2address().size());
        for (IClass objectIds2 : this.excludeMap.keySet()) {
            for (int i : objectIds2.getObjectIds()) {
                this.excludeInstances.set(i);
            }
        }
    }

    private void computePaths(IProgressListener iProgressListener) throws SnapshotException {
        ArrayList arrayList = new ArrayList();
        int[] bfs = bfs(iProgressListener);
        for (int pathFromBFS : this.objectIds) {
            int[] pathFromBFS2 = getPathFromBFS(pathFromBFS, bfs);
            if (pathFromBFS2 != null) {
                arrayList.add(pathFromBFS2);
            }
        }
        this.pathsCalculated = true;
        this.paths = arrayList.toArray();
    }

    public MultiplePathsFromGCRootsRecord[] getPathsByGCRoot(IProgressListener iProgressListener) throws SnapshotException {
        if (!this.pathsCalculated) {
            computePaths(iProgressListener);
        }
        MultiplePathsFromGCRootsRecord multiplePathsFromGCRootsRecord = new MultiplePathsFromGCRootsRecord(-1, -1, this.snapshot);
        for (Object obj : this.paths) {
            multiplePathsFromGCRootsRecord.addPath((int[]) obj);
        }
        return multiplePathsFromGCRootsRecord.nextLevel();
    }

    public Object[] getAllPaths(IProgressListener iProgressListener) throws SnapshotException {
        if (!this.pathsCalculated) {
            computePaths(iProgressListener);
        }
        return this.paths;
    }

    public MultiplePathsFromGCRootsClassRecord[] getPathsGroupedByClass(boolean z, IProgressListener iProgressListener) throws SnapshotException {
        if (!this.pathsCalculated) {
            computePaths(iProgressListener);
        }
        MultiplePathsFromGCRootsClassRecord multiplePathsFromGCRootsClassRecord = new MultiplePathsFromGCRootsClassRecord(null, -1, z, this.snapshot);
        for (Object obj : this.paths) {
            multiplePathsFromGCRootsClassRecord.addPath((int[]) obj);
        }
        return multiplePathsFromGCRootsClassRecord.nextLevel();
    }

    private boolean refersOnlyThroughExcluded(int i, int i2) throws SnapshotException {
        if (!this.excludeInstances.get(i)) {
            return false;
        }
        IObject object = this.snapshot.getObject(i);
        Set set = this.excludeMap.get(object.getClazz());
        if (set == null) {
            return true;
        }
        long mapIdToAddress = this.snapshot.mapIdToAddress(i2);
        for (NamedReference next : object.getOutboundReferences()) {
            if (mapIdToAddress == next.getObjectAddress() && !set.contains(next.getName())) {
                return false;
            }
        }
        return true;
    }

    private int[] bfs(IProgressListener iProgressListener) throws SnapshotException {
        int[] iArr;
        int[] gCRoots;
        int[] iArr2;
        IProgressListener iProgressListener2 = iProgressListener;
        int numberOfObjects = this.snapshot.getSnapshotInfo().getNumberOfObjects();
        boolean z = this.excludeMap != null;
        int[] iArr3 = new int[numberOfObjects];
        Arrays.fill(iArr3, -2);
        boolean[] zArr = new boolean[numberOfObjects];
        int i = 0;
        for (int i2 : this.objectIds) {
            if (!zArr[i2]) {
                i++;
            }
            zArr[i2] = true;
        }
        QueueInt queueInt = new QueueInt(numberOfObjects / 8);
        for (int i3 : this.snapshot.getGCRoots()) {
            queueInt.put(i3);
            iArr3[i3] = -1;
        }
        int max = Math.max(10, numberOfObjects / 100);
        iProgressListener2.beginTask(Messages.MultiplePathsFromGCRootsComputerImpl_FindingPaths, 100);
        int i4 = 0;
        while (queueInt.size() > 0 && i > 0) {
            int i5 = queueInt.get();
            if (zArr[i5]) {
                i--;
            }
            for (int i6 : this.outboundIndex.get(i5)) {
                if (iArr3[i6] == -2 && (!z || !refersOnlyThroughExcluded(i5, i6))) {
                    iArr3[i6] = i5;
                    queueInt.put(i6);
                }
            }
            i4++;
            if (i4 % max == 0) {
                if (iProgressListener.isCanceled()) {
                    throw new OperationCanceledException();
                }
                iProgressListener2.worked(1);
            }
        }
        iProgressListener.done();
        return iArr3;
    }

    private int[] getPathFromBFS(int i, int[] iArr) {
        if (iArr[i] == -2) {
            return null;
        }
        ArrayInt arrayInt = new ArrayInt();
        while (i != -1) {
            arrayInt.add(i);
            i = iArr[i];
        }
        return arrayInt.toArray();
    }
}
