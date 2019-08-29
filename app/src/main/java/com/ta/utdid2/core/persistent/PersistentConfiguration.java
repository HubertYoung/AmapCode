package com.ta.utdid2.core.persistent;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import com.ta.audid.utils.UtdidLogger;
import com.ta.utdid2.android.utils.StringUtils;
import com.ta.utdid2.core.persistent.MySharedPreferences.MyEditor;
import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

public class PersistentConfiguration {
    private static final String KEY_TIMESTAMP = "t";
    private static final String KEY_TIMESTAMP2 = "t2";
    private boolean mCanRead = false;
    private boolean mCanWrite = false;
    private String mConfigName = "";
    private Context mContext = null;
    private Editor mEditor = null;
    private String mFolderName = "";
    private boolean mIsLessMode = false;
    private boolean mIsSafety = false;
    private MyEditor mMyEditor = null;
    private MySharedPreferences mMySP = null;
    private SharedPreferences mSp = null;
    private TransactionXMLFile mTxf = null;

    /* JADX WARNING: Removed duplicated region for block: B:26:0x008d A[SYNTHETIC, Splitter:B:26:0x008d] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x017c  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x018f A[Catch:{ Exception -> 0x019f }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PersistentConfiguration(android.content.Context r9, java.lang.String r10, java.lang.String r11, boolean r12, boolean r13) {
        /*
            r8 = this;
            r8.<init>()
            java.lang.String r0 = ""
            r8.mConfigName = r0
            java.lang.String r0 = ""
            r8.mFolderName = r0
            r0 = 0
            r8.mIsSafety = r0
            r8.mCanRead = r0
            r8.mCanWrite = r0
            r1 = 0
            r8.mSp = r1
            r8.mMySP = r1
            r8.mEditor = r1
            r8.mMyEditor = r1
            r8.mContext = r1
            r8.mTxf = r1
            r8.mIsLessMode = r0
            r8.mIsSafety = r12
            r8.mIsLessMode = r13
            r8.mConfigName = r11
            r8.mFolderName = r10
            r8.mContext = r9
            r2 = 0
            if (r9 == 0) goto L_0x003f
            android.content.SharedPreferences r12 = r9.getSharedPreferences(r11, r0)
            r8.mSp = r12
            android.content.SharedPreferences r12 = r8.mSp
            java.lang.String r4 = "t"
            long r4 = r12.getLong(r4, r2)
            goto L_0x0040
        L_0x003f:
            r4 = r2
        L_0x0040:
            java.lang.String r12 = android.os.Environment.getExternalStorageState()     // Catch:{ Exception -> 0x0045 }
            goto L_0x004e
        L_0x0045:
            r12 = move-exception
            java.lang.String r6 = ""
            java.lang.Object[] r7 = new java.lang.Object[r0]
            com.ta.audid.utils.UtdidLogger.e(r6, r12, r7)
            r12 = r1
        L_0x004e:
            boolean r1 = com.ta.utdid2.android.utils.StringUtils.isEmpty(r12)
            if (r1 != 0) goto L_0x006f
            java.lang.String r1 = "mounted"
            boolean r1 = r12.equals(r1)
            r6 = 1
            if (r1 == 0) goto L_0x0062
            r8.mCanWrite = r6
            r8.mCanRead = r6
            goto L_0x0073
        L_0x0062:
            java.lang.String r1 = "mounted_ro"
            boolean r12 = r12.equals(r1)
            if (r12 == 0) goto L_0x006f
            r8.mCanRead = r6
            r8.mCanWrite = r0
            goto L_0x0073
        L_0x006f:
            r8.mCanWrite = r0
            r8.mCanRead = r0
        L_0x0073:
            boolean r12 = r8.mCanRead
            if (r12 != 0) goto L_0x007b
            boolean r12 = r8.mCanWrite
            if (r12 == 0) goto L_0x0157
        L_0x007b:
            if (r9 == 0) goto L_0x0157
            boolean r12 = com.ta.utdid2.android.utils.StringUtils.isEmpty(r10)
            if (r12 != 0) goto L_0x0157
            com.ta.utdid2.core.persistent.TransactionXMLFile r10 = r8.getTransactionXMLFile(r10)
            r8.mTxf = r10
            com.ta.utdid2.core.persistent.TransactionXMLFile r10 = r8.mTxf
            if (r10 == 0) goto L_0x0157
            com.ta.utdid2.core.persistent.TransactionXMLFile r10 = r8.mTxf     // Catch:{ Exception -> 0x0157 }
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r10.getMySharedPreferences(r11, r0)     // Catch:{ Exception -> 0x0157 }
            r8.mMySP = r10     // Catch:{ Exception -> 0x0157 }
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r8.mMySP     // Catch:{ Exception -> 0x0157 }
            java.lang.String r12 = "t"
            long r6 = r10.getLong(r12, r2)     // Catch:{ Exception -> 0x0157 }
            if (r13 != 0) goto L_0x00d9
            int r10 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r10 <= 0) goto L_0x00b5
            android.content.SharedPreferences r9 = r8.mSp     // Catch:{ Exception -> 0x0158 }
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r8.mMySP     // Catch:{ Exception -> 0x0158 }
            r8.copySPToMySP(r9, r10)     // Catch:{ Exception -> 0x0158 }
            com.ta.utdid2.core.persistent.TransactionXMLFile r9 = r8.mTxf     // Catch:{ Exception -> 0x0158 }
            com.ta.utdid2.core.persistent.MySharedPreferences r9 = r9.getMySharedPreferences(r11, r0)     // Catch:{ Exception -> 0x0158 }
            r8.mMySP = r9     // Catch:{ Exception -> 0x0158 }
            goto L_0x0158
        L_0x00b5:
            if (r10 >= 0) goto L_0x00c6
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r8.mMySP     // Catch:{ Exception -> 0x0158 }
            android.content.SharedPreferences r12 = r8.mSp     // Catch:{ Exception -> 0x0158 }
            r8.copyMySPToSP(r10, r12)     // Catch:{ Exception -> 0x0158 }
            android.content.SharedPreferences r9 = r9.getSharedPreferences(r11, r0)     // Catch:{ Exception -> 0x0158 }
            r8.mSp = r9     // Catch:{ Exception -> 0x0158 }
            goto L_0x0158
        L_0x00c6:
            if (r10 != 0) goto L_0x0158
            android.content.SharedPreferences r9 = r8.mSp     // Catch:{ Exception -> 0x0158 }
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r8.mMySP     // Catch:{ Exception -> 0x0158 }
            r8.copySPToMySP(r9, r10)     // Catch:{ Exception -> 0x0158 }
            com.ta.utdid2.core.persistent.TransactionXMLFile r9 = r8.mTxf     // Catch:{ Exception -> 0x0158 }
            com.ta.utdid2.core.persistent.MySharedPreferences r9 = r9.getMySharedPreferences(r11, r0)     // Catch:{ Exception -> 0x0158 }
            r8.mMySP = r9     // Catch:{ Exception -> 0x0158 }
            goto L_0x0158
        L_0x00d9:
            android.content.SharedPreferences r10 = r8.mSp     // Catch:{ Exception -> 0x0158 }
            java.lang.String r12 = "t2"
            long r12 = r10.getLong(r12, r2)     // Catch:{ Exception -> 0x0158 }
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r8.mMySP     // Catch:{ Exception -> 0x0143 }
            java.lang.String r1 = "t2"
            long r4 = r10.getLong(r1, r2)     // Catch:{ Exception -> 0x0143 }
            int r10 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r10 >= 0) goto L_0x0103
            int r1 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x0103
            android.content.SharedPreferences r9 = r8.mSp     // Catch:{ Exception -> 0x0142 }
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r8.mMySP     // Catch:{ Exception -> 0x0142 }
            r8.copySPToMySP(r9, r10)     // Catch:{ Exception -> 0x0142 }
            com.ta.utdid2.core.persistent.TransactionXMLFile r9 = r8.mTxf     // Catch:{ Exception -> 0x0142 }
            com.ta.utdid2.core.persistent.MySharedPreferences r9 = r9.getMySharedPreferences(r11, r0)     // Catch:{ Exception -> 0x0142 }
            r8.mMySP = r9     // Catch:{ Exception -> 0x0142 }
            goto L_0x0142
        L_0x0103:
            if (r10 <= 0) goto L_0x0117
            int r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x0117
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r8.mMySP     // Catch:{ Exception -> 0x0142 }
            android.content.SharedPreferences r1 = r8.mSp     // Catch:{ Exception -> 0x0142 }
            r8.copyMySPToSP(r10, r1)     // Catch:{ Exception -> 0x0142 }
            android.content.SharedPreferences r9 = r9.getSharedPreferences(r11, r0)     // Catch:{ Exception -> 0x0142 }
            r8.mSp = r9     // Catch:{ Exception -> 0x0142 }
            goto L_0x0142
        L_0x0117:
            int r1 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r1 != 0) goto L_0x012d
            int r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r6 <= 0) goto L_0x012d
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r8.mMySP     // Catch:{ Exception -> 0x0142 }
            android.content.SharedPreferences r1 = r8.mSp     // Catch:{ Exception -> 0x0142 }
            r8.copyMySPToSP(r10, r1)     // Catch:{ Exception -> 0x0142 }
            android.content.SharedPreferences r9 = r9.getSharedPreferences(r11, r0)     // Catch:{ Exception -> 0x0142 }
            r8.mSp = r9     // Catch:{ Exception -> 0x0142 }
            goto L_0x0142
        L_0x012d:
            int r9 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r9 != 0) goto L_0x0145
            if (r1 <= 0) goto L_0x0145
            android.content.SharedPreferences r9 = r8.mSp     // Catch:{ Exception -> 0x0142 }
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r8.mMySP     // Catch:{ Exception -> 0x0142 }
            r8.copySPToMySP(r9, r10)     // Catch:{ Exception -> 0x0142 }
            com.ta.utdid2.core.persistent.TransactionXMLFile r9 = r8.mTxf     // Catch:{ Exception -> 0x0142 }
            com.ta.utdid2.core.persistent.MySharedPreferences r9 = r9.getMySharedPreferences(r11, r0)     // Catch:{ Exception -> 0x0142 }
            r8.mMySP = r9     // Catch:{ Exception -> 0x0142 }
        L_0x0142:
            r6 = r4
        L_0x0143:
            r4 = r12
            goto L_0x0158
        L_0x0145:
            if (r10 != 0) goto L_0x0142
            android.content.SharedPreferences r9 = r8.mSp     // Catch:{ Exception -> 0x0142 }
            com.ta.utdid2.core.persistent.MySharedPreferences r10 = r8.mMySP     // Catch:{ Exception -> 0x0142 }
            r8.copySPToMySP(r9, r10)     // Catch:{ Exception -> 0x0142 }
            com.ta.utdid2.core.persistent.TransactionXMLFile r9 = r8.mTxf     // Catch:{ Exception -> 0x0142 }
            com.ta.utdid2.core.persistent.MySharedPreferences r9 = r9.getMySharedPreferences(r11, r0)     // Catch:{ Exception -> 0x0142 }
            r8.mMySP = r9     // Catch:{ Exception -> 0x0142 }
            goto L_0x0142
        L_0x0157:
            r6 = r2
        L_0x0158:
            int r9 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r9 != 0) goto L_0x0164
            int r9 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r9 != 0) goto L_0x019f
            int r9 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r9 != 0) goto L_0x019f
        L_0x0164:
            long r9 = java.lang.System.currentTimeMillis()
            boolean r11 = r8.mIsLessMode
            if (r11 == 0) goto L_0x0178
            boolean r11 = r8.mIsLessMode
            if (r11 == 0) goto L_0x019f
            int r11 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r11 != 0) goto L_0x019f
            int r11 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r11 != 0) goto L_0x019f
        L_0x0178:
            android.content.SharedPreferences r11 = r8.mSp
            if (r11 == 0) goto L_0x018b
            android.content.SharedPreferences r11 = r8.mSp
            android.content.SharedPreferences$Editor r11 = r11.edit()
            java.lang.String r12 = "t2"
            r11.putLong(r12, r9)
            r11.commit()
        L_0x018b:
            com.ta.utdid2.core.persistent.MySharedPreferences r11 = r8.mMySP     // Catch:{ Exception -> 0x019f }
            if (r11 == 0) goto L_0x019e
            com.ta.utdid2.core.persistent.MySharedPreferences r11 = r8.mMySP     // Catch:{ Exception -> 0x019f }
            com.ta.utdid2.core.persistent.MySharedPreferences$MyEditor r11 = r11.edit()     // Catch:{ Exception -> 0x019f }
            java.lang.String r12 = "t2"
            r11.putLong(r12, r9)     // Catch:{ Exception -> 0x019f }
            r11.commit()     // Catch:{ Exception -> 0x019f }
        L_0x019e:
            return
        L_0x019f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.core.persistent.PersistentConfiguration.<init>(android.content.Context, java.lang.String, java.lang.String, boolean, boolean):void");
    }

    private TransactionXMLFile getTransactionXMLFile(String str) {
        File rootFolder = getRootFolder(str);
        if (rootFolder == null) {
            return null;
        }
        this.mTxf = new TransactionXMLFile(rootFolder.getAbsolutePath());
        return this.mTxf;
    }

    private File getRootFolder(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory == null) {
            return null;
        }
        File file = new File(String.format("%s%s%s", new Object[]{externalStorageDirectory.getAbsolutePath(), File.separator, str}));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private void copySPToMySP(SharedPreferences sharedPreferences, MySharedPreferences mySharedPreferences) {
        if (!(sharedPreferences == null || mySharedPreferences == null)) {
            MyEditor edit = mySharedPreferences.edit();
            if (edit != null) {
                edit.clear();
                for (Entry next : sharedPreferences.getAll().entrySet()) {
                    String str = (String) next.getKey();
                    Object value = next.getValue();
                    if (value instanceof String) {
                        edit.putString(str, (String) value);
                    } else if (value instanceof Integer) {
                        edit.putInt(str, ((Integer) value).intValue());
                    } else if (value instanceof Long) {
                        edit.putLong(str, ((Long) value).longValue());
                    } else if (value instanceof Float) {
                        edit.putFloat(str, ((Float) value).floatValue());
                    } else if (value instanceof Boolean) {
                        edit.putBoolean(str, ((Boolean) value).booleanValue());
                    }
                }
                try {
                    edit.commit();
                } catch (Exception unused) {
                }
            }
        }
    }

    private void copyMySPToSP(MySharedPreferences mySharedPreferences, SharedPreferences sharedPreferences) {
        if (mySharedPreferences != null && sharedPreferences != null) {
            Editor edit = sharedPreferences.edit();
            if (edit != null) {
                edit.clear();
                for (Entry next : mySharedPreferences.getAll().entrySet()) {
                    String str = (String) next.getKey();
                    Object value = next.getValue();
                    if (value instanceof String) {
                        edit.putString(str, (String) value);
                    } else if (value instanceof Integer) {
                        edit.putInt(str, ((Integer) value).intValue());
                    } else if (value instanceof Long) {
                        edit.putLong(str, ((Long) value).longValue());
                    } else if (value instanceof Float) {
                        edit.putFloat(str, ((Float) value).floatValue());
                    } else if (value instanceof Boolean) {
                        edit.putBoolean(str, ((Boolean) value).booleanValue());
                    }
                }
                edit.commit();
            }
        }
    }

    private boolean checkSDCardXMLFile() {
        if (this.mMySP == null) {
            return false;
        }
        boolean checkFile = this.mMySP.checkFile();
        if (!checkFile) {
            commit();
        }
        return checkFile;
    }

    private void initEditor() {
        if (this.mEditor == null && this.mSp != null) {
            this.mEditor = this.mSp.edit();
        }
        if (this.mCanWrite && this.mMyEditor == null && this.mMySP != null) {
            this.mMyEditor = this.mMySP.edit();
        }
        checkSDCardXMLFile();
    }

    public void putInt(String str, int i) {
        if (!StringUtils.isEmpty(str) && !str.equals("t")) {
            initEditor();
            if (this.mEditor != null) {
                this.mEditor.putInt(str, i);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.putInt(str, i);
            }
        }
    }

    public void putLong(String str, long j) {
        if (!StringUtils.isEmpty(str) && !str.equals("t")) {
            initEditor();
            if (this.mEditor != null) {
                this.mEditor.putLong(str, j);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.putLong(str, j);
            }
        }
    }

    public void putBoolean(String str, boolean z) {
        if (!StringUtils.isEmpty(str) && !str.equals("t")) {
            initEditor();
            if (this.mEditor != null) {
                this.mEditor.putBoolean(str, z);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.putBoolean(str, z);
            }
        }
    }

    public void putFloat(String str, float f) {
        if (!StringUtils.isEmpty(str) && !str.equals("t")) {
            initEditor();
            if (this.mEditor != null) {
                this.mEditor.putFloat(str, f);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.putFloat(str, f);
            }
        }
    }

    public void putString(String str, String str2) {
        if (!StringUtils.isEmpty(str) && !str.equals("t")) {
            initEditor();
            if (this.mEditor != null) {
                this.mEditor.putString(str, str2);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.putString(str, str2);
            }
        }
    }

    public void remove(String str) {
        if (!StringUtils.isEmpty(str) && !str.equals("t")) {
            initEditor();
            if (this.mEditor != null) {
                this.mEditor.remove(str);
            }
            if (this.mMyEditor != null) {
                this.mMyEditor.remove(str);
            }
        }
    }

    public void reload() {
        if (!(this.mSp == null || this.mContext == null)) {
            this.mSp = this.mContext.getSharedPreferences(this.mConfigName, 0);
        }
        String str = null;
        try {
            str = Environment.getExternalStorageState();
        } catch (Exception e) {
            UtdidLogger.e("", e, new Object[0]);
        }
        if (!StringUtils.isEmpty(str) && (str.equals("mounted") || (str.equals("mounted_ro") && this.mMySP != null))) {
            try {
                if (this.mTxf != null) {
                    this.mMySP = this.mTxf.getMySharedPreferences(this.mConfigName, 0);
                }
            } catch (Exception unused) {
            }
        }
    }

    public void clear() {
        initEditor();
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mEditor != null) {
            this.mEditor.clear();
            this.mEditor.putLong("t", currentTimeMillis);
        }
        if (this.mMyEditor != null) {
            this.mMyEditor.clear();
            this.mMyEditor.putLong("t", currentTimeMillis);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008d, code lost:
        if (r6.mMyEditor.commit() == false) goto L_0x008f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00a8 A[Catch:{ Exception -> 0x00b2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean commit() {
        /*
            r6 = this;
            long r0 = java.lang.System.currentTimeMillis()
            android.content.SharedPreferences$Editor r2 = r6.mEditor
            r3 = 0
            if (r2 == 0) goto L_0x0023
            boolean r2 = r6.mIsLessMode
            if (r2 != 0) goto L_0x0019
            android.content.SharedPreferences r2 = r6.mSp
            if (r2 == 0) goto L_0x0019
            android.content.SharedPreferences$Editor r2 = r6.mEditor
            java.lang.String r4 = "t"
            r2.putLong(r4, r0)
        L_0x0019:
            android.content.SharedPreferences$Editor r0 = r6.mEditor
            boolean r0 = r0.commit()
            if (r0 != 0) goto L_0x0023
            r0 = 0
            goto L_0x0024
        L_0x0023:
            r0 = 1
        L_0x0024:
            android.content.SharedPreferences r1 = r6.mSp
            if (r1 == 0) goto L_0x0036
            android.content.Context r1 = r6.mContext
            if (r1 == 0) goto L_0x0036
            android.content.Context r1 = r6.mContext
            java.lang.String r2 = r6.mConfigName
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)
            r6.mSp = r1
        L_0x0036:
            r1 = 0
            java.lang.String r2 = android.os.Environment.getExternalStorageState()     // Catch:{ Exception -> 0x003d }
            r1 = r2
            goto L_0x0045
        L_0x003d:
            r2 = move-exception
            java.lang.String r4 = ""
            java.lang.Object[] r5 = new java.lang.Object[r3]
            com.ta.audid.utils.UtdidLogger.e(r4, r2, r5)
        L_0x0045:
            boolean r2 = com.ta.utdid2.android.utils.StringUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x00b2
            java.lang.String r2 = "mounted"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0090
            com.ta.utdid2.core.persistent.MySharedPreferences r2 = r6.mMySP
            if (r2 != 0) goto L_0x0083
            java.lang.String r2 = r6.mFolderName
            com.ta.utdid2.core.persistent.TransactionXMLFile r2 = r6.getTransactionXMLFile(r2)
            if (r2 == 0) goto L_0x0090
            java.lang.String r4 = r6.mConfigName
            com.ta.utdid2.core.persistent.MySharedPreferences r2 = r2.getMySharedPreferences(r4, r3)
            r6.mMySP = r2
            boolean r2 = r6.mIsLessMode
            if (r2 != 0) goto L_0x0073
            android.content.SharedPreferences r2 = r6.mSp
            com.ta.utdid2.core.persistent.MySharedPreferences r4 = r6.mMySP
            r6.copySPToMySP(r2, r4)
            goto L_0x007a
        L_0x0073:
            com.ta.utdid2.core.persistent.MySharedPreferences r2 = r6.mMySP
            android.content.SharedPreferences r4 = r6.mSp
            r6.copyMySPToSP(r2, r4)
        L_0x007a:
            com.ta.utdid2.core.persistent.MySharedPreferences r2 = r6.mMySP
            com.ta.utdid2.core.persistent.MySharedPreferences$MyEditor r2 = r2.edit()
            r6.mMyEditor = r2
            goto L_0x0090
        L_0x0083:
            com.ta.utdid2.core.persistent.MySharedPreferences$MyEditor r2 = r6.mMyEditor     // Catch:{ Exception -> 0x008f }
            if (r2 == 0) goto L_0x0090
            com.ta.utdid2.core.persistent.MySharedPreferences$MyEditor r2 = r6.mMyEditor     // Catch:{ Exception -> 0x008f }
            boolean r2 = r2.commit()     // Catch:{ Exception -> 0x008f }
            if (r2 != 0) goto L_0x0090
        L_0x008f:
            r0 = 0
        L_0x0090:
            java.lang.String r2 = "mounted"
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto L_0x00a4
            java.lang.String r2 = "mounted_ro"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x00b2
            com.ta.utdid2.core.persistent.MySharedPreferences r1 = r6.mMySP
            if (r1 == 0) goto L_0x00b2
        L_0x00a4:
            com.ta.utdid2.core.persistent.TransactionXMLFile r1 = r6.mTxf     // Catch:{ Exception -> 0x00b2 }
            if (r1 == 0) goto L_0x00b2
            com.ta.utdid2.core.persistent.TransactionXMLFile r1 = r6.mTxf     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r2 = r6.mConfigName     // Catch:{ Exception -> 0x00b2 }
            com.ta.utdid2.core.persistent.MySharedPreferences r1 = r1.getMySharedPreferences(r2, r3)     // Catch:{ Exception -> 0x00b2 }
            r6.mMySP = r1     // Catch:{ Exception -> 0x00b2 }
        L_0x00b2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.core.persistent.PersistentConfiguration.commit():boolean");
    }

    public String getString(String str) {
        checkSDCardXMLFile();
        if (this.mSp != null) {
            String string = this.mSp.getString(str, "");
            if (!StringUtils.isEmpty(string)) {
                return string;
            }
        }
        return this.mMySP != null ? this.mMySP.getString(str, "") : "";
    }

    public int getInt(String str) {
        checkSDCardXMLFile();
        if (this.mSp != null) {
            return this.mSp.getInt(str, 0);
        }
        if (this.mMySP != null) {
            return this.mMySP.getInt(str, 0);
        }
        return 0;
    }

    public long getLong(String str) {
        checkSDCardXMLFile();
        if (this.mSp != null) {
            return this.mSp.getLong(str, 0);
        }
        if (this.mMySP != null) {
            return this.mMySP.getLong(str, 0);
        }
        return 0;
    }

    public float getFloat(String str) {
        checkSDCardXMLFile();
        if (this.mSp != null) {
            return this.mSp.getFloat(str, 0.0f);
        }
        if (this.mMySP != null) {
            return this.mMySP.getFloat(str, 0.0f);
        }
        return 0.0f;
    }

    public boolean getBoolean(String str) {
        checkSDCardXMLFile();
        if (this.mSp != null) {
            return this.mSp.getBoolean(str, false);
        }
        if (this.mMySP != null) {
            return this.mMySP.getBoolean(str, false);
        }
        return false;
    }

    public Map<String, ?> getAll() {
        checkSDCardXMLFile();
        if (this.mSp != null) {
            return this.mSp.getAll();
        }
        if (this.mMySP != null) {
            return this.mMySP.getAll();
        }
        return null;
    }
}
