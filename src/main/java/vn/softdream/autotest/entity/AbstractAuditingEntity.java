package vn.softdream.autotest.entity;

import vn.softdream.autotest.config.PersistenceConfig;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * Provides a mechanism to automatically track information about data creation and modification in entities<p>
 * This is part of the Auditing model in Hibernate/Spring Data JPA <p>
 * Which helps automatically record who created, modified data and when <p>
 * {@link EntityListeners}({@link AuditingEntityListener}.class) to enable {@link PersistenceConfig#auditorProvider()}
 *
 * @author thoaidc
 */
@DynamicInsert // Hibernate only insert the nonnull columns to the database instead of insert the entire table
@DynamicUpdate // Hibernate only updates the changed columns to the database instead of updating the entire table
@MappedSuperclass // Make this class a superclass that other entities can inherit from
@EntityListeners(AuditingEntityListener.class)
@SuppressWarnings("unused")
public abstract class AbstractAuditingEntity implements Serializable {

    // Used to identify the version of the class when performing serialization.
    // Ensures compatibility when serialized data is read from different versions of the class.
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Used by Spring Data JPA to automatically populate information about the record creator <p>
     * Requires additional AuditorAware configuration to identify the current user <p>
     * Use JPA Auditing configuration in {@link PersistenceConfig} to auto set values
     */
    @CreatedBy
    @Column(name = "created_by", length = 45, updatable = false)
    private String createdBy;

    // Automatically saves the time the record was created (usually using system time)
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    /**
     * Automatically save the information of the person who made the last edit <p>
     * Requires additional AuditorAware configuration to identify the current user <p>
     * Use JPA Auditing configuration in {@link PersistenceConfig} to auto set values
     */
    @LastModifiedBy
    @Column(name = "last_modified_by", length = 45, updatable = false)
    private String lastModifiedBy;

    // Automatically saves the time of the last edit (usually using system time)
    @LastModifiedDate
    @Column(name = "last_modified_date", updatable = false)
    private Instant lastModifiedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }
}
