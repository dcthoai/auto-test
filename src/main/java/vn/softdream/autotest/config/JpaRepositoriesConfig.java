package vn.softdream.autotest.config;

import vn.softdream.autotest.config.properties.JpaConfig;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Specifies the list of packages that Spring will scan and configure for JPA repositories
 * through the value from {@link JpaConfig#getBaseRepositoryPackages()}
 *
 * @author thoaidc
 */
@SuppressWarnings("unused")
@EnableJpaRepositories(basePackages = "#{@jpaProperties.baseRepositoryPackages.toArray(new String[0])}")
public class JpaRepositoriesConfig {}
