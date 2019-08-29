package org.eclipse.mat.parser.internal;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.parser.IIndexBuilder;
import org.eclipse.mat.parser.internal.util.ParserRegistry;
import org.eclipse.mat.parser.internal.util.ParserRegistry.Parser;
import org.eclipse.mat.parser.model.XSnapshotInfo;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.MessageUtil;

public class SnapshotFactory {
    private Map<File, SnapshotEntry> snapshotCache = new HashMap();

    static class SnapshotEntry {
        /* access modifiers changed from: private */
        public WeakReference<ISnapshot> snapshot;
        /* access modifiers changed from: private */
        public int usageCount;

        public SnapshotEntry(int i, ISnapshot iSnapshot) {
            this.usageCount = i;
            this.snapshot = new WeakReference<>(iSnapshot);
        }
    }

    public ISnapshot openSnapshot(File file, Map<String, String> map, IProgressListener iProgressListener) throws SnapshotException {
        String str;
        SnapshotEntry snapshotEntry = this.snapshotCache.get(file);
        if (snapshotEntry != null) {
            ISnapshot iSnapshot = (ISnapshot) snapshotEntry.snapshot.get();
            if (iSnapshot != null) {
                snapshotEntry.usageCount = snapshotEntry.usageCount + 1;
                return iSnapshot;
            }
        }
        String absolutePath = file.getAbsolutePath();
        int lastIndexOf = absolutePath.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            str = absolutePath.substring(0, lastIndexOf + 1);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(absolutePath);
            sb.append(".");
            str = sb.toString();
        }
        deleteIndexFiles(file);
        ISnapshot parse = parse(file, str, map, iProgressListener);
        this.snapshotCache.put(file, new SnapshotEntry(1, parse));
        return parse;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0039, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0040, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void dispose(org.eclipse.mat.snapshot.ISnapshot r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.util.Map<java.io.File, org.eclipse.mat.parser.internal.SnapshotFactory$SnapshotEntry> r0 = r3.snapshotCache     // Catch:{ all -> 0x0041 }
            java.util.Collection r0 = r0.values()     // Catch:{ all -> 0x0041 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0041 }
        L_0x000b:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x0041 }
            if (r1 == 0) goto L_0x003a
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x0041 }
            org.eclipse.mat.parser.internal.SnapshotFactory$SnapshotEntry r1 = (org.eclipse.mat.parser.internal.SnapshotFactory.SnapshotEntry) r1     // Catch:{ all -> 0x0041 }
            java.lang.ref.WeakReference r2 = r1.snapshot     // Catch:{ all -> 0x0041 }
            java.lang.Object r2 = r2.get()     // Catch:{ all -> 0x0041 }
            org.eclipse.mat.snapshot.ISnapshot r2 = (org.eclipse.mat.snapshot.ISnapshot) r2     // Catch:{ all -> 0x0041 }
            if (r2 != 0) goto L_0x0027
            r0.remove()     // Catch:{ all -> 0x0041 }
            goto L_0x000b
        L_0x0027:
            if (r2 != r4) goto L_0x000b
            r1.usageCount = r1.usageCount - 1     // Catch:{ all -> 0x0041 }
            int r1 = r1.usageCount     // Catch:{ all -> 0x0041 }
            if (r1 != 0) goto L_0x0038
            r4.dispose()     // Catch:{ all -> 0x0041 }
            r0.remove()     // Catch:{ all -> 0x0041 }
        L_0x0038:
            monitor-exit(r3)
            return
        L_0x003a:
            if (r4 == 0) goto L_0x003f
            r4.dispose()     // Catch:{ all -> 0x0041 }
        L_0x003f:
            monitor-exit(r3)
            return
        L_0x0041:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.internal.SnapshotFactory.dispose(org.eclipse.mat.snapshot.ISnapshot):void");
    }

    private ISnapshot parse(File file, String str, Map<String, String> map, IProgressListener iProgressListener) throws SnapshotException {
        List<Parser> matchParser = ParserRegistry.matchParser(file.getName());
        if (matchParser.isEmpty()) {
            matchParser.addAll(ParserRegistry.allParsers());
        }
        ArrayList arrayList = new ArrayList();
        for (Parser next : matchParser) {
            IIndexBuilder indexBuilder = next.getIndexBuilder();
            if (indexBuilder != null) {
                try {
                    indexBuilder.init(file, str);
                    XSnapshotInfo xSnapshotInfo = new XSnapshotInfo();
                    xSnapshotInfo.setPath(file.getAbsolutePath());
                    xSnapshotInfo.setPrefix(str);
                    xSnapshotInfo.setProperty("$heapFormat", next.getId());
                    if (Boolean.parseBoolean(map.get("keep_unreachable_objects"))) {
                        xSnapshotInfo.setProperty("keep_unreachable_objects", Integer.valueOf(2048));
                    }
                    PreliminaryIndexImpl preliminaryIndexImpl = new PreliminaryIndexImpl(xSnapshotInfo);
                    indexBuilder.fill(preliminaryIndexImpl, iProgressListener);
                    SnapshotImplBuilder snapshotImplBuilder = new SnapshotImplBuilder(preliminaryIndexImpl.getSnapshotInfo());
                    indexBuilder.clean(GarbageCleaner.clean(preliminaryIndexImpl, snapshotImplBuilder, map, iProgressListener), iProgressListener);
                    SnapshotImpl create = snapshotImplBuilder.create(next);
                    create.calculateDominatorTree(iProgressListener);
                    return create;
                } catch (IOException e) {
                    arrayList.add(e);
                    indexBuilder.cancel();
                } catch (Exception e2) {
                    indexBuilder.cancel();
                    throw SnapshotException.rethrow(e2);
                }
            }
        }
        throw new SnapshotException(MessageUtil.format(Messages.SnapshotFactoryImpl_Error_NoParserRegistered, file.getName()));
    }

    private void deleteIndexFiles(File file) {
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            parentFile = new File(".");
        }
        final String name = file.getName();
        int lastIndexOf = name.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            name = name.substring(0, lastIndexOf);
        }
        final Pattern compile = Pattern.compile("\\.(.*\\.)?index$");
        final Pattern compile2 = Pattern.compile("\\.inbound\\.index.*\\.log$");
        File[] listFiles = parentFile.listFiles(new FileFilter() {
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return false;
                }
                String name = file.getName();
                if (!name.startsWith(name) || (!compile.matcher(name).matches() && !compile2.matcher(name).matches())) {
                    return false;
                }
                return true;
            }
        });
        if (listFiles != null) {
            for (File delete : listFiles) {
                delete.delete();
            }
        }
    }
}
