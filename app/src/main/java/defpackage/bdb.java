package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.setting.page.AddNaviShortcutPage;
import com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage;
import com.autonavi.mine.page.setting.sysmapset.page.SysMapSettingPage;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.bundle.setting.api.ISettingService;

@BundleInterface(ISettingService.class)
/* renamed from: bdb reason: default package */
/* compiled from: SettingService */
public class bdb extends esi implements ISettingService {
    public final void a() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(AmapSettingPage.class, (PageBundle) null);
        }
    }

    public final void b() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", Ajx3Path.SETTING_FONT_SIZE_PATH);
            pageContext.startPage(Ajx3Page.class, pageBundle);
        }
    }

    public final void c() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(SysMapSettingPage.class, (PageBundle) null);
        }
    }

    public final void a(bid bid, PageBundle pageBundle) {
        if (bid != null) {
            bid.startPage(AddNaviShortcutPage.class, pageBundle);
        }
    }
}
