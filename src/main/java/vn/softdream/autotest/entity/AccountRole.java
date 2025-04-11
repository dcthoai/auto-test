package vn.softdream.autotest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert // Hibernate only insert the nonnull columns to the database instead of insert the entire table
@DynamicUpdate // Hibernate only updates the changed columns to the database instead of updating the entire table
@Table(name = "account_role")
@SuppressWarnings("unused")
public class AccountRole extends AbstractAuditingEntity {

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    public AccountRole() {}

    public AccountRole(Integer accountId, Integer roleId) {
        this.accountId = accountId;
        this.roleId = roleId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
