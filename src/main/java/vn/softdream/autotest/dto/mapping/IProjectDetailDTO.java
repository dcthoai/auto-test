package vn.softdream.autotest.dto.mapping;

import java.time.Instant;

public interface IProjectDetailDTO {

    Integer getId();
    String getThumbnailSquare();
    String getThumbnailRect();
    String getSubName();
    String getName();
    String getCategoryName();
    String getTitle();
    String getDescription();
    String getMoreDescription();
    String getCustomer();
    Instant getFinishedDate();
    String getLinkDemo();
    String getLinkDemoTitle();
}
