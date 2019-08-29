package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.life.db.CinemaRecordDao;
import com.autonavi.minimap.life.db.CouponDao;
import com.autonavi.minimap.life.db.HotelBrowseHistoryRecordDao;
import com.autonavi.minimap.life.db.NearbyIconDao;
import com.autonavi.minimap.life.db.QuickSearchMoreDataDao;
import com.autonavi.minimap.life.db.ShortcutDao;
import com.autonavi.minimap.life.db.WeekendHappyCacheDao;
import com.autonavi.minimap.life.db.WeekendHappyFavouriteDao;
import com.autonavi.minimap.life.db.model.Shortcut;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: dot reason: default package */
/* compiled from: LifeDaoSession */
public class dot implements xx {
    private DaoConfig a;
    private CinemaRecordDao b;
    private DaoConfig c;
    private CouponDao d;
    private DaoConfig e;
    private HotelBrowseHistoryRecordDao f;
    private DaoConfig g;
    private ShortcutDao h;
    private DaoConfig i;
    private WeekendHappyCacheDao j;
    private DaoConfig k;
    private WeekendHappyFavouriteDao l;
    private DaoConfig m;
    private NearbyIconDao n;
    private DaoConfig o;
    private QuickSearchMoreDataDao p;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(CinemaRecordDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new CinemaRecordDao(this.a, xtVar);
        this.c = map.get(CouponDao.class).clone();
        this.c.initIdentityScope(identityScopeType);
        this.d = new CouponDao(this.c, xtVar);
        this.e = map.get(HotelBrowseHistoryRecordDao.class).clone();
        this.e.initIdentityScope(identityScopeType);
        this.f = new HotelBrowseHistoryRecordDao(this.e, xtVar);
        this.g = map.get(ShortcutDao.class).clone();
        this.g.initIdentityScope(identityScopeType);
        this.h = new ShortcutDao(this.g, xtVar);
        this.i = map.get(WeekendHappyCacheDao.class).clone();
        this.i.initIdentityScope(identityScopeType);
        this.j = new WeekendHappyCacheDao(this.i, xtVar);
        this.k = map.get(WeekendHappyFavouriteDao.class).clone();
        this.k.initIdentityScope(identityScopeType);
        this.l = new WeekendHappyFavouriteDao(this.k, xtVar);
        this.m = map.get(NearbyIconDao.class).clone();
        this.m.initIdentityScope(identityScopeType);
        this.n = new NearbyIconDao(this.m, xtVar);
        this.o = map.get(QuickSearchMoreDataDao.class).clone();
        this.o.initIdentityScope(identityScopeType);
        this.p = new QuickSearchMoreDataDao(this.o, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(dov.class, this.b);
        hashMap.put(avx.class, this.d);
        hashMap.put(dow.class, this.f);
        hashMap.put(Shortcut.class, this.h);
        hashMap.put(doz.class, this.j);
        hashMap.put(dpa.class, this.l);
        hashMap.put(dox.class, this.n);
        hashMap.put(doy.class, this.p);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(CinemaRecordDao.class)) {
            return this.b;
        }
        if (cls.equals(CouponDao.class)) {
            return this.d;
        }
        if (cls.equals(HotelBrowseHistoryRecordDao.class)) {
            return this.f;
        }
        if (cls.equals(ShortcutDao.class)) {
            return this.h;
        }
        if (cls.equals(WeekendHappyCacheDao.class)) {
            return this.j;
        }
        if (cls.equals(WeekendHappyFavouriteDao.class)) {
            return this.l;
        }
        if (cls.equals(NearbyIconDao.class)) {
            return this.n;
        }
        if (cls.equals(QuickSearchMoreDataDao.class)) {
            return this.p;
        }
        return null;
    }
}
