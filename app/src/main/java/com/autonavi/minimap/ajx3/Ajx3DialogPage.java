package com.autonavi.minimap.ajx3;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.ajx3.Ajx3Page.AjxPageResultExecutor;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.modules.ModuleJsBridge;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AjxLifeCircleListener;
import java.util.ArrayList;
import org.json.JSONObject;

public class Ajx3DialogPage extends AbstractBasePage implements Transparent, AjxLifeCircleListener {
    public static final String PAGE_DATA = "jsData";
    public static final String PAGE_ENV = "env";
    public static final String PAGE_ID = "pageId";
    public static final String PAGE_RESULT_EXECUTOR = "resultExecutor";
    public static final String PAGE_URL = "url";
    protected AjxPageStateInvoker ajxPageStateInvoker;
    private AjxPageResultExecutor mAjxPageResultExecutor;
    protected AmapAjxView mAjxView;
    private Object mData = null;
    private String mEnv = null;
    private String mPageId;
    protected String mUrl = null;

    public boolean isSetSoftInput() {
        return false;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        handlePageBundle();
        setContentView(prepareView());
        loadJs();
    }

    public IPresenter createPresenter() {
        return new Ajx3DialogPagePresenter(this);
    }

    /* access modifiers changed from: protected */
    public void resume() {
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.onResume();
        }
    }

    /* access modifiers changed from: protected */
    public void stop() {
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.onStop();
        }
    }

    /* access modifiers changed from: protected */
    public void destroy() {
        if (this.mAjxView != null) {
            this.mAjxView.onDestroy();
            this.mAjxView = null;
        }
        if (this.mAjxPageResultExecutor != null) {
            this.mAjxPageResultExecutor = null;
        }
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.onDestroy();
        }
    }

    /* access modifiers changed from: protected */
    public boolean backPressed() {
        if (this.mAjxView != null) {
            return this.mAjxView.backPressed();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void result(int i, ResultType resultType, PageBundle pageBundle) {
        Object obj;
        new StringBuilder("result method start ").append(this.ajxPageStateInvoker.getResumeData());
        if (pageBundle == null || resultType != ResultType.OK) {
            obj = null;
        } else {
            obj = pageBundle.get(ModuleHistory.AJX_BACK_RETURN_DATA_KEY);
            new StringBuilder("get mResumeData ").append(obj);
        }
        this.ajxPageStateInvoker.setResumeData(obj);
        if (this.mAjxPageResultExecutor != null && this.mAjxView.getAjxContext() != null) {
            this.mAjxPageResultExecutor.onFragmentResult(this, i, resultType, pageBundle, ((ModuleJsBridge) this.mAjxView.getJsModule("js")).getJsMethod());
        }
    }

    private void handlePageBundle() {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString("url");
            if (!TextUtils.isEmpty(string)) {
                this.mUrl = string;
            }
            this.mAjxPageResultExecutor = (AjxPageResultExecutor) arguments.get("resultExecutor");
            this.mData = arguments.getObject("jsData");
            this.mPageId = arguments.getString("pageId");
            this.mEnv = arguments.getString("env");
        }
    }

    private View prepareView() {
        this.mAjxView = new AmapAjxView(getContext());
        this.mAjxView.setAjxLifeCircleListener(this);
        this.ajxPageStateInvoker = new AjxPageStateInvoker(this, this.mAjxView);
        return this.mAjxView;
    }

    /* access modifiers changed from: protected */
    public void loadJs() {
        int[] fullScreenSize = Ajx3Page.getFullScreenSize(getActivity());
        this.mAjxView.load(this.mUrl, this.mData, this.mPageId, getClass().getSimpleName(), fullScreenSize[0], fullScreenSize[1], this.mEnv);
    }

    @Nullable
    public String getAjx3Url() {
        if (this.mAjxView != null) {
            return this.mAjxView.getUrl();
        }
        return null;
    }

    public void onJsBack(Object obj, String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, obj);
        try {
            pageBundle.putObject("data", new JSONObject(obj.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(str)) {
            ajw ajw = (ajw) ank.a(ajw.class);
            if (!(Uri.parse(str).getScheme() != null)) {
                ArrayList arrayList = new ArrayList();
                StringBuilder sb = new StringBuilder("amapuri://ajx?path=");
                sb.append(Uri.encode(str));
                arrayList.add(sb.toString());
                ajw.a(arrayList, this, ResultType.OK, pageBundle);
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(str);
            ajw.a(arrayList2, this, ResultType.OK, pageBundle);
            return;
        }
        setResult(ResultType.OK, pageBundle);
        finish();
    }
}
