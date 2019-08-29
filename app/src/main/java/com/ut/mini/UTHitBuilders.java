package com.ut.mini;

import com.alibaba.analytics.AnalyticsMgr;
import com.alibaba.analytics.core.Constants.LogContentKeys;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.MapUtils;
import com.alibaba.analytics.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class UTHitBuilders {

    public static class UTControlHitBuilder extends UTHitBuilder {
        public UTControlHitBuilder(String str) {
            if (!StringUtils.isEmpty(str)) {
                String currentPageName = UTPageHitHelper.getInstance().getCurrentPageName();
                if (!StringUtils.isEmpty(currentPageName)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(currentPageName);
                    sb.append("_");
                    sb.append(str);
                    String sb2 = sb.toString();
                    super.setProperty(UTHitBuilder.FIELD_PAGE, currentPageName);
                    super.setProperty(UTHitBuilder.FIELD_EVENT_ID, "2101");
                    super.setProperty(UTHitBuilder.FIELD_ARG1, sb2);
                } else if (AnalyticsMgr.isDebug) {
                    throw new IllegalArgumentException("Please call in at PageAppear and PageDisAppear.");
                } else {
                    Logger.e((String) "Please call in at PageAppear and PageDisAppear.", new Object[0]);
                }
            } else if (AnalyticsMgr.isDebug) {
                throw new IllegalArgumentException("Control name can not be empty.");
            } else {
                Logger.e((String) "Control name can not be empty.", new Object[0]);
            }
        }

        public UTControlHitBuilder(String str, String str2) {
            if (StringUtils.isEmpty(str2)) {
                if (AnalyticsMgr.isDebug) {
                    throw new IllegalArgumentException("Control name can not be empty.");
                }
                Logger.e((String) "Control name can not be empty.", new Object[0]);
            } else if (!StringUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("_");
                sb.append(str2);
                String sb2 = sb.toString();
                super.setProperty(UTHitBuilder.FIELD_PAGE, str);
                super.setProperty(UTHitBuilder.FIELD_EVENT_ID, "2101");
                super.setProperty(UTHitBuilder.FIELD_ARG1, sb2);
            } else if (AnalyticsMgr.isDebug) {
                throw new IllegalArgumentException("Page name can not be empty.");
            } else {
                Logger.e((String) "Page name can not be empty.", new Object[0]);
            }
        }
    }

    public static class UTCustomHitBuilder extends UTHitBuilder {
        public UTCustomHitBuilder(String str) {
            if (!StringUtils.isEmpty(str)) {
                super.setProperty(UTHitBuilder.FIELD_ARG1, str);
            }
            super.setProperty(UTHitBuilder.FIELD_EVENT_ID, "19999");
            super.setProperty(UTHitBuilder.FIELD_ARG3, "0");
            super.setProperty(LogContentKeys.PRIORITY, "4");
        }

        public UTCustomHitBuilder setDurationOnEvent(long j) {
            if (j < 0) {
                j = 0;
            }
            super.setProperty(UTHitBuilder.FIELD_ARG3, String.valueOf(j));
            return this;
        }

        public UTCustomHitBuilder setEventPage(String str) {
            if (!StringUtils.isEmpty(str)) {
                super.setProperty(UTHitBuilder.FIELD_PAGE, str);
            }
            return this;
        }

        public Map<String, String> build() {
            Map<String, String> build = super.build();
            if (build != null) {
                String str = build.get(LogField.PAGE.toString());
                String str2 = build.get(LogField.ARG1.toString());
                if (str2 != null) {
                    build.remove(LogField.ARG1.toString());
                    build.remove(LogField.PAGE.toString());
                    Map<String, String> convertToUrlEncodedMap = MapUtils.convertToUrlEncodedMap(build);
                    convertToUrlEncodedMap.put(LogField.ARG1.toString(), str2);
                    convertToUrlEncodedMap.put(LogField.PAGE.toString(), str);
                    return convertToUrlEncodedMap;
                }
            }
            return build;
        }
    }

    public static class UTHitBuilder {
        public static final String FIELD_ARG1 = "_field_arg1";
        public static final String FIELD_ARG2 = "_field_arg2";
        public static final String FIELD_ARG3 = "_field_arg3";
        public static final String FIELD_ARGS = "_field_args";
        public static final String FIELD_EVENT_ID = "_field_event_id";
        public static final String FIELD_PAGE = "_field_page";
        private Map<String, String> mHitMap = new HashMap(64);

        public UTHitBuilder() {
            if (!this.mHitMap.containsKey(FIELD_PAGE)) {
                this.mHitMap.put(FIELD_PAGE, "UT");
            }
        }

        public UTHitBuilder setProperty(String str, String str2) {
            if (StringUtils.isEmpty(str) || str2 == null) {
                Logger.e((String) "setProperty", "key is null or key is empty or value is null,please check it!");
            } else {
                if (this.mHitMap.containsKey(str)) {
                    this.mHitMap.remove(str);
                }
                this.mHitMap.put(str, str2);
            }
            return this;
        }

        public UTHitBuilder setProperties(Map<String, String> map) {
            if (map != null) {
                Set<Entry<String, String>> entrySet = map.entrySet();
                if (entrySet != null) {
                    for (Entry next : entrySet) {
                        Object key = next.getKey();
                        Object value = next.getValue();
                        if (key == null || !(key instanceof String)) {
                            Logger.w((String) "param aProperties key error", "key", key, "value", value);
                        } else if (value == null || !(value instanceof String)) {
                            Logger.w((String) "param aProperties value error", "key".concat(String.valueOf(key)), "value", value);
                        } else {
                            setProperty((String) key, (String) value);
                        }
                    }
                }
            }
            return this;
        }

        public String getProperty(String str) {
            if (str == null || !this.mHitMap.containsKey(str)) {
                return null;
            }
            return this.mHitMap.get(str);
        }

        public Map<String, String> build() {
            Map<String, String> map = this.mHitMap;
            map.put(PrivateLogFields.FLAG_BUILD_MAP_BY_UT, "yes");
            if (!_checkIlleagleProperty(map)) {
                return null;
            }
            _dropAllIllegalFields(map);
            _translateFieldsName(map);
            if (!map.containsKey(LogField.EVENTID.toString())) {
                return null;
            }
            return map;
        }

        private static boolean _checkIlleagleProperty(Map<String, String> map) {
            if (map != null) {
                if (map.containsKey(null)) {
                    map.remove(null);
                }
                if (map.containsKey("")) {
                    map.remove("");
                }
                if (map.containsKey(LogField.PAGE.toString())) {
                    Logger.e((String) "checkIlleagleProperty", "IlleaglePropertyKey(PAGE) is setted when you call the method setProperty or setProperties ,please use another key to replace it!");
                    return false;
                } else if (map.containsKey(LogField.EVENTID.toString())) {
                    Logger.e((String) "checkIlleagleProperty", "IlleaglePropertyKey(EVENTID) is setted when you call the method setProperty or setProperties ,please use another key to replace it!");
                    return false;
                } else if (map.containsKey(LogField.ARG1.toString())) {
                    Logger.e((String) "checkIlleagleProperty", "IlleaglePropertyKey(ARG1) is setted when you call the method setProperty or setProperties ,please use another key to replace it!");
                    return false;
                } else if (map.containsKey(LogField.ARG2.toString())) {
                    Logger.e((String) "checkIlleagleProperty", "IlleaglePropertyKey(ARG2) is setted when you call the method setProperty or setProperties ,please use another key to replace it!");
                    return false;
                } else if (map.containsKey(LogField.ARG3.toString())) {
                    Logger.e((String) "checkIlleagleProperty", "IlleaglePropertyKey(ARG3) is setted when you call the method setProperty or setProperties ,please use another key to replace it!");
                    return false;
                }
            }
            return true;
        }

        private static void _translateFieldsName(Map<String, String> map) {
            if (map != null) {
                String remove = map.remove(FIELD_PAGE);
                if (remove != null) {
                    map.put(LogField.PAGE.toString(), remove);
                }
                String remove2 = map.remove(FIELD_ARG1);
                if (remove2 != null) {
                    map.put(LogField.ARG1.toString(), remove2);
                }
                String remove3 = map.remove(FIELD_ARG2);
                if (remove3 != null) {
                    map.put(LogField.ARG2.toString(), remove3);
                }
                String remove4 = map.remove(FIELD_ARG3);
                if (remove4 != null) {
                    map.put(LogField.ARG3.toString(), remove4);
                }
                String remove5 = map.remove(FIELD_ARGS);
                if (remove5 != null) {
                    map.put(LogField.ARGS.toString(), remove5);
                }
                String remove6 = map.remove(FIELD_EVENT_ID);
                if (remove6 != null) {
                    map.put(LogField.EVENTID.toString(), remove6);
                }
            }
        }

        private static void _dropAllIllegalFields(Map<String, String> map) {
            if (map != null) {
                map.remove(LogField.PAGE.toString());
                map.remove(LogField.EVENTID.toString());
                map.remove(LogField.ARG1.toString());
                map.remove(LogField.ARG2.toString());
                map.remove(LogField.ARG3.toString());
                map.remove(LogField.ARGS.toString());
            }
        }
    }

    public static class UTPageHitBuilder extends UTHitBuilder {
        public UTPageHitBuilder(String str) {
            if (!StringUtils.isEmpty(str)) {
                super.setProperty(UTHitBuilder.FIELD_PAGE, str);
            }
            super.setProperty(UTHitBuilder.FIELD_EVENT_ID, "2001");
            super.setProperty(UTHitBuilder.FIELD_ARG3, "0");
        }

        public UTPageHitBuilder setReferPage(String str) {
            if (!StringUtils.isEmpty(str)) {
                super.setProperty(UTHitBuilder.FIELD_ARG1, str);
            }
            return this;
        }

        public UTPageHitBuilder setDurationOnPage(long j) {
            if (j < 0) {
                j = 0;
            }
            super.setProperty(UTHitBuilder.FIELD_ARG3, String.valueOf(j));
            return this;
        }
    }
}
