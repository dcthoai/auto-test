package vn.softdream.autotest.dto.auth;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class PermissionTreeNode implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String code;
    private boolean disabled;
    private boolean checked;
    private boolean collapsed;
    private Integer parentId;
    private List<PermissionTreeNode> children = new ArrayList<>();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final PermissionTreeNode instance = new PermissionTreeNode();

        public Builder id(Integer id) {
            instance.setId(id);
            return this;
        }

        public Builder name(String name) {
            instance.setName(name);
            return this;
        }

        public Builder code(String code) {
            instance.setCode(code);
            return this;
        }

        public Builder disabled(boolean disabled) {
            instance.setDisabled(disabled);
            return this;
        }

        public Builder checked(boolean checked) {
            instance.setChecked(checked);
            return this;
        }

        public Builder collapsed(boolean collapsed) {
            instance.setCollapsed(collapsed);
            return this;
        }

        public Builder parentId(Integer parentId) {
            instance.setParentId(parentId);
            return this;
        }

        public Builder children(List<PermissionTreeNode> children) {
            instance.setChildren(children);
            return this;
        }

        public Builder appendChild(PermissionTreeNode node) {
            instance.appendChildren(node);
            return this;
        }

        public PermissionTreeNode build() {
            return instance;
        }
    }

    public void appendChildren(PermissionTreeNode node) {
        this.children.add(node);
    }

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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<PermissionTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionTreeNode> children) {
        this.children = children;
    }
}
