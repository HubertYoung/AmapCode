package com.autonavi.minimap.offline.koala.intf;

import com.autonavi.minimap.offline.koala.model.KoalaDownloadProfile;

public interface IKoalaCustomAction {
    Object execute(KoalaDownloadProfile koalaDownloadProfile) throws Exception;
}
