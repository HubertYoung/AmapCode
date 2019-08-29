package com.alipay.android.phone.mobilesdk.storage.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.util.Map;

public class APSharedPreferences {
    private Editor edit = null;
    private String mGroup = "alipay_default_sp";
    private int mMode = 0;
    private SharedPreferences mSP;
    private Context sContext = null;

    protected APSharedPreferences(Context context, String group, int mode) {
        if (context != null) {
            this.sContext = context.getApplicationContext();
        }
        this.mGroup = group;
        this.mMode = mode;
    }

    public void init() {
    }

    private void createEditIfNot() {
        if (this.sContext != null && this.edit == null) {
            synchronized (this) {
                if (this.edit == null) {
                    this.edit = this.sContext.getSharedPreferences(getGroup(), this.mMode).edit();
                }
            }
        }
    }

    private void createSPIfNot() {
        if (this.sContext != null && this.mSP == null) {
            synchronized (this) {
                if (this.mSP == null) {
                    this.mSP = this.sContext.getSharedPreferences(getGroup(), this.mMode);
                }
            }
        }
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getBoolean(getGroup(), key, defValue);
    }

    public String getString(String key, String defValue) {
        return getString(getGroup(), key, defValue);
    }

    public int getInt(String key, int defValue) {
        return getInt(getGroup(), key, defValue);
    }

    public long getLong(String key, long defValue) {
        return getLong(getGroup(), key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return getFloat(getGroup(), key, defValue);
    }

    public boolean putInt(String key, int value) {
        return putInt(getGroup(), key, value);
    }

    public boolean putBoolean(String key, boolean value) {
        return putBoolean(getGroup(), key, value);
    }

    public boolean putString(String key, String value) {
        return putString(getGroup(), key, value);
    }

    public boolean putLong(String key, long value) {
        return putLong(getGroup(), key, value);
    }

    public boolean putFloat(String key, float value) {
        return putFloat(getGroup(), key, value);
    }

    public boolean contains(String key) {
        return contains(getGroup(), key);
    }

    public boolean commit() {
        createEditIfNot();
        if (this.edit != null) {
            return this.edit.commit();
        }
        return false;
    }

    public void apply() {
        createEditIfNot();
        if (this.edit != null) {
            this.edit.apply();
        }
    }

    public boolean remove(String key) {
        createEditIfNot();
        if (this.edit == null || TextUtils.isEmpty(key)) {
            return false;
        }
        this.edit.remove(key);
        return true;
    }

    public boolean clear() {
        createEditIfNot();
        if (this.edit == null) {
            return false;
        }
        this.edit.clear();
        return true;
    }

    public Map<String, ?> getAll() {
        if (this.sContext == null) {
            return null;
        }
        if (this.mMode != 0) {
            return this.sContext.getSharedPreferences(getGroup(), this.mMode).getAll();
        }
        createSPIfNot();
        return this.mSP.getAll();
    }

    private boolean contains(String name, String key) {
        if (this.sContext == null) {
            return false;
        }
        if (this.mMode != 0) {
            return this.sContext.getSharedPreferences(name, this.mMode).contains(key);
        }
        createSPIfNot();
        return this.mSP.contains(key);
    }

    private boolean getBoolean(String name, String key, boolean defValue) {
        if (this.sContext == null) {
            return defValue;
        }
        if (this.mMode != 0) {
            return this.sContext.getSharedPreferences(name, this.mMode).getBoolean(key, defValue);
        }
        createSPIfNot();
        return this.mSP.getBoolean(key, defValue);
    }

    private String getString(String name, String key, String defValue) {
        if (this.sContext == null) {
            return defValue;
        }
        if (this.mMode != 0) {
            return this.sContext.getSharedPreferences(name, this.mMode).getString(key, defValue);
        }
        createSPIfNot();
        return this.mSP.getString(key, defValue);
    }

    private int getInt(String name, String key, int defValue) {
        if (this.sContext == null) {
            return defValue;
        }
        if (this.mMode != 0) {
            return this.sContext.getSharedPreferences(name, this.mMode).getInt(key, defValue);
        }
        createSPIfNot();
        return this.mSP.getInt(key, defValue);
    }

    private long getLong(String name, String key, long defValue) {
        if (this.sContext == null) {
            return defValue;
        }
        if (this.mMode != 0) {
            return this.sContext.getSharedPreferences(name, this.mMode).getLong(key, defValue);
        }
        createSPIfNot();
        return this.mSP.getLong(key, defValue);
    }

    private float getFloat(String name, String key, float defValue) {
        if (this.sContext == null) {
            return defValue;
        }
        if (this.mMode != 0) {
            return this.sContext.getSharedPreferences(name, this.mMode).getFloat(key, defValue);
        }
        createSPIfNot();
        return this.mSP.getFloat(key, defValue);
    }

    private boolean putInt(String name, String key, int value) {
        createEditIfNot();
        if (this.edit == null) {
            return false;
        }
        this.edit.putInt(key, value);
        return true;
    }

    private boolean putBoolean(String name, String key, boolean value) {
        createEditIfNot();
        if (this.edit == null) {
            return false;
        }
        this.edit.putBoolean(key, value);
        return true;
    }

    private boolean putString(String name, String key, String value) {
        createEditIfNot();
        if (this.edit == null) {
            return false;
        }
        this.edit.putString(key, value);
        return true;
    }

    private boolean putLong(String name, String key, long value) {
        createEditIfNot();
        if (this.edit == null) {
            return false;
        }
        this.edit.putLong(key, value);
        return true;
    }

    private boolean putFloat(String name, String key, float value) {
        createEditIfNot();
        if (this.edit == null) {
            return false;
        }
        this.edit.putFloat(key, value);
        return true;
    }

    private String getGroup() {
        return this.mGroup;
    }
}
