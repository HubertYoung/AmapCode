package org.eclipse.mat.hprof;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.HashMapIntObject;
import org.eclipse.mat.collect.HashMapLongObject;
import org.eclipse.mat.collect.HashMapLongObject.Entry;
import org.eclipse.mat.collect.IteratorLong;
import org.eclipse.mat.hprof.IHprofParserHandler.HeapObject;
import org.eclipse.mat.parser.IPreliminaryIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2LongIndex;
import org.eclipse.mat.parser.index.IndexManager.Index;
import org.eclipse.mat.parser.index.IndexWriter;
import org.eclipse.mat.parser.index.IndexWriter.Identifier;
import org.eclipse.mat.parser.index.IndexWriter.IntArray1NWriter;
import org.eclipse.mat.parser.index.IndexWriter.IntIndexCollector;
import org.eclipse.mat.parser.index.IndexWriter.IntIndexCollectorUncompressed;
import org.eclipse.mat.parser.index.IndexWriter.LongIndexCollector;
import org.eclipse.mat.parser.model.ClassImpl;
import org.eclipse.mat.parser.model.XGCRootInfo;
import org.eclipse.mat.parser.model.XSnapshotInfo;
import org.eclipse.mat.snapshot.model.Field;
import org.eclipse.mat.snapshot.model.FieldDescriptor;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IPrimitiveArray;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.IProgressListener.Severity;
import org.eclipse.mat.util.MessageUtil;

public class HprofParserHandlerImpl implements IHprofParserHandler {
    private IntIndexCollectorUncompressed array2size = null;
    private HashMapLongObject<ClassImpl> classesByAddress = new HashMapLongObject<>();
    private Map<String, List<ClassImpl>> classesByName = new HashMap();
    private HashMapLongObject<String> constantPool = new HashMapLongObject<>(10000);
    private HashMapLongObject<List<XGCRootInfo>> gcRoots = new HashMapLongObject<>(200);
    private Identifier identifiers = null;
    private XSnapshotInfo info = new XSnapshotInfo();
    private IntIndexCollector object2classId = null;
    private LongIndexCollector object2position = null;
    private IntArray1NWriter outbound = null;
    private Set<Long> requiredArrayClassIDs = new HashSet();
    private Set<Integer> requiredPrimitiveArrays = new HashSet();
    private HashMapLongObject<HashMapLongObject<List<XGCRootInfo>>> threadAddressToLocals = new HashMapLongObject<>();
    private Version version;

    public void beforePass1(XSnapshotInfo xSnapshotInfo) throws IOException {
        this.info = xSnapshotInfo;
        this.identifiers = new Identifier();
    }

    public void beforePass2(IProgressListener iProgressListener) throws IOException, SnapshotException {
        this.identifiers.add(0);
        this.identifiers.sort();
        if (!this.requiredArrayClassIDs.isEmpty() || !this.requiredPrimitiveArrays.isEmpty()) {
            createRequiredFakeClasses();
        }
        iProgressListener.sendUserMessage(Severity.INFO, MessageUtil.format(Messages.HprofParserHandlerImpl_HeapContainsObjects, this.info.getPath(), Integer.valueOf(this.identifiers.size())), null);
        Iterator values = this.classesByAddress.values();
        int i = 0;
        while (values.hasNext()) {
            ClassImpl classImpl = (ClassImpl) values.next();
            int reverse = this.identifiers.reverse(classImpl.getObjectAddress());
            classImpl.setObjectId(reverse);
            i = Math.max(i, reverse);
            classImpl.setHeapSizePerInstance(calculateInstanceSize(classImpl));
            classImpl.setUsedHeapSize(calculateClassSize(classImpl));
        }
        int size = this.identifiers.size();
        Index index = Index.OUTBOUND;
        StringBuilder sb = new StringBuilder();
        sb.append(this.info.getPrefix());
        sb.append("temp.");
        this.outbound = new IntArray1NWriter(size, index.getFile(sb.toString()));
        this.object2classId = new IntIndexCollector(this.identifiers.size(), IndexWriter.mostSignificantBit(i));
        this.object2position = new LongIndexCollector(this.identifiers.size(), IndexWriter.mostSignificantBit(new File(this.info.getPath()).length()));
        this.array2size = new IntIndexCollectorUncompressed(this.identifiers.size());
        ClassImpl classImpl2 = (ClassImpl) this.classesByName.get("java.lang.Class").get(0);
        classImpl2.setObjectId(this.identifiers.reverse(classImpl2.getObjectAddress()));
        Iterator values2 = this.classesByAddress.values();
        while (values2.hasNext()) {
            ClassImpl classImpl3 = (ClassImpl) values2.next();
            classImpl3.setSuperClassIndex(this.identifiers.reverse(classImpl3.getSuperClassAddress()));
            classImpl3.setClassLoaderIndex(this.identifiers.reverse(classImpl3.getClassLoaderAddress()));
            if (classImpl3.getClassLoaderId() < 0) {
                classImpl3.setClassLoaderAddress(0);
                classImpl3.setClassLoaderIndex(this.identifiers.reverse(0));
            }
            classImpl3.setClassInstance(classImpl2);
            classImpl2.addInstance(classImpl3.getUsedHeapSize());
            ClassImpl lookupClass = lookupClass(classImpl3.getSuperClassAddress());
            if (lookupClass != null) {
                lookupClass.addSubClass(classImpl3);
            }
            this.object2classId.set(classImpl3.getObjectId(), classImpl3.getClazz().getObjectId());
            this.outbound.log(this.identifiers, classImpl3.getObjectId(), classImpl3.getReferences());
        }
        ClassImpl classImpl4 = (ClassImpl) this.classesByName.get(IClass.JAVA_LANG_CLASSLOADER).get(0);
        HeapObject heapObject = new HeapObject(this.identifiers.reverse(0), 0, classImpl4, classImpl4.getHeapSizePerInstance());
        heapObject.references.add(classImpl4.getObjectAddress());
        addObject(heapObject, 0);
        this.constantPool = null;
    }

    private void createRequiredFakeClasses() throws IOException, SnapshotException {
        if (!this.requiredArrayClassIDs.isEmpty()) {
            for (Long longValue : this.requiredArrayClassIDs) {
                long longValue2 = longValue.longValue();
                if (lookupClass(longValue2) == null) {
                    if (this.identifiers.reverse(longValue2) >= 0) {
                        throw new SnapshotException(MessageUtil.format(Messages.HprofParserHandlerImpl_Error_ExpectedClassSegment, Long.toHexString(longValue2)));
                    }
                    ClassImpl classImpl = new ClassImpl(longValue2, "unknown-class[]", 0, 0, new Field[0], new FieldDescriptor[0]);
                    addClass(classImpl, -1);
                }
            }
        }
        this.requiredArrayClassIDs = null;
        if (!this.requiredPrimitiveArrays.isEmpty()) {
            long j = 0;
            for (Integer intValue : this.requiredPrimitiveArrays) {
                String str = IPrimitiveArray.TYPE[intValue.intValue()];
                if (lookupClassByName(str, true) == null) {
                    do {
                        j++;
                    } while (this.identifiers.reverse(j) >= 0);
                    ClassImpl classImpl2 = new ClassImpl(j, str, 0, 0, new Field[0], new FieldDescriptor[0]);
                    addClass(classImpl2, -1);
                }
            }
        }
        this.identifiers.sort();
    }

    private int calculateInstanceSize(ClassImpl classImpl) {
        if (!classImpl.isArrayType()) {
            return alignUpToX(calculateSizeRecursive(classImpl), 8);
        }
        return this.info.getIdentifierSize();
    }

    private int calculateSizeRecursive(ClassImpl classImpl) {
        if (classImpl.getSuperClassAddress() == 0) {
            return this.info.getIdentifierSize() * 2;
        }
        ClassImpl classImpl2 = (ClassImpl) this.classesByAddress.get(classImpl.getSuperClassAddress());
        int i = 0;
        for (FieldDescriptor sizeOf : classImpl.getFieldDescriptors()) {
            i += sizeOf(sizeOf);
        }
        return alignUpToX(i + calculateSizeRecursive(classImpl2), this.info.getIdentifierSize());
    }

    private int calculateClassSize(ClassImpl classImpl) {
        int i = 0;
        for (Field sizeOf : classImpl.getStaticFields()) {
            i += sizeOf(sizeOf);
        }
        return alignUpToX(i, 8);
    }

    private int sizeOf(FieldDescriptor fieldDescriptor) {
        int type = fieldDescriptor.getType();
        if (type == 2) {
            return this.info.getIdentifierSize();
        }
        return IPrimitiveArray.ELEMENT_SIZE[type];
    }

    private int alignUpToX(int i, int i2) {
        int i3 = i % i2;
        return i3 == 0 ? i : (i + i2) - i3;
    }

    public IOne2LongIndex fillIn(IPreliminaryIndex iPreliminaryIndex) throws IOException {
        ClassImpl[] classImplArr;
        for (ClassImpl classImpl : (ClassImpl[]) this.classesByAddress.getAllValues(new ClassImpl[0])) {
            if (classImpl.getClassLoaderAddress() == 0 && !classImpl.isArrayType() && !this.gcRoots.containsKey(classImpl.getObjectAddress())) {
                addGCRoot(classImpl.getObjectAddress(), 0, 2);
            }
        }
        HashMapIntObject hashMapIntObject = new HashMapIntObject(this.classesByAddress.size());
        Iterator values = this.classesByAddress.values();
        while (values.hasNext()) {
            ClassImpl classImpl2 = (ClassImpl) values.next();
            hashMapIntObject.put(classImpl2.getObjectId(), classImpl2);
        }
        iPreliminaryIndex.setClassesById(hashMapIntObject);
        iPreliminaryIndex.setGcRoots(map2ids(this.gcRoots));
        HashMapIntObject hashMapIntObject2 = new HashMapIntObject();
        Iterator entries = this.threadAddressToLocals.entries();
        while (entries.hasNext()) {
            Entry entry = (Entry) entries.next();
            int reverse = this.identifiers.reverse(entry.getKey());
            if (reverse >= 0) {
                HashMapIntObject<List<XGCRootInfo>> map2ids = map2ids((HashMapLongObject) entry.getValue());
                if (!map2ids.isEmpty()) {
                    hashMapIntObject2.put(reverse, map2ids);
                }
            }
        }
        iPreliminaryIndex.setThread2objects2roots(hashMapIntObject2);
        iPreliminaryIndex.setIdentifiers(this.identifiers);
        IntIndexCollectorUncompressed intIndexCollectorUncompressed = this.array2size;
        Index index = Index.A2SIZE;
        StringBuilder sb = new StringBuilder();
        sb.append(this.info.getPrefix());
        sb.append("temp.");
        iPreliminaryIndex.setArray2size(intIndexCollectorUncompressed.writeTo(index.getFile(sb.toString())));
        iPreliminaryIndex.setObject2classId(this.object2classId);
        iPreliminaryIndex.setOutbound(this.outbound.flush());
        LongIndexCollector longIndexCollector = this.object2position;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.info.getPrefix());
        sb2.append("temp.o2hprof.index");
        return longIndexCollector.writeTo(new File(sb2.toString()));
    }

    private HashMapIntObject<List<XGCRootInfo>> map2ids(HashMapLongObject<List<XGCRootInfo>> hashMapLongObject) {
        HashMapIntObject<List<XGCRootInfo>> hashMapIntObject = new HashMapIntObject<>();
        Iterator entries = hashMapLongObject.entries();
        while (entries.hasNext()) {
            Entry entry = (Entry) entries.next();
            int reverse = this.identifiers.reverse(entry.getKey());
            if (reverse >= 0) {
                Iterator it = ((List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    XGCRootInfo xGCRootInfo = (XGCRootInfo) it.next();
                    xGCRootInfo.setObjectId(reverse);
                    if (xGCRootInfo.getContextAddress() != 0) {
                        int reverse2 = this.identifiers.reverse(xGCRootInfo.getContextAddress());
                        if (reverse2 < 0) {
                            it.remove();
                        } else {
                            xGCRootInfo.setContextId(reverse2);
                        }
                    }
                }
                hashMapIntObject.put(reverse, entry.getValue());
            }
        }
        return hashMapIntObject;
    }

    public void cancel() {
        if (this.constantPool != null) {
            this.constantPool.clear();
        }
        if (this.outbound != null) {
            this.outbound.cancel();
        }
    }

    public void addProperty(String str, String str2) throws IOException {
        if (IHprofParserHandler.VERSION.equals(str)) {
            this.version = Version.valueOf(str2);
            this.info.setProperty(HprofHeapObjectReader.VERSION_PROPERTY, this.version.name());
        } else if (IHprofParserHandler.IDENTIFIER_SIZE.equals(str)) {
            this.info.setIdentifierSize(Integer.parseInt(str2));
        } else {
            if (IHprofParserHandler.CREATION_DATE.equals(str)) {
                this.info.setCreationDate(new Date(Long.parseLong(str2)));
            }
        }
    }

    public void addGCRoot(long j, long j2, int i) {
        if (j2 != 0) {
            HashMapLongObject hashMapLongObject = (HashMapLongObject) this.threadAddressToLocals.get(j2);
            if (hashMapLongObject == null) {
                hashMapLongObject = new HashMapLongObject();
                this.threadAddressToLocals.put(j2, hashMapLongObject);
            }
            List list = (List) hashMapLongObject.get(j);
            if (list == null) {
                list = new ArrayList(1);
                hashMapLongObject.put(j, list);
            }
            XGCRootInfo xGCRootInfo = new XGCRootInfo(j, j2, i);
            list.add(xGCRootInfo);
            return;
        }
        List list2 = (List) this.gcRoots.get(j);
        if (list2 == null) {
            HashMapLongObject<List<XGCRootInfo>> hashMapLongObject2 = this.gcRoots;
            List arrayList = new ArrayList(3);
            hashMapLongObject2.put(j, arrayList);
            list2 = arrayList;
        }
        XGCRootInfo xGCRootInfo2 = new XGCRootInfo(j, j2, i);
        list2.add(xGCRootInfo2);
    }

    public void addClass(ClassImpl classImpl, long j) throws IOException {
        this.identifiers.add(classImpl.getObjectAddress());
        this.classesByAddress.put(classImpl.getObjectAddress(), classImpl);
        List list = this.classesByName.get(classImpl.getName());
        if (list == null) {
            Map<String, List<ClassImpl>> map = this.classesByName;
            String name = classImpl.getName();
            List arrayList = new ArrayList();
            map.put(name, arrayList);
            list = arrayList;
        }
        list.add(classImpl);
    }

    public void addObject(HeapObject heapObject, long j) throws IOException {
        int i = heapObject.objectId;
        HashMapLongObject hashMapLongObject = (HashMapLongObject) this.threadAddressToLocals.get(heapObject.objectAddress);
        if (hashMapLongObject != null) {
            IteratorLong keys = hashMapLongObject.keys();
            while (keys.hasNext()) {
                heapObject.references.add(keys.next());
            }
        }
        this.outbound.log(this.identifiers, i, heapObject.references);
        int objectId = heapObject.clazz.getObjectId();
        heapObject.clazz.addInstance(heapObject.usedHeapSize);
        this.object2classId.set(i, objectId);
        this.object2position.set(i, j);
        if (heapObject.isArray) {
            this.array2size.set(i, heapObject.usedHeapSize);
        }
    }

    public void reportInstance(long j, long j2) {
        this.identifiers.add(j);
    }

    public void reportRequiredObjectArray(long j) {
        this.requiredArrayClassIDs.add(Long.valueOf(j));
    }

    public void reportRequiredPrimitiveArray(int i) {
        this.requiredPrimitiveArrays.add(Integer.valueOf(i));
    }

    public int getIdentifierSize() {
        return this.info.getIdentifierSize();
    }

    public HashMapLongObject<String> getConstantPool() {
        return this.constantPool;
    }

    public ClassImpl lookupClass(long j) {
        return (ClassImpl) this.classesByAddress.get(j);
    }

    public IClass lookupClassByName(String str, boolean z) {
        List list = this.classesByName.get(str);
        if (list == null) {
            return null;
        }
        if (!z || list.size() == 1) {
            return (IClass) list.get(0);
        }
        throw new RuntimeException(MessageUtil.format(Messages.HprofParserHandlerImpl_Error_MultipleClassInstancesExist, str));
    }

    public IClass lookupClassByIndex(int i) {
        return lookupClass(this.identifiers.get(i));
    }

    public List<IClass> resolveClassHierarchy(long j) {
        ArrayList arrayList = new ArrayList();
        ClassImpl classImpl = (ClassImpl) this.classesByAddress.get(j);
        arrayList.add(classImpl);
        while (classImpl.hasSuperClass()) {
            classImpl = (ClassImpl) this.classesByAddress.get(classImpl.getSuperClassAddress());
            arrayList.add(classImpl);
        }
        return arrayList;
    }

    public int mapAddressToId(long j) {
        return this.identifiers.reverse(j);
    }

    public XSnapshotInfo getSnapshotInfo() {
        return this.info;
    }
}
