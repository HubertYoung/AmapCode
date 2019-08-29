package org.eclipse.mat.parser.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayInt;
import org.eclipse.mat.collect.ArrayIntBig;
import org.eclipse.mat.collect.BitField;
import org.eclipse.mat.collect.HashMapIntObject;
import org.eclipse.mat.collect.IteratorInt;
import org.eclipse.mat.collect.SetInt;
import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.parser.IObjectReader;
import org.eclipse.mat.parser.index.IIndexReader.IOne2ManyIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2OneIndex;
import org.eclipse.mat.parser.index.IndexManager;
import org.eclipse.mat.parser.internal.snapshot.MultiplePathsFromGCRootsComputerImpl;
import org.eclipse.mat.parser.internal.snapshot.ObjectCache;
import org.eclipse.mat.parser.internal.snapshot.ObjectMarker;
import org.eclipse.mat.parser.internal.snapshot.PathsFromGCRootsTreeBuilder;
import org.eclipse.mat.parser.internal.snapshot.RetainedSizeCache;
import org.eclipse.mat.parser.internal.util.IntStack;
import org.eclipse.mat.parser.model.ClassImpl;
import org.eclipse.mat.parser.model.ClassLoaderImpl;
import org.eclipse.mat.parser.model.XGCRootInfo;
import org.eclipse.mat.parser.model.XSnapshotInfo;
import org.eclipse.mat.snapshot.DominatorsSummary;
import org.eclipse.mat.snapshot.DominatorsSummary.ClassDominatorRecord;
import org.eclipse.mat.snapshot.ExcludedReferencesDescriptor;
import org.eclipse.mat.snapshot.IMultiplePathsFromGCRootsComputer;
import org.eclipse.mat.snapshot.IPathsFromGCRootsComputer;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.PathsFromGCRootsTree;
import org.eclipse.mat.snapshot.model.GCRootInfo;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.IThreadStack;
import org.eclipse.mat.snapshot.model.NamedReference;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.IProgressListener.OperationCanceledException;
import org.eclipse.mat.util.MessageUtil;
import org.eclipse.mat.util.VoidProgressListener;

public final class SnapshotImpl implements ISnapshot {
    private BitField arrayObjects;
    private HashMapIntObject<ClassImpl> classCache;
    private Map<String, List<IClass>> classCacheByName;
    private boolean dominatorTreeCalculated;
    /* access modifiers changed from: private */
    public IObjectReader heapObjectReader;
    /* access modifiers changed from: private */
    public IndexManager indexManager;
    private HashMapIntObject<String> loaderLabels;
    private ObjectCache<IObject> objectCache;
    private boolean parsedThreads = false;
    private RetainedSizeCache retainedSizeCache;
    /* access modifiers changed from: private */
    public HashMapIntObject<XGCRootInfo[]> roots;
    private HashMapIntObject<HashMapIntObject<XGCRootInfo[]>> rootsPerThread;
    private XSnapshotInfo snapshotInfo;
    HashMapIntObject<IThreadStack> threadId2stack;

    static final class HeapObjectCache extends ObjectCache<IObject> {
        SnapshotImpl snapshot;

        private HeapObjectCache(SnapshotImpl snapshotImpl, int i) {
            super(i);
            this.snapshot = snapshotImpl;
        }

        /* JADX WARNING: type inference failed for: r0v4, types: [org.eclipse.mat.snapshot.model.IObject] */
        /* JADX WARNING: type inference failed for: r8v3 */
        /* JADX WARNING: type inference failed for: r1v5, types: [org.eclipse.mat.parser.model.InstanceImpl] */
        /* JADX WARNING: type inference failed for: r1v6, types: [org.eclipse.mat.parser.model.ClassLoaderImpl] */
        /* JADX WARNING: type inference failed for: r8v5, types: [org.eclipse.mat.snapshot.model.IObject] */
        /* JADX WARNING: type inference failed for: r0v13 */
        /* JADX WARNING: type inference failed for: r1v8, types: [org.eclipse.mat.parser.model.InstanceImpl] */
        /* JADX WARNING: type inference failed for: r1v9, types: [org.eclipse.mat.parser.model.ClassLoaderImpl] */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v13
          assigns: [?[OBJECT, ARRAY], org.eclipse.mat.parser.model.InstanceImpl, org.eclipse.mat.parser.model.ClassLoaderImpl]
          uses: [?[OBJECT, ARRAY], org.eclipse.mat.snapshot.model.IObject, org.eclipse.mat.parser.model.InstanceImpl, org.eclipse.mat.parser.model.ClassLoaderImpl]
          mth insns count: 40
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
        /* JADX WARNING: Unknown variable types count: 5 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final org.eclipse.mat.snapshot.model.IObject load(int r8) {
            /*
                r7 = this;
                org.eclipse.mat.parser.internal.SnapshotImpl r0 = r7.snapshot     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                boolean r0 = r0.isArray(r8)     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                if (r0 == 0) goto L_0x0016
                org.eclipse.mat.parser.internal.SnapshotImpl r0 = r7.snapshot     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                org.eclipse.mat.parser.IObjectReader r0 = r0.heapObjectReader     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                org.eclipse.mat.parser.internal.SnapshotImpl r1 = r7.snapshot     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                org.eclipse.mat.snapshot.model.IObject r8 = r0.read(r8, r1)     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                r0 = r8
                goto L_0x004a
            L_0x0016:
                org.eclipse.mat.parser.internal.SnapshotImpl r0 = r7.snapshot     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                org.eclipse.mat.parser.internal.SnapshotImpl r1 = r7.snapshot     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                org.eclipse.mat.parser.index.IndexManager r1 = r1.indexManager     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                org.eclipse.mat.parser.index.IIndexReader$IOne2OneIndex r1 = r1.o2class()     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                int r1 = r1.get(r8)     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                org.eclipse.mat.snapshot.model.IObject r0 = r0.getObject(r1)     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                r5 = r0
                org.eclipse.mat.parser.model.ClassImpl r5 = (org.eclipse.mat.parser.model.ClassImpl) r5     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                org.eclipse.mat.parser.internal.SnapshotImpl r0 = r7.snapshot     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                boolean r0 = r0.isClassLoader(r8)     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                if (r0 == 0) goto L_0x0040
                org.eclipse.mat.parser.model.ClassLoaderImpl r0 = new org.eclipse.mat.parser.model.ClassLoaderImpl     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                r3 = -9223372036854775808
                r6 = 0
                r1 = r0
                r2 = r8
                r1.<init>(r2, r3, r5, r6)     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                goto L_0x004a
            L_0x0040:
                org.eclipse.mat.parser.model.InstanceImpl r0 = new org.eclipse.mat.parser.model.InstanceImpl     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                r3 = -9223372036854775808
                r6 = 0
                r1 = r0
                r2 = r8
                r1.<init>(r2, r3, r5, r6)     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
            L_0x004a:
                r8 = r0
                org.eclipse.mat.parser.model.AbstractObjectImpl r8 = (org.eclipse.mat.parser.model.AbstractObjectImpl) r8     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                org.eclipse.mat.parser.internal.SnapshotImpl r1 = r7.snapshot     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                r8.setSnapshot(r1)     // Catch:{ IOException -> 0x005a, SnapshotException -> 0x0053 }
                return r0
            L_0x0053:
                r8 = move-exception
                java.lang.RuntimeException r0 = new java.lang.RuntimeException
                r0.<init>(r8)
                throw r0
            L_0x005a:
                r8 = move-exception
                java.lang.RuntimeException r0 = new java.lang.RuntimeException
                r0.<init>(r8)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.internal.SnapshotImpl.HeapObjectCache.load(int):org.eclipse.mat.snapshot.model.IObject");
        }
    }

    static class Path {
        int index;
        Path next;

        public Path(int i, Path path) {
            this.index = i;
            this.next = path;
        }

        public Path getNext() {
            return this.next;
        }

        public int getIndex() {
            return this.index;
        }

        public boolean contains(long j) {
            for (Path path = this; path != null; path = path.next) {
                if (((long) path.index) == j) {
                    return true;
                }
            }
            return false;
        }
    }

    class PathsFromGCRootsComputerImpl implements IPathsFromGCRootsComputer {
        int currentId;
        Path currentPath;
        int[] currentReferrers;
        int currentReferringThread;
        BitField excludeInstances;
        Map<IClass, Set<String>> excludeMap;
        LinkedList<Path> fifo = new LinkedList<>();
        int[] foundPath;
        IOne2ManyIndex inboundIndex;
        int lastReadReferrer;
        private int nextState;
        int objectId;
        int[] referringThreads;
        private int state;
        BitField visited = new BitField(SnapshotImpl.this.indexManager.o2address().size());

        public PathsFromGCRootsComputerImpl(int i, Map<IClass, Set<String>> map) throws SnapshotException {
            this.objectId = i;
            this.excludeMap = map;
            this.inboundIndex = SnapshotImpl.this.indexManager.inbound();
            if (map != null) {
                initExcludeInstances();
            }
            this.currentId = i;
            this.visited.set(i);
            if (SnapshotImpl.this.roots.get(i) == null) {
                this.fifo.add(new Path(i, null));
            }
        }

        private void initExcludeInstances() throws SnapshotException {
            this.excludeInstances = new BitField(SnapshotImpl.this.indexManager.o2address().size());
            for (IClass objectIds : this.excludeMap.keySet()) {
                for (int i : objectIds.getObjectIds()) {
                    this.excludeInstances.set(i);
                }
            }
        }

        private boolean refersOnlyThroughExcluded(int i, int i2) throws SnapshotException {
            if (!this.excludeInstances.get(i)) {
                return false;
            }
            IObject object = SnapshotImpl.this.getObject(i);
            Set set = this.excludeMap.get(object.getClazz());
            if (set == null) {
                return true;
            }
            long mapIdToAddress = SnapshotImpl.this.mapIdToAddress(i2);
            for (NamedReference next : object.getOutboundReferences()) {
                if (mapIdToAddress == next.getObjectAddress() && !set.contains(next.getName())) {
                    return false;
                }
            }
            return true;
        }

        public int[] getNextShortestPath() throws SnapshotException {
            switch (this.state) {
                case 0:
                    if (SnapshotImpl.this.roots.containsKey(this.currentId)) {
                        this.referringThreads = null;
                        this.state = 2;
                        this.nextState = 1;
                        this.foundPath = new int[]{this.currentId};
                        return getNextShortestPath();
                    }
                    this.state = 3;
                    return getNextShortestPath();
                case 1:
                    return null;
                case 2:
                    if (this.referringThreads == null) {
                        this.referringThreads = getReferringTreads(SnapshotImpl.this.getGCRootInfo(this.foundPath[this.foundPath.length - 1]));
                        this.currentReferringThread = 0;
                        if (this.referringThreads.length == 0) {
                            this.state = this.nextState;
                            return this.foundPath;
                        }
                    }
                    if (this.currentReferringThread < this.referringThreads.length) {
                        int[] iArr = new int[(this.foundPath.length + 1)];
                        System.arraycopy(this.foundPath, 0, iArr, 0, this.foundPath.length);
                        iArr[iArr.length - 1] = this.referringThreads[this.currentReferringThread];
                        this.currentReferringThread++;
                        return iArr;
                    }
                    this.state = this.nextState;
                    return getNextShortestPath();
                case 3:
                    if (this.currentReferrers != null) {
                        int[] processCurrentReferrefs = processCurrentReferrefs(this.lastReadReferrer + 1);
                        if (processCurrentReferrefs != null) {
                            return processCurrentReferrefs;
                        }
                    }
                    while (this.fifo.size() > 0) {
                        this.currentPath = this.fifo.getFirst();
                        this.fifo.removeFirst();
                        this.currentId = this.currentPath.getIndex();
                        this.currentReferrers = this.inboundIndex.get(this.currentId);
                        if (this.currentReferrers != null) {
                            int[] processCurrentReferrefs2 = processCurrentReferrefs(0);
                            if (processCurrentReferrefs2 != null) {
                                return processCurrentReferrefs2;
                            }
                        }
                    }
                    return null;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append(Messages.SnapshotImpl_Error_UnrecognizedState.pattern);
                    sb.append(this.state);
                    throw new RuntimeException(sb.toString());
            }
        }

        private int[] getReferringTreads(GCRootInfo[] gCRootInfoArr) {
            SetInt setInt = new SetInt();
            for (GCRootInfo gCRootInfo : gCRootInfoArr) {
                if (!(gCRootInfo.getContextAddress() == 0 || gCRootInfo.getObjectAddress() == gCRootInfo.getContextAddress())) {
                    setInt.add(gCRootInfo.getContextId());
                }
            }
            return setInt.toArray();
        }

        public PathsFromGCRootsTree getTree(Collection<int[]> collection) {
            PathsFromGCRootsTreeBuilder pathsFromGCRootsTreeBuilder = new PathsFromGCRootsTreeBuilder(this.objectId);
            for (int[] next : collection) {
                PathsFromGCRootsTreeBuilder pathsFromGCRootsTreeBuilder2 = pathsFromGCRootsTreeBuilder;
                for (int i = 1; i < next.length; i++) {
                    int i2 = next[i];
                    PathsFromGCRootsTreeBuilder pathsFromGCRootsTreeBuilder3 = pathsFromGCRootsTreeBuilder2.getObjectReferers().get(Integer.valueOf(i2));
                    if (pathsFromGCRootsTreeBuilder3 == null) {
                        pathsFromGCRootsTreeBuilder3 = new PathsFromGCRootsTreeBuilder(i2);
                        pathsFromGCRootsTreeBuilder2.addObjectReferer(pathsFromGCRootsTreeBuilder3);
                    }
                    pathsFromGCRootsTreeBuilder2 = pathsFromGCRootsTreeBuilder3;
                }
            }
            return pathsFromGCRootsTreeBuilder.toPathsFromGCRootsTree();
        }

        private int[] path2Int(Path path) {
            IntStack intStack = new IntStack();
            while (path != null) {
                intStack.push(path.getIndex());
                path = path.getNext();
            }
            int[] iArr = new int[intStack.size()];
            for (int i = 0; i < iArr.length; i++) {
                iArr[i] = intStack.pop();
            }
            return iArr;
        }

        private int[] processCurrentReferrefs(int i) throws SnapshotException {
            int[] iArr;
            while (i < this.currentReferrers.length) {
                if (((GCRootInfo[]) SnapshotImpl.this.roots.get(this.currentReferrers[i])) != null) {
                    if (this.excludeMap == null) {
                        this.lastReadReferrer = i;
                        Path path = new Path(this.currentReferrers[i], this.currentPath);
                        this.referringThreads = null;
                        this.state = 2;
                        this.nextState = 3;
                        this.foundPath = path2Int(path);
                        return getNextShortestPath();
                    } else if (!refersOnlyThroughExcluded(this.currentReferrers[i], this.currentId)) {
                        this.lastReadReferrer = i;
                        Path path2 = new Path(this.currentReferrers[i], this.currentPath);
                        this.referringThreads = null;
                        this.state = 2;
                        this.nextState = 3;
                        this.foundPath = path2Int(path2);
                        return getNextShortestPath();
                    }
                }
                i++;
            }
            for (int i2 : this.currentReferrers) {
                if (i2 >= 0 && !this.visited.get(i2) && !SnapshotImpl.this.roots.containsKey(i2)) {
                    if (this.excludeMap == null) {
                        this.fifo.add(new Path(i2, this.currentPath));
                        this.visited.set(i2);
                    } else if (!refersOnlyThroughExcluded(i2, this.currentId)) {
                        this.fifo.add(new Path(i2, this.currentPath));
                        this.visited.set(i2);
                    }
                }
            }
            return null;
        }
    }

    public static SnapshotImpl create(XSnapshotInfo xSnapshotInfo, IObjectReader iObjectReader, HashMapIntObject<ClassImpl> hashMapIntObject, HashMapIntObject<XGCRootInfo[]> hashMapIntObject2, HashMapIntObject<HashMapIntObject<XGCRootInfo[]>> hashMapIntObject3, BitField bitField, IndexManager indexManager2) throws IOException, SnapshotException {
        SnapshotImpl snapshotImpl = new SnapshotImpl(xSnapshotInfo, iObjectReader, hashMapIntObject, hashMapIntObject2, hashMapIntObject3, null, bitField, indexManager2);
        snapshotImpl.calculateLoaderLabels();
        return snapshotImpl;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0072, code lost:
        r0 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private SnapshotImpl(org.eclipse.mat.parser.model.XSnapshotInfo r2, org.eclipse.mat.parser.IObjectReader r3, org.eclipse.mat.collect.HashMapIntObject<org.eclipse.mat.parser.model.ClassImpl> r4, org.eclipse.mat.collect.HashMapIntObject<org.eclipse.mat.parser.model.XGCRootInfo[]> r5, org.eclipse.mat.collect.HashMapIntObject<org.eclipse.mat.collect.HashMapIntObject<org.eclipse.mat.parser.model.XGCRootInfo[]>> r6, org.eclipse.mat.collect.HashMapIntObject<java.lang.String> r7, org.eclipse.mat.collect.BitField r8, org.eclipse.mat.parser.index.IndexManager r9) throws org.eclipse.mat.SnapshotException, java.io.IOException {
        /*
            r1 = this;
            r1.<init>()
            r0 = 0
            r1.parsedThreads = r0
            r1.snapshotInfo = r2
            r1.heapObjectReader = r3
            r1.classCache = r4
            r1.roots = r5
            r1.rootsPerThread = r6
            r1.loaderLabels = r7
            r1.arrayObjects = r8
            r1.indexManager = r9
            org.eclipse.mat.parser.internal.snapshot.RetainedSizeCache r3 = new org.eclipse.mat.parser.internal.snapshot.RetainedSizeCache
            r3.<init>(r2)
            r1.retainedSizeCache = r3
            java.util.HashMap r2 = new java.util.HashMap
            org.eclipse.mat.collect.HashMapIntObject<org.eclipse.mat.parser.model.ClassImpl> r3 = r1.classCache
            int r3 = r3.size()
            r2.<init>(r3)
            r1.classCacheByName = r2
            org.eclipse.mat.collect.HashMapIntObject<org.eclipse.mat.parser.model.ClassImpl> r2 = r1.classCache
            java.util.Iterator r2 = r2.values()
        L_0x0030:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0060
            java.lang.Object r3 = r2.next()
            org.eclipse.mat.parser.model.ClassImpl r3 = (org.eclipse.mat.parser.model.ClassImpl) r3
            r3.setSnapshot(r1)
            java.util.Map<java.lang.String, java.util.List<org.eclipse.mat.snapshot.model.IClass>> r4 = r1.classCacheByName
            java.lang.String r5 = r3.getName()
            java.lang.Object r4 = r4.get(r5)
            java.util.List r4 = (java.util.List) r4
            if (r4 != 0) goto L_0x005c
            java.util.Map<java.lang.String, java.util.List<org.eclipse.mat.snapshot.model.IClass>> r4 = r1.classCacheByName
            java.lang.String r5 = r3.getName()
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r4.put(r5, r6)
            r4 = r6
        L_0x005c:
            r4.add(r3)
            goto L_0x0030
        L_0x0060:
            org.eclipse.mat.parser.index.IIndexReader$IOne2ManyIndex r2 = r9.dominated()
            if (r2 == 0) goto L_0x0073
            org.eclipse.mat.parser.index.IIndexReader$IOne2LongIndex r2 = r9.o2retained()
            if (r2 == 0) goto L_0x0073
            org.eclipse.mat.parser.index.IIndexReader$IOne2OneIndex r2 = r9.dominator()
            if (r2 == 0) goto L_0x0073
            r0 = 1
        L_0x0073:
            r1.dominatorTreeCalculated = r0
            org.eclipse.mat.parser.internal.SnapshotImpl$HeapObjectCache r2 = new org.eclipse.mat.parser.internal.SnapshotImpl$HeapObjectCache
            r3 = 1000(0x3e8, float:1.401E-42)
            r4 = 0
            r2.<init>(r3)
            r1.objectCache = r2
            org.eclipse.mat.parser.IObjectReader r2 = r1.heapObjectReader
            r2.open(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.internal.SnapshotImpl.<init>(org.eclipse.mat.parser.model.XSnapshotInfo, org.eclipse.mat.parser.IObjectReader, org.eclipse.mat.collect.HashMapIntObject, org.eclipse.mat.collect.HashMapIntObject, org.eclipse.mat.collect.HashMapIntObject, org.eclipse.mat.collect.HashMapIntObject, org.eclipse.mat.collect.BitField, org.eclipse.mat.parser.index.IndexManager):void");
    }

    private void calculateLoaderLabels() throws SnapshotException {
        int[] objectIds;
        String str;
        String str2;
        this.loaderLabels = new HashMapIntObject<>();
        int reverse = this.indexManager.o2address().reverse(0);
        Object[] allValues = this.classCache.getAllValues();
        long j = 0;
        for (Object obj : allValues) {
            ClassImpl classImpl = (ClassImpl) obj;
            j += classImpl.getTotalSize();
            int classLoaderId = classImpl.getClassLoaderId();
            if (((String) this.loaderLabels.get(classLoaderId)) == null) {
                if (classLoaderId == reverse) {
                    str2 = "<system class loader>";
                } else {
                    str2 = getObject(classLoaderId).getClassSpecificName();
                    if (str2 == null) {
                        str2 = ClassLoaderImpl.NO_LABEL;
                    }
                }
                this.loaderLabels.put(classLoaderId, str2);
            }
        }
        Collection<IClass> classesByName = getClassesByName((String) IClass.JAVA_LANG_CLASSLOADER, true);
        if (classesByName != null) {
            for (IClass objectIds2 : classesByName) {
                for (int i : objectIds2.getObjectIds()) {
                    if (((String) this.loaderLabels.get(i)) == null) {
                        if (i == reverse) {
                            str = "<system class loader>";
                        } else {
                            str = getObject(i).getClassSpecificName();
                            if (str == null) {
                                str = ClassLoaderImpl.NO_LABEL;
                            }
                        }
                        this.loaderLabels.put(i, str);
                    }
                }
            }
        }
        this.snapshotInfo.setUsedHeapSize(j);
        this.snapshotInfo.setNumberOfObjects(this.indexManager.idx.size());
        this.snapshotInfo.setNumberOfClassLoaders(this.loaderLabels.size());
        this.snapshotInfo.setNumberOfGCRoots(this.roots.size());
        this.snapshotInfo.setNumberOfClasses(this.classCache.size());
        this.objectCache.clear();
    }

    public final XSnapshotInfo getSnapshotInfo() {
        return this.snapshotInfo;
    }

    public final int[] getGCRoots() throws SnapshotException {
        return this.roots.getAllKeys();
    }

    public final Collection<IClass> getClasses() throws SnapshotException {
        return Arrays.asList(this.classCache.getAllValues(new IClass[this.classCache.size()]));
    }

    public final Collection<IClass> getClassesByName(String str, boolean z) throws SnapshotException {
        List<IClass> list = this.classCacheByName.get(str);
        if (list == null) {
            return null;
        }
        if (!z) {
            return Collections.unmodifiableCollection(list);
        }
        HashSet hashSet = new HashSet();
        hashSet.addAll(list);
        for (IClass allSubclasses : list) {
            hashSet.addAll(allSubclasses.getAllSubclasses());
        }
        return hashSet;
    }

    public final Collection<IClass> getClassesByName(Pattern pattern, boolean z) throws SnapshotException {
        HashSet hashSet = new HashSet();
        Object[] allValues = this.classCache.getAllValues();
        for (Object obj : allValues) {
            IClass iClass = (IClass) obj;
            if (pattern.matcher(iClass.getName()).matches()) {
                hashSet.add(iClass);
                if (z) {
                    hashSet.addAll(iClass.getAllSubclasses());
                }
            }
        }
        return hashSet;
    }

    public final int[] getInboundRefererIds(int i) throws SnapshotException {
        return this.indexManager.inbound().get(i);
    }

    public final int[] getOutboundReferentIds(int i) throws SnapshotException {
        return this.indexManager.outbound().get(i);
    }

    public final int[] getInboundRefererIds(int[] iArr, IProgressListener iProgressListener) throws SnapshotException {
        if (iProgressListener == null) {
            iProgressListener = new VoidProgressListener();
        }
        IOne2ManyIndex inbound = this.indexManager.inbound();
        SetInt setInt = new SetInt();
        iProgressListener.beginTask(Messages.SnapshotImpl_ReadingInboundReferrers, iArr.length / 100);
        for (int i = 0; i < iArr.length; i++) {
            for (int add : inbound.get(iArr[i])) {
                setInt.add(add);
            }
            if (i % 100 == 0) {
                if (iProgressListener.isCanceled()) {
                    return null;
                }
                iProgressListener.worked(1);
            }
        }
        int[] array = setInt.toArray();
        iProgressListener.done();
        return array;
    }

    public final int[] getOutboundReferentIds(int[] iArr, IProgressListener iProgressListener) throws SnapshotException {
        if (iProgressListener == null) {
            iProgressListener = new VoidProgressListener();
        }
        IOne2ManyIndex outbound = this.indexManager.outbound();
        SetInt setInt = new SetInt();
        iProgressListener.beginTask(Messages.SnapshotImpl_ReadingOutboundReferrers, iArr.length / 100);
        for (int i = 0; i < iArr.length; i++) {
            for (int add : outbound.get(iArr[i])) {
                setInt.add(add);
            }
            if (i % 100 == 0) {
                if (iProgressListener.isCanceled()) {
                    return null;
                }
                iProgressListener.worked(1);
            }
        }
        int[] array = setInt.toArray();
        iProgressListener.done();
        return array;
    }

    public final IPathsFromGCRootsComputer getPathsFromGCRoots(int i, Map<IClass, Set<String>> map) throws SnapshotException {
        return new PathsFromGCRootsComputerImpl(i, map);
    }

    public final IMultiplePathsFromGCRootsComputer getMultiplePathsFromGCRoots(int[] iArr, Map<IClass, Set<String>> map) throws SnapshotException {
        return new MultiplePathsFromGCRootsComputerImpl(iArr, map, this);
    }

    /* access modifiers changed from: 0000 */
    public final int[] getRetainedSetSingleThreaded(int[] iArr, IProgressListener iProgressListener) throws SnapshotException {
        if (iArr.length == 0) {
            return new int[0];
        }
        if (iArr.length == 1) {
            return getSingleObjectRetainedSet(iArr[0]);
        }
        int numberOfObjects = this.snapshotInfo.getNumberOfObjects();
        if (iProgressListener == null) {
            iProgressListener = new VoidProgressListener();
        }
        boolean[] zArr = new boolean[numberOfObjects];
        for (int i : iArr) {
            zArr[i] = true;
        }
        try {
            int[] iArr2 = new int[(numberOfObjects - new ObjectMarker(this.roots.getAllKeys(), zArr, this.indexManager.outbound(), iProgressListener).markSingleThreaded())];
            for (int i2 : iArr) {
                zArr[i2] = false;
            }
            int i3 = 0;
            for (int i4 = 0; i4 < numberOfObjects; i4++) {
                if (!zArr[i4]) {
                    iArr2[i3] = i4;
                    i3++;
                }
            }
            return iArr2;
        } catch (OperationCanceledException unused) {
            return null;
        }
    }

    private int[] getRetainedSetMultiThreaded(int[] iArr, int i, IProgressListener iProgressListener) throws SnapshotException {
        if (iArr.length == 0) {
            return new int[0];
        }
        if (iArr.length == 1) {
            return getSingleObjectRetainedSet(iArr[0]);
        }
        int numberOfObjects = this.snapshotInfo.getNumberOfObjects();
        if (iProgressListener == null) {
            iProgressListener = new VoidProgressListener();
        }
        boolean[] zArr = new boolean[numberOfObjects];
        for (int i2 : iArr) {
            zArr[i2] = true;
        }
        try {
            new ObjectMarker(this.roots.getAllKeys(), zArr, this.indexManager.outbound(), iProgressListener).markMultiThreaded(i);
            for (int i3 : iArr) {
                zArr[i3] = false;
            }
            ArrayIntBig arrayIntBig = new ArrayIntBig();
            for (int i4 = 0; i4 < numberOfObjects; i4++) {
                if (!zArr[i4]) {
                    arrayIntBig.add(i4);
                }
            }
            return arrayIntBig.toArray();
        } catch (InterruptedException e) {
            throw new SnapshotException((Throwable) e);
        }
    }

    public final int[] getRetainedSet(int[] iArr, IProgressListener iProgressListener) throws SnapshotException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        if (availableProcessors > 1) {
            return getRetainedSetMultiThreaded(iArr, availableProcessors, iProgressListener);
        }
        return getRetainedSetSingleThreaded(iArr, iProgressListener);
    }

    public final int[] getRetainedSet(int[] iArr, String[] strArr, IProgressListener iProgressListener) throws SnapshotException {
        if (iArr.length == 0) {
            return new int[0];
        }
        int size = this.indexManager.o2address().size();
        if (iProgressListener == null) {
            iProgressListener = new VoidProgressListener();
        }
        BitField bitField = new BitField(size);
        for (int i : iArr) {
            bitField.set(i);
        }
        if (iProgressListener.isCanceled()) {
            return null;
        }
        BitField bitField2 = new BitField(size);
        int[] iArr2 = new int[(size - dfs2(bitField2, bitField, strArr))];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (!bitField2.get(i3)) {
                iArr2[i2] = i3;
                i2++;
            }
        }
        return iArr2;
    }

    public final int[] getRetainedSet(int[] iArr, ExcludedReferencesDescriptor[] excludedReferencesDescriptorArr, IProgressListener iProgressListener) throws SnapshotException {
        boolean[] zArr = new boolean[getSnapshotInfo().getNumberOfObjects()];
        for (int i : iArr) {
            zArr[i] = true;
        }
        new ObjectMarker(getGCRoots(), zArr, getIndexManager().outbound, new VoidProgressListener()).markSingleThreaded(excludedReferencesDescriptorArr, this);
        for (int i2 : iArr) {
            zArr[i2] = false;
        }
        boolean[] zArr2 = new boolean[zArr.length];
        System.arraycopy(zArr, 0, zArr2, 0, zArr.length);
        new ObjectMarker(iArr, zArr2, getIndexManager().outbound, new VoidProgressListener()).markSingleThreaded();
        int numberOfObjects = getSnapshotInfo().getNumberOfObjects();
        ArrayIntBig arrayIntBig = new ArrayIntBig();
        for (int i3 = 0; i3 < numberOfObjects; i3++) {
            if (!zArr[i3] && zArr2[i3]) {
                arrayIntBig.add(i3);
            }
        }
        return arrayIntBig.toArray();
    }

    public final long getMinRetainedSize(int[] iArr, IProgressListener iProgressListener) throws UnsupportedOperationException, SnapshotException {
        if (iArr.length == 1) {
            return getRetainedHeapSize(iArr[0]);
        }
        long j = 0;
        if (iArr.length == 0) {
            return 0;
        }
        for (int retainedHeapSize : getTopAncestorsInDominatorTree(iArr, iProgressListener)) {
            j += getRetainedHeapSize(retainedHeapSize);
        }
        return j;
    }

    public final int[] getMinRetainedSet(int[] iArr, IProgressListener iProgressListener) throws UnsupportedOperationException, SnapshotException {
        int i;
        boolean z;
        int i2;
        int[] iArr2;
        int i3;
        int[] iArr3 = iArr;
        int i4 = 1;
        if (iArr3.length == 1) {
            return getSingleObjectRetainedSet(iArr3[0]);
        }
        SetInt setInt = new SetInt(iArr3.length * 2);
        for (int add : iArr3) {
            setInt.add(add);
        }
        SetInt setInt2 = new SetInt(iArr3.length * 2);
        IOne2OneIndex dominator = this.indexManager.dominator();
        IOne2ManyIndex dominated = this.indexManager.dominated();
        int length = iArr3.length;
        int[] iArr4 = new int[10240];
        int[] iArr5 = new int[10240];
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 10240;
        int i9 = 0;
        int i10 = 10240;
        while (i5 < length) {
            int i11 = iArr3[i5];
            i6 += i4;
            if ((i6 & 65535) != 0 || !iProgressListener.isCanceled()) {
                int[] iArr6 = iArr4;
                int i12 = i8;
                int i13 = i7;
                int i14 = dominator.get(i11) - 2;
                while (true) {
                    if (i14 >= 0) {
                        if (i13 == i12) {
                            int i15 = i12 << 1;
                            int[] iArr7 = new int[i15];
                            System.arraycopy(iArr6, 0, iArr7, 0, i12);
                            iArr6 = iArr7;
                            i12 = i15;
                        }
                        int i16 = i13 + 1;
                        iArr6[i13] = i14;
                        if (!setInt.contains(i14)) {
                            if (setInt2.contains(i14)) {
                                i13 = i16;
                                break;
                            }
                            i14 = dominator.get(i14) - 2;
                            i13 = i16;
                            int[] iArr8 = iArr;
                        } else {
                            i13 = i16;
                            i = i12;
                            z = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                i = i12;
                z = true;
                iArr4 = iArr6;
                if (z) {
                    while (i13 > 0) {
                        i13--;
                        setInt2.add(iArr4[i13]);
                    }
                    int i17 = i9 + 1;
                    iArr5[i9] = i11;
                    while (i17 > 0) {
                        int i18 = i17 - 1;
                        int i19 = iArr5[i18];
                        setInt.add(i19);
                        int[] iArr9 = dominated.get(i19 + 1);
                        int length2 = iArr9.length;
                        int i20 = i18;
                        int[] iArr10 = iArr5;
                        int i21 = i10;
                        int i22 = 0;
                        while (i22 < length2) {
                            int i23 = iArr9[i22];
                            if (i20 == i21) {
                                i3 = i;
                                int i24 = i21 << 1;
                                iArr2 = iArr9;
                                int[] iArr11 = new int[i24];
                                System.arraycopy(iArr10, 0, iArr11, 0, i21);
                                iArr10 = iArr11;
                                i21 = i24;
                            } else {
                                i3 = i;
                                iArr2 = iArr9;
                            }
                            iArr10[i20] = i23;
                            i22++;
                            i20++;
                            i = i3;
                            iArr9 = iArr2;
                        }
                        int i25 = i;
                        i17 = i20;
                        i10 = i21;
                        iArr5 = iArr10;
                    }
                    i2 = i;
                    i9 = i17;
                } else {
                    i2 = i;
                }
                i7 = i13;
                i5++;
                i8 = i2;
                iArr3 = iArr;
                i4 = 1;
            } else {
                throw new OperationCanceledException();
            }
        }
        return setInt.toArray();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b3, code lost:
        r11 = r15;
        r17 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int[] getTopAncestorsInDominatorTree(int[] r20, org.eclipse.mat.util.IProgressListener r21) throws org.eclipse.mat.SnapshotException {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            boolean r2 = r19.isDominatorTreeCalculated()
            if (r2 != 0) goto L_0x0012
            org.eclipse.mat.SnapshotException r1 = new org.eclipse.mat.SnapshotException
            org.eclipse.mat.hprof.Messages r2 = org.eclipse.mat.hprof.Messages.SnapshotImpl_Error_DomTreeNotAvailable
            r1.<init>(r2)
            throw r1
        L_0x0012:
            if (r21 != 0) goto L_0x001a
            org.eclipse.mat.util.VoidProgressListener r2 = new org.eclipse.mat.util.VoidProgressListener
            r2.<init>()
            goto L_0x001c
        L_0x001a:
            r2 = r21
        L_0x001c:
            int r3 = r1.length
            r4 = 1000000(0xf4240, float:1.401298E-39)
            if (r3 <= r4) goto L_0x0027
            int[] r1 = r0.getTopAncestorsWithBooleanCache(r1, r2)
            return r1
        L_0x0027:
            org.eclipse.mat.collect.SetInt r3 = new org.eclipse.mat.collect.SetInt
            int r4 = r1.length
            r3.<init>(r4)
            org.eclipse.mat.collect.SetInt r4 = new org.eclipse.mat.collect.SetInt
            int r5 = r1.length
            int r5 = r5 * 2
            r4.<init>(r5)
            int r5 = r1.length
            r6 = 0
            r7 = 0
        L_0x0038:
            if (r7 >= r5) goto L_0x0042
            r8 = r1[r7]
            r4.add(r8)
            int r7 = r7 + 1
            goto L_0x0038
        L_0x0042:
            org.eclipse.mat.collect.ArrayInt r5 = new org.eclipse.mat.collect.ArrayInt
            r5.<init>()
            r7 = 10240(0x2800, float:1.4349E-41)
            int[] r8 = new int[r7]
            org.eclipse.mat.parser.index.IndexManager r9 = r0.indexManager
            org.eclipse.mat.parser.index.IIndexReader$IOne2OneIndex r9 = r9.dominator()
            int r10 = r1.length
            r11 = r8
            r7 = 0
            r8 = 0
            r12 = 0
            r13 = 10240(0x2800, float:1.4349E-41)
        L_0x0058:
            if (r7 >= r10) goto L_0x00cc
            r14 = r1[r7]
            r15 = 1
            int r8 = r8 + r15
            r16 = 65535(0xffff, float:9.1834E-41)
            r16 = r8 & r16
            if (r16 != 0) goto L_0x0071
            boolean r16 = r2.isCanceled()
            if (r16 == 0) goto L_0x0071
            org.eclipse.mat.util.IProgressListener$OperationCanceledException r1 = new org.eclipse.mat.util.IProgressListener$OperationCanceledException
            r1.<init>()
            throw r1
        L_0x0071:
            int r16 = r9.get(r14)
            int r16 = r16 + -2
            r15 = r11
            r11 = r16
        L_0x007a:
            if (r11 < 0) goto L_0x00b3
            if (r12 != r13) goto L_0x0087
            int r0 = r13 << 1
            int[] r1 = new int[r0]
            java.lang.System.arraycopy(r15, r6, r1, r6, r13)
            r13 = r0
            r15 = r1
        L_0x0087:
            int r0 = r12 + 1
            r15[r12] = r11
            boolean r1 = r4.contains(r11)
            if (r1 == 0) goto L_0x00a0
            r12 = r0
        L_0x0092:
            if (r12 <= 0) goto L_0x009c
            int r12 = r12 + -1
            r0 = r15[r12]
            r4.add(r0)
            goto L_0x0092
        L_0x009c:
            r11 = r15
            r17 = 0
            goto L_0x00b6
        L_0x00a0:
            boolean r1 = r3.contains(r11)
            if (r1 != 0) goto L_0x00b2
            int r1 = r9.get(r11)
            int r11 = r1 + -2
            r12 = r0
            r0 = r19
            r1 = r20
            goto L_0x007a
        L_0x00b2:
            r12 = r0
        L_0x00b3:
            r11 = r15
            r17 = 1
        L_0x00b6:
            if (r17 == 0) goto L_0x00c5
            r5.add(r14)
        L_0x00bb:
            if (r12 <= 0) goto L_0x00c5
            int r12 = r12 + -1
            r0 = r11[r12]
            r3.add(r0)
            goto L_0x00bb
        L_0x00c5:
            int r7 = r7 + 1
            r0 = r19
            r1 = r20
            goto L_0x0058
        L_0x00cc:
            int[] r0 = r5.toArray()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.internal.SnapshotImpl.getTopAncestorsInDominatorTree(int[], org.eclipse.mat.util.IProgressListener):int[]");
    }

    private int[] getTopAncestorsWithBooleanCache(int[] iArr, IProgressListener iProgressListener) {
        int i;
        boolean z;
        int[] iArr2 = iArr;
        boolean[] zArr = new boolean[this.snapshotInfo.getNumberOfObjects()];
        boolean[] zArr2 = new boolean[this.snapshotInfo.getNumberOfObjects()];
        int length = iArr2.length;
        int i2 = 0;
        while (true) {
            i = 1;
            if (i2 >= length) {
                break;
            }
            zArr2[iArr2[i2]] = true;
            i2++;
        }
        ArrayInt arrayInt = new ArrayInt();
        IOne2OneIndex dominator = this.indexManager.dominator();
        int length2 = iArr2.length;
        int[] iArr3 = new int[10240];
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 10240;
        while (i3 < length2) {
            int i7 = iArr2[i3];
            i4 += i;
            if ((65535 & i4) != 0 || !iProgressListener.isCanceled()) {
                int i8 = dominator.get(i7) - 2;
                while (true) {
                    if (i8 >= 0) {
                        if (i5 == i6) {
                            int i9 = i6 << 1;
                            int[] iArr4 = new int[i9];
                            System.arraycopy(iArr3, 0, iArr4, 0, i6);
                            iArr3 = iArr4;
                            i6 = i9;
                        }
                        int i10 = i5 + 1;
                        iArr3[i5] = i8;
                        if (!zArr2[i8]) {
                            if (zArr[i8]) {
                                i5 = i10;
                                break;
                            }
                            i8 = dominator.get(i8) - 2;
                            i5 = i10;
                        } else {
                            i5 = i10;
                            while (i5 > 0) {
                                i5--;
                                zArr2[iArr3[i5]] = true;
                            }
                            z = false;
                        }
                    } else {
                        break;
                    }
                }
                z = true;
                if (z) {
                    arrayInt.add(i7);
                    while (i5 > 0) {
                        i5--;
                        zArr[iArr3[i5]] = true;
                    }
                }
                i = 1;
                i3++;
            } else {
                throw new OperationCanceledException();
            }
        }
        return arrayInt.toArray();
    }

    private boolean isDominatorTreeCalculated() {
        return this.dominatorTreeCalculated;
    }

    public final void calculateDominatorTree(IProgressListener iProgressListener) throws SnapshotException, OperationCanceledException {
        try {
            DominatorTree.calculate(this, iProgressListener);
            this.dominatorTreeCalculated = (this.indexManager.dominated() == null || this.indexManager.o2retained() == null || this.indexManager.dominator() == null) ? false : true;
        } catch (IOException e) {
            throw new SnapshotException((Throwable) e);
        }
    }

    public final int[] getImmediateDominatedIds(int i) throws SnapshotException {
        if (isDominatorTreeCalculated()) {
            return this.indexManager.dominated().get(i + 1);
        }
        throw new SnapshotException(Messages.SnapshotImpl_Error_DomTreeNotAvailable);
    }

    public final int getImmediateDominatorId(int i) throws SnapshotException {
        if (isDominatorTreeCalculated()) {
            return this.indexManager.dominator().get(i) - 2;
        }
        throw new SnapshotException(Messages.SnapshotImpl_Error_DomTreeNotAvailable);
    }

    public final DominatorsSummary getDominatorsOf(int[] iArr, Pattern pattern, IProgressListener iProgressListener) throws SnapshotException {
        String str;
        int i;
        IClass iClass;
        String str2;
        int[] iArr2 = iArr;
        Pattern pattern2 = pattern;
        if (!isDominatorTreeCalculated()) {
            throw new SnapshotException(Messages.SnapshotImpl_Error_DomTreeNotAvailable);
        }
        IProgressListener voidProgressListener = iProgressListener == null ? new VoidProgressListener() : iProgressListener;
        IOne2OneIndex dominator = this.indexManager.dominator();
        IOne2OneIndex o2class = this.indexManager.o2class();
        SetInt setInt = new SetInt();
        SetInt setInt2 = new SetInt();
        voidProgressListener.beginTask(Messages.SnapshotImpl_RetrievingDominators, iArr2.length / 10);
        HashMap hashMap = new HashMap();
        int i2 = 0;
        while (i2 < iArr2.length) {
            int i3 = iArr2[i2];
            int i4 = dominator.get(i3) - 2;
            if (i4 == -1) {
                str = "<ROOT>";
                iClass = null;
                i = -1;
            } else {
                i = o2class.get(i4);
                iClass = (IClass) this.classCache.get(i);
                str = iClass.getName();
            }
            if (pattern2 == null || i4 < 0) {
                str2 = str;
            } else {
                int i5 = i4;
                str2 = str;
                boolean z = true;
                while (z) {
                    if (voidProgressListener.isCanceled()) {
                        throw new OperationCanceledException();
                    } else if (setInt.contains(i)) {
                        i5 = dominator.get(i5) - 2;
                        if (i5 == -1) {
                            str2 = "<ROOT>";
                            iClass = null;
                            i = -1;
                        } else {
                            i = o2class.get(i5);
                            iClass = (IClass) this.classCache.get(i);
                            str2 = iClass.getName();
                        }
                    } else {
                        if (!setInt2.contains(i)) {
                            if (!pattern2.matcher(str2).matches() || i5 < 0) {
                                setInt2.add(i);
                                int[] iArr3 = iArr;
                            } else {
                                setInt.add(i);
                                int[] iArr4 = iArr;
                            }
                        }
                        z = false;
                    }
                }
                i4 = i5;
            }
            ClassDominatorRecord classDominatorRecord = (ClassDominatorRecord) hashMap.get(iClass);
            if (classDominatorRecord == null) {
                classDominatorRecord = new ClassDominatorRecord();
                hashMap.put(iClass, classDominatorRecord);
                classDominatorRecord.setClassName(str2);
                classDominatorRecord.setClassId(i);
                classDominatorRecord.setClassloaderId((i4 == -1 || iClass == null) ? -1 : iClass.getClassLoaderId());
            }
            if (classDominatorRecord.addDominator(i4) && i4 != -1) {
                classDominatorRecord.addDominatorNetSize((long) getHeapSize(i4));
            }
            if (classDominatorRecord.addDominated(i3)) {
                classDominatorRecord.addDominatedNetSize((long) getHeapSize(i3));
            }
            if (i2 % 10 == 0) {
                if (voidProgressListener.isCanceled()) {
                    throw new OperationCanceledException();
                }
                voidProgressListener.worked(1);
            }
            i2++;
            iArr2 = iArr;
        }
        voidProgressListener.done();
        return new DominatorsSummary((ClassDominatorRecord[]) hashMap.values().toArray(new ClassDominatorRecord[0]), this);
    }

    public final IObject getObject(int i) throws SnapshotException {
        IObject iObject = (IObject) this.classCache.get(i);
        if (iObject != null) {
            return iObject;
        }
        return (IObject) this.objectCache.get(i);
    }

    public final GCRootInfo[] getGCRootInfo(int i) throws SnapshotException {
        return (GCRootInfo[]) this.roots.get(i);
    }

    public final IClass getClassOf(int i) throws SnapshotException {
        if (isClass(i)) {
            return getObject(i).getClazz();
        }
        return (IClass) getObject(this.indexManager.o2class().get(i));
    }

    public final long mapIdToAddress(int i) throws SnapshotException {
        return this.indexManager.o2address().get(i);
    }

    public final int getHeapSize(int i) throws SnapshotException {
        if (this.arrayObjects.get(i)) {
            return this.indexManager.a2size().get(i);
        }
        IClass iClass = (IClass) this.classCache.get(i);
        if (iClass != null) {
            return iClass.getUsedHeapSize();
        }
        return ((IClass) this.classCache.get(this.indexManager.o2class().get(i))).getHeapSizePerInstance();
    }

    public final long getHeapSize(int[] iArr) throws UnsupportedOperationException, SnapshotException {
        int i;
        IOne2OneIndex o2class = this.indexManager.o2class();
        IOne2OneIndex a2size = this.indexManager.a2size();
        long j = 0;
        for (int i2 : iArr) {
            if (this.arrayObjects.get(i2)) {
                i = a2size.get(i2);
            } else {
                IClass iClass = (IClass) this.classCache.get(i2);
                if (iClass != null) {
                    i = iClass.getUsedHeapSize();
                } else {
                    i = ((IClass) this.classCache.get(o2class.get(i2))).getHeapSizePerInstance();
                }
            }
            j += (long) i;
        }
        return j;
    }

    public final long getRetainedHeapSize(int i) throws SnapshotException {
        if (isDominatorTreeCalculated()) {
            return this.indexManager.o2retained().get(i);
        }
        return 0;
    }

    public final boolean isArray(int i) {
        if (!this.arrayObjects.get(i) || !((IClass) this.classCache.get(this.indexManager.o2class().get(i))).isArrayType()) {
            return false;
        }
        return true;
    }

    public final boolean isClass(int i) {
        return this.classCache.containsKey(i);
    }

    public final boolean isGCRoot(int i) {
        return this.roots.containsKey(i);
    }

    public final int mapAddressToId(long j) throws SnapshotException {
        int reverse = this.indexManager.o2address().reverse(j);
        if (reverse >= 0) {
            return reverse;
        }
        Messages messages = Messages.SnapshotImpl_Error_ObjectNotFound;
        StringBuilder sb = new StringBuilder("0x");
        sb.append(Long.toHexString(j));
        throw new SnapshotException(MessageUtil.format(messages, sb.toString()));
    }

    public final void dispose() {
        try {
            this.heapObjectReader.close();
            e = null;
        } catch (IOException e) {
            e = e;
        }
        try {
            this.indexManager.close();
        } catch (IOException e2) {
            e = e2;
        }
        this.retainedSizeCache.close();
        if (e != null) {
            throw new RuntimeException(e);
        }
    }

    public final List<IClass> resolveClassHierarchy(int i) {
        IClass iClass = (IClass) this.classCache.get(i);
        if (iClass == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(iClass);
        while (iClass.hasSuperClass()) {
            iClass = (IClass) this.classCache.get(iClass.getSuperClassId());
            if (iClass == null) {
                return null;
            }
            arrayList.add(iClass);
        }
        return arrayList;
    }

    public final boolean isClassLoader(int i) {
        return this.loaderLabels.containsKey(i);
    }

    public final String getClassLoaderLabel(int i) {
        return (String) this.loaderLabels.get(i);
    }

    public final void setClassLoaderLabel(int i, String str) {
        if (str == null) {
            throw new NullPointerException(Messages.SnapshotImpl_Label.pattern);
        } else if (((String) this.loaderLabels.put(i, str)) == null) {
            throw new RuntimeException(Messages.SnapshotImpl_Error_ReplacingNonExistentClassLoader.pattern);
        }
    }

    private int dfs2(BitField bitField, BitField bitField2, String[] strArr) throws SnapshotException {
        int i;
        int[] iArr;
        int[] iArr2;
        BitField bitField3 = bitField;
        String[] strArr2 = strArr;
        HashSet hashSet = new HashSet(strArr2.length);
        for (String add : strArr2) {
            hashSet.add(add);
        }
        IOne2ManyIndex outbound = this.indexManager.outbound();
        IntStack intStack = new IntStack();
        IteratorInt keys = this.roots.keys();
        int i2 = 0;
        while (keys.hasNext()) {
            int next = keys.next();
            intStack.push(next);
            bitField3.set(next);
            i2 = i + 1;
        }
        while (intStack.size() > 0) {
            int pop = intStack.pop();
            if (bitField2.get(pop)) {
                int i3 = i;
                for (int i4 : outbound.get(pop)) {
                    IObject object = getObject(pop);
                    long mapIdToAddress = mapIdToAddress(i4);
                    for (NamedReference next2 : object.getOutboundReferences()) {
                        if (!bitField3.get(i4) && next2.getObjectAddress() == mapIdToAddress && !hashSet.contains(next2.getName())) {
                            intStack.push(i4);
                            bitField3.set(i4);
                            i3++;
                        }
                    }
                }
                i = i3;
            } else {
                int i5 = i;
                for (int i6 : outbound.get(pop)) {
                    if (!bitField3.get(i6)) {
                        intStack.push(i6);
                        bitField3.set(i6);
                        i5++;
                    }
                }
                i = i5;
            }
        }
        return i;
    }

    private int[] getSingleObjectRetainedSet(int i) throws SnapshotException {
        ArrayIntBig arrayIntBig = new ArrayIntBig();
        IntStack intStack = new IntStack();
        intStack.push(i);
        while (intStack.size() > 0) {
            int pop = intStack.pop();
            arrayIntBig.add(pop);
            for (int push : getImmediateDominatedIds(pop)) {
                intStack.push(push);
            }
        }
        return arrayIntBig.toArray();
    }

    public final IndexManager getIndexManager() {
        return this.indexManager;
    }

    public final IObjectReader getHeapObjectReader() {
        return this.heapObjectReader;
    }

    public final RetainedSizeCache getRetainedSizeCache() {
        return this.retainedSizeCache;
    }

    public final HashMapIntObject<HashMapIntObject<XGCRootInfo[]>> getRootsPerThread() {
        return this.rootsPerThread;
    }

    public final <A> A getSnapshotAddons(Class<A> cls) throws SnapshotException {
        return this.heapObjectReader.getAddon(cls);
    }

    public final IThreadStack getThreadStack(int i) throws SnapshotException {
        if (!this.parsedThreads) {
            this.threadId2stack = ThreadStackHelper.loadThreadsData(this);
            this.parsedThreads = true;
        }
        if (this.threadId2stack != null) {
            return (IThreadStack) this.threadId2stack.get(i);
        }
        return null;
    }
}
