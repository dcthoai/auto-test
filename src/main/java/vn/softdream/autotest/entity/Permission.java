package vn.softdream.autotest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert // Hibernate only insert the nonnull columns to the database instead of insert the entire table
@DynamicUpdate // Hibernate only updates the changed columns to the database instead of updating the entire table
@Table(name = "permission")
@SuppressWarnings("unused")
public class Permission extends AbstractAuditingEntity {

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "code", nullable = false, length = 45, unique = true)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "parent_code", length = 45)
    private String parentCode;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
