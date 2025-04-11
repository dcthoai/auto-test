package vn.softdream.autotest.dto.request;

import vn.softdream.autotest.constants.BaseConstants;
import vn.softdream.autotest.constants.ExceptionConstants;
import vn.softdream.autotest.exception.handler.CustomExceptionHandler;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to map with register requests in a manual authenticate flow <p>
 * The @{@link Valid} annotation is used along with @{@link ResponseBody} to validate input data format <p>
 * Annotations like @{@link Pattern}, @{@link NotBlank} will be automatically handled by Spring <p>
 * {@link MethodArgumentNotValidException} will be thrown with the predefined message key
 * if any of the validated fields contain invalid data <p>
 * This exception is configured to be handled by {@link CustomExceptionHandler}.handleMethodArgumentNotValid()
 *
 * @author thoaidc
 */
public class CreateAccountRequestDTO extends BaseRequestDTO {

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

    @NotBlank(message = ExceptionConstants.PASSWORD_NOT_BLANK)
    @Size(min = 8, message = ExceptionConstants.PASSWORD_MIN_LENGTH)
    @Size(max = 20, message = ExceptionConstants.PASSWORD_MAX_LENGTH)
    @Pattern(regexp = BaseConstants.REGEX.PASSWORD_PATTERN, message = ExceptionConstants.PASSWORD_INVALID)
    private String password;

    @Size(min = 1, message = ExceptionConstants.ROLE_PERMISSIONS_NOT_EMPTY)
    private List<Integer> roleIds = new ArrayList<>();

    @Size(max = 100, message = ExceptionConstants.NAME_MAX_LENGTH)
    private String fullname;

    @Size(max = 255, message = ExceptionConstants.ADDRESS_MAX_LENGTH)
    private String address;

    @Size(min = 6, message = ExceptionConstants.PHONE_MIN_LENGTH)
    @Size(max = 20, message = ExceptionConstants.PHONE_MAX_LENGTH)
    @Pattern(regexp = BaseConstants.REGEX.PHONE_PATTERN, message = ExceptionConstants.PHONE_INVALID)
    private String phone;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
