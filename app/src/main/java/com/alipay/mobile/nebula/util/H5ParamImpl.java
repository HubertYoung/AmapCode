package com.alipay.mobile.nebula.util;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param.ParamType;

public class H5ParamImpl {
    public static final String TAG = "H5ParamImpl";
    private Object defaultValue;
    private String longName;
    private String shortName;
    private ParamType type;

    public H5ParamImpl(String ln, String sn, ParamType type2, Object dv) {
        this.longName = ln;
        this.shortName = sn;
        this.type = type2;
        this.defaultValue = dv;
    }

    public void setDefaultValue(Object defaultValue2) {
        this.defaultValue = defaultValue2;
    }

    public String getLongName() {
        return this.longName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public ParamType getType() {
        return this.type;
    }

    public void setType(ParamType type2) {
        this.type = type2;
    }

    public Bundle unify(Bundle bundle, boolean fillDefault) {
        if (fillDefault || H5Utils.contains(bundle, this.longName) || H5Utils.contains(bundle, this.shortName)) {
            if (ParamType.BOOLEAN == this.type) {
                boolean value = ((Boolean) this.defaultValue).booleanValue();
                Object obj = null;
                if (bundle.containsKey(this.shortName)) {
                    obj = bundle.get(this.shortName);
                } else if (bundle.containsKey(this.longName)) {
                    obj = bundle.get(this.longName);
                }
                if (obj instanceof String) {
                    String valueStr = ((String) obj).trim();
                    if ("YES".equalsIgnoreCase(valueStr)) {
                        value = true;
                    } else if ("NO".equalsIgnoreCase(valueStr)) {
                        value = false;
                    } else if ("false".equalsIgnoreCase(valueStr)) {
                        value = false;
                    } else if ("true".equalsIgnoreCase(valueStr)) {
                        value = true;
                    }
                } else if (obj instanceof Boolean) {
                    value = ((Boolean) obj).booleanValue();
                }
                bundle.putBoolean(this.longName, value);
            } else if (ParamType.STRING == this.type) {
                String df = (String) this.defaultValue;
                String value2 = df;
                if (H5Utils.contains(bundle, this.shortName)) {
                    value2 = H5Utils.getString(bundle, this.shortName, df);
                } else if (H5Utils.contains(bundle, this.longName)) {
                    value2 = H5Utils.getString(bundle, this.longName, df);
                }
                if (value2 != null) {
                    value2 = value2.trim();
                }
                bundle.putString(this.longName, value2);
            } else if (ParamType.INT.equals(this.type)) {
                int value3 = ((Integer) this.defaultValue).intValue();
                Object obj2 = null;
                if (bundle.containsKey(this.shortName)) {
                    obj2 = bundle.get(this.shortName);
                } else if (bundle.containsKey(this.longName)) {
                    obj2 = bundle.get(this.longName);
                }
                if (obj2 instanceof String) {
                    String valueStr2 = ((String) obj2).trim();
                    try {
                        if (!TextUtils.isEmpty(valueStr2)) {
                            value3 = Integer.parseInt(valueStr2);
                        }
                    } catch (Exception e) {
                        H5Log.e(TAG, "Exception", e);
                    }
                } else if (obj2 instanceof Integer) {
                    value3 = ((Integer) obj2).intValue();
                }
                bundle.putInt(this.longName, value3);
            } else if (ParamType.DOUBLE.equals(this.type)) {
                double value4 = (double) ((Integer) this.defaultValue).intValue();
                Object obj3 = null;
                if (bundle.containsKey(this.shortName)) {
                    obj3 = bundle.get(this.shortName);
                } else if (bundle.containsKey(this.longName)) {
                    obj3 = bundle.get(this.longName);
                }
                if (obj3 instanceof String) {
                    try {
                        value4 = Double.parseDouble(((String) obj3).trim());
                    } catch (Exception e2) {
                        H5Log.e(TAG, "Exception", e2);
                    }
                } else if (obj3 instanceof Double) {
                    value4 = ((Double) obj3).doubleValue();
                }
                bundle.putDouble(this.longName, value4);
            }
            if (!TextUtils.equals(this.longName, this.shortName)) {
                try {
                    bundle.remove(this.shortName);
                } catch (Exception e3) {
                    H5Log.e((String) TAG, (Throwable) e3);
                }
            }
        }
        return bundle;
    }
}
