package com.alipay.android.phone.mobilesdk.storage.encryption;

import android.content.ContextWrapper;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.dynamicdataencrypt.IDynamicDataEncryptComponent;
import com.alibaba.wireless.security.open.staticdataencrypt.IStaticDataEncryptComponent;
import com.alibaba.wireless.security.open.staticdatastore.IStaticDataStoreComponent;

public class UtilWX {
    private static volatile IStaticDataEncryptComponent staticDataEncryptComponent;
    private static volatile IStaticDataStoreComponent staticDataStoreComponent;
    private ContextWrapper context;

    public UtilWX(ContextWrapper context2) {
        this.context = context2;
    }

    public String Put(String data, DataContext ctx) {
        String appKey;
        if (data == null || data.length() <= 0 || ctx == null) {
            return null;
        }
        try {
            if (staticDataStoreComponent == null) {
                staticDataStoreComponent = SecurityGuardManager.getInstance(this.context).getStaticDataStoreComp();
            }
            if (staticDataStoreComponent == null) {
                return null;
            }
            if (ctx.extData != null) {
                appKey = new String(ctx.extData);
            } else {
                appKey = staticDataStoreComponent.getAppKeyByIndex(ctx.index < 0 ? 0 : ctx.index, "");
            }
            if (appKey == null) {
                return null;
            }
            if (staticDataEncryptComponent == null) {
                staticDataEncryptComponent = SecurityGuardManager.getInstance(this.context).getStaticDataEncryptComp();
            }
            if (staticDataEncryptComponent != null) {
                return staticDataEncryptComponent.staticSafeEncrypt(16, appKey, data, "");
            }
            return null;
        } catch (SecException e) {
            return null;
        }
    }

    public String Get(String data, DataContext ctx) {
        String appKey;
        if (data == null || data.length() <= 0 || ctx == null) {
            return null;
        }
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(this.context);
            if (staticDataStoreComponent == null) {
                staticDataStoreComponent = instance.getStaticDataStoreComp();
            }
            if (staticDataStoreComponent == null) {
                return null;
            }
            if (ctx.extData != null) {
                appKey = new String(ctx.extData);
            } else {
                appKey = staticDataStoreComponent.getAppKeyByIndex(ctx.index < 0 ? 0 : ctx.index, "");
            }
            if (appKey == null) {
                return null;
            }
            if (staticDataEncryptComponent == null) {
                staticDataEncryptComponent = instance.getStaticDataEncryptComp();
            }
            if (staticDataEncryptComponent != null) {
                return staticDataEncryptComponent.staticSafeDecrypt(16, appKey, data, "");
            }
            return null;
        } catch (SecException e) {
            return null;
        }
    }

    public byte[] Put(byte[] data, DataContext ctx) {
        String appKey;
        if (data == null || data.length <= 0 || ctx == null) {
            return null;
        }
        try {
            IStaticDataStoreComponent staticDataStoreComponent2 = SecurityGuardManager.getInstance(this.context).getStaticDataStoreComp();
            if (staticDataStoreComponent2 == null) {
                return null;
            }
            if (ctx.extData != null) {
                appKey = new String(ctx.extData);
            } else {
                appKey = staticDataStoreComponent2.getAppKeyByIndex(ctx.index < 0 ? 0 : ctx.index, "");
            }
            if (appKey == null) {
                return null;
            }
            IStaticDataEncryptComponent staticDataEncryptComponent2 = SecurityGuardManager.getInstance(this.context).getStaticDataEncryptComp();
            if (staticDataEncryptComponent2 != null) {
                return staticDataEncryptComponent2.staticBinarySafeEncrypt(16, appKey, data, "");
            }
            return null;
        } catch (SecException e) {
            return null;
        }
    }

    public byte[] Get(byte[] data, DataContext ctx) {
        String appKey;
        if (data == null || data.length <= 0 || ctx == null) {
            return null;
        }
        try {
            IStaticDataStoreComponent staticDataStoreComponent2 = SecurityGuardManager.getInstance(this.context).getStaticDataStoreComp();
            if (staticDataStoreComponent2 == null) {
                return null;
            }
            if (ctx.extData != null) {
                appKey = new String(ctx.extData);
            } else {
                appKey = staticDataStoreComponent2.getAppKeyByIndex(ctx.index < 0 ? 0 : ctx.index, "");
            }
            if (appKey == null) {
                return null;
            }
            IStaticDataEncryptComponent staticDataEncryptComponent2 = SecurityGuardManager.getInstance(this.context).getStaticDataEncryptComp();
            if (staticDataEncryptComponent2 != null) {
                return staticDataEncryptComponent2.staticBinarySafeDecrypt(16, appKey, data, "");
            }
            return null;
        } catch (SecException e) {
            return null;
        }
    }

    public String EncryptData(String data, String key) {
        if (data != null && data.length() > 0 && key != null && key.length() > 0) {
            try {
                IStaticDataEncryptComponent staticDataEncryptComponent2 = SecurityGuardManager.getInstance(this.context).getStaticDataEncryptComp();
                if (staticDataEncryptComponent2 != null) {
                    byte[] result = staticDataEncryptComponent2.staticBinarySafeEncrypt(16, key, data.getBytes(), "");
                    if (result != null) {
                        return new String(result);
                    }
                }
            } catch (SecException e) {
                return null;
            }
        }
        return null;
    }

    public String DecryptData(String data, String key) {
        if (data != null && data.length() > 0 && key != null && key.length() > 0) {
            try {
                IStaticDataEncryptComponent staticDataEncryptComponent2 = SecurityGuardManager.getInstance(this.context).getStaticDataEncryptComp();
                if (staticDataEncryptComponent2 != null) {
                    byte[] result = staticDataEncryptComponent2.staticBinarySafeDecrypt(16, key, data.getBytes(), "");
                    if (result != null) {
                        return new String(result);
                    }
                }
            } catch (SecException e) {
                return null;
            }
        }
        return null;
    }

    public byte[] EncryptData(byte[] data, byte[] key) {
        if (data == null || data.length <= 0 || key == null || key.length <= 0) {
            return null;
        }
        try {
            IStaticDataEncryptComponent staticDataEncryptComponent2 = SecurityGuardManager.getInstance(this.context).getStaticDataEncryptComp();
            if (staticDataEncryptComponent2 != null) {
                return staticDataEncryptComponent2.staticBinarySafeEncrypt(16, new String(key), data, "");
            }
            return null;
        } catch (SecException e) {
            return null;
        }
    }

    public byte[] DecryptData(byte[] data, byte[] key) {
        if (data == null || data.length <= 0 || key == null || key.length <= 0) {
            return null;
        }
        try {
            IStaticDataEncryptComponent staticDataEncryptComponent2 = SecurityGuardManager.getInstance(this.context).getStaticDataEncryptComp();
            if (staticDataEncryptComponent2 != null) {
                return staticDataEncryptComponent2.staticBinarySafeDecrypt(16, new String(key), data, "");
            }
            return null;
        } catch (SecException e) {
            return null;
        }
    }

    public String Put(String data) {
        if (data == null || data.length() <= 0) {
            return null;
        }
        try {
            IDynamicDataEncryptComponent dynamicDataEncryptComponent = SecurityGuardManager.getInstance(this.context).getDynamicDataEncryptComp();
            if (dynamicDataEncryptComponent != null) {
                return dynamicDataEncryptComponent.dynamicEncrypt(data);
            }
            return null;
        } catch (SecException e) {
            return null;
        }
    }

    public String Get(String data) {
        if (data == null || data.length() <= 0) {
            return null;
        }
        try {
            IDynamicDataEncryptComponent dynamicDataEncryptComponent = SecurityGuardManager.getInstance(this.context).getDynamicDataEncryptComp();
            if (dynamicDataEncryptComponent != null) {
                return dynamicDataEncryptComponent.dynamicDecrypt(data);
            }
            return null;
        } catch (SecException e) {
            return null;
        }
    }

    public byte[] Put(byte[] data) {
        if (data == null || data.length <= 0) {
            return null;
        }
        try {
            IDynamicDataEncryptComponent dynamicDataEncryptComponent = SecurityGuardManager.getInstance(this.context).getDynamicDataEncryptComp();
            if (dynamicDataEncryptComponent == null) {
                return null;
            }
            String result = dynamicDataEncryptComponent.dynamicEncrypt(new String(data));
            if (result == null || result.length() <= 0) {
                return null;
            }
            return result.getBytes();
        } catch (SecException e) {
            return null;
        }
    }

    public byte[] Get(byte[] data) {
        if (data == null || data.length <= 0) {
            return null;
        }
        try {
            IDynamicDataEncryptComponent dynamicDataEncryptComponent = SecurityGuardManager.getInstance(this.context).getDynamicDataEncryptComp();
            if (dynamicDataEncryptComponent == null) {
                return null;
            }
            String result = dynamicDataEncryptComponent.dynamicDecrypt(new String(data));
            if (result == null || result.length() <= 0) {
                return null;
            }
            return result.getBytes();
        } catch (SecException e) {
            return null;
        }
    }
}
