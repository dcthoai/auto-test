package vn.softdream.autotest.dto.mapping;

public interface IAuthenticationDTO {

    Integer getId();
    String getUsername();
    String getPassword();
    String getEmail();
    String getStatus();
    String getDeviceId();
}
