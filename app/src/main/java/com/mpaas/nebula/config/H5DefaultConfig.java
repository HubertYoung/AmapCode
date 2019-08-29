package com.mpaas.nebula.config;

import com.alipay.mobile.nebula.util.H5Utils;
import java.util.HashMap;
import java.util.Map;

public class H5DefaultConfig {
    public static final Map<String, String> mSwitchMap = new HashMap<String, String>() {
        {
            put("h5_bizServiceConfig", "{\"deduct\":{\"appId\":\"60000157\",\"level\":\"level_low\"},\"zm-borrow\":{\"appId\":\"66666684\",\"level\":\"level_low\"},\"coupon-detail\":{\"appId\":\"66666746\",\"level\":\"level_low\"},\"ar-valentine-cn\":{\"appId\":\"66666760\",\"level\":\"level_low\"},\"ar-service\":{\"appId\":\"66666800\",\"level\":\"level_low\"},\"zm-service\":{\"appId\":\"66666808\",\"level\":\"level_low\"},\"add-card\":{\"appId\":\"66666896\",\"level\":\"level_low\"},\"shopping-address\":{\"appId\":\"20000714\",\"level\":\"level_none\"},\"credit-rent\":{\"appId\":\"68687032\",\"level\":\"level_low\"},\"rent-transition\":{\"appId\":\"68687032\",\"level\":\"level_low\"},\"invoice-title\":{\"appId\":\"68687039\",\"level\":\"level_none\"},\"zmep-freedeposit\":{\"appId\":\"68687044\",\"level\":\"level_low\"}}");
            put("webar_url_white_list", "[\"^http(s)?://.*\"]");
            put("h5_shouldverifyapp", "NO");
            put("h5_amrUnzipSecCheck", "NO");
            put("h5_disableUcAR", "yes");
            put("h5_mainProcKeepAlive", "NO");
            put("amap_mini_app_jsapi_whitelist", "[\"startAPVerify\", \"sendMtop\", \"getTBCode\", \"getTBSessionInfo\", \"setTBSessionInfo\", \"requestTemplateData\"]");
            put("start_app_whitelist", "{\"2018102561769634\": [\"2017103009621199\"]}");
            put("prefetch_config", "{\"2017103009621199\": [\"2018102561769634\"]}");
            put(H5Utils.KEY_H5_WEBVIEW_CONFIG, "{\"h5_enableExternalWebView\":\"YES\",\"h5_externalWebViewUsageRule\":{},\"h5_externalWebViewSdkVersion\":{\"min\":11,\"max\":99},\"h5_externalWebView4UC\":[],\"h5_externalWebView4CPU\":2}");
            put("h5_logNebulaTechEnable", "yes");
            put("h5_logNewBlankScreenConfig", "{\"enable\":\"YES\",\"wifiLimit\":10,\"elseNetWork\":30,\"testFilter\":6,\"appId\":\".*\",\"script\":\"try{(function(){if(location.href.indexOf('#')<=0){return{'isDSLError':false,'detail':''};}var isDSLError=false;if(document.getElementsByClassName('tiny-page').length==0||document.getElementsByClassName('tiny-page')[0].childNodes.length==0){isDSLError=true;}if(!isDSLError){isDSLError=true;var childNodes=document.getElementsByClassName('tiny-page')[0].childNodes;for(var i=0;i<childNodes.length;i++){if(childNodes[i].childNodes.length>0){isDSLError=false;break;}}}return isDSLError?{'isDSLError':true,'detail':''}:{'isDSLError':false,'detail':''};})()}catch(err){};\"}");
            put(H5Utils.KEY_H5_FORCE_UC, "{\"globalFlag\":true}");
        }
    };
}
