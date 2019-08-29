package com.alipay.mobile.common.transportext.biz.spdy.internal;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
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
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public final class DiskLruCache implements Closeable {
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
        public final void write(int b) {
        }
    };
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final int appVersion;
    private final Callable<Void> cleanupCallable = new Callable<Void>() {
        public Void call() {
            synchronized (DiskLruCache.this) {
                if (DiskLruCache.this.journalWriter != null) {
                    DiskLruCache.this.trimToSize();
                    if (DiskLruCache.this.journalRebuildRequired()) {
                        DiskLruCache.this.rebuildJournal();
                        DiskLruCache.this.redundantOpCount = 0;
                    }
                }
            }
            return null;
        }
    };
    /* access modifiers changed from: private */
    public final File directory;
    final ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    /* access modifiers changed from: private */
    public Writer journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap<>(0, 0.75f, true);
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
            private FaultHidingOutputStream(OutputStream out) {
                super(out);
            }

            public void write(int oneByte) {
                try {
                    this.out.write(oneByte);
                } catch (IOException e) {
                    Editor.this.hasErrors = true;
                }
            }

            public void write(byte[] buffer, int offset, int length) {
                try {
                    this.out.write(buffer, offset, length);
                } catch (IOException e) {
                    Editor.this.hasErrors = true;
                }
            }

            public void close() {
                try {
                    this.out.close();
                } catch (IOException e) {
                    Editor.this.hasErrors = true;
                }
            }

            public void flush() {
                try {
                    this.out.flush();
                } catch (IOException e) {
                    Editor.this.hasErrors = true;
                }
            }
        }

        private Editor(Entry entry2) {
            this.entry = entry2;
            this.written = entry2.readable ? null : new boolean[DiskLruCache.this.valueCount];
        }

        public final InputStream newInputStream(int index) {
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                } else if (!this.entry.readable) {
                    return null;
                } else {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(this.entry.getCleanFile(index));
                        return fileInputStream;
                    } catch (FileNotFoundException e) {
                        return null;
                    }
                }
            }
        }

        public final String getString(int index) {
            InputStream in = newInputStream(index);
            if (in != null) {
                return DiskLruCache.inputStreamToString(in);
            }
            return null;
        }

        public final OutputStream newOutputStream(int index) {
            OutputStream access$2000;
            FileOutputStream outputStream;
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                }
                if (!this.entry.readable) {
                    this.written[index] = true;
                }
                File dirtyFile = this.entry.getDirtyFile(index);
                try {
                    outputStream = new FileOutputStream(dirtyFile);
                } catch (FileNotFoundException e) {
                    DiskLruCache.this.directory.mkdirs();
                    try {
                        outputStream = new FileOutputStream(dirtyFile);
                    } catch (FileNotFoundException e2) {
                        access$2000 = DiskLruCache.NULL_OUTPUT_STREAM;
                    }
                }
                access$2000 = new FaultHidingOutputStream(outputStream);
            }
            return access$2000;
        }

        public final void set(int index, String value) {
            Writer writer = null;
            try {
                Writer writer2 = new OutputStreamWriter(newOutputStream(index), Util.UTF_8);
                try {
                    writer2.write(value);
                    Util.closeQuietly((Closeable) writer2);
                } catch (Throwable th) {
                    th = th;
                    writer = writer2;
                    Util.closeQuietly((Closeable) writer);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                Util.closeQuietly((Closeable) writer);
                throw th;
            }
        }

        public final void commit() {
            if (this.hasErrors) {
                DiskLruCache.this.completeEdit(this, false);
                DiskLruCache.this.remove(this.entry.key);
            } else {
                DiskLruCache.this.completeEdit(this, true);
            }
            this.committed = true;
        }

        public final void abort() {
            DiskLruCache.this.completeEdit(this, false);
        }

        public final void abortUnlessCommitted() {
            if (!this.committed) {
                try {
                    abort();
                } catch (IOException ignored) {
                    LogCatUtil.warn((String) "DiskLruCache", " abortUnlessCommitted exception:" + ignored.toString());
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

        private Entry(String key2) {
            this.key = key2;
            this.lengths = new long[DiskLruCache.this.valueCount];
        }

        public final String getLengths() {
            StringBuilder result = new StringBuilder();
            for (long size : this.lengths) {
                result.append(' ').append(size);
            }
            return result.toString();
        }

        /* access modifiers changed from: private */
        public void setLengths(String[] strings) {
            if (strings.length != DiskLruCache.this.valueCount) {
                throw invalidLengths(strings);
            }
            int i = 0;
            while (i < strings.length) {
                try {
                    this.lengths[i] = Long.parseLong(strings[i]);
                    i++;
                } catch (NumberFormatException e) {
                    throw invalidLengths(strings);
                }
            }
        }

        private IOException invalidLengths(String[] strings) {
            throw new IOException("unexpected journal line: " + Arrays.toString(strings));
        }

        public final File getCleanFile(int i) {
            return new File(DiskLruCache.this.directory, this.key + "." + i);
        }

        public final File getDirtyFile(int i) {
            return new File(DiskLruCache.this.directory, this.key + "." + i + FilePathHelper.SUFFIX_DOT_TMP);
        }
    }

    public final class Snapshot implements Closeable {
        private final InputStream[] ins;
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;

        private Snapshot(String key2, long sequenceNumber2, InputStream[] ins2, long[] lengths2) {
            this.key = key2;
            this.sequenceNumber = sequenceNumber2;
            this.ins = ins2;
            this.lengths = lengths2;
        }

        public final Editor edit() {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public final InputStream getInputStream(int index) {
            return this.ins[index];
        }

        public final String getString(int index) {
            return DiskLruCache.inputStreamToString(getInputStream(index));
        }

        public final long getLength(int index) {
            return this.lengths[index];
        }

        public final void close() {
            for (InputStream closeQuietly : this.ins) {
                Util.closeQuietly((Closeable) closeQuietly);
            }
        }
    }

    private DiskLruCache(File directory2, int appVersion2, int valueCount2, long maxSize2) {
        this.directory = directory2;
        this.appVersion = appVersion2;
        this.journalFile = new File(directory2, JOURNAL_FILE);
        this.journalFileTmp = new File(directory2, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(directory2, JOURNAL_FILE_BACKUP);
        this.valueCount = valueCount2;
        this.maxSize = maxSize2;
    }

    public static DiskLruCache open(File directory2, int appVersion2, int valueCount2, long maxSize2) {
        if (maxSize2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (valueCount2 <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        } else {
            File backupFile = new File(directory2, JOURNAL_FILE_BACKUP);
            if (backupFile.exists()) {
                File journalFile2 = new File(directory2, JOURNAL_FILE);
                if (journalFile2.exists()) {
                    backupFile.delete();
                } else {
                    renameTo(backupFile, journalFile2, false);
                }
            }
            DiskLruCache cache = new DiskLruCache(directory2, appVersion2, valueCount2, maxSize2);
            if (cache.journalFile.exists()) {
                try {
                    cache.readJournal();
                    cache.processJournal();
                    cache.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cache.journalFile, true), Util.US_ASCII));
                    return cache;
                } catch (IOException journalIsCorrupt) {
                    Platform.get().logW("DiskLruCache " + directory2 + " is corrupt: " + journalIsCorrupt.getMessage() + ", removing");
                    cache.delete();
                }
            }
            directory2.mkdirs();
            DiskLruCache cache2 = new DiskLruCache(directory2, appVersion2, valueCount2, maxSize2);
            cache2.rebuildJournal();
            return cache2;
        }
    }

    private void readJournal() {
        int lineCount;
        StrictLineReader reader = new StrictLineReader(new FileInputStream(this.journalFile), Util.US_ASCII);
        try {
            String magic = reader.readLine();
            String version = reader.readLine();
            String appVersionString = reader.readLine();
            String valueCountString = reader.readLine();
            String blank = reader.readLine();
            if (!TextUtils.equals(MAGIC, magic) || !TextUtils.equals("1", version) || !TextUtils.equals(Integer.toString(this.appVersion), appVersionString) || !TextUtils.equals(Integer.toString(this.valueCount), valueCountString) || !TextUtils.equals("", blank)) {
                throw new IOException("unexpected journal header: [" + magic + ", " + version + ", " + valueCountString + ", " + blank + "]");
            }
            lineCount = 0;
            while (true) {
                readJournalLine(reader.readLine());
                lineCount++;
            }
        } catch (EOFException e) {
            this.redundantOpCount = lineCount - this.lruEntries.size();
            Util.closeQuietly((Closeable) reader);
        } catch (Throwable th) {
            Util.closeQuietly((Closeable) reader);
            throw th;
        }
    }

    private void readJournalLine(String line) {
        String key;
        int firstSpace = line.indexOf(32);
        if (firstSpace == -1) {
            throw new IOException("unexpected journal line: " + line);
        }
        int keyBegin = firstSpace + 1;
        int secondSpace = line.indexOf(32, keyBegin);
        if (secondSpace == -1) {
            key = line.substring(keyBegin);
            if (firstSpace == 6 && line.startsWith(REMOVE)) {
                this.lruEntries.remove(key);
                return;
            }
        } else {
            key = line.substring(keyBegin, secondSpace);
        }
        Entry entry = this.lruEntries.get(key);
        if (entry == null) {
            entry = new Entry(key);
            this.lruEntries.put(key, entry);
        }
        if (secondSpace != -1 && firstSpace == 5 && line.startsWith(CLEAN)) {
            String[] parts = line.substring(secondSpace + 1).split(Token.SEPARATOR);
            entry.readable = true;
            entry.currentEditor = null;
            entry.setLengths(parts);
        } else if (secondSpace == -1 && firstSpace == 5 && line.startsWith(DIRTY)) {
            entry.currentEditor = new Editor(entry);
        } else if (secondSpace != -1 || firstSpace != 4 || !line.startsWith(READ)) {
            throw new IOException("unexpected journal line: " + line);
        }
    }

    private void processJournal() {
        deleteIfExists(this.journalFileTmp);
        Iterator i = this.lruEntries.values().iterator();
        while (i.hasNext()) {
            Entry entry = i.next();
            if (entry.currentEditor == null) {
                for (int t = 0; t < this.valueCount; t++) {
                    this.size += entry.lengths[t];
                }
            } else {
                entry.currentEditor = null;
                for (int t2 = 0; t2 < this.valueCount; t2++) {
                    deleteIfExists(entry.getCleanFile(t2));
                    deleteIfExists(entry.getDirtyFile(t2));
                }
                i.remove();
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void rebuildJournal() {
        if (this.journalWriter != null) {
            this.journalWriter.close();
        }
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFileTmp), Util.US_ASCII));
        try {
            writer.write(MAGIC);
            writer.write("\n");
            writer.write("1");
            writer.write("\n");
            writer.write(Integer.toString(this.appVersion));
            writer.write("\n");
            writer.write(Integer.toString(this.valueCount));
            writer.write("\n");
            writer.write("\n");
            for (Entry entry : this.lruEntries.values()) {
                if (entry.currentEditor != null) {
                    writer.write("DIRTY " + entry.key + 10);
                } else {
                    writer.write("CLEAN " + entry.key + entry.getLengths() + 10);
                }
            }
            writer.close();
            if (this.journalFile.exists()) {
                renameTo(this.journalFile, this.journalFileBackup, true);
            }
            renameTo(this.journalFileTmp, this.journalFile, false);
            this.journalFileBackup.delete();
            this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), Util.US_ASCII));
        } catch (Throwable th) {
            writer.close();
            throw th;
        }
    }

    private static void deleteIfExists(File file) {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void renameTo(File from, File to, boolean deleteDestination) {
        if (deleteDestination) {
            deleteIfExists(to);
        }
        if (!from.renameTo(to)) {
            throw new IOException();
        }
    }

    public final synchronized Snapshot get(String key) {
        Snapshot snapshot = null;
        synchronized (this) {
            checkNotClosed();
            validateKey(key);
            Entry entry = this.lruEntries.get(key);
            if (entry != null) {
                if (entry.readable) {
                    InputStream[] ins = new InputStream[this.valueCount];
                    int i = 0;
                    while (i < this.valueCount) {
                        try {
                            ins[i] = new FileInputStream(entry.getCleanFile(i));
                            i++;
                        } catch (FileNotFoundException e) {
                            int i2 = 0;
                            while (i2 < this.valueCount && ins[i2] != null) {
                                Util.closeQuietly((Closeable) ins[i2]);
                                i2++;
                            }
                        }
                    }
                    this.redundantOpCount++;
                    this.journalWriter.append("READ " + key + 10);
                    if (journalRebuildRequired()) {
                        this.executorService.submit(this.cleanupCallable);
                    }
                    snapshot = new Snapshot(key, entry.sequenceNumber, ins, entry.lengths);
                }
            }
        }
        return snapshot;
    }

    public final Editor edit(String key) {
        return edit(key, -1);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005f, code lost:
        if (com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache.Entry.access$700(r1) != null) goto L_0x0020;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache.Editor edit(java.lang.String r6, long r7) {
        /*
            r5 = this;
            r0 = 0
            monitor-enter(r5)
            r5.checkNotClosed()     // Catch:{ all -> 0x0058 }
            r5.validateKey(r6)     // Catch:{ all -> 0x0058 }
            java.util.LinkedHashMap<java.lang.String, com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache$Entry> r2 = r5.lruEntries     // Catch:{ all -> 0x0058 }
            java.lang.Object r1 = r2.get(r6)     // Catch:{ all -> 0x0058 }
            com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache$Entry r1 = (com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache.Entry) r1     // Catch:{ all -> 0x0058 }
            r2 = -1
            int r2 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x0022
            if (r1 == 0) goto L_0x0020
            long r2 = r1.sequenceNumber     // Catch:{ all -> 0x0058 }
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 == 0) goto L_0x0022
        L_0x0020:
            monitor-exit(r5)
            return r0
        L_0x0022:
            if (r1 != 0) goto L_0x005b
            com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache$Entry r1 = new com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache$Entry     // Catch:{ all -> 0x0058 }
            r2 = 0
            r1.<init>(r6)     // Catch:{ all -> 0x0058 }
            java.util.LinkedHashMap<java.lang.String, com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache$Entry> r2 = r5.lruEntries     // Catch:{ all -> 0x0058 }
            r2.put(r6, r1)     // Catch:{ all -> 0x0058 }
        L_0x002f:
            com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache$Editor r0 = new com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache$Editor     // Catch:{ all -> 0x0058 }
            r2 = 0
            r0.<init>(r1)     // Catch:{ all -> 0x0058 }
            r1.currentEditor = r0     // Catch:{ all -> 0x0058 }
            java.io.Writer r2 = r5.journalWriter     // Catch:{ all -> 0x0058 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
            java.lang.String r4 = "DIRTY "
            r3.<init>(r4)     // Catch:{ all -> 0x0058 }
            java.lang.StringBuilder r3 = r3.append(r6)     // Catch:{ all -> 0x0058 }
            r4 = 10
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0058 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0058 }
            r2.write(r3)     // Catch:{ all -> 0x0058 }
            java.io.Writer r2 = r5.journalWriter     // Catch:{ all -> 0x0058 }
            r2.flush()     // Catch:{ all -> 0x0058 }
            goto L_0x0020
        L_0x0058:
            r2 = move-exception
            monitor-exit(r5)
            throw r2
        L_0x005b:
            com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache$Editor r2 = r1.currentEditor     // Catch:{ all -> 0x0058 }
            if (r2 == 0) goto L_0x002f
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache.edit(java.lang.String, long):com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache$Editor");
    }

    public final File getDirectory() {
        return this.directory;
    }

    public final long getMaxSize() {
        return this.maxSize;
    }

    public final synchronized void setMaxSize(long maxSize2) {
        this.maxSize = maxSize2;
        this.executorService.submit(this.cleanupCallable);
    }

    public final synchronized long size() {
        try {
        }
        return this.size;
    }

    /* access modifiers changed from: private */
    public synchronized void completeEdit(Editor editor, boolean success) {
        Entry entry = editor.entry;
        if (entry.currentEditor != editor) {
            throw new IllegalStateException();
        }
        if (success) {
            if (!entry.readable) {
                int i = 0;
                while (true) {
                    if (i >= this.valueCount) {
                        break;
                    } else if (!editor.written[i]) {
                        editor.abort();
                        throw new IllegalStateException("Newly created entry didn't create value for index " + i);
                    } else if (!entry.getDirtyFile(i).exists()) {
                        editor.abort();
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
        for (int i2 = 0; i2 < this.valueCount; i2++) {
            File dirty = entry.getDirtyFile(i2);
            if (!success) {
                deleteIfExists(dirty);
            } else if (dirty.exists()) {
                File clean = entry.getCleanFile(i2);
                dirty.renameTo(clean);
                long oldLength = entry.lengths[i2];
                long newLength = clean.length();
                entry.lengths[i2] = newLength;
                this.size = (this.size - oldLength) + newLength;
            }
        }
        this.redundantOpCount++;
        entry.currentEditor = null;
        if (entry.readable || success) {
            entry.readable = true;
            this.journalWriter.write("CLEAN " + entry.key + entry.getLengths() + 10);
            if (success) {
                long j = this.nextSequenceNumber;
                this.nextSequenceNumber = 1 + j;
                entry.sequenceNumber = j;
            }
        } else {
            this.lruEntries.remove(entry.key);
            this.journalWriter.write("REMOVE " + entry.key + 10);
        }
        this.journalWriter.flush();
        if (this.size > this.maxSize || journalRebuildRequired()) {
            this.executorService.submit(this.cleanupCallable);
        }
    }

    /* access modifiers changed from: private */
    public boolean journalRebuildRequired() {
        return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
    }

    public final synchronized boolean remove(String key) {
        boolean z;
        checkNotClosed();
        validateKey(key);
        Entry entry = this.lruEntries.get(key);
        if (entry == null || entry.currentEditor != null) {
            z = false;
        } else {
            for (int i = 0; i < this.valueCount; i++) {
                File file = entry.getCleanFile(i);
                if (!file.delete()) {
                    throw new IOException("failed to delete " + file);
                }
                this.size -= entry.lengths[i];
                entry.lengths[i] = 0;
            }
            this.redundantOpCount++;
            this.journalWriter.append("REMOVE " + key + 10);
            this.lruEntries.remove(key);
            if (journalRebuildRequired()) {
                this.executorService.submit(this.cleanupCallable);
            }
            z = true;
        }
        return z;
    }

    public final boolean isClosed() {
        return this.journalWriter == null;
    }

    private void checkNotClosed() {
        if (this.journalWriter == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public final synchronized void flush() {
        checkNotClosed();
        trimToSize();
        this.journalWriter.flush();
    }

    public final synchronized void close() {
        if (this.journalWriter != null) {
            Iterator it = new ArrayList(this.lruEntries.values()).iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                if (entry.currentEditor != null) {
                    entry.currentEditor.abort();
                }
            }
            trimToSize();
            this.journalWriter.close();
            this.journalWriter = null;
        }
    }

    /* access modifiers changed from: private */
    public void trimToSize() {
        while (this.size > this.maxSize) {
            remove((String) this.lruEntries.entrySet().iterator().next().getKey());
        }
    }

    public final void delete() {
        close();
        Util.deleteContents(this.directory);
    }

    private void validateKey(String key) {
        if (!LEGAL_KEY_PATTERN.matcher(key).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,64}: \"" + key + "\"");
        }
    }

    /* access modifiers changed from: private */
    public static String inputStreamToString(InputStream in) {
        return Util.readFully(new InputStreamReader(in, Util.UTF_8));
    }
}
