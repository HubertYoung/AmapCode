package com.alipay.mobile.common.utils;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonConvert {
    public JsonConvert() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static HashMap<String, Object> Json2Map(JSONObject object) {
        Iterator iterator = object.keys();
        HashMap ret = new HashMap();
        while (iterator.hasNext()) {
            String name = iterator.next().toString();
            Object val = object.opt(name);
            if (val instanceof JSONObject) {
                ret.put(name, Json2Map((JSONObject) val));
            } else if (val instanceof JSONArray) {
                ret.put(name, Json2Array((JSONArray) val));
            } else {
                ret.put(name, val);
            }
        }
        return ret;
    }

    public static <T> T json2Bean(JSONObject object, T bean) {
        Iterator iterator = object.keys();
        while (iterator.hasNext()) {
            String name = iterator.next().toString();
            Object val = object.opt(name);
            Class clazz = bean.getClass();
            try {
                clazz.getMethod("set" + (name.substring(0, 1).toUpperCase() + name.substring(1)), new Class[]{val.getClass()}).invoke(bean, new Object[]{val});
            } catch (Exception e) {
                LogCatUtil.warn((String) "JsonConvert", "Exception " + e.toString());
            }
        }
        return bean;
    }

    public static <T> List<T> jArry2BeanList(JSONArray object, T bean) {
        List beanList = new ArrayList();
        if (object != null) {
            for (int i = 0; i < object.length(); i++) {
                try {
                    beanList.add(json2Bean(object.optJSONObject(i), bean.getClass().newInstance()));
                } catch (Exception e) {
                    Log.e("JsonConvert", "", e);
                }
            }
        }
        return beanList;
    }

    public static ArrayList<Object> Json2Array(JSONArray array) {
        int length = array.length();
        ArrayList ret = new ArrayList();
        for (int i = 0; i < length; i++) {
            Object val = array.opt(i);
            if (val instanceof JSONObject) {
                ret.add(Json2Map((JSONObject) val));
            } else if (val instanceof JSONArray) {
                ret.add(Json2Array((JSONArray) val));
            } else {
                ret.add(val);
            }
        }
        return ret;
    }

    public static String Map2Json(HashMap<String, Object> hashMap) {
        if (hashMap == null) {
            return "";
        }
        Set set = hashMap.keySet();
        JSONObject jsonObject = new JSONObject();
        try {
            for (String str : set) {
                Object o = hashMap.get(str);
                if (o instanceof HashMap) {
                    jsonObject.put(str, new JSONObject((HashMap) o));
                } else if (o instanceof ArrayList) {
                    jsonObject.put(str, new JSONArray((ArrayList) o));
                } else {
                    jsonObject.put(str, o);
                }
            }
        } catch (Exception e) {
            LogCatUtil.warn((String) "JsonConvert", "Map2Json exception. " + e.toString());
        }
        return jsonObject.toString();
    }

    public static String Array2Json(ArrayList<Object> arrayList) {
        if (arrayList == null) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        Iterator<Object> it = arrayList.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof HashMap) {
                jsonArray.put(new JSONObject((HashMap) o));
            } else if (o instanceof ArrayList) {
                jsonArray.put(new JSONArray((ArrayList) o));
            } else {
                jsonArray.put(o);
            }
        }
        return jsonArray.toString();
    }

    public static String ArrayString2Json(ArrayList<String> arrayList) {
        return new JSONArray(arrayList).toString();
    }

    public static JSONObject convertString2Json(String result) {
        if (result == null) {
            return null;
        }
        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            Log.e("JsonConvert", "", e);
            return null;
        }
    }

    public static JSONArray converString2JsonArray(String result) {
        if (result == null) {
            return null;
        }
        try {
            return new JSONArray(result);
        } catch (JSONException e) {
            Log.e("JsonConvert", "", e);
            return null;
        }
    }
}
