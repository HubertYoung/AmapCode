package org.eclipse.mat.hprof;

import com.alibaba.wireless.security.SecExceptionCode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.IteratorLong;
import org.eclipse.mat.hprof.extension.IParsingEnhancer;
import org.eclipse.mat.parser.IIndexBuilder;
import org.eclipse.mat.parser.IPreliminaryIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2LongIndex;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.IProgressListener.OperationCanceledException;
import org.eclipse.mat.util.MessageUtil;
import org.eclipse.mat.util.SimpleMonitor;
import org.eclipse.mat.util.SimpleMonitor.Listener;

public class HprofIndexBuilder implements IIndexBuilder {
    private List<IParsingEnhancer> enhancers;
    private File file;
    private IOne2LongIndex id2position;
    private String prefix;

    static final class IndexIterator implements IteratorLong {
        private final IOne2LongIndex id2position;
        private int nextIndex;
        private final int[] purgedMapping;

        private IndexIterator(IOne2LongIndex iOne2LongIndex, int[] iArr) {
            this.nextIndex = -1;
            this.id2position = iOne2LongIndex;
            this.purgedMapping = iArr;
            findNext();
        }

        public final boolean hasNext() {
            return this.nextIndex < this.purgedMapping.length;
        }

        public final long next() {
            long j = this.id2position.get(this.nextIndex);
            findNext();
            return j;
        }

        /* access modifiers changed from: protected */
        public final void findNext() {
            this.nextIndex++;
            while (this.nextIndex < this.purgedMapping.length && this.purgedMapping[this.nextIndex] < 0) {
                this.nextIndex++;
            }
        }
    }

    public void init(File file2, String str) {
        this.file = file2;
        this.prefix = str;
        this.enhancers = new ArrayList();
    }

    public void fill(IPreliminaryIndex iPreliminaryIndex, IProgressListener iProgressListener) throws SnapshotException, IOException {
        SimpleMonitor simpleMonitor = new SimpleMonitor(MessageUtil.format(Messages.HprofIndexBuilder_Parsing, this.file.getAbsolutePath()), iProgressListener, new int[]{500, SecExceptionCode.SEC_ERROR_SIMULATORDETECT});
        iProgressListener.beginTask(MessageUtil.format(Messages.HprofIndexBuilder_Parsing, this.file.getName()), 3000);
        HprofParserHandlerImpl hprofParserHandlerImpl = new HprofParserHandlerImpl();
        hprofParserHandlerImpl.beforePass1(iPreliminaryIndex.getSnapshotInfo());
        Listener listener = (Listener) simpleMonitor.nextMonitor();
        listener.beginTask(MessageUtil.format(Messages.HprofIndexBuilder_Scanning, this.file.getAbsolutePath()), (int) (this.file.length() / 1000));
        new Pass1Parser(hprofParserHandlerImpl, listener).read(this.file);
        if (iProgressListener.isCanceled()) {
            throw new OperationCanceledException();
        }
        listener.done();
        hprofParserHandlerImpl.beforePass2(iProgressListener);
        Listener listener2 = (Listener) simpleMonitor.nextMonitor();
        listener2.beginTask(MessageUtil.format(Messages.HprofIndexBuilder_ExtractingObjects, this.file.getAbsolutePath()), (int) (this.file.length() / 1000));
        new Pass2Parser(hprofParserHandlerImpl, listener2).read(this.file);
        if (iProgressListener.isCanceled()) {
            throw new OperationCanceledException();
        }
        listener2.done();
        if (iProgressListener.isCanceled()) {
            throw new OperationCanceledException();
        }
        for (IParsingEnhancer onParsingCompleted : this.enhancers) {
            onParsingCompleted.onParsingCompleted(hprofParserHandlerImpl.getSnapshotInfo());
        }
        this.id2position = hprofParserHandlerImpl.fillIn(iPreliminaryIndex);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:0|1|2|3|4|5|7) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x003f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void clean(int[] r6, org.eclipse.mat.util.IProgressListener r7) throws java.io.IOException {
        /*
            r5 = this;
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r5.prefix
            r1.append(r2)
            java.lang.String r2 = "o2hprof.index"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            org.eclipse.mat.hprof.Messages r1 = org.eclipse.mat.hprof.Messages.HprofIndexBuilder_Writing
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = r0.getAbsolutePath()
            r4 = 0
            r2[r4] = r3
            java.lang.String r1 = org.eclipse.mat.util.MessageUtil.format(r1, r2)
            r7.subTask(r1)
            org.eclipse.mat.parser.index.IndexWriter$LongIndexStreamer r7 = new org.eclipse.mat.parser.index.IndexWriter$LongIndexStreamer
            r7.<init>()
            org.eclipse.mat.hprof.HprofIndexBuilder$IndexIterator r1 = new org.eclipse.mat.hprof.HprofIndexBuilder$IndexIterator
            org.eclipse.mat.parser.index.IIndexReader$IOne2LongIndex r2 = r5.id2position
            r3 = 0
            r1.<init>(r2, r6)
            org.eclipse.mat.parser.index.IIndexReader$IOne2LongIndex r6 = r7.writeTo(r0, r1)
            r6.close()     // Catch:{ IOException -> 0x003f }
        L_0x003f:
            org.eclipse.mat.parser.index.IIndexReader$IOne2LongIndex r6 = r5.id2position     // Catch:{ IOException -> 0x0044 }
            r6.close()     // Catch:{ IOException -> 0x0044 }
        L_0x0044:
            org.eclipse.mat.parser.index.IIndexReader$IOne2LongIndex r6 = r5.id2position
            r6.delete()
            r5.id2position = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.hprof.HprofIndexBuilder.clean(int[], org.eclipse.mat.util.IProgressListener):void");
    }

    public void cancel() {
        if (this.id2position != null) {
            try {
                this.id2position.close();
            } catch (IOException unused) {
            }
            this.id2position.delete();
        }
    }
}
