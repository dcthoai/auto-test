package vn.softdream.autotest.dto.mapping;

public interface IPermissionDTO {

    Integer getId();
    String getName();
    String getCode();
    String getDescription();
    Integer getParentId();
    String getParentCode();
}
