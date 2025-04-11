package vn.softdream.autotest.config;

import vn.softdream.autotest.config.properties.JpaConfig;
import vn.softdream.autotest.constants.BaseConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * JPA (Java Persistence API) configurations <p>
 * {@link EnableJpaAuditing} help JPA fills in information about the creator, the modifier in the entities <p>
 * {@link EnableJpaRepositories} allows Spring to auto find and create repositories for entities in the basePackages <p>
 * Helping to reduce the need to write code for basic CRUD operations
 *
 * @author thoaidc
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

    private static final Logger log = LoggerFactory.getLogger(PersistenceConfig.class);
    private final JpaConfig jpaConfig;

    public PersistenceConfig(@Qualifier("jpaConfig") JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    /**
     * Helps JPA automatically handle annotations like @{@link CreatedBy}, @{@link LastModifiedBy},... in entities
     * @return {@link AuditorAware}
     */
    @Bean(name = "auditorProvider")
    public AuditorAware<String> auditorProvider() {
        log.debug("AuditorProvider initialized successful");

        return () -> {
            // Get the current username from the SecurityContext, using a default value if no user is authenticated
            String credential = SecurityContextHolder.getContext().getAuthentication().getName();
            String createdBy = Objects.equals(BaseConstants.ANONYMOUS_USER, credential) ? null : credential;
            return Optional.of(Optional.ofNullable(createdBy).orElse(BaseConstants.DEFAULT_CREATOR));
        };
    }

    /**
     * {@link EntityManagerFactory} is where {@link EntityManager} objects are created <p>
     * Each {@link EntityManager} is a session with the database <p>
     * Used to perform operations like `find`, `persist`, `merge`, `remove`, and queries on entities <p>
     * Each {@link EntityManager} maintains a context for the entity objects and can perform CRUD operations
     */
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("dataSource") DataSource dataSource) {
        List<String> basePackages = jpaConfig.getBaseEntityPackages();
        log.debug("EntityManagerFactory initialized successful. Scan for packages: {}", basePackages);

        return builder.dataSource(dataSource)
                .packages(basePackages.toArray(new String[0])) // Convert List to string array
                .persistenceUnit(jpaConfig.getPersistenceUnitName())
                .build();
    }
}
