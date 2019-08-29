package com.alibaba.analytics.core.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import com.alibaba.analytics.utils.SPHelper;
import com.alibaba.analytics.utils.StringUtils;
import java.util.Map;

public class PersistentConfiguration {
    private String mConfigName = "";
    private Context mContext = null;
    private Editor mEditor = null;
    private SharedPreferences mSp = null;

    public PersistentConfiguration(Context context, String str, String str2, boolean z, boolean z2) {
        this.mConfigName = str2;
        this.mContext = context;
        if (context != null) {
            this.mSp = context.getSharedPreferences(str2, 0);
        }
    }

    private void initEditor() {
        if (this.mEditor == null && this.mSp != null) {
            this.mEditor = this.mSp.edit();
        }
    }

    public void putInt(String str, int i) {
        initEditor();
        if (this.mEditor != null) {
            this.mEditor.putInt(str, i);
        }
    }

    public void putLong(String str, long j) {
        initEditor();
        if (this.mEditor != null) {
            this.mEditor.putLong(str, j);
        }
    }

    public void putBoolean(String str, boolean z) {
        initEditor();
        if (this.mEditor != null) {
            this.mEditor.putBoolean(str, z);
        }
    }

    public void putFloat(String str, float f) {
        initEditor();
        if (this.mEditor != null) {
            this.mEditor.putFloat(str, f);
        }
    }

    public void putString(String str, String str2) {
        initEditor();
        if (this.mEditor != null) {
            this.mEditor.putString(str, str2);
        }
    }

    public void remove(String str) {
        initEditor();
        if (this.mEditor != null) {
            this.mEditor.remove(str);
        }
    }

    public void reload() {
        if (this.mSp != null && this.mContext != null) {
            this.mSp = this.mContext.getSharedPreferences(this.mConfigName, 0);
        }
    }

    public void clear() {
        initEditor();
        System.currentTimeMillis();
        if (this.mEditor != null) {
            this.mEditor.clear();
        }
    }

    public boolean commit() {
        if (this.mEditor != null) {
            if (VERSION.SDK_INT >= 9) {
                SPHelper.apply(this.mEditor);
            } else {
                this.mEditor.commit();
            }
        }
        if (!(this.mSp == null || this.mContext == null)) {
            this.mSp = this.mContext.getSharedPreferences(this.mConfigName, 0);
        }
        return true;
    }

    public String getString(String str) {
        if (this.mSp != null) {
            String string = this.mSp.getString(str, "");
            if (!StringUtils.isEmpty(string)) {
                return string;
            }
        }
        return "";
    }

    public int getInt(String str) {
        if (this.mSp != null) {
            return this.mSp.getInt(str, 0);
        }
        return 0;
    }

    public long getLong(String str) {
        if (this.mSp != null) {
            return this.mSp.getLong(str, 0);
        }
        return 0;
    }

    public float getFloat(String str) {
        if (this.mSp != null) {
            return this.mSp.getFloat(str, 0.0f);
        }
        return 0.0f;
    }

    public boolean getBoolean(String str) {
        if (this.mSp != null) {
            return this.mSp.getBoolean(str, false);
        }
        return false;
    }

    public Map<String, ?> getAll() {
        if (this.mSp != null) {
            return this.mSp.getAll();
        }
        return null;
    }
}
