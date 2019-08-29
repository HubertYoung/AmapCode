package org.eclipse.mat.snapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.eclipse.mat.collect.SetInt;
import org.eclipse.mat.snapshot.model.IObject;

public final class DominatorsSummary {
    public static final Comparator<Object> COMPARE_BY_DOMINATED = new Comparator<Object>() {
        public final int compare(Object obj, Object obj2) {
            int i;
            int i2;
            if (obj instanceof ClassDominatorRecord) {
                i2 = ((ClassDominatorRecord) obj).getDominatedCount();
                i = ((ClassDominatorRecord) obj2).getDominatedCount();
            } else {
                i2 = ((ClassloaderDominatorRecord) obj).getDominatedCount();
                i = ((ClassloaderDominatorRecord) obj2).getDominatedCount();
            }
            if (i2 > i) {
                return 1;
            }
            return i2 == i ? 0 : -1;
        }
    };
    public static final Comparator<Object> COMPARE_BY_DOMINATED_HEAP_SIZE = new Comparator<Object>() {
        public final int compare(Object obj, Object obj2) {
            long j;
            long j2;
            if (obj instanceof ClassDominatorRecord) {
                j2 = ((ClassDominatorRecord) obj).getDominatedNetSize();
                j = ((ClassDominatorRecord) obj2).getDominatedNetSize();
            } else {
                j2 = ((ClassloaderDominatorRecord) obj).getDominatedNetSize();
                j = ((ClassloaderDominatorRecord) obj2).getDominatedNetSize();
            }
            int i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
            if (i > 0) {
                return 1;
            }
            return i == 0 ? 0 : -1;
        }
    };
    public static final Comparator<Object> COMPARE_BY_DOMINATED_RETAINED_HEAP_SIZE = new Comparator<Object>() {
        public final int compare(Object obj, Object obj2) {
            long j;
            long j2;
            if (obj instanceof ClassDominatorRecord) {
                j2 = ((ClassDominatorRecord) obj).getDominatedRetainedSize();
                j = ((ClassDominatorRecord) obj2).getDominatedRetainedSize();
            } else {
                j2 = ((ClassloaderDominatorRecord) obj).getDominatedRetainedSize();
                j = ((ClassloaderDominatorRecord) obj2).getDominatedRetainedSize();
            }
            int i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
            if (i > 0) {
                return 1;
            }
            return i == 0 ? 0 : -1;
        }
    };
    public static final Comparator<Object> COMPARE_BY_DOMINATORS = new Comparator<Object>() {
        public final int compare(Object obj, Object obj2) {
            int i;
            int i2;
            if (obj instanceof ClassDominatorRecord) {
                i2 = ((ClassDominatorRecord) obj).getDominatorCount();
                i = ((ClassDominatorRecord) obj2).getDominatorCount();
            } else {
                i2 = ((ClassloaderDominatorRecord) obj).getDominatorCount();
                i = ((ClassloaderDominatorRecord) obj2).getDominatorCount();
            }
            if (i2 > i) {
                return 1;
            }
            return i2 == i ? 0 : -1;
        }
    };
    public static final Comparator<Object> COMPARE_BY_DOMINATOR_HEAP_SIZE = new Comparator<Object>() {
        public final int compare(Object obj, Object obj2) {
            long j;
            long j2;
            if (obj instanceof ClassDominatorRecord) {
                j2 = ((ClassDominatorRecord) obj).getDominatorNetSize();
                j = ((ClassDominatorRecord) obj2).getDominatorNetSize();
            } else {
                j2 = ((ClassloaderDominatorRecord) obj).getDominatorNetSize();
                j = ((ClassloaderDominatorRecord) obj2).getDominatorNetSize();
            }
            int i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
            if (i > 0) {
                return 1;
            }
            return i == 0 ? 0 : -1;
        }
    };
    public static final Comparator<Object> COMPARE_BY_DOMINATOR_RETAINED_HEAP_SIZE = new Comparator<Object>() {
        public final int compare(Object obj, Object obj2) {
            long j;
            long j2;
            if (obj instanceof ClassDominatorRecord) {
                j2 = ((ClassDominatorRecord) obj).getDominatorRetainedSize();
                j = ((ClassDominatorRecord) obj2).getDominatorRetainedSize();
            } else {
                j2 = ((ClassloaderDominatorRecord) obj).getDominatorRetainedSize();
                j = ((ClassloaderDominatorRecord) obj2).getDominatorRetainedSize();
            }
            int i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
            if (i > 0) {
                return 1;
            }
            return i == 0 ? 0 : -1;
        }
    };
    public static final Comparator<Object> COMPARE_BY_NAME = new Comparator<Object>() {
        public final int compare(Object obj, Object obj2) {
            if (obj instanceof ClassDominatorRecord) {
                return ((ClassDominatorRecord) obj).getClassName().compareTo(((ClassDominatorRecord) obj2).getClassName());
            }
            return ((ClassloaderDominatorRecord) obj).getName().compareTo(((ClassloaderDominatorRecord) obj2).getName());
        }
    };
    private ClassDominatorRecord[] classDominatorRecords;
    private ClassloaderDominatorRecord[] classloaderDominatorRecords;
    private Object data;
    private ISnapshot snapshot;

    public static class ClassDominatorRecord {
        int classId;
        String className;
        int classloaderId;
        SetInt dominated = new SetInt(500);
        long dominatedNetSize;
        long dominatedRetainedSize;
        SetInt dominator = new SetInt(500);
        long dominatorNetSize;
        long dominatorRetainedSize;
        DominatorsSummary summary;

        public String getClassName() {
            return this.className;
        }

        public void setClassName(String str) {
            this.className = str;
        }

        public int getClassId() {
            return this.classId;
        }

        public void setClassId(int i) {
            this.classId = i;
        }

        public long getDominatedNetSize() {
            return this.dominatedNetSize;
        }

        public long getDominatorNetSize() {
            return this.dominatorNetSize;
        }

        public long getDominatedRetainedSize() {
            return this.dominatedRetainedSize;
        }

        public void setDominatedRetainedSize(long j) {
            this.dominatedRetainedSize = j;
        }

        public long getDominatorRetainedSize() {
            return this.dominatorRetainedSize;
        }

        public void setDominatorRetainedSize(long j) {
            this.dominatorRetainedSize = j;
        }

        public int getDominatedCount() {
            return this.dominated.size();
        }

        public int getDominatorCount() {
            return this.dominator.size();
        }

        public int getClassloaderId() {
            return this.classloaderId;
        }

        public void setClassloaderId(int i) {
            this.classloaderId = i;
        }

        public boolean addDominated(int i) {
            return this.dominated.add(i);
        }

        public boolean addDominator(int i) {
            return this.dominator.add(i);
        }

        public void addDominatedNetSize(long j) {
            this.dominatedNetSize += j;
        }

        public void addDominatorNetSize(long j) {
            this.dominatorNetSize += j;
        }

        public int[] getDominated() {
            return this.dominated.toArray();
        }

        public int[] getDominators() {
            return this.dominator.toArray();
        }

        public DominatorsSummary getSummary() {
            return this.summary;
        }
    }

    public static class ClassloaderDominatorRecord {
        int dominated;
        long dominatedNetSize;
        long dominatedRetainedSize;
        int dominator;
        long dominatorNetSize;
        long dominatorRetainedSize;
        protected int id;
        protected String name;
        protected List<ClassDominatorRecord> records = new ArrayList();

        public String getName() {
            return this.name;
        }

        public long getDominatedNetSize() {
            return this.dominatedNetSize;
        }

        public int getDominatedCount() {
            return this.dominated;
        }

        public int getDominatorCount() {
            return this.dominator;
        }

        public long getDominatorNetSize() {
            return this.dominatorNetSize;
        }

        public List<ClassDominatorRecord> getRecords() {
            return this.records;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int i) {
            this.id = i;
        }

        public long getDominatedRetainedSize() {
            return this.dominatedRetainedSize;
        }

        public void setDominatedRetainedSize(long j) {
            this.dominatedRetainedSize = j;
        }

        public long getDominatorRetainedSize() {
            return this.dominatorRetainedSize;
        }

        public void setDominatorRetainedSize(long j) {
            this.dominatorRetainedSize = j;
        }
    }

    public DominatorsSummary(ClassDominatorRecord[] classDominatorRecordArr, ISnapshot iSnapshot) {
        this.classDominatorRecords = classDominatorRecordArr;
        this.snapshot = iSnapshot;
        for (ClassDominatorRecord classDominatorRecord : classDominatorRecordArr) {
            classDominatorRecord.summary = this;
        }
    }

    public final Object getData() {
        return this.data;
    }

    public final void setData(Object obj) {
        this.data = obj;
    }

    public final ClassDominatorRecord[] getClassDominatorRecords() {
        return this.classDominatorRecords;
    }

    public final ClassloaderDominatorRecord[] getClassloaderDominatorRecords() {
        return getClassloaderDominatorRecords(ClassloaderDominatorRecord.class);
    }

    public final <C extends ClassloaderDominatorRecord> C[] getClassloaderDominatorRecords(Class<C> cls) {
        synchronized (this) {
            if (this.classloaderDominatorRecords == null) {
                this.classloaderDominatorRecords = load(cls);
            }
        }
        return (ClassloaderDominatorRecord[]) this.classloaderDominatorRecords;
    }

    private ClassloaderDominatorRecord[] load(Class<ClassloaderDominatorRecord> cls) {
        ClassDominatorRecord[] classDominatorRecordArr;
        try {
            HashMap hashMap = new HashMap();
            for (ClassDominatorRecord classDominatorRecord : this.classDominatorRecords) {
                ClassloaderDominatorRecord classloaderDominatorRecord = (ClassloaderDominatorRecord) hashMap.get(Integer.valueOf(classDominatorRecord.getClassloaderId()));
                if (classloaderDominatorRecord == null) {
                    Integer valueOf = Integer.valueOf(classDominatorRecord.getClassloaderId());
                    ClassloaderDominatorRecord newInstance = cls.newInstance();
                    hashMap.put(valueOf, newInstance);
                    newInstance.setId(classDominatorRecord.getClassloaderId());
                    if (newInstance.getId() == -1) {
                        newInstance.name = "<ROOT>";
                    } else {
                        IObject object = this.snapshot.getObject(newInstance.id);
                        newInstance.name = object.getClassSpecificName();
                        if (newInstance.name == null) {
                            newInstance.name = object.getTechnicalName();
                        }
                    }
                    classloaderDominatorRecord = newInstance;
                }
                classloaderDominatorRecord.dominated += classDominatorRecord.getDominatedCount();
                classloaderDominatorRecord.dominator += classDominatorRecord.getDominatorCount();
                classloaderDominatorRecord.dominatedNetSize += classDominatorRecord.getDominatedNetSize();
                classloaderDominatorRecord.dominatorNetSize += classDominatorRecord.getDominatorNetSize();
                classloaderDominatorRecord.records.add(classDominatorRecord);
            }
            return (ClassloaderDominatorRecord[]) hashMap.values().toArray(new ClassloaderDominatorRecord[hashMap.size()]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Comparator<Object> reverseComparator(final Comparator<Object> comparator) {
        return new Comparator<Object>() {
            public final int compare(Object obj, Object obj2) {
                return comparator.compare(obj2, obj);
            }
        };
    }
}
