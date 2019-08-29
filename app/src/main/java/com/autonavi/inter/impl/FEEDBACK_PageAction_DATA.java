package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.bundle.feedback.contribution.page.ContributionSearchPage;
import com.autonavi.mine.feedback.fragment.DoorAddressUploadPage;
import com.autonavi.mine.feedback.fragment.FeedbackMainPage;
import com.autonavi.mine.feedback.navi.ReportErrorListPage;
import com.autonavi.mine.feedback.navi.ReportErrorPicFullScreenPage;
import com.autonavi.mine.feedbackv2.drivenavigationissues.DriveNavigationIssuesListPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.feedback_report_error_list_page", "amap.basemap.action.feedback_door_address_upload_page", "amap.basemap.action.drive_navigation_issue", "amap.basemap.action.contribution_search_page", "amap.basemap.action.help_and_feedback_page", "amap.basemap.action.feedback_report_pic_full_screen_page"}, module = "feedback", pages = {"com.autonavi.mine.feedback.navi.ReportErrorListPage", "com.autonavi.mine.feedback.fragment.DoorAddressUploadPage", "com.autonavi.mine.feedbackv2.drivenavigationissues.DriveNavigationIssuesListPage", "com.autonavi.bundle.feedback.contribution.page.ContributionSearchPage", "com.autonavi.mine.feedback.fragment.FeedbackMainPage", "com.autonavi.mine.feedback.navi.ReportErrorPicFullScreenPage"})
@KeepName
public final class FEEDBACK_PageAction_DATA extends HashMap<String, Class<?>> {
    public FEEDBACK_PageAction_DATA() {
        put("amap.basemap.action.feedback_report_error_list_page", ReportErrorListPage.class);
        put("amap.basemap.action.feedback_door_address_upload_page", DoorAddressUploadPage.class);
        put("amap.basemap.action.drive_navigation_issue", DriveNavigationIssuesListPage.class);
        put("amap.basemap.action.contribution_search_page", ContributionSearchPage.class);
        put("amap.basemap.action.help_and_feedback_page", FeedbackMainPage.class);
        put("amap.basemap.action.feedback_report_pic_full_screen_page", ReportErrorPicFullScreenPage.class);
    }
}
