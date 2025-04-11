package vn.softdream.autotest.dto.request;

import vn.softdream.autotest.constants.BaseConstants;
import vn.softdream.autotest.constants.ExceptionConstants;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class UpdateAccountRequestDTO extends BaseRequestDTO {

    @NotNull(message = ExceptionConstants.ID_NOT_NULL)
    @Min(value = 1, message = ExceptionConstants.ID_INVALID)
    private Integer id;

    @Size(max = 100, message = ExceptionConstants.NAME_MAX_LENGTH)
    private String fullname;

    @NotBlank(message = ExceptionConstants.USERNAME_NOT_BLANK)
    @Size(min = 2, message = ExceptionConstants.USERNAME_MIN_LENGTH)
    @Size(max = 45, message = ExceptionConstants.USERNAME_MAX_LENGTH)
    @Pattern(regexp = BaseConstants.REGEX.USERNAME_PATTERN, message = ExceptionConstants.USERNAME_INVALID)
    private String username;

    @NotBlank(message = ExceptionConstants.EMAIL_NOT_BLANK)
    @Size(min = 6, message = ExceptionConstants.EMAIL_MIN_LENGTH)
    @Size(max = 100, message = ExceptionConstants.EMAIL_MAX_LENGTH)
    @Pattern(regexp = BaseConstants.REGEX.EMAIL_PATTERN, message = ExceptionConstants.EMAIL_INVALID)
    private String email;

    @Size(min = 6, message = ExceptionConstants.PHONE_MIN_LENGTH)
    @Size(max = 20, message = ExceptionConstants.PHONE_MAX_LENGTH)
    @Pattern(regexp = BaseConstants.REGEX.PHONE_PATTERN, message = ExceptionConstants.PHONE_INVALID)
    private String phone;

    @Size(max = 255, message = ExceptionConstants.ADDRESS_MAX_LENGTH)
    private String address;

    @NotBlank(message = ExceptionConstants.STATUS_NOT_BLANK)
    @Pattern(regexp = BaseConstants.REGEX.ACCOUNT_STATUS_PATTERN, message = ExceptionConstants.STATUS_INVALID)
    private String status;

    @Size(min = 1, message = ExceptionConstants.ROLE_PERMISSIONS_NOT_EMPTY)
    private List<Integer> roleIds = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
