package com.squareup.leakcanary;

import java.io.File;
import java.io.Serializable;

public final class HeapDump implements Serializable {
    public final ExcludedRefs excludedRefs;
    public final long gcDurationMs;
    public final long heapDumpDurationMs;
    public final File heapDumpFile;
    public final String referenceKey;
    public final String referenceName;
    public final long watchDurationMs;

    public interface Listener {
        void analyze(HeapDump heapDump);
    }

    public HeapDump(File file, String str, String str2, ExcludedRefs excludedRefs2, long j, long j2, long j3) {
        this.heapDumpFile = (File) Preconditions.checkNotNull(file, "heapDumpFile");
        this.referenceKey = (String) Preconditions.checkNotNull(str, "referenceKey");
        this.referenceName = (String) Preconditions.checkNotNull(str2, "referenceName");
        this.excludedRefs = (ExcludedRefs) Preconditions.checkNotNull(excludedRefs2, "excludedRefs");
        this.watchDurationMs = j;
        this.gcDurationMs = j2;
        this.heapDumpDurationMs = j3;
    }

    public final HeapDump renameFile(File file) {
        this.heapDumpFile.renameTo(file);
        HeapDump heapDump = new HeapDump(file, this.referenceKey, this.referenceName, this.excludedRefs, this.watchDurationMs, this.gcDurationMs, this.heapDumpDurationMs);
        return heapDump;
    }
}
