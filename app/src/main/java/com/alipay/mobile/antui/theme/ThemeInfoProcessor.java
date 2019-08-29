package com.alipay.mobile.antui.theme;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.antui.excutor.AntUIExecutorManager;
import com.alipay.mobile.antui.excutor.ConfigExecutor;
import com.alipay.mobile.antui.excutor.FileLoadRequest;
import com.alipay.mobile.antui.theme.model.AUThemeModel;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.text.SimpleDateFormat;

public class ThemeInfoProcessor {
    public static void getConfig(String bundle, String code_key, ThemeCallback themeCallback) {
        ConfigExecutor configExecutor = AntUIExecutorManager.getInstance().getConfigExecutor();
        if (configExecutor != null) {
            configExecutor.getConfig(code_key, new a(bundle, themeCallback));
        }
    }

    public static void dealConfig(String bundle, String config, ThemeCallback themeCallback) {
        try {
            AUThemeModel model = JSONArray.parseArray(config, AUThemeModel.class).get(0);
            long currentTime = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            model.startTime = sdf.parse(model.start_time).getTime();
            model.endTime = sdf.parse(model.end_time).getTime();
            String url = model.theme_url;
            if (model.startTime < currentTime && currentTime < model.endTime && !TextUtils.isEmpty(url)) {
                FileLoadRequest request = new FileLoadRequest();
                request.docPath = "theme";
                request.fileName = url;
                request.zipName = url;
                request.fileId = url;
                AntUIExecutorManager.getInstance().getFileLoadExecutor().download(request, new b(bundle, model, themeCallback));
            }
        } catch (Exception e) {
            AuiLogger.error("ThemeInfoProcessor", "Exception error : " + e);
        }
    }
}
