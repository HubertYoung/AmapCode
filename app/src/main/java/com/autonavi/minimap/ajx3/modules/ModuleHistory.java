package com.autonavi.minimap.ajx3.modules;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.AjxPathLoader;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.scheme.Ajx3SchemeHelper;
import com.autonavi.minimap.ajx3.util.OpenThirdAppUtil;
import com.autonavi.minimap.ajx3.util.PathUtils;
import com.autonavi.minimap.ajx3.util.SchemeUtils;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONObject;

@AjxModule("ajx.history")
public class ModuleHistory extends AbstractModule {
    public static final String AJX_BACK_RETURN_DATA_KEY = "returnData";
    public static final String MODULE_NAME = "ajx.history";

    public ModuleHistory(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("backTo")
    public void backTo(String[] strArr, String str) {
        if (strArr != null && strArr.length > 0) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                ajw ajw = (ajw) ank.a(ajw.class);
                if (ajw != null) {
                    updateSchemePath2Ajx(strArr);
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject(AJX_BACK_RETURN_DATA_KEY, str);
                    ajw.a(Arrays.asList(strArr), pageContext, ResultType.OK, pageBundle);
                }
            }
        }
    }

    @AjxMethod("backBefore")
    public void backBefore(String[] strArr, String str) {
        if (strArr != null && strArr.length > 0) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                ajw ajw = (ajw) ank.a(ajw.class);
                if (ajw != null) {
                    updateSchemePath2Ajx(strArr);
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject(AJX_BACK_RETURN_DATA_KEY, str);
                    try {
                        pageBundle.putObject("data", new JSONObject(str));
                    } catch (Exception unused) {
                    }
                    ajw.b(Arrays.asList(strArr), pageContext, ResultType.OK, pageBundle);
                }
            }
        }
    }

    @AjxMethod("backToAndStart")
    public void backToAndStart(String[] strArr, String str, String str2) {
        if (strArr != null && strArr.length > 0) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                ajw ajw = (ajw) ank.a(ajw.class);
                if (ajw != null) {
                    updateSchemePath2Ajx(strArr);
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject(AJX_BACK_RETURN_DATA_KEY, str);
                    ajw.a(Arrays.asList(strArr), pageContext, ResultType.OK, pageBundle, str2);
                }
            }
        }
    }

    @AjxMethod("backBeforeAndStart")
    public void backBeforeAndStart(String[] strArr, String str, String str2) {
        if (strArr != null && strArr.length > 0) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                ajw ajw = (ajw) ank.a(ajw.class);
                if (ajw != null) {
                    updateSchemePath2Ajx(strArr);
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject(AJX_BACK_RETURN_DATA_KEY, str);
                    ajw.b(Arrays.asList(strArr), pageContext, ResultType.OK, pageBundle, str2);
                }
            }
        }
    }

    @AjxMethod("open")
    public void open(String str, Object obj, String str2, String str3, Object obj2) {
        if (!TextUtils.isEmpty(str)) {
            String joinPath = joinPath(str);
            String joinPath2 = joinPath(str3);
            ku a = ku.a();
            StringBuilder sb = new StringBuilder("onOpen url:");
            sb.append(joinPath);
            sb.append("\ndata:");
            sb.append(obj);
            a.c("auiLog", sb.toString());
            if (joinPath.startsWith("amapuri") || joinPath.startsWith("androidamap")) {
                onCustomActon(joinPath, obj);
            } else if (joinPath.startsWith("appurl")) {
                OpenThirdAppUtil.openThirdApp((String) obj);
            } else {
                startPage(joinPath, joinPath2, obj, str2);
            }
        }
    }

    private void onCustomActon(@NonNull String str, @Nullable Object obj) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(str));
        intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
        if (obj != null) {
            intent.putExtra("ajxData", String.valueOf(obj));
        }
        DoNotUseTool.startScheme(intent);
    }

    private void startPage(String str, String str2, Object obj, String str3) {
        bid pageContext = AMapPageUtil.getPageContext();
        AjxView rootView = getContext().getDomTree().getRootView();
        if (pageContext != null && (rootView instanceof AmapAjxView)) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", str);
            pageBundle.putObject("jsData", obj);
            pageBundle.putString("pageId", str3);
            pageBundle.putString("env", str2);
            pageBundle.putObject("resultExecutor", ((AmapAjxView) rootView).getAjxFragmentResultExecutor());
            pageContext.startPageForResult(Ajx3Page.class, pageBundle, 99);
        }
    }

    @AjxMethod("replace")
    public void replace(String str, Object obj, String str2, String str3, Object obj2) {
        Object obj3;
        if (!TextUtils.isEmpty(str)) {
            AmapAjxView amapAjxView = (AmapAjxView) getContext().getDomTree().getRootView();
            if (amapAjxView != null) {
                String joinPath = joinPath(str);
                amapAjxView.setEnvironment(joinPath(str3));
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    amapAjxView.pageHide(false);
                    ku a = ku.a();
                    StringBuilder sb = new StringBuilder("onReplace url:");
                    sb.append(joinPath);
                    sb.append("\ndata:");
                    sb.append(obj);
                    a.c("auiLog", sb.toString());
                    if (joinPath.startsWith("amapuri") || joinPath.startsWith("androidamap")) {
                        pageContext.finish();
                        onCustomActon(joinPath, obj);
                    } else if (joinPath.startsWith("appurl")) {
                        pageContext.finish();
                        OpenThirdAppUtil.openThirdApp((String) obj);
                    } else {
                        if (obj == null) {
                            obj3 = null;
                        } else {
                            obj3 = obj.toString();
                        }
                        amapAjxView.load(joinPath, obj3, str2);
                        amapAjxView.pageShow(false, null);
                    }
                }
            }
        }
    }

    @AjxMethod("back")
    public void back(Object obj, String str, Object obj2) {
        AjxView rootView = getContext().getDomTree().getRootView();
        if (rootView != null) {
            rootView.onBack(obj, str);
        }
    }

    private String joinPath(String str) {
        String preProcessUrl = PathUtils.preProcessUrl(str);
        String scheme = Uri.parse(preProcessUrl).getScheme();
        String jsPath = getContext().getJsPath();
        return (!TextUtils.isEmpty(scheme) || TextUtils.isEmpty(jsPath)) ? preProcessUrl : PathUtils.processPath(jsPath.substring(0, jsPath.lastIndexOf("/") + 1), preProcessUrl);
    }

    @AjxMethod("popAllWithBundleName")
    public void popAllWithBundleName(String str, String str2) {
        ArrayList<akc> pagesStacks = AMapPageUtil.getPagesStacks();
        if (pagesStacks != null && !pagesStacks.isEmpty()) {
            for (int i = 0; i < pagesStacks.size(); i++) {
                bid stackFragment = AMapPageUtil.getStackFragment(i);
                if (stackFragment != null && (stackFragment instanceof Ajx3Page) && AjxFileInfo.getBundleName(((Ajx3Page) stackFragment).getAjx3Url()).equals(str)) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject(AJX_BACK_RETURN_DATA_KEY, str2);
                    try {
                        pageBundle.putObject("data", new JSONObject(str2));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stackFragment.setResult(ResultType.OK, pageBundle);
                    stackFragment.finish();
                }
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "pageExist")
    public boolean pageExist(String str) {
        if (TextUtils.isEmpty(str) || !str.startsWith(AjxPathLoader.DOMAIN)) {
            return false;
        }
        PictureParams pictureParams = new PictureParams();
        pictureParams.url = str;
        return AjxFileInfo.isFileExists(Ajx.getInstance().lookupLoader(str).processingPath(pictureParams));
    }

    private String getJsLocalPath(String str) {
        int indexOf = str.indexOf("?");
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        return SchemeUtils.isLocalSupport(str);
    }

    private String updateSchemePath(String str) {
        String jsLocalPath = getJsLocalPath(str);
        if (TextUtils.isEmpty(jsLocalPath)) {
            return str;
        }
        Uri addQueryParam = Ajx3SchemeHelper.addQueryParam(Uri.parse(str), "path", jsLocalPath);
        if (addQueryParam == null) {
            return str;
        }
        String uri = addQueryParam.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("amapuri://ajx");
        sb.append(uri.substring(uri.indexOf("?")));
        return sb.toString();
    }

    private void updateSchemePath2Ajx(String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = updateSchemePath(strArr[i]);
        }
    }

    @AjxMethod("openWebview")
    public void openWebview(String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            aja aja = new aja(str);
            aja.b = new ajf();
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a(pageContext, aja);
            }
        }
    }
}
