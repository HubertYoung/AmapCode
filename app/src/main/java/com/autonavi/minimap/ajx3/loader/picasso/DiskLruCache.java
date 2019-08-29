package com.autonavi.minimap.ajx3.loader.picasso;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class DiskLruCache implements Closeable {
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,64}");
    static final String MAGIC = "libcore.io.DiskLruCache";
    /* access modifiers changed from: private */
    public static final OutputStream NULL_OUTPUT_STREAM = new OutputStream() {
        public final void write(int i) throws IOException {
        }
    };
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    private static final int TRIM_MAX_RETRY_COUNT = 7;
    static final String VERSION_1 = "1";
    private final int appVersion;
    private final Callable<Void> cleanupCallable;
    /* access modifiers changed from: private */
    public final File directory;
    final ThreadPoolExecutor executorService;
    private int fileCount = 0;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    /* access modifiers changed from: private */
    public Writer journalWriter;
    private final Map<String, Entry> lruEntries = Collections.synchronizedMap(new LinkedHashMap(0, 0.75f, true));
    private int maxFileCount;
    private long maxSize;
    private long nextSequenceNumber = 0;
    /* access modifiers changed from: private */
    public int redundantOpCount;
    private long size = 0;
    /* access modifiers changed from: private */
    public final int valueCount;

    public final class Editor {
        private boolean committed;
        /* access modifiers changed from: private */
        public final Entry entry;
        /* access modifiers changed from: private */
        public boolean hasErrors;
        /* access modifiers changed from: private */
        public final boolean[] written;

        class FaultHidingOutputStream extends FilterOutputStream {
            private FaultHidingOutputStream(OutputStream outputStream) {
                super(outputStream);
            }

            public void write(int i) {
                try {
                    this.out.write(i);
                } catch (IOException unused) {
                    Editor.this.hasErrors = true;
                }
            }

            public void write(byte[] bArr, int i, int i2) {
                try {
                    this.out.write(bArr, i, i2);
                } catch (IOException unused) {
                    Editor.this.hasErrors = true;
                }
            }

            public void close() {
                try {
                    this.out.close();
                } catch (IOException unused) {
                    Editor.this.hasErrors = true;
                }
            }

            public void flush() {
                try {
                    this.out.flush();
                } catch (IOException unused) {
                    Editor.this.hasErrors = true;
                }
            }
        }

        private Editor(Entry entry2) {
            this.entry = entry2;
            this.written = entry2.readable ? null : new boolean[DiskLruCache.this.valueCount];
        }

        public final InputStream newInputStream(int i) throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                } else if (!this.entry.readable) {
                    return null;
                } else {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(this.entry.getCleanFile(i));
                        return fileInputStream;
                    } catch (FileNotFoundException unused) {
                        return null;
                    }
                }
            }
        }

        public final String getString(int i) throws IOException {
            InputStream newInputStream = newInputStream(i);
            if (newInputStream != null) {
                return DiskLruCache.inputStreamToString(newInputStream);
            }
            return null;
        }

        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x002a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.io.OutputStream newOutputStream(int r4) throws java.io.IOException {
            /*
                r3 = this;
                com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache r0 = com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.this
                monitor-enter(r0)
                com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry r1 = r3.entry     // Catch:{ all -> 0x0046 }
                com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Editor r1 = r1.currentEditor     // Catch:{ all -> 0x0046 }
                if (r1 == r3) goto L_0x0011
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0046 }
                r4.<init>()     // Catch:{ all -> 0x0046 }
                throw r4     // Catch:{ all -> 0x0046 }
            L_0x0011:
                com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry r1 = r3.entry     // Catch:{ all -> 0x0046 }
                boolean r1 = r1.readable     // Catch:{ all -> 0x0046 }
                if (r1 != 0) goto L_0x001e
                boolean[] r1 = r3.written     // Catch:{ all -> 0x0046 }
                r2 = 1
                r1[r4] = r2     // Catch:{ all -> 0x0046 }
            L_0x001e:
                com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry r1 = r3.entry     // Catch:{ all -> 0x0046 }
                java.io.File r4 = r1.getDirtyFile(r4)     // Catch:{ all -> 0x0046 }
                java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x002a }
                r1.<init>(r4)     // Catch:{ FileNotFoundException -> 0x002a }
                goto L_0x0038
            L_0x002a:
                com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache r1 = com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.this     // Catch:{ all -> 0x0046 }
                java.io.File r1 = r1.directory     // Catch:{ all -> 0x0046 }
                r1.mkdirs()     // Catch:{ all -> 0x0046 }
                java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0040 }
                r1.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0040 }
            L_0x0038:
                com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Editor$FaultHidingOutputStream r4 = new com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Editor$FaultHidingOutputStream     // Catch:{ all -> 0x0046 }
                r2 = 0
                r4.<init>(r1)     // Catch:{ all -> 0x0046 }
                monitor-exit(r0)     // Catch:{ all -> 0x0046 }
                return r4
            L_0x0040:
                java.io.OutputStream r4 = com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.NULL_OUTPUT_STREAM     // Catch:{ all -> 0x0046 }
                monitor-exit(r0)     // Catch:{ all -> 0x0046 }
                return r4
            L_0x0046:
                r4 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0046 }
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Editor.newOutputStream(int):java.io.OutputStream");
        }

        public final void set(int i, String str) throws IOException {
            OutputStreamWriter outputStreamWriter = null;
            try {
                OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(newOutputStream(i), Utils.UTF_8);
                try {
                    outputStreamWriter2.write(str);
                    Utils.closeQuietly((Closeable) outputStreamWriter2);
                } catch (Throwable th) {
                    th = th;
                    outputStreamWriter = outputStreamWriter2;
                    Utils.closeQuietly((Closeable) outputStreamWriter);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                Utils.closeQuietly((Closeable) outputStreamWriter);
                throw th;
            }
        }

        public final void commit() throws IOException {
            if (this.hasErrors) {
                DiskLruCache.this.completeEdit(this, false);
                DiskLruCache.this.remove(this.entry.key);
            } else {
                DiskLruCache.this.completeEdit(this, true);
            }
            this.committed = true;
        }

        public final void abort() throws IOException {
            DiskLruCache.this.completeEdit(this, false);
        }

        public final void abortUnlessCommitted() {
            if (!this.committed) {
                try {
                    abort();
                } catch (IOException unused) {
                }
            }
        }
    }

    final class Entry {
        /* access modifiers changed from: private */
        public Editor currentEditor;
        /* access modifiers changed from: private */
        public final String key;
        /* access modifiers changed from: private */
        public final long[] lengths;
        /* access modifiers changed from: private */
        public boolean readable;
        /* access modifiers changed from: private */
        public long sequenceNumber;
        private AtomicInteger snapshotCount;

        private Entry(String str) {
            this.key = str;
            this.lengths = new long[DiskLruCache.this.valueCount];
            this.snapshotCount = new AtomicInteger(0);
        }

        public final String getLengths() throws IOException {
            StringBuilder sb = new StringBuilder();
            for (long append : this.lengths) {
                sb.append(' ');
                sb.append(append);
            }
            return sb.toString();
        }

        /* access modifiers changed from: private */
        public void setLengths(String[] strArr) throws IOException {
            if (strArr.length != DiskLruCache.this.valueCount) {
                throw invalidLengths(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    this.lengths[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException unused) {
                    throw invalidLengths(strArr);
                }
            }
        }

        private IOException invalidLengths(String[] strArr) throws IOException {
            StringBuilder sb = new StringBuilder("unexpected journal line: ");
            sb.append(Arrays.toString(strArr));
            throw new IOException(sb.toString());
        }

        public final File getCleanFile(int i) {
            File access$2000 = DiskLruCache.this.directory;
            StringBuilder sb = new StringBuilder();
            sb.append(this.key);
            sb.append(i);
            return new File(access$2000, sb.toString());
        }

        public final File getDirtyFile(int i) {
            File access$2000 = DiskLruCache.this.directory;
            StringBuilder sb = new StringBuilder();
            sb.append(this.key);
            sb.append(i);
            sb.append(FilePathHelper.SUFFIX_DOT_TMP);
            return new File(access$2000, sb.toString());
        }

        public final int incrementSnapshotCount() {
            return this.snapshotCount.incrementAndGet();
        }

        public final int decrementSnapshotCount() {
            return this.snapshotCount.decrementAndGet();
        }

        public final int getSnapshotCount() {
            return this.snapshotCount.get();
        }
    }

    public final class Snapshot implements Closeable {
        private File[] files;
        private final InputStream[] ins;
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;

        private Snapshot(String str, long j, File[] fileArr, InputStream[] inputStreamArr, long[] jArr) {
            this.key = str;
            this.sequenceNumber = j;
            this.files = fileArr;
            this.ins = inputStreamArr;
            this.lengths = jArr;
        }

        public final Editor edit() throws IOException {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public final File getFile(int i) {
            return this.files[i];
        }

        public final InputStream getInputStream(int i) {
            return this.ins[i];
        }

        public final String getString(int i) throws IOException {
            return DiskLruCache.inputStreamToString(getInputStream(i));
        }

        public final long getLength(int i) {
            return this.lengths[i];
        }

        public final void close() {
            DiskLruCache.this.decrementSnapshotCount(this.key);
            for (InputStream closeQuietly : this.ins) {
                Utils.closeQuietly(closeQuietly);
            }
        }
    }

    private DiskLruCache(File file, int i, int i2, long j, int i3) {
        File file2 = file;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
        this.executorService = threadPoolExecutor;
        this.cleanupCallable = new Callable<Void>() {
            /* JADX WARNING: Code restructure failed: missing block: B:11:0x002c, code lost:
                return null;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Void call() throws java.lang.Exception {
                /*
                    r4 = this;
                    com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache r0 = com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.this
                    monitor-enter(r0)
                    com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache r1 = com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.this     // Catch:{ all -> 0x002d }
                    java.io.Writer r1 = r1.journalWriter     // Catch:{ all -> 0x002d }
                    r2 = 0
                    if (r1 != 0) goto L_0x000e
                    monitor-exit(r0)     // Catch:{ all -> 0x002d }
                    return r2
                L_0x000e:
                    com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache r1 = com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.this     // Catch:{ all -> 0x002d }
                    r1.trimToSize()     // Catch:{ all -> 0x002d }
                    com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache r1 = com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.this     // Catch:{ all -> 0x002d }
                    r1.trimToFileCount()     // Catch:{ all -> 0x002d }
                    com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache r1 = com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.this     // Catch:{ all -> 0x002d }
                    boolean r1 = r1.journalRebuildRequired()     // Catch:{ all -> 0x002d }
                    if (r1 == 0) goto L_0x002b
                    com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache r1 = com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.this     // Catch:{ all -> 0x002d }
                    r1.rebuildJournal()     // Catch:{ all -> 0x002d }
                    com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache r1 = com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.this     // Catch:{ all -> 0x002d }
                    r3 = 0
                    r1.redundantOpCount = r3     // Catch:{ all -> 0x002d }
                L_0x002b:
                    monitor-exit(r0)     // Catch:{ all -> 0x002d }
                    return r2
                L_0x002d:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x002d }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.AnonymousClass1.call():java.lang.Void");
            }
        };
        this.directory = file2;
        this.appVersion = i;
        this.journalFile = new File(file2, JOURNAL_FILE);
        this.journalFileTmp = new File(file2, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(file2, JOURNAL_FILE_BACKUP);
        this.valueCount = i2;
        this.maxSize = j;
        this.maxFileCount = i3;
    }

    public static DiskLruCache open(File file, int i, int i2, long j, int i3) throws IOException {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i3 <= 0) {
            throw new IllegalArgumentException("maxFileCount <= 0");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        } else {
            File file2 = new File(file, JOURNAL_FILE_BACKUP);
            if (file2.exists()) {
                File file3 = new File(file, JOURNAL_FILE);
                if (file3.exists()) {
                    file2.delete();
                } else {
                    renameTo(file2, file3, false);
                }
            }
            DiskLruCache diskLruCache = new DiskLruCache(file, i, i2, j, i3);
            if (diskLruCache.journalFile.exists()) {
                try {
                    diskLruCache.readJournal();
                    diskLruCache.processJournal();
                    diskLruCache.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(diskLruCache.journalFile, true), Utils.US_ASCII));
                    return diskLruCache;
                } catch (IOException e) {
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder("DiskLruCache ");
                    sb.append(file);
                    sb.append(" is corrupt: ");
                    sb.append(e.getMessage());
                    sb.append(", removing");
                    diskLruCache.delete();
                }
            }
            file.mkdirs();
            DiskLruCache diskLruCache2 = new DiskLruCache(file, i, i2, j, i3);
            diskLruCache2.rebuildJournal();
            return diskLruCache2;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:16|17|18|19) */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r8.redundantOpCount = r1 - r8.lruEntries.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006a, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x005e */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:20:0x006b=Splitter:B:20:0x006b, B:16:0x005e=Splitter:B:16:0x005e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readJournal() throws java.io.IOException {
        /*
            r8 = this;
            com.autonavi.minimap.ajx3.loader.picasso.StrictLineReader r0 = new com.autonavi.minimap.ajx3.loader.picasso.StrictLineReader
            java.io.FileInputStream r1 = new java.io.FileInputStream
            java.io.File r2 = r8.journalFile
            r1.<init>(r2)
            java.nio.charset.Charset r2 = com.autonavi.minimap.ajx3.loader.picasso.Utils.US_ASCII
            r0.<init>(r1, r2)
            java.lang.String r1 = r0.readLine()     // Catch:{ all -> 0x009d }
            java.lang.String r2 = r0.readLine()     // Catch:{ all -> 0x009d }
            java.lang.String r3 = r0.readLine()     // Catch:{ all -> 0x009d }
            java.lang.String r4 = r0.readLine()     // Catch:{ all -> 0x009d }
            java.lang.String r5 = r0.readLine()     // Catch:{ all -> 0x009d }
            java.lang.String r6 = "libcore.io.DiskLruCache"
            boolean r6 = r6.equals(r1)     // Catch:{ all -> 0x009d }
            if (r6 == 0) goto L_0x006b
            java.lang.String r6 = "1"
            boolean r6 = r6.equals(r2)     // Catch:{ all -> 0x009d }
            if (r6 == 0) goto L_0x006b
            int r6 = r8.appVersion     // Catch:{ all -> 0x009d }
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ all -> 0x009d }
            boolean r3 = r6.equals(r3)     // Catch:{ all -> 0x009d }
            if (r3 == 0) goto L_0x006b
            int r3 = r8.valueCount     // Catch:{ all -> 0x009d }
            java.lang.String r3 = java.lang.Integer.toString(r3)     // Catch:{ all -> 0x009d }
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x009d }
            if (r3 == 0) goto L_0x006b
            java.lang.String r3 = ""
            boolean r3 = r3.equals(r5)     // Catch:{ all -> 0x009d }
            if (r3 != 0) goto L_0x0053
            goto L_0x006b
        L_0x0053:
            r1 = 0
        L_0x0054:
            java.lang.String r2 = r0.readLine()     // Catch:{ EOFException -> 0x005e }
            r8.readJournalLine(r2)     // Catch:{ EOFException -> 0x005e }
            int r1 = r1 + 1
            goto L_0x0054
        L_0x005e:
            java.util.Map<java.lang.String, com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry> r2 = r8.lruEntries     // Catch:{ all -> 0x009d }
            int r2 = r2.size()     // Catch:{ all -> 0x009d }
            int r1 = r1 - r2
            r8.redundantOpCount = r1     // Catch:{ all -> 0x009d }
            com.autonavi.minimap.ajx3.loader.picasso.Utils.closeQuietly(r0)
            return
        L_0x006b:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x009d }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x009d }
            java.lang.String r7 = "unexpected journal header: ["
            r6.<init>(r7)     // Catch:{ all -> 0x009d }
            r6.append(r1)     // Catch:{ all -> 0x009d }
            java.lang.String r1 = ", "
            r6.append(r1)     // Catch:{ all -> 0x009d }
            r6.append(r2)     // Catch:{ all -> 0x009d }
            java.lang.String r1 = ", "
            r6.append(r1)     // Catch:{ all -> 0x009d }
            r6.append(r4)     // Catch:{ all -> 0x009d }
            java.lang.String r1 = ", "
            r6.append(r1)     // Catch:{ all -> 0x009d }
            r6.append(r5)     // Catch:{ all -> 0x009d }
            java.lang.String r1 = "]"
            r6.append(r1)     // Catch:{ all -> 0x009d }
            java.lang.String r1 = r6.toString()     // Catch:{ all -> 0x009d }
            r3.<init>(r1)     // Catch:{ all -> 0x009d }
            throw r3     // Catch:{ all -> 0x009d }
        L_0x009d:
            r1 = move-exception
            com.autonavi.minimap.ajx3.loader.picasso.Utils.closeQuietly(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.readJournal():void");
    }

    private void readJournalLine(String str) throws IOException {
        String str2;
        int indexOf = str.indexOf(32);
        if (indexOf == -1) {
            throw new IOException("unexpected journal line: ".concat(String.valueOf(str)));
        }
        int i = indexOf + 1;
        int indexOf2 = str.indexOf(32, i);
        if (indexOf2 == -1) {
            str2 = str.substring(i);
            if (indexOf == 6 && str.startsWith(REMOVE)) {
                this.lruEntries.remove(str2);
                return;
            }
        } else {
            str2 = str.substring(i, indexOf2);
        }
        Entry entry = this.lruEntries.get(str2);
        if (entry == null) {
            entry = new Entry(str2);
            this.lruEntries.put(str2, entry);
        }
        if (indexOf2 != -1 && indexOf == 5 && str.startsWith(CLEAN)) {
            String[] split = str.substring(indexOf2 + 1).split(Token.SEPARATOR);
            entry.readable = true;
            entry.currentEditor = null;
            entry.setLengths(split);
        } else if (indexOf2 == -1 && indexOf == 5 && str.startsWith(DIRTY)) {
            entry.currentEditor = new Editor(entry);
        } else if (indexOf2 != -1 || indexOf != 4 || !str.startsWith(READ)) {
            throw new IOException("unexpected journal line: ".concat(String.valueOf(str)));
        }
    }

    private void processJournal() throws IOException {
        deleteIfExists(this.journalFileTmp);
        Iterator<Entry> it = this.lruEntries.values().iterator();
        while (it.hasNext()) {
            Entry next = it.next();
            int i = 0;
            if (next.currentEditor == null) {
                while (i < this.valueCount) {
                    this.size += next.lengths[i];
                    this.fileCount++;
                    i++;
                }
            } else {
                next.currentEditor = null;
                while (i < this.valueCount) {
                    deleteIfExists(next.getCleanFile(i));
                    deleteIfExists(next.getDirtyFile(i));
                    i++;
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public synchronized void rebuildJournal() throws IOException {
        if (this.journalWriter != null) {
            this.journalWriter.close();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFileTmp), Utils.US_ASCII));
        try {
            bufferedWriter.write(MAGIC);
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.appVersion));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.valueCount));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (Entry next : this.lruEntries.values()) {
                if (next.currentEditor != null) {
                    StringBuilder sb = new StringBuilder("DIRTY ");
                    sb.append(next.key);
                    sb.append(10);
                    bufferedWriter.write(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder("CLEAN ");
                    sb2.append(next.key);
                    sb2.append(next.getLengths());
                    sb2.append(10);
                    bufferedWriter.write(sb2.toString());
                }
            }
            bufferedWriter.close();
            if (this.journalFile.exists()) {
                renameTo(this.journalFile, this.journalFileBackup, true);
            }
            renameTo(this.journalFileTmp, this.journalFile, false);
            this.journalFileBackup.delete();
            this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), Utils.US_ASCII));
        } catch (Throwable th) {
            bufferedWriter.close();
            throw th;
        }
    }

    private static void deleteIfExists(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void renameTo(File file, File file2, boolean z) throws IOException {
        if (z) {
            deleteIfExists(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:32|33|28|27) */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r12.redundantOpCount++;
        r1 = r12.journalWriter;
        r2 = new java.lang.StringBuilder("READ ");
        r2.append(r13);
        r2.append(10);
        r1.append(r2.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005c, code lost:
        if (journalRebuildRequired() == false) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005e, code lost:
        r12.executorService.submit(r12.cleanupCallable);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0065, code lost:
        incrementSnapshotCount(r13);
        r3 = new com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Snapshot(r12, r13, com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Entry.access$1300(r0), r8, r9, com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Entry.access$1100(r0), null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007a, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008c, code lost:
        return null;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x007b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Snapshot get(java.lang.String r13) throws java.io.IOException {
        /*
            r12 = this;
            monitor-enter(r12)
            r12.checkNotClosed()     // Catch:{ all -> 0x008d }
            r12.validateKey(r13)     // Catch:{ all -> 0x008d }
            java.util.Map<java.lang.String, com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry> r0 = r12.lruEntries     // Catch:{ all -> 0x008d }
            java.lang.Object r0 = r0.get(r13)     // Catch:{ all -> 0x008d }
            com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry r0 = (com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Entry) r0     // Catch:{ all -> 0x008d }
            r1 = 0
            if (r0 != 0) goto L_0x0014
            monitor-exit(r12)
            return r1
        L_0x0014:
            boolean r2 = r0.readable     // Catch:{ all -> 0x008d }
            if (r2 != 0) goto L_0x001c
            monitor-exit(r12)
            return r1
        L_0x001c:
            int r2 = r12.valueCount     // Catch:{ all -> 0x008d }
            java.io.File[] r8 = new java.io.File[r2]     // Catch:{ all -> 0x008d }
            int r2 = r12.valueCount     // Catch:{ all -> 0x008d }
            java.io.InputStream[] r9 = new java.io.InputStream[r2]     // Catch:{ all -> 0x008d }
            r2 = 0
            r3 = 0
        L_0x0026:
            int r4 = r12.valueCount     // Catch:{ FileNotFoundException -> 0x007b }
            if (r3 >= r4) goto L_0x003a
            java.io.File r4 = r0.getCleanFile(r3)     // Catch:{ FileNotFoundException -> 0x007b }
            r8[r3] = r4     // Catch:{ FileNotFoundException -> 0x007b }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x007b }
            r5.<init>(r4)     // Catch:{ FileNotFoundException -> 0x007b }
            r9[r3] = r5     // Catch:{ FileNotFoundException -> 0x007b }
            int r3 = r3 + 1
            goto L_0x0026
        L_0x003a:
            int r1 = r12.redundantOpCount     // Catch:{ all -> 0x008d }
            int r1 = r1 + 1
            r12.redundantOpCount = r1     // Catch:{ all -> 0x008d }
            java.io.Writer r1 = r12.journalWriter     // Catch:{ all -> 0x008d }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x008d }
            java.lang.String r3 = "READ "
            r2.<init>(r3)     // Catch:{ all -> 0x008d }
            r2.append(r13)     // Catch:{ all -> 0x008d }
            r3 = 10
            r2.append(r3)     // Catch:{ all -> 0x008d }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x008d }
            r1.append(r2)     // Catch:{ all -> 0x008d }
            boolean r1 = r12.journalRebuildRequired()     // Catch:{ all -> 0x008d }
            if (r1 == 0) goto L_0x0065
            java.util.concurrent.ThreadPoolExecutor r1 = r12.executorService     // Catch:{ all -> 0x008d }
            java.util.concurrent.Callable<java.lang.Void> r2 = r12.cleanupCallable     // Catch:{ all -> 0x008d }
            r1.submit(r2)     // Catch:{ all -> 0x008d }
        L_0x0065:
            r12.incrementSnapshotCount(r13)     // Catch:{ all -> 0x008d }
            com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Snapshot r1 = new com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Snapshot     // Catch:{ all -> 0x008d }
            long r6 = r0.sequenceNumber     // Catch:{ all -> 0x008d }
            long[] r10 = r0.lengths     // Catch:{ all -> 0x008d }
            r11 = 0
            r3 = r1
            r4 = r12
            r5 = r13
            r3.<init>(r5, r6, r8, r9, r10)     // Catch:{ all -> 0x008d }
            monitor-exit(r12)
            return r1
        L_0x007b:
            int r13 = r12.valueCount     // Catch:{ all -> 0x008d }
            if (r2 >= r13) goto L_0x008b
            r13 = r9[r2]     // Catch:{ all -> 0x008d }
            if (r13 == 0) goto L_0x008b
            r13 = r9[r2]     // Catch:{ all -> 0x008d }
            com.autonavi.minimap.ajx3.loader.picasso.Utils.closeQuietly(r13)     // Catch:{ all -> 0x008d }
            int r2 = r2 + 1
            goto L_0x007b
        L_0x008b:
            monitor-exit(r12)
            return r1
        L_0x008d:
            r13 = move-exception
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.get(java.lang.String):com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Snapshot");
    }

    public Editor edit(String str) throws IOException {
        return edit(str, -1);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0021, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Editor edit(java.lang.String r6, long r7) throws java.io.IOException {
        /*
            r5 = this;
            monitor-enter(r5)
            r5.checkNotClosed()     // Catch:{ all -> 0x005e }
            r5.validateKey(r6)     // Catch:{ all -> 0x005e }
            java.util.Map<java.lang.String, com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry> r0 = r5.lruEntries     // Catch:{ all -> 0x005e }
            java.lang.Object r0 = r0.get(r6)     // Catch:{ all -> 0x005e }
            com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry r0 = (com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Entry) r0     // Catch:{ all -> 0x005e }
            r1 = -1
            int r1 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            r2 = 0
            if (r1 == 0) goto L_0x0022
            if (r0 == 0) goto L_0x0020
            long r3 = r0.sequenceNumber     // Catch:{ all -> 0x005e }
            int r7 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x0022
        L_0x0020:
            monitor-exit(r5)
            return r2
        L_0x0022:
            if (r0 != 0) goto L_0x002f
            com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry r0 = new com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry     // Catch:{ all -> 0x005e }
            r0.<init>(r6)     // Catch:{ all -> 0x005e }
            java.util.Map<java.lang.String, com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry> r7 = r5.lruEntries     // Catch:{ all -> 0x005e }
            r7.put(r6, r0)     // Catch:{ all -> 0x005e }
            goto L_0x0037
        L_0x002f:
            com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Editor r7 = r0.currentEditor     // Catch:{ all -> 0x005e }
            if (r7 == 0) goto L_0x0037
            monitor-exit(r5)
            return r2
        L_0x0037:
            com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Editor r7 = new com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Editor     // Catch:{ all -> 0x005e }
            r7.<init>(r0)     // Catch:{ all -> 0x005e }
            r0.currentEditor = r7     // Catch:{ all -> 0x005e }
            java.io.Writer r8 = r5.journalWriter     // Catch:{ all -> 0x005e }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x005e }
            java.lang.String r1 = "DIRTY "
            r0.<init>(r1)     // Catch:{ all -> 0x005e }
            r0.append(r6)     // Catch:{ all -> 0x005e }
            r6 = 10
            r0.append(r6)     // Catch:{ all -> 0x005e }
            java.lang.String r6 = r0.toString()     // Catch:{ all -> 0x005e }
            r8.write(r6)     // Catch:{ all -> 0x005e }
            java.io.Writer r6 = r5.journalWriter     // Catch:{ all -> 0x005e }
            r6.flush()     // Catch:{ all -> 0x005e }
            monitor-exit(r5)
            return r7
        L_0x005e:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.edit(java.lang.String, long):com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Editor");
    }

    public File getDirectory() {
        return this.directory;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public synchronized int getMaxFileCount() {
        return this.maxFileCount;
    }

    public synchronized void setMaxSize(long j) {
        this.maxSize = j;
        this.executorService.submit(this.cleanupCallable);
    }

    public synchronized boolean isFull() {
        return ((double) this.maxSize) * 1.2d < ((double) this.size) || ((double) this.maxFileCount) * 1.5d < ((double) this.fileCount);
    }

    public synchronized void incrementSnapshotCount(String str) {
        Entry entry = this.lruEntries.get(str);
        if (entry != null) {
            entry.incrementSnapshotCount();
        }
    }

    public synchronized void decrementSnapshotCount(String str) {
        Entry entry = this.lruEntries.get(str);
        if (entry != null) {
            entry.decrementSnapshotCount();
        }
    }

    public synchronized long size() {
        try {
        }
        return this.size;
    }

    public synchronized long fileCount() {
        return (long) this.fileCount;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x010e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void completeEdit(com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Editor r11, boolean r12) throws java.io.IOException {
        /*
            r10 = this;
            monitor-enter(r10)
            com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry r0 = r11.entry     // Catch:{ all -> 0x010f }
            com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Editor r1 = r0.currentEditor     // Catch:{ all -> 0x010f }
            if (r1 == r11) goto L_0x0011
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException     // Catch:{ all -> 0x010f }
            r11.<init>()     // Catch:{ all -> 0x010f }
            throw r11     // Catch:{ all -> 0x010f }
        L_0x0011:
            r1 = 0
            if (r12 == 0) goto L_0x004c
            boolean r2 = r0.readable     // Catch:{ all -> 0x010f }
            if (r2 != 0) goto L_0x004c
            r2 = 0
        L_0x001b:
            int r3 = r10.valueCount     // Catch:{ all -> 0x010f }
            if (r2 >= r3) goto L_0x004c
            boolean[] r3 = r11.written     // Catch:{ all -> 0x010f }
            boolean r3 = r3[r2]     // Catch:{ all -> 0x010f }
            if (r3 != 0) goto L_0x003a
            r11.abort()     // Catch:{ all -> 0x010f }
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException     // Catch:{ all -> 0x010f }
            java.lang.String r12 = "Newly created entry didn't create value for index "
            java.lang.String r0 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x010f }
            java.lang.String r12 = r12.concat(r0)     // Catch:{ all -> 0x010f }
            r11.<init>(r12)     // Catch:{ all -> 0x010f }
            throw r11     // Catch:{ all -> 0x010f }
        L_0x003a:
            java.io.File r3 = r0.getDirtyFile(r2)     // Catch:{ all -> 0x010f }
            boolean r3 = r3.exists()     // Catch:{ all -> 0x010f }
            if (r3 != 0) goto L_0x0049
            r11.abort()     // Catch:{ all -> 0x010f }
            monitor-exit(r10)
            return
        L_0x0049:
            int r2 = r2 + 1
            goto L_0x001b
        L_0x004c:
            int r11 = r10.valueCount     // Catch:{ all -> 0x010f }
            r2 = 1
            if (r1 >= r11) goto L_0x0087
            java.io.File r11 = r0.getDirtyFile(r1)     // Catch:{ all -> 0x010f }
            if (r12 == 0) goto L_0x0081
            boolean r3 = r11.exists()     // Catch:{ all -> 0x010f }
            if (r3 == 0) goto L_0x0084
            java.io.File r3 = r0.getCleanFile(r1)     // Catch:{ all -> 0x010f }
            r11.renameTo(r3)     // Catch:{ all -> 0x010f }
            long[] r11 = r0.lengths     // Catch:{ all -> 0x010f }
            r4 = r11[r1]     // Catch:{ all -> 0x010f }
            long r6 = r3.length()     // Catch:{ all -> 0x010f }
            long[] r11 = r0.lengths     // Catch:{ all -> 0x010f }
            r11[r1] = r6     // Catch:{ all -> 0x010f }
            long r8 = r10.size     // Catch:{ all -> 0x010f }
            r11 = 0
            long r8 = r8 - r4
            long r8 = r8 + r6
            r10.size = r8     // Catch:{ all -> 0x010f }
            int r11 = r10.fileCount     // Catch:{ all -> 0x010f }
            int r11 = r11 + r2
            r10.fileCount = r11     // Catch:{ all -> 0x010f }
            goto L_0x0084
        L_0x0081:
            deleteIfExists(r11)     // Catch:{ all -> 0x010f }
        L_0x0084:
            int r1 = r1 + 1
            goto L_0x004c
        L_0x0087:
            int r11 = r10.redundantOpCount     // Catch:{ all -> 0x010f }
            int r11 = r11 + r2
            r10.redundantOpCount = r11     // Catch:{ all -> 0x010f }
            r11 = 0
            r0.currentEditor = r11     // Catch:{ all -> 0x010f }
            boolean r11 = r0.readable     // Catch:{ all -> 0x010f }
            r11 = r11 | r12
            r1 = 10
            if (r11 == 0) goto L_0x00ca
            r0.readable = r2     // Catch:{ all -> 0x010f }
            java.io.Writer r11 = r10.journalWriter     // Catch:{ all -> 0x010f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x010f }
            java.lang.String r3 = "CLEAN "
            r2.<init>(r3)     // Catch:{ all -> 0x010f }
            java.lang.String r3 = r0.key     // Catch:{ all -> 0x010f }
            r2.append(r3)     // Catch:{ all -> 0x010f }
            java.lang.String r3 = r0.getLengths()     // Catch:{ all -> 0x010f }
            r2.append(r3)     // Catch:{ all -> 0x010f }
            r2.append(r1)     // Catch:{ all -> 0x010f }
            java.lang.String r1 = r2.toString()     // Catch:{ all -> 0x010f }
            r11.write(r1)     // Catch:{ all -> 0x010f }
            if (r12 == 0) goto L_0x00ed
            long r11 = r10.nextSequenceNumber     // Catch:{ all -> 0x010f }
            r1 = 1
            long r1 = r1 + r11
            r10.nextSequenceNumber = r1     // Catch:{ all -> 0x010f }
            r0.sequenceNumber = r11     // Catch:{ all -> 0x010f }
            goto L_0x00ed
        L_0x00ca:
            java.util.Map<java.lang.String, com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry> r11 = r10.lruEntries     // Catch:{ all -> 0x010f }
            java.lang.String r12 = r0.key     // Catch:{ all -> 0x010f }
            r11.remove(r12)     // Catch:{ all -> 0x010f }
            java.io.Writer r11 = r10.journalWriter     // Catch:{ all -> 0x010f }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x010f }
            java.lang.String r2 = "REMOVE "
            r12.<init>(r2)     // Catch:{ all -> 0x010f }
            java.lang.String r0 = r0.key     // Catch:{ all -> 0x010f }
            r12.append(r0)     // Catch:{ all -> 0x010f }
            r12.append(r1)     // Catch:{ all -> 0x010f }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x010f }
            r11.write(r12)     // Catch:{ all -> 0x010f }
        L_0x00ed:
            java.io.Writer r11 = r10.journalWriter     // Catch:{ all -> 0x010f }
            r11.flush()     // Catch:{ all -> 0x010f }
            long r11 = r10.size     // Catch:{ all -> 0x010f }
            long r0 = r10.maxSize     // Catch:{ all -> 0x010f }
            int r11 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r11 > 0) goto L_0x0106
            int r11 = r10.fileCount     // Catch:{ all -> 0x010f }
            int r12 = r10.maxFileCount     // Catch:{ all -> 0x010f }
            if (r11 > r12) goto L_0x0106
            boolean r11 = r10.journalRebuildRequired()     // Catch:{ all -> 0x010f }
            if (r11 == 0) goto L_0x010d
        L_0x0106:
            java.util.concurrent.ThreadPoolExecutor r11 = r10.executorService     // Catch:{ all -> 0x010f }
            java.util.concurrent.Callable<java.lang.Void> r12 = r10.cleanupCallable     // Catch:{ all -> 0x010f }
            r11.submit(r12)     // Catch:{ all -> 0x010f }
        L_0x010d:
            monitor-exit(r10)
            return
        L_0x010f:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.completeEdit(com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Editor, boolean):void");
    }

    /* access modifiers changed from: private */
    public boolean journalRebuildRequired() {
        return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0090, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0092, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean remove(java.lang.String r9) throws java.io.IOException {
        /*
            r8 = this;
            monitor-enter(r8)
            r8.checkNotClosed()     // Catch:{ all -> 0x0093 }
            r8.validateKey(r9)     // Catch:{ all -> 0x0093 }
            java.util.Map<java.lang.String, com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry> r0 = r8.lruEntries     // Catch:{ all -> 0x0093 }
            java.lang.Object r0 = r0.get(r9)     // Catch:{ all -> 0x0093 }
            com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry r0 = (com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Entry) r0     // Catch:{ all -> 0x0093 }
            r1 = 0
            if (r0 == 0) goto L_0x0091
            com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Editor r2 = r0.currentEditor     // Catch:{ all -> 0x0093 }
            if (r2 != 0) goto L_0x0091
            int r2 = r0.getSnapshotCount()     // Catch:{ all -> 0x0093 }
            if (r2 <= 0) goto L_0x001f
            goto L_0x0091
        L_0x001f:
            int r2 = r8.valueCount     // Catch:{ all -> 0x0093 }
            r3 = 1
            if (r1 >= r2) goto L_0x0060
            java.io.File r2 = r0.getCleanFile(r1)     // Catch:{ all -> 0x0093 }
            boolean r4 = r2.exists()     // Catch:{ all -> 0x0093 }
            if (r4 == 0) goto L_0x0044
            boolean r4 = r2.delete()     // Catch:{ all -> 0x0093 }
            if (r4 != 0) goto L_0x0044
            java.io.IOException r9 = new java.io.IOException     // Catch:{ all -> 0x0093 }
            java.lang.String r0 = "failed to delete "
            java.lang.String r1 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0093 }
            java.lang.String r0 = r0.concat(r1)     // Catch:{ all -> 0x0093 }
            r9.<init>(r0)     // Catch:{ all -> 0x0093 }
            throw r9     // Catch:{ all -> 0x0093 }
        L_0x0044:
            long r4 = r8.size     // Catch:{ all -> 0x0093 }
            long[] r2 = r0.lengths     // Catch:{ all -> 0x0093 }
            r6 = r2[r1]     // Catch:{ all -> 0x0093 }
            r2 = 0
            long r4 = r4 - r6
            r8.size = r4     // Catch:{ all -> 0x0093 }
            int r2 = r8.fileCount     // Catch:{ all -> 0x0093 }
            int r2 = r2 - r3
            r8.fileCount = r2     // Catch:{ all -> 0x0093 }
            long[] r2 = r0.lengths     // Catch:{ all -> 0x0093 }
            r3 = 0
            r2[r1] = r3     // Catch:{ all -> 0x0093 }
            int r1 = r1 + 1
            goto L_0x001f
        L_0x0060:
            int r0 = r8.redundantOpCount     // Catch:{ all -> 0x0093 }
            int r0 = r0 + r3
            r8.redundantOpCount = r0     // Catch:{ all -> 0x0093 }
            java.io.Writer r0 = r8.journalWriter     // Catch:{ all -> 0x0093 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0093 }
            java.lang.String r2 = "REMOVE "
            r1.<init>(r2)     // Catch:{ all -> 0x0093 }
            r1.append(r9)     // Catch:{ all -> 0x0093 }
            r2 = 10
            r1.append(r2)     // Catch:{ all -> 0x0093 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0093 }
            r0.append(r1)     // Catch:{ all -> 0x0093 }
            java.util.Map<java.lang.String, com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache$Entry> r0 = r8.lruEntries     // Catch:{ all -> 0x0093 }
            r0.remove(r9)     // Catch:{ all -> 0x0093 }
            boolean r9 = r8.journalRebuildRequired()     // Catch:{ all -> 0x0093 }
            if (r9 == 0) goto L_0x008f
            java.util.concurrent.ThreadPoolExecutor r9 = r8.executorService     // Catch:{ all -> 0x0093 }
            java.util.concurrent.Callable<java.lang.Void> r0 = r8.cleanupCallable     // Catch:{ all -> 0x0093 }
            r9.submit(r0)     // Catch:{ all -> 0x0093 }
        L_0x008f:
            monitor-exit(r8)
            return r3
        L_0x0091:
            monitor-exit(r8)
            return r1
        L_0x0093:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.remove(java.lang.String):boolean");
    }

    public synchronized boolean isClosed() {
        return this.journalWriter == null;
    }

    private void checkNotClosed() {
        if (this.journalWriter == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void flush() throws IOException {
        checkNotClosed();
        trimToSize();
        trimToFileCount();
        this.journalWriter.flush();
    }

    public synchronized void close() throws IOException {
        if (this.journalWriter != null) {
            Iterator it = new ArrayList(this.lruEntries.values()).iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                if (entry.currentEditor != null) {
                    entry.currentEditor.abort();
                }
            }
            trimToSize();
            trimToFileCount();
            this.journalWriter.close();
            this.journalWriter = null;
        }
    }

    /* access modifiers changed from: private */
    public void trimToSize() throws IOException {
        int i = 0;
        while (this.size > this.maxSize) {
            int i2 = i + 1;
            if (i <= 7) {
                remove((String) this.lruEntries.entrySet().iterator().next().getKey());
                i = i2;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void trimToFileCount() throws IOException {
        int i = 0;
        while (this.fileCount > this.maxFileCount) {
            int i2 = i + 1;
            if (i <= 7) {
                remove((String) this.lruEntries.entrySet().iterator().next().getKey());
                i = i2;
            } else {
                return;
            }
        }
    }

    public void delete() throws IOException {
        close();
        Utils.deleteContents(this.directory);
    }

    public void clearCache() throws IOException {
        Utils.deleteContents(this.directory);
    }

    private void validateKey(String str) {
        if (!LEGAL_KEY_PATTERN.matcher(str).matches()) {
            StringBuilder sb = new StringBuilder("keys must match regex [a-z0-9_-]{1,64}: \"");
            sb.append(str);
            sb.append("\"");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public static String inputStreamToString(InputStream inputStream) throws IOException {
        return Utils.readFully(new InputStreamReader(inputStream, Utils.UTF_8));
    }
}
