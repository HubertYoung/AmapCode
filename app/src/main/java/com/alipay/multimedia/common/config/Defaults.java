package com.alipay.multimedia.common.config;

import com.alipay.multimedia.common.config.item.PlayerConf;
import com.alipay.multimedia.common.config.parser.PlayerConfParser;
import java.util.HashMap;
import java.util.Map;

public class Defaults {
    public static Map<String, IConfig> getDefaultConfigs() {
        Map defaultConfigs = new HashMap();
        setupDefaultConfigs(defaultConfigs);
        return defaultConfigs;
    }

    private static void setupDefaultConfigs(Map<String, IConfig> configMap) {
        configMap.put(ConfigConstants.KEY_PLAYER, new PlayerConf());
    }

    public static Map<String, IParser> getDefaultParsers() {
        Map defaultParsers = new HashMap();
        setupDefaultParsers(defaultParsers);
        return defaultParsers;
    }

    private static void setupDefaultParsers(Map<String, IParser> parserMap) {
        parserMap.put(ConfigConstants.KEY_PLAYER, new PlayerConfParser());
    }
}
