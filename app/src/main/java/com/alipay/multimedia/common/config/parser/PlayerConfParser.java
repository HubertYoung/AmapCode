package com.alipay.multimedia.common.config.parser;

import com.alibaba.fastjson.JSON;
import com.alipay.multimedia.common.config.IParser;
import com.alipay.multimedia.common.config.item.PlayerConf;

public class PlayerConfParser implements IParser<PlayerConf> {
    public PlayerConf parse(String configVal) {
        return (PlayerConf) JSON.parseObject(configVal, PlayerConf.class);
    }
}
