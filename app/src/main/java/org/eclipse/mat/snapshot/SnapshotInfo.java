package org.eclipse.mat.snapshot;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SnapshotInfo implements Serializable {
    private static final long serialVersionUID = 4;
    protected Date creationDate;
    protected int identifierSize;
    protected String jvmInfo;
    protected int numberOfClassLoaders;
    protected int numberOfClasses;
    protected int numberOfGCRoots;
    protected int numberOfObjects;
    protected String path;
    protected String prefix;
    protected Map<String, Serializable> properties;
    protected long usedHeapSize;

    public SnapshotInfo(String str, String str2, String str3, int i, Date date, int i2, int i3, int i4, int i5, long j) {
        this.path = str;
        this.prefix = str2;
        this.jvmInfo = str3;
        this.identifierSize = i;
        this.creationDate = date != null ? new Date(date.getTime()) : null;
        this.numberOfObjects = i2;
        this.numberOfGCRoots = i3;
        this.numberOfClasses = i4;
        this.numberOfClassLoaders = i5;
        this.usedHeapSize = j;
        this.properties = new HashMap();
    }

    @Deprecated
    public SnapshotInfo(String str, String str2, int i, Date date, int i2, int i3, int i4, int i5, long j) {
        this(str, prefix(str), str2, i, date, i2, i3, i4, i5, j);
    }

    private static String prefix(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            return str.substring(0, lastIndexOf + 1);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(DjangoUtils.EXTENSION_SEPARATOR);
        return sb.toString();
    }

    public Serializable getProperty(String str) {
        return this.properties.get(str);
    }

    public Serializable setProperty(String str, Serializable serializable) {
        return this.properties.put(str, serializable);
    }

    public String getPath() {
        return this.path;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getJvmInfo() {
        return this.jvmInfo;
    }

    public int getIdentifierSize() {
        return this.identifierSize;
    }

    public Date getCreationDate() {
        if (this.creationDate != null) {
            return new Date(this.creationDate.getTime());
        }
        return null;
    }

    public int getNumberOfObjects() {
        return this.numberOfObjects;
    }

    public int getNumberOfGCRoots() {
        return this.numberOfGCRoots;
    }

    public int getNumberOfClasses() {
        return this.numberOfClasses;
    }

    public int getNumberOfClassLoaders() {
        return this.numberOfClassLoaders;
    }

    public long getUsedHeapSize() {
        return this.usedHeapSize;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Path: ");
        sb.append(this.path);
        sb.append("\r\nJVM Info: ");
        sb.append(this.jvmInfo);
        sb.append("\r\nIdentifier Size: ");
        sb.append(this.identifierSize);
        sb.append("\r\nCreation Date: ");
        sb.append(this.creationDate);
        sb.append("\r\nNumber of Objects: ");
        sb.append(this.numberOfObjects);
        sb.append("\r\nNumber of GC roots: ");
        sb.append(this.numberOfGCRoots);
        sb.append("\r\nNumber of Classes: ");
        sb.append(this.numberOfClasses);
        sb.append("\r\nNumber of ClassLoaders: ");
        sb.append(this.numberOfClassLoaders);
        sb.append("\r\nUsed Heap Size: ");
        sb.append(this.usedHeapSize);
        return sb.toString();
    }
}
