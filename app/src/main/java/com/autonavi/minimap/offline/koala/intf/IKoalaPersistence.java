package com.autonavi.minimap.offline.koala.intf;

import com.autonavi.minimap.offline.koala.model.KoalaDownloadModel;

public interface IKoalaPersistence {
    KoalaDownloadModel load(String str);

    void save(KoalaDownloadModel koalaDownloadModel);
}
