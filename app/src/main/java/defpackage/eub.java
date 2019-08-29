package defpackage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: eub reason: default package */
/* compiled from: LruDiskUsage */
public abstract class eub implements etx {
    private final ExecutorService a = Executors.newSingleThreadExecutor();

    /* renamed from: eub$a */
    /* compiled from: LruDiskUsage */
    class a implements Callable<Void> {
        private final File b;

        public a(File file) {
            this.b = file;
        }

        public final /* synthetic */ Object call() throws Exception {
            eub eub = eub.this;
            File file = this.b;
            if (file.exists() && !file.setLastModified(System.currentTimeMillis())) {
                long length = file.length();
                if (length != 0) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                    long j = length - 1;
                    randomAccessFile.seek(j);
                    byte readByte = randomAccessFile.readByte();
                    randomAccessFile.seek(j);
                    randomAccessFile.write(readByte);
                    randomAccessFile.close();
                } else if (!file.delete() || !file.createNewFile()) {
                    throw new IOException("Error recreate zero-size file ".concat(String.valueOf(file)));
                }
                file.lastModified();
            }
            File parentFile = file.getParentFile();
            List linkedList = new LinkedList();
            File[] listFiles = parentFile.listFiles();
            if (listFiles != null) {
                linkedList = Arrays.asList(listFiles);
                Collections.sort(linkedList, new a(0));
            }
            eub.a(linkedList);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public abstract boolean a(long j);

    public final void a(File file) throws IOException {
        this.a.submit(new a(file));
    }

    /* access modifiers changed from: 0000 */
    public final void a(List<File> list) {
        long j = 0;
        for (File length : list) {
            j += length.length();
        }
        list.size();
        for (File next : list) {
            if (!a(j)) {
                long length2 = next.length();
                if (next.delete()) {
                    j -= length2;
                }
            }
        }
    }
}
