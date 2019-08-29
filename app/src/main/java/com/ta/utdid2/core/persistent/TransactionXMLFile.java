package com.ta.utdid2.core.persistent;

import com.ta.utdid2.core.persistent.MySharedPreferences.MyEditor;
import com.ta.utdid2.core.persistent.MySharedPreferences.OnSharedPreferenceChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

public class TransactionXMLFile {
    /* access modifiers changed from: private */
    public static final Object GLOBAL_COMMIT_LOCK = new Object();
    public static final int MODE_PRIVATE = 0;
    private File mPreferencesDir;
    private final Object mSync = new Object();
    private HashMap<File, MySharedPreferencesImpl> sSharedPrefs = new HashMap<>();

    static final class MySharedPreferencesImpl implements MySharedPreferences {
        private static final Object mContent = new Object();
        private boolean hasChange = false;
        private final File mBackupFile;
        private final File mFile;
        /* access modifiers changed from: private */
        public WeakHashMap<OnSharedPreferenceChangeListener, Object> mListeners;
        /* access modifiers changed from: private */
        public Map mMap;
        private final int mMode;

        public final class EditorImpl implements MyEditor {
            private boolean mClear = false;
            private final Map<String, Object> mModified = new HashMap();

            public EditorImpl() {
            }

            public final MyEditor putString(String str, String str2) {
                synchronized (this) {
                    this.mModified.put(str, str2);
                }
                return this;
            }

            public final MyEditor putInt(String str, int i) {
                synchronized (this) {
                    this.mModified.put(str, Integer.valueOf(i));
                }
                return this;
            }

            public final MyEditor putLong(String str, long j) {
                synchronized (this) {
                    this.mModified.put(str, Long.valueOf(j));
                }
                return this;
            }

            public final MyEditor putFloat(String str, float f) {
                synchronized (this) {
                    this.mModified.put(str, Float.valueOf(f));
                }
                return this;
            }

            public final MyEditor putBoolean(String str, boolean z) {
                synchronized (this) {
                    this.mModified.put(str, Boolean.valueOf(z));
                }
                return this;
            }

            public final MyEditor remove(String str) {
                synchronized (this) {
                    this.mModified.put(str, this);
                }
                return this;
            }

            public final MyEditor clear() {
                synchronized (this) {
                    this.mClear = true;
                }
                return this;
            }

            public final boolean commit() {
                boolean z;
                ArrayList arrayList;
                Set<OnSharedPreferenceChangeListener> set;
                boolean access$400;
                synchronized (TransactionXMLFile.GLOBAL_COMMIT_LOCK) {
                    z = MySharedPreferencesImpl.this.mListeners.size() > 0;
                    arrayList = null;
                    if (z) {
                        arrayList = new ArrayList();
                        set = new HashSet<>(MySharedPreferencesImpl.this.mListeners.keySet());
                    } else {
                        set = null;
                    }
                    synchronized (this) {
                        if (this.mClear) {
                            MySharedPreferencesImpl.this.mMap.clear();
                            this.mClear = false;
                        }
                        for (Entry next : this.mModified.entrySet()) {
                            String str = (String) next.getKey();
                            Object value = next.getValue();
                            if (value == this) {
                                MySharedPreferencesImpl.this.mMap.remove(str);
                            } else {
                                MySharedPreferencesImpl.this.mMap.put(str, value);
                            }
                            if (z) {
                                arrayList.add(str);
                            }
                        }
                        this.mModified.clear();
                    }
                    access$400 = MySharedPreferencesImpl.this.writeFileLocked();
                    if (access$400) {
                        MySharedPreferencesImpl.this.setHasChange(true);
                    }
                }
                if (z) {
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        String str2 = (String) arrayList.get(size);
                        for (OnSharedPreferenceChangeListener onSharedPreferenceChangeListener : set) {
                            if (onSharedPreferenceChangeListener != null) {
                                onSharedPreferenceChangeListener.onSharedPreferenceChanged(MySharedPreferencesImpl.this, str2);
                            }
                        }
                    }
                }
                return access$400;
            }
        }

        MySharedPreferencesImpl(File file, int i, Map map) {
            this.mFile = file;
            this.mBackupFile = TransactionXMLFile.makeBackupFile(file);
            this.mMode = i;
            this.mMap = map == null ? new HashMap() : map;
            this.mListeners = new WeakHashMap<>();
        }

        public final boolean checkFile() {
            return this.mFile != null && new File(this.mFile.getAbsolutePath()).exists();
        }

        public final void setHasChange(boolean z) {
            synchronized (this) {
                this.hasChange = z;
            }
        }

        public final boolean hasFileChanged() {
            boolean z;
            synchronized (this) {
                z = this.hasChange;
            }
            return z;
        }

        public final void replace(Map map) {
            if (map != null) {
                synchronized (this) {
                    this.mMap = map;
                }
            }
        }

        public final void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
            synchronized (this) {
                this.mListeners.put(onSharedPreferenceChangeListener, mContent);
            }
        }

        public final void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
            synchronized (this) {
                this.mListeners.remove(onSharedPreferenceChangeListener);
            }
        }

        public final Map<String, ?> getAll() {
            HashMap hashMap;
            synchronized (this) {
                hashMap = new HashMap(this.mMap);
            }
            return hashMap;
        }

        public final String getString(String str, String str2) {
            String str3;
            synchronized (this) {
                str3 = (String) this.mMap.get(str);
                if (str3 == null) {
                    str3 = str2;
                }
            }
            return str3;
        }

        public final int getInt(String str, int i) {
            synchronized (this) {
                Integer num = (Integer) this.mMap.get(str);
                if (num != null) {
                    i = num.intValue();
                }
            }
            return i;
        }

        public final long getLong(String str, long j) {
            synchronized (this) {
                Long l = (Long) this.mMap.get(str);
                if (l != null) {
                    j = l.longValue();
                }
            }
            return j;
        }

        public final float getFloat(String str, float f) {
            synchronized (this) {
                Float f2 = (Float) this.mMap.get(str);
                if (f2 != null) {
                    f = f2.floatValue();
                }
            }
            return f;
        }

        public final boolean getBoolean(String str, boolean z) {
            synchronized (this) {
                Boolean bool = (Boolean) this.mMap.get(str);
                if (bool != null) {
                    z = bool.booleanValue();
                }
            }
            return z;
        }

        public final boolean contains(String str) {
            boolean containsKey;
            synchronized (this) {
                containsKey = this.mMap.containsKey(str);
            }
            return containsKey;
        }

        public final MyEditor edit() {
            return new EditorImpl();
        }

        private FileOutputStream createFileOutputStream(File file) {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (FileNotFoundException unused) {
                if (!file.getParentFile().mkdir()) {
                    return null;
                }
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException unused2) {
                    fileOutputStream = null;
                }
            }
            return fileOutputStream;
        }

        /* access modifiers changed from: private */
        public boolean writeFileLocked() {
            if (this.mFile.exists()) {
                if (this.mBackupFile.exists()) {
                    this.mFile.delete();
                } else if (!this.mFile.renameTo(this.mBackupFile)) {
                    return false;
                }
            }
            try {
                FileOutputStream createFileOutputStream = createFileOutputStream(this.mFile);
                if (createFileOutputStream == null) {
                    return false;
                }
                XmlUtils.writeMapXml(this.mMap, createFileOutputStream);
                createFileOutputStream.close();
                this.mBackupFile.delete();
                return true;
            } catch (Exception unused) {
                if (this.mFile.exists()) {
                    this.mFile.delete();
                }
                return false;
            }
        }
    }

    public TransactionXMLFile(String str) {
        if (str == null || str.length() <= 0) {
            throw new RuntimeException("Directory can not be empty");
        }
        this.mPreferencesDir = new File(str);
    }

    private File makeFilename(File file, String str) {
        if (str.indexOf(File.separatorChar) < 0) {
            return new File(file, str);
        }
        StringBuilder sb = new StringBuilder("File ");
        sb.append(str);
        sb.append(" contains a path separator");
        throw new IllegalArgumentException(sb.toString());
    }

    private File getPreferencesDir() {
        File file;
        synchronized (this.mSync) {
            file = this.mPreferencesDir;
        }
        return file;
    }

    private File getSharedPrefsFile(String str) {
        File preferencesDir = getPreferencesDir();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".xml");
        return makeFilename(preferencesDir, sb.toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        r0 = makeBackupFile(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
        if (r0.exists() == false) goto L_0x002a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        r5.delete();
        r0.renameTo(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002a, code lost:
        r2 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002f, code lost:
        if (r5.exists() == false) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0035, code lost:
        if (r5.canRead() == false) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r0 = new java.io.FileInputStream(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r3 = com.ta.utdid2.core.persistent.XmlUtils.readMapXml(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0048, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x004a, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x004c, code lost:
        r5 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x004d, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x004f, code lost:
        r5 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0051, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0052, code lost:
        if (r0 == null) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0058, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r3 = new java.io.FileInputStream(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r3.read(new byte[r3.available()]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x006b, code lost:
        r5 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x006c, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x006e, code lost:
        r0 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0070, code lost:
        r5 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0071, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0073, code lost:
        r0 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0075, code lost:
        r5 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0076, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0077, code lost:
        if (r2 != null) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        throw r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x007d, code lost:
        if (r0 != null) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0083, code lost:
        if (r2 != null) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0088, code lost:
        throw r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0089, code lost:
        if (r0 != null) goto L_0x0054;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x0059 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:61:0x007c */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x004c A[ExcHandler: all (th java.lang.Throwable), PHI: r0 
      PHI: (r0v17 java.io.FileInputStream) = (r0v8 java.io.FileInputStream), (r0v8 java.io.FileInputStream), (r0v18 java.io.FileInputStream), (r0v18 java.io.FileInputStream), (r0v18 java.io.FileInputStream) binds: [B:65:0x007f, B:66:?, B:19:0x003c, B:21:0x0040, B:22:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:19:0x003c] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0079 A[SYNTHETIC, Splitter:B:59:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x007f A[SYNTHETIC, Splitter:B:65:0x007f] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0085 A[SYNTHETIC, Splitter:B:68:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x008f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ta.utdid2.core.persistent.MySharedPreferences getMySharedPreferences(java.lang.String r5, int r6) {
        /*
            r4 = this;
            java.io.File r5 = r4.getSharedPrefsFile(r5)
            java.lang.Object r0 = GLOBAL_COMMIT_LOCK
            monitor-enter(r0)
            java.util.HashMap<java.io.File, com.ta.utdid2.core.persistent.TransactionXMLFile$MySharedPreferencesImpl> r1 = r4.sSharedPrefs     // Catch:{ all -> 0x00b0 }
            java.lang.Object r1 = r1.get(r5)     // Catch:{ all -> 0x00b0 }
            com.ta.utdid2.core.persistent.TransactionXMLFile$MySharedPreferencesImpl r1 = (com.ta.utdid2.core.persistent.TransactionXMLFile.MySharedPreferencesImpl) r1     // Catch:{ all -> 0x00b0 }
            if (r1 == 0) goto L_0x0019
            boolean r2 = r1.hasFileChanged()     // Catch:{ all -> 0x00b0 }
            if (r2 != 0) goto L_0x0019
            monitor-exit(r0)     // Catch:{ all -> 0x00b0 }
            return r1
        L_0x0019:
            monitor-exit(r0)     // Catch:{ all -> 0x00b0 }
            java.io.File r0 = makeBackupFile(r5)
            boolean r2 = r0.exists()
            if (r2 == 0) goto L_0x002a
            r5.delete()
            r0.renameTo(r5)
        L_0x002a:
            boolean r0 = r5.exists()
            r2 = 0
            if (r0 == 0) goto L_0x008c
            boolean r0 = r5.canRead()
            if (r0 == 0) goto L_0x008c
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ XmlPullParserException -> 0x0058, Exception -> 0x0051 }
            r0.<init>(r5)     // Catch:{ XmlPullParserException -> 0x0058, Exception -> 0x0051 }
            java.util.HashMap r3 = com.ta.utdid2.core.persistent.XmlUtils.readMapXml(r0)     // Catch:{ XmlPullParserException -> 0x0059, Exception -> 0x0052, all -> 0x004c }
            r0.close()     // Catch:{ XmlPullParserException -> 0x004a, Exception -> 0x0048, all -> 0x004c }
            r0.close()     // Catch:{ Throwable -> 0x0046 }
        L_0x0046:
            r2 = r3
            goto L_0x008c
        L_0x0048:
            r2 = r3
            goto L_0x0052
        L_0x004a:
            r2 = r3
            goto L_0x0059
        L_0x004c:
            r5 = move-exception
            r2 = r0
            goto L_0x0083
        L_0x004f:
            r5 = move-exception
            goto L_0x0083
        L_0x0051:
            r0 = r2
        L_0x0052:
            if (r0 == 0) goto L_0x008c
        L_0x0054:
            r0.close()     // Catch:{ Throwable -> 0x008c }
            goto L_0x008c
        L_0x0058:
            r0 = r2
        L_0x0059:
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x007d, all -> 0x0075 }
            r3.<init>(r5)     // Catch:{ Exception -> 0x007d, all -> 0x0075 }
            int r0 = r3.available()     // Catch:{ Exception -> 0x0073, all -> 0x0070 }
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0073, all -> 0x0070 }
            r3.read(r0)     // Catch:{ Exception -> 0x0073, all -> 0x0070 }
            r3.close()     // Catch:{ Throwable -> 0x006e, all -> 0x006b }
            goto L_0x006e
        L_0x006b:
            r5 = move-exception
            r2 = r3
            goto L_0x0083
        L_0x006e:
            r0 = r3
            goto L_0x0089
        L_0x0070:
            r5 = move-exception
            r2 = r3
            goto L_0x0077
        L_0x0073:
            r0 = r3
            goto L_0x007d
        L_0x0075:
            r5 = move-exception
            r2 = r0
        L_0x0077:
            if (r2 == 0) goto L_0x007c
            r2.close()     // Catch:{ Throwable -> 0x007c }
        L_0x007c:
            throw r5     // Catch:{ all -> 0x004f }
        L_0x007d:
            if (r0 == 0) goto L_0x0089
            r0.close()     // Catch:{ Throwable -> 0x0089, all -> 0x004c }
            goto L_0x0089
        L_0x0083:
            if (r2 == 0) goto L_0x0088
            r2.close()     // Catch:{ Throwable -> 0x0088 }
        L_0x0088:
            throw r5
        L_0x0089:
            if (r0 == 0) goto L_0x008c
            goto L_0x0054
        L_0x008c:
            java.lang.Object r3 = GLOBAL_COMMIT_LOCK
            monitor-enter(r3)
            if (r1 == 0) goto L_0x0097
            r1.replace(r2)     // Catch:{ all -> 0x0095 }
            goto L_0x00ac
        L_0x0095:
            r5 = move-exception
            goto L_0x00ae
        L_0x0097:
            java.util.HashMap<java.io.File, com.ta.utdid2.core.persistent.TransactionXMLFile$MySharedPreferencesImpl> r0 = r4.sSharedPrefs     // Catch:{ all -> 0x0095 }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x0095 }
            r1 = r0
            com.ta.utdid2.core.persistent.TransactionXMLFile$MySharedPreferencesImpl r1 = (com.ta.utdid2.core.persistent.TransactionXMLFile.MySharedPreferencesImpl) r1     // Catch:{ all -> 0x0095 }
            if (r1 != 0) goto L_0x00ac
            com.ta.utdid2.core.persistent.TransactionXMLFile$MySharedPreferencesImpl r1 = new com.ta.utdid2.core.persistent.TransactionXMLFile$MySharedPreferencesImpl     // Catch:{ all -> 0x0095 }
            r1.<init>(r5, r6, r2)     // Catch:{ all -> 0x0095 }
            java.util.HashMap<java.io.File, com.ta.utdid2.core.persistent.TransactionXMLFile$MySharedPreferencesImpl> r6 = r4.sSharedPrefs     // Catch:{ all -> 0x0095 }
            r6.put(r5, r1)     // Catch:{ all -> 0x0095 }
        L_0x00ac:
            monitor-exit(r3)     // Catch:{ all -> 0x0095 }
            return r1
        L_0x00ae:
            monitor-exit(r3)     // Catch:{ all -> 0x0095 }
            throw r5
        L_0x00b0:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00b0 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.core.persistent.TransactionXMLFile.getMySharedPreferences(java.lang.String, int):com.ta.utdid2.core.persistent.MySharedPreferences");
    }

    /* access modifiers changed from: private */
    public static File makeBackupFile(File file) {
        StringBuilder sb = new StringBuilder();
        sb.append(file.getPath());
        sb.append(".bak");
        return new File(sb.toString());
    }
}
