package com.alibaba.analytics.utils;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class StringUtils {
    public static String convertObjectToString(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return ((String) obj).toString();
        }
        if (obj instanceof Integer) {
            StringBuilder sb = new StringBuilder();
            sb.append(((Integer) obj).intValue());
            return sb.toString();
        } else if (obj instanceof Long) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(((Long) obj).longValue());
            return sb2.toString();
        } else if (obj instanceof Double) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(((Double) obj).doubleValue());
            return sb3.toString();
        } else if (obj instanceof Float) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(((Float) obj).floatValue());
            return sb4.toString();
        } else if (obj instanceof Short) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(((Short) obj).shortValue());
            return sb5.toString();
        } else if (obj instanceof Byte) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(((Byte) obj).byteValue());
            return sb6.toString();
        } else if (obj instanceof Boolean) {
            return ((Boolean) obj).toString();
        } else {
            if (obj instanceof Character) {
                return ((Character) obj).toString();
            }
            return obj.toString();
        }
    }

    public static int hashCode(String str) {
        if (str.length() <= 0) {
            return 0;
        }
        int i = 0;
        for (char c : str.toCharArray()) {
            i = (i * 31) + c;
        }
        return i;
    }

    public static String convertMapToString(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        boolean z = true;
        StringBuffer stringBuffer = new StringBuffer();
        for (String next : map.keySet()) {
            String convertObjectToString = convertObjectToString(map.get(next));
            String convertObjectToString2 = convertObjectToString(next);
            if (!(convertObjectToString == null || convertObjectToString2 == null)) {
                if (z) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(convertObjectToString2);
                    sb.append("=");
                    sb.append(convertObjectToString);
                    stringBuffer.append(sb.toString());
                    z = false;
                } else {
                    stringBuffer.append(",");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(convertObjectToString2);
                    sb2.append("=");
                    sb2.append(convertObjectToString);
                    stringBuffer.append(sb2.toString());
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String convertStringAToString(String... strArr) {
        if (strArr != null && strArr.length == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (strArr != null && strArr.length > 0) {
            boolean z = false;
            for (int i = 0; i < strArr.length; i++) {
                if (!isEmpty(strArr[i])) {
                    if (z) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(strArr[i]);
                    z = true;
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String[] convertMapToStringA(Map<String, String> map) {
        if (map != null) {
            LinkedList linkedList = new LinkedList();
            for (String next : map.keySet()) {
                String str = map.get(next);
                String convertObjectToString = convertObjectToString(next);
                if (!(str == null || convertObjectToString == null)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(convertObjectToString);
                    sb.append("=");
                    sb.append(str);
                    linkedList.add(sb.toString());
                }
            }
            if (linkedList.size() > 0) {
                String[] strArr = new String[linkedList.size()];
                for (int i = 0; i < linkedList.size(); i++) {
                    strArr[i] = (String) linkedList.get(i);
                }
                return strArr;
            }
        }
        return null;
    }

    public static String convertToPostString(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        boolean z = true;
        StringBuffer stringBuffer = new StringBuffer();
        for (String next : map.keySet()) {
            String convertObjectToString = convertObjectToString(map.get(next));
            String convertObjectToString2 = convertObjectToString(next);
            if (!(convertObjectToString == null || convertObjectToString2 == null)) {
                if (z) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(convertObjectToString2);
                    sb.append("=");
                    sb.append(convertObjectToString);
                    stringBuffer.append(sb.toString());
                    z = false;
                } else {
                    stringBuffer.append("&");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(convertObjectToString2);
                    sb2.append("=");
                    sb2.append(convertObjectToString);
                    stringBuffer.append(sb2.toString());
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String convertMapToStringWithUrlEncoder(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        boolean z = true;
        StringBuffer stringBuffer = new StringBuffer();
        for (String next : map.keySet()) {
            String convertObjectToString = convertObjectToString(map.get(next));
            String convertObjectToString2 = convertObjectToString(next);
            if (!(convertObjectToString == null || convertObjectToString2 == null)) {
                if (z) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append(URLEncoder.encode(convertObjectToString2, "UTF-8"));
                        sb.append("=");
                        sb.append(URLEncoder.encode(convertObjectToString, "UTF-8"));
                        stringBuffer.append(sb.toString());
                        z = false;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        stringBuffer.append(",");
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(URLEncoder.encode(convertObjectToString2, "UTF-8"));
                        sb2.append("=");
                        sb2.append(URLEncoder.encode(convertObjectToString, "UTF-8"));
                        stringBuffer.append(sb2.toString());
                    } catch (UnsupportedEncodingException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String transMapToString(Map<String, String> map) {
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry next = it.next();
            stringBuffer.append(next.getKey().toString());
            stringBuffer.append("'");
            if (next.getValue() == null) {
                str = "";
            } else {
                str = next.getValue().toString();
            }
            stringBuffer.append(str);
            stringBuffer.append(it.hasNext() ? "^" : "");
        }
        return stringBuffer.toString();
    }

    public static Map<String, String> transStringToMap(String str) {
        HashMap hashMap = new HashMap();
        StringTokenizer stringTokenizer = new StringTokenizer(str, "^");
        while (stringTokenizer.hasMoreTokens()) {
            StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), "'");
            hashMap.put(stringTokenizer2.nextToken(), stringTokenizer2.hasMoreTokens() ? stringTokenizer2.nextToken() : null);
        }
        return hashMap;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        if (str != null) {
            int length = str.length();
            if (length != 0) {
                for (int i = 0; i < length; i++) {
                    if (!Character.isWhitespace(str.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }
}
