package org.eclipse.mat.snapshot;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.model.GCRootInfo;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.IThreadStack;
import org.eclipse.mat.util.IProgressListener;

public interface ISnapshot {
    void dispose();

    IClass getClassOf(int i) throws SnapshotException;

    Collection<IClass> getClasses() throws SnapshotException;

    Collection<IClass> getClassesByName(String str, boolean z) throws SnapshotException;

    Collection<IClass> getClassesByName(Pattern pattern, boolean z) throws SnapshotException;

    DominatorsSummary getDominatorsOf(int[] iArr, Pattern pattern, IProgressListener iProgressListener) throws SnapshotException;

    GCRootInfo[] getGCRootInfo(int i) throws SnapshotException;

    int[] getGCRoots() throws SnapshotException;

    int getHeapSize(int i) throws SnapshotException;

    long getHeapSize(int[] iArr) throws SnapshotException;

    int[] getImmediateDominatedIds(int i) throws SnapshotException;

    int getImmediateDominatorId(int i) throws SnapshotException;

    int[] getInboundRefererIds(int i) throws SnapshotException;

    int[] getInboundRefererIds(int[] iArr, IProgressListener iProgressListener) throws SnapshotException;

    int[] getMinRetainedSet(int[] iArr, IProgressListener iProgressListener) throws SnapshotException;

    long getMinRetainedSize(int[] iArr, IProgressListener iProgressListener) throws SnapshotException;

    IMultiplePathsFromGCRootsComputer getMultiplePathsFromGCRoots(int[] iArr, Map<IClass, Set<String>> map) throws SnapshotException;

    IObject getObject(int i) throws SnapshotException;

    int[] getOutboundReferentIds(int i) throws SnapshotException;

    int[] getOutboundReferentIds(int[] iArr, IProgressListener iProgressListener) throws SnapshotException;

    IPathsFromGCRootsComputer getPathsFromGCRoots(int i, Map<IClass, Set<String>> map) throws SnapshotException;

    long getRetainedHeapSize(int i) throws SnapshotException;

    int[] getRetainedSet(int[] iArr, IProgressListener iProgressListener) throws SnapshotException;

    int[] getRetainedSet(int[] iArr, String[] strArr, IProgressListener iProgressListener) throws SnapshotException;

    int[] getRetainedSet(int[] iArr, ExcludedReferencesDescriptor[] excludedReferencesDescriptorArr, IProgressListener iProgressListener) throws SnapshotException;

    <A> A getSnapshotAddons(Class<A> cls) throws SnapshotException;

    SnapshotInfo getSnapshotInfo();

    IThreadStack getThreadStack(int i) throws SnapshotException;

    int[] getTopAncestorsInDominatorTree(int[] iArr, IProgressListener iProgressListener) throws SnapshotException;

    boolean isArray(int i);

    boolean isClass(int i);

    boolean isClassLoader(int i);

    boolean isGCRoot(int i);

    int mapAddressToId(long j) throws SnapshotException;

    long mapIdToAddress(int i) throws SnapshotException;
}
