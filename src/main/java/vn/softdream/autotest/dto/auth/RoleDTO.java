package vn.softdream.autotest.dto.auth;

import vn.softdream.autotest.dto.response.AuditingEntityDTO;

import java.util.HashSet;
import java.util.Set;

public class RoleDTO extends AuditingEntityDTO {

    private Integer id;
    private String name;
    private String code;
    Set<String> permissions = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
