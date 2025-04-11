package vn.softdream.autotest.dto.request;

import vn.softdream.autotest.constants.ExceptionConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class CreateRoleRequestDTO extends BaseRequestDTO {

    @NotBlank(message = ExceptionConstants.NAME_NOT_BLANK)
    private String name;

    @NotBlank(message = ExceptionConstants.CODE_NOT_BLANK)
    private String code;

    @Size(min = 1, message = ExceptionConstants.ROLE_PERMISSIONS_NOT_EMPTY)
    private List<Integer> permissionIds = new ArrayList<>();

    public CreateRoleRequestDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Integer> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Integer> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
