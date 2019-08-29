package com.alipay.streammedia.encode.utils;

import android.os.Build;
import android.os.Build.VERSION;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.alipay.streammedia.encode.exceptions.OMXConfigException;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Iterator;
import java.util.Map;

public class OMXConfigUtil {

    interface BuildVersionHandler {
        boolean handleRange(String str, String str2);

        boolean handleSingle(String str);
    }

    public static OMXConfig parse(String localConfig, String remoteConfig) {
        return parse(Build.MANUFACTURER, Build.MODEL, VERSION.SDK_INT, Build.DISPLAY, localConfig, remoteConfig);
    }

    public static OMXConfig parse(String product, String model, int osVersion, String buildVersion, String localConfig, String remoteConfig) {
        if (isEmpty(product) || isEmpty(model) || isEmpty(localConfig)) {
            throw new OMXConfigException("Invalid argument, product, model and localConfig should not be empty. product:" + product + ", model:" + model);
        }
        String product2 = product.toLowerCase();
        String model2 = model.toLowerCase();
        String os = Integer.toString(osVersion);
        String buildVersion2 = buildVersion != null ? buildVersion.toLowerCase() : null;
        return parseRemote(product2, model2, os, buildVersion2, remoteConfig, parseLocal(product2, model2, os, buildVersion2, localConfig));
    }

    private static OMXConfig parseLocal(String product, String model, String os, String buildVersion, String config) {
        OMXConfig res = new OMXConfig();
        res.support = false;
        Map maps = parseConfigMap(config);
        if (maps != null && !maps.isEmpty()) {
            String configValue = getConfigValue(maps, product, model, os);
            if (!isEmpty(configValue)) {
                parseConfigValue(product, buildVersion, configValue, res);
            }
        }
        return res;
    }

    private static OMXConfig parseRemote(String product, String model, String os, String buildVersion, String config, OMXConfig local) {
        OMXConfig res = new OMXConfig();
        res.support = false;
        Map maps = parseConfigMap(config);
        if (maps == null || maps.isEmpty()) {
            return local;
        }
        String configValue = getConfigValue(maps, product, model, os);
        if (!isEmpty(configValue)) {
            parseConfigValue(product, buildVersion, configValue, res);
            return res;
        } else if (!local.support) {
            return res;
        } else {
            String configValue2 = getConfigValue(maps, product, model, "*");
            if (!isEmpty(configValue2)) {
                parseConfigValue(product, buildVersion, configValue2, res);
                return res;
            }
            String configValue3 = getConfigValue(maps, product, "*", os);
            if (!isEmpty(configValue3)) {
                parseConfigValue(product, buildVersion, configValue3, res);
                return res;
            }
            String configValue4 = getConfigValue(maps, product, "*", "*");
            if (isEmpty(configValue4)) {
                return local;
            }
            parseConfigValue(product, buildVersion, configValue4, res);
            return res;
        }
    }

    private static Map<String, Map<String, Map<String, String>>> parseConfigMap(String config) {
        if (isEmpty(config)) {
            return null;
        }
        try {
            return (Map) JSON.parseObject(config.toLowerCase(), (TypeReference<T>) new TypeReference<Map<String, Map<String, Map<String, String>>>>() {
            }, new Feature[0]);
        } catch (Exception e) {
            throw new OMXConfigException("fail to parse config", e);
        }
    }

    private static String getConfigValue(Map<String, Map<String, Map<String, String>>> maps, String product, String model, String os) {
        Map models = maps.get(product);
        if (models == null) {
            return null;
        }
        Map versions = (Map) models.get(model);
        if (versions == null) {
            Iterator it = models.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String m = (String) it.next();
                if (m.length() > 1) {
                    String ptn = m.replaceAll("\\*", "");
                    if (!m.startsWith("*") || !m.endsWith("*")) {
                        if (!m.startsWith("*")) {
                            if (m.endsWith("*") && model.startsWith(ptn)) {
                                versions = (Map) models.get(m);
                                break;
                            }
                        } else if (model.endsWith(ptn)) {
                            versions = (Map) models.get(m);
                            break;
                        }
                    } else if (model.contains(ptn)) {
                        versions = (Map) models.get(m);
                        break;
                    }
                }
            }
        }
        if (versions != null) {
            return (String) versions.get(os);
        }
        return null;
    }

    private static void parseConfigValue(String product, String buildVersion, String value, OMXConfig config) {
        String[] ts = value.split("\\|");
        if (ts.length > 0) {
            parseRateControl(ts[0], config);
            if (config.support) {
                if (ts.length >= 2 && !isEmpty(ts[1]) && !isEmpty(buildVersion)) {
                    if (DeviceProperty.ALIAS_HUAWEI.equals(product)) {
                        parseHuaweiBuildVersion(buildVersion, ts[1], config);
                    } else if (DeviceProperty.ALIAS_OPPO.equals(product)) {
                        parseOppoBuildVersion(buildVersion, ts[1], config);
                    }
                }
                if (ts.length >= 3 && !isEmpty(ts[2])) {
                    parseResolution(ts[2], config);
                }
                if (ts.length >= 4 && !isEmpty(ts[3])) {
                    parseFlags(ts[3], config);
                }
            }
        }
    }

    private static void parseRateControl(String value, OMXConfig config) {
        try {
            int rc = Integer.parseInt(value);
            config.bitrate = rc * 100 * 1024;
            if (rc > 0) {
                config.support = true;
            } else {
                config.support = false;
            }
        } catch (Exception e) {
            config.support = false;
        }
    }

    private static void parseHuaweiBuildVersion(String buildVersion, String value, OMXConfig config) {
        if (buildVersion.length() < 3) {
            config.support = false;
            return;
        }
        final String bv = buildVersion.substring(buildVersion.length() - 3);
        config.support = parseBuildVersion(value, new BuildVersionHandler() {
            public final boolean handleRange(String start, String end) {
                if (bv.compareTo(start) < 0) {
                    return false;
                }
                if (OMXConfigUtil.isEmpty(end) || bv.compareTo(end) <= 0) {
                    return true;
                }
                return false;
            }

            public final boolean handleSingle(String single) {
                return bv.equals(single);
            }
        });
    }

    private static void parseOppoBuildVersion(String buildVersion, String value, OMXConfig config) {
        if (buildVersion.length() < 6) {
            config.support = false;
            return;
        }
        final String bv = buildVersion.substring(buildVersion.length() - 6);
        config.support = parseBuildVersion(value, new BuildVersionHandler() {
            public final boolean handleRange(String start, String end) {
                if (bv.compareTo(start) < 0) {
                    return false;
                }
                if (OMXConfigUtil.isEmpty(end) || bv.compareTo(end) <= 0) {
                    return true;
                }
                return false;
            }

            public final boolean handleSingle(String single) {
                return bv.equals(single);
            }
        });
    }

    private static boolean parseBuildVersion(String value, BuildVersionHandler handler) {
        String[] ts = value.split(",");
        for (String t : ts) {
            if (!isEmpty(t)) {
                if (t.indexOf(Constants.WAVE_SEPARATOR) > 0) {
                    String[] range = t.split(Constants.WAVE_SEPARATOR);
                    if (ts.length != 0 && !isEmpty(ts[0])) {
                        if (handler.handleRange(range[0].trim(), range.length >= 2 ? range[1].trim() : null)) {
                            return true;
                        }
                    }
                } else if (handler.handleSingle(t)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void parseResolution(String value, OMXConfig config) {
        String[] ts = value.split(",");
        try {
            int width = Integer.parseInt(ts[0]);
            int height = Integer.parseInt(ts[1]);
            if (width > 0 && height > 0) {
                config.width = width;
                config.height = height;
            }
        } catch (Exception e) {
        }
    }

    private static void parseFlags(String value, OMXConfig config) {
        try {
            config.flags = Integer.parseInt(value);
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: private */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
