package org.eclipse.mat.snapshot;

public class SnapshotFormat {
    private String[] fileExtensions;
    private String name;

    public SnapshotFormat(String str, String[] strArr) {
        this.fileExtensions = strArr;
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public String[] getFileExtensions() {
        return this.fileExtensions;
    }
}
