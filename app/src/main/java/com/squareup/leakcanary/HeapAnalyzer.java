package com.squareup.leakcanary;

import com.squareup.leakcanary.ExcludedRefs.Builder;
import com.squareup.leakcanary.LeakTraceElement.Holder;
import com.squareup.leakcanary.LeakTraceElement.Type;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.parser.internal.SnapshotFactory;
import org.eclipse.mat.snapshot.IPathsFromGCRootsComputer;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.PathsFromGCRootsTree;
import org.eclipse.mat.snapshot.model.Field;
import org.eclipse.mat.snapshot.model.IArray;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IInstance;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.IObjectArray;
import org.eclipse.mat.snapshot.model.NamedReference;
import org.eclipse.mat.snapshot.model.ObjectReference;
import org.eclipse.mat.snapshot.model.PrettyPrinter;
import org.eclipse.mat.snapshot.model.ThreadToLocalReference;
import org.eclipse.mat.util.VoidProgressListener;

public final class HeapAnalyzer {
    private static final String ANONYMOUS_CLASS_NAME_PATTERN = "^.+\\$\\d+$";
    private static final String TAG = "HeapAnalyzer";
    private final ExcludedRefs baseExcludedRefs;
    private final ExcludedRefs excludedRefs;

    public HeapAnalyzer(ExcludedRefs excludedRefs2) {
        this(new Builder().build(), excludedRefs2);
    }

    public HeapAnalyzer(ExcludedRefs excludedRefs2, ExcludedRefs excludedRefs3) {
        this.baseExcludedRefs = excludedRefs2;
        this.excludedRefs = excludedRefs3;
    }

    public final AnalysisResult checkForLeak(File file, String str) {
        long nanoTime = System.nanoTime();
        if (!file.exists()) {
            return AnalysisResult.failure(new IllegalArgumentException("File does not exist: ".concat(String.valueOf(file))), since(nanoTime));
        }
        ISnapshot iSnapshot = null;
        try {
            ISnapshot iSnapshot2 = openSnapshot(file);
            try {
                IObject findLeakingReference = findLeakingReference(str, iSnapshot2);
                if (findLeakingReference == null) {
                    AnalysisResult noLeak = AnalysisResult.noLeak(since(nanoTime));
                    cleanup(file, iSnapshot2);
                    return noLeak;
                }
                String name = findLeakingReference.getClazz().getName();
                AnalysisResult findLeakTrace = findLeakTrace(nanoTime, iSnapshot2, findLeakingReference, name, true);
                if (!findLeakTrace.leakFound) {
                    findLeakTrace = findLeakTrace(nanoTime, iSnapshot2, findLeakingReference, name, false);
                }
                cleanup(file, iSnapshot2);
                return findLeakTrace;
            } catch (Exception e) {
                e = e;
                iSnapshot = iSnapshot2;
                try {
                    AnalysisResult failure = AnalysisResult.failure(e, since(nanoTime));
                    cleanup(file, iSnapshot);
                    return failure;
                } catch (Throwable th) {
                    th = th;
                    iSnapshot2 = iSnapshot;
                    cleanup(file, iSnapshot2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cleanup(file, iSnapshot2);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            AnalysisResult failure2 = AnalysisResult.failure(e, since(nanoTime));
            cleanup(file, iSnapshot);
            return failure2;
        }
    }

    private AnalysisResult findLeakTrace(long j, ISnapshot iSnapshot, IObject iObject, String str, boolean z) throws SnapshotException {
        ExcludedRefs excludedRefs2 = z ? this.excludedRefs : this.baseExcludedRefs;
        PathsFromGCRootsTree shortestPathToGcRoots = shortestPathToGcRoots(iSnapshot, iObject, excludedRefs2);
        if (shortestPathToGcRoots == null) {
            return AnalysisResult.noLeak(since(j));
        }
        return AnalysisResult.leakDetected(!z, str, buildLeakTrace(iSnapshot, shortestPathToGcRoots, excludedRefs2), since(j));
    }

    private ISnapshot openSnapshot(File file) throws SnapshotException {
        return new SnapshotFactory().openSnapshot(file, Collections.emptyMap(), new VoidProgressListener());
    }

    private IObject findLeakingReference(String str, ISnapshot iSnapshot) throws SnapshotException {
        Collection<IClass> classesByName = iSnapshot.getClassesByName(KeyedWeakReference.class.getName(), false);
        if (classesByName.size() != 1) {
            StringBuilder sb = new StringBuilder("Expecting one class for ");
            sb.append(KeyedWeakReference.class.getName());
            sb.append(" in ");
            sb.append(classesByName);
            throw new IllegalStateException(sb.toString());
        }
        for (int object : classesByName.iterator().next().getObjectIds()) {
            IObject object2 = iSnapshot.getObject(object);
            if (PrettyPrinter.objectAsString((IObject) object2.resolveValue("key"), 100).equals(str)) {
                return (IObject) object2.resolveValue("referent");
            }
        }
        throw new IllegalStateException("Could not find weak reference with key ".concat(String.valueOf(str)));
    }

    private PathsFromGCRootsTree shortestPathToGcRoots(ISnapshot iSnapshot, IObject iObject, ExcludedRefs excludedRefs2) throws SnapshotException {
        return shortestValidPath(iSnapshot, iSnapshot.getPathsFromGCRoots(iObject.getObjectId(), buildClassExcludeMap(iSnapshot, excludedRefs2.excludeFieldMap)), excludedRefs2);
    }

    private Map<IClass, Set<String>> buildClassExcludeMap(ISnapshot iSnapshot, Map<String, Set<String>> map) throws SnapshotException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Entry next : map.entrySet()) {
            Collection<IClass> classesByName = iSnapshot.getClassesByName((String) next.getKey(), false);
            if (classesByName != null && classesByName.size() == 1) {
                linkedHashMap.put(classesByName.iterator().next(), next.getValue());
            }
        }
        return linkedHashMap;
    }

    private PathsFromGCRootsTree shortestValidPath(ISnapshot iSnapshot, IPathsFromGCRootsComputer iPathsFromGCRootsComputer, ExcludedRefs excludedRefs2) throws SnapshotException {
        PathsFromGCRootsTree tree;
        Map<IClass, Set<String>> buildClassExcludeMap = buildClassExcludeMap(iSnapshot, excludedRefs2.excludeStaticFieldMap);
        do {
            int[] nextShortestPath = iPathsFromGCRootsComputer.getNextShortestPath();
            if (nextShortestPath == null) {
                return null;
            }
            tree = iPathsFromGCRootsComputer.getTree(Collections.singletonList(nextShortestPath));
        } while (!validPath(iSnapshot, tree, buildClassExcludeMap, excludedRefs2));
        return tree;
    }

    private boolean validPath(ISnapshot iSnapshot, PathsFromGCRootsTree pathsFromGCRootsTree, Map<IClass, Set<String>> map, ExcludedRefs excludedRefs2) throws SnapshotException {
        if (map.isEmpty() && excludedRefs2.excludedThreads.isEmpty()) {
            return true;
        }
        IObject iObject = null;
        while (pathsFromGCRootsTree != null) {
            IObject object = iSnapshot.getObject(pathsFromGCRootsTree.getOwnId());
            if (object instanceof IClass) {
                Set set = map.get((IClass) object);
                if (set != null) {
                    NamedReference findHeldInHolder = findHeldInHolder(iObject, object, excludedRefs2);
                    if (findHeldInHolder != null && set.contains(findHeldInHolder.getName())) {
                        return false;
                    }
                }
            } else if (object.getClazz().doesExtend(Thread.class.getName()) && excludedRefs2.excludedThreads.contains(getThreadName(object))) {
                return false;
            }
            int[] objectIds = pathsFromGCRootsTree.getObjectIds();
            pathsFromGCRootsTree = objectIds.length > 0 ? pathsFromGCRootsTree.getBranch(objectIds[0]) : null;
            iObject = object;
        }
        return true;
    }

    private String getThreadName(IObject iObject) throws SnapshotException {
        return PrettyPrinter.objectAsString((IObject) iObject.resolveValue("name"), Integer.MAX_VALUE);
    }

    private NamedReference findHeldInHolder(IObject iObject, IObject iObject2, ExcludedRefs excludedRefs2) throws SnapshotException {
        if (iObject == null) {
            return null;
        }
        Set set = excludedRefs2.excludeFieldMap.get(iObject2.getClazz().getName());
        for (NamedReference next : iObject2.getOutboundReferences()) {
            if (next.getObjectId() == iObject.getObjectId() && (set == null || !set.contains(next.getName()))) {
                return next;
            }
        }
        return null;
    }

    private LeakTrace buildLeakTrace(ISnapshot iSnapshot, PathsFromGCRootsTree pathsFromGCRootsTree, ExcludedRefs excludedRefs2) throws SnapshotException {
        ArrayList arrayList = new ArrayList();
        IObject iObject = null;
        while (pathsFromGCRootsTree != null) {
            IObject object = iSnapshot.getObject(pathsFromGCRootsTree.getOwnId());
            arrayList.add(0, buildLeakElement(iObject, object, excludedRefs2));
            int[] objectIds = pathsFromGCRootsTree.getObjectIds();
            pathsFromGCRootsTree = objectIds.length > 0 ? pathsFromGCRootsTree.getBranch(objectIds[0]) : null;
            iObject = object;
        }
        return new LeakTrace(arrayList);
    }

    private LeakTraceElement buildLeakElement(IObject iObject, IObject iObject2, ExcludedRefs excludedRefs2) throws SnapshotException {
        Type type;
        String str;
        String str2;
        String str3;
        Holder holder;
        Holder holder2;
        Holder holder3;
        String sb;
        long[] referenceArray;
        Type type2;
        NamedReference findHeldInHolder = findHeldInHolder(iObject, iObject2, excludedRefs2);
        if (findHeldInHolder != null) {
            String name = findHeldInHolder.getName();
            if (iObject2 instanceof IClass) {
                type2 = Type.STATIC_FIELD;
            } else if (findHeldInHolder instanceof ThreadToLocalReference) {
                type2 = Type.LOCAL;
            } else {
                type2 = Type.INSTANCE_FIELD;
            }
            type = type2;
            str = name;
        } else {
            str = null;
            type = null;
        }
        ArrayList arrayList = new ArrayList();
        if (iObject2 instanceof IClass) {
            IClass iClass = (IClass) iObject2;
            Holder holder4 = Holder.CLASS;
            String name2 = iClass.getName();
            for (Field fieldToString : iClass.getStaticFields()) {
                StringBuilder sb2 = new StringBuilder("static ");
                sb2.append(fieldToString(fieldToString));
                arrayList.add(sb2.toString());
            }
            holder = holder4;
            str2 = null;
            str3 = name2;
        } else {
            if (iObject2 instanceof IArray) {
                Holder holder5 = Holder.ARRAY;
                String name3 = iObject2.getClazz().getName();
                if (iObject2 instanceof IObjectArray) {
                    ISnapshot snapshot = iObject2.getSnapshot();
                    int i = 0;
                    for (long j : ((IObjectArray) iObject2).getReferenceArray()) {
                        if (j == 0) {
                            StringBuilder sb3 = new StringBuilder("[");
                            sb3.append(i);
                            sb3.append("] = null");
                            arrayList.add(sb3.toString());
                        } else {
                            IObject object = snapshot.getObject(snapshot.mapAddressToId(j));
                            StringBuilder sb4 = new StringBuilder("[");
                            sb4.append(i);
                            sb4.append("] = ");
                            sb4.append(object);
                            arrayList.add(sb4.toString());
                        }
                        i++;
                    }
                }
                holder = holder5;
                str2 = null;
                str3 = name3;
            } else {
                IInstance iInstance = (IInstance) iObject2;
                IClass clazz = iObject2.getClazz();
                for (Field fieldToString2 : clazz.getStaticFields()) {
                    StringBuilder sb5 = new StringBuilder("static ");
                    sb5.append(fieldToString(fieldToString2));
                    arrayList.add(sb5.toString());
                }
                for (Field fieldToString3 : iInstance.getFields()) {
                    arrayList.add(fieldToString(fieldToString3));
                }
                String name4 = clazz.getName();
                if (clazz.doesExtend(Thread.class.getName())) {
                    holder3 = Holder.THREAD;
                    String threadName = getThreadName(iObject2);
                    StringBuilder sb6 = new StringBuilder("(named '");
                    sb6.append(threadName);
                    sb6.append("')");
                    sb = sb6.toString();
                } else if (name4.matches(ANONYMOUS_CLASS_NAME_PATTERN)) {
                    String name5 = clazz.getSuperClass().getName();
                    if (Object.class.getName().equals(name5)) {
                        holder2 = Holder.OBJECT;
                        try {
                            Class cls = Class.forName(clazz.getName()).getInterfaces()[0];
                            StringBuilder sb7 = new StringBuilder("(anonymous class implements ");
                            sb7.append(cls.getName());
                            sb7.append(")");
                            str3 = name4;
                            holder = holder2;
                            str2 = sb7.toString();
                        } catch (ClassNotFoundException unused) {
                        }
                    } else {
                        holder3 = Holder.OBJECT;
                        StringBuilder sb8 = new StringBuilder("(anonymous class extends ");
                        sb8.append(name5);
                        sb8.append(")");
                        sb = sb8.toString();
                    }
                } else {
                    holder2 = Holder.OBJECT;
                    str3 = name4;
                    holder = holder2;
                    str2 = null;
                }
                str3 = name4;
                str2 = sb;
                holder = holder3;
            }
        }
        LeakTraceElement leakTraceElement = new LeakTraceElement(str, type, holder, str3, str2, arrayList);
        return leakTraceElement;
    }

    private String fieldToString(Field field) throws SnapshotException {
        Object value = field.getValue();
        if (value instanceof ObjectReference) {
            value = ((ObjectReference) value).getObject();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(field.getName());
        sb.append(" = ");
        sb.append(value);
        return sb.toString();
    }

    private void cleanup(File file, ISnapshot iSnapshot) {
        if (iSnapshot != null) {
            iSnapshot.dispose();
        }
        final String name = file.getName();
        final String substring = name.substring(0, file.getName().length() - 6);
        File[] listFiles = file.getParentFile().listFiles(new FileFilter() {
            public boolean accept(File file) {
                return !file.isDirectory() && file.getName().startsWith(substring) && !file.getName().equals(name);
            }
        });
        if (listFiles != null) {
            for (File delete : listFiles) {
                delete.delete();
            }
        }
    }

    private long since(long j) {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - j);
    }
}
