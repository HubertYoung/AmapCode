package com.autonavi.minimap.ajx3;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.ajx3.modules.ModuleAMap;
import com.autonavi.minimap.ajx3.modules.ModuleAmapApp;
import com.autonavi.minimap.ajx3.modules.ModuleAmapCanvas;
import com.autonavi.minimap.ajx3.modules.ModuleAmapLoading;
import com.autonavi.minimap.ajx3.modules.ModuleAmapLocalStorage;
import com.autonavi.minimap.ajx3.modules.ModuleAmapUT;
import com.autonavi.minimap.ajx3.modules.ModuleCommonUtils;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.modules.ModuleImage;
import com.autonavi.minimap.ajx3.modules.ModuleJsBridge;
import com.autonavi.minimap.ajx3.modules.ModuleLocation;
import com.autonavi.minimap.ajx3.modules.ModuleLog;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import com.autonavi.minimap.ajx3.modules.ModuleMap;
import com.autonavi.minimap.ajx3.modules.ModuleNavi;
import com.autonavi.minimap.ajx3.modules.ModuleNetwork;
import com.autonavi.minimap.ajx3.modules.ModulePhoto;
import com.autonavi.minimap.ajx3.modules.ModuleSchemeLoader;
import com.autonavi.minimap.ajx3.modules.ModuleSchemeTest;
import com.autonavi.minimap.ajx3.modules.ModuleSocket;
import com.autonavi.minimap.ajx3.modules.ModuleWebLoader;
import com.autonavi.minimap.ajx3.modules.net.ModuleRequest;
import com.autonavi.minimap.ajx3.modules.os.ModuleAmapNotification;
import com.autonavi.minimap.ajx3.modules.os.ModuleAmapOS;
import com.autonavi.minimap.ajx3.modules.os.ModuleAmapScreen;
import com.autonavi.minimap.ajx3.modules.os.ModuleAmapShortcut;

@MultipleImpl(ema.class)
public class AjxRegister implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleLongLinkService.class, ModuleSchemeTest.class, ModuleHistory.class, ModuleRequest.class, ModuleJsBridge.class, ModulePhoto.class, ModuleLocation.class, ModuleNetwork.class, ModuleLog.class, ModuleMap.class, ModuleAMap.class, ModuleAmapApp.class, ModuleImage.class, ModuleAMap.class, ModuleSocket.class, ModuleCommonUtils.class, ModuleAmapLoading.class, ModuleWebLoader.class, ModuleSchemeLoader.class, ModuleAmapCanvas.class, ModuleNavi.class, ModuleAmapOS.class, ModuleAmapUT.class, ModuleAmapLocalStorage.class, ModuleAmapNotification.class, ModuleAmapShortcut.class, ModuleAmapScreen.class);
    }
}
